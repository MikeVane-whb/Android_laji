package top.mikevane.laji.tool;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtil {

    //配置对象
    private static Properties properties = new Properties();

    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    //jdbc的变量
    private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String jdbcUrl = "jdbc:mysql://106.55.225.93/laji?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "134679";

    /**
     * 设置默认属性(包括jdbc配置与properties)
     */
    static {
        try{
//            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("/assets/jdbc.properties");
//            //读取配置
//            properties.load(resourceAsStream);
//
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
     * 本方法中调用Date类型变量的setter方法时使用的是java.sql.Date，
     * 所以实体类在声明Date类型变量时一定声明成java.sql.Date
     * 至少Date类型变量对应的setter方法的形参必须是java.sql.Date，否则报错
     *
     * 查询完毕后，使用者通过JDBCHelper.getConnection()获取连接对象，并关闭它
     * 外部获取的连接对象与本方法使用的连接对象，在同一线程类，是同一个对象
     * @param sql   需要执行的sql语句
     * @param t     实体类对象
     * @param objs   SQL中的参数
     * @return  装有实体类的list集合
     */
    public static <T> List<T>  executeQuery(String sql,T t,Object...objs){
        //声明jdbc变量
        List<T> list=new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs =null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            //给占位符赋值
            if(objs!=null) {
                for(int i=0;i<objs.length;i++) {
                    ps.setObject((i+1), objs[i]);
                }
            }
            //执行sql语句
            rs = ps.executeQuery();
            //获取结果集中字段的所有信息
            ResultSetMetaData rm = rs.getMetaData();
            int columnCount = rm.getColumnCount();//获取字段数
            //遍历结果集
            while(rs.next()) {
                Class<? extends Object> cla = t.getClass();//获取类对象
                T newInstance=(T)cla.newInstance();//获取类的对象
                //一个for循环封装一条记录的所有值
                for(int i=1;i<=columnCount;i++) {
                    String columnName = rm.getColumnName(i);//获取字段名
                    //获取字段对应的setter方法
                    String methodName="set"+columnName.substring(0, 1).toUpperCase()+columnName.substring(1);
                    String columnClassName = rm.getColumnClassName(i);//获取字段java类型的完全限定名
                    //创建方法对象
                    Method method = cla.getDeclaredMethod(methodName, Class.forName(columnClassName));
                    method.invoke(newInstance,rs.getObject(columnName));//调用setter方法，执行对象属性赋值
                }
                list.add(newInstance);//将对象加入集合
            }
        } catch (Exception  e) {
            e.printStackTrace();
        }finally {
            //关流
            JDBCUtil.close(ps,rs);
        }
        return list;

    }


    /**
     * 获取数据库的Connection对象
     * @return
     * @throws SQLException
     */

    public static Connection getConnection() {
        Connection connection = connectionThreadLocal.get();
        if(connection==null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                connectionThreadLocal.set(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;//返回jdbc连接
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
