package top.mikevane.laji;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import top.mikevane.laji.tool.JDBCUtil;

public class JDBCUtilTest {
    /**
     * 测试是否能正常连接数据库
     */
    @Test
    public void testGetConnection(){
        Connection connection = JDBCUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from stu_user");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id")
                        + resultSet.getString("username")
                        + resultSet.getString("password"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
