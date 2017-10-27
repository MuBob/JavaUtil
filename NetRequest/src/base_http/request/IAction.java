package base_http.request;

/**
 * Created by Administrator on 2017/8/11.
 */

public interface IAction<Content, Callback> {
    Callback initCallbackFactory();
    Content getRequestContent();
    Callback getCallback();
}
