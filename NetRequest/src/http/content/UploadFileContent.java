package http.content;

import base_http.packet.BaseRequestContent;
import base_http.packet.NetworkFile;

import java.io.File;

/**
 * Created by Administrator on 2017/8/9.
 */

public class UploadFileContent extends BaseRequestContent {
    public UploadFileContent() {
        super("upload/client");
    }
    public void setParams(
            int uid,
            String token,
            int uploadType,
            int fileType,
            String filePath,
            String fileExt,
            int zoom,
            String zoomopts){
        File file = new File(filePath);
        String fileName = file.getName();
        getContent().put("uid", uid + "");
        getContent().put("token", token);
        getContent().put("uploadtype", uploadType + "");
        getContent().put("filetype", fileType + "");
        getContent().put("fileext", fileExt);
        setFile(new NetworkFile("filedata", fileName, file));
        putParamSignkey(uid, token);
        if (fileType == 1) {
            getContent().put("zoom", String.valueOf(zoom));
            getContent().put("zoomopts", zoomopts);
        }

    }
}
