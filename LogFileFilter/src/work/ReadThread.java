package work;

import content.Config;
import content.Path;
import util.Log;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

public class ReadThread {
    private AtomicLong threadCount;
    private Stack<Thread> threadList;
    private long temp;

    private ReadThread() {
        threadCount=new AtomicLong(0);
        threadList=new Stack<>();
    }

    public void startTime(){
        temp=System.currentTimeMillis();
    }
    public void endTime(){
        temp=System.currentTimeMillis()-temp;
    }
    public void printDuration(){
        Log.i("总共用时("+temp+")ms.");
    }
    public static ReadThread getInstance(){
        return ReadThreadHolder.instance;
    }

    private static class ReadThreadHolder{
        private static final ReadThread instance=new ReadThread();
    }

    public long getThreadCount(){
        return threadCount.get();
    }

    public long incrementThreadCount(){
        return threadCount.incrementAndGet();
    }

    public long decrementThreadCount(){
        return threadCount.decrementAndGet();
    }

    public void setThreadCount(long c){
        threadCount.set(c);
    }

    public void push(Thread t){
        threadList.push(t);
    }

    public Thread pop(){
        return threadList.pop();
    }
    public boolean isEmpty(){
        return threadList.isEmpty();
    }

    public void readFileAll(){
        LogFilter filter=new LogFilter(Config.WORD_FILTER);
        LogRead read=new LogRead(Path.getLogPath());
        long lines = read.getLines();
        long part=lines/3;
        ReadFilterRunnable readFilter=null;
        ReadFilterRunnable parentRead=null;
        for (long i = 1; i <= lines; i+=part) {
            String outPath = Path.getOutPath(i);
            Log.i("work thread count="+ReadThread.getInstance().incrementThreadCount()+", name="+outPath);
            LogWrite writeFilter=new LogWrite(outPath, true);
            readFilter=new ReadFilterRunnable(
                    Path.getLogPath(), filter, writeFilter, i, part);
            readFilter.setParentRead(parentRead);
            parentRead=readFilter;
            Thread thread = new Thread(readFilter);
            thread.setName(outPath);
            ReadThread.getInstance().push(thread);
        }
        startTime();
        while (!ReadThread.getInstance().isEmpty()){
            ReadThread.getInstance().pop().start();
        }

    }
}
