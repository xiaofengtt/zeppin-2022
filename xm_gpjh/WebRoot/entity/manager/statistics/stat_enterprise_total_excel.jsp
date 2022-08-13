<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_enterprise_total_excel.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
	String search = fixnull(request.getParameter("search"));
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	//String name = fixnull(request.getParameter("name"));
    String name = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("name")),"UTF-8"));
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	for(int i=0;i<entList.size();i++)
	{
		PeEnterprise e=(PeEnterprise)entList.get(i);
		sql_ent+="'"+e.getId()+"',";
	}
	sql_entbk=sql_ent;
	if(!sql_ent.equals(""))
	{
		sql_ent="("+sql_ent.substring(0,sql_ent.length()-1)+")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
	
	
				String sql_con="";
				String sql_en="";
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and e.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				if(!enterprise_id.equals(""))
				{
					sql_en ="and e.id = '"+enterprise_id+"'";
				}
				if(!batch_id.equals(""))
				{
					sql_en +="and b.id = '"+batch_id+"'";
				}
				
				
       String sql_t = "select   s.id as student_id,s.reg_no as student_reg_no, s.true_name as student_name ,s.email as student_email ,s.mobile_phone as student_phone , "+ 
		" e.name as enterprise_name , b.name as batch_name from pe_bzz_student s,pe_enterprise e ,pe_bzz_batch b "+
		" where  s.fk_enterprise_id = e.id and s.fk_batch_id = b.id  "+
                "   "+sql_en+" "+sql_con+" "+sql_con1+" and s.reg_no like '%"+search+"%'  order by s.reg_no ";
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="新建%20Microsoft%20Excel%20工作表.files/filelist.xml">
<link rel=Edit-Time-Data href="新建%20Microsoft%20Excel%20工作表.files/editdata.mso">
<link rel=OLE-Object-Data href="新建%20Microsoft%20Excel%20工作表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2009-07-23T02:45:52Z</o:LastSaved>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:RemovePersonalInformation/>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:left;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>0</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>1</x:ActiveRow>
       <x:ActiveCol>5</x:ActiveCol>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet2</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet3</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>4530</x:WindowHeight>
  <x:WindowWidth>8505</x:WindowWidth>
  <x:WindowTopX>480</x:WindowTopX>
  <x:WindowTopY>120</x:WindowTopY>
  <x:AcceptLabelsInFormulas/>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1048 style='border-collapse:
 collapse;table-layout:fixed;width:787pt'>
 <col width=159 style='mso-width-source:userset;mso-width-alt:5088;width:119pt'>
 <col width=125 style='mso-width-source:userset;mso-width-alt:4000;width:94pt'>
 <col width=150 style='mso-width-source:userset;mso-width-alt:4800;width:113pt'>
 <col width=188 style='mso-width-source:userset;mso-width-alt:6016;width:141pt'>
 <col width=212 style='mso-width-source:userset;mso-width-alt:6784;width:159pt'>
 <col width=214 style='mso-width-source:userset;mso-width-alt:6848;width:161pt'>
 <tr height=32 style='mso-height-source:userset;height:24.0pt'>
 <%
  	 String sql_total="";
 		if(!enterprise_id.equals("")){
 			sql_total = "select b.name as enterprise_name  from pe_enterprise b where b.id ='"+enterprise_id+"'";
 		}else if(!batch_id.equals("")){
 			sql_total = "select b.name as enterprise_name  from pe_bzz_batch b where b.id ='"+batch_id+"'";
 		}
  		 rs = db.executeQuery(sql_total);
  		rs.next();
		String ename = fixnull(rs.getString("enterprise_name"));
		 db.close(rs);
 %>
  <td colspan=6 height=32 class=xl26 width=1048 style='height:24.0pt;
  width:787pt'><font color="red">&lt;&lt;<%=ename %>&gt;&gt;</font>学员列表</td>
  </tr>
 <tr height=29 style='mso-height-source:userset;height:21.75pt'>
  <td height=29 class=xl24 style='height:21.75pt;border-top:none'>学号</td>
  <td class=xl24 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl24 style='border-top:none;border-left:none'>所在学期</td>
  <td class=xl24 style='border-top:none;border-left:none'>所在企业</td>
  <td class=xl24 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl24 style='border-top:none;border-left:none'>电子邮件</td>
 </tr>
 <%
 	            rs = db.executeQuery(sql_t);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					
					String enterprise_name = fixnull(rs.getString("enterprise_name"));
					String student_id = fixnull(rs.getString("student_id"));
					String student_name = fixnull(rs.getString("student_name"));
					String student_phone = fixnull(rs.getString("student_phone"));
					String student_reg_no = fixnull(rs.getString("student_reg_no"));
					String student_email = fixnull(rs.getString("student_email"));
					String batch_name = fixnull(rs.getString("batch_name"));
 %>
 <tr height=22 style='mso-height-source:userset;height:16.5pt'>
  <td height=22 class=xl25 style='height:16.5pt;border-top:none'><%=student_reg_no %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_name %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=batch_name %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=enterprise_name %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_phone %>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=student_email %>　</td>
 </tr>
 <%
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=159 style='width:119pt'></td>
  <td width=125 style='width:94pt'></td>
  <td width=150 style='width:113pt'></td>
  <td width=188 style='width:141pt'></td>
  <td width=212 style='width:159pt'></td>
  <td width=214 style='width:161pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
