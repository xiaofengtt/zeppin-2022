<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<script src="/FCKeditor/fckeditor.js"></script>
</head>
<script language=javascript>
	var bLoaded=false; 
	function chkSubmit()
	{
	
		if(frmAnnounce.title.value.length ==0)
		{
			alert("请填写标题");
			return false;
		}
		
		 var oEditor = FCKeditorAPI.GetInstance('body') ;
       var acontent=oEditor.GetXHTML();
		
		if(acontent.length <2 || acontent == "")
		{
			alert("内容为空，还是多写几句吧？");
			return false;
		}	
	if(acontent.length>5000){
			alert("输入内容过多，超过指定长度5000个字符！");
			document.question.body.focus();
			return;
		}
		document.getElementById("sub").disabled = true;
	}
</script>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="/entity/studyZone/courseResources_addZL.action" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type='hidden' name='course_id' value='<s:property value="#parameters.course_id[0]"/>' />
<input type='hidden' name='type' value='<s:property value="#parameters.type[0]"/>' />
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="../images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="../images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
                              <strong>参考资料</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="../images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            <tr>
                            <td class="neirong" width=20% class="text1" nowrap="nowrap">
							题目：
							</td>
							<td class="neirong" valign=top>
							<input type=text name=title class=input1 size=60 maxlength="18">
							</td>
						  </tr>
                          	<tr>
                          	<td class="neirong" valign=top width=20% class="text1">
							内容：
							</td>
							<td valign=top>
							<textarea  cols="80" name="body" rows="12"></textarea>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交" id=sub>&nbsp;&nbsp;<input type=button value="返回" onclick="window.location='/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value='#parameters.course_id[0]'/>'"></td>
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
