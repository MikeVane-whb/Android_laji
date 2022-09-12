package top.mikevane.laji;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import top.mikevane.laji.tool.BtnClickListener;

/**
 * 信息显示模块
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 登录事件
        Button loginButton = findViewById(R.id.login_login);
        loginButton.setOnClickListener(new BtnClickListener(this,IndexActivity.class));
    }
}