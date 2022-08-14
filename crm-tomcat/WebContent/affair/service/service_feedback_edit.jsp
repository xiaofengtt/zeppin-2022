<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//���ҳ�洫�ݱ���
Integer serial_no_details = Utility.parseInt(request.getParameter("serial_no_details"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("targetCustID"), new Integer(0));
//������������
boolean bSuccess = false;
List list_details = null;
List rsList = null;
Map map_details = new HashMap();
Map rsMap = new HashMap();
//������ʾ�ֶ�
Integer serial_no =new Integer(0);
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
//������������
String feedbackContent =""; //������Ϣ
Integer satisfaction = new Integer(0);
String cust_name = "";
String executeTime = "";
String serviceWay_details = "";
String serviceWayName_details = "";
String result_details= "";
String serviceTypeName_details = "";
//��ö���
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//����ҳ�����
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
    //������ϸ��Ϣ
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
				originateName = enfo.crm.tools.LocalUtilis.language("message.systemGegerate",clientLocale);//ϵͳ����
			}
			else if(originate.intValue()==2){
				originateName = enfo.crm.tools.LocalUtilis.language("message.artificiallyBuild",clientLocale);//�˹�����
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
<title><%=LocalUtilis.language("class.feedback",clientLocale)%> </title><!-- ���� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language=javascript>
/*����*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}
/*ȡ��*/
function CancelAction(){
	//window.returnValue=null;
	window.parent.document.getElementById("closeImg").click();	
	//window.close();
}
/*��֤����*/
function validateForm(form){		
	if(!sl_check(document.getElementsByName('feedbackContent')[0],"<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> ",200,0)){ return false;}// ������������
      return sl_check_update();
}
/*��ʼ��*/
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
<!--�޸ĳɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no_details" value="<%=serial_no_details%>"/>
<!-- 
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message",clientLocale)%> </b></font>������
</div>
<hr noshade color="#808080" size="1" width="98%">
 -->

<div align="left" style=" height:100px; margin-left:10px;margin-top:5px;">
		<table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
 			<tr style="background:F7F7F7;">
		 		<td colspan="4" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message",clientLocale)%> </b></font></td><!-- ������ -->
		 	</tr>
			<tr style="background:F7F7F7;">	
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp; <%=LocalUtilis.language("class.customerName",clientLocale)%> ��</font></td><!-- �ͻ����� -->
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= cust_name%></font> </td>
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp; <%=LocalUtilis.language("class.executeTime",clientLocale)%> ��</font></td><!-- ����ʱ�� -->
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= executeTime%></font> </td>
			 </tr>
			 <tr style="background:F7F7F7;">	
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;  <%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ��</font></td><!-- ������� -->
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTypeName_details%></font> </td>
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;  <%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font></td><!-- ��ϵ��ʽ -->
				 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=serviceWayName_details%></font> </td>
			 </tr>
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ��</font></td><!-- ���������� -->
				 <td colspan="3"><font size="2" face="΢���ź�"color="red">&nbsp;&nbsp;<%= result_details%></font></td>
			 </tr>	
			<tr style="background:F7F7F7;">
				<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.satisfaction",clientLocale)%> :</font></td><!-- �ͻ������ -->
				<td colspan="3">
					<select name="satisfaction" style="width:120px">
						<%= Argument.getCustomerSatifaction(satisfaction.intValue())%>
					</select>				
				</td>
			</tr>
			<tr style="background:F7F7F7;">
				<td  valign=top><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.feedbackContent",clientLocale)%> :</font></td><!-- ������������ -->
				<td  colspan="3">
					<textarea rows="3" name="feedbackContent" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=feedbackContent%></textarea>			
				</td>
			</tr>	
			<tr style="background:F7F7F7;">	
				<td colspan="4" align="right">
					<!-- ���� -->
                    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
                    <!-- �ر� -->
					<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
				</td>
			</tr>
		</table>
		<hr noshade color="#808080" size="1" style=" width:800px; ">
</div>
<div  align="left"  style=" height:230px; margin-left:10px;">
	 <table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
 			<tr style="background:F7F7F7;">
		 			<!-- ������Ϣ��Ҫ���� -->
                    <td colspan="4" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> </b></font></td>
		 	</tr>
		   <tr style="background:F7F7F7;">	
				 <td width="150px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.originateName",clientLocale)%> ��</font></td><!-- �������ɷ�ʽ -->
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= originateName%></font>  </td>
				 <td width="150px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskFlagName",clientLocale)%> ��</font></td><!-- ����״̬ -->
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
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> ��</font></td><!-- ��ʼ���� -->
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
				<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ��</font></td><!-- �ʾ����� -->
				<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= quesTitle%></font></td>
				<td><font size="2" face="΢���ź�">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ��</font></td><!-- ��Ʒ���� -->
				<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= productName%></font>  </td>
			</tr>	 
 		</table>
</div>
<br>
</form>
</body>
</html>
<%serviceTask.remove();%>