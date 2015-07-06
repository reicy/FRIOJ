package com.TK.frioj.daoImpl;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.TK.frioj.dao.TagsDao;
import com.TK.frioj.entities.HashTag;
import com.TK.frioj.rowMappers.HashTagsRowMapper;
import com.TK.frioj.rowMappers.UniversalStringStringArrayRowMapper;


@Repository
@Transactional
public class MysqlTagsDao implements TagsDao{

	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MysqlSubmissionDao.class);
	private NamedParameterJdbcOperations jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	private HashTagsRowMapper hashTagsRowMapper;
	
	@Autowired
	private UniversalStringStringArrayRowMapper universalStringStringArrayRowMapper;

	@Override
	public List<HashTag> getAllHashTags() {
		String sql = "SELECT `HashTagId`, `HashTagName` FROM `HashTags` ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		try{
			return jdbcTemplate.query(sql, params,hashTagsRowMapper);
		}catch(EmptyResultDataAccessException ex){
			return new LinkedList<HashTag>();
		}
		
	}

	@Override
	public List<HashTag> problemTags(int problemId) {
		String sql = "SELECT `HashTagId`,`HashTagName` FROM `ProblemTags` JOIN `HashTags` USING(`HashTagId`) WHERE `ProblemId` = :problemId ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("problemId", problemId);
		
		try{
			return jdbcTemplate.query(sql, params, hashTagsRowMapper);
		}catch(EmptyResultDataAccessException ex){
			return new LinkedList<HashTag>();
		}
	}

	@Override
	@PreAuthorize("hasAnyRole('Senior,Teacher,Admin')")
	public void tagProblem(int problemId, int tagId, int setterId) {
		String sql = "INSERT INTO `ProblemTags`(`HashTagId`, `ProblemId`, `Setter`) "
				+ "VALUES ( :tagId , :problemId , :setterId)";
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("problemId", problemId)
		.addValue("tagId", tagId)
		.addValue("setterId", setterId);
		
		try{
			jdbcTemplate.update(sql, params);
		}catch(DuplicateKeyException ex){
			
		}
		
		
		
		
	}

	@Override
	@PreAuthorize("hasAnyRole('Senior,Teacher,Admin')")
	public void untagProblem(int problemId, int tagId) {
		String sql = "DELETE FROM `ProblemTags` WHERE `HashTagId` = :tagId AND `ProblemId` = :problemId ";

		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("problemId", problemId)
		.addValue("tagId", tagId);
		
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void addTag(String tagName) {
		
		String sql = "INSERT INTO `HashTags`(`HashTagName`) VALUES ( :tagName )";
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("tagName", tagName);
		jdbcTemplate.update(sql, params);
		
		
	}

	@Override
	public void removeTag(int tagId) {
		String sql = "DELETE FROM `HashTags` WHERE `HashTagId` = :tagId ";
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("tagId", tagId);
		
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void updateTagName(int tagId, String tagName) {
		String sql = "UPDATE `HashTags` SET `HashTagName`= :tagName WHERE `HashTagId` = :tagId ";
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("tagId", tagId)
		.addValue("tagName", tagName);
		
		jdbcTemplate.update(sql, params);
	
	}

	@Override
	public String getTagName(int tagId) {
		String sql = "SELECT `HashTagName` FROM `HashTags` WHERE `HashTagId` = :tagId ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("tagId", tagId);
		
		try{
			return jdbcTemplate.queryForObject(sql, params,String.class);
		}catch(EmptyResultDataAccessException ex){
			return "";
		}
	}

	@Override
	public boolean existTag(int tagId) {
		String sql = "SELECT COUNT(*) FROM `HashTags` WHERE `HashTagId` = :tagId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("tagId", tagId);
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class).intValue()==1;
	}
}