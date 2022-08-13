<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.*" %>
<%@ include file="../pub/priv.jsp"%>
<% 
	String comp = request.getParameter("comp");
	String content = request.getParameter("content");
	
	//out.print(comp);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<title>可视化编辑器</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	var bLoaded=false; 
	function pageGuarding()	{
		
		if (bLoaded==false) {
			alert("表单正在下载")
			return false
		}
		
		var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		if(acontent.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
		
		return true;
	}
	
	function doAdd() {
		if(pageGuarding()) {
			//alert(document.question.body.value);
			 var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
//			window.opener.document.getElementById("<%=comp%>").value = document.question.body.value;
			window.opener.document.getElementById("<%=comp%>").value = acontent;
			window.close();
		} else {
			alert("Error!!!");
		}
	}
</script>
</head>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
%>
<body leftmargin="0" topmargin="0">
<form name="question" action="" onsubmit="return pageGuarding();">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3">试题内容编辑区（"Shift+Enter"=换行,"Enter"=分段）</td>
  </tr>
  <tr> 
    <td><table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
        <tr>
          <td align="center">
          <%
          	WhatyStrManage strManage = new WhatyStrManage();
			strManage.setString(content);
			if(content !=null)
			{
				content=strManage.htmlDecode();
			}
			else
				content = "";
           %>
          	  <textarea name=body class=selfScale rows="22" cols="72"><%=content %></textarea>
             
          </td>
        </tr>
      </table><br>
     </td>
  </tr>
  <tr> 
    <td align="center"><input type="button" value="确定" onclick="doAdd()"></td>
  </tr>
  <tr> 
    <td height="41" background="images/ct_07.gif" style="padding-left:40px;padding-top:5px" class="text3"></td>
  </tr>
</table>
<script>bLoaded=true;</script>
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


oFCKeditor.Value = '' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
</html>
