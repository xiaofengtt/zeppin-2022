<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//������������
boolean bSuccess = false;
List rsList = null;
Map rsMap = null;

//��ö���
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;

//������ʾ�ֶ�
String serviceTitle ="";
Integer serviceType = Utility.parseInt(request.getParameter("serviceType"), new Integer(0));
String serviceTypeName = "";
String serviceWay = "";
String serviceWayName = "";
int serviceStatus = 0;//����״̬
String serviceStatusName = "";
String serviceResult =  Utility.trimNull(request.getParameter("serviceResult"));//������

if(request.getMethod().equals("POST")){
	vo = new ServiceTaskVO();	
	vo.setSerial_no(serial_no);
	vo.setResult(serviceResult);
	vo.setInputMan(input_operatorCode);
	
	serviceTask.treat(vo);
	bSuccess = true;
}

//�༭ʱ��ʾ ������Ϣ
if(serial_no.intValue()>0){
	//��ѯ����Ϣ
	vo = new ServiceTaskVO();	
	vo.setSerial_no(serial_no);
	vo.setServiceType(serviceType);
	vo.setInputMan(input_operatorCode);
	
	rsList = serviceTask.query(vo);	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
	}

	if(rsMap!=null){
		serviceTitle = Utility.trimNull(rsMap.get("ServiceTitle"));	
		serviceType = Utility.parseInt(rsMap.get("ServiceType").toString(), new Integer(0));
		serviceTypeName = Utility.trimNull(rsMap.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(rsMap.get("ServiceWay"));
		serviceWayName= Argument.getDictParamName(1109,serviceWay);
		serviceStatus = Utility.parseInt(Utility.trimNull(rsMap.get("Status")),0);
		serviceStatusName= Argument.getService_status_name(serviceStatus);
		serviceResult = Utility.trimNull(rsMap.get("Result"));
	}
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("messag.taskingMemo",clientLocale)%> </title><!-- �������� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;	
	if(v_bSuccess=="true"){	
		sl_update_ok();		
		window.close();
	}
}

function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="<%=request.getContextPath()%>/affair/service/service_pigeonhole_remark.jsp";
		document.getElementsByName('theform')[0].submit();
	}
}

/*��֤����*/
function validateForm(form){
	if(!sl_check(document.getElementById("serviceResult"),"<%=LocalUtilis.language("message.taskMemo",clientLocale)%> ",300,1)){ return false;}	//������		
	return sl_check_update();		
}
</script>

</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--�޸ĳɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>

<div align="left" class="page-title">
    <!-- ������� --><!-- ����鵵 --><!-- ������ -->
    <font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskArchiving",clientLocale)%>>><%=LocalUtilis.language("message.taskMemo",clientLocale)%> </b></font>
</div>
<br/>
<div  align="left"  style="margin-left:5px">
<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="table-popup">
		<tr style="">
			<td  width="120px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</font></td><!-- ������� -->
			<td  width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTitle%></font></td>	
			<td width="120px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ��</font></td><!-- ������� -->
			<td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTypeName%></font></td>
		</tr>
		
		<tr style="">
			<td  width="120px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskFlagName",clientLocale)%> ��</font></td><!-- ����״̬ -->
			<td  width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceStatusName%></font></td>			
			<td width="120px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font> </td><!-- ��ϵ��ʽ -->
			<td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceWayName%></font></td>
		</tr>
		
		<tr style="">
			<td  width="120px"><font size="2" face="΢���ź�"><%=LocalUtilis.language("message.taskMemo",clientLocale)%> ��&nbsp;&nbsp;</font></td><!-- ������ -->
			<td  width="*" colspan="3">
				<textarea rows="8" name="serviceResult" id="serviceResult" onkeydown="javascript:nextKeyPress(this)" style="width:100%"><%=serviceResult%></textarea>			
			</td>
		</tr>
	
</table>
</div>

<div align="right" style="margin-right:5px; margin-top:10px;">
	<!-- ���� -->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;	
	<!--�ر� -->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>

</form>
</BODY>
</HTML>