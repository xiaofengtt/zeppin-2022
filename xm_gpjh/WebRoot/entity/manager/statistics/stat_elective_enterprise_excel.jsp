<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_elective_enterprise_excel.xls"); %>
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
	String batch_id = fixnull(request.getParameter("batch_id"));
	String course_id = fixnull(request.getParameter("course_id"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String search1 = fixnull(request.getParameter("search1"));
	String batch_name = "";
	String course_name = "";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String batch_sql = "select b.name as batch_name,c.name as course_name from pe_bzz_batch b,pe_bzz_tch_course c where b.id = '"+batch_id+"' and c.id='"+course_id+"'";
	rs = db.executeQuery(batch_sql);
	if(rs!=null&&rs.next())
	{
		batch_name = rs.getString("batch_name");
		course_name = rs.getString("course_name");
	}
	db.close(rs);
	
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
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and en.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				String sql = "select id,name,num from (select en.id,en.name,count(e.fk_stu_id) as num from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o,pr_bzz_tch_stu_elective e,pe_bzz_student s,pe_enterprise en "
							+"	where b.id=o.fk_batch_id "+sql_con+" "+sql_con1+"  and c.id=o.fk_course_id and o.id=e.fk_tch_opencourse_id and e.fk_stu_id=s.id and s.fk_enterprise_id=en.id and c.id='"+course_id+"' and b.id='"+batch_id+"' "
							+"	group by en.id,en.name) where name like '%"+search+"%' order by name";
System.out.println(sql);
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="新建%20Microsoft%20Excel%20工作表%20(2).files/filelist.xml">
<link rel=Edit-Time-Data
href="新建%20Microsoft%20Excel%20工作表%20(2).files/editdata.mso">
<link rel=OLE-Object-Data
href="新建%20Microsoft%20Excel%20工作表%20(2).files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2009-07-29T08:19:34Z</o:LastSaved>
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
	font-size:11.0pt;
	mso-number-format:"\@";
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:16.0pt;
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
       <x:RangeSelection>$A$1:$B$1</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=581 style='border-collapse:
 collapse;table-layout:fixed;width:436pt'>
 <col width=366 style='mso-width-source:userset;mso-width-alt:11712;width:275pt'>
 <col width=215 style='mso-width-source:userset;mso-width-alt:6880;width:161pt'>
 <tr height=39 style='mso-height-source:userset;height:29.25pt'>
  <td colspan=2 height=39 class=xl26 width=581 style='height:29.25pt;
  width:436pt'><%=batch_name%><%=course_name%>选课统计信息</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'>企业名称</td>
  <td class=xl24 style='border-top:none;border-left:none'>选课人数</td>
 </tr>
 <%
 	            rs = db.executeQuery(sql);			
				while(rs!=null&&rs.next())
				{
					
					String enterprise_id = fixnull(rs.getString("id"));
					String enterprise_name = fixnull(rs.getString("name"));
					String num = fixnull(rs.getString("num"));
 %>
 <tr height=16 style='mso-height-source:userset;height:12.0pt'>
  <td height=16 class=xl25 style='height:12.0pt;border-top:none'><%=enterprise_name%>　</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=num%>　</td>
 </tr>
 <%}
 db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=366 style='width:275pt'></td>
  <td width=215 style='width:161pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

 