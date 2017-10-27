package http.content;

import base_http.packet.BaseRequestContent;
/**
 * Created by Administrator on 2017/8/1.
 */

public class RegisterContent  extends BaseRequestContent {
    public RegisterContent() {
        super("client/reg");
    }

    public RegisterContent(String name, String pwd, String number) {
        this();
        setName(name);
        if (pwd!=null)
            setPwd(pwd);
        if (number!=null)
            setAuthCode(number);
    }
    public void setName(String name){
        getContent().put("phone", name);
    }
    public void setAuthCode(String number){
        getContent().put("code", number);
    }
    public void setPwd(String pwd){
        getContent().put("password", pwd);
    }

}
