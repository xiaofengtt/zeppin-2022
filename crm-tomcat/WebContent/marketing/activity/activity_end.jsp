<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//取得页面查询参数
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
String q_activity_code =  Utility.trimNull(request.getParameter("q_activity_code"));
String q_active_type = Utility.trimNull(request.getParameter("q_active_type"));
String q_active_theme = Utility.trimNull(request.getParameter("q_active_theme"));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_active_type="+q_active_type;
tempUrl = tempUrl+"&q_active_theme="+q_active_theme;
tempUrl = tempUrl+"&q_activity_code="+q_activity_code;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO vo = new ActivityVO();

//设置查询条件
vo.setActive_type(q_active_type);
vo.setActive_theme(q_active_theme);
vo.setStartDate(q_start_date);
vo.setEndDate(q_end_date);
vo.setActive_flag(new Integer(0));
vo.setActive_code(q_activity_code);
vo.setManage_code(input_operatorCode);

//查找信息
IPageList pageList = activityLocal.pageList_query(vo,totalColumn,t_sPage,t_sPagesize);

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
<!--活动设置-->
<title><%=LocalUtilis.language("menu.activitiesSet",clientLocale)%> </title>
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

function validate(){
    //开始日期
	if((document.getElementById("q_start_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){
		return false;
	}	
	//结束日期
	if((document.getElementById("q_end_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> "))){
		return false;			
	}	
	
	syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
	syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);	
	return true;	
}

/*查询功能*/
function QueryAction(){		
	if(validate()){
		//url 组合
		var url = "activity_end.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
		var url = url + "&q_active_type=" + document.getElementById("q_active_type").getAttribute("value");
		var url = url + "&q_active_theme=" + document.getElementById("q_active_theme").getAttribute("value");
		var url = url +"&q_activity_code="+document.getElementById("q_activity_code").getAttribute("value");
		var url = url +"&q_start_date="+document.getElementById("q_start_date").getAttribute("value");
		var url = url +"&q_end_date="+ document.getElementById("q_end_date").getAttribute("value");		
		
		disableAllBtn(true);		
		window.location = url;
	}
}

/*刷新*/
function refreshPage(){
	QueryAction();
}

function EndAction(serial_no){
	var url = "activity_end_action.jsp?serial_no="+serial_no;	
	window.location.href = url;	
}

/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "activeTr"+serial_no;
	var v_table = "activeTablePro"+serial_no;
	var v_flag = "activeFlag_display"+serial_no;
	var v_div = "activeDiv"+serial_no;
	var v_image = "activeImage"+serial_no;
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

</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:500px;height:165px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.activityCode",clientLocale)%> : </td><!--活动编号-->
			<td  align="left">
				<input type="text" name="q_activity_code" id="q_activity_code" value="<%= q_activity_code%>" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
			<td  align="right"><%=LocalUtilis.language("class.activityTheme",clientLocale)%> : </td><!--活动主题-->
			<td  align="left">
				<input type="text" name="q_active_theme" id="q_active_theme" value="<%= q_active_theme%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_start_date_picker"  class=selecttext 
				<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
			</td>
			<td  align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--结束日期-->
			<td  align="left">
				<input type="t ext" style="width:120px" name="q_end_date_picker" class=selecttext 
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.activityCategories",clientLocale)%> : </td><!--活动类别-->
			<td  align="left">
				<select name="q_active_type" id="q_active_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%=Argument.getDictParamOptions(3012,q_active_type)%>
				</select>
			</td>	
			<td  align="right" style="width: 90px;">&nbsp;&nbsp;</td>
			<td  align="left">&nbsp;&nbsp;</td>	
		</tr>
		<tr>
			<td align="center" colspan="4">
			    <!--确认-->
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
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
		<!--查询-->
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		<%}%>
	</div>	
	<br/>
</div>		

