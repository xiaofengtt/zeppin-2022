<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<jsp:directive.page import="java.net.ConnectException"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//ȡ��������ϸ��Ϣserial_no
String serialNoDetail_Message = Utility.trimNull(request.getParameter("serialNoDetail_Message"));
String[] serialNoDetail_Array = serialNoDetail_Message.split("_");
int serialNoDetail_len = serialNoDetail_Array.length;

//���ò�����Ϣ
Integer serial_no_detail = new Integer(0);
Integer target_custid = new Integer(0);
String cust_name = "";
String cust_mobile = "";
String cust_name_alert = "";//���ڱ���û���ֻ��ŵĿͻ�����������ʾ����

//������������
boolean bSuccess = false;
String[] sendBackInfo = null;
List rsList_task = new ArrayList();
Map map_task = null;
List rsList_customer = new ArrayList();
Map map_customer = null;

//��ö���
ServiceTaskLocal serviceTaskLocal = EJBFactory.getServiceTask();
CustomerLocal customerLocal = EJBFactory.getCustomer();
SmsRecordLocal smsRecordLocal = EJBFactory.getSmsRecord();
SendSMSVO vo_sms = null;

//�������
ServiceTaskVO vo_details = new ServiceTaskVO();
CustomerVO vo_cust = new CustomerVO();

if(request.getMethod().equals("POST")){
	String[] cust_message_Array = request.getParameterValues("cust_message");//�ͻ��ֻ���+��ϸ����ID
	String sms_content =  request.getParameter("sms_content");
	Integer sendLevel = Utility.parseInt(Utility.trimNull(request.getParameter("sendLevel")),new Integer(0));
	String putType= enfo.crm.tools.LocalUtilis.language("message.readyToSend", clientLocale);//����
	String sms_cust_mobile = "";
	Integer sms_define_no = new Integer(0);
	String cust_message = null;
	String[] cust_message2 = null;
	Integer smsIndex = new Integer(0);
	
	for(int i=0;i<cust_message_Array.length;i++){
		vo_sms = new SendSMSVO();
		cust_message = cust_message_Array[i];
		cust_message2 = cust_message.split("_");
		
		sms_cust_mobile = cust_message2[1];
		sms_define_no = Utility.parseInt(cust_message2[0],new Integer(0));
		
		vo_sms.setSmsUser(Utility.trimNull(input_operatorCode));
		vo_sms.setPutType(putType);
		vo_sms.setNewFlag(new Integer(1));
		vo_sms.setSendLevel(sendLevel);
		vo_sms.setInputOperator(input_operatorCode);
		vo_sms.setSmsContent(sms_content);
		vo_sms.setPhoneNumber(sms_cust_mobile);
		vo_sms.setSerial_no_detail(sms_define_no);
		//�ȰѶ�����Ϣ������ϵͳ����
		smsIndex = smsRecordLocal.append(vo_sms);
		vo_sms.setUserDefineNo(smsIndex);
		
		try {
			sendBackInfo = SmsClient.sendMessage(vo_sms);	
		}
		catch(Exception e){
			vo_sms = new SendSMSVO();
			
			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(new Integer(2));
			vo_sms.setStatusName(enfo.crm.tools.LocalUtilis.language("message.failSubmit", clientLocale));//�ύʧ��
			vo_sms.setInputOperator(input_operatorCode);

			smsRecordLocal.modi(vo_sms);			
			throw new Exception(enfo.crm.tools.LocalUtilis.language("message.checkSMSPlatform", clientLocale)+"!");//������ŷ���ƽ̨
		}
		
		vo_sms = new SendSMSVO();
		
		vo_sms.setSmsIndex(smsIndex);
		vo_sms.setStatus(Utility.parseInt(sendBackInfo[0],new Integer(0)));
		vo_sms.setStatusName(sendBackInfo[1]);
		vo_sms.setInputOperator(input_operatorCode);
		smsRecordLocal.modi(vo_sms);
	}
	
	bSuccess = true;
}

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.sendSMS",clientLocale)%> </TITLE><!--���Ͷ���-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script language="javascript">
	/*��֤����*/
	function validateForm(form){
		if(checkedCount(document.getElementsByName('cust_message'))== 0){
			sl_alert("<%=LocalUtilis.language("message.selectCusTel",clientLocale)%> ��");//��ѡ��Ҫ���͵Ŀͻ��ֻ�
			return false;
		}
		if(!sl_check(document.getElementsByName('sms_content')[0],"<%=LocalUtilis.language("class.smsContent",clientLocale)%> ",150,1)){return false;}//��������
		
		return true;
	}
	
	/*���Ͷ��ŷ���*/
	function sendAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].action="sendMes.jsp";
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;	
		var v_cust_name_alert = document.getElementById("cust_name_alert").value;
		
		if(v_bSuccess=="true"){	
			sl_update_ok();		
			window.close();
		}
		
		if(v_cust_name_alert.length>0){
			v_cust_name_alert = v_cust_name_alert.substring(0,v_cust_name_alert.length-1);
            //�ͻ� //û���ֻ����룬�޷����Ͷ���
			var alertInfo = v_cust_name_alert+":�ͻ�û���ֻ����룬�޷����Ͷ���";
			sl_alert(alertInfo);
		}
	}

