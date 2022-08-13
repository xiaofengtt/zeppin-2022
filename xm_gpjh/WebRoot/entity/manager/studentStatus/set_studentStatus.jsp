<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<script>
	//文件类型(*.xls)
	 function FileTypeCheck()
  {
  var obj =document.getElementById('src');
  if(obj.value==null || obj.value ==''){
  	alert('文件格式不正确！');
  	this.focus()
  	return false;
  }
  var length = obj.value.length;
  var charindex = obj.value.lastIndexOf(".");
  var ExtentName = obj.value.substring(charindex,charindex+4);
  if(!(ExtentName == ".xls" )){
  	alert('文件格式不正确！');
  	this.focus()
  	return false;
  }
  	return true;
  }
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="/entity/exam/scoreBatchUpload_practiceUpload.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">设置学籍变动</div id="zlb_title_end"></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">新学习中心:</span></td>
              <td width="77%"><select></select></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">新年级:</span></td>
              <td width="77%"><select></select></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">新专业:</span></td>
              <td width="77%"><select></select></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">新层次:</span></td>
              <td width="77%"><select></select></td>
            </tr>          
            
            <tr class="postFormBox">
              <td ></td>
              <td><input type=submit value = "提交" /></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>
</body>
</html>