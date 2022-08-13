<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	   <s:set name="typeCode" value="peVotePaper.enumConstByFlagType.code"/> 
	   <s:set name="semester" value="semester_id"/>
	   
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-ext.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js" ></script> 
		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="../js/check.js"></script>	
		
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
		var bSubmit=false;
		var bLoaded=false;
		function pageGuarding(){
			
			if(document.paperinfo.title.value.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("调查问卷名不能为空!");
				document.paperinfo.title.focus();
				return false;
			}
			
			if(document.paperinfo.startDate.value == "") {
				alert("开始时间不能为空!");
				return false;
			}
			
			if(document.paperinfo.endDate.value == "") {
				alert("结束时间不能为空!");
				return false;
			}
			
			if(!checkRight(document.paperinfo.startDate.value,document.paperinfo.endDate.value)) {
				alert("开始结束时间不合理！");
				return false;
			}
			if(document.paperinfo.title.value.length > 50)
			{
				alert("调查问卷名不得多于50个字，请重新填写!");
				document.paperinfo.title.focus();
				document.paperinfo.title.select();
				return false;
			}	
			if(document.paperinfo.paper_pictitle.value.length > 50)
			{
				alert("图片背景上的名称不得多于50个字，请重新填写!");
				document.paperinfo.paper_pictitle.focus();
				document.paperinfo.paper_pictitle.select();
				return false;
			}	
			
			
			var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
       	if(acontent == null || acontent==""){
       		alert("内容为空，您还是多写几句吧！");
					return;
       	}
       	
       	if(acontent.length > 2000)
			{
				alert("说明不得多于2000个字，请重新填写!");
				return false;
			}
			
			document.paperinfo.submit();
		}
		// 取消操作
		function quxiao() {
		document.paperinfo.reset();	
		var oEditor = FCKeditorAPI.GetInstance('body') ;
		oEditor.EditorDocument.body.innerHTML="";    //清空Editor中内容	
		}
		function checkRight(startDateStr, endDateStr) {
			var startArr = startDateStr.split("-");
			var endArr = endDateStr.split("-");
			var startDate = new Date(startArr[0],startArr[1],startArr[2]);
			var endDate = new Date(endArr[0],endArr[1],endArr[2]);
			if(startDate > endDate)
				return false;
			return true;
		}
