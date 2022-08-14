<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//获得页面传递变量
Integer serial_no_details = Utility.parseInt(request.getParameter("serial_no_details"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("targetCustID"), new Integer(0));
//声明辅助变量
boolean bSuccess = false;
List list_details = null;
List rsList = null;
Map map_details = new HashMap();
Map rsMap = new HashMap();
//声明显示字段
Integer serial_no =new Integer(0);
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
Integer serviceType = Utility.parseInt(request.getParameter("serviceType"), new Integer(0));
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
//声明参数变量
String feedbackContent =""; //描述信息
Integer satisfaction = new Integer(0);
String cust_name = "";
String executeTime = "";
String serviceWay_details = "";
String serviceWayName_details = "";
String result_details= "";
String serviceTypeName_details = "";
//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//保存页面对象
if(request.getMethod().equals("POST")){
	vo = new ServiceTaskVO();
	satisfaction = Utility.parseInt(Utility.trimNull(request.getParameter("satisfaction")),new Integer(0));
	feedbackContent = Utility.trimNull(request.getParameter("feedbackContent"));

	vo.setSerial_no(serial_no_details);
	vo.setSatisfaction(satisfaction);
	vo.setFeedbackContent(feedbackContent);
	vo.setInputMan(input_operatorCode);
	serviceTask.feedback(vo);
	
	bSuccess = true;
}
else{
    //查找明细信息
	vo = new ServiceTaskVO();
    vo.setSerial_no(serial_no_details);
	list_details = serviceTask.query_details(vo);
			
	if(list_details.size() != 0){
		map_details = (Map) list_details.get(0);

		cust_name = Utility.trimNull(map_details.get("CUST_NAME"));
		executeTime = Utility.trimNull(map_details.get("ExecuteTime"));
		serviceTypeName_details = Utility.trimNull(map_details.get("ServiceTypeName"));
		serviceWay_details = Utility.trimNull(map_details.get("ServiceWay"));
		serviceWayName_details = Argument.getDictParamName(1109,serviceWay_details);
		result_details = Utility.trimNull(map_details.get("Result"));
		serial_no = Utility.parseInt(Utility.trimNull(map_details.get("TaskSerialNO")), new Integer(0));

		if(executeTime.length()>0){
			executeTime = executeTime.substring(0,16);
		}
	}

	if(serial_no.intValue()>0){
		//查询主信息
		vo = new ServiceTaskVO();	
		vo.setSerial_no(serial_no);
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
<title><%=LocalUtilis.language("class.feedback",clientLocale)%> </title><!-- 反馈 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language=javascript>
/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}
/*取消*/
function CancelAction(){
	//window.returnValue=null;
	window.parent.document.getElementById("closeImg").click();	
	//window.close();
}
/*验证数据*/
function validateForm(form){		
	if(!sl_check(document.getElementsByName('feedbackContent')[0],"<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> ",200,0)){ return false;}// 反馈内容描述
      return sl_check_update();
}
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.parent.location.reload();
		window.parent.document.getElementById("closeImg").click();		
		window.close();
	}
}
</script>
</head>
<body class="BODY">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/service/service_feedback_edit.jsp" onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no_details" value="<%=serial_no_details%>"/>
<!-- 
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message",clientLocale)%> </b></font>任务反馈
</div>
<hr noshade color="#808080" size="1" width="98%">
 -->

<div align="left" style=" height:100px; margin-left:10px;margin-top:5px;">
		<table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
 			<tr style="background:F7F7F7;">
		 		<td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message",clientLocale)%> </b></font></td><!-- 任务反馈 -->
		 	</tr>
			<tr style="background:F7F7F7;">	
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.customerName",clientLocale)%> ：</font></td><!-- 客户姓名 -->
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= cust_name%></font> </td>
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.executeTime",clientLocale)%> ：</font></td><!-- 任务时间 -->
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= executeTime%></font> </td>
			 </tr>
			 <tr style="background:F7F7F7;">	
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;  <%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ：</font></td><!-- 任务类别 -->
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceTypeName_details%></font> </td>
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;  <%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font></td><!-- 联系方式 -->
				 <td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=serviceWayName_details%></font> </td>
			 </tr>
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ：</font></td><!-- 处理结果描述 -->
				 <td colspan="3"><font size="2" face="微软雅黑"color="red">&nbsp;&nbsp;<%= result_details%></font></td>
			 </tr>	
			<tr style="background:F7F7F7;">
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.satisfaction",clientLocale)%> :</font></td><!-- 客户满意度 -->
				<td colspan="3">
					<select name="satisfaction" style="width:120px">
						<%= Argument.getCustomerSatifaction(satisfaction.intValue())%>
					</select>				
				</td>
			</tr>
			<tr style="background:F7F7F7;">
				<td  valign=top><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> :</font></td><!-- 反馈内容描述 -->
				<td  colspan="3">
					<textarea rows="3" name="feedbackContent" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=feedbackContent%></textarea>			
				</td>
			</tr>	
			<tr style="background:F7F7F7;">	
				<td colspan="4" align="right">
					<!-- 保存 -->
                    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- 关闭 -->
					<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
				</td>
			</tr>
		</table>
		<hr noshade color="#808080" size="1" style=" width:800px; ">
</div>
<div  align="left"  style=" height:230px; margin-left:10px;">
	 <table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
 			<tr style="background:F7F7F7;">
		 			<!-- 任务信息主要内容 -->
                    <td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> </b></font></td>
		 	</tr>
		   <tr style="background:F7F7F7;">	
				 <td width="150px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.originateName",clientLocale)%> ：</font></td><!-- 任务生成方式 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= originateName%></font>  </td>
				 <td width="150px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskFlagName",clientLocale)%> ：</font></td><!-- 任务状态 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceStatusName%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.created",clientLocale)%> ：</font></td><!-- 创建人 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= insertManName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.Insert_date",clientLocale)%> ：</font></td><!-- 创建时间 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= Insert_date%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</font></td><!-- 客户经理 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= managerName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font></td><!-- 联系方式 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceWayName%></font>  </td>
			 </tr>
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> ：</font></td><!-- 开始日期 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= start_date%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate",clientLocale)%> ：</font></td><!-- 结束日期 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= end_date%></font>  </td>
			 </tr>
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ：</font></td><!-- 任务详细描述 -->
				 <td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceRemark%></font>  </td>
			 </tr>
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.executor",clientLocale)%> ：</font></td><!-- 执行人 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= executorName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ：</font></td><!-- 完成时间 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= complete_date%></font>  </td>
			 </tr>
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCount",clientLocale)%> ：</font></td><!-- 目标客户数 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= customerCount%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeCount",clientLocale)%> ：</font></td><!-- 完成客户数 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= completeCount%></font>  </td>
			 </tr>	
			<tr style="background:F7F7F7;">
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!-- 问卷名称 -->
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= quesTitle%></font></td>
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ：</font></td><!-- 产品名称 -->
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= productName%></font>  </td>
			</tr>	 
 		</table>
</div>
<br>
</form>
</body>
</html>
<%serviceTask.remove();%>