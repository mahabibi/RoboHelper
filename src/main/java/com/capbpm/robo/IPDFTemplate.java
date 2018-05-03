package com.capbpm.robo;

public interface IPDFTemplate {
	
		
		public static String PREAMBLE="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">\r\n" + 
				"<HTML>\r\n" + 
				"<HEAD>\r\n" + 
				"	<TITLE>@@DOC_NAME@@</TITLE>\r\n" + 
				"</HEAD>\r\n" + 
				"\r\n" + 
				"<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" LINK=\"#FF0000\" VLINK=\"#800000\" ALINK=\"#FF00FF\" BACKGROUND=\"?\">\r\n" + 
				"<TABLE ALIGN=\"left\" BORDER=1 CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\r\n" + 
				"<TR ALIGN=\"left\" VALIGN=\"middle\">\r\n" + 
				"	<TH>English</TH>\r\n" + 
				"	<TH>French</TH>\r\n" + 
				"</TR>";
		
		public static String BODY="<TR ALIGN=\"left\" VALIGN=\"middle\">\r\n" + 
				"	<TD colspan=\"2\" align=\"middle\">@@SECTION_NAME@@</TD>\r\n" + 
				"</TR>\r\n" + 
				"<TR ALIGN=\"left\" VALIGN=\"middle\">\r\n" + 
				"	<TD>@@ENG@@</TD>\r\n" + 
				"	<TD>@@FR@@</TD>\r\n" + 
				"</TR>";
		
		
		
		public static String POST="</TABLE>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"</BODY>\r\n" + 
				"</HTML>\r\n" + 
				"";

	
}
