<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//取得页面查询参数
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
Integer q_completeTimeUp = Utility.parseInt(Utility.trimNull(request.getParameter("q_completeTimeUp")),new Integer(0));
Integer q_completeTimeDown = Utility.parseInt(Utility.trimNull(request.getParameter("q_completeTimeUp")),new Integer(0));
BigDecimal q_ActiveFeeUp = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_ActiveFeeUp")),new BigDecimal(0.00)) ;
BigDecimal q_ActiveFeeDown = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_ActiveFeeDown")),new BigDecimal(0.00)) ;
String q_activity_code =  Utility.trimNull(request.getParameter("q_activity_code"));
String q_active_type = Utility.trimNull(request.getParameter("q_active_type"));
String q_active_theme = Utility.trimNull(request.getParameter("q_active_theme"));
Integer q_active_flag =Utility.parseInt(Utility.trimNull(request.getParameter("q_active_flag")),new Integer(0));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_completeTimeUp="+q_completeTimeUp;
tempUrl = tempUrl+"&q_completeTimeDown="+q_completeTimeDown;
tempUrl = tempUrl+"&q_ActiveFeeUp="+q_ActiveFeeUp;
tempUrl = tempUrl+"&q_ActiveFeeDown="+q_ActiveFeeDown;
tempUrl = tempUrl+"&q_active_type="+q_active_type;
tempUrl = tempUrl+"&q_active_theme="+q_active_theme;
tempUrl = tempUrl+"&q_activity_code="+q_activity_code;
tempUrl = tempUrl+"&q_active_flag="+q_active_flag;
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
vo.setCompleteTimeUp(q_completeTimeUp);
vo.setCompleteTimeDown(q_completeTimeDown);
vo.setActive_fee_up(q_ActiveFeeUp);
vo.setActive_fee_down(q_ActiveFeeDown);
vo.setActive_flag(q_active_flag);
vo.setActive_code(q_activity_code);
vo.setManage_code(input_operatorCode);
vo.setCreator(input_operatorCode);

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
<title><%=LocalUtilis.language("menu.activitiesSet",clientLocale)%> </title>
<!--活动设置-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
	var q_active_flag = document.getElementById("q_active_flag").value;
	var tdId = "td"+q_active_flag;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function validate(){
	if((document.getElementById("q_start_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){
	//开始日期
		return false;
	}	
	if((document.getElementById("q_end_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> "))){
	//结束日期
		return false;			
	}	
	
	if((document.getElementById("q_completeTimeUp").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_completeTimeUp_picker,"<%=LocalUtilis.language("class.completeTimeUp",clientLocale)%> "))){
	//完成时间上限
		return false;
	}	
	if((document.getElementById("q_completeTimeDown").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_completeTimeDown_picker,"<%=LocalUtilis.language("class.completeTimeDown",clientLocale)%> "))){
	//完成时间下限
		return false;			
	}	
	
	syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
	syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);	
	syncDatePicker(document.theform.q_completeTimeUp_picker,document.theform.q_completeTimeUp);	
	syncDatePicker(document.theform.q_completeTimeDown_picker,document.theform.q_completeTimeDown);	
	
	if((document.getElementById("q_start_date").getAttribute("value")!="")&&(document.getElementById("q_end_date").getAttribute("value")!="")){
		var q_start_date = document.getElementById("q_start_date").value;
		var q_end_date = document.getElementById("q_end_date").value;
		
		if(q_end_date<q_start_date){
			sl_alert("<%=LocalUtilis.language("message.endDateError",clientLocale)%> ！");//结束日期不能小于开始日期
			return false;
		}
	}
	
	if((document.getElementById("q_completeTimeUp").getAttribute("value")!="")&&(document.getElementById("q_completeTimeDown").getAttribute("value")!="")){
		var q_completeTimeUp = document.getElementById("q_completeTimeUp").value;
		var q_completeTimeDown = document.getElementById("q_completeTimeDown").value;
		
		if(q_completeTimeDown<q_completeTimeUp){
			sl_alert("<%=LocalUtilis.language("message.completeTimeDownError",clientLocale)%> ！");//完成时间下限不能小于上限
			return false;
		}
	}
	
	var q_ActiveFeeUp_value = document.getElementById("q_ActiveFeeUp").value;
	var q_ActiveFeeDown_value = document.getElementById("q_ActiveFeeDown").value;
	
	if((q_ActiveFeeUp_value!="")&&(! sl_checkDecimal(document.getElementById("q_ActiveFeeUp"),"<%=LocalUtilis.language("class.feeAmountUp2",clientLocale)%> "))){ return false;}//费用上限
	if((q_ActiveFeeDown_value!="")&&(! sl_checkDecimal(document.getElementById("q_ActiveFeeDown"),"<%=LocalUtilis.language("class.feeAmountDown2",clientLocale)%> "))){ return false;}//费用下限
	
	if((q_ActiveFeeUp_value!="")&&((q_ActiveFeeDown_value!=""))&&(q_ActiveFeeUp_value<q_ActiveFeeDown_value)){
		sl_alert("<%=LocalUtilis.language("message.feeError",clientLocale)%> ");//费用上限不能小于费用下限
		return false;
	}
	return true;	
}

