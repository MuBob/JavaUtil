package data;

import org.json.JSONObject;

/**
 * Created by test on 2016/4/19.
 */
public class ConfigInfo {

    public int mUserUpdate;

    @Override
    public String toString() {
        return "ConfigInfo{" +
                "mUserUpdate=" + mUserUpdate +
                '}';
    }

    public void parse(JSONObject jsonObject){

        if(jsonObject == null){
            return ;
        }
        mUserUpdate = jsonObject.optInt("user_update");
    }
}
