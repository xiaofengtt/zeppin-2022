<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer close_flag = Utility.parseInt(request.getParameter("close_flag"), new Integer(0));

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
Integer managerID =new Integer(0);
Integer serviceType = new Integer(0);
String managerName = "";
String serviceTypeName = "";
String start_date = "";
String end_date =  "";
String serviceTitle ="";
String serviceWay = "";
String serviceWayName = "";
String serviceRemark =  "";

//�༭ʱ��ʾ ������Ϣ
if(serial_no.intValue()>0){
	vo = new ServiceTaskVO();
	vo_details = new ServiceTaskVO();
	
	vo.setSerial_no(serial_no);
	vo.setInputMan(input_operatorCode);
	
	rsList = serviceTask.query(vo);
	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
	}
	
	if(rsMap!=null){
		start_date =  Utility.trimNull(rsMap.get("StartDateTime"));	
		end_date = Utility.trimNull(rsMap.get("EndDateTime"));
		serviceTitle = Utility.trimNull(rsMap.get("ServiceTitle"));	
		managerID = Utility.parseInt(Utility.trimNull(rsMap.get("ManagerID")), new Integer(0));
		managerName = Argument.getOpNameByOpCode(managerID);
		serviceWay = Utility.trimNull(rsMap.get("ServiceWay"));
		serviceWayName= Argument.getDictParamName(1109,serviceWay);
		serviceType = Utility.parseInt(rsMap.get("ServiceType").toString(), new Integer(0));
		serviceTypeName = Utility.trimNull(rsMap.get("ServiceTypeName"));
		serviceRemark = Utility.trimNull(rsMap.get("ServiceRemark"));
	}
	
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

%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.addTask",clientLocale)%> 2</title><!-- ���񴴽����� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
/*������ӿͻ���Ϣ*/
function addBatchCustomer(serial_no){
	var url = "<%=request.getContextPath()%>/affair/service/service_create_details_customers_batchAdd.jsp?serial_no="+serial_no+"&cust_id=-1";				
	if(showModalDialog(url,'', 'dialogWidth:650px;dialogHeight:660px;status:0;help:0')){
		sl_update_ok();
		refreshPage();
	}
}

function refreshPage(){
	var a = document.createElement("a");
	var v_serial_no= document.getElementById("serial_no").value;
	var v_close_flag = document.getElementById("close_flag").value;

    a.href = "<%=request.getContextPath()%>/affair/service/service_create_setAction2.jsp?serial_no="+v_serial_no+"&close_flag="+v_close_flag;
    document.body.appendChild(a);
    a.click();
}

/*ɾ������*/
function delCustomer(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ��");//��ѡ��Ҫɾ���ļ�¼
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "<%=request.getContextPath()%>/affair/service/service_create_details_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

function previorsAction(){
	var v_close_flag = document.getElementById("close_flag").value;
	var a = document.createElement("a");
	var v_serial_no= document.getElementById("serial_no").value;
	
    a.href = "<%=request.getContextPath()%>/affair/service/service_create_setAction1.jsp?serial_no="+v_serial_no+"&close_flag="+v_close_flag;
    document.body.appendChild(a);
    a.click();
}

function nextAction(){
	var v_close_flag = document.getElementById("close_flag").value;
	var a = document.createElement("a");
	var v_serial_no = document.getElementById("serial_no").value;
	var v_serviceType = document.getElementById("serviceType").value;

    a.href = "<%=request.getContextPath()%>/affair/service/service_create_setAction3.jsp?serial_no="+v_serial_no+"&serviceType="+v_serviceType+"&close_flag="+v_close_flag;
    document.body.appendChild(a);
    a.click();
}
</script>

</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no"  id="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="serviceType"  id="serviceType" value="<%=serviceType%>"/>
<input type="hidden" name="close_flag" id="close_flag" value="<%= close_flag%>" />

<div>
	<div align="left" class="page-title">
        <!-- ������� --><!-- ���񴴽� --><!-- ������Ϣ���� -->
		<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskAdd",clientLocale)%>>><%=LocalUtilis.language("message.taskSet",clientLocale)%></b></font>
	</div>	
	
	<div align="right" style="margin-right:20px;" class="btn-wrapper">
		<button type="button" class="xpbutton5" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.batchAddCustomers",clientLocale)%> " onclick="javascript:addBatchCustomer(<%=serial_no%>);"><%=LocalUtilis.language("message.addCustomers",clientLocale)%> </button><!-- ��ӿͻ� -->
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton5" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.deleteCustomer",clientLocale)%> " onclick="javascript:delCustomer(<%=serial_no%>);"><%=LocalUtilis.language("message.deleteCustomer",clientLocale)%> </button><!-- ɾ���ͻ� -->	
	</div>
