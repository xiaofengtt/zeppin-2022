<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
Integer subject_id = Utility.parseInt(Utility.trimNull(request.getParameter("subject_id")),new Integer(0));
String subject_no = Utility.trimNull(request.getParameter("subject_no"));
String subject_name = Utility.trimNull(request.getParameter("subject_name"));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));

Map map = null;
List rsList = null;

//��ö��󼰽����
RatingVO vo = new RatingVO();
ScoreSubjectLocal score_subject = EJBFactory.getScoreSubject();

boolean bSuccess = false;

//������Ϣ
if(request.getMethod().equals("POST")){

	vo.setSubject_no(subject_no);
	vo.setSubject_name(subject_name);
	vo.setSummary(summary);
	vo.setInput_man(input_man);	

	if(subject_id.intValue()!= 0){
	vo.setSubject_id(subject_id);
	score_subject.modi(vo);
	}else{
		score_subject.append(vo);
	}
	bSuccess = true;
}
else {
	//��Ϣ��ʾ
	if(subject_id.intValue()!= 0){
		vo.setSubject_id(subject_id);
		vo.setInput_man(input_man);
		List list = score_subject.query(vo);
		map = (Map)list.get(0); 
	}
	
	if(map != null){
		subject_id = Utility.parseInt(Utility.trimNull(map.get("SUBJECT_ID")),
				new Integer(0));
		subject_no = Utility.trimNull(map.get("SUBJECT_NO"));
		subject_name = Utility.trimNull(map.get("SUBJECT_NAME"));
		summary = Utility.trimNull(map.get("SUMMARY"));

	}
}
%>

<HTML>
<HEAD>
<TITLE></TITLE><!--�������ֿ�Ŀ-->
<base target="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language="javascript">	
/*����*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function validateForm(form){
	if(form.subject_no.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.subjectNumber",clientLocale)%>��");//����д��Ŀ���
			return false;
	}
	if(form.subject_name.value == ""){
			sl_alert("<%=LocalUtilis.language("class.pleaseFillIn",clientLocale)%><%=LocalUtilis.language("class.subjectsName",clientLocale)%>��");//����д��Ŀ����
			return false;
	}	
	return sl_check_update();
}

/*��ʼ��*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}

/*ȡ��*/
function CancelAction(){
	window.close();
}
</script>
</HEAD>

<BODY class="BODY" style="overflow-x: hidden">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="score_subject_action.jsp" onsubmit="javascript:return validateForm(this);">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="subject_id" value="<%=subject_id%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div align="left"  style="margin-left:20px">
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 0px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.subjectNumber",clientLocale)%>:</td><!--��Ŀ���-->
		<td align="left" >
		<input type="text" name="subject_no" size="40" value="<%=subject_no %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.subjectsName",clientLocale)%>:</td><!--��Ŀ����-->
		<td align="left" >
			<input type="text" name="subject_name" size="40" value="<%=subject_name %>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.memberDescription",clientLocale)%>:</td><!--����-->
		<td align="left"><textarea  rows="6" name="summary" cols="60"><%=summary %></textarea></td>
	</tr>
</table>
</div>
<div align="right">
	<br>
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
score_subject.remove();
%>
