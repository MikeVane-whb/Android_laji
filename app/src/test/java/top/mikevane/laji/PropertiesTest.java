package top.mikevane.laji;

import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Author mikevane
 * @Date 10:55
 * @Version 1.0
 */
public class PropertiesTest {

    public static Properties properties1;

    //加载system.properties配置文件,读取文件的配置参数
    private static synchronized void loadProperties() {
        try {
            Properties properties=new Properties();
            InputStream inputStream=PropertiesTest.class.getClassLoader().getResourceAsStream("assets/jdbc.properties");
            properties.load(inputStream);
            properties1 = properties;
        }catch(Exception e) {
            throw new RuntimeException("未找到配置文件");
        }
    }
    //读取配置文件中的参数的值
    public void get() {
        loadProperties();
        System.out.println(properties1.getProperty("jdbc.driverUrl"));
        System.out.println(properties1.getProperty("jdbc.driverClassName"));
        System.out.println(properties1.getProperty("jdbc.password"));
        System.out.println(properties1.getProperty("jdbc.user"));
    }
    @Test
    public void test() {
        PropertiesTest testProperties=new PropertiesTest();
        testProperties.get();
    }
}
