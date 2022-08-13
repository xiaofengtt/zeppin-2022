<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ include file="/entity/function/pub/priv.jsp"%>
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中国社会科学院（研究生院） </title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>

<script type="text/javascript">	
	function chkSubmit()
	{
		var oEditor = FCKeditorAPI.GetInstance('note') ;
       var acontent=oEditor.GetXHTML();
	}
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/entity/workspaceTeacher/interactionHomework_finishHomework.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type="hidden" value="<s:property value="homeworkInfoId"/>" name="homeworkInfoId">

        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>做作业</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                            <td class="text2" background="/entity/function/images/table_02.gif" >
                            	<table border="0" align="left" cellpadding="0" cellspacing="0" width=77%>
                            		<tr>
                            			题目：
                            			<td class="text2" width="95%" align="center"><s:property value="homeworkInfo.note" escape="false"/></td>
                            		</tr>
                            	</table>
							</td>
							</tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=70%>
                          		<tr>
							<td class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="note" rows="12"></textarea>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
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
<script type="text/javascript"> 
<!-- 
// Automatically calculates the editor base path based on the _samples directory. 
// This is usefull only for these samples. A real application should use something like this: 
// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 

var oFCKeditor = new FCKeditor( 'note' ) ; 
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