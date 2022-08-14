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
Integer q_manageCode = Utility.parseInt(Utility.trimNull(request.getParameter("q_manageCode")),new Integer(0));
Integer q_activitySerialNo = Utility.parseInt(Utility.trimNull(request.getParameter("q_activitySerialNo")),new Integer(0));
String q_taskTitle = Utility.trimNull(request.getParameter("q_taskTitle"));
Integer q_taskflag =Utility.parseInt(Utility.trimNull(request.getParameter("q_taskflag")),new Integer(0));

//url����
String tempUrl = "";
tempUrl = tempUrl+"&q_begin_date="+q_begin_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_completeTimeUp="+q_completeTimeUp;
tempUrl = tempUrl+"&q_completeTimeDown="+q_completeTimeDown;
tempUrl = tempUrl+"&q_manageCode="+q_manageCode;
tempUrl = tempUrl+"&q_taskTitle="+q_taskTitle;
tempUrl = tempUrl+"&q_activitySerialNo="+q_activitySerialNo;
tempUrl = tempUrl+"&q_taskflag="+q_taskflag;
sUrl = sUrl + tempUrl;

//ҳ���ø�������
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

//��ö���
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO vo = new ActivityTaskVO();

//���ò�ѯ����
vo.setExecutor(input_operatorCode);
vo.setActivitySerial_no(q_activitySerialNo);
vo.setTaskTitle(q_taskTitle);
vo.setBeginDateQuery(q_begin_date);
vo.setEndDateQuery(q_end_date);
vo.setTaskFlag(q_taskflag);
vo.setManagerCode(q_manageCode);

vo.setCompleteDateUp(q_completeTimeUp);
vo.setCompleteDateDown(q_completeTimeDown);

//������Ϣ
IPageList pageList = activityTaskLocal.pageList_query(vo,totalColumn,t_sPage,t_sPagesize);

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
<title><%=LocalUtilis.language("menu.activityTaskPigeonhole",clientLocale)%> </title>
<!--�����鵵-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*��������*/	
window.onload = function(){
	initQueryCondition();
	var q_taskflag = document.getElementById("q_taskflag").value;
	var tdId = "td"+q_taskflag;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
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
			sl_alert("<%=LocalUtilis.language("class.completeTimeDown",clientLocale)%> ��");//�������ڲ���С�ڿ�ʼ����
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
		var url = "activity_task_pigeonhole.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
		var url = url + "&q_begin_date=" + document.getElementById("q_begin_date").getAttribute("value");
		var url = url + "&q_end_date=" + document.getElementById("q_end_date").getAttribute("value");
		var url = url + "&q_completeTimeUp=" + document.getElementById("q_completeTimeUp").getAttribute("value");
		var url = url + "&q_completeTimeDown=" + document.getElementById("q_completeTimeDown").getAttribute("value");
		var url = url +"&q_taskTitle="+document.getElementById("q_taskTitle").getAttribute("value");
		var url = url +"&q_manageCode="+document.getElementById("q_manageCode").getAttribute("value");
		var url = url +"&q_activitySerialNo="+document.getElementById("q_activitySerialNo").getAttribute("value");
		var url = url +"&q_taskflag="+document.getElementById("q_taskflag").getAttribute("value");
		
		disableAllBtn(true);		
		window.location = url;
	}
}

/*ˢ��*/
function refreshPage(){
	QueryAction();
}

/*�޸ķ���*/
function ModiAction(serial_no){				
	var url = "activity_task_action.jsp?actionFlag=2&transFlag=3&serial_no="+serial_no;	
	window.location.href = url;	
}

function DealAction(serial_no){				
	var url = "activity_task_deal_action.jsp?transFlag=2&serial_no="+serial_no;	
	window.location.href = url;	
}

/*ɾ������*/
function DelAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ��");//��ѡ��Ҫɾ���ļ�¼
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "activity_task_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

