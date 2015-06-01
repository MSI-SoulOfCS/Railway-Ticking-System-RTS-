package com.redis.test;

import java.util.Random;

public class TestRandom 
{
	public static void main(String[] args) 
	{
		
		String[] tickets = {
				"beijing#baotou#201505291023",
				"beijing#tianjin#201505291023"
		};
		
		Random random = new Random();
		
		for(int i=0;i<20;i++)
		{
			System.out.println(tickets[random.nextInt(2)]);
		}
	}
}
