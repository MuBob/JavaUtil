package base_http.request;

import base_http.callback.BaseCallback;
import base_http.packet.BaseRequestContent;
import http.HttpsClientUtil;
import okhttp3.*;
import org.json.JSONObject;
import util.Global;
import util.LogUtil;

/**
 * Created by Administrator on 2017/10/25.
 */
public class HttpManager  implements IHttpManager {

    //因为这里继承了接口所以不能通过枚举来实现单例了
    private static volatile IHttpManager instance = null;
    private HttpManager(){
    }
    public synchronized static IHttpManager getInstance() {
        if (instance == null) {
            synchronized (IHttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    @Override
    public void getRequest(BaseRequestAction action){
        BaseCallback callback= action.getCallback();
        BaseRequestContent requestContent = action.getRequestContent();
        LogUtil.i("TAG", "HttpManager2.getRequest: action="+action);
        callback.getiCallback().beforeRequest(requestContent, callback);
        requestContent.putParamComm();
//            PostFormBuilder url = OkHttpUtils.post().url().tag(requestContent);
//        PostStringBuilder builder = OkHttpUtils.postString().url(Global.getRequestUrl(requestContent.getUrl())).tag(requestContent);
        if(requestContent.getContent()!=null&&!requestContent.getContent().isEmpty()){
            JSONObject obj=new JSONObject(requestContent.getContent());
            String content = obj.toString();
            LogUtil.i("TAG", "HttpManager2.getRequest: content="+content);
        }
        try {
            String result = HttpsClientUtil.post(Global.getRequestUrl(requestContent.getUrl()), requestContent.getContent());
            LogUtil.i("TAG","HttpManager2.getRequest:result="+result);
            ResponseBody body=ResponseBody.create(MediaType.parse("json"), result);
            Response.Builder builder=new Response.Builder();
            builder.body(body);
            Request.Builder builderRequest=new Request.Builder();
            builderRequest.url(Global.getRequestUrl(requestContent.getUrl()));
            Request request=builderRequest.build();
            builder.request(request);
            builder.protocol(Protocol.HTTP_1_0);
            builder.code(1);
            Response response = builder.build();
            action.getCallback().parseNetworkResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            action.getCallback().onError(null, e);
        }
    }

    @Override
    public void downFile(BaseDownloadAction action) {

    }

    @Override
    public void postFile(BaseRequestAction action) {

    }

    @Override
    public void cancelRequest(BaseRequestAction action) {

    }

    @Override
    public void cancelDownload(BaseDownloadAction action) {

    }

    @Override
    public void cancelAllRequests() {

    }
}
