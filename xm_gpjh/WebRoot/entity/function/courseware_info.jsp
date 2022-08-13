<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.courseware.basic.*"%>
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
   String courseware_id = fixnull(request.getParameter("courseware_id"));
   try{
			InteractionFactory interactionFactory = InteractionFactory.getInstance();
			InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
			Courseware cware = interactionManage.getCourseware(courseware_id);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/ht生殖健康咨询师培训网charset=utf-8">
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
                              <strong><%=cware.getName()%></strong></td>
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
                            			<td width=30% class="text2">课件名称</td>
                            			<td class="text2"><%=cware.getName() %></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">制作教师</td>
                            			<td class="text2"><%=cware.getAuthor() %></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">制作时间</td>
                            			<td class="text2"><%=cware.getFoundDate() %></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">课件描述</td>
                            			<td class="text2"><%=cware.getNote() %></td>
                            		</tr>
                            	</table></td>
              </tr>
            </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>