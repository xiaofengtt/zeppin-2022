<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>国培计划</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">

  </head>
  
  <body>
<div id="main_content">
    <div class="content_title">指定的填报回执单位别表</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="700">
				
                <s:iterator value="receiptUnit"  id="unit_">
                	<tr>
                    <td height="22"><s:property value="#unit_[1]"/></td>
                    </tr>
                </s:iterator>
                
			</table>
	  </div>
	<div align="center"><img src="/entity/student/images/close.gif" title="关闭" style="cursor: hand" onclick="javascript:window.close();" ></div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>
