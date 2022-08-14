<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获取页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
Integer edit_flag = Utility.parseInt(request.getParameter("edit_flag"), new Integer(0));
Integer q_serviceType = Utility.parseInt(request.getParameter("q_serviceType"),new Integer(0));
String q_serviceWay = Utility.trimNull(request.getParameter("q_serviceWay"));
Integer executor = Utility.parseInt(request.getParameter("executor"),new Integer(0));
//声明辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
boolean bSuccess = false;

String qs = Utility.getQueryString(request, new String[]{"q_serviceType", "q_serviceWay", "executor"});
sUrl += "&"+qs;

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//获得明细列表
vo.setServiceType(q_serviceType);
vo.setServiceWay(q_serviceWay);
vo.setTaskSerialNO(serial_no);
vo.setNeedFeedBack(new Integer(1));
vo.setStatus(new Integer(3));
vo.setInputMan(input_operatorCode);
vo.setExecutor(executor);

IPageList pageList = serviceTask.query_detailsa(vo,totalColumn,t_sPage,t_sPagesize);

//分页辅助参数
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;

%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("message",clientLocale)%> </title><!-- 任务反馈 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />	
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
		initQueryCondition();
	};

//刷新
function refreshPage(){
	disableAllBtn(true);
	var _pagesize = document.getElementsByName("pagesize")[0];		
	window.location = '<%=request.getContextPath()%>/affair/service/service_feedback.jsp?<%=qs%>&page=1&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
}
	
/*编辑*/
function ModiAction(serial_no,TargetCustID){				
	var url = "<%=request.getContextPath()%>/affair/service/service_feedback_edit.jsp?serial_no_details="+serial_no + "&targetCustID="+TargetCustID;	
	dialog("<%=LocalUtilis.language("message.taskManager",clientLocale)%>\>><%=LocalUtilis.language("message",clientLocale)%> ","iframe:"+url,"850px","500px","show","<%=request.getContextPath()%>"); //任务管理 任务反馈
}
	
/*查询功能*/
function QueryAction(){			
	var _pagesize = document.getElementsByName("pagesize")[0];
	
    //url 组合
	var url = "<%=request.getContextPath()%>/affair/service/service_feedback.jsp?page=1&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");	
	url += "&q_serviceType=" + document.getElementById("q_serviceType").getAttribute("value");
	url += "&q_serviceWay=" + document.getElementById("q_serviceWay").getAttribute("value");		
	disableAllBtn(true);		
	window.location = url;
}
/*关闭框*/
function closeDialogDiv(){
	j$("#closeImg")[0].onclick();
}
/*批量反馈*/
function batchModiAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
        //请选定要处理的明细信息
		sl_alert("<%=LocalUtilis.language("message.chooseDetailInfo",clientLocale)%> ！");
		return false;
	}
	else{
		var check_serial_no = document.getElementsByName("check_serial_no");
		var checkedValues = "";
		
		for(var i=0;i<check_serial_no.length;i++){			
			if(check_serial_no[i].checked){
				var checkedValues = checkedValues + check_serial_no[i].value + "|"
			}
		}

		checkedValues = checkedValues.substring(0,checkedValues.length-1);
		
		var url = "service_feedback_batchEdit.jsp?checkedValues="+checkedValues;
		var ret = showModalDialog(url,'','dialogWidth:600px;dialogHeight:300px;status:0;help:0');
		
		if(ret==1){
			window.location.reload();
		}		
	}
}

function exportInfo()
{
	if(sl_confirm("导出数据"))
	{
	  	setWaittingFlag(false);
	 	var url ="service_export.jsp?q_serviceType=" + document.getElementById('q_serviceType').getAttribute('value');
							+ "&q_serviceWay=" + document.getElementById('q_serviceWay').getAttribute('value');
	 	location = url;
	}
}

function exportSelectedInfo() {
	var arr = document.theform.check_serial_no;
	if (arr==null) {
		sl_alert("没有可以导出的数据！");
		return;
	}

	var s = "";
	if (arr.length==null) {
		if (arr.checked) s += arr.value;

	} else {
		for (var i=0; i<arr.length; i++) {
			if (arr[i].checked) {
				if (s!="") s += ",";
				s += arr[i].value;
			}
		}
	}

	if (s=="") {
		sl_alert("请选择要导出的数据！");
		return;
	}

	if(sl_confirm("导出数据")) {
	  	setWaittingFlag(false);
	 	var url ="service_export.jsp?serial_no="+s
							+ "&q_serviceType=" + document.getElementById('q_serviceType').getAttribute('value');
							+ "&q_serviceWay=" + document.getElementById('q_serviceWay').getAttribute('value');
	 	location = url;
	}
}
</script>
</HEAD>
<body class="body body-nox">
<form id="theform" name="theform" method="post">

