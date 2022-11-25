package top.mikevane.laji.constant;

import android.graphics.Color;
import android.widget.Toast;

/**
 * Toast 的常量管理
 * @Author mikevane
 * @Date 23:17
 * @Version 1.0
 */
public class ToastConstant {
    /**
     * 失败 flag
     */
    public static final int ERROR_FLAG = -1;
    /**
     * 默认 flag
     */
    public static final int DEFAULT_FLAG = 0;
    /**
     * 成功 flag
     */
    public static final int SUCCESS_FLAG = 1;
    /**
     * 默认 Toast 字体颜色
     */
    public static final int DEFAULT_COLOR = Color.parseColor("#cd9b74");
    /**
     * 成功后 Toast 字体颜色
     */
    public static final int SUCCESS_COLOR = Color.parseColor("#3f735d");
    /**
     * 失败后 Toast 字体颜色
     */
    public static final int ERROR_COLOR = Color.RED;
    /**
     * 默认时间
     */
    public static final int DEFAULT_TIME = Toast.LENGTH_SHORT;
    /**
     * 成功后 Toast 的默认消失时间
     */
    public static final int SUCCESS_TIME = Toast.LENGTH_LONG;
    /**
     * 失败后 Toast 的默认消失时间
     */
    public static final int ERROR_TIME = Toast.LENGTH_SHORT;
    /**
     * 默认字体大小
     */
    public static final int DEFAULT_TEXT_SIZE = 20;
}
