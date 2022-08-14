<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//取得页面查询参数
int queryFlag = Utility.parseInt(request.getParameter("queryFlag"),0);
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(0));//动作标记：1.发送;2.查询发送结果

String ServiceTitle = Utility.trimNull(request.getParameter("ServiceTitle"));
String SmsContent = Utility.trimNull(request.getParameter("SmsContent"));
Integer SmsUser = Utility.parseInt(request.getParameter("SmsUser"),null);
Integer date_1 = Utility.parseInt(request.getParameter("date_1"),new Integer(Utility.getCurrentDate()));
Integer date_2 = Utility.parseInt(request.getParameter("date_2"),new Integer(Utility.getCurrentDate()));

//url设置
String tempUrl = "&queryFlag=1";
tempUrl = tempUrl+"&ServiceTitle="+ServiceTitle+"&SmsUser="+SmsUser
		  +"&SmsContent="+SmsContent+"&date_1="+date_1
		  +"&date_2="+date_2;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int iCount = 0;
String[] totalColumn = new String[0];

//获得对象
SmsRecordLocal smsRecordLocal = EJBFactory.getSmsRecord();
SendSMSVO vo_sms = null;
//actionFlag-2 查询发送结果
if(request.getMethod().equals("POST")){
	if(actionFlag.intValue()==2){
		String[] smsIndex_Array = request.getParameterValues("check_smsIndex");
		Integer q_smsIndex = null;
		String[] smsStatus = null;
		Integer q_status = new Integer(0);
		String q_statusName = "";
		
		for(int i=0;i<smsIndex_Array.length;i++){
			q_smsIndex = Utility.parseInt(smsIndex_Array[i],new Integer(0));
			
			try {
				smsStatus = SmsClient.querySMS(q_smsIndex);
				q_statusName = smsStatus[1];
				q_status = Utility.parseInt(smsStatus[0],new Integer(0));
					
				vo_sms = new SendSMSVO();
				
				vo_sms.setSmsIndex(q_smsIndex);
				vo_sms.setStatus(q_status);
				vo_sms.setStatusName(q_statusName);
				vo_sms.setInputOperator(input_operatorCode);

				smsRecordLocal.modi(vo_sms);
			}
			catch(Exception e){
				String checkSMSPlatform = LocalUtilis.language("message.checkSMSPlatform",clientLocale);
				throw new Exception(checkSMSPlatform+"！");//请检查短信发送平台
			}
		}
		
		actionFlag = new Integer(0);
	}
}

vo_sms = new SendSMSVO();
vo_sms.setServiceTitle(ServiceTitle);
vo_sms.setCust_name(q_cust_name);
vo_sms.setSmsUser(Utility.trimNull(SmsUser));
vo_sms.setSmsContent(SmsContent);
vo_sms.setDate_1(date_1);
vo_sms.setDate_2(date_2); 
IPageList pageList = null;
List list  = new ArrayList();
if(queryFlag==1){
	pageList = smsRecordLocal.query_page(vo_sms,totalColumn,Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
	list = pageList.getRsList();
}

//分页辅助参数
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
<title><%=LocalUtilis.language("menu.querySMS",clientLocale)%> </title><!--短信信息查询-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
	initQueryCondition();
	<%}%>
}

