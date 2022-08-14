<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//声明辅助变量
int iCount = 0;
List rsList = null;
List rsList_details = null;
Map rsMap = null;
Map rsMap_details = null;

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;
ServiceTaskVO vo_details = null;

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

//编辑时显示 任务信息
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
			originateName = "系统生成";
		}
		else if(originate.intValue()==2){
			originateName = "人工建立";
		}
	}
	//查询明细任务信息
	vo_details = new ServiceTaskVO();
	vo_details.setTaskSerialNO(serial_no);
	vo_details.setInputMan(input_operatorCode);
	rsList_details = serviceTask.query_details(vo_details);
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

if(Insert_date.length()>0){
	Insert_date = Insert_date.substring(0,16);
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.displayTaskInformation",clientLocale)%> </title><!-- 任务信息显示 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
//初始化页面
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
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no"  id="serial_no" value="<%=serial_no%>"/>

<div align="left" class="page-title page-title-noborder">
	<!-- 任务管理--><!-- 任务创建 --><!-- 任务信息查看 -->
    <font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskAdd",clientLocale)%>>><%=LocalUtilis.language("message.queryTask",clientLocale)%> </b></font>
</div>

<div style="margin-left:40px;margin-top:10px;" class="btn-wrapper">
	<font  size="2" face="宋体"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ：</font><!-- 任务标题 -->
	<input type="text" name="service_title" style="width:120px"  readonly class='edline'  value="<%=serviceTitle%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
	<font  size="2" face="宋体"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ：</font><!-- 任务类别 -->
	<input type="text" name="serviceTypeName" style="width:120px"  readonly class='edline'  value="<%=serviceTypeName%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
</div>
<br/>
<div align="left"  style="margin-left:10px">
	<TABLE cellSpacing=0 cellPadding=0 width="97%" border="0" class="edline">
				<TBODY>
					<TR class="tr-tabs">							
						<TD vAlign=top>&nbsp;</TD>					
						<!-- 基本信息 -->
                        <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
						<!-- 扩展信息 -->
                        <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.extendedInformation",clientLocale)%> </TD>	
						<!-- 明细信息 -->
                        <TD id="d3" style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.detailInformation",clientLocale)%> </TD>						
					</TR>
			</TBODY>
	</TABLE>
</div>

<div  id="r1" align="left"  style="display:none; height:230px; margin-left:10px;margin-top:10px;">
	 <table  id="table1" cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC" class="table-select">
 			<tr style="background:F7F7F7;">
		 			<!-- 任务信息主要内容 -->
                    <td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> 
                    </b></font></td>
		 	</tr>
		   <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.originateName",clientLocale)%> ：</font></td><!-- 任务生成方式 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= originateName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskFlagName",clientLocale)%> ：</font></td><!-- 任务状态 -->
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
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> :</font></td><!-- 开始日期 -->
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
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ：</font></td><!-- 处理结果描述 -->
				 <td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceResult%></font>  </td>
			 </tr>			 
 		</table>
</div>

<div  id="r2" align="left"  style="display:none; height:230px; margin-left:10px;margin-top:10px;">
	<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
					<tr style="background:F7F7F7;">
						<td  width="90px"><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.questionaireName",clientLocale)%> ：</font></td><!-- 问卷名称 -->
						<td  width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= quesTitle%></font>  </td>
						
						<td width="90px"><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ：</font></td><!-- 产品名称 -->
						<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= productName%></font>  </td>
					</tr>	
	</table>
</div>

<div  id="r3" align="left"  style="display:none; height:230px; margin-left:10px;margin-top:10px;">
	<table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
		<tr style="background:6699FF;">
			 <td width="25%" align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- 客户名称 -->
			 <td width="25%" align="center"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td>  <!-- 联系方式 -->
			 <td width="25%" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!-- 状态 -->
			 <td width="25%" align="center"><%=LocalUtilis.language("message.detailInfo",clientLocale)%> </td><!-- 详细信息 -->
		</tr>  
	</table>	
	<span id="tableList" style="overflow-y:auto;height:250;">
		<table border="0" width="800px" cellspacing="1" cellpadding="2" bgcolor="#CCCCCC">
