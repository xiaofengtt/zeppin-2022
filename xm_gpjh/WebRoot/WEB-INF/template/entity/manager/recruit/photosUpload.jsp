<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批量上传照片</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body>

<s:form action="recStudentPhotos_photosUpload" method="POST" enctype="multipart/form-data">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">照片上传</div></td>
  </tr>
    
            
  <tr>
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="68%" border="0" cellpadding="0" cellspacing="0">

            <tr valign="bottom" class="postFormBox"> 
              <td  valign="bottom" colspan="2" align="left">请将以学生身份证号命名的照片（bmp/jpg/gif格式） 打成zip文件 ， 文件大小<5M</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" ><span class="name">文件:</span></td>
              <td valign="bottom" align="left"><input type=file name="photos" id="src"/></td>
            </tr>

            <tr class="postFormBox">
              <td ></td>
              <td align="left"><input type=submit id='submit1' value = "上传" /></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>

</body>
</html>