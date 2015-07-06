package com.TK.frioj.daoImpl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.TK.frioj.dao.ArticleDao;
import com.TK.frioj.entities.Article;
import com.TK.frioj.enums.Articles;
import com.TK.frioj.rowMappers.ArticleRowMapper;

@Repository
@Transactional
public class MysqlArticleDao implements ArticleDao {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MysqlArticleDao.class);

	private NamedParameterJdbcOperations jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	private ArticleRowMapper articleRowMapper;

	@Override
	public void addArticle(Article article) {
		String sql = "INSERT INTO `Articles`(`Name`, `Note`, `Content`, `Author`, `Status`, `Pre`) "
				+ "VALUES ( :name , :note , :content , :author, :status , :pre )";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("name", article.getName())
				.addValue("note", article.getNote())
				.addValue("content", article.getContent())
				.addValue("author", article.getAuthor())
				.addValue("status", article.getStatus().getNum())
				.addValue("pre", article.getPre());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(sql, (SqlParameterSource) params, keyHolder,
				new String[] { "ArticleId" });
		article.setArticleId(keyHolder.getKey().intValue());
	}

	@Override
	public Article getArticle(int articleId) {
		String sql = "SELECT `ArticleId`, `Name`, `Note`, `Content`, `Status`, `Author`, `Date`, `Pre` FROM `Articles` WHERE `ArticleId` = :articleId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"articleId", articleId);
		return jdbcTemplate.queryForObject(sql, params, articleRowMapper);
	}

	@Override
	@PostFilter("filterObject.status.num == 2 || (hasAnyRole('Admin,Teacher') && filterObject.status.num !=3)")
	public List<Article> getAllArticles() {
		String sql = "SELECT `ArticleId`, `Name`, `Note`, `Content`, `Status`, `Author`, `Date`, `Pre` FROM `Articles` ORDER BY `Date` DESC";
		MapSqlParameterSource params = new MapSqlParameterSource();

		return jdbcTemplate.query(sql, params, articleRowMapper);
	}

	@Override
	public List<Article> getAllTutorials() {
		String sql = "SELECT `ArticleId`, `Name`, `Note`, `Content`, `Status`, `Author`, `Date`, `Pre` FROM `Articles` where `Status` = :tutorialNum ORDER BY `Date` DESC";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"tutorialNum", Articles.Tutorial.getNum());
		return jdbcTemplate.query(sql, params, articleRowMapper);
	}

	@Override
	public void updateArticle(Article article) {
		String sql = "UPDATE `Articles` SET `Name`= :name ,`Note`= :note ,`Content`= :content ,"
				+ "`Status`= :status ,`Author`= :author ,`Pre` = :pre  WHERE `ArticleId` = :articleId";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("articleId", article.getArticleId())
				.addValue("name", article.getName())
				.addValue("note", article.getNote())
				.addValue("content", article.getContent())
				.addValue("status", article.getStatus().getNum())
				.addValue("author", article.getAuthor())
				.addValue("pre", article.getPre());
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void deleteArticle(int articleId) {
		String sql = "DELETE FROM `Articles` WHERE `ArticleId` = :articleId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"articleId", articleId);
		jdbcTemplate.update(sql, params);

	}

}
