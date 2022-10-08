package top.mikevane.laji.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;


/**
 * 按钮跳转工具类
 * @author MikeV
 */
public class BtnClickJumpListener implements View.OnClickListener{

    /**
     * 跳转的目标活动
     */
    private Class<?> targetActivityClass;

    /**
     * 源活动
     */
    private AppCompatActivity activity;

    /**
     * 传输的数据
     */
    private Map<String,String> message;


    public BtnClickJumpListener(AppCompatActivity activity, Class<?> targetActivityClass) {
        this(activity,targetActivityClass,null);
    }

    /**
     * 构造方法
     * @param activity 当前 activity
     * @param targetActivityClass 目标活动的 class
     * @param message 需要传输的数据
     */
    public BtnClickJumpListener(AppCompatActivity activity, Class<?> targetActivityClass, Map<String,String> message) {
        this.targetActivityClass = targetActivityClass;
        this.activity = activity;
        this.message = message;
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