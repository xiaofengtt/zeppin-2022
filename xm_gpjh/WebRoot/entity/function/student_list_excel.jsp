<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=student_excel.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ include file="./pub/priv.jsp"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%
	Object o1 = session.getAttribute("teacher_eduplatform_user");
	Object o2 = session.getAttribute("teacher_eduplatform_priv");
	Teacher session_teacher;
	if(o1 != null)
		session_teacher = (Teacher)o1;
	else
		session_teacher = (Teacher)(session.getAttribute("eduplatform_user"));
		
	TeacherPriv session_teacherPriv;
	if(o2 != null)
		session_teacherPriv = (TeacherPriv)o2;
	else
		session_teacherPriv = (TeacherPriv)(session.getAttribute("eduplatform_priv"));
	
	String teacher_id = session_teacher.getId();
	String teacher_name = session_teacher.getName();
	String teacher_login_id = session_teacher.getLoginId();
	
	PlatformFactory platformFactory = PlatformFactory.getInstance();
	TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%!
	String fixnull(String str)
	{
		if(str==null || str.equals("") || str.equals("null"))
			str = "";
		return str;
	}
%>
<%try{
BasicActivityManage basicActivityManage = teacherOperationManage.createBasicActivityManage();	
 
String course_name = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("coursename")),"UTF-8"));
String stuName = java.net.URLDecoder.decode(fixnull(request.getParameter("name")),"utf-8");
String stuReg_No = fixnull(request.getParameter("stureg_no"));
String batch_id = fixnull(request.getParameter("batch_id"));

dbpool db = new dbpool();
MyResultSet rs =null;

//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
					sql_con+=" and pe.id in "+sql_ent;
					sql_con1=" and bs.fk_enterprise_id in "+sql_ent;
				}

String sql = "";
String sql_name = "";
String sql_reg = "";
String sql_batch = "";


    
if(!stuName.equals(""))
		sql_name=" and bs.true_name like '%"+stuName.trim()+"%'";
if(!stuReg_No.equals(""))
		sql_reg=" and bs.reg_no like '%"+stuReg_No.trim()+"%'";
if(!batch_id.equals(""))
		sql_batch=" and bb.id='"+batch_id.trim()+"'";
sql = "select bs.id         as id,"+
      " bs.true_name       as name,"+
      " bs.reg_no     as reg_no,"+
      " bs.email      as email,"+
      " bs.gender     as gender,"+
      " bs.folk       as folk,"+
      " bs.position   as position,"+
      " bs.department as department,"+
      " bs.title      as title,"+
      " bb.name       as batch_name,"+
      " pe.name       as enterprise_name"+
 " from pe_bzz_student          bs,"+
      " pe_bzz_batch            bb,"+
      " pe_enterprise           pe,"+
      " pr_bzz_tch_stu_elective se,"+
      " pe_bzz_tch_course       tc,"+
      " pr_bzz_tch_opencourse   bo"+
" where bs.fk_batch_id = bb.id"+
 "  and bs.fk_enterprise_id = pe.id"+
 "  and se.fk_stu_id = bs.id"+
 "  and tc.name = '"+openCourse.getBzzCourse().getName()+"'"+
 " and bo.fk_course_id = tc.id"+
 " and bo.fk_batch_id = bb.id"+
 "  and se.fk_tch_opencourse_id = bo.id"+sql_con+sql_con1+sql_name+sql_reg+sql_batch +" order by bs.reg_no";
 


%>

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="新建%20Microsoft%20Excel%20工作表.files/filelist.xml">
<link rel=Edit-Time-Data href="新建%20Microsoft%20Excel%20工作表.files/editdata.mso">
<link rel=OLE-Object-Data href="新建%20Microsoft%20Excel%20工作表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2007-02-06T02:42:53Z</o:LastSaved>
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
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;}
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
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>7</x:ActiveRow>
       <x:ActiveCol>3</x:ActiveCol>
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

<body link=blue vlink=purple class=xl25>

<table x:str border=0 cellpadding=0 cellspacing=0 width=404 style='border-collapse:
 collapse;table-layout:fixed;width:304pt'>
 <col class=xl25 width=200 span=4 style='mso-width-source:userset;mso-width-alt:
 3232;width:76pt'>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl24 colspan=7 style='height:15.0pt;width:76pt'><%=openCourse.getBzzCourse().getName()%> 学生列表</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'>学号</td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'>姓名</td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'>性别</td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'>民族</td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'>所在学期</td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'>所在企业</td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:130pt'>电子邮箱</td>  
 </tr>
<%
    rs = db.executeQuery(sql);
	while(rs!=null && rs.next())
	{
		String id = fixnull(rs.getString("id"));
		String stu_name=fixnull(rs.getString("name"));
		String reg_no=fixnull(rs.getString("reg_no"));
		String email=fixnull(rs.getString("email"));
		String gender=fixnull(rs.getString("gender"));
		String folk=fixnull(rs.getString("folk"));
		String position=fixnull(rs.getString("position"));
		String department=fixnull(rs.getString("department"));
		String title=fixnull(rs.getString("title"));
		String enterprise_name=fixnull(rs.getString("enterprise_name"));
	    String batch_name=fixnull(rs.getString("batch_name"));
%>         
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=reg_no%></td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=stu_name%></td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=gender%></td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=folk%></td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=batch_name%></td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=enterprise_name%></td>
  <td height=20 class=xl24 width=200 style='height:15.0pt;width:76pt'><%=email%></td>            
</tr>
<%       
	} 
	db.close(rs); 
%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=200 style='width:76pt'></td>
  <td width=200 style='width:76pt'></td>
  <td width=200 style='width:76pt'></td>
  <td width=200 style='width:76pt'></td>
  <td width=200 style='width:76pt'></td>
  <td width=200 style='width:76pt'></td>
 </tr>
 <%
	} catch(Exception e) {
		out.print(e.getMessage());
		return;
	}
%>
 <![endif]>
</table>

</body>

</html>
