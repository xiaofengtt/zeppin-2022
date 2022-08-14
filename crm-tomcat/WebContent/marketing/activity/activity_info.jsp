<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面传递参数
Integer serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//活动主键

//----活动信息
Integer manage_code = new Integer(0);
Integer active_flag = new Integer(0);
String active_flag_name = "";
BigDecimal active_fee = new BigDecimal(0.00);
String active_type ="";
String active_type_name ="";
String active_theme ="";
String beginDate ="";
String endDate ="";
String customer_type ="";
String active_plan ="";
String active_code= "";
String creatorName = "";
String active_completeTime ="";
String active_trace = "";

//声明辅助变量
int iCount = 0;
int iCount2 = 0;
String[] totalColumn = new String[0];
List activityList = null;
List activityFeeList = null;
List activityTaskList = null;
Map activityMap = null;
Map activityFeeMap = null;
Map activityTaskMap = null;

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityFeeLocal activityFeeLocal = EJBFactory.getActivityFee();
ActivityTaskVO vo_task = new ActivityTaskVO();
ActivityFeeVO vo_fee = new ActivityFeeVO();

//获取活动信息
if(serial_no.intValue()>0){	
	activityList = activityLocal.load(serial_no);
	if(activityList.size()>0){
		activityMap = (Map)activityList.get(0);
	}	
	
	if(activityMap!=null){
		active_type_name = Utility.trimNull(activityMap.get("ACTIVE_TYPE_NAME"));
		active_type = Utility.trimNull(activityMap.get("ACTIVE_TYPE"));
		active_theme = Utility.trimNull(activityMap.get("ACTIVE_THEME"));
		beginDate = Utility.trimNull(activityMap.get("START_DATE"));
		endDate = Utility.trimNull(activityMap.get("END_DATE"));
		manage_code = Utility.parseInt(Utility.trimNull(activityMap.get("MANAGE_CODE")),new Integer(0));
		customer_type  = Utility.trimNull(activityMap.get("CUSTOMER_TYPE"));
		active_plan = Utility.trimNull(activityMap.get("ACTIVE_PLAN"));
		active_code = Utility.trimNull(activityMap.get("ACTIVE_CODE"));
		active_flag =  Utility.parseInt(Utility.trimNull(activityMap.get("ACTIVE_FLAG")),new Integer(0));
		active_flag_name = Argument.getActivityFlagName(active_flag);
		creatorName = Utility.trimNull(activityMap.get("CREATOR_NAME"));
		active_completeTime = Utility.trimNull(activityMap.get("COMPLETE_TIME"));
		active_fee = Utility.parseDecimal( Utility.trimNull(activityMap.get("ACTIVITY_FEE")),new BigDecimal(0.00),2,"1");
		active_trace =  Utility.trimNull(activityMap.get("ACTIVE_TRACE"));
	}	
	
	vo_task.setActivitySerial_no(serial_no);
	vo_fee.setActive_serial_no(serial_no);
	
	activityTaskList = activityTaskLocal.query(vo_task);
	activityFeeList = activityFeeLocal.query(vo_fee);
}

if(active_completeTime.length()>0){
	active_completeTime = active_completeTime.substring(0,16);
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
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title><%=LocalUtilis.language("menu.activityInfo",clientLocale)%> </title>
<!--活动信息综合查询-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<base target="_self">
<style>
.headdarkbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}

.headbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}
</style>
<script language=javascript>
/*启动加载*/	
window.onload = function(){
	show(1);
}

function show(parm){
   for(i=1;i<4;i++) {  
	     if(i!= parm){	     	
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");		   
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display = ''");
		 } 
	}
}

/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "taskTr"+serial_no;
	var v_table = "taskTablePro"+serial_no;
	var v_flag = "taskFlag_display"+serial_no;
	var v_div = "taskDiv"+serial_no;
	var v_image = "taskImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<div>
	<table id="titleNameTable" border="0" width="95%" cellspacing="1" cellpadding=0>
			<tr>
				<td class="page-title page-title-noborder">
					<b><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_theme%></font></b>
				</td>
			</tr>
	</table>
</div>
<br/>
<div align="center">
		<TABLE cellSpacing=0 cellPadding=0 width="95%" border="0" class="edline" class="page-title-select">
					<TBODY>
						<TR>
							<TD vAlign=top>&nbsp;</TD>							
							<TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
							      &nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.activeInfo",clientLocale)%> </TD><!--活动信息-->
							<TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=90 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
							      &nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.taskInfo",clientLocale)%> </TD><!--相关任务信息-->
							<TD id="d3" style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=90 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
							      &nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.activeFeeInfo",clientLocale)%> </TD><!--活动费用明细-->
						</TR>
				</TBODY>
		</TABLE>
