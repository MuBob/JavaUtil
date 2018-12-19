package util;

public interface FileReaderCallback {
    void onFileReadLine(int line, String content);
    void afterFileReadLine(String fileName, int lines);
}
