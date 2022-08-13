<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.history.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>

<%
try{
	String tsId = request.getParameter("tsId"); 
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	ExamPaperHistory history = testManage.getExamPaperHistory(tsId);
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
	
	if(total_score.equals("null")) {
		total_score = "";
	}
	String total_note = (String)map.get("totalNote");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
function DetailInfo(id)
{
	//window.showModalDialog('store_question_info.jsp?id='+id,'','dialogWidth=630px;dialogHeight=550px');
}
</script>
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden'>
<table width="70%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
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
							//Set set = Title.keySet();
							for(Iterator it = idList.iterator();it.hasNext();)
							{
								id = (String)it.next();
								type = (String)Type.get(id);
								if(!type.equalsIgnoreCase(TestQuestionType.YUEDU))
								{
									title = (String)Title.get(id);
									standard_answer = (String)standardAnswer.get(id);
									user_answer = (String)userAnswer.get(id);
									if(standard_answer!=null&&!standard_answer.equals("")&&!standard_answer.equals("null") || user_answer!=null)
									{
										standard_answer = standard_answer.replace('|',',');
										status = true;
									}
									else
									{
										standard_answer = "未答此题！";
										status = false;
									}
									if(type.equalsIgnoreCase(TestQuestionType.PANDUAN))
									{
										if(standard_answer.equals("0"))
											standard_answer = "错误";
										if(standard_answer.equals("1"))
											standard_answer = "正确";
									}
									standard_score = (String)standardScore.get(id);
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
									String displayNote = "";
									if(user_score == null || user_score.equals("0.0") || user_score.equals("0"))
										displayNote = "<font color='red'>(待批阅)</font>";
						%>
                          <tr> 
                            <td valign="middle" align="left" onClick="DetailInfo('<%=id%>')">第<%=count++%>题</td>
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
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">此题得分：<%=user_score%>&nbsp;<%=displayNote%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">批注：<%=note%></td>
                          </tr>
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
							int yuedutotal = 0;
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
								String displayNote = ""; 
									
								yuedutotal +=  Integer.parseInt(standard_score);

								if(user_score == null || user_score.equals("0.0") || user_score.equals("0"))
									displayNote = "<font color='red'>(待批阅)</font>";
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
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;&nbsp;&nbsp;此题得分：<%=user_score%>&nbsp;<%=displayNote%></td>
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
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">&nbsp;&nbsp;题目总分数：<%=yuedutotal%><%//=scoretmp1%></td>
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
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总得分：<%=total_score%></td>
                          </tr>
                          <tr> 
                            <td valign="middle" align="left" style="font-size:12px;color:#7f7f7f;line-height:24px">试卷总批注：<%=total_note%></td>
                          </tr>
			              <tr> 
			                <td ><table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			                    <tr align="center"> 
			                      <td class="text"><img src="images/gb.gif" width="80" height="24" onclick="window.close()"></td><!--
			                      <td class="text"><img src="images/zjfh.gif" width="100" height="30" onclick="window.location.href='testpaper_list.jsp'"></td>
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
