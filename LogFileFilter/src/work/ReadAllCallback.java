package work;

import util.Log;
import work.inter.LogLineCallback;

public class ReadAllCallback implements LogLineCallback {
    LogWrite writeAllRunnable;

    public ReadAllCallback(LogWrite writeAllRunnable) {
        this.writeAllRunnable = writeAllRunnable;
    }

    @Override
    public void onReadLine(String fileName, long line, String content) {
        writeAllRunnable.writeLine(content);
    }

    @Override
    public void afterReadLine(String fileName, long line) {
        long remind = ReadThread.getInstance().decrementThreadCount();
        Log.i("合并文件来自："+fileName+", 剩余数量="+remind);
        if (remind<=0){
            ReadThread.getInstance().endTime();
            ReadThread.getInstance().printDuration();
        }
    }
}
