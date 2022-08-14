<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.web.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//���ҳ�洫�ݱ���


Integer actionFlag1 = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag1")),new Integer(1));//������ʶ 1.������2.�޸�
boolean bSuccess = false;
//��ö���
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = null;
SendMail2 mail=new SendMail2();
List rsList = null;
Map rsMap = null;
//������Ϣ
Integer way_type = Utility.parseInt(request.getParameter("way_type_value"), new Integer(0));
Integer send_type = Utility.parseInt(request.getParameter("send_type"), new Integer(1));
String msg_type = Utility.trimNull(request.getParameter("msg_type"));
String plan_time = Utility.trimNull(request.getParameter("plan_time"));
String mobiles = Utility.trimNull(request.getParameter("mobiles"));
String content_templet = Utility.trimNull(request.getParameter("content_templet"));
Integer check_man = Utility.parseInt(request.getParameter("check_man"), new Integer(0));
String submit_flag = Utility.trimNull(request.getParameter("submit_flag"));

String smtp_server  = Utility.trimNull(Argument.getADDITIVE_VALUE("190201"));
String from_email = Utility.trimNull(request.getParameter("from_email"));
String smtp_userpwd = Utility.trimNull(request.getParameter("smtp_userpwd")); 

if(request.getMethod().equals("POST")){	
	vo = new SendSMSVO();
	vo.setWay_type(way_type);
	vo.setSend_type(send_type);
	vo.setMsg_type(msg_type);
	if(send_type.intValue() == 2)
		vo.setPlan_time(plan_time);//��ʱ����ʱ��
	else
		vo.setPlan_time(null);
	vo.setMobiles(mobiles);
	vo.setContent_templet(content_templet);

	vo.setCheck_man(check_man);
	vo.setInputOperator(input_operatorCode);
	
	vo.setCom_user_id(user_id);
	vo.setFrom_email(from_email);
	vo.setSmtp_password(smtp_userpwd);
	if("send".equals(submit_flag)){
		vo.setCheck_flag(new Integer(3));
		Integer serial_no = (Integer)sendTotal.appendMessage(vo);
		if(way_type.intValue() == 2){//�ʼ�
			vo.setSerial_no(serial_no);
			sendTotal.sendEmail(vo);
		}else{
			sendTotal.sendSms(serial_no,input_operatorCode);//���Ͷ���
		}
		bSuccess = true;
	}else if("check".equals(submit_flag)){
		vo.setCheck_flag(new Integer(1));
		Integer serial_no = (Integer)sendTotal.appendMessage(vo);
		bSuccess = true;
	}

}

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.addTask",clientLocale)%> 1</title><!-- ���񴴽����� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language="javascript">

<%if(bSuccess){
	if("send".equals(submit_flag)){%>
		alert("<%=LocalUtilis.language("message.sendOK",clientLocale)%> ��");//���ͳɹ�;
	<%}else{%>
		alert("<%=LocalUtilis.language("message.tSuccessfullysub",clientLocale)%> ��");//�ύ��˳ɹ�
	<%}%>
	CancelAction();
<%}%>
	
function CancelAction(){
	window.parent.location.reload();
	window.parent.document.getElementById("closeImg").click();	
}
function chooseAction(){
	var url = "<%=request.getContextPath()%>/affair/message/template_choose.jsp";
	var ret = showModalDialog(url,'','dialogWidth:610px;dialogHeight:450px;status:0;help:0');
	if(ret != null &&ret.length>0){
		var rets = ret.split("_");
		document.getElementById("tempID").value=rets[0];
		document.getElementById("tempTitle").value=rets[1];
		document.getElementById("content_templet").value=rets[2];
	}
	showWaitting(0);
}
function showTr(){
	var send_type = document.getElementsByName('send_type')[0].value;
	if(send_type== 1 || send_type == 2){
		document.getElementById("checkTd1").style.display = "";
		document.getElementById("checkTd2").style.display = "";
		document.getElementById("btnSave").style.display = "none";
		document.getElementById("btnNext").style.display = "";		
	}
	else{
		document.getElementById("checkTd1").style.display = "none";
		document.getElementById("checkTd2").style.display = "none";
		document.getElementById("btnSave").style.display = "";
		document.getElementById("btnNext").style.display = "none";
	}
}

function clearAction(){
	document.getElementById("tempID").value=0;
	document.getElementById("tempTitle").value="";
}

