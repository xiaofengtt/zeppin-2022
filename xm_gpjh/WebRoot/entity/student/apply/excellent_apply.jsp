<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评优申请</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script>

</script>
</head>

<body>
<form action="/entity/workspaceStudent/applyExcellent_excellent.action" >
<input type="hidden" name="bean.id" value="<s:property value="bean.id" />" />
<div id="main_content">
	<div class="content_title">评优申请</div>
<div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">---评优申请</font>
	  </td>
    </tr>
</table>
<s:if test="message!=null">
<table width="100%" border="0" cellpadding="0" cellspacing="0">    
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="center">
			<s:property value="message" escape="false"/>&nbsp;	
			</div>
		</td>
	</tr>
</table>
</s:if>
<s:if test="apply">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div align="center" class="postFormBox"><input type="radio" checked="checked" value="goodStu" name="type" />申请学习积极分子 </div>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center" class="postFormBox"><input type="radio" value="cadreman" name="type"  />申请优秀学生干部 </div>
				</td>
			</tr>	
			<tr>
				<td>
					说明：申请学习积极分子必须所有课程成绩>=70分。
				</td>
			</tr>		
			<tr> 
				<td>
					<input TYPE='submit' value="提交申请" />
				</td>
			</tr>
			</table>
			</div>
		</td>
	</tr>
</table>
</s:if>

    </div>
    </div>
    <s:if test="applys!=null">
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 已经提交的申请</td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="25%" align=center>申请类型</td>    
      <td height=25px width="25%"  align=center>申请时间</td>
      <td height=25px width="25%" align=center>审核状态</td>
      <td height=25px width="25%"  align=center>审核时间</td>
    </tr>
  	<s:iterator value="applys">  
  <tr class="table_bg2" >
      <td height=25px width="25%" align=center><s:property value="enumConstByApplyType.name"/>&nbsp;</td>  
      <td height=25px width="25%"  align=center><s:date name="applyDate" format="yyyy-MM-dd"/>&nbsp;</td>
      <td height=25px width="25%" align=center><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
      <td height=25px width="25%"  align=center><s:date name="checkDate" format="yyyy-MM-dd"/>&nbsp;</td>

  </tr>
   </s:iterator>      
</table>    
</div>
</div>
</s:if>	
</div>
</form>
</body>
</html>
    