<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="java.net.URLDecoder"/>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	function onCommit()
	{
		document.answers.submit();
	}
	
</script>
</head>

<body>
<form id="answers" name="answers" action="question_infoexe.jsp" method="post">
<table width="98%" border="0" cellspacing="0" cellpadding="0">
<%
	HashMap standardAnswer = (HashMap)session.getAttribute("StandardAnswer");

	List singleList = (ArrayList)session.getAttribute("singleList");
	List multiList = (ArrayList)session.getAttribute("multiList");
	List judgeList = (ArrayList)session.getAttribute("judgeList");
	List blankList = (ArrayList)session.getAttribute("blankList");
	List answerList = (ArrayList)session.getAttribute("answerList");
	List comprehensionList = (ArrayList)session.getAttribute("comprehensionList");
	int qNum=1;
	try {
		if(singleList!=null && singleList.size()>0)
		{
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">单项选择题</td>
  </tr>
<%
			for(int t=0;t<singleList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)singleList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = (String)list.get(0);
				String standard_answer = (String)list.get(list.size()-1);
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%="第"+(qNum++)+"题"+"   "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<%
                		for(int i=1; i<list.size()-2; i=i+2) {
                			String index = (String)list.get(i);
                			String content = (String)list.get(i+1);
                	%>
                    <tr> 
                      <td width="10%" class="mc1"><input type="radio" name="single_<%=id %>" value="<%=index%>"></td>
                      <td width="90%" class="mc1"><%=index+"、"+content %></td>
                    </tr>
                    <%
                    	}
                    %>
                    
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(multiList!=null && multiList.size()>0)
		{
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">多项选择题</td>
  </tr>
<%
			for(int t=0;t<multiList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)multiList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = URLDecoder.decode((String)list.get(0));
				String standard_answer = (String)list.get(list.size()-1);
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%="第"+(qNum++)+"题"+"   "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<%
                		for(int i=1; i<list.size()-2; i=i+2) {
                			String index = (String)list.get(i);
                			String content = (String)list.get(i+1);
                	%>
                    <tr> 
                      <td width="10%" class="mc1"><input type="checkbox" name="multi_<%=id %>" value="<%=index%>"></td>
                      <td width="90%" class="mc1"><%=index+"、"+content %></td>
                    </tr>
                    <%
                    	}
                    %>
                    
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(judgeList!=null && judgeList.size()>0)
		{
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">判断题</td>
  </tr>
<%
			for(int t=0;t<judgeList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)judgeList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = (String)list.get(0);
				String standard_answer = (String)list.get(list.size()-1);
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%="第"+(qNum++)+"题"+"   "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="10%" class="mc1"><input type="radio" name="judge_<%=id %>" value="正确"></td>
                      <td width="90%" class="mc1">正确</td>
                    </tr>
                    <tr> 
                      <td width="10%" class="mc1"><input type="radio" name="judge_<%=id %>" value="错误"></td>
                      <td width="90%" class="mc1">错误</td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(blankList!=null && blankList.size()>0)
		{
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">填空题</td>
  </tr>
<%
			for(int t=0;t<blankList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)blankList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = (String)list.get(0);
				String standard_answer = (String)list.get(list.size()-1);
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%="第"+(qNum++)+"题"+"   "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="0%" class="mc1"></td>
                      <td width="100%" class="mc1"><textarea name="blank_<%=id %>"></textarea></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(answerList!=null && answerList.size()>0)
		{
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">问答题</td>
  </tr>
<%
			for(int t=0;t<answerList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)answerList.get(t);
				String coreXML = pq.getQuestionCore();
				String id = pq.getId();
				List list = XMLParserUtil.parserSingleMulti(coreXML);
				String body = (String)list.get(0);
				String standard_answer = (String)list.get(list.size()-1);
				standardAnswer.put(id,standard_answer);
%>
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td width="100%" align="left" class="content1"><%="第"+(qNum++)+"题"+"   "+body%></td>
              </tr>
              <tr> 
                <td width="100%" valign="top" align="left" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="0%" class="mc1"></td>
                      <td width="30%" class="mc1"><textarea name="answer_<%=id %>"></textarea></td>
                      <td style="padding-left:5px"></td>
					  <td width="70%" valign="bottom" style="padding-left:5px"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}

		if(comprehensionList!=null && comprehensionList.size()>0)
		{
%>
  <tr> 
    <td height="42" style="padding-left:23px;padding-top:8px" class="text3">阅读理解题</td>
  </tr>
<%
			HashMap Score = (HashMap)session.getAttribute("Score");
			HashMap Title = (HashMap)session.getAttribute("Title");
			for(int t=0;t<comprehensionList.size();t++)
			{
				PaperQuestion pq = (PaperQuestion)comprehensionList.get(t);
				String id = pq.getId();
				String questionCoreXml = pq.getQuestionCore();
				List coreList = XMLParserUtil.parserComprehension(questionCoreXml);
				String bodyString = (String)coreList.get(0);
				List scoreList = new ArrayList();
				List titleList = new ArrayList();
				List standard_answer = new ArrayList();
				standard_answer.add("");
				titleList.add(pq.getTitle());
				scoreList.add(pq.getScore());
%>
	<INPUT type="hidden" name="comp_<%=id %>_totalNum" value="<%=coreList.size()-1%>">	
  <tr> 
    <td valign="top" style="padding-left:23px;padding-top:8px"> <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td class="content1" width="100%"><%="第"+(qNum++)+"题"+"   "+bodyString%></td>
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
			  		
			  		if(subType.equals(TestQuestionType.DANXUAN)) {
			%>
              <tr> 
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
			              	<%
					        	for(int j=5; j<subList.size()-2; j=j+2) {
									String index = (String)subList.get(j);
									String content = (String)subList.get(j+1);
					        %>
							<tr> 
						      <td width="10%" class="mc1">
						      	<input type="radio" name="comp_<%=id %>_<%=i%>" value="<%=index%>">
						      </td>
						      <td width="90%" class="mc1"><%=index+"、"+content %></td>
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
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
			              	<%
					        	for(int j=5; j<subList.size()-2; j=j+2) {
									String index = (String)subList.get(j);
									String content = (String)subList.get(j+1);
					        %>
							<tr> 
						      <td width="10%" class="mc1">
						      	<input type="checkbox" name="comp_<%=id %>_<%=i%>" value="<%=index%>">
						      </td>
						      <td width="90%" class="mc1"><%=index+"、"+content %></td>
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
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="100%" class="mc1">
						      	<input type="radio" name="comp_<%=id %>_<%=i%>" value="正确">正确&nbsp;<input type="radio" name="comp_<%=id %>_<%=i%>" value="错误">错误
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
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="100%" class="mc1">
						      	<textarea name="comp_<%=id %>_<%=i%>" cols="40" rows="5"></textarea>
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
                <td width="100%" valign="top" style="padding-left:10px">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                	<tr> 
					  <td width="100%" class="mc1"><%=i%>．<%=subTitle%></td>
                	</tr>
                	<tr> 
					  <td width="100%" class="mc1">&nbsp;&nbsp;<%=subBody%></td>
                	</tr>
					<tr> 
						<td width="100%" valign="top" style="padding-left:10px">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
						      <td width="100%" class="mc1">
						      	<textarea name="comp_<%=id %>_<%=i%>" cols="40" rows="5"></textarea>
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
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
<%
			}
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
  <tr> 
    <td width="100%" align="center"><input type=button value="完成自测" border=0 onclick="javascript:onCommit()"></td>
  </tr>
</table>
</body>
</html>
