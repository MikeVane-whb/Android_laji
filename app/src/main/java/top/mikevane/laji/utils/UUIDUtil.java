package top.mikevane.laji.utils;

import java.util.UUID;

/**
 * DengYu
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
