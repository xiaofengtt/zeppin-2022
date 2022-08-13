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
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function cfmdel(link)
{
	if(confirm("您确定要删除此试卷吗？"))
		window.navigate(link);
}
function DetailInfo(tsId)
{
	window.open('testhistory_info.jsp?tsId='+tsId);
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
		totalItems = testManage.getExamPaperHistorysNum(null,paperId,null,null,teachclass_id);
	else
		totalItems = testManage.getExamPaperHistorysNum(user_id,paperId,null,null,teachclass_id);
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
		String link = "&paperId="+paperId;
		//----------分页结束---------------
	List historyList = null;
	if("teacher".equalsIgnoreCase(userType))
		historyList = testManage.getExamPaperHistorys(pageover,null,paperId,null,null,teachclass_id);
	else
		historyList = testManage.getExamPaperHistorys(pageover,user_id,paperId,null,null,teachclass_id);
%>

<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="../images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="images/zxzc.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="70%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="center"><a href="testpaper_list.jsp?pageInt=<%=prepageInt %>" class="tj">[返回试卷列表]</a></td>
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
                      <td width="24%" align="center" class="title">试卷名称</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">答题人</td>
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="16%" align="center" class="title">答题时间</td>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
					  <td width="1%" align="center" valign="bottom"><img src="images/topxb.gif"></td>
                      <td width="15%" align="center" class="title">操作</td>
<%
	}
%>	
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
                             	ExamPaperHistory history = (ExamPaperHistory)it.next();
                             	String tsId = history.getId();
								String userId = history.getUserId();
								String realUserId = userId.substring(userId.indexOf("(")+1,userId.indexOf(")"));
								String realUserName = userId.substring(userId.indexOf(")")+1);
								String regNo = testManage.getRegNoByUserId(realUserId);
								String testDate = history.getTestDate();
								String testPaperId = history.getTestPaperId();
								ExamPaper paper = testManage.getExamPaper(testPaperId);
						%>
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="99%" border="0" cellspacing="0" cellpadding="0" class="mc1">
                          <tr bgcolor="<%=trBgcolor%>"> 
                            <td width="5%" align="center" valign="middle" class="td1"><img src="images/xb2.gif" width="8" height="8" border="0"></td>
                            <td width="24%"  class="td1"><a href="testhistory_info.jsp?tsId=<%=tsId%>" target=_blank><%="("+testPaperId+")"+paper.getTitle()%></a></td>
                            <td width="18%" align="center"  class="td1"><%=realUserName%></td>
                            <td width="17%" align="left"  class="td1"><%=testDate%></td>
<%
	if("teacher".equalsIgnoreCase(userType))
	{
%>	
                            <td width="15%" align="center"  class="td1">
                            <a href="testhistory_check.jsp?pageInt=<%=pageInt%>&tsId=<%=tsId%>" class="button">批改</a>
                            <a href="javascript:cfmdel('testhistory_delexe.jsp?pageInt=<%=pageInt%>&tsId=<%=tsId%>')" class="button">删除</a>
				            </td>
<%
	}
%>	
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
                       <%@ include file="../pub/dividepage1.jsp" %>
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
