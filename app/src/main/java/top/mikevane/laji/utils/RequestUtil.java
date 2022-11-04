package top.mikevane.laji.utils;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.mikevane.laji.constant.CodeConstant;
import top.mikevane.laji.constant.HttpConstant;
import top.mikevane.laji.http.config.RequestCallback;
import top.mikevane.laji.manager.ThreadPoolManager;
import top.mikevane.laji.pojo.ResponseResult;

/**
 * @author MikeV
 */
public class RequestUtil {

    private static final MediaType HEADER_CONTENT_TYPE = MediaType.parse("application/json;charset=utf-8");

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .build();

    /**
     * 发送 post 请求
     * @param url 请求地址
     * @param map 请求参数
     * @param <T> 泛型，上限为 Map
     * @return 返回 Call 对象，由其他模块选择后续操作
     */
    public static <T extends Map<String,String>> void sendPostRequest(String url, T map, RequestCallback requestCallback){
        // 将需要传入的数据放到 jsonObject 中
        JSONObject jsonObject = new JSONObject();
        if(map != null && map.size() > 0){
            Set<String> keySet = map.keySet();
            for (String s : keySet) {
                try {
                    jsonObject.put(s,map.get(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        // 创建请求体
        RequestBody requestBody = RequestBody.create(HEADER_CONTENT_TYPE, String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        // 异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ResponseResult responseResult = new ResponseResult();
                responseResult.setCode(CodeConstant.CLIENT_ERROR);
                responseResult.setMsg("请求失败");
                requestCallback.getResult(responseResult);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 获取状态码
                int code = response.code();
                // 封装类，用于接收返回数据
                ResponseResult responseResult;
                // 请求失败，服务器返回404
                if(code == CodeConstant.CLIENT_ERROR){
                    responseResult = new ResponseResult();
                    responseResult.setCode(CodeConstant.CLIENT_ERROR);
                    responseResult.setMsg("请求失败");
                }
                else{
                    // 请求成功，将响应体 json 转换为封装类
                    responseResult = JsonUtil.jsonToResponseRes(response.body().string());
                }
                // 赋值给回调函数
                requestCallback.getResult(responseResult);
            }
        });
    }

}
