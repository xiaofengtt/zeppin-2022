<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//��ȡҳ�洫�ݱ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
Integer q_serviceType = Utility.parseInt(request.getParameter("serviceType"), null);
Integer transFlag = Utility.parseInt(request.getParameter("transFlag"), new Integer(1));

//������������
int count = 0;
String sReadonly = "readonly class='edline'";
List rsList,rsList_details;
Map map,map_details;

//�����ֶα���
String start_date="";
String end_date="";
String serviceTitle="";
Integer managerID = new Integer(0);
Integer executorID = new Integer(0);
String managerName="";
String serviceWay="";
String serviceWayName = "";
int status_flag=1;
String serviceStatusName = "";
Integer serviceType = new Integer(0);
String serviceTypeName = "";
String result ="";
String serviceRemark =  "";
Integer product_id =new Integer(0);
Integer ques_id = new Integer(0);
String productName = "";
String quesTitle = "";


//��ö���
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();
ServiceTaskVO vo_details = new ServiceTaskVO();

//�������
vo.setSerial_no(serial_no);
vo.setServiceType(q_serviceType);
vo.setInputMan(input_operatorCode);
rsList = serviceTask.query(vo);

//����������ϸ����
map = (Map)rsList.get(0);

if(map!=null){
	start_date = map.get("StartDateTime").toString().substring(0,10);
	end_date = map.get("EndDateTime").toString().substring(0,10);
	serviceTitle = Utility.trimNull(map.get("ServiceTitle"));	
	executorID = Utility.parseInt(map.get("Executor").toString(), new Integer(0));
	managerID = Utility.parseInt(map.get("ManagerID").toString(), new Integer(0));
	managerName = Argument.getManager_Name(managerID);
	serviceWay = Utility.trimNull(map.get("ServiceWay"));
	serviceWayName = Argument.getDictParamName(1109,serviceWay);
	serviceType = Utility.parseInt( Utility.trimNull(map.get("ServiceType")), new Integer(0));
	serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
	result = Utility.trimNull(map.get("Result"));
	serviceRemark = Utility.trimNull(map.get("ServiceRemark"));
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	productName = Argument.getProductName(product_id);
	quesTitle = Utility.trimNull(map.get("QUES_TITLE"));
}

//�����ϸ�б�
vo_details.setTaskSerialNO(serial_no);
vo_details.setStatus(new Integer(2));//����������״̬����ϸ��Ϣ
vo_details.setInputMan(input_operatorCode);
rsList_details = serviceTask.query_details(vo_details);
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("message.addServiceProcessing",clientLocale)%> </title><!-- ���������� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet /> 
<LINK href="<%=request.getContextPath()%>/includes/selectbox.css" type=text/css rel=stylesheet />
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />	
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript"></SCRIPT>
<script language=javascript>
/*��ʼ��*/
window.onload = function(){
	<%if(serviceType.intValue()!=64&&!serviceWay.equals("110901")){%>
		initQueryCondition();
	<%}%>
}
	
/*����*/
function CancelAction(){
	var transFlag = document.getElementById("transFlag").value;
	
	if(transFlag==1){
		window.location.href = "<%=request.getContextPath()%>/affair/service/service_deal.jsp";
	}
	else if(transFlag==2){
		window.location.href = "<%=request.getContextPath()%>/marketing/investigation/questionnarie_task_deal.jsp";
	}
	else if(transFlag==3){
		window.location.href = "<%=request.getContextPath()%>/lib/portlet-simple.jsp?display_flag=1";
	}
	else if(transFlag==4){
		window.location.href = "<%=request.getContextPath()%>/lib/portlet-simple.jsp?display_flag=1";
	}
}

/*��������*/
function treat_batch(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.chooseDetailInfo",clientLocale)%> ��");//��ѡ��Ҫ�������ϸ��Ϣ
		return false;
	}
	
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "<%=request.getContextPath()%>/affair/service/service_deal_batch_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}

