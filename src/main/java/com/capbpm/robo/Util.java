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

		retval = IPDFTemplate.PREAMBLE.replace("@@DOC_NAME@@", fmd.getFileName());
		
		String htmlBody ="";

		Iterator<FileSection> it = fmd.getSections().iterator();
		int debug =0;
		String allBodyText = "";
		while (it.hasNext()) {
			htmlBody="";

			FileSection section = it.next();
			String sName = section.getSectionName();
			if (sName.startsWith("Manager, Trustee and Portfolio Advisor"))
			{
				debug = -1;
			}
			if (sName != null) {
				String en = section.getEng();
				String fr = section.getFr();
				if (en == null) {
					en = "";
				}
				if (fr == null) {
					fr = "";
				}

				try
				{
					debug=1;
					htmlBody = IPDFTemplate.BODY.replace("@@SECTION_NAME@@", sName);
				debug=2;
				htmlBody = htmlBody.replace("@@ENG@@", en);
				debug=3;
				htmlBody = htmlBody.replace("@@FR@@", fr);
				debug=4;
				}
				catch(Exception e)
				{
//					if (debug==3)
//					{
//						htmlBody = htmlBody.replaceAll("@@FR@@", "");
//					}
					e.printStackTrace();
				}

			}
			
			allBodyText += htmlBody;

		}

		retval = retval +allBodyText + IPDFTemplate.POST;
		return retval;
	}
}
