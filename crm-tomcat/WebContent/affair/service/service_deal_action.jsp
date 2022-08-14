<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递变量
String serviceTitle = Utility.trimNull(request.getParameter("serviceTitle"));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer serial_no_detail = Utility.parseInt(request.getParameter("serial_no_detail"), new Integer(0));
Integer serviceType = Utility.parseInt(request.getParameter("serviceType"), null);

//声明辅助变量
boolean bSuccess = false;
List rsList = null;
Map map = null;

//声明字段变量
String cust_name = "";
String service_type_name = "";
String executeTime = "";
String result = "";
Integer service_status = new Integer(0);
Integer needFeedback = new Integer(0);
Integer satifaction = new Integer(0);
Integer service_way = new Integer(0);
Integer target_custid = new Integer(0);

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();

if(request.getMethod().equals("POST")){
	ServiceTaskVO vo_treat = new ServiceTaskVO();
	
	service_way = Utility.parseInt(Utility.trimNull(request.getParameter("service_way")), new Integer(0));	
	service_status = Utility.parseInt(Utility.trimNull(request.getParameter("service_status")), new Integer(0));	
	result = Utility.trimNull(request.getParameter("deal_result"));
	satifaction = Utility.parseInt(Utility.trimNull(request.getParameter("Satifaction")), new Integer(0));
	Integer r_needFeedback = Utility.parseInt(Utility.trimNull(request.getParameter("feedback")), new Integer(0));	
	String r_executeTime = Utility.trimNull(request.getParameter("execute_date"));
	
	vo_treat.setSerial_no(serial_no_detail);
	vo_treat.setServiceWay(service_way.toString());
	vo_treat.setExecutorTime(r_executeTime);
	vo_treat.setStatus(service_status);
	vo_treat.setResult(result);
	vo_treat.setNeedFeedBack(r_needFeedback);
	vo_treat.setSatisfaction(satifaction);
	vo_treat.setInputMan(input_operatorCode);
	
	serviceTask.treat_details(vo_treat);
	bSuccess=true;
}

//获得数据
ServiceTaskVO vo_details = new ServiceTaskVO();

vo_details.setSerial_no(serial_no_detail);
vo_details.setInputMan(input_operatorCode);
rsList = serviceTask.query_details(vo_details);

if(rsList.size()>0){
	map = (Map)rsList.get(0);
	
	cust_name = Utility.trimNull(map.get("CUST_NAME")); 
	service_type_name = Utility.trimNull(map.get("ServiceTypeName"));
	executeTime = Utility.trimNull(map.get("ExecuteTime"),Utility.timeSpanToString(Utility.getCurrentTimestamp()));
	service_status = Utility.parseInt(map.get("Status").toString(), new Integer(0));	
	result =  Utility.trimNull(map.get("Result"));	
	needFeedback = Utility.parseInt(Utility.trimNull(map.get("NeedFeedback")), new Integer(0));
	service_way = Utility.parseInt(Utility.trimNull(map.get("ServiceWay")), new Integer(0));	
	satifaction = Utility.parseInt(Utility.trimNull(map.get("Satifaction")), new Integer(0));
	target_custid = Utility.parseInt(Utility.trimNull(map.get("TargetCustID")), new Integer(0));
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.taskProcessingSettings",clientLocale)%> </title><!-- 任务处理设置 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;	
	if(v_bSuccess=="true"){	
		sl_update_ok();		
		//window.parent.document.getElementById("closeImg").click();		
		//window.parent.location.reload();
		//window.close();
		var url = "<%=request.getContextPath()%>/affair/service/service_deal_add.jsp?serial_no=<%=serial_no%>&serviceType=<%=serviceType%>";
		window.location.href=url;
	}
}

/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="<%=request.getContextPath()%>/affair/service/service_deal_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}


/*验证数据*/
function validateForm(form){
	//if(!sl_check(document.getElementsByName('executor')[0],"任务标题",60,1)){ return false;}
	if(!sl_check(document.getElementById('execute_date'), "<%=LocalUtilis.language("class.nearExecuteDate",clientLocale)%> ", 20, 1))return false;//最近联系日期
	if(document.getElementsByName("service_status")[0].value==9){
		var deal_result = document.getElementsByName("deal_result")[0].value;
		if(deal_result==""){
			sl_alert("作废、请一定在备注中写明详细原因！");//作废、请一定在备注中写明详细原因
			return false;
		}
	}
			
	return sl_check_update();		
}

