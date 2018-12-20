package content;

import util.Log;
import work.*;
import work.inter.LogLineCallback;
import work.inter.LogReadCallback;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

public class Environment implements LogReadCallback, LogLineCallback {
    public static void main(String[] args){
        Environment environment=new Environment();
        Config.WORD_FILTER="PlaySerTAG";
        environment.readFileAll();
    }


    private AtomicLong threadCount;
    private Stack<Thread> threadList;

    private void readFileAll(){
        LogFilter filter=new LogFilter(Config.WORD_FILTER);
        LogRead read=new LogRead(Config.getLogPath());
        long lines = read.getLines();
        long part=1024*40;
        threadCount=new AtomicLong(0);
        ReadFilterRunnable readFilter=null;
        threadList=new Stack<>();
        for (long i = 0; i < lines; i+=part) {
            Log.i("work thread count="+threadCount.incrementAndGet());
            String outPath = Config.getOutPath(i);
            LogWrite writeFilter=new LogWrite(outPath);
            readFilter=new ReadFilterRunnable(
                    Config.getLogPath(), filter, writeFilter, i, part, readFilter==null?this:readFilter);
            Thread thread = new Thread(readFilter);
            thread.setName(outPath);
            threadList.push(thread);
        }
        while (!threadList.empty()){
            threadList.pop().start();
        }

    }

    WriteAllRunnable writeAll;
    LogRead readAll;
    @Override
    public void onComplete(LogRead readAllRunnable, String readFileName) {
        readAll=new LogRead(readFileName);
        writeAll=new WriteAllRunnable(readAll, Config.getOutPath(), this);
        writeAll.run();
    }

    @Override
    public void onReadLine(String fileName, int line, String content) {
        writeAll.writeLine(content);
    }

    @Override
    public void afterReadLine(String fileName, int line) {
        Log.i("合并文件来自："+fileName+", 剩余数量="+threadCount.decrementAndGet());
    }
}