</div>

<div id="r1" align="center" style="display:none;">
<br>
<table  id="table1" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
			 	<tr style="background:F7F7F7;">
			 			<td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeInfo",clientLocale)%> </b></font></td><!--活动信息->
			 	</tr>
				 <tr style="background:F7F7F7;">	
					 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCode",clientLocale)%> ：</font></td><!--活动编号-->
					 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_code%></font>  </td>
					 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ：</font></td><!--活动主题-->
					 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_theme%></font> </td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeFlagName",clientLocale)%> ：</font></td><!--活动状态-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_flag_name%></font> </td>
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.manageCode",clientLocale)%> ：</font></td><!--活动类别-->
					 <td> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_type_name%></font></td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.manageCode",clientLocale)%> ：</font></td><!--活动负责人-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(manage_code)%></font> </td>
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.creatorName",clientLocale)%> ：</font></td><!--活动创建人-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=creatorName%></font> </td>
				 </tr>

				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.startTime",clientLocale)%> ：</font></td><!--开始时间-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= beginDate%></font></td>
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.endTime",clientLocale)%> ：</font></td>,<!--结束时间-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= endDate%></font></td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ：</font></td><!--完成时间-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_completeTime%></font></td>
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeFee2",clientLocale)%> ：</font></td><!--活动花费-->
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_fee%></font></td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ：</font></td><!--对象客户群-->
					 <td colspan="3"> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= customer_type%></font></td>
				 </tr>
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ：</font></td><!--活动计划和目标-->
					 <td colspan="3"> <font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_plan%></font></td>
				 </tr>

				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeTrace",clientLocale)%> ：</font></td><!--活动总结-->
					 <td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= active_trace%></font></td>
				 </tr>
</table>
<br>
</div>

<div id="r2" style="display:none" align="center">
			<br>
			<table border="0"  width="95%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr style="background:6699FF;">
					<td align="center" width="25%"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> </td><!--任务标题-->
					<td align="center" width="25%"><%=LocalUtilis.language("class.taskFlagName",clientLocale)%> </td><!--任务状态-->
					<td align="center" width="25%"><%=LocalUtilis.language("class.executor",clientLocale)%> </td><!--执行人-->
					<td align="center" width="25%"><%=LocalUtilis.language("class.taskContent2",clientLocale)%> </td><!--详细内容-->
				</tr>
