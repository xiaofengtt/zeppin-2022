<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递参数
Integer channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
String channel_code = Utility.trimNull(request.getParameter("channel_code"));
String channel_name = Utility.trimNull(request.getParameter("channel_name"));
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")), new Integer(0));
String plan_content = Utility.trimNull(request.getParameter("plan_content"));
Integer plan_man = Utility.parseInt(Utility.trimNull(request.getParameter("plan_man")), new Integer(0));
String plan_time = Utility.trimNull(request.getParameter("plan_time"));
String service_time = Utility.trimNull(request.getParameter("service_time"));
String service_content = Utility.trimNull(request.getParameter("service_content"));;
Integer service_man = Utility.parseInt(Utility.trimNull(request.getParameter("service_man")), new Integer(0));

//声明辅助变量
boolean bSuccess = false;
input_bookCode = new Integer(1);
int iCount = 0;
List list = null;
Map map = new HashMap();
String disabled = "";
	
//获得对象
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();
//保存对象
if(request.getMethod().equals("POST")){
	vo.setChannel_id(channel_id);
	vo.setPlan_contnet(plan_content);
	vo.setPlan_man(plan_man);
	vo.setPlan_time(plan_time);
	vo.setPlan_contnet(plan_content);
	vo.setService_time(service_time);
	vo.setService_content(service_content);
	vo.setService_man(service_man);
	vo.setInput_man(input_operatorCode);

	if(serial_no.intValue()>0){
		vo.setSerial_no(serial_no);		
		local.modiServicePlans(vo);
		bSuccess = true;
	}
	else{
		local.appendServicePlans(vo);
		bSuccess = true;
	}
}

if(serial_no.intValue()>0){
	vo = new ChannelVO();
	vo.setSerial_no(serial_no);

	list = local.queryServicePlan(vo);

	if(list!=null&&list.size()>0){
		map = (HashMap)list.get(0);
		
		if(map!=null){
			serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
			channel_id = Utility.parseInt(Utility.trimNull(map.get("CHANNEL_ID")),new Integer(0));
			channel_code =Utility.trimNull(map.get("CHANNEL_CODE"));
			channel_name =Utility.trimNull(map.get("CHANNEL_NAME"));
			
			//计划维护
			plan_time = Utility.trimNull(map.get("PLAN_TIME"));
			plan_content =Utility.trimNull(map.get("PLAN_CONTENT"));
			plan_man = Utility.parseInt(Utility.trimNull(map.get("PLAN_MAN")),new Integer(0));
			
			//实际维护
			service_time = Utility.trimNull(map.get("SERVICE_TIME"));
			service_content =Utility.trimNull(map.get("SERVICE_CONTENT"));
			service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
		}
	}
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
<title></title><!--代理销售渠道设置-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}
/*保存*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}


/*验证数据*/
function validateForm(form){
	if(form.channel_id.value <1){
		sl_alert('<%=LocalUtilis.language("message.select2",clientLocale)%><%=LocalUtilis.language("class.partnName",clientLocale)%>！');//请选择渠道名称
		return false;
	}
	
	return sl_check_update();
}
/*取消*/
function CancelAction(){
	window.close();
}

</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" action="service_plan_action.jsp" method="post" onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="channel_code" name="channel_code" value="<%=channel_code%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="channel_name" name="channel_name" value="<%=channel_name%>"/>
<div align="left" class="page-title ">
	<font color="#215dc6"><b>代理销售渠道>>代理销售渠道设置</b></font>
</div>
<br/>
<div align="left" style="">
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
	 	<tr>
			<td  align="right">*<%=LocalUtilis.language("class.partnName",clientLocale)%>: </td><!-- 渠道名称 -->
			<td colspan=3 align="left">
				<select size="1" name="channel_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 200px">
					<%=Argument.getChannelName(channel_id)%>
				</select>
				<br/>
			</td>	
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.planMaintain",clientLocale)%><%=LocalUtilis.language("class.time",clientLocale)%>: </td><!-- 计划维护时间 -->
			<td width="*">
				<input type="text" name="plan_time" id="plan_time" size="30" onclick="javascript:setday(this);" readOnly value="<%=plan_time %>"/> 
			</td>
			<td  align="right"><%=LocalUtilis.language("class.planMaintain",clientLocale)%><%=LocalUtilis.language("class.persons",clientLocale)%>: </td><!--计划维护人-->
			<td  align="left">
				<select size="1" name="plan_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(plan_man)%>
				</select>
				<br/>
			</td>
		</tr>
 		<tr>
			<td width="100px" align="right" vAlign="top">
			<%=LocalUtilis.language("class.planMaintain",clientLocale)%><%=LocalUtilis.language("class.content2",clientLocale)%>:</td><!--计划维护内容-->
			<td colspan=3><textarea rows="5" name="plan_content" onkeydown="javascript:nextKeyPress(this)" cols="100"><%=plan_content%></textarea></td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("class.time",clientLocale)%>: </td><!-- 实际维护时间 -->
			<td width="*">
				<input type="text" name="service_time" id="service_time" size="30" onclick="javascript:setday(this);" readOnly value="<%=service_time %>"/> 
			</td>
			<td  align="right"><%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("class.persons",clientLocale)%>: </td><!--实际维护人-->
			<td  align="left">
				<select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(service_man)%>
				</select>
				<br/>
			</td>
		</tr>
 		<tr>
			<td width="100px" align="right" vAlign="top">
			<%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("class.content2",clientLocale)%>:</td><!--实际维护内容-->
			<td colspan=3><textarea rows="5" name="service_content" onkeydown="javascript:nextKeyPress(this)" cols="100"><%=service_content%></textarea></td>
		</tr>
	</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<!-- 保存 -->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 关闭 -->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
<%
local.remove();
%>
</body>
</html>
