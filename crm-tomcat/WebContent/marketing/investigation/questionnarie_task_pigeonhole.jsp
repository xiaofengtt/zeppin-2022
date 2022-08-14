<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
String q_serviceWay = Utility.trimNull(request.getParameter("q_serviceWay"));
Integer q_serviceType = Utility.parseInt(request.getParameter("q_serviceType"),new Integer(64));
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
Integer q_actorId = Utility.parseInt(request.getParameter("q_actorId"),new Integer(1));
Integer q_service_status = Utility.parseInt(request.getParameter("q_service_status"), new Integer(0));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_serviceType="+q_serviceType;
tempUrl = tempUrl+"&q_serviceWay="+q_serviceWay;
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_service_status="+q_service_status;
tempUrl = tempUrl+"&q_actorId="+q_actorId;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int iCount = 0;
int iCurrent = 0;
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

//获得对象
ServiceTaskLocal local = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

vo.setServiceType(q_serviceType);
vo.setServiceWay(q_serviceWay);
vo.setStartDateTime(q_start_date);
vo.setEndDateTime(q_end_date);
vo.setStatus(q_service_status);

if(q_actorId.intValue()==1){
	vo.setInsertMan(input_operatorCode);
	vo.setManagerID(input_operatorCode);
}

if(q_actorId.intValue()==2){
	vo.setExecutor(input_operatorCode);
}

if(q_actorId.intValue()==3){
	vo.setExecutor(input_operatorCode);
}

IPageList pageList = local.query_page(vo,totalColumn,t_sPage,t_sPagesize);

//分页辅助参数
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
<title><%=LocalUtilis.language("menu.questionnarieTaskPigeonhole",clientLocale)%> </title>
<!--任务归档-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
	
	var q_service_status = document.getElementById("q_service_status").value;
	var tdId = "td"+q_service_status;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function changeStatus(flag){
	document.getElementById("q_service_status").value=flag;
	QueryAction();
}

//刷新
function refreshPage(){
 	QueryAction();
}

function validate(){
	if((document.getElementById("q_start_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){
		return false;//开始日期
	}	
	if((document.getElementById("q_end_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> "))){
		return false;//结束日期			
	}	

	syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
	syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);	
	
	if((document.getElementById("q_start_date").getAttribute("value")!="")&&(document.getElementById("q_end_date").getAttribute("value")!="")){
		var q_start_date = document.getElementById("q_start_date").value;
		var q_end_date = document.getElementById("q_end_date").value;
		
		if(q_end_date<q_start_date){
			sl_alert("<%=LocalUtilis.language("message.endDateError",clientLocale)%> ！");//结束日期不能小于开始日期
			return false;
		}
	}
	
	return true;	
}

/*查询功能*/
function QueryAction(){		
	if(validate()){	
		//url 组合
		var url = "questionnarie_task_pigeonhole.jsp?1=1";	
		var url = url + "&q_serviceWay=" + document.getElementById("q_serviceWay").value;
		var url = url +"&q_start_date="+document.getElementById("q_start_date").value;
		var url = url +"&q_end_date="+ document.getElementById("q_end_date").value;
		var url = url +"&q_service_status="+ document.getElementById("q_service_status").value;
		var url = url +"&q_actorId="+document.getElementById("q_actorId").value;
		
		disableAllBtn(true);		
		window.location = url;	
	}
}

/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "tr"+serial_no;
	var v_table = "tablePro"+serial_no;
	var v_flag = "flag_display"+serial_no;
	var v_div = "div"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
	}
}
//主信息查看
function showMenuView(serial_no,serviceType){
	var url = "<%=request.getContextPath()%>/affair/service/service_main_view.jsp?serviceType=64&serial_no="+serial_no+"&serviceType="+serviceType;	
	showModalDialog(url,'', 'dialogWidth:850px;dialogHeight:450px;status:0;help:0')
}

