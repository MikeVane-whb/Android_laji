package top.mikevane.laji.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import top.mikevane.laji.R;
import top.mikevane.laji.constant.CodeConstant;
import top.mikevane.laji.constant.HttpConstant;
import top.mikevane.laji.pojo.User;
import top.mikevane.laji.pojo.UserInfo;
import top.mikevane.laji.utils.RequestUtil;
import top.mikevane.laji.utils.StringUtil;
import top.mikevane.laji.utils.ToastUtil;

/**
 * 登录模块
 * @author MikeV
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    /**
     * 登录手机号
     */
    private EditText loginPhone;
    /**
     * 登录密码
     */
    private EditText loginPassword;
    /**
     * 登录按钮
     */
    private Button loginBtn;
    private User user;
    /**
     * 记住密码
     */
    private CheckBox loginRememberPwd;

    /**
     * 自定义监听器
     */
    private LoginActivityClicker clicker = new LoginActivityClicker();


    private class LoginActivityClicker implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                // 按下登录按钮
                case R.id.login_login:
                    Log.i(TAG,"按下登录按钮");
                    String phone = loginPhone.getText().toString();
                    String password = loginPassword.getText().toString();
                    if (StringUtil.getInstance().isNullOrEmpty(phone)){
                        ToastUtil.errorMsg(LoginActivity.this,"手机号不能为空");
                        return;
                    }
//                    if (StringUtil.getInstance().isPhone(phone)){
//                        ToastUtil.errorMsg(LoginActivity.this,"手机号格式有误");
//                        return;
//                    }
                    if (StringUtil.getInstance().isNullOrEmpty(password)){
                        ToastUtil.errorMsg(LoginActivity.this,"密码不能为空");
                        return;
                    }
                    Map<String,String> userMap = new HashMap<>(2);
                    userMap.put("phone",phone);
                    userMap.put("password",password);
                    // 确定手机号与密码正确
                    RequestUtil.sendPostRequest(HttpConstant.BASE_URL + HttpConstant.LOGIN,
                            userMap, responseResult -> {
                        Log.i(TAG,"发送登录请求");
                        // 状态码
                        Integer code = responseResult.getCode();
                        // 如果发送请求失败
                        if(CodeConstant.CLIENT_ERROR.equals(code)){
                            ToastUtil.errorMsg(LoginActivity.this,"请求失败");
                        }
                        // 如果服务器出现错误
                        if(CodeConstant.SERVER_ERROR.equals(code)){
                            ToastUtil.errorMsg(LoginActivity.this,responseResult.getMsg());
                        }
                        // 如果请求发送成功
                        if(CodeConstant.SERVER_SUCCESS.equals(code)){
                            Log.i(TAG,"登录成功");
                            // 获取数据
                            SharedPreferences.Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
                            editor.putString(phone, String.valueOf(responseResult.getData()));
                            editor.commit();
                            // 如果手机号与密码正确
                            // sessionId传入下一个活动
                            Intent intent = new Intent(LoginActivity.this,UserInfoActivity.class);
                            intent.putExtra("sessionId",String.valueOf(responseResult.getData()));
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                        }
                    });
                    break;
                default:
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"进入到登录页面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();
        bindListener();
    }

    /**
     * 绑定 id
     */
    private void bind(){
        loginPhone =  findViewById(R.id.login_phone);
        loginPassword =  findViewById(R.id.login_password);
        loginBtn= findViewById(R.id.login_login);
        user = new User();
        loginRememberPwd=findViewById(R.id.login_remember_password);
    }

    /**
     * 绑定监听器
     */
    private void bindListener(){
        loginBtn.setOnClickListener(clicker);
    }

}