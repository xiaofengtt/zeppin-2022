<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递变量
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
Integer task_serial_no = Utility.parseInt(request.getParameter("task_serial_no"), null);
//声明辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
boolean bSuccess = false;

//url设置
String sUrl = "service_pigeonhole_detail.jsp?&sPage="+sPage;
//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//获得明细列表
vo.setTaskSerialNO(task_serial_no);
vo.setStatus(new Integer(0));
vo.setInputMan(input_operatorCode);


List list = serviceTask.query_details(vo);

//分页辅助参数
int iCount = 0;
int iCurrent = 0;

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
//刷新
function refreshPage(){
	disableAllBtn(true);
	var _pagesize = document.getElementsByName("pagesize")[0];		
	window.location = '<%=request.getContextPath()%>/affair/service/service_pigeonhole_detail.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
}

/*关闭框*/
function closeDialogDiv(){
	j$("#closeImg")[0].onclick();
}
</script>
</HEAD>
<body class="body">
<form id="theform" name="theform" method="post">
	<hr noshade color="#808080" size="1">

	<table border="0" width="100%" cellspacing="1" cellpadding="2"
		class="tablelinecolor">
		<tr class="trh">
			<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!-- 客户名称 -->
			<td align="center"><%=LocalUtilis.language("class.serviceWayName",clientLocale)%> </td><!-- 任务方式 -->
			<td align="center"><%=LocalUtilis.language("class.serviceTitle",clientLocale)%>  </td><!-- 任务标题 -->
			<td align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!-- 任务类别 -->
			<td align="center"><%=LocalUtilis.language("class.executeTime",clientLocale)%> </td><!-- 任务时间 -->
			<td align="center">处理结果描述 </td>
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
			<td align="left">&nbsp;&nbsp;<%= cust_name%></td>
			<td align="center"><%= serviceWayName%></td>
			<td align="center"><%= serviceTitle%></td>
			<td align="center"><%= serviceTypeName%></td>
			<td align="center"><%= executeTime%></td>
			<td align="center"><%= Utility.trimNull(map.get("Result"))%></td>
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
	</tr>
<%}%>
	<tr class="trbottom">
         <!-- 合计  --><!-- 项 -->
		<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=list.size()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
	</tr>
</table>
<br>
  <% serviceTask.remove();%>
</form>
</BODY>
</HTML>