function validateForm(){
	var obj = document.getElementsByName("way_type");
	var way_type_value = 0;
    if(obj!=null){
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
               	way_type_value = obj[i].value;            
            }
        }
    }
	document.getElementById('way_type_value').value = way_type_value;

	if(!sl_checkChoice(document.getElementsByName('msg_type')[0],"<%=LocalUtilis.language("class.sms",clientLocale)%><%=LocalUtilis.language("class.type",clientLocale)%> ")){ return false;}
	var msg_type = document.getElementsByName('msg_type')[0].value;
	if(msg_type== '190191' || msg_type == '190192'){
		if(!sl_checkChoice(document.getElementsByName('check_man')[0],"<%=LocalUtilis.language("class.reviewer",clientLocale)%> ")){ return false;}//�����	
	}
	var plan_time = null;
	if(document.getElementById('planTime2').style.display != "none"){
		if(!sl_check(document.getElementById('plan_time'), "<%=LocalUtilis.language("class.smsTime",clientLocale)%> ", 20, 1))return false;
		plan_time = document.getElementById('plan_time').value;
		var time1 = new Date(plan_time.replace(/[-]/g,"/"));
		var now_time = new Date();
		var s = (time1.getTime() - now_time.getTime())/(1000*60);//�뵱ǰʱ�����ķ�����.
		if(s < 30)
		{
			sl_alert("��ʱ����ʱ��������ڵ�ǰʱ��30����");
			return false;
		}
	}
	if(!sl_check(document.getElementById("mobiles"), "<%=LocalUtilis.language("class.mobile",clientLocale)%>/<%=LocalUtilis.language("class.email",clientLocale)%> ",2000,1)){return false;}//�ֻ�����-�ʼ���ַ
	if(!sl_check(document.theform.content, "<%=LocalUtilis.language("class.smsContent",clientLocale)%> ",5000,1)){return false;}//��������
	if(way_type_value == 2){
		validateEmial();
	}
	var sms_content = document.getElementById("content_templet").value
	if(sms_content.indexOf("%1") != -1)
	{
		sl_alert("�ַ�'%1'������");
		return false;
	}
	return true;
	
}
function validateEmial()
{
	if(document.getElementById("smtp_server").value == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureServerTip",clientLocale)%> ...");
		//�ʼ���������ַΪ�գ����ܷ����ʼ��������÷�����...
		return false;
	}
	if(document.getElementById("from_email") .value == ""){
		sl_alert("<%=LocalUtilis.language("message.fromEmialError'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureFromEmailTip",clientLocale)%> ...");
		//�����˵�ַΪ�գ����ܷ����ʼ��������÷�����...
		return false;
	}
	if(document.getElementById("smtp_user.value") == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError2'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureUserNameTip",clientLocale)%> ...");
		//�ʼ���������Ҫ�û�����֤�����ܷ����ʼ����������û���...
		return false;
	}
	if(document.getElementById("smtp_userpwd.value") == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError3'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureUserPwdTip",clientLocale)%> ...");
		//�ʼ���������Ҫ������֤�����ܷ����ʼ����������û�������...
		return false;
	}
	return true;
}
function sendAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="send_create_action.jsp";
		document.getElementById("submit_flag").value = "send";//����
		document.getElementsByName('theform')[0].submit();
	}	
}
function checkAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="send_create_action.jsp";
		document.getElementById("submit_flag").value = "check";//�ύ���
		document.getElementsByName('theform')[0].submit();
	}	
}
function showPlanTime(msg_type){
	if(msg_type == 2){
		document.getElementById("planTime").style.display = "none";
		document.getElementById("planTime1").style.display = "";
		document.getElementById("planTime2").style.display = "";
	}else{
		document.getElementById("planTime").style.display = "";
		document.getElementById("planTime1").style.display = "none";
		document.getElementById("planTime2").style.display = "none";	
	}
}
function showTr(send_type){
	if(send_type== "190191" || send_type == "190192"){
		document.getElementById("checkTd").style.display = "none";
		document.getElementById("checkTd1").style.display = "";
		document.getElementById("checkTd2").style.display = "";
		document.getElementById("btnSave").style.display = "none";
		document.getElementById("btnNext").style.display = "";	
	}else{
		document.getElementById("checkTd").style.display = "";
		document.getElementById("checkTd1").style.display = "none";
		document.getElementById("checkTd2").style.display = "none";
		document.getElementById("btnSave").style.display = "";
		document.getElementById("btnNext").style.display = "none";
	}
}
function showWay(way_type){
	if(way_type == 1 ){
		document.getElementById("sendType").style.display = "";
		document.getElementById("contnet").innerHTML = "&nbsp;&nbsp;�ֻ�����";
		document.getElementById("email1").style.display = "none";
		document.getElementById("email2").style.display = "none";
		
		
	}else{
		document.getElementById("sendType").style.display = "none";
		document.getElementById("send_type").value = 2;
		document.getElementById("contnet").innerHTML = "&nbsp;&nbsp;�ʼ���ַ";
		document.getElementById("email1").style.display = "";
		document.getElementById("email2").style.display = "";
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--�����־-->
<input type="hidden" name="actionFlag1" id="actionFlag1" value="<%=actionFlag1%>" />
<input type="hidden" name="way_type_value" id="way_type_value" value="" /> 
<input type="hidden" name="submit_flag" id="submit_flag" value="" /> 
<input type="hidden" name="smtp_server" id="smtp_server" value="<%=smtp_server%>"/> 
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%>>>�Զ��巢��</b></font>
</div>
<div style="margin-left:10px;margin-top:10px;">
	<input type="radio" name="way_type" id="way_type" style = "background:;border:0;" value="1" checked onclick="javascript:showWay(1);"/><font  size="2" face="΢���ź�">���Ͷ���</font>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="way_type" id="way_type" style = "background:;border:0;" value="2" onclick="javascript:showWay(2);"/><font  size="2" face="΢���ź�">�����ʼ�</font>
	&nbsp;&nbsp;
</div>
<div  id="r1" align="left" >
	<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
		<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC">
		<tr id="sendType"style="background:F7F7F7;">
			<td  width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;*������� :</font></td> 
			<td  width="*">
				<select id="send_type" name="send_type" style="width:120px" onchange="javascript:showPlanTime(this.value);">
					<option value ="1" selected>��ʱ����</option>
					<option value ="2">��ʱ����</option>	
				</select>
			</td>
			<td id="planTime" colspan="2"></td> 
			<td id="planTime1" width="130px" style="display:none;"><font size="2" face="΢���ź�">&nbsp;&nbsp;��ʱ����ʱ�� :</font> </td><!-- ��ʱ����ʱ�� -->
			<td id="planTime2" width="300px" style="display:none;">
				<input type="text" name="plan_time" id="plan_time" size="25" onclick="javascript:setday(this);"  value=""/>(�������ڵ�ǰʱ��30����)	
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td  width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;*��Ϣ���� :</font></td> 
			<td  width="*">
				<select id="msg_type" name="msg_type" style="width:120px" onchange="javascript:showTr(this.value);">
					<%= Argument.getDictParamOptions(1901, "")%>
				</select>
			</td>
			<td id="checkTd" colspan="2"></td> 
			<td width="130px" id="checkTd1" style="display:none;"><font size="2" face="΢���ź�">&nbsp;&nbsp;*����� :</font> </td>
			<td width="*" id="checkTd2" style="display:none;">
				<select name="check_man">
					<%=Argument.getOperatorOptions(new Integer(0)) %>
				</select>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td width="130px" valign="top"><font id="contnet" size="2" face="΢���ź�">&nbsp;&nbsp;�ֻ�����:</font><br>(�����ķ�������)</td>
			<td  width="*" colspan="3">
				<textarea rows="5" name="mobiles"  style="width:95%"></textarea>		
			</td>
		</tr>
		<tr  style="background:F7F7F7;">
			<td width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;ģ�� :</font></td>
			<td width="*" colspan="3">
				<input type="text" name="tempTitle" id="tempTitle" size="30"  value="" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<!-- ѡ�� -->  &nbsp;&nbsp;&nbsp;	
              	<button type="button"  class="xpbutton2" id="btnChoTemp" name="btnChoTemp" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
                <!-- ��� -->
				&nbsp;<button type="button"  class="xpbutton2" id="btnClear" name="btnClear" onclick="javascript:clearAction();"><%=LocalUtilis.language("message.clear",clientLocale)%> </button>
				<input type="hidden" name="tempID" id="tempID"  value="" />
			</td>
		</tr>  
		
		<tr style="background:F7F7F7;">
				<td width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.templateContent",clientLocale)%> :<br>(�˴�����ʹ��%1)</font></td>
				<td  width="*" colspan="3">
					<textarea rows="10" id="content_templet" name="content_templet"  style="width:95%"></textarea>		
				</td>
		</tr>
		<tr id="email1"  style="background:F7F7F7;display:none">
			<td  align="left">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.smtpService",clientLocale)%> ��</font></p>
			</td><!--SMTP������-->
			<td><%=smtp_server%></td>
			<td align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.authFlag",clientLocale)%> ��</font></td><!--�Ƿ���֤-->
			<td>
				<input type="checkbox"  name="auth_flag"  id="auth_flag" value="1" onkeydown="javascript:nextKeyPress(this)" class="flatcheckbox" checked disabled="disabled"/>
			</td>
		</tr>
		<tr id="email2"  style="background:F7F7F7;display:none">
			<td width="120px">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.fromEmail",clientLocale)%> ��</font></p>
			</td><!--�����˵�ַ-->
			<td><input name="from_email" size="30" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(Argument.getOpEmailName(input_operatorCode))%>" ></td>
			<td width="80px">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("login.password",clientLocale)%> ��</font></p>
			</td><!--����-->
			<td><input type="password" name="smtp_userpwd" size="30" onkeydown="javascript:nextKeyPress(this)" value="guifeng841024" ></td>
		</tr>
		</span>
	</table>
	</table>
	
</div>
<div align="right" >
    <!-- ���� -->
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:sendAction();">
		<%=LocalUtilis.language("message.send",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%></button>
	&nbsp;&nbsp;&nbsp;
    <!-- �ύ���-->
	<button type="button"  class="xpbutton3" style="display:none;" accessKey=n id="btnNext" name="btnNext"onclick="javascript: checkAction();">
	<%=LocalUtilis.language("message.submit",clientLocale)%><%=LocalUtilis.language("message.check",clientLocale)%></button>
	&nbsp;&nbsp;&nbsp;
    <!-- �ر� -->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>
</form>
</BODY>
</HTML>