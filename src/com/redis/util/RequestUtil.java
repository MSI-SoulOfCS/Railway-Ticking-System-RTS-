package com.redis.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class RequestUtil 
{
	public static int sendPostRequest(String url,String[] arr)
	{
		if(arr.length%2 != 0)
			return 0;
		
		HttpClient client = new HttpClient();
		
		PostMethod method = new PostMethod(url);
		
		method.setRequestHeader("Content-Type",  
                "application/x-www-form-urlencoded;charset=gb2312");   
		
		int arrLeng = arr.length/2;
		NameValuePair[] param = new NameValuePair[arrLeng];
		
		for(int i=0;i<arrLeng;i++)
		{
			param[i] = new NameValuePair(arr[i*2],arr[i*2+1]);
		}
		method.setRequestBody(param);
		
		int statusCode = 0;
		try 
		{
			statusCode = client.executeMethod(method);
			System.out.println(statusCode);
			
		} 
		catch (HttpException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return statusCode;
	}
	
	public static void sendTicketInfoRequest(String url, String userid, String ticketid)
	{
		String[] arr = new String[4];
		arr[0] = "userid";
		arr[1] = userid;
		arr[2] = "ticketid";
		arr[3] = ticketid;
		
		sendPostRequest(url,arr);
	}

}
