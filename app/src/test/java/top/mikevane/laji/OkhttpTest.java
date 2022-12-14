package top.mikevane.laji;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.mikevane.laji.http.config.RequestCallback;
import top.mikevane.laji.pojo.ResponseResult;
import top.mikevane.laji.utils.JsonUtil;
import top.mikevane.laji.utils.RequestUtil;

/**
 * 测试向服务器发送请求
 */
public class OkhttpTest {

    private static final String TAG = "OkhttpTest";

    /**
     * 测试 okhttp是否能正常发送 get 请求
     */
    @Test
    public void testRequest(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://localhost:8080/laji/user/select?id=1").build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试封装类是否能正常发送 post 请求
     */
    @Test
    public void testPost(){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> map = new HashMap<>();
        map.put("id","1");
        RequestUtil.sendPostRequest("http://localhost:8080/laji/user/select", map, new RequestCallback() {
            @Override
            public void getResult(ResponseResult responseResult) {

            }
        });
    }

    @Test
    public void testSendCode() throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put("phone","123");
        RequestUtil.sendPostRequest("http://localhost:8080/laji/user/select", map, new RequestCallback() {
            @Override
            public void getResult(ResponseResult responseResult) {

            }
        });
    }
}
