<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>北京交通大学远程教育石家庄教学中心</title>

<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此结果吗？"))
		window.navigate(link);
}
function DetailInfo(tsId)
{
	window.open('homeworkhistory_info.jsp?tsId='+tsId);
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
try
{
	String paperId = "";
	paperId = fixnull((String)session.getAttribute("historyPaperId"));
	if(paperId.equals(""))
		paperId = request.getParameter("paperId");
	session.setAttribute("historyPaperId",paperId);
	String prepageInt = request.getParameter("prepageInt");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	String user_id = "("+user.getId()+")"+user.getName();
	int totalItems = 0;
	if("teacher".equalsIgnoreCase(userType))
		totalItems = testManage.getExperimentPaperHistorysNum(null,paperId,null,null,teachclass_id);
	else
		totalItems = testManage.getExperimentPaperHistorysNum(user_id,paperId,null,null,teachclass_id);
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
		String link = "";
		//----------分页结束---------------
	List historyList = null;
	if("teacher".equalsIgnoreCase(userType))
		historyList = testManage.getExperimentPaperHistorys(pageover,null,paperId,null,null,teachclass_id);
	else
		historyList = testManage.getExperimentPaperHistorys(pageover,user_id,paperId,null,null,teachclass_id);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="images/kcsy.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center"><a href="homeworkpaper_list.jsp?pageInt=<%=prepageInt %>" class="tj">[返回实验列表]</a></td>
                            <td width="35">&nbsp;</td>
                       </tr>
                        </table>
                      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center">
				<table width="812" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="26" background="../images/tabletop.gif">
                	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
                    <tr>
                      <td width="5%" height="17">&nbsp;</td>
                      <td width="24%" align="center" class="title">实验名称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                    <td width="16%" align="center" class="title">上交人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">上交时间</td>
                      <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">状态</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">操作</td>

                    </tr>                    
                  	</table>
                  </td>
                 </tr>
                    	<%                         
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=historyList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	ExperimentPaperHistory history = (ExperimentPaperHistory)it.next();
                             	String tsId = history.getId();
								String userId = history.getUserId();
								
								String login_id1 = "";
								int index1 = -1;
								int index2 = -1;
								if(user_id.indexOf("(")!=-1){
									index1 = userId.indexOf("(");
								}
								if(user_id.indexOf(")")!=-1){
									index2 = userId.indexOf(")");
								}
								if(index1!=-1&&index2!=-1){
									login_id1 = userId.substring(index1+1,index2);
								}

								String name = userId;
								if(userId!=null){
								name = userId.substring(index2+1,userId.length());
								}
								

								String realUserId = login_id1;
								String regNo = testManage.getRegNoByUserId(realUserId);
								String name2 = "("+regNo+")"+name;
							
								
								String testDate = history.getTestDate();
								String testPaperId = history.getTestPaperId();
								String status = history.getStatus();
								if(status!=null && status.equals("0"))
									status = "未批改";
								else if(status!=null && status.equals("1"))
									status = "已批改";
								ExperimentPaper paper = testManage.getExperimentPaper(testPaperId);
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            <td width="5%" align="center" valign="middle" class="td1"><a href="homeworkhistory_info.jsp?tsId=<%=tsId%>" border=0 target=_blank><img src="images/xb2.gif" width="8" height="8" border="0"></a></td>
                            <td width="24%"  class="td1""><a href="homeworkhistory_info.jsp?tsId=<%=tsId%>" border=0 target=_blank><%="("+testPaperId+")"+paper.getTitle()%></a></td>
                            <td width="18%" align="center"  class="td1"><font size=2><%=name%></font></td>
                            <td width="17%" align="center"  class="td1"><font size=2><%=testDate%></font></td>
                            <td width="17%" align="center"  class="td1"><font size=2><%=status%></font></td>
                            <td width="15%" align="center"  class="td1"><font size=2>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            
                            <a href="homeworkhistory_check.jsp?pageInt=<%=pageInt%>&tsId=<%=tsId%>" class="button">批改</a>
                            <a href="javascript:cfmdel('homeworkhistory_delexe.jsp?pageInt=<%=pageInt%>&tsId=<%=tsId%>')" class="button">删除</a>
				            
<%
	}
	else
	{
		if(status.equals("未批改"))
		{
%>	
	<a href="javascript:cfmdel('homeworkhistory_delexe.jsp?pageInt=<%=pageInt%>&tsId=<%=tsId%>')" class="button">删除</a></font>
<%---	&nbsp;&nbsp;&nbsp;&nbsp;<a href="homeworkpaper_pre.jsp?id=<%=paperId%>&tsId=<%=tsId%>" class="button">修改</a>--%>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:window.open('homeworkpaper_pre.jsp?id=<%=paperId%>&tsId=<%=tsId%>','xgzy','resizable,width=800,height=600')">重做</a>
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
                       		trNo++;	
                          	}
                        %> 
                    
                    <tr>
                      <td><img src="../images/tablebottom.gif" width="812" height="4"></td>
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
