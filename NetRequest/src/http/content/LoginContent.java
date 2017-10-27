package http.content;


import base_http.packet.BaseRequestContent;
import util.EncryptUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/28.
 */

public class LoginContent extends BaseRequestContent {

    public LoginContent() {
        super("client/login");
    }

    public LoginContent(String name, String pwd) {
        this();
        setName(name);
        setPwd(pwd);
    }

    private String sipData;

    public void setAuth(String auth){
        getContent().put("code",auth);
    }
    public void setName(String name){
        getContent().put("phone", name);
    }
    public void setPwd(String pwd){
        getContent().put("password", pwd);
    }

    public String getSipData() {
        return sipData;
    }

    public void setSipData(String sipData) {
        this.sipData = sipData;
    }
}
