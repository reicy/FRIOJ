package com.TK.frioj.dao;

import java.util.List;

import com.TK.frioj.entities.Article;

public interface ArticleDao {

	void addArticle(Article article);
	
	Article getArticle(int articleId);
	
	List<Article> getAllArticles();
	
	List<Article> getAllTutorials();
	
	void updateArticle(Article article);
	
	void deleteArticle(int articleId);
	
}
