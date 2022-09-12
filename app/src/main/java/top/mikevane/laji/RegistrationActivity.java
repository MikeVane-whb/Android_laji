package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import top.mikevane.laji.tool.BtnClickListener;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // 注册按钮事件
        Button registerButton = findViewById(R.id.register_register);
        registerButton.setOnClickListener(new BtnClickListener(this,LoginActivity.class));

        // 发送验证码事件
        Button codeButton = findViewById(R.id.register_send_code);
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegistrationActivity.this, "已发送验证码",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}