package com.TK.frioj.daoImpl;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.entities.StoredSettings;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.main.Settings;

@Repository
public class FileSettingsDao implements SettingsDao {

	private StoredSettings storedSettings;

	public FileSettingsDao() {

		try {

			ObjectMapper mapper = new ObjectMapper();
			storedSettings = mapper.readValue(new File(Settings.SETTINGS_FILE),
					StoredSettings.class);

		} catch (IOException e) {
			storedSettings = new StoredSettings();
			e.printStackTrace();
		}

	}

	@Override
	public boolean isJuniorPlusEnabled() {

		return storedSettings.isJuniorPlusEnabled();
	}

	@Override
	public synchronized void setJuniorPlusMode(boolean enabled) {
		storedSettings.setJuniorPlusEnabled(enabled);
		saveSettings();
	}

	@Override
	public boolean isContestModeEnabled() {
		return storedSettings.isContestModeEnabled();
	}

	@Override
	public void setContestMode(boolean enabled) {
		storedSettings.setContestModeEnabled(enabled);
	}

	@Override
	public int getMaxProblemPerPageCount() {
		return storedSettings.getMaxProblemPerPageCount();

	}

	@Override
	public void setMaxProblemPerPageCount(int num) {
		storedSettings.setMaxProblemPerPageCount(num);
		saveSettings();
	}

	@Override
	public int getMaxSubmissionsPerPageCount() {
		return storedSettings.getMaxSubsPerPageCount();
	}

	@Override
	public void setMaxSubmissionsPerPageCount(int num) {
		storedSettings.setMaxSubsPerPageCount(num);
		saveSettings();
	}

	@Override
	public String getAdminMailboxAddress() {

		return storedSettings.getAdminMailbox();
	}

	@Override
	@PreAuthorize("hasRole('Admin')")
	public void setAdminMailboxAddress(String addr) {
		storedSettings.setAdminMailbox(addr);
		saveSettings();
	}

	@Override
	public void saveSettings() {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(Settings.SETTINGS_FILE), storedSettings);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getMaxArticlesPerPageCount() {

		return storedSettings.getMaxArticlesPerPageCount();
	}

	@Override
	public void setMaxArticlesPerPageCount(int num) {
		storedSettings.setMaxArticlesPerPageCount(num);
		saveSettings();
	}

	@Override
	public double getCMultiplier() {
		return storedSettings.getcMultiplier();
	}

	@Override
	public double getCppMultiplier() {
		return storedSettings.getCppMultiplier();
	}

	@Override
	public double getJavaMultiplier() {
		return storedSettings.getJavaMultiplier();
	}


	@Override
	public String getFriojFilesLocation() {
		return storedSettings.getFriojFilesLocation();
	}

	@Override
	public String getChrootMainFolderLocation() {
		return storedSettings.getChrootMainFolderLocation();
	}

	@Override
	public String getRunnersLocation() {
		return storedSettings.getRunnersLocation();
	}

	@Override
	public String getInLocation() {
		return storedSettings.getInLocation();
	}

	@Override
	public String getOutLocation() {
		return storedSettings.getOutLocation();
	}

	@Override
	public String getScriptsLocation() {
		return storedSettings.getScriptsLocation();
	}

	@Override
	public String getSourceCodeLocation() {
		return storedSettings.getSourceCodeLocation();
	}

	@Override
	public int getStandardMaxInputFileSize() {
		return storedSettings.getStandardMaxInputFileSize();
	}

	@Override
	public int getStandardTimeLimit() {
		return storedSettings.getStandardTimeLimit();
	}

	@Override
	public int getStandardMemoryLimit() {
		return storedSettings.getStandardMemoryLimit();
	}

	@Override
	public void setCMultiplier(double multiplier) {
		storedSettings.setcMultiplier(multiplier);
		saveSettings();
	}

	@Override
	public void setCppMultiplier(double multiplier) {
		storedSettings.setCppMultiplier(multiplier);
		saveSettings();
	}

	@Override
	public void setJavaMultiplier(double multiplier) {
		storedSettings.setJavaMultiplier(multiplier);
		saveSettings();

	}

	@Override
	@PreAuthorize("hasRole('Admin')")
	public void setFriojFilesLocation(String location) {
		storedSettings.setFriojFilesLocation(location);
		saveSettings();

	}

