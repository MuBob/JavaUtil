package http.callback;


import base_http.callback.BaseCallback;
import base_http.callback.ICallback;
import base_http.packet.BaseRequestContent;

/**
 * Created by Administrator on 2017/7/28.
 */

public class RequestCallback extends BaseCallback {

    public RequestCallback(BaseRequestContent requestContent, ICallback iCallback) {
        super(requestContent, iCallback);
    }
}
