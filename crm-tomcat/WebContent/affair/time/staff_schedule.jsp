<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer op_code = Utility.parseInt(request.getParameter("op_code"), new Integer(0));
Integer schedule_type = Utility.parseInt(request.getParameter("schedule_type"), new Integer(0));
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"),null);
Integer end_date = Utility.parseInt(request.getParameter("end_date"),null);

RemindersLocal local = EJBFactory.getReminders();

RemindersVO vo = new RemindersVO();
vo.setOp_code(op_code);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
vo.setSchedule_type(schedule_type);
vo.setInput_man(input_operatorCode);

IPageList pageList =local.pagelistStaffSchedule(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();

String qs =  Utility.getQueryString(request, new String[]{"op_code", "schedule_type", "begin_date", "end_date"});
sUrl += "&"+qs;

local.remove();
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>员工日程表</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
window.onload = function(){
		initQueryCondition();
	};

	function refreshPage(){
		disableAllBtn(true);
		window.location = 'staff_schedule.jsp?page=1&pagesize=' + document.theform.pagesize.value+'&<%=qs%>';
	}
	
	function QueryAction(){
		if(!sl_checkDate(document.theform.begin_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> ")){//开始日期
			return;
		}else{
			syncDatePicker(document.theform.begin_date_picker,document.theform.begin_date);			
		}
		
		if(!sl_checkDate(document.theform.end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> ")){//结束日期
			return;
		}else{
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);			
		}
		
		if(document.theform.begin_date.value > document.theform.end_date.value){
			sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ！");//结束日期不能比开始日期早
			return;
		}

		var url = 'staff_schedule.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>';
		url += "&begin_date="+ document.theform.begin_date.value;
		url += "&end_date="+ document.theform.end_date.value;
		url += "&op_code="+ document.theform.op_code.value;
		url += "&schedule_type="+ document.theform.schedule_type.value;

		disableAllBtn(true);
		location.href = url;
	}
</script>
</head>

<body class="body body-nox">
<form name="theform">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width: 90px;">员工 : </td>
			<td  align="left">
				<select name="op_code" style="width:120px;">
					<%=/*Argument.getOperatorOptions(op_code)*/Argument.getOperatorMembers(input_operatorCode, op_code)%>
				</select>
			</td>
			<td  align="right" style="width: 90px;">日程类型 : </td>
			<td  align="left">
				<select name="schedule_type" style="width:120px;">
					<%=Argument.getScheduleTypeList(schedule_type.toString())%>
				</select>
			</td>						
		</tr>
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
			<td  align="left">
				<input type="text" name="begin_date_picker" class=selecttext style="width: 99px;" <%if(begin_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(begin_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="begin_date" id="begin_date" />	
			</td>	
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--结束日期-->
			<td  align="left">
				<input type="text" name="end_date_picker" class=selecttext style="width: 99px;" <%if(end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(end_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="end_date" id="end_date" />	
			</td>	
		</tr>						
		<tr>
			<td align="center" colspan="4">
				<!--确定-->
                <button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="QueryAction()"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">
		<div class="btn-wrapper">
            <!-- 查询 -->
			<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>          
		</div>
		<br/>
		</td>
	</tr>
</table>
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
		<tr class="trh">
			 <td width="8%"  align="center">员工名字</td>
	         <td width="8%"  align="center">日程类型</td>
	         <td width="*"  align="center">日程描述</td>
	         <td width="15%" align="center">开始时间</td>
	         <td width="15%"  align="center">结束时间 </td>
	    </tr>
<%
//分页参数
int iCount = 0;

for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);	
	iCount++;
%>   
	     <tr class="tr<%=iCount%2%>">
	        <td align="center"><%=map.get("OP_NAME")%></td>
	        <td align="center" style="padding-left:7px;"><%=map.get("SCHEDULE_NAME")%></td>
	        <td align="left" width="*"><%=map.get("CONTENT")%></td>
	        <td align="center">
				<%=map.get("START_DATE")==null?"":Utility.trimNull(map.get("START_DATE")).substring(0, Utility.trimNull(map.get("START_DATE")).lastIndexOf(':'))%>
	        </td>  
	        <td align="center">  
	   			<%=map.get("END_DATE")==null?"":Utility.trimNull(map.get("END_DATE")).substring(0, Utility.trimNull(map.get("END_DATE")).lastIndexOf(':'))%>
	        </td>             
	     </tr>   
<%}
for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){
%>      	

         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>             
         </tr>           
<%}%> 
		<tr class="trbottom">
			<!--合计--><!--项-->
            <td align="left" class="tdh" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		</tr>   
	</TABLE>
	</td>
	</tr>
	<tr>
    	<td>
    		<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
    	</td>
    </tr>
</table>
</form>
</body>
</html>