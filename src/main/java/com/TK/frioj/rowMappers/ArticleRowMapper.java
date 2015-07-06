package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.TK.frioj.entities.Article;
import com.TK.frioj.enums.Articles;

@Component
public class ArticleRowMapper implements RowMapper<Article>{
	
	@Override
	public Article mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Article article = new Article();
		
		article.setArticleId(resultSet.getInt("ArticleId"));
		article.setName(resultSet.getString("Name"));
		article.setNote(resultSet.getString("Note"));
		article.setContent(resultSet.getString("Content"));
		article.setAuthor(resultSet.getString("Author"));
		article.setDate(new DateTime(Timestamp.valueOf(resultSet.getString("Date"))));
		article.setStatus(Articles.getArticleType(resultSet.getInt("status")));
		article.setPre(resultSet.getInt("Pre"));	
		
		return article;
	}
}	