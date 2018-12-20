package work;

import util.Log;
import work.inter.LogLineCallback;
import work.inter.LogReadCallback;

import static work.ReadState.*;

public class ReadFilterRunnable extends LogRead implements Runnable, LogReadCallback, LogLineCallback {
    private LogFilter filter;
    private LogWrite writeFilter;
    private long readLineFrom;
    private long readLineLength;
    private LogReadCallback callback;

    public ReadFilterRunnable(String fileName, LogFilter filter, LogWrite writeFilter, long readLineFrom, long readLineLength, LogReadCallback logCallback) {
        super(fileName);
        this.readLineFrom = readLineFrom;
        this.readLineLength = readLineLength;
        this.filter=filter;
        this.writeFilter=writeFilter;
        this.callback=logCallback;
    }


    @Override
    public void run() {
        readState.set(STATE_DO_MYSELF);
        readLines(readLineFrom, readLineLength, this);
    }

    @Override
    public void onReadLine(String fileName, int line, String content) {
        if (content!=null&&content.contains(filter.getMatchWord())){
            writeFilter.writeLine(line+" "+content);
        }
    }
    @Override
    public void afterReadLine(String fileName, int lines) {
        setState(this, STATE_POST_PARENT);
        if (callback!=null){
            //还要给上级汇报，继续做
            callback.onComplete(this, writeFilter.getOutName());
        }
        while(getState(this)==STATE_FULL_RECEIVE_CHILD){

        }
        setState(this, STATE_EMPTY_WAIT_CHILD);
        //那我自己可以清空文件了，如果下级再有提交直接传给上级
        writeFilter.afterWrite();
        Thread.currentThread().interrupt();
    }

    @STATE
    private  int lastState;
    /**
     * 下级Thread完成任务后继续做这些
     * @param myReadRunnable
     * @param readFileName
     */
    @Override
    public void onComplete(LogRead myReadRunnable, String readFileName) {
        lastState=STATE_DEFAULT;
        while (true){
            synchronized (this) {

                int rState = getState(this);
                if (lastState != rState) {
                    Log.i("\t\t\t来自：" + readFileName + ", 状态：" + getStateStr(getState(myReadRunnable)));
                    Log.i("\t\t\t\t\t上级：" + writeFilter.getOutName() + ". 状态：" + getStateStr(rState));
                }
                lastState = rState;
                if (rState == STATE_DO_MYSELF) {
                    //上级正在做，自己等会,通知我的下级可以先给我提交着
                    if (getState(myReadRunnable) != STATE_FULL_RECEIVE_CHILD) {
                        setState(myReadRunnable, STATE_FULL_WAIT_CHILD);
                    }
                    continue;
                } else if (rState == STATE_POST_PARENT) {
                    //上级正在把他的文件提交到上级的上级线程中，我只能等着了
                    if (getState(myReadRunnable) != STATE_FULL_RECEIVE_CHILD) {
                        setState(myReadRunnable, STATE_FULL_WAIT_CHILD);
                    }
                    continue;
                } else if (rState == STATE_EMPTY_WAIT_CHILD) {
                    //上级提交完了，他的文本已经空了，只能写给上级的上级了
                    if (getState(myReadRunnable) == STATE_FULL_WAIT_CHILD) {
                        setState(myReadRunnable, STATE_POST_PARENT);
                    }
                    if (getState(myReadRunnable) == STATE_POST_PARENT) {
                        handlerUp(myReadRunnable, readFileName);
                        break;
                    }
                    continue;
                } else if (rState == STATE_FULL_WAIT_CHILD) {
                    //上级正在等待给上级的上级提交中。我先给上级提交
                    int mState = getState(myReadRunnable);
                    if (mState == STATE_POST_PARENT || mState == STATE_FULL_WAIT_CHILD) {
                        writeUp(myReadRunnable, readFileName);
                    }
                    continue;
                } else if (rState == STATE_FULL_RECEIVE_CHILD) {
                    //do nothing
                    continue;
                } else if (rState == STATE_FULL_RECEIVE_CHILD_END) {
                    //do nothing
                    setState(ReadFilterRunnable.this, STATE_FULL_WAIT_CHILD);
                    break;
                }
            }
        }
    }

    private void writeUp(LogRead myRunnable, String readFileName) {
        setState(ReadFilterRunnable.this, STATE_FULL_RECEIVE_CHILD);
        ReadAllRunnable readAll=new ReadAllRunnable(readFileName, new LogLineCallback() {
            @Override
            public void onReadLine(String fileName, int line, String content) {
                writeFilter.writeLine(content);
            }

            @Override
            public void afterReadLine(String fileName, int line) {
                if (getState(ReadFilterRunnable.this)==STATE_FULL_RECEIVE_CHILD){
                    setState(ReadFilterRunnable.this, STATE_FULL_RECEIVE_CHILD_END);
                }

            }
        });
        readAll.run();
    }

    private void handlerUp(LogRead readRunnable, String readFileName) {
        if (callback!=null) {
            callback.onComplete(readRunnable, readFileName);
        }
        readRunnable.readState.set(4);
    }

}
