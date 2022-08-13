<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作结果</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="main_content">
	<div class="content_title">您当前的位置是：学生<s:property value="peStudent.getTrueName()"/>的工作室<span class="table_bg1">操作结果</span></div>
    <div class="student_cntent_k">
    	<div class="k_cc">
    <form action="/entity/student/studentstudio_passwordedit.action" name="pass_chg" onsubmit='return userAddGuarding()'>
		<table width="100%" border="0">
  <tr>
    <td class="table_bg1"><div align="center"><s:property value="operateresult" escape="<br>"/>&nbsp;</div></td>
  </tr>
 <tr>
 
    <td class="table_bg2"><div align="center">
    							<s:if test="getTogo()!=null">
								<input type=button 								
								<s:if test="getTogo()==\"back\"">
									value="<s:text name="test.back"/>" onclick="history.back();"
								</s:if>
								<s:else>
									value="<s:text name="test.confirm"/>" onclick="window.location='<s:property value="getTogo()" escape="false"/>';"
								</s:else>
								>
							</s:if>
							<s:else>
							<input type="button" onclick="window.history.back();" value="返回"/>
							</s:else>&nbsp;</div></td>
  </tr>
</table>

	</form>
    </div>
  </div>
</div>
</body>
</html>