/*��֤����*/
function validateForm(form){
	if(!sl_check(document.getElementById('execute_date'), "<%=LocalUtilis.language("class.nearExecuteDate",clientLocale)%> ", 20, 1))return false;//�����ϵ����
	if(document.getElementsByName("service_status")[0].value==9){
		var deal_result = document.getElementsByName("deal_result")[0].value;
		if(deal_result==""){
			sl_alert("<%=LocalUtilis.language("class.invalid'/>��<fmt:message key='message.reasons",clientLocale)%> ��");//���ϣ���һ���ڱ�ע��д����ϸԭ��
			return false;
		}
	}
			
	return sl_check_update();		
}

/*����������ϸ*/
function treat_detail(serial_no_detail,cust_name,targetCustId){
	var serial_no = document.getElementById("serial_no").value;
	var serviceType = document.getElementById("serviceType").value;
	var ret = 0;

	if(serviceType==64){
		var url1 = "<%=request.getContextPath()%>/marketing/investigation/questionnarie_task_dealAction.jsp?serviceTitle=<%=serviceTitle%>";
		var url1 = url1 + "&ques_taskId="+serial_no;
		var url1 = url1 + "&ques_taskId_detail="+serial_no_detail;
		var url1 = url1 + "&cust_name="+cust_name;
		var url1 = url1 + "&targetCustId="+targetCustId;
		
		if(showModalDialog(url1, '', 'dialogWidth:900px;dialogHeight:370px;status:0;help:0') != null)////������ϸ����
		{
			location.reload();
		}	
	}
	else{			
		var url ="<%=request.getContextPath()%>/affair/service/service_deal_action.jsp?serviceTitle=<%=serviceTitle%>&serviceType=<%=q_serviceType%>";
		var url = url + "&serial_no_detail="+serial_no_detail;
		var url = url + "&serial_no="+serial_no;
		disableAllBtn(true);		
		window.location = url;	
		//if(showModalDialog(url, '', 'dialogWidth:850px;dialogHeight:370px;status:0;help:0') != null)////������ϸ����
		//{
			//location.reload();
		//}		
	}
	
	showWaitting(0);	
}

/*�ı���ϵ��ʽ*/
function changeServiceWay(flag){	
	var serialNoDetail_Message = "";
	var target_custid = "";
	var to_name = "";
	var v_check_serial_no = document.getElementsByName("check_serial_no");
	var v_check_target_custid = document.getElementsByName("check_target_custid");
	var v_check_cust_name =  document.getElementsByName("check_cust_name");
	
	if(checkedCount(v_check_serial_no) == 0){
		sl_alert("<%=LocalUtilis.language("message.chooseDetailInfo",clientLocale)%> ��");//��ѡ��Ҫ�������ϸ��Ϣ
		return false;
	}
	else{
		//ѡ�е�serialNo���ɱ���
		for(i = 0; i < v_check_serial_no.length; i++){	
			if(v_check_serial_no[i].checked){
				serialNoDetail_Message = serialNoDetail_Message + v_check_serial_no[i].value + "_";
			}	
		}
		
		for(i = 0; i < v_check_target_custid.length; i++){	
			if(v_check_target_custid[i].checked){
				target_custid = target_custid + v_check_target_custid[i].value + "_";
			}	
		}
		
		for(i = 0; i < v_check_cust_name.length; i++){	
			if(v_check_cust_name[i].checked){
				to_name = to_name + v_check_cust_name[i].value + ",";
			}	
		}
		
		if(serialNoDetail_Message.length>0){
			serialNoDetail_Message = serialNoDetail_Message.substring(0,serialNoDetail_Message.length-1);
		}
		
		if(target_custid.length>0){
			target_custid = target_custid.substring(0,target_custid.length-1);
		}
		
		if(to_name.length>0){
			to_name = to_name.substring(0,to_name.length-1);
		}
	}
	
	if(flag==1){		
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsTele",clientLocale)%> ";//��  ��	
		document.getElementById("service_way").value="110901";		
	}
	if(flag==2){		
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsPost",clientLocale)%> ";//��  ��		
		document.getElementById("service_way").value="110902";	
	}
	if(flag==3){	
		document.getElementById("service_way_name").value="Email";		
		document.getElementById("service_way").value="110903";	
		
		var url = "<%=request.getContextPath()%>/marketing/mail/sendmail_cust.jsp?target_custid="+target_custid+"&to_name="+to_name;
		showModalDialog(url,'','dialogWidth:900px;dialogHeight:500px;status:0;help:0');	

	}
	if(flag==4){		
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsFax",clientLocale)%> ";//��  ��		
		document.getElementById("service_way").value="110904";	
	}
	if(flag==5){	
		document.getElementById("service_way_name").value="<%=LocalUtilis.language("message.jsSms",clientLocale)%> ";	//��  ��	
		document.getElementById("service_way").value="110905";	
		
		<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
			var url = "<%=request.getContextPath()%>/affair/sms/sendMes2.jsp?serialNoDetail_Message="+serialNoDetail_Message;
			showModalDialog(url,'','dialogWidth:600px;dialogHeight:500px;status:0;help:0');
			location.reload();
		<%}%>
	}
}