Ext.onReady(function() {

	var datefield = new Ext.form.DateField({   
        			 id: 'diliveryDate',                      
       				  format: 'Y-m-d',                         
        			 maxValue: '2100-01-01',                  
        			 minValue: '1900-01-01',                  
       				// disabledDays: [0, 6],                    
       				// disabledDaysText: '禁止选择该日期',       
       				 fieldLabel: '选择日期*',                  
       				 width:180,                                
       				 showToday:true ,                          
        			 allowBlank:false, 
        			 applyTo: 'startDate',
          			blankText:'不能为空，请填写' 
      })  ; 
     var datefield2 = new Ext.form.DateField({   
        			 id: 'diliveryDate',                      
       				  format: 'Y-m-d',                         
        			 maxValue: '2100-01-01',                  
        			 minValue: '1900-01-01',                  
       				// disabledDays: [0, 6],                    
       				// disabledDaysText: '禁止选择该日期',       
       				 fieldLabel: '选择日期*',                  
       				 width:180,                                
       				 showToday:true ,                          
        			 allowBlank:false, 
        			 applyTo: 'endDate',
          			blankText:'不能为空，请填写' 
      })  ; 

});
</script>
	</head>

	<body leftmargin="0" topmargin="0" class="scllbar">
		<form name='paperinfo' action="/entity/information/peVotePaper_editVotePaper.action" method='post'
			class='nomargin' onsubmit="">
			<input type="hidden" name="peVotePaper.id" value="<s:property value="peVotePaper.id"/>" />
			<table width="99%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><div class="content_title" id="zlb_title_start">
												修改调查问卷
											</div id="zlb_title_end"></td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder_zlb">
						<div class="cntent_k" id="zlb_content_start">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" width="20%" nowrap>
										<span class="name">调查问卷名称：&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type=text name="peVotePaper.title" id="title" value="<s:property value="peVotePaper.title"/> " class=selfScale size="50" maxlength="50"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap>
										<span class="name">培训项目:<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="applyno" >
										<s:iterator value="paperType" id="type_">
										<s:set name="typeId" value="peVotePaper.peProApplyno.id"/>
										<option  value="<s:property value="#type_[0]"/>" <s:if test="#typeId == #type_[0]">selected="selected"</s:if>><s:property value="#type_[1]"/></option>  
										</s:iterator>
										</select> 
									</td>
								</tr>


								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap>
										<span class="name">图片背景上的名称：</span>
									</td>
									<td>
										<input type=text name="peVotePaper.pictitle" id="paper_pictitle" value="<s:property value="peVotePaper.pictitle"/>" class=selfScale size="100" maxlength="100"> 
										 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>

								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">开始时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type=text name="peVotePaper.startDate" id="startDate" value="<s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />" class=selfScale readonly>
										
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">结束时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type="text" name="peVotePaper.endDate" id=endDate value="<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" />" class=selfScale readonly>
										
									</td>
								</tr>

								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top"><span class="name">内容：</span></td>
						              <td> 
						              <textarea class="smallarea"  name="peVotePaper.note" cols="70" rows="12" id="body"><s:property value="peVotePaper.note" escape="false"/></textarea>
						
									  <img src="/entity/manager/images/buttons/help.png" width="16" height="16" class="helpImg" onMouseOver="window.status='该调查问卷的介绍'" onmouseout="window.status='信息提示...'"/>
						              </td>
            					</tr>

								
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">是否发布：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="active" id="active" class="input6303">
											<option value="0"  <s:if test="peVotePaper.enumConstByFlagIsvalid.code == 0">selected="selected"</s:if>>
												否
											</option>
											<option value="1" <s:if test="peVotePaper.enumConstByFlagIsvalid.code == 1">selected="selected"</s:if>>
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">是否允许添加建议：</span>
									</td>
									<td>
										<select name="canSuggest" id="canSuggest" class="input6303">
											<option value="0" <s:if test="peVotePaper.enumConstByFlagCanSuggest.code == 0">selected="selected"</s:if>>
												否
											</option>
											<option value="1"  <s:if test="peVotePaper.enumConstByFlagCanSuggest.code == 1">selected="selected"</s:if>>
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							 	<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">是否允许查看建议：</span>
									</td>
									<td>
										<select name="viewSuggest" id="viewSuggest" class="input6303">
											<option value="0"  <s:if test="peVotePaper.enumConstByFlagViewSuggest.code == 0">selected="selected"</s:if>>
												否
											</option>
											<option value="1" <s:if test="peVotePaper.enumConstByFlagViewSuggest.code == 1">selected="selected"</s:if>>
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">限制IP：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="limitIp" id="limitIp" class="input6303">
											<option value="0" <s:if test="peVotePaper.enumConstByFlagLimitDiffip.code == 0">selected="selected"</s:if>>
												否
											</option>
											<option value="1"<s:if test="peVotePaper.enumConstByFlagLimitDiffip.code == 1">selected="selected"</s:if>>
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">限制会话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="limitSession" id="limitSession"
											class="input6303">
											<option value="0" <s:if test="peVotePaper.enumConstByFlagLimitDiffsession.code == 0">selected="selected"</s:if>>
												否
											</option>
											<option value="1" <s:if test="peVotePaper.enumConstByFlagLimitDiffsession.code == 1">selected="selected"</s:if>>
												是
											</option>
										</select> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</div>

					</td>
				</tr>
				<tr>
					<td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="30%" height="100%" border="0" cellpadding="0"
							cellspacing="0"><tr>
								<td align="center" valign="top">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:pageGuarding();"><span class="text">提交</span>
									</span>
								</td>
								<td align="center" valign="top">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:window.navigate('/entity/information/peVotePaper_toEditVotePaper.action?bean.id=<s:property value="peVotePaper.id"/>');"><span class="text">重置</span>
									</span>
								</td>
								<td align="center" valign="top">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:history.back();"><span class="text">返回</span>
									</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
		</form>
		<script>bLoaded=true;</script>
	
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

