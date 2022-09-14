package top.mikevane.laji;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import top.mikevane.laji.pojo.User;
import top.mikevane.laji.pojo.UserInfo;
import top.mikevane.laji.tool.BtnClickListener;

/**
 * 用户详情界面
 */

public class UserInfoActivity extends AppCompatActivity {
    private ImageView userPhoto;
    private TextView userInfoView;
    private EditText userInfo_userName,userInfo_sex,userInfo_email,userInfo_phone,userInfo_address;
    private Button userInfo_changePwdBtn;
    private Button userInfo_saveChangeBtn;
    private Button userInfo_exitLoginBtn;
    private Button userInfoBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        //获取传过来的userinfoId
        String userInfoId = getIntent().getStringExtra("userInfoId");

        //根据userInfoId,查到对应的UserInfo，把整个userInfo传过去,这里手动添加
        UserInfo userInfo = new UserInfo(123,"小明","123","123.com","光明路","123456","photo","男");
        showInfo(userInfo);

        //修改按钮事假

        //修改密码事件

        // 返回事件
        userInfoBackBtn.setOnClickListener(new BtnClickListener(this,IndexActivity.class));
        //退出账号
        userInfo_exitLoginBtn.setOnClickListener(new BtnClickListener(this,HomePageActivity.class));
    }



    private void showInfo(UserInfo userInfo){
        userInfoView.setText("欢迎您"+userInfo.getUsername());
        userInfo_userName.setText("名称："+userInfo.getUsername());
        userInfo_sex.setText("性别："+userInfo.getSex());
        userInfo_email.setText("邮箱："+userInfo.getEmail());
        userInfo_phone.setText("电话："+userInfo.getPhone());
        userInfo_address.setText("地址："+userInfo.getAddress());
    }



    private void initView(){
        userInfoBackBtn= findViewById(R.id.userInfo_back);
        userPhoto =  findViewById(R.id.userInfo_userPhoto);
        userInfoView =  findViewById(R.id.userInfo_userInfo);
        userInfo_userName= findViewById(R.id.userInfo_userName);
        userInfo_sex= findViewById(R.id.userInfo_sex);
        userInfo_email= findViewById(R.id.userInfo_email);
        userInfo_phone= findViewById(R.id.userInfo_phone);
        userInfo_address= findViewById(R.id.userInfo_address);
        userInfo_changePwdBtn=findViewById(R.id.userInfo_changePwdBtn);
        userInfo_saveChangeBtn=findViewById(R.id.userInfo_saveChangeBtn);
        userInfo_exitLoginBtn=findViewById(R.id.userInfo_exitLoginBtn);
    }

}
