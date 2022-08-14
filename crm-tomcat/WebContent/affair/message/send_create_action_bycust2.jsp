<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer close_flag = Utility.parseInt(request.getParameter("close_flag"), new Integer(0));

//声明辅助变量
int iCount = 0;
//获得对象
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = null;
SendSMSVO vo_details = null;
List rsList = null;
List rsList_details = null;


Integer way_type = Utility.parseInt(request.getParameter("way_type"), new Integer(1));
Integer send_type = Utility.parseInt(request.getParameter("send_type"), new Integer(0));
String msg_type = Utility.trimNull(request.getParameter("msg_type"));
String plan_time = Utility.trimNull(request.getParameter("plan_time"));
String mobiles = Utility.trimNull(request.getParameter("mobiles"));
String content_templet = Utility.trimNull(request.getParameter("content_templet"));
Integer check_man = Utility.parseInt(request.getParameter("check_man"), new Integer(0));

String smtp_server  = Utility.trimNull(Argument.getADDITIVE_VALUE("190201"));
String from_email = Utility.trimNull(request.getParameter("from_email"));
String smtp_userpwd = Utility.trimNull(request.getParameter("smtp_userpwd")); 

Map rsMap = null;
//编辑时显示 任务信息
if(serial_no.intValue()>0){
	vo = new SendSMSVO();
	vo_details = new SendSMSVO();
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
		mobiles = Utility.trimNull(rsMap.get("MOBILES"));
		content_templet = Utility.trimNull(rsMap.get("CONTENT_TEMPLET"));
		check_man = Utility.parseInt(Utility.trimNull(rsMap.get("CHECK_MAN")), new Integer(0));
	}
	
	vo_details.setSerial_no_total(serial_no);
	vo_details.setInputOperator(input_operatorCode);
	rsList_details = sendTotal.queryMessageDetail(vo_details);
}
if(plan_time.length()>0){
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

<script language=javascript>
/*批量添加客户信息*/
function addBatchCustomer(serial_no){
	var url = "<%=request.getContextPath()%>/affair/message/send_member_list.jsp?serial_no="+serial_no+"&first_flag=1";				
	if(showModalDialog(url,'', 'dialogWidth:800px;dialogHeight:500px;status:0;help:0')){
		sl_update_ok();
		refreshPage();
	}
}
/*按认购记录批量添加客户信息*/
function addBatchCustomerRg(serial_no){
	var url = "<%=request.getContextPath()%>/affair/message/send_member_rg_list.jsp?serial_no="+serial_no+"&first_flag=1";				
	if(showModalDialog(url,'', 'dialogWidth:800px;dialogHeight:500px;status:0;help:0')){
		sl_update_ok();
		refreshPage();
	}
}
function refreshPage(){
	var a = document.createElement("a");
	var v_serial_no= document.getElementById("serial_no").value;
	var v_close_flag = document.getElementById("close_flag").value;

    a.href = "<%=request.getContextPath()%>/affair/message/send_create_action_bycust2.jsp?serial_no="+v_serial_no+"&close_flag="+v_close_flag;
    document.body.appendChild(a);
    a.click();
}

/*删除功能*/
function delCustomer(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	if(sl_check_remove()){			
		var url = "<%=request.getContextPath()%>/affair/message/send_create_action_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

function previorsAction(){
	var v_close_flag = document.getElementById("close_flag").value;
	var a = document.createElement("a");
	var v_serial_no= document.getElementById("serial_no").value;
	
    a.href = "<%=request.getContextPath()%>/affair/message/send_create_action_bycust.jsp?serial_no="+v_serial_no+"&close_flag="+v_close_flag;
    document.body.appendChild(a);
    a.click();
}

function nextAction(){
    var iCount = document.getElementById("iCount").value;
	if(iCount <= 0){
		sl_alert("<%=LocalUtilis.language("message.noCustInfo",clientLocale)%> ！");//请选定要添加的记录
		return false;
	}	
	if(sl_confirm("提交审核!")){	
		var url = "<%=request.getContextPath()%>/affair/message/send_create_action_end.jsp";
		var form = document.getElementsByName("theform")[0];
		document.getElementById("submit_flag").value = "check";//提交审核
		form.setAttribute("action",url);
		form.submit();
	}
}
function sendAction(){
	var iCount = document.getElementById("iCount").value;
	if(iCount <= 0){
		sl_alert("<%=LocalUtilis.language("message.noCustInfo",clientLocale)%> ！");//请选定要添加的记录
		return false;
	}
	if(sl_confirm("对客户发送信息!")){
		if(document.getElementById("way_type").value == 2)
			validateEmial();
		var url = "<%=request.getContextPath()%>/affair/message/send_create_action_end.jsp";
		var form = document.getElementsByName("theform")[0];
		document.getElementById("submit_flag").value = "send";//发送
		form.setAttribute("action",url);
		form.submit();
	}
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
	if(document.getElementById("smtp_userpwd").value == ""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError3'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureUserPwdTip",clientLocale)%> ...");
		//邮件服务器需要密码验证，不能发送邮件！请配置用户名密码...
		return false;
	}
	return true;
}
function CancelAction(){
	window.parent.location.reload();
	window.parent.document.getElementById("closeImg").click();	
}
</script>

</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no"  id="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="close_flag" id="close_flag" value="<%= close_flag%>" />
<input type="hidden" name="submit_flag" id="submit_flag" value="" />
<input type="hidden" name="way_type" id="way_type" value="<%=way_type%>" />  
<input type="hidden" name="smtp_server" id="smtp_server" value="<%=smtp_server%>" /> 

	<div align="left" class="page-title">
        <!-- 任务管理 --><!-- 任务创建 --><!-- 任务信息设置 -->
		<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskAdd",clientLocale)%>>><%=LocalUtilis.language("message.taskSet",clientLocale)%></b></font>
	</div>	
	
	<div align="right" class="btn-wrapper">
		<button type="button" class="xpbutton5" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.batchAddCustomers",clientLocale)%> " onclick="javascript:addBatchCustomer(<%=serial_no%>);"><%=LocalUtilis.language("message.addCustomers",clientLocale)%> </button><!-- 添加客户 -->
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton5" accessKey=e name="btnEdit" title="按认购添加客户" onclick="javascript:addBatchCustomerRg(<%=serial_no%>);">按认购添加客户</button><!-- 添加客户 -->
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton5" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.deleteCustomer",clientLocale)%> " onclick="javascript:delCustomer(<%=serial_no%>);"><%=LocalUtilis.language("message.deleteCustomer",clientLocale)%> </button><!-- 删除客户 -->	
	</div>

<br/>
<div style="width:100%;">
		<div style=" float:left; width:40%;">
				 <table  id="table1" cellSpacing="1" cellPadding="2" width="99%" class="product-list">
				 			<tr style="background:F7F7F7;">
                                    <td colspan="2" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;信息内容 </b></font></td>
						 	</tr>
						 	<tr style="background:F7F7F7;">	
                                 <td width="30%"><font size="2" face="微软雅黑">&nbsp;短信/邮件:</font></td>
								 <td width="*"><font size="2" face="微软雅黑">&nbsp;<%if(way_type.intValue()==2)out.print("邮件发送");else out.print("短信发送");%></font>  </td>
							 </tr>
						 	 <tr style="background:F7F7F7;">	
                                 <td width="30%"><font size="2" face="微软雅黑">&nbsp;发送模式:</font></td>
								 <td width="*"><font size="2" face="微软雅黑">&nbsp;<%if(send_type.intValue()==2)out.print("定时发送");else out.print("即时发送");%></font>  </td>
							 </tr>
							 <tr style="background:F7F7F7;">	
                                 <td><font size="2" face="微软雅黑">&nbsp;信息类型:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<%=Argument.getDictParamName(1901,msg_type)%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">
                                 <td><font size="2" face="微软雅黑">&nbsp;审核人:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<%=Argument.getOpNameByOpCode(check_man)%></font>  </td>
							 </tr>
							 <%if(send_type.intValue() == 2){%>
							 <tr style="background:F7F7F7;">	
                                 <td><font size="2" face="微软雅黑">&nbsp;定时发送时间:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<%=plan_time%></font>  </td>
							 </tr>
							<%} %>			 	
						 	 <tr style="background:F7F7F7;">	
                                 <td><font size="2" face="微软雅黑">&nbsp;发送内容:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<%= content_templet%></font>  </td>
							 </tr>
							 <%if(way_type.intValue() == 2){%>	
							 <tr style="background:F7F7F7;">	
                                 <td><font size="2" face="微软雅黑">&nbsp;邮件服务器:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<%=smtp_server%></font>  </td>
							 </tr>
							 <tr style="background:F7F7F7;">	
                                 <td><font size="2" face="微软雅黑">&nbsp;发件人邮箱:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<input name="from_email" size="30" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(Argument.getOpEmailName(input_operatorCode))%>" ></font>  </td>
							 </tr>
							<tr style="background:F7F7F7;">	
                                 <td><font size="2" face="微软雅黑">&nbsp;邮箱密码:</font></td>
								 <td><font size="2" face="微软雅黑">&nbsp;<input type="password" name="smtp_userpwd" size="30" onkeydown="javascript:nextKeyPress(this)" value="guifeng841024" ></font>  </td>
							 </tr>	
							<%}%>
				 </table>
		</div>
		
		<div style="float:right; width:60%;">
			<table cellSpacing="1" cellPadding="2" width="480px"  bgcolor="#CCCCCC" class="product-list">
					<tr style="background:6699FF;">
						 <td width="*" align="center"><input type="checkbox" name="btnCheckbox" class="selectAllBox"	
				onclick="javascript:selectAllBox(document.theform.check_serial_no,this);"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- 客户名称 -->  	
						 <td width="20%" align="center"><%=LocalUtilis.language("class.mobile",clientLocale)%> </td><!-- 手机号码 -->
						 <td width="35%" align="center"><%=LocalUtilis.language("class.email",clientLocale)%> </td><!-- 状态 -->
					</tr>  
			</table>	
		<span id="tableList" style="overflow-y:auto;">
			<table border="0" cellspacing="1" cellpadding="2" bgcolor="#CCCCCC" class="product-list" style="width:480px;">	
					<%
					//声明字段
					String cust_name = null ;	
					String mobile = null;
					String mobile2 = null;
					String email = null;
					Integer serial_no_detail = null;

					if(rsList_details!=null&&rsList_details.size()>0){
						 Iterator iterator = rsList_details.iterator();
						Map rsMap_details = null;				
						while(iterator!=null&&iterator.hasNext()){		
							rsMap_details = (Map)iterator.next();
							iCount++;

							if(rsMap_details!=null){
									serial_no_detail = Utility.parseInt(Utility.trimNull(rsMap_details.get("SERIAL_NO")),null);
									cust_name = Utility.trimNull(rsMap_details.get("CUST_NAME"));	
									mobile = Utility.trimNull(rsMap_details.get("MOBILE"));
									mobile2 = Utility.trimNull(rsMap_details.get("BP"));	
									email = Utility.trimNull(rsMap_details.get("E_MAIL"));
							}
							
					%>
					<tr class="tr1">
						 <td align="center" width="*" >		
					       <table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="check_serial_no" value="<%= serial_no_detail%>" class="flatcheckbox"/> 
									
									<td width="90%" align="left"><%= cust_name%></td>
								</tr>
							</table></td> 
						 <td width="20%" ><%= mobile%></td> 
						 <td width="35%" ><%= email%></td> 
					</tr>
				<%}
				}%>
					
				<%if(iCount==0){%>
					<tr class="tr1"><td align="center" colspan="4"><%=LocalUtilis.language("message.noCustInfo",clientLocale)%> </td></tr><!-- 没有添加明细任务信息 -->
				<%}%>
				</table>
			</span><input type="hidden" name = "iCount" value ="<%=iCount %>">
		</div>

</div>

<div align="right" >		
		<button type="button" class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.up",clientLocale)%> " onclick="javascript: previorsAction();"><%=LocalUtilis.language("message.up",clientLocale)%> </button>
		&nbsp;&nbsp;&nbsp;
		<%if(check_man.intValue()!= 0){ %>
		<button type="button" class="xpbutton3" accessKey=c id="btnColse" name="btnColse" title="<%=LocalUtilis.language("message.next",clientLocale)%> " onclick="javascript: nextAction();">
			<%=LocalUtilis.language("message.submit",clientLocale)%><%=LocalUtilis.language("message.check",clientLocale)%></button>		
		<%}else{ %>
		<button type="button" class="xpbutton3" accessKey=c id="btnColse" name="btnColse" title="<%=LocalUtilis.language("message.next",clientLocale)%> " onclick="javascript: sendAction();"><%=LocalUtilis.language("message.send",clientLocale)%></button>
		<%} %>
		&nbsp;&nbsp;&nbsp;	
		 <!-- 关闭 -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>	
<% sendTotal.remove();%>
</form>
</BODY>
</HTML>