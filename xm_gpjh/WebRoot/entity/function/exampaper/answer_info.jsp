<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:directive.page import="java.net.URLDecoder"/>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
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
		var select = false;
		var answer = document.getElementsByName("answer");
		for(i=0;i<answer.length;i++)
		{
			if(answer[i].value.length>0)
				select = true;
		}
		if(!select)
		{
			alert("请填写答案！");
			return;
		}
		document.answers.submit();
	}
	
</script>
<script language=javascript>
	var bLoaded=false; 
	function onCommit(b)
	{
		var dire = b;
		if(currentflag==1)//处于普通编辑模式
		{
			answers.body.value=document.frames.editor.frames.edit1.textEdit.document.body.innerHTML;
		}        
		else 
		{
			if (currentflag==3)//处于预浏览先通编辑模式
			{
				answers.body.value=oDiv.innerHTML;
			}
		}
		document.answers.action = "answer_infoexe.jsp?direction="+dire;
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
		List list = XMLParserUtil.parserBlankAnswer(coreXML);
		String body = (String)list.get(0);
		String standard_answer = (String)list.get(list.size()-1);
		standardAnswer.put(id,standard_answer);
%>
<table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="42" style="padding-left:53px;padding-top:8px" class="text3">问答题</td>
  </tr>
<form id="answers" name="answers" action="answer_infoexe.jsp" method="post">
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
                	<tr> 
                      <td width="10%" class="mc1"></td>
                      <td class="mc1"> <textarea class="smallarea" cols=65" name="body" rows="12"><%if(user_answer!=null) out.print(user_answer);%></textarea>
							<script language=JavaScript src="../../../WhatyEditor/config.jsp"></script><br>
							<script language=JavaScript src="../../../WhatyEditor/edit.js"></script>
							</td>
                    </tr>
                </table>
                </td>
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
                      <!-- a href="javascript:chkSubmit()"><img src="images/OK.gif" width="80" height="24" border="0"></a></td>
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
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