/*查询功能*/
function QueryAction(){		
	if(validate()){
		//url 组合
		var url = "activity_pigeonhole.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
		var url = url + "&q_active_type=" + document.getElementById("q_active_type").getAttribute("value");
		var url = url + "&q_active_theme=" + document.getElementById("q_active_theme").getAttribute("value");
		var url = url +"&q_activity_code="+document.getElementById("q_activity_code").getAttribute("value");
		var url = url +"&q_start_date="+document.getElementById("q_start_date").getAttribute("value");
		var url = url +"&q_end_date="+ document.getElementById("q_end_date").getAttribute("value");	
		var url = url + "&q_completeTimeUp=" + document.getElementById("q_completeTimeUp").getAttribute("value");
		var url = url + "&q_completeTimeDown=" + document.getElementById("q_completeTimeDown").getAttribute("value");
		var url = url + "&q_ActiveFeeUp=" + document.getElementById("q_ActiveFeeUp").getAttribute("value");
		var url = url + "&q_ActiveFeeDown=" + document.getElementById("q_ActiveFeeDown").getAttribute("value");
		var url = url +"&q_active_flag="+ document.getElementById("q_active_flag").getAttribute("value");			
		
		disableAllBtn(true);		
		window.location = url;
	}
}

function changeStatus(flag){
	document.getElementById("q_active_flag").value=flag;
	QueryAction();
}

/*刷新*/
function refreshPage(){
	QueryAction();
}

