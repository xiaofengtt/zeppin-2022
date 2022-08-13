<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.vote.*,com.whaty.platform.vote.basic.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>

<%@ include file="pub/priv.jsp"%>
<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String mock_login = (String)session.getAttribute("mock_login");
%>
<%
try
{
	String openCourseId = openCourse.getId();
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	VoteNormalManage voteManage = interactionManage.creatVoteNormalManage();
	int totalItems;
	totalItems=voteManage.getActiveCourseVotePapersNum(openCourseId);
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
	List votePaperList = voteManage.getActiveCourseVotePapers(pageover,openCourseId);
	
%>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top" background="images/top_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <!-- tr> 
                      <td width="217" height="104" rowspan="2" valign="top"><img src="images/ggl.gif" width="217" height="86"></td>
                      <td height="46">&nbsp;</td>
                    </tr-->
                    <tr> 
                      <td align="right" valign="top"> 
                        <table width="95%" border="0" cellpadding="0" cellspacing="0">
                          <tr>
                            <td align="left"><img src="images/xb.gif" width="48" height="32"></td>
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
                      
                <td height="26" background="images/tabletop.gif">
                	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
                    	<tr>
                      	<td width="80%" align="center" class="title">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
                      	<%
		if(mock_login == null || !mock_login.equals("1")){
		%>
                      	<td width="20%" align="center" class="title">操&nbsp;&nbsp;&nbsp;&nbsp;作</td>
                    	<%
			}
		%>
                    	</tr>
                  	</table>
                  </td>
                 </tr>
                    
                    <tr>
                      <td align="center" background="images/tablebian.gif">
						<table width="90%" border="0" cellspacing="0" cellpadding="0" class="mc1">
						<%                         
							String links = "";
							if("teacher".equalsIgnoreCase(userType))
								links = "../../vote/vote_result.jsp";
							else if("student".equalsIgnoreCase(userType))
								links = "../../vote/vote.jsp";
                          	int trNo=1;	//用来表示每行
                          	int trNoMod;	//记录是奇数行还是偶数行
                          	String trBgcolor;	//设置每行的背景色
                                                    	
                          	Iterator it=votePaperList.iterator();
                          
                          	while(it.hasNext()){
								//根据奇偶行，决定每行的背景颜色          	
                          		trNoMod=trNo%2;
                          		if(trNoMod==0){
                          			trBgcolor="#E8F9FF";
                          		}else{
                          			trBgcolor="#ffffff";
                          		}
                             	VotePaper votePaper = (VotePaper)it.next();
						%>
                       
                         <tr> 
                            <td bgcolor="<%=trBgcolor%>" width="80%" align="left" class="td1">&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=links %>?paper_id=<%=votePaper.getId()%>" target="_blank"><%=votePaper.getTitle()%></a></td>
                           <%
		if(mock_login == null || !mock_login.equals("1")){
		%>
                            <td bgcolor="<%=trBgcolor%>" width="20%" align="center" class="td1"><a href="<%=links %>?paper_id=<%=votePaper.getId()%>" target="_blank">进入</a></td>
                         <%
			}
		%>
                          </tr>
                        <%  
                       		trNo++;	
                          	}
                        %>     
                          
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td><img src="images/tablebottom.gif" width="812" height="4"></td>
                    </tr>
                  </table><br>
                </td>
              </tr>
              <tr>
                <td align="center">
					<%@ include file="pub/dividepage.jsp"%>
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
