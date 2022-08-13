<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../../css/admincss.css" rel="stylesheet" type="text/css">
<script language=javascript src="../../js/check.js"></script>
<script language=javascript>
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
  	document.upload.submit();;
  }

function fixfile()
{
	if(isNull(document.upload.src.value))
	{
		document.upload.submit();
	}
	else
	{
		alert("输入为空!");
		document.upload.src.focus();
		document.upload.src.select();
		return false;
	}
}
</script>

</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量录入手机号</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
   <form name='upload' action='excel_upload_info.jsp' method='post' ENCTYPE="multipart/form-data" class='nomargin' >
        <div class="cntent_k" id="zlb_content_start">
          <table width="80%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td width="30%" valign="bottom"><span class="name">下载模版:</span></td>
              <td> <span class="link"><img src='../../images/buttons/excel.png' alt='Print' width="20" height="20" title='Print'>&nbsp;<a href='mobile_excel.xls' target=_blank>Excel模版</a></span></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">上载Excel文件：</span></td>
              <td> <input name="src" id="src" type="file" class="selfScale"> &nbsp;</td>
            </tr>
          </table>
      </div>
</form>
    </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
          <td align="center" valign="middle"><a href="#" title="提交" class="button" onclick="FileTypeCheck()"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">提交</span></span></a></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
