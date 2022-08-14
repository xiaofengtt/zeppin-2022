<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//ȡ��ҳ�洫�ݲ���
Integer serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//���������
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(1));//1 �½���2 �༭
Integer transFlag = Utility.parseInt(Utility.trimNull(request.getParameter("transFlag")),new Integer(1));//1 ������б�2 ��б�
Integer q_activitySerialNo = Utility.parseInt(Utility.trimNull(request.getParameter("q_activitySerialNo")),new Integer(0));

//�����ֶ�
//----���Ϣ
Integer manage_code = new Integer(0);
String active_type ="";
String active_type_name ="";
String active_theme ="";
String beginDate ="";
String endDate ="";
String customer_type ="";
String active_plan ="";
String active_code= "";
Integer active_flag = new Integer(0);
String active_flag_name = "";
//--�������Ϣ
Integer activitySerialNo = q_activitySerialNo;
String taskTitle = "";
Integer executor = input_operatorCode;
String taskBeginDate ="";
String taskEndDate ="";
String content="";

//������������
boolean bSuccess = false;
int iCount = 0;
String[] totalColumn = new String[0];
List list = null;
List tasklist = null;
List actionList = null;
Map map = null;
Map taskMap = null;
Map actionMap = null;

//��ö���
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO vo_show = new ActivityTaskVO();
ActivityTaskVO vo_action = new ActivityTaskVO();

//�������޸���Ϣ
if(request.getMethod().equals("POST")&&actionFlag.intValue()==1){
	vo_action = new ActivityTaskVO();
	taskTitle = Utility.trimNull(request.getParameter("taskTitle"));
	executor = Utility.parseInt(Utility.trimNull(request.getParameter("executor")),new Integer(0));
	taskBeginDate  = Utility.trimNull(request.getParameter("beginDate"));
	taskEndDate = Utility.trimNull(request.getParameter("endDate"));
	content = Utility.trimNull(request.getParameter("content"));
	
	vo_action.setActivitySerial_no(activitySerialNo);
	vo_action.setTaskFlag(new Integer(1));
	vo_action.setTaskTitle(taskTitle);
	vo_action.setExecutor(executor);
	vo_action.setBeginDate(taskBeginDate);
	vo_action.setEndDate(taskEndDate);
	vo_action.setContent(content);
	vo_action.setInputMan(input_operatorCode);
	
	activityTaskLocal.append(vo_action);
	bSuccess = true;
}
else if(request.getMethod().equals("POST")&&actionFlag.intValue() == 2){
	vo_action = new ActivityTaskVO();
	taskTitle = Utility.trimNull(request.getParameter("taskTitle"));
	executor = Utility.parseInt(Utility.trimNull(request.getParameter("executor")),new Integer(0));
	taskBeginDate  = Utility.trimNull(request.getParameter("beginDate"));
	taskEndDate = Utility.trimNull(request.getParameter("endDate"));
	content = Utility.trimNull(request.getParameter("content"));
	
	vo_action.setSerial_no(serial_no);
	vo_action.setTaskTitle(taskTitle);
	vo_action.setActivitySerial_no(activitySerialNo);
	vo_action.setExecutor(executor);
	vo_action.setBeginDate(taskBeginDate);
	vo_action.setEndDate(taskEndDate);
	vo_action.setContent(content);
	vo_action.setTaskFlag(new Integer(1));
	vo_action.setInputMan(input_operatorCode);
	
	activityTaskLocal.modi(vo_action);
	bSuccess = true;
}

//���Ϊ�༭״̬ ��ȡ�������ϸ��Ϣ
if(actionFlag.intValue()==2){
	vo_action = new ActivityTaskVO();	
	vo_action.setSerial_no(serial_no);
	actionList = activityTaskLocal.query(vo_action);
	
	if(actionList.size()>0){
		actionMap = (Map)actionList.get(0);
	}
	
	if(actionMap!=null){
			q_activitySerialNo = Utility.parseInt(Utility.trimNull(actionMap.get("Active_Serial_no")),new Integer(0));
			taskTitle = Utility.trimNull(actionMap.get("ActiveTaskTitle"));
			taskBeginDate = Utility.trimNull(actionMap.get("BeginDate")).toString().substring(0,16);
			taskEndDate = Utility.trimNull(actionMap.get("EndDate")).toString().substring(0,16);
			executor = Utility.parseInt(Utility.trimNull(actionMap.get("Executor")),new Integer(0));
			content = Utility.trimNull(actionMap.get("Content"));
	}
}

