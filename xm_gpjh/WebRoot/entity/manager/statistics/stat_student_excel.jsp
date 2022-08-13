<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_student_excel.xls"); %>
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
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
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
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and e.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				String sql = "select a.id,a.name,a.total_num,nvl(b.completed_num,0) as completed_num,(a.total_num-nvl(b.completed_num,0)) as incompleted_num from "
							+"	(select b.id,b.name,count(s.id) as total_num from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id "+sql_con1+" group by b.id,b.name) a,"
							+"	(select batch_id,batch_name,count(student_id) as completed_num from "
							+"		(select b.id as batch_id,b.name as batch_name,s.id as student_id,s.reg_no,s.name as student_name,sum(c.time) as total_time "
							+"		from pe_bzz_batch b,pe_bzz_student s,pe_enterprise e,sso_user u,training_course_student cs,pr_bzz_tch_opencourse o,pe_bzz_tch_course c "
							+"		where b.id=s.fk_batch_id and e.id = s.fk_enterprise_id and b.id=o.fk_batch_id and s.fk_sso_user_id=u.id and u.id=cs.student_id and cs.course_id=o.id and o.fk_course_id=c.id and cs.learn_status='COMPLETED'  "+sql_con+" group by b.id,b.name,s.id,s.reg_no,s.name) "
							+"	where to_number(total_time)>=(select nvl(standards,0) from pe_bzz_batch where id=batch_id) group by batch_id,batch_name) b "
							+"where a.id=b.batch_id(+) and name like '%"+search+"%' order by a.name";
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
  <o:LastSaved>2009-07-22T05:57:01Z</o:LastSaved>
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
	font-size:18.0pt;
	font-weight:700;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:left;
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
       <x:RangeSelection>$A$3:$F$3</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=811 style='border-collapse:
 collapse;table-layout:fixed;width:609pt'>
 <col width=184 style='mso-width-source:userset;mso-width-alt:5888;width:138pt'>
 <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>
 <col width=150 style='mso-width-source:userset;mso-width-alt:4800;width:113pt'>
 <col width=159 style='mso-width-source:userset;mso-width-alt:5088;width:119pt'>
 <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>
 <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>
 <tr height=44 style='mso-height-source:userset;height:33.0pt'>
  <td colspan=6 height=44 class=xl25 width=811 style='height:33.0pt;width:609pt'>学员统计信息</td>
 </tr>
 <tr height=40 style='mso-height-source:userset;height:30.0pt'>
  <td height=40 class=xl24 style='height:30.0pt;border-top:none'>学期名称</td>
  <td class=xl24 style='border-top:none;border-left:none'>总人数</td>
  <td class=xl24 style='border-top:none;border-left:none'>达到学时人数</td>
  <td class=xl24 style='border-top:none;border-left:none'>已颁发证书人数</td>
  <td class=xl24 style='border-top:none;border-left:none'>结业人数</td>
  <td class=xl24 style='border-top:none;border-left:none'>未学完人数</td>
 </tr>
 <%
 	            rs = db.executeQuery(sql);
				while(rs!=null&&rs.next())
				{
					String batch_id = fixnull(rs.getString("id"));
					String batch_name = fixnull(rs.getString("name"));
					String total_num = fixnull(rs.getString("total_num"));
					String completed_num = fixnull(rs.getString("completed_num"));
					String incompleted_num = fixnull(rs.getString("incompleted_num"));           
 %>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td height=26 class=xl26 style='height:19.5pt;border-top:none'><%=batch_name %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%= total_num%>　</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=completed_num %>　</td>
  <td class=xl26 style='border-top:none;border-left:none'>0</td>
  <td class=xl26 style='border-top:none;border-left:none'>0</td>
  <td class=xl26 style='border-top:none;border-left:none'><%=incompleted_num %>　</td>
 </tr>
 <%    }
       db.close(rs); %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=184 style='width:138pt'></td>
  <td width=100 style='width:75pt'></td>
  <td width=150 style='width:113pt'></td>
  <td width=159 style='width:119pt'></td>
  <td width=98 style='width:74pt'></td>
  <td width=120 style='width:90pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
