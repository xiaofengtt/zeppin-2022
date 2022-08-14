<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer q_serviceType = Utility.parseInt(request.getParameter("q_serviceType"),new Integer(0));
String q_serviceWay = Utility.trimNull(request.getParameter("q_serviceWay"));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_serviceType="+q_serviceType;
tempUrl = tempUrl+"&q_serviceWay="+q_serviceWay;
sUrl = sUrl + tempUrl;

//页面用辅助变量
String[] totalColumn = new String[0];
Integer q_service_status = new Integer(1);//任务状态

//获得对象
ServiceTaskLocal local = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

vo.setServiceType(q_serviceType);
vo.setServiceWay(q_serviceWay);
vo.setStatus(q_service_status);
vo.setManagerID(input_operatorCode);
vo.setInputMan(input_operatorCode);

IPageList pageList = local.query_page(vo,totalColumn,1,-1);

//分页辅助参数
int iCount = 0;
int iCurrent = 0;
int iHiddenCount = 0;
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
<title><%=LocalUtilis.language("menu.assignTasks",clientLocale)%> </title><!-- 任务分配 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx1.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
/*启动加载*/
window.onload = function(){
	initQueryCondition();
	initQueryCondition1();
}

//刷新
function refreshPage(){
	QueryAction();
}

/*查询功能*/
function QueryAction(){
	var _pagesize = document.getElementsByName("pagesize")[0];

	//url 组合
	var url = "<%=request.getContextPath()%>/affair/service/service_allot.jsp?1=1" ;
	var url = url + "&q_serviceType=" + document.getElementById("q_serviceType").getAttribute("value");
	var url = url + "&q_serviceWay=" + document.getElementById("q_serviceWay").getAttribute("value");

	disableAllBtn(true);
	window.location = url;
}

/*分配功能*/
function allotAction(){
	<%if (user_id.intValue()==19) {%>
	if(!sl_checkChoice(document.getElementsByName('executorID')[0],"<%=LocalUtilis.language("class.executor",clientLocale)%> ")){ return false;}//执行人
	<%}%>
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
        //请选定要分配的记录
		sl_alert("<%=LocalUtilis.language("message.selectRecord",clientLocale)%> ！");
		return false;
	}

	var form = document.getElementsByName("theform")[0];
	form.action = "<%=request.getContextPath()%>/affair/service/service_allot_add.jsp";
	form.submit();
}
//查看任务信息
function showAction(serial_no,serviceType){
	var url = "<%=request.getContextPath()%>/affair/service/service_create_setAction3.jsp?showFlag2=1&&serial_no="+serial_no+"&serviceType="+serviceType;
	showModalDialog(url,'','dialogWidth:850px;dialogHeight:450px;status:0;help:0');
	showWaitting(0);
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:460px;height:100px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table>

	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> : </td><!-- 任务类别 -->
			<td  align="left">
				<select name="q_serviceType" id="q_serviceType" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%= Argument.getService_typeName(q_serviceType) %>
				</select>
			</td>
			<td  align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> : </td><!-- 联系方式 -->
			<td  align="left">
				<select id="q_serviceWay" name="q_serviceWay" style="width:120px">
					<%= Argument.getDictParamOptions(1109, q_serviceWay)%>
				</select>
			</td>
		</tr>
		<tr>
		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</div>

<div id="queryCondition1" class="qcMain" style="display:none;width:300px;height:90px;">
<table  id="qcTitle1" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
 	<tr>
		<td><%=LocalUtilis.language("message.selectExecutor",clientLocale)%> ：</td><!-- 选择执行人 -->
		<td align="right">
			<button type="button" class="qcClose" accessKey=c id="qcClose1" name="qcClose1" onclick="javascript:cancelQuery1();"></button>
		</td>
	</tr>
</table>

<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.executor",clientLocale)%> : </td><!-- 执行人 -->
			<td  align="left">
				<select name="executorID" style="width:160px">
<%
//20120113 dongyg 华澳说分配时的可选操作员要能够设置，添加角色【90短信发送人员】
if (user_id.intValue()==19) {
%>
					<%=Argument.getRoledOperatorOptions(input_bookCode,Argument.getValueOfTSysControlByFlagType("AROLE90","90"),input_operatorCode)%>
<% } else {%>
					<%=Argument.getManager_Value(null)%>
<% } %>
				</select>
			</td>
		</tr>

		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:allotAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
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
        <!-- 查询 -->
		<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;<%} %>
		<%if (input_operator.hasFunc(menu_id, 103)) {%>
        <!-- 分配 -->
		<button type="button" class="xpbutton3" accessKey=a id="queryButton1" name="queryButton1" title="<%=LocalUtilis.language("message.assign",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.assign",clientLocale)%> (<u>A</u>)</button>
		<%} %>
	</div>
	<br/>
