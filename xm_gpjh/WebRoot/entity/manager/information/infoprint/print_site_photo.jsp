<%
		/*---------------------------------------------
		程序员:		Askwater
		email:		askwater@msn.com

		功能描述: 	公共课试题信封标签  PDF版本
		完成时间: 	2006-7-5
		修改情况:	
		修改时间:  	
		修改原因:	
		 -----------------------------------------------*/
%>
<%@ page import="java.sql.*,java.io.*,java.awt.Color,com.lowagie.text.*,com.lowagie.text.pdf.*"%>
<%@ page pageEncoding="GBK"%>

<jsp:useBean id="work" scope="application" class="com.dbConnection.dbpool"/>
<%
//////////////////////////////////////////////////////////////
// Privilege Guarding

	String 	PrivXP="打印管理";
	String 	PrivME="650914";
%>
<%@ include file="../pub/privGuarding.jsp" %>	
<%
// Privilege Guarding
//////////////////////////////////////////////////////////////
%>
<%
   response.setContentType("application/pdf");
%>

<%!
	String fixNull(String s){
		if(s == null || s.equals("null"))
			return "";
		return s;
	}
%>

<%!
	String convertCode(String str){
	  return str;
	}
%>

<%
	String[] roomid = request.getParameterValues("roomid");
	String sql ;
	String sql1 = "";
	
	com.dbConnection.MyResultSet rs_year = null;
	String sql_year="";
	String curyear="";
	sql_year="select to_char(sysdate,'yyyy') as year from dual";
	rs_year=work.executeQuery(sql_year);
	if(rs_year!=null && rs_year.next())
	{
		curyear=fixNull(rs_year.getString("year"));
	}
	work.close(rs_year);
	int tmpyear=0;
	tmpyear=Integer.parseInt(curyear);
	tmpyear++;
	curyear=""+tmpyear;
	curyear=curyear.substring(2,4);
	
	int i = 0;
	int l = 0;
	int len = 0;
	if(roomid==null){
		response.sendRedirect("print_site_select.jsp");
	}else{
		len = roomid.length;
	}

//	System.out.println(sql[0]);

	com.dbConnection.MyResultSet rs = null;
  
  // step 1: creation of a document-object
