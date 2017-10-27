package base_http.callback;

import base_http.packet.LoginAckPacket;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by test on 2016/4/19.
 */
public abstract class LoginCallback extends Callback<LoginAckPacket> {

    @Override
    public LoginAckPacket parseNetworkResponse(Response response) throws Exception {
        String responseBody = response.body().string();
        LoginAckPacket inPacket = new LoginAckPacket();
        inPacket.parse(responseBody);
        return inPacket;
    }


}
