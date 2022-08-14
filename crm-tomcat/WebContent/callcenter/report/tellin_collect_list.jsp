<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.callcenter.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ȡ��ҳ���ѯ����
Integer q_extension = Utility.parseInt(Utility.trimNull(request.getParameter("q_extension")),new Integer(0));
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
Integer q_statFlag = Utility.parseInt(Utility.trimNull(request.getParameter("q_statFlag")),new Integer(0));
Integer q_managerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_managerID")),new Integer(0));

//ҳ�渨������
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
int iCount = 0;

//url����
String tempUrl = "";
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
tempUrl = tempUrl+"&q_statFlag="+q_statFlag;
tempUrl = tempUrl+"&q_extension="+q_extension;
tempUrl = tempUrl+"&q_managerID="+q_managerID;
sUrl = sUrl + tempUrl;

//��ö��󼰽����
SeatLocal seatLocal = EJBFactory.getSeat();
SeatVO vo = new SeatVO();

vo.setManagerID(q_managerID);
vo.setStatFlag(q_statFlag);
vo.setStartDate(q_start_date);
vo.setEndDate(q_end_date);
vo.setInputman(input_operatorCode);

IPageList pageList = seatLocal.query_page_seatCallin(vo,totalColumn,t_sPage,t_sPagesize);
%>
<HTML>
<HEAD>
<TITLE>��ϯ����绰����</TITLE>
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
/*��������*/	
window.onload = function(){
	initQueryCondition();
}

//ˢ��
function refreshPage(){
	disableAllBtn(true);
	var _pagesize = document.getElementsByName("pagesize")[0];		
	window.location = 'agent_workload_list.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
}

//��֤����
function validate(){
	if((document.getElementById("q_start_date_picker").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"��ʼ����"))){
		return false;
	}
	else{
		syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
	}
	
	if((document.getElementById("q_end_date_picker").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"��������"))){
		return false;			
	}
	else{
		syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);			
	}		
	if(document.theform.q_start_date.value != "" && document.theform.q_end_date.value != "" ){
		   if(document.theform.q_start_date.value > document.theform.q_end_date.value){
			alert("��ʼ���ڲ��ܴ���������ڣ�");
			return false;
		   }
		}
	return true;	
}

/*��ѯ����*/
function QueryAction(){		
	if(validate()){
		var _pagesize = document.getElementsByName("pagesize")[0];
			
		//url ���
		var url = "agent_workload_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
		var url = url + "&q_managerID=" + document.getElementById("q_managerID").getAttribute("value");
		var url = url + "&q_extension=" + document.getElementById("q_extension").getAttribute("value");
		var url = url + "&q_statFlag=" + document.getElementById("q_statFlag").getAttribute("value");
		var url = url + "&q_end_date=" + document.getElementById("q_end_date").getAttribute("value");
		var url = url +"&q_start_date="+ document.getElementById("q_start_date").getAttribute("value");		
		
		disableAllBtn(true);		
		window.location = url;	
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="get">


<div id="queryCondition" class="qcMain" style="display:none;width:530px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   	<td>��ѯ������</td>
   	<td align="right">
   		<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
    </td>
  	</tr>
</table>
<!--��ѯ����-->
<table>
	<tr>
		<td  align="right">�ͻ�����: </td>
		<td  align="left">
			<select name="q_managerID" id="q_managerID" style="width:120px">
				<%=Argument.getManager_Value(q_managerID)%>
			</select>	
		</td>
		<td align="right">&nbsp;&nbsp;�ֻ�����:</td>
	    <td>
		  	<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_extension" id="q_extension" value="<%=Utility.trimNull(q_extension)%>" size="25">
		</td>
	</tr>
	<tr>
		<td  align="right">&nbsp;&nbsp;��ʼ����: </td>
		<td  align="left">
			<input type="text" name="q_start_date_picker" id="q_start_date_picker" class=selecttext 
			<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >	&nbsp;&nbsp;
			<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
			<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
		</td>
		<td  align="right">&nbsp;&nbsp;��������: </td>
		<td  align="left">
			<input type="t ext" name="q_end_date_picker" id="q_end_date_picker"  class=selecttext 
			<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >	&nbsp;&nbsp;
			<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
			<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
		</td>
	</tr>
	<tr>
		<td  align="right">ͳ�Ʒ�ʽ: </td>
		<td  align="left">
			<select name="q_statFlag" id="q_statFlag" style="width:120px">
				<%=Argument.getStatFlagOptions(q_statFlag.intValue())%>
			</select>	
		</td>
		<td>&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;</td>
	</tr>
	 <tr>
	  	<td align=center colspan=4>
		  	<%if (input_operator.hasFunc(menu_id, 108)) {%>
				<button type="button" class="xpbutton3" accessKey=o name="btnQuery"onclick="javascript:QueryAction();">ȷ��(<u>O</u>)</button>
			<%}%>
		</td>
	</tr>
</table>
</div>


<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div class="btn-wrapper">
		<button type="button" class="xpbutton3" accessKey=f id="queryButton" name="queryButton">��ѯ(<u>F</u>)</button>
	</div>
	<br/>
</div>
<div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td align="center">�ͻ�����</td>
		<td align="center">ͨ��ʱ��</td>
		<td align="center">ͳ�Ʒ�ʽ</td>		
	</tr>
<%
//������ʾ����
String managerName ="";
String callLength = "";
String statDate = "";

List list = pageList.getRsList();
Map map = null;

Iterator iterator = list.iterator();

while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next(); 
		
		managerName = Utility.trimNull(map.get("ManagerName"));
		callLength = Utility.trimNull(map.get("Hours"));
 		statDate  = Utility.trimNull(map.get("StatDate")).substring(0,7);
%>
	<tr class="tr<%=iCount%2%>">
		<td align="center"><%= managerName%></td>
		<td align="center"><%= callLength%>Сʱ</td>	
		<td align="center"><%= statDate%></td>
	</tr>
<%}%>
<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
         </tr>           
<%}%>   

		<tr class="tr1">
			<td class="tdh" align="center"><b>�ϼ� <%=pageList.getTotalSize()%> ��</b></td>
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