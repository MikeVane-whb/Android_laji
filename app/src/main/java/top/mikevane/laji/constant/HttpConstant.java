package top.mikevane.laji.constant;

/**
 * @Author mikevane
 * @Date 15:00
 * @Version 1.0
 */
public class HttpConstant {

    /**
     * 主机地址
     */
    public static final String BASE_URL = "http://192.168.56.1:8080/laji";

    /**
     * 发送验证码请求 uri
     */
    public static final String SEND_CODE = "/user/sendCode";

    /**
     * 发送手机请求 uri
     */
    public static final String SELECT_PHONE = "/user/select";

    /**
     * 发送注册请求 uri
     */
    public static final String REGISTER_ACCOUNT = "/user/register";

    /**
     * 发送登录请求 uri
     */
    public static final String LOGIN = "/user/login";

    /**
     * 发送查询用户信息请求 uri
     */
    public static final String SELECT_USERINFO = "/user/selectUserInfo";
}
