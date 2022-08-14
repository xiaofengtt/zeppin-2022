<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
Integer close_flag = Utility.parseInt(request.getParameter("close_flag"), new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer managerID = Utility.parseInt(request.getParameter("managerID"), input_operatorCode);
Integer serviceType = Utility.parseInt(request.getParameter("serviceType"), new Integer(0));
String serviceTypeName = Utility.trimNull(request.getParameter("serviceTypeName"));
String start_date = Utility.trimNull(request.getParameter("start_date"));
String end_date =  Utility.trimNull(request.getParameter("end_date"));
String serviceTitle =Utility.trimNull(request.getParameter("service_title")); 
String serviceWay = Utility.trimNull(request.getParameter("serviceWay"),"110901");
String serviceRemark = Utility.trimNull(request.getParameter("serviceRemark"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer ques_id = Utility.parseInt(request.getParameter("ques_id"), new Integer(0));
Integer tempID = Utility.parseInt(request.getParameter("tempID"), new Integer(0));
Integer autoFlag = Utility.parseInt(request.getParameter("auto_flag"), new Integer(0));
String tempTitle = Utility.trimNull(request.getParameter("tempTitle"));
Integer executorId = Utility.parseInt(request.getParameter("executor"), input_operatorCode);
int nextFlag = Utility.parseInt(request.getParameter("nextFlag"),1);
//������������
Integer showFlag =  Utility.parseInt(Utility.trimNull(request.getParameter("showFlag")),new Integer(1));//��ҳ����ʾ���
Integer actionFlag1 = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag1")),new Integer(1));//������ʶ 1.������2.�޸�

boolean bSuccess = false;
List rsList = null;
Map rsMap = null;

//��ö���
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;

//������Ϣ
if(request.getMethod().equals("POST")){	
	vo = new ServiceTaskVO();
	
	vo.setServiceTitle(serviceTitle);
	vo.setManagerID(managerID);
	vo.setServiceWay(serviceWay);
	vo.setServiceType(serviceType);
	vo.setStartDate(start_date);
	vo.setEndDate(end_date);
	vo.setInputMan(input_operatorCode);
	vo.setServiceRemark(serviceRemark);
	vo.setQues_id(ques_id);
	vo.setProductId(product_id);
	vo.setTempID(tempID);
	vo.setTempTitle(tempTitle);
	vo.setAutoFlag(autoFlag);
	vo.setExecutor(executorId);
	
	if(actionFlag1.intValue()==1){
		serial_no = (Integer)serviceTask.append(vo);
		bSuccess = true;
	}
	else if(actionFlag1.intValue()==2&&serial_no.intValue()>0){
		vo.setSerial_no(serial_no);
		serviceTask.modi(vo);
		bSuccess = true;
	}
}
//�༭ʱ��ʾ ������Ϣ
if(serial_no.intValue()>0){
	vo = new ServiceTaskVO();
	
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
		serviceWay = Utility.trimNull(rsMap.get("ServiceWay"));
		serviceType = Utility.parseInt(Utility.trimNull(rsMap.get("ServiceType")),new Integer(0));
		serviceTypeName = Utility.trimNull(rsMap.get("ServiceTypeName"));
		serviceRemark = Utility.trimNull(rsMap.get("ServiceRemark"));
		ques_id = Utility.parseInt(Utility.trimNull(rsMap.get("QUES_ID")), new Integer(0));
		product_id = Utility.parseInt(Utility.trimNull(rsMap.get("PRODUCT_ID")),new Integer(0));
		tempID = Utility.parseInt(Utility.trimNull(rsMap.get("TempID")), new Integer(0));
		autoFlag = Utility.parseInt(Utility.trimNull(rsMap.get("AutoFlag")), new Integer(0));
		tempTitle = Utility.trimNull(rsMap.get("TempTitle"));
		executorId = Utility.parseInt(Utility.trimNull(rsMap.get("Executor")), new Integer(0));
	}
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
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">

<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.addTask",clientLocale)%> 1</title><!-- ���񴴽����� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>

	/*����*/
	function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].action="service_create_setAction1.jsp";
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*��֤����*/
	function validateForm(form){
		var i ;
		var saveFlag = true;
		
		if(!sl_check(document.getElementsByName('service_title')[0],"<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ",60,1)){ return false;}//�������
	    if(!sl_checkChoice(document.getElementsByName('s_serviceType')[0],"<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ")){ return false;}//�������
	
		for(var folderNo=1;saveFlag&&folderNo<3;folderNo++) {  
				i = folderNo;
				show(i);
				saveFlag = checkfolder(folderNo);	
		}
		
		if(saveFlag){
				show(1); 
				return sl_check_update();		
		}
	}
	
	function checkfolder(folder_no){
		if(folder_no ==1){
				if(!sl_checkChoice(document.getElementsByName('managerID')[0],"<%=LocalUtilis.language("class.accountManager",clientLocale)%> ")){ return false;}//�ͻ�����
				if(!sl_checkChoice(document.getElementsByName('serviceWay')[0],"<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ")){ return false;}//��ϵ��ʽ	
				var v_serviceWay = document.getElementsByName('serviceWay')[0].value;
				if(v_serviceWay=='110905'){
					if(!sl_check(document.getElementsByName('tempTitle')[0],"<%=LocalUtilis.language("class.tempTitle",clientLocale)%> ",50,1)){ return false;}//����ģ��
				}
	
				if(!sl_check(document.getElementById('start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//��ʼʱ��
				if(!sl_check(document.getElementById('end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//����ʱ��
				
				if(document.getElementById('end_date').value<document.getElementById('start_date').value){
					sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//�������ڲ��ܱȿ�ʼ������
					return false;
				}
				
				if(!sl_check(document.getElementsByName('serviceRemark')[0],"<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ",500,0)){ return false;}//������ϸ����
		}
		else if(folder_no ==2){
				var v_serviceType =  document.getElementById("serviceType").value;
				
				if(v_serviceType==64){
					if(!sl_checkChoice(document.getElementsByName('ques_id')[0],"<%=LocalUtilis.language("class.questionaireName",clientLocale)%> ")){ return false;}//�ʾ�����
				}
		}
		
		return true;
	}
	
	function show(parm){
		//document.getElementById("showFlag").value = parm;

	   for(i=1;i<3;i++) {  
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
	//��ʼ��ҳ��
	window.onload = function(){
		show('<%=showFlag%>');
		showTr();
		var v_bSuccess = document.getElementById("bSuccess").value;
		var v_serial_no = document.getElementById("serial_no").value;
		var v_serviceType =  document.getElementById("serviceType").value;
		
		if(v_serial_no>0){
			document.getElementById("btnNext").style.display = "";
			//document.getElementById("btnSave").style.display = "none";
			document.getElementById("actionFlag1").value = 2;
			<%if(bSuccess){%>
				nextAction();	
			<%}%>	
		}
		
		if(v_serviceType>0){
			document.getElementById("s_serviceType").disabled=true;
		}
		
		if(v_bSuccess=="true"){			
			sl_update_ok();
		}
	}

	function nextAction(){
		var v_close_flag = document.getElementById("close_flag").value;
		var v_serial_no = document.getElementById("serial_no").value;
		var url = "<%=request.getContextPath()%>/affair/service/service_create_setAction2.jsp?close_flag="+v_close_flag+"&serial_no="+v_serial_no;
		if(v_close_flag==0){
			var a = document.createElement("a");
		    a.href = url;
		    document.body.appendChild(a);
		    a.click();	
		}
		else if(v_close_flag==1){
			window.parent.document.getElementById("_dialogIframe").src=url;
		}
	}
	
	function changeType(){
		var v_serviceType = document.getElementById("s_serviceType").value;
		document.getElementById("serviceType").value = v_serviceType;
	}
	/*ȡ��*/
	function CancelAction(){
		var v_close_flag = document.getElementById("close_flag").value;
		if(v_close_flag==0){
			window.returnValue=null;
			window.close();
			window.parent.location.reload();
		}
		else if(v_close_flag==1){
			window.parent.location.reload();
			//window.parent.document.getElementById("closeImg").click();	
		}
	}
	function chooseAction(){
		var url = "<%=request.getContextPath()%>/affair/sms/smsTemplate_choose.jsp";
		var ret = showModalDialog(url,'','dialogWidth:610px;dialogHeight:450px;status:0;help:0');
		if(ret!=null){
		if(ret.length>0){
			var rets = ret.split("_");
			document.getElementById("tempID").value=rets[0];
			document.getElementById("tempTitle").value=rets[1];
		}}

		showWaitting(0);
	}
	function showTr(){
		var serviceWay = document.getElementsByName('serviceWay')[0].value;
		if(serviceWay=='110905'){
			document.getElementById("smsTr").style.display = "";
		}
		else{
			document.getElementById("smsTr").style.display = "none";
		}
	}
	function clearAction(){
		document.getElementById("tempID").value=0;
		document.getElementById("tempTitle").value="";
	}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<!--�����־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="serial_no"  id="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="showFlag" id="showFlag" value="<%= showFlag%>" />
<input type="hidden" name="close_flag" id="close_flag" value="<%= close_flag%>" />
<input type="hidden" name="actionFlag1" id="actionFlag1" value="<%= actionFlag1%>" />

<div align="left" class="page-title page-title-noborder">
    <!-- ������� --><!-- ���񴴽� --><!-- ������Ϣ���� -->
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskAdd",clientLocale)%>>><%=LocalUtilis.language("message.taskSet",clientLocale)%> </b></font>
</div>
<br/>
<div class="btn-wrapper" >
	<font  size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</font><!-- ������� -->
	<input type="text" name="service_title" style="width:200px"  value="<%=serviceTitle%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
	<font  size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ��</font><!-- ������� -->
	<select name="s_serviceType" id="s_serviceType" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:changeType();">
		<%= Argument.getService_typeName(serviceType) %>
	</select>	
	<input type="hidden" name="serviceType" id="serviceType" value="<%=serviceType%>" />
</div>
<div align="left" >
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline"  >
		<TBODY>
			<TR class="tr-tabs" >							
				<TD vAlign=top>&nbsp;</TD>					
				<!-- ������Ϣ -->
                <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
				<!-- ��չ��Ϣ -->
                <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.extendedInformation",clientLocale)%> </TD>						
			</TR>
		</TBODY>
	</TABLE>
</div>
<br/>
<div  id="r1" align="left"  style="display:none; ">
	<table border="0" width="100%" cellSpacing="0" cellPadding="0"  class="product-list">
		<tr >
			<td  width="130px"><font size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</font></td><!-- �ͻ����� -->
			<td  width="*">
				<select name="managerID" style="width:180px">
					<%=Argument.getManager_Value(managerID)%>
				</select>	
			</td>
			
			<td width="130px"><font size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</font> </td><!-- ��ϵ��ʽ -->
			<td width="*">
				<select name="serviceWay" style="width:180px" onchange="javascript:showTr();">
					<%= Argument.getDictParamOptions(1109, serviceWay)%>
				</select>
			</td>
		</tr>

		<tr id="smsTr" style=" display:none">
			<td width="130px"><font size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.tempTitle",clientLocale)%> :</font></td><!-- ����ģ�� -->
			<td width="*">
				<input type="text" name="tempTitle" id="tempTitle" size="30"  value="<%=tempTitle%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<!-- ѡ�� -->
                &nbsp;&nbsp;&nbsp;	<button type="button" class="xpbutton2" id="btnChoTemp" name="btnChoTemp" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
                <!-- ��� -->
				&nbsp;<button type="button" class="xpbutton2" id="btnClear" name="btnClear" onclick="javascript:clearAction();"><%=LocalUtilis.language("message.clear",clientLocale)%> </button>
				<input type="hidden" name="tempID" id="tempID"  value="<%=tempID%>" />
			</td>
			<td width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.autoFlag",clientLocale)%> :</font></td><!-- �Ƿ��Զ� -->
			<td width="130px"><input type="checkbox" name="auto_flag" <%if(autoFlag.intValue()==1) out.print("checked");%> value="1" /></td>
		</tr>
		
		<tr style="">
			<td width="130px"><font size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.startDate",clientLocale)%> :</font> </td><!-- ��ʼ���� -->
			<td  width="*">
				<input type="text" name="start_date" id="start_date" size="30" onclick="javascript:setday(this);"  value="<%= start_date%>" readOnly/> 			
			</td>	
			
			<td width="130px"><font size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.endDate",clientLocale)%> :</font></td><!-- �������� -->
			<td width="*">
				<input type="text" name="end_date" id="end_date" size="30" onclick="javascript:setday(this);" value="<%= end_date%>"  readOnly/> 		
			</td>
		</tr>
		
		<tr style="">
			<td width="130px"><font size="2" face="΢���ź�"><font color="red">*</font><%=LocalUtilis.language("class.executor",clientLocale)%> :</font> </td><!-- ִ���� -->
			<td  width="*">
				<select name="executor" style="width:180px">
					<%=Argument.getManager_Value(executorId)%>
				</select>	
			</td>
			<td width="130px"></td>
			<td width="*">
			</td>
		</tr>		
		
		<tr style="">
				<td width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> :</font></td><!-- ������ϸ���� -->
				<td  width="*" colspan="3">
						<textarea rows="5" name="serviceRemark"  style="width:95%"><%= serviceRemark%></textarea>		
				</td>
		</tr>
	</table>
</div>

<div  id="r2" align="left"  style="display:none; ">
	<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC" class="table-popup">
		<tr style="">
			<td  width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName2",clientLocale)%> :</font></td><!-- �ʾ����� -->
			<td  width="*" colspan="3">
				<select name="ques_id"  class="productname" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getQuesInfoOption(ques_id)%>
				</select>	
			</td>
		</tr>
		<tr style="">	
			<td width="130px"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> :</font> </td><!-- ��Ʒ���� -->
			<td width="*" colspan="3">
				<SELECT name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
				</SELECT>
			</td>
		</tr>	
	</table>
</div>
<br/>
<div align="right">
    <!-- ���� -->
	<button type="button" class="xpbutton3" <%if(nextFlag!=1) out.print("style='display:none;'");%> accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;	
    <!-- ��һ�� -->
	<button type="button" class="xpbutton3" style="display:none;" accessKey=n id="btnNext" name="btnNext" title="<%=LocalUtilis.language("message.next",clientLocale)%> " onclick="javascript: nextAction();"><%=LocalUtilis.language("message.next",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;	
    <!-- �ر� -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
</div>
<% serviceTask.remove();%>
</form>
</BODY>
</HTML>