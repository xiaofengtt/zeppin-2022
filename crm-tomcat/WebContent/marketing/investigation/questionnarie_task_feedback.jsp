<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递变量
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
Integer edit_flag = Utility.parseInt(request.getParameter("edit_flag"), new Integer(0));
String q_serviceWay = Utility.trimNull(request.getParameter("q_serviceWay"));

//声明辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
boolean bSuccess = false;

//url设置
String sUrl = "service_feedback.jsp?&sPage="+sPage;
String tempUrl = "";
tempUrl = tempUrl+"&q_serviceWay="+q_serviceWay;
sUrl = sUrl + tempUrl;

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//获得明细列表
vo.setServiceType(new Integer(64));
vo.setServiceWay(q_serviceWay);
vo.setTaskSerialNO(serial_no);
vo.setNeedFeedBack(new Integer(1));
vo.setStatus(new Integer(3));
vo.setInputMan(input_operatorCode);

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
<title><%=LocalUtilis.language("menu.questionnarieTaskFeedback",clientLocale)%> </title>
<!--服务反馈-->
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
}

//刷新
function refreshPage(){
	disableAllBtn(true);
	var _pagesize = document.getElementsByName("pagesize")[0];		
	window.location = 'questionnarie_task_feedback.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
}
	
/*编辑*/
function ModiAction(serial_no,TargetCustID){				
	var url = "<%=request.getContextPath()%>/affair/service/service_feedback_edit.jsp?serial_no="+serial_no + "&targetCustID="+TargetCustID;	
	
	if(showModalDialog(url,'', 'dialogWidth:830px;dialogHeight:550px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}
	
/*查询功能*/
function QueryAction(){		
	var _pagesize = document.getElementsByName("pagesize")[0];
	
    //url 组合
	var url = "questionnarie_task_feedback.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");	
	var url = url + "&q_serviceWay=" + document.getElementById("q_serviceWay").getAttribute("value");		
	disableAllBtn(true);		
	window.location = url;	
}
</script>
</HEAD>
<body class="body body-nox">
<form id="theform" name="theform" method="post">

<div id="queryCondition" class="qcMain" style="display:none;width:230px;height:100px;">
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
			<td  align="right"><%=LocalUtilis.language("class.serviceWay2",clientLocale)%> : </td><!--服务途径-->
			<td  align="left">
				<select name="q_serviceWay" id="q_serviceWay" style="width:120px">
					<%= Argument.getDictParamOptions(1109, q_serviceWay)%>
				</select>
			</td>		
		</tr>	
		<tr>
			<td align="center" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--确认-->	 				
			</td>
		</tr>		
	</table>
</div>

<div align="left" class="page-title">
 	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
			
<div align="right" class="btn-wrapper">
	<%if (input_operator.hasFunc(menu_id, 108)) {%>
	<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
	<!--查询-->
	<%}%>

</div>
<br/>

<table border="0" width="100%" cellspacing="1" cellpadding="2"
	class="tablelinecolor">
	<tr class="trh">
		<td width="21%" align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
		<td width="21%" align="center"><%=LocalUtilis.language("class.serviceWayName2",clientLocale)%> </td><!--服务方式-->
		<td width="21%" align="center"><%=LocalUtilis.language("class.serviceTypeName2",clientLocale)%> </td><!--服务类别2-->
		<td width="21%" align="center"><%=LocalUtilis.language("class.executeTime2",clientLocale)%> </td><!--服务时间-->
		<td width="18%" align="center"><%=LocalUtilis.language("message.feedBackService",clientLocale)%> </td><!--反馈服务-->
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
	
	while(iterator.hasNext()){		
		map = (Map)iterator.next();
		serial_no_detail = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),null);
		targetCustID = Utility.parseInt(Utility.trimNull(map.get("TargetCustID")),null);
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		executeTime = Utility.trimNull(map.get("ExecuteTime")).substring(0,11);
		serviceWayName = Argument.getDictParamName(1109,serviceWay);
		
		if(needFeedback.intValue()==1){
			 feedback = LocalUtilis.language("message.need2",clientLocale);//要
		}else if(needFeedback.intValue()==2){
			feedback =LocalUtilis.language("message.notNeed",clientLocale);//不要
		}
        iCount++;	
%>
<tr class="tr<%=iCount%2%>">
		<td align="center"><%= cust_name%></td>
		<td align="center"><%= serviceWayName%></td>
		<td align="center"><%= serviceTypeName%></td>
		<td align="center"><%= executeTime%></td>
		<td align="center">
		<a href="javascript:ModiAction(<%=serial_no_detail%>,<%=targetCustID%>);"> 
		<img border="0"
			src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
			 </a>
		</td>
</tr>
<%}%>
	
<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
	<tr class="tr0">
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center" width="27">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
	</tr>
<%}%>
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		<!-- <td align="center" class="tdh"></td>
		<td align="center" class="tdh" width="27"></td>
		<td align="center" class="tdh"></td>
		<td align="center" class="tdh"></td> -->
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