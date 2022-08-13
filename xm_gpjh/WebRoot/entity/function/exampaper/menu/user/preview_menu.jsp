<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.whaty.platform.test.paper.ExamPaper"%>
<%@page import="com.whaty.platform.test.question.PaperQuestion"%>
<%@page import="com.whaty.platform.test.question.TestQuestionType"%>
<%@ include file="../../../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
%>
<html>
<head>
<title></title>
<link href="../js/tree/menu.css" rel="stylesheet" type="text/css">
<script src="../js/tree/menu.js" type="text/javascript"></script>
<script language="JavaScript">
function window_onload()
{
	initialize();
	setTimeout(expandall,500);
}
//
var judge=1;
function expandall()
{
	var o = document.getElementById('topB');
	if (judge==0)
	{
	
		closeAll();
		judge=1;
		o.src='../js/tree/icon-closeall.gif';
		o.alt='全部展开';
		document.getElementById("topText").innerText = "( 展开全部 )";
	}
	else
	{
		openAll();
		judge=0;
		o.src='../js/tree/icon-expandall.gif';
		o.alt='全部折叠';
		document.getElementById("topText").innerText = "( 折叠全部 )";
	}
}
</SCRIPT>
</head>
<body class="scllbar" style="background-color:transparent" bgcolor="#FFFFFF" text="#000000" onselectstart="return false;"  oncontextmenu="" onLoad="window_onload()">
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		ExamPaper paper = testManage.getExamPaper(id);
		List questionList = paper.getPaperQuestion();
		HashMap Score = new HashMap();
		HashMap Title = new HashMap();
		HashMap Type = new HashMap();
		List singleList = new ArrayList();
		List multiList = new ArrayList();
		List judgeList = new ArrayList();
		List blankList = new ArrayList();
		List answerList = new ArrayList();
		List comprehensionList = new ArrayList();
		for(int i=0; i<questionList.size(); i++) {
			PaperQuestion pq = (PaperQuestion)questionList.get(i);
			String pq_type = pq.getType();
			if(!pq_type.equalsIgnoreCase(TestQuestionType.YUEDU))
			{
				Score.put(pq.getId(),pq.getScore());
				
			}
			Type.put(pq.getId(),pq_type);
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
			{
				comprehensionList.add(pq);
				List scoreList = new ArrayList();
				scoreList.add(pq.getScore());
				Score.put(pq.getId(),scoreList);
				//List titleList = new ArrayList();
				//titleList.add(pq.getTitle());
				//Title.put(pq.getId(),titleList);
			}
		}
		session.setAttribute("singleList",singleList);
		session.setAttribute("multiList",multiList);
		session.setAttribute("judgeList",judgeList);
		session.setAttribute("blankList",blankList);
		session.setAttribute("answerList",answerList);
		session.setAttribute("comprehensionList",comprehensionList);

		session.setAttribute("Score",Score);
		
		session.setAttribute("Type",Type);
%>
<table id=control width="80%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="16" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand"  onclick="expandall(document.getElementById('topB'))"><img src="../js/tree/icon-expandall.gif" id="topB" width="16" height="15" class="button" vspace="2" alt="全部展开"></td>
    <td id="topText" align="left" valign="top" style="font-size:9pt;color:#666666;cursor:hand;padding-top:4px"  onclick="expandall(document.getElementById('topB'))">( 折叠全部 )</td>
  </tr>
</table>
<table border=0>
  <tr>
    <td>
	<script language="javascript" type="text/javascript">
	// treemenu 的参数意义依次为：资源目录，如"treemenu/image/";树名称；树图标

	//objTree	= new treemenu("","课程超市－课程列表","../js/tree/usericon.gif");
	// add_item 的参数意义依次为：该项编号，不能重复；父id，所属的上一层的编号；该项显示的内容；折叠时的图标；展开时的图标；网址或命令；指向的窗口
