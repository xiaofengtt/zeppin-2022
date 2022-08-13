<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>
<div id="main_content">
	<div class="content_title">您当前的位置是：学生<s:property value="peStudent.getTrueName()"/>的工作室 > 费用明细查询</div>
  <div class="student_cntent_k">
    	<div class="k_cc">
  您现在的费用余额：<s:property value="peStudent.getFeeBalance()"/>元
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="table_bg1">
    <td>费用类型</td>
    <td>费用额</td>
    <td>日期</td>
  </tr>
 <s:if test="prFeeDetailList.size > 0">
  <s:iterator value="prFeeDetailList" status="stuts">
  	<s:if test="#stuts.odd == true">
		<tr class="table_bg2">
	</s:if>
	<s:else>
		<tr>
	</s:else>
		<td><s:property value="getEnumConstByFlagFeeType().getName()"/></td>
		<td><s:property value="getFeeAmount()"/></td>
		<td><s:date name="getInputDate()" format="yyyy年MM月dd日 HH时mm分"/> </td>
	</tr>
  </s:iterator>
 </s:if>
  <tr class="table_bg1">
    <td colspan="7">
    <table  align="right" cellpadding="0" cellspacing="5">
      <tr>
        	<%@ include file="/entity/student/pub/studentFeeDetail_dividepage.jsp" %>
      </tr>
    </table></td>
</tr>
</table>

</body>
</html>
