<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String disabled = "";
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
String sql = "select b.id from pe_bzz_batch b,pe_bzz_student s where b.id=s.fk_batch_id and s.fk_sso_user_id='"+user_id+"' and to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') between start_time and end_time";
dbpool db = new dbpool();
if(db.countselect(sql)>0)
{
	disabled = "";
}
else
{
	disabled = "disabled";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
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

<body bgcolor="#E0E0E0">
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
     <td width="110" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：学习状态总览-
        <s:if test="ctype=='jcwc'">已完成基础课程列表</s:if>
        <s:elseif test="ctype=='tswc'">已完成提升课程列表</s:elseif>
        <s:elseif test="ctype=='jczx'">在学基础课程列表</s:elseif>
        <s:elseif test="ctype=='tszx'">在学提升课程列表</s:elseif>
        </td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"> &nbsp;<!--<a href="/entity/workspaceStudent/bzzstudent_toLearningCourses.action"><img border="0" src="/entity/bzz-students/images/two/basic_course2.jpg" width="124" height="25" /></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_tishengcourse.action"><img border="0" src="/entity/bzz-students/images/two/tisheng_course.jpg" width="124" height="25" /></a>
           --></td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop" align="center">
          <tr>
          <td width="5%" align="center"></td>
            <td width="35%" align="left">课程名称</td>
            <td width="10%" align="left">学习要求</td>
            <td width="20%" align="left">学习进度</td>
            <td width="14%" align="center">课程学习</td>
            <td width="16%" align="center">详细学习记录</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
           <s:if test="comlist.size()> 0">
     	 <s:iterator value="comlist" status="stuts" >
          <tr>
            <td width="5%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="35%"><s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getName()"/></td>
             <td width="10%" align="left"><s:if test="prBzzTchOpencourse.enumConstByFlagCourseType.name=='基础课程'"><font color=red>&nbsp;&nbsp;必修/考试</font></s:if><s:else>
             <font color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选&nbsp;&nbsp;&nbsp;修&nbsp;</font>
             </s:else></td>
            <td width="20%">
            <script language="javascript">
             	var selectC<s:property value="#stuts.index"/> =new CreatPro("selectC<s:property value="#stuts.index"/>",<s:property value="percent"/>,"",5000,1,10,"/entity/bzz-students/");selectC<s:property value="#stuts.index"/>.stepPro()
                            </script>
                            </td>
            <td width="14%">
             <input type="button" value="进入学习" <%if(disabled!=null&&disabled.equals("disabled")){%> disabled="<%=disabled %>" <%} %> onclick='window.open("<%=basePath %>/sso/bzzinteraction_InteractionStuManage.action?course_id=<s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getId()"/>&opencourseId=<s:property value="getPrBzzTchOpencourse().getId()"/>","newwindow");'></td>
            <td width="16%" align=center>
           <a href="/training/student/course/jumpCoursewareStatus.jsp?courseId=<s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getId()"/>"  onfocus="this.blur()"  class="button" target=_blank ><img src="/entity/bzz-students/images/two/5.jpg" width="59" height="22" border=0/></a>&nbsp;&nbsp;&nbsp;&nbsp;
             <%if(disabled!=null&&disabled.equals("disabled")){}else{ %><a href="/training/student/course/jumpCourseware.jsp?courseId=<s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getId()"/>&opencourseId=<s:property value="getPrBzzTchOpencourse().getId()"/>"  class="button" title="播放课件" target=_blank><img src="/entity/bzz-students/images/two/6.jpg" width="21" height="19"  border=0/></a><%} %></td>
          </tr>
          <tr>
            <td colspan="6"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          </s:iterator>
       		      <tr align="center">
         		<td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg" colspan="8">
         			<table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
         					<tr>
         						<td width="20%"><s:if test="pagedown!=-1"><a href="/entity/workspaceStudent/stuPeBulletinView_DetailCourse.action?ctype=<s:property value='ctype'/>&pagenow=1&pageSize =<s:property value ='pageSize'/>" >首  页</a></s:if><s:else> &nbsp; </s:else></td>
         						<td width="20%"><s:if test="pagedown!=-1"><a href="/entity/workspaceStudent/stuPeBulletinView_DetailCourse.action?ctype=<s:property value='ctype'/>&pagenow=<s:property value ='pagedown'/>&pageSize=<s:property value ='pageSize'/>">上一页</a></s:if></td>
         						<td width="20%"><s:if test="pagenext!=-1"><a href="/entity/workspaceStudent/stuPeBulletinView_DetailCourse.action?ctype=<s:property value='ctype'/>&pagenow=<s:property value ='pagenext'/>&pageSize=<s:property value ='pageSize'/>">下一页</a></s:if></td>
         						<td width="20%"><s:if test="pagenext!=-1"><a href="/entity/workspaceStudent/stuPeBulletinView_DetailCourse.action?ctype=<s:property value='ctype'/>&pagenow=<s:property value ='pagetotal'/>&pageSize=<s:property value='pageSize'/>">末 页</a></s:if></td>
         						<td width="20%">共&nbsp;<s:property value ='pagetotal'/>&nbsp;页/&nbsp;<s:property value ='pagecount'/>&nbsp;条</td>
         					</tr>
         			</table>
         		</td>
         	</tr>
      </s:if><s:else>
      <tr>
       <td width="752" colspan="5"  align="center">&nbsp;</td>
      </tr>
      <tr>
       <td width="752" colspan="5"  align="center">&nbsp;</td>
      </tr>
      <tr>
       <td width="752" colspan="5"  align="center">很抱歉!暂时没有相关数据!</td>
      </tr>
      <tr>
       <td width="752" colspan="5"  align="center">&nbsp;</td>
      </tr>
      <tr>
       <td width="752" colspan="5"  align="center">&nbsp;</td>
      </tr>
      </s:else>
          </table>
          </td>
      </tr>
         	<tr>
       <td width="752" colspan="5" align="center">&nbsp;</td>
      </tr>
      <tr>
       <td width="752" colspan="5" align="center"><input type="button" value="返  回" onclick="javascript:window.location='stuPeBulletinView_getFirstinfo.action';"></td>
      </tr>
      
        </table></td>
    <td width="13"></td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>