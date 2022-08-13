<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>报名信息录入</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">报名信息录入</div id="zlb_title_end"></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p>&nbsp;</p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"></td>
              <td width="77%"> <s:property value="operateresult"/></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td ></td>
              <td></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</body>
</html>