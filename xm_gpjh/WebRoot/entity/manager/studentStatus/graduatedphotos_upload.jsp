<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script>
	//文件类型(*.zip)
	function FileTypeCheck(){
		var obj =document.getElementById('src');
		if(obj.value==null || obj.value ==''){
			alert('文件格式不正确！');
			this.focus()
			return false;
  		}
		var length = obj.value.length;
		var charindex = obj.value.lastIndexOf(".");
		var ExtentName = obj.value.substring(charindex,charindex+4);
		if(ExtentName != ".zip"){
			alert('文件格式不正确！');
			this.focus()
			return false;
		}
		return true;
	}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="entity/studentStatus/peStudentPhoto_photosUploadexe.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量导入学生毕业照片</div></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
           
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">选择文件:</span></td>
              <td valign="bottom"><input type=file name="_upload" id="src"/></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td align="right"><input type=submit value = "上传" /></td>
              <td ><input type=button value = "返回" onclick="javascript:window.navigate('/entity/studentStatus/peStudentPhoto.action')" /></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 <tr> 
    <td><div class="content_title" >使用说明</div></td>
  </tr>  
  <tr>
    <td >
        <div class="cntent_k">
        1、上传的压缩包类型是zip格式。<br>
        2、压缩包中照片以学生的身份证号命名。<br>
        3、照片的格式要是jpg、bmp、gif。
         </div>
     </td>
  </tr>
</table>
</s:form>
</body>
</html>