<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获取页面传递SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
//页面辅助参数
input_bookCode = new Integer(1);//页面辅助参数
List redeem_list = null;
Map redeem_map = new HashMap();
List product_list = null;
Map product_map = new HashMap();
List contract_list = null;
Map contract_map = new HashMap();
//辅助变量
Integer product_id = new Integer(0);
BigDecimal ht_invest_rate = null;
BigDecimal invest_rate = null;
BigDecimal rg_money = null;
String cust_name = "";
String contract_bh = "";
String product_name = "";
String fee = "";
String print_date = "";
char[] s_money = null;
//获得对象及结果集
RedeemLocal redeemLocal = EJBFactory.getRedeem();
RedeemVO redeem_vo = new RedeemVO();
ProductLocal productLocal = EJBFactory.getProduct();
ProductVO product_vo = new ProductVO();
ContractLocal contract = EJBFactory.getContract();
ContractVO cont_vo = new ContractVO();
//查询信息
if(serial_no != null){
	redeem_vo.setSerial_no(serial_no);
	redeem_list = redeemLocal.listBySql(redeem_vo);
	
	if(redeem_list.size()>0){
		redeem_map = (Map)redeem_list.get(0);	
		product_id = Utility.parseInt(Utility.trimNull(redeem_map.get("PRODUCT_ID")),null);
		contract_bh = Utility.trimNull(redeem_map.get("CONTRACT_BH"));
		rg_money = Utility.parseDecimal(Utility.trimNull(redeem_map.get("REDEEM_AMOUNT")),null);
		Integer date = Utility.parseInt(Utility.trimNull(redeem_map.get("SQ_DATE")),new Integer(Utility.getCurrentDate()));
		print_date = String.valueOf(date); 
		if(rg_money!=null)
			rg_money = rg_money.setScale(2);
		StringBuffer print_money= new StringBuffer("￥"+Format.formatMoneyPrint(rg_money.doubleValue(),2));
		s_money = print_money.reverse().toString().toCharArray();
	}
	
	if(product_id.intValue()>0){
		product_vo.setProduct_id(product_id);
		product_list = productLocal.load(product_vo);
		
		if(product_list.size()>0){
			product_map = (Map)product_list.get(0);			
		}
	}
	
	if(contract_bh.length()>0&&product_id.intValue()>0){
		cont_vo.setProduct_id(product_id);
		cont_vo.setContract_bh(contract_bh);
		cont_vo.setBook_code(input_bookCode);
		cont_vo.setInput_man(input_operatorCode);
		
		contract_list = contract.queryContract(cont_vo);
		
		if(contract_list.size()>0){
			contract_map = (Map)contract_list.get(0);
		}
	}
}
%>

<html> 
<head>  
<title>打印付款通知单</title> 
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style media="print">
.noPrint {
	display: none;
}
td {
	border-style: none;
}
table{
	border:none;
}
</style> 
<style>
TABLE, TR, TD, HR, INPUT, BUTTON, SELECT, TEXTAREA {
	font-family: 宋体, 仿宋_gb2312, 仿宋, tahoma, verdana, arial, @方正姚体;
	font-size: 10pt;
}

.ednoline
{
    BACKGROUND-COLOR: transparent;
    BORDER-BOTTOM: medium none;
    BORDER-LEFT: medium none;
    BORDER-RIGHT: medium none;
    BORDER-TOP: medium none;
}
</style>
<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
</head>
<body topmargin="8" leftmargin="60">
<form name="theform">  
<span class="noPrint"><font color="red">*红色字体部分可以手工录入</font></span>

