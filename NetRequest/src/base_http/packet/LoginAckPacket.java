package base_http.packet;


import data.AccountInfo;
import data.CSInfo;
import data.ConfigInfo;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by test on 2016/4/19.
 */
public class LoginAckPacket extends InPacket {

    public AccountInfo mAccountInfo;
    public ConfigInfo mConfigInfo;
    public CSInfo mCSInfo;
    public int mErrorCnt;

    @Override
    protected void parseBody(JSONObject dataObject) throws JSONException {

        mAccountInfo = new AccountInfo();
        mAccountInfo.parse(dataObject.optJSONObject("userinfo"));

        mCSInfo = new CSInfo();
        mCSInfo.parse(dataObject.optJSONObject("csinfo"));

        mConfigInfo = new ConfigInfo();
        mConfigInfo.parse(dataObject.optJSONObject("config"));

        mErrorCnt = dataObject.optInt("errorcnt");
    }
}
