<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="../pub/priv.jsp"%>

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
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
   try{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
		
		InteractionTeachClass  teachclass = interactionManage.getTeachClass(id);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/htm生殖健康咨询师培训网harset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="homeworkpaper_editexe.jsp" method="POST" name="frmAnnounce" onsubmit="return chkSubmit()">
<input type=hidden name=teachclass_id value=<%=teachclass_id %>>
<input type=hidden name=type value=<%=type %>>
<input type=hidden name=id value=<%=id %>>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="../images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="../images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
                              <strong><%=InteractionTeachClassType.typeShow(type) %></strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="../images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            	<tr>
                            <td class="neirong" width=20%>
							<font size=2>题目：</font>
							</td>
							<td class="neirong" valign=top>
							<font size=2><%=teachclass.getTitle() %>
							</td>
						  </tr>
                          		<tr>
                          		<td class="neirong" valign=top width=20%>
							<font size=2>内容：</font>
							</td>
							<td class="neirong" valign=top>
							<%=teachclass.getContent() %>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=button value="返回" onclick="window.location='homeworkpaper_list.jsp?pageInt=<%=pageInt %>&title=<%=title%>'"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
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
