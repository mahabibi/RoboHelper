package com.capbpm.robo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Util {

	

	public static ByteArrayOutputStream writeToPDF(String content) throws Exception {
		InputStream fis = new ByteArrayInputStream(content.getBytes("UTF-8"));
		ByteArrayOutputStream baot = writeToPDF(fis);

		return baot;
	}

	private static ByteArrayOutputStream writeToPDF(InputStream fis) throws Exception {
		Document document = new Document();
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		PdfWriter writer = PdfWriter.getInstance(document, os);
		document.open();

		XMLWorkerHelper.getInstance().parseXHtml(writer, document, fis);
		document.close();

		return os;
	}

	
	public static String constructHTML(FileMetaData fmd) {
		String retval = "";

		retval = IPDFTemplate.PREAMBLE.replaceAll("@@DOC_NAME@@", fmd.getFileName());

		Iterator<FileSection> it = fmd.getSections().iterator();
		while (it.hasNext()) {
			FileSection section = it.next();
			String en = section.getEng();
			String fr = section.getFr();

			retval += IPDFTemplate.BODY.replaceAll("@@SECTION_NAME@@", section.getSectionName())
					.replaceAll("@@ENG@@", en).replaceAll("@@FR@@", fr);

		}

		retval += IPDFTemplate.POST;
		return retval;
	}
}
