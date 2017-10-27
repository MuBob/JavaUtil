package context;

import base_http.callback.BaseCallback;
import base_http.callback.ICallback;
import base_http.packet.BaseRequestContent;
import base_http.request.BaseRequestAction;
import base_http.response.BaseResponseResult;
import http.RequestAction;
import http.RequestCallback;
import http.content.LoginContent;
import http.content.RegisterContent;
import org.json.JSONException;
import util.EncryptUtil;

/**
 * Created by Administrator on 2017/10/25.
 */
public class Config {
    public static RequestAction<RegisterContent> registContentRequestAction=new RequestAction<RegisterContent>(new RegisterContent("","",""), new RequestCallback());
    public static RequestAction<RegisterContent> authCodeContentRequestAction=new RequestAction<RegisterContent>(new RegisterContent("",null,null), new RequestCallback());
    public static RequestAction<LoginContent> loginContentRequestAction=new RequestAction<LoginContent>(new LoginContent("13552785471", EncryptUtil.getEncrypPassword("123456")), new RequestCallback());

}
