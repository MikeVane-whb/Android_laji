package top.mikevane.laji.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
     * 查询标签
     */
    private static final int SELECT_USERINFO = 0;

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
    private Button userInfoIndex;
    /**
     * 进入到我的页面 button
     */
    private Button userInfoUserInfo;
    /**
     * 返回按钮
     */
    private Button userInfoBackBtn;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SELECT_USERINFO:
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
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
//        initView();
        //获取传过来的userinfoId
        Bundle bundle = getIntent().getExtras();


        //根据userInfoId,查到对应的UserInfo，把整个userInfo传过去,这里手动添加
        UserInfo userInfo = new UserInfo(123,"小明","123","123.com","光明路","123456","photo","男");
        showInfo(userInfo);

        //修改按钮事假

        //修改密码事件

        // 返回事件
        userInfoBackBtn.setOnClickListener(new BtnClickJumpListener(this,IndexActivity.class));
        //退出账号
//        userInfo_exitLoginBtn.setOnClickListener(new BtnClickJumpListener(this,HomePageActivity.class));
    }



    private void showInfo(UserInfo userInfo){
//        userInfoView.setText("欢迎您"+userInfo.getUsername());
//        userInfo_userName.setText("名称："+userInfo.getUsername());
//        userInfo_sex.setText("性别："+userInfo.getSex());
//        userInfo_email.setText("邮箱："+userInfo.getEmail());
//        userInfo_phone.setText("电话："+userInfo.getPhone());
    }


    /**
     * 绑定控件
     */
    private void bind(){
        userInfoWelcomeName = findViewById(R.id.userInfo_welcomeName);
        userInfoUserName = findViewById(R.id.userInfo_userName);
        userInfoSex = findViewById(R.id.userInfo_sex);
        userInfoEmail = findViewById(R.id.userInfo_email);
        userInfoPhone = findViewById(R.id.login_phone);
        userInfoChangePwdBtn = findViewById(R.id.userInfo_changePwdBtn);
        userInfoChangeInfoBtn = findViewById(R.id.userInfo_changeInfoBtn);
        userInfoExitLoginBtn = findViewById(R.id.userInfo_exitLoginBtn);
        userInfoIndex = findViewById(R.id.userInfo_index);
        userInfoUserInfo = findViewById(R.id.userInfo_userInfo);
    }

}
