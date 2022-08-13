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
	//			if(document.getElementById("combo-box-edutypes").value == "") {
	//				alert("请选择层次");
	//				document.getElementById("combo-box-edutypes").focus();
	//				return false;					
	//			}
	//			if(document.getElementById("combo-box-majors").value == "") {
	//				alert("请选择专业");
	//				document.getElementById("combo-box-majors").focus();
	//				return false;
	//			}
				var reg = /^[0-9]+\.?[0-9]{0,3}$/;
				
				if(document.getElementById("combo-box-courses").value == "") {
					alert("请选择课程");
					document.getElementById("combo-box-courses").focus();
					return false;
				} 
				if(document.getElementById("examRate").value == "") {
					alert("请设置考试成绩比例");
					document.getElementById("examRate").focus();
					return false;
				} else if(! reg.test(document.getElementById("examRate").value)){
					alert("请输入数字。");
					return false;
				}
				if(document.getElementById("homeworkRate").value == "") {
					alert("请设置作业成绩比例");
					document.getElementById("homeworkRate").focus();
					return false;
				}else if(! reg.test(document.getElementById("homeworkRate").value)){
					alert("请输入数字。");
					return false;
				}
				if(document.getElementById("usualRate").value == "") {
					alert("请设置平时成绩比例");
					document.getElementById("usualRate").focus();
					return false;
				}else if(! reg.test(document.getElementById("usualRate").value)){
					alert("请输入数字。");
					return false;
				}
				if((parseFloat(document.getElementById("examRate").value) + parseFloat(document.getElementById("homeworkRate").value) + parseFloat(document.getElementById("usualRate").value)) != 100) {
					alert("三种成绩之和应该等于 100 。");
					return false;
				}
				return true;
			}
		</script>			
		<script>
			Ext.onReady(function(){	
		
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
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-grades',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
			
			//生成层次下拉列表
			 var edutypeStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeEdutype'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		   
		    edutypeStore.setDefaultSort('id', 'asc');
		    edutypeStore.load();
		    var edutypeCombo = new Ext.form.ComboBox({
				//el:'combo-box-grades',
				store: edutypeStore,
				displayField:'name',
				valueField: 'id',
				typeAhead: true,
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-edutypes',
				editable: false,
				forceSelection: true,	//True to restrict the selected value to one of the values in the list, false to allow the user to set arbitrary text input
				selectOnFocus:true
			});
			
			//生成专业下拉列表
			 var majorStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeMajor'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		   
		    majorStore.setDefaultSort('id', 'asc');
		    majorStore.load();
		    var majorCombo = new Ext.form.ComboBox({
				//el:'combo-box-grades',
				store: majorStore,
				displayField:'name',
				valueField: 'id',
				typeAhead: true,
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-majors',
				editable: false,
				forceSelection: true,	//True to restrict the selected value to one of the values in the list, false to allow the user to set arbitrary text input
				selectOnFocus:true
			});
			
			//生成课程下拉列表
			 var courseStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeTchCourse'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		   
		    courseStore.setDefaultSort('id', 'asc');
		    courseStore.load();
		    var courseCombo = new Ext.form.ComboBox({
				//el:'combo-box-courses',
				store: courseStore,
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
		<form name="usual" method="get" action="/entity/teaching/scorePercentSet_composeSingle.action" onsubmit="javascript:return pageGuarding();">
			<div id="main_content">
			   <div class="content_title">单独合成成绩。</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">请选择条件</div>
			   			</td>
			   		</tr>			   					   		
			   		
				   	<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>层次</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-edutypes" name="peEdutypeName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>专业</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-majors" name="peMajorName" size="24"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>年级</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-grades" name="peGradeName" size="24"/></div>
			   			</td>
			   		</tr>	
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>课程 *</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="combo-box-courses" name="peCourseName" size="24"/></div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>考试成绩比例 *</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="examRate" name="scoreExamRate" size="5"/>%</div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>作业成绩比例 *</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="homeworkRate" name="scoreHomeworkRate" size="5"/>%</div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>平时成绩比例 *</label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" id="usualRate" name="scoreUsualRate" size="5"/>%</div>
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
