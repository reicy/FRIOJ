package com.TK.frioj.entities;

import org.springframework.stereotype.Component;

@Component
public class ProblemStatistics {
	private int wa;
	private int rte;
	private int ac;
	private int ce;
	private int tle;
	private int crv;
	private int ue;
	private int ne;

	public ProblemStatistics() {
		super();
		this.wa = 0;
		this.rte = 0;
		this.ac = 0;
		this.ce = 0;
		this.tle = 0;
		this.crv = 0;
		this.ue = 0;
		this.ne = 0;
	}

	public int getCount() {

		return wa + rte + ac + ce + tle + crv + ue + ne;

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

}
