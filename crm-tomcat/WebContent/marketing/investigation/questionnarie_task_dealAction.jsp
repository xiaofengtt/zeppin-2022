<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String serviceTitle = Utility.trimNull(request.getParameter("serviceTitle"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer ques_taskId = Utility.parseInt(request.getParameter("ques_taskId"), new Integer(0));
Integer ques_taskId_detail = Utility.parseInt(request.getParameter("ques_taskId_detail"), new Integer(0));
Integer targetCustId = Utility.parseInt(request.getParameter("targetCustId"), new Integer(0));
Integer ques_Id = Utility.parseInt(request.getParameter("ques_Id"), new Integer(0));

//获得对象
QuestionnaireLocal local_ques = EJBFactory.getQuestionnaire();
QuestionnaireVO vo_ques = null;
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo_detail = null;

int iCount = 0;
int iCount2=0;
List rsList_ques = null;
List rsList_topic = null;
List rsList_task = null;
List rsList_taskDetail = null;
List rsList_topicValue = null;
Map map_ques = null;
Map map_topic = null;
Map map_task = null;
Map map_taskDetail = null;
Map map_topicValue = null;

//声明字段
String ques_title = "";

//声明辅助变量
boolean bSuccess = false;

if(ques_taskId.intValue()>0){
	vo_detail = new ServiceTaskVO();
	vo_detail.setSerial_no(ques_taskId);
	vo_detail.setServiceType(new Integer(64));
	vo_detail.setInputMan(input_operatorCode);
	rsList_task = serviceTask.query(vo_detail);
	
	if(rsList_task.size()>0){
		map_task = (Map)rsList_task.get(0);			
		ques_Id = Utility.parseInt(Utility.trimNull(map_task.get("QUES_ID")), new Integer(0));
	}
}

if(ques_Id.intValue()>0){
	 vo_ques = new QuestionnaireVO();	 
	 vo_ques.setQues_id(ques_Id);
	 	 
	 rsList_ques = local_ques.queryQuestInfo(vo_ques);
	 map_ques = null;
	 
	 if(rsList_ques.size()>0){
	 	 map_ques = (Map)rsList_ques.get(0);	 	 
	 	 ques_title = Utility.trimNull(map_ques.get("QUES_TITLE"));
	 }
	 
	rsList_topic = local_ques.queryQuesTopic(vo_ques);
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
<title><%=LocalUtilis.language("menu.questionnarieTaskList",clientLocale)%> </title>
<!--服务创建-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language="javascript">
function setTopicValue(topic_id,topic_value){
	var topic_value_id = "topic_value_" +topic_id;
	document.getElementById(topic_value_id).value = topic_value;
}

/*验证数据*/
function validateForm(form){
	//if(!sl_check(document.getElementsByName('executor')[0],"<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ",60,1)){ return false;}//任务标题
	if(!sl_check(document.getElementById('execute_date'), "<%=LocalUtilis.language("class.nearExecuteDate",clientLocale)%> ", 20, 1))return false;//最近联系日期
	if(!sl_checkChoice(document.getElementsByName('service_way')[0],"<%=LocalUtilis.language("message.serviceWay",clientLocale)%> ")){ return false;}//实际联系方式
	if(document.getElementsByName("service_status")[0].value==9){
		var deal_result = document.getElementsByName("deal_result")[0].value;
		if(deal_result==""){
			alert("作废，请一定在备注中写明详细原因！");//作废，请一定在备注中写明详细原因
			return false;
		}
	}
			
	return sl_check_update();			
}

function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		var theform = document.getElementById("theform");
		theform.action="questionnarie_task_dealAction_1.jsp"
		theform.submit();
	}
}

function callServiceWay(){
	var target_custid = document.getElementById("targetCustId").value;
	<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
	//var url = "/affair/sms/cust_tel.jsp?target_custid="+target_custid;
	//showModalDialog(url,'','dialogWidth:420px;dialogHeight:300px;status:0;help:0');	
		document.parentWindow.parent.document.getElementById("target_custid").value = target_custid; 
		document.parentWindow.parent.document.getElementById("callTalkType").value = 1;
		document.parentWindow.parent.document.getElementById("callcenterLink").onclick();
	<%}
	else{%>
		sl_alert("<%=LocalUtilis.language("message.callCenterNotEnabled",clientLocale)%> ");//呼叫中心未启用
	<%}%>
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="ques_taskId" name="ques_taskId" value="<%=ques_taskId%>"/>
<input type="hidden" id="ques_taskId_detail" name="ques_taskId_detail" value="<%=ques_taskId_detail%>"/>
<input type="hidden" id="targetCustId" name="targetCustId" value="<%=targetCustId%>"/>
<input type="hidden" id="ques_Id" name="ques_Id" value="<%=ques_Id%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("class.serviceProcessing",clientLocale)%>>><%=LocalUtilis.language("message.taskDetails",clientLocale)%> </b></font>
	<!--任务管理>>任务处理>>任务明细处理-->
</div>
<div align="right" class="btn-wrapper">
<button type="button"  class="xpbutton3" id="queryButton2" name="queryButton2" title="<%=LocalUtilis.language("class.tele",clientLocale)%> " onclick="javascript:callServiceWay();" ><%=LocalUtilis.language("message.telephone",clientLocale)%> </button>&nbsp;&nbsp;
<!--电话--><!--电&nbsp;&nbsp;话-->
</div>
<br/>
<div style="margin-top:5px; backgroundColor: '#CCCCCC';"  align="center" >
 <table cellSpacing="1" cellPadding="2" width="98%"  bgcolor="#CCCCCC" class="table-popup">
 			<tr style="background:F7F7F7;">
					<td colspan="4" align="center"><font size="4" face="微软雅黑"><b><%=ques_title%></b></font></td>
			</tr>
			
			<tr style="background:F7F7F7;">
					<td width="15%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ：</font></td><!--任务标题-->
					<td width="35%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=serviceTitle%></font>  </td>
					<td width="15%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerName",clientLocale)%> ：</font></td><!--客户名称-->
					<td width="35%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=cust_name%></font>  </td>
			</tr>
			
			<tr style="background:F7F7F7;">
				 <td colspan="4">						
