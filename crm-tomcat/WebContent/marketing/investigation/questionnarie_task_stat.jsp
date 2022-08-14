<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer quesTask_serialNo = Utility.parseInt(Utility.trimNull(request.getParameter("quesTask_serialNo")),new Integer(0));
Integer showFlag = Utility.parseInt(Utility.trimNull(request.getParameter("showFlag")),new Integer(1));
String q_topicType = "";

if(showFlag.intValue()==2){
	q_topicType ="305101";
}
else if(showFlag.intValue()==3){
	q_topicType ="305102";
}
else if(showFlag.intValue()==4){
	q_topicType ="305103";
}

//声明辅助变量
int iCount = 0;

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;
QuestionnaireLocal quesLocal = EJBFactory.getQuestionnaire();
QuestionnaireVO vo_ques = null;

//声明显示字段
String serviceTitle ="";
String serviceRemark =  "";
String serviceResult =  "";//处理备忘
Integer managerID =new Integer(0);
String managerName = "";
Integer executorID = new Integer(0);
String executorName = "";
Integer insertManID= new Integer(0);
String insertManName = "";
int serviceStatus = 0;//任务状态
String serviceStatusName = "";
Integer serviceType = new Integer(64);
String serviceTypeName = "";
String serviceWay = "";
String serviceWayName = "";
String start_date = "";
String end_date =  "";
String complete_date = "";
String Insert_date = "";
Integer product_id =new Integer(0);
String productName = "";
Integer ques_id = new Integer(0);
String quesTitle = "";
Integer customerCount = new Integer(0);//目标客户数
Integer completeCount = new Integer(0);//已完成客户数
Integer originate = new Integer(0);//任务生产方式
String originateName = "";

