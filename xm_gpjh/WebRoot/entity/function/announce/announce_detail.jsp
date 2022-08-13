<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>

<%@ include file="../pub/priv.jsp"%>

<script  language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除本公告吗？"))
		window.navigate(link);
}
</script>

<%
	String announce_id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	String title = request.getParameter("title");
	String publisher_name = request.getParameter("publisher_name");
	
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	
	Announce announce = interactionManage.getAnnounce(announce_id);
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/ggl.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="../images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
                              <strong><%=announce.getTitle()%></strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="../images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2"><%=announce.getBody()%>
</td></tr>
     </table></td>
    </tr>
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
                    <tr>
                      <td><br>
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
<%
	if("teacher".equals(userType) && announce.getPublisherId().equals(user.getId()))
	{
%>                          
                            <td><a href="announce_edit.jsp?id=<%=announce_id%>&pageInt=<%=pageInt%>&title=<%=title%>&publisher_name=<%=publisher_name%>" class="tj">[修改本公告]&nbsp;&nbsp;</a></td>
                            <td> <a href="javascript:cfmdel('announce_delexe.jsp?id=<%=announce_id%>&pageInt=<%=pageInt%>&title=<%=title%>&publisher_name=<%=publisher_name%>')" class="tj">[删除本公告]&nbsp;&nbsp;</a></td>
<%
	}
%>	                            
                            <td> <a href="announce_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>&publisher_name=<%=publisher_name%>" class="tj">[返回公告栏]&nbsp;&nbsp;</a> </td>
                          </tr>
                        </table></td>
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
