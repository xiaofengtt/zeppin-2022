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
		            url: '/entity/student/generatecombo_getmajors.action'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'majors'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    majorDataStore.setDefaultSort('id', 'asc');
		    //majorDataStore.load();
			
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
		            url: '/entity/student/generatecombo_getedutype.action'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'edutype'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    edutypeDataStore.setDefaultSort('id', 'asc');
		   // edutypeDataStore.load();
			
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
		            url: '/entity/student/generatecombo_getgrades.action'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'grades'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    gradeDataStore.setDefaultSort('id', 'asc');
		    //gradeDataStore.load();
			
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
					
		</script>
	</head>
	<body>
		<form name="score2" method="get" action="/entity/fee/studentAccount.action">
			<div id="main_content">
			   <div class="content_title">学生个人账户查询</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">		   		
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择专业</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-majors" name="majorName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择层次</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-edutypes" name="edutypeName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>请选择年级</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-grades" name="gradeName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>是否毕业</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<select name="graduationStatus">
			   					<option value="请选择是否毕业...">请选择是否毕业...</option>
			   					<option value="未毕业">未毕业</option>
        						<option value="已毕业">已毕业</option>
			   				</select>
			   				</div>
			   			</td>
			   		</tr>

			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>是否欠费</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox">
			   				<select name="feeStatus">
			   					<option value="请选择是否欠费...">请选择是否欠费...</option>
			   					<option value="0">否</option>
        						<option value="1">是</option>
			   				</select>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>学号</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="regNo" size="19"/></div>
			   			</td>
			   		</tr>			   		
			   					   		
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交" /></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>		
	</body>
</html>