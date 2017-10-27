package base_http.request;


import base_http.callback.BaseCallback;
import base_http.callback.ICallback;
import base_http.packet.BaseRequestContent;

/**
 * 网络请求行为，包括网络请求内容和请求返回处理
 * Created by Administrator on 2017/7/31.
 */

public class BaseRequestAction<T extends BaseRequestContent>
implements IAction<T, BaseCallback>{
    private T requestContent;
    private ICallback iCallback;
    private BaseCallback callback;

    public BaseRequestAction(T requestContent, ICallback iCallback) {
        this.requestContent = requestContent;
        this.iCallback=iCallback;
    }

    @Override
    public BaseCallback initCallbackFactory(){
        if(callback==null){
            callback=new BaseCallback(requestContent, iCallback);
        }
        return callback;
    }

    @Override
    public T getRequestContent() {
        return requestContent;
    }

    @Override
    public BaseCallback getCallback() {
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
