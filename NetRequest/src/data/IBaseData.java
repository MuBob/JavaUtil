package data;

import android.os.Parcelable;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface IBaseData extends Parcelable {
    void parse(JSONObject jsonObject);
}
