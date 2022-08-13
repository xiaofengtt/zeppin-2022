<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.onlinetest.OnlineExamCourse"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此考试课程吗？"))
		window.navigate(link);
}
function DetailInfo(testCourseId)
{
	window.open('onlinetestcourse_info.jsp?testCourseId='+testCourseId,'','width=630,height=550,scrollbars=yes');
}
</script>
</head>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>	
<%
	String title = fixnull(request.getParameter("title"));
try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	int totalItems = 0;
	if("teacher".equalsIgnoreCase(userType))
		totalItems = testManage.getOnlineExamCoursesNum(null,title,null,null,null,null,null,null,null,teachclass_id,null);
	else
		totalItems = testManage.getOnlineExamCoursesNum(null,title,null,null,null,"1",null,null,null,teachclass_id,null);
	//----------分页开始---------------
		String spagesize = (String) session.getValue("pagesize");  
		String spageInt = request.getParameter("pageInt");
		Page pageover = new Page();
		pageover.setTotalnum(totalItems);
		pageover.setPageSize(spagesize);
		pageover.setPageInt(spageInt);
		int pageNext = pageover.getPageNext();
		int pageLast = pageover.getPagePre();
		int maxPage = pageover.getMaxPage();
		int pageInt = pageover.getPageInt();
		int pagesize = pageover.getPageSize();
		String link = "&title="+title;
		//----------分页结束---------------
	List testCourseList = null;
	
	if("teacher".equalsIgnoreCase(userType))
		testCourseList = testManage.getOnlineExamCourses(pageover,null,title,null,null,null,null,null,null,null,teachclass_id,null);
	else
		testCourseList = testManage.getOnlineExamCourses(pageover,null,title,null,null,null,"1",null,null,null,teachclass_id,null);
	
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../images/zxzc.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
                    <tr> 
                      <td align="right" valign="top"> 
                       
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <tr>
                		<td   class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                                         <strong>工具与案例</strong></td>   
                	</tr>
                	<br><br>				
                   <td height="26" background="../images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="18%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="22%" align="center" class="title">时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="10%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="11%" align="center" class="title">发布时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					if("teacher".equalsIgnoreCase(userType))
					{
					%>	
                      <td width="5%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					}
					%>	
                      <td width="13%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="#ffffff"> 
                            
                            <td width="13%"  class="td1" colspan=10>目前没有记录 </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="100%" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="806" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="806" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                        <%@ include file="../pub/dividepage.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="806" height="7"></td>
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
	}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
%>
