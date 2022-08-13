<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div id="main_content">
	<div class="content_title">成绩查询 </div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="table_bg1">
    <td>课程编号</td>
    <td>课程名称</td>
    <td>学分</td>
    <td>学期</td>
    <td>成绩</td>
  </tr>
  <s:if test="stuelectivepage.getTotalCount() > 0">
      <s:iterator value="stuelectivepage.getItems()" status="stuts">
	  	<s:if test="#stuts.odd == true">
			<tr class="table_bg2">
		</s:if>
		<s:else>
			<tr>
		</s:else>
		   <td height=25px  align=center><s:property value="getPrTchOpencourse().getPeTchCourse().getCode()"/>&nbsp;</td>
	       <td height=25px align=center><a href="/entity/student/studentstudio_getCourseInfo.action?courseId=<s:property value="getPrTchOpencourse().getPeTchCourse().getId()"/>" target=_new><s:property value="getPrTchOpencourse().getPeTchCourse().getName()"/>&nbsp;</a></td>
	       <td height=25px  align=center><s:property value="getPrTchOpencourse().getPeTchCourse().getCredit()"/>&nbsp;</td>
	       <td height=25px  align=center><s:property value="getPrTchOpencourse().getPeExamno().getPeSemester().getName()"/>&nbsp;</td>
	       <td height=25px  align=center><s:if test="getScoreTotal() == null">未录入</s:if><s:else><s:property value="getScoreTotal()"/></s:else></td>
	     </tr>
	   </s:iterator>
	</s:if>
  <tr class="table_bg1">
    <td colspan="5">
    <table border="0" align="right" cellpadding="0" cellspacing="5">
      <tr>
      		<%@ include file="/entity/student/pub/scoreinfo_dividepage.jsp" %>
      </tr>
    </table></td>
    </tr>
</table>
    </div>
  </div>
</div>
</body>
</html>
