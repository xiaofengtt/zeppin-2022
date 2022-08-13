<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.onlinetest.OnlineExamCourse"%>
<%@ include file="../pub/priv.jsp"%>
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
                        <table width="90%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <td align="center"><a href="onlinetestcourse_add.jsp" class="tj">[添加新考试]</a>&nbsp;</td>
<%
	}
%>	                            	
                            <td>
                            	<form method="post" action="onlinetestcourse_list.jsp" name="paper_listSearchForm">
                            	<table border="0" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="center" class="mc1">按标题搜索：</td>
                            			<td align="center"><input name="title" type="text" size="20" maxlength="50" value="<%=title%>"/></td>
                            		</tr>                            
                            	</table>
                            </td>
                            <td align="center">
                            	<input type="image" src="../images/search.gif" width="99" height="19" />
                            </td>
                            </form>
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
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
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=testCourseList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	OnlineExamCourse testCourse = (OnlineExamCourse)it.next();
                             	String testCourseId = testCourse.getId();
								String testCourseTitle = testCourse.getTitle();
								String startDate = testCourse.getStartDate();
								String endDate = testCourse.getEndDate();
								String creatUser = testCourse.getCreatUser();
								String creatDate = testCourse.getCreatDate();
								String isAutoCheck = testCourse.getIsAutoCheck();
								String isHiddenAnswer = testCourse.getIsHiddenAnswer();
								String status = testCourse.getStatus();
								java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String s = sdf.format(new Date());
								Date newDate = sdf.parse(s);
								Date startdate = sdf.parse(startDate);
								Date enddate = sdf.parse(endDate);
								long newDateTime = newDate.getTime();
								long startDateTime = startdate.getTime();
								long endDateTime = enddate.getTime();
								if((newDateTime<startDateTime || newDateTime>endDateTime) && "student".equalsIgnoreCase(userType)) continue;
								String status_link = "";
								if(status.equals("1"))
								{
									status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId="+testCourseId+"&title="+title+"&pageInt="+pageInt+"&status=1&note=无效'>有效</a>";
								}
								else
								{
									status_link = "<a href='onlinetestcourse_changestatus.jsp?testCourseId="+testCourseId+"&title="+title+"&pageInt="+pageInt+"&status=0&note=有效'>无效</a>";
								}
								if(("student".equalsIgnoreCase(userType)&&status.equals("1"))||("teacher".equalsIgnoreCase(userType)))
								{
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            
                            <td width="13%"  class="td1"> 
                            <a href="onlinetestcourse_info.jsp?testCourseId=<%=testCourseId%>" target=_blank>&nbsp;&nbsp;<%=testCourseTitle%></a>
							</td>
                            <td width="22%" align="center"  class="td1"><%=startDate%>到<%=endDate%></td>
                            <td width="8%" align="left"  class="td1"><%=creatUser%></td>
                            <td width="12%" align="center"  class="td1"><%=creatDate%></td>
                            <%
							if("teacher".equalsIgnoreCase(userType))
							{
							%>	
                            <td width="5%" align="center"  class="td1">
                            <%=status_link%>
                            </td>
                            <%
                            }
                            %>
                            <td width="10%" align="center"  class="td1">
                            <a href="onlinetestcourse_session.jsp?testCourseId=<%=testCourseId%>&isAutoCheck=<%=isAutoCheck%>&isHiddenAnswer=<%=isHiddenAnswer%>&pageInt=<%=pageover.getPageInt() %>">进入</a>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <a href="onlinetestcourse_edit.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>">编辑</a>
                            <a href="javascript:cfmdel('onlinetestcourse_delexe.jsp?title=<%=title%>&pageInt=<%=pageInt%>&testCourseId=<%=testCourseId%>')" class="button">删除</a>
<%
	}
%>	                            	
				            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    	<%  
                    			}
                       		trNo++;	
                          	}
                        %> 
                    
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
