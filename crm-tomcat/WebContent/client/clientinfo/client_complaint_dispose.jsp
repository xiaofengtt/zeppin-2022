<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.customer.CustomerLocal"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ҳ�洫�ݲ���
Integer serial = Utility.parseInt(Utility.trimNull(request.getParameter("serial")),new Integer(0));
System.out.print("--------------------------------------------------------------------------"+serial);
Integer compType = Utility.parseInt(Utility.trimNull(request.getParameter("compType")),new Integer(0));
Integer inputDate = Utility.parseInt(Utility.trimNull(request.getParameter("inputDate")),new Integer(0));
String content = Utility.trimNull(request.getParameter("content"));
String checkContent = Utility.trimNull(request.getParameter("checkContent"));
String custName = Utility.trimNull(request.getParameter("custName"));

Integer replyType = Utility.parseInt(Utility.trimNull(request.getParameter("replyType")),new Integer(0));
Integer replyDate = Utility.parseInt(Utility.trimNull(request.getParameter("replyDate")),new Integer(0));
Integer doStatus = Utility.parseInt(Utility.trimNull(request.getParameter("doStatus")),new Integer(0));
Integer forwardType = Utility.parseInt(Utility.trimNull(request.getParameter("forwardType")),new Integer(0));
Integer replyMan = Utility.parseInt(Utility.trimNull(request.getParameter("replyMan")),new Integer(0));
String doResult = Utility.trimNull(request.getParameter("doResult"));

//������������
boolean bSuccess = false;
int iCount = 0;
List list = null;
Map map = new HashMap();

//��ö���
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//�������
if(request.getMethod().equals("POST")){
	vo.setSerial_no(serial);
	vo.setComp_type(compType);
	vo.setInput_date(inputDate);
	vo.setContent(content);
	vo.setCheck_content(checkContent);
	vo.setInput_man(input_operatorCode);
	vo.setCust_name(custName);
	
	vo.setReply_type(replyType);
	vo.setReply_date(replyDate);
	vo.setDo_status(doStatus);
	vo.setForward_type(forwardType);
	vo.setDo_result(doResult);
	vo.setRelpy_man(replyMan);

	customer.updateCustomerComplaint(vo);		
	bSuccess = true;

}

