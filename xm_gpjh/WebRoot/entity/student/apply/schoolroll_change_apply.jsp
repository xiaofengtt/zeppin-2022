<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教学计划</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 		
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
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
		        fieldLabel: '<@s.text name="层次"/>',
		         name:'semestername',
		         id:'sitename',
		        triggerAction: 'all',
		        emptyText:'所有学习中心',
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
		         name:'edutypename',
		         id:'edutypename',
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
		           url: "/test/myList.action?sql=select m.id,m.name from pr_stu_changeable_major t , pe_major m  where t.fk_old_major_id='<s:property value='peStudent.peMajor.id'/>' and t.fk_new_major_id = m.id"
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
		         id:'majorname',
		        triggerAction: 'all',
		        emptyText:'所有专业',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    major.render('showmajor');
		
		studycenter.disabled = false;
		studycenter.hide();
		edutype.disabled = true;
		edutype.hide();
		major.disabled = true;
		major.hide();
		
		Ext.get("a").addListener("click",function(){
				studycenter.disabled = false;
				studycenter.show();
				edutype.disabled = true;
				edutype.hide();
				major.disabled = true;
				major.hide();
		});
		Ext.get("b").addListener("click",function(){
				studycenter.disabled = true;
				studycenter.hide();
				edutype.disabled = false;
				edutype.show();
				major.disabled = true;
				major.hide();
		});
		Ext.get("c").addListener("click",function(){
				studycenter.disabled = true;
				studycenter.hide();
				edutype.disabled = true;
				edutype.hide();
				major.disabled = false;
				major.show();
		});
		
	});
function confirmChange() {
	var changeArr = document.getElementsByName('change');
	
	for(var i=0;i<changeArr.length;i++) {
		if(changeArr[i].checked == true) {
			if(changeArr[i].value == 'a') {
				if(document.getElementById("sitename").value == "所有学习中心") {
					alert("请选择要更换的学习中心。");
					return;
				}
				break;
			}
			if(changeArr[i].value == 'b') {
				if(document.getElementById("edutypename").value == "所有层次") {
					alert("请选择要更换的层次。");
					return;
				}
				break;
			}
			if(changeArr[i].value == 'c') {
				if(document.getElementById("majorname").value == "所有专业") {
					alert("请选择要更换的专业。");
					return;
				}
				break;
			}
		}
		if(i == changeArr.length-1) {
			alert("请选择学籍异动的类型。");
			return;
		}
	}

	if(window.confirm("您确定要更换学籍吗？") == true) {
		//var changeArr = document.getElementsByName('change');
		var changeValue;
		for(var i=0;i<changeArr.length;i++) {
			if(changeArr[i].checked == true) {
				if(changeArr[i].value == 'a') {
					changeValue = document.getElementById("sitename").value;
				} else if (changeArr[i].value == 'b') {
					changeValue = document.getElementById("edutypename").value;
				} else {
					changeValue = document.getElementById("majorname").value;
				}
				document.getElementById('changeType').value = changeArr[i].value;
				document.getElementById('changeValue').value = changeValue;
			}
		}
		window.changeform.submit();
	}
}
</script>
</head>

<body>
<form id="changeform" name="changeform" action="/entity/workspaceStudent/apply_changeStudent.action" method="post">
<input type="hidden" id="changeValue" name="changeValue"/>
<input type="hidden" id="changeType" name="changeType"/>
<div id="main_content">
	<div class="content_title">变更学籍申请</div>
<div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">变更学籍申请</font>		
	  </td>
    </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr class="table_bg1">
		<td height=25px  align=right width="30%" >学籍变更类型:</td>
		<td height=25px  align="right" width="70%" >
			<div align="left">
			<input name="change" id="a" type="radio" value="a"/> 变更学习中心<span class="postFormBox" id="showstudeycenter"></span></br>
			<input name="change" id="b" type="radio" value="b"/> 变更层次<span class="postFormBox" id="showedutype"></span></br>
			<input name="change" id="c" type="radio" value="c"/> 变更专业<span class="postFormBox" id="showmajor"></span></br>
			</div>
		</td>
	</tr>
	<tr class="table_bg1">
		<td height=25px  align=right width="30%" >变更原因:</td>
		<td height=25px  align="right" width="70%" >
			<div align="left">
			 <textarea class="smallarea"  name="reason" cols="70" rows="12" id="body"></textarea>
			</div>
		</td>
	</tr>
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="center">
			<input type="button" value="提交申请" onclick="confirmChange()"/>
			</div>
		</td>
	</tr>
 
</table>
</div>
</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<s:if test="applyList.size()>0">

<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0">
	<tr class="table_bg1">
		<td>异动类型</td>
		<td>申请时间</td>
		<td>异动新学籍</td>
		<td>审核状态</td>
	</tr>
	<s:iterator value="applyList">
	<tr>
		<td><s:property value="enumConstByApplyType.name"/> </td>
		<td><s:date name="applyDate" format="yyyy年MM月dd日 HH：mm"/> </td>
		<td><s:property value="applyNote.substring(0,applyNote.indexOf(':'))"/> </td>
		<td><s:property value="enumConstByFlagApplyStatus.name"/> </td>
	</tr>	
	</s:iterator>
</table>

</s:if>
    </div>
    </div>
</div>
</form>
</body>
	
<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'body' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>
    