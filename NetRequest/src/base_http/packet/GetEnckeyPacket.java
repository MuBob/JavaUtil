package base_http.packet;

import org.json.JSONException;

/**
 * Created by test on 2016/4/19.
 */
public class GetEnckeyPacket extends OutPacket {

    public String mEncTime;
    public String mToken;
    public String mUserEncTime;
    public int mUid;

    @Override
    public String packBody() throws JSONException {
        mJsonObj.put("enctime", mEncTime);
        mJsonObj.put("token", mToken);
        mJsonObj.put("user_enctime", mUserEncTime);
        mJsonObj.put("uid", mUid);
        putCommParam();
        putSignkey(mUid, mToken);
        return mJsonObj.toString();
    }
}
