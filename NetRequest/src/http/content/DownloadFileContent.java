package http.content;

import base_http.packet.BaseRequestContent;
import base_http.packet.NetworkFile;

/**
 * Created by Administrator on 2017/8/11.
 */

public class DownloadFileContent extends BaseRequestContent {
    public DownloadFileContent() {
        super("");
    }

    public void setParams(String url, String dir, String filename){
        setUrl(url);
        setFile(new NetworkFile(dir, filename));
    }

}
