package base_http.callback;


import java.io.File;

import base_http.packet.BaseRequestContent;
import com.zhy.http.okhttp.callback.FileCallBack;
import okhttp3.Call;
import util.LogUtil;

/**
 * Created by Administrator on 2017/7/27.
 */

public class BaseDownloadCallback extends FileCallBack {
    private BaseRequestContent requestContent;
    private IFileCallback iCallback;

    public BaseDownloadCallback(BaseRequestContent requestContent, IFileCallback iCallback) {
        super(requestContent.getFile().getDestFileDir(), requestContent.getFile().getFileName());
        this.requestContent=requestContent;
        this.iCallback=iCallback;
    }

    public BaseRequestContent getRequestContent() {
        return requestContent;
    }


    public IFileCallback getiCallback() {
        return iCallback;
    }

    @Override
    public void inProgress(float v, long l) {
        iCallback.inProgress(v, l);
    }


    @Override
    public void onError(Call call, Exception e) {
        LogUtil.i("BaseCallbackTAG", "BaseCallback.onError: e="+e);
        iCallback.onError(requestContent, this, e);
        iCallback.endResponse(requestContent, this);
    }

    @Override
    public void onResponse(File file) {
        iCallback.onResponseSuccess(requestContent, this, file);
        iCallback.endResponse(requestContent, this);
    }

}
