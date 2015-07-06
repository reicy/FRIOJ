package com.TK.frioj.entities;

import java.util.LinkedList;

public class UserStatistics {
	
	private int wa;
	private int rte;
	private int ac;
	private int ce;
	private int tle;
	private int crv;
	private int ue;
	private int ne;
	private double aCChance;
	private double averageACTime;
	private LinkedList<Integer> solved;
	private LinkedList<Integer> triedNotSolved;
	
	
	
	public UserStatistics() {
		super();
		this.wa = 0;
		this.rte = 0;
		this.ac = 0;
		this.ce = 0;
		this.tle = 0;
		this.crv = 0;
		this.ue = 0;
		this.ne = 0;
		this.aCChance = 0;
		this.averageACTime = 0;
		this.solved = new LinkedList<Integer>();
		this.triedNotSolved = new LinkedList<Integer>();
	}

	public int getCount(){
		return wa+rte+ac+ce+tle+crv+ue+ne;
	}

	public int getWa() {
		return wa;
	}

	public void setWa(int wa) {
		this.wa = wa;
	}

	public int getRte() {
		return rte;
	}

	public void setRte(int rte) {
		this.rte = rte;
	}

	public int getAc() {
		return ac;
	}

	public void setAc(int ac) {
		this.ac = ac;
	}

	public int getCe() {
		return ce;
	}

	public void setCe(int ce) {
		this.ce = ce;
	}

	public int getTle() {
		return tle;
	}

	public void setTle(int tle) {
		this.tle = tle;
	}

	public int getCrv() {
		return crv;
	}

	public void setCrv(int crv) {
		this.crv = crv;
	}

	public int getUe() {
		return ue;
	}

	public void setUe(int ue) {
		this.ue = ue;
	}

	public int getNe() {
		return ne;
	}

	public void setNe(int ne) {
		this.ne = ne;
	}

	public double getACChance() {
		return aCChance;
	}

	public void setACChance(double aCChance) {
		this.aCChance = aCChance;
	}

	public double getAverageACTime() {
		return averageACTime;
	}

	public void setAverageACTime(double averageACTime) {
		this.averageACTime = averageACTime;
	}

	public LinkedList<Integer> getSolved() {
		return solved;
	}

	public void setSolved(LinkedList<Integer> solved) {
		this.solved = solved;
	}
	
	public void addSolved(Integer problemId) {
		this.solved.addLast(problemId);
	}

	public LinkedList<Integer> getTriedNotSolved() {
		return triedNotSolved;
	}

	public void setTriedNotSolved(LinkedList<Integer> triedNotSolved) {
		this.triedNotSolved = triedNotSolved;
	}
	
	
	public void addTriedNotSolved(Integer problemId) {
		this.triedNotSolved.addLast(problemId);
	}
	
}
