package com.TK.frioj.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoredSettings {
	
	private boolean juniorPlusEnabled;
	private boolean contestModeEnabled;
	private int maxProblemPerPageCount;
	private int maxSubsPerPageCount;
	private int maxArticlesPerPageCount;
	private int maxMessagesShoutBoxCount;
	private String adminMailbox;
	
	private double cMultiplier;
	private double cppMultiplier;
	private double javaMultiplier;
	
	private String friojFilesLocation;
	private String friojChrootLocation;
	
	private int standardMaxInputFileSize ;
	private int standardTimeLimit ;
	private int standardMemoryLimit ;
	
	private int maxOutputFileSize;
	private int maxCompileTime;
	private int maxCompileVirtualMemory;
	private int maxTotalSubmissionRuntime;
	
	private int memoryLimitC;
	private int memoryLimitCpp;
	private int memoryLimitJava;
	private int stackMemoryLimitJava;
	
	private int maxSubmissionQueueCapacity;
	
	private int penalization;
	
	
	public boolean isJuniorPlusEnabled() {
		return juniorPlusEnabled;
	}
	
	public StoredSettings(){
		//Default
		
		cMultiplier = 1;
		cppMultiplier = 1;
		javaMultiplier = 1;
		
		maxCompileTime = 600;
		maxCompileVirtualMemory = 2048576;
		maxTotalSubmissionRuntime = 60;
		maxOutputFileSize = 100;
		
		standardMemoryLimit = 1024;
		standardMaxInputFileSize = 65535;
		standardTimeLimit = 3000;
		
		memoryLimitC = 1000;
		memoryLimitCpp = 1000;
		memoryLimitJava = 3000;
		stackMemoryLimitJava = 8;
		
		maxSubmissionQueueCapacity = 5;
		penalization = 20;
		
		friojFilesLocation = "/opt/frioj/friojFiles/";
		friojChrootLocation = "/opt/frioj/friojChroot/trusty/";
		
		juniorPlusEnabled = false;
		contestModeEnabled = false;
		maxMessagesShoutBoxCount = 40;
		maxProblemPerPageCount = 30;
		maxSubsPerPageCount = 20;
		maxArticlesPerPageCount = 10;
		adminMailbox="unknown@unknown.sth";
	}
	
	
	
	
	public int getPenalization() {
		return penalization;
	}

	public void setPenalization(int penalization) {
		this.penalization = penalization;
	}

	public int getStackMemoryLimitJava() {
		return stackMemoryLimitJava;
	}

	public void setStackMemoryLimitJava(int stackMemoryLimitJava) {
		this.stackMemoryLimitJava = stackMemoryLimitJava;
	}

	public int getMaxSubmissionQueueCapacity() {
		return maxSubmissionQueueCapacity;
	}

	public void setMaxSubmissionQueueCapacity(int maxSubmissionQueueCapacity) {
		this.maxSubmissionQueueCapacity = maxSubmissionQueueCapacity;
	}

	public int getMemoryLimitC() {
		return memoryLimitC;
	}

	public void setMemoryLimitC(int memoryLimitC) {
		this.memoryLimitC = memoryLimitC;
	}

	public int getMemoryLimitCpp() {
		return memoryLimitCpp;
	}

	public void setMemoryLimitCpp(int memoryLimitCpp) {
		this.memoryLimitCpp = memoryLimitCpp;
	}

	public int getMemoryLimitJava() {
		return memoryLimitJava;
	}

	public void setMemoryLimitJava(int memoryLimitJava) {
		this.memoryLimitJava = memoryLimitJava;
	}

	public int getMaxOutputFileSize() {
		return maxOutputFileSize;
	}

	public void setMaxOutputFileSize(int maxOutputFileSize) {
		this.maxOutputFileSize = maxOutputFileSize;
	}

	public int getMaxTotalSubmissionRuntime() {
		return maxTotalSubmissionRuntime;
	}

	public void setMaxTotalSubmissionRuntime(int maxTotalSubmissionRuntime) {
		this.maxTotalSubmissionRuntime = maxTotalSubmissionRuntime;
	}

	public int getMaxMessagesShoutBoxCount() {
		return maxMessagesShoutBoxCount;
	}

	public void setMaxMessagesShoutBoxCount(int maxMessagesShoutBoxCount) {
		this.maxMessagesShoutBoxCount = maxMessagesShoutBoxCount;
	}

	public double getcMultiplier() {
		return cMultiplier;
	}

	public void setcMultiplier(double cMultiplier) {
		this.cMultiplier = cMultiplier;
	}

	public double getCppMultiplier() {
		return cppMultiplier;
	}

	public void setCppMultiplier(double cppMultiplier) {
		this.cppMultiplier = cppMultiplier;
	}

	public double getJavaMultiplier() {
		return javaMultiplier;
	}

	public void setJavaMultiplier(double javaMultiplier) {
		this.javaMultiplier = javaMultiplier;
	}


	public String getFriojFilesLocation() {
		return friojFilesLocation;
	}

	public void setFriojFilesLocation(String friojFilesLocation) {
		this.friojFilesLocation = friojFilesLocation;
	}

	public String getFriojChrootLocation() {
		return friojChrootLocation;
	}

	public void setFriojChrootLocation(String friojChrootLocation) {
		this.friojChrootLocation = friojChrootLocation;
	}

	public String getRunnersLocation() {
		return friojFilesLocation+"runners/";
	}

	public String getInLocation() {
		return friojFilesLocation+"in/";
	}


	public String getOutLocation() {
		return friojFilesLocation+"out/";
	}

	public String getScriptsLocation() {
		return friojFilesLocation+"scripts/";
	}

	public String getSourceCodeLocation() {
		return friojFilesLocation+"sourceCode/";
	}

	public int getStandardMaxInputFileSize() {
		return standardMaxInputFileSize;
	}

	public void setStandardMaxInputFileSize(int standardMaxInputFileSize) {
		this.standardMaxInputFileSize = standardMaxInputFileSize;
	}

	public int getStandardTimeLimit() {
		return standardTimeLimit;
	}

	public void setStandardTimeLimit(int standardTimeLimit) {
		this.standardTimeLimit = standardTimeLimit;
	}

	public int getStandardMemoryLimit() {
		return standardMemoryLimit;
	}

	public void setStandardMemoryLimit(int standardMemoryLimit) {
		this.standardMemoryLimit = standardMemoryLimit;
	}

	public void setJuniorPlusEnabled(boolean juniorPlusEnabled) {
		this.juniorPlusEnabled = juniorPlusEnabled;
	}

	
	
	public boolean isContestModeEnabled() {
		return contestModeEnabled;
	}

	public void setContestModeEnabled(boolean contestModeEnabled) {
		this.contestModeEnabled = contestModeEnabled;
	}

	public int getMaxProblemPerPageCount() {
		return maxProblemPerPageCount;
	}
	public void setMaxProblemPerPageCount(int maxProblemPerPageCount) {
		this.maxProblemPerPageCount = maxProblemPerPageCount;
	}
	
	
	
	public int getMaxSubsPerPageCount() {
		return maxSubsPerPageCount;
	}

	public void setMaxSubsPerPageCount(int maxSubsPerPageCount) {
		this.maxSubsPerPageCount = maxSubsPerPageCount;
	}

	public String getAdminMailbox() {
		return adminMailbox;
	}
	public void setAdminMailbox(String adminMailbox) {
		this.adminMailbox = adminMailbox;
	}

	public int getMaxArticlesPerPageCount() {
		return maxArticlesPerPageCount;
	}

	public void setMaxArticlesPerPageCount(int maxArticlesPerPageCount) {
		this.maxArticlesPerPageCount = maxArticlesPerPageCount;
	}
	
	public String getChrootMainFolderLocation(){
		return getFriojChrootLocation()+"main/";
	}

	public int getMaxCompileTime() {
		return maxCompileTime;
	}

	public void setMaxCompileTime(int maxCompileTime) {
		this.maxCompileTime = maxCompileTime;
	}

	public int getMaxCompileVirtualMemory() {
		return maxCompileVirtualMemory;
	}

	public void setMaxCompileVirtualMemory(int maxCompileVirtualMemory) {
		this.maxCompileVirtualMemory = maxCompileVirtualMemory;
	}

	
	
	
	
}
