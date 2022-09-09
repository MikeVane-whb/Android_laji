package top.mikevane.laji.tool;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


/**
 * 按钮跳转工具类
 */
public class BtnClickListener implements View.OnClickListener{

    /**
     * 跳转的活动
     */
    private Class<?> targetActivityClass;
    private AppCompatActivity activity;

    /**
     * 构造方法
     * @param activity 当前 activity
     * @param targetActivityClass 目标活动的 class
     */
    public BtnClickListener(AppCompatActivity activity, Class<?> targetActivityClass) {
        this.targetActivityClass = targetActivityClass;
        this.activity = activity;
    }

    /**
     * 指定跳转活动
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity,targetActivityClass);
        activity.startActivity(intent);
    }
}