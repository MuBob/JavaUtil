package context;

import base_http.request.HttpManager;

/**
 * Created by Administrator on 2017/10/25.
 */
public class Environment {
    public static void main(String[] args){
        HttpManager.getInstance().getRequest(Config.loginContentRequestAction);
    }
}
