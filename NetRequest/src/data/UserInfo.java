package data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONObject;
import util.HanziToPinyin;

/**
 * Created by test on 2016/4/5.
 */
public class UserInfo implements Comparable<UserInfo>, Parcelable {
    public int mUid;
    public int mSex;
    public String mNickName;
    public String mLevel;
    public String mRemarkName;
    public String mHead;
    public int mUpdateTime;
    public boolean mIsSelected;
    public boolean mIsFixedSelected;

    public int mHasAtt = 3; //

    public UserInfo(String nickName, String head) {
        mNickName = nickName;
        mHead = head;
    }

    public UserInfo() {

    }


    public String getNickName(){
        return mNickName;
    }

    public String getHead(){
        return mHead;
    }

    public UserInfo(int uid, int sex, String nickName, String head){
        mUid = uid;
        mSex = sex;
        mNickName = nickName;
        mHead = head;
    }

    @Override
    public boolean equals(Object o) {
        UserInfo userInfo = (UserInfo) o;
        if(userInfo.mUid == mUid){
            return true;
        }
        return false;
    }

    public UserInfo(Parcel in) {
        this.mUid = in.readInt();
        this.mSex = in.readInt();
        this.mNickName = in.readString();
        this.mRemarkName = in.readString();
        this.mHead = in.readString();
        this.mLevel = in.readString();
        this.mUpdateTime = in.readInt();
        this.mIsFixedSelected = in.readInt() == 1 ? true : false;
        this.mIsSelected = in.readInt() == 1 ? true : false;
    }

    public String getDisplayName() {
        if (!TextUtils.isEmpty(mRemarkName)) {
            return mRemarkName;
        }
        return mNickName;
    }

    public void parse(JSONObject jsonObject) {
        mUid = jsonObject.optInt("uid");
        mSex = jsonObject.optInt("sex");
        mNickName = jsonObject.optString("nick_name");
        mRemarkName = jsonObject.optString("remark_name");
        mLevel = jsonObject.optString("level");
        if(TextUtils.isEmpty(mLevel)){
            mLevel = jsonObject.optString("level_name");
        }
        mHead = jsonObject.optString("head");
        mUpdateTime = jsonObject.optInt("utime");
    }

    @Override
    public int compareTo(UserInfo another) {
        String name1 = mNickName;
        String name2 = another.mNickName;
        if (!TextUtils.isEmpty(mRemarkName)) {
            name1 = mRemarkName;
        }
        if (!TextUtils.isEmpty(another.mRemarkName)) {
            name2 = another.mRemarkName;
        }
        return HanziToPinyin.getFirstPinYinChar(name1)
                .compareTo(HanziToPinyin.getFirstPinYinChar(name2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mUid);
        dest.writeInt(this.mSex);
        dest.writeString(this.mNickName);
        dest.writeString(this.mRemarkName);
        dest.writeString(this.mHead);
        dest.writeString(this.mLevel);
        dest.writeInt(this.mUpdateTime);
        dest.writeInt(this.mIsFixedSelected ? 1 : 0);
        dest.writeInt(this.mIsSelected ? 1 : 0);
    }

    public static final Creator CREATOR = new Creator() {
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
