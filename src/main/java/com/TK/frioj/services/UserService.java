package com.TK.frioj.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.RankListRow;
import com.TK.frioj.helpers.AuthorizationHelper;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired 
	private SubmissionDao submissionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<RankListRow> getTopNUsers(int topN){
		LinkedList<RankListRow> list = new LinkedList<RankListRow>();
		List<String[]> topNList = submissionDao.getTopNUserACSubmissionsCountDisticntOrdered(topN);
		Map<String, Integer> mapLoginNumberOfSolutions = submissionDao.getAllUserSubmissionsCount();
		RankListRow row ;
		int i=1;;
		
		for (String[] pair : topNList) {
			row = new RankListRow(i++, pair[0], Integer.parseInt(pair[1]), mapLoginNumberOfSolutions.get(pair[0]));
			list.addLast(row);
		}
		
		return list;
	}

	public String updatePassword(String currentPass, String newPass1,	String newPass2) {
		if(!newPass1.equals(newPass2)){
			return "new password and retyped new password do not match";
		}
		
		String password = userDao.getPassword(AuthorizationHelper.getCurrentUserName());
		
		
		if(!bCryptPasswordEncoder.matches(currentPass, password)){
			return "incorrect password";
		}
		
		if(!newPass1.matches("^[a-zA-Z0-9]{6,20}$")){
			return "password must be alphanumeric, length 6-20";
		}
		
		userDao.updatePassword(AuthorizationHelper.getCurrentUserName(), bCryptPasswordEncoder.encode(newPass1));
		
		return "password successfully changed";
	}
	
	
}

	
	