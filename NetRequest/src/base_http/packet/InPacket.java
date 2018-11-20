package base_http.packet;

import org.json.JSONException;
import org.json.JSONObject;
import util.LogUtil;
import util.TextUtils;

/**
 * Created by test on 2016/4/19.
 */
public abstract class InPacket{

    public static final int SUCCESS = 0;

    protected int mRetCode;
    protected String mMsg;

    public boolean isSuccess(){
        if(RetCodeHelper.instance().isSuccess(mRetCode)){
            return true;
        }
        return false;
    }

    public int getRetCode(){
        return mRetCode;
    }

    public String getRetDesc(){
        if(TextUtils.isEmpty(mMsg)){
            mMsg = RetCodeHelper.instance().getRealRetDesc(mRetCode);
        }
        return mMsg;
    }


    public void parse(String responseBody) throws JSONException {
        JSONObject json = new JSONObject(responseBody);
        mRetCode = json.optInt("rcode");
        mMsg = json.optString("msg");
        if(TextUtils.isEmpty(mMsg)){
            mMsg = json.optString("other");
        }

        LogUtil.LogShow("test", "InPacket event mRetCode = " + mRetCode + ", mMsg = " + mMsg);

        JSONObject dataObj = json.optJSONObject("data");
        if(dataObj != null){

            LogUtil.LogShow("test", "InPacket event dataObj = " + dataObj);

            parseBody(dataObj);
        }
    }

    protected abstract void parseBody(JSONObject dataObject) throws JSONException;
}
