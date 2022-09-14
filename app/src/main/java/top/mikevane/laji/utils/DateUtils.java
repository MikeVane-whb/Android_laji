package top.mikevane.laji.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DengYu
 */
public class DateUtils {
    /**
     * 对指定的date对象进行格式化
     * @param date
     * @return
     */
    public static String formateDateTime(Date date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String dataStr = dateFormat.format(date);
        return dataStr;
    }
    /**
     * 对指定的date对象进行格式化yyyy-MM-DD
     * @param date
     * @return
     */
    public static String formateDate(Date date){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        String dataStr=sdf.format(date);
        return dataStr;
    }


}
