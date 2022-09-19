package top.mikevane.laji;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.mikevane.laji.pojo.RoomInfo;
import top.mikevane.laji.pojo.User;
import top.mikevane.laji.pojo.UserInfo;
import top.mikevane.laji.socket.TcpClient;
import top.mikevane.laji.tool.BtnClickJumpListener;
import top.mikevane.laji.utils.DateUtils;

/**
 * 信息显示模块
 * @author MikeV
 */
public class IndexActivity extends AppCompatActivity {

    /**
     * 标签，用于日志
     */
    private final String TAG = "IndexActivity";

    /**
     * 上下文，用于传输数据
     */
    @SuppressLint("StaticFieldLeak")
    public static Context context ;

    /**
     * 跳转“我的”按钮
     */
    private Button toUserINfoBtn;
    /**
     * connect按钮
     */
    private Button connectBtn;
    /**
     * close按钮
     */
    private Button closeBtn;
    /**
     * 服务器ip 输入框
     */
    private EditText serverIp;
    /**
     * 服务器port 输入框
     */
    private EditText serverPort;
    /**
     * 用户实体对象
     */
    private User user;
    /**
     * Bundle对象，用于传输数据到其他活动
     */
    private Bundle bundle;
    /**
     * 首页，温度，湿度，亮度
     */
    private TextView title,temperature,humidity,light;
    /**
     * TcpClient对象
     */
    private static TcpClient client;
    /**
     * 线程池
     */
    ExecutorService exec = Executors.newCachedThreadPool();
    /**
     * 自定义监听器
     */
    IndexActivityClicker clicker = new IndexActivityClicker();

    /**
     * 点击事件类，封装各种点击事件处理
     */
    private class IndexActivityClicker implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //如果点击了我的
                case R.id.index_user:
                    Log.i(TAG,"onClick: 我的");
                    String userInfoId = user.getUserInfoId();
                    //跳转页面
                    Intent intent =new Intent(IndexActivity.this,UserInfoActivity.class);
                    //把userInfo传过去
                    intent.putExtra("userInfoId",userInfoId);
                    startActivity(intent);
                    break;
                //如果点击了连接按钮
                case R.id.index_connect_device:
                    Log.i(TAG,"onClick: 连接");
                    //连接按钮关闭，关闭按钮打开
                    connectBtn.setEnabled(false);
                    closeBtn.setEnabled(true);
                    String ip = serverIp.getText().toString().substring(3);
                    String port = serverPort.getText().toString().substring(5);
                    client = new TcpClient(ip,Integer.parseInt(port));
                    exec.execute(client);
                    break;
                //如果点击了关闭按钮
                case R.id.index_close_device:
                    Log.i(TAG,"onClick: 关闭");
                    //连接按钮打开，关闭按钮关闭
                    connectBtn.setEnabled(true);
                    closeBtn.setEnabled(false);
                    client.closeSelf();
                    break;
            }
        }
    }

    /**
     * 使用handler进行异步通信
     */
    private class IndexActivityHandler extends Handler{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        bindId();
        bindListener();
        initView();

        User user = (User)getIntent().getParcelableExtra("user");
        //查询实时数据，RoomInfo，默认在客厅，这里直接设置
        RoomInfo roomInfo = new RoomInfo(20, 30, "亮", "keting");

        //显示画面
        showInfo(user,roomInfo);

    }

    private void showInfo(User user,RoomInfo roomInfo){
        String nowTime = DateUtils.formateDateTime(new Date());
        //index_title.setText(user.getUsername()+"的家");
        //房间信息
        light.setText("亮度："+roomInfo.getLight());
        humidity.setText("温度："+roomInfo.getHumidity());
        temperature.setText("湿度："+roomInfo.getTemperature());

    }

    /**
     * 初始化对象
     */
    private void initView(){
        user = new User();

        //连接按钮亮起，关闭按钮熄灭
        connectBtn.setEnabled(true);
        closeBtn.setEnabled(false);
    }

    /**
     * 绑定id与控件
     */
    private void bindId(){
        toUserINfoBtn = findViewById(R.id.index_user);
        connectBtn = findViewById(R.id.index_connect_device);
        closeBtn = findViewById(R.id.index_close_device);
        serverIp = findViewById(R.id.index_device_ip);
        serverPort = findViewById(R.id.index_device_port);
        title = findViewById(R.id.index_title);
        temperature = findViewById(R.id.index_temperature);
        humidity = findViewById(R.id.index_humidity);
        light = findViewById(R.id.index_light);
    }

    /**
     * 绑定监听器
     */
    private void bindListener(){
        closeBtn.setOnClickListener(clicker);
        connectBtn.setOnClickListener(clicker);
        toUserINfoBtn.setOnClickListener(clicker);
    }
}