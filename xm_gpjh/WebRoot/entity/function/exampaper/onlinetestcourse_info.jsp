<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.onlinetest.OnlineExamCourse"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%
	try {
		String testCourseId = request.getParameter("testCourseId");
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		OnlineExamCourse testCourse = testManage.getOnlineExamCourse(testCourseId);
		String title = testCourse.getTitle();
		String note = testCourse.getNote();
		String startDate = testCourse.getStartDate();
		String endDate = testCourse.getEndDate();
		String status = testCourse.getStatus();
		String isAutoCheck = testCourse.getIsAutoCheck();
		String isHiddenAnswer = testCourse.getIsHiddenAnswer();
		String selected = "";
%>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="160" valign="top" class="text3" style="padding-top:25px">详细信息</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">标题:</td>
		                                  <td valign="top"><%=title%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3">开始时间:</td>
		                                  <td><%=startDate%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3">结束时间:</td>
		                                  <td><%=endDate%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">说明:</td>
		                                  <td valign="top"><%=note%></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<!-- tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">客观题是否自动判卷:</td>
		                                  <td valign="top">
		                                  	<%
		                                  	if(isAutoCheck.equals("1")) 
		                                  			out.print("是");
		                                  	else 
		                                  			out.print("否");
		                                  	
		                                  	%>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">答题完毕后是否显示答案:</td>
		                                  <td valign="top">
		                                  	<%
		                                  	if(isHiddenAnswer.equals("1")) 
		                                  			out.print("是");
		                                  	else 
		                                  			out.print("否");
		                                  	
		                                  	%>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top">
		                                  	<%
		                                  	if(status.equals("1")) 
		                                  			out.print("是");
		                                  	else 
		                                  			out.print("否");
		                                  	
		                                  	%>
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr-->
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="#" class="tj" onclick="javascript:window.close()">[关闭]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
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
