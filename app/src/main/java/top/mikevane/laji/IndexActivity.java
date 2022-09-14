package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;

import top.mikevane.laji.pojo.RoomInfo;
import top.mikevane.laji.pojo.User;
import top.mikevane.laji.pojo.UserInfo;
import top.mikevane.laji.tool.BtnClickListener;
import top.mikevane.laji.utils.DateUtils;

/**
 * 信息显示模块
 */
public class IndexActivity extends AppCompatActivity {

    private Button toUserINfoBtn;
    private User user;
    private Bundle bundle;
    private TextView textView,index_title,index_temperature,index_humidity,index_light;
    private EditText editTextTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();

        User user = (User)getIntent().getParcelableExtra("user");
        //查询实时数据，RoomInfo，默认在客厅，这里直接设置
        RoomInfo roomInfo = new RoomInfo(20, 30, "亮", "keting");

        //显示画面
        showInfo(user,roomInfo);

        //"我的"按钮，跳转到userinfo界面
        //toUserINfoBtn.setOnClickListener(new BtnClickListener(this,UserInfoActivity.class));
        toUserINfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInfoId = user.getUserInfoId();
               //跳转页面
                Intent intent =new Intent(IndexActivity.this,UserInfoActivity.class);
               //把userInfo传过去
                intent.putExtra("userInfoId",userInfoId);
                startActivity(intent);
            }
        });
    }

    private void showInfo(User user,RoomInfo roomInfo){
        textView.setText("天气：晴，温度：22C");
        String nowTime = DateUtils.formateDateTime(new Date());
        editTextTime2.setText(nowTime);
        index_title.setText(user.getUsername()+"的家");
        //房间信息
        index_light.setText("亮度："+roomInfo.getLight());
        index_humidity.setText("温度："+roomInfo.getHumidity());
        index_temperature.setText("湿度："+roomInfo.getTemperature());

    }


    private void initView(){
        toUserINfoBtn= findViewById(R.id.index_user);
        user = new User();
        textView=findViewById(R.id.tianqi);
        editTextTime2=findViewById(R.id.editTextTime2);
        index_title=findViewById(R.id.index_title);
        index_light=findViewById(R.id.index_light);
        index_temperature=findViewById(R.id.index_temperature);
        index_humidity=findViewById(R.id.index_humidity);
    }
}