<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.encode.*" %>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库--单选题添加</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<%
	String id = request.getParameter("id");
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
	
	String diff = request.getParameter("diff");
	String cognizetype = request.getParameter("cognizetype");
	String referencetime = request.getParameter("referencetime");
	String referencescore = request.getParameter("referencescore");
	String title = request.getParameter("title");
	String[] purposeVal = request.getParameterValues("purpose");
	String type = request.getParameter("type");
	String questioncore = request.getParameter("body");
	String studentnote = request.getParameter("studentnote");
	String teachernote = request.getParameter("teachernote");
	
	int singleNum = Integer.parseInt(request.getParameter("singleNum"));
	int multiNum = Integer.parseInt(request.getParameter("multiNum"));
	int judgeNum = Integer.parseInt(request.getParameter("judgeNum"));
	int blankNum = Integer.parseInt(request.getParameter("blankNum"));
	int answerNum = Integer.parseInt(request.getParameter("answerNum"));
	int totalNum = Integer.parseInt(request.getParameter("totalNum"));
	//out.println("singleNum:" + singleNum + "<br>");
	//out.println("multiNum:" + multiNum + "<br>");
	//out.println("judgeNum:" + judgeNum + "<br>");
	//out.println("blankNum:" + blankNum + "<br>");
	//out.println("answerNum:" + answerNum + "<br>");
	//out.println("totalNum:" + totalNum + "<br>");
	
	String purpose = "";
	for(int i=0; i<purposeVal.length; i++)
		purpose = purpose + purposeVal[i] + "|";
	if(purpose.length() > 0)
		purpose = purpose.substring(0, purpose.length()-1);
	
	Hashtable ht = new Hashtable();
	
	String xml = "<question><body>" + HtmlEncoder.encode(questioncore) + "</body>";
	for(int i=1; i<=singleNum; i++) {
		String subXml = "<subquestion>";
		String serial = request.getParameter("singleSerial"+i);
		String subBody = request.getParameter("singleBody"+i);
		String subType = request.getParameter("singleType"+i);
		String subTitle = request.getParameter("singleTitle"+i);
		String subTime = request.getParameter("singleTime"+i);
		String subScore = request.getParameter("singleScore"+i);
		String subAnswer = request.getParameter("singleAnswer"+i);
		String[] subOptions = request.getParameterValues("singleOptions"+i);
		subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
			+ subTime + "</referencetime><referencescore>" + HtmlEncoder.encode(subScore) + "</referencescore><body>" 
			+ HtmlEncoder.encode(subBody) + "</body><select>";
		for(int j=0; j<subOptions.length; j++) {
			int charCode = j + 65;		//选项字母的ASCII码
			String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
			subXml = subXml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(subOptions[j]) + "</content></item>";
		}
		
		subXml = subXml + "</select><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
		ht.put(serial, subXml);
	}
	
	for(int i=1; i<=multiNum; i++) {
		String subXml = "<subquestion>";
		String serial = request.getParameter("multiSerial"+i);
		String subBody = request.getParameter("multiBody"+i);
		String subType = request.getParameter("multiType"+i);
		String subTitle = request.getParameter("multiTitle"+i);
		String subTime = request.getParameter("multiTime"+i);
		String subScore = request.getParameter("multiScore"+i);
		String[] subAnswers = request.getParameterValues("multiAnswer"+i);
		String[] subOptions = request.getParameterValues("multiOptions"+i);
		subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
			+ subTime + "</referencetime><referencescore>" + subScore + "</referencescore><body>" 
			+ HtmlEncoder.encode(subBody) + "</body><select>";
		for(int j=0; j<subOptions.length; j++) {
			int charCode = j + 65;		//选项字母的ASCII码
			String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
			subXml = subXml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(subOptions[j]) + "</content></item>";
		}
		
		String subAnswer = "";
		for(int j=0; j<subAnswers.length; j++) {
			subAnswer = subAnswer + subAnswers[j] + "|";
		}
		if(subAnswer.length() > 0)
			subAnswer = subAnswer.substring(0, subAnswer.length()-1);
		
		//out.println(subAnswers.length + "<br>");
			
		subXml = subXml + "</select><answer>" + subAnswer + "</answer></subquestion>";
		ht.put(serial, subXml);
	}
	
	for(int i=1; i<=judgeNum; i++) {
		String subXml = "<subquestion>";
		String serial = request.getParameter("judgeSerial"+i);
		String subBody = request.getParameter("judgeBody"+i);
		String subType = request.getParameter("judgeType"+i);
		String subTitle = request.getParameter("judgeTitle"+i);
		String subTime = request.getParameter("judgeTime"+i);
		String subScore = request.getParameter("judgeScore"+i);
		String subAnswer = request.getParameter("judgeAnswer"+i);
		subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
			+ subTime + "</referencetime><referencescore>" + HtmlEncoder.encode(subScore) + "</referencescore><body>" 
			+ HtmlEncoder.encode(subBody) + "</body><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
		ht.put(serial, subXml);
	}
	
	for(int i=1; i<=blankNum; i++) {
		String subXml = "<subquestion>";
		String serial = request.getParameter("blankSerial"+i);
		String subBody = request.getParameter("blankBody"+i);
		String subType = request.getParameter("blankType"+i);
		String subTitle = request.getParameter("blankTitle"+i);
		String subTime = request.getParameter("blankTime"+i);
		String subScore = request.getParameter("blankScore"+i);
		String subAnswer = request.getParameter("blankAnswer"+i);
		subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
			+ subTime + "</referencetime><referencescore>" + subScore + "</referencescore><body>" 
			+ HtmlEncoder.encode(subBody) + "</body><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
		ht.put(serial, subXml);
	}
	
	for(int i=1; i<=answerNum; i++) {
		String subXml = "<subquestion>";
		String serial = request.getParameter("answerSerial"+i);
		String subBody = request.getParameter("answerBody"+i);
		String subType = request.getParameter("answerType"+i);
		String subTitle = request.getParameter("answerTitle"+i);
		String subTime = request.getParameter("answerTime"+i);
		String subScore = request.getParameter("answerScore"+i);
		String subAnswer = request.getParameter("answerAnswer"+i);
		subXml = subXml + "<type>" + HtmlEncoder.encode(subType) + "</type><title>" + HtmlEncoder.encode(subTitle) + "</title><referencetime>"
			+ subTime + "</referencetime><referencescore>" + HtmlEncoder.encode(subScore) + "</referencescore><body>" 
			+ HtmlEncoder.encode(subBody) + "</body><answer>" + HtmlEncoder.encode(subAnswer) + "</answer></subquestion>";
		ht.put(serial, subXml);
	}
	//out.println(ht.size());
	for(int i=1; i<=ht.size(); i++) {
		String subXML = (String)ht.get(String.valueOf(i));
		xml = xml + subXML;
	}
	xml = xml + "</question>";
	
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		
		String creatuser = user.getName();
//		String creatdate = DateFormat.getDateTimeInstance().format(new Date());
//		creatdate = creatdate.substring(0, creatdate.indexOf(" "));
	 java.util.Date sDate = new java.util.Date();
	    	 SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	    	 String creatdate=sDateFormat.format(sDate);	
		int i = testManage.updateStoreQuestion(id, title, creatuser, creatdate, diff, "keyword", xml,
			loreId, cognizetype, purpose, referencescore, referencetime, studentnote, teachernote, type);
		if(i > 0) {
%>
<script type="text/javascript">
	alert("修改成功");
	location.href = "lore_store_question.jsp?id=<%=loreId%>&loreDirId=<%=loreDirId%>";
</script>
<%
		} else {
%>
<script type="text/javascript">
	alert("修改失败");
	window.history.back();
</script>
<%		
		}		

	} catch (Exception e) {
		out.print(e.toString());
	}
%>