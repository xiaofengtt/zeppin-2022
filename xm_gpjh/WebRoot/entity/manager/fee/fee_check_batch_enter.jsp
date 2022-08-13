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
	 function FileTypeCheck(){
		var obj =document.getElementById('src');
		if(obj.value==null || obj.value ==''){
			alert('请选择文件！');
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
<s:form name = "batch" action="/entity/fee/peFeeDetailForReciept_batchexe.action" method="POST"  enctype="multipart/form-data" onsubmit ="return FileTypeCheck();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量导入交费收据明细</div></td>
  </tr> 
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">             
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载交费收据明细:</span></td>
              <td valign="bottom"><input type=file name="_upload" id="src"/></td>
            </tr>
            <tr class="postFormBox">
              <td align="right"><input type=submit value = "上传" /></td>
              <td align="left">
             	<input type="button" value = "关闭" onclick="javascript:window.close();" />              
              </td>
            </tr>
         </table>
      </div>
    </td>
  </tr>
  <tr> 
    <td><div class="content_title" >导入说明</div></td>
  </tr> 
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" > 
        <div class="cntent_k">
        1、到导入发票号的学生交费明细页面，选择报表格式，导出交费明细报表。<br/>
        2、打开下载后的报表，填写对应的收据或者发票号。发票号格式：2位字母加8位数字<br/>
        3、上传后如果要修改发票号，可以到学生交费明细页面双击打开修改。<br/>
         注：不要修改第1列的ID，会导致上传失败。
      </div>
    </td>
  </tr>
</table>
</s:form>
</body>
</html>