package top.mikevane.laji.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import top.mikevane.laji.R;
import top.mikevane.laji.manager.ThreadPoolManager;
import top.mikevane.laji.pojo.RoomInfo;
import top.mikevane.laji.pojo.User;
import top.mikevane.laji.socket.TcpClient;
import top.mikevane.laji.utils.DateUtil;

/**
 * 信息显示模块
 * @author MikeV
 */
public class IndexActivity extends BaseActivity {

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
    private Button toUserInfoBtn;
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
    ExecutorService exec = ThreadPoolManager.getInstance();
    /**
     * 自定义监听器
     */
    IndexActivityClicker clicker = new IndexActivityClicker();
    /**
     * 自定义handler，用于异步通信
     */
    IndexActivityHandler handler = new IndexActivityHandler(this);
    /**
     * 自定义广播，用于接收数据
     */
    private IndexActivityReceiver indexActivityReceiver = new IndexActivityReceiver();

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
        /**
         * 弱引用指向IndexActivity活动
         */
        private WeakReference<IndexActivity> weakReference;

        IndexActivityHandler(IndexActivity indexActivity){
            weakReference = new WeakReference<IndexActivity>(indexActivity);
        }

        /**
         * 向IndexActivity发送消息
         * @param msg
         */
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(weakReference != null){
                switch (msg.what){
                    case 1:
                        temperature.setText("温度："+msg.obj.toString());
                        break;
                }
            }
        }
    }

    /**
     * 广播，监听是否有信息发送
     */
    private class IndexActivityReceiver extends BroadcastReceiver {

        /**
         * 接收发送的信息
         * @param context 上下文，用于传输从服务器接收到的数据
         * @param intent 承载部分标识字符串
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpClientReceiver":
                    String msg = intent.getStringExtra("tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    handler.sendMessage(message);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        bindId();
        bindListener();
        bindReceiver();
        initView();

        User user = (User)getIntent().getParcelableExtra("user");
        //查询实时数据，RoomInfo，默认在客厅，这里直接设置
        RoomInfo roomInfo = new RoomInfo(20, 30, "亮", "keting");

        //显示画面
        showInfo(user,roomInfo);

    }

    /**
     * 初始化页面
     * @param user
     * @param roomInfo
     */
    private void showInfo(User user,RoomInfo roomInfo){
        String nowTime = DateUtil.formateDateTime(new Date());
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
        context = this;
        //连接按钮亮起，关闭按钮熄灭
        connectBtn.setEnabled(true);
        closeBtn.setEnabled(false);
    }

    /**
     * 绑定id与控件
     */
    private void bindId(){
        toUserInfoBtn = findViewById(R.id.index_user);
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
        toUserInfoBtn.setOnClickListener(clicker);
    }

    /**
     * 绑定广播接收器
     */
    private void bindReceiver(){
        // 添加action，这里的action由 TcpClient 设置
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(indexActivityReceiver,intentFilter);
    }
}