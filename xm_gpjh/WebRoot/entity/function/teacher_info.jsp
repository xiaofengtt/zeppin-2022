<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>
<%@ include file="./pub/priv.jsp"%>

<%!

   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
   
   String[] sex={"男","女"};
%>
<%
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		List  teacherList = (List)interactionManage.getTeachers(teachclass_id);
		
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
                              <strong>教 师 简 介</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <%
                    	if(teacherList.size()>0)
						{	
							for(int j=0;j<teacherList.size();j++){
							Teacher courseTeacher = (Teacher)teacherList.get(j);
                     %>
                    <tr>
                      <td background="./images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=70%>
                            		<tr>
                            			<td width=30% class="text2">姓　　名</td>
                            			<td class="text2"><%=courseTeacher.getName() %></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">性　　别</td>
                            			<td class="text2"><%=courseTeacher.getNormalInfo().getGender()%></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">电    话</td>
                            			<td class="text2"><%=fixnull(courseTeacher.getNormalInfo().getPhones())%></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">移动电话</td>
                            			<td class="text2"><%=fixnull(courseTeacher.getNormalInfo().getMobilePhones())%></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">职　　称</td>
                            			<td class="text2"><%=fixnull(courseTeacher.getNormalInfo().getTitle())%></td>
                            		</tr>
                            		<tr>
                            			<td class="text2">电子邮箱</td>
                            			<td class="text2">
                            			<%
                            				if(courseTeacher.getNormalInfo().getEmail()!=null)                            				
                            				for(int i=0;i<courseTeacher.getNormalInfo().getEmail().length;i++)
                            				{              
                            					out.print(fixnull(courseTeacher.getNormalInfo().getEmail()[i]));
                            				} 
                            				%>
                            			</td>
                            		</tr>
                            		<tr>
                            			<td class="text2" valign=top>简　　介</td>
                            			<td class="text2"><%=fixnull(courseTeacher.getNormalInfo().getNote())%></td>
                            		</tr>
                            		<tr>
                            			<td class="text2" valign=top colspan="2">
                            			<%if(teacherList.size()>0 && j<teacherList.size()-1){
                            		%>
                            			<hr align="left" width="250"/>
                            		<%
                            	} %></td>
                            		</tr>
                            	</table>
							</td>
							</tr>
     </table></td>
    </tr>
    <%
    	}}
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
