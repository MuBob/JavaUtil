package base_http.packet;

import org.json.JSONException;

/**
 * Created by test on 2016/4/19.
 */
public class EditUserInfoPacket extends OutPacket {

    public int mUid;
    public String mToken;
    public String mHead;
    public int mSex;
    public String mNickName;
    public String mLevelName;

    @Override
    public String packBody() throws JSONException {
        mJsonObj.put("uid", mUid);
        mJsonObj.put("token", mToken);
        mJsonObj.put("head", mHead);
        mJsonObj.put("sex", mSex);
        mJsonObj.put("nick_name", mNickName);
        mJsonObj.put("level_name", mLevelName);
        putCommParam();
        putSignkey(mUid, mToken);
        return mJsonObj.toString();
    }
}
