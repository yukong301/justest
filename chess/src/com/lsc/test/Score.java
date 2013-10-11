package com.lsc.test;

public enum Score {

	A("a"),
	B("b"),
	C("c");
	private  String value;
	Score(String value){
		this.value = value;
	}
	public String value(){
		return this.value;
		
	}

	public static void main(String[] args) {
		System.out.println(Score.A);
	}
}
