<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script>
		Ext.onReady(function(){
		
			//生成专业下拉列表
			var majorDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeMajor'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    majorDataStore.setDefaultSort('id', 'asc');
		    majorDataStore.load();
			
			var majorCombo = new Ext.form.ComboBox({
				store: majorDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择专业...',
				applyTo: 'combo-box-majors',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
			
			//生成层次下拉列表
			var edutypeDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeEdutype'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    edutypeDataStore.setDefaultSort('id', 'asc');
		    edutypeDataStore.load();
			
			var edutypeCombo = new Ext.form.ComboBox({
				store: edutypeDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择层次...',
				applyTo: 'combo-box-edutypes',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
			
			//生成年级下拉列表
			var gradeDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeGrade'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    gradeDataStore.setDefaultSort('id', 'asc');
		    gradeDataStore.load();
			
			var gradeCombo = new Ext.form.ComboBox({
				store: gradeDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择年级...',
				applyTo: 'combo-box-grades',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
		});
		
		function checkAll() {
			if(document.getElementById('combo-box-majors').value=="" || document.getElementById('combo-box-majors').value=="请选择专业...") {
				alert('请选择专业！');
				return;
			}
			if(document.getElementById('combo-box-edutypes').value=="" || document.getElementById('combo-box-edutypes').value=="请选择层次...") {
				alert('请选择层次！');
				return;
			}
			if(document.getElementById('combo-box-grades').value=="" || document.getElementById('combo-box-grades').value=="请选择年级...") {
				alert('请选择年级！');
				return;
			}
			document.getElementById("electiveforstudent").submit();
		}
				
		</script>
	</head>
	<body>
		<form name="score2" id="electiveforstudent" method="get" action="/entity/teaching/elelctiveManage.action">
			<div id="main_content">
			   <div class="content_title">代学生选课</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择专业</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-majors" name="bean.peMajor.name" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择层次</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-edutypes" name="bean.peEdutype.name" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择年级</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-grades" name="bean.peGrade.name" size="24"/></div>
			   			</td>
			   		</tr>
			   				   		
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="button" value="提交" onclick="checkAll()"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>		
	</body>
</html>