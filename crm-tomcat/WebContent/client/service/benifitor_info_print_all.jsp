<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.intrust.*, enfo.crm.marketing.*, enfo.crm.customer.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
sPage=Utility.trimNull(sPage);
sPagesize=Utility.trimNull(sPagesize);

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
String [] paramValues=request.getParameterValues("selectbox");

BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();
ProductLocal product=EJBFactory.getProduct();
ProductVO product_vo = new ProductVO();
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_bh = request.getParameter("contract_bh");

String paramValue="";
String period_unit="月";
Integer periodUint = new Integer(0);
String product_name = "";
String contract_sub_bh = "";
Integer valid_period = new Integer(0);
Integer ben_date = new Integer(0);
Integer ben_end_date = new Integer(0);
String cust_name = "";
String card_type_name = "";
String card_id = "";
String bank_name = "";
String bank_sub_name = "";
String bank_acct = "";
BigDecimal ben_amount = new BigDecimal(0);
Integer start_date = new Integer(0);

Map map = null;
Map cust_map = null;
Map product_map = null;

if (paramValues != null)
{
	int count=0;
	
	for(int i = 0;i <paramValues.length; i++)
	{
	    count++;
	    String [] paramValues2  = Utility.splitString(paramValues[i],"$");
	    Integer serial_no = Utility.parseInt(paramValues2[0], null);
	    Integer cust_id = Utility.parseInt(paramValues2[2], null);
	    product_id = Utility.parseInt(paramValues2[3], null);
	    BigDecimal fact_money= new BigDecimal(0);
		
		java.math.BigDecimal totalsum=new java.math.BigDecimal(0);
		Integer cust_type=new Integer(1);
		if(serial_no!=null)
		{
			vo.setBook_code(new Integer(1));
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			List list = benifitor.load1(vo);
			if(list.size()>0){
				map = (Map)list.get(0); 
			}
			if(map != null){
				contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
				ben_date = Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0));
				ben_end_date = Utility.parseInt(Utility.trimNull(map.get("BEN_END_DATE")),new Integer(0));
				bank_name = Utility.trimNull(map.get("BANK_NAME"));
				bank_sub_name = Utility.trimNull(map.get("BANK_SUB_NAME"));
				ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0),2,"1"); 
				bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
			}
			
			cust_vo.setCust_id(cust_id);
			cust_vo.setInput_man(input_operatorCode);
			List cust_list = customer.listCustomerLoad(cust_vo);

			if(cust_list.size()>0){
				cust_map = (Map)cust_list.get(0); 
			}

			if(cust_map != null){
				cust_name = Utility.trimNull(cust_map.get("CUST_NAME"));
				card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
				card_id = Utility.trimNull(cust_map.get("CARD_ID"));
			}
			
			product_vo.setProduct_id(product_id);
			List product_list = product.load(product_vo);
			if(product_list.size()>0){
				product_map = (Map)product_list.get(0); 
			}
			if(product_map != null){
				fact_money = Utility.parseDecimal(Utility.trimNull(product_map.get("FACT_MONEY")),new BigDecimal(0),2,"1"); 
				periodUint = Utility.parseInt(Utility.trimNull(product_map.get("PERIOD_UNIT")),new Integer(0));
				product_name = Utility.trimNull(product_map.get("PRODUCT_NAME"));
				valid_period = Utility.parseInt(Utility.trimNull(product_map.get("VALID_PERIOD")),new Integer(0));
				start_date = Utility.parseInt(Utility.trimNull(product_map.get("START_DATE")),new Integer(0));
			}
			
			period_unit=Argument.getProductUnitName(periodUint);
			
		}
		
		String factmoney = "";
		
		if(Utility.trimNull(fact_money) != null)
			factmoney=Utility.numToChinese(Utility.trimNull(fact_money).toString());
		
		
			
		
%>	

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<style type="text/css"> 
@media print {.noprint{display: none;}}

TABLE,TR,TD,HR,INPUT,SELECT,TEXTAREA
{ 
	FONT-FAMILY: 仿宋_GB2312, 仿宋, Tahoma, Verdana, Arial;
	FONT-SIZE: 10pt;
}
</style>
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script language=javascript>

function refreshPage()
{
	StartQuery();
}


</script>

