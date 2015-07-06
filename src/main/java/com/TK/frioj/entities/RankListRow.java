package com.TK.frioj.entities;

public class RankListRow {

	private int rank;
	private String login;
	private int numberOfACSubmissions;
	private int numberOfAllSubmissions;

	public RankListRow(int rank, String login, int numberOfACSubmissions,
			int numberOfAllSubmissions) {
		super();
		this.rank = rank;
		this.login = login;
		this.numberOfACSubmissions = numberOfACSubmissions;
		this.numberOfAllSubmissions = numberOfAllSubmissions;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getNumberOfACSubmissions() {
		return numberOfACSubmissions;
	}

	public void setNumberOfACSubmissions(int numberOfACSubmissions) {
		this.numberOfACSubmissions = numberOfACSubmissions;
	}

	public int getNumberOfAllSubmissions() {
		return numberOfAllSubmissions;
	}

	public void setNumberOfAllSubmissions(int numberOfAllSubmissions) {
		this.numberOfAllSubmissions = numberOfAllSubmissions;
	}

}
