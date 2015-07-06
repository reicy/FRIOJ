package com.TK.frioj.helpers;

import java.util.ArrayList;

import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.SubmissionStatus;

public class StringHelper {

	/**
	 * returns items of list delimited by ,
	 * if list is empty returns -1
	 * @param list
	 * @return
	 */
	public static String addCommaDelim(ArrayList<Integer> list){
		if(list.size()==0)return "-1";
		StringBuilder sb = new StringBuilder();
		
		for (Integer val : list) {
			sb.append(val);
			sb.append(",");
		}
		
		
		
		return sb.toString().substring(0,sb.length()-1);
	}
	

	public static boolean isWaDueToDifferentSize(Submission submission){
		return(submission.getStatus()==SubmissionStatus.WA && submission.getErr().matches("Different size of output files. Yours: [0-9]+ lines vs correct: [0-9]+ lines."));
	}
	
	public static String encodeDifferentSize(long yours, long correct){
		return "Different size of output files. Yours: "+yours+" lines vs correct: "+correct+" lines.";
	}
}
