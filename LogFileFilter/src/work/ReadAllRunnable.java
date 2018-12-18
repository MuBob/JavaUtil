package work;

public class ReadAllRunnable extends LogRead implements Runnable{
    private LogReadCallback callback;
    private LogWrite logWrite;

    public ReadAllRunnable(String fileName, LogWrite logWrite, LogReadCallback logCallback) {
        super(fileName);
        this.callback=logCallback;
        this.logWrite=logWrite;
    }

    public void readFile(long from, long length){
        for (long i = 0; i < length; i++) {
            String lineStr = getLineStr(from+i);
            logWrite.writeLine(lineStr);
        }
    }

    @Override
    public void run() {
        readFile(0, getLines());
        if (callback!=null){
            callback.onComplete(this, fileName);
        }
    }
}
