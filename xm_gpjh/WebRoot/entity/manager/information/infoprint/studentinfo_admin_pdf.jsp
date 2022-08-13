
<%@page import="java.util.List"%>
<%@page import="com.lowagie.text.Font"%>
<%@page import="com.lowagie.text.Rectangle"%>
<%@page import="com.lowagie.text.Image"%>
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
<%@ page
	import="java.sql.*,java.io.*,java.awt.*,java.awt.Color,com.lowagie.text.*,com.lowagie.text.pdf.*"%>
<%@ page pageEncoding="utf-8"%>
<%@ page import="java.sql.*,java.io.*,java.awt.Color,com.lowagie.text.*,com.lowagie.text.pdf.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ page
	import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page
	import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page
	import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page
	import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%
	//////////////////////////////////////////////////////////////
	// Privilege Guarding
	String PrivXP = "打印管理";
	String PrivME = "650914";
%>
<% //@ include file="./pub/privGuarding.jsp"%>
<%
	// Privilege Guarding
	//////////////////////////////////////////////////////////////
%>
<%
	response.setContentType("application/pdf");
    response.setHeader("Content-Disposition","attachment;filename=report.pdf");
%>

<%!
	String fixnull(String s) {
		if (s == null || s.equals("null"))
			return "";
		return s;
	}%>
