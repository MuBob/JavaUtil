package base_http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.LogUtil;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by test on 2016/6/19.
 */
public class GetRetCodeAckPacket implements Serializable {

    public int mRetCode;
    public HashMap<Integer, String> mRetCodeMap;

    public void parse(String responseBody) throws JSONException {
        JSONObject json = new JSONObject(responseBody);
        mRetCode = json.optInt("rcode");

        LogUtil.LogShow("GetRetCodeAckPacket", "parse mRetCode = " + mRetCode);
        LogUtil.LogShow("GetRetCodeAckPacket", "parse responseBody = " + responseBody);
        JSONArray array = json.optJSONArray("data");
        if(array != null){
            mRetCodeMap = new HashMap<Integer, String>();
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                int rcode = obj.optInt("rcode");
                String desc = obj.optString("content");
                mRetCodeMap.put(rcode, desc);
            }
        }
    }
}