//��ȡ���Ϣ
if(q_activitySerialNo.intValue()>0){	
	list = activityLocal.load(q_activitySerialNo);
	if(list.size()>0){
		map = (Map)list.get(0);
	}	
	
	if(map!=null){
		active_type_name = Utility.trimNull(map.get("ACTIVE_TYPE_NAME"));
		active_type = Utility.trimNull(map.get("ACTIVE_TYPE"));
		active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));
		beginDate = Utility.trimNull(map.get("START_DATE")).toString().substring(0,16).trim();
		endDate = Utility.trimNull(map.get("END_DATE")).toString().substring(0,16).trim();
		manage_code = Utility.parseInt(Utility.trimNull(map.get("MANAGE_CODE")),new Integer(0));
		customer_type  = Utility.trimNull(map.get("CUSTOMER_TYPE"));
		active_plan = Utility.trimNull(map.get("ACTIVE_PLAN"));
		active_code = Utility.trimNull(map.get("ACTIVE_CODE"));
		active_flag = Utility.parseInt(Utility.trimNull(map.get("ACTIVE_FLAG")),new Integer(0));
		active_flag_name = Argument.getActivityFlagName(active_flag);
	}	
	
	vo_show.setActivitySerial_no(q_activitySerialNo);
	//������Ϣ
	IPageList pageList = activityTaskLocal.pageList_query(vo_show,totalColumn,1,-1);
	tasklist = pageList.getRsList();
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.activityFeeAction",clientLocale)%> </title>
<!--���������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
/*��������*/	
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var actionFlag = document.getElementById("actionFlag").value;
	var active_flag = document.getElementById("active_flag").value;
	
	//����༭״̬ �����ѡ
	if(actionFlag==2){
		document.getElementById("q_activityOptions").disabled = true;
	}
	
	if(actionFlag == 1&& active_flag== 3 ){
		sl_alert("<%=LocalUtilis.language("message.chooseNew",clientLocale)%> ��");//����������ᣬ������ѡ������
		var transFlag = document.getElementById("transFlag").value;
		var url = "activity_task_action.jsp?transFlag="+transFlag;
		window.location.href = url;
	}

		if(v_bSuccess=="true"){
			sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ��");//����ɹ�
			
			if(actionFlag==1){
				if(confirm("<%=LocalUtilis.language("message.ifAppendActiveTask",clientLocale)%> ?")){//����Ҫ�����������
					changeActivity();
				}else{
					CancelAction();
				}		
			}
		}
//-------end
}
/*ѡ��*/
function changeActivity(){
	var transFlag = document.getElementById("transFlag").value;
	var q_activitySerialNo = document.getElementById("q_activityOptions").value;	
	document.getElementById("q_activitySerialNo").value = q_activitySerialNo;
	var url = "activity_task_action.jsp?q_activitySerialNo="+q_activitySerialNo+"&transFlag="+transFlag;
	window.location.href = url;
}

