<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.web.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量


Integer actionFlag1 = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag1")),new Integer(1));//操作标识 1.新增；2.修改
boolean bSuccess = false;
//获得对象
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = null;
SendMail2 mail=new SendMail2();
List rsList = null;
Map rsMap = null;
//保存信息
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
		vo.setPlan_time(plan_time);//定时发送时间
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
		if(way_type.intValue() == 2){//邮件
			vo.setSerial_no(serial_no);
			sendTotal.sendEmail(vo);
		}else{
			sendTotal.sendSms(serial_no,input_operatorCode);//发送短信
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
<title><%=LocalUtilis.language("menu.addTask",clientLocale)%> 1</title><!-- 任务创建设置 -->
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
		alert("<%=LocalUtilis.language("message.sendOK",clientLocale)%> ！");//发送成功;
	<%}else{%>
		alert("<%=LocalUtilis.language("message.tSuccessfullysub",clientLocale)%> ！");//提交审核成功
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
		if(!sl_checkChoice(document.getElementsByName('check_man')[0],"<%=LocalUtilis.language("class.reviewer",clientLocale)%> ")){ return false;}//审核人	
	}
	var plan_time = null;
	if(document.getElementById('planTime2').style.display != "none"){
		if(!sl_check(document.getElementById('plan_time'), "<%=LocalUtilis.language("class.smsTime",clientLocale)%> ", 20, 1))return false;
		plan_time = document.getElementById('plan_time').value;
		var time1 = new Date(plan_time.replace(/[-]/g,"/"));
		var now_time = new Date();
		var s = (time1.getTime() - now_time.getTime())/(1000*60);//与当前时间相差的分钟数.
		if(s < 30)
		{
			sl_alert("定时发送时间必须晚于当前时间30分钟");
			return false;
		}
	}
	if(!sl_check(document.getElementById("mobiles"), "<%=LocalUtilis.language("class.mobile",clientLocale)%>/<%=LocalUtilis.language("class.email",clientLocale)%> ",2000,1)){return false;}//手机号码-邮件地址
	if(!sl_check(document.theform.content, "<%=LocalUtilis.language("class.smsContent",clientLocale)%> ",5000,1)){return false;}//短信内容
	if(way_type_value == 2){
		validateEmial();
	}
	var sms_content = document.getElementById("content_templet").value
	if(sms_content.indexOf("%1") != -1)
	{
		sl_alert("字符'%1'不适用");
		return false;
	}
	return true;
	
}
function validateEmial()
{
	if(document.getElementById("smtp_server").value == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureServerTip",clientLocale)%> ...");
		//邮件服务器地址为空，不能发送邮件！请配置服务器...
		return false;
	}
	if(document.getElementById("from_email") .value == ""){
		sl_alert("<%=LocalUtilis.language("message.fromEmialError'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureFromEmailTip",clientLocale)%> ...");
		//发件人地址为空，不能发送邮件！请配置发件人...
		return false;
	}
	if(document.getElementById("smtp_user.value") == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError2'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureUserNameTip",clientLocale)%> ...");
		//邮件服务器需要用户名验证，不能发送邮件！请配置用户名...
		return false;
	}
	if(document.getElementById("smtp_userpwd.value") == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError3'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureUserPwdTip",clientLocale)%> ...");
		//邮件服务器需要密码验证，不能发送邮件！请配置用户名密码...
		return false;
	}
	return true;
}
function sendAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="send_create_action.jsp";
		document.getElementById("submit_flag").value = "send";//发送
		document.getElementsByName('theform')[0].submit();
	}	
}
function checkAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="send_create_action.jsp";
		document.getElementById("submit_flag").value = "check";//提交审核
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
		document.getElementById("contnet").innerHTML = "&nbsp;&nbsp;手机号码";
		document.getElementById("email1").style.display = "none";
		document.getElementById("email2").style.display = "none";
		
		
	}else{
		document.getElementById("sendType").style.display = "none";
		document.getElementById("send_type").value = 2;
		document.getElementById("contnet").innerHTML = "&nbsp;&nbsp;邮件地址";
		document.getElementById("email1").style.display = "";
		document.getElementById("email2").style.display = "";
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--保存标志-->
<input type="hidden" name="actionFlag1" id="actionFlag1" value="<%=actionFlag1%>" />
<input type="hidden" name="way_type_value" id="way_type_value" value="" /> 
<input type="hidden" name="submit_flag" id="submit_flag" value="" /> 
<input type="hidden" name="smtp_server" id="smtp_server" value="<%=smtp_server%>"/> 
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%>>>自定义发送</b></font>
</div>
<div style="margin-left:10px;margin-top:10px;">
	<input type="radio" name="way_type" id="way_type" style = "background:;border:0;" value="1" checked onclick="javascript:showWay(1);"/><font  size="2" face="微软雅黑">发送短信</font>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="way_type" id="way_type" style = "background:;border:0;" value="2" onclick="javascript:showWay(2);"/><font  size="2" face="微软雅黑">发送邮件</font>
	&nbsp;&nbsp;
</div>
<div  id="r1" align="left" >
	<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
		<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC">
		<tr id="sendType"style="background:F7F7F7;">
			<td  width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;*发送类别 :</font></td> 
			<td  width="*">
				<select id="send_type" name="send_type" style="width:120px" onchange="javascript:showPlanTime(this.value);">
					<option value ="1" selected>即时发送</option>
					<option value ="2">定时发送</option>	
				</select>
			</td>
			<td id="planTime" colspan="2"></td> 
			<td id="planTime1" width="130px" style="display:none;"><font size="2" face="微软雅黑">&nbsp;&nbsp;定时发送时间 :</font> </td><!-- 定时发送时间 -->
			<td id="planTime2" width="300px" style="display:none;">
				<input type="text" name="plan_time" id="plan_time" size="25" onclick="javascript:setday(this);"  value=""/>(必须晚于当前时间30分钟)	
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td  width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;*信息类型 :</font></td> 
			<td  width="*">
				<select id="msg_type" name="msg_type" style="width:120px" onchange="javascript:showTr(this.value);">
					<%= Argument.getDictParamOptions(1901, "")%>
				</select>
			</td>
			<td id="checkTd" colspan="2"></td> 
			<td width="130px" id="checkTd1" style="display:none;"><font size="2" face="微软雅黑">&nbsp;&nbsp;*审核人 :</font> </td>
			<td width="*" id="checkTd2" style="display:none;">
				<select name="check_man">
					<%=Argument.getOperatorOptions(new Integer(0)) %>
				</select>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td width="130px" valign="top"><font id="contnet" size="2" face="微软雅黑">&nbsp;&nbsp;手机号码:</font><br>(用中文符；隔开)</td>
			<td  width="*" colspan="3">
				<textarea rows="5" name="mobiles"  style="width:95%"></textarea>		
			</td>
		</tr>
		<tr  style="background:F7F7F7;">
			<td width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;模板 :</font></td>
			<td width="*" colspan="3">
				<input type="text" name="tempTitle" id="tempTitle" size="30"  value="" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<!-- 选择 -->  &nbsp;&nbsp;&nbsp;	
              	<button type="button"  class="xpbutton2" id="btnChoTemp" name="btnChoTemp" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
                <!-- 清空 -->
				&nbsp;<button type="button"  class="xpbutton2" id="btnClear" name="btnClear" onclick="javascript:clearAction();"><%=LocalUtilis.language("message.clear",clientLocale)%> </button>
				<input type="hidden" name="tempID" id="tempID"  value="" />
			</td>
		</tr>  
		
		<tr style="background:F7F7F7;">
				<td width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.templateContent",clientLocale)%> :<br>(此处不能使用%1)</font></td>
				<td  width="*" colspan="3">
					<textarea rows="10" id="content_templet" name="content_templet"  style="width:95%"></textarea>		
				</td>
		</tr>
		<tr id="email1"  style="background:F7F7F7;display:none">
			<td  align="left">
				<p align="left"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.smtpService",clientLocale)%> ：</font></p>
			</td><!--SMTP服务器-->
			<td><%=smtp_server%></td>
			<td align="left"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.authFlag",clientLocale)%> ：</font></td><!--是否验证-->
			<td>
				<input type="checkbox"  name="auth_flag"  id="auth_flag" value="1" onkeydown="javascript:nextKeyPress(this)" class="flatcheckbox" checked disabled="disabled"/>
			</td>
		</tr>
		<tr id="email2"  style="background:F7F7F7;display:none">
			<td width="120px">
				<p align="left"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.fromEmail",clientLocale)%> ：</font></p>
			</td><!--发件人地址-->
			<td><input name="from_email" size="30" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(Argument.getOpEmailName(input_operatorCode))%>" ></td>
			<td width="80px">
				<p align="left"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("login.password",clientLocale)%> ：</font></p>
			</td><!--密码-->
			<td><input type="password" name="smtp_userpwd" size="30" onkeydown="javascript:nextKeyPress(this)" value="guifeng841024" ></td>
		</tr>
		</span>
	</table>
	</table>
	
</div>
<div align="right" >
    <!-- 发送 -->
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:sendAction();">
		<%=LocalUtilis.language("message.send",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%></button>
	&nbsp;&nbsp;&nbsp;
    <!-- 提交审核-->
	<button type="button"  class="xpbutton3" style="display:none;" accessKey=n id="btnNext" name="btnNext"onclick="javascript: checkAction();">
	<%=LocalUtilis.language("message.submit",clientLocale)%><%=LocalUtilis.language("message.check",clientLocale)%></button>
	&nbsp;&nbsp;&nbsp;
    <!-- 关闭 -->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>
</form>
</BODY>
</HTML>