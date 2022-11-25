package top.mikevane.laji.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import top.mikevane.laji.R;
import top.mikevane.laji.ui.listener.BtnClickJumpListener;
import top.mikevane.laji.utils.ToastUtil;

/**
 * 开始页面
 * @author MikeV
 */
public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ToastUtil.successMsg(this,"这是主页面");

        // 登录按钮事件
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new BtnClickJumpListener(this,LoginActivity.class));

        // 注册按钮事件
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new BtnClickJumpListener(this,RegistrationActivity.class));
    }

}