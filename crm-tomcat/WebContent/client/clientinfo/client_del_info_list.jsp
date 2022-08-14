<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.service.*,java.util.*" %>
<%@ page import="enfo.crm.customer.CustomerLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String custName = Utility.trimNull(request.getParameter("custName"));
String cardId = Utility.trimNull(request.getParameter("cardId"));


boolean bSuccess=false;

//���ڲ�ѯע���ͻ�
CustomerLocal customer = EJBFactory.getCustomer();
Object[] params = new Object[3];
	params[0] = new Integer(0);
	params[1] = custName;
	params[2] = cardId;


IPageList pageList = customer.getDelCustomer(params,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

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
<script language=javascript>

function refreshPage(){
	disableAllBtn(true);
	document.theform.submit();											
}

window.onload = function(){
	initQueryCondition()};

function StartQuery(){
	refreshPage();
}

//�鿴�ͻ���Ϣ
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?flag=1&cust_id='+ cust_id ;
	showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');

}


/*�ָ�*/
function recover(cust_id){
	if(sl_confirm("ע���ָ�")){
		location="client_recover.jsp?cust_id=" + cust_id;
	}
}

</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="client_del_info_list.jsp">
<div id="queryCondition" class="qcMain" style="display: none; width: 475px">
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
		<td valign="bottom" align="right">֤������ :</td>
		<td>
			<input name="cardId" value='<%=cardId%>' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();">ȷ�� (<u>O</u>)</button>
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
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">��ѯ (<u>F</u>)</button>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td>���</td>
						<td>����</td>
						<td>֤������</td>
						<td>�ͻ�����</td>
						<td>�ͻ����</td>
						<td>�ͻ���Դ</td>
						<td>��ϸ��Ϣ</td>
						<td>ע���ָ�</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
Integer custId = null;
Integer is_deal = null;
String is_deal_name = "";
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	custId = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	is_deal = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
	is_deal_name = Argument.getWTName(is_deal);
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"><input type="hidden" id="cust_id" name="cust_id" value="<%=custId %>"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
						<td align="center"><%=is_deal_name%></td>
						<td align="center"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("CUST_SOURCE_NAME"))%></td>
						<td align="center"><button type="button"  class="xpbutton2" name="queryButton" onclick="javascript:getCustomer(<%=custId%>);">�鿴</button></td>
						<td align="center"><button type="button"  class="xpbutton2" name="recoverButton" onclick="javascript:recover(<%=custId%>);">�ָ�</button></td>
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
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>						
					</tr>
				</table>
				<table border="0" width="100%">
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

