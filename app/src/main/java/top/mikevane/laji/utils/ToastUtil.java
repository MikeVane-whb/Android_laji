package top.mikevane.laji.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import top.mikevane.laji.R;

/**
 * Toast 组件工具类
 * @Author mikevane
 * @Date 11:17
 * @Version 1.0
 */
public class ToastUtil {

    /**
     * 成功后 Toast 字体颜色
     */
    private static final int SUCCESS_COLOR = Color.BLUE;
    /**
     * 失败后 Toast 字体颜色
     */
    private static final int ERROR_COLOR = Color.RED;
    /**
     * 默认时间
     */
    private static final int DEFAULT_TIME = Toast.LENGTH_SHORT;
    /**
     * 成功后 Toast 的默认消失时间
     */
    private static final int SUCCESS_TIME = Toast.LENGTH_LONG;
    /**
     * 失败后 Toast 的默认消失时间
     */
    private static final int ERROR_TIME = Toast.LENGTH_SHORT;

    /**
     * 成功，显示消息
     * @param activity
     * @param msg
     * @param showTime
     */
    public static void success(Activity activity, String msg, int showTime){
        Toast toast = Toast.makeText(activity, msg, showTime);
        LinearLayout view = (LinearLayout) toast.getView();
        TextView viewChildAt = (TextView) view.getChildAt(0);
        viewChildAt.setTextSize(15);
        viewChildAt.setTextColor(SUCCESS_COLOR);
        toast.show();
    }

    public static void success(Activity activity, String msg){
        success(activity,msg,SUCCESS_TIME);
    }
}
