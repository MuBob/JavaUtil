package work;

import work.inter.LogLineCallback;
import work.inter.LogReadCallback;

public class ReadAllRunnable extends LogRead implements Runnable {
    private LogLineCallback callback;

    public ReadAllRunnable(String fileName, LogLineCallback logCallback) {
        super(fileName);
        this.callback=logCallback;
    }

    @Override
    public void run() {
        readLines(0, getLines(), callback);
    }
}
