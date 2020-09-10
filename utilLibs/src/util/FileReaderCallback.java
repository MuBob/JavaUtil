package util;

public interface FileReaderCallback {
    void onFileReadLine(long line, String content);
    void afterFileReadLine(String fileName, long lines);
}
