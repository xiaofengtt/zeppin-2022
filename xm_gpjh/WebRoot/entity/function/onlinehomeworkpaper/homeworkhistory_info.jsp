<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%!
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
	return str;
}
%>
<%
try{
	
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

	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	if("teacher".equalsIgnoreCase(userType))
		totalItems = testManage.getHomeworkPaperHistorysNum(null,paperId,null,null,teachclass_id);
	else
		totalItems = testManage.getHomeworkPaperHistorysNum(user_id,paperId,null,null,teachclass_id);
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
	if("teacher".equalsIgnoreCase(userType)){
		historyList = testManage.getHomeworkPaperHistorys(pageover,null,paperId,null,null,teachclass_id);
	}
	else{
		historyList = testManage.getHomeworkPaperHistorys(pageover,user_id,paperId,null,null,teachclass_id);
	}

	HomeworkPaperHistory history = testManage.getHomeworkPaperHistory(((HomeworkPaperHistory)historyList.get(0)).getId());
	String isCheck = history.getStatus();
	HashMap map = history.getTestResultMap();
	List idList = (List)map.get("idList");
	HashMap userAnswer = (HashMap)map.get("userAnswer");
	HashMap standardAnswer = (HashMap)map.get("standardAnswer");
	HashMap Title = (HashMap)map.get("title");
	HashMap Type = (HashMap)map.get("type");
	HashMap standardScore = (HashMap)map.get("standardScore");
	HashMap userScore = (HashMap)map.get("userScore");
	HashMap Note = (HashMap)map.get("note");
	String total_score = (String)map.get("totalScore");
	String total_note = (String)map.get("totalNote");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function DetailInfo()
{
	window.opener.location.reload();
}
</script>
</head>
<body background="images/bg2.gif" onload="DetailInfo();">
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="800" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">作&nbsp;&nbsp;业</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="67%" border="0" align="center" cellpadding="0" cellspacing="0">
						<%
							int count = 1;
							boolean status =false;
							String id = "";
							String title = "";
							String type = "";
							String user_answer = "";
							String standard_answer = "";
							String standard_score = "";
							String user_score = "";
							String note = "";
							boolean flagWenda = false;
							//Set set = Title.keySet();
							for(Iterator it = idList.iterator();it.hasNext();)
							{
								id = (String)it.next();
								type = (String)Type.get(id);
								if(!type.equalsIgnoreCase(TestQuestionType.YUEDU))
								{
									
									if(type.equalsIgnoreCase(TestQuestionType.WENDA)) {
										flagWenda = true;
									}
									title = (String)Title.get(id);
									standard_answer = (String)standardAnswer.get(id);
									/*
									if(standard_answer!=null&&!standard_answer.equals("")&&!standard_answer.equals("null"))
									{
										standard_answer = standard_answer.replace('|',',');
										status = true;
									}
									else
									{
										standard_answer = "未答此题！";
										status = false;
									}*/
									status = true;
									standard_score = (String)standardScore.get(id);
									user_answer = (String)userAnswer.get(id);
									user_score = (String)userScore.get(id);
									note = (String)Note.get(id);
									if(user_answer!=null)
										user_answer = user_answer.replace('|',',');
									else
										user_answer = "未答此题！";
									if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
									{
										if(user_answer.equals("0"))
											user_answer = "错误";
										if(user_answer.equals("1"))
											user_answer = "正确";
									}
									if(note==null || note.equals("") || note.equals("null"))
										note = "";
						%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#000000;line-height:24px"  onClick="DetailInfo('<%=id%>')"><%=title%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#000000;line-height:24px">
                            <%
	                           PaperQuestion paperQuestion = testManage.getPaperQuestion(id);
	                           String questionCore = "";
	                           if(type.equalsIgnoreCase(TestQuestionType.DANXUAN) || type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
									questionCore = XMLParserUtil.getSingleMultiContent(paperQuestion.getQuestionCore());
								if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA))
									questionCore = XMLParserUtil.getBlankAnswerContent(paperQuestion.getQuestionCore());
								if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
									questionCore = XMLParserUtil.getJudgContent(paperQuestion.getQuestionCore());
                            %>
                            <%=questionCore.trim()%>
                            </td>
                          </tr>
                        <%
                        if(status)
                        {
                        %>
                          <!-- tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<%=standard_answer%></td>
                          </tr-->
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">您的答案：<%=user_answer%></td>
                          </tr>
                       	<%
                       	}
                       	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;<%=standard_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">题目分数：<%=standard_score%></td>
                          </tr>
                          
                          <%if(flagWenda) {
                          		if(isCheck.endsWith("1")) {
                          %>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<%=user_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">批注：<%=note%></td>
                          </tr>
                          <%
                          		} else {
                          %>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px"><!-- <font color="red">教师未批改</font> --></td>
                          </tr>
                          <%
                          		}
                          	} 
                          %>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;</td>
                          </tr>
                        <%
                        }
                        else
                		{
							List titleList = (List)Title.get(id);
							List standard_answerList = (List)standardAnswer.get(id);
							List standard_scoreList = (List)standardScore.get(id);
							List user_answerList = (List)userAnswer.get(id);
							List user_scoreList = (List)userScore.get(id);
							List noteList = (List)Note.get(id);
							title = (String)titleList.get(0);
							String notetmp = (String)noteList.get(0);
							String scoretmp = (String)user_scoreList.get(0);
							String scoretmp1 = (String)standard_scoreList.get(0);
							if(scoretmp==null || scoretmp.equals("") ||scoretmp.equals("null"))
							{
								standard_answer = "未答此题！";
								status = false;
							}
							else
								status = true;
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=title%></td>
                          </tr>
                        <%
                     	if(status)
                        {
                        	for(int k=1;k<titleList.size();k++)
                        	{
                        		title = (String)titleList.get(k);
								standard_answer = (String)standard_answerList.get(k);
								if(standard_answer!=null)
									standard_answer = standard_answer.replace('|',',');
								else
									standard_answer = "";
								standard_score = (String)standard_scoreList.get(k);
								user_answer = (String)user_answerList.get(k);
								if(user_answer!=null)
									user_answer = user_answer.replace('|',',');
								user_score = (String)user_scoreList.get(k);
								note = (String)noteList.get(k);
                        %>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<%=k%>. <%=title%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;标准答案：<%=standard_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;您的答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;题目分数：<%=standard_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;此题得分：<%=user_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;批注：<%=note%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                       	<%
                       		}
                       	}
                    	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;<%=standard_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总分数：<%=scoretmp1%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总得分：<%=scoretmp%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总批注：<%=notetmp%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                        		}
                        	}
                        %>
                        <!--  <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">作业总得分：<%=total_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">作业总批注：<%=total_note%></td>
                          </tr>   --> 
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/gb.gif" width="80" height="24" onclick="window.close()"></td><!--
			                      <td class="text"><img src="images/zjfh.gif" width="100" height="30" onclick="window.location.href='homeworkpaper_list.jsp'"></td>
			                    --></tr>
			                  </table></td>
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
