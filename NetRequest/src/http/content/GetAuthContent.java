package http.content;


import base_http.packet.BaseRequestContent;

/**
 * Created by Administrator on 2017/8/8.
 */

public class GetAuthContent extends BaseRequestContent {
    public GetAuthContent() {
        super("client/setpwd");
    }

    public void setPhone(String phone){
        getContent().put("phone", phone);
    }
}
