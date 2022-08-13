<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		HomeworkPaper paper = testManage.getHomeworkPaper(id);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function commitPaper()
{
	//document.cookie="TestTime=0";
	location.href= "homeworkpaper_result.jsp?id=<%=id%>";
}
</script></head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden' >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="98%" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="15%" background="images/st_01.gif" class="text3" style="padding-left:50px">作&nbsp;&nbsp;业</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right" valign="middle">
                            <span><a href="homeworkpaper_result.jsp?id=<%=id%>" onclick="javascript:location.replace(this.href);event.returnValue=false;" border=0><img src="images/tjzy.gif" width="97" height="31" border=0></a></SPAN>
                            <span><a href="homeworkpaper_list.jsp?pageInt=<%=pageInt%>" onclick="javascript:location.replace(this.href);event.returnValue=false;" border=0><img src="images/fh.gif" border=0></a></SPAN>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td width="30%" valign="top"><iframe align="middle" width="273" height="100%" src="homeworkpaper_left.jsp?id=<%=id%>" frameborder="0" scrolling="auto" marginWidth=0 marginHeight=0 ></iframe></td>
                            <td width="1%">&nbsp;</td>
                            <td width="69%" valign="top"><iframe align="left" name="contentBox" width="100%" height="500" src="homeworkpaper_main.jsp" frameborder="0" scrolling="auto" marginWidth=0 marginHeight=0 ></iframe> </td>
                          </tr>
                        </table><br>
                      </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
