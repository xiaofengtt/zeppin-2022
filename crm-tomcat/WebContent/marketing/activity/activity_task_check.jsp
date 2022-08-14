<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ȡ��ҳ���ѯ����
Integer q_begin_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_begin_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
Integer q_completeTimeUp = Utility.parseInt(Utility.trimNull(request.getParameter("q_completeTimeUp")),new Integer(0));
Integer q_completeTimeDown = Utility.parseInt(Utility.trimNull(request.getParameter("q_completeTimeDown")),new Integer(0));
Integer q_executor = Utility.parseInt(Utility.trimNull(request.getParameter("q_executor")),new Integer(0));
Integer q_activitySerialNo = Utility.parseInt(Utility.trimNull(request.getParameter("q_activitySerialNo")),new Integer(0));
String q_taskTitle = Utility.trimNull(request.getParameter("q_taskTitle"));
String q_check_options = Utility.trimNull(request.getParameter("q_check_options"));

//url����
String tempUrl = "";
tempUrl = tempUrl+"&q_begin_date="+q_begin_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_completeTimeUp="+q_completeTimeUp;
tempUrl = tempUrl+"&q_completeTimeDown="+q_completeTimeDown;
tempUrl = tempUrl+"&q_executor="+q_executor;
tempUrl = tempUrl+"&q_taskTitle="+q_taskTitle;
tempUrl = tempUrl+"&q_check_options="+q_check_options;
tempUrl = tempUrl+"&q_activitySerialNo="+q_activitySerialNo;
sUrl = sUrl + tempUrl;

//ҳ���ø�������
int iCount = 0;
String[] totalColumn = new String[0];

//��ö���
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO vo = new ActivityTaskVO();

//���ò�ѯ����
vo.setTaskFlag(new Integer(2));
vo.setActivitySerial_no(q_activitySerialNo);
vo.setTaskTitle(q_taskTitle);
vo.setBeginDateQuery(q_begin_date);
vo.setEndDateQuery(q_end_date);
vo.setCompleteDateUp(q_completeTimeUp);
vo.setCompleteDateDown(q_completeTimeDown);
vo.setManagerCode(input_operatorCode);
vo.setExecutor(q_executor);

//������Ϣ
IPageList pageList = activityTaskLocal.pageList_query(vo,totalColumn,1,-1);

List list = pageList.getRsList();
Map map = null;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.activityTaskCheck",clientLocale)%> </title>
<!--���������б�-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx1.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*��������*/	
window.onload = function(){
	initQueryCondition();
	initQueryCondition1();
}

function validate(){
	if((document.getElementById("q_begin_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_begin_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){
	//��ʼ����
		return false;
	}	
	if((document.getElementById("q_end_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> "))){
	//��������
		return false;			
	}	
	
	if((document.getElementById("q_completeTimeUp").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_completeTimeUp_picker,"<%=LocalUtilis.language("class.completeTimeUp",clientLocale)%> "))){
	//���ʱ������
		return false;
	}	
	if((document.getElementById("q_completeTimeDown").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_completeTimeDown_picker,"<%=LocalUtilis.language("class.completeTimeDown",clientLocale)%> "))){
	//���ʱ������
		return false;			
	}	
	
	syncDatePicker(document.theform.q_begin_date_picker,document.theform.q_begin_date);			
	syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);	
	syncDatePicker(document.theform.q_completeTimeUp_picker,document.theform.q_completeTimeUp);	
	syncDatePicker(document.theform.q_completeTimeDown_picker,document.theform.q_completeTimeDown);	
	
	if((document.getElementById("q_begin_date").getAttribute("value")!="")&&(document.getElementById("q_end_date").getAttribute("value")!="")){
		var q_begin_date = document.getElementById("q_begin_date").value;
		var q_end_date = document.getElementById("q_end_date").value;
		
		if(q_end_date<q_begin_date){
			sl_alert("<%=LocalUtilis.language("message.endDateError",clientLocale)%> ��");//�������ڲ���С�ڿ�ʼ����
			return false;
		}
	}
	
	if((document.getElementById("q_completeTimeUp").getAttribute("value")!="")&&(document.getElementById("q_completeTimeDown").getAttribute("value")!="")){
		var q_completeTimeUp = document.getElementById("q_completeTimeUp").value;
		var q_completeTimeDown = document.getElementById("q_completeTimeDown").value;
		
		if(q_completeTimeDown<q_completeTimeUp){
			sl_alert("<%=LocalUtilis.language("message.completeTimeDownError",clientLocale)%> ��");//���ʱ�����޲���С������
			return false;
		}
	}
	return true;	
}

