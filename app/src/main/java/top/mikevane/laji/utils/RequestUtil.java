package top.mikevane.laji.utils;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.mikevane.laji.http.config.RequestCallback;
import top.mikevane.laji.pojo.ResponseResult;

/**
 * @author MikeV
 */
public class RequestUtil {

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
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if(map != null && map.size() > 0){
            Set<String> keySet = map.keySet();
            for (String s : keySet) {
                formBodyBuilder.add(s,map.get(s));
            }
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 555代表请求失败，500代表服务器处理失败
                requestCallback.getResult(new ResponseResult(555,"请求失败"));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseResult responseResult = JsonUtil.jsonToResponseRes(response.body().string());
                requestCallback.getResult(responseResult);
            }
        });
    }

}