<BODY class="BODY"">
<form name="theform" method="post"  >
<input type="hidden" name="product_id" value="<%=Utility.trimNull(request.getParameter("product_id"))%>" >

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tr>
				<td></td>
			</tr>
		</TABLE>
		<TABLE id="blankid" style="display:none" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tr>
				<td align="center"></td>
			</tr>
		</TABLE>	
		<TABLE id="blankid" style=<%if(count>1)out.print("page-break-before:always");%>  cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tr>
				<td align="center" height=40></td>
			</tr>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0 >
			<tr>
				<td align="left" height=25></td>
				<td colspan="4"></td>
			</tr>
			
			<tr>
			    <td align="left" >&nbsp;&nbsp;&nbsp;</td>
			    <td colspan=2 align="left" height=25><b><FONT face="宋体" size=4>信托基本情况</FONT></b></td>
				<td colspan=2 align="left"><b><FONT face="宋体" size=4>信托利益分配方式</FONT></b></td>
			</tr>
			
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=4></td></tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" ><b>计划名称：</b><%=product_name%></td>
			<td align="left" ></td>
			<td align="left" colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;受益人按照
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" ></td>
				<td align="left" colspan=3 ></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2><b>信托合同编号：</b><%=contract_sub_bh%></td>
				<td align="left" colspan=2 >《<%=product_name%>》</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" ></td>
				<td align="left" colspan=3 ></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>计划本金：</b><%=Utility.trimNull(factmoney)%></td>
			<td align="left" colspan=2>的约定分享信托利益，利益由受托人划转至受益人</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>计划期限：</b><%=valid_period%><%=period_unit%></td>
			<td align="left" colspan=2>指定的信托利益划付账户。</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>受托人：</b><%=application.getAttribute("COMPANY_NAME")%></td>
			<td align="left" ></td>
			<td align="left" ></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" ></td>
			<td align="left" ></td>
			<td align="left" colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="qs_date"   type="text" class='ednoline'    value="受益人享有自<%=Format.formatDateCn(ben_date)%>至<%=Format.formatDateCn(ben_end_date)%>的" size="50">
			 </td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2 ><b><FONT face="宋体" size=4>信托受益权状况</FONT></b></td>
			<td align="left" colspan=2>
			<input name="qs_date"   type="text" class='ednoline'    value="信托利益，信托计划终止时剩余信托财产的权利归属人" size="50">
			</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>受益人:</b><FONT face="宋体" size=2><%out.print(cust_name);%></FONT></td>
			<td align="left" colspan=2><input name="qs_date"   type="text" class='ednoline'    value="为：<%=cust_name%>" size="50"></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>证件号码:</b>[<%=card_type_name %>]<font class=date><%out.print(card_id);%></font></td>
			<td align="left" height=25 colspan=2><b>签发人：<FONT face="宋体" size=2><%=application.getAttribute("COMPANY_NAME")%></FONT></b></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>	
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>信托本金:￥</b><FONT face="宋体" size=2><%=Utility.trimNull(Format.formatMoney(ben_amount))%></FONT></td>
			<td align="left"colspan=2 ><b>签发日期：</b><input name="qs_date"   type="text" class='ednoline'    value="<%=Format.formatDateCn(start_date)%>" size="14"></td>	
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>	
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			<td align="left" colspan=2><b>开户银行:</b><FONT face="宋体" size=2><%=bank_name%><%=bank_sub_name%></FONT></td>
			<td align="center" colspan=2></td>	
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>	
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td colspan=4 align="left" ><b>信托利益划付账户：</b><FONT face="宋体" size=2><%=bank_acct%></FONT></td>			
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" ></td>
				<td align="left" colspan=3 >&nbsp;</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
			</tr>
						
		   </TABLE>
		<%}}%>		   
		   	<table id=printbtn border="0" width="100%">
					<tr>
						<td align=center class=noprint>
						<%if (input_operator.hasFunc(menu_id, 107))
{
%>
						<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
						<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:document.all.WebBrowser.ExecWB(6,6);">打印(<u>P</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton4"   id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5"   id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>
						<button type="button"  style="display:" class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
						</td>
					</tr>
				</table>
		</TD>
		<TR>
</TABLE>
</form>

</BODY>
</HTML>
<%
customer.remove();
product.remove();
benifitor.remove();
%>
