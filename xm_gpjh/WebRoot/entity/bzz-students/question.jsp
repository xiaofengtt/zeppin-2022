<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.resource.ResourceFactory"/>
<jsp:directive.page import="com.whaty.platform.resource.BasicResourceManage"/>
<jsp:directive.page import="com.whaty.platform.resource.basic.Resource"/>
<jsp:directive.page import="com.whaty.platform.resource.basic.ResourceContent"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
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
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession!=null)
{
}
else
{
%>
<script>
	window.alert("登录超时，为了您的帐户安全，请重新登录。");
	window.location = "/";
</script>
<%
return;
}
String user_id = userSession.getId();
String reg_no = userSession.getLoginId();
String course_id = "";
String course_id_str = "";
String course_name = "";
String course_code = "";
String sql = "select c.id,c.name,c.code from pr_bzz_tch_stu_elective e,pe_bzz_student s,pr_bzz_tch_opencourse o,pe_bzz_tch_course c"
			+" where s.fk_sso_user_id='"+user_id+"' and s.id=e.fk_stu_id and e.fk_tch_opencourse_id=o.id and o.fk_course_id=c.id";
System.out.println("==============" + sql + "===============");
dbpool db = new dbpool();
MyResultSet rs = null;
rs = db.executeQuery(sql);
while(rs!=null&&rs.next())
{
	course_id = rs.getString("id");
	course_name = rs.getString("name");
	course_code = rs.getString("code");
	course_id_str += "'faq_"+course_id+"',";
}
db.close(rs);
if(course_id_str!=null&&course_id_str.length()>0)
{
	course_id_str = course_id_str.substring(0,course_id_str.length()-1);
}
else
{
	course_id_str = "''";
}

String ask_sql = "select b.id,to_char(b.ask_start_date,'yyyy-mm-dd') as ask_start_date,to_char(b.ask_end_date,'yyyy-mm-dd') as ask_end_date from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"'";
String ask_end_date = "";
String ask_start_date = "";
rs = db.executeQuery(ask_sql);
while(rs!=null&&rs.next())
{
	ask_start_date = fixnull(rs.getString("ask_start_date"));
	ask_end_date = fixnull(rs.getString("ask_end_date"));
}
db.close(rs);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style></head>

<body bgcolor="#E0E0E0" onLoad="MM_preloadImages('/entity/bzz-students/images/cjwtk.jpg','/entity/bzz-students/images/cjwtk_over.jpg','/entity/bzz-students/images/wytw.jpg','/entity/bzz-students/images/wytw_over.jpg')">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程答疑</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> -->
          <font color=red>课程答疑说明</font>：组织老师对相应课程集中进行考前网上辅导答疑，具体时间见公告。
		当前提问时间为：<font color=red><%=ask_start_date %>至<%=ask_end_date %></font>
          </td>
      </tr>
       <tr>
          <td><br>
          </td>
      </tr>
      <tr>
            <td align="left"><a href="/entity/bzz-students/question.jsp"><img border="0" src="/entity/bzz-students/images/cjwtk.jpg" width="124" height="25"  name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/cjwtk_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/bzz-students/course_select.jsp"><img border="0" src="/entity/bzz-students/images/wytw.jpg" width="124" height="25"  name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/wytw_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a>
            </td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          	<td width="5%" align="center">&nbsp;</td>
            <td width="40%" align="left">问题名称</td>
            <td width="30%" align="center">所属课程</td>
            <td width="25%" align="center">详细信息</td>
          </tr>
        </table></td>
      </tr>
<%
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	sql = "select i.id,i.title,i.language,i.description,i.keywords,i.creatuser,i.content,d.id as dir_id,d.name from resource_dir d,resource_info i where d.id=i.dir_id and d.keyid in("+course_id_str+")";
	System.out.println("==============" + sql + "===============");
	int totalItems = 0;
	totalItems = db.countselect(sql);
	//----------分页开始---------------
		String spagesize = (String) session.getValue("pagesize");
		String spageInt = request.getParameter("pageInt");
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "";
	rs = db.execute_oracle_page(sql,pageInt,pagesize);
	while(rs!=null&&rs.next())
	{
		String resId=fixnull(rs.getString("id"));
		Resource res = resManage.getResource(rs.getString("id"));
		List contentList = res.getResourceContentList();
		ResourceContent detail = (ResourceContent)contentList.get(0);
%>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
          <tr>
            <td width="5%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="40%" align="left"><%=rs.getString("title") %></td>
            <td width="30%" align="center"><%=rs.getString("name") %></td>
            <td width="25%" align="center"><input type="button" value="详细信息" onclick='window.open("<%=basePath%>/entity/bzz-students/faq_detail.jsp?resId=<%=resId %>","question");'></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          </table></td>
      </tr>
<%
	}
	db.close(rs);
 %>
      <tr>
       <td align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><%@ include file="./pub/dividepage.jsp" %></td>
      </tr>
       </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
  </tr>
</table>
</body>
</html>