package top.mikevane.laji.http.config;

import top.mikevane.laji.pojo.ResponseResult;

/**
 * 网络访问类的回调接口
 * @Author mikevane
 * @Date 15:08
 * @Version 1.0
 */
public interface RequestCallback {
    /**
     * 获取相应后的数据
     * @param responseResult
     */
    public void getResult(ResponseResult responseResult);
}
