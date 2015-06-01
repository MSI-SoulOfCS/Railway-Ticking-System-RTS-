package com.redis.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.redis.util.RelationConverter;

public class Test1 {

	@Test
	public void test() 
	{
		String[] arr = RelationConverter.seatGenerator("hard", 150);
		
		for(String str:arr)
		{
			System.out.println(str);
		}
	}

}
