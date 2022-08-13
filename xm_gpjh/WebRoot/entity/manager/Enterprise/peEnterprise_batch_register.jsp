<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link rel="stylesheet" href="/entity/manager/css/admincss.css" type="text/css"></link>
<script>
	//文件类型(*.xls)
	 function FileTypeCheck()
  {
  var obj =document.getElementById('src');
  if(obj.value==null || obj.value ==''){
  	alert('请选择上传文件！');
  	this.focus();
  	return false;
  }
  var length = obj.value.length;
  var charindex = obj.value.lastIndexOf(".");
  var ExtentName = obj.value.substring(charindex,charindex+4);
  if(!(ExtentName == ".xls" )){
  	alert('文件格式不正确！');
  	this.focus();
  	return false;
  }
  	return true;
  }
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="/entity/basic/peEnterprise_uploadExcel.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">企业批量注册</div id="zlb_title_end"></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">下载标准格式:</span></td>
              <td width="77%"> <span class="link" help="下载Excel格式的模版表格"><img src='/entity/manager/images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='/entity/manager/Enterprise/PeEnterprise.xls' target=_blank>一级企业报表</a></span> <span class="link" help="下载Excel格式的模版表格"><img src='/entity/manager/images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='/entity/manager/Enterprise/PeSubEnterprise.xls' target=_blank>二级企业报表</a></span></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载企业信息:</span></td>
              <td valign="bottom"><input type=file name="upload" id="src"/></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td ></td>
              <td><input type=submit value = "上传" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <input type="button" value="返回" onclick="javascript:window.history.back()" ></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>
</body>
</html>