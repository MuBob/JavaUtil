package work;

import work.inter.LogLineCallback;

public class WriteAllRunnable extends LogWrite implements Runnable {
    private LogRead readLogs;
    private LogLineCallback lineCallback;

    public WriteAllRunnable(LogRead readLogs, String outName, LogLineCallback callback) {
        super(outName, true);
        this.readLogs=readLogs;
        this.lineCallback=callback;
    }

    @Override
    public void run() {
        readLogs.readLines(0, readLogs.getLines(), lineCallback);
    }

}
