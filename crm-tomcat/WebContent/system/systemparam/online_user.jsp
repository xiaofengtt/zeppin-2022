<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ö���
OperatorLocal op_local = EJBFactory.getOperator();

//��ý����
IPageList pagelist =
	op_local.listOnLineAllOp(
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 8));
List list = pagelist.getRsList();
Iterator it = list.iterator();

//��������
Map map = null;

//url����
sUrl = sUrl + "";
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.onlineUser",clientLocale)%> </TITLE>
<!--�����û���ѯ-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
/**��ҳ����**/
function refreshPage()
{
	location = 'online_user.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<form id="theform" method="post" name="theform">
<%@ include file="/includes/waiting.inc"%>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD><!--��ͷ start-->
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title">					
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
						<button type="button"  class="xpbutton3" accessKey=r id="btnRefresh" name="btnRefresh" title="<%=LocalUtilis.language("message.currentPageRefrash",clientLocale)%> "
							onclick="javascript:refreshPage();"><%=LocalUtilis.language("message.refresh",clientLocale)%> (<u>R</u>)</button><!--ˢ�µ�ǰҳ��--><!--ˢ��-->
					</div>
					<br/>
						</td>
					</tr>
				</table>
				<!--��ͷ end--> <!--��ϸ�б���ʾ start-->
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trtagsort">
						<td width="5%" align="center" sort="num"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--���-->
						<td width="8%" align="center"><%=LocalUtilis.language("class.opName2",clientLocale)%> </td><!--Ա������-->
						<td width="10%" align="center"><%=LocalUtilis.language("class.IPAddress",clientLocale)%> </td><!--IP��ַ-->
						<td width="10%" align="center"><%=LocalUtilis.language("class.oTel2",clientLocale)%> </td><!--�̶��绰-->
						<td width="10%" align="center"><%=LocalUtilis.language("class.mobile2",clientLocale)%> </td><!--�ƶ��绰-->
						<td width="20%" align="center"><%=LocalUtilis.language("class.address2",clientLocale)%> </td><!--��ϸ��ַ-->
					</tr>
					<!-- ѭ��������ʾÿ����Ϣ -->
					<%int iCount = 0;
while (it.hasNext()) {
	map = (Map) it.next();%>
					<tr class="tr<%=(iCount % 2)%>">
						<td width="5%" align="center"><%=iCount + 1%></td>
						<td width="8%" align="center"><%=map.get("OP_NAME")%></td>
						<td width="10%" align="center"><%=Utility.trimNull(map.get("IP_ADDRESS"))%></td>
						<td width="10%" align="center"><%=Utility.trimNull(map.get("O_TEL"))%></td>
						<td width="10%" align="center"><%=Utility.trimNull(map.get("MOBILE"))%></td>
						<td width="20%" align="left"><%=Utility.trimNull(map.get("ADDRESS"))%></td>
					</tr>
					<%iCount++;
}%>
					<!-- ���ʵ�ʼ�¼����Ҫ��ʾ�����������ӡ���� -->
					<%for (int i = 0; i < (8 - iCount); i++) {%>
					<tr class="tr0">
						<td align="center">&nbsp;</td>
						<td align="center">&nbsp;</td>
						<td align="center">&nbsp;</td>
						<td align="center">&nbsp;</td>
						<td align="center">&nbsp;</td>
						<td align="center">&nbsp;</td>
					</tr>
					<%}%>
					<!-- ͳ�����еļ�¼ -->
					<tr class="trbottom">
						<td width="5%" align="left" colspan="6" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pagelist.getTotalSize()%>&nbsp;<%=LocalUtilis.language("class.persons",clientLocale)%> <!--��--></b></td>
						<!-- <td width="8%" align="left"></td>
						<td width="5%" align="left"></td>
						<td width="10%" align="left"></td>
						<td width="10%" align="left"></td>
						<td width="20%" align="left"></td>  -->
					</tr>
				</table>
				<!-- ��ϸ�б���ʾ end --> <!-- ��ҳ��Ϣ��ʾ start--> 
				<table border="0" width="100%">
					<tr valign="top">
						<td align="right">
						<table border="0" width="100%" class="page-link">
							<tr valign="top">
								<td><%=pagelist.getPageLink(sUrl,clientLocale)%></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<!-- ��ҳ��Ϣ��ʾ end--></TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>

</BODY>
</HTML>

