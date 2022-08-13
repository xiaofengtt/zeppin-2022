<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_course_total_excel.xls"); %>
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
	String search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search")),"UTF-8"));
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
		System.out.println(t_sql);
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
	
	String sql = "select a.id,a.name,total_course_num,nvl(jichu_course_num,0) as jichu_course_num,nvl(tisheng_course_num,0) as tisheng_course_num,nvl(total_course_time,0) as total_course_time,nvl(jichu_course_time,0) as jichu_course_time,nvl(tisheng_course_time,0) as tisheng_course_time from "
							+"	(select b.id,b.name,count(c.id) as total_course_num from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o where b.id=o.fk_batch_id and c.id=o.fk_course_id group by b.id,b.name) a,"
							+"	(select b.id,b.name,count(c.id) as jichu_course_num from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o,enum_const e where b.id=o.fk_batch_id and c.id=o.fk_course_id and o.flag_course_type=e.id and e.name='基础课程' group by b.id,b.name) b,"
							+"	(select b.id,b.name,count(c.id) as tisheng_course_num from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o,enum_const e where b.id=o.fk_batch_id and c.id=o.fk_course_id and o.flag_course_type=e.id and e.name='提升课程' group by b.id,b.name) c,"
							+"	(select b.id,b.name,sum(c.time) as total_course_time from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o where b.id=o.fk_batch_id and c.id=o.fk_course_id group by b.id,b.name) d,"
							+"	(select b.id,b.name,sum(c.time) as jichu_course_time from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o,enum_const e where b.id=o.fk_batch_id and c.id=o.fk_course_id and o.flag_course_type=e.id and e.name='基础课程' group by b.id,b.name) e,"
							+"	(select b.id,b.name,sum(c.time) as tisheng_course_time from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o,enum_const e where b.id=o.fk_batch_id and c.id=o.fk_course_id and o.flag_course_type=e.id and e.name='提升课程' group by b.id,b.name) f"
							+" where a.id=b.id(+) and a.id=c.id(+) and a.id=d.id(+) and a.id=e.id(+) and a.id=f.id(+) and a.name like '%"+search+"%' order by a.name";
	
	
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
  <o:LastSaved>2009-07-29T08:29:49Z</o:LastSaved>
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
	font-size:18.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:18.0pt;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:11.0pt;
	mso-number-format:"\@";
	text-align:left;
	border:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
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
       <x:ActiveRow>2</x:ActiveRow>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=767 style='border-collapse:
 collapse;table-layout:fixed;width:577pt'>
 <col width=139 style='mso-width-source:userset;mso-width-alt:4448;width:104pt'>
 <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>
 <col width=121 style='mso-width-source:userset;mso-width-alt:3872;width:91pt'>
 <col width=114 style='mso-width-source:userset;mso-width-alt:3648;width:86pt'>
 <col width=72 style='mso-width-source:userset;mso-width-alt:2304;width:54pt'>
 <col width=114 style='mso-width-source:userset;mso-width-alt:3648;width:86pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <tr height=36 style='mso-height-source:userset;height:27.0pt'>
  <td colspan=7 height=36 class=xl24 width=767 style='height:27.0pt;width:577pt'>资源统计信息</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl27 style='height:14.25pt;border-top:none'>学期名称</td>
  <td class=xl27 style='border-top:none;border-left:none'>开课总数</td>
  <td class=xl27 style='border-top:none;border-left:none'>基础课程总数</td>
  <td class=xl27 style='border-top:none;border-left:none'>提升课程总数</td>
  <td class=xl27 style='border-top:none;border-left:none'>总学时</td>
  <td class=xl27 style='border-top:none;border-left:none'>基础课程学时</td>
  <td class=xl27 style='border-top:none;border-left:none'>提升课程学时</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String batch_id = fixnull(rs.getString("id"));
					String batch_name = fixnull(rs.getString("name"));
					String total_course_num = fixnull(rs.getString("total_course_num"));
					String jichu_course_num = fixnull(rs.getString("jichu_course_num"));
					String tisheng_course_num = fixnull(rs.getString("tisheng_course_num"));
					String total_course_time = fixnull(rs.getString("total_course_time"));
					String jichu_course_time = fixnull(rs.getString("jichu_course_time"));
					String tisheng_course_time = fixnull(rs.getString("tisheng_course_time"));
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'><%=batch_name%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=total_course_num%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=jichu_course_num%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=tisheng_course_num%></td>
  <td class=xl26 style='border-top:none;border-left:none'><%=total_course_time%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=jichu_course_time%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=tisheng_course_time %>　</td>
 </tr>
 <%
 }
 db.close(rs);%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=139 style='width:104pt'></td>
  <td width=98 style='width:74pt'></td>
  <td width=121 style='width:91pt'></td>
  <td width=114 style='width:86pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=114 style='width:86pt'></td>
  <td width=109 style='width:82pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

