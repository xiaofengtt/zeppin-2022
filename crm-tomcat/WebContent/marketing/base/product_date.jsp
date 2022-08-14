<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String trade_type=request.getParameter("trade_type");
String trade_type_name=request.getParameter("trade_type_name");
int readonly=Utility.parseInt(request.getParameter("readonly"),0);
List list = null;
Map map = new HashMap();
Iterator iterator = list.iterator();

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

boolean bSuccess = false;
if (request.getMethod().equals("POST"))
{
String[] s = request.getParameterValues("s_id");
	if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		Integer serial_no = Utility.parseInt(s[i], new Integer(0));
		if (product_id != null)
		{
			vo.setSerial_no(serial_no);			
			vo.setInput_man(input_operatorCode);
			vo.setProduct_id(product_id);
			product.addDeleteDate1(vo);
			bSuccess = true;
		}
	}
}
}
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
<title><%=LocalUtilis.language("menu.productDate",clientLocale)%> </title>
<!--产品时间信息设置-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

function newInfo(product_id)
{
	if(showModalDialog('product_date_update.jsp?product_id=' + product_id+'&trade_type='+document.theform.trade_type.value+'&trade_type_name='+document.theform.trade_type_name.value, '', 'dialogWidth:430px;dialogHeight:350px;status:0;help:0') != null)
	{
		sl_update_ok();
		document.theform.action='product_date.jsp';
		document.theform.submit();
	}
}
function reflash()
{
		sl_remove_ok();
		document.theform.action='product_date.jsp';
		document.theform.submit();	
}
function confirmRemove()
{
	if (document.theform.s_id==null) {
		sl_alert("<%=LocalUtilis.language("message.noRecordToDel",clientLocale)%> ！");//没有需要删除的记录
		return false;
	}
		
	if(checkedCount(document.theform.s_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	
	if(confirm("<%=LocalUtilis.language("message.ifSureToDelRecords",clientLocale)%> ？"))//您确定选定的记录删除吗
		{disableAllBtn(true);
			document.theform.submit();
		}
	
	
}
</script>

</HEAD>

<BODY class="BODY">

<form name="theform" action="product_date.jsp" method="post">
<input type=hidden name="remove">
<input type="hidden" name="product_id" value=<%=product_id%>>
<input type="hidden" name="trade_type" value=<%=request.getParameter("trade_type")%>>
<input type="hidden" name="trade_type_name" value=<%=request.getParameter("trade_type_name")%>>

<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0" height="74">
							<tr>
								<td>
									<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28">
									<b><font color="#215dc6"><%=LocalUtilis.language("class.product",clientLocale)%><!--产品-->
									<%=trade_type_name%><%=LocalUtilis.language("message.infoSet",clientLocale)%><!--信息设置--></font></b>
								</td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td width="8%" align="center" height="25"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.s_id,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.ID",clientLocale)%> </td>
								<!--编号-->
								<td width="12%" align="center" height="25"><%=LocalUtilis.language("class.time",clientLocale)%> </td><!--时间-->
								<%if(!trade_type.equals("211003")){%>
								<td width="12%" align="center" height="25"><%=LocalUtilis.language("class.ratio",clientLocale)%>/<%=LocalUtilis.language("class.money",clientLocale)%> </td><!--比率/金额-->
								<%}%>
								<td width="12%" align="center" height="25"><%=LocalUtilis.language("class.description",clientLocale)%> </td><!--备注-->
								
							</tr>
						<%int iCount = 0;
int iCurrent = 0;
vo.setProduct_id(product_id);
vo.setInput_man(input_operatorCode);
vo.setSerial_no(new Integer(0));
list = product.queryDate1(vo);
while (iterator.hasNext())
{
	map = (Map)iterator.next();
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	iCurrent++;
%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" align="center" height="25">
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"><input type="checkbox" <%if(readonly==1) out.print("style='display:none'");%> name="s_id" value="<%=Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0))%>" class="flatcheckbox"></td>
										<td width="90%" align="center"><%=Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0))%></td>
									</tr>
								</table>
								</td>
								<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("TRADE_DATE")),new Integer(0)))%></td>
								<%if(!trade_type.equals("211003")){%>
								<td align="center" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TRADE_RATE")),new BigDecimal(0))))%></td>
								<%}%>
								<td align="center" height="25"><%=Utility.trimNull(map.get("DESCRIPTION"))%></td>
							</tr>
							<%}%>
							<tr class="trbottom">
								<td class="tdh" align="center" height="25">
									<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> 
									<%=iCurrent%><%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
								</td>
								<td align="center" height="25">-</td>
								<%if(!trade_type.equals("211003")){%>
								<td align="center" height="25">-</td>
								<%}%>
								<td align="center" height="25">-</td>
							</tr>
						</table>

						<br>
<script language=javascript>
<%if (bSuccess)
{%>
reflash();
<%}%>
</script>

						<table border="0" width="100%">
							<tr>
								<td align="right">
								<%if(readonly==0){%>
								
								<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo(<%=product_id%>);"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
								&nbsp;&nbsp;&nbsp;<!--新建记录--><!--新建-->
							
								<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:confirmRemove();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
								<%}%>
				                &nbsp;&nbsp;&nbsp;<!--删除所选记录--><!--删除-->
								<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="<%=LocalUtilis.language("message.closePage",clientLocale)%> " onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>B</u>)
                                </button><!--关闭当前页面--><!--关闭-->
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
