<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公共论坛</title>
<link href="/entity/teacher/css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link href="/entity/teacher/images/layout.css" rel="stylesheet" type="text/css" />
  </head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title"></div>
    <div class="cntent_k">
   	  <div class="k_cc">
 <table border="0" cellpadding="0" cellspacing="0" width="100%">
                  <tr class='table_bg1'>
                    <td  >公共论坛</td> 
                  </tr>
                  <tr> 
                      <td >->>  <a href="/entity/function/commonforum_whaty.jsp" target="_blank">进入公共论坛</a> 
          </td>
        </tr>
</table>        

	  </div>
    </div>
</div>
</body>  

</html>
