package com.TK.frioj.entities;

import org.joda.time.DateTime;

import com.TK.frioj.enums.Articles;

public class Article {

	private int articleId;
	private String name;
	private String note;
	private String content;
	private String author;
	private DateTime date;
	private Articles status;
	private int pre;

	public Article() {
		this.articleId = -1;
		this.name = "";
		this.note = "";
		this.content = "";
		this.author = "";
		this.date = DateTime.now();
		this.status = Articles.Hidden;
		this.pre = 0;

	}

	public Article(int articleId, String name, String note, String content,
			String author, DateTime date, Articles status, int pre) {
		super();
		this.articleId = articleId;
		this.name = name;
		this.note = note;
		this.content = content;
		this.author = author;
		this.date = date;
		this.status = status;
		this.pre = pre;
	}

	public int getPre() {
		return pre;
	}

	public void setPre(int pre) {
		this.pre = pre;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public Articles getStatus() {
		return status;
	}

	public void setStatus(Articles status) {
		this.status = status;
	}

}
