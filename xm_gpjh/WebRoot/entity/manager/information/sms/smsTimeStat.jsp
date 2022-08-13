<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��������ͳ��</title>
		
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		
		<script>
		Ext.onReady(function(){

		var startDate = new Ext.form.DateField({
			        fieldLabel: '��ʼ����',           
			        name: 'start',
			        format:'Y-m-d',
			        allowBlank:false,
			        anchor: '29%'   
			    }); 
			startDate.render('startDate');
			
			var endDate = new Ext.form.DateField({
			        fieldLabel: '��ֹ����',           
			        name: 'end',
			        format:'Y-m-d',
			        allowBlank:false,
			        anchor: '29%'   
			    }); 
			    
			endDate.render('endDate');
		
		});
		
		
		</script>
	</head>
	<body>
		<form name="print" id= "print" method="get" action="/entity/information/peSmsStatistic.action">
			<div id="main_content">
			   <div class="content_title">短信统计</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">请选择时间段</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>开始时间</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id = "startDate"></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>截止时间</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id = "endDate"></div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" value="统计"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>