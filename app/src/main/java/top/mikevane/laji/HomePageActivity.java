package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import top.mikevane.laji.tool.BtnClickListener;
import top.mikevane.laji.tool.JDBCUtil;

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
        loginButton.setOnClickListener(new BtnClickListener(this,LoginActivity.class));

        // 注册按钮事件
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new BtnClickListener(this,RegistrationActivity.class));
    }

}