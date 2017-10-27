package data;

import android.os.Parcel;
import android.text.TextUtils;

import org.json.JSONObject;
import util.LogUtil;

/**
 * 账户相关
 * Created by test on 2016/4/18.
 */
public class AccountInfo implements IUserBean {

    public int mUid;
    public String mPhone;
    public String mHead; //
    public String mToken; //
    public String mNickName;
    public int mSex;        //0保密，1男，2女
    public int mStatus; //用户状态： 0.提交中，未审核 1新审核通过但要提交昵称等信息  2.帐号正常
    public String mEncrypPwd; // 加密后的密码
    public String mClassic;

    private String mEncTime;
    private String mEncKey;

    public synchronized void setmPhoneAndEncryptPwd(String phone, String encryptPwd){
        mPhone=phone;
        mEncrypPwd=encryptPwd;
    }
    public void setAccountInfo(AccountInfo info) {
        mUid = info.mUid;
        mPhone = info.mPhone;
        mHead = info.mHead;
        mToken = info.mToken;
        mNickName = info.mNickName;
        mSex = info.mSex;
        mStatus = info.mStatus;
        mClassic = info.mClassic;
    }

    public AccountInfo() {
    }

    public boolean isLogin() {
        if (mUid == 0) {
            return false;
        }
        return true;
    }

    public int getUserId() {
        return mUid;
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.mUid = mUid;
        userInfo.mHead = mHead;
        userInfo.mSex = mSex;
        userInfo.mNickName = mNickName;
        return userInfo;
    }

    public static String readToken() {
        return null;
    }

    public static int readUserId() {
        return 0;
    }

    public String getToken() {
        String token = readToken();   //每次都更新token，避免IM进程和UI进程的不同步
        if (!TextUtils.isEmpty(token)) {
            mToken = token;
        }
        return mToken;
    }

    public synchronized void setNewToken(String token) {
        LogUtil.LogShow("AccountInfo", "setNewToken = " + token);
        mToken = token;
    }

    public synchronized void setEncKeyAndTime(String encKey, String encTime) {
        LogUtil.LogShow("AccountInfo", "setEncKeyAndTime encKey = " + encKey
                + ", encTime = " + encTime);
        mEncTime = encTime;
        mEncKey = encKey;
    }

    public synchronized String getEncTime() {
        return mEncTime;
    }

    public synchronized String getEncKey() {
        return mEncKey;
    }

    public int getSex() {
        return mSex;
    }


    public void parse(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        mUid = jsonObject.optInt("uid");
        mPhone = jsonObject.optString("phone");
        mNickName = jsonObject.optString("nickname");
        if (TextUtils.isEmpty(mNickName)) {
            mNickName = jsonObject.optString("nick_name");
        }
        mHead = jsonObject.optString("head");
        mSex = jsonObject.optInt("sex");
        mClassic = jsonObject.optString("level_name");
        mToken = jsonObject.optString("token");
        mStatus = jsonObject.optInt("ustatus");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mUid);
        dest.writeString(this.mPhone);
        dest.writeString(this.mHead);
        dest.writeString(this.mToken);
        dest.writeString(this.mNickName);
        dest.writeInt(this.mSex);
        dest.writeInt(this.mStatus);
        dest.writeString(this.mEncrypPwd);
        dest.writeString(this.mClassic);
    }

    protected AccountInfo(Parcel in) {
        this.mUid = in.readInt();
        this.mPhone = in.readString();
        this.mHead = in.readString();
        this.mToken = in.readString();
        this.mNickName = in.readString();
        this.mSex = in.readInt();
        this.mStatus = in.readInt();
        this.mEncrypPwd = in.readString();
        this.mClassic = in.readString();
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "mUid=" + mUid +
                ", mPhone='" + mPhone + '\'' +
                ", mHead='" + mHead + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mNickName='" + mNickName + '\'' +
                ", mSex=" + mSex +
                ", mStatus=" + mStatus +
                ", mEncrypPwd='" + mEncrypPwd + '\'' +
                ", mClassic='" + mClassic + '\'' +
                ", mEncTime='" + mEncTime + '\'' +
                ", mEncKey='" + mEncKey + '\'' +
                '}';
    }

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel source) {
            return new AccountInfo(source);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };
}