<%
if(activityTaskList!=null){
	Iterator taskIterator = activityTaskList.iterator();
	Integer taskSerialNo = new Integer(0);
	Integer taskFlag = new Integer(0);
	String taskFlagName = "";
	String taskTitle = "";
	Integer taskExecutor = new Integer(0);
	String taskExecutorName = "";
	String taskBeginDate = "";
	String taskEndDate = "";
	String taskContent = "";
	String taskCompleteTime ="";
	String taskRemark = "";
	Integer taskCheckMan= new Integer(0);
	String taskCheckOptions = "";	
	
	while(taskIterator.hasNext()){
		iCount++;
		activityTaskMap = (Map)taskIterator.next();

		taskSerialNo =  Utility.parseInt(Utility.trimNull(activityTaskMap.get("Serial_no")),new Integer(0));
		taskFlag =  Utility.parseInt(Utility.trimNull(activityTaskMap.get("TaskFlag")),new Integer(0));
		taskFlagName = Argument.getActivityTaskFlagName(taskFlag);
		taskTitle = Utility.trimNull(activityTaskMap.get("ActiveTaskTitle"));
		taskExecutor = Utility.parseInt(Utility.trimNull(activityTaskMap.get("Executor")),new Integer(0));
		taskExecutorName = Argument.getOpNameByOpCode(taskExecutor);
		taskBeginDate  = Utility.trimNull(activityTaskMap.get("BeginDate"));
		taskEndDate = Utility.trimNull(activityTaskMap.get("EndDate"));
		taskContent =  Utility.trimNull(activityTaskMap.get("Content"));
		taskCompleteTime =  Utility.trimNull(activityTaskMap.get("CompleteTime"));
		taskRemark = Utility.trimNull(activityTaskMap.get("Remark"));
		taskCheckMan = Utility.parseInt(Utility.trimNull(activityTaskMap.get("CheckMan")),new Integer(0));
		taskCheckOptions  = Utility.trimNull(activityTaskMap.get("Check_Options"));
		
		if(taskCompleteTime.length()>0){
			taskCompleteTime = taskCompleteTime.substring(0,16);
		}
		
		if(taskBeginDate.length()>0){
			taskBeginDate = taskBeginDate.substring(0,16);
		}
		
		if(taskEndDate.length()>0){
			taskEndDate = taskEndDate.substring(0,16);
		}		
		
%>
			<tr style="background:F7F7F7;">
				<td align="center" ><%= taskTitle%></td>      
				<td align="center" ><%= taskFlagName%></td> 	
				<td align="center" ><%= taskExecutorName%></td> 
		        <td align="center">
		         	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=taskSerialNo%>);">
		         		<IMG id="taskImage<%=taskSerialNo%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
		         	</button>
		         	<input type="hidden" id="taskFlag_display<%=taskSerialNo%>" name="taskFlag_display<%=taskSerialNo%>" value="0">
		         </td>   
			</tr>
			<tr id="taskTr<%=taskSerialNo%>" style="display: none">
				<td align="center" bgcolor="#FFFFFF" colspan="5" >
					<div id="taskDiv<%=taskSerialNo%>" style="overflow-y:auto;" align="center">
						<table id="taskTablePro<%=taskSerialNo%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
							<tr style="background:F7F7F7;">
								<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.startTime",clientLocale)%> ：</td><!--开始时间-->
								<td width="25%"><%= taskBeginDate%></td>
								<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.endTime",clientLocale)%> ：</td><!--结束时间-->
								<td width="25%"><%= taskEndDate%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.taskContent",clientLocale)%> ：</td><!--任务内容-->
								<td colspan="3"><%= taskContent%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ：</td><!--完成时间-->
								<td colspan="3"><%= taskCompleteTime%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTaskRemark",clientLocale)%> ：</td><!--完成汇报-->
								<td colspan="3"><%= taskRemark%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ：</td><!--负责人-->
								<td colspan="3"><%= Argument.getOpNameByOpCode(taskCheckMan)%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> ：</td><!--审核意见-->
								<td colspan="3"><%= taskCheckOptions%></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
<%}
}if(iCount==0){
%>
	<tr style="background:F7F7F7;">
		<td align="center" colspan="5" ><%=LocalUtilis.language("message.noActiveTask",clientLocale)%> </td><!--没有相关活动任务-->    
	</tr>
<%}%>
	</table>
</div>

<div id="r3" style="display:none" align="center">
	<br>
	<table border="0"  width="95%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr style="background:6699FF;">
			<td align="center" width="20%"><%=LocalUtilis.language("class.feeItems",clientLocale)%> </td>><!--费用名目-->
			<td align="center" width="20%"><%=LocalUtilis.language("class.feeAmount2",clientLocale)%> </td><!--费用金额-->
			<td align="center" width="60%"><%=LocalUtilis.language("class.feeRemark",clientLocale)%> </td><!--费用说明-->
		</tr>
<%
if(activityFeeList!=null){	
	Iterator feeIterator = activityFeeList.iterator();
	String feeItmes = "";
	BigDecimal feeAmount = new BigDecimal(0.00);
	String feeRemark = "";
	
	while(feeIterator.hasNext()){
		iCount2++;
		activityFeeMap = (Map)feeIterator.next();
		
		feeItmes = Utility.trimNull(activityFeeMap.get("FeeItems"));	
		feeAmount = Utility.parseDecimal( Utility.trimNull(activityFeeMap.get("FeeAmount")),new BigDecimal(0.00),2,"1");
		feeRemark = Utility.trimNull(activityFeeMap.get("Remark"));	
%>		
	<tr style="background:F7F7F7;">
			<td align="center" ><%= feeItmes%></td>      
			<td align="center" ><%= feeAmount%></td>      
			<td align="center" ><%= feeRemark%></td>      
	</tr>
<%}
}
if(iCount2==0){
%>
	<tr style="background:F7F7F7;">
		<td align="center" colspan="3" ><%=LocalUtilis.language("message.noRelevanFeeInfo",clientLocale)%> </td> <!--没有相关费用信息-->   
	</tr>
<%}%>
	</table>
</div>
<%
activityLocal.remove();
activityTaskLocal.remove();
activityFeeLocal.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
