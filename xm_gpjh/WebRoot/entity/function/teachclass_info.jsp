<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
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
	String type = request.getParameter("type");
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		int teachClassNum = interactionManage.getTeachClassListNum(teachclass_id,type);
		
		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" ></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="./images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="./images/xb3.gif" width="17" height="15"> 
                              <strong><%=InteractionTeachClassType.typeShow(type) %></strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <%
                    	if(teachClassNum >0)
						{
							InteractionTeachClass  teachclass = interactionManage.getTeachClass(teachclass_id,type);
                     %>
                    <tr>
                      <td background="./images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=77%>
                            		<tr>
                            			
                            			<td class="text2" width=95%><%=teachclass.getContent() %></td>
                            		</tr>
                            		
                            	</table>
							</td>
							</tr>
							<%
								if("teacher".equals(userType))
								{
							 %>
							<tr>
								<td class="text2" align=center><a href="teachclass_info_edit.jsp?id=<%=teachclass.getId() %>&type=<%=type %>">[修改]</a></td>
							</tr>
							<%
								}
							 %>
				     </table></td>
				    </tr>
	    <%
	    	}
	    	else
	    	{
	     %>
	     		<tr>
                      <td background="./images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=70%>
                            		<tr>
                            			
                            			<td class="text2" width=95% height=300></td>
                            		</tr>
                            		
                            	</table>
							</td>
							</tr>
							<%
								if("teacher".equals(userType))
								{
							 %>
							<tr>
								<td class="text2" align=center><a href="teachclass_info_add.jsp?type=<%=type %>">[添加]</a></td>
							</tr>
							<%
								}
							 %>
				     </table></td>
				    </tr>
	     <%
	     	}
	      %>
        <tr>
		 <td><img src="./images/table_03.gif" width="765" height="21"></td>
                    </tr>

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
