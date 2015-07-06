package com.TK.frioj.enums;

public enum ProblemStatus {

	visible(1), hidden(2);
	
	private int numRepresentation;

	ProblemStatus(int numRepresentation) {
		this.numRepresentation = numRepresentation;
	};

	public int getNumRepresentation() {
		return this.numRepresentation;
	}
	
	public static ProblemStatus getProblemStatus(int num){
		if(num==1)return visible;
		return hidden;
	}
}