function changeStatus(flag){
	document.getElementById("q_taskflag").value=flag;
	QueryAction();
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
/*�鿴��������*/
function DownloadAttachment(serial_no,table_id){
	var url = "<%=request.getContextPath()%>/tool/download.jsp?serial_no="+serial_no+"&&table_id="+table_id;		
	window.location.href = url;	
}
</script>
</head>
<body class="body body-nox">

<form id="theform" name="theform" method="get">
<input type="hidden" id="q_taskflag" name="q_taskflag" value="<%=q_taskflag%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:520px;height:170px;">
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
				<td  align="right"><%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ��</td><!--������-->
				<td  align="left">
					<select name="q_manageCode" id="q_manageCode" onkeydown="javascript:nextKeyPress(this)" style="width:122px">
						<%=Argument.getManager_Value(q_manageCode)%>
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
				<td  align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.to",clientLocale)%> <!--��-->&nbsp;&nbsp;</td>
			<td>
				<input type="text" style="width:120px" name="q_completeTimeDown_picker" class=selecttext 
				<%if(q_completeTimeDown==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_completeTimeDown)%>"<%}%> >
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_completeTimeDown_picker,theform.q_completeTimeDown_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_completeTimeDown" id="q_completeTimeDown" value=""/>	
			</td>
		</tr>
		
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--ȷ��-->(<u>O</u>)</button>
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
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		<!--��ѯ--><%}%>
	</div>	