	@Override
	@PreAuthorize("hasRole('Admin')")
	public void setFriojChrootLocation(String location) {
		storedSettings.setFriojChrootLocation(location);
		saveSettings();

	}

	@Override
	public void setStandardMaxInputFileSize(int standard) {
		storedSettings.setStandardMaxInputFileSize(standard);
		saveSettings();

	}

	@Override
	public void setStandardTimeLimit(int standard) {
		storedSettings.setStandardTimeLimit(standard);
		saveSettings();

	}

	@Override
	public void setStandardMemoryLimit(int standard) {
		storedSettings.setStandardMemoryLimit(standard);
		saveSettings();

	}

	@Override
	@PreAuthorize("hasRole('Admin')")
	public void resetToDefaults() {

		this.storedSettings = new StoredSettings();
		saveSettings();

	}

	@Override
	public int getMaxMessagesShoutBoxCount() {
		return storedSettings.getMaxMessagesShoutBoxCount();
	}

	@Override
	public void setMaxMessagesShoutBoxCount(int num) {
		storedSettings.setMaxMessagesShoutBoxCount(num);
		saveSettings();

	}

	@Override
	public double getMultiplier(Languages lang) {
		switch (lang) {
		case Java:
			return getJavaMultiplier();
		case C:
			return getCMultiplier();
		case CPP:
			return getCppMultiplier();

		default:
			return 1;
		}
	}

	@Override
	public String getFriojChrootLocation() {
		return storedSettings.getFriojChrootLocation();
	}

	@Override
	public int getMaxCompileTime() {
		return storedSettings.getMaxCompileTime();
	}

	@Override
	public void setMaxCompileTime(int maxTimeInSeconds) {
		storedSettings.setMaxCompileTime(maxTimeInSeconds);
		saveSettings();
		
	}

	@Override
	public int getMaxCompileVirtualMemory() {
		
		return storedSettings.getMaxCompileVirtualMemory();
	}

	@Override
	public void setMaxCompileVirtualMemory(int maxVirtualMemoryInKB) {
		storedSettings.setMaxCompileVirtualMemory(maxVirtualMemoryInKB);
		saveSettings();
		
	}

	@Override
	public int getMaxTotalSubmissionRuntime() {
		
		return storedSettings.getMaxTotalSubmissionRuntime();
	}

	@Override
	public void setMaxTotalSubmissionRuntime(int maxTimeInSeconds) {
		storedSettings.setMaxTotalSubmissionRuntime(maxTimeInSeconds);
		saveSettings();
	}

	@Override
	public int getMaxOutputFileSize() {
		
		return storedSettings.getMaxOutputFileSize();
	}

	@Override
	public void setMaxOutputFileSize(int maxSize) {
		storedSettings.setMaxOutputFileSize(maxSize);
		saveSettings();
	}

	@Override
	public int getMemoryLimitJava() {
		return storedSettings.getMemoryLimitJava();
	}

	@Override
	public void setMemoryLimitJava(int mb) {
		storedSettings.setMemoryLimitJava(mb);
		saveSettings();
		
	}

	@Override
	public int getMemoryLimitC() {
		return storedSettings.getMemoryLimitC();
	}

	@Override
	public void setMemoryLimitC(int mb) {
		storedSettings.setMemoryLimitC(mb);
		saveSettings();
		
	}

	@Override
	public int getMemoryLimitCpp() {
		return storedSettings.getMemoryLimitCpp();
	}

	@Override
	public void setMemoryLimitCpp(int mb) {
		storedSettings.setMemoryLimitCpp(mb);
		saveSettings();
		
	}

	@Override
	public int getMaxSubmissionQueueCapacity() {
		return storedSettings.getMaxSubmissionQueueCapacity();
	}

	@Override
	public void setMaxSubmissionQueueCapacity(int maxCapacity) {
		storedSettings.setMaxSubmissionQueueCapacity(maxCapacity);
		saveSettings();
	}

	@Override
	public int getStackMemoryLimitJava() {
		
		return storedSettings.getStackMemoryLimitJava();
	}

	@Override
	public void setStackMemoryLimitJava(int mb) {
		storedSettings.setStackMemoryLimitJava(mb);
		saveSettings();
		
	}

	@Override
	public int getPenalization() {
		
		return storedSettings.getPenalization();
	}

	@Override
	public void setPenalization(int min) {
		storedSettings.setPenalization(min);
		saveSettings();
	}

}
