package top.mikevane.laji.ui.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;
import top.mikevane.laji.R;
import top.mikevane.laji.constant.CodeConstant;
import top.mikevane.laji.constant.HttpConstant;
import top.mikevane.laji.http.config.RequestCallback;
import top.mikevane.laji.manager.ThreadPoolManager;
import top.mikevane.laji.pojo.ResponseResult;
import top.mikevane.laji.ui.listener.BtnClickJumpListener;
import top.mikevane.laji.utils.RequestUtil;
import top.mikevane.laji.utils.StringUtil;
import top.mikevane.laji.utils.ToastUtil;

/**
 * @author MikeV
 */
public class RegistrationActivity extends BaseActivity {

    private static final String TAG = "RegistrationActivity";

    /**
     * 手机号已存在标识符，用于异步处理
     */
    public static final int PHONE_IS_EXIST = 0;

    /**
     * 发送邮件标识符，用于异步处理
     */
    public static final int SEND_CODE = 1;


    /**
     * 上下文，用于传输数据
     */
    @SuppressLint("StaticFieldLeak")
    public static Context context;

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

    /**
     * 线程池
     */
    private ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();

    /**
     * 异步操作
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
          switch (msg.what){
              // 如果手机存在
              case PHONE_IS_EXIST:
                  ToastUtil.showMsg(RegistrationActivity.this,"手机号已存在");
                  break;
              case SEND_CODE:
                  Log.i(TAG,"发送验证码请求");
                  // 用于将 session 存入到 SharedPreferences 中
                  String sendCodeUrl = HttpConstant.BASE_URL + HttpConstant.SEND_CODE;
                  // 携带手机号发送验证码请求
                  String phone = registerPhone.getText().toString();
                  HashMap<String,String> emailMap = new HashMap<>(2);
                  emailMap.put("phone",phone);
                  RequestUtil.sendPostRequest(sendCodeUrl, emailMap, responseResult ->  {
                      // 获取状态码
                      Integer code = responseResult.getCode();
                      // 获取数据
                      String data = (String)responseResult.getData();
                      // 如果发送请求失败
                      if(CodeConstant.CLIENT_ERROR.equals(code)){
                          ToastUtil.errorMsg(RegistrationActivity.this,"请求失败");
                      }
                      // 如果服务器出现错误
                      if(CodeConstant.SERVER_ERROR.equals(code)){
                          ToastUtil.errorMsg(RegistrationActivity.this,responseResult.getMsg());
                      }
                      // 如果请求发送成功
                      if(CodeConstant.SERVER_SUCCESS.equals(code)){
                          SharedPreferences.Editor sessionSharedEdit = getSharedPreferences("session",MODE_PRIVATE).edit();
                          // 将 session 存入本地
                          sessionSharedEdit.putString(phone,data);
                          sessionSharedEdit.commit();
                          ToastUtil.showMsg(RegistrationActivity.this,responseResult.getMsg());
                      }
                  });
                  break;
              default:
                  break;
          }
        }
    };

    /**
     * 按钮点击事件
     */
    private class RegistrationActivityClicker implements View.OnClickListener{

        /**
         * flag 用于标志手机是否存在
         */
        private boolean phoneIsExist = true;

        @SneakyThrows
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                // 如果点击了发送验证码按钮
                case R.id.register_send_code:
                    Log.i(TAG,"点击了发送验证码按钮");
                    // 防空与鉴别手机格式
                    // 如果手机为空
                    if (StringUtil.getInstance().isNullOrEmpty(registerPhone.getText().toString())){
                        ToastUtil.errorMsg(RegistrationActivity.this,"手机号不能为空");
                        return;
                    }
                    // 如果手机格式有误
//                    if (!StringUtil.getInstance().isPhone(registerPhone.getText().toString())){
//                        ToastUtil.errorMsg(RegistrationActivity.this,"手机号格式有误");
//                        return;
//                    }
                    phoneIsRegister();
                    break;
                // 如果点击了注册按钮
                case R.id.register_register:
                    Log.i(TAG,"点击了注册按钮");
                    String phone = registerPhone.getText().toString();
                    String code = registerCode.getText().toString();
                    String password = registerPassword.getText().toString();
                    String confirmPassword = registerConfirmPassword.getText().toString();
                    // 如果手机为空
                    if (StringUtil.getInstance().isNullOrEmpty(phone)){
                        ToastUtil.errorMsg(RegistrationActivity.this,"手机号不能为空");
                        return;
                    }
                    // 如果手机格式有误
//                    if (!StringUtil.getInstance().isPhone(phone)){
//                        ToastUtil.errorMsg(RegistrationActivity.this,"手机号格式有误");
//                        return;
//                    }
                    // 如果验证码为空
                    if (StringUtil.getInstance().isNullOrEmpty(code)){
                        ToastUtil.errorMsg(RegistrationActivity.this,"验证码不能为空");
                        return;
                    }
                    // 如果密码为空
                    if (StringUtil.getInstance().isNullOrEmpty(password)){
                        ToastUtil.errorMsg(RegistrationActivity.this,"密码不能为空");
                        return;
                    }
                    if (StringUtil.getInstance().isNullOrEmpty(confirmPassword)){
                        ToastUtil.errorMsg(RegistrationActivity.this,"密码不能为空");
                        return;
                    }
                    // 如果两次输入密码不一致
                    if (!password.equals(confirmPassword)){
                        ToastUtil.errorMsg(RegistrationActivity.this,"两次密码必须要一致");
                        return;
                    }

