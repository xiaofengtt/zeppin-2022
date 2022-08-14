<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
Integer close_flag = Utility.parseInt(request.getParameter("close_flag"), new Integer(0));

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer way_type = Utility.parseInt(request.getParameter("way_type_value"), new Integer(1));
Integer send_type = Utility.parseInt(request.getParameter("send_type"), new Integer(0));
String msg_type = Utility.trimNull(request.getParameter("msg_type"));
String plan_time = Utility.trimNull(request.getParameter("plan_time"));
String content_templet = Utility.trimNull(request.getParameter("content_templet"));
Integer check_man = Utility.parseInt(request.getParameter("check_man"), new Integer(0));

//声明辅助变量
Integer actionFlag1 = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag1")),new Integer(1));//操作标识 1.新增；2.修改
boolean bSuccess = false;
//获得对象
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = null;
List rsList = null;
Map rsMap = null;
//保存信息
if(request.getMethod().equals("POST")){
	vo = new SendSMSVO();
	vo.setWay_type(way_type);
	vo.setSend_type(send_type);
	vo.setMsg_type(msg_type);
	if(send_type.intValue() == 2)
		vo.setPlan_time(plan_time);//定时发送时间
	else
		vo.setPlan_time(null);
	vo.setContent_templet(content_templet);
	vo.setCheck_man(check_man);
	vo.setInputOperator(input_operatorCode);

	if(actionFlag1.intValue()==1){
		serial_no = (Integer)sendTotal.appendMessage(vo);
		bSuccess = true;
	}else
	if(actionFlag1.intValue()==2){
		vo.setSerial_no(serial_no);
		sendTotal.modiMessage(vo);
		bSuccess = true;
	}

}
//编辑时显示记录
if(serial_no.intValue()>0){
	vo = new SendSMSVO();
	
	vo.setSerial_no(serial_no);
	vo.setInputOperator(input_operatorCode);
	
	rsList = sendTotal.queryMessage(vo);
	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
	}
	
	if(rsMap!=null){

		way_type = Utility.parseInt(Utility.trimNull(rsMap.get("WAY_TYPE")), new Integer(0));
		send_type = Utility.parseInt(Utility.trimNull(rsMap.get("SEND_TYPE")), new Integer(0));
		plan_time = Utility.trimNull(rsMap.get("PLAN_TIME"));
		msg_type = Utility.trimNull(rsMap.get("MSG_TYPE"));
		content_templet = Utility.trimNull(rsMap.get("CONTENT_TEMPLET"));
		check_man = Utility.parseInt(Utility.trimNull(rsMap.get("CHECK_MAN")), new Integer(0));
	}
}
if(plan_time!=null && plan_time.length()>0){
	plan_time = plan_time.substring(0,16);
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title></title>
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
//初始化页面
window.onload = function(){
	showTr();
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_serial_no = document.getElementById("serial_no").value;
	
	if(v_serial_no>0){
		document.getElementById("btnNext").style.display = "";
		document.getElementById("actionFlag1").value = 2;
	}
	if(v_bSuccess=="true"){
		sl_update_ok();
		nextAction();
	}
	showPlanTime('<%=send_type%>');
	showTr('<%=msg_type%>');
}
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

function clearAction(){
	document.getElementById("tempID").value=0;
	document.getElementById("tempTitle").value="";
	document.getElementById("content_templet").value="";	
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
	document.getElementById("way_type_value").value = way_type_value;
	if(!sl_checkChoice(document.getElementsByName('msg_type')[0],"<%=LocalUtilis.language("class.sms",clientLocale)%><%=LocalUtilis.language("class.type",clientLocale)%> ")){ return false;}//客户经理
	var msg_type = document.getElementsByName('msg_type')[0].value;
// 	if(msg_type== '190191' || msg_type == '190192'){
		if(!sl_checkChoice(document.getElementsByName('check_man')[0],"<%=LocalUtilis.language("class.reviewer",clientLocale)%> ")){ return false;}//审核人	
// 	}else{
// 		document.getElementsByName('check_man')[0].value = null;
// 	}
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
	if(!sl_check(document.theform.content_templet, "<%=LocalUtilis.language("class.smsContent",clientLocale)%> ",5000,1)){return false;}//短信内容
	
	return true;
	
}

/*保存*/
function saveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="send_create_action_bycust.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}
 
function nextAction(){
	var v_close_flag = document.getElementById("close_flag").value;
	var v_serial_no = document.getElementById("serial_no").value;
	var url = "<%=request.getContextPath()%>/affair/message/send_create_action_bycust2.jsp?close_flag="+v_close_flag+"&serial_no="+v_serial_no;
	if(v_close_flag==0){
		var a = document.createElement("a");
	    a.href = url;
	    document.body.appendChild(a);
	    a.click();	
	}
	else if(v_close_flag==1){
		window.parent.document.getElementById("_dialogIframe").src=url;
	}
}
function submitAction(check_flag){
	var check_man = document.getElementById("check_man").value;
	var obj = document.getElementsByName("send_type");
	var send_level = 0;
    if(obj!=null){
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
                send_level = obj[i].value;            
            }
        }
    }

	if(sms_content.length>0){
		if(sl_confirm("'提交短信信息'")){
			ccService.sendSmsByPhone(mobiles,sms_content,check_flag,check_man,send_level,send_time,"<%=input_operatorCode%>",{callback:function(data){
				var arr = data.split("|");
				sl_alert(arr[1]);
				window.parent.location.reload();
				window.parent.document.getElementById("closeImg").click();	
			}});
		}
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
// 	if(send_type== "190191" || send_type == "190192"){
	document.getElementById("checkTd").style.display = "none";
	document.getElementById("checkTd1").style.display = "";
	document.getElementById("checkTd2").style.display = "";
// 	}else{
// 		document.getElementById("checkTd").style.display = "";
// 		document.getElementById("checkTd1").style.display = "none";
// 		document.getElementById("checkTd2").style.display = "none";
// 	}
}
function showWay(way_type){
	if(way_type == 1 ){
		document.getElementById("sendType").style.display = "";
	}else{
		document.getElementById("sendType").style.display = "none";
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--保存标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="actionFlag1" id="actionFlag1" value="<%= actionFlag1%>" />
<input type="hidden" name="close_flag" id="close_flag" value="<%= close_flag%>" />
<input type="hidden" name="way_type_value" id="way_type_value" value="" /> 
<input type="hidden" name="serial_no"  id="serial_no" value="<%=serial_no%>"/>

<div align="left" class="page-title">
	<font color="#215dc6" size="2"><b><%=menu_info%>>>按客户发送</b></font>
</div>
<div style="margin-left:10px;margin-top:10px;">
	<input type="radio" name="way_type" id="way_type" style = "background:;border:0;" value="1" onclick="javascript:showWay(1);" <%if(way_type.intValue() ==1){%> checked <%} %> /><font  size="2" face="微软雅黑">发送短信</font>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="way_type" id="way_type" style = "background:;border:0;" value="2" onclick="javascript:showWay(2);" <%if(way_type.intValue() ==2){%> checked <%} %>/><font  size="2" face="微软雅黑">发送邮件</font>
	&nbsp;&nbsp;
</div>
<div  id="r1" align="left"  >
	<table border="0" width="98%" cellSpacing="1" cellPadding="2"  class="product-list">
		<tr id="sendType" style="background:F7F7F7;">
			<td  width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;*发送类别 :</font></td> 
			<td  width="*">
				<select id="send_type" name="send_type" style="width:120px" onchange="javascript:showPlanTime(this.value);">
					<option value ="1" <%if(send_type.intValue() ==1){%> selected <%}%>>即时发送</option>
					<option value ="2" <%if(send_type.intValue() ==2){%> selected <%}%>>定时发送</option>	
				</select>
			</td>
			<td id="planTime" colspan="2"></td> 
			<td id="planTime1" width="130px" style="display:none;"><font size="2" face="微软雅黑">&nbsp;&nbsp;定时发送时间 :</font> </td><!-- 定时发送时间 -->
			<td id="planTime2" width="300px" style="display:none;">
				<input type="text" name="plan_time" id="plan_time" size="25" onclick="javascript:setday(this);"  value="<%=plan_time%>"/>(必须晚于当前时间30分钟)	
			</td>
		</tr>	
		<tr style="background:F7F7F7;">
			<td  width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;*信息类型 :</font></td> 
			<td  width="*">
				<select id="msg_type" name="msg_type" style="width:120px" onchange="javascript:showTr(this.value);">
					<%= Argument.getDictParamOptions(1901, msg_type)%>
				</select>
			</td>
			<td id="checkTd" colspan="2"></td> 
			<td width="130px" id="checkTd1" style="display:none;"><font size="2" face="微软雅黑">&nbsp;&nbsp;*审核人 :</font> </td>
			<td width="*" id="checkTd2" style="display:none;">
				<select name="check_man">
					<%=Argument.getOperatorOptions(check_man) %>
				</select>
			</td>
		</tr>
		<tr  style="background:F7F7F7;">
			<td width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;信息模板 :</font></td> 
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
				<td width="130px"><font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.templateContent",clientLocale)%> :</font></td>
				<td  width="*" colspan="3">
						<textarea rows="10" name="content_templet"  style="width:95%"><%=content_templet%></textarea>		
				</td>
		</tr>
	</table>
</div>

<div align="right">
    <!-- 保存 -->
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
   	&nbsp;&nbsp;&nbsp;
	<!-- 下一步-->
	<button type="button"  class="xpbutton3" style="display:none;" accessKey=n id="btnNext" name="btnNext"onclick="javascript: nextAction();"><%=LocalUtilis.language("message.next",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;	
    <!-- 关闭 -->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>
</form>
</BODY>
</HTML>