<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,com.whaty.platform.config.*"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>国培计划</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-ext.css" />
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/whatyExtjs.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js" ></script> 
		<script type="text/javascript" src="/js/extjs/examples/whatyExtjs.js" ></script> 

		<script language="javascript" src="../js/check.js"></script>	
		
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<script>
		var bSubmit=false;
		var bLoaded=false;
		function pageGuarding(){
			//document.paperinfo.submit();return true;
			//alert(document.getElementById('applyno').value);return false;
			if(document.paperinfo.title.value.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("调查问卷名不能为空!");
				document.paperinfo.title.focus();
				return false;
			}
			
			var type = document.getElementById("applyno");
			if(type.value=="请选择培训项目..."){
			alert("请选择培训项目！");
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
  	 var subjectDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getApplynoAll.action'
		        }),
				reader: new Ext.data.JsonReader({
				            root: 'applyno'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    subjectDataStore.setDefaultSort('id', 'asc');
		    subjectDataStore.load();
			
			var subjectCombo = new Ext.form.ComboBox({
				store: subjectDataStore,
				displayField:'name',
				valueField: 'id' ,
				//id:'applyno',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择培训项目...',
				applyTo: 'applyno',
				editable: false,
				forceSelection: false,
				selectOnFocus:true
			});
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
		<form name='paperinfo' action="/entity/information/peVotePaper_addVotePaper.action" method='post'
			class='nomargin'>
			<table width="99%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td><div class="content_title" id="zlb_title_start">
												添加调查问卷
											</div id="zlb_title_end"></td>
				</tr>
				<tr>
					<td valign="top" class="pageBodyBorder_zlb">
						<div class="cntent_k" id="zlb_content_start">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" width="20%" nowrap>
										<span class="name">调查问卷名称：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type=text name="peVotePaper.title" id="title" class=selfScale size="50" maxlength="250"> 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap>
										<span class="name">培训项目：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
									
									<input type=text name="applyno" id="applyno" class=selfScale size="50" maxlength="250"> 
									<!-- 	<select name="voteType" id="voteType">
										<s:iterator value="applynoList" id="type_">
										<option value="<s:property value="#type_[0]"/>"><s:property value="#type_[1]"/></option>  
										</s:iterator> 
										</select>  -->
									</td>
								</tr>
								 
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom" nowrap>
										<span class="name">图片背景上的名称:&nbsp;</span>
									</td>
									<td>
										<input type=text name="peVotePaper.pictitle" id="paper_pictitle" class=selfScale size="100" maxlength="25">
										 
										&nbsp;&nbsp;&nbsp;
									</td>
								</tr>

								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">开始时间：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type=text name="startDate" id="startDate" class=selfScale readonly>
										
									</td>
								</tr>
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">结束时间：<font color="red">*</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<input type="text" name="endDate" id=endDate class=selfScale readonly>
										
									</td>
								</tr>

								 <tr valign="bottom" class="postFormBox"> 
						              <td valign="top"><span class="name">内容：<font color="red">*</font></span></td>
						              <td> 
						              <textarea class="smallarea"  name="peVotePaper.note" cols="70" rows="12" id="body"></textarea>
						
						              </td>
            					</tr>

								
								<tr valign="bottom" class="postFormBox">
									<td valign="bottom">
										<span class="name">是否发布：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									</td>
									<td>
										<select name="active" id="active" class="input6303">
											<option value="0" selected>
												否
											</option>
											<option value="1">
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
											<option value="0" selected>
												否
											</option>
											<option value="1">
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
											<option value="0" selected>
												否
											</option>
											<option value="1">
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
											<option value="0" selected>
												否
											</option>
											<option value="1">
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
											<option value="0" selected>
												否
											</option>
											<option value="1">
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
					<td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="20%" height="100%" border="0" cellpadding="0"
							cellspacing="0"><tr>
								<td align="center" valign="middle">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:pageGuarding();"><span class="text">提交</span>
									</span>
								</td>
								<td align="center" valign="middle">
									<span class="norm"
										style="width:46px;height:15px;padding-top:3px"
										onmouseover="className='over'" onmouseout="className='norm'"
										onmousedown="className='push'" onmouseup="className='over'"
										onclick="javascript:quxiao();"><span class="text">取消</span>
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