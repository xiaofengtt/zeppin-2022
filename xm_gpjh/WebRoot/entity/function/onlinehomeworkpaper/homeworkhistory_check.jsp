<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
try{
	String tsId = request.getParameter("tsId");
	String pageInt = request.getParameter("pageInt");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	HomeworkPaperHistory history = testManage.getHomeworkPaperHistory(tsId);
	String paperId = history.getTestPaperId();
	HashMap map = history.getTestResultMap();
	List idList = (List)map.get("idList");
	session.putValue("idList",idList);
	HashMap userAnswer = (HashMap)map.get("userAnswer");
	session.putValue("UserAnswer",userAnswer);
	HashMap standardAnswer = (HashMap)map.get("standardAnswer");
	session.putValue("StandardAnswer",standardAnswer);
	HashMap Title = (HashMap)map.get("title");
	session.putValue("Title",Title);
	HashMap Type = (HashMap)map.get("type");
	session.putValue("Type",Type);
	HashMap standardScore = (HashMap)map.get("standardScore");
	session.putValue("StandardScore",standardScore);
	HashMap userScore = (HashMap)map.get("userScore");
	HashMap Note = (HashMap)map.get("note");
	String total_score = (String)map.get("totalScore");
	String total_note = (String)map.get("totalNote");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">

<script>
	function isNum(x)
	{
		var isRight=true;
		var count = 0;
		for (var i=0;i<x.length;i++)
		{
			var k = x.charAt(i);
			if ((k<'0' && k!='.') || (k>'9' && k!='.' ))
			{
				isRight=false;
				break;
			}
			if(k=='.')
				count++;
		}
		if(count>1)
		{
			isRight=false;
		}	
		return isRight;
	}
	
	function checkscore(name,score,sscore)
	{
		if(!isNum(score))
		{
			alert("分数请输入数字！");
			document.getElementById(name).focus();
			document.getElementById(name).select();
			return;
		}
		if(score*1>sscore*1)
		{
			alert("评分不能大于题目分数！");
			document.getElementById(name).focus();
			document.getElementById(name).select();
			return;
		}
	}

</script>

</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<form action="homeworkhistory_checkexe.jsp" method="post" name="check">
<input type="hidden" name="tsId" value=<%=tsId%>>
<input type="hidden" name="paperId" value=<%=paperId%>>
<input type="hidden" name="pageInt" value=<%=pageInt%>>
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
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">试&nbsp;&nbsp;题</td>
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
									
									if(standard_answer!=null&&!standard_answer.equals("")&&!standard_answer.equals("null"))
									{
										standard_answer = standard_answer.replace('|',',');
										status = true;
									}
									else
									{
										standard_answer = "此题没有标准答案！";
										status = false;
									}
									
									status = true;
									standard_score = (String)standardScore.get(id);
									user_answer = (String)userAnswer.get(id);
									user_score = (String)userScore.get(id);
									note = (String)Note.get(id);
									if(user_answer!=null)
										user_answer = user_answer.replace('|',',');
									else
										user_answer = "未答此题！";
									if(note==null || note.equals("") || note.equals("null"))
										note = "";
										
										if(type.equalsIgnoreCase(TestQuestionType.PANDUAN)){
											if("1".equals(standard_answer)){
											standard_answer="正确";
											}if("0".equals(standard_answer)){
											standard_answer="错误";
											}
										}
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=title%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">
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
                            <%=questionCore%>
                            </td>
                          </tr>
                        <%
                        if(status)
                        {
                        %>
                          <!-- 
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<%=standard_answer%></td>
                          </tr>
                           -->
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">所填答案：<%=user_answer%></td>
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
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<input type="text" size=2 style="text-align:center" name="<%=id+"_score"%>" value=<%=user_score%> onblur="checkscore('<%=id+"_score"%>',this.value,'<%=standard_score%>');totalScore();" ></td>
                          </tr>
                          <%
                          if(flagWenda) {
                           %>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">批注：<textarea name="<%=id+"_note"%>"><%=note%></textarea></td>
                          </tr>
                          <%
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
                          <!-- tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;标准答案：<%=standard_answer%></td>
                          </tr-->
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;所填答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;题目分数：<%=standard_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;此题得分：<input type="text" size=2 style="text-align:center" name="<%=id+"_"+k+"_score"%>" value=<%=user_score%> ></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;批注：<textarea name="<%=id+"_"+k+"_note"%>"><%=note%></textarea></td>
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
                        <input type="hidden" name="<%=id+"_totalnum"%>" value="<%=titleList.size()%>">
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总分数：<%=scoretmp1%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总得分：<input type="text" size=2 style="text-align:center" name="<%=id+"_score"%>" value=<%=scoretmp%> ></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总批注：<textarea name="<%=id+"_note"%>"><%=notetmp%></textarea></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                        		}
                        	}
                        %>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总得分：<input type="text" size=2 style="text-align:center" name="total_score" value=<%=total_score%> ></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总批注：<textarea name="total_note"><%=total_note%></textarea></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/OK.gif" width="80" height="24" onclick="document.check.submit()"></td>
			                      <td class="text"><img src="images/tlfh.gif" width="80" height="24" onclick="window.location.href='homeworkhistory_list.jsp?pageInt=<%=pageInt%>'"></td>
			                    </tr>
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
</form>
</body>
</html>
<script>
	function totalScore()
	{
		var tags = document.getElementsByTagName("INPUT");
		var total = 0;
		for(var i=0;i<tags.length;i++)
		{
			var t = tags[i].type;
			var name = tags[i].name;
			if(t == 'text' && name.indexOf("score") != -1 && name.indexOf("total") < 0)
			{
				total += parseInt(tags[i].value);
			}
		}
		document.check.total_score.value = total;
	}
	totalScore();
</script>