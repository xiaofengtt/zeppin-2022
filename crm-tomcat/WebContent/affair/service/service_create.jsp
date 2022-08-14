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
vo.setInsertMan(input_operatorCode);
vo.setInputMan(input_operatorCode);
vo.setManagerID(input_operatorCode);

IPageList pageList = local.query_page(vo,totalColumn,1,-1);

//分页辅助参数
int iCount = 0;
int iCurrent = 0;
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
<title><%=LocalUtilis.language("menu.createService",clientLocale)%> </title><!-- 服务创建 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}

/*修改方法*/
function ModiAction(serial_no){				
	var url = "<%=request.getContextPath()%>/affair/service/service_create_setAction1.jsp?nextFlag=1&close_flag=0&serial_no="+serial_no;		
	//var v_result = showModalDialog(url,'', 'dialogWidth:850px;dialogHeight:450px;status:0;help:0');	
	//window.location.reload();	
	//dialog("","iframe:"+url,"850px","400px","show","<%=request.getContextPath()%>"); 
	showModalDialog(url,'', 'dialogWidth:850px;dialogHeight:450px;status:0;help:0');
	showWaitting(0);
	location.reload();
}

/*添加功能*/
function AppendAction(){
	//任务管理>>任务创建>>任务信息设置
	var url = "<%=request.getContextPath()%>/affair/service/service_create_setAction1.jsp?nextFlag=1&close_flag=0";		
	//dialog("","iframe:"+url,"850px","400px","show","<%=request.getContextPath()%>"); 
	showModalDialog(url,'', 'dialogWidth:850px;dialogHeight:450px;status:0;help:0');
	window.location.reload();	
	showWaitting(0);	
}

/*删除功能*/
function DelAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "<%=request.getContextPath()%>/affair/service/service_create_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

//刷新
function refreshPage(){
	 QueryAction();
}

/*查询功能*/
function QueryAction(){				
	//url 组合
	var url = "<%=request.getContextPath()%>/affair/service/service_create.jsp?1=1" ;		
	var url = url + "&q_serviceType=" + document.getElementById("q_serviceType").getAttribute("value");
	var url = url + "&q_serviceWay=" + document.getElementById("q_serviceWay").getAttribute("value");
	
	disableAllBtn(true);		
	window.location = url;	
}

function showAction(serial_no,serviceType){
	var url = "<%=request.getContextPath()%>/affair/service/service_create_setAction3.jsp?showFlag2=1&&serial_no="+serial_no+"&serviceType="+serviceType;
	showModalDialog(url,'','dialogWidth:850px;dialogHeight:450px;status:0;help:0');
	showWaitting(0);
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
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

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>		
	<div align="right" class="btn-wrapper">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;<%}%>
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
		<button type="button" class="xpbutton3" accessKey=n id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
		&nbsp;&nbsp;&nbsp;<%}%>
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<button type="button" class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<%}%>
	</div>
	<br/>
</div>

<div>
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			 <td width="*" align="center">
				 <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.check_serial_no,this);" />
				 <%=LocalUtilis.language("class.serviceTitle",clientLocale)%>  <!-- 任务标题 -->
			 </td>
	         <td width="12%" align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!-- 任务类别 -->   
	         <td width="10%" align="center"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td><!-- 联系方式 -->
	         <td width="12%" align="center"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!-- 开始时间 -->
	         <td width="12%" align="center"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!-- 结束时间 -->
	         <td width="12%" align="center"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!-- 客户经理 -->                         
	         <td width="8%" align="center" ><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!-- 状态 -->
	         <td width="8%" align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!-- 编辑 --> 	         
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

Iterator iterator = list.iterator();

while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		
		serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
		serviceTitle = Utility.trimNull(map.get("ServiceTitle"));		
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		serviceWayName = Utility.trimNull(Argument.getDictParamName(1109,serviceWay));
		status_flag = Utility.parseInt(Utility.trimNull(map.get("Status")),0);
		serviceStatusName = Argument.getService_status_name(status_flag);
		start_date = map.get("StartDateTime").toString().substring(0,11);
		end_date = map.get("EndDateTime").toString().substring(0,11);	
		managerID = Utility.parseInt(map.get("ManagerID").toString(), new Integer(0));
		managerName = Argument.getOperator_Name(managerID);
		serviceType = Utility.parseInt(map.get("ServiceType").toString(), new Integer(0));
%>
	<tr class="tr<%=iCount%2%>">
	         <td align="center">
	         	<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="10%">
							<%if(input_operatorCode.intValue()== managerID.intValue()){%>
								<input type="checkbox" name="check_serial_no" value="<%= serial_no%>" class="flatcheckbox"/>
							<%}%> 
						</td>
						<td width="90%" align="left"><a href="javascript:showAction(<%=serial_no%>,<%=serviceType%>)" class="a2">&nbsp;&nbsp;<%= serviceTitle%></a></td>
					</tr>
				</table>
	         </td>
	         <td align="center"><%= serviceTypeName%></td>             
	         <td align="center"><%= serviceWayName%></td>
	         <td align="center"><%= start_date%></td>
	         <td align="center"><%= end_date%></td>     
	         <td align="center"><%= managerName%></td>         
	         <td align="center"><%= serviceStatusName%></td>
	         <td align="center">    
	         	<%if(input_operatorCode.intValue()== managerID.intValue()){%>          	
	          	<a href="javascript:ModiAction(<%=serial_no%>);">
	           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="absmiddle" width="16" height="16">
	           	</a>
	           	<%}%>
	         </td>
	</tr>  
<%}%>     

<%for(int i=0;i<(8-iCount);i++){%>  
         <tr class="tr0">
            <td align="center"></td>
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
        	<!-- 合计 --><!-- 项 -->
			<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		</tr>
	</TABLE>
</div>
<br>
<% local.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>