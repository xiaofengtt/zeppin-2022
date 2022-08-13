<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>统计考试录取人数</title>
		<%
			String path = request.getContextPath();
		%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript"
			src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>

		<script>
		Ext.onReady(function(){
		
			//生成学习中心下拉列表
			var studycenter = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeSite'
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
		        fieldLabel: '<@s.text name="学习中心"/>',
		         name:'siteName',
		         id:'siteName',
		        triggerAction: 'all',
		        emptyText:'',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    studycenter.render('showstudeycenter');
			
			//层次下拉列表
			var edutype = new Ext.form.ComboBox({
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
		         name:'edutypeName',
		         id:'edutypeName',
		        triggerAction: 'all',
		        emptyText:'',
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
		         name:'majorName',
		         id:'majorName',
		        triggerAction: 'all',
		        emptyText:'',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    major.render('showmajor');
		    });
		</script>
<script> 
	function showcheck(){ 
	var flag=true;
	var info='';
		if(document.getElementById('edutypeName').value=='' || document.getElementById('edutypeName').value.trim() == ''){
			flag=false;
			info+='层次不能为空!\n';
		}
		if(document.getElementById('majorName').value=='' || document.getElementById('majorName').value.trim() == ''){
			flag=false;
			info+='专业不能为空!\n';
		}
		if(!flag){
		alert(info);
		}
		return flag;
   }
   
   </script>
	</head>
	<body>
		<form name="print" id="print" method="get"
			action="/entity/recruit/examresultcalculate_turnToConditionSet.action"
			onsubmit="return showcheck();">
			<div id="main_content">
				<div class="content_title">
					统计考试录取人数
				</div>
				<div class="cntent_k" align="center">
					<div class="k_cc">
						<table width="80%">
							<tr>
								<td colspan="2">
									<div align="center" class="postFormBox">
										统计考试录取人数---选择条件
									</div>
								</td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td>
									<div align="left" class="postFormBox">
										<span class="name"><label>
												请选择学习中心
											</label>
										</span>
									</div>
								</td>
								<td>
									<div align="left" class="postFormBox" id="showstudeycenter"></div>
								</td>
							</tr>
							<tr>
								<td>
									<div align="left" class="postFormBox">
										<span class="name"><label>
												请选择层次
												<font color="#ff0000">*</font>
											</label>
										</span>
									</div>
								</td>
								<td>
									<div align="left" class="postFormBox" id="showedutype"></div>
								</td>
							</tr>
							<tr>
								<td>
									<div align="left" class="postFormBox">
										<span class="name"><label>
												请选择专业
												<font color="#ff0000">*</font>
											</label>
										</span>
									</div>
								</td>
								<td>
									<div align="left" class="postFormBox" id="showmajor"></div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div align="center" class="postFormBox">
										<input type="submit" value="提交" />
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