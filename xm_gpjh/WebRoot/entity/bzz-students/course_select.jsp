<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.resource.ResourceFactory"/>
<jsp:directive.page import="com.whaty.platform.resource.BasicResourceManage"/>
<jsp:directive.page import="com.whaty.platform.resource.basic.Resource"/>
<jsp:directive.page import="com.whaty.platform.resource.basic.ResourceContent"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
dbpool db = new dbpool();
MyResultSet rs = null;
String ask_sql = "select b.id,to_char(b.ask_start_date,'yyyy-mm-dd') as ask_start_date,to_char(b.ask_end_date,'yyyy-mm-dd') as ask_end_date from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"'";
String ask_end_date = "";
String ask_start_date = "";
rs = db.executeQuery(ask_sql);
while(rs!=null&&rs.next())
{
	ask_start_date = rs.getString("ask_start_date");
	ask_end_date = rs.getString("ask_end_date");
}
db.close(rs);
String check_sql1 = "select b.id from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') between start_time and end_time";
if(db.countselect(check_sql1)<1)
{
%>
<script type="text/javascript">
	alert("现在不在学期时间范围内，不能提问。");
	window.history.back();
</script>
<%
	return;
}
String check_sql = "select b.id from pe_bzz_batch b,pe_bzz_student s where s.fk_batch_id=b.id and s.reg_no='"+reg_no+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') between ask_start_date and ask_end_date";
if(db.countselect(check_sql)<1)
{
%>
<script type="text/javascript">
	alert("集中提问、答疑时间未到");
	window.history.back();
</script>
<%
	return;
}
String course_id = "";
String course_id_str = "";
String course_name = "";
String course_code = "";
String open_course_id = "";
String sql = "select c.id,c.name,c.code,o.id as open_course_id from pr_bzz_tch_stu_elective e,pe_bzz_student s,pr_bzz_tch_opencourse o,pe_bzz_tch_course c"
			+" where s.fk_sso_user_id='"+user_id+"' and s.id=e.fk_stu_id and e.fk_tch_opencourse_id=o.id and o.fk_course_id=c.id order by c.name";
rs = db.executeQuery(sql);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
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
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：常见问题库</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
      <tr>
            <td align="left"><a href="/entity/bzz-students/question.jsp"><img border="0" src="/entity/bzz-students/images/cjwtk.jpg" width="124" height="25"  name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/cjwtk_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/bzz-students/course_select.jsp"><img border="0" src="/entity/bzz-students/images/wytw.jpg" width="124" height="25"  name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/wytw_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a>
            </td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="40%" align="left"></td>
            <td width="10%" align="center"></td>
            <td width="30%" align="center"></td>
            <td width="15%" align="center"></td>
          </tr>
        </table></td>
      </tr>
      <form name="course_select" method="post" action="<%=basePath %>entity/bzz-students/course_selectexe.jsp" target="_blank">
      <tr align="center">
        <td height="29"><table width="60%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
            <td width="50%" align="right">选择课程：</td>
            <td width="50%" align="left">
			<select name="id">
<%
while(rs!=null&&rs.next())
{
	course_id = rs.getString("id");
	course_name = rs.getString("name");
	course_code = rs.getString("code");
	open_course_id = rs.getString("open_course_id");
	course_id_str = course_id+"|"+open_course_id;
%>
				<option value="<%=course_id_str %>"><%=course_name %></option>
<%
}
db.close(rs);
 %>
			</select>
			</td>
          </tr>
        </table></td>
      </tr>
      <tr>
       <td align="center">&nbsp;</td>
      </tr>
      <tr>
       <td align="center">
       <input type="submit" value="提  交" />
       <input type="button" value="返  回" onclick="window.history.back();"/>
       </td>
      </tr>
      </form>
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