<div id="queryCondition" class="qcMain" style="display:none;width:530px;height:100px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> : </td><!-- 任务类别 -->
			<td  align="left">
				<select name="q_serviceType" id="q_serviceType" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%= Argument.getService_typeName(q_serviceType) %>
				</select>
			</td>	
			<td  align="right"><%=LocalUtilis.language("class.serviceWayName",clientLocale)%> : </td><!-- 任务方式 -->
			<td  align="left">
				<select id="q_serviceWay" name="q_serviceWay" style="width:120px">
					<%= Argument.getDictParamOptions(1109, q_serviceWay)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
                <button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>		
	</table>
	
</div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
			
	<div align="right" class="btn-wrapper">
		
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
        <!-- 查询 -->
		<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		<%}%>
		&nbsp;&nbsp;&nbsp;
		<!-- 导出 -->
		<%if (input_operator.hasFunc(menu_id, 107)) {%>
		<button type="button" class="xpbutton3" onclick="javascript:exportInfo();">全部导出</button>&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton3" onclick="javascript:exportSelectedInfo();">选定导出</button>&nbsp;&nbsp;&nbsp;
		<%} %>
		<!-- 反馈 -->
		<button type="button" class="xpbutton3" accessKey=f id="feedbackButton" name="feedbackButton" title="<%=LocalUtilis.language("class.feedback",clientLocale)%> " onclick="javascript:batchModiAction();"><%=LocalUtilis.language("class.feedback",clientLocale)%> (<u>F</u>)</button>
	</div>

<br/>
	<table border="0" width="100%" cellspacing="1" cellpadding="2"
		class="tablelinecolor">
		<tr class="trh">
			<td align="center">
				 <input type="checkbox" name="btnCheckbox" class="selectAllBox" 
				 		onclick="javascript:selectAllBox(document.theform.check_serial_no,this);">
			</td>
			<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!-- 客户名称 -->
			<td align="center"><%=LocalUtilis.language("class.serviceWayName",clientLocale)%> </td><!-- 任务方式 -->
			<td align="center"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%>  </td><!-- 任务标题 -->
			<td align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!-- 任务类别 -->
			<td align="center"><%=LocalUtilis.language("class.executeTime",clientLocale)%> </td><!-- 任务时间 -->
			<td align="center"><%=LocalUtilis.language("class.taskFeedback",clientLocale)%> </td><!-- 反馈任务 -->
		</tr>
<%
	
    //声明字段
	Iterator iterator = list.iterator();
	Integer serial_no_detail;
	String cust_name = "" ;
	String post_address="";
	Integer needFeedback = new Integer(1);
	String feedback ="";
	String serviceWay="";
	String serviceWayName="";
	String serviceTypeName = "";
	String executeTime = "";
	Integer targetCustID;
	String serviceTitle="";
	while(iterator.hasNext()){		
		map = (Map)iterator.next();
		serial_no_detail = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),null);
		targetCustID = Utility.parseInt(Utility.trimNull(map.get("TargetCustID")),null);
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		executeTime = Utility.trimNull(map.get("ExecuteTime"));
		serviceWayName = Argument.getDictParamName(1109,serviceWay);
		serviceTitle = Utility.trimNull(map.get("ServiceTitle"));	
		if(executeTime.length()>0){
			executeTime = executeTime.substring(0,16);
		}
		
		if(needFeedback.intValue()==1){
			 feedback = LocalUtilis.language("message.need2",clientLocale);//要
		}else if(needFeedback.intValue()==2){
			feedback = LocalUtilis.language("message.notNeed",clientLocale);//不要
		}
        iCount++;	
%>
	<tr class="tr<%=iCount%2%>">
			<td align="center">
				<input class="selectAllBox" type="checkbox" 
					   name="check_serial_no" value="<%= serial_no_detail%>" class="flatcheckbox"/>
			</td>
			<td align="left">&nbsp;&nbsp;<%= cust_name%></td>
			<td align="center"><%= serviceWayName%></td>
			<td align="center"><%= serviceTitle%></td>
			<td align="center"><%= serviceTypeName%></td>
			<td align="center"><%= executeTime%></td>
			<td align="center">
				<%if (input_operator.hasFunc(menu_id, 102)) {%>
				<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" 
					 onclick="javascript:ModiAction(<%=serial_no_detail%>,<%=targetCustID%>);"
					 style="cursor:hand" align="absmiddle" width="16" height="16">
				<%} %>
			</td>
	</tr>
<%}%>
	
<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
	<tr class="tr0">
		<td align="center"></td>
		<td align="center"></td>	
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="center"></td>
	</tr>
<%}%>
	<tr class="trbottom">
         <!-- 合计  --><!-- 项 -->
		<td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
	</tr>
</table>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
    <% serviceTask.remove();%>
</form>
</BODY>
</HTML>