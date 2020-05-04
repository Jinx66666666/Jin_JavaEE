package cn.jin.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description: JDBV工具类
 * @author: HuangJing
 * @date: 2020/02/25  18:23:13
 * @Version: 1.0
 **/
public class JDBCUtil {
    private static DataSource ds;
    static {
        try {
            Properties properties = new Properties();
            InputStream rs = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(rs);
//            初始化连接池对象
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//获取连接池对象
    public static DataSource getDataSource(){
        return ds;
    }
//    获取连接对象
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