<%
//声明字段
Iterator iterator = rsList_topic.iterator();
Iterator iterator2 = null;
Integer s_topic_id = new Integer(0);
Integer s_topic_serial_no = new Integer(0);
String s_topic_type_name = "";
String s_topic_type = "";
String s_topic_content ="";
String temp="";
Integer s_serial_no = new Integer(0);
String s_topic_value_no = "";
String s_topic_value = "";
Integer s_score = new Integer(0);

while(iterator.hasNext()){
	iCount++;
	map_topic = (Map)iterator.next();
	
	s_topic_id = Utility.parseInt(Utility.trimNull(map_topic.get("TOPIC_ID")),new Integer(0));
	s_topic_serial_no = Utility.parseInt(Utility.trimNull(map_topic.get("TOPIC_SERIAL_NO")),new Integer(0));
	s_topic_type = Utility.trimNull(map_topic.get("TOPIC_TYPE"));
	s_topic_type_name = Argument.getDictParamName(0,s_topic_type);
	s_topic_content  = Utility.trimNull(map_topic.get("TOPIC_CONTENT"));
	
	temp = "("+s_topic_serial_no +")"+ "." + s_topic_content;
%>
<div style="margin-top:10px;margin-left:20px;width:100%;" >
		<table cellSpacing="0" cellPadding="0" width="100%" class="table-popup">
				<tr>
					<td>
						<font size="2" face="微软雅黑"><%=temp%></font>
						<input type="hidden" name="topic_id" value="<%=s_topic_id%>"/>
						<input type="hidden" name="topic_value" id="topic_value_<%=s_topic_id%>" value=""/>
					</td>
					<%if(s_topic_type.equals("305102")){%>
						<td>
							<div style="margin-right:12px" align="right">
								<select onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setTopicValue(<%=s_topic_id%>,this.value);" style="width:170px">
									<%=Argument.getTopicValue(s_topic_id)%>
								</select>
							</div>
						</td>					
					<%}
					else if(s_topic_type.equals("305103")){%>
						<td>
							<div style="margin-right:12px" align="right">
								<select onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setTopicValue(<%=s_topic_id%>,this.value);" style="width:170px">
									<%=Argument.getDictParamOptions(3052,"")%>
								</select>
							</div>
						</td>	
					<%}%>
				</tr>	
		</table>		
</div>
<%
if(s_topic_type.equals("305102")){	
%>
<div style="margin-left:20px;margin-top:10px; width:100%;" align="center">		
		<table cellSpacing="1" cellPadding="2" width="90%">
			<tr>	
<%		
			vo_ques = new QuestionnaireVO();	  
			vo_ques.setTopic_id(s_topic_id);
			rsList_topicValue = local_ques.queryTTopicScroe(vo_ques);
			
			if(rsList_topicValue.size()>0){
				iterator2 = rsList_topicValue.iterator();	
				iCount2 = 0;
				temp = "";
				
				while(iterator2.hasNext()){
					map_topicValue = (Map)iterator2.next();
					iCount2++;
							
					s_serial_no = Utility.parseInt(Utility.trimNull(map_topicValue.get("SERIAL_NO")),new Integer(0));
					s_topic_value_no = Utility.trimNull(map_topicValue.get("TOPIC_VALUE_NO"));
					s_topic_value = Utility.trimNull(map_topicValue.get("TOPIC_VALUE"));
					s_score = Utility.parseInt(Utility.trimNull(map_topicValue.get("Score")),new Integer(0));
					
					temp=  s_topic_value_no +"." + s_topic_value;
%>		
					<td width="80px"><font size="2" face="微软雅黑"><%=temp%></font></td>		
<%}
}
%>
			</tr>		
	</table>
</div>
<%
} 
else if(s_topic_type.equals("305103")){
%>
<div style="margin-left:20px;margin-top:10px; width:100%;" align="center">		
		<table cellSpacing="1" cellPadding="2" width="100%" class="class="table-popup"">
			<tr>	
				<td width="80px"><font size="2" face="微软雅黑">1.<%=LocalUtilis.language("message.verySatisfied",clientLocale)%> </font></td><!--非常满意-->	
				<td width="80px"><font size="2" face="微软雅黑">2.<%=LocalUtilis.language("message.satisfied",clientLocale)%> </font></td><!--满意-->	
				<td width="80px"><font size="2" face="微软雅黑">3.<%=LocalUtilis.language("message.justSoSo",clientLocale)%> </font></td>	<!--一般-->
				<td width="80px"><font size="2" face="微软雅黑">4.<%=LocalUtilis.language("message.unsatisfied",clientLocale)%> </font></td><!--不满意-->	
			</tr>		
	</table>
</div>
<%
}else  if(s_topic_type.equals("305101")){%>
		<div style="margin-left:'17px'; width:100%; margin-top:'10px'; margin-right:7px;" align="left">		
			<textarea rows="3"  style="width:100%;" onblur="javascript:setTopicValue(<%=s_topic_id%>,this.value);"></textarea>	
		</div>	
<%}
}
%>								
				</td>
			<tr>	
			
			<tr style="background:F7F7F7;">
				<td colspan="4"><font size="3" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.surveyRecords",clientLocale)%> </b></font></td><!--调查记录-->
			</tr>	
			<tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.nearExecuteDate",clientLocale)%> ：</font></td><!--最近联系日期-->
				 <td colspan="3">
				 		<input type="text" name="execute_date" id="execute_date" size="30" onclick="javascript:setday(this);"  value="" readOnly/> 					 
				 </td>
			 </tr>
			<tr style="background:F7F7F7;">
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("message.serviceWay",clientLocale)%> ：</font></td><!--实际联系方式-->
					 <td colspan="3">
							<select onkeydown="javascript:nextKeyPress(this)" style="width:170px" name="service_way">
								<%=Argument.getDictParamOptions(1109,"")%>
							</select>
					 </td>
			</tr>		
			
			<tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("message.deal_result",clientLocale)%> ：</font></td><!--处理结果-->
				 <td colspan="3">
						<input type="radio" value="1" name="feedback"  onclick="javascript:document.getElementById('service_status').value=3;"/><%=LocalUtilis.language("class.feedback",clientLocale)%> &nbsp;&nbsp;
						<!--反馈-->
						<input type="radio" value="2" name="feedback" checked onclick="javascript:document.getElementById('service_status').value=4;"/><%=LocalUtilis.language("class.noFeedback",clientLocale)%> &nbsp;&nbsp;
						<!--不反馈-->
						<input type="radio" value="2" name="feedback" onclick="javascript:document.getElementById('service_status').value=9;"/><%=LocalUtilis.language("class.invalid",clientLocale)%> &nbsp;&nbsp;
						<!--作废-->
						<input type="hidden" value="4" name="service_status" id="service_status"/>
				 </td>
			 </tr>
				  
		  	<tr style="background:F7F7F7;">
				<td valign="top"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ：</font></td><!--处理结果描述-->
				<td colspan="3">
					<textarea rows="8" name="deal_result" onkeydown="javascript:nextKeyPress(this)" style="width:100%"></textarea>			
				</td>
			</tr>		
 	</table>
</div>

<div align="right" style="margin-top:5px; margin-right:5px;">	
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	<!--关闭-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>