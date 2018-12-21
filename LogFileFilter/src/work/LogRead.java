package work;

import util.FileReaderCallback;
import util.FileReaderTXT;
import work.inter.LogLineCallback;

import java.util.concurrent.atomic.AtomicInteger;

import static work.ReadState.*;


public class LogRead implements FileReaderCallback{
    protected String fileName;
    FileReaderTXT fileReaderTXT;
    @STATE
    public AtomicInteger readState;
    public LogRead(String fileName) {
        this.fileName = fileName;
        fileReaderTXT=new FileReaderTXT(fileName);
        readState=new AtomicInteger(STATE_DEFAULT);
    }

    public long getLines(){
        long lines = fileReaderTXT.getLines();
        return lines;
    }

    public void readLines(long from, long length, LogLineCallback callback){
        this.callback=callback;
        fileReaderTXT.readFileByLines(from, length, this);
    }

    private LogLineCallback callback;

    @Override
    public void onFileReadLine(long line, String content) {
        callback.onReadLine(fileName, line, content);
    }

    @Override
    public void afterFileReadLine(String fileName, long lines) {
        callback.afterReadLine(fileName, lines);
    }

    @STATE
    public synchronized int getState() {
        return readState.get();
    }
    public synchronized void setState(@STATE int state) {
        readState.set(state);
    }

    public void stPostParent(){
        setState(STATE_POST_PARENT);
    }

    public void stFullWait(){
        setState(STATE_FULL_WAIT_CHILD);
    }

    public void stFullReceive(){
        setState(STATE_FULL_RECEIVE_CHILD);
    }

    public void stFullReceiveEnd(){
        setState(STATE_FULL_RECEIVE_CHILD_END);
    }
    public void stEmptyWait(){
        setState(STATE_EMPTY_WAIT_CHILD);
    }
}
