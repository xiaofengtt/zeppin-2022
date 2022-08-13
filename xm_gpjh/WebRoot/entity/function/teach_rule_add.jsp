<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="./pub/priv.jsp"%>

<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>
<%
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		String rule = interactionManage.getTeachRule(teachclass_id);
		WhatyEditorConfig editorConfig=interactionManage.getWhatyEditorConfig(session);	
		session.setAttribute("whatyEditorConfig",editorConfig);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
</head>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
		if(currentflag==1)//处于普通编辑模式
		{
			frmAnnounce.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		}        
		else 
		{
			if (currentflag==3)//处于预浏览先通编辑模式
			{
				frmAnnounce.body.value=oDiv.innerHTML;
			}
		}
		
		if(frmAnnounce.body.value.length <2 || frmAnnounce.body.value == "<P>&nbsp;</P>")
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="teach_rule_addexe.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="./images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="./images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="./images/xb3.gif" width="17" height="15"> 
                              <strong>教 学 要 求</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="./images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=70%>
                          		<tr>
							<td class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"><%=fixnull(rule) %></textarea>
							<script language=JavaScript src="../../WhatyEditor/config.jsp"></script><br>
							<script language=JavaScript src="../../WhatyEditor/edit.js"></script>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="./images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交">&nbsp;&nbsp;<input type=button value="返回" onclick="javascript:history.back()"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
</body>
</html>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
