package top.mikevane.laji.http.api;

import top.mikevane.laji.http.config.RequestApi;

/**
 * 发送验证码信息
 * @Author mikevane
 * @Date 20:53
 * @Version 1.0
 */
public class SendCodeApi implements RequestApi {
    @Override
    public String getApi() {
        return "/user/sendCode";
    }
}
