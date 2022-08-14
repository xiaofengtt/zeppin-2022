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
			rg_money2_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(rg_money2)));
			rg_fee_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_RATE")),new BigDecimal(0));
			rg_fee_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_MONEY")),new BigDecimal(0));
			jk_total_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("JK_TOTAL_MONEY")),new BigDecimal(0));
			jk_total_money_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(jk_total_money)));	
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
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<style media="print">
.noprint { display: none }
</style>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
</HEAD>

<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0 onload="javascript:print();close();">

<FORM name="theform" method="post" action="purchase_print.jsp">
	<TABLE height="90%" cellSpacing=0 cellPadding=0 width="710px" border=0  >
		<TR>
			<TD vAlign=top align=left>
				<TABLE cellSpacing=0 cellPadding=2 width="704px" height="955px" align=center border=0>
					<tr>
						<td align="center">
						</td>
					</tr>
					<tr>
						<td  style="font-size:16px;" height="120px">
						</td>
					</tr>		
					<tr>
						<td>
							<table  cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="160px">
								<tr>
										<td>
										<table>
											<tr><td style="font-size:16px" width="60px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(cust_name)%></td></tr></table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px" width="80px"></td>
												<td  style="font-size:16px" width="140px"><%=Utility.trimNull(legal_man) %></td>
												<td  style="font-size:16px" width="140px"></td>
												<td  style="font-size:16px"><%=Utility.trimNull(contact_man) %></td>
											</tr>
										</table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px"width="60px"></td><td  style="font-size:16px" width="140px"><%=Utility.trimNull(card_type_name) %></td><td  style="font-size:16px" width="140px"></td><td  style="font-size:16px"><%=Utility.trimNull(card_id) %></td></tr></table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px"width="60px"></td><td style="font-size:15px"><%=Utility.trimNull(post_address)%></td>
											</tr>	
										</table>	
									</td>
									
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0><tr><td style="font-size:16px" height="16px"width="50px"></td><td  style="font-size:16px" width="140px"><%=Utility.trimNull(post_code)%></td><td  style="font-size:16px" width="140px"></td><td  style="font-size:16px"><%=Utility.trimNull(mobile)%></td></tr></table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px"width="60px"></td><td style="font-size:16px"><%=Utility.trimNull(eMail)%></td>
											</tr>	
										</table>	
									</td>
								</tr>
							</table>
						</td>
					</tr>		
					<tr>
						<td style="font-size:16px" height="18px">
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0 height="90px">
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="100px"></td>
												<td style="font-size:16px" height="16px"><%=Utility.trimNull(product_name)%></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="150px"></td>
												<td style="font-size:16px" width="120px"><%=rg_money2_string.substring(0,rg_money2_string.indexOf("元"))%></td>
												<td  width="160px"></td>
												<td style="font-size:16px"><%=Utility.trimNull(rg_money2)%></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="90px"></td>
												<td style="font-size:16px" height="16px"  width="120px"><%=Utility.trimNull(rg_fee_rate)%></td>
												<td width="140px"></td>
												<td style="font-size:16px"><%if(rg_fee_money ==null){%><%}else{%><%=Utility.numToChinese(Utility.trimNull(rg_fee_money))%><%}%></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="130px"></td>
												<td style="font-size:16px"  width="120px"><%=jk_total_money_string.substring(0,jk_total_money_string.indexOf("元"))%></td>
												<td  width="140px"></td><td style="font-size:16px"><%=Utility.trimNull(jk_total_money)%></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>	
					</tr>	
					<tr>
						<td style="font-size:16px" height="18px">
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="110px">
								<tr>
									<td  style="font-size:16px" colspan="2" height="16px"></td>
								</tr>
								<tr>
									<td style="font-size:16px"width="60px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(gain_acct)%></td>	
								</tr>
								<tr>
									<td style="font-size:16px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(bank_acct)%></td>	
								</tr>
								<tr>
									<td style="font-size:16px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(bank_name)%>&nbsp;<%=Utility.trimNull(bank_sub_name)%></td>	
								</tr>
							</table>	
						</td>
					</tr>	
					<tr>
						<td style="font-size:16px">
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0  height="70px">
								<tr>
									<td style="font-size:16px" colspan="2"></td>
								</tr>
								<tr>
									<td style="font-size:16px"></td><td style="font-size:16px"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td style="font-size:16px">
							
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0  height="120px">
								<tr>
									<td style="font-size:16px" colspan="2"></td>
								</tr>
								<tr>
									<td style="font-size:16px"></td>
								</tr>
								<tr>
									<td style="font-size:16px"></td>
								</tr>								
							</table>
						</td>
					</tr>	
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="110px">
								<tr>
									<td style="font-size:16px;font-weight:bold"></td>
								</tr>						
							</table>
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="80px">
								<tr>
									<td style="font-size:16px"></td><td style="font-size:16px"></td>	
								</tr>	
								<tr>
									<td></td>
									<td></td>
								</tr>
							</table>
						</td>
					</tr>			
					<tr><td align="right" style="font-size:18px"></td></tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px"></td><td style="font-size:16px"></td>	
								</tr>	
							</table>
						</td>
					</tr>																																																																										
				</table>
			</TD>
		</TR>
	</TABLE>
</FORM>

</BODY>
</HTML>
