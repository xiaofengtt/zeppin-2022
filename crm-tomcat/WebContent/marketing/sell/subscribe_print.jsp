<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//声明显示参数
//客户信息
Integer cust_id = new Integer(0);
String cust_name = "";
String legal_man = "";
String contact_man = "";
String card_type_name = "";
String card_id = "";
String post_address = "";
String post_code = "";
String mobile = "";
String eMail = "";

//账户信息
String gain_acct = "";
String bank_acct = "";
String bank_name = "";
String bank_sub_name = "";

//认购合同信息
String product_name = "";
BigDecimal rg_money2 = new BigDecimal(0.0);//认购金额 
String  rg_money2_string = "";//认购金额 中文
BigDecimal rg_fee_rate = new BigDecimal(0.0);
BigDecimal rg_fee_money = new BigDecimal(0.0);
BigDecimal jk_total_money = new BigDecimal(0.0);
String jk_total_money_string = ""; //交款总额

//获得对象及结果集
ContractLocal contract = EJBFactory.getContract();
ContractVO cont_vo = new ContractVO();

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
String rg_money_cn = "万元";
String jk_money_cn = "万元";

if(serial_no.intValue()>0){
		List rsList_contract = null;
		Map map_contract = null;
		
		cont_vo.setSerial_no(serial_no);
		rsList_contract = contract.load(cont_vo);
		
		if(rsList_contract.size()>0){
			map_contract = (Map)rsList_contract.get(0);
		}
		
		if(map_contract!=null){
			gain_acct = Utility.trimNull(map_contract.get("GAIN_ACCT"));
			bank_acct = Utility.trimNull(map_contract.get("BANK_ACCT"));
			bank_name = Utility.trimNull(map_contract.get("BANK_NAME"));
			bank_sub_name = Utility.trimNull(map_contract.get("BANK_SUB_NAME"));
			product_name = Utility.trimNull(map_contract.get("PRODUCT_NAME"));
			rg_money2 = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_MONEY2")),new BigDecimal(0));
			if(rg_money2.doubleValue()-10000>0){
				rg_money2_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(rg_money2)));
			}
			else{
				rg_money2_string = Utility.numToChinese(Utility.trimNull(rg_money2));
				rg_money_cn = "元";
			}			
			rg_fee_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_RATE")),new BigDecimal(0));
			rg_fee_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_MONEY")),new BigDecimal(0));
			jk_total_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("JK_TOTAL_MONEY")),new BigDecimal(0));
			if(jk_total_money.doubleValue()-10000>0){
				jk_total_money_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(jk_total_money)));
			}
			else{
				jk_total_money_string = Utility.numToChinese(Utility.trimNull(jk_total_money));
				jk_money_cn = "元";
			}	
			cust_id = Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")),new Integer(0));
		}
		
		//客户信息
		if(cust_id.intValue()>0){
			List rsList_cust = null;
			Map map_cust = null;	
			
			//客户单个值		
			cust_vo.setCust_id(cust_id);
			cust_vo.setInput_man(input_operatorCode);
			rsList_cust = customer.listByControl(cust_vo);
			
			if(rsList_cust.size()>0){
				map_cust = (Map)rsList_cust.get(0);
			}
		
			if(map_cust!=null){				
				cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
				legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
				contact_man = Utility.trimNull(map_cust.get("CONTACT_MAN"));
				card_type_name = Utility.trimNull(map_cust.get("CARD_TYPE_NAME"));
				card_id = Utility.trimNull(map_cust.get("CARD_ID"));
				post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
				post_code = Utility.trimNull(map_cust.get("POST_CODE"));
				mobile = Utility.trimNull(map_cust.get("MOBILE"));
				eMail = Utility.trimNull(map_cust.get("E_MAIL"));
			}		
		}
		
}
%>

<HTML>
<HEAD>
<TITLE>认购合同打印</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/print.css" type=text/css rel=stylesheet>
<style media="print">
.noprint { display: none }
</style>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>

<script language=javascript>
function doPrint(){	
	window.open("subscribe_print_do.jsp?serial_no=<%=serial_no%>");	
}
</script>
</HEAD>

