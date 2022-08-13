<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*;" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>网上报名</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
	<link href="/web/registration/registration.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script type="text/javascript" src="/js/check.js"></script> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <form name="print" method="POST" action="/entity/first/firstRegister_addStudent.action" onsubmit='return userAddGuarding();'>
      <table width="1002" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td  width="1002" height="210" valign="top">	      <iframe id="top" name="top" frameborder="0" scrolling="no" src="/web/top.jsp" width="1002" height="210"></iframe></td>
  </tr>
  <tr>
    <td width="1002"  height="100%" valign="top" align="center">
	<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0px 3px 0px;">
      <tr>
        <td><table width="980" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/04.jpg">
          <tr>
            <td width="104"><img src="/web/registration/images/baomin1_03.jpg" width="104" height="29" /></td>
            <td width="798" align="center">
			<table width="240" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:5px;">
              <tr>
                <td bgcolor="#FFFBE5"  class="redtitle">报  名  结  果</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" align="center" style="padding-bottom:5px;"><table width="28%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
                    		<s:if test="operateresult == null">
                    		</s:if>
                    		<s:else>
                    			<tr> 
                            		<td width="100%" align="center" class="td1"><strong><s:property value="operateresult"/></strong></td> 
                             	</tr> 
                             </s:else>
		  <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table>
    </table></td>
  </tr>
  <tr>
    <td width="1002"  height="68" valign="top">
	<iframe id="footer" name="footer" frameborder="0" scrolling="no" src="/web/footer.jsp" width="1002"  height="68" ></iframe>	</td>
  </tr>
</table>

      <map name="Map" id="Map">
  <area shape="rect" coords="272,94,391,127" href="#" />
  <area shape="rect" coords="267,16,386,49" href="#" />
<area shape="rect" coords="73,94,194,128" href="#" />
<area shape="rect" coords="72,15,191,48" href="#" /><area shape="rect" coords="176,55,272,91" href="#" /></map>
 </form>
  </body>
</html>