/*更新*/
function UpdataAction(){	
	if(checkedCount(document.getElementsByName('check_smsIndex'))== 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordToUpdate",clientLocale)%> ！");//请选定要更新的记录
		return false;
	}
	
	document.getElementById("actionFlag").value = 2;
	
	document.getElementsByName('theform')[0].action="smsRecord_list.jsp?queryFlag=1";
	document.getElementsByName('theform')[0].submit();
}
/*查询方法*/
function QueryAction(){
	
	if(!sl_checkDate(theform.date_1_picker,"<%=LocalUtilis.language("class.startTime",clientLocale)%> ")){return false;}//开始日期
	syncDatePicker(theform.date_1_picker, theform.date_1);
	if(!sl_checkDate(theform.date_2_picker,"<%=LocalUtilis.language("class.endTime",clientLocale)%> ")){return false;}//结束日期
	syncDatePicker(theform.date_2_picker, theform.date_2);

	if (document.theform.pagesize!=null) {
		location = "smsRecord_list.jsp?queryFlag=1&page=<%=sPage%>&pagesize="+ document.theform.pagesize.value 
				+ "&q_cust_name=" + document.getElementById("q_cust_name").value
				+ "&ServiceTitle=" + document.getElementById("ServiceTitle").value
				+"&date_1="+theform.date_1.value+"&date_2="+theform.date_2.value
				+"&SmsUser="+theform.SmsUser.value
				+"&SmsContent="+theform.SmsContent.value;
	} else {
		location = "smsRecord_list.jsp?queryFlag=1&page=<%=sPage%>&q_cust_name=" + document.getElementById("q_cust_name").value
				+ "&ServiceTitle=" + document.getElementById("ServiceTitle").value
				+"&date_1="+theform.date_1.value+"&date_2="+theform.date_2.value
				+"&SmsUser="+theform.SmsUser.value
				+"&SmsContent="+theform.SmsContent.value;
	}
}

function SentAction(){
	alert("a");
}
//刷新
function refreshPage(){
	 if(!sl_checkDate(theform.date_1_picker,"<%=LocalUtilis.language("class.startTime",clientLocale)%> ")){return false;}//开始日期
	syncDatePicker(theform.date_1_picker, theform.date_1);
	if(!sl_checkDate(theform.date_2_picker,"<%=LocalUtilis.language("class.endTime",clientLocale)%> ")){return false;}//结束日期
	syncDatePicker(theform.date_2_picker, theform.date_2);
	
	location = "smsRecord_list.jsp?queryFlag=1&page=<%=sPage%>&pagesize="+ document.theform.pagesize.value 
				+ "&q_cust_name=" + document.getElementById("q_cust_name").value
				+ "&ServiceTitle=" + document.getElementById("ServiceTitle").value
				+"&date_1="+theform.date_1.value+"&date_2="+theform.date_2.value
				+"&SmsUser="+theform.SmsUser.value
				+"&SmsContent="+theform.SmsContent.value;
}
</script>
</head>

<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post">
<input type="hidden" name="actionFlag" id="actionFlag" value="<%= actionFlag%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:455px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("message.taskName",clientLocale)%> ： </td><!--任务名称-->
			<td  align="left">
				<input TYPE="text" name="ServiceTitle" id="ServiceTitle" value="<%=ServiceTitle%>"/>	
			</td>		
			<td  align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> ： </td><!--客户名称-->
			<td  align="left">
				<input TYPE="text" name="q_cust_name" id="q_cust_name" value="<%=q_cust_name%>"/>	
			</td>	
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.smsContent",clientLocale)%> ： </td><!--短信内容-->
			<td  align="left">
				<input TYPE="text" name="SmsContent" id="SmsContent" value="<%=SmsContent%>"/>	
			</td>		
			<td  align="right"><%=LocalUtilis.language("class.SmsUser",clientLocale)%> ： </td><!--发送人-->
			<td  align="left">	
				<select name="SmsUser" id="SmsUser" style="width: 125px;">
					<%=Argument.getOperatorOptions(SmsUser) %>
				</select>
			</td>	
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startTime",clientLocale)%> ： </td><!--开始时间-->
			<td  align="left">
				<INPUT TYPE="text" NAME="date_1_picker" class=selecttext value="<%=Format.formatDateLine(date_1)%>">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.date_1_picker,theform.date_1_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="date_1" value="<%=date_1%>">	
			</td>		
			<td  align="right"><%=LocalUtilis.language("class.endTime",clientLocale)%> ： </td><!--结束时间-->
			<td  align="left">
				<INPUT TYPE="text" NAME="date_2_picker" class=selecttext value="<%=Format.formatDateLine(date_2)%>">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.date_2_picker,theform.date_2_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="date_2" value="<%=date_2%>">	
			</td>	
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>		
	</table>
</div>


