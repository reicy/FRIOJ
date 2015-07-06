package com.TK.frioj.entities;

import java.util.Arrays;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.daoImpl.FileSettingsDao;
import com.TK.frioj.enums.ProblemStatus;

public class Problem {

	private int problemId;
	private int setterID;
	private String name;
	private String note;
	private String text;
	private String input;
	private String output;
	private ProblemStatus status;
	private int timeLimit;
	private int memoryLimit;
	private int maxInputFileSize;
	private byte[] pdf;

	private String idColor = "black";

	public String getIdColor() {
		return idColor;
	}

	public void setIdColor(String idColor) {
		this.idColor = idColor;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public Problem() {

	}

	public Problem(int setterID, String meno, String popis, String zadanie,
			String input, String output, int timeLimit, byte[] pdf) {
		super();

		SettingsDao settingsDao = new FileSettingsDao();
		this.setterID = setterID;
		this.name = meno;
		this.note = popis;
		this.text = zadanie;
		this.input = input;
		this.output = output;
		this.status = ProblemStatus.visible;
		this.timeLimit = timeLimit;
		this.memoryLimit = settingsDao.getStandardMemoryLimit();
		this.maxInputFileSize = settingsDao.getStandardMaxInputFileSize();
		this.pdf = pdf;
	}

	public Problem(int id, int setterID, String name, String note,
			String input, String output, ProblemStatus status, int timeLimit,
			int memoryLimit, int maxInputFileSize) {

		this.problemId = id;
		this.setterID = setterID;
		this.name = name;
		this.note = note;
		this.input = input;
		this.output = output;
		this.status = status;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.maxInputFileSize = maxInputFileSize;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public int getSetterID() {
		return setterID;
	}

	public void setSetterID(int setterID) {
		this.setterID = setterID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public ProblemStatus getStatus() {
		return status;
	}

	public void setStatus(ProblemStatus status) {
		this.status = status;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public int getMaxInputFileSize() {
		return maxInputFileSize;
	}

	public void setMaxInputFileSize(int maxInputFileSize) {
		this.maxInputFileSize = maxInputFileSize;
	}

	@Override
	public String toString() {
		return "Problem [id=" + problemId + ", setterID=" + setterID
				+ ", meno=" + name + ", popis=" + note + ", zadanie=" + text
				+ ", input=" + input + ", output=" + output + ", stav="
				+ status + ", timeLimit=" + timeLimit + ", memoryLimit="
				+ memoryLimit + ", maxInputFileSize=" + maxInputFileSize
				+ ", pdf=" + Arrays.toString(pdf) + ", idColor=" + idColor
				+ "]";
	}

}