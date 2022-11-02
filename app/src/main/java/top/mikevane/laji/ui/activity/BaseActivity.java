package top.mikevane.laji.ui.activity;

import android.os.Bundle;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.mikevane.laji.manager.ActivityManager;

/**
 * 所有活动的父类，与 ActivityManager 联合使用，用于管理 activity
 * @Author mikevane
 * @Date 19:11
 * @Version 1.0
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        Message message = new Message();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
