package top.mikevane.laji.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络访问返回的对象
 * @Author mikevane
 * @Date 14:05
 * @Version 1.0
 */
public class ResponseResult {
    /**
     * 编码：200成功，500和其它数字为失败
     */
    private Integer code;

    /**
     * 错误或者成功信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 动态数据
     */
    private Map map = new HashMap();

    public ResponseResult(){}

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public Map getMap() {
        return map;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", sessionId='" + sessionId + '\'' +
                ", map=" + map +
                '}';
    }
}

