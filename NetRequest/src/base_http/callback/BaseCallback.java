package base_http.callback;

import base_http.packet.BaseRequestContent;
import base_http.response.BaseResponseResult;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import util.LogUtil;


/**
 * Created by Administrator on 2017/7/27.
 */

public class BaseCallback extends Callback<BaseResponseResult> {
    private BaseRequestContent requestContent;
    private ICallback iCallback;

    public BaseCallback(BaseRequestContent requestContent,  ICallback iCallback) {
        this.requestContent = requestContent;
        this.iCallback=iCallback;
    }

    public BaseRequestContent getRequestContent() {
        return requestContent;
    }


    public ICallback getiCallback() {
        return iCallback;
    }

    @Override
    public BaseResponseResult parseNetworkResponse(Response response) throws Exception {
        LogUtil.i("BaseCallbackTAG", "BaseCallback.parseNetworkResponse: requestContent="+requestContent);
        LogUtil.i("BaseCallbackTAG", "BaseCallback.parseNetworkResponse: response="+response);
        BaseResponseResult result=new BaseResponseResult();
        if(response.isSuccessful()){
            ResponseBody body = response.body();
            String bodyStr=body.string();
            LogUtil.i("TAG", "BaseCallback.parseNetworkResponse: body="+bodyStr);
            JSONObject obj=new JSONObject(bodyStr);
            int statusCode=obj.optInt("rcode");
            String msg=obj.optString("msg");
            JSONObject dataObject=obj.optJSONObject("data");
            result.setStatusCode(statusCode);
            result.setMessage(msg);
            if(dataObject!=null){
                result.setData(dataObject.toString());
            }else {
                result.setData("");
            }
        }else {
            result.setMessage("Callback解析，网络请求失败");
            result.setStatusCode(response.code());
        }
        LogUtil.i("BaseCallbackTAG", "BaseCallback.parseNetworkResponse: result="+result);
        return result;
    }

    @Override
    public void onError(Call call, Exception e) {
        LogUtil.i("BaseCallbackTAG", "BaseCallback.onError: e="+e);
        iCallback.endResponse(requestContent, this);
        iCallback.onError(requestContent, this, e);
    }

    @Override
    public void onResponse(BaseResponseResult result) {
        LogUtil.i("BaseCallbackTAG", "BaseCallback.onResponse: requestContent="+requestContent);
        LogUtil.i("BaseCallbackTAG", "BaseCallback.onResponse: result="+result);
        try {
            iCallback.endResponse(requestContent, this);
            if (result.isSuccess()) {
                iCallback.onResponseSuccess(requestContent, this, result);
            } else {
                iCallback.onResponseFail(requestContent, this, result);
            }
        }catch (Exception e){
            e.printStackTrace();
            onError(null, e);
        }
    }


    @Override
    public String toString() {
        return "BaseCallback{" +
                "requestContent=" + requestContent +
                '}';
    }

}
