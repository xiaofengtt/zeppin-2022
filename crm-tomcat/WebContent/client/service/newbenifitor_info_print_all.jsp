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
ContractLocal contract=EJBFactory.getContract();
ContractVO contract_vo = new ContractVO();
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_bh = request.getParameter("contract_bh");

Map map = null;
Map cust_map = null;
Map product_map = null;
Map contract_map = null;
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
<style type="text/css"> 
@media print {.noprint{display: none;}}
</style>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

function refreshPage()
{
	StartQuery();
}


</script>

<BODY class="BODY">
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
<%
String paramValue="";
if (paramValues != null)
{
	int count=0;
	
	for(int i = 0;i <paramValues.length; i++)
	{
	    count++;
	    paramValue=paramValues[i];
	    String[] para=Utility.splitString(paramValue,"$");
        Integer serial_no = Utility.parseInt(para[0], null);
contract_bh = para[1];
java.math.BigDecimal totalsum=new java.math.BigDecimal(0);
Integer cust_type=new Integer(1);
Integer cust_id = new Integer(0);
Integer list_id = new Integer(0);
String cust_name = "";
String card_type_name = "";
String card_id = "";
String product_code = "";
String post_code = "";
String post_address = "";
String product_name = "";
Integer start_date = new Integer(0);
BigDecimal fact_money = new BigDecimal(0);
Integer fact_num = new Integer(0);
String H_tel = "";
String mobile = "";
String e_mail = "";
Integer ben_date = new Integer(0);
Integer qs_date = new Integer(0);
String bank_name = "";
String bank_acct = "";
BigDecimal rg_money = new BigDecimal(0);
BigDecimal to_amount = new BigDecimal(0);
String contractBh = "";

if(serial_no!=null)
{
	vo.setSerial_no(serial_no);
	List list = benifitor.load1(vo);
	if(list.size()>0){
		map = (Map)list.get(0); 
	}
	if(map != null){
		//cust_id = Utility.trimNull(map.get("CUST_ID"));
		list_id = Utility.parseInt(Utility.trimNull(map.get("LIST_ID")),new Integer(0));
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
		product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
		ben_date = Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0));
		/*bank_name = Utility.trimNull(map.get("BANK_NAME"));
		bank_sub_name = Utility.trimNull(map.get("BANK_SUB_NAME"));
		ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0),2,"1"); 
		bank_acct = Utility.trimNull(map.get("BANK_ACCT"));*/
	}
	cust_vo.setBook_code(input_bookCode);
	cust_vo.setProduct_id(product_id);
	cust_vo.setContract_bh(contract_bh);
	cust_vo.setCust_id(cust_id);
	cust_vo.setCust_type(list_id); 
	cust_vo.setInput_man(input_operatorCode);
	List cust_list = customer.listCustomerLoad(cust_vo);
	
	if(cust_list.size()>0){
		cust_map = (Map)cust_list.get(0); 
	}
	if(cust_map != null){
		cust_name = Utility.trimNull(cust_map.get("CUST_NAME"));
		card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
		card_id = Utility.trimNull(cust_map.get("CARD_ID"));
		contract_bh = Utility.trimNull(cust_map.get("POST_CODE"));
		post_code = Utility.trimNull(cust_map.get("POST_CODE"));
		post_address = Utility.trimNull(cust_map.get("POST_ADDRESS"));
		fact_num = Utility.parseInt(Utility.trimNull(map.get("FACT_NUM")),new Integer(0));
		H_tel = Utility.trimNull(cust_map.get("H_TEL"));
		mobile = Utility.trimNull(cust_map.get("MOBILE"));
		e_mail = Utility.trimNull(cust_map.get("E_MAIL"));
		
	}
	
	product_vo.setProduct_id(product_id);
	List product_list = product.load(product_vo);
	 
	if(product_list.size()>0){
		product_map = (Map)product_list.get(0); 
	}
	if(product_name != null){
		product_name = Utility.trimNull(cust_map.get("PRODUCT_NAME"));
		start_date = Utility.parseInt(Utility.trimNull(product_map.get("START_DATE")),new Integer(0));
		fact_money = Utility.parseDecimal(Utility.trimNull(product_map.get("FACT_MONEY")),new BigDecimal(0),2,"1"); 
	}
	
	contract_vo.setBook_code(input_bookCode);
	contract_vo.setProduct_id(product_id);
	contract_vo.setProduct_code(product_code);
	contract_vo.setContract_bh(contract_bh);
	contract_vo.setInput_man(input_operatorCode);
	List contract_list = contract.queryContract(contract_vo);
	 
	if(contract_list.size()>0){
		contract_map = (Map)contract_list.get(0); 
	}
	if(contract_map != null){
		bank_name = Utility.trimNull(map.get("BANK_NAME"));
		bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
		to_amount = Utility.parseDecimal(Utility.trimNull(map.get("TO_AMOUNT")),new BigDecimal(0),2,"1"); 
		rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0),2,"1"); 
		contractBh = Utility.trimNull(map.get("CONTRACT_BH"));
	}

}%>	
	
		<TABLE id="blankid" style=<%if(count>1)out.print("page-break-before:always");%>  cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tr>
				<td align="center" height=40></td>
			</tr>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0 >
			<tr>
				<td align="left" height=25></td>
				<td colspan="2">
						<hr class=noprint color="#000000"  size="1">
						</td>
			</tr>
			
			
			<tr >
				<td align="left" height=25></td>
				<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT class="font16"><%out.print(post_code);%></font></td></tr>
			<tr>
			<td align="left" height=25></td>
				<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT class="font16"><%out.print(post_address);%></font></td></tr>
			<tr>
			    <td align="left" height=25></td>
				<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT face="宋体" size=2><%out.print(cust_name);%>(收)</font></td></tr>
			<tr>
			    <td align="left" height=25></td>
				<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT class="font12"><%=Utility.trimNull(contract_bh)%></FONT></td><td>
			</tr>
			<tr>
			    <td align="left" height=25></td>
				<td align="left"></td>
				<td align="left" ></td>
			</tr>
			<tr>
			    <td align="left" height=25></td>
				<td align="left"></td>
				<td align="left" ></td>
			</tr>
			<tr>
				<td align="center" colspan=3><H2>受益人证明</H2></td>
			</tr>
			
			<tr>
			<td align="left" ></td>
				<td align="left" height=25 colspan=2></td>
				
			</tr>
			<tr>
				<td align="left" ></td>
				<td align="left" colspan=2 rowspan="5" ><FONT class="font14">&nbsp;&nbsp;&nbsp;本受益人证明根据信托文件的约定，由本信托计划的受益人持有，是进行转让、质押、继承和遗失登记等信托处分的基本依据，但前述所有信托处分事项均须至受托人处办理手续，否则无效。受益人应按信托文件的约定，享有权利并履行义务。本信托计划由<%=application.getAttribute("COMPANY_NAME")%>作为受托人管理信托财产，与信托专户开户银行无关。敬请您仔细核对本受益人证明记载的各项资料。</FONT></td>
			</tr>
			<tr>
				<td align="left" ></td></tr>
			<tr>
			<td align="left" ></td></tr>
			<tr>
			<td align="left" ></td></tr>
			<tr>
			<td align="left" ></td></tr>
			<tr>
			<td align="left" ></td>
				<td align="left" height=25 colspan=2></td>
				
			</tr>
			<tr>
			<td align="left" ></td>
				<td align="left" colspan=3>信托计划:<%=Utility.trimNull(product_name)%></td>
			</tr>
			<tr>
			<td align="left" ></td>
				<td align="left" colspan=2>成立日:<font class=date><%=Utility.trimNull(Format.formatDateCn(start_date))%></font></td>
				
			</tr>
			<tr>
			<td align="left" ></td>
				<td align="left"  colspan=2>实际筹资:<font class=date><%=Utility.trimNull(Format.formatMoney(fact_money))%></font>元</td>
				
			</tr>
			<tr>	
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left"  colspan=2>合同份数:<font class=date><%=Utility.trimNull(fact_num)%></font>份</td>
				
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" height=25  colspan=2></td>
				
			</tr>
			<tr>	
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>  受益人:<FONT face="宋体" size=2><%out.print(Utility.trimNull(cust_name));%></FONT></td>
			</tr>
			<tr>	
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>邮编:<font class=date><%out.print(Utility.trimNull(post_code));%></font></td>
				
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>联系地址:<%out.print(Utility.trimNull(post_address));%></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>证件号码:[<%=Utility.trimNull(card_type_name)%>]<font class=date><%out.print(Utility.trimNull(card_id));%></font></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>固定电话:<font class=date><%=Utility.trimNull(H_tel)%></font></td>
			</tr>
			<tr>
			<td align="left">&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>移动电话:<font class=date><%=Utility.trimNull(mobile)%></font></td>
			</tr>
			<td align="left">&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>电子邮件:<%=Utility.trimNull(e_mail)%></td>
			</tr>
			<tr>
				<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td colspan=2 align="left" ></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>信托合同/受益权转让协议编号:<font class=date><%=Utility.trimNull(contractBh)%>-<%=Utility.trimNull(list_id)%></font></td>
			</tr>
			<tr> 
				<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>份额：<font class=date><%=Utility.trimNull(Format.formatMoney(to_amount))%></font>份</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>价值（人民币）:<font class=date><%=Utility.trimNull(Format.formatMoney(rg_money))%></font>元</td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>信托利益帐户:[<%=Utility.trimNull(bank_name)%>]<font class=date><%=Utility.trimNull(bank_acct)%></font></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>签约日:<font class=date><%=Utility.trimNull(Format.formatDateCn(qs_date))%></font></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>受益起始日:<font class=date><%=Utility.trimNull(Format.formatDateCn(ben_date))%></font></td>
			</tr>
			<tr>
				<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td colspan=2 align="left" ></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="left" colspan=2>再转让、回购、质押、遗失等信托事项登记：</td>
			</tr>
			
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td colspan=2 align="center" height=100></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="right" colspan=2>受托人：<%=application.getAttribute("COMPANY_NAME")%></td>
			</tr>
			<tr>
			<td align="left" >&nbsp;&nbsp;&nbsp;</td>
				<td align="right"  colspan=2><font class=date><%=Format.formatDateCn(new Integer(Utility.getCurrentDate()))%></font></td>
			</tr>
			
		   </TABLE>
		<%}}%>		   
		   	<table id=printbtn border="0" width="100%">
					<tr>
						<td align=center class=noprint>
						<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
						<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:document.all.WebBrowser.ExecWB(6,6);">打印(<u>P</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
						&nbsp;&nbsp;&nbsp;
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
contract.remove();
%>
