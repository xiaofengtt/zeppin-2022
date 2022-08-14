<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面传递参数
Integer serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//活动主键

//声明字段
Integer manage_code = new Integer(0);
BigDecimal active_fee = new BigDecimal(0.00);
String active_type ="";
String active_type_name ="";
String active_theme ="";
String beginDate ="";
String endDate ="";
String customer_type ="";
String active_plan ="";
String active_code= "";
Integer active_flag = new Integer(0);
String active_flag_name = "";
String active_trace = "";
String completeTime = "";
Integer creator = new Integer(0);

//声明辅助变量
boolean bSuccess = false;
List activeList = null;
Map activeMap = null;

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();

if(serial_no.intValue()>0){
	//获得活动信息
	activeList = activityLocal.load(serial_no);
	if(activeList.size()>0){
		activeMap = (Map)activeList.get(0);
	}	
	
	if(activeMap!=null){
		active_type_name = Utility.trimNull(activeMap.get("ACTIVE_TYPE_NAME"));
		active_type = Utility.trimNull(activeMap.get("ACTIVE_TYPE"));
		active_theme = Utility.trimNull(activeMap.get("ACTIVE_THEME"));
		beginDate = Utility.trimNull(activeMap.get("START_DATE"));
		endDate = Utility.trimNull(activeMap.get("END_DATE"));
		manage_code = Utility.parseInt(Utility.trimNull(activeMap.get("MANAGE_CODE")),new Integer(0));
		customer_type  = Utility.trimNull(activeMap.get("CUSTOMER_TYPE"));
		active_plan = Utility.trimNull(activeMap.get("ACTIVE_PLAN"));
		active_code = Utility.trimNull(activeMap.get("ACTIVE_CODE"));
		active_trace = Utility.trimNull(activeMap.get("ACTIVE_TRACE"));
		completeTime = Utility.trimNull(activeMap.get("COMPLETE_TIME"));
		creator = Utility.parseInt(Utility.trimNull(activeMap.get("CREATOR")),new Integer(0));
		active_flag =  Utility.parseInt(Utility.trimNull(activeMap.get("ACTIVE_FLAG")),new Integer(0));
		active_flag_name = Argument.getActivityFlagName(active_flag);
		active_fee = Utility.parseDecimal( Utility.trimNull(activeMap.get("ACTIVITY_FEE")),new BigDecimal(0.00),2,"1");
	}	
	
	//保存修改
	if(request.getMethod().equals("POST")){
		completeTime = Utility.trimNull(request.getParameter("completeTime"));
		active_trace = Utility.trimNull(request.getParameter("active_trace"));
		active_flag = new Integer(3);
		
		ActivityVO vo = new ActivityVO();
		vo.setSerial_no(serial_no);
		vo.setManage_code(manage_code);
		vo.setActive_type(active_type);
		vo.setActive_theme(active_theme);
		vo.setActive_start_date(beginDate);
		vo.setActive_end_date(endDate);
		vo.setCustomer_type(customer_type);
		vo.setActive_plan(active_plan);
		vo.setActive_flag(active_flag);//1为新建活动
		vo.setCreator(creator);
		vo.setCompleteTime(completeTime);
		vo.setActive_trace(active_trace);
		vo.setInput_man(input_operatorCode);
		
		activityLocal.modi(vo);	
		bSuccess = true;
	}
}

if(completeTime.length()>0){
	completeTime = completeTime.substring(0,16);
}

if(beginDate.length()>0){
	beginDate = beginDate.substring(0,16);
}

if(endDate.length()>0){
	endDate = endDate.substring(0,16);
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<!--活动任务审核设置-->
<title><%=LocalUtilis.language("menu.activityEndAction",clientLocale)%> </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>

<script language="javascript">
/*启动加载*/	
window.onload = function(){
	document.getElementById("table2").style.height =document.getElementById("table1").clientHeight;
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_serialNo = document.getElementById("serial_no").value;
	
	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.doneSuccess",clientLocale)%> ！");	//处理成功
		if(confirm("<%=LocalUtilis.language("message.ifAddFeeDetails",clientLocale)%> ？")){//您是否要添加本次活动的费用明细
			window.location.href = "activity_fee_action.jsp?actionFlag=1&transFlag=2&q_activitySerialNo="+v_serialNo;	
		}
		else{
			CancelAction();
		}
	}
}	

