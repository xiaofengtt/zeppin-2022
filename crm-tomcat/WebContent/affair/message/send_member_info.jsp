<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
Integer tempID = Utility.parseInt(Utility.trimNull(request.getParameter("tempID")),new Integer(0));
String serial_nos = Utility.trimNull(request.getParameter("serial_nos"));

String[] serial_nos_Array = serial_nos.split("_");
int serial_nos_len = serial_nos_Array.length;

//设置参数信息
Integer cust_id = new Integer(0);
String cust_name = "";
String cust_mobile = "";
String sms_content = "";
String cust_name_alert = "";//用于保存没有手机号的客户姓名，并提示出来
Integer alertFlag = new Integer(0);
String taskDetailMsg = "";
//声明辅助变量
boolean bSuccess = false;
List rsList_sms = new ArrayList();
Map map_cust = null;
//获得对象
SmsRecordLocal smsRecordLocal = EJBFactory.getSmsRecord();
SendSMSVO vo_sms = new SendSMSVO();
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.sendSMS",clientLocale)%> </TITLE><!--发送短信-->  
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language="javascript">

/*保存*/
function saveAction(){
	var s_serial_no = document.getElementById("s_serial_no").value;
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

function nextAction(check_flag){
	var serial_nos_Message = "";
	var serial_nos = document.getElementsByName("serial_no");
		
	if(checkedCount(serial_nos) == 0){
		sl_alert("<%=LocalUtilis.language("message.noSelectTask",clientLocale)%> ！");//请选定要发送的短信任务
		return false;
	}
	else{
			
		var send_time = '';
		if(document.getElementById('sendTime').style.display == ""){
			if(!sl_check(document.getElementById('send_time'), "<%=LocalUtilis.language("class.smsTime",clientLocale)%> ", 20, 1))return false;
			send_time = document.getElementById('send_time').value;
			var time1 = new Date(send_time.replace(/[-]/g,"/"));
			var now_time = new Date();
			var s = (time1.getTime() - now_time.getTime())/(1000*60);//与当前时间相差的分钟数.
			if(s < 30)
			{
				sl_alert("定时发送时间必须晚于当前时间30分钟");
				return false;
			}
		}
	
		var v_send_type = document.getElementById("send_type").value;
		if(v_send_type == 0){
			sl_alert("请选择短信类别!");
			return false;
		}
		if(v_send_type== 1 || v_send_type == 2){
			if(!sl_checkChoice(document.getElementById(('check_man')),"<%=LocalUtilis.language("class.reviewer",clientLocale)%> ")){ return false;}//审核人	
		}
 		var obj = document.getElementsByName("send_method");
		var send_level = 0;
		if(obj!=null){
		    for(i=0;i<obj.length;i++){
		        if(obj[i].checked){
		            send_level = obj[i].value;            
		        }
		    }
		}
		//选中的serialNo做成报文
		for(i = 0; i < serial_nos.length; i++){	
			if(serial_nos[i].checked){
				serial_nos_Message = serial_nos_Message + serial_nos[i].value + "_";
			}	
		}
	alert(send_time);
		if(serial_nos_Message.length>0){
			serial_nos_Message = serial_nos_Message.substring(0,serial_nos_Message.length-1);
			if(check_flag == 1){//直接发送
				ccService.sendSmsInfo(serial_nos_Message,send_level,send_time,"<%=input_operatorCode%>",{callback:function(data){
					var arr = data.split("|");
					sl_alert(arr[1]);
					window.close();
				}});
				
			}else{//提交审核
				var check_man = document.getElementById("check_man").value;
				ccService.updateSmsStatus(serial_nos_Message,send_level,send_time,check_man,"<%=input_operatorCode%>",{callback:function(data){
					var arr = data.split("|");
					sl_alert(arr[1]);
					window.close();
				}});
			}
		}	
	}
}
 
function showTr(){
	var send_type = document.getElementsByName('send_type')[0].value;
	if(send_type== 1 || send_type == 2){
		document.getElementById("check_info").style.display = "";	
		document.getElementById("btnSave").style.display = "none";
		document.getElementById("btnNext").style.display = "";	
	}
	else{
		document.getElementById("check_info").style.display = "none";
		document.getElementById("btnSave").style.display = "";
		document.getElementById("btnNext").style.display = "none";
	}
}
function showSendTime(send_method){
	if(send_method == 1){
		document.getElementById("sendTime").style.display = "none";

	}
	else{
		document.getElementById("sendTime").style.display = "";

	}
}
function deleteInfo(){
	ccService.delSmsInfo("-2","<%=input_operatorCode%>",{callback:function(data){
		var arr = data.split("|");
	}}); 
}
</script>
</HEAD>

<BODY class="BODY" onunload="javascript:deleteInfo();">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);" >
<input type="hidden" id="serial_nos" value="<%=serial_nos%>" />
<input type="hidden" id="sendLevel" value="1" />
	<div align="left">
        <!--发送短信-->
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("message.sendSMS",clientLocale)%>  </b></font>
	</div>
	<div style="margin-left:10px;margin-top:10px;">
		<input type="radio" name="send_method" id="send_method" style = "background:;border:0;" onclick="javascript:showSendTime('1');" value="1" checked /><font  size="2" face="微软雅黑">即时发送</font>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="send_method" id="send_method" style = "background:;border:0;" onclick="javascript:showSendTime('2');" value="2"/><font  size="2" face="微软雅黑">定时发送</font>
		(必须晚于当前时间半小时)
	</div>
	<div align="right">
		<span style="display:none;" id="sendTime" >	
			<font size="2" face="微软雅黑">&nbsp;&nbsp;*<%=LocalUtilis.language("class.smsTime",clientLocale)%> :</font>
			<input type="text" name="send_time" id="send_time" size="20" onclick="javascript:setday(this);"  value=""/>		
		</span>	
		*短信类型:
		<select id="send_type" name="send_type" onchange="javascript:showTr();">
			<option value="0">请选择</option> 
			<option value="1">新品推介</option> 
			<option value="2">资讯、市场行情</option> 
			<option value="3">信息披露</option>
			<option value="4">生日、节日祝福</option>
			<option value="5">活动宣传</option>
			<option value="6">预约、缴款确认</option>
			<option value="7">其他</option>
		</select>
		<span style="display:none;" id="check_info" >	
			*审核人:
			<select name="check_man"  id="check_man">
				<%=Argument.getOperatorOptions(new Integer(0)) %>
			</select>
		</span>	
		&nbsp;&nbsp;
	</div>
	<hr noshade color="#808080" size="1" width="98%">
	
	<div align="center" style="margin-left:5px">
		<table border="0"  width="570px" cellspacing="1" cellpadding="2" class="tablelinecolor">
			<tr>
				<td width="30px" align="center">
					<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
				</td>
				<td align="center" width="20%"><font size="2" face="微软雅黑"><b><%=LocalUtilis.language("class.custMobile",clientLocale)%> </b></font></td><!--客户手机-->
				<td align="center" width="*"><font size="2" face="微软雅黑"><b><%=LocalUtilis.language("class.smsContent",clientLocale)%> </b></font></td><!--短信内容-->
			</tr>
		</table>
		
		<span style="overflow-y:auto;height:280px;">
			<table border="0" width="570px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
			<% 
				for(int i = 0;i<serial_nos_len;i++){
					Integer serial_no = Utility.parseInt(Utility.trimNull(serial_nos_Array[i]),new Integer(0));
				
					if(serial_no.intValue() > 0){
						vo_sms.setSmsIndex(serial_no);
						vo_sms.setInputOperator(input_operatorCode);
						rsList_sms = smsRecordLocal.query(vo_sms);
					}
					//都过serial_no 
					if(rsList_sms.size()>0){
						Map map_sms = (Map)rsList_sms.get(0);				
						cust_name = Utility.trimNull(map_sms.get("CUST_NAME"));
						cust_mobile = Utility.trimNull(map_sms.get("PhoneNumber"));
						sms_content = Utility.trimNull(map_sms.get("SmsContent"));
						taskDetailMsg = serial_nos+"_"+cust_name+"_"+sms_content;
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
						<%if(alertFlag.intValue()!=1){%><input type="checkbox" name="serial_no" id="serial_no"  class="selectAllBox" value="<%=serial_no%>" class="flatcheckbox"  /> <%}%>
					</td>
					<td width="15%" align="center"><b><font size="2" face="微软雅黑" <%if(alertFlag.intValue()==1){%>color="gray"<%}%>><%=cust_mobile%></font></b></td>
					<td width="*" align="center" style="padding-right:10px;"><b><font size="1" face="微软雅黑" <%if(alertFlag.intValue()==1){%>color="gray"<%}%>><%= sms_content%></font></b></td>

				</tr>
					<%}
				}%>
			</table>
		</span>
	</div>
	<div align="right" style="margin-right:5px; margin-top:10px">	
		<!-- 发送 -->
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:nextAction(1);">
			<%=LocalUtilis.language("message.send",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%></button>
		<!-- 提交审核-->
		<button type="button" class="xpbutton3" style="display:none;" accessKey=n id="btnNext" name="btnNext"onclick="javascript: nextAction(2);">
		<%=LocalUtilis.language("message.submit",clientLocale)%><%=LocalUtilis.language("message.check",clientLocale)%> </button>
		&nbsp;&nbsp;&nbsp;	
		<button type="button" class="xpbutton2" id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> </button><!--关闭-->
	</div>	
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>