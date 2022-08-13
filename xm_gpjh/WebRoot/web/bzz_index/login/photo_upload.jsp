<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片包</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/entity/manager/js/check.js"></script>
<script>
	function doCommit() {
		if(isNull(document.photo_upform.photo_zip.value))
		{
		}
		else
		{
			alert("请浏览输入要上传的zip包！");
			document.photo_upform.photo_zip.focus();
			return false;
		}
			
		document.forms["photo_upform"].submit();
	}
	
</script>	
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<br>
  <form name='photo_upform' action='/entity/basic/peTrainee_doUpload.action' enctype="multipart/form-data" method='post' class='nomargin'>
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">上传图片压缩包</div id="zlb_title_end"></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              	<td width="120" valign="bottom"><span class="name">上传图片压缩包：&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
            	<td> <input name="photoZip" id="photo_zip" type="file" class="selfScale"  maxlength="20">            
            </tr>
            <tr valign="bottom" class="postFormBox"> 
               <td align="center" valign="middle" colspan=2><a href="#" title="提交" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">提交</span></span></a></td>
            </tr>
          </table>
          <p style="color:red">注意：上传格式为zip的压缩包，压缩包中不能有文件夹，包中图片格式为jpg,jpeg,bmp,gif,图片名为"学员的用户名"+"图片格式",例如：001.jpg；</p>
      </div>
   </td>
  </tr>
</table>
</form>

<s:if test="uploadMsg != '' and uploadMsg != null">
              		<script>
              			alert("<s:property value='uploadMsg' escape='false'/>");
					</script>  
              	</s:if>

</body>
</html>
