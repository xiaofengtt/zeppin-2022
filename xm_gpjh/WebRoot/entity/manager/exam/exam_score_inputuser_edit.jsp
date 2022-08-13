<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>课程登分人信息修改</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript">
		Ext.onReady(function(){
			var useraDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		           url: "/test/myList.action?sql=select id,name from pe_exam_score_input_user where name like '%25a' order by name"
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    useraDataStore.load();
			
			var useraCombo = new Ext.form.ComboBox({
				store: useraDataStore,
				displayField:'name',
				valueField: 'id' ,
				id:'useraname',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-usera',
				editable: true,
				forceSelection: true,
				selectOnFocus:true,
				allowBlank:false
			});
			
			var userbDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select id,name from pe_exam_score_input_user where name like '%25b' order by name "
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    userbDataStore.load();
			
			var userbCombo = new Ext.form.ComboBox({
				store: userbDataStore,
				displayField:'name',
				valueField: 'id' ,
				id:'userbname',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-userb',
				editable: true,
				forceSelection:true,
				selectOnFocus:true,
				allowBlank:false
			});	
			
			useraCombo.on('select', function() {
				var a = document.getElementById("combo-box-usera").value;
				var b = a.substr(0,a.length-1)+"b";
				userbCombo.setValue(b);
			});
			
			userbCombo.on('select', function() {
				var b = document.getElementById("combo-box-userb").value;
				var a = b.substr(0,b.length-1)+"a";
				useraCombo.setValue(a);
			});
		});
		</script>
	</head>
  <body>
    <form name="final" method="get" action="/entity/exam/prexamscoreinputuser_editexe.action" >
    <input type="hidden" name="bean.id" value='<s:property value="bean.id"/>' />
	  <div id="main_content">
		 <div class="content_title">课程登分人信息修改</div>
		 <div class="cntent_k" align="center">
			 <div class="k_cc">
			   <table width="80%">
		   		 <tr>
		   			<td colspan="2">
		   				<div align="center" class="postFormBox">课程登分人信息修改</div>
		   			</td>
		   		 </tr>
		   		 <tr>
		   			<td width="18%" nowrap="nowrap">
		   				<div align="left" class="postFormBox"><span class="name">科目编号：<br /></span></div>
	   			  </td>
		   			<td width="82%">
		   				<div align="left" class="postFormBox"><s:property value="bean.code"/></div>
	   			  </td>
		   		 </tr>
		   		 <tr>
		   			<td width="18%" nowrap="nowrap">
		   				<div align="left" class="postFormBox"><span class="name">科目名称：<br /></span></div>
	   			  </td>
		   			<td width="82%">
		   				<div align="left" class="postFormBox"><s:property value="bean.name"/></div>
	   			  </td>
		   		 </tr>
		   		 <tr>
		   			<td width="18%" nowrap="nowrap">
		   				<div align="left" class="postFormBox"><span class="name">登分帐户A：<br /></span></div>
	   			  </td>
		   			<td width="82%">
		   				<div align="left" class="postFormBox">
		   					<input type="text"  name="bean.peExamScoreInputUserByFkExamScoreInputUseraId.name" value="<s:property value='bean.peExamScoreInputUserByFkExamScoreInputUseraId.name'/>" id="combo-box-usera" />
		   				</div>
	   			  </td>
		   		 </tr>
		   		 <tr>
		   			<td width="18%" nowrap="nowrap">
		   				<div align="left" class="postFormBox"><span class="name">登分帐户B：<br /></span></div>
	   			  </td>
		   			<td width="82%">
		   				<div align="left" class="postFormBox">
		   					<input type="text" name="bean.peExamScoreInputUserByFkExamScoreInputUserbId.name" value="<s:property value='bean.peExamScoreInputUserByFkExamScoreInputUserbId.name'/>" id="combo-box-userb" />
		   				</div>
	   			  </td>
		   		 </tr>
		   		 <tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交" /></div>
			   			</td>
			   	 </tr>
		   		 <tr>
			   			<td colspan="2">
			   				<div id="test" ></div>
			   			</td>
			   	 </tr>
			   </table>
			 </div>
	     </div>
	  </div>
	</form>
  </body>
</html>