<%
	String search = fixnull(request.getParameter("search"));
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	String enterprise_id = fixnull(request
			.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	//String name = fixnull(request.getParameter("name"));
	String name = fixnull(java.net.URLDecoder.decode(fixnull(request
			.getParameter("name")), "UTF-8"));
	
	String bSub=fixnull(request.getParameter("bSub"));
	String erji_id=fixnull(request.getParameter("erji_id"));

	UserSession us = (UserSession) session
			.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList = us.getPriEnterprises();
	String sql_ent = "";
	String sql_entbk = "";
	for (int i = 0; i < entList.size(); i++) {
		PeEnterprise e = (PeEnterprise) entList.get(i);
		sql_ent += "'" + e.getId() + "',";
	}
	sql_entbk = sql_ent;
	if (!sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)
				+ ")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	boolean bErji=false;
	//判断是否二级企业
	if(entList!=null && entList.size()>0)
	{
		PeEnterprise e=(PeEnterprise)entList.get(0);
		String e_id=e.getId();
		String temp_sql="select id,code, name,fk_parent_id from pe_enterprise where id='"+e_id+"' and fk_parent_id is not null";
		//System.out.println(temp_sql);
		if(db.countselect(temp_sql)>0)
		{
			bErji=true;
			erji_id=e_id;
			rs=db.executeQuery(temp_sql);
			if(rs!=null && rs.next())
			{	
				enterprise_id=fixnull(rs.getString("fk_parent_id"));
			}
			db.close(rs);
		}
	}
	
	if (us.getRoleId().equals("2")
			|| us.getRoleId()
					.equals("402880a92137be1c012137db62100006")) //表示为一级主管和一级辅导员
	{
		String t_sql = "select id from pe_enterprise where fk_parent_id in "
				+ sql_ent;
		rs = db.executeQuery(t_sql);
		while (rs != null && rs.next()) {
			sql_entbk += "'" + fixnull(rs.getString("id")) + "',";
		}
		db.close(rs);
		sql_ent = "(" + sql_entbk.substring(0, sql_entbk.length() - 1)
				+ ")";
	}

	String sql_con = "";
	String sql_en = "";
	String sql_con1 = "";
	//if(!search.equals(""))
	//sql_con=" and b.id='"+search+"'";
	if (!sql_ent.equals("")) {
		sql_con += "  and ee.id in " + sql_ent;
		sql_con1 = "  and ps.fk_enterprise_id in " + sql_ent;
	}
	if(!enterprise_id.equals(""))
	{
		if(erji_id.equals(""))
		{
			sql_en = " and (ee.id = '" + enterprise_id + "' or ee.fk_parent_id ='"+enterprise_id+"')";
		}
		else
			sql_en =" and ee.id = '"+erji_id+"'";
	}
	if (!batch_id.equals("")) {
		sql_en += "and pb.id = '" + batch_id + "'";
	}

	String esql = "select name from pe_enterprise p  where p.id = '"+enterprise_id+"'";

	 rs  = db.executeQuery(esql);
	 String qyname="";
	 if(rs!=null &&rs.next() )
	 {
		 qyname = fixnull(rs.getString("name"));
	}
	
	db.close(rs);

	int i = 0;
	int l = 0;
	int len = 0;

	//	System.out.println(sql[0]);

	// step 1: creation of a document-object
	//  Rectangle pageSize = new Rectangle(256, 142);
	//   Document document = new Document();

	Document document = new Document(PageSize.A4);
	//  document.setMargins(20, 11, 20, 11);
	BaseFont bfChinese = BaseFont.createFont("STSong-Light",
			"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	
	Font FontChinese = new Font(bfChinese, 10,
			com.lowagie.text.Font.NORMAL);
	Font FontChinese2 = new Font(bfChinese, 1,
			com.lowagie.text.Font.NORMAL);
	Font Fontkaiti = new Font(bfChinese, 12, Font.NORMAL);
	Font Fontkaitisb = new Font(bfChinese, 10, Font.BOLD);
	Font fontHeiti = new Font(bfChinese, 28, Font.NORMAL);
	Font FontkaitiB = new Font(bfChinese, 12, Font.BOLD);
	Font fontDWMC = new Font(bfChinese, 8, Font.NORMAL);
	Font fontName = new Font(bfChinese, 10, Font.BOLD);
	try {

		// Chinese support!
		// step 2:
		// we create a writer that listens to the document
		// and directs a PDF-stream to a file
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
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
		String zpmc = "";
		String tmpstring = "";

		PdfPTable table = null;
		PdfPTable nest = null;
		PdfPCell cell = null;
		int count = 0;
		Chunk chunk = null;  //文本框
		Paragraph paragraph = null;

		String sql_t = "select rownum , ps.true_name as name  , ps.reg_no as regno ,  ss.password as passwords ,\n"
			+ "ps.gender as gender ,ps.folk as folk , ps.education as education , to_char(ps.birthday,'yyyymmdd') as birthday ,\n"
			+ " ee.ename pename  ,  ee.name as subname , ps.mobile_phone as mobile , ps.phone as phone ,\n"
			+ " ps.email as email  from pe_bzz_student ps inner join sso_user ss on ps.fk_sso_user_id = ss.id\n"
			+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id  "
			+ " inner join (select pe.id,pe.name,pes.name as ename,pe.fk_parent_id as fk_parent_id from pe_enterprise pe\n"
			+ " left outer join pe_enterprise pes on pe.fk_parent_id= pes.id) ee\n"
			+ " on ps.fk_enterprise_id = ee.id  "
			+ sql_en
			+ sql_con
			+ sql_con1 + " order by reg_no";
		
		String sql_conut = "select count(*) from pe_bzz_student ps inner join sso_user ss on ps.fk_sso_user_id = ss.id\n"
			+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id  "
			+ " inner join (select pe.id,pe.name,pes.name as ename,pe.fk_parent_id as fk_parent_id from pe_enterprise pe\n"
			+ " left outer join pe_enterprise pes on pe.fk_parent_id= pes.id) ee\n"
			+ " on ps.fk_enterprise_id = ee.id  "
			+ sql_en
			+ sql_con
			+ sql_con1 + " order by reg_no";
		
		
		String username ="";
		int rownum =0;
		int num = 0;
		
		rs = db.executeQuery(sql_conut);
		if(rs!=null && rs.next())
			num = rs.getInt(1);
		db.close(rs);
		
		if(num==0)
		{
			document.add(new Paragraph("目前没有学员。",fontName));
			document.close();
			return;
		}
		rs = db .executeQuery(sql_t);
		int headerwidths[] = { 20, 5, 20, 5, 20, 5,20, 1};
	 	for(l = 0 ;l<num;l++){
	 		if(rs!=null&&rs.next()){
	 		username = rs.getString("name");
			rownum = rs.getInt("rownum");
			if(l%16==0){
				document.add(new Paragraph("企业名称:"+qyname,fontName));
				document.add(new Paragraph("  ",fontName));
				
				table = new PdfPTable(8);
				table.setWidths(headerwidths);
				table.setWidthPercentage(100.0f);
			}
			
		
		nest =new PdfPTable(1);
		nest.setWidthPercentage(90);
		
		
		PdfPTable pdfTable = new PdfPTable(1);
		pdfTable.setWidthPercentage(90);
		
		cell = new PdfPCell();
		cell.setBorder(1);
		cell.setMinimumHeight(140);
		pdfTable.addCell(cell);
		
		cell  = new PdfPCell(pdfTable);
		nest.addCell(cell);
		
		cell = new PdfPCell(new Phrase("姓 名:"+username,fontDWMC));
		cell.cloneNonPositionParameters(border);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		nest.addCell(cell);
		
		cell = new PdfPCell(new Phrase("序 号:"+rownum,fontDWMC));
		cell.cloneNonPositionParameters(border);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		nest.addCell(cell);
		

		cell  = new PdfPCell(nest); 
		cell.cloneNonPositionParameters(border);
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("    "));
		cell.cloneNonPositionParameters(border);
		cell.setMinimumHeight(12f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		if (l % 4 == 3 && l % 16 != 15) {
			cell = new PdfPCell(new Paragraph("   ",
					FontChinese));
			cell.cloneNonPositionParameters(border);
			cell.setMinimumHeight(20f);
			cell.setColspan(10);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}

		if (l % 16 == 15 && l != num - 1) {
			document.add(table);
			document.newPage();
			}
		if (l == num - 1) {
			if (l % 4 != 3) {
				for (i = 0; i < 4 - l % 4; i++) {
					cell = new PdfPCell(new Paragraph(
							"   ", FontChinese));
					cell.cloneNonPositionParameters(border);
					cell.setMinimumHeight(6f);
					cell
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell
							.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Paragraph(
							"    ", FontChinese));
					cell.cloneNonPositionParameters(border);
					cell.setMinimumHeight(6f);
					cell
							.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell
							.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

				}
			}
			document.add(table);
			document.newPage();
		}
	 		}
	 	}
	 	db.close(rs);
		// document.add(new Paragraph("Hello World"));
	} catch (DocumentException de) {
		System.err.println(de.getMessage());
	} catch (Exception ioe) {
		System.err.println(ioe.getMessage());
	} finally {
		// step 5: we close the document
		document.close();
	}
	
%>