package com.TK.frioj.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.TK.frioj.entities.Session;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.SubmissionStatus;

public class SessionHelper {

	/**
	 * from list of users, problems and submissions creates table and sorts it by the count of solved problems
	 * @param subs
	 * @param members
	 * @param problems
	 * @param penalization
	 * @param session
	 * @param sessionMembersNames
	 * @return
	 */
	public static int[][][] submissionsToAcToAllTable(ArrayList<Submission> subs, ArrayList<Integer> members, ArrayList<Integer> problems, int penalization, Session session, List<String> sessionMembersNames){
		int arr[][][] = new int [members.size()][problems.size()][3];
		int totalProblemSolvedCountPerUser[] = new int [members.size()];
		int totalTimeAndPenalizationPerUser[] = new int [members.size()];
		Date start = session.getDstart();
		
		for (int i = 0; i < arr.length; i++) {
			totalProblemSolvedCountPerUser[i]=0;
			totalTimeAndPenalizationPerUser[i]=0;
			for (int j = 0; j < arr[0].length; j++) {
				for (int k = 0; k < arr[0][0].length; k++) {
					arr[i][j][k]=0;
				}
			}
		}
		
		for (Submission sub : subs) {
			arr[members.indexOf(sub.getUserId())][problems.indexOf(sub.getProblem().getProblemId())][0]++;
			if(sub.getStatus().equals(SubmissionStatus.AC)){
				arr[members.indexOf(sub.getUserId())][problems.indexOf(sub.getProblem().getProblemId())][1]++;
				arr[members.indexOf(sub.getUserId())][problems.indexOf(sub.getProblem().getProblemId())][2]=(int) (sub.getDate().toDate().getTime()-start.getTime())/(1000*60);
				
			}
			
		}
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if(arr[i][j][1]>0){
					totalProblemSolvedCountPerUser[i]++;
					totalTimeAndPenalizationPerUser[i]+=(arr[i][j][0]-arr[i][j][1])*penalization+arr[i][j][2];
				}
				
			}
		}
		
		int [][] tempArr;
		int tempMember;
		int temp;
		int first;
		String tempName;
		
		for (int i = arr.length-1 ; i>0 ; i--) {
			
			first = 0;
			for (int j = 1; j < i+1; j++) {
				if(totalProblemSolvedCountPerUser[first]>totalProblemSolvedCountPerUser[j] ||
						(totalProblemSolvedCountPerUser[first]==totalProblemSolvedCountPerUser[j] &&
						totalTimeAndPenalizationPerUser[first]<totalTimeAndPenalizationPerUser[j])){
					first = j;
				}
			}
			
			tempArr = arr[first];
			arr[first]=arr[i];
			arr[i]=tempArr;
			
			tempName = sessionMembersNames.get(first);
			sessionMembersNames.set(first, sessionMembersNames.get(i));
			sessionMembersNames.set(i, tempName);
			
			tempMember = members.get(first);
			members.set(first, members.get(i));
			members.set(i, tempMember);
			
			temp = totalProblemSolvedCountPerUser[first];
			totalProblemSolvedCountPerUser[first] = totalProblemSolvedCountPerUser[i];
			totalProblemSolvedCountPerUser[i] = temp;
			
			temp = totalTimeAndPenalizationPerUser[first];
			totalTimeAndPenalizationPerUser[first] = totalTimeAndPenalizationPerUser[i];
			totalTimeAndPenalizationPerUser[i] = temp;
		}
		
		
		return arr;
	}
	
}
