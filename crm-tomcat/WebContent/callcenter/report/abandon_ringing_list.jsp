<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.callcenter.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
Integer q_extension = Utility.parseInt(Utility.trimNull(request.getParameter("q_extension")),new Integer(0));
Integer q_managerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_managerID")),new Integer(0));

//页面辅助参数
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
int iCount = 0;

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_extension="+q_extension;
tempUrl = tempUrl+"&q_managerID="+q_managerID;
sUrl = sUrl + tempUrl;

//获得对象及结果集
SeatLocal seatLocal = EJBFactory.getSeat();
SeatVO vo = new SeatVO();

vo.setManagerID(q_managerID);
vo.setExtension(q_extension);
vo.setStartDate(q_start_date);
vo.setEndDate(q_end_date);
vo.setInputman(input_operatorCode);

IPageList pageList = seatLocal.query_page_seatAbandon(vo,totalColumn,t_sPage,t_sPagesize);
%>
<HTML>
<HEAD>
<TITLE>坐席放弃振铃明细报表</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>

<script language=javascript>
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}

//刷新
function refreshPage(){
	disableAllBtn(true);
	var _pagesize = document.getElementsByName("pagesize")[0];		
	window.location = 'abandon_ringing_list.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
}

//验证方法
function validate(){
	if((document.getElementById("q_start_date_picker").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"开始日期"))){
		return false;
	}
	else{
		syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
	}
	
	if((document.getElementById("q_end_date_picker").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"结束日期"))){
		return false;			
	}
	else{
		syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);			
	}		
		if(document.theform.q_start_date.value != "" && document.theform.q_end_date.value != "" ){
		   if(document.theform.q_start_date.value > document.theform.q_end_date.value){
			alert("开始日期不能大与结束日期！");
			return false;
		   }
		}
	return true;	
}

/*查询功能*/
function QueryAction(){		
	if(validate()){
		var _pagesize = document.getElementsByName("pagesize")[0];
			
		//url 组合
		var url = "abandon_ringing_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
		var url = url + "&q_managerID=" + document.getElementById("q_managerID").getAttribute("value");
		var url = url + "&q_extension=" + document.getElementById("q_extension").getAttribute("value");
		var url = url + "&q_end_date=" + document.getElementById("q_end_date").getAttribute("value");
		var url = url + "&q_start_date= "+ document.getElementById("q_start_date").getAttribute("value");		
		
		disableAllBtn(true);		
		window.location = url;	
	}
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="get">

<div id="queryCondition" class="qcMain" style="display:none;width:530px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   	<td>查询条件：</td>
   	<td align="right">
   		<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
    </td>
  	</tr>
</table>
<!--查询条件-->
<table>
	<tr>
	  <td align="right">&nbsp;&nbsp;分机号码:</td>
	  <td>
	  	<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_extension" id="q_extension" value="<%=Utility.trimNull(q_extension)%>" size="25">
	  </td>
		<td  align="right">客户经理: </td>
		<td  align="left">
			<select name="q_managerID" id="q_managerID" style="width:120px">
				<%=Argument.getManager_Value(q_managerID)%>
			</select>	
		</td>	
	</tr>
	<tr>
		<td  align="right">&nbsp;&nbsp;开始日期: </td>
		<td  align="left">
			<input type="text" name="q_start_date_picker" id="q_start_date_picker" class=selecttext 
			<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >	&nbsp;&nbsp;
			<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
			<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
		</td>
		<td  align="right">&nbsp;&nbsp;结束日期: </td>
		<td  align="left">
			<input type="text" name="q_end_date_picker" id="q_end_date_picker" class=selecttext 
			<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >	&nbsp;&nbsp;
			<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
			<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
		</td>
	</tr>
	 <tr>
	  	<td align=center colspan=4>
		  	<%if (input_operator.hasFunc(menu_id, 108)) {%>
				<button type="button" class="xpbutton3" accessKey=o name="btnQuery"onclick="javascript:QueryAction();">确定(<u>O</u>)</button>
			<%}%>
		</td>
	</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button" class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>
	</div>
	<br/>
</div>


<div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td align="center">分机号</td>
		<td align="center">呼叫方式</td>
		<td align="center">对方电话</td>	
		<td align="center">通话开始时间</td>					
		<td align="center">通话时长</td>
		<td align="center">客户名称</td>
		<td align="center">联系人姓名</td>
		<td align="center">通话结果</td>
		<td align="center">客户经理</td>		
	</tr>
	
<%
//声明显示参数
Integer extno = new Integer(0);
Integer call_type = new Integer(0);
String phoneNumber ="";
String callTime ="";
Integer callLength = new Integer(0);
String cust_id = "";
String contactId = "";
Integer cc_result = new Integer(0);
String managerId = "";
String cc_result_s = "" ;
String direction = "";
List list = pageList.getRsList();
Map map = null;

Iterator iterator = list.iterator();

while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		
		extno = Utility.parseInt(Utility.trimNull(map.get("Extension")),new Integer(0));
		call_type = Utility.parseInt(Utility.trimNull(map.get("Direction")),new Integer(0));
		phoneNumber = Utility.trimNull(map.get("PhoneNumber"));
		callTime = Utility.trimNull(map.get("CallTime")).substring(0,19);
		callLength = Utility.parseInt(Utility.trimNull(map.get("CallLength")),new Integer(0));
		cust_id = Utility.trimNull(map.get("CUST_NAME"));
		contactId = Utility.trimNull(map.get("Contactor"));
		cc_result = Utility.parseInt(Utility.trimNull(map.get("Status")),new Integer(0));
		managerId = Utility.trimNull(map.get("ManagerName"));
						if(call_type.intValue()==1){
			          direction ="被叫";
		         }else if(call_type.intValue()==2){
			          direction ="主叫";
		         }
				if(cc_result.intValue()==1){
			          cc_result_s ="正常";
		         }else if(cc_result.intValue()==2){
			          cc_result_s ="待处理";
		         }else if(cc_result.intValue()==9){
			          cc_result_s ="放弃";}
%>
	<tr class="tr<%=iCount%2%>">
		<td align="center"><%= extno%></td>
		<td align="center"><%= direction%></td>	
		<td align="center"><%= phoneNumber%></td>					
		<td align="center"><%= callTime%></td>
		<td align="center"><%= callLength%>分钟</td>
		<td align="center"><%= cust_id%></td>
		<td align="center"><%= contactId%></td>
		<td align="center"><%= cc_result_s%></td>
		<td align="center"><%= managerId%></td>
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
         </tr>           
<%}%>   

		<tr class="tr1">
			<td class="tdh" align="center"><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
		</tr>

</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl)%>
</div>
<% seatLocal.remove();%>

</form>
</BODY>
</HTML>