<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
</head>
<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
<form action="/entity/workspaceStudent/bzzAssess_Assessing.action" name="frm" method="post">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=500 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：直播课堂</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="15%" align="left">课堂名称</td>
            <td width="35%" align="left">课堂公告</td>
            <td width="15%" align="left">开始时间</td>
            <td width="15%" align="left">结束时间</td>
            <td width="15%" align="left">进入课堂学习</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td >
        <table width="96%" border="0" cellpadding="0" cellspacing="0">
        	<s:iterator value="liveroomlist" status="livest">
        		<tr>
        			<td width="5%" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9"/></td>
        			<td width="15%" align="left"><s:property value="name"/></td>
           			<td width="35%" align="left"><s:property value="note"/></td>
                    <td width="15%" align="left">&nbsp;&nbsp;<s:date name="beginDate" format="yy-MM-dd"/></td>
                    <td width="15%" align="left">&nbsp;&nbsp;<s:date name="endDate" format="yy-MM-dd"/></td>
                    <td width="15%" align="left">
                     <script>
						var tmp_url_<s:property value="#livest.index"/> = "http://210.76.98.124:8080/mcs2/vlog.asp?roomid=<s:property value="roomId"/>&op=0&user=" + escape("<s:property value="getPeStudent().getTrueName()"/>") + "&nick=" + escape("<s:property value="getPeStudent().getTrueName()"/>") + "&roomname=" + escape("<s:property value="name"/>") + "&roomgroup=" + escape("直播教室");
					</script>
                    	<input type="button" value="进入课堂" onclick='window.open(tmp_url_<s:property value="#livest.index"/>,"newwindow");'
                    <s:if test="isOpen==1">	
                    	 disabled="disabled" 
                    </s:if>
                    />
                    </td>
        		</tr>
        		<tr valign="top">
					<td colspan="10">
						<img src="/entity/bzz-students/images/two/7.jpg" width="743" height="2" />
					</td>
				</tr>
        	</s:iterator>
       	</table>
        </td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
      
     </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</form>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>