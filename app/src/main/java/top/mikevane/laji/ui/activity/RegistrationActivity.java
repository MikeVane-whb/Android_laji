package top.mikevane.laji.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import top.mikevane.laji.manager.ThreadPoolManager;
import top.mikevane.laji.pojo.ResponseResult;
import top.mikevane.laji.ui.listener.BtnClickJumpListener;
import top.mikevane.laji.utils.RequestUtil;
import top.mikevane.laji.utils.ToastUtil;

/**
 * @author MikeV
 */
public class RegistrationActivity extends BaseActivity {

    public static final int PHONE_IS_EXIST = 0;

    /**
     * 上下文，用于传输数据
     */
    @SuppressLint("StaticFieldLeak")
    public static Context context ;

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
     * 异步更新ui
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
          switch (msg.what){
              // 如果手机存在
              case PHONE_IS_EXIST:
                  ToastUtil.showMsg(RegistrationActivity.this,"手机号已存在");
                  break;
              default:
                  break;
          }
        }
    };

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
                    Map<String,String> map = new HashMap<>();
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
                                    //通过异步消息处理机制，解决了在子线程中进行UI操作的问题
                                    Message message = new Message();
                                    message.what = PHONE_IS_EXIST;
                                    handler.sendMessage(message);
                                });
                                phoneIsExist = true;
                            }
                            else{
                                phoneIsExist = false;
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
                    };
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