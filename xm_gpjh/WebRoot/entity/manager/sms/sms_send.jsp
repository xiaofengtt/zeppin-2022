<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ include file="/entity/manager/pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">

</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">发送结果</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
	<div class="cntent_k" id="zlb_content_start">
          <table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
<s:if test="succ">
              <td valign="top">短信成功构建，请等待审核!</td>
</s:if>
<s:else>
              <td valign="top">短信构建失败!</td>
</s:else>
            </tr>            
          </table>
    </div>
    </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
          <td align="center" valign="middle"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onclick="location.href='/entity/information/peSmsInfoAction.action'" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">返回</span></span></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
<s:if test="!succ">
<script>
alert('<s:property value="msg"/>');
</script>
</s:if>