<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="./pub/priv.jsp"%>

<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>
<%
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		String rule = interactionManage.getTeachRule(teachclass_id);
		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="./images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="./images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="./images/xb3.gif" width="17" height="15"> 
                              <strong>教 学 要 求</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="./images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=70%>
                          		
                            		<tr>
                            			<td class="text2">&nbsp;&nbsp;<%=fixnull(rule) %></td>
                            		</tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="./images/table_03.gif" width="765" height="21"></td>
                    </tr>
     <%
     	if("teacher".equals(userType))
			{
      %>
		<tr>
			<td align=center><input type=button value="修改" onclick="javascript:window.location='teach_rule_add.jsp'"></td>
		</tr>
		<%
			}
		 %>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
<%
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
