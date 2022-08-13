<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/admincss.css" rel="stylesheet" type="text/css">
<script>
	function doEdit() {
		
	}
</script>
</head>
<%
	String id = request.getParameter("id");
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	ResourceDir dir = manage.getResourceDir(id);
%>
<body leftmargin="0" topmargin="0" class="scllbar">
<form name='edit' action='dir_editexe.jsp' method='post' class='nomargin'>
<INPUT type="hidden" name="id" value="<%=id%>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="../images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="../images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="../images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="../images/page_titleRightM.gif" class="pageTitleText">修改名称</td>
              </tr>
            </table></td>
          <td width="8"><img src="../images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder"> 
	<div class="border">
		<table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td width="50" valign="bottom">
              	<span class="name">名称：</span>
              </td>
              <td>
              	<input name="name" type="text" class="selfScale" value="<%=dir.getName()%>"> &nbsp;
              	<span class="link" onclick="javascript:document.forms['edit'].submit()">修改</span>
              </td>
            </tr>
            
        </table>
	</div>
    </td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
        <tr>
          <td align="center" valign="middle"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
