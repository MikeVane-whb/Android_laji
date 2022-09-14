package top.mikevane.laji.utils;

import java.util.UUID;

/**
 * DengYu
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
