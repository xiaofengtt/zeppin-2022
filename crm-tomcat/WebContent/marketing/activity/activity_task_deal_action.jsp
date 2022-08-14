<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ȡ��ҳ�洫�ݲ���
Integer serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//���������
Integer transFlag = Utility.parseInt(Utility.trimNull(request.getParameter("transFlag")),new Integer(1));//1 ������б�2 ��鵵�б�,3��ҳ

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

String taskTitle = "";
String beginDate="";
String endDate = "";
Integer executor = new Integer(0);
String content = "";
String completeTime  = "";
String remark = "";
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
	DocumentFileToCrmDB file = null;
	if(request.getMethod().equals("POST")){
	    file = new DocumentFileToCrmDB(pageContext);
		file.parseRequest();
		//remark = Utility.trimNull(request.getParameter("remark"));
		//completeTime  = Utility.trimNull(request.getParameter("completeTime"));
		remark = Utility.trimNull(file.getParameter("remark"));
		completeTime  = Utility.trimNull(file.getParameter("completeTime"));
		vo = new  ActivityTaskVO();
		vo.setSerial_no(serial_no);
		vo.setTaskTitle(taskTitle);
		vo.setActivitySerial_no(activeSerialNo);
		vo.setExecutor(executor);
		vo.setBeginDate(beginDate);
		vo.setEndDate(endDate);
		vo.setContent(content);
		vo.setRemark(remark);
		vo.setCompleteTime(completeTime);
		vo.setTaskFlag(new Integer(2));
		vo.setInputMan(input_operatorCode);
		activityTaskLocal.modi(vo);   
		int attachment_modify_flag_value=Integer.parseInt(file.getParameter("attachment_modify_flag"));
		save_name=Utility.trimNull(file.getParameter("attachment_save_name"));
		if(attachment_modify_flag_value!=0){//0:�����ޱ仯��1��ԭ���и����������޸ģ���ɾ��ԭ�и�����2��ԭ���޸����������ϴ�������ɾ������
			if(attachment_modify_flag_value==1){
				attachmentVO.setSave_name(save_name);
				attachmentVO.setInput_man(input_operatorCode); 
				attachmentLocal.delete(attachmentVO);
				attachmentLocal.deleteFile(attachmentVO);
			}
			file.uploadAttchment(new Integer(2),"TActivitiesTask",serial_no,"",input_operatorCode);
		}
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

<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.activityTaskDealAction",clientLocale)%> </title>
<!--�����������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
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
		sl_alert("<%=LocalUtilis.language("message.doneSuccess",clientLocale)%> ��");	//����ɹ�
		CancelAction();
	}
}	

/*����*/
/**
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "activity_task_deal_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}
*/
//���淽����ȡpostֵ��ʱ����false

function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

/*��֤����*/
function validateForm(form){
	if(!sl_check(document.getElementById('completeTime'), "<%=LocalUtilis.language("class.completeTime",clientLocale)%> ", 20, 1))return false;//���ʱ��
	if(!sl_check(document.theform.remark, "<%=LocalUtilis.language("message.completeRemark",clientLocale)%> ",500,1)){return false;}	//�������
	
	return sl_check_update();		
}

