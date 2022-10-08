package top.mikevane.laji;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import top.mikevane.laji.pojo.User;
import top.mikevane.laji.utils.JDBCUtil;

public class JDBCUtilTest {
    /**
     * 测试是否能正常连接数据库
     */
    @Test
    public void testGetConnection(){
        Connection connection = JDBCUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from lj_user");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id") + "\n"
                        + resultSet.getString("username") + "\n"
                        + resultSet.getString("password") + "\n"
                        + resultSet.getString("email"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConnection2(){
        List<User> users = JDBCUtil.executeQuery("select * from stu_user", new User());
        System.out.println(users);
    }
}
