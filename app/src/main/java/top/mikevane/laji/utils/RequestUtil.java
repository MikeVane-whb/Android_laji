package top.mikevane.laji.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author MikeV
 */
public class RequestUtil {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 发送 post 请求
     * @param url 请求地址
     * @param map 请求参数
     * @param <T> 泛型，上限为 Map
     * @return 返回 Call 对象，由其他模块选择后续操作
     */
    public static <T extends Map<String,String>> Call sendPostRequest(String url, T map){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            formBodyBuilder.add(s,map.get(s));
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return okHttpClient.newCall(request);
    }

    /**
     * 发送 get 请求
     * @param url 请求地址
     * @param map 请求参数
     * @param <T> 泛型，上限为 Map
     * @return 返回 Map 对象，里面是返回数据的所有键值对
     */
//    public static <T extends Map<String,String>> Map sendGetRequest(String url, T map){
//
//    }
}
