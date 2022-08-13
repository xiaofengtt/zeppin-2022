<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考务公告</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>
<div id="main_content">
<div class="content_title">考务公告</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  	<tr class="table_bg1">
			   		<td height="17" >&nbsp;&nbsp;<font color="#000000">考 试 安 排</font></td>
			  	</tr>
				<tr> 
				<td >
					<table border=0 cellPadding=6 cellSpacing=1 width="100%">
						<tr class="table_bg1"> 
						<td height=25  align=center >课程编号</td>
						<td height=25  align=center >课程名称</td>
						<td height=25  align=center >考试日期</td>
						<td height=25  align=center >考试地址</td>
						<td height=25  align=center >考场号</td>
						<td height=25  align=center >座位号</td>
						<td height=25  align=center >申请缓考</td>
						</tr> 
						<s:if test="stuelectivepage.getTotalCount() > 0">
						  <s:iterator value="stuelectivepage.getItems()" status="stuts">
						  	<s:if test="#stuts.odd == true">
								<tr class="table_bg2">
							</s:if>
							<s:else>
								<tr>
							</s:else>
								<td height=25  align=center><s:property value="getPrExamplanStu().getPrTchStuElective().getPrTchOpencourse().getPeTchCourse().getCode()"/>&nbsp;</td>
								<td height=25  align=center><s:property value="getPrExamplanStu().getPrTchStuElective().getPrTchOpencourse().getPeTchCourse().getName()"/>&nbsp;</td>
								<td height=25  align=center><s:property value="getExamTime()"/>&nbsp;</td>
								<td height=25  align=center><s:property value="getExamAddress()"/>&nbsp;</td>
								<td height=25  align=center><s:property value="getRoomNo()"/>&nbsp;</td>
								<td height=25  align=center><s:property value="getSeatNo()"/>&nbsp;</td>
								<td height=25  align=center><a href="#">申请缓考</a></td>
							</tr>
						  </s:iterator>
						 </s:if>
						<tr class="table_bg1">
							<td colspan="10">
								<table border="0" align="center" cellpadding="0" cellspacing="5">
								  <tr>
									<%@ include file="/entity/student/pub/examinfo_dividepage.jsp" %>
								  </tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
</div>
</body>
</html>

