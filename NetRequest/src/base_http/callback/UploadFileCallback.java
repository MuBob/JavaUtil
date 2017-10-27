package base_http.callback;

import base_http.packet.UploadFileAckPacket;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by test on 2016/4/19.
 */
public abstract class UploadFileCallback extends Callback<UploadFileAckPacket> {

    public int mThumbWidth;
    public int mThumbHeight;

    @Override
    public UploadFileAckPacket parseNetworkResponse(Response response) throws Exception {
        String responseBody = response.body().string();
        UploadFileAckPacket inPacket = new UploadFileAckPacket();
        inPacket.parse(responseBody);
        return inPacket;
    }
}