function showRemark(serial_no){
	var url = "<%=request.getContextPath()%>/affair/service/service_pigeonhole_remark.jsp?serial_no="+serial_no;	
	showModalDialog(url,'', 'dialogWidth:550px;dialogHeight:350px;status:0;help:0');
	showWaitting(0);
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="q_service_status" name="q_service_status" value="<%=q_service_status%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:550px;height:140px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> ：</td><!--开始日期-->
			<td  align="left">
				<input type="text" name="q_start_date_picker"  class=selecttext  id="q_start_date_picker"
				<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
			</td>
			<td  align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> ： </td><!--结束日期-->
			<td  align="left">
				<input type="text" name="q_end_date_picker" class=selecttext   id="q_end_date_picker"
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> ： </td><!--联系方式-->
			<td  align="left" colspan="3">
				<select name="q_serviceWay" id="q_serviceWay" style="width:123px">
					<%= Argument.getDictParamOptions(1109, q_serviceWay)%>
				</select>
			</td>		
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--确认-->	 				
			</td>
		</tr>		
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right" style="margin-right:5px;" class="btn-wrapper">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		<%} %><!--查询-->
	</div>
</div>

		<br/>
<div id="headDiv" style="margin-left:0px">
	<table cellSpacing="1" cellPadding="2" width="750px"  bgcolor="#CCCCCC" class="table-select" >
			<tr style="background:F7F7F7;">
				<td align="left">
					 <font size="2" face="微软雅黑">&nbsp;<%=LocalUtilis.language("class.roleSelection",clientLocale)%> ：&nbsp;&nbsp;</font><!--角色选择-->
					<select name = "q_actorId" id="q_actorId" onchange="javascript:QueryAction();"  style="width:150px">
						<%= Argument.getServiceActorOptions(q_actorId)%>
					</select>				
				</td>
				<td width="80px" align="center" id="td0" <%if (q_service_status.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
				<!--全部-->
				<td width="80px" align="center" id="td1" <%if (q_service_status.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(1);" class="a2"><%=LocalUtilis.language("message.undone",clientLocale)%> </a></font></td>
				<!--未处理-->
				<td width="80px" align="center" id="td2" <%if (q_service_status.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(2);" class="a2"><%=LocalUtilis.language("class.pending",clientLocale)%> </a></font></td>
				<!--待处理-->
				<td width="80px" align="center" id="td3" <%if (q_service_status.intValue()==3) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(3);" class="a2"><%=LocalUtilis.language("message.beingProcessed",clientLocale)%> </a></font></td>
				<!--处理中-->
				<td width="80px" align="center" id="td4" <%if (q_service_status.intValue()==4) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(4);" class="a2"><%=LocalUtilis.language("class.processed",clientLocale)%> </a></font></td>
				<!--已处理-->
				<td width="80px" align="center" id="td5" <%if (q_service_status.intValue()==5) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(5);" class="a2"><%=LocalUtilis.language("class.invalid",clientLocale)%> </a></font></td>
				<!--作废-->
			</tr> 
	</table>
</div>

<div style="margin-top:5px;">
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
		<tr class="trh">
			 <td width="*" align="center"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%> </td><!--任务标题-->
	         <td width="8%" align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!--任务类别-->	 
	         <td width="8%" align="center"><%=LocalUtilis.language("class.insertManName",clientLocale)%> </td><!--创建者-->
	         <td width="8%" align="center"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--客户经理-->          
	         <td width="8%" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!--任务状态-->	 
	         <td width="8%" align="center"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td><!--联系方式--> 
	         <td width="8%" align="center"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--开始时间--> 
	         <td width="8%" align="center"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--结束时间--> 
	         <td width="8%" align="center"><%=LocalUtilis.language("class.customerCount",clientLocale)%> </td><!--目标客户数-->
	         <td width="8%" align="center"><%=LocalUtilis.language("class.completeCount",clientLocale)%> </td><!--完成客户数-->       
	         <td width="8%" align="center"><%=LocalUtilis.language("messag.memo",clientLocale)%> </td><!--备忘-->	          
	     </tr>
<%
//声明字段
Integer serial_no;
String serviceTitle="";
Integer serviceType= new Integer(0);
String serviceTypeName = "";
String serviceWay="";
String serviceWayName = "";
int status_flag;
String serviceStatusName = "";
Integer managerID = new Integer(0);
String managerName="";
Integer customerCount = new Integer(0);//目标客户数
Integer completeCount = new Integer(0);//已完成客户数
String start_date = "";
String end_date =  "";
Integer insertMan = new Integer(0);
String insertManName = "";

if(list.size()>0){
Iterator iterator = list.iterator();

while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		
		serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
		serviceTitle = Utility.trimNull(map.get("ServiceTitle"));		
		serviceType = Utility.parseInt(map.get("ServiceType").toString(), new Integer(0));	
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		serviceWayName = Argument.getDictParamName(1109,serviceWay);
		status_flag = Utility.parseInt(Utility.trimNull(map.get("Status")),0);
		serviceStatusName = Argument.getService_status_name(status_flag);
		managerID = Utility.parseInt(Utility.trimNull(map.get("ManagerID")), new Integer(0));
		managerName = Argument.getManager_Name(managerID);		
		customerCount =  Utility.parseInt(Utility.trimNull(map.get("CustomerCount")),new Integer(0));
		completeCount =  Utility.parseInt(Utility.trimNull(map.get("CompleteCount")),new Integer(0));
		start_date =  Utility.trimNull(map.get("StartDateTime"));	
		end_date = Utility.trimNull(map.get("EndDateTime"));
		insertMan =  Utility.parseInt(Utility.trimNull(map.get("InsertMan")),new Integer(0));
		insertManName = Argument.getOperator_Name(insertMan);
		
		if(start_date.length()>0){
			start_date = start_date.substring(0,10);
		}
		
		if(end_date.length()>0){
			end_date = end_date.substring(0,10);
		}
%>
	<tr class="tr<%=iCount%2%>">
         <td align="left">
	         <a class="a2" href="#" onclick="javascript:showMenuView(<%=serial_no%>,<%=serviceType%>);">
	         	&nbsp;&nbsp;<%= serviceTitle%>
	         </a>
         </td>
         <td align="left">&nbsp;&nbsp;<%= serviceTypeName%></td>    
         <td align="center"><%= insertManName%></td>         
         <td align="center"><%= managerName%></td>
         <td align="center"><%= serviceStatusName%></td>
         <td align="center"><%= serviceWayName%></td>   
         <td align="center"><%= start_date%></td>
         <td align="center"><%= end_date%></td>             
         <td align="center"><%= customerCount%></td>
         <td align="center"><%= completeCount%></td>
 		 <td align="center" width="10%">         
 		 	<%if(input_operatorCode.equals(managerID)){%>     	
              	<a href="javascript:showRemark(<%=serial_no%>)">
               		<img border="0" src="<%=request.getContextPath()%>/images/FUNC20076.gif"  width="16" height="16">
               	</a>
            <%}%>
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
        <td align="center">&nbsp;</td>           
        <td align="center">&nbsp;</td>                         
     </tr>           
<%}
}%>      
		<tr class="trbottom">
			<td class="tdh" align="left" colspan="11"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			<!-- <td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>	
			<td align="center" class="tdh"></td>		
			<td align="center" class="tdh"></td> -->				
		</tr>
	</TABLE>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<% local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
