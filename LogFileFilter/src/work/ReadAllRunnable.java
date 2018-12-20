package work;

import work.inter.LogLineCallback;
import work.inter.LogReadCallback;

public class ReadAllRunnable extends LogRead implements Runnable, LogLineCallback {
    private LogLineCallback callback;

    public ReadAllRunnable(String fileName, LogLineCallback logCallback) {
        super(fileName);
        this.callback=logCallback;
    }

    @Override
    public void run() {
        readLines(0, getLines(), this);
    }

    @Override
    public void onReadLine(String fileName, int line, String content) {
        if (callback!=null){
            callback.onReadLine(fileName, line, content);
        }
    }

    @Override
    public void afterReadLine(String fileName, int line) {
        if (callback!=null){
            callback.afterReadLine(fileName, line);
        }
    }
}
