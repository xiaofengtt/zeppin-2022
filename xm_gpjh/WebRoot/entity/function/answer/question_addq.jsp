<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>答疑</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
</head>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
		
		if (bLoaded==false)
		{
			alert("表单正在下载");
			return false;
		}
		
	var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2)
		{
			alert("问题内容为空，还是多写几句吧？");
			return false;
		}	
	}
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/entity/studyZone/courseResources_addQuestExe.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type="hidden" name="course_id" value="<s:property value="course_id"/>"/>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"><img src="/entity/function/images/dy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="100"  align="right" class="text1">提问主题*&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                  <td><input name="title" type="text" size="55" maxlength=25></td>
                                  <td width="250" align="right"><img src="/entity/function/images/gg2.gif" width="249" height="32"></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="65" align="right" class="text1">内容：</td> 
                                  <td align="right"><img src="/entity/function/images/gg3.gif" width="147" height="42"></td>
                                </tr>
                              </table></td>
                          </tr>
						  <tr>
							<td colspan=2 class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"></textarea>
							</td>
						  </tr>
						  <tr>
							<td colspan="2" align="center"><input type="image" name="Submit" src="/entity/function/images/fs.gif" width="82" height="23">&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:history.back()"><img src="/entity/function/images/fh.gif" width="82" height="23" border="0"></a></td>                                
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
</body>
</html>
