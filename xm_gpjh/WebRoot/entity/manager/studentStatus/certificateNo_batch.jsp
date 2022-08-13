<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script>
	//文件类型(*.xls)
	 function FileTypeCheck()
  {
   document.getElementById('submit1').disabled = true;
  var obj =document.getElementById('src');
  if(obj.value==null || obj.value ==''){
  	alert('请选择上传文件！');
  	this.focus();
  	document.getElementById('submit1').disabled = false;
  	return false;
  }
  var length = obj.value.length;
  var charindex = obj.value.lastIndexOf(".");
  var ExtentName = obj.value.substring(charindex,charindex+4);
  if(!(ExtentName == ".xls" )){
  	alert('文件格式不正确！');
  	this.focus();
  	document.getElementById('submit1').disabled = false;
  	return false;
  }
  	return true;
  }
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="/entity/studentStatus/peGraduatedStudent_certificateNoBatch.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td ><div class="content_title"  id="zlb_title_start">批量导入毕业证编号、学位证编号</div></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p><FONT color="red"><s:property value="msg" escape="false" /></FONT></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">下载标准格式:</span></td>
              <td width="77%"> <span class="link" help="下载Excel格式的模版表格"><img src='<%=request.getContextPath()%>/entity/manager/images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='certificateNo_batch.xls' target=_blank>Excel报表</a></span></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" nowrap="nowrap"><span class="name">毕业证学位证编号信息:</span></td>
              <td valign="bottom"><input type=file name="upload" id="src"/></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td ></td>
              <td><input type=submit id="submit1" value = "上传" /></td>
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
        <span class="name">使用方法一：</span><br/>
        1、下载标准格式，按要求填写相应内容。上传文件。<br/>
         <span class="name">使用方法二：</span><br/>
        1、到毕业学生列表页面，查询出所需导入编号的学生。下方选择报表格式，选择Excel导出。<br/>
        2、打开下载后的报表，保留 学号，姓名，毕业证编号，学位证编号 这四列，其余列全部删除。<br/>
        3、填写学生对应的证书编号，填写完后保存。<br/>
        4、上传修改完成后的Excel表格。<br/>
        注：毕业证编号和学位证编号至少填写一项
      </div>
     </td>
  </tr>
</table>
</s:form>
</body>
</html>