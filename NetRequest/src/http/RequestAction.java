package http;


import base_http.callback.ICallback;
import base_http.packet.BaseRequestContent;
import base_http.request.BaseRequestAction;

/**
 * Created by Administrator on 2017/8/1.
 */

public class RequestAction<T extends BaseRequestContent> extends BaseRequestAction<T> {
    public RequestAction(T requestContent, ICallback iCallback) {
        super(requestContent, iCallback);
    }
}
