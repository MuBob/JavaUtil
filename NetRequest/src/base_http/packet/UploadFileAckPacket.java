package base_http.packet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by test on 2016/4/19.
 */
public class UploadFileAckPacket extends InPacket {

    public String mUrl;  //原始文件路径
    public String mThumbnail; // filetype = 1返回
    @Override
    protected void parseBody(JSONObject dataObject) throws JSONException {
        mUrl = dataObject.optString("url");
        mThumbnail = dataObject.optString("thumb");
    }
}
