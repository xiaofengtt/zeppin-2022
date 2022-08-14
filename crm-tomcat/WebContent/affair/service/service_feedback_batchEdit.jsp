<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<% 
//���ҳ�洫�ݱ���
String checkedValues = Utility.trimNull(request.getParameter("checkedValues"));
String[] serialno_details_str = Utility.splitString(checkedValues,"|");
//������������
boolean bSuccess = false;
Integer serial_no_details = new Integer(0);
Integer satisfaction = new Integer(3);
String feedbackContent = "";

//��ö���
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//����ҳ�����
if(request.getMethod().equals("POST")){
	satisfaction = Utility.parseInt(Utility.trimNull(request.getParameter("satisfaction")),new Integer(0));
	feedbackContent = Utility.trimNull(request.getParameter("feedbackContent"));
	
	for(int i=0;i<serialno_details_str.length;i++){
		vo = new ServiceTaskVO();
		serial_no_details = Utility.parseInt(serialno_details_str[i],new Integer(0));
		
		vo.setSerial_no(serial_no_details);
		vo.setSatisfaction(satisfaction);
		vo.setFeedbackContent(feedbackContent);
		vo.setInputMan(input_operatorCode);
		serviceTask.feedback(vo);
	}
	
	bSuccess = true;
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*����*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "<%=request.getContextPath()%>/affair/service/service_feedback_batchEdit.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}
/*ȡ��*/
function CancelAction(){
	window.returnValue=null;
	//window.parent.document.getElementById("closeImg").click();	
	window.close();
}
/*��֤����*/
function validateForm(form){		
	if(!sl_check(document.getElementsByName('feedbackContent')[0],"<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> ",200,0)){ return false;}// ������������
      return sl_check_update();
}
/*��ʼ��*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		//window.parent.location.reload();
		//window.parent.document.getElementById("closeImg").click();	
		window.returnValue=1;	
		window.close();
	}
}
</script>
</head>
<body class="BODY">
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<!--�޸ĳɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="checkedValues" value="<%=checkedValues%>"/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message",clientLocale)%> </b></font><!--������ -->
</div>
<hr noshade color="#808080" size="1" width="100%">

<div align="left" style=" height:100px;margin-top:3px;">
	<table cellSpacing="1" cellPadding="2" width="100%"  bgcolor="#CCCCCC">
 		<tr style="background:F7F7F7;">
	 		<td colspan="4" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message",clientLocale)%> </b></font></td><!-- ������ -->
	 	</tr>
	 	<tr style="background:F7F7F7;">
			<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.satisfaction",clientLocale)%> :</font></td><!-- �ͻ������ -->
			<td colspan="3">
				<select name="satisfaction" style="width:120px">
					<%= Argument.getCustomerSatifaction(satisfaction.intValue())%>
				</select>				
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td  valign=top><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> :</font></td><!-- ������������ -->
			<td  colspan="3">
				<textarea rows="5" name="feedbackContent" width="100%" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=feedbackContent%></textarea>			
			</td>
		</tr>	
		<tr style="background:F7F7F7;">	
			<td colspan="4" align="right">
				<!-- ���� -->
                   <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
                   <!-- �ر� -->
				<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
			</td>
		</tr>
	</table>
</div>
</form>
</body>
</html>