package top.mikevane.laji.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
import top.mikevane.laji.R;
import top.mikevane.laji.constant.CodeConstant;
import top.mikevane.laji.constant.HttpConstant;
import top.mikevane.laji.http.config.RequestCallback;
import top.mikevane.laji.pojo.ResponseResult;
import top.mikevane.laji.ui.listener.BtnClickJumpListener;
import top.mikevane.laji.utils.RequestUtil;

/**
 * @author MikeV
 */
public class RegistrationActivity extends BaseActivity {

    /**
     * 上下文，用于传输数据
     */
    @SuppressLint("StaticFieldLeak")
    public static Context context ;

//    private static final String URL = ""

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
    /**
     * 自定义监听器
     */
    private RegistrationActivityClicker clicker= new RegistrationActivityClicker();

    private class RegistrationActivityClicker implements View.OnClickListener{

        private boolean phoneIsExist = true;

        @SneakyThrows
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                // 如果点击了发送验证码按钮
                case R.id.register_send_code:
                    Map<String,String> map = new HashMap<>();
                    // 获取输入的手机号
                    map.put("phone",registerPhone.getText().toString());
                    // 发送请求，确定手机是否已注册
                    String selectPhoneUrl = HttpConstant.BASE_URL + HttpConstant.SELECT_PHONE;
                    RequestUtil.sendPostRequest(selectPhoneUrl, map, new RequestCallback() {
                        @Override
                        public void getResult(ResponseResult responseResult) {
                            Integer code = responseResult.getCode();
                            if(CodeConstant.CLIENT_ERROR.equals(code)){
                               Toast.makeText(RegistrationActivity.this, "请求失败",
                                       Toast.LENGTH_SHORT).show();
                           }
                           if(CodeConstant.SERVER_ERROR.equals(code)){
                               Toast.makeText(RegistrationActivity.this, "手机已注册",
                                       Toast.LENGTH_SHORT).show();
                               phoneIsExist = false;
                           }
                           if(CodeConstant.SERVER_SUCCESS.equals(code)){
                               phoneIsExist = true;
                           }
                        }
                    });
                    // 如果手机不存在，则发送请求验证码
                    String sendCodeUrl = HttpConstant.BASE_URL + HttpConstant.SEND_CODE;
                    if(!phoneIsExist){
                        RequestUtil.sendPostRequest(sendCodeUrl, null, new RequestCallback() {
                            @Override
                            public void getResult(ResponseResult responseResult) {

                            }
                        });
                    }
                    ;
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bindId();
        initView();
        bindListener();
        // 注册按钮事件
        Button registerButton = findViewById(R.id.register_register);
        registerButton.setOnClickListener(new BtnClickJumpListener(this,LoginActivity.class));

    }

    /**
     * 初始化
     */
    private void initView(){
        context = this;
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

    /**
     * 绑定监听器
     */
    private void bindListener(){
        registerSendCode.setOnClickListener(clicker);
    }
}