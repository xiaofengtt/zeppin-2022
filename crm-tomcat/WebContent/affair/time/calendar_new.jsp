<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String imgURl = "http://"+request.getRemoteAddr()+":"+request.getServerPort();
boolean bSuccess = false;

ScheDulesVO vo = new ScheDulesVO();
ScheDulesLocal local = EJBFactory.getScheDules();

Integer schedule_type = null;
String schedule_start_date = null;
String schedule_end_date = null;
String content = null;
Integer input_man = null;

//������Ϣ
if(request.getMethod().equals("POST")){  
	schedule_type = Utility.parseInt(Utility.trimNull(request.getParameter("schedule_type")),null);
	schedule_start_date = Utility.trimNull(request.getParameter("schedule_start_date"));
	schedule_end_date = Utility.trimNull(request.getParameter("schedule_end_date"));
	content = Utility.trimNull(request.getParameter("content"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setSchedule_type(schedule_type);
	vo.setSchedule_start_date(schedule_start_date);
	vo.setSchedule_end_date(schedule_end_date);
	vo.setContent(content);
	vo.setInput_man(input_man);
	
	try{
		local.append(vo);
	}catch(Exception e){
		String saveFail = LocalUtilis.language("message.saveFail",clientLocale);
		out.println("<script language=\"javascript\">alert(\""+saveFail+","+e.getMessage()+"\")</script>");//����ʧ��
	}
	
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.addSchedule",clientLocale)%> </title><!--�����ճ�-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>

	<% if(bSuccess){ %>
		alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ��");//����ɹ�
		window.returnValue = true;
		window.close();
	<%}%>

	/*��֤����*/
	function validateForm(form){
		return sl_check_update();	
	}

	/*����*/
	function SaveAction(){
		var startTime = Date.parse(new Date($("#schedule_start_date").val().replace(/-/g,'/')));
		var endTime = Date.parse(new Date($("#schedule_end_date").val().replace(/-/g,'/')));
		if(startTime > endTime){
			alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
			return false;
		}
		if(startTime == endTime){
			alert("��ʼʱ�䲻�ܵ��ڽ���ʱ��");
			return false;
		}
		if(document.getElementsByName("schedule_type")[0].getAttribute("value") == ""){
			alert("<%=LocalUtilis.language("message.selectScheduleType",clientLocale)%> ��"); return false;//��ѡ���ճ�����
		}
		
		if(!sl_check(document.getElementById('schedule_start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 60, 1))return false;//��ʼʱ��
		if(!sl_check(document.getElementById('schedule_end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 60, 1))return false;//����ʱ��
		if(!sl_check(document.getElementsByName("content")[0], "<%=LocalUtilis.language("class.scheduleDescription",clientLocale)%> ", 512, 1)) return false;//�ճ�����

		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*ȡ��*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="calendar_new.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.addSchedule",clientLocale)%> </b></font><!--�����ճ�-->
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td style="width:30%" align="right">*<%=LocalUtilis.language("class.scheduleType",clientLocale)%> ��</td><!--�ճ�����-->
			<td >
				<!--3042�ճ�����-->
				<select name="schedule_type" style="width: 110px;">
					<%=Argument.getDictParamOptions((new Integer(3042)).intValue(),null)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.startTime",clientLocale)%> ��</td><!--��ʼʱ��-->
			<td>
				<input type="text" name="schedule_start_date" id="schedule_start_date" onclick="javascript:setday(this);" readOnly/> 
				<!--input TYPE="button" value="��"  class=selectbtn tabindex="13" onclick="javascript:document.getElementById('schedule_time').onclick();"/-->	
			</td>
		</tr>
		<tr>	
			<td align="right">*<%=LocalUtilis.language("class.endTime",clientLocale)%> ��</td><!--����ʱ��-->
			<td>
				<input type="text" name="schedule_end_date" id="schedule_end_date" onclick="javascript:setday(this);" readOnly/> 
				<!--input TYPE="button" value="��"  class=selectbtn tabindex="13" onclick="javascript:document.getElementById('schedule_time').onclick();"/-->	
			</td>
		</tr>	
		<tr>
			<td align="right" valign="top">*<%=LocalUtilis.language("class.scheduleDescription",clientLocale)%> ��</td><!--�ճ�����-->
			<td>
				<textarea rows="3" name="content" onkeydown="javascript:nextKeyPress(this)" cols="50"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--����-->
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button><!--����-->
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>