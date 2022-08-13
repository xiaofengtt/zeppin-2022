<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教学计划</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>
<div id="main_content">
	<div class="content_title">毕业论文重修申请</div>
<div class="student_cntent_k">
    	<div class="k_cc">

<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">毕业论文重修申请</font>
	  </td>
    </tr>
</table>

<s:if test="message==null">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
    
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="left">
			您已满足毕业论文重修申请条件，可以提交申请：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type='button' value="提交申请" onclick="location.href('/entity/workspaceStudent/apply_applyPaperReapply.action?');" />
			</div>
			<div align="left">
			重修申请后请重新选题，不用再选毕业论文课程。请谨慎。
			</div>
		</td>
	</tr>
 
</table>
</s:if><s:else>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="left">
			<s:property value="message"/>
			</div>
		</td>
	</tr>
 
</table>
</s:else>
    </div>
    </div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 已经提交的申请</td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="35%"  align=center>申请时间</td>
      <td height=25px width="30%" align=center>审核状态</td>
      <td height=25px width="35%"  align=center>审核时间</td>
    </tr>
<s:iterator value="applys" status="n">	
   <s:if test="#n.index== 0 "><tr class="table_bg2" >
      <td height=25px width="35%"  align=center><s:date name="applyDate" format="yyyy-MM-dd"/>&nbsp;</td>
      <td height=25px width="30%" align=center><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
      <td height=25px width="35%"  align=center><s:date name="checkDate" format="yyyy-MM-dd"/>&nbsp;</td>
  </tr>
  </s:if>
   </s:iterator>    
    
</table>    
</div>
</div>     
</div>
</body>
</html>
    