/*ͬ��serialNO �� target_custid*/
function setCustId(serial_no_detail){
	var v_target_custid = "target_custid_"+serial_no_detail;
	var v_serial_no_detail = "serial_no_"+serial_no_detail;
	var v_cust_name = "cust_name_"+serial_no_detail;
	
	document.getElementById(v_target_custid).checked = document.getElementById(v_serial_no_detail).checked;
	document.getElementById(v_cust_name).checked = document.getElementById(v_serial_no_detail).checked;
}
function showInfo(custid){
	var url = '<%=request.getContextPath()%>/affair/service/service_deal_cust_detail.jsp?showflag=1&cust_id='+custid;
	showModalDialog(url,window,'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
	showWaitting(0);

}
</script>

</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="serviceType" name="serviceType" value="<%=serviceType%>"/>
<input type="hidden" id="transFlag" name="transFlag" value="<%=transFlag%>"/>

<div id="queryCondition" class="qcMain" style="display:none;width:800px;height:330px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.batchProcessingTasks",clientLocale)%> ��</td><!-- ������������ -->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table  border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC">	 	
	 		 <tr >	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.nearExecuteDate",clientLocale)%> ��</font></td><!-- �����ϵ���� -->
				 <td>
				 		<input type="text" name="execute_date" id="execute_date" size="30" onclick="javascript:setday(this);"  value="" readOnly/> 					 
				 </td>
			 </tr>
			 
			 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font></td><!-- ��ϵ��ʽ -->
					 <td>
					 	<!-- �绰 -->
						<button type="button" class="xpbutton3" id="queryButton2" name="queryButton2" title="�ʼ�" onclick="javascript:changeServiceWay(2);" ><%=LocalUtilis.language("class.tele",clientLocale)%> </button>&nbsp;&nbsp;
						<button type="button" class="xpbutton3" id="queryButton3" name="queryButton3" title="Email" onclick="javascript:changeServiceWay(3);">Email</button>&nbsp;&nbsp;
						<!-- ���� --><!-- ��&nbsp;&nbsp;�� -->
						<button type="button" class="xpbutton3" id="queryButton4" name="queryButton4" title="����" onclick="javascript:changeServiceWay(4);" ><%=LocalUtilis.language("class.fax",clientLocale)%> </button>&nbsp;&nbsp;
						<!-- ���� --><!-- ��&nbsp;&nbsp;�� -->
						<button type="button" class="xpbutton3" id="queryButton5" name="queryButton5" title="����" onclick="javascript:changeServiceWay(5);" ><%=LocalUtilis.language("message.sms",clientLocale)%> </button>&nbsp;&nbsp;					 
					 </td>
			 </tr>
					 
			  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("message.serviceWay",clientLocale)%> ��</font></td><!-- ʵ����ϵ��ʽ -->
					 <td>
							<input readonly class="edline"  name="service_way_name" id="service_way_name"size="30" value="<%=Utility.trimNull(Argument.getDictParamName(1109,serviceWay))%>" onkeydown="javascript:nextKeyPress(this)"/>
							<input type="hidden" name="service_way" id="service_way" value="<%=serviceWay%>" />	
					 </td>
			 </tr>
					 
			  <tr >	
				 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("message.deal_result",clientLocale)%> ��</font></td><!-- ������ -->
				 <td>
						<!-- ���� -->
                        <input type="radio" value="1" name="feedback" onclick="javascript:document.getElementById('service_status').value=3;"  /><%=LocalUtilis.language("class.feedback",clientLocale)%> &nbsp;&nbsp;
						<!-- ������ -->
                        <input type="radio" value="2" name="feedback" onclick="javascript:document.getElementById('service_status').value=4;" checked/><%=LocalUtilis.language("class.noFeedback",clientLocale)%> &nbsp;&nbsp;
						<!-- ���� -->
                        <input type="radio" value="2" name="feedback" onclick="javascript:document.getElementById('service_status').value=9;"/><%=LocalUtilis.language("class.invalid",clientLocale)%> &nbsp;&nbsp;
						<input type="hidden" value="4" name="service_status" id="service_status"/>
				 </td>
			 </tr>
					  
			  	<tr >
					<td valign="top"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.deal_result",clientLocale)%> ��</font></td><!-- ���������� -->
					<td>
						<textarea rows="8" name="deal_result" onkeydown="javascript:nextKeyPress(this)" style="width:100%"></textarea>			
					</td>
				</tr>			
	</table>
	
	<div align="center" style="margin-top:10px;margin-bottom:10px;">
		<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:treat_batch()"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!-- ȷ�� -->	
	</div>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	
	<div align="right" style="margin-right:10px;" class="btn-wrapper">
		<%if(serviceType.intValue()!=64&&!serviceWay.equals("110901")){%>
        <!-- �������� -->
		<button type="button" class="xpbutton3" accessKey=e id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.batchProcessing",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.batchProcessing",clientLocale)%> </button>
		<%}%>
		&nbsp;&nbsp;
		<button type="button" class="xpbutton3" id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> </button><!-- ���� -->
	</div>