                    // 如果以上所有信息都没有问题，发送注册请求
                    Map<String,String> accountMap = new HashMap<>(10);
                    SharedPreferences sessionShared = getSharedPreferences("session", MODE_PRIVATE);
                    accountMap.put("phone",phone);
                    accountMap.put("verityCode", code);
                    accountMap.put("password", password);
                    accountMap.put("sessionId",sessionShared.getString(phone,""));
                    register(accountMap);
                    break;
            }
        }

        /**
         * 判断手机是否被注册
         * 如果已被注册则显示失败 toast
         * 否则，发送验证码请求
         * 否则为 false;
         */
        private void phoneIsRegister(){
            Log.i(TAG,"验证手机是否存在");
            Map<String,String> map = new HashMap<>(5);
            // 获取输入的手机号
            map.put("phone",registerPhone.getText().toString());
            // 发送请求，确定手机是否已注册
            String selectPhoneUrl = HttpConstant.BASE_URL + HttpConstant.SELECT_PHONE;
            RequestUtil.sendPostRequest(selectPhoneUrl, map, responseResult -> {
                // 获取状态码
                Integer code = responseResult.getCode();
                // 获取数据
                Integer dataCount = (Integer)responseResult.getData();
                // 如果发送请求失败
                if(CodeConstant.CLIENT_ERROR.equals(code)){
                    ToastUtil.errorMsg(RegistrationActivity.this,"请求失败");
                }
                // 如果服务器出现错误
                if(CodeConstant.SERVER_ERROR.equals(code)){
                    ToastUtil.errorMsg(RegistrationActivity.this,"服务器故障");
                }
                // 如果请求发送成功
                if(CodeConstant.SERVER_SUCCESS.equals(code)){
                    // 如果手机号已存在
                    if(dataCount > 0){
                        threadPoolManager.execute(()->{
                            // 通过异步消息处理机制，解决了在子线程中进行UI操作的问题
                            Message message = new Message();
                            message.what = PHONE_IS_EXIST;
                            handler.sendMessage(message);
                        });
                    }
                    // 如果手机不存在，发送验证码请求
                    else{
                        threadPoolManager.execute(()->{
                            // 手机确定不存在后，发送验证码请求
                            Message message = new Message();
                            message.what = SEND_CODE;
                            handler.sendMessage(message);
                        });
                    }
                }
            });
        }

        /**
         * 发送注册请求
         * @param accountMap
         */
        private void register(Map<String,String> accountMap){
            Log.i(TAG,"发送注册请求");
            RequestUtil.sendPostRequest(HttpConstant.BASE_URL + HttpConstant.REGISTER_ACCOUNT,
                    accountMap, responseResult ->  {
                // 状态码
                Integer resultCode = responseResult.getCode();
                if (resultCode.equals(CodeConstant.SERVER_ERROR)){
                    ToastUtil.errorMsg(RegistrationActivity.this,(String)responseResult.getMsg());
                    return;
                }
                if (resultCode.equals(CodeConstant.CLIENT_ERROR)){
                    ToastUtil.errorMsg(RegistrationActivity.this,"请求失败");
                }
                if (resultCode.equals(CodeConstant.SERVER_SUCCESS)){
                    // 注册成功
                    if((Integer)responseResult.getData() > 0){
                        Log.i(TAG,"注册成功");
//                        ToastUtil.successMsg(RegistrationActivity.this,"注册成功");
                        Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
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
        registerRegister.setOnClickListener(clicker);
    }
}