<%
	//声明字段
	Integer serial_no_detail = new Integer(0);
	Integer serviceTypeDetail = new Integer(0);
	String serviceTypeDetailName ="";
	String executeTimeDetail = "";
	String serviceWay_detail = "";
	String serviceWayName_detail = "";
	String cust_name = "" ;		
	int status_details = 0;
	String statusName_details = "";
	String serviceDetailResult = "";
	Integer needFeedback = new Integer(1);
	String feedback ="";	
	String feedbackContent = "";
	Integer satisfaction = new Integer(0);	
	
	if(rsList_details!=null&&rsList_details.size()>0){
		 Iterator iterator = rsList_details.iterator();
						
		while(iterator!=null&&iterator.hasNext()){		
			rsMap_details = (Map)iterator.next();
			iCount++;

			if(rsMap_details!=null){
					serial_no_detail = Utility.parseInt(Utility.trimNull(rsMap_details.get("Serial_no")),null);
					serviceTypeDetail = Utility.parseInt(Utility.trimNull(rsMap_details.get("ServiceType")),null);
					serviceTypeDetailName = Utility.trimNull(rsMap_details.get("ServiceTypeName"));	
					executeTimeDetail = Utility.trimNull(rsMap_details.get("ExecuteTime"));	
					serviceWay_detail = Utility.trimNull(rsMap_details.get("ServiceWay"));
					serviceWayName_detail= Argument.getDictParamName(1109,serviceWay_detail);
					cust_name = Utility.trimNull(rsMap_details.get("CUST_NAME"));	
					status_details = Utility.parseInt(Utility.trimNull(rsMap_details.get("Status")),0);
					statusName_details = Argument.getService_status_name(status_details);
					needFeedback = Utility.parseInt(Utility.trimNull(rsMap_details.get("NeedFeedback")),new Integer(0));
					feedbackContent  = Utility.trimNull(rsMap_details.get("FeedbackContent"));	
					satisfaction = Utility.parseInt(Utility.trimNull(rsMap_details.get("Satisfaction")),new Integer(0));
					serviceDetailResult = Utility.trimNull(rsMap_details.get("Result"));
			}
			
			if(needFeedback.intValue()==1){
				 feedback ="要";
			}else if(needFeedback.intValue()==2){
				feedback ="不要";
			}
			
			if(executeTimeDetail.length()>0){
				executeTimeDetail = executeTimeDetail.substring(0,16);
			}
	%>
			<tr class="tr1">
				 <td align="center" width="25%"><%= cust_name%> </td>         
				 <td align="center" width="25%"><%= serviceWayName_detail%></td>  
				 <td align="center" width="25%"><%= statusName_details%></td>  
			 	 <td align="center" width="25%">
		         	<button type="button" class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no_detail%>);">
		         		<IMG id="taskImage<%=serial_no_detail%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
		         	</button>
		         	<input type="hidden" id="taskFlag_display<%=serial_no_detail%>" name="taskFlag_display<%=serial_no_detail%>" value="0">
		         </td>         
			</tr>
			
			<tr id="taskTr<%=serial_no_detail%>" style="display: none">
				<td align="center" bgcolor="#FFFFFF" colspan="4" >
						<div id="taskDiv<%=serial_no_detail%>" style="overflow-y:auto;" align="center">
								<table id="taskTablePro<%=serial_no_detail%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">	
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ：</td><!-- 完成时间 -->
											<td><%= executeTimeDetail%></td>
										</tr>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("message.deal_result",clientLocale)%> ：</td><!-- 处理内容 -->
											<td><%= serviceDetailResult%></td>
										</tr>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.isFeedback",clientLocale)%> ：</td><!-- 是否反馈 -->
											<td><%= feedback%></td>
										</tr>
										<%if(needFeedback.intValue()==1){%>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> ：</td><!-- 反馈内容描述 -->
											<td><%= feedbackContent%></td>
										</tr>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.satisfaction",clientLocale)%> ：</td><!-- 客户满意度 -->
											<td><%= satisfaction%></td>
										</tr>
										<%}%>
								</table>
						</div>
				</td>
			</tr>
<%}
}%>
		<%if(iCount==0){%>
			<tr class="tr1"><td align="center" colspan="4"><%=LocalUtilis.language("message.noDetails",clientLocale)%> </td></tr><!-- 没有添加明细任务信息 -->
		<%}%>
		</table>
	</span>
</div>

<% serviceTask.remove();%>
</form>
</BODY>
</HTML>