/*����*/
function CancelAction(){
	var url;
	var transFlag = document.getElementById("transFlag").value;
	var activeSerialNo =  document.getElementById("activeSerialNo").value;
	
	if(transFlag==2){
		url = "activity_task_pigeonhole.jsp?q_activitySerialNo="+activeSerialNo;
	}
	else if(transFlag==3){
		url = "<%=request.getContextPath()%>/login/main.jsp?display_flag=1";
	}
	else {
		url = "activity_task_deal.jsp";
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
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="activeSerialNo" value="<%=activeSerialNo%>"/>
<input type="hidden" id="transFlag" name="transFlag" value="<%=transFlag%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.activityTaskDealAction",clientLocale)%> </b></font><!--�����������-->
</div>

<div style="position:absolute;width:100%;margin-left:10px;margin-top:10px">
	<div style="float:left; width:40%; ">
			 <p>
			  <table  id="table1" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC" class="product-list">
			 	<tr >
			 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeInfo2",clientLocale)%> </b></font></td>
						<!--�������Ϣ-->
			 	</tr>
			 	
				 <tr >	
					 <td width="40%"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCode",clientLocale)%> ��</font></td><!--����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_code%></font>  </td>
				 </tr>
				 
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ��</font></td><!--�����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_theme%></font> </td>
				 </tr>
				 
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ��</font></td><!--����-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_type_name%></font></td>
				 </tr>
				 
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.manageCode",clientLocale)%> ��</font></td><!--�������-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(active_manage_code)%></font> </td>
				 </tr>
				 
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.beginDate2",clientLocale)%> ��</font></td><!--���ʼʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_startDate%></font></td>
				 </tr>
				 
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate2",clientLocale)%> ��</font></td><!--�����ʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_endDate%></font></td>
				 </tr>
				 
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ��</font></td><!--����ͻ�Ⱥ-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_customer_type%></font></td>
				 </tr>
				 
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ��</font></td><!--��ƻ���Ŀ��-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_plan%></font></td>
				 </tr>
				 
			</table>
			<br>
	</div>
	
	<div style=" float:right; width:60%; ">
 		<p>
		 <table  id="table2" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC" class="product-list">
		 		<tr >
			 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeTaskTreat",clientLocale)%> </b></font></td><!--�������-->
			 	</tr>
			 	
		 		<tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskTitle",clientLocale)%> ��</font></td><!--��������-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= taskTitle%></font> </td>
				 </tr>
				 
			 	 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.executor2",clientLocale)%> ��</font></td><!--�����ִ����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(executor)%></font> </td>
				 </tr>
				 
			 	 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.planBeginTime",clientLocale)%> ��</font></td><!--�ƻ���ʼʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= beginDate%></font></td>
				 </tr>
				 
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.planEndTime",clientLocale)%> ��</font></td><!--�ƻ����ʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= endDate%></font></td>
				 </tr>
				 
			 	<tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.content",clientLocale)%> ��</font></td><!--�������ϸ����-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= content%></font></td>
				 </tr>
				 
 				<tr >
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.completeDate",clientLocale)%> ��&nbsp;</font></td><!--�������-->
					<td>
						<input type="text" name="completeTime" id="completeTime" size="30" value="<%= completeTime%>" readOnly/> 				
					</td>	
				</tr>
				<script language="javascript">
					laydate({elem:'#completeTime',format:'YYYY-MM-DD hh:mm:ss',istime:true});
				</script>
 				<tr >
					<td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.remark",clientLocale)%> ��&nbsp;</font></td><!--����㱨-->
					<td>
							<textarea rows="5" name="remark" onkeydown="javascript:nextKeyPress(this)" cols="80"><%= remark%></textarea>			
					</td>
				</tr>	
		<tr >
			<td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.existedAttachment",clientLocale)%></font></td> <!-- ���и��� �� -->
			<td colspan="3">
				<%if(isAttachmentExist){%>
					<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly"/>
					<a href="javascript:DownloadAttachment(<%=serial_no%>,2)">
               			<font size="2" face="΢���ź�"><%=LocalUtilis.language("class.viewAttachments",clientLocale)%></font> <!-- �鿴���� �� -->
               		</a>
               		<input type="hidden" name="attachment_modify_flag" value=0>
               		<input type="hidden" name="attachment_save_name" value=<%=save_name%>>
               		<button type="button"  class="xpbutton3"  id="btnCancel" name="btnCancel" onclick="javascript:ModifyAttachment();"><%=LocalUtilis.language("class.deleteAttachment",clientLocale)%></button><!-- ɾ������ -->
			</td>
		</tr>
		<tr >
          	        <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.appendAttachment",clientLocale)%></font></td><!-- ��Ӹ���: -->
                    <td colspan="3">       	
            	    <input type="file" name="file_info" size="60" disabled="disabled">&nbsp;<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%>" src="<%=request.getContextPath()%>/images/minihelp.gif"><!-- ѡ�񸽼��ļ� -->
                </td>
        </tr>
				<%}else{%>
					<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly">
					<input type="hidden" name="attachment_modify_flag" value=2> 
			</td>
		</tr> 
		 <tr id="reader2" >
          	        <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.appendAttachment",clientLocale)%></font></td><!-- ��Ӹ���: -->
                    <td colspan="3">       	
            	    <input type="file" name="file_info" size="60">&nbsp;<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%>" src="<%=request.getContextPath()%>/images/minihelp.gif"><!-- ѡ�񸽼��ļ� -->
                </td>
        </tr> 
				<%}%>
				<tr><td><br/></td></tr>
				<tr >
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
<% 
activityTaskLocal.remove();
activityLocal.remove();
attachmentLocal.remove();
 %>
</form>
</BODY>
</HTML>
