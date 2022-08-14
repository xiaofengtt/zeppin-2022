<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);
String q_faq_keywords = Utility.trimNull(request.getParameter("q_faq_keywords"));
Integer is_flag = Utility.parseInt(request.getParameter("is_flag"),null);
Integer is_success = Utility.parseInt(request.getParameter("is_success"),new Integer(0));
String faq_title = "";
String faq_keywords = "";
String faq_content = "";
Integer Support_times = new Integer(0);
Integer Oppose_times = new Integer (0);



FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();

boolean bSuccess = false;
List listAll = null;
Map map = null;

if(serial_no.intValue()!= 0){
	vo.setSerial_no(serial_no);
	listAll = faqs.listBySql(vo);
	map = (Map)listAll.get(0); 
}

if(map!= null){
	faq_title = Utility.trimNull(map.get("FAQ_TITLE"));
	faq_keywords = Utility.trimNull(map.get("FAQ_KEYWORDS"));
	faq_content = Utility.trimNull(map.get("FAQ_CONTENT"));
	Support_times = Utility.parseInt(Utility.trimNull(map.get("SUPPORT_TIMES")),new Integer(0));
	Oppose_times = Utility.parseInt(Utility.trimNull(map.get("OPPOSE_TIMES")),new Integer(0));
}

if(request.getMethod().equals("POST")){
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	System.out.println();
	faqs.countFaq(vo,is_flag);
	bSuccess = true;
		
}
%>
<html>
<head>
<title>编辑知识库</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<style>
a:link {text-decoration: none;}
a:visited {text-decoration: none;}
a:active {text-decoration: none;}
a:hover {text-decoration: none;}
</style>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js"></SCRIPT>

<script language=javascript>


function SuOp(value){serial_no=<%=serial_no%>
	$.ajax({
		type:"POST",
		url:"knowledge_info.jsp?is_flag=" + value + '&serial_no=<%=serial_no%>',
		//data:"sub_id="+node.prod_id,
		success:function(data){
			location = 'knowledge_info.jsp?serial_no=<%=serial_no%>'+'&q_faq_keywords=<%=q_faq_keywords%>'+'&is_success=1';
		},
		error:function(data){
		}
	});

}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="knowledge_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="is_dialog" value="1">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<br><br>
</table>
<table border="0" width="75%" align="center" cellspacing="3">
			<tr>
				<td  align="center"><font size="5"><b><%=faq_title%></b></font></td>
			</tr>
			<tr>
<!--				<td align="left" ><input type="text" name="faq_keywords" size="60" value="" onkeydown="javascript:nextKeyPress(this)"></td>-->
				<td align="center"><font size="3"><b><%=faq_keywords%></b></font><br></td>
			</tr>
			<tr>
				<td align="center">
						<%if(q_faq_keywords == null || q_faq_keywords.equals("")) {%>
								<font size="2"><%=faq_content%></font>
						<%}else{
							faq_content = faq_content.replaceAll("(?i)"+q_faq_keywords,"<font color=\"red\">"+q_faq_keywords+"</font>") ;	
						 %>
							<%=faq_content%>	
						<%}%>
				</td>
			</tr>
</table>
<table border="0" width="75%" align="center" cellspacing="3">
			<tr>
				<br><br>
				<td align="left">
					<%if(is_success.intValue() == 0){%>
						<a href="#" onclick="SuOp(1)" id="Support"  title="这个文档不错">
						<img src="<%=request.getContextPath()%>/images/Support.gif" />这个文档不错&nbsp;&nbsp;(<%=Support_times%>&nbsp;人)</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" onclick="SuOp(2)" id="Oppose"  title="文档有待改进 ">
						<img src="<%=request.getContextPath()%>/images/Opposition.gif" />文档有待改进&nbsp;&nbsp;(<%=Oppose_times%>&nbsp;人)</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<%}else{ %>
						<img src="<%=request.getContextPath()%>/images/Support.gif" />这个文档不错&nbsp;&nbsp;(<%=Support_times%>&nbsp;人)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="<%=request.getContextPath()%>/images/Opposition.gif" />文档有待改进&nbsp;&nbsp;(<%=Oppose_times%>&nbsp;人)&nbsp;&nbsp;&nbsp;&nbsp;
					<%} %>
				</td>
				<td align="right">
					<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:location='knowledge_repos_list.jsp?q_faq_keywords=<%=q_faq_keywords%>';">返回(<u>B</u>)</button></td>
				</td>
			</tr>
</table>
</form>
</body>
</html>
