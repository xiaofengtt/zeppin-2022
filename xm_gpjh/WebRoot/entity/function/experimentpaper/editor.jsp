<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.editor.*,com.whaty.util.string.*" %>
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
<title>可视化编辑器</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	var bLoaded=false; 
	function pageGuarding()	{
		if(currentflag==1) {//处于普通编辑模式
			document.question.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		} else {
			if (currentflag==3) {//处于预浏览先通编辑模式
				document.question.body.value=oDiv.innerHTML;
			}
		}
		document.question.body.value=Absolute2Relative(document.question.body.value);//替换绝对路径
		if (bLoaded==false) {
			alert("表单正在下载")
			return false
		}
		
		return true;
	}
	
	function doAdd() {
		if(pageGuarding()) {
			//alert(document.question.body.value);
			window.opener.document.getElementById("<%=comp%>").value = document.question.body.value;
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
	WhatyEditorConfig editorConfig = interactionManage.getWhatyEditorConfig(session);
	session.setAttribute("whatyEditorConfig",editorConfig);
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
              <script language=JavaScript src="<%=editorConfig.getEditorURI() %>config.jsp"></script>
		      <script language=JavaScript src="<%=editorConfig.getEditorURI() %>edit.js"></script>
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

</html>