<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" >
		<tr class="trh">
			<td align="center" width="10%"><%=LocalUtilis.language("class.activityCode",clientLocale)%> </td><!--活动编号-->
			<td align="center" width="*"><%=LocalUtilis.language("class.activityTheme",clientLocale)%> </td><!--活动主题-->
			<td align="center" width="10%"><%=LocalUtilis.language("class.activityCategories",clientLocale)%> </td><!--活动类别-->			
			<td align="center" width="12%"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--开始时间-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--结束时间-->
			<td align="center" width="12%"><%=LocalUtilis.language("class.overTime",clientLocale)%> </td><!--完结时间-->
			<td align="center" width="10%"><%=LocalUtilis.language("class.activeFee2",clientLocale)%>（<%=LocalUtilis.language("message.yuan",clientLocale)%>）</td><!--活动花费--><!--元-->
			<td align="center" width="6%"><%=LocalUtilis.language("class.activeFlagName",clientLocale)%> </td><!--活动状态-->
			<td align="center" width="6%"><%=LocalUtilis.language("message.detailInfo",clientLocale)%> </td><!--详细信息-->			
			<td align="center" width="6%"><%=LocalUtilis.language("class.activeTrace",clientLocale)%> </td><!--活动总结-->
		</tr>
<%
//声明字段
Iterator iterator = list.iterator();
Integer serial_no;	
BigDecimal active_fee = new BigDecimal(0.00);
String start_date;
String end_date;	
String active_type_name;
String active_theme;
String active_code;
String customer_type;
String active_plan;
String active_trace;
Integer activityFlag = new Integer(0);
String activityFlagName;
String completeTime;	

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
			
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	active_code = Utility.trimNull(map.get("ACTIVE_CODE"));
	active_type_name = Utility.trimNull(map.get("ACTIVE_TYPE_NAME"));
	active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));
	start_date = Utility.trimNull(map.get("START_DATE"));
	end_date = Utility.trimNull(map.get("END_DATE"));
	customer_type  = Utility.trimNull(map.get("CUSTOMER_TYPE"));
	active_trace = Utility.trimNull(map.get("ACTIVE_TRACE"));
	active_plan = Utility.trimNull(map.get("ACTIVE_PLAN"));
	activityFlag =  Utility.parseInt(Utility.trimNull(map.get("ACTIVE_FLAG")),new Integer(0));
	activityFlagName = Argument.getActivityFlagName(activityFlag);
	active_fee = Utility.parseDecimal( Utility.trimNull(map.get("ACTIVITY_FEE")),new BigDecimal(0.00),2,"1");
	completeTime = Utility.trimNull(map.get("COMPLETE_TIME"));
	
	if(start_date.length()>0){
		start_date = start_date.substring(0,16);
	}	
	if(end_date.length()>0){
		end_date = end_date.substring(0,16);
	}
	if(completeTime.length()>0){
		completeTime = completeTime.substring(0,16);
	}
%>
			<tr class="tr<%=iCount%2%>">
				  	 <td align="center"><%=active_code%></td>					
					 <td align="left">&nbsp;&nbsp;<%= active_theme%></td>             
					 <td align="left">&nbsp;&nbsp;<%= active_type_name%></td>        
					 <td align="center"><%= start_date%></td>   
					 <td align="center"><%= end_date%></td>  
					 <td align="center"><%= completeTime%></td>   
					 <td align="right">&nbsp;&nbsp;<%= active_fee%></td>               
					 <td align="center"><%= activityFlagName%></td>    
					 <td align="center">
			         	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);" >
			         		<IMG id="activeImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
			         	</button>
			         	<input type="hidden" id="activeFlag_display<%=serial_no%>" name="activeFlag_display<%=serial_no%>" value="0">
			         </td>   
					 <td align="center">       
					 	<%if(activityFlag.intValue()!=3){%>
			              	<a href="javascript:EndAction(<%=serial_no%>)">
			               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
			               	</a>
		               	<%}%>       	
		             </td>    
		     </tr>        
		     
		     <tr id="activeTr<%=serial_no%>" style="display: none">
				<td align="center" bgcolor="#FFFFFF" colspan="10" >
					<div id="activeDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
						<table id="activeTablePro<%=serial_no%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">
							<tr style="background:F7F7F7;">
								<td  width="10%">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ：</td><!--对象客户群-->
								<td  width="*"><%= customer_type%></td>
							</tr>
						
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ：</td><!--活动计划和目标-->
								<td><%= active_plan%></td>
							</tr>
							
							<tr style="background:F7F7F7;">
								<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activeTrace",clientLocale)%> ：</td><!--活动总结-->
								<td><%= active_trace%></td>
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
	             <td align="center">&nbsp;</td>            
	         </tr>           
	<%}%> 

		<tr class="trbottom">
		    <!--合计--><!-- 项-->
			<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			<!--  <td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>	
			<td align="center" class="tdh"></td>	
			<td align="center" class="tdh"></td>	
			<td align="center" class="tdh"></td>-->	
		</tr>
		</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<% activityLocal.remove(); %>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>

