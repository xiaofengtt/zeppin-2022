<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学籍变动设置</title>
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
			var siteDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select m.id,m.name from pr_stu_changeable_major t , pe_major m  where t.fk_old_major_id='<s:property value='bean.peStudent.peMajor.id'/>' and t.fk_new_major_id = m.id"
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
				name: 'bean.peMajorByFkNewMajorId.name',
				id:'peMajorByFkNewMajorId_id',
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择新专业...',
				applyTo: 'combo-box-major',
				editable: true,
				forceSelection: true
			});
			Ext.get("main_content").addListener("mouseover",function(){
				if(siteCombo.getValue()!=siteCombo.getRawValue()){
					document.getElementById("combo-box-major-id").value=siteCombo.getValue();
				}
			});
			
			var inputDate = new Ext.form.DateField({
			        fieldLabel: '变动日期',           
			        name: 'bean.CDate',
			        id:'inputDate',
			        format:'Y-m-d',
			        allowBlank:false, 
			        readOnly:true,
			        anchor: '29%'
			    }); 
			    
			inputDate.on('render',function showvalue(p,_record,_options){
					inputDate.setValue((new Date).format('Y-m-d'));
					inputDate.setRawValue((new Date).format('Y-m-d'));
			},inputDate);
			
			inputDate.render('showinputDate')			
		});
	
		</script>
<script >
	 function check()
  {

  var obj =document.getElementById('combo-box-major');
  if(obj.value=='' || obj.value =='请选择新专业...'){
  	alert('请选择专业！');
  	return false;
  }		
  return true;
	}
	
</script>  		
	</head>
	<body>
		<form name="print" method="post" action="/entity/studentStatus/peChangeMajor_executechange.action"  onsubmit="return check();">
			<div id="main_content">
			   <div class="content_title">学籍变动设置</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="100%">
			   		<tr>
			   			<td colspan="7">
			   				<div align="center" class="postFormBox">学籍变动设置</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="7">
			   				<div align="center" class="postFormBox"><font color="red"><s:property value="msg"/></font></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td width="15%">
			   				<div align="center" class="form_table" >学号</div>
			   			</td>
			   			<td width="10%">
			   				<div align="center" class="form_table" >姓名</div>
			   			</td>
			   			<td width="10%">
			   				<div align="center" class="form_table" >年级</div>
			   			</td>
			   			<td width="20%">
			   				<div align="center" class="form_table" >现所属专业</div>
			   			</td>
			   			<td width="10%">
			   				<div align="center" class="form_table" >层次</div>
			   			</td>
			   			<td width="20%">
			   				<div align="center" class="form_table" >学习中心</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.regNo"/></div>
			   				<input type="hidden" name="bean.peStudent.id" value="<s:property value='bean.peStudent.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.regNo" value="<s:property value='bean.peStudent.regNo'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.trueName"/></div>
			   				<input type="hidden" name="bean.peStudent.trueName" value="<s:property value='bean.peStudent.trueName'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peGrade.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peGrade.id" value="<s:property value='bean.peStudent.peGrade.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peGrade.name" value="<s:property value='bean.peStudent.peGrade.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><u><s:property value="bean.peStudent.peMajor.name"/></u></div>
			   				<input type="hidden" name="bean.peStudent.peMajor.id" value="<s:property value='bean.peStudent.peMajor.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peMajor.name" value="<s:property value='bean.peStudent.peMajor.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peEdutype.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peEdutype.id" value="<s:property value='bean.peStudent.peEdutype.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peEdutype.name" value="<s:property value='bean.peStudent.peEdutype.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peSite.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peSite.id" value="<s:property value='bean.peStudent.peSite.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peSite.name" value="<s:property value='bean.peStudent.peSite.name'/>"/>
			   			</td>
			   		</tr>
		   		
					<tr>
			   			<td colspan="1">
			   				<div align="left" class="postFormBox"><span class="name"><label>转到新专业*</label></span></div>
			   			</td>
			   			<td colspan="6">
			   				<div align="left" class="postFormBox">
			   					<input type="hidden" name="bean.peMajorByFkOldMajorId.id" value="<s:property value='bean.peMajorByFkOldMajorId.id'/>" />
			   					<input type="text" name="bean.peMajorByFkNewMajorId.name" value="<s:property value='bean.peMajorByFkNewMajorId.name'/>" size="40" id="combo-box-major" />
			   					<input type="hidden" name="bean.peMajorByFkNewMajorId.id" value="<s:property value='bean.peMajorByFkNewMajorId.id'/>" id="combo-box-major-id"/>
			   				</div>
			   			</td>
			   		</tr>
			   		<tr valign="middle" class="postFormBox"> 
		              <td valign="middle" align="left"  colspan="1" ><span class="name">变动日期*</span></td>
		              <td valign="middle" colspan="6"><div align="left" class="postFormBox" id = "showinputDate"></div></td>
		            </tr>
			   		<tr class="postFormBox">
			   			<td colspan="1" nowrap="nowrap">
			   				<div align="left" ><span class="name"><label>学籍变动的原因</label></span></div>
			   			</td>
			   			<td colspan="6" height="50px">
			   				<div align="left" > <textarea class="smallarea"  name="bean.note" cols="70" rows="12" id="body"><s:property value='bean.note'/></textarea></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="7">
			   				<div align="center" class="postFormBox"><input type="submit" name="Submit" value="提交"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="back" onclick="window.location='/entity/studentStatus/studentStatus.action'" value="返回"/></div>
			   			</td>
			   		</tr>
			   </table>
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