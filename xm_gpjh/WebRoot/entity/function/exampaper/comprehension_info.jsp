<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@page import="com.whaty.platform.test.question.TestQuestionType"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>阅读题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	function onCommit(t,b)
	{
		var dire = b;
		for(k=1;k<=t;k++)
		{
			var select = false;
			var answer = document.getElementsByName("answer_"+k);
			//alert("answer_"+k+"  "+answer.length);
			var i=0;
			if(answer.length>1)
			{
				for(;i<answer.length;i++)
				{
					if(answer[i].checked)
					{
						select = true;
						break;
					}
				}
			}
			else
			{
				if(answer[i].value.length>0)
				{
					select = true;
				}
			}
			if(!select)
			{
				alert("请填写答案！");
				answer[i].focus();
				return;
			}
		}
		
		document.answers.action = "comprehension_infoexe.jsp?direction="+dire;
		document.answers.submit();
	}
	
</script>
</head>
<body>
<%
	String id = request.getParameter("id");
	String qNum = request.getParameter("qNum");
	List qList = (ArrayList)session.getAttribute("qList");
	HashMap userAnswer = (HashMap)session.getAttribute("UserAnswer");
	HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");
	HashMap Score = (HashMap)session.getAttribute("Score");
	HashMap Title = (HashMap)session.getAttribute("Title");
	List user_answer = new ArrayList();
	List standard_answer = new ArrayList();
	List scoreList = new ArrayList();
	List titleList = new ArrayList();
	standard_answer.add("");
	if(userAnswer!=null)
	{
		user_answer = (List)userAnswer.get(id);
	}
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		PaperQuestion question = testManage.getPaperQuestion(id);
		String questionCoreXml = question.getQuestionCore();
		List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
		String bodyString = (String)coreList.get(0);
		titleList.add(question.getTitle());
		scoreList.add(question.getScore());
%>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="42"  style="padding-left:53px;padding-top:8px" class="text3">问答题</td>
  </tr>
<form id="answers" name="answers" action="comprehension_infoexe.jsp" method="post">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="qNum" value="<%=qNum%>">
  <tr> 
    <td valign="top"  align=center> <table height="390" border="0" cellpadding="0" cellspacing="0" align=center>
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td class="content1"><%="第"+(Integer.parseInt(qNum)+1)+"题"+"   "+bodyString%></td>
              </tr>
              <%
				int i = 1;
				for(; i<coreList.size(); i++) {
			  		List subList = (List)coreList.get(i);
			  		String subType = (String)subList.get(0);
			  		String subTitle = (String)subList.get(1);
			  		String subTime = (String)subList.get(2);
			  		String subScore = (String)subList.get(3);
			  		String subBody = (String)subList.get(4);
			  		String subAnswer = (String)subList.get(subList.size()-1);
			  		if(subType.equals(TestQuestionType.PANDUAN))
			  		{
			  			if(subAnswer.equals("1"))
			  				subAnswer = "正确";
			  			else
			  				subAnswer = "错误";
			  		}	
			  		standard_answer.add(subAnswer);
			  		titleList.add(subTitle);
			  		scoreList.add(subScore);
			  		String subUserAnswer;
			  		try{
			  			subUserAnswer = (String)user_answer.get(i);
			  		}catch(Exception e)
			  		{
			  			subUserAnswer = "";
			  		}
			  		
			  		if(subType.equals(TestQuestionType.DANXUAN)) {
			%>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
			              	<%
					        	for(int j=5; j<subList.size()-2; j=j+2) {
									String index = (String)subList.get(j);
									String content = (String)subList.get(j+1);
					        %>
							<tr> 
						      <td width="10%" class="mc1"><%=index%>
						      	<input type="radio" name="answer_<%=i%>" value="<%=index%>" <%if(subUserAnswer.contains(index)){%>checked<%}%>>
						      </td>
						      <td class="mc1"><%=content%></td>
						    </tr>
					        <%
					        	}
					        %>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.DUOXUAN)) {
				%>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
			              	<%
					        	for(int j=5; j<subList.size()-2; j=j+2) {
									String index = (String)subList.get(j);
									String content = (String)subList.get(j+1);
					        %>
							<tr> 
						      <td width="10%" class="mc1"><%=index%>
						      	<input type="checkbox" name="answer_<%=i%>" value="<%=index%>" <%if(subUserAnswer.contains(index)){%>checked<%}%>>
						      </td>
						      <td class="mc1"><%=content%></td>
						    </tr>
					        <%
					        	}
					        %>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.PANDUAN)) {
				%>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="10%" class="mc1">
						      	<input type="radio" name="answer_<%=i%>" value="正确" <%if(subUserAnswer.contains("正确")){%>checked<%}%>>正确&nbsp;<input type="radio" name="answer_<%=i%>" value="错误" <%if(subUserAnswer.contains("错误")){%>checked<%}%>>错误
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.TIANKONG)) {
				%>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="10%" class="mc1">
						      	<textarea name="answer_<%=i%>" cols="40" rows="5"><%=subUserAnswer%></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
				<%
					} else if(subType.equals(TestQuestionType.WENDA)) {
				%>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td valign="top" style="padding-left:30px">
						<table width="80%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="10%" class="mc1">
						      	<textarea name="answer_<%=i%>" cols="40" rows="5"><%=subUserAnswer%></textarea>
						      </td>
						    </tr>
						</table>
						</td>
					</tr>
                </table>
                </td>
              </tr>
              	<%
              	}
              }
              	standardAnswer.put(id,standard_answer);
              	Score.put(id,scoreList);
              	Title.put(id,titleList);
				session.setAttribute("Score",Score);
				session.setAttribute("Title",Title);
				session.setAttribute("StandardAnswer",standardAnswer);
              	%>
              </table></td></tr>
              <tr> 
                <td ><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr align="center"> 
                    
                      <td>
                       <%
                      if(qNum!=null && !qNum.equals("0"))
                      {
                       %>
                      <input type=button value="上一题" border=0 onclick="javascript:onCommit('<%=coreList.size()-1%>','last')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <%
                      	}
                      	if(qNum!=null && Integer.parseInt(qNum)<qList.size()-1)
                      	{
                       %>
                       <input type=button value="下一题" border=0 onclick="javascript:onCommit('<%=coreList.size()-1%>','next')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <%
                       	}
                       	else
                       	{
                        %>
                        <input type=button value="完成自测" border=0 onclick="javascript:onCommit('final')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <%
                        }
                         %>
                        <input type=reset value="重填" border=0>&nbsp;&nbsp;
                      <!-- img src="images/OK.gif" width="80" height="24" onclick="onCommit()"></td>
                      <td><img src="images/reset.gif" width="80" height="24" onclick="reset()"--></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="images/st_07.gif" width="489" height="15"></td>
  </tr>
</table>
<INPUT type="hidden" name="totalNum" value="<%=coreList.size()-1%>">
</form>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>