/*��ѯ����*/
function QueryAction(){		
	if(validate()){		
		//url ���
		var url = "activity_task_check.jsp?1=1" ;		
		var url = url + "&q_begin_date=" + document.getElementById("q_begin_date").getAttribute("value");
		var url = url + "&q_end_date=" + document.getElementById("q_end_date").getAttribute("value");
		var url = url + "&q_completeTimeUp=" + document.getElementById("q_completeTimeUp").getAttribute("value");
		var url = url + "&q_completeTimeDown=" + document.getElementById("q_completeTimeDown").getAttribute("value");
		var url = url +"&q_taskTitle="+document.getElementById("q_taskTitle").getAttribute("value");
		var url = url +"&q_executor="+document.getElementById("q_executor").getAttribute("value");
		var url = url +"&q_activitySerialNo="+document.getElementById("q_activitySerialNo").getAttribute("value");
		
		disableAllBtn(true);		
		window.location = url;
	}
}

function changeAction(){
		//url ���
		var url = "activity_task_check.jsp?1=1" ;	
		var url = url +"&q_activitySerialNo="+document.getElementById("q_activitySerialNo").getAttribute("value");
		
		disableAllBtn(true);		
		window.location = url;
}

/*��˷���*/
function CheckAction(serial_no){				
	var url = "activity_task_check_action.jsp?serial_no="+serial_no;	
	window.location.href = url;	
}

/*������˹���*/
function CheckBatchAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ��");//��ѡ��Ҫ��˵ļ�¼
		return false;
	}
	
	if(!sl_check(document.getElementById('q_check_options'), "<%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> ", 500, 1))return false;//������
	 
	if(confirm("<%=LocalUtilis.language("class.toCheckActiveTask",clientLocale)%> ��")){//��ȷ��Ҫ��˻������			
		var url = "activity_task_check_batch_action.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}
</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:500px;height:165px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">

		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> �� </td><!--��ʼ����-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_begin_date_picker"  class=selecttext 
				<%if(q_begin_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_begin_date)%>"<%}%> >
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_begin_date_picker,theform.q_begin_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_begin_date" id="q_begin_date" value=""/>	
			</td>
			<td  align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> ��</td><!--��������-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_end_date_picker" class=selecttext 
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		<tr>
				<td  align="right"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ��</td><!--�������-->
				<td  align="left">
						<input type="text" name="q_taskTitle" id="q_taskTitle" value="<%= q_taskTitle%>" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
				</td>	
				<td  align="right"><%=LocalUtilis.language("class.executor",clientLocale)%> ��</td><!--ִ����-->
				<td  align="left">
					<select name="q_executor" id="q_executor" onkeydown="javascript:nextKeyPress(this)" style="width:122px">
						<%=Argument.getManager_Value(q_executor)%>
					</select>	
				</td>	
		</tr>
		
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.completeTime",clientLocale)%> ��</td><!--���ʱ��-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_completeTimeUp_picker"  class=selecttext 
				<%if(q_completeTimeUp==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_completeTimeUp)%>"<%}%> >
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_completeTimeUp_picker,theform.q_completeTimeUp_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_completeTimeUp" id="q_completeTimeUp" value=""/>	
			</td>	
				<td  align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.to",clientLocale)%> &nbsp;&nbsp;</td><!--��-->
			<td>
				<input type="text" name="q_completeTimeDown_picker" class=selecttext 
				<%if(q_completeTimeDown==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_completeTimeDown)%>"<%}%> >
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_completeTimeDown_picker,theform.q_completeTimeDown_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_completeTimeDown" id="q_completeTimeDown" value=""/>	
			</td>
		</tr>
		
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--ȷ��-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>	
	</table>
</div>	

<!--�������-->
<div id="queryCondition1" class="qcMain" style="display:none;width:500px;height:180px;">
	<table  id="qcTitle1" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> </td><!--������-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose1" name="qcClose1" onclick="javascript:cancelQuery1();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
		<tr>	
			 <td align="center" colspan="4">
			 	<textarea rows="7" name="q_check_options" id="q_check_options" onkeydown="javascript:nextKeyPress(this)" cols="80"><%= q_check_options%></textarea>	
			 </td>
		</tr>
	
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:CheckBatchAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--ȷ��-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>	
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right" class="btn-wrapper">
	<font size=3>&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%>��</font> &nbsp;&nbsp;<!--�����-->
	<select name = "q_activitySerialNo" id="q_activitySerialNo" onchange="javascript:changeAction();"  style="width:250px">
		<%=Argument.getActivityOptions(q_activitySerialNo)%>
	</select>
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);">
		<%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--��ѯ-->
		&nbsp;&nbsp;&nbsp;<%}%>
		<%if (input_operator.hasFunc(menu_id, 103)) {%>
		<button type="button"  class="xpbutton3" accessKey=c id="queryButton1" name="queryButton1" title="<%=LocalUtilis.language("message.check",clientLocale)%> " onclick="javascript:void(0);">
		<%=LocalUtilis.language("message.check",clientLocale)%> (<u>C</u>)</button><!--���-->
		<%}%>
	</div>	
