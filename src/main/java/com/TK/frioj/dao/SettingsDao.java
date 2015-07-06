package com.TK.frioj.dao;

import com.TK.frioj.enums.Languages;

public interface SettingsDao {
	
	boolean isJuniorPlusEnabled();
	
	void setJuniorPlusMode(boolean enabled);
		
	boolean isContestModeEnabled();
	
	void setContestMode(boolean enabled);
	
	int getMaxProblemPerPageCount();
	
	void setMaxProblemPerPageCount(int num);
	
	int getMaxSubmissionsPerPageCount();
	
	void setMaxSubmissionsPerPageCount(int num);
	
	int getMaxArticlesPerPageCount();
	
	void setMaxArticlesPerPageCount(int num);

	String getAdminMailboxAddress();
	
	void setAdminMailboxAddress(String addr);
	
	int getMaxMessagesShoutBoxCount();
	
	void setMaxMessagesShoutBoxCount(int num);

	double getMultiplier(Languages lang);
	
	double getCMultiplier();
	double getCppMultiplier();
	double getJavaMultiplier();
	String getFriojFilesLocation();
	String getFriojChrootLocation();
	String getChrootMainFolderLocation();
	String getRunnersLocation();
	String getInLocation();
	String getOutLocation();
	String getScriptsLocation();
	String getSourceCodeLocation();
	int getStandardMaxInputFileSize();
	int getStandardTimeLimit();
	int getStandardMemoryLimit();
	
	void setCMultiplier(double multiplier);
	void setCppMultiplier(double multiplier);
	void setJavaMultiplier(double multiplier);
	void setFriojFilesLocation(String location);
	void setFriojChrootLocation(String location);
	void setStandardMaxInputFileSize(int standard);
	void setStandardTimeLimit(int standard);
	void setStandardMemoryLimit(int standard);
	
	void resetToDefaults();
	void saveSettings();
	
	int getMaxCompileTime();
	void setMaxCompileTime(int maxTimeInSeconds);
	int getMaxCompileVirtualMemory();
	void setMaxCompileVirtualMemory(int maxVirtualMemoryInKB);

	int getMaxTotalSubmissionRuntime();
	void setMaxTotalSubmissionRuntime(int maxTimeInSeconds);

	int getMaxOutputFileSize();
	void setMaxOutputFileSize(int maxSize);
	
	int getMemoryLimitJava();
	void setMemoryLimitJava(int mb);
	
	int getMemoryLimitC();
	void setMemoryLimitC(int mb);
	
	int getMemoryLimitCpp();
	void setMemoryLimitCpp(int mb);
	
	int getStackMemoryLimitJava();
	void setStackMemoryLimitJava(int mb);

	int getMaxSubmissionQueueCapacity();
	void setMaxSubmissionQueueCapacity(int maxCapacity);
	
	int getPenalization();
	void setPenalization(int min);
	
	
}
