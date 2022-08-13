<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多选题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	function onCommit(b)
	{
		var dire = b;
		var select = false;
		var answer = document.getElementsByName("index");
		for(i=0;i<answer.length;i++)
		{
			if(answer[i].checked)
				select = true;
		}
		if(!select)
		{
			alert("请选择答案！");
			return;
		}
		document.multi.action="multi_infoexe.jsp?direction="+dire;
		document.multi.submit();
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
	String user_answer = "";
	if(userAnswer!=null)
	{
		user_answer = (String)userAnswer.get(id);
	}
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		PaperQuestion pq = testManage.getPaperQuestion(id);
		String coreXML = pq.getQuestionCore();
		List list = XMLParserUtil.parserSingleMulti(coreXML);
		String body = (String)list.get(0);
		String standard_answer = (String)list.get(list.size()-1);
		standardAnswer.put(id,standard_answer);
%>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="42" style="padding-left:53px;padding-top:8px" class="text3">多项选择题</td>
  </tr>
<form id="multi" name="multi" action="multi_infoexe.jsp" method="post">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="qNum" value="<%=qNum%>">
  <tr> 
    <td valign="top"  align=center> <table height="390" border="0" cellpadding="0" cellspacing="0" align=center>
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td class="content1"><%="第"+(Integer.parseInt(qNum)+1)+"题"+"   "+body%></td>
              </tr>
              <tr> 
                <td valign="top" style="padding-left:30px">
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                	<%
                		for(int i=1; i<list.size()-2; i=i+2) {
                			String index = (String)list.get(i);
                			String content = (String)list.get(i+1);
                	%>
                    <tr> 
                      <td width="10%" class="mc1"><input type="checkbox" name="index" value="<%=index%>" <%if(user_answer!=null && user_answer.indexOf(index)>=0) out.print("checked");%>></td>
                      <td class="mc1"><%=index+"、"+content%></td>
                    </tr>
                    <%
                    	}
                    %>
                    
                  </table></td>
              </tr>
              <tr> 
                <td ><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr align="center"> 
                      <td>
                       <%
                      if(qNum!=null && !qNum.equals("0"))
                      {
                       %>
                      <input type=button value="上一题" border=0 onclick="javascript:onCommit('last')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <%
                      	}
                      	if(qNum!=null && Integer.parseInt(qNum)<qList.size()-1)
                      	{
                       %>
                       <input type=button value="下一题" border=0 onclick="javascript:onCommit('next')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

</form>
</table>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