/*新增方法*/
function AppendAction(){		
	var url = "activity_add.jsp";		
	if(showModalDialog(url,'', 'dialogWidth:580px; dialogHeight:440px; status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
	showWaitting(0);
}

/*展示详细信息*/
function showInfo(serial_no){
	var url = "activity_info.jsp?serial_no="+serial_no;
	showModalDialog(url,'','dialogWidth:840px;dialogHeight:450px;status:0;help:0');
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

/*修改方法*/
function ModiAction(serial_no){				
	var url = "coustomer_query.jsp?serial_no="+serial_no;	
	
	if(showModalDialog(url,'', 'dialogWidth:580px; dialogHeight:440px; status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
	showWaitting(0);
}

function queryCustomerGroup(serial_no)
{
	var url = "coustomer_query.jsp?serial_no="+serial_no;	
	
	if(showModalDialog(url,'', 'dialogWidth:580px; dialogHeight:440px; status:0;help:0')){
		sl_update_ok();
		location.reload();
	}	
}

</script>
</head>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="q_active_flag" name="q_active_flag" value="<%=q_active_flag%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:540px;height:230px;">
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
			<td  align="right"><%=LocalUtilis.language("class.activityTheme",clientLocale)%> ： </td><!--活动主题-->
			<td  align="left">
				<input type="text" name="q_active_theme" id="q_active_theme" value="<%= q_active_theme%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
			<td  align="right"><%=LocalUtilis.language("class.activityCode",clientLocale)%> ： </td><!--活动编号-->
			<td  align="left">
				<input type="text" name="q_activity_code" id="q_activity_code" value="<%= q_activity_code%>" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
		</tr>
		
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> ：</td><!--开始日期-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_start_date_picker"  class=selecttext 
				<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
			</td>
			<td  align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> ： </td><!--结束日期-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_end_date_picker" class=selecttext 
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.overTime",clientLocale)%> ：</td><!--完结时间-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_completeTimeUp_picker"  class=selecttext 
				<%if(q_completeTimeUp==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_completeTimeUp)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_completeTimeUp_picker,theform.q_completeTimeUp_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_completeTimeUp" id="q_completeTimeUp" value=""/>	
			</td>	
				<td  align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.to",clientLocale)%> &nbsp;</td><!--至-->
			<td>
				<input type="text" style="width:120px" name="q_completeTimeDown_picker" class=selecttext 
				<%if(q_completeTimeDown==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_completeTimeDown)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_completeTimeDown_picker,theform.q_completeTimeDown_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_completeTimeDown" id="q_completeTimeDown" value=""/>	
			</td>
		</tr>
		
		<tr>
			<td align="right"> <%=LocalUtilis.language("class.feeAmountDown",clientLocale)%> ：</td><!--费用最低-->
			<td  align="left">
				<input type="text" name="q_ActiveFeeDown" id="q_ActiveFeeDown" value="<%= q_ActiveFeeDown%>" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
			
			<td align="right"> <%=LocalUtilis.language("class.feeAmountUp",clientLocale)%> ：</td><!--费用最高-->
			<td  align="left">
				<input type="text" name="q_ActiveFeeUp" id="q_ActiveFeeUp" value="<%= q_ActiveFeeUp%>" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>			
		</tr>
		
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.activityCategories",clientLocale)%> ： </td><!--活动类别-->
			<td  align="left">
				<select name="q_active_type" id="q_active_type" onkeydown="javascript:nextKeyPress(this)" style="width:122px">	
					<%=Argument.getDictParamOptions(3012,q_active_type)%>
				</select>
			</td>	
			<td  align="right" style="width: 90px;">&nbsp;&nbsp;</td>
			<td  align="left">&nbsp;&nbsp;</td>	
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确认-->
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
</div>		
<br/>
<div id="headDiv" >
	<table cellSpacing="1" cellPadding="2" width="320px"  bgcolor="#CCCCCC" class="table-select">
			<tr style="background:F7F7F7;" >
				<td width="60px" align="center" id="td0" <%if (q_active_flag.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
				<!--全部-->
				<td width="60px" align="center" id="td1" <%if (q_active_flag.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(1);" class="a2"><%=LocalUtilis.language("message.undone",clientLocale)%> </a></font></td>
				<!--未处理-->
				<td width="60px" align="center" id="td2" <%if (q_active_flag.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(2);" class="a2"><%=LocalUtilis.language("message.beingProcessed",clientLocale)%> </a></font></td>
				<!--处理中-->
				<td width="60px" align="center" id="td3" <%if (q_active_flag.intValue()==3) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeStatus(3);" class="a2"><%=LocalUtilis.language("message.completed",clientLocale)%> </a></font></td>
				<!--已完结-->
			</tr> 
	</table>
</div>
<br/>
<div align="center"  >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="8%"><%=LocalUtilis.language("class.activityCode",clientLocale)%> </td><!--活动编号-->
			<td align="center" width="*"><%=LocalUtilis.language("class.activityTheme",clientLocale)%> </td><!--活动主题-->
			<td align="center" width="8%"><%=LocalUtilis.language("class.activityCategories",clientLocale)%> </td><!--活动类别-->			
			<td align="center" width="10%"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--开始时间-->
			<td align="center" width="10%"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--结束时间-->
			<td align="center" width="10%"><%=LocalUtilis.language("class.overTime",clientLocale)%> </td><!--完结时间-->
			<td align="center" width="8%"><%=LocalUtilis.language("class.responsibleMan",clientLocale)%> </td><!--负责人-->
			<td align="center" width="6%"><%=LocalUtilis.language("class.activeFee2",clientLocale)%> </td><!--活动花费-->
			<td align="center" width="6%"><%=LocalUtilis.language("class.activeFlagName",clientLocale)%> </td><!--活动状态-->
			<td align="center" width="6%"><%=LocalUtilis.language("message.detailInfo",clientLocale)%> </td><!--详细信息-->			
			<td align="center" width="6%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		</tr>
<%
//声明字段
Iterator iterator = list.iterator();
Integer serial_no;	
Integer manageCode;
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
String manageName;

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
			
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	manageCode =  Utility.parseInt(Utility.trimNull(map.get("MANAGE_CODE")),new Integer(0));
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
	manageName  = Utility.trimNull(map.get("MANAGE_MAN"));
	
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
	  	 <td align="left">&nbsp;&nbsp;<%=active_code%></td>					
		 <td align="left">&nbsp;&nbsp;<a href="#" onclick="javascript:showInfo(<%= serial_no%>);" class="a2"><%= active_theme%></a></td>             
		 <td align="left">&nbsp;&nbsp;<%= active_type_name%></td>        
		 <td align="center"><%= start_date%></td>   
		 <td align="center"><%= end_date%></td>    
		 <td align="center"><%= completeTime%></td> 
		 <td align="center"><%= manageName%></td>  
		 <td align="right">&nbsp;&nbsp;<%= active_fee%></td>               
		 <td align="left">&nbsp;&nbsp;<%= activityFlagName%></td>    
		 <td align="center">
         	<button type="button"  class="xpbutton2" name=""  onclick="javascript:setiteminfor(<%=serial_no%>);">
         		<IMG id="activeImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
         	</button>
         	<input type="hidden" id="activeFlag_display<%=serial_no%>" name="activeFlag_display<%=serial_no%>" value="0">
         </td>   
		 <td align="center">       
		 	<%if((input_operatorCode.intValue() ==  manageCode.intValue())&&(activityFlag.intValue()!=3)){%>
              	<a href="javascript:ModiAction(<%=serial_no%>)">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
               	</a>
           	<%}%>       	
         </td>    
 </tr>        
		     
     <tr id="activeTr<%=serial_no%>" style="display: none">
		<td align="center" bgcolor="#FFFFFF" colspan="11" >
			<div id="activeDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
				<table id="activeTablePro<%=serial_no%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">
					
					<tr style="background:F7F7F7;">
						<td  width="10%">&nbsp;&nbsp;<%=LocalUtilis.language("class.objectCustomerGroup",clientLocale)%> ：</td><!--对象客户群组-->
						<td  width="*">&nbsp;&nbsp;&nbsp;
			               	<button type="button"  class="xpbutton3"  accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" 
			               		onclick="javascript:queryCustomerGroup(<%=serial_no%>)"><%=LocalUtilis.language("message.query",clientLocale)%> <u></u></button>
               			</td>
					</tr>
					
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
              <td align="center">&nbsp;</td>           
         </tr>           
<%}%> 
	
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="11" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>

	</tr>
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<% activityLocal.remove(); %>