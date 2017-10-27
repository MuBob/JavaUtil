package base_http.callback;

import base_http.packet.StandandAckPacket;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by test on 2016/4/19.
 */
public abstract class StandandCallback extends Callback<StandandAckPacket> {

    @Override
    public StandandAckPacket parseNetworkResponse(Response response) throws Exception {
        String responseBody = response.body().string();
        StandandAckPacket inPacket = new StandandAckPacket();
        inPacket.parse(responseBody);
        return inPacket;
    }
}
