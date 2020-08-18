package com.bdqn.financing.utils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author wang_lei
 * @date 2020.08.01
 */
public class DataSourceUtil {

	private static DataSource dataSource;
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	static {
		init();
	}

	/**
	 * 初始化方法
	 * 从配置文件获取数据库连接关键信息
	 */
	public static void init(){
		Properties params=new Properties();
		String configFile = "database.properties";
		InputStream is=DataSourceUtil.class.getClassLoader().getResourceAsStream(configFile);
		try {
			params.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver=params.getProperty("driver");
		url=params.getProperty("url");
		user=params.getProperty("username");
		password=params.getProperty("password");
	}

	/**
	 * 开启并获取数据库连接
	 * @return 连接对象
	 * @throws SQLException
	 */
	public static Connection openConnection() throws SQLException {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}

	/**
	 * 关闭数据库连接
	 * @param connection 连接对象
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