</div>
<br/>
<div style="width:100%" align="center">
			<table  border="0" width="100%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="product-list">
		 			<tr style="background:F7F7F7;">
				 			<!-- ������Ϣ��Ҫ���� -->
                            <td colspan="4" align="left"><p class="title-table"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> </b></font></p></td>
				 	</tr>
 	
		 			<tr >	
							<td width="25%"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</font></td><!-- ������� -->
							<td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTitle%></font>  </td>	
						<td width="20%"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ��</font></td><!-- ������� -->
						<td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTypeName%></font>  </td>
				 </tr>

				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.accountManager",clientLocale)%> ��</font></td><!-- �ͻ����� -->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= managerName%></font>  </td>
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font></td><!-- ��ϵ��ʽ -->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceWayName%></font>  </td>
				 </tr>
		 
	 			  <tr >	
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> ��</font></td><!-- ��ʼ���� -->
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= start_date%></font>  </td>
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate",clientLocale)%> ��</font></td><!-- �������� -->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= end_date%></font>  </td>
				 </tr>
		 
	 			<tr >	
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ��</font></td><!-- ������ϸ���� -->
					<td colspan="3"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceRemark%></font>  </td>
				 </tr>
			 	<%if(serviceType.intValue()==64){%>
			 	  <tr >
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ��</font></td><!-- �ʾ����� -->
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= quesTitle%></font>  </td>
						
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ��</font></td><!-- ��Ʒ���� -->
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= productName%></font>  </td>
					</tr>	
				<%}%>
		</table>					