/*改变联系方式*/
function changeServiceWay(flag){	
	var serial_no_detail = document.getElementById("serial_no_detail").value;
	var target_custid = document.getElementById("target_custid").value;
	var cust_name = document.getElementById("cust_name").value;

	if(flag==1){		
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsTele",clientLocale)%> ";//电  话		
		document.getElementById("service_way").value="110901";	
		<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
		//var url = "/affair/sms/cust_tel.jsp?target_custid="+target_custid;
		//showModalDialog(url,'','dialogWidth:420px;dialogHeight:300px;status:0;help:0');		
			document.parentWindow.parent.parent.document.getElementById("target_custid").value = target_custid; 
			document.parentWindow.parent.parent.document.getElementById("callTalkType").value = 1;
			document.parentWindow.parent.parent.document.getElementById("callcenterLink").onclick();	
		<%}%>
	}
	if(flag==2){		
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsPost",clientLocale)%> ";//邮  寄		
		document.getElementById("service_way").value="110902";	
	}
	if(flag==3){				
		document.getElementById("service_way_name").value="Email";		
		document.getElementById("service_way").value="110903";	
		
		var url = "<%=request.getContextPath()%>/marketing/mail/sendmail_cust.jsp?target_custid="+target_custid+"&to_name="+cust_name;
		showModalDialog(url,'','dialogWidth:900px;dialogHeight:500px;status:0;help:0');
	}
	if(flag==4){		
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsFax",clientLocale)%> ";//传  真
		document.getElementById("service_way").value="110904";	
	}
	if(flag==5){					
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsSms",clientLocale)%> ";//短  信
		document.getElementById("service_way").value="110905";	
		
		<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
		var url = "<%=request.getContextPath()%>/affair/sms/sendMes2.jsp?serialNoDetail_Message="+serial_no_detail;
		var ret = showModalDialog(url,'','dialogWidth:600px;dialogHeight:500px;status:0;help:0');
		document.theform.deal_result.value = ret;
		<%}%>
	}
}
function showInfo(custid){
	var url = '<%=request.getContextPath()%>/affair/service/service_deal_cust_detail.jsp?showflag=1&cust_id='+custid;
	showModelessDialog(url,window,'dialogWidth:700px;dialogHeight:500px;dialogLeft:500px;status:0;help:0');
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="target_custid" name="target_custid" value="<%=target_custid%>"/>
<input type="hidden" id="serial_no_detail" name="serial_no_detail" value="<%=serial_no_detail%>"/>
<input type="hidden" id="serviceTitle" name="serviceTitle" value="<%=serviceTitle%>"/>
<input type="hidden" id="cust_name" name="cust_name" value="<%=cust_name%>"/>
<!--  遮蔽页面优化
<div align="left">
	<img border="0" src="/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b>任务管理>>任务处理>>任务明细处理</b></font>
</div>
<hr noshade color="#808080" size="1" width="98%">
-->
<div>
<table style="width: 100%"><tr>
                        <!-- 任务处理 -->
			 			<td colspan="2" class="page-title" align="left"><b><%=LocalUtilis.language("class.serviceProcessing",clientLocale)%></b></td>
			 	</tr></table>
			 	<br/>
	<table  border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
				
			 	
			 	<tr >	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ：</font></td><!-- 任务标题 -->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=serviceTitle%></font>  </td>
				 </tr>
				 	
				<tr >	
					 <td width="20%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerName",clientLocale)%> ：</font></td><!-- 客户名称 -->
					 <td>&nbsp;&nbsp;<a title="<%=LocalUtilis.language("message.viewCustomerInfo",clientLocale)%> " 
				 		href="javascript:showInfo('<%=target_custid%>');" class="a2" ><font size="2" face="微软雅黑" color="red"><%=cust_name%></font></a></td>
				 </tr>
					 
				 <tr >	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.nearExecuteDate",clientLocale)%> ：</font></td><!-- 最近联系日期 -->
					 <td>
					 	<input type="text" name="execute_date" id="execute_date" size="30" onclick="javascript:setday(this);"  value="<%= executeTime%>" readOnly/> 					 
					 </td>
				 </tr>
				 
				 <tr >	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font></td><!-- 联系方式 -->
					 <td>
					 		<!-- 电话 --><!-- 电&nbsp;&nbsp;话 --> 
                            <button type="button" class="xpbutton3" id="queryButton1" name="queryButton1" title="<%=LocalUtilis.language("class.tele",clientLocale)%> " onclick="javascript:changeServiceWay(1);" ><%=LocalUtilis.language("message.telephone",clientLocale)%> </button>&nbsp;&nbsp;
							<!-- 邮寄 --><!-- 邮&nbsp;&nbsp;寄 -->
                            <button type="button" class="xpbutton3" id="queryButton2" name="queryButton2" title="<%=LocalUtilis.language("class.post",clientLocale)%> " onclick="javascript:changeServiceWay(2);" ><%=LocalUtilis.language("message.post",clientLocale)%> </button>&nbsp;&nbsp;
							<button type="button" class="xpbutton3" id="queryButton3" name="queryButton3" title="Email" onclick="javascript:changeServiceWay(3);">Email</button>&nbsp;&nbsp;
							<!-- 传真 --><!-- 传&nbsp;&nbsp;真 -->
                            <button type="button" class="xpbutton3" id="queryButton4" name="queryButton4" title="<%=LocalUtilis.language("class.fax",clientLocale)%> " onclick="javascript:changeServiceWay(4);" ><%=LocalUtilis.language("message.fax",clientLocale)%> </button>&nbsp;&nbsp;
							<!-- 短信 --><!-- 短&nbsp;&nbsp;信 -->
                            <button type="button" class="xpbutton3" id="queryButton5" name="queryButton5" title="<%=LocalUtilis.language("class.sms",clientLocale)%> " onclick="javascript:changeServiceWay(5);" ><%=LocalUtilis.language("message.sms",clientLocale)%> </button>&nbsp;&nbsp;					 
					 </td>
				 </tr>
					 
				  <tr >	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("message.serviceWay",clientLocale)%> ：</font></td><!-- 实际联系方式 -->
					 <td>
						<input readonly class="edline"  name="service_way_name" id="service_way_name"size="30" value="<%=Utility.trimNull(Argument.getDictParamName(1109,service_way.toString()))%>" onkeydown="javascript:nextKeyPress(this)"/>
						<input type="hidden" name="service_way" id="service_way" value="<%= service_way%>" />	
					 </td>
				 </tr>
					 
				  <tr >	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("message.deal_result",clientLocale)%> ：</font></td><!-- 处理结果 -->
					 <td>
						<input type="radio" value="1" name="feedback" <%if(needFeedback.intValue()==1){out.print("checked");}%> onclick="javascript:document.getElementById('service_status').value=3;"/><%=LocalUtilis.language("class.feedback",clientLocale)%> &nbsp;&nbsp;<!-- 反馈 -->
						<input type="radio" value="2" name="feedback" <%if(needFeedback.intValue()==2){out.print("checked");}%> onclick="javascript:document.getElementById('service_status').value=4;"/><%=LocalUtilis.language("class.noFeedback",clientLocale)%> &nbsp;&nbsp;<!-- 不反馈 -->
						<input type="radio" value="2" name="feedback" onclick="javascript:document.getElementById('service_status').value=9;"/><%=LocalUtilis.language("class.invalid",clientLocale)%> &nbsp;&nbsp;<!-- 作废 -->
						<input type="hidden" value="<%if(needFeedback.intValue()==1){out.print("3");}else if(needFeedback.intValue()==2){out.print("4");}%>" name="service_status" id="service_status"/>
				 	</td>
				 </tr>
					  
			  	<tr >
					<!-- 处理结果描述 -->
                    <td valign="top"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ：</font></td>
					<td>
						<textarea rows="8" name="deal_result" onkeydown="javascript:nextKeyPress(this)" style="width:100%"><%= result%></textarea>			
					</td>
				</tr>				
		</table>
</div>

<div align="right" style="margin-right:10px; margin-top:10px" >	
	<!-- 保存 -->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;
	<!-- 
	<button type="button" class="xpbutton3" id="btnCancel" name="btnCancel" onclick="javascript:window.close();">关闭</button>
	 -->
</div>		
<% serviceTask.remove();%>
</form>
</BODY>
</HTML>
