<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>批量开课类型选择</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
 </head>
<body leftmargin="0" topmargin="0" class="scllbar">
<form action="/entity/teaching/prBzzTchOpenCourseReady.action" method="get" >
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">批量开课类型</div></td>
  </tr>          
  <tr>
    <td valign="top" class="pageBodyBorder_zlb" align="center">
    <div class="cntent_k" id="zlb_content_start">
          <p><FONT color="red"><s:property value="msg" escape="false" /></FONT></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">说明:</span></td>
              <td width="77%"> <span class="link" style="Color:red">请选择开课类型，此类型表示将要把某门课程开设为需要的类型，学员学习中心下课程列表中基础与提升的区分就根据此类型。课程基本信息中的课程性质只是标志课程的基本性质。</span></td>
            </tr>
                      
            <tr valign="bottom" align="center">
              <td><input type="hidden" name="pid" value="<%=request.getParameter("bean.id")%>"></td>
              <td align="left"  valign="bottom" ><span class="link">
              <select name="type">
              	<s:iterator value="tchCourse">
              		<option value="<s:property value="id"/>"><s:property value="name"/></option>
              	</s:iterator>
             </select></span></td>
            </tr>
            <tr valign="bottom" align="center">
              <td> &nbsp;</td>
            	<td align="left" colspan="2" valign="bottom"><input type=submit id="submit1" value = "下一步" />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <input type="button" value="返回" onclick="javascript:window.history.back()" > </td>
            </tr>
         </table>
      </div>        
    </td>
  </tr>
</table>
</form>
</body>
</html>
