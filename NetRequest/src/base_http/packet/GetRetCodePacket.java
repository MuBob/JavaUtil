package base_http.packet;

import org.json.JSONException;

/**
 * Created by test on 2016/4/19.
 */
public class GetRetCodePacket extends OutPacket {

    public int mUid;
    public String mToken;

    @Override
    public String packBody() throws JSONException {
        mJsonObj.put("uid", mUid);
        mJsonObj.put("token", mToken);
        putCommParam();
        putSignkey(mUid, mToken);
        return mJsonObj.toString();
    }
}