</div>

<div>
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			 <td width="*" align="center">
				 <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.check_serial_no,this);">
				 <%=LocalUtilis.language("class.serviceTitle",clientLocale)%> <!-- 任务标题 -->
			 </td>
	         <td width="14%" align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!-- 任务类别 -->
	         <td width="14%" align="center"><%=LocalUtilis.language("class.serviceWayName",clientLocale)%> </td><!-- 任务方式 -->
	          <td width="8%" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!-- 状态 -->
	         <td width="14%" align="center"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!-- 开始时间 -->
	         <td width="14%" align="center"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!-- 结束时间 -->
	         <td width="12%" align="center"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!-- 客户经理 -->
	     </tr>
<%
//声明字段
String serviceTitle="";
Integer serviceType = new Integer(0);
String serviceTypeName = "";
String serviceWay="";
String serviceWayName = "";
int status_flag;
String serviceStatusName = "";
String start_date;
String end_date;
Integer serial_no;
Integer managerID = new Integer(0);
String managerName="";

List rsList_detail = null;

Iterator iterator = list.iterator();

while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();


		serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
		//判断是否有子任务
		vo = new ServiceTaskVO();
		vo.setTaskSerialNO(serial_no);
		vo.setInputMan(input_operatorCode);
		rsList_detail = local.query_details(vo);

		if(rsList_detail.size()==0){
			iHiddenCount++;
			continue;
		}

		serviceTitle = Utility.trimNull(map.get("ServiceTitle"));
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		serviceWayName = Argument.getDictParamName(1109,serviceWay);
		status_flag = Utility.parseInt(Utility.trimNull(map.get("Status")),0);
		serviceStatusName = Argument.getService_status_name(status_flag);
		start_date = map.get("StartDateTime").toString().substring(0,11);
		end_date = map.get("EndDateTime").toString().substring(0,11);
		managerID = Utility.parseInt(map.get("ManagerID").toString(), new Integer(0));
		managerName = Argument.getManager_Name(managerID);
		serviceType = Utility.parseInt(map.get("ServiceType").toString(), new Integer(0));
%>
	<tr class="tr<%=iCount%2%>">
         <td align="center">
        	<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%">
						<input type="checkbox" name="check_serial_no" value="<%= serial_no%>" class="flatcheckbox"/>
					</td>
					<td width="90%" align="left"><a href="javascript:showAction(<%=serial_no%>,<%=serviceType%>)" class="a2">&nbsp;&nbsp;<%= serviceTitle%></a></td>
				</tr>
			</table>
         </td>
         <td align="center"><%= serviceTypeName%></td>
         <td align="center"><%= serviceWayName%></td>
         <td align="center"><%= serviceStatusName%></td>
         <td align="center"><%= start_date%></td>
         <td align="center"><%= end_date%></td>
         <td align="center"><%= managerName%></td>
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
         </tr>
<%}%>

		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize() - iHiddenCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		</tr>
	</TABLE>
</div>
<br>
<% local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>