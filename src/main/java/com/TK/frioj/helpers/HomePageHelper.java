package com.TK.frioj.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HomePageHelper {

	private static String homePageContent = "";
	private static long length = 0;
	
	
	public static synchronized String getHomePageContent(String homePagePath){
		
		File homePage = new File(homePagePath);
		if(homePage.length() == length)return homePageContent;
		length = homePage.length();
		
		try {
			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader input = new BufferedReader(new FileReader(homePage));
			while((line = input.readLine())!=null){
				sb.append(line+"\n");
				
			}
			input.close();
			homePageContent = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return homePageContent;
	}
}
