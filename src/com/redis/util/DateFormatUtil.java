package com.redis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil 
{
	public static String dateToString(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(date);
	}
	
	public static Date stringToDate(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = null;
		try 
		{
			date = format.parse(str);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}
	
	public static String dateToStringBlur(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		return format.format(date);
	}
	
	public static Date stringToDateBlur(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = null;
		try 
		{
			date = format.parse(str);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}

}
