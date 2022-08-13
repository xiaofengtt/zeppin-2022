<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="/entity/studentStatus/derateFeeApply_apply.action" method="get" >
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">减免申请</div></td>
  </tr>            
  <tr>
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p><font color="red"><s:property value="msg"></s:property></font></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
           
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">&nbsp;准考证号*</span></td>
              <td width="77%"><input type="text" name="examCardNum" value="<s:property value='examCardNum'></s:property>"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">&nbsp;申请理由&nbsp;&nbsp;</span></td>
              <td valign="bottom"><textarea name="applyNote" ><s:property value='applyNote'></s:property></textarea></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td ></td>
              <td><input type=submit value = "提交" /></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>
</body>
</html>