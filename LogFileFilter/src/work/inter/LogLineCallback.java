package work.inter;

public interface LogLineCallback {
    void onReadLine(String fileName, long line, String content);
    void afterReadLine(String fileName, long line);
}
