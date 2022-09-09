package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    /**
     * 按键跳转类
     */
    class BtnClickListener implements View.OnClickListener{

        /**
         * 跳转的活动
         */
        private Class<?> targetActivityClass;


        public BtnClickListener(Class<?> activityClass) {
            this.targetActivityClass = activityClass;
        }

        /**
         * 指定跳转活动
         * @param view
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomePageActivity.this,targetActivityClass);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        // 登录按钮事件
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new BtnClickListener(IndexActivity.class));

        // 注册按钮事件
        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new BtnClickListener(RegistrationActivity.class));
    }

}