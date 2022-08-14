<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.service.*,java.util.*" %>
<%@ page import="enfo.crm.customer.CustomerLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

String custName = Utility.trimNull(request.getParameter("custName"));
Integer compType = Utility.parseInt(Utility.trimNull(request.getParameter("compType")),new Integer(0));
Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));
Integer serial=new Integer(0);
Integer inputDate=new Integer(0);
String checkContent="";


boolean bSuccess=false;

//���ڲ�ѯ�ͻ�Ͷ�߼�¼
CustomerLocal customer = EJBFactory.getCustomer();
Object[] params = new Object[7];
	params[0] = serial;
	params[1] = custName;
	params[2] = start_date;
	params[3] = end_date;
	params[4] = compType;
	params[5] = "";
	params[6] = new Integer(0);


IPageList pageList = customer.getCustomerComplaint(params,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&custName="+custName ;

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(theform.start_date_picker, theform.start_date);
	syncDatePicker(theform.end_date_picker, theform.end_date);
	location = 'client_complaint.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
												+'&custName='+document.theform.custName.value
												+'&start_date=' + document.theform.start_date.value
												+'&end_date=' + document.theform.end_date.value
												+'&compType=' + document.theform.compType.value;
}

window.onload = function(){
	initQueryCondition()};

function StartQuery(){
	refreshPage();
}

/*���� �˲�*/
function newInfo(serial){	
	//disableAllBtn(true);
	var url = "client_complaint_add.jsp?serial="+serial;
	var ret = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:300px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}

function dispose(){
	alert("���Ⱥ˲�");
}

/*����*/
function startDispose(serial){
	var url = "client_complaint_dispose.jsp?serial="+serial + "&date=" + new Date();
	var ret = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:400px;status:0;help:0');
	if(ret==1){
		window.location.reload();
	}
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" >
<input type="hidden" id="serial" name="serial" value="<%=serial%>"/>
<div id="queryCondition" class="qcMain" style="display: none; width: 520px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2">

	<tr>
		<td valign="bottom" align="right">�ͻ����� :</td>
		<td valign="bottom" align="left">
			<input name="custName" value='<%=custName%>' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
		<td valign="bottom" align="right">Ͷ�߷�ʽ :</td>
		<td>
			<select size="1"  name="compType" style="width: 150px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getComplaintOptions(compType)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">��ʼʱ�� :</td>
		<td>
			<input type="text" name="start_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(start_date)%>">
			<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);">
			<input type="hidden" name="start_date" value="">
		</td>
		<td align="right">����ʱ�� :</td>
		<td>
			<input type="text" name="end_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(end_date)%>">
			<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);">
			<input type="hidden" name="end_date" value="">
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();">ȷ�� (<u>O</u>)</button><!--ȷ��-->
		</td>
	</tr>
</table>
</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>


	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=2 class="page-title">			
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>>��ѯ (<u>Q</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--��ѯ-->
							<button type="button"  class="xpbutton3" accessKey=n id="appendButton" onclick="javascript:newInfo(0);" name="appendButton"<%if(!input_operator.hasFunc(menu_id, 100)){%> style="display:none"<%}%>>�½� (<u>N</u>)</button>
							<!--�½�-->
						</div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td>�ͻ�����</td>
						<td>Ͷ������</td>
						<td>Ͷ�߷�ʽ</td>
						<td>�˲����</td>
						<td>�˲�</td>
						<td>����</td>
						<td>�ظ���ʽ</td>
						<td>�ظ�����</td>
						<td>�ط���</td>
						<td>ת����ʽ</td>
						<td>������</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	Integer doStatus = Utility.parseInt(Utility.trimNull(map.get("DO_STATUS")),new Integer(0));
	serial = Utility.parseInt(Utility.trimNull(map.get("SERIAL")),new Integer(0));
	custName = Utility.trimNull(map.get("CUST_NAME"));
	inputDate=Utility.parseInt(Utility.trimNull(map.get("INPUT_DATE")),new Integer(0));
	compType=Utility.parseInt(Utility.trimNull(map.get("COMP_TYPE")),new Integer(0));
	checkContent=Utility.trimNull(map.get("CHECK_CONTENT"));
	
	Integer replyType=Utility.parseInt(Utility.trimNull(map.get("REPLY_TYPE")),new Integer(0));
	Integer replyDate=Utility.parseInt(Utility.trimNull(map.get("REPLY_DATE")),new Integer(0));
	Integer replyMan=Utility.parseInt(Utility.trimNull(map.get("REPLY_MAN")),new Integer(0));
	Integer forwardType=Utility.parseInt(Utility.trimNull(map.get("FORWARD_TYPE")),new Integer(0));
	String doResult=Utility.trimNull(map.get("DO_RESULT"));
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"><%=custName%></td>
						<td align="center"><%=Format.formatDateLine(inputDate)%></td>
						<td align="center"><%if(compType.intValue()==1){out.print("�绰");}
											else if(compType.intValue()==2){out.print("����");}
												else if(compType.intValue()==3){out.print("Email");}
													else{out.print("����");}%></td>
						<td align="left"><%=checkContent%></td>
						<td align="center">
							<%if(checkContent.equals("")){%>
								<a href="javascript:newInfo(<%=serial%>)">�˲�</a>						
							<%}else{%>
								�Ѻ˲�
							<%} %>
							
						</td>
						<td align="center">
							<%if(doStatus.intValue()==1){%>
								<a href="<%if(checkContent.equals("")){ %>javascript:dispose()<%}else{%>javascript:startDispose(<%=serial%>)<%}%>" >����</a>
							<%}else if(doStatus.intValue()==2){%>	
								�Ѵ���
							<%}else if(doStatus.intValue()==3){%>
								ת����ز���
							<%}%>							
						</td>
						<td align="center">
							<%if(replyType.intValue()==1){%>
								�绰
							<%}else if(replyType.intValue()==2){%>
								����
							<%}else if(replyType.intValue()==3){%>
								Email
							<%}else if(replyType.intValue()==4){%>
								�ż�
							<%}%>
						</td>
						<td align="center"><%=Format.formatDateLine(replyDate)%></td>
						<td align="center"><%=Argument.getOpName(replyMan)%></td>
						<td align="center"><%if(forwardType.intValue()==1){%>
								�绰
							<%}else if(forwardType.intValue()==2){%>
								Email
							<%}else if(forwardType.intValue()==3){%>
								����
							<%}%>
						</td>
						<td align="left"><%=doResult%></td>
					</tr>
<%
iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="11"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>						
					</tr>
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
</BODY>
</HTML>
<%customer.remove();%>

