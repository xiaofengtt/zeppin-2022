<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>复制招生计划</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript"
			src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>
		<script type="text/javascript">
		Ext.onReady(function(){
			//源招生计划下拉列表
			var plan1 = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeRecruitplan'
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
		        fieldLabel: '<@s.text name="招生计划"/>',
		         name:'plan1name',
		         id:'plan1',
		        triggerAction: 'all',
		        emptyText:'请选择源招生计划',
		        editable: true,
		        selectOnFocus:true
		    });
		    
		    plan1.render('showplan1');
			//目标招生计划下拉列表
			var plan2 = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeRecruitplan'
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
		        fieldLabel: '<@s.text name="考试场次"/>',
		         name:'plan2name',
		         id:'plan2',
		        triggerAction: 'all',
		        emptyText:'请选择目标招生计划',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    plan2.render('showplan2');

		});		
		function check() {
				if(document.getElementById("plan1").value=="请选择源招生计划"||document.getElementById("plan1").value=='') {
					alert('请选择源招生计划！');
						return false;					
				}
				if(document.getElementById("plan2").value=="请选择目标招生计划"||document.getElementById("plan2").value=='') {
					alert('请选择目标招生计划！');
						return false;					
				}	
				if(document.getElementById("plan1").value==document.getElementById("plan2").value)	{
					alert('源招生计划和目标招生计划不能相同！');
						return false;					
				}		
				return true;
			}
		</script>
	</head>
	<body>
		<div id="main_content">
			<div class="content_title">
				复制招生计划
			</div>
			<div class="cntent_k" align="center">
				<div class="k_cc">
					<form action="/entity/recruit/prRecPlanMajorEdutype_copyPlan.action"
						onsubmit="return check();" >
						<input type="hidden" name="printType" value="seatNo" />
						<table width="554" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="26" align="center" valign="middle"></td>
							</tr>
							<tr>
								<td height="8">
								</td>
							</tr>
							<tr valign="middle">
								<td width="200" height="90" align="left" class="postFormBox">
									<span class="name">*源招生计划：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">

									<div align="left" class="postFormBox" id="showplan1"></div>
								</td>
							</tr>
							<tr valign="middle">
								<td width="200" height="90" align="left" class="postFormBox">
									<span class="name">*目标招生计划：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">

									<div align="left" class="postFormBox" id="showplan2"></div>
								</td>
							</tr>
							<tr valign="middle">
								<td width="200" height="60" align="left" class="postFormBox"></td>
								<td class="postFormBox" align="left" style="padding-left: 18px">
									<input type="submit" value="确定" />
								</td>
							</tr>

							<tr>
								<td height="10">
								</td>
							</tr>
						</table>

					</form>
				</div>
			</div>
		</div>
	</body>
</html>