<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post" action="purchase_print.jsp">
	<TABLE height="90%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		<TR>
			<TD vAlign=top align=left>
				<TABLE cellSpacing=0 cellPadding=2 width="704px" height="955px" align=center border=0>
					<tr>
						<td align="center"  style="font-size:22px;line-height:50px">
							<%=Utility.trimNull(application.getAttribute("COMPANY_NAME"))%>信托单位认/申购单
						</td>
					</tr>
					<tr>
						<td  style="font-size:16px;">
							一、投资者基本信息
						</td>
					</tr>		
					<tr>
						<td>
							<table  cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">姓名/名称：</td><td colspan="4"><input class="edline" readonly name="cust_name" style="font-size:15px" size="29" value="<%=Utility.trimNull(cust_name)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">法定代表人：</td><td><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(legal_man) %>"></td>
									<td style="font-size:16px">联系人：</td><td><input class="edline" readonly name="" style="font-size:15px" size="30" value="<%=Utility.trimNull(contact_man) %>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">证件类型：</td><td><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(card_type_name) %>"></td>
									<td style="font-size:16px">证件号码：</td><td><input class="edline" readonly name="" style="font-size:15px" size="30" value="<%=Utility.trimNull(card_id) %>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">通讯地址：</td><td colspan="4"><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(post_address)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">邮政编码</td><td><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(post_code)%>"></td>
									<td style="font-size:16px">移动电话：</td><td><input class="edline" readonly name=mobile"" style="font-size:15px" size="30" value="<%=Utility.trimNull(mobile)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">E-mail:</td><td colspan="4"><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(eMail)%>"></td>
								</tr>
							</table>
						</td>
					</tr>		
					<tr>
						<td style="font-size:16px">
							二、认/申购情况
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">信托计划名称：</td><td  colspan="3"style="font-size:16px"><INPUT class="edline" readonly name="product_name" style="font-size:15px" size="69" value="<%=Utility.trimNull(product_name)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">认/申购金额：</td><td  colspan="3"style="font-size:16px">人民币<INPUT class="edline" readonly name="rg_money" style="font-size:15px" size="28" value="<%=rg_money2_string.substring(0,rg_money2_string.indexOf("元"))%>"><%=rg_money_cn%>整(小写金额￥<INPUT class="edline" readonly name="rg_money" style="font-size:15px" size="10" value="<%=Utility.trimNull(rg_money2)%>">)。</td>
								</tr>
								<tr>
									<td style="font-size:16px">认/申购费率：</td><td style="font-size:16px"><INPUT class="edline" readonly name="fee_rate" style="font-size:15px" size="28" value="<%=Utility.trimNull(rg_fee_rate)%>">%</td><td width="80px" style="font-size:16px">认购费</td><td><INPUT class="edline" readonly name="fee_amount" style="font-size:15px" size="29" <%if(rg_fee_money ==null){%>value=""<%}else{%>value="<%=Utility.numToChinese(Utility.trimNull(rg_fee_money))%>"<%}%>></td>
								</tr>
								<tr>
									<td style="font-size:16px">交款总额</td><td  colspan="3"style="font-size:16px">人民币<INPUT class="edline" readonly  name="to_money" style="font-size:15px" size="33" value="<%=jk_total_money_string.substring(0,jk_total_money_string.indexOf("元"))%>"><%=jk_money_cn%>整（小写金额￥<INPUT class="edline" readonly name="to_money" style="font-size:15px" size="10" value="<%=Utility.trimNull(jk_total_money)%>">)。</td>
								</tr>
							</table>
						</td>	
					</tr>	
					<tr>
						<td style="font-size:16px">
							三、信托利益分配账户
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td  style="font-size:16px" colspan="2">投资者指定以下账户用于接受受托人分配的信托利益</td>
								</tr>
								<tr>
									<td style="font-size:16px">账户名：</td><td style="font-size:16px"><INPUT class="edline" readonly name="gain_acct" style="font-size:15px" size="50" value="<%=Utility.trimNull(gain_acct)%>"></td>	
								</tr>
								<tr>
									<td style="font-size:16px">账　号：</td><td style="font-size:16px"><INPUT class="edline" readonly name="" style="font-size:15px" size="50" value="<%=Utility.trimNull(bank_acct)%>"></td>	
								</tr>
								<tr>
									<td style="font-size:16px">开户行：</td><td style="font-size:16px"><INPUT class="edline" readonly name="" style="font-size:15px" size="50" value="<%=Utility.trimNull(bank_name)%>&nbsp;<%=Utility.trimNull(bank_sub_name)%>"></td>	
								</tr>
							</table>	
						</td>
					</tr>	
					<tr>
						<td style="font-size:16px">
							四、信托利益分配方式的选择(适用时)
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px" colspan="2">信托计划存续期间，投资者选择如下方式取得信托利益:</td>
								</tr>
								<tr>
									<td style="font-size:16px">□现金分红方式</td><td style="font-size:16px">□分红转投资方式</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td style="font-size:16px">
							五、申购不成功时的选择(适用时)
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px" colspan="2">因投资者的申购资金未能在开放日前一个工作日到达受托人账户，或者申购文件未能在开放日前一个工作日送达受托人时或其他情况导致申购不成功时，投资者选择:</td>
								</tr>
								<tr>
									<td style="font-size:16px">□全部申购资金退回投资者</td>
								</tr>
								<tr>
									<td style="font-size:16px">□全部申购资金直接作为下一个开放日的申购资金</td>
								</tr>								
							</table>
						</td>
					</tr>	
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px;font-weight:bold">特别提示：委托人签署本认/申购单即代表已阅读并签署了所有信托文件。委托人在本认/申购单上签字即代表对本认/申购单填写信息真实性和准确性的确认。如需办理退款手续，委托人签署本认购单则代表委托人同意将认购资金退还至上述预留信托利益分配账户。本信托单位认/申购单仅作为客户认购凭据，实际认购金额以受托人<%=application.getAttribute("COMPANY_NAME")%>出具的信托单位确认单为准。</td>
								</tr>						
							</table>
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">委托人签章：</td><td style="font-size:16px">受托人签章：</td>	
								</tr>	
								<tr>
									<td></td>
									<td></td>
								</tr>
							</table>
						</td>
					</tr>			
					<tr><td align="right" style="font-size:16px">日期：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</td></tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">经办：</td><td style="font-size:16px">复核：</td>	
								</tr>	
							</table>
						</td>
					</tr>																																																																										
				</table>
				<tr>
					<td align="right"><button type="button"   class="xpbutton3" name="btnPrint" id="btnPrint" onclick="javascript:doPrint();">打印</button>&nbsp;&nbsp;&nbsp;<button type="button" class="xpbutton3" name="btnCancel" onclick="javascript:history.back();">取消</button>&nbsp;&nbsp;</td>
				</tr>
				<tr><td>&nbsp;</td><tr>
			</TD>
		</TR>
	</TABLE>

</FORM>

<script language=javascript>
window.onload = function(){
	var cust_name = document.getElementById("cust_name").value;
	
	if(cust_name==""){		
		sl_alert("客户信息不存在");
	}
}
</script>
</BODY>
</HTML>