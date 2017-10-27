package base_http.packet;

import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.Call;

/**
 * Created by test on 2016/6/19.
 */
public class RetCodeHelper {

    private static RetCodeHelper mInstance;
    private GetRetCodeAckPacket mRetCodeAckPacket;
    public synchronized String getRealRetDesc(int retCode) {
        if(mRetCodeAckPacket == null || !isSuccess(mRetCodeAckPacket.mRetCode)){
        }
        if (mRetCodeAckPacket != null && mRetCodeAckPacket.mRetCodeMap != null) {
            return mRetCodeAckPacket.mRetCodeMap.get(retCode);
        }
        return "";
    }

    public static boolean isSuccess(int retCode) {
        if (getRealRetCode(retCode) == 0) {
            return true;
        }
        return false;
    }

    public static int getRealRetCode(int retCode) {
        int real = retCode & 0x0000ffff;
        return real;
    }


    public static RetCodeHelper instance() {
        if (mInstance == null) {
            mInstance = new RetCodeHelper();
        }
        return mInstance;
    }

}
