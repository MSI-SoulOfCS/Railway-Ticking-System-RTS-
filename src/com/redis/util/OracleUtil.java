package com.redis.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.redis.entity.RedisTransaction;

public class OracleUtil 
{
	public static void save(List<RedisTransaction> transactions)
	{
		Connection con = JDBCUtil.getConnection();
		
		if(transactions == null)
			return;
		
		else if(transactions.size() == 0)
			return;
		
		try 
		{
			PreparedStatement stat = 
					con.prepareStatement("insert into transaction values(?,?,?,?)");
			
			for(RedisTransaction transaction:transactions)
			{
				stat.setString(1, transaction.getUserId());
				stat.setString(2, transaction.getTicketId());
				stat.setString(3, transaction.getSeatNo());
				stat.setString(4, DateFormatUtil.dateToString(transaction.getDate()));
				stat.addBatch();
			}
			
			stat.executeBatch();

		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			JDBCUtil.close();
		}
	}

}
