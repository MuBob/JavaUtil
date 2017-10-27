package base_http.request;

import base_http.callback.BaseCallback;
import base_http.callback.BaseDownloadCallback;
import base_http.packet.BaseRequestContent;
import base_http.packet.NetworkFile;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;
import util.Global;
import util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2017/7/28.
 */

public class HttpManager2  {
//    private List<IAction> requests;
//
//    //因为这里继承了接口所以不能通过枚举来实现单例了
//    private static volatile IHttpManager instance = null;
//    private HttpManager2(){
//        requests=new ArrayList<>();
//    }
//    public synchronized static IHttpManager getInstance() {
//        if (instance == null) {
//            synchronized (IHttpManager.class) {
//                if (instance == null) {
//                    instance = new HttpManager2();
//                }
//            }
//        }
//        return instance;
//    }
//
//    @Override
//    public void getRequest(BaseRequestAction action) {
//        try {
//            BaseCallback callback= action.getCallback();
//            BaseRequestContent requestContent = action.getRequestContent();
//            LogUtil.i("TAG", "HttpManager2.getRequest: action="+action);
//            callback.getiCallback().beforeRequest(requestContent, callback);
////            PostFormBuilder url = OkHttpUtils.post().url(Global.getRequestUrl(requestContent.getUrl())).tag(requestContent);
//            PostStringBuilder builder = OkHttpUtils.postString().url(Global.getRequestUrl(requestContent.getUrl())).tag(requestContent);
//            if(requestContent.getHeader()!=null&&!requestContent.getHeader().isEmpty()){
//                builder.headers(requestContent.getHeader());
//            }
//            if(requestContent.getContent()!=null&&!requestContent.getContent().isEmpty()){
//                JSONObject obj=new JSONObject(requestContent.getContent());
//                String content = obj.toString();
//                LogUtil.i("TAG", "HttpManager2.getRequest: content="+content);
//                builder.content(content);
//            }
//            RequestCall requestCall = builder.build();
//            requests.add(action);
//            requestCall.execute(callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void downFile(BaseDownloadAction action) {
//        try {
//            BaseDownloadCallback callback = action.getCallback();
//            BaseRequestContent requestContent = action.getRequestContent();
//            LogUtil.i("TAG", "HttpManager2.getRequest: action="+action);
//            callback.getiCallback().beforeRequest(requestContent, callback);
////            PostFormBuilder url = OkHttpUtils.post().url(Global.getRequestUrl(requestContent.getUrl())).tag(requestContent);
//            GetBuilder builder = OkHttpUtils.get().url(requestContent.getUrl()).tag(requestContent);
//
//            if(requestContent.getHeader()!=null&&!requestContent.getHeader().isEmpty()){
//                builder.headers(requestContent.getHeader());
//            }
//            if(requestContent.getContent()!=null&&!requestContent.getContent().isEmpty()){
////                builder.params(requestContent.getContent());
//            }
//            RequestCall requestCall = builder.build();
//            requests.add(action);
//            requestCall.execute(callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void postFile(BaseRequestAction action) {
//        try {
//            LogUtil.i("TAG", "HttpManager2.getRequest: action="+action);
//            BaseCallback callback= action.getCallback();
//            BaseRequestContent requestContent = action.getRequestContent();
//            callback.getiCallback().beforeRequest(requestContent, callback);
//            PostFormBuilder builder = OkHttpUtils.post().url(Global.getPostFileUrl(requestContent.getUrl())).tag(requestContent);
//            if(requestContent.getHeader()!=null&&!requestContent.getHeader().isEmpty()){
//                builder.headers(requestContent.getHeader());
//            }
////            Map<String, String> contentMap = requestContent.getContent();
//            NetworkFile postFile = requestContent.getFile();
//            if(contentMap!=null&&postFile!=null){
//                builder.params(contentMap);
//                builder.addFile(postFile.getPostName(), postFile.getFileName(), postFile.getFile());
//            }
//            RequestCall requestCall = builder.build();
//            requests.add(action);
//            requestCall.execute(callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void cancelRequest(BaseRequestAction action) {
//        OkHttpUtils.getInstance().cancelTag(action.getRequestContent());
//        requests.remove(action);
//    }
//
//    @Override
//    public void cancelDownload(BaseDownloadAction action) {
//        OkHttpUtils.getInstance().cancelTag(action.getRequestContent());
//        requests.remove(action);
//    }
//
//    @Override
//    public void cancelAllRequests(){
//        for (IAction request : requests) {
//            OkHttpUtils.getInstance().cancelTag(request.getRequestContent());
//            requests.remove(request);
//        }
//    }
}
