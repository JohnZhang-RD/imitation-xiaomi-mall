package cn.francis.mall.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.Getter;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Name: DataSourceUtils
 * Package: cn.francis.mall.utils
 * date: 2024/09/04 - 15:21
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class DataSourceUtils {
    @Getter
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
    static {
        try {
            Properties properties=new Properties();
            InputStream is = DataSourceUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);
            is.close();
            //初始化数据源
            dataSource=(DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.out.println("初始化数据失败："+e.getMessage());
        }
    }

    //获取连接
    public static Connection getConnection(){
        try {
            Connection connection = threadLocal.get();
            if (connection == null) {
                //获取连接
                connection=dataSource.getConnection();
                //存入集合
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("获取连接失败："+e.getMessage());
        }
        return null;
    }
    //释放资源
    public static void closeAll(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                //判断是否自动处理事务
                if (connection.getAutoCommit()) {
                    //释放资源
                    connection.close();
                    //解除绑定
                    threadLocal.remove();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //编写4个与事务相关的方法
    public static void begin()throws SQLException{
        Connection connection = getConnection();
        if (connection != null) {
            connection.setAutoCommit(false);
        }
    }
    public static void commit()throws SQLException{
        Connection connection = getConnection();
        if (connection != null) {
            connection.commit();
        }
    }
    public static void rollback()throws SQLException{
        Connection connection = getConnection();
        if (connection != null) {
            connection.rollback();
        }
    }
    public static void close()throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            connection.close();
            //解除绑定
            threadLocal.remove();
        }
    }
}
