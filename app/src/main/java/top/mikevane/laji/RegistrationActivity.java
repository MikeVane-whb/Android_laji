package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import top.mikevane.laji.utils.BtnClickJumpListener;

/**
 * @author MikeV
 */
public class RegistrationActivity extends AppCompatActivity {

    /**
     * 手机号
     */
    private EditText registerPhone;
    /**
     * 验证码
     */
    private EditText registerCode;
    /**
     * 发送验证码
     */
    private Button registerSendCode;
    /**
     * 密码
     */
    private EditText registerPassword;
    /**
     * 确认密码
     */
    private EditText registerConfirmPassword;
    /**
     * 注册
     */
    private Button registerRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // 注册按钮事件
        Button registerButton = findViewById(R.id.register_register);
        registerButton.setOnClickListener(new BtnClickJumpListener(this,LoginActivity.class));

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

    /**
     * 绑定 id
     */
    private void bindId(){
        registerPhone = findViewById(R.id.register_phone);
        registerCode = findViewById(R.id.register_code);
        registerSendCode = findViewById(R.id.register_send_code);
        registerPassword = findViewById(R.id.register_password);
        registerConfirmPassword = findViewById(R.id.register_confirm_password);
        registerRegister = findViewById(R.id.register_register);
    }
}