package top.mikevane.laji;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author mikevane
 * @Date 23:44
 * @Version 1.0
 */
public class JacksonTest {
    @Test
    public void testJsonToMap(){
        String s = "{\"code\":1,\"msg\":null,\"data\":{\"id\":1,\"email\":\"1312706383@qq.com\",\"password\":\"123456\",\"userInfoId\":\"1\"},\"map\":{}}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map map = objectMapper.readValue(s, Map.class);
            Set set = map.keySet();
            for (Object s1 : set) {
                Object o = map.get(s1);
                if (o instanceof Integer){
                    Integer integer = (Integer) o;
                    System.out.println(s1 + ":" + integer.toString());
                }
                if (o instanceof LinkedHashMap){
                    LinkedHashMap linkedHashMap = (LinkedHashMap) o;
                    Set keySet = linkedHashMap.keySet();
                    for (Object o1 : keySet) {
                        System.out.println(o1 + " : " + linkedHashMap.get(o1));
                    }
                    System.out.println(s1 + ":" + linkedHashMap.toString());
                }
                if (o instanceof String){
                    System.out.println(s1 + ":" + (String) o);
                }

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
