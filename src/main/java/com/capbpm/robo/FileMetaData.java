package com.capbpm.robo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileMetaData {
	private String fileName;
	private Map<String, String> engMap = new TreeMap<String, String>();
	private Map<String, FileSection> sectionMap = new TreeMap<String, FileSection>();

	public List<String> getEngListFromSections() {
		List<String> retval = new ArrayList<String>();

		for (int i = 0; i < this.sections.size(); i++) {
			retval.add(this.sections.get(i).getEng());
		}

		return retval;
	}

	private List<FileSection> sections = new ArrayList<FileSection>();

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<FileSection> getSections() {
		return sections;
	}

	public void addSection(FileSection s) {
		this.sections.add(s);
		this.sectionMap.put(s.getSectionName(), s);
	}

	public void setSections(List<FileSection> sections) {
		this.sections = sections;
		for (int i = 0; i < this.sections.size(); i++) {
			this.sectionMap.put(this.sections.get(i).getSectionName(), this.sections.get(i));
		}
	}

	public void updateFr(Map<String, String> transMap) {
		Iterator<String> keyz = transMap.keySet().iterator();
		while (keyz.hasNext()) {
			String sName = keyz.next();
			String tranalated = transMap.get(sName);

			FileSection fs = sectionMap.get(sName);
			fs.setFr(tranalated);
		}
	}

}
