<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//������������
int iCount = 0;
List rsList = null;
List rsList_details = null;
Map rsMap = null;
Map rsMap_details = null;

//��ö���
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;
ServiceTaskVO vo_details = null;

//������ʾ�ֶ�
String serviceTitle ="";
String serviceRemark =  "";
String serviceResult =  "";//������
Integer managerID =new Integer(0);
String managerName = "";
Integer executorID = new Integer(0);
String executorName = "";
Integer insertManID= new Integer(0);
String insertManName = "";
int serviceStatus = 0;//����״̬
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
Integer customerCount = new Integer(0);//Ŀ��ͻ���
Integer completeCount = new Integer(0);//����ɿͻ���
Integer originate = new Integer(0);//����������ʽ
String originateName = "";

//�༭ʱ��ʾ ������Ϣ
if(serial_no.intValue()>0){
	//��ѯ����Ϣ
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
			originateName = "ϵͳ����";
		}
		else if(originate.intValue()==2){
			originateName = "�˹�����";
		}
	}
	//��ѯ��ϸ������Ϣ
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
<title><%=LocalUtilis.language("menu.displayTaskInformation",clientLocale)%> </title><!-- ������Ϣ��ʾ -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
//��ʼ��ҳ��
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

/*�鿴��ϸ*/
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
	<!-- �������--><!-- ���񴴽� --><!-- ������Ϣ�鿴 -->
    <font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskAdd",clientLocale)%>>><%=LocalUtilis.language("message.queryTask",clientLocale)%> </b></font>
</div>

<div style="margin-left:40px;margin-top:10px;" class="btn-wrapper">
	<font  size="2" face="����"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</font><!-- ������� -->
	<input type="text" name="service_title" style="width:120px"  readonly class='edline'  value="<%=serviceTitle%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
	<font  size="2" face="����"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ��</font><!-- ������� -->
	<input type="text" name="serviceTypeName" style="width:120px"  readonly class='edline'  value="<%=serviceTypeName%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
</div>
<br/>
<div align="left"  style="margin-left:10px">
	<TABLE cellSpacing=0 cellPadding=0 width="97%" border="0" class="edline">
				<TBODY>
					<TR class="tr-tabs">							
						<TD vAlign=top>&nbsp;</TD>					
						<!-- ������Ϣ -->
                        <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
						<!-- ��չ��Ϣ -->
                        <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.extendedInformation",clientLocale)%> </TD>	
						<!-- ��ϸ��Ϣ -->
                        <TD id="d3" style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.detailInformation",clientLocale)%> </TD>						
					</TR>
			</TBODY>
	</TABLE>
</div>

<div  id="r1" align="left"  style="display:none; height:230px; margin-left:10px;margin-top:10px;">
	 <table  id="table1" cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC" class="table-select">
 			<tr style="background:F7F7F7;">
		 			<!-- ������Ϣ��Ҫ���� -->
                    <td colspan="4" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> 
                    </b></font></td>
		 	</tr>
		   <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.originateName",clientLocale)%> ��</font></td><!-- �������ɷ�ʽ -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= originateName%></font>  </td>
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskFlagName",clientLocale)%> ��</font></td><!-- ����״̬ -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceStatusName%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.created",clientLocale)%> ��</font></td><!-- ������ -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= insertManName%></font>  </td>
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.Insert_date",clientLocale)%> ��</font></td><!-- ����ʱ�� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= Insert_date%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.accountManager",clientLocale)%> ��</font></td><!-- �ͻ����� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= managerName%></font>  </td>
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font></td><!-- ��ϵ��ʽ -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceWayName%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> :</font></td><!-- ��ʼ���� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= start_date%></font>  </td>
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate",clientLocale)%> ��</font></td><!-- �������� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= end_date%></font>  </td>
			 </tr>
		 	
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ��</font></td><!-- ������ϸ���� -->
				 <td colspan="3"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceRemark%></font>  </td>
			 </tr>
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.executor",clientLocale)%> ��</font></td><!-- ִ���� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= executorName%></font>  </td>
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ��</font></td><!-- ���ʱ�� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= complete_date%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCount",clientLocale)%> ��</font></td><!-- Ŀ��ͻ��� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= customerCount%></font>  </td>
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeCount",clientLocale)%> ��</font></td><!-- ��ɿͻ��� -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= completeCount%></font>  </td>
			 </tr>
			 
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ��</font></td><!-- ���������� -->
				 <td colspan="3"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceResult%></font>  </td>
			 </tr>			 
 		</table>
</div>

<div  id="r2" align="left"  style="display:none; height:230px; margin-left:10px;margin-top:10px;">
	<table border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
					<tr style="background:F7F7F7;">
						<td  width="90px"><font size="2" face="΢���ź�">&nbsp;&nbsp; <%=LocalUtilis.language("class.questionaireName",clientLocale)%> ��</font></td><!-- �ʾ����� -->
						<td  width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= quesTitle%></font>  </td>
						
						<td width="90px"><font size="2" face="΢���ź�">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ��</font></td><!-- ��Ʒ���� -->
						<td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= productName%></font>  </td>
					</tr>	
	</table>
</div>

<div  id="r3" align="left"  style="display:none; height:230px; margin-left:10px;margin-top:10px;">
	<table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
		<tr style="background:6699FF;">
			 <td width="25%" align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- �ͻ����� -->
			 <td width="25%" align="center"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td>  <!-- ��ϵ��ʽ -->
			 <td width="25%" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!-- ״̬ -->
			 <td width="25%" align="center"><%=LocalUtilis.language("message.detailInfo",clientLocale)%> </td><!-- ��ϸ��Ϣ -->
		</tr>  
	</table>	
	<span id="tableList" style="overflow-y:auto;height:250;">
		<table border="0" width="800px" cellspacing="1" cellpadding="2" bgcolor="#CCCCCC">
<%
	//�����ֶ�
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
				 feedback ="Ҫ";
			}else if(needFeedback.intValue()==2){
				feedback ="��Ҫ";
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
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeTime",clientLocale)%> ��</td><!-- ���ʱ�� -->
											<td><%= executeTimeDetail%></td>
										</tr>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("message.deal_result",clientLocale)%> ��</td><!-- �������� -->
											<td><%= serviceDetailResult%></td>
										</tr>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.isFeedback",clientLocale)%> ��</td><!-- �Ƿ��� -->
											<td><%= feedback%></td>
										</tr>
										<%if(needFeedback.intValue()==1){%>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> ��</td><!-- ������������ -->
											<td><%= feedbackContent%></td>
										</tr>
										<tr style="background:F7F7F7;">
											<td width="25%">&nbsp;&nbsp;<%=LocalUtilis.language("class.satisfaction",clientLocale)%> ��</td><!-- �ͻ������ -->
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
			<tr class="tr1"><td align="center" colspan="4"><%=LocalUtilis.language("message.noDetails",clientLocale)%> </td></tr><!-- û�������ϸ������Ϣ -->
		<%}%>
		</table>
	</span>
</div>

<% serviceTask.remove();%>
</form>
</BODY>
</HTML>
