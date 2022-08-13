<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.info.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.forum.*"%>
<%@ page import="com.whaty.platform.*,com.whaty.util.editor.*"%>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
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
		document.frmAnnounce.body.value=Absolute2Relative(document.frmAnnounce.body.value);//替换绝对路径
		if (bLoaded==false)
		{
			alert("表单正在下载")
			return false
		}
		
		if(frmAnnounce.title.value.length <2)
		{
			alert("标题好象忘记写了!");
			return false;
		}
		
		if(frmAnnounce.body.value.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>
<%
	String id = request.getParameter("id");	
	String realted_id = request.getParameter("realted_id");
	
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	WhatyEditorConfig editorConfig=interactionManage.getWhatyEditorConfig(session);	
	session.setAttribute("whatyEditorConfig",editorConfig);	
	Forum forum = interactionManage.getForum(id);
%>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="forum_editexe.jsp?id=<%=id%>&realted_id=<%=realted_id%>" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/tlq.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="100">主题</td>																		                           
                                  <td><input name="title" type="text" size="30" maxlength="50" value="<%=forum.getTitle()%>"></td>
                                  <td width="260" align="right"><img src="../images/gg2.gif" width="259" height="32"></td>
                                </tr>
                              </table></td>
                          </tr>                      
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="65" align="right" class="text1">内容：</td> 
                                  <td width="90"><input type="image" name="Submit" src="../images/fs.gif" width="82" height="23"></td>                                 
                                  <td width="90"><a href="javascript:history.back()"><img src="../images/fh.gif" width="82" height="23" border="0"></a></td>                                
                                  <td align="right"><img src="../images/gg3.gif" width="147" height="42"></td>
                                </tr>
                              </table></td>
                          </tr>
						  <tr>
							<td colspan=2 class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"><%=forum.getBody()%></textarea>
							<script language=JavaScript src="../../../WhatyEditor/config.jsp"></script><br>
							<script language=JavaScript src="../../../WhatyEditor/edit.js"></script>
							</td>
						  </tr>
                        </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
</form>
</table>

      <br>
<script>bLoaded=true;</script>
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
