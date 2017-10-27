package base_http.packet;

import android.text.TextUtils;

import org.json.JSONException;

/**
 * Created by test on 2016/4/19.
 */
public class RegisterPacket extends OutPacket {

    public String mPhone; //手机号，第一步和第二步时传递
    public String mCode; // 短信验证码，第二步时带上来
    public String mPwd;  //本地安全密码，规则 md5(原文+公钥)，第二步时带上来

    @Override
    public String packBody() throws JSONException {
        mJsonObj.put("phone", mPhone);
        if(!TextUtils.isEmpty(mCode)){
            mJsonObj.put("code", mCode);
        }
        if(!TextUtils.isEmpty(mPwd)){
            mJsonObj.put("password", mPwd);
        }
        putCommParam();
        return mJsonObj.toString();
    }
}
