<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ȡ��ҳ�洫�ݲ���
Integer serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//���������
Integer transFlag = Utility.parseInt(Utility.trimNull(request.getParameter("transFlag")),new Integer(1));//1 �����б�2 ��ҳ

//�����ֶ�
//----���Ϣ
Integer activeSerialNo = new Integer(0);
Integer active_manage_code = new Integer(0);
String active_manageMan = "";
String active_type ="";
String active_type_name ="";
String active_theme ="";
String active_startDate ="";
String active_endDate ="";
String active_customer_type ="";
String active_plan ="";
String active_code= "";
//----�������Ϣ
String taskTitle = "";
String beginDate="";
String endDate = "";
Integer executor = new Integer(0);
String content = "";
String completeTime  = "";
String remark = "";
String check_options = "";
String check_man = Argument.getOpNameByOpCode(input_operatorCode);
//������ر���
String origin_name="";
String save_name="";
//������������
boolean bSuccess = false;
List taskList = null;
List activeList = null;
Map taskMap = null;
Map activeMap = null;

//��ö���
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO vo =null;
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(2));
List attachmentList=null;
Map attachmentMap=null;
boolean isAttachmentExist=false;

//��ȡ�������Ϣ
if(serial_no.intValue()>0){
	vo = new  ActivityTaskVO();
	vo.setSerial_no(serial_no);	
	taskList = activityTaskLocal.query(vo);
	
	if(taskList.size()>0){
		taskMap = (Map)taskList.get(0);
	}
	
	if(taskMap!=null){
			activeSerialNo = Utility.parseInt(Utility.trimNull(taskMap.get("Active_Serial_no")),new Integer(0));
			beginDate = Utility.trimNull(taskMap.get("BeginDate"));
			endDate = Utility.trimNull(taskMap.get("EndDate"));
			executor = Utility.parseInt(Utility.trimNull(taskMap.get("Executor")),new Integer(0));
			content = Utility.trimNull(taskMap.get("Content"));
			remark =  Utility.trimNull(taskMap.get("Remark"));
			completeTime =  Utility.trimNull(taskMap.get("CompleteTime"));
			taskTitle = Utility.trimNull(taskMap.get("ActiveTaskTitle"));
	}	
	
	//��ȡ���Ϣ
	if(activeSerialNo.intValue()>0){	
		activeList = activityLocal.load(activeSerialNo);
		if(activeList.size()>0){
			activeMap = (Map)activeList.get(0);
		}	
		
		if(activeMap!=null){
			active_type_name = Utility.trimNull(activeMap.get("ACTIVE_TYPE_NAME"));
			active_type = Utility.trimNull(activeMap.get("ACTIVE_TYPE"));
			active_theme = Utility.trimNull(activeMap.get("ACTIVE_THEME"));
			active_startDate = Utility.trimNull(activeMap.get("START_DATE")).toString().substring(0,16).trim();
			active_endDate = Utility.trimNull(activeMap.get("END_DATE")).toString().substring(0,16).trim();
			active_manage_code = Utility.parseInt(Utility.trimNull(activeMap.get("MANAGE_CODE")),new Integer(0));
			active_customer_type  = Utility.trimNull(activeMap.get("CUSTOMER_TYPE"));
			active_plan = Utility.trimNull(activeMap.get("ACTIVE_PLAN"));
			active_code = Utility.trimNull(activeMap.get("ACTIVE_CODE"));
			attachmentVO.setInput_man(input_operatorCode);
			attachmentList=attachmentLocal.load(attachmentVO);
			if(!attachmentList.isEmpty()){
			    isAttachmentExist=true;
				//attachmentMap=(Map)attachmentList.get(0);
				attachmentMap=(Map)attachmentList.get(attachmentList.size()-1);
				origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
		    	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
			}else{
			    isAttachmentExist=false;
				origin_name=LocalUtilis.language("class.noAttachment",clientLocale);//�˻����û���ϴ�����
			}
		}	
	}

	//�����޸�
	if(request.getMethod().equals("POST")){
		check_options = Utility.trimNull(request.getParameter("check_options"));
		
		vo = new  ActivityTaskVO();
		vo.setTaskTitle(taskTitle);
		vo.setSerial_no(serial_no);
		vo.setTaskFlag(new Integer(3));	
		vo.setActivitySerial_no(activeSerialNo);
		vo.setExecutor(executor);
		vo.setBeginDate(beginDate);
		vo.setEndDate(endDate);
		vo.setContent(content);
		vo.setRemark(remark);
		vo.setCompleteTime(completeTime);
		vo.setCheckMan(input_operatorCode);
		vo.setCheckOptions(check_options);
		vo.setInputMan(input_operatorCode);

		activityTaskLocal.modi(vo);
		
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
<title><%=LocalUtilis.language("menu.activityEndAction",clientLocale)%> </title>
<!--������������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
/*��������*/	
window.onload = function(){
	var table1_height = document.getElementById("table1").clientHeight;
	var table2_height = document.getElementById("table2").clientHeight;
	if(table1_height<table2_height){
		document.getElementById("table1").style.height = table2_height;
	}	
	
	var v_bSuccess = document.getElementById("bSuccess").value;	
	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.checkOK",clientLocale)%> ��");	//��˳ɹ�
		CancelAction();
	}
}	

