package http.content;


import base_http.packet.BaseRequestContent;

/**
 * Created by Administrator on 2017/8/21.
 */

public class EditUserInfoContent extends BaseRequestContent {
    public EditUserInfoContent() {
        super("client/edituser");
    }

    public void setParames(int uid, String token, String head, String sex, String nickName, String levelName){
        setUid(uid);
        setToken(token);
        setHead(head);
        setSex(sex);
        setNickName(nickName);
        setLevelName(levelName);
        putParamSignkey(uid, token);

    }
    public void setUid(int uid){
        getContent().put("uid", String.valueOf(uid));
    }
    public void setToken(String token){
        getContent().put("token", token);
    }
    public void setHead(String head){
        getContent().put("head", head);
    }
    public void setSex(String sex){
        getContent().put("sex", sex);
    }
    public void setNickName(String nickName){
        getContent().put("nick_name", nickName);
    }
    public void setLevelName(String levelName){
        getContent().put("level_name", levelName);
    }

    public String getHead(){
        return (String) getContent().get("head");
    }
    public String getSex(){
        return  (String) getContent().get("sex");
    }
    public String getNickName(){
        return  (String) getContent().get("nick_name");
    }
    public String getLevelName(){
        return  (String) getContent().get("level_name");
    }
    
}
