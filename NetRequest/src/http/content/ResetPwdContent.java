package http.content;
import base_http.packet.BaseRequestContent;
/**
 * Created by Administrator on 2017/8/8.
 */

public class ResetPwdContent extends BaseRequestContent {
    public ResetPwdContent() {
        super("client/setpwd");
    }

    public void setPhone(String phone){
        getContent().put("phone", phone);
    }

    public void setPwd(String pwd){
        getContent().put("pwd", pwd);
    }

    public void setAuth(String auth){
        getContent().put("auth", auth);
    }
}
