<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//取得任务明细信息serial_no
String serialNoDetail_Message = Utility.trimNull(request.getParameter("serialNoDetail_Message"));
String[] serialNoDetail_Array = serialNoDetail_Message.split("_");
int serialNoDetail_len = serialNoDetail_Array.length;
//设置参数信息
Integer serial_no_detail = new Integer(0);
String cust_name = "";
String cust_mobile = "";
String sms_content = "";
String cust_name_alert = "";//用于保存没有手机号的客户姓名，并提示出来
Integer alertFlag = new Integer(0);
String taskDetailMsg = "";
//声明辅助变量
boolean bSuccess = false;
List rsList_task = new ArrayList();
Map map_task = null;
//获得对象
ServiceTaskLocal serviceTaskLocal = EJBFactory.getServiceTask();
ServiceTaskVO vo_details = new ServiceTaskVO();
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.sendMessageTaskDetail",clientLocale)%> </TITLE><!--发送明细任务短信-->  
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language="javascript">
/*编辑事件*/
function editAction(i){
	var taskMsgID = "taskDetailMsg_"+i;
	var taskDetailMsg = document.getElementById(taskMsgID).value;
	var taskMsgList = taskDetailMsg.split("_");
	document.getElementById("s_serial_no").value=taskMsgList[0];
	document.getElementById("s_cust_name").value=taskMsgList[1];
	document.getElementById("sms_content").value=taskMsgList[2];
}
/*保存*/
function saveAction(){
	var s_serial_no = document.getElementById("s_serial_no").value;
	var s_cust_name = document.getElementById("s_cust_name").value;
	var sms_content = document.getElementById("sms_content").value;
	if(s_serial_no!=''&&s_serial_no!=null){
		ccService.updateSMSContent(s_serial_no,sms_content,"<%=input_operatorCode%>",{callback:function(data){
			var arr = data.split("|");
			sl_alert(arr[1]);
			refreshPage();
		}});
	}
	else{
		sl_alert("<%=LocalUtilis.language("message.noRecords",clientLocale)%> ！");//未选中记录
	}
}
function refreshPage(){
	var a = document.createElement("a");
	var v_serialNoDetail_Message= document.getElementById("serialNoDetail_Message").value;

    a.href = "sendMes2.jsp?serialNoDetail_Message="+v_serialNoDetail_Message; 
    document.body.appendChild(a);
    a.click();
}
function sendAction(){
	var serialNoDetail_Message = "";
	var v_check_serial_no = document.getElementsByName("serial_no_detail");
	var v_sendLevel = document.getElementById("sendLevel").value;

	if(checkedCount(v_check_serial_no) == 0){
		sl_alert("<%=LocalUtilis.language("message.noSelectTask",clientLocale)%> ！");//请选定要发送的短信任务
		return false;
	}
	else{
		//选中的serialNo做成报文
		for(i = 0; i < v_check_serial_no.length; i++){	
			if(v_check_serial_no[i].checked){
				serialNoDetail_Message = serialNoDetail_Message + v_check_serial_no[i].value + "_";
			}	
		}
		if(serialNoDetail_Message.length>0){
			serialNoDetail_Message = serialNoDetail_Message.substring(0,serialNoDetail_Message.length-1);
			
			ccService.sendSMS(serialNoDetail_Message,v_sendLevel,"<%=input_operatorCode%>",{callback:function(data){
				var arr = data.split("|");
				//sl_alert(arr[1]);
				window.returnValue = arr[1];
				window.close()
			}});
		}	
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serialNoDetail_Message" value="<%=serialNoDetail_Message%>" />
	<div align="left" class="page-title">
        <!--发送短信-->
		<font color="#215dc6"><b><%=LocalUtilis.language("message.sendSMS",clientLocale)%>  </b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<font size=3><%=LocalUtilis.language("class.sendLevel",clientLocale)%> &nbsp;&nbsp; </font><!--短信优先级-->
		<select id="sendLevel" name="sendLevel" style="width:60px">
			<option value="-3"><%=LocalUtilis.language("message.high",clientLocale)%> </option><!--高-->
			<option value="-2" selected><%=LocalUtilis.language("message.edium",clientLocale)%> </option><!--中-->
			<option value="-1"><%=LocalUtilis.language("message.low",clientLocale)%> </option><!--低-->
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:sendAction();"><%=LocalUtilis.language("message.send",clientLocale)%> </button><!--发送-->
		&nbsp;&nbsp;
	</div>
<br/>	
	<div align="center" style="margin-left:5px">
		<table border="0"  width="570px" cellspacing="1" cellpadding="2" class="tablelinecolor">
			<tr style="background:6699FF;">
				<td width="30px" align="center">
					<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no_detail,this);">
				</td>
				<td align="center" width="20%"><font size="2" face="微软雅黑"><b><%=LocalUtilis.language("class.customerName",clientLocale)%> </b></font></td><!--客户名称-->
				<td align="center" width="20%"><font size="2" face="微软雅黑"><b><%=LocalUtilis.language("class.custMobile",clientLocale)%> </b></font></td><!--客户手机-->
				<td align="center" width="*"><font size="2" face="微软雅黑"><b><%=LocalUtilis.language("class.smsContent",clientLocale)%> </b></font></td><!--短信内容-->
				<td width="30px" align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td> <!--编辑-->
			</tr>
		</table>
		
		<span style="overflow-y:auto;height:160;">
			<table border="0" width="570px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
			<% 
				for(int i = 0;i<serialNoDetail_len;i++){
					serial_no_detail = Utility.parseInt(Utility.trimNull(serialNoDetail_Array[i]),new Integer(0));
				
					if(serial_no_detail.intValue()>0){
						vo_details.setSerial_no(serial_no_detail);
						vo_details.setInputMan(input_operatorCode);
						rsList_task = serviceTaskLocal.query_details(vo_details);
					}
					//都过serial_no 拿到cust_id
					if(rsList_task.size()>0){
						map_task = (Map)rsList_task.get(0);				
						cust_name = Utility.trimNull(map_task.get("CUST_NAME"));
						cust_mobile = Utility.encryCustTel(Utility.trimNull(map_task.get("Mobile")));
						sms_content = Utility.trimNull(map_task.get("SmsContent"));
						taskDetailMsg = serial_no_detail+"_"+cust_name+"_"+sms_content;
	
						if(cust_mobile.length()>0){
							alertFlag = new Integer(0);
						}
						else{
							alertFlag = new Integer(1);
							cust_name_alert = cust_name_alert + cust_name + "," ;
							cust_mobile = LocalUtilis.language("message.nothing",clientLocale);//无
						}
					
			%>
				<tr style="background:F7F7F7;" title="<%=LocalUtilis.language("message.doubleClickSelect",clientLocale)%> "><!--双击选中修改短信内容-->
					<td width="30px" align="center">
						<input type="checkbox" name="serial_no_detail" class="selectAllBox" value="<%=serial_no_detail%>" class="flatcheckbox" <%if(alertFlag.intValue()==1){%>style="display:none"<%}%> />
					</td>
					<td width="20%" align="center"><b><font size="2" face="微软雅黑" <%if(alertFlag.intValue()==1){%>color="gray"<%}%>><%= cust_name%></font></b></td>
					<td width="20%" align="center"><b><font size="2" face="微软雅黑" <%if(alertFlag.intValue()==1){%>color="gray"<%}%>><%=cust_mobile%></font></b></td>
					<td width="*" align="center"><b><font size="2" face="微软雅黑" <%if(alertFlag.intValue()==1){%>color="gray"<%}%>><%= sms_content%></font></b></td>
					<td width="30px" align="center" >             	
		           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16" style="cursor:hand;" onclick="javascript:editAction(<%=i%>);">
						<input type="hidden" id="taskDetailMsg_<%=i%>" value="<%=taskDetailMsg%>" />
		         	</td>
				</tr>
					<%}
				}%>
			</table>
		</span>
	</div>

	<div style="margin-top:5px;margin-left:5px">
		<table border="0"  width="570px" cellspacing="1" cellpadding="2" class="tablelinecolor">
			<tr style="background:F7F7F7;">
		 		<td colspan="2" align="left"><p class="title-table"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.setSMSContent",clientLocale)%> </b></font></p></td><!--短信内容设置-->
		 	</tr>
			<tr style="background:F7F7F7;">
				<td width="120px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerName",clientLocale)%> ：</font></td><!--客户名称-->
				<td>
					<input type="text" name="s_cust_name" style="width:120px"  readonly class='edline' />
					<input type="hidden" id="s_serial_no" value="" />
				</td>
			</tr>
			<tr style="background:F7F7F7;">
				<td valign="top" width="120px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.smsContent",clientLocale)%> ：</font></td><!--短信内容-->
				<td><textarea rows="5" name="sms_content" onkeydown="javascript:nextKeyPress(this)" style="width:98%"></textarea></td>
			</tr>
		</table>
	</div>

	<div align="right" style="margin-right:5px; margin-top:10px">	
		<button type="button" class="xpbutton2" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> </button><!--保存-->
		&nbsp;&nbsp;
		<button type="button" class="xpbutton2" id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> </button><!--关闭-->
	</div>	
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>