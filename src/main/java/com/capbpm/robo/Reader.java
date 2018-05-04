package com.capbpm.robo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

public class Reader {
	
	public static void main(String args[]) throws Exception {

		List<FileMetaData> fmdList = readFilesFromSystem();
		for (FileMetaData current : fmdList) {
			String htmlContent = Util.constructHTML(current);
			ByteArrayOutputStream baos = Util.writeToPDF(htmlContent);
			try (OutputStream outputStream = new FileOutputStream("C:\\_ci\\out\\" + current.getFileName().replaceFirst(".txt", "") + ".pdf")) {
				baos.writeTo(outputStream);
			}
		}
		
		System.out.println("done");

	}

	
	static String readFile(String path) throws Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, "UTF-8");
	}

	public static List<FileMetaData> readFilesFromSystem() throws Exception {
		List<FileMetaData> retval = new ArrayList<FileMetaData>();
		
		File inDir = new File("C:\\_ci\\in");
		
		System.out.println("got root dir");

		File[] files = inDir.listFiles();
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].length()>0)
				{	
					String fName = files[i].getName();
					if (fName.startsWith("in_")) {
						System.out.println("about to load files");
						FileMetaData currentFMD = getFileConentToFileMetaData(files[i]);
						retval.add(currentFMD);
	
						// clean up
						//String newFileName = files[i].getName().replaceFirst("in_", "done_").replaceFirst(".txt", "");
						//files[i].renameTo(new File(inDir,newFileName));
					}
					}
			}
		}

		return retval;
	}

	private static FileMetaData getFileConentToFileMetaData(File file) throws Exception {
		FileMetaData retval = new FileMetaData();
		retval.setFileName(file.getName().replaceFirst("in_", "").replaceFirst(".txt_", ""));
		String fileContent = readFile(file.getAbsolutePath());

		String[] sections = fileContent.split("/////@@@@@");

		for (int i = 0; i < sections.length; i++) {
			try {
			if (sections[i] == null || sections[i].trim().equals("")) {
				continue;
			}
			String[] sectionBody = sections[i].split("@@@@@");
			String sBody = "";
			
			if (sectionBody.length==2 && sectionBody[1] !=null)
			{
				sBody = sectionBody[1];
			}
			FileSection fs = new FileSection();
			fs.setSectionName(sectionBody[0]);
			fs.setEng( sBody.trim());
			// fs.setFr(sectionBody[1]);
			fs.setOrder(i);
			retval.addSection(fs);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		boolean isTrans = true;
		if (isTrans) {
			System.out.println("before calling translation");
			callTranslate(retval);
			System.out.println("after calling translation");
		}

		return retval;
	}

	
	public static FileMetaData getFileData() {

		FileMetaData fmd = new FileMetaData();
		fmd.setFileName("dummy");

		for (int i = 0; i < 5; i++) {
			FileSection fs = new FileSection();
			fs.setOrder(i);
			fs.setSectionName("Section " + (i + 1));
			fs.setEng("I love you times " + i);
			fs.setFr("j'eaime vous " + i);
			fmd.addSection(fs);
		}

		return fmd;
	}

	public static void callTranslate(FileMetaData fmd) throws Exception {
		String url = "https://capbpmlab1.capbpm.com/translate/to/fr";

		URL obj = new URL(url);
		System.out.println( "processing " + fmd.getFileName());

		List<FileSection> sections = fmd.getSections();

		InputStream in = null;
		for (int i = 0; i < sections.size(); i++) {	
			if (sections.get(i).getEng() !=null && !sections.get(i).getEng().trim().equals(""))
			{
			System.out.println(" before translation " + i + " of " + sections.size() + ":"+ sections.get(i).getSectionName());
			HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			OutputStream outputStream = connection.getOutputStream(); // <-- I get an exception.

			outputStream.write(sections.get(i).getEng().getBytes());
			outputStream.flush();
			System.out.println(" getting input stream");
			in = connection.getInputStream();
			System.out.println(" got response  stream");
			String result = CharStreams.toString(new InputStreamReader(in, Charsets.UTF_8));
			sections.get(i).setFr(result);
			in.close();
			}
		}
	

	}
	
}
