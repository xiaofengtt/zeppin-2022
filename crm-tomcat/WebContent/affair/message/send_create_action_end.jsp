<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//��ȡ����ID 
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
String submit_flag = Utility.trimNull(request.getParameter("submit_flag"));
int way_type = Utility.parseInt(request.getParameter("way_type"), 0);
String from_email = Utility.trimNull(request.getParameter("from_email"));
String smtp_userpwd = Utility.trimNull(request.getParameter("smtp_userpwd")); 
//��ö���
boolean bSuccess = false;
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = new SendSMSVO();
SendMail2 mail=new SendMail2();
if(serial_no.intValue()!=0){
	vo.setSerial_no(serial_no);
	vo.setInputOperator(input_operatorCode);
	if("send".equals(submit_flag)){
		if(way_type == 2){//�ʼ�
			vo.setInputOperator(input_operatorCode);
		
			vo.setCom_user_id(user_id);
			vo.setFrom_email(from_email);
			vo.setSmtp_password(smtp_userpwd);
			sendTotal.sendEmail(vo);
		}else{
			sendTotal.sendSms(serial_no,input_operatorCode);
		}
		bSuccess = true;
		vo.setCheck_flag(new Integer(3));
		sendTotal.checkMessage(vo);//��ʱ����,check_flag=3ʱ�������
	}else{
		vo.setCheck_flag(new Integer(1));//�ύ���
		sendTotal.checkMessage(vo);	
	}
}
sendTotal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	<%if("send".equals(submit_flag)){%>
		alert("<%=LocalUtilis.language("message.sendOK",clientLocale)%> ��");//���ͳɹ�;
	<%}else{%>
		alert("<%=LocalUtilis.language("message.tSuccessfullysub",clientLocale)%> ��");//�ύ��˳ɹ�
	<%}%>

	window.returnValue = 1;
	window.parent.location.reload();
	window.close();	
</SCRIPT>