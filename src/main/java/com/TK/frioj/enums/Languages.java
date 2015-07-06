package com.TK.frioj.enums;


public enum Languages {

	C ("C"),
	Java ("Java"), 
	CPP ("CPP");
	
	private String name;
	
	Languages(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
