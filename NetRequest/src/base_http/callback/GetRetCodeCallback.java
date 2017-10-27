package base_http.callback;

import base_http.packet.GetRetCodeAckPacket;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by test on 2016/4/19.
 */
public abstract class GetRetCodeCallback extends Callback<GetRetCodeAckPacket> {

    @Override
    public GetRetCodeAckPacket parseNetworkResponse(Response response) throws Exception {
        String responseBody = response.body().string();
        GetRetCodeAckPacket inPacket = new GetRetCodeAckPacket();
        inPacket.parse(responseBody);
        return inPacket;
    }
}
