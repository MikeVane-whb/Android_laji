package top.mikevane.laji.tool;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {

    //配置对象
    private static Properties properties = new Properties();

    private static Connection connection = null;

    //jdbc的变量
    private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String jdbcUrl = "jdbc:mysql://localhost:3306/student?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "123456";

    /**
     * 设置默认属性(包括jdbc配置与properties)
     */
    static {
        try{
            //InputStream resourceAsStream =
            //读取配置
            //properties.load(resourceAsStream);

//            jdbcDriver = properties.getProperty("jdbc_driver");
//            jdbcUrl = properties.getProperty("jdbc_url");
//            jdbcUsername = properties.getProperty("jdbc_username");
//            jdbcPassword = properties.getProperty("jdbc_password");

            /*
             * 加载驱动，静态代码块只执行一次，驱动只加载一次（加载驱动很耗性能的）
             */
            Class.forName(jdbcDriver);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库的Connection对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



    /**
     * 关流的方法，接收任意多个任意类型的流对象
     * 如果关闭的流对象有关闭的先后顺序
     * 请将要先关闭的流对象放在前方
     *
     * 所有流对象的顶级父接口都是 AutoCloseable
     * @param t    要关闭的流对象，可以是一个或多个（也可以是零个）
     *
     */
    public static <T>void close(T...t){
        //循环关闭流
        for(T temp : t){
            //如果流的接口是AutoCloseable
            if(temp instanceof AutoCloseable){
                //关闭流
                try {
                    ((AutoCloseable) temp).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
