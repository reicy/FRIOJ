package com.TK.frioj.enums;

public enum Articles {
	
	
	Hidden(1), Article(2), Tutorial(3);
	
	private int num;
	
	private Articles(int num){
		this.num = num;
	}

	public int getNum() {
		return num;
	}
	
	public static Articles getArticleType(int num){
		switch (num) {
		case 1:
			return Hidden;
		
		case 2:
			return Article;
		
		case 3:
			return Tutorial;
		}
		
		
		return Hidden;
	}
}
