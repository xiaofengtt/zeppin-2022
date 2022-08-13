<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>填写开班通知</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>
<script type="text/javascript">
function checkContent(){
 	//var flag=true;
 	
 	if(document.getElementById('bean.noticeName').value==''){
		//flag=false;
		alert("开班名称不能为空！");
		document.getElementById('bean.noticeName').focus(); 
		return false;
	}
	var content;
		if(FCKeditor_IsCompatibleBrowser()){
		var oEditor = FCKeditorAPI.GetInstance("bean.noticeContent"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
	
	if(content.length <50){
		//flag=false;
		alert("开班通知内容太少！");
		//document.getElementById('bean.noticeContent').focus(); 
		return false;
	}
	
 	return true;
 }
</script>
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">

<div id="main_content">
    <div class="content_title">填写开班通知</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/implementation/trainingCourseNotice_uploadNoticeExe.action" method="post" onsubmit="return checkContent();" >
<table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" ><!--<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>--></td>
                          </tr>
						 
                                  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="bean.noticeName" name="bean.noticeName" size="52" maxlength="50"/>  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>
                     			
                     			  <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea"  name="bean.noticeContent" cols="70" rows="12" id="bean.noticeContent"></textarea>  </td>
                       			 	<td width="140" class="postFormBox" style="padding-left:10px" id="noticeContentInfo">&nbsp;</td>
                     			 </tr>
                     			
                           <tr>
                            <td height="50" align="center" colspan="3">
                              <input type="submit" value="提交"/></td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<script type="text/javascript"> 
	<!-- 
	// Automatically calculates the editor base path based on the _samples directory. 
	// This is usefull only for these samples. A real application should use something like this: 
	// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 
	
	var oFCKeditor1 = new FCKeditor( 'bean.noticeContent' ) ; 
	
	oFCKeditor1.Height = 300 ; 
	oFCKeditor1.Width  = 700 ; 
	
	oFCKeditor1.Config['ImageBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor1.Config['ImageUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor1.Config['LinkBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor1.Config['LinkUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor1.Config['FlashBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor1.Config['FlashUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	oFCKeditor1.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor1.ReplaceTextarea() ; 
	
	

	//--> 
	<s:if test="msg!=null">
		alert('<s:property value="msg"/>');
	</s:if>
</script>
</body>
</html>
