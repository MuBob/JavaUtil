package data;

import org.json.JSONObject;

/**
 * Created by test on 2016/4/19.
 */
public class CSInfo {

    public String mIp;
    public int mPort;

    @Override
    public String toString() {
        return "CSInfo{" +
                "mIp='" + mIp + '\'' +
                ", mPort=" + mPort +
                '}';
    }

    public void parse(JSONObject jsonObject){

        if(jsonObject == null){
            return ;
        }
        mIp = jsonObject.optString("ip");
        mPort = jsonObject.optInt("port");
    }
}
