package base_http.packet;

import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/21.
 */

public interface BaseCallbackBean extends Serializable {
    void parse(String data) throws JSONException;
}