//编辑时显示 任务信息
if(quesTask_serialNo.intValue()>0){
	List rsList = null;
	Map rsMap = null;
	//查询主信息
	vo = new ServiceTaskVO();	
	vo.setSerial_no(quesTask_serialNo);
	vo.setServiceType(serviceType);
	vo.setInputMan(input_operatorCode);
	
	rsList = serviceTask.query(vo);	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
	}
	
	if(rsMap!=null){
		serviceTitle = Utility.trimNull(rsMap.get("ServiceTitle"));	
		serviceRemark = Utility.trimNull(rsMap.get("ServiceRemark"));
		serviceResult = Utility.trimNull(rsMap.get("Result"));
		managerID = Utility.parseInt(Utility.trimNull(rsMap.get("ManagerID")), new Integer(0));
		managerName = Argument.getOpNameByOpCode(managerID);
		executorID = Utility.parseInt(Utility.trimNull(rsMap.get("Executor")), new Integer(0));
		executorName = Argument.getOpNameByOpCode(executorID);
		insertManID = Utility.parseInt(Utility.trimNull(rsMap.get("InsertMan")), new Integer(0));
		insertManName = Argument.getOpNameByOpCode(insertManID);
		serviceStatus = Utility.parseInt(Utility.trimNull(rsMap.get("Status")),0);
		serviceStatusName= Argument.getService_status_name(serviceStatus);
		serviceType = Utility.parseInt(rsMap.get("ServiceType").toString(), new Integer(0));
		serviceTypeName = Utility.trimNull(rsMap.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(rsMap.get("ServiceWay"));
		serviceWayName= Argument.getDictParamName(1109,serviceWay);
		start_date =  Utility.trimNull(rsMap.get("StartDateTime"));	
		end_date = Utility.trimNull(rsMap.get("EndDateTime"));
		complete_date = Utility.trimNull(rsMap.get("CompleteTime"));	
		Insert_date = Utility.trimNull(rsMap.get("InsertTime"));	
		ques_id = Utility.parseInt(Utility.trimNull(rsMap.get("QUES_ID")), new Integer(0));
		product_id = Utility.parseInt(Utility.trimNull(rsMap.get("PRODUCT_ID")),new Integer(0));
		productName = Argument.getProductName(product_id);
		quesTitle = Utility.trimNull(rsMap.get("QUES_TITLE"));
		customerCount =  Utility.parseInt(Utility.trimNull(rsMap.get("CustomerCount")),new Integer(0));
		completeCount =  Utility.parseInt(Utility.trimNull(rsMap.get("CompleteCount")),new Integer(0));
		originate =  Utility.parseInt(Utility.trimNull(rsMap.get("Originate")),new Integer(0));
		
		if(originate.intValue()==1){
			originateName = enfo.crm.tools.LocalUtilis.language("message.systemGegerate",clientLocale);//系统生成
		}
		else if(originate.intValue()==2){
			originateName = enfo.crm.tools.LocalUtilis.language("message.artificiallyBuild",clientLocale);//人工建立
		}
	}
}
//设置时间格式
if(Insert_date.length()>0){
	Insert_date = Insert_date.substring(0,16);
}
if(start_date.length()>0){
	start_date = start_date.substring(0,16);
}
if(end_date.length()>0){
	end_date = end_date.substring(0,16);
}
if(complete_date.length()>0){
	complete_date = complete_date.substring(0,16);
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
<title><%=LocalUtilis.language("menu.questionnarieTaskStat",clientLocale)%> </title>
<!--调查结果统计-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
function changeStatus(showFlag){
	document.getElementById("showFlag").value=showFlag;
	 QueryAction();
}

window.onload = function(){
	var tdId = "td<%=showFlag%>";
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function QueryAction(){
	var url = "questionnarie_task_stat.jsp?1=1";
	var url = url + "&showFlag=" + document.getElementById("showFlag").value;
	var url = url + "&quesTask_serialNo=" + document.getElementById("quesTask_serialNo").value;

	window.location.href = url;
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

function reportAction(topic_id,topic_content,ques_taskId){
	var url = "questionnarie_task_stat_1.jsp?q_topicType=<%=q_topicType%>";
	var url = url + "&topic_id="+topic_id;
	var url = url + "&topic_content="+topic_content;
	var url = url + "&ques_taskId="+ques_taskId;

	showModalDialog(url,'', 'dialogWidth:550px;dialogHeight:450px;status:0;help:0');
	showWaitting(0);
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="showFlag" name="showFlag" value="<%=showFlag%>"/>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
</div>
<br/>
<div id="headDiv" style="margin-left:10px">
	<table cellSpacing="1" cellPadding="2" width="700px"  bgcolor="#CCCCCC" class="table-select">
		<tr style="background:F7F7F7;">
				<td align="center" width="*" >
					 <font size="2"  face="微软雅黑">&nbsp;<%=LocalUtilis.language("menu.questionnarieTask",clientLocale)%> ：&nbsp;&nbsp;</font><!--调查问卷任务-->
					<select name = "quesTask_serialNo" id="quesTask_serialNo" onchange="javascript:QueryAction();"  style="width:200px">
						<%=Argument.getQuesTaskOptions(quesTask_serialNo)%>
					</select>				
				</td>
				<td width="80px" align="center" id="td1" <%if (showFlag.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(1);" class="a2"><%=LocalUtilis.language("message.basicInformation",clientLocale)%> </a></font></td>
				<!--基本信息-->
				<td width="80px" align="center" id="td2" <%if (showFlag.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(2);" class="a2"><%=LocalUtilis.language("message.generalNarrationQuestion",clientLocale)%> </a></font></td>
				<!--一般叙述题-->
				<td width="80px" align="center" id="td3" <%if (showFlag.intValue()==3) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(3);" class="a2"><%=LocalUtilis.language("message.generalChoice",clientLocale)%> </a></font></td>
				<!--一般选择题-->
				<td width="80px" align="center" id="td4" <%if (showFlag.intValue()==4) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(4);" class="a2"><%=LocalUtilis.language("message.satisfactionSurvey",clientLocale)%> </a></font></td>
				<!--满意度调查-->
		</tr> 
	</table>
</div>

<%if(quesTask_serialNo.intValue()>0&&showFlag.intValue()==1){%>
<div  id="r1" align="left"  style="margin-left:10px;margin-top:10px;">
	 <table  id="table1" cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC" class="table-select">
 			<tr style="background:F7F7F7;">
		 			<td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> </b></font></td>
		 	</tr><!--任务信息主要内容-->
		 	
		   <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.originateName",clientLocale)%> ：</font></td><!--任务生成方式-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= originateName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> ：</font></td><!--任务状态-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceStatusName%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.created",clientLocale)%> ：</font></td><!--创建人-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= insertManName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.Insert_date",clientLocale)%> ：</font></td><!--创建时间-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= Insert_date%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</font></td><!--客户经理-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= managerName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font></td><!--联系方式-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceWayName%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> ：</font></td><!--开始日期-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= start_date%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate",clientLocale)%> ：</font></td><!--结束日期-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= end_date%></font>  </td>
			 </tr>
		 	
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ：</font></td><!--任务详细描述-->
				 <td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceRemark%></font>  </td>
			 </tr>
			 
	 		<tr style="background:F7F7F7;">
				<td  width="90px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!--问卷名称-->
				<td  width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= quesTitle%></font>  </td>
				
				<td width="90px"><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ：</font></td><!--产品名称-->
				<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= productName%></font>  </td>
			</tr>	
			
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.executor",clientLocale)%> ：</font></td><!--执行人-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= executorName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ：</font></td><!--完成时间-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= complete_date%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCount",clientLocale)%> ：</font></td><!--目标客户数-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= customerCount%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeCount",clientLocale)%> ：</font></td><!--完成客户数-->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= completeCount%></font>  </td>
			 </tr>
			 
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("messag.taskingMemo",clientLocale)%> ：</font></td><!--任务处理备忘-->
				 <td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceResult%></font>  </td>
			 </tr>			 
 		</table>
</div>
<%}%>

<%if(quesTask_serialNo.intValue()>0&&showFlag.intValue()>1&&ques_id.intValue()>0){%>
<%
	//声明变量
	List rsList2 = null,rsList2_detail = null;
	Map rsMap2 = null,rsMap2_detail = null;
	Iterator iterator = null;
	Iterator iterator2 = null;
	Integer topic_id = new Integer(0);
	String topic_content = "";
	Integer topic_serial_no = new Integer(0);
	vo_ques = new QuestionnaireVO();
	//取数据
	vo_ques.setQues_id(ques_id);
	vo_ques.setTopic_type(q_topicType);		
	rsList2 = quesLocal.queryQuesTopic(vo_ques);
%>
<div  id="r2" align="left"  style="margin-left:10px;margin-top:10px;">
	<table border="0"  width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="20%"><%=LocalUtilis.language("class.questionaireName",clientLocale)%> </td><!--问卷名称-->
			<td align="center" width="10%"><%=LocalUtilis.language("class.topicSerialNO",clientLocale)%> </td><!--题目序号-->
			<td align="center" width="*"><%=LocalUtilis.language("class.topicContent2",clientLocale)%> </td><!--题目名称-->
			<td align="center" width="10%"><%=LocalUtilis.language("message.taskFlagDisplay",clientLocale)%> </td><!--调查明细-->
			<td align="center" width="10%"><%=LocalUtilis.language("message.statistics",clientLocale)%> </td><!--统计-->
		</tr>
<%		
		if(rsList2.size()>0){
			iterator = rsList2.iterator();
			
			while(iterator.hasNext()){
				rsMap2 = (Map)iterator.next();
				
				topic_id = Utility.parseInt(Utility.trimNull(rsMap2.get("TOPIC_ID")),new Integer(0));
				topic_content = Utility.trimNull(rsMap2.get("TOPIC_CONTENT"));
				topic_serial_no =  Utility.parseInt(Utility.trimNull(rsMap2.get("TOPIC_SERIAL_NO")),new Integer(0));
%>
	<tr class="tr0">
		<td align="left">&nbsp;&nbsp;<%= quesTitle%></td> 
		<td align="center"><%= topic_serial_no%></td> 
		<td align="left">&nbsp;&nbsp;<%= topic_content%></td> 
		<td align="center">
			     <button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=topic_id%>);">
	         			<IMG id="taskImage<%=topic_id%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
	         	</button>
	         	<input type="hidden" id="taskFlag_display<%=topic_id%>" name="taskFlag_display<%=topic_id%>" value="0">
		</td> 	
		 <td align="center">         
		 		<%if(showFlag.intValue()>2){%>
              	<a href="javascript:reportAction(<%= topic_id%>,'<%= topic_content%>',<%=quesTask_serialNo%>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/report.gif"  width="16" height="16">
               	</a>
               	<%}%>
         </td>    
	</tr>
	<!--下拉显示选项编号-->
	<tr id="taskTr<%=topic_id%>" style="display: none">
				<td align="center" bgcolor="#FFFFFF" colspan="5" >
						<div id="taskDiv<%=topic_id%>" style="overflow-y:auto;" align="center">
								<table id="taskTablePro<%=topic_id%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">	
									<tr  style="background:F7F7F7;">
										<td align="center"  width="20%"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
										<td align="center"  width="10%"><%=LocalUtilis.language("class.insertManName2",clientLocale)%> </td><!--记录人-->
										<td align="center"  width="*"><%=LocalUtilis.language("message.surveyRecords",clientLocale)%> </td><!--调查记录-->
										<td align="center"  width="10%"><%=LocalUtilis.language("class.insertTime",clientLocale)%> </td><!--记录时间-->										
									</tr>								
<%
vo_ques = new QuestionnaireVO();
vo_ques.setQues_taskId(quesTask_serialNo);
vo_ques.setTopic_id(topic_id);									
rsList2_detail = quesLocal.queryQuestRecord(vo_ques);

String cust_name,insertManName2,topic_value,insertTime2;
Integer insertMan2;

if(rsList2_detail.size()>0){
		iterator2 = rsList2_detail.iterator();
		
		while(iterator2.hasNext()){
			rsMap2_detail = (Map)iterator2.next();
			
			cust_name = Utility.trimNull(rsMap2_detail.get("TargetCustName"));
			topic_value = Utility.trimNull(rsMap2_detail.get("TOPIC_VALE")); 
			insertTime2 =  Utility.trimNull(rsMap2_detail.get("InsertTime")); 
			insertMan2 = Utility.parseInt(Utility.trimNull(rsMap2_detail.get("InsertMan")),new Integer(0));
			insertManName2 = Argument.getOperator_Name(insertMan2);
			
			if(insertTime2.length()>0){
				insertTime2 = insertTime2.substring(0,16);
			}
			
			if(q_topicType.equals("305103")){
				topic_value = Argument.getDictParamName(3052,topic_value);
			}
%>
	<tr  style="background:F7F7F7;">
		<td align="center"><%= cust_name%></td>
		<td align="center"><%= insertManName%></td>
		<td align="left">&nbsp;&nbsp;<%= topic_value%></td>
		<td align="center"><%= insertTime2%></td>
	</tr>	
<%			
	}
}								
%>
								
								</table>
						</div>
				</td>
		</tr>
<%}
}%>
<%for(int i=0;i<(8-iCount);i++){%>     	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>       
            <td align="center">&nbsp;</td>           
         </tr>           
<%}%> 
	</table>
</div>
<%}%>
</form>
<%
quesLocal.remove();
serviceTask.remove();
%>
<%@ include file="/includes/foot.inc"%>
</body>
</html>