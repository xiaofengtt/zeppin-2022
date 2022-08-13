<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.encode.*,com.whaty.platform.database.oracle.dbpool" %>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库--多选题添加</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String types = request.getParameter("types");
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	
	String diff = request.getParameter("diff");
	String cognizetype = request.getParameter("cognizetype");
	String referencetime = request.getParameter("referencetime");
	String referencescore = request.getParameter("referencescore");
	String title = request.getParameter("title");
//	String[] purposeVal = request.getParameterValues("purpose");
	String[] purposeVal = new String[]{"ZUOYE|KAOSHI"};
	String type = request.getParameter("type");
	String questioncore = request.getParameter("body");
	String[] options = request.getParameterValues("options");
	String[] answers = request.getParameterValues("answer");
	
		//保证同一门课程下试题题目名称不能重复
	String t_courseId=openCourse.getBzzCourse().getId();
	
	String sql_t="select title from test_storequestion_info where title='"+title
					+"' and lore in (select a.id from test_lore_info a,test_lore_dir b "
					+"where a.loredir=b.id and b.group_id='"
					+ t_courseId
					+ "')";
	dbpool db=new dbpool();
	if(db.countselect(sql_t)>0)
	{
		
		%>
		<script>
		window.top.alert("同一课程下题目名称不能重复，请重新录入题目名称。");
		window.history.back();
		</script>
		<%
		return;
	}
	
	if(answers == null)
	{
		out.println("<script>alert('没有选择正确答案!');window.history.back();</script>");
		return;
	}
	
	String studentnote = request.getParameter("studentnote");
	String teachernote = request.getParameter("teachernote");
	
	String xml = "<question><body>" + HtmlEncoder.encode(questioncore) + "</body><select>";
	String purpose = "";
	for(int i=0; i<purposeVal.length; i++)
		purpose = purpose + purposeVal[i] + "|";
	if(purpose.length() > 0)
		purpose = purpose.substring(0, purpose.length()-1);
		
	for(int i=0; i<options.length; i++) {
		int charCode = i + 65;		//选项字母的ASCII码
		String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
		xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
	}
	
	String answer = "";
	for(int i=0; i<answers.length; i++) {
		answer = answer + answers[i] + "|";
	}
	if(answer.length() > 0)
		answer = answer.substring(0, answer.length()-1);
	xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
	
	//out.print(questioncore);
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String creatuser = user.getName();
//		String creatdate = DateFormat.getDateTimeInstance().format(new Date());
//		creatdate = "20080723";//creatdate.substring(0, creatdate.indexOf(" "));
		 java.util.Date sDate = new java.util.Date();
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		 String creatdate=sDateFormat.format(sDate);
		
		int i = testManage.addStoreQuestion(title, creatuser,	creatdate, diff, "keyword", xml,
			loreId, cognizetype, purpose, referencescore, referencetime, studentnote, teachernote, type);
		if(i > 0) {
%>
<%
	if(types.equals("1")){
%>
<script type="text/javascript">
	alert("添加成功");
	parent.opener.location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>";
	parent.location.href = "store_question_frame.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>";
</script>
<%
	}else{
%>
<script type="text/javascript">
	alert("添加成功");
	parent.opener.location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>";
	top.window.close();
</script>
<%
	}
%>
<%
		} else {
%>
<script type="text/javascript">
	alert("添加失败");
	window.history.back();
</script>
<%		
		}
	} catch (Exception e) {
		out.print(e.toString());
	}
%>