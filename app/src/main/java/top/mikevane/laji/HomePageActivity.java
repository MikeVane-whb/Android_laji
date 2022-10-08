package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import top.mikevane.laji.utils.BtnClickJumpListener;

/**
 * 开始页面
 */
public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        // 登录按钮事件
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new BtnClickJumpListener(this,LoginActivity.class));

        // 注册按钮事件
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new BtnClickJumpListener(this,RegistrationActivity.class));
    }

}