<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>		
	<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
	<div align="right">
		<!--查询-->
        <button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;
		<!--  
		<button type="button" class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="发送" onclick="javascript:SentAction();">发送(<u>S</u>)</button>
		&nbsp;&nbsp;&nbsp;
		-->
		<%if (input_operator.hasFunc(menu_id, 103)) {%>
        <!--查询发送状态-->
		<button type="button" class="xpbutton5" accessKey=u id="btnUpdate" name="btnAppend" title="<%=LocalUtilis.language("class.QuerySendStatus",clientLocale)%>" onclick="javascript:UpdataAction();"><%=LocalUtilis.language("class.QuerySendStatus",clientLocale)%>(<u>U</u>)</button>
		&nbsp;&nbsp;&nbsp;<%}%>
	</div>
	<%}%>
	<hr noshade color="#808080" size="1">
</div>
<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
<div>
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			<td width="15%" align="center">
				 <input type="checkbox" name="checkBox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.check_smsIndex,this);" />
				 <%=LocalUtilis.language("class.customerName",clientLocale)%> <!--客户名称-->
			 </td>
	         <td width="*" align="center"><%=LocalUtilis.language("class.smsContent",clientLocale)%> </td><!--短信内容-->
	         <td width="15%" align="center"><%=LocalUtilis.language("class.smsTime",clientLocale)%> </td>  <!--发送时间-->
	         <td width="15%" align="center"><%=LocalUtilis.language("class.SmsUser",clientLocale)%> </td><!--发送人-->
	         <td width="15%" align="center"><%=LocalUtilis.language("class.statusName",clientLocale)%> </td> <!--短信状态-->
	     </tr>
<% 
//声明字段
Integer smsIndex = new Integer(0);//主键
String cust_name = "";
String serviceTitle = "";
String smsTime = "";
String smsUser = "";
Integer smsUserCode = new Integer(0);
String statusName = "";
Integer smsStatus = new Integer(0);

String smsContent = "";

Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	smsIndex = Utility.parseInt(Utility.trimNull(map.get("SmsIndex")),new Integer(0));	
	cust_name = Utility.trimNull(map.get("Cust_Name"));	
	serviceTitle = Utility.trimNull(map.get("ServiceTitle"));	
	smsTime = Utility.trimNull(map.get("SmsTime"));
	//smsUserCode = Utility.parseInt(Utility.trimNull(map.get("SmsUser")),new Integer(0));
	smsUser = Utility.trimNull(map.get("OP_NAME"));
	statusName = Utility.trimNull(map.get("StatusName"));
	smsStatus = Utility.parseInt(Utility.trimNull(map.get("Status")),new Integer(0));
	smsContent = Utility.trimNull(map.get("SmsContent"));
	if(smsContent!=null && smsContent.length()>30)
		smsContent = smsContent.substring(0,30);
	if(smsTime.length()>0){
		smsTime = smsTime.substring(0,16);
	}
%>
	<tr class="tr<%=iCount%2%>">
		<td align="center">				
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%">
						<input type="checkbox" <%if(smsStatus.intValue()!= 3 && smsStatus.intValue()!= 4){%> style="visibility:hidden" <%}%>
							   name="check_smsIndex" value="<%= smsIndex%>" class="flatcheckbox"/>&nbsp;&nbsp; 
					</td>
					<td width="90%" align="center"><%= cust_name%>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</td> 
		<td align="center"><%= smsContent%></td> 
		<td align="center"><%= smsTime%></td> 
		<td align="center"><%= smsUser%></td> 
		<td align="center"><%= statusName%></td> 
	</tr>
<%}%>

		<%
		int page_size = (pageList!=null)?pageList.getPageSize():Utility.parseInt(sPagesize, 8);
		for(; iCount < page_size; iCount++){%>  
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>                 
         </tr>           
		<%}%>           
		<tr class="tr1">
            <!--合计--><!--项-->
			<td align="center" class="tdh"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%if(pageList!=null) out.print(pageList.getTotalSize());%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
		</tr>
	</TABLE>
</div>
<br>
<div>
	<%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%>
</div>
<%}
else{%>
<TABLE  border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="tr1"><td align="center"><font size="4" face="微软雅黑"><%=LocalUtilis.language("message.smdIsNotEnable",clientLocale)%> </font></td></tr><!--尚未启用短信平台-->
</TABLE>
<%}%>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<% smsRecordLocal.remove();%>