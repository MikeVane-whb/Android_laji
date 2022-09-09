package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import top.mikevane.laji.tool.BtnClickListener;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        // 登录按钮事件
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new BtnClickListener(this,IndexActivity.class));

        // 注册按钮事件
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new BtnClickListener(this,RegistrationActivity.class));
    }

}