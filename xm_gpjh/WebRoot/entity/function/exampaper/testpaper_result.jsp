<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
try{
	String ids = request.getParameter("id");
	HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
	HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
	HashMap Title = (HashMap)session.getAttribute("Title");
	HashMap Type = (HashMap)session.getAttribute("Type");
	HashMap Score = (HashMap)session.getAttribute("Score");//就是这里得到的标准得分。
	HashMap userScore = new HashMap();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function saveTime()
{
	//document.cookie="TestTime=0";
}

</script>
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<!--table width="100%" border="0" cellpadding="0" cellspacing="0">
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
							float total_score = 0f;
							boolean status =false;
							String id = "";
							String title = "";
							String type = "";
							String user_answer = "";
							String standard_answer = "";
							String standard_score = "";
							float user_score = 0f;
							Set set = Title.keySet();
							for(Iterator it = set.iterator();it.hasNext();)
							{
								id = (String)it.next(); 
								type = (String)Type.get(id);
								if(!type.equalsIgnoreCase(TestQuestionType.YUEDU))
								{
									title = (String)Title.get(id);
									standard_answer = (String)standardAnswer.get(id);
									if(standard_answer!=null)
										standard_answer = standard_answer.replace('|',',');
									else
										standard_answer = "";
									standard_score = (String)Score.get(id);
									user_answer = (String)userAnswer.get(id);
									if(user_answer!=null)
									{
										user_answer = user_answer.replace('|',',');
										status = true;
									}
									else
									{
										if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA))
											user_answer = "";
										else
											user_answer = "未答此题！";
										status = false;
									}
								
								
									if(user_answer!=null && standard_answer.equals(user_answer))
										user_score = Float.parseFloat(standard_score);
									else
										user_score = 0f;
									if(type.equalsIgnoreCase(TestQuestionType.TIANKONG) || type.equalsIgnoreCase(TestQuestionType.WENDA))
										user_score = 0f;
									total_score+=user_score;
									userScore.put(id,Float.toString(user_score));
									
						%>
                          <tr> 
                            <td valign="middle" align="left"><%=count++%>．<%=title%></td>
                          </tr>
                        <%
                     	if(status)
                        {
                        %>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">标准答案：<%=standard_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">您的答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<%=user_score%></td>
                          </tr>
                       	<%
                       	}
                     	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;<%=user_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                		}
                		else
                		{
							List titleList = (List)Title.get(id);
							List standard_answerList = (List)standardAnswer.get(id);
							List standard_scoreList = (List)Score.get(id);//就是从这里得到的标准得分来影响了后面的分数。
							List user_answerList = (List)userAnswer.get(id);
							List user_scoreList = new ArrayList();
							float scoretmp = 0f;
							user_scoreList.add("");
							title = (String)titleList.get(0);
							if(user_answer==null)
							{
								user_answer = "未答此题！";
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
								if(user_answerList!=null)
								{
									user_answer = (String)user_answerList.get(k);
									user_answer = user_answer.replace('|',',');
								}
								else
									user_answer = "未答此题！";
								if(user_answer!=null && standard_answer.equals(user_answer))
									user_score = Float.parseFloat(standard_score);
								else
									user_score = 0f;
								total_score+=user_score;
								scoretmp+=user_score;
								user_scoreList.add(Float.toString(user_score));
									
                        %>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<%=k%>. <%=title%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left"  style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;标准答案：<%=standard_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;您的答案：<%=user_answer%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目得分：<%=user_score%></td>
                          </tr>
                       	<%
                       		}
                       	}
                    	else
                       	{
                       	%>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;<%=user_answer%></td>
                          </tr>
                        <%
                        }
                       	%>
                          <tr> 
                            <td valign="middle" align="left">题目总得分：<%=scoretmp%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left">&nbsp;</td>
                          </tr>
                        <%
                        		user_scoreList.set(0,Float.toString(scoretmp));
								userScore.put(id,user_scoreList);
                        		}
                        	}
							session.setAttribute("UserScore",userScore);
                        %>
                          <tr> 
                            <td valign="middle" align="left">试卷总得分：<%=total_score%></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/bcfh.gif" width="100" height="30" onclick="window.location.href='testpaper_resultexe.jsp?id=<%=ids%>&totalScore=<%=total_score%>'"></td><!--
			                      <td class="text"><img src="images/zjfh.gif" width="100" height="30" onclick="window.location.href='homeworkpaper_list.jsp'"></td>
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
      </table-->
      <script>
      	window.location.href="testpaper_resultexe.jsp?id=<%=ids%>&totalScore=<%=total_score%>";
      </script>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
