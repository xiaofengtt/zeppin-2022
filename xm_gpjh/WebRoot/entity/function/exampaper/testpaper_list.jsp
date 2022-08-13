<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ page import="com.whaty.platform.database.oracle.standard.test.paper.OracleTestPaper" %>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此试卷吗？"))
		window.navigate(link);
}
function DetailInfo(paperId)
{
	window.open('testpaper_info.jsp?paperId='+paperId,'','dialogWidth=630px;dialogHeight=550px,scrollbars=yes');
}
</script>
</head>
<%
	String mock_login = (String)session.getAttribute("mock_login");
%>
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
	String backPageInt = request.getParameter("pageInt");
	session.removeAttribute("historyPaperId");
	
	//try {
		String testCourseId = (String) session.getValue("testCourseId");  
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		int totalItems = testManage.getExamPapersNumByOnlineExamCourse(testCourseId);
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
		List testPaperList = testManage.getExamPapersByOnlineExamCourse(pageover,testCourseId);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="../exampaper/images/zxzc.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="65%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <td align="center"><a href="testpaper_add.jsp" class="tj">[添加新试卷]</a>&nbsp;</td>
                            <td align="center"><a href="paperpolicy_add.jsp" class="tj">[添加新组卷策略]</a>&nbsp;</td>
                            <td align="center"><a href="testpaperpolicy_list.jsp?type=list" class="tj">[查看组卷策略]</a>&nbsp;</td>
<%
	}
%>	                            	
                            <!-- td align="center"><a href="testhistory_list.jsp" class="tj">[查看结果]</a></td-->
                            <td></td>                            
                            <td width="35">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="90%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="../images/tabletop.gif" style="background-repeat: no-repeat;filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='../images/tabletop.gif');">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="18%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="10%" align="center" class="title">发布人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="14%" align="center" class="title">发布时间</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <!-- td width="8%" align="center" class="title">试卷用途</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td-->
                    <%
					if("teacher".equalsIgnoreCase(userType))
					{
					%>	
                      <td width="5%" align="center" class="title">状态</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <%
					}
					%>	
                      <td width="30%" align="center" class="title">操作</td>
                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=testPaperList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	OracleTestPaper testPaper = (OracleTestPaper)it.next();
                             	String paperId = testPaper.getId();
								String paperTitle = testPaper.getTitle();
								String creatUser = testPaper.getCreatUser();
								String creatDate = testPaper.getCreatDate();
								String paperType = testPaper.getType();
								String status = testPaper.getStatus();
								String status_link = "";
								if(status.equals("1"))
								{
									status_link = "<a href='testpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=1&note=无效'>有效</a>";
								}
								else
								{
									status_link = "<a href='testpaper_changestatus.jsp?paperId="+paperId+"&title="+title+"&pageInt="+pageInt+"&status=0&note=有效'>无效</a>";
								}
								if(("student".equalsIgnoreCase(userType)&&status.equals("1"))||("teacher".equalsIgnoreCase(userType)))
								{
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            <td width="5%" align="center" valign="middle" class="td1"><!-- a href="#" onclick="return false;"><img src="images/xb2.gif" width="8" height="8" border="0" onClick="DetailInfo('<%=paperId%>')"></a--></td>
                            <td width="17%"  class="td1"> 
                            <a href="testpaper_info1.jsp?id=<%=paperId%>" target=_blank><%=paperTitle%></a>
							</td>
                            <td width="10%" align="center"  class="td1"><%=creatUser%></td>
                            <td width="11%" align="center"  class="td1"><%=creatDate%></td>
                            <!-- td width="11%" align="center"  class="td1"><%if(paperType.equals("test")) out.print("考试");else if(paperType.equals("homework")) out.print("作业");%></td-->
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
                            <td width="30%" align="center"  class="td1">
                            
<%
	String user_id = "("+user.getId()+")"+user.getName();
	int isTest = testManage.getExamPaperHistorysNum(user_id,paperId,null,null,teachclass_id);
	
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
							<a href="testhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>">查看已交试卷</a>
                            <a href="testpaper_edit.jsp?id=<%=paperId%>&pageInt=<%=pageover.getPageInt() %>">编辑基本信息</a>
                            <a href="testpaper_edit_bypolicy.jsp?id=<%=paperId%>&pageInt=<%=pageover.getPageInt() %>">编辑试题</a>
                            <a href="#" onclick="javascript:window.open('preview_testpaper_pre.jsp?id=<%=paperId%>','zc','resizable,width=800,height=600')" class="button">预览试题</a>
                            <a href="javascript:cfmdel('testpaper_delete.jsp?title=<%=title%>&pageInt=<%=pageInt%>&paperId=<%=paperId%>')" class="button">删除</a>
<%
	}
	else
	{
		if(isTest>0)
		{
%>	 
		已做&nbsp;&nbsp;&nbsp;
		<a href="testhistory_list.jsp?paperId=<%=paperId%>&prepageInt=<%=pageInt %>">查看已交试卷</a>					
<%
		}
		else
		{
		%>
		<font color=red>未做</font>&nbsp;&nbsp;&nbsp;
		<%
			if(mock_login == null || !mock_login.equals("1")){
		%>
		<a href="#" onclick="javascript:window.open('testpaper_pre.jsp?id=<%=paperId%>','ks','resizable,width=800,height=600')">开始考试</a>
		<%
			}
		%>
		<%
		}
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
                      <td><img src="../images/tablebottom.gif" width="90%" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="images/bottomtop.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td background="images/bottom02.gif">
                       <%@ include file="../pub/dividepage.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/bottom03.gif" width="100%" height="7"></td>
                    </tr>
                    <tr>
                      <td align=center>
                   <!--  <img src="images/fh.gif" width="80" height="24" onclick="location.href='testpaper_list.jsp?pageInt=<%=pageInt %>'">
                   -->
                    </td>
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
	//}
//catch(Exception e)
//{
//	out.print(e.getMessage());
//	return;
//}
%>
