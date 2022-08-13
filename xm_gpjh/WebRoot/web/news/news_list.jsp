<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
<title>生殖健康咨询师培训网</title>
<link rel="stylesheet" href="/web/news/images/index.css" type="text/css"></link>
<link href="/web/news/images/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center" class="body_box"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100%" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="15" height="31"><img src="/web/news/images/zcxwbj_03.jpg" width="15" height="31"></td>
    <td background="/web/news/images/zcxwbj_04.jpg"><div id="zcxw_tit">政策新闻</div></td>
    <td width="15"><img src="/web/news/images/zcxwbj_08.jpg" width="15" height="31"></td>
  </tr>
  <tr>
    <td background="/web/news/images/zcxwbj_10.jpg">&nbsp;</td>
    <td style="padding:15px 15px;"><iframe id="xytd" name="xytd" allowtransparency="yes" frameborder="0" width="100%" scrolling="no" src="/entity/first/firstInfoNews_toNewsList.action?type=_xygg" onload="resize()"></iframe></td>
    <td background="/web/news/images/zcxwbj_12.jpg">&nbsp;</td>
  </tr>
  <tr>
    <td height="15"><img src="/web/news/images/zcxwbj_16.jpg" width="15" height="15"></td>
    <td background="/web/news/images/zcxwbj_17.jpg"></td>
    <td><img src="/web/news/images/zcxwbj_19.jpg" width="15" height="15"></td>
  </tr>
</table>

		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/news/bottom1.jsp"></iframe></td>
  </tr>
</table>
</body>
</html>
            
