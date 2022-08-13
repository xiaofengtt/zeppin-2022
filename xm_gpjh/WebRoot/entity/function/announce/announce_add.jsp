<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.util.*,com.whaty.platform.config.*"%>
<%@ page import="com.whaty.platform.*"%>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加公告</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<%
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
%>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
		
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
		
		
		//判断fckeditor 的内容是否为空
	   var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2 || acontent == "")
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="announce_addexe.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/ggl.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="20"><img src="../images/ggzt.gif" width="20" height="32"></td>
								  <td width="80" class="text3">公告主题:</td>
                                  <td><input name="title" type="text" size="30" maxlength="50"></td>
                                  <td width="260" align="right"><img src="../images/gg2.gif" width="259" height="32"></td>
                                </tr>
                            </table>
                            </td>
                          </tr>
                          
						  <tr>
							<td colspan=2 class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"></textarea>
							</td>
						  </tr>
						  
						  <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="65" align="right" class="text1"></td> 
                                  <td width="90"><input type="image" name="Submit" src="../images/fs.gif" width="82" height="23"></td>                                 
                                  <td width="90"><a href="javascript:history.back()"><img src="../images/fh.gif" width="82" height="23" border="0"></a></td>                                
                                  <td align="right"><img src="../images/gg3.gif" width="147" height="42"></td>
                                </tr>
                              </table></td>
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
<%
}
catch(Exception e)
{
	
	out.print(e);
	return;
}
%>