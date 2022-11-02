package top.mikevane.laji.utils;

/**
 * String 工具类
 * @Author mikevane
 * @Date 14:51
 * @Version 1.0
 */
public class StringUtil {
    /**
     * 字符串是否可用
     * @param s
     * @return 如果字符串为空、长度不合法以及字符串为空字符串返回true
     *          否则，返回true
     */
    public static boolean isNull(String s){
        if(s == null || s.length() <= 0 || "".equals(s)){
            return true;
        }
        return false;
    }
}
