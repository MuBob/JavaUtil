package base_http.callback;


import base_http.packet.BaseRequestContent;
import base_http.response.BaseResponseResult;
import org.json.JSONException;

/**
 * Created by Administrator on 2017/7/31.
 */

public abstract class ICallback<C extends BaseRequestContent> {

    //在主线程做网络请求之前的操作，比如打开加载框，检查网络连接等
    public abstract void beforeRequest(C content, BaseCallback callback);

    //在此处做网络请求成功之后的操作，正常操作
    public abstract void onResponseSuccess(C content, BaseCallback callback, BaseResponseResult result) throws JSONException;

    //在此处做请求失败的操作
    public abstract void onResponseFail(C content, BaseCallback callback, BaseResponseResult result) throws JSONException;

    //在此处做网络请求之后的结束操作，比如关闭加载框
    public abstract void endResponse(C content, BaseCallback callback);

    //网络请求过程中出现异常会调用此方法，比如断网异常等
    //注意：在调用onError()之后仍然会调用endResponse()
    public abstract void onError(C content, BaseCallback callback, Exception e);

    public void inProgress(float progress){}


}
