package work;

import content.Path;
import util.Log;
import work.inter.LogLineCallback;

import static work.ReadState.*;

public class ReadFilterRunnable extends LogRead implements Runnable, LogLineCallback {
    private LogFilter filter;
    private LogWrite writeFilter;
    private long readLineFrom;
    private long readLineLength;
    private ReadFilterRunnable parentReadRunnable;

    public ReadFilterRunnable(String fileName, LogFilter filter, LogWrite writeFilter, long readLineFrom, long readLineLength, ReadFilterRunnable parentRead) {
        this(fileName, filter, writeFilter, readLineFrom, readLineLength);
        this.parentReadRunnable=parentRead;
    }

    public ReadFilterRunnable(String fileName, LogFilter filter, LogWrite writeFilter, long readLineFrom, long readLineLength) {
        super(fileName);
        this.readLineFrom = readLineFrom;
        this.readLineLength = readLineLength;
        this.filter=filter;
        this.writeFilter=writeFilter;
    }

    public ReadFilterRunnable getParentRead() {
        return parentReadRunnable;
    }

    public void setParentRead(ReadFilterRunnable parentRead) {
        this.parentReadRunnable = parentRead;
    }

    @Override
    public void run() {
        setState(STATE_DO_MYSELF);
        readLines(readLineFrom, readLineLength, this);
    }

    @Override
    public void onReadLine(String fileName, long line, String content) {
        if (content!=null&&content.contains(filter.getMatchWord())){
            writeFilter.writeLine(line+" "+content);
        }
    }
    ReadAllRunnable readAllRunnable;
    LogWrite writeAllRunnable;

    @Override
    public void afterReadLine(String fileName, long lines) {
        stPostParent();
        doPostParent(this, writeFilter.getOutName());
        while(getState()==STATE_FULL_RECEIVE_CHILD){

        }
        stEmptyWait();
        //那我自己可以清空文件了，如果下级再有提交直接传给上级
        doEndMyself();
    }

    private void doPostParent(ReadFilterRunnable readRunnable, String fileName) {
        if (parentReadRunnable!=null){
            //还要给上级汇报，继续做
            parentReadRunnable.onComplete(readRunnable, fileName);
        }else{
            writeAllRunnable=new LogWrite(Path.getOutPath(), true);
            readAllRunnable=new ReadAllRunnable(fileName, new ReadAllCallback(writeAllRunnable));
            readAllRunnable.run();
        }
    }

    private void doEndMyself() {
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
    public void onComplete(ReadFilterRunnable myReadRunnable, String readFileName) {
        lastState=STATE_DEFAULT;
        while (true){
                int rState = getState();
                if (lastState != rState) {
                    Log.i("\t\t\t来自：" + readFileName + ", 状态：" + getStateStr(myReadRunnable.getState()));
                    Log.i("\t\t\t\t\t上级：" + writeFilter.getOutName() + ". 状态：" + getStateStr(rState));
                }
                lastState = rState;
                if (rState == STATE_DO_MYSELF) {
                    //上级正在做，自己等会,通知我的下级可以先给我提交着
                    stayWait(myReadRunnable);
                    continue;
                } else if (rState == STATE_POST_PARENT) {
                    //上级正在把他的文件提交到上级的上级线程中，我只能等着了
                    stayWait(myReadRunnable);
                    continue;
                } else if (rState == STATE_EMPTY_WAIT_CHILD) {
                    //上级提交完了，他的文本已经空了，只能写给上级的上级了
                    boolean isHandler = handlerUp(myReadRunnable, readFileName);
                    if (isHandler) break;
                    else continue;
                } else if (rState == STATE_FULL_WAIT_CHILD) {
                    //上级正在等待给上级的上级提交中。我先给上级提交
                    boolean isWrite = writeUp(myReadRunnable, readFileName);
                    if (isWrite) break;
                    else continue;
                } else if (rState == STATE_FULL_RECEIVE_CHILD) {
                    //do nothing
                    continue;
                } else if (rState == STATE_FULL_RECEIVE_CHILD_END) {
                    //do nothing
                    stFullWait();
                    break;
                }
        }
    }

    private synchronized boolean stayWait(LogRead myReadRunnable) {
        int state = getState();
        if (state!=STATE_POST_PARENT||state!=STATE_DO_MYSELF) return false;
        if (myReadRunnable.getState() != STATE_FULL_RECEIVE_CHILD) {
            myReadRunnable.stFullWait();
            return true;
        }
        return false;
    }


    private synchronized boolean writeUp(LogRead myRunnable, String readFileName) {
        if (getState()!=STATE_FULL_WAIT_CHILD) return false;
        int mState=myRunnable.getState();
        if (mState==STATE_POST_PARENT||mState == STATE_FULL_WAIT_CHILD) {
            stFullReceive();
            myRunnable.stPostParent();
            ReadAllRunnable readAll=new ReadAllRunnable(readFileName, new LogLineCallback() {
                @Override
                public void onReadLine(String fileName, long line, String content) {
                    writeFilter.writeLine(content);
                }

                @Override
                public void afterReadLine(String fileName, long line) {
                    int s=getState();
                    Log.i("中和文件来自："+fileName+", 并入："+writeFilter.getOutName()+", 剩余数量="+ReadThread.getInstance().decrementThreadCount());
                    if (s==STATE_FULL_RECEIVE_CHILD){
                        stFullReceiveEnd();
                    }
                    myRunnable.stEmptyWait();
                }
            });
            readAll.run();
            return true;
        }else if (mState==STATE_EMPTY_WAIT_CHILD){
            return true;
        }
        return false;
    }

    private synchronized boolean handlerUp(ReadFilterRunnable myRunnable, String readFileName) {
        if (getState()!=STATE_EMPTY_WAIT_CHILD) return false;
        if (myRunnable.getState() == STATE_FULL_WAIT_CHILD) {
            myRunnable.stPostParent();
        }
        if (myRunnable.getState() == STATE_POST_PARENT) {
            doPostParent(myRunnable, readFileName);
            myRunnable.stEmptyWait();
            return true;
        }
        return false;
    }

}
