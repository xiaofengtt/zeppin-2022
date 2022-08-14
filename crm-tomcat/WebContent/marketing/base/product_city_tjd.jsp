<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
int check_flag=Utility.parseInt(request.getParameter("check_flag"),0);

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

List list = null;
Map map = new HashMap();
boolean bSuccess = false;

if (request.getMethod().equals("POST"))
{
String[] s = request.getParameterValues("s_id");
	if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
	String[] sValue = Utility.splitString(s[i], "$");
		
		
		Integer serial_no = Utility.parseInt(sValue[0], new Integer(0));
		Integer product_id1 = Utility.parseInt(sValue[1], new Integer(0));
		if (product_id != null)
		{
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			vo.setProduct_id(product_id1);;
			product.adddeletecity1(vo);
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
<title><%=LocalUtilis.language("menu.productCityTjd",clientLocale)%> </title>
<!--产品推介地信息设置-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
function newInfo(product_id)
{
	if(showModalDialog('product_city_update.jsp?product_id=' + product_id,'', 'dialogWidth:430px;dialogHeight:150px;status:0;help:0')!= null)
	//showModalDialog('product_city_tjd.jsp?check_flag=2&product_id=' + product_id, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
	{
		sl_update_ok();
		document.theform.action='product_city_tjd.jsp';
		document.theform.submit();
	}
}
function reflash()
{
		sl_remove_ok();
		document.theform.action='product_city_tjd.jsp';
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

<BODY class="BODY body-nox">

<form name="theform" action="product_city_tjd.jsp" method="post">
<input type=hidden name="remove">
<input type="hidden" name="product_id" value=<%=product_id%>>
<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0" >
							<tr>
								<td class="page-title"><b><font color="#215dc6"><%=LocalUtilis.language("menu.productCityTjd",clientLocale)%> </font></b></td>
								<!--产品推介地信息设置-->
							</tr>
						</table>
						<br/>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td width="8%" align="center" height="25"><!--
<input type="checkbox" name="btnCheckbox" class="selectAllBox" 

onclick="javascript:selectAllBox(document.theform.s_id,this);">-->&nbsp;&nbsp;<%=LocalUtilis.language("class.ID",clientLocale)%> <!--编号--></td>
								<td width="12%" align="center" height="25"><%=LocalUtilis.language("class.tjdName",clientLocale)%> </td><!--推介地名称-->
								
							</tr>
						<%int iCount = 0;
int iCurrent = 0;
vo.setProduct_id(product_id);
list = product.querycity1(vo);
Iterator iterator = list.iterator();
while(iterator.hasNext())
{
	map = (Map)iterator.next();
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	iCurrent++;
%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" align="center" height="25">
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"><!--<input type="checkbox" name="s_id" value="<%=Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0))%><%="$"%><%=product_id%>" class="flatcheckbox">--></td>
										<td width="90%" align="center"><%=Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0))%></td>
									</tr>
								</table>
								</td>
								<td align="center" height="25"><%=map.get("CITY_NAME")%></td>
								
							</tr>
							<%}%>
							<tr class="trbottom">
								<td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=iCurrent%> <%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
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

						<table border="0" width="100%" >
							<tr>
								<td align="right">
								<%if(check_flag!=2){%>
								
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
