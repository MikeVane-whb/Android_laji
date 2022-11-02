package top.mikevane.laji.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import top.mikevane.laji.pojo.ResponseResult;

/**
 * JSON处理类
 * @Author mikevane
 * @Date 12:34
 * @Version 1.0
 */
public class JsonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * json转为map
     * @param data
     * @return
     * @throws JsonProcessingException
     */
    public static Map<String,Object> jsonToMap(String data) throws JsonProcessingException {
        return objectMapper.readValue(data, Map.class);
    }

    /**
     * json转为 ResponseResult
     * @param data
     * @return
     * @throws JsonProcessingException
     */
    public static ResponseResult jsonToResponseRes(String data) throws JsonProcessingException{
        return objectMapper.readValue(data,ResponseResult.class);
    }
}
