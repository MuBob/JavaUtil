package work;

public class WriteAllRunnable extends LogWrite implements Runnable {
    private LogRead readLogs;

    public WriteAllRunnable(LogRead readLogs, String outName) {
        super(outName, true);
        this.readLogs=readLogs;
    }

    @Override
    public void run() {
        writeAll();
    }

    private void writeAll() {
        long lines = readLogs.getLines();
        for (int i = 0; i < lines; i++) {
            writeLine(readLogs.getLineStr(i));
        }
    }
}
