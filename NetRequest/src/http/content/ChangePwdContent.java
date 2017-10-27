package http.content;


import base_http.packet.BaseRequestContent;

/**
 * Created by Administrator on 2017/8/8.
 */

public class ChangePwdContent extends BaseRequestContent {
    public ChangePwdContent() {
        super("client/editpwd");
    }

    public void setToken(String token){
        getContent().put("token",token);
    }
    public void setUserId(int userId){
        getContent().put("uId",String.valueOf(userId));
    }

    public void setOldPwd(String pwd){
        getContent().put("oldPwd", pwd);
    }

    public void setNewPwd(String pwd){
        getContent().put("newPwd",pwd);
    }

}
