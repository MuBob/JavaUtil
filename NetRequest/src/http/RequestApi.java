package http;


import base_http.request.BaseDownloadAction;
import base_http.request.BaseRequestAction;
import base_http.request.HttpManager;
import base_http.request.IHttpManager;

/**
 * Created by Administrator on 2017/7/28.
 */

public class RequestApi implements IHttpManager {

    @Override
    public void getRequest(BaseRequestAction action) {
        if(action!=null){
            HttpManager.getInstance().getRequest(action);
        }
    }

    @Override
    public void downFile(BaseDownloadAction action) {
        if(action!=null){
            HttpManager.getInstance().downFile(action);
        }
    }

    @Override
    public void postFile(BaseRequestAction action) {
        if(action!=null){
            HttpManager.getInstance().postFile(action);
        }
    }

    @Override
    public void cancelRequest(BaseRequestAction action) {
        HttpManager.getInstance().cancelRequest(action);
    }

    @Override
    public void cancelDownload(BaseDownloadAction action) {
        HttpManager.getInstance().cancelDownload(action);
    }

    @Override
    public void cancelAllRequests() {
        HttpManager.getInstance().cancelAllRequests();
    }
}
