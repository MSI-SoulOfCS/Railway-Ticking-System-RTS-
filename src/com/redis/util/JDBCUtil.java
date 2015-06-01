package com.redis.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil 
{
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	
	private static ThreadLocal<Connection> local = 
			new ThreadLocal<Connection>();
	
	static
	{
		InputStream in = JDBCUtil.class.getClassLoader()
				.getResourceAsStream("JDBC.properties");

			Properties p = new Properties();
			try {
				p.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DRIVER = p.getProperty("ORACLE_DRIVER");
			URL = p.getProperty("ORACLE_URL");
			USERNAME = p.getProperty("ORACLE_USERNAME");
			PASSWORD = p.getProperty("ORACLE_PASSWORD");
	}
	
	public static Connection getConnection()
	{
		Connection con = local.get();
		try
		{
			if(con == null)
			{
				Class.forName(DRIVER);
				con  = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				local.set(con);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void close()
	{
		Connection con = local.get();
		
		if(con != null)
		{
			try 
			{
				con.close();
				local.set(null);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}

}
