<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="test.info"/></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		<form name="print" >
			<div id="main_content">
			   <div class="content_title"><s:text name="test.info"/></div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">

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

							</div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>
