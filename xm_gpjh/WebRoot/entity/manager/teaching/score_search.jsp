<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
		<title>选择条件</title>
		
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script Language="JavaScript">
			function pageGuarding() {
				if(document.getElementById("combo-box-semesters").value == "") {
					alert("请选择学期");
					document.getElementById("combo-box-semesters").focus();
					return false;					
				}
				if(document.getElementById("combo-box-courses").value == "") {
					alert("请选择课程");
					document.getElementById("combo-box-courses").focus();
					return false;
				}
				return true;
			}
		</script>			
		<script>
			Ext.onReady(function(){	
		
			//生成学期下拉列表
			var semesterDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeSemester'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    semesterDataStore.setDefaultSort('id', 'asc');
		    semesterDataStore.load();
			
			var semesterCombo = new Ext.form.ComboBox({
				store: semesterDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-semesters',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
			
			//生成课程下拉列表
			 var courseDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeTchCourse'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		   
		    courseDataStore.setDefaultSort('id', 'asc');
		    courseDataStore.load();
		    var courseCombo = new Ext.form.ComboBox({
				//el:'combo-box-grades',
				store: courseDataStore,
				displayField:'name',
				valueField: 'id',
				typeAhead: true,
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-courses',
				editable: false,
				forceSelection: true,	//True to restrict the selected value to one of the values in the list, false to allow the user to set arbitrary text input
				selectOnFocus:true
			});
			
			
			});
		</script>
	</head>
	<body>
		<form name="usual" method="get" action="/entity/teaching/scoreSearch.action" onsubmit="javascript:return pageGuarding();">
			<div id="main_content">
			   <div class="content_title">查看成绩</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">请选择课程</div>
			   			</td>
			   		</tr>			   					   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>学期*</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-semesters" name="bean.prTchOpencourse.peSemester.name" size="24"/></div>
			   			</td>
			   		</tr>
				   	<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>课程*</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-courses" name="bean.prTchOpencourse.peTchCourse.name" size="24"/></div>
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
