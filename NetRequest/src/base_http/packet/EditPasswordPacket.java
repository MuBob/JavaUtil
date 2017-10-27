package base_http.packet;

import org.json.JSONException;

/**
 * Created by test on 2016/4/19.
 */
public class EditPasswordPacket extends OutPacket {

    public int mUid;
    public String mToken;
    public String mOldPwd;
    public String mNewPwd;

    @Override
    public String packBody() throws JSONException {
        mJsonObj.put("uid", mUid);
        mJsonObj.put("token", mToken);
        mJsonObj.put("password", mOldPwd);
        mJsonObj.put("new_password", mNewPwd);
        putCommParam();
        putSignkey(mUid, mToken);
        return mJsonObj.toString();
    }
}
