package work.inter;

public interface LogLineCallback {
    void onReadLine(String fileName, int line, String content);
    void afterReadLine(String fileName, int line);
}
