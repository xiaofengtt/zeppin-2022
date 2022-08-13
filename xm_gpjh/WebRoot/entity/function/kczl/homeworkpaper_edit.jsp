<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<script src="/FCKeditor/fckeditor.js"></script>
</head>
<script>
	var bLoaded=false; 
	function chkSubmit()
	{
		if(frmAnnounce.title.value.length ==0)
		{
			alert("请填写标题");
			return false;
		}
		if(frmAnnounce.body.value.length <2 || frmAnnounce.body.value == "<P>&nbsp;</P>")
		{
			alert("内容为空，还是多写几句吧？");
			return false;
		}	
		document.getElementById("sub").disabled = true;
	}
</script>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/entity/studyZone/courseResources_doEdit.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type=hidden name='course_id' value='<s:property value="interTchInfo.teachclassId"/>' />
<input type=hidden name='type' value='<s:property value="interTchInfo.type"/>' />
<input type=hidden name='r_id' value='<s:property value="interTchInfo.id"/>' />
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
                              <strong>参考资料</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            	<tr>
                            <td class="neirong" width=20% nowrap="nowrap">
							<font size=2>题目：</font>
							</td>
							<td class="neirong" valign=top>
							<input type=text name=title class=input1 size=60 value='<s:property value="interTchInfo.title"/>' />
							</td>
						  </tr>
                          		<tr>
                          		<td class="neirong" valign=top width=20%>
							<font size=2>内容：</font>
							</td>
							<td class="neirong" valign=top>
							<textarea class="smallarea" cols="80" name="body" rows="12"><s:property value="interTchInfo.content"/></textarea>
							<script language=JavaScript src="../../../WhatyEditor/config.jsp"></script><br>
							<script language=JavaScript src="../../../WhatyEditor/edit.js"></script>
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
			<td align=center><input type=submit value="提交" id="sub">&nbsp;&nbsp;<input type=button value="返回" onclick="window.location='/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value='interTchInfo.teachclassId'/>'"></td>
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
