package com.exer.e001;

import java.util.*;

/**
 * 2023 分割数组
 * @author 86153
 *
 */
public class Main {
	
	static int MOD = 1000000007;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main().test();
	}
	void test() {
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		long ans = 0;
		for(int i = 0; i < n; i++) {
			int m = input.nextInt(), odd = 0, even = 0;
			for(int j = 0; j < m; j++) {
				if (input.nextInt() % 2 == 0) even++;
				else odd++;
			}
			if (odd % 2 == 0) ans = fastPow(2, even) * fastPow(2, odd == 0 ? 0 : odd - 1) % MOD;
			System.out.println(ans);
			ans = 0;
		}
		
		
	}
	long fastPow(long a, long b) {
		if (b == -1) return 1;
		long res = 1;
		while(b > 0) {
			if((b & 1) == 1) res = res *a % MOD;
			a = a * a % MOD;
			b >>= 1;
		}
		return res % MOD;
	}

}