/*����*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "activity_task_check_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}

/*��֤����*/
function validateForm(form){
	if(!sl_check(document.theform.check_options, "<%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> ",500,1)){return false;}//������		
	return sl_check_update();		
}

/*����*/
function CancelAction(){
	var transFlag = document.getElementById("transFlag").value;
	if(transFlag==2){
		url = "/login/main.jsp?display_flag=1";
	}
	else {
		url = "activity_task_check.jsp";
	}	
	window.location.href = url;	
}
/*�޸ĸ�������*/
function ModifyAttachment(){
    if(window.confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //ȷ��ɾ��������
			document.theform.attachment_modify_flag.value = "1";;
			document.getElementById("file_info").removeAttribute("disabled"); 
			document.getElementById("oldAttachment").value="<%=LocalUtilis.language("message.deleteTaskAttachment",clientLocale)%>";//�������󣬴˻����ԭ�и�������ɾ��
    }
}
/*�鿴��������*/
function DownloadAttachment(serial_no,table_id){
	var url = "<%=request.getContextPath()%>/tool/download.jsp?serial_no="+serial_no+"&&table_id="+table_id;		
	window.location.href = url;	
}

</script>
</HEAD>
<BODY class="body body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="transFlag" name="transFlag" value="<%=transFlag%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.activityEndAction",clientLocale)%> </b></font><!--������������-->
</div>

<div style="position:absolute;float:left;width:98%;margin-left:10px;margin-top:10px" class="product-list">
	<div style="float:left; width:40%; ">
			 <p>
			  <table  id="table1" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
			 	<tr style="background:F7F7F7;">
			 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeInfo2",clientLocale)%> </b></font></td><!--�������Ϣ-->
			 	</tr>
			 	
				 <tr style="background:F7F7F7;">	
					 <td width="40%"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCode",clientLocale)%> ��</font></td><!--����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_code%></font>  </td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ��</font></td><!--�����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_theme%></font> </td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ��</font></td><!--����-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_type_name%></font></td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.manageCode",clientLocale)%> ��</font></td><!--�������-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(active_manage_code)%></font> </td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.beginDate2",clientLocale)%> ��</font></td><!--���ʼʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_startDate%></font></td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate2",clientLocale)%> ��</font></td><!--�����ʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_endDate%></font></td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ��</font></td><!--����ͻ�Ⱥ-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_customer_type%></font></td>
				 </tr>
				 
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ��</font></td><!--��ƻ���Ŀ��-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_plan%></font></td>
				 </tr>
				 
			</table>
			<br>
	</div>
	
	<div style="position:absolute; float:right; width:60%; ">
 		<p>
		 <table  id="table2" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
		 		<tr style="background:F7F7F7;">
			 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeTaskTreat",clientLocale)%> </b></font></td><!--�������-->
			 	</tr>
			 	
		 		<tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskTitle",clientLocale)%> ��</font></td><!--��������-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= taskTitle%></font> </td>
				 </tr>
				 
			 	 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.executor2",clientLocale)%> ��</font></td><!--�����ִ����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(executor)%></font> </td>
				 </tr>
				 
			 	 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.planBeginTime",clientLocale)%> ��</font></td><!--�ƻ���ʼʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= beginDate%></font></td>
				 </tr>
				 
				  <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.planEndTime",clientLocale)%> ��</font></td><!--�ƻ����ʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= endDate%></font></td>
				 </tr>
				 
			 	<tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.content",clientLocale)%> ��</font></td><!--�������ϸ����-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= content%></font></td>
				 </tr>
				 
 				<tr style="background:F7F7F7;">
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.completeDate",clientLocale)%> ��&nbsp;</font></td><!--�������-->
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= completeTime%></font></td>	
				</tr>
				
 				<tr style="background:F7F7F7;">
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.remark",clientLocale)%> ��&nbsp;</font></td><!--����㱨-->
 					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= remark%></font></td>
				</tr>		
				
				 <tr style="background:F7F7F7;">
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ��&nbsp;</font></td><!--������-->
 					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= check_man%></font></td>
				</tr>		
				
				 <tr style="background:F7F7F7;">	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> ��</font></td><!--������-->
					 <td><font size="2" face="΢���ź�">
					 	<textarea rows="7" name="check_options" onkeydown="javascript:nextKeyPress(this)" cols="80"><%= check_options%></textarea>	
					 </td>
				</tr>
				<tr style="background:F7F7F7;">
			<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.existedAttachment",clientLocale)%></font></td> <!-- ���и��� �� -->
			<td colspan="3">
				<%if(isAttachmentExist){%>
					<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly"/>
					<a href="javascript:DownloadAttachment(<%=serial_no%>,2)">
               			<font size="2" face="΢���ź�"><%=LocalUtilis.language("class.viewAttachments",clientLocale)%></font> <!-- �鿴���� �� -->
               		</a>
			</td>
		</tr>
				<%}else{%>
					<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly">
					<input type="hidden" name="attachment_modify_flag" value=2> 
					</td>
		</tr> 
				<%}%>
				<tr style="background:F7F7F7;">
			 			<td colspan="2" align="right">				
			 				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
							&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
							<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--����-->
						</td>
			 	</tr>
		</table>
		<br>
	</div>
</div>
<% activityTaskLocal.remove(); %>
</form>
</BODY>
</HTML>

