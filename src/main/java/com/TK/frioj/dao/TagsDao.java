package com.TK.frioj.dao;

import java.util.List;

import com.TK.frioj.entities.HashTag;

public interface TagsDao {
	
	List<HashTag> getAllHashTags();
	
	List<HashTag> problemTags(int problemId);
	
	void tagProblem(int problemId, int tagId, int setterId);
	
	void untagProblem(int problemId, int tagId);
	
	void addTag(String tagName);
	
	void removeTag(int tagId);
	
	void updateTagName(int tagId, String tagName);
	
	String getTagName(int tagId);

	boolean existTag(int tagId);
	
}
