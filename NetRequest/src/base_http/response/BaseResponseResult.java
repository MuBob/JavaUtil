package base_http.response;


import base_http.packet.BaseRequestContent;

/**
 * 返回结果抽象类，工厂方法模式
 * Created by Administrator on 2017/7/28.
 */

public class BaseResponseResult {
    private static int SUCCESS_CODE;
    protected String message;
    //请求对象
    protected BaseRequestContent requestContent;
    //状态码
    protected int statusCode=-1;
    //JsonObject类型转成的数据
    protected String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseRequestContent getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(BaseRequestContent requestContent) {
        this.requestContent = requestContent;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return getStatusCode()==SUCCESS_CODE;
    }

    @Override
    public String toString() {
        return "BaseResponseResult{" +
                " isSuccess='" + isSuccess() + '\'' +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", requestContent=" + requestContent +
                ", data='" + data + '\'' +
                '}';
    }
}