</div>
<br/>
<div style="width:100%; height:165px;">
		<div style=" float:left; width:49%;">
				 <table  id="table1" cellSpacing="1" cellPadding="2" width="98%"  bgcolor="#CCCCCC" class="table-select">
				 			<tr style="background:F7F7F7;">
						 			<!-- ������Ϣ��Ҫ���� -->
                                    <td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> </b></font></td>
						 	</tr>
						 	
						 	 <tr style="background:F7F7F7;">	
								 <!-- ������� -->
                                 <td width="30%"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</font></td>
								 <td width="*"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTitle%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <!-- ������� -->
                                 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ��</font></td>
								 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceTypeName%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <!-- �ͻ����� -->
                                 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.accountManager",clientLocale)%> ��</font></td>
								 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= managerName%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <!-- ��ϵ��ʽ -->
                                 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font></td>
								 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceWayName%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <!-- ��ʼ���� -->
                                 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> ��</font></td>
								 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= start_date%></font>  </td>
							 </tr>
							 
							 <tr style="background:F7F7F7;">	
								 <!-- �������� -->
                                 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate",clientLocale)%> ��</font></td>
								 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= end_date%></font>  </td>
							 </tr>
						 	
						 	 <tr style="background:F7F7F7;">	
								 <!-- ������ϸ���� -->
                                 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ��</font></td>
								 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= serviceRemark%></font>  </td>
							 </tr>
				 </table>
		</div>
		
		<div style="float:right; width:49%;">
			<table cellSpacing="1" cellPadding="2" width="410px"  bgcolor="#CCCCCC">
					<tr style="background:6699FF;">
						 <td width="*" align="center"><input type="checkbox" name="btnCheckbox" class="selectAllBox"	
				onclick="javascript:selectAllBox(document.theform.check_serial_no,this);"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- �ͻ����� -->  
						 <td width="20%" align="center"><%=LocalUtilis.language("class.feedback",clientLocale)%> </td><!-- ���� -->
						 <td width="20%" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!-- ״̬ -->
					</tr>  
			</table>	
		<span id="tableList" style="overflow-y:auto;height:185;">
			<table border="0" width="410px" cellspacing="1" cellpadding="2" bgcolor="#CCCCCC">	
					<%
					//�����ֶ�
					String cust_name = "" ;	
					String feedback ="";
					String serviceStatusName_details = "";
					Integer needFeedback = new Integer(1);
					Integer serial_no_detail = new Integer(0);
					int status_flag_details = 0;

					if(rsList_details!=null&&rsList_details.size()>0){
						 Iterator iterator = rsList_details.iterator();
										
						while(iterator!=null&&iterator.hasNext()){		
							rsMap_details = (Map)iterator.next();
							iCount++;

							if(rsMap_details!=null){
									serial_no_detail = Utility.parseInt(Utility.trimNull(rsMap_details.get("Serial_no")),null);
									cust_name = Utility.trimNull(rsMap_details.get("CUST_NAME"));	
									needFeedback = Utility.parseInt(Utility.trimNull(rsMap_details.get("NeedFeedback")),new Integer(0));
									status_flag_details = Utility.parseInt(Utility.trimNull(rsMap_details.get("Status")),0);
									serviceStatusName_details = Argument.getService_status_name(status_flag_details);
							}
							
							if(needFeedback.intValue()==1){
								 feedback = LocalUtilis.language("message.need",clientLocale);//��Ҫ
							}else if(needFeedback.intValue()==2){
								feedback =LocalUtilis.language("message.no",clientLocale);//����Ҫ
							}
					%>
					<tr class="tr1">
						 <td align="center" width="*" >		
					       <table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="check_serial_no" value="<%= serial_no_detail%>" class="flatcheckbox"/> 
									</td>
									<td width="90%" align="left">&nbsp;&nbsp; <%= cust_name%></td>
								</tr>
							</table></td>         
						 <td align="center" width="20%" ><%= feedback%></td>  
						 <td align="center" width="20%" ><%= serviceStatusName_details%></td>        
					</tr>
				<%}
				}%>
					
				<%if(iCount==0){%>
					<tr class="tr1"><td align="center" colspan="4"><%=LocalUtilis.language("message.noDetails",clientLocale)%> </td></tr><!-- û�������ϸ������Ϣ -->
				<%}%>
				</table>
			</span>
		</div>
</div>

<div align="right" style="float:right; width:49%; margin-top:5px; margin-right:10px; ">		
		<button type="button" class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.up",clientLocale)%> " onclick="javascript: previorsAction();"><%=LocalUtilis.language("message.up",clientLocale)%> </button>
		&nbsp;&nbsp;&nbsp;		
		<button type="button" class="xpbutton3" accessKey=c id="btnColse" name="btnColse" title="<%=LocalUtilis.language("message.next",clientLocale)%> " onclick="javascript: nextAction();"><%=LocalUtilis.language("message.next",clientLocale)%> </button>
</div>	
<% serviceTask.remove();%>
</form>
</BODY>
</HTML>