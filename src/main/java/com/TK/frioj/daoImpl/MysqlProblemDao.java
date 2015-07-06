package com.TK.frioj.daoImpl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.entities.Problem;
import com.TK.frioj.enums.ProblemStatus;
import com.TK.frioj.rowMappers.ProblemIdAndNameRowMapper;
import com.TK.frioj.rowMappers.ProblemRowMapper;

@Repository
@Transactional
public class MysqlProblemDao implements ProblemDao{
	
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MysqlProblemDao.class);
	private NamedParameterJdbcOperations jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	private ProblemRowMapper problemRowMapper;
	
	@Autowired
	private ProblemIdAndNameRowMapper problemIdAndNameRowMapper ;
	
	public void addProblem(Problem problem){
		String sql = "INSERT INTO `Problem`(`SetterId`, `Name`, `Note`, `Text`, `Input`, `Output`, "
				+ "`Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize`, `Pdf`) "
				+ "VALUES (:SetterId, :Name, :Note, :Text, :Input, :Output, :Status,"
				+ ":TimeLimit, :MemoryLimit, :MaxInputFileSize, :Pdf)";
			
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("SetterId",problem.getSetterID())
		.addValue("Name", problem.getName())
		.addValue("Note", problem.getNote())
		.addValue("Text", problem.getText())
		.addValue("Input", problem.getInput())
		.addValue("Output", problem.getOutput())
		.addValue("Status", problem.getStatus().getNumRepresentation())
		.addValue("TimeLimit", problem.getTimeLimit())
		.addValue("MemoryLimit", problem.getMemoryLimit())
		.addValue("MaxInputFileSize", problem.getMaxInputFileSize())
		.addValue("Pdf", problem.getPdf());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(sql, (SqlParameterSource) params, keyHolder, new String[]{"ProblemId"});
		problem.setProblemId( keyHolder.getKey().intValue());
		
	}

	public Problem getProblem(int problemId) {
		
		SqlParameterSource params = new MapSqlParameterSource("id", problemId);
		String sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` ,`Pdf` FROM `Problem` WHERE ProblemId = :id";
		return jdbcTemplate.queryForObject(sql, params, problemRowMapper);
	}
	

	
	public void updateAddPdf(int id, byte[] pdf){
		String sql = "update `Problem` set Pdf = :pdf where ProblemId = :id";
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("id",id)
		.addValue("pdf", pdf);
		
		jdbcTemplate.update(sql, (SqlParameterSource) params);
		
	}

	public void updateProblem(Problem problem) {
		String sql = "update `Problem` set"
				+ " Pdf = :Pdf,"
				+ " SetterId = :SetterId,"
				+ " Name =:Name  ,"
				+ " Note =:Note  ,"
				+ " Text =:Text  ,"
				+ " Input =:Input  ,"
				+ " Output =:Output  ,"
				+ " Status =:Status ,"
				+ " TimeLimit =:TimeLimit ,"
				+ " MemoryLimit =:MemoryLimit ,"
				+ " MaxInputFileSize =:MaxInputFileSize "
				+ " where ProblemId = :id";
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("id",problem.getProblemId())
		.addValue("SetterId",problem.getSetterID())
		.addValue("Name", problem.getName())
		.addValue("Note", problem.getNote())
		.addValue("Text", problem.getText())
		.addValue("Input", problem.getInput())
		.addValue("Output", problem.getOutput())
		.addValue("Status", problem.getStatus().getNumRepresentation())
		.addValue("TimeLimit", problem.getTimeLimit())
		.addValue("MemoryLimit", problem.getMemoryLimit())
		.addValue("MaxInputFileSize", problem.getMaxInputFileSize())
		.addValue("Pdf", problem.getPdf());
		
		jdbcTemplate.update(sql, (SqlParameterSource) params);
	}

	public void updateProblemWithoutPdf(Problem problem) {
		String sql = "update `Problem` set"
				+ " SetterId = :SetterId,"
				+ " Name =:Name  ,"
				+ " Note =:Note  ,"
				+ " Text =:Text  ,"
				+ " Input =:Input  ,"
				+ " Output =:Output  ,"
				+ " Status =:Status ,"
				+ " TimeLimit =:TimeLimit ,"
				+ " MemoryLimit =:MemoryLimit ,"
				+ " MaxInputFileSize =:MaxInputFileSize "
				+ " where ProblemId = :id";
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("id",problem.getProblemId())
		.addValue("SetterId",problem.getSetterID())
		.addValue("Name", problem.getName())
		.addValue("Note", problem.getNote())
		.addValue("Text", problem.getText())
		.addValue("Input", problem.getInput())
		.addValue("Output", problem.getOutput())
		.addValue("Status", problem.getStatus().getNumRepresentation())
		.addValue("TimeLimit", problem.getTimeLimit())
		.addValue("MemoryLimit", problem.getMemoryLimit())
		.addValue("MaxInputFileSize", problem.getMaxInputFileSize());
		
		jdbcTemplate.update(sql, (SqlParameterSource) params);
		
	}

	@Override
	public List<Problem> getAllVisibleProblems(int tagId) {
		
		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("status", ProblemStatus.visible.getNumRepresentation());
		
		if(tagId<=0){
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` where `Status` = :status";
		}else{
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` JOIN `ProblemTags` USING(`ProblemId`) where `HashTagId` = :tagId AND `Status` = :status";
			params.addValue("tagId", tagId);
		}
		
		return jdbcTemplate.query(sql,params, problemRowMapper);
		
	}

	@Override
	public List<String[]> getAllProblemNamesAndIds() {
		String sql = "SELECT `ProblemId`,  `Name`  FROM `Problem` WHERE `Status` = '1'";
		MapSqlParameterSource params = new MapSqlParameterSource();
		return jdbcTemplate.query(sql, params, problemIdAndNameRowMapper);
	}

	@Override
	public List<Problem> getAllProblems(int tagId) {
		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if(tagId<=0){
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem`";
		}else{
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` JOIN `ProblemTags` USING(`ProblemId`) where `HashTagId` = :tagId ";
			params.addValue("tagId", tagId);
		}
		
		
		return jdbcTemplate.query(sql,params, problemRowMapper);
	}

	@Override
	public boolean existProblem(int problemId) {
		String sql = "SELECT COUNT(*) FROM `Problem` WHERE `ProblemId` = :problemId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("problemId", problemId);
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class).intValue()==1;
	}

	@Override
	public List<Problem> getProblems(int tagId, int start, int count) {
		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("start", start)
		.addValue("count", count);
		
		if(tagId<=0){
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` LIMIT :start, :count";
		}else{
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` JOIN `ProblemTags` USING(`ProblemId`) where `HashTagId` = :tagId LIMIT :start, :count";
			params.addValue("tagId", tagId);
		}
		
		
		return jdbcTemplate.query(sql,params, problemRowMapper);
	}

	@Override
	public List<Problem> getVisibleProblems(int tagId, int start, int count) {
		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("status", ProblemStatus.visible.getNumRepresentation())
		.addValue("start", start)
		.addValue("count", count);
		
		if(tagId<=0){
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` where `Status` = :status LIMIT :start, :count";
		}else{
			sql = "SELECT `ProblemId`, `SetterId`, `Name`, `Note`, `Text` , `Input`, `Output`, `Status`, `TimeLimit`, `MemoryLimit`, `MaxInputFileSize` FROM `Problem` JOIN `ProblemTags` USING(`ProblemId`) where `HashTagId` = :tagId AND `Status` = :status LIMIT :start, :count";
			params.addValue("tagId", tagId);
		}
		
		return jdbcTemplate.query(sql,params, problemRowMapper);
	}

	@Override
	public int getProblemCount(int tagId) {
		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if(tagId<=0){
			sql = "SELECT COUNT(*) FROM `Problem`";
		}else{
			sql = "SELECT COUNT(*) FROM `Problem` JOIN `ProblemTags` USING(`ProblemId`) where `HashTagId` = :tagId ";
			params.addValue("tagId", tagId);
		}
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}
	
	
	@Override
	public int getVisibleProblemCount(int tagId) {

		String sql;
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("status", ProblemStatus.visible.getNumRepresentation());
		
		if(tagId<=0){
			sql = "SELECT COUNT(*) FROM `Problem` where `Status` = :status";
		}else{
			sql = "SELECT COUNT(*) FROM `Problem` JOIN `ProblemTags` USING(`ProblemId`) where `HashTagId` = :tagId AND `Status` = :status";
			params.addValue("tagId", tagId);
		}
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public int getMaxInputFileSizeOfProblem(int problemId) {
		String sql = "SELECT `MaxInputFileSize` FROM `Problem` WHERE `ProblemId` = :problemId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("problemId", problemId);
		try{
			return jdbcTemplate.queryForObject(sql, params, Integer.class).intValue();
		}catch(EmptyResultDataAccessException ex){
			return 0;
		}
	}
	
	
}
