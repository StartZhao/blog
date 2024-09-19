package com.exer.e002;

import java.io.*;
import java.util.*;
/**
 * 2023 矩形总面积
 * @author 86153
 *
 */
public class Main {
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		new Main().test();
	}
	void test() throws Exception{
		String[] s = br.readLine().split(" ");
		int x1 = Integer.valueOf(s[0]);
		int y1 = Integer.valueOf(s[1]);
		int x2 = Integer.valueOf(s[2]);
		int y2 = Integer.valueOf(s[3]);
		int x3 = Integer.valueOf(s[4]);
		int y3 = Integer.valueOf(s[5]);
		int x4 = Integer.valueOf(s[6]);
		int y4 = Integer.valueOf(s[7]);
		long s1 = (long)(x2 - x1) * (long)(y2 - y1);
		long s2 = (long)(x4 - x3) * (long)(y4 - y3);
		int x_left = Math.max(x1, x3);
		int y_left = Math.max(y1, y3);
		int x_right = Math.min(x2, x4);
		int y_right = Math.min(y2, y4);
		int length = x_right - x_left;
		int width = y_right - y_left;
		long s3 = 0;
		if (length > 0 && width > 0) s3 = (long)length * (long)width;
		pw.println(s1 + s2 - s3);
		pw.flush();
	
	}
	
	
	
	

}
