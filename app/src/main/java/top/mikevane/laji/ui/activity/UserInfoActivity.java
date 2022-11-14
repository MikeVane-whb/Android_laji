package top.mikevane.laji.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import top.mikevane.laji.R;
import top.mikevane.laji.constant.CodeConstant;
import top.mikevane.laji.constant.HttpConstant;
import top.mikevane.laji.pojo.UserInfo;
import top.mikevane.laji.ui.listener.BtnClickJumpListener;
import top.mikevane.laji.utils.RequestUtil;
import top.mikevane.laji.utils.ToastUtil;

/**
 * 用户详情界面
 * @author MikeV
 */

public class UserInfoActivity extends BaseActivity {

    public static final String TAG = "UserInfoActivity";

    /**
     * 显示用户信息标签
     */
    private static final int SHOW_USERINFO = 0;

    /**
     * 欢迎模块 名字
     */
    private TextView userInfoWelcomeName;
    /**
     * 姓名
     */
    private EditText userInfoUserName;
    /**
     * 性别
     */
    private EditText userInfoSex;
    /**
     * email
     */
    private EditText userInfoEmail;
    /**
     * 手机
     */
    private EditText userInfoPhone;
    /**
     * 修改用户信息 button
     */
    private Button userInfoChangeInfoBtn;
    /**
     * 修改密码 button
     */
    private Button userInfoChangePwdBtn;
    /**
     * 退出 button
     */
    private Button userInfoExitLoginBtn;
    /**
     * 进入到监控页面 button
     */
    private Button userInfoIndexBtn;
    /**
     * 进入到我的页面 button
     */
    private Button userInfoUserInfoBtn;

    /**
     * 按钮点击事件
     */
    private UserInfoActivityListener listener = new UserInfoActivityListener();
    private class UserInfoActivityListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                // 修改用户信息
                case R.id.userInfo_changeInfoBtn:
                    break;
                // 修改密码
                case R.id.userInfo_changePwdBtn:
                    break;
                // 退出账户
                case R.id.userInfo_exitLoginBtn:
                    break;
                // 进入监控页面
                case R.id.userInfo_index:
                    Log.i(TAG,"进入监控页面");
                    Intent intent = new Intent(UserInfoActivity.this,IndexActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                // 进入到我的页面，就进行查询用户信息
                case SHOW_USERINFO:
                    UserInfo userInfo = (UserInfo) msg.obj;
                    showInfo(userInfo);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        bindId();
        bindListener();
        selectUserInfo();
        initButton();
    }

    /**
     * 查询 userinfo
     */
    private void selectUserInfo(){
        Log.i(TAG, "发送查询用户信息请求");
        String userinfoUrl = HttpConstant.BASE_URL + HttpConstant.SELECT_USERINFO;
        Intent intent = getIntent();
        // 携带手机号查询
        String phone = intent.getStringExtra("phone");
        // 携带 sessionId
        String sessionId = intent.getStringExtra("sessionId");
        Map<String, String> userinfoMap = new HashMap<>(3);
        userinfoMap.put("phone", phone);
        userinfoMap.put("sessionId", sessionId);
        RequestUtil.sendPostRequest(userinfoUrl, userinfoMap, responseResult -> {
            // 状态码
            Integer code = responseResult.getCode();
            // 如果发送请求失败
            if (CodeConstant.CLIENT_ERROR.equals(code)) {
                ToastUtil.errorMsg(UserInfoActivity.this, "请求失败");
            }
            // 如果服务器出现错误
            if (CodeConstant.SERVER_ERROR.equals(code)) {
                ToastUtil.errorMsg(UserInfoActivity.this, responseResult.getMsg());
            }
            // 如果请求发送成功
            if (CodeConstant.SERVER_SUCCESS.equals(code)) {
                Log.i(TAG, "查询成功");
                UserInfo userInfo = new UserInfo();
                // 将后端传入数据装入 userInfo
                LinkedHashMap<String, String> resultData = (LinkedHashMap<String, String>) responseResult.getData();
                if (resultData.containsKey("username")) {
                    userInfo.setUsername(resultData.get("username"));
                }
                if (resultData.containsKey("email")) {
                    userInfo.setEmail(resultData.get("email"));
                }
                if (resultData.containsKey("phone")) {
                    userInfo.setPhone(resultData.get("phone"));
                }
                if (resultData.containsKey("userPhoto")) {
                    userInfo.setUserPhoto(resultData.get("userPhoto"));
                }
                if (resultData.containsKey("sex")) {
                    userInfo.setSex(resultData.get("sex"));
                }
                Message message = new Message();
                message.what = SHOW_USERINFO;
                message.obj = userInfo;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 显示用户信息
     * @param userInfo
     */
    private void showInfo(UserInfo userInfo){
        String username = userInfo.getUsername() == null ? "" : userInfo.getUsername();
        String sex = userInfo.getSex() == null ? "" : userInfo.getSex();
        String email = userInfo.getEmail() == null ? "" : userInfo.getEmail();
        String phone = userInfo.getPhone() == null ? "" : userInfo.getPhone();
        userInfoWelcomeName.setText(username);
        userInfoUserName.setText("名称："+username);
        userInfoSex.setText("性别："+sex);
        userInfoEmail.setText("邮箱："+email);
        userInfoPhone.setText("电话："+phone);
    }


    /**
     * 绑定控件
     */
    private void bindId(){
        userInfoWelcomeName = findViewById(R.id.userInfo_welcomeName);
        userInfoUserName = findViewById(R.id.userInfo_userName);
        userInfoSex = findViewById(R.id.userInfo_sex);
        userInfoEmail = findViewById(R.id.userInfo_email);
        userInfoPhone = findViewById(R.id.userInfo_phone);
        userInfoChangePwdBtn = findViewById(R.id.userInfo_changePwdBtn);
        userInfoChangeInfoBtn = findViewById(R.id.userInfo_changeInfoBtn);
        userInfoExitLoginBtn = findViewById(R.id.userInfo_exitLoginBtn);
        userInfoIndexBtn = findViewById(R.id.userInfo_index);
        userInfoUserInfoBtn = findViewById(R.id.userInfo_userInfo);
    }

    /**
     * 绑定事件
     */
    private void bindListener(){
        userInfoChangeInfoBtn.setOnClickListener(listener);
        userInfoExitLoginBtn.setOnClickListener(listener);
        userInfoChangePwdBtn.setOnClickListener(listener);
        userInfoIndexBtn.setOnClickListener(listener);
        userInfoUserInfoBtn.setOnClickListener(listener);
    }

    /**
     * 初始化 button
     */
    private void initButton(){
        userInfoIndexBtn.setEnabled(true);
        userInfoUserInfoBtn.setEnabled(false);
    }


}
