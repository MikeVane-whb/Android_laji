package top.mikevane.laji.ui.activity;

import androidx.annotation.NonNull;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import top.mikevane.laji.R;
import top.mikevane.laji.manager.ThreadPoolManager;
import top.mikevane.laji.pojo.RoomInfo;
import top.mikevane.laji.pojo.User;
import top.mikevane.laji.socket.TcpClient;
import top.mikevane.laji.utils.DateUtil;
import top.mikevane.laji.utils.JsonUtil;

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
    private Button indexUserInfoBtn;
    /**
     * 跳转"监控"按钮
     */
    private Button indexIndexBtn;
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
     * 服务器 port 输入框
     */
    private EditText serverPort;
    /**
     * 开灯 按钮
     */
    private Button indexOpenLightBtn;
    /**
     * 关灯 按钮
     */
    private Button indexCloseLightBtn;
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
                // 如果点击了我的
                case R.id.index_userinfo:
                    Log.i(TAG,"onClick: 我的");
                    client.closeSelf();
                    Intent intent = getIntent();
                    // 携带手机号
                    String phone = intent.getStringExtra("phone");
                    // 携带 sessionId
                    String sessionId = intent.getStringExtra("sessionId");
                    // 将手机号和session信息传入到下一个activity
                    Intent nextIntent = new Intent(IndexActivity.this,UserInfoActivity.class);
                    nextIntent.putExtra("sessionId",sessionId);
                    nextIntent.putExtra("phone",phone);
                    startActivity(nextIntent);
                    break;
                // 如果点击了连接按钮
                case R.id.index_connect_device:
                    Log.i(TAG,"onClick: 连接");
                    //连接按钮关闭，关闭按钮打开
                    connectBtn.setEnabled(false);
                    closeBtn.setEnabled(true);
                    String ip = serverIp.getText().toString();
                    String port = serverPort.getText().toString();
                    client = new TcpClient(ip,Integer.parseInt(port));
                    exec.execute(client);
                    break;
                // 如果点击了关闭按钮
                case R.id.index_close_device:
                    Log.i(TAG,"onClick: 关闭");
                    //连接按钮打开，关闭按钮关闭
                    connectBtn.setEnabled(true);
                    closeBtn.setEnabled(false);
                    client.closeSelf();
                    break;
                case R.id.index_open_light:
                    Log.i(TAG,"onClick：打开灯");
                    exec.execute(() -> {
                        client.send("a");
                    });
                    indexCloseLightBtn.setEnabled(true);
                    indexOpenLightBtn.setEnabled(false);
                    break;
                case R.id.index_close_light:
                    Log.i(TAG,"onClick：关闭灯");
                    exec.execute(() -> {
                        client.send("A");
                    });
                    indexCloseLightBtn.setEnabled(false);
                    indexOpenLightBtn.setEnabled(true);
                    break;
                default:
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
                        try {
                            Map<String, Object> jsonToMap = JsonUtil.jsonToMap(msg.obj.toString());
                            temperature.setText("温度："+jsonToMap.get("t"));
                            humidity.setText("湿度："+jsonToMap.get("h"));
                            light.setText("亮度："+jsonToMap.get("l"));
                            // 如果 LED 处于关闭状态
                            if(jsonToMap.get("L").equals("0")){
                                indexCloseLightBtn.setEnabled(false);
                                indexOpenLightBtn.setEnabled(true);
                            }
                            // 如果 LED 处于开启状态
                            if(jsonToMap.get("L").equals("1")){
                                indexCloseLightBtn.setEnabled(true);
                                indexOpenLightBtn.setEnabled(false);
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
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
                default:
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
        initButton();

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
        // 房间信息
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
        connectBtn = findViewById(R.id.index_connect_device);
        closeBtn = findViewById(R.id.index_close_device);
        serverIp = findViewById(R.id.index_device_ip);
        serverPort = findViewById(R.id.index_device_port);
        temperature = findViewById(R.id.index_temperature);
        humidity = findViewById(R.id.index_humidity);
        light = findViewById(R.id.index_light);
        indexOpenLightBtn = findViewById(R.id.index_open_light);
        indexCloseLightBtn = findViewById(R.id.index_close_light);
        indexIndexBtn = findViewById(R.id.index_index);
        indexUserInfoBtn = findViewById(R.id.index_userinfo);
    }

    /**
     * 绑定监听器
     */
    private void bindListener(){
        closeBtn.setOnClickListener(clicker);
        connectBtn.setOnClickListener(clicker);
        indexOpenLightBtn.setOnClickListener(clicker);
        indexCloseLightBtn.setOnClickListener(clicker);
        indexIndexBtn.setOnClickListener(clicker);
        indexUserInfoBtn.setOnClickListener(clicker);
    }

    private void initButton(){
        indexIndexBtn.setEnabled(false);
        indexUserInfoBtn.setEnabled(true);
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