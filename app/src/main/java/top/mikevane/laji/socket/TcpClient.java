package top.mikevane.laji.socket;


import android.content.Intent;
import android.util.Log;

import com.mysql.cj.xdevapi.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import top.mikevane.laji.IndexActivity;
import top.mikevane.laji.exception.ClientException;

/**
 * 客户端socket
 * @Author mikevane
 * @Date 17:51
 */

public class TcpClient implements Runnable{
    /**
     * 名称（提示用）
     */
    private final String TAG = "TcpClient";
    /**
     * 需要连接的服务器ip
     */
    private String  serverIP;
    /**
     * 需要连接的服务器port
     */
    private Integer serverPort;
    /**
     * 输出流，用于打印字符
     */
    private PrintWriter pw;
    /**
     * 从服务器获取输入流
     */
    private InputStream is;
    /**
     * 输入流，用作读取文件
     */
    private DataInputStream dis;
    /**
     * 标识符，确定是否断开连接
     */
    private boolean isRun = true;
    /**
     * socket对象
     */
    private Socket socket = null;
    /**
     * 缓冲区
     */
    byte[] buff  = new byte[4096];
    /**
     * 接收到的字符串
     */
    private String rcvMsg;
    /**
     * 接收到的字符串长度
     */
    private int rcvLen;



    public TcpClient(String ip , Integer port){
        this.serverIP = ip;
        this.serverPort = port;
    }

    public void closeSelf(){
        isRun = false;
    }

    public void send(String msg){
        pw.println(msg);
        pw.flush();
    }

    @Override
    public void run() {
        try {
            //必须指定ip和port，否则抛出异常
            if(serverIP == null || serverPort == null){
                throw new ClientException("服务器ip或端口不能为空");
            }
            Log.i(TAG, "run: 收到消息:"+ serverIP);
            Log.i(TAG, "run: 收到消息:"+ serverPort);
            socket = new Socket(serverIP,serverPort);
            socket.setSoTimeout(5000);
            pw = new PrintWriter(socket.getOutputStream(),true);
            is = socket.getInputStream();
            dis = new DataInputStream(is);
            Log.i(TAG, "run: 收到消息:"+ rcvMsg);
        } catch (IOException | ClientException e) {
            e.printStackTrace();
        }
        while (isRun){
            try {
                rcvLen = dis.read(buff);
                rcvMsg = new String(buff,0,rcvLen,"utf-8");
                Log.i(TAG, "run: 收到消息:"+ rcvMsg);
                Intent intent =new Intent();
                intent.setAction("tcpClientReceiver");
                intent.putExtra("tcpClientReceiver",rcvMsg);
                //将消息发送给主界面
                IndexActivity.context.sendBroadcast(intent);
                //服务器要求客户端结束
                if (rcvMsg.equals("QuitClient")){
                    isRun = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            pw.close();
            is.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}