</div>		
<br>

<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="10%">
				<input type="checkbox" 
							name="btnCheckbox" 
							class="selectAllBox"	
							onclick="javascript:selectAllBox(document.theform.check_serial_no,this);" />
				<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> 
			</td><!--�������-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.activeThemeList",clientLocale)%> </td><!--���������-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--��ʼʱ��-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--����ʱ��-->
			<td align="center" width="8%"><%=LocalUtilis.language("class.executor",clientLocale)%> </td><!--ִ����-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.completeTime",clientLocale)%> </td><!--���ʱ��-->
			<td align="center" width="*"><%=LocalUtilis.language("class.remark",clientLocale)%> </td><!--����㱨-->
			<td align="center" width="8%"><%=LocalUtilis.language("message.check",clientLocale)%> </td><!--���-->
		</tr>
<%
//�����ֶ�
Iterator iterator = list.iterator();
Integer serial_no;
String taskTitle;
String active_code;
String active_theme;
String beginDate;
String endDate;
Integer executor;
String exectorName;
String content;	
String completeTime  = "";
String remark = "";

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();

	serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
	taskTitle = Utility.trimNull(map.get("ActiveTaskTitle"));
	active_code = Utility.trimNull(map.get("ACTIVE_CODE"));
	active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));
	beginDate = Utility.trimNull(map.get("BeginDate"));
	endDate = Utility.trimNull(map.get("EndDate"));
	executor = Utility.parseInt(Utility.trimNull(map.get("Executor")),new Integer(0));
	exectorName = Argument.getOpNameByOpCode(executor);
	content = Utility.trimNull(map.get("Content"));
	remark =  Utility.trimNull(map.get("Remark"));
	completeTime =  Utility.trimNull(map.get("CompleteTime"));
	
	if(beginDate.length()>0){
		beginDate = beginDate.substring(0,16);
	}
	
	if(endDate.length()>0){
		endDate = endDate.substring(0,16);
	}
	
	if(completeTime.length()>0){
		completeTime = completeTime.substring(0,16);
	}
%>		
			<tr class="tr<%=iCount%2%>">
					  <td align="center">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%"><input type="checkbox" name="check_serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
									<td width="90%" align="left">&nbsp;&nbsp;<%=taskTitle%></td>
								</tr>
							</table>
						</td>
						<td align="left">&nbsp;&nbsp;<%= active_theme%></td>              
						 <td align="center"><%= beginDate%></td>   
						 <td align="center"><%= endDate%></td>        
						 <td align="center"><%= exectorName%></td>             
						 <td align="center"><%= completeTime%></td>
						  <td align="left">&nbsp;&nbsp;<%= remark%></td>
						 <td align="center">              	
			              	<a href="javascript:CheckAction(<%=serial_no%>)">
			               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
			               	</a>
			             </td>         
				</tr>
<%}%>
	<%for(int i=0;i<(8-iCount);i++){%>     	
	         <tr class="tr0">
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>       
	            <td align="center">&nbsp;</td>
	            <td align="center">&nbsp;</td>            
	            <td align="center">&nbsp;</td>            
	         </tr>           
	<%}%> 

		<tr class="trbottom">
			<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
			<!-- <td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>	
			<td align="center" class="tdh"></td> -->	
		</tr>
		</table>
</div>
<% activityTaskLocal.remove(); %>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>