</script>
</HEAD>

<BODY>
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left">
    <!--���Ͷ���-->
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("message.sendSMS",clientLocale)%> </b></font>
</div>
<hr noshade color="#808080" size="1" width="98%">

<div align="center" style="margin-left:5px">
	<table border="0"  width="570px" cellspacing="1" cellpadding="2" class="tablelinecolor">
		<tr style="background:6699FF;">
			<td align="center" width="50%">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_message,this);">
				<font size="2" face="΢���ź�"><b><%=LocalUtilis.language("class.customerName",clientLocale)%> </b></font><!--�ͻ�����-->
			</td>
			<td align="center" width="50%"><font size="2" face="΢���ź�"><b><%=LocalUtilis.language("class.custMobile",clientLocale)%> </b></font></td><!--�ͻ��ֻ�-->
		</tr>
	</table>
	
	<span style="overflow-y:auto;height:160;">
		<table border="0" width="570px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
		<%for(int i = 0;i<serialNoDetail_len;i++){
			serial_no_detail = Utility.parseInt(Utility.trimNull(serialNoDetail_Array[i]),new Integer(0));
			
			if(serial_no_detail.intValue()>0){
				vo_details.setSerial_no(serial_no_detail);
				vo_details.setInputMan(input_operatorCode);
				rsList_task = serviceTaskLocal.query_details(vo_details);
			}

			//����serial_no �õ�cust_id
			if(rsList_task.size()>0){
				map_task = (Map)rsList_task.get(0);				
				target_custid = Utility.parseInt(Utility.trimNull(map_task.get("TargetCustID")), new Integer(0));
			}
			
			if(target_custid.intValue()>0){
				vo_cust.setCust_id(target_custid);
				vo_cust.setInput_man(input_operatorCode);
				rsList_customer = customerLocal.listProcAll(vo_cust);
				
				if(rsList_customer.size()>0){
					map_customer = (Map) rsList_customer.get(0);
					
					cust_name = Utility.trimNull(map_customer.get("CUST_NAME"));
					cust_mobile = Utility.trimNull(map_customer.get("MOBILE"));

				}			
		%>
			<tr style="background:F7F7F7;">
				<td align="center" width="50%">
					<table border="0" width="80%" cellspacing="0" cellpadding="0">
					
					<%if(cust_mobile.length()>0){%>					
						<tr>
							<td width="10%" align="left">		
								<input type="checkbox" name="cust_message" value="<%=serial_no_detail%>_<%=cust_mobile%>" class="flatcheckbox"/>
							</td>
							<td width="90%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<b><%= cust_name%></b></td>
						</tr>
						<%}
						else{
							cust_name_alert = cust_name_alert + cust_name + "," ;
						%>
						<tr>
							<td width="100%" align="center"><b><font color="gray"><%= cust_name%></font></b></td>
						</tr>
						<%}%>
					</table>
				</td> 
				<td align="center" width="50%"><%if(cust_mobile.length()>0){out.print("����");}else{out.print("��");}%></td> 
			</tr>
		<% }
		}%>
		</table>
	</span>
<div>

<div style="margin-top:5px;">
	<table  border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC">
		<tr style="background:F7F7F7;">
	 		<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.setSMSContent",clientLocale)%> </b></font></td><!--������������-->
	 	</tr>
		<tr style="background:F7F7F7;">
			<td valign="top" width="120px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.smsContent",clientLocale)%> ��</font></td><!--��������-->
			<td>
				<textarea rows="10" name="sms_content" onkeydown="javascript:nextKeyPress(this)" style="width:100%"></textarea>			
			</td>
		</tr>
		
		<tr style="background:F7F7F7;">
			<td valign="top" width="120px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.sendLevel",clientLocale)%> ��</font></td><!--�������ȼ�-->
			<td>
				&nbsp;&nbsp;
				<select id="sendLevel" name="sendLevel" style="width:60px">
					<option value="-3"><%=LocalUtilis.language("message.high",clientLocale)%> </option><!--��-->
					<option value="-2" selected><%=LocalUtilis.language("message.edium",clientLocale)%> </option><!--��-->
					<option value="-1"><%=LocalUtilis.language("message.low",clientLocale)%> </option><!--��-->
				</select>		
			</td>
		</tr>
	</table>
</div>

<div align="right" style="margin-right:10px; margin-top:10px">	
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:sendAction();"><%=LocalUtilis.language("message.send",clientLocale)%> </button><!--����-->
	&nbsp;&nbsp;
	<button type="button" class="xpbutton3" id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> </button><!--�ر�-->
</div>	

<input type="hidden" id="cust_name_alert" value="<%=cust_name_alert%>"/>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>