<%
		ArrayList qList = new ArrayList();
		List idList = new ArrayList();
		int qNum = 0;
		if(singleList.size()>0) {
	%>
	add_item(1,0,"单选题","../js/tree/close.gif","../js/tree/open.gif","#","");
		<%

				for(int i=0; i<singleList.size(); i++) {
					PaperQuestion question = (PaperQuestion)singleList.get(i);
					String qid = question.getId();
					String qindex = question.getIndex();
					String title = question.getTitle();
					
					int serial = 1000+i+1;
		%>
		add_item(<%=serial%>,1,"<%="第"+(qNum+1)+"题"%>","../js/tree/mono.gif","../js/tree/usericon.gif","single_info.jsp?id=<%=qid%>&qNum=<%=qNum%>","submain");
		<%
					Title.put(question.getId(),"第"+(qNum+1)+"题");
					idList.add(qid);
					qList.add("single_info.jsp?id="+qid+"&qNum="+qNum);
					qNum ++;
				}
			} 
			if(multiList.size()>0) {
		%>
		
	
	add_item(2,0,"多选题","../js/tree/close.gif","../js/tree/open.gif","#","");
		<%
				for(int i=0; i<multiList.size(); i++) {
					PaperQuestion question = (PaperQuestion)multiList.get(i);
					String qid = question.getId();
					String qindex = question.getIndex();
					String title = question.getTitle();
					
					int serial = 2000+i+1;
		%>
		add_item(<%=serial%>,2,"<%="第"+(qNum+1)+"题"%>","../js/tree/mono.gif","../js/tree/usericon.gif","multi_info.jsp?id=<%=qid%>&qNum=<%=qNum%>","submain");
		<%
					Title.put(question.getId(),"第"+(qNum+1)+"题");
					idList.add(qid);
					qList.add("multi_info.jsp?id="+qid+"&qNum="+qNum);
					qNum ++;
				}
			} 
			if(judgeList.size()>0) {
		%>
	add_item(3,0,"判断题","../js/tree/close.gif","../js/tree/open.gif","#","");
		<%
				for(int i=0; i<judgeList.size(); i++) {
					PaperQuestion question = (PaperQuestion)judgeList.get(i);
					String qid = question.getId();
					String qindex = question.getIndex();
					String title = question.getTitle();
					
					int serial = 3000+i+1;
		%>
		add_item(<%=serial%>,3,"<%="第"+(qNum+1)+"题"%>","../js/tree/mono.gif","../js/tree/usericon.gif","judge_info.jsp?id=<%=qid%>&qNum=<%=qNum%>","submain");
		<%
					Title.put(question.getId(),"第"+(qNum+1)+"题");
					idList.add(qid);
					qList.add("judge_info.jsp?id="+qid+"&qNum="+qNum);
					qNum ++;
				}
			} 
			if(blankList.size()>0) {
		%>

	add_item(4,0,"填空题","../js/tree/close.gif","../js/tree/open.gif","#","");
		<%
				for(int i=0; i<blankList.size(); i++) {
					PaperQuestion question = (PaperQuestion)blankList.get(i);
					String qid = question.getId();
					String qindex = question.getIndex();
					String title = question.getTitle();
					
					int serial = 4000+i+1;
		%>
		add_item(<%=serial%>,4,"<%="第"+(qNum+1)+"题"%>","../js/tree/mono.gif","../js/tree/usericon.gif","blank_info.jsp?id=<%=qid%>&qNum=<%=qNum%>","submain");
		<%
					Title.put(question.getId(),"第"+(qNum+1)+"题");
					idList.add(qid);
					qList.add("blank_info.jsp?id="+qid+"&qNum="+qNum);
					qNum ++;
				}
			} 
			if(answerList.size()>0) {
		%>
	
	add_item(5,0,"问答题","../js/tree/close.gif","../js/tree/open.gif","#","");
		<%
			
				for(int i=0; i<answerList.size(); i++) {
					PaperQuestion question = (PaperQuestion)answerList.get(i);
					String qid = question.getId();
					String qindex = question.getIndex();
					String title = question.getTitle();
					
					int serial = 5000+i+1;
		%>
		add_item(<%=serial%>,5,"<%="第"+(qNum+1)+"题"%>","../js/tree/mono.gif","../js/tree/usericon.gif","answer_info.jsp?id=<%=qid%>&qNum=<%=qNum%>","submain");
		<%
					Title.put(question.getId(),"第"+(qNum+1)+"题");
					idList.add(qid);
					qList.add("answer_info.jsp?id="+qid+"&qNum="+qNum);
					qNum ++;
				}
			} 
			if(comprehensionList.size()>0) {
		%>
		
	add_item(6,0,"阅读题","../js/tree/close.gif","../js/tree/open.gif","#","");
		<%
			
				for(int i=0; i<comprehensionList.size(); i++) {
					PaperQuestion question = (PaperQuestion)comprehensionList.get(i);
					String qid = question.getId();
					String qindex = question.getIndex();
					String title = question.getTitle();
					
					int serial = 6000+i+1;
		%>
		add_item(<%=serial%>,6,"<%="第"+(qNum+1)+"题"%>","../js/tree/mono.gif","../js/tree/usericon.gif","comprehension_info.jsp?id=<%=qid%>&qNum=<%=qNum%>","submain");
		<%
					Title.put(question.getId(),"第"+(qNum+1)+"题");
					idList.add(qid);
					qList.add("comprehension_info.jsp?id="+qid+"&qNum="+qNum);
					qNum ++;
				}
			} 
		%>
	// menu 的参数意义为：所要显示的树的父id；

	// 该函数返回树的html代码，需要由 write 函数输出。

	document.write(menu(0));
	<%
		session.setAttribute("Title",Title);
		if(qList.size()>0)
		{
	%>
	parent.parent.submain.location= "../../preview_question_info.jsp";
	<%
		}
		session.removeAttribute("qList");
		session.setAttribute("qList",qList);
		session.removeAttribute("idList");
		session.setAttribute("idList",idList);
	%>
	
	//print_arr();
	</script>
    </td>
  </tr>
</table>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>