/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "activity_end_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}

/*验证数据*/
function validateForm(form){
	if(!sl_check(document.getElementById('completeTime'), "<%=LocalUtilis.language("class.completeTime",clientLocale)%> ", 20, 1))return false;//完成时间
	if(!sl_check(document.theform.active_trace, "<%=LocalUtilis.language("class.activeTrace",clientLocale)%> ",800,1)){return false;}	//活动总结
			
	return sl_check_update();		
}

/*返回*/
function CancelAction(){
	var url = "activity_end.jsp";	
	window.location.href = url;	
}
</script>

</HEAD>
<BODY class="body body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.activeTrace",clientLocale)%> </b></font><!--活动任务总结-->
</div>
<br/>
<div style="position:absolute;float:left;width:100%;" class="table-popup">
	<div style="float:left; width:40%; ">
			  <p>
			 <table  id="table1" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC" class="product-list">
			 	<tr style="background:F7F7F7;">
			 			<td colspan="2" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeInfo",clientLocale)%> </b></font></td><!--活动信息-->
			 	</tr>
				 <tr style="background:F7F7F7;">	
					 <td width="40%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCode",clientLocale)%> ：</font></td><!--活动编号-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_code%></font>  </td>
				 </tr>
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ：</font></td><!--活动主题-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_theme%></font> </td>
				 </tr>
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ：</font></td><!--活动类别-->
					 <td> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_type_name%></font></td>
				 </tr>
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.manageCode",clientLocale)%> ：</font></td><!--活动负责人-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(manage_code)%></font> </td>
				 </tr>
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeFee2",clientLocale)%> ：</font></td><!--活动花费-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=active_fee%></font> </td>
				 </tr>
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.beginDate2",clientLocale)%> ：</font></td><!--活动起始时间-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= beginDate%></font></td>
				 </tr>
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate2",clientLocale)%> ：</font></td><!--活动结束时间-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= endDate%></font></td>
				 </tr>
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeFlagName2",clientLocale)%> ：</font></td><!--活动进行状态-->
					 <td> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_flag_name%></font></td>
				 </tr>
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ：</font></td><!--对象客户群-->
					 <td> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= customer_type%></font></td>
				 </tr>
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ：</font></td><!--活动计划和目标-->
					 <td> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_plan%></font></td>
				 </tr>
			</table>
	</div>
	
	<div style=" float:right; width:60%; ">
				<table  id="table2" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC" class="product-list">
					<tr style="background:F7F7F7;">
					        <!--活动完成备注-->
				 			<td colspan="2" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeCompleteComment",clientLocale)%> </b></font></td>
				 	</tr>
				 	<tr style="background:F7F7F7;">	
						 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ：</font></td><!--完成时间-->
						 <td><input type="text" name="completeTime" id="completeTime" size="30" value="<%= completeTime%>" readOnly/> </td>
					 </tr>
					 <script language="javascript">
						laydate({elem:'#completeTime',format:'YYYY-MM-DD hh:mm:ss',istime:true});
					</script>
					 <tr style="background:F7F7F7;">	
						 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeTrace",clientLocale)%> ：</font></td><!--活动总结-->
						 <td><textarea rows="8" name="active_trace" onkeydown="javascript:nextKeyPress(this)" cols="80"><%= active_trace%></textarea>	</td>
					 </tr>
					 <tr style="background:F7F7F7;">
				 			<td colspan="2" align="right">	
			                    <!--保存-->
				 				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<!--返回-->
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
				 	</tr>				 	
				</table>
				<br>
	</div>
</div>

</form>
</BODY>
</HTML>