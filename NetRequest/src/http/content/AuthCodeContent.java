package http.content;


import base_http.packet.BaseRequestContent;

/**
 * Created by Administrator on 2017/8/1.
 */

public class AuthCodeContent  extends BaseRequestContent {
    public AuthCodeContent() {
        super("client/reg");
    }
    public void setPhone(String phone){
        getContent().put("phone",phone);
    }
}