</div>		
<br/>
<div id="headDiv">
	<table cellSpacing="1" cellPadding="2" width="600px"  bgcolor="#CCCCCC" class="table-select">
			<tr style="background:F7F7F7;">
				<td align="center"><!--�����-->
					<font size="2" face="΢���ź�">&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%>��&nbsp;&nbsp;</font>
					<select name = "q_activitySerialNo" id="q_activitySerialNo" onchange="javascript:QueryAction();"  style="width:250px">
						<%=Argument.getActivityOptions(q_activitySerialNo)%>
					</select>				
				</td>
				<td width="60px" align="center" id="td0" <%if (q_taskflag.intValue()==0) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeStatus(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
				<!--ȫ��-->
				<td width="60px" align="center" id="td1" <%if (q_taskflag.intValue()==1) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeStatus(1);" class="a2"><%=LocalUtilis.language("message.undone",clientLocale)%> </a></font></td>
				<!--δ����-->
				<td width="60px" align="center" id="td2" <%if (q_taskflag.intValue()==2) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeStatus(2);" class="a2"><%=LocalUtilis.language("message.toBeCheck",clientLocale)%> </a></font></td>
				<!--�����-->
				<td width="60px" align="center" id="td3" <%if (q_taskflag.intValue()==3) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeStatus(3);" class="a2"><%=LocalUtilis.language("message.checked",clientLocale)%> </a></font></td>
				<!--�����-->
			</tr> 
	</table>
</div>
<br/>
<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" >
		<tr class="trh">
			<td align="center" width="10%"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> </td><!--�������-->
			<td align="center" width="*"><%=LocalUtilis.language("class.activeThemeList",clientLocale)%> </td><!--���������-->
			<td align="center" width="8%"><%=LocalUtilis.language("class.responsibleMan",clientLocale)%> </td><!--������-->
			<td align="center" width="8%"><%=LocalUtilis.language("class.taskFlagName",clientLocale)%> </td><!--����״̬-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--��ʼʱ��-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--����ʱ��-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.completeTime",clientLocale)%> </td><!--���ʱ��-->
			<td align="center" width="6%"><%=LocalUtilis.language("message.detailInfo",clientLocale)%> </td><!--��ϸ��Ϣ-->
			<td align="center" width="6%"><%=LocalUtilis.language("message.process",clientLocale)%> </td><!--����-->
		</tr>
<%
//�����ֶ�
Iterator iterator = list.iterator();
Integer serial_no;
String active_code;
String active_theme;
String beginDate;
String endDate;
String manageMan;
Integer taskFlag = new Integer(0);
String taskFlagName = "";
String taskTitle = "";
String content;	
String completeTime ="";
String remark = "";
Integer checkMan= new Integer(0);
String checkOptions = "";
//����������ر���
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
String origin_name="";
String save_name="";
boolean isAttachmentExist=false;	

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();

	serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
	taskFlag =  Utility.parseInt(Utility.trimNull(map.get("TaskFlag")),new Integer(0));
	taskFlagName = Argument.getActivityTaskFlagName(taskFlag);
	taskTitle = Utility.trimNull(map.get("ActiveTaskTitle"));
	active_code = Utility.trimNull(map.get("ACTIVE_CODE"));
	active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));
	beginDate = Utility.trimNull(map.get("BeginDate"));
	endDate = Utility.trimNull(map.get("EndDate"));
	manageMan = Utility.trimNull(map.get("MANAGE_MAN"));
	content = Utility.trimNull(map.get("Content"));
	completeTime =  Utility.trimNull(map.get("CompleteTime"));
	remark = Utility.trimNull(map.get("Remark"));
	checkMan = Utility.parseInt(Utility.trimNull(map.get("CheckMan")),new Integer(0));
	checkOptions  = Utility.trimNull(map.get("Check_Options"));
	//��ø�����Ϣ
	attachmentVO.setDf_serial_no(serial_no);
	attachmentVO.setDf_talbe_id(new Integer(2));//������1���������2
	attachmentVO.setInput_man(input_operatorCode);
	List attachmentList=null;
	Map attachmentMap=null;
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
					<td align="left">&nbsp;&nbsp;<%=taskTitle%></td>
					<td align="left">&nbsp;&nbsp;<%= active_theme%></td>        
					<td align="center"><%= manageMan%></td>    
					<td align="center"><%= taskFlagName%></td>            
					<td align="center"><%= beginDate%></td>   
					<td align="center"><%= endDate%></td>             
					<td align="center"><%= completeTime%></td>                 
					 <td align="center">
			         	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);">
			         		<IMG id="taskImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
			         	</button>
			         	<input type="hidden" id="taskFlag_display<%=serial_no%>" name="taskFlag_display<%=serial_no%>" value="0">
			         </td>   
					 <td align="center" width="10%">   
					 	<%if(taskFlag.intValue()!=3){%>           	
		              	<a href="javascript:DealAction(<%=serial_no%>)">
		               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
		               	</a>
		               	<%}%>
		             </td>         
				</tr>
				
			 <tr id="taskTr<%=serial_no%>" style="display: none">
				<td align="center" bgcolor="#FFFFFF" colspan="9" >
					<div id="taskDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
						<table id="taskTablePro<%=serial_no%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">							
							<tr style="background:F7F7F7;">
								<td width="10%">&nbsp;&nbsp;<%=LocalUtilis.language("class.taskContent",clientLocale)%> ��</td><!--��������-->
								<td><%= content%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTaskRemark",clientLocale)%> ��</td><!--��ɻ㱨-->
								<td><%= remark%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> ��</td><!--������-->
								<td><%= checkOptions%></td>
							</tr>
							<tr style="background:F7F7F7;">
								<td  width="15%">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTaskAttachment",clientLocale)%> ��</td><!--����񸽼�-->
								<%if(isAttachmentExist){%>
									<td  width="*"><a href="javascript:DownloadAttachment(<%=serial_no%>,2)"><%= origin_name%></a></td>
							    <%}else{%>
							    	<td  width="*"><%=origin_name%></td>
							    <%}%>
							</tr>
						</table>
					</div>
				</td>
			</tr>
<%}%>

	<%for(int i=0;i<(t_sPagesize-iCount);i++){%>     	
	         <tr class="tr0">
	            <td align="center">&nbsp;</td>
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
			<td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
	
		</tr>
		</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<% 
	activityTaskLocal.remove(); 
	attachmentLocal.remove();
%>
</form>

</body>
</html>