<TABLE BORDER=0  cellSpacing=0 cellPadding=0 border=0 align="center">
	<TBODY>
		<TR> 
			<TD align="center"> 
				
				<table id="table3" align="center" border="0" cellspacing="0" cellpadding="0" style="width:173mm;">
					<tr>
						<td colspan="4" align="center" height="30"><b><font size="+1" class="noPrint"><%=application.getAttribute("COMPANY_NAME")%></font></b></td>
					</tr>
					<tr>
						<td colspan="4" align="center" height="25"><b><font size="+2" class="noPrint">信托业务付款通知单</font></b></td>
					</tr>
					<tr>
						<td align="right" height="20" width="10%"></td>
						<td align="left" height="20" width=28%"><font style="FONT-SIZE: 9pt;"><%=Utility.trimNull(redeem_map.get("PRODUCT_NAME"))%></font></td>
						<td align="left"width="40%"><%=print_date.substring(0,4)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%=print_date.substring(4,6)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=print_date.substring(6,8)%> </td>
						<td align="right"> <%=Utility.trimNull(Argument.getCurrencyName(Utility.trimNull(product_map.get("currency_id"))))%>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				<table  id="printtable" align="center"  border="1" style="width:173mm;border-collapse:collapse;"  bordercolor="black"  cellspacing="0" cellpadding="0">
					<tr>
						<td style="height:9mm" colspan="1"><span  class="noPrint">合同编号</span></td>
						<td align="left" colspan="3" style="padding-left:11px;"> <%=contract_bh%></td>
						<td align="center"><span  class="noPrint"><font color="red">信托专户</font></span></td>
						<td align="left"colspan="13"  style="padding-left:11px">
							<input name ="bank" class="ednoline" maxlength="42"  size="42"
									value="<%=Utility.trimNull(product_map.get("tg_bank_name"))%><%=Utility.trimNull(product_map.get("tg_bank_sub_name"))%>:<%=Utility.trimNull(product_map.get("tg_bank_acct"))%>">
						</td>
					</tr>
					<tr>
						<td style="height:8mm" colspan="2" ><span class="noPrint">付款单位名称</span></td>
						<td colspan="16">&nbsp;&nbsp;<%=Utility.trimNull(redeem_map.get("CUST_NAME"))%></td>
					</tr>
					<tr>
						<td colspan="2" style="height:9mm;" ><span  class="noPrint">开户银行</span></td>
						<td colspan="2" style="padding-left:11px;"><%=Utility.trimNull(contract_map.get("BANK_NAME"))%></td>
						<td align="center" nowrap><span class="noPrint">银行账号</span></td>
						<td align="left" colspan="13">&nbsp;&nbsp;<%=Utility.trimNull(contract_map.get("BANK_SUB_NAME"))%></td>
					</tr>
					<tr>
						<td colspan="2" style="height:13mm" nowrap rowspan="2"><span class="noPrint">付款金额(大写)</span></td>
						<td align="left" colspan="4" rowspan="2">&nbsp;&nbsp;<%=Utility.amountToChinese(rg_money.doubleValue())%></td>
						<td align="center"style="height:7mm"><span class="noPrint">十</span></td>
						<td align="center"><span class="noPrint">亿</span></td>
						<td align="center"><span class="noPrint">千</span></td>
						<td align="center"><span class="noPrint">百</span></td>
						<td align="center"><span class="noPrint">十</span></td>
						<td align="center"><span class="noPrint">万</span></td>
						<td align="center"><span class="noPrint">千</span></td>
						<td align="center"><span class="noPrint">百</span></td>
						<td align="center"><span class="noPrint">十</span></td>
						<td align="center"><span class="noPrint">元</span></td>
						<td align="center"><span class="noPrint">角</span></td>
						<td align="center"><span class="noPrint">分</span></td>
					</tr>
					<tr>
						<td align="center" style="height:6mm"><%if(s_money.length >= 13)out.print(s_money[12]);%></td>
						<td align="center"><%if(s_money.length >= 12)out.print(s_money[11]);%></td>
						<td align="center"><%if(s_money.length >= 11)out.print(s_money[10]);%></td>
						<td align="center"><%if(s_money.length >= 10)out.print(s_money[9]);%></td>
						<td align="center"><%if(s_money.length >= 9)out.print(s_money[8]);%></td>
						<td align="center"><%if(s_money.length >= 8)out.print(s_money[7]);%></td>
						<td align="center"><%if(s_money.length >= 7)out.print(s_money[6]);%></td>
						<td align="center"><%if(s_money.length >= 6)out.print(s_money[5]);%></td>
						<td align="center"><%if(s_money.length >= 5)out.print(s_money[4]);%></td>
						<td align="center"><%if(s_money.length >= 4)out.print(s_money[3]);%></td>
						<td align="center"><%if(s_money.length >= 3)out.print(s_money[1]);%></td>
						<td align="center" ><%if(s_money.length >= 2)out.print(s_money[0]);%></td>
					</tr>
					<tr>
						<td colspan="2" style="height:9mm" align="right"><span  class="noPrint"><font color="red">收益率:</font></span>
							<input name ="rate"  value=""  class="ednoline" size="5" maxlength="5" style="text-align:right;">%&nbsp;
						</td>
						<td align="center"><span  class="noPrint"><font color="red">期限</font></span></td>
						<td align="center"><input name ="period" value=""  class="ednoline" size="10" maxlength="10"></td>
						<td align="center"><span  class="noPrint"><font color="red">用途</font></span></td>
						<td align="center" colspan="13"><input name ="purpose"  value=""  class="ednoline" size="44" maxlength="44"></td>
					</tr>			
					<tr>
						<td style="height:22mm" align="center"><span  class="noPrint"><font color="red"><b>备注</b></font></span></td>
						<td align="left" valign="middle" colspan="17" style="padding:11px;line-height:20px;">
							 <textarea rows="4" name="remark" cols="60" style="overflow-y:hidden;padding:4px;line-height:20px;" onscroll="javascript:alert('只能输入3行');" class="ednoline"></textarea>	
						</td>
					</tr>
					<tr>
						<td style="height:0mm;width:17mm;"></td>
						<td style="width:14mm;"></td>
						<td style="width:14mm;"></td>
						<td style="width:28mm;"></td>
						<td style="width:16mm;"></td>
						<td style="width:37mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
					</tr>
				</table>

				
				</TD>
		</TR>
	</TBODY>
</TABLE>
<br>

<br />
<table id="printbtn" style="display: " border="0" width="90%" class="noprint">
	<tr>
		<td align="right">
		<object  id="WebBrowser"  classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"  height="0"  width="0"  viewastext="VIEWASTEXT"></object>
	    <button type="button"  class="xpbutton2" accesskey="p" id="btnPrint" name="btnPrint" onclick="javascript:window.print();">打印(<u>P</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accesskey="a" id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accesskey="c" id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  style="display: " class="xpbutton3" accesskey="b" id="btnBack" name="btnBack" onclick="javascript:history.back();">返回(<u>B</u>)</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>