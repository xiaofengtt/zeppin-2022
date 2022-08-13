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
				if(document.getElementById("combo-box-edutypes").value == "") {
					alert("请选择层次");
					document.getElementById("combo-box-edutypes").focus();
					return false;					
				}
				if(document.getElementById("combo-box-majors").value == "") {
					alert("请选择专业");
					document.getElementById("combo-box-majors").focus();
					return false;
				}
				if(document.getElementById("combo-box-grades").value == "") {
					alert("请选择年级");
					document.getElementById("combo-box-grades").focus();
					return false;
				}
				return true;
			}
			
			function singleElective(){
				if(document.getElementById('regNo').value == '' ){
					alert("请输入学号!");
					return false;
				}
			}
			
		</script>			
		<script>
			Ext.onReady(function(){	
		
			//生成学习中心下拉列表
			var siteDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeSite'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    siteDataStore.setDefaultSort('id', 'asc');
		    siteDataStore.load();
			
			var siteCombo = new Ext.form.ComboBox({
				store: siteDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-sites',
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
			
			//生成年级下拉列表
			 var gradeStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeGrade'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		   
		    gradeStore.setDefaultSort('id', 'asc');
		    gradeStore.load();
		    var gradeCombo = new Ext.form.ComboBox({
				//el:'combo-box-grades',
				store: gradeStore,
				displayField:'name',
				valueField: 'id',
				typeAhead: true,
			//	mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-grades',
				editable: false,
				forceSelection: true,	//True to restrict the selected value to one of the values in the list, false to allow the user to set arbitrary text input
				selectOnFocus:true
			});
			
			});
		</script>
	</head>
	<body>
		<div id="main_content">
		   <div class="content_title">代学生选课</div>
		   <div class="cntent_k" align="center">
		   <div class="k_cc">
		   <form name="usual" method="get" action="/entity/teaching/elelctiveManage.action" onsubmit="javascript:return pageGuarding();">
		   <table width="80%">
		   		<tr>
		   			<td colspan="2">
		   				<div align="center" class="postFormBox">请选择条件</div>
		   			</td>
		   		</tr>			   					   		
		   		<tr>
		   			<td>
		   				<div align="left" class="postFormBox"><span class="name"><label>学习中心</label></span></div>
		   			</td>
		   			<td>
		   				<div align="left" class="postFormBox"><input type="text" id="combo-box-sites" name="bean.peSite.name" size="24"/></div>
		   			</td>
		   		</tr>
			   	<tr>
		   			<td>
		   				<div align="left" class="postFormBox"><span class="name"><label>层次 *</label></span></div>
		   			</td>
		   			<td>
		   				<div align="left" class="postFormBox"><input type="text" id="combo-box-edutypes" name="bean.peEdutype.name" size="24"/></div>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				<div align="left" class="postFormBox"><span class="name"><label>专业 *</label></span></div>
		   			</td>
		   			<td>
		   				<div align="left" class="postFormBox"><input type="text" id="combo-box-majors" name="bean.peMajor.name" size="24"/></div>
		   			</td>
		   		</tr>	
		   		<tr>
		   			<td>
		   				<div align="left" class="postFormBox"><span class="name"><label>年级 *</label></span></div>
		   			</td>
		   			<td>
		   				<div align="left" class="postFormBox"><input type="text" id="combo-box-grades" name="bean.peGrade.name" size="24"/></div>
		   			</td>
		   		</tr>   				
   				<tr>
		   			<td colspan="2">
		   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交" /></div>
		   			</td>
		   		</tr>
		   </table>
		   </form>
		   <br/><br/><br/>
		 	 <form name="single" method="get" action="/entity/teaching/elelctiveManage.action" onsubmit="javascript:return singleElective();">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">单个为学生选课(精确匹配)</div>
			   			</td>
			   		</tr>			   					   		
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>学号* </label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="bean.regNo" id="regNo" size="24"/></div>
			   			</td>
			   		</tr>	
	   				<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交" /></div>
			   			</td>
			   		</tr>
			   </table>
			  </form>
		   </div>
	   </div>
	   </div>
		
	</body>
</html>
