package top.mikevane.laji.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import top.mikevane.laji.R;
import top.mikevane.laji.constant.ToastConstant;

/**
 * Toast 组件工具类
 * @Author mikevane
 * @Date 11:17
 * @Version 1.0
 */
public class ToastUtil {

    /**
     * 通过传入 flag 来设置 Toast 格式
     * @param toast 传入的 Toast 对象
     * @param flag -1为失败 0为默认 1为成功
     */
    private static void showToast(Toast toast, int flag){
        // 获取 toast 中的 View 对象，用于设置字体等元素
        ViewGroup view = (ViewGroup) toast.getView();
        // 获取 view 中的 TextView 对象，用于对字体进行操作
        TextView viewChildAt = (TextView) view.getChildAt(0);
        // 设置字体大小
        viewChildAt.setTextSize(ToastConstant.DEFAULT_TEXT_SIZE);
        switch (flag){
            case -1:
                // 设置 toast 位置
                toast.setGravity(Gravity.CENTER,0,0);
                // 设置字体颜色
                viewChildAt.setTextColor(ToastConstant.ERROR_COLOR);
                break;
            case 0:
                // 设置字体颜色
                viewChildAt.setTextColor(ToastConstant.DEFAULT_COLOR);
                break;
            case 1:
                // 设置字体颜色
                viewChildAt.setTextColor(ToastConstant.SUCCESS_COLOR);
                break;
        }
        // 设置背景样式
        view.setBackgroundResource(R.drawable.toast_background);
        // 显示 toast
        toast.show();
    }

    /**
     * 显示信息
     * @param activity
     * @param msg
     * @param showTime
     */
    public static void showMsg(Activity activity, String msg, int showTime){
        // 获取 toast 对象
        Toast toast = Toast.makeText(activity, msg, showTime);
        showToast(toast,ToastConstant.DEFAULT_FLAG);
    }

    public static void showMsg(Activity activity, String msg){
        showMsg(activity,msg,ToastConstant.DEFAULT_TIME);
    }

    /**
     * 成功，显示消息
     * @param activity 当前 activity
     * @param msg 需要显示的消息
     * @param showTime toast 提示的时间
     */
    public static void successMsg(Activity activity, String msg, int showTime){
        // 获取 toast 对象
        Toast toast = Toast.makeText(activity, msg, showTime);
        showToast(toast,ToastConstant.SUCCESS_FLAG);
    }

    public static void successMsg(Activity activity, String msg){
        successMsg(activity,msg,ToastConstant.SUCCESS_TIME);
    }

    /**
     * 失败，显示消息
     * @param activity 当前 activity
     * @param msg 需要显示的消息
     * @param showTime toast 提示的时间
     */
    public static void errorMsg(Activity activity, String msg, int showTime){
        // 获取 toast 对象
        Toast toast = Toast.makeText(activity, msg, showTime);
        showToast(toast,ToastConstant.ERROR_FLAG);
    }

    public static void errorMsg(Activity activity, String msg){
        errorMsg(activity,msg,ToastConstant.ERROR_TIME);
    }
}
