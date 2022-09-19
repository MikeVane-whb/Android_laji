package top.mikevane.laji;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import top.mikevane.laji.pojo.User;
import top.mikevane.laji.tool.BtnClickJumpListener;

/**
 * 信息显示模块
 */
public class LoginActivity extends AppCompatActivity {

    public EditText userName;
    public EditText userPwd;
    public Button loginBtn;
    public User user;
    public CheckBox remembCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //实例化View
        initView();
        //loginBtn.setOnClickListener(new BtnClickListener(this,IndexActivity.class));
       loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取信息
                String userNameStr = userName.getText().toString();
                String userPwdStr = userPwd.getText().toString();
                if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(userPwdStr)) {
                    Toast.makeText(LoginActivity.this,"空",Toast.LENGTH_LONG).show();
                }else {
                    user.setPassword(userPwdStr);
                    //这里在数据库验证用户密码是否正确并且在数据库中查询返回一个相应user，这里使用假数据，手动设置user的id和email
                    user.setId(1);
                    user.setEmail("123.com");
                    //判断是否选中记住密码
                    boolean checked = remembCB.isChecked();
                    if (checked) {
                        //执行记住密码的业务逻辑
                    }
                    //用户合法
                    //跳转页面
                    Intent intent =new Intent(LoginActivity.this,IndexActivity.class);
                    //把整个user传过去
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
    }


    private void initView(){
        userName =  findViewById(R.id.login_userName);
        userPwd =  findViewById(R.id.login_Password);
        loginBtn= findViewById(R.id.login_login);
         user = new User();
        remembCB=findViewById(R.id.rememb);
    }

}