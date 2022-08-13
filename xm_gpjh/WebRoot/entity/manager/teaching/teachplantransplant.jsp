<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>教学计划复制</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		
		<script>
		Ext.onReady(function(){
		
			//层次下拉列表
/*			var edutype = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeEdutype'
		        }),
		
		        // create reader that reads the Topic records
		        reader: new Ext.data.JsonReader({
		            root: 'models',
		            id: 'id',
		            fields: [
		               	'id','name' ]
		        }),
		
		        // turn on remote sorting
		        remoteSort: true
		    }),
		        valueField: 'id',
		        displayField:'name',
		        typeAhead: true,
		        fieldLabel: '<@s.text name="层次"/>',
		         name:'edutypename',
		         id:'edutype1',
		        triggerAction: 'all',
		        emptyText:'所有层次',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    edutype.render('showedutype');
		    
		    //专业
				var major = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeMajor'
		        }),
		
		        // create reader that reads the Topic records
		        reader: new Ext.data.JsonReader({
		            root: 'models',
		            id: 'id',
		            fields: [
		               	'id','name' ]
		        }),
		
		        // turn on remote sorting
		        remoteSort: true
		    }),
		        valueField: 'id',
		        displayField:'name',
		        typeAhead: true,
		        fieldLabel: '专业',
		         name:'majorname',
		         id:'major1',
		        triggerAction: 'all',
		        emptyText:'所有专业',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    major.render('showmajor');
*/		    
		    //年级
				var grade = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeGrade'
		        }),
		
		        // create reader that reads the Topic records
		        reader: new Ext.data.JsonReader({
		            root: 'models',
		            id: 'id',
		            fields: [
		               	'id','name' ]
		        }),
		
		        // turn on remote sorting
		        remoteSort: true
		    }),
		        valueField: 'id',
		        displayField:'name',
		        typeAhead: true,
		        fieldLabel: '年级',
		         name:'grade',
		         id:'grade1',
		        triggerAction: 'all',
		        emptyText:'请选择年级',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    grade.render('showgrade'); 
			
		});
		
function check() {
	if(document.getElementById("grade1").value=="请选择年级") {
		alert("请选择年级");
		return;
	}
	document.plantform.submit();
}

		
		</script>
	</head>
	<body>
		<form name="plantform" id= "plantform" method="post" action="/entity/teaching/peTeaPlan_plantExe.action?ids=<s:property value="bean.id"/> " >
			<div id="main_content">
			   <div class="content_title">教学计划复制</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">请选择目标年级</div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td>
	 		   				<div align="center" class="postFormBox"><span class="name"><label>层次</label></span></div>	
			   			</td>
			   			<td>
							<div align="left" class="postFormBox">
							<label align="center"><s:property value="bean.peEdutype.name"/> </label> 
							</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="center" class="postFormBox"><span class="name"><label>专业</label></span></div>
			   			</td>
			   			<td>
							<div align="left" class="postFormBox">
								<label align="center"><s:property value="bean.peMajor.name"/> </label>
							</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="center" class="postFormBox"><span class="name"><label>请选择年级</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox" id = "showgrade"></div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="button" value="提交" onclick="check()"/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="button" value="返回" onclick="window.history.go(-2)"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>