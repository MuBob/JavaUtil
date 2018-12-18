package content;

import util.Log;
import work.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Environment implements LogReadCallback{
    public static void main(String[] args){
        Environment environment=new Environment();
        Config.WORD_FILTER="PlaySerTAG";
        environment.readFileAll();
    }


    private AtomicLong threadCount;
    private List<Thread> threadList;

    private void readFileAll(){
        LogFilter filter=new LogFilter(Config.WORD_FILTER);
        LogRead read=new LogRead(Config.getLogPath());
        long lines = read.getLines();
        long part=256;
        threadCount=new AtomicLong(0);
        ReadFilterRunnable readFilter=null;
        threadList=new ArrayList<>();
        for (long i = 0; i < lines; i+=part) {
            threadCount.incrementAndGet();
            String outPath = Config.getOutPath(i);
            LogWrite writeFilter=new LogWrite(outPath);
            readFilter=new ReadFilterRunnable(Config.getLogPath(), filter, writeFilter, i, part, readFilter==null?this:readFilter);
            Thread thread = new Thread(readFilter);
            thread.setName(outPath);
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.start();
        }

    }

    WriteAllRunnable writeAll;
    LogRead readAll;
    @Override
    public synchronized void onComplete(LogRead readAllRunnable, String readFileName) {
        readAll=new LogRead(readFileName);
        writeAll=new WriteAllRunnable(readAll, Config.getOutPath());
        writeAll.run();
        Log.i("合并文件来自："+readFileName);
/*        while (true){
            if (threadCount.decrementAndGet()<=0){
                writeAll.run();
                Log.i("合并文件来自："+readFileName);
                break;
            }
        }*/

    }
}
