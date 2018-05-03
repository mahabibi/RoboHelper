package com.capbpm.robo;

import java.util.Comparator;

public class FileSection implements Comparable<FileSection>,  Comparator<FileSection>{

	private String sectionName;
	private int order;
	private String eng;
	private String fr;
	
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String section) {
		this.sectionName = section;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getEng() {
		return eng;
	}

	public void setEng(String eng) {
		this.eng = eng;
	}

	public String getFr() {
		return fr;
	}

	public void setFr(String fr) {
		this.fr = fr;
	}

	public int compareTo(FileSection other) {

		return compare(this,other);
	}

	
	public int compare(FileSection o1, FileSection o2) {
		// TODO Auto-generated method stub
		return Math.min(o1.getOrder(), o2.getOrder());
	}	
}
