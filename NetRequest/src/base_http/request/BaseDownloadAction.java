package base_http.request;

import base_http.callback.BaseDownloadCallback;
import base_http.callback.IFileCallback;
import base_http.packet.BaseRequestContent;

/**
 * 网络请求行为，包括网络请求内容和请求返回处理
 * Created by Administrator on 2017/7/31.
 */

public class BaseDownloadAction<T extends BaseRequestContent>
implements IAction<T, BaseDownloadCallback>{
    private T requestContent;
    private IFileCallback iCallback;
    private BaseDownloadCallback callback;

    public BaseDownloadAction(T requestContent, IFileCallback iCallback) {
        this.requestContent = requestContent;
        this.iCallback=iCallback;
    }

    @Override
    public BaseDownloadCallback initCallbackFactory(){
        if(callback==null){
            callback=new BaseDownloadCallback(requestContent, iCallback);
        }
        return callback;
    }

    @Override
    public T getRequestContent() {
        return requestContent;
    }

    @Override
    public BaseDownloadCallback getCallback() {
        return initCallbackFactory();
    }

    @Override
    public String toString() {
        return "BaseRequestAction{" +
                "requestContent=" + requestContent +
                ", iCallback=" + iCallback +
                ", callback=" + callback +
                '}';
    }
}
