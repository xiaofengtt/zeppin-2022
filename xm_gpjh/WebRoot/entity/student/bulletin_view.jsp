<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'bulletin_view.jsp' starting page</title>
<link href="/entity/student/css/admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
body{
 padding:0px;
 margin:2px 0 0 0;}
.STYLE1 {color: #000000}
.border{
border:solid 1px #ffffff;
}
</style>    

  </head>
  
  <body>
<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
  <td align="center" background="/entity/student/images/g_24.jpg"  height="24"   style="font:'宋体'; font-size:12px; color:#053C5B; text-align:left; padding-left:18px;"><img src="/entity/student/images/l.gif" width="9" height="9">
<strong>您当前的位置是:</strong>学生<s:property value="peStudent.trueName"/>的工作室 > 通知公告</td>
  </tr>
  <tr>
    <td height="2" colspan="3" align="center" style="border-left:#85C5E3 solid thin;border-right:#85C5E3 solid  thin; "></td>
  </tr>
  <tr>
    <td colspan="3" align="center">
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bordercolor="#FFFFFF" bgcolor="#85C5E3" style="padding:0px 0px 0px 0px;">
       <tr bgcolor="#A6D3EF">
        <td width="44%" height="24" align="center" bgcolor="#A6D3EF"  class="helpBox border">新闻标题</td>
        <td width="20%"  align="center" class="helpBox border">报道日期</td>
        <td width="16%"  align="center"  class="helpBox border">发布人</td>
        <td width="20%"  align="center"  class="helpBox border">发布更新时间</td>
      </tr>
 <s:iterator value="bulletinList" status="num">	
  <tr <s:if test="#num.index%2 == 0 ">bgcolor="#FFFFFF"</s:if><s:else>bgcolor="#EAF7FF"</s:else> >
  	 <td class="font01 border"><a href="/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="id"/>"> 
  	 <s:property value="title"/></a> &nbsp;</td>
      <td align="center" class="font01 border"><s:date name="publishDate" format="yyyy-MM-dd" />&nbsp;</td>
      <td align="center" class="font01 border"><s:property value="peManager.trueName"/>&nbsp;</td>
      <td align="center" class="font01 border"><s:date name="updateDate" format="yyyy-MM-dd" />&nbsp;</td>     
  </tr>
  </s:iterator>      
    </table></td>
  </tr>
  <tr>
  <td height="50" colspan="3" align="center" bgcolor="#A6D3EF"   class="font02 STYLE1">
  <%@ include file="/entity/student/pub/bulletin_detail.jsp" %>
  </td>
  </tr>
</table>
</body>
</html>
