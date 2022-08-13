<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_2</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<SCRIPT>
function cfmdel(link)
{
	if(confirm("您确定要删除这道题目吗？"))
		window.navigate(link);
}

function DetailInfo(type,id)
{
	if(type=='YUEDU')
		window.open('paper_question_comprehension_info.jsp?id='+id,'','dialogWidth=630px;dialogHeight=550px');
	else 
		window.open('paper_question_info.jsp?id='+id,'','dialogWidth=630px;dialogHeight=550px');
	
}
  function doCommit()
  {
  	document.add.submit();
  }
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<%
	String paperId = request.getParameter("paperId");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	TestManage testManage = interactionManage.creatTestManage();
	HomeworkPaper paper = testManage.getHomeworkPaper(paperId);
	List questionLists = paper.getPaperQuestion();
	HashMap paperQuestions = new HashMap();
	List singleList = new ArrayList();
	List multiList = new ArrayList();
	List judgeList = new ArrayList();
	List blankList = new ArrayList();
	List answerList = new ArrayList();
	List comprehensionList = new ArrayList();
	for(int i=0; i<questionLists.size(); i++) {
		PaperQuestion pq = (PaperQuestion)questionLists.get(i);
		String pq_type = pq.getType();
		if(pq_type.equalsIgnoreCase(TestQuestionType.DANXUAN))
			singleList.add(pq);
		if(pq_type.equalsIgnoreCase(TestQuestionType.DUOXUAN))
			multiList.add(pq);
		if(pq_type.equalsIgnoreCase(TestQuestionType.PANDUAN))
			judgeList.add(pq);
		if(pq_type.equalsIgnoreCase(TestQuestionType.TIANKONG))
			blankList.add(pq);
		if(pq_type.equalsIgnoreCase(TestQuestionType.WENDA))
			answerList.add(pq);
		if(pq_type.equalsIgnoreCase(TestQuestionType.YUEDU))
			comprehensionList.add(pq);
	}
	paperQuestions.put(TestQuestionType.DANXUAN,singleList);
	paperQuestions.put(TestQuestionType.DUOXUAN,multiList);
	paperQuestions.put(TestQuestionType.PANDUAN,judgeList);
	paperQuestions.put(TestQuestionType.TIANKONG,blankList);
	paperQuestions.put(TestQuestionType.WENDA,answerList);
	paperQuestions.put(TestQuestionType.YUEDU,comprehensionList);
	Set set = paperQuestions.keySet();

	//session.removeAttribute("paperQuestions");
%>
<table width="489" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="42" background="images/st_05.gif" style="padding-left:53px;padding-top:8px" class="text3">试卷浏览</td>
  </tr>
<form name='add' action='homeworkpaper_addexe_bypolicy.jsp' method='post' class='nomargin' onsubmit="">
<input type="hidden" name="paperId" value=<%=paperId%>>
  <tr> 
    <td valign="top" background="images/st_06.gif" > <table height="390" width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td valign="top" class="bg2"> <table width="100%" border="0" cellspacing="0" cellpadding="6">
              <tr> 
                <td valign="top" style="padding-left:30px">
				          <table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <%
				          	String field = "";
				          	String typename = "";
				          	List questionList = null;
				          	int count = 1;
				          	for(Iterator it = set.iterator();it.hasNext();)
				          	{
				          		field = (String)it.next();
				          		typename = TestQuestionType.typeShow(field);
				          		questionList = (List)paperQuestions.get(field);
				          		if(questionList.size()>0)
				          		{
				          %>
				            <tr>
				              <td align="center">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
				                  <tr valign="bottom" class="mc1"> 
				                    <td colspan="2" valign="bottom"><%=typename%>：</td>
				                    <td colspan="2" ><!--<a href="homeworkpaper_add2_bypolicy.jsp?field=<%=field%>&paperId=<%=paperId%>" title="提交" class="button"><span unselectable="on" class="norm"  style="width:53px;height:15px;padding-top:2px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">添加试题</span></span></a></span>--></td>
				                  </tr>
				                  <%
				                  	for(int i=0;i<questionList.size();i++)
				                  	{
				                  		PaperQuestion question = (PaperQuestion)questionList.get(i);
				                  		
				                  		String link = "";
				                  		if(question.getType().equals("YUEDU"))
											link = "paper_question_comprehension_info.jsp?id="+question.getId();
										else 
											link = "paper_question_info.jsp?id="+question.getId();
				                  %>
				                  <tr valign="bottom" class="mc1"> 
				                    <td width="5%" align="right" valign="bottom" style="padding-right:8px"><%=count++%>.</td>
				                    <td width="60%" valign="bottom">
				                    <a href="<%=link %>" target=_blank><span unselectable="on" class="norm" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text"><%=question.getTitle()%></span></span></a></span> 
				                    </td>
				                    <td width="20%" valign="bottom">分值：<!--<input type="text" name="<%=question.getId()%>score" size="2" style="text-align:center" value=>--><%=question.getScore()%> </td>
				                    <td valign="bottom" width="15%">
				                      &nbsp;<!--<a href="javascript:cfmdel('homeworkpaper_del_bypolicy.jsp?field=<%=field%>&i=<%=i%>&paperId=<%=paperId%>')" title="提交" class="button"><span unselectable="on" class="norm"  style="width:35px;padding-top:2px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text">删 
				                      除</span></span></a></span>--></td>
				                  </tr>
				                  <%
				                  	}
				                  %>
				                  
				                </table></td>
							  </tr>
									<%
									}
									}
									%>
							</table>
              </tr>
              <tr> 
			      <td><table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
			        <tr>
			          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
			        </tr>
			        <tr>
			          <td align="center" valign="middle">
			          <img src="images/gb.gif" width="80" height="24" onclick="window.close()">
			          </td>
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
</form>
</table>
</body>
</html>
