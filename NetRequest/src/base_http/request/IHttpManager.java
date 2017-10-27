package base_http.request;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface IHttpManager {
    /**
     * 发送get请求
     */
    void getRequest(BaseRequestAction action);

    void downFile(BaseDownloadAction action);
    void postFile(BaseRequestAction action);
    /**
     * 取消所有请求，可能中断请求
     */
    void cancelRequest(BaseRequestAction action);

    void cancelDownload(BaseDownloadAction action);
    void cancelAllRequests();
}
