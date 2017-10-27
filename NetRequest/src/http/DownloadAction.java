package http;


import base_http.callback.IFileCallback;
import base_http.packet.BaseRequestContent;
import base_http.request.BaseDownloadAction;

/**
 * Created by Administrator on 2017/8/17.
 */

public class DownloadAction<C extends BaseRequestContent> extends BaseDownloadAction<C> {
    public DownloadAction(C requestContent, IFileCallback iCallback) {
        super(requestContent, iCallback);
    }
}