if(serial.intValue()>0&&!request.getMethod().equals("POST")){
	Object[] params = new Object[7];
	params[0] = serial;
	params[1] = "";
	params[2] = new Integer(0);
	params[3] = new Integer(0);
	params[4] = new Integer(0);
	params[5] = "";
	params[6] = new Integer(0);

	IPageList pageList = customer.getCustomerComplaint(params,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
	map=(Map)pageList.getRsList().get(0);
	custName=Utility.trimNull(map.get("CUST_NAME"));
	compType=Utility.parseInt(Utility.trimNull(map.get("COMP_TYPE")),new Integer(0));
	inputDate = Utility.parseInt(Utility.trimNull(map.get("INPUT_DATE")),new Integer(0));
	content=Utility.trimNull(map.get("CONTENT"));
	checkContent=Utility.trimNull(map.get("CHECK_CONTENT"));
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
<title>�ͻ�Ͷ��</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language="javascript">
/*��ʼ��*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}
/*����*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}

/*��֤����*/
function validateForm(form){
	if(form.replyType.value==0){
		sl_alert("��ѡ��ظ���ʽ!");
		return false;
	}
	if(!sl_checkDate(form.replyDate_piker,"�ظ�ʱ��")){return false;}	
	if(form.doStatus.value==0){
		sl_alert("��ѡ����״̬!");
		return false;
	}
	if(form.replyMan.value==0){
		sl_alert("��ѡ��ط���!");
		return false;
	}
	if(!sl_check(form.doResult, "������",1000,1)){return false;}
	if(form.doStatus.value==3){
		if(form.forwardType.value==0){
		sl_alert("��ѡ��ת����ʽ!");
		return false;
	}
	}

	syncDatePicker(form.replyDate_piker, form.replyDate);
	if(form.replyDate.value<form.inputDate.value){
		sl_alert("�ظ����ڱ�����Ͷ������֮��");
		return false;
	}
	return sl_check_update();
}
/*ȡ��*/
function CancelAction(){
	window.close();
}

function doSelect(doStatus){
	if(doStatus==3){
		document.getElementById('forwardType').disabled = false;
	}else{
		document.getElementById('forwardType').value = 0;
		document.getElementById('forwardType').disabled = true;
	}
}

</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" action="client_complaint_dispose.jsp" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial" name="serial" value="<%=serial%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b>�ͻ�Ͷ��>>�ͻ�Ͷ�ߴ���</b></font>
</div>
<br/>
<div align="left"  class="table-popup">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
	 	<tr>
	 		<td  width="100px" align="right">�ͻ�����:</td>
	 		<td  width="*">
	 			<input  name="custName" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=custName%>" readonly/>
	 		</td>
	 		<td  width="100px" align="right">Ͷ�߷�ʽ:</td>
			<td>
				<input  name="compType" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%if(compType.intValue()==1){out.print("�绰");}
											else if(compType.intValue()==2){out.print("����");}
												else if(compType.intValue()==3){out.print("Email");}
													else{out.print("����");}%>" readonly/>
			</td>
	 	</tr>
	 	<tr>
	 		<td width="100px" align="right" vAlign="top">Ͷ������:</td>
			<td width="*">
				<input  type="text" name="start_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(inputDate)%>" readonly>					
				<input type="hidden" name="inputDate" value="<%=inputDate%>">
			</td>
	 		<td width="100px" align="right" vAlign="top">�˲����:</td>
			<td width="*">
				<input  name="checkContent" onkeydown="javascript:nextKeyPress(this)" size="40" maxlength="60" value="<%=checkContent%>" readonly>
			</td>
	 
 		<tr>
			<td width="100px" align="right" vAlign="top">Ͷ������:</td>
			<td  colspan=3><textarea rows="3" name="content" onkeydown="javascript:nextKeyPress(this)" cols="100" readonly><%=content%></textarea></td>
		</tr>
		<tr>
	 		<td  width="100px" align="right">�ظ���ʽ:</td>
	 		<td  width="*">
	 			<select size="1"  name="replyType" style="width: 150px" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getReplyOptions(replyType)%>
				</select>
	 		</td>
	 		<td  width="100px" align="right">�ظ�����:</td>
			<td>
				<input type="text" name="replyDate_piker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(replyDate)%>" readonly>
				<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.replyDate_piker,theform.replyDate_piker.value,this);">
				<input type="hidden" name="replyDate" value="">
			</td>
	 	</tr>
		<tr>
	 		<td  width="100px" align="right">����״̬:</td>
	 		<td  width="*">
	 			<select size="1" id="doStatus" name="doStatus" style="width: 150px" onkeydown="javascript:nextKeyPress(this)" onchange="doSelect(document.getElementById('doStatus').value)">
					<%=Argument.getDisposeOptions(doStatus)%>
				</select>
	 		</td>
	 		<td  width="100px" align="right">ת����ʽ:</td>
			<td>
				<select size="1" id="forwardType" name="forwardType" style="width: 150px" onkeydown="javascript:nextKeyPress(this)" disabled="disabled">
					<%=Argument.getForwardOptions(forwardType)%>
				</select>
			</td>
	 	</tr>
		<tr>
			<td  width="100px" align="right">�ط���:</td>
			<td  width="*">
	 			<select size="1"  name="replyMan" style="width: 150px" onkeydown="javascript:nextKeyPress(this)"">
					<%=Argument.getRoledOperatorOptions(input_bookCode,2,input_operatorCode)%>
				</select>
	 		</td>
		</tr>
		<tr>
			<td width="100px" align="right" vAlign="top">������:</td>
			<td  colspan=3><textarea rows="3" name="doResult" onkeydown="javascript:nextKeyPress(this)" cols="100"><%=doResult%></textarea></td>
		</tr>
	</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<!-- ���� -->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();">����(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- �ر� -->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">�ر�(<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<%
customer.remove();
%>
