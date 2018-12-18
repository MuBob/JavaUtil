package work;

import util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadFilterRunnable extends LogRead implements Runnable, LogReadCallback{
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

    public void readFile(long from, long length){
        for (long i = 0; i < length; i++) {
            String lineStr = getLineStr(from+i);
            if (lineStr!=null&&lineStr.contains(filter.getMatchWord())){
                writeFilter.writeLine(lineStr);
            }
        }
    }

    @Override
    public void run() {
        readState.set(0);
        readFile(readLineFrom, readLineLength);
        if (callback!=null){
            //还要给上级汇报，继续做
            callback.onComplete(this, writeFilter.getOutName());
        }
        //给上级的做完了
        readState.set(2);
        //那我自己可以清空文件了，如果下级再有提交直接传给上级
        writeFilter.afterWrite();
        while (true){
            //等待下级的提交
            if (readState.get()<3) {
                readState.set(3);
            }else if (readState.get()>3){
                //下级提交完就退出
                break;
            }
        }
    }

    /**
     * 下级Thread完成任务后继续做这些
     * @param readRunnable
     * @param readFileName
     */
    @Override
    public synchronized void onComplete(LogRead readRunnable, String readFileName) {
        while (true){
            int rState = readState.get();
            Log.i("来自："+readFileName+". 写入状态："+rState+", 上级："+writeFilter.getOutName());
            if (rState==0){
                //上级正在做，自己等会,通知我的下级可以先给我提交着
                readRunnable.readState.set(1);
                sleepThread();
            }else if (rState==1){
                //上级正在等待给上级的上级提交中。我先给上级提交

                ReadAllRunnable readAll=new ReadAllRunnable(readFileName, writeFilter, callback);
                readAll.run();
                break;
            }else if (rState==2){
                //上级提交完了，他的文本已经空了，只能写给上级的上级了
                if (callback!=null) {
                    callback.onComplete(this, readFileName);
                }
                break;
            }else if (rState>=3){
                //上级的提交完了，等待我给他提交，但是提交了也不做，继续上传给上级的上级做
                if (callback!=null) {
                    callback.onComplete(this, readFileName);
                }
                readState.set(4);
                break;
            }
        }
    }

    private void sleepThread() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
