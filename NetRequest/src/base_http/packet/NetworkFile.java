package base_http.packet;

import java.io.File;

/**
 * Created by Administrator on 2017/8/9.
 */

public class NetworkFile {
    private String postKey;
    private String fileName;
    private File file;
    private String destFileDir;

    /**
     * 两参，下载文件
     * @param destFileDir
     * @param destFileName
     */
    public NetworkFile(String destFileDir, String destFileName) {
        this.destFileDir=destFileDir;
        this.postKey = destFileName;
    }

    /**
     * 三参，上传文件
     * @param postKey
     * @param fileName
     * @param file
     */
    public NetworkFile(String postKey, String fileName, File file) {
        this.postKey = postKey;
        this.fileName = fileName;
        this.file = file;
    }

    @Override
    public String toString() {
        return "NetworkFile{" +
                "postKey='" + postKey + '\'' +
                ", fileName='" + fileName + '\'' +
                ", file=" + file +
                ", destFileDir='" + destFileDir + '\'' +
                '}';
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public void setDestFileDir(String destFileDir) {
        this.destFileDir = destFileDir;
    }

    public String getPostName() {
        return postKey;
    }

    public void setPostName(String postKey) {
        this.postKey = postKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
