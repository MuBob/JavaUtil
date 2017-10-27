package http;

import android.util.Log;
import base_http.callback.BaseCallback;
import base_http.callback.ICallback;
import base_http.packet.BaseRequestContent;
import base_http.response.BaseResponseResult;
import org.json.JSONException;
import util.LogUtil;

/**
 * Created by Administrator on 2017/10/25.
 */
public class RequestCallback extends ICallback {
    @Override
    public void beforeRequest(BaseRequestContent content, BaseCallback callback) {
        LogUtil.i("RequestCallback.beforeRequest", "content="+content);
    }

    @Override
    public void onResponseSuccess(BaseRequestContent content, BaseCallback callback, BaseResponseResult result) throws JSONException {
        LogUtil.i("RequestCallback.onResponseSuccess", "result="+result);
    }

    @Override
    public void onResponseFail(BaseRequestContent content, BaseCallback callback, BaseResponseResult result) throws JSONException {
        LogUtil.i("RequestCallback.onResponseFail", "result="+result);
    }

    @Override
    public void endResponse(BaseRequestContent content, BaseCallback callback) {
        LogUtil.i("RequestCallback.endResponse", "content="+content);
    }

    @Override
    public void onError(BaseRequestContent content, BaseCallback callback, Exception e) {
        LogUtil.i("RequestCallback.onError", "onResponseFail: e="+e.getMessage());
    }
}