//  Rectangle pageSize = new Rectangle(256, 142);
//   Document document = new Document();
  
  Document document = new Document(PageSize.A4);
  ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//  document.setMargins(20, 11, 20, 11);
	  BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
      BaseFont bfKaiti = BaseFont.createFont(com.whaty.parameter.g_basedir+File.separator+"pdffont"+File.separator+"simkai.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	  BaseFont bfSimli = BaseFont.createFont(com.whaty.parameter.g_basedir+File.separator+"pdffont"+File.separator+"SIMLI.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	  BaseFont bfHeiti = BaseFont.createFont(com.whaty.parameter.g_basedir+File.separator+"pdffont"+File.separator+"SIMLI.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

      Font FontChinese = new Font(bfKaiti, 10, com.lowagie.text.Font.NORMAL);
      Font FontChinese2 = new Font(bfKaiti, 1, com.lowagie.text.Font.NORMAL);
	  Font Fontkaiti = new Font(bfKaiti,12,Font.NORMAL); 
	  Font Fontkaitisb = new Font(bfKaiti,10,Font.BOLD); 
	  Font fontHeiti = new Font(bfHeiti,28,Font.NORMAL);
	  Font FontkaitiB = new Font(bfHeiti,12,Font.BOLD); 
	  Font fontDWMC = new Font(bfKaiti,10,Font.NORMAL);
	  Font fontName = new Font(bfChinese,10,Font.BOLD);

  try {
  
				// Chinese support!
      // step 2:
	    // we create a writer that listens to the document
	    // and directs a PDF-stream to a file
      PdfWriter writer = PdfWriter.getInstance(document,buffer);
//      Paragraph paragraph = null;  
  
      Rectangle border = new Rectangle(0f, 0f);
      border.setBorderWidthLeft(0f);
      border.setBorderWidthBottom(0f);
      border.setBorderWidthRight(0f);
      border.setBorderWidthTop(0f);
      
      Rectangle border2 = new Rectangle(0f, 0f);
  
      // step 3: we open the document
      document.open();

      String examroom_name = "";
      String site_no = "";
      String dwmc = "";
      String ksbh = "";
      String xm = "";
      String bmh = "";
      String dwdm = "";
      String recruitno = "";
      String tmppath = "";
      String zpmc="";
      String basePath = request.getSession().getServletContext().getRealPath("");
      String tmpstring="";
      
      PdfPTable table = null;
      PdfPTable nest = null ;
      int headerwidths[] = { 8,5,8,5,8,5,8,5,8,1 };
//      int headerwidths[] = { 13,13,13,13,13,13,13 };
      int headerwidths2[] = {35,65};
      PdfPCell cell = null;
      int count = 0;
      Chunk ch = null;
      Paragraph paragraph = null;
      
	for(int k = 0 ; k<len; k++){
		sql1 = "select name from bs_ex_examroom_info where id ='" + roomid[k] + "' ";
		
		rs = work.executeQuery(sql1);
		if(rs!=null && rs.next()){
			examroom_name = rs.getString("name");
		}
		work.close(rs);
		
	sql = " select e.name as examroom_name, f.ksbh, f.site_no, f.xm, m.name as zymc,f.recruitno,f.zpmc " +
				" from bs_recruit_msg f, bs_ex_examroom_info e, lrn_major_info m " +
				" where m.id(+)=f.zydm and f.examroom_id=e.id and f.examroom_id ='" + roomid[k] + "' and f.recruitno in (select id from bs_recruitno_info where selected='1') " +
				" order by e.name,f.site_no ";

		count = work.countselect(sql);
		rs=work.executeQuery(sql);
	
		for(l=0;l<count;l++) {
			if(rs!=null && rs.next()){
			site_no = rs.getString("site_no");
			xm = rs.getString("xm");
		    ksbh = rs.getString("ksbh");
	       // dwmc = rs.getString("dwmc");
	        recruitno = rs.getString("recruitno");
	         dwmc = rs.getString("zymc");
	          zpmc = rs.getString("zpmc");
	      //  bmh = rs.getString("bmh");
	       // dwdm = rs.getString("dwdm");
	        
	        tmpstring = basePath+"work\\doctor\\photo\\"+recruitno+"\\"+zpmc;
	
			if(l%30==0){
				ch = new Chunk(examroom_name,fontHeiti);
				paragraph=new Paragraph(ch);
				paragraph.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph);
				document.add(new Paragraph("  "));
				table = new PdfPTable(10);
	        	table.setWidths(headerwidths);
	        	table.setWidthPercentage(100.0f);	
			}

			nest = new PdfPTable(1);
			nest.setWidthPercentage(90);
			Image image = Image.getInstance(tmpstring);
			nest.addCell(image);
			
			cell = new PdfPCell(new Paragraph(xm,fontName));
			cell.cloneNonPositionParameters(border);
			cell.setMinimumHeight(12f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
			nest.addCell(cell); 	
			cell = new PdfPCell(new Paragraph("座位号:" + site_no,Fontkaitisb));
			cell.cloneNonPositionParameters(border);
			cell.setMinimumHeight(12f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
			nest.addCell(cell); 	
			
			cell = new PdfPCell(nest);
			cell.cloneNonPositionParameters(border);
			cell.setMinimumHeight(12f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  		
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("  "));
			cell.cloneNonPositionParameters(border);
			cell.setMinimumHeight(12f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  		
			table.addCell(cell);
			
			if(l%5==4&&l%30!=29){
				cell = new PdfPCell(new Paragraph("   ",FontChinese2));
				cell.cloneNonPositionParameters(border);
				cell.setMinimumHeight(1f);
				cell.setColspan(10);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
				table.addCell(cell);
			}
			
	 		if(l%30==29 && l!=count-1 ){
	 			document.add(table); 
	 			document.newPage();
	 		}
	 		
	 		if(l==count-1){
	 			if(l%5!=4){
					for(i=0;i<5-l%5;i++){
						cell = new PdfPCell(new Paragraph("   ",FontChinese));
						cell.cloneNonPositionParameters(border);
						cell.setMinimumHeight(6f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
						table.addCell(cell);
						
						cell = new PdfPCell(new Paragraph("   ",FontChinese));
						cell.cloneNonPositionParameters(border);
						cell.setMinimumHeight(6f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
						table.addCell(cell);
						
					}
	 			}
				document.add(table); 
				document.newPage();
			}
	 
	      }
      }
	 	work.close(rs);
	 	
	 }	
			
     // step 4: we add a paragraph to the document
     // document.add(new Paragraph("Hello World"));
     }catch(DocumentException de) {
			 System.err.println(de.getMessage());
	 }catch(Exception ioe) {
			System.err.println(ioe.getMessage());
	 }finally{
	 }
	 
	   // step 5: we close the document
     document.close();
  
     DataOutput output = new DataOutputStream(response.getOutputStream());
     byte[] bytes = buffer.toByteArray();
     response.reset();
     response.setContentLength(bytes.length);
   response.setHeader("Content-Disposition", "attachment;filename=print_site_small.pdf");
   
     for(i=0; i<bytes.length; i++){
       output.writeByte(bytes[i]);
     }   
%>