/*����*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "activity_task_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}

/*��֤����*/
function validateForm(form){
	if(!sl_checkChoice(document.theform.q_activitySerialNo,"<%=LocalUtilis.language("message.activeInfo",clientLocale)%> ")){return false;}//���Ϣ
	if(!sl_check(document.theform.taskTitle, "<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ",30,1)){return false;}//�������	
	if(!sl_checkChoice(document.theform.executor, "<%=LocalUtilis.language("class.executor",clientLocale)%> ")){return false;}//ִ����		
	if(!sl_check(document.getElementById('beginDate'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//��ʼʱ��
	if(!sl_check(document.getElementById('endDate'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//����ʱ��	
	if(document.getElementById('endDate').value<document.getElementById('beginDate').value){
		sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//�������ڲ��ܱȿ�ʼ������
		return false;
	}	
	if(!sl_check(document.theform.content, "<%=LocalUtilis.language("message.activeTaskInfo",clientLocale)%> ",200,1)){return false;}	//�������Ϣ	
	 
	return sl_check_update();		
}

/*����*/
function CancelAction(){
	var url;
	var transFlag = document.getElementById("transFlag").value;
	var q_activitySerialNo =  document.getElementById("q_activitySerialNo").value;

	if(transFlag==2){
		url = "activity_list.jsp";
	}
	else if(transFlag==3){
		url = "activity_task_pigeonhole.jsp?q_activitySerialNo="+q_activitySerialNo;
	}
	else {
		url = "activity_task_list.jsp";
	}
	
	window.location.href = url;	
}

/*�޸ķ���*/
function ModiAction(serial_no){				
	var url = "activity_task_action.jsp?actionFlag=2&transFlag=1&serial_no="+serial_no;	
	window.location.href = url;	
}

</script>
</HEAD>
<BODY class="body body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="active_flag" value="<%=active_flag%>"/>
<input type="hidden" id="transFlag" name="transFlag" value="<%=transFlag%>"/>
<input type="hidden" id="actionFlag" name="actionFlag" value="<%=actionFlag%>"/>
<input type="hidden" id="q_activitySerialNo" name="q_activitySerialNo"  value="<%= q_activitySerialNo%>" />

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.activityFeeAction",clientLocale)%> </b></font><!--���������-->
</div>
<br/>
<div style="float:left;width:98%;margin-left:10px;margin-top:10px">
		<div style="float:left; width:40%; ">
				<font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("message.activeChoose",clientLocale)%> ��</font><!--��ѡ��-->
				<select name = "q_activityOptions" id="q_activityOptions" onkeydown="javascript:nextKeyPress(this)" style="width:250px" onchange="javascript:changeActivity()" >
					<%=Argument.getActivityOptions(q_activitySerialNo)%>
				</select>				
			<p>
			 <table  id="table1" cellSpacing="1" cellPadding="2" width="100%" class="product-list" >
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
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(manage_code)%></font> </td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeFlagName",clientLocale)%> ��</font></td><!--�״̬-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=active_flag_name%></font> </td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.beginDate2",clientLocale)%> ��</font></td><!--���ʼʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= beginDate%></font></td>
				 </tr>
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate2",clientLocale)%> ��</font></td><!--�����ʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= endDate%></font></td>
				 </tr>
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ��</font></td><!--����ͻ�Ⱥ-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= customer_type%></font></td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ��</font></td><!--��ƻ���Ŀ��-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_plan%></font></td>
				 </tr>
			</table>
			<br>
		</div>
		
		<div style=" float:right; width:50%; margin-top:28px">
			<br>
			<table  id="table2" cellSpacing="1" cellPadding="2" width="100%" class="product-list">
					<tr >
				 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeTaskInfo",clientLocale)%> </b></font></td>
							<!--�������Ϣ-->
				 	</tr>
				 	
				 	<tr >
						<td  width="40%"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</font>&nbsp;</td><!--�������-->
						<td>&nbsp;&nbsp;
								<input type="text" name="taskTitle" size="30" value="<%= taskTitle%>" onkeydown="javascript:nextKeyPress(this)"/>				
						</td>
					</tr>
					
					<tr >
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.executor",clientLocale)%> ��&nbsp;</font></td><!--ִ����-->
						<td>&nbsp;&nbsp;	
							<select name="executor" onkeydown="javascript:nextKeyPress(this)" style="width:175px">
								<%=Argument.getManager_Value(executor)%>
							</select>		
						</td>	
					</tr>
					
					<tr >
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.startDate",clientLocale)%> ��&nbsp;</font></td><!--��ʼ����-->
						<td>&nbsp;&nbsp;
							<input type="text" name="beginDate" id="beginDate" size="30" onclick="javascript:setday(this);" value="<%= taskBeginDate%>" readOnly/> 				
						</td>	
					</tr>
					
					<tr >
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.endDate",clientLocale)%> ��&nbsp;</font></td><!--��������-->
						<td>&nbsp;&nbsp;
							<input type="text" name="endDate" id="endDate" size="30" onclick="javascript:setday(this);" value="<%= taskEndDate%>" readOnly/> 		
						</td>
					</tr>	
					
					<tr >
						<td><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.taskContent",clientLocale)%> ��&nbsp;</font></td><!--��������-->
						<td>&nbsp;&nbsp;
							<textarea rows="3" name="content" onkeydown="javascript:nextKeyPress(this)" cols="80"><%= content%></textarea>			
						</td>
					</tr>	
					
					<tr >
				 			<td colspan="2" align="right">				
				 				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;&nbsp;<!--����-->
							</td>
				 	</tr>
				 	
			</table>
		</div>
		
		<div align="center" >
			<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr class="trh">
					<td align="center" width="15%"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> </td><!--�������-->
					<td align="center" width="15%"><%=LocalUtilis.language("class.taskFlagName",clientLocale)%> </td><!--����״̬-->
					<td align="center" width="20%"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--��ʼʱ��-->
					<td align="center" width="20%"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--����ʱ��-->
					<td align="center" width="15%"><%=LocalUtilis.language("class.executor",clientLocale)%> </td><!--ִ����-->
					<td align="center" width="10%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
				</tr>
<%
if(tasklist!=null){
	Iterator iterator = tasklist.iterator();
	Integer serial_no_show;
	String activeTaskTitle;
	String activeTaskBeginDate;
	String activeTaskEndDate;
	Integer activeTaskExecutor;
	String activeTaskExectorName;
	Integer activeTaskFlag ;
	String activeTaskFlagName;
	
	while(iterator.hasNext()){
		iCount++;
		taskMap = (Map)iterator.next();

		serial_no_show = Utility.parseInt(Utility.trimNull(taskMap.get("Serial_no")),new Integer(0));
		activeTaskTitle = Utility.trimNull(taskMap.get("ActiveTaskTitle"));
		activeTaskBeginDate = Utility.trimNull(taskMap.get("BeginDate"));
		activeTaskEndDate = Utility.trimNull(taskMap.get("EndDate"));
		activeTaskExecutor = Utility.parseInt(Utility.trimNull(taskMap.get("Executor")),new Integer(0));
		activeTaskExectorName = Argument.getOpNameByOpCode(activeTaskExecutor);
		activeTaskFlag = Utility.parseInt(Utility.trimNull(taskMap.get("TaskFlag")),new Integer(0));
		activeTaskFlagName = Argument.getActivityTaskFlagName(activeTaskFlag);
		
		if(activeTaskBeginDate.length()>0){
			activeTaskBeginDate = activeTaskBeginDate.substring(0,16);
		}
		
		if(activeTaskEndDate.length()>0){
			activeTaskEndDate = activeTaskEndDate.substring(0,16);
		}
		
%>
			<tr >
				<td align="left" >&nbsp;&nbsp;<%= activeTaskTitle%></td>  
				<td align="center" ><%= activeTaskFlagName%></td> 	   
				<td align="center" ><%= activeTaskBeginDate%></td> 	
				<td align="center" ><%= activeTaskEndDate%></td> 
				<td align="center" ><%= activeTaskExectorName%></td> 
				 <td align="center">              	
				 	<%if(activeTaskFlag.intValue()!=3){%>
	              	<a href="javascript:ModiAction(<%=serial_no_show%>)">
	               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
	               	</a>
	               	<%}%>
	             </td>         
			</tr>
<%
	}
}
if(iCount==0){
%>
	<tr >
		<td align="center" colspan="6" ><%=LocalUtilis.language("message.noActiveTask",clientLocale)%> </td><!--û����ػ����-->    
	</tr>
<%
}
%>
		</table>		
		</div>
</div>
<% 
activityTaskLocal.remove();
activityLocal.remove();
 %>
</form>
</BODY>
</HTML>

