package base_http.packet;

import org.json.JSONException;
import org.json.JSONObject;
import util.EncryptUtil;
import util.LogUtil;
import util.Util;

public abstract class OutPacket {

    public OutPacket() {
        mJsonObj = new JSONObject();
    }

    protected JSONObject mJsonObj;

    public String pack() throws JSONException {
        String body = packBody();
        LogUtil.LogShow("test", "OutPacket body = " + body);
        return body;
    }

    public abstract String packBody() throws JSONException;

    public void putCommParam() throws JSONException {
        mJsonObj.put("comm", Util.getSimpleParams());
    }

    public void putSignkey(int uid, String token) throws JSONException {
        long time = System.currentTimeMillis() / 1000;
        mJsonObj.put("time", time);
        mJsonObj.put("signkey", EncryptUtil.getSignKey(uid, time, token));
    }

}
