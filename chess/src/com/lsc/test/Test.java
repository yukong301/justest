package com.lsc.test;

public class Test {
	int c;
	int[] d = new int[10];
	static int a;
	static int[] b = new int[10];

	public static void init() {
		for (int i = 0; i < 10; i++) {
			a = 9;
			b[i] = i + 100;
		}

	}

	public Test() {
		System.out.println("test");
	}

	static {
		init();
		System.out.println("---");
	}
	{
		init2();
		System.out.println(c);
	}

	public void init2() {
		c = 9999;
		for (int i = 0; i < 10; i++) {
			d[i] = i + 2000;
		}
	}

	public static void main(String[] args) {
//		Test test = new Test();
//		Test tets1 = new Test();
		System.out.println(400+500+200+600+1000+500);
	}
}
