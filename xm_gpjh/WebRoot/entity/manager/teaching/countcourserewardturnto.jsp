<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>课酬核算</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		
		<script>
		Ext.onReady(function(){
		
			var startDate = new Ext.form.DateField({
			        fieldLabel: '起始日期',           
			        name: 'startDate',
			        id:'startDate',
			        format:'Y-m-d',
			        allowBlank:false,
			        editable: false,
			        anchor: '29%'   
			    }); 
			    
			startDate.render('showStartDate'); 
			
			var endDate = new Ext.form.DateField({
			        fieldLabel: '截止日期',           
			        name: 'endDate',
			        id:'endDate',
			        format:'Y-m-d',
			        allowBlank:false,
			        editable: false,
			        anchor: '29%'   
			    }); 
			    
			endDate.render('showEndDate'); 
			
		    });
		</script>
	</head>
	<body>
		<form name="print" id= "print" method="get" action="/entity/teaching/countcoursereward.action">
			<div id="main_content">
			   <div class="content_title">课酬核算</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">课酬核算---选择条件</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择起始日期</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id="showStartDate"></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择截止日期</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id = "showendDate"></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" value="提交"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>