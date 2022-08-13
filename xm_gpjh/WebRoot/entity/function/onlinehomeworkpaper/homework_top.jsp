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
	document.getElementById("commit").disabled = true;
	document.getElementById("status").style.display = "";
	parent.submain.chkSubmit();
	setTimeout('parent.location.href= "homeworkpaper_result.jsp?id=<%=id%>"',10000);
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
                            <td width="25%" height="53" background="images/wt_03.gif" align="right" valign="middle"><div id="status" class="text3"  style="display:none"><font color="red" size=+1>作业提交中请等待...</font></div></td>
                            <td width="50%" height="53" background="images/wt_03.gif" align="right" valign="middle">
                            <span><a href="javascript:window.top.opener.location.reload();window.top.close();"  border=0><img src="images/gb.gif" border=0></a></SPAN>
                            </td>
                          </tr>
                        </table></td>
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