</div>
<br/>
<br/>
<br/>
<div align="center" style="overflow:auto; height:200; margin-top:10px;">	
			<table border="0" width="100%" cellspacing="1" cellpadding="2" class="tablelinecolor">
				<tr class="trh">
					<td align="center" width="15px">
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" 
							   onclick="javascript:selectAllBox(document.theform.check_serial_no,this);selectAllBox(document.theform.check_target_custid,this);selectAllBox(document.theform.check_cust_name,this);" />
					</td>
					 <td width="*" align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!-- �ͻ����� -->
					 <td width="*" align="center"><%=LocalUtilis.language("class.cardID",clientLocale)%></td><!-- ֤������ -->
					 <td width="120px" align="center"><%=LocalUtilis.language("class.feedback",clientLocale)%> </td><!-- ���� -->
					 <td width="120px" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!-- ����״̬ -->
					 <td width="120px" align="center"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td><!-- ��ϵ��ʽ -->
					 <td width="60px" align="center"><%=LocalUtilis.language("message.process",clientLocale)%> </td><!-- ���� -->
				</tr>  
			
<%
//�����ֶ�
Iterator iterator = rsList_details.iterator();
Integer needFeedback = new Integer(1);
Integer serial_no_detail;
Integer service_way = new Integer(0);
String service_way_name = "";
Integer targetCustId = new Integer(0);
String cust_name = "" ;			
String feedback ="Ҫ";
String serviceStatusName_details = "";
int status_flag_details = 0;

while(iterator.hasNext()){		
	map = (Map)iterator.next();
	
	serial_no_detail = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),null);
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	needFeedback = Utility.parseInt(Utility.trimNull(map.get("NeedFeedback")), new Integer(0));
	status_flag_details = Utility.parseInt(Utility.trimNull(map.get("Status")),0);
	serviceStatusName_details = Argument.getService_status_name(status_flag_details);
	service_way = Utility.parseInt(Utility.trimNull(map.get("ServiceWay")), new Integer(0));	
	service_way_name = Utility.trimNull(Argument.getDictParamName(1109,service_way.toString()));
	targetCustId = Utility.parseInt(Utility.trimNull(map.get("TargetCustID")), new Integer(0));	
	
	if(needFeedback.intValue()==2){
		 feedback ="��Ҫ";
	}
%>
			<tr class="tr1">
				<td align="center">
					<input type="checkbox" id="serial_no_<%= serial_no_detail%>" name="check_serial_no" value="<%= serial_no_detail%>" 
								onclick="javascript:setCustId(<%= serial_no_detail%>);" class="flatcheckbox"/>
					<input type="checkbox" id="target_custid_<%= serial_no_detail%>" name="check_target_custid" value="<%= targetCustId%>" 
								style="display:none" class="flatcheckbox"/> 
					<input type="checkbox" id="cust_name_<%= serial_no_detail%>" name="check_cust_name" value="<%= cust_name%>" 
								style="display:none" class="flatcheckbox"/> 
				</td>   
				 <!-- ����鿴�ͻ���Ϣ -->
				 <td align="left">&nbsp;&nbsp; 
				 	<a title="<%=LocalUtilis.language("message.viewCustomerInfo",clientLocale)%> " 
				 		href="javascript:showInfo(<%= targetCustId%>);" class="a2"><font color="red"><%=cust_name%></font></a>
				 </td>
				 <TD align="center"><%=Utility.trimNull(map.get("CARD_ID")) %></TD>
				 <td align="center"><%= feedback%></td>   
				 <td align="center"><%= serviceStatusName_details%></td>      
				 <td align="center"><%= service_way_name%></td>
				 <td align="center">  
		           	<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" style="cursor:hand" width="16" height="16"
			           		onclick="javascript:treat_detail(<%=serial_no_detail%>,'<%= cust_name%>','<%=targetCustId%>');">
		         </td>
			</tr>
<%count++;
}%>

<%if(count==0){%>
<tr class="tr1"><td align="center" colspan="7"><%=LocalUtilis.language("message.noTask",clientLocale)%> </td></tr><!-- û����ϸ������Ҫ���� -->
<%}%>
		</table>
		<br>
	</div>	
<% serviceTask.remove();%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>