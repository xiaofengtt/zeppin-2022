<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">

<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx1.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<%

  GainLevelRateLocal local = EJBFactory.getGainLevelRate();
  GainLevelRateVO vo = new GainLevelRateVO();  
  Integer open_status = Utility.parseInt(request.getParameter("open_status"), new Integer(2));
  Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
  Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));

  vo.setInput_man(input_operatorCode);
  vo.setProduct_id(product_id);
  vo.setSub_product_id(sub_product_id);
  vo.setOpen_status(open_status);
  List list = local.listOpenDate(vo);
%>

<script language="javascript">

</script>
</HEAD>


<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method = "post" >
<input type="hidden" name="open_status" value="<%=open_status%>">
<input type="hidden" name="open_status1" value="0">
<input type="hidden" name="deal_flag" value="0">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<%//@ include file="menu.inc" %>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
				<TBODY>
					<TR>
					  <TD>
						<table border="0" width="100%" cellspacing="2" cellpadding="0">						
							<tr>
								<td >
								   <hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td >开放日</td>
								<td >确认净值/实际收益率</td>
							</tr>
							
<% 
int iCount = 0;
int iCurrent = 0;
for (; iCount<list.size(); iCurrent++, iCount++){
	Map map =(Map)list.get(iCount);
	Integer open_date = Utility.parseInt(Utility.trimNull(map.get("OPEN_DATE")),new Integer(0));
	BigDecimal nav_price = Utility.parseDecimal(Utility.trimNull(map.get("NAV_PRICE")),new BigDecimal(0));
%>

							<tr class="tr<%=(iCurrent % 2)%>">
								<td align="center"  ><%=Format.formatDateCn(open_date)%></td>
								<td align="center"  ><%=Utility.trimZero(nav_price)%></td>
<% }%>

							<tr class="trbottom">	
								<td class="tdh" align="center" ><b>合计 <%=list.size()%> 项</b></td>
								<td  align="center" ></td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
 local.remove();
%>
