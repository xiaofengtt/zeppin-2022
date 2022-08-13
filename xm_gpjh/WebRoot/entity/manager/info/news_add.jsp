<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加新闻</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script type="text/javascript">
	function doCommit(){
	
	var content;
	var flag = true;
			if(FCKeditor_IsCompatibleBrowser()){
			var oEditor = FCKeditorAPI.GetInstance("note"); 
			content=oEditor.GetXHTML(); 
		}else{
			alert("表单正在下载");
			return false;
		}
	
		var title = document.getElementById("title").value;
		if(title.length<1){
			window.alert("标题不能为空!");
			flag = false;
			window.focus();
			return false;
		}
		var types = document.getElementById("types").value;
		if(types==""){
			window.alert("请选择新闻类型!");
			flag = false;
			window.focus();
			return false;
		}
		
		if(content.length<1){
			window.alert("新闻内容不能为空!");
			flag = false;
			window.focus();
			return false;
		}
		if(content.length>1024){
			window.alert("新闻内容长度超过限制!");
			flag =false;
			window.focus();
			return false;
		}
		//alert(content);
		//alert(content.length);
		//return false;
		var pic = document.getElementById("upload").value;
		pic = pic.toLowerCase();
		var ptem =pic.split(".");
		//var str ="jpg:bmp:gif:png:jpeg";
		var str ="jpg";
		if(pic.length>1){
		if(!(str.indexOf(ptem[1])>=0)){
			window.alert("新闻图片格式不符!");
			flag = false;
			window.focus();
			return false;
		}
		}
		if(flag==true){
			var action="/entity/information/peInfoNews_addInfoNews.action";
			document.addNews.action=action;
			document.addNews.submit();
		}
	}
	function checkLength(obj,len){
		if(obj.value.length > (len)){
			alert('此字段最多只能输入'+len+'个字符');
			obj.value = obj.value.substr(0,len);
			return false;
		}
	}
</script>
<body leftmargin="0" topmargin="0" class="scllbar" >
  <form name ="addNews" method="post" enctype="multipart/form-data">
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">添加新闻</div></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width="90" nowrap><span class="name">新闻主题*</span></td>
              <td width="626"> <input name="title" type="text" class="selfScale" id="title" size="50" maxlength="250" onkeyup="checkLength(this,225);"></td> 
            </tr>
                        
            <tr valign="bottom" class="postFormBox">
            	<td valign="bottom" width="90" nowrap><span class="name">新闻类型*</span></td>
              <td width="626"><SELECT name="types">
              	<option value="">请选择新闻类型...</option>
              	<s:if test="newList.size() > 0">
              	<s:iterator value="newList" status="types">
              		<option value="<s:property value='id' />"><s:property value="name"/></option>
              	</s:iterator>
              	</s:if>
              </SELECT>
              </td> 
            </tr>
             <tr valign="bottom" class="postFormBox">
             <td valign="bottom" width="90" nowrap><span class="name">新闻图片&nbsp;&nbsp;</span></td>
              <td width="626"><input id="upload" type="file" name="upload"><span><font size="2" color="red"> &nbsp;  &nbsp;格式: JPG</font></span></td> 
            </tr>
       
			<tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">新闻简介&nbsp;&nbsp;</span></td>
             <td width="626"><input name="summary" type="text" class="selfScale" id="summary" maxlength="1000" onkeyup="checkLength(this,999);"></td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">新闻内容*</span></td>
              <td> 
              	<textarea class="smallarea" name="note" cols="70" rows="12" id="notetext"></textarea>
			  </td>
            </tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'">
       			<span class="text">提交</span>       			
       		</span>
       	</a>
       	</td>
       <td  align="center" id="back" valign="middle">
       	</td>            
       	</tr>
      </table></td>
  </tr>
</table>
</form>
<script type="text/javascript"> 


var oFCKeditor = new FCKeditor( 'note' ) ; 
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
<script>bLoaded=true;</script>

</body>

</html>



