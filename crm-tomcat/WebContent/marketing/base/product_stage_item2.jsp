<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>


<%
//��ȡҳ�����
Integer product_id = new Integer(Utility.parseInt(request.getParameter("product_id"), 0));
int readonly = Utility.parseInt(request.getParameter("readonly"),0);
//�趨��������
boolean bSuccess = false;
int serial_no;
List list = null;
Map map = new HashMap();
//��ö���
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
//���ò�ѯ����
vo.setSerial_no(null);//ɾ��ʱ���и�setSerial_no,��������Set������ɾ����鲻������
vo.setProduct_id(product_id);
//��ѯ
list = product.list_manrateConfig(vo);
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.productStagItem2",clientLocale)%> </title>
<!--�������ȡ�ֶ�������Ϣ-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	sl_remove_ok();
<%}%>
function showInfo(serial_no)
{
	if(showModalDialog('product_stage_item2_info.jsp?product_id=<%=product_id%>&serial_no=' + serial_no, '', 'dialogWidth:420px;dialogHeight:360px;status:0;help:0') != null)
	{
		sl_update_ok();
		document.theform.submit();
	}
}

function newInfo()
{
	if(showModalDialog('product_stage_item2_info.jsp?product_id=<%=product_id%>', '', 'dialogWidth:420px;dialogHeight:360px;status:0;help:0') != null)
	{
		sl_update_ok();
		document.theform.submit();
	}
}
</script>

</HEAD>

<BODY class="BODY body-nox">
<form name="theform" method="get" action="product_stage_item2.jsp">
<input type="hidden" name="product_id" value="<%=product_id%>">
<input type="hidden" name="remove">

<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 border=0 width="100%">
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title"> <b><font color="#215dc6"><%=LocalUtilis.language("menu.productManageFeeSectionInfo",clientLocale)%> </font></b></td>
								<!--��Ʒ�������ȡ�ֶ���Ϣ-->
							</tr>
						</table>
						<br/>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td width="11%" align="center" height="25"><%=LocalUtilis.language("class.serial_no2",clientLocale)%> </td><!--�ֶ����-->
								<td width="11%" align="center" height="25" sort="num"><%=LocalUtilis.language("class.beginPrice",clientLocale)%> </td><!--��ʼ��ֵ-->
								<td width="11%" align="center" height="25" sort="num"><%=LocalUtilis.language("class.endPrice",clientLocale)%> </td><!--������ֵ-->
								<td width="11%" align="center" height="25" sort="num"><%=LocalUtilis.language("class.divRate",clientLocale)%> </td><!--��ɱ���-->
								<td width="11%" align="center" height="25"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
							</tr>
							<%
							Iterator it = list.iterator();
							int iCount = 0;
							int iCount2 = 0;
							Integer serial_no2;
							BigDecimal begin_price;
							BigDecimal end_price;
							BigDecimal div_price;
							BigDecimal div_rate;

							while(it.hasNext()){
								iCount++;
								iCount2++;
								map = (Map)it.next();
								serial_no2 = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
								begin_price = Utility.parseDecimal(Utility.trimNull(map.get("BEGIN_PRICE")),new BigDecimal(0),2,"100");
								end_price = Utility.parseDecimal(Utility.trimNull(map.get("END_PRICE")),new BigDecimal(0),2,"100");
								div_price = Utility.parseDecimal(Utility.trimNull(map.get("DIV_PRICE")),new BigDecimal(0),2,"100");
								div_rate = Utility.parseDecimal(Utility.trimNull(map.get("DIV_RATE")),null);
%>

							<tr class="tr<%=(iCount % 2)%>">
								<td class="tdh" width="9%" align="center" height="25">
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"><%if(readonly==0){%><input type="checkbox" name="selectbox" value="<%=serial_no2%>" class="flatcheckbox"><%}%></td>
										<td width="90%" align="center"><%=serial_no2%></td>
									</tr>
								</table>
								</td>
								<td width="10%" align="right" height="25"><%=Utility.trimZero(begin_price)%></td>
								<td width="10%" align="right" height="25"><%=Utility.trimZero(end_price)%></td>
								<td width="10%" align="right" height="25"><%=(div_rate == null)? "": Utility.trimZero(div_rate.multiply(new BigDecimal(100)).setScale(4))%>%</td>
								<td width="10%" align="center" height="25">
								<%if(readonly==0){%>
								<button type="button"  class="xpbutton2" onclick="javascript:showInfo(<%=serial_no2%>);">&gt;&gt;</button>
								<%}%>
								</td>
							</tr>
							<%
							}

							for (; iCount < 8; iCount++){
							%>
							<tr class="tr<%=(iCount % 2)%>">
								<td class="tdh" width="9%" align="center" height="25"></td>
								<td width="10%" align="center" height="25"></td>
								<td width="10%" align="center" height="25"></td>
								<td width="10%" align="center" height="25"></td>
								<td width="10%" align="center" height="25"></td>
							</tr>
							<%}
%>
							<tr class="trbottom">
								<td class="tdh" width="9%" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�--> <%=iCount2%> <%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
								<td width="10%" align="center" height="25">-</td>
								<td width="10%" align="center" height="25">-</td>
								<td width="10%" align="center" height="25">-</td>
								<td width="10%" align="center" height="25"></td>
							</tr>
						</table>

						<br>

						<table border="0" width="100%">
							<tr valign="top">
								<td align="right">
								<%if(readonly==0){%>
								<button type="button"  class="xpbutton3" accessKey=a id="btnSelectAll" name="btnSelectAll" title="<%=LocalUtilis.language("menu.selectAllRecords",clientLocale)%> " onclick="javascript:selectAll(document.theform.selectbox);"><%=LocalUtilis.language("message.selectAll",clientLocale)%> (<u>A</u>)</button>
								&nbsp;&nbsp;&nbsp;<!--ѡ��ȫ����¼--><!--ȫѡ-->
								<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
								&nbsp;&nbsp;&nbsp;<!--�½���¼--><!--�½�-->
								<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.selectbox)) {disableAllBtn(true);document.theform.remove.value=1;document.theform.submit();}"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
								&nbsp;&nbsp;&nbsp;<!--ɾ����ѡ��¼--><!--ɾ��-->
								<%}%>
								<button type="button"  class="xpbutton3" accessKey=q id="btnQuit" name="btnQuit" title="<%=LocalUtilis.language("message.closePage",clientLocale)%> " onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>Q</u>)
                                </button><!--�رյ�ǰҳ��--><!--�ر�-->
								</td>
							</tr>
						</table>

						</TD>
					</TR>
			</TABLE>
			</TD>
		</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%product.remove();%>
