<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//客户信息
Integer cust_id = new Integer(0);
String cust_name = "";

//产品信息
Integer product_id = new Integer(0);
String bg_bank_name = "";

//账户信息
String gain_acct = "";
String bank_acct = "";
String bank_name = "";
String bank_sub_name = "";

//认购合同信息
String product_name = "";
String contract_sub_bh = "";//合同编号
String contract_bh = "";
String contract_name = "";//合同名称
BigDecimal rg_money2 = new BigDecimal(0.0);//认购金额 
BigDecimal to_amount = new BigDecimal(0.0);
BigDecimal rg_fee_rate = new BigDecimal(0.0);
BigDecimal rg_fee_money = new BigDecimal(0.0);
BigDecimal jk_total_money = new BigDecimal(0.0);
String  rg_money2_string = "";//认购金额 中文
String jk_total_money_string = ""; //交款总额
Integer qs_date = new Integer(0);//申请日期
Integer start_date = new Integer(0);
Integer to_bank_date = new Integer(0); 

//已持有份额及原持有份额
BigDecimal total_amount = new BigDecimal(0.0);
BigDecimal past_amount = new BigDecimal(0.0);

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
ContractLocal contract = EJBFactory.getContract();
ContractVO cont_vo = new ContractVO();

ProductLocal product = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();

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
			to_amount = Utility.parseDecimal(Utility.trimNull(map_contract.get("TO_AMOUNT")),new BigDecimal(0),2,"1");
			rg_money2 = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_MONEY2")),new BigDecimal(0),2,"1");
			rg_money2_string = Utility.numToChinese(Utility.trimNull(rg_money2));
			rg_fee_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_RATE")),new BigDecimal(0));
			rg_fee_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_MONEY")),new BigDecimal(0),2,"1");
			jk_total_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("JK_TOTAL_MONEY")),new BigDecimal(0),2,"1");
			jk_total_money_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(jk_total_money)));	
			cust_id = Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")),new Integer(0));
			qs_date = Utility.parseInt(Utility.trimNull(map_contract.get("QS_DATE")),new Integer(0)); 
			contract_sub_bh = Utility.trimNull(map_contract.get("CONTRACT_SUB_BH"));
			contract_bh = Utility.trimNull(map_contract.get("CONTRACT_BH"));
			product_id = Utility.parseInt(Utility.trimNull(map_contract.get("PRODUCT_ID")),new Integer(0));
			cust_name = Utility.trimNull(map_contract.get("CUST_NAME"));
			start_date = Utility.parseInt(Utility.trimNull(map_contract.get("START_DATE")),new Integer(0)); 
			
			to_bank_date = contract.getToBankDate(cust_id,product_id,contract_bh);
			total_amount = (benifitor.getTotalBenAmount(cust_id,product_id)).setScale(2);
			past_amount = (total_amount.subtract(to_amount)).setScale(2);
		}

		//System.out.println("-------------product_id"+product_id);
		//产品相关信息查询
		if(product_id.intValue()>0){
			List rsList_product = null;
			Map map_product = null;	
			
			//产品单个值
			p_vo.setProduct_id(product_id);
			rsList_product = product.load(p_vo);
			
			if(rsList_product!=null){
				map_product = (Map)rsList_product.get(0);
			}
			
			if(map_product!=null){
				bg_bank_name = Utility.trimNull(map_product.get("BG_BANK_NAME"));
				contract_name = Utility.trimNull(map_product.get("TRUST_CONTRACT_NAME"));
			}
			
		}
}
%>

<HTML>
<HEAD>
<TITLE>国民信托认购确认书打印</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<script language=javascript>
function doPrint(){	
	document.getElementById("buttonDiv").style.display = "none";
	window.print();
}
</script>
</HEAD>

<BODY  leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post">
<DIV style="width:100%;height:90%" align="center">
	<DIV style="width:704px;height:955px">
				<div align="center"  style="font-size:22px;line-height:50px"><%=application.getAttribute("COMPANY_NAME")%>信托认购和增资确认书</div>
		<div style="font-size:16px;" align="left">
			<b>尊敬的<input class="edline" readonly name="" style="font-size:16px" size="15" value=" <%=Utility.trimNull(cust_name)%>" />先生/女士：</b>
		</div>
		<br>
		<div style="font-size:16px" align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 根据本信托合同的规定，我司作为受托人已将您认购本信托计划信托单位的认购资金从认购账户划入信托计划专用银行账户，并已将认购资金按照信托合同的规定换算成信托单位份数。您此次认购信托单位的信息如下：
		</div>
		<br>

		<div style="font-size:16px;width:704px;" align="left">
			<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="tr1"><td align="right" colspan="3" style="font-size:16px;"><div style="margin-right:50%;"><b>基本信息</b></div></td></tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;信托计划名称</td>
					<td style="font-size:16px;">&nbsp;<%=Utility.trimNull(product_name)%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;信托合同名称</td>
					<td style="font-size:16px;">&nbsp;<%=contract_name%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合同编号</td>
					<td style="font-size:16px;">&nbsp;<%=contract_sub_bh%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;委托人名称</td>
					<td style="font-size:16px;">&nbsp;<%=Utility.trimNull(cust_name)%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保管银行</td>
					<td>&nbsp;<%= bg_bank_name%></td>
				</tr>
				
				<tr class="tr1">
					<td width="140" rowspan="3" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认购帐户</td>
					<td width="60"  align="center" style="font-size:16px;">户名</td>
					<td  style="font-size:16px;">&nbsp;<%=Utility.trimNull(gain_acct)%></td>
				</tr>
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">账号</td>
					<td  style="font-size:16px;">&nbsp;<%=Utility.trimNull(bank_acct)%></td>
				</tr>
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">开户行</td>
					<td  style="font-size:16px;">&nbsp;<%=Utility.trimNull(bank_name)%>&nbsp;<%=Utility.trimNull(bank_sub_name)%></td>
				</tr>
				
			<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认购资金到账日期</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(to_bank_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				
				<tr class="tr1">
					<td width="140" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认购资金金额</td>
					<td width="60" align="center" style="font-size:16px;">大写</td>
					<td style="font-size:16px;">
							<div  style="width=60%;">
								<div style="float:left; width=60px" align="left">&nbsp;人民币</div>
								<div style="float:right; " align="right"><%if(rg_money2 !=null){out.print(Utility.numToChinese(Utility.trimNull(rg_money2)));}%></div>			
							</div>							
					</td>
				</tr>
				
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">小写</td>
					<td style="font-size:16px;">
						<div  style="width=60%;">
							<div style="float:left; width=20px" align="left">&nbsp;￥</div>
							<div style="float:right;"  align="right"><%= Utility.trimNull(rg_money2)%>元&nbsp;&nbsp;&nbsp;</div>
						</div>								
					</td>
				</tr>
				<tr class="tr1">
					<td width="140" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认购费</td>
					<td width="60" align="center" style="font-size:16px;">大写</td>
					<td style="font-size:16px;">
							<div  style="width=60%;">
								<div style="float:left; width=60px" align="left">&nbsp;人民币</div>
								<div style="float:right; " align="right"><%if(rg_fee_money !=null){out.print(Utility.numToChinese(Utility.trimNull(rg_fee_money)));}%></div>			
							</div>				
					</td>
				</tr>
				
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">小写</td>
					<td style="font-size:16px;" align="left">
					<div  style="width=60%;">
						<div style="float:left; width=20px" align="left">&nbsp;￥</div>
						<div style="float:right;"  align="right"><%= Utility.trimNull(rg_fee_money)%>元&nbsp;&nbsp;&nbsp;</div>
					</div>					
					</td>
				</tr>
				
				<tr class="tr1"><td align="center" colspan="3" style="font-size:16px;"><b>认购结果</b></td></tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认购申请日期</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(qs_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对应认购日</td>
					<td  style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(start_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="300" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本认购日认购的信托单位份数</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=to_amount%>份&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原已持有的信托单位份数</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=past_amount%>份&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现共持有的信托单位份数</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=total_amount%>份&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
			</table>
		</div>
		<br>
		<div style="font-size:16px;" align="left">
			<table  border="0" cellSpacing=0 cellPadding=2>
				<tr>
					<td style="font-size:16px;width:400px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 特此确认。</td>
					<td>&nbsp;&nbsp;</td>
				</tr>
				
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;受托人：<%=application.getAttribute("COMPANY_NAME")%></td>
				</tr>
				
				<tr>
					<td colspan="2">&nbsp;&nbsp;</td>
				</tr>		
				<tr>
					<td colspan="2">&nbsp;&nbsp;</td>
				</tr>		
				
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;法定代表人</td>
				</tr>
				
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;或授权代表（签字或盖章）：</td>
				</tr>
				
				<tr>
					<td colspan="2">&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;日期：<input class="edline" readonly name="" style="font-size:15px" size="5"/>年<input class="edline" readonly name="" style="font-size:15px" size="5"/>月<input class="edline" readonly name="" style="font-size:15px" size="5"/>日</td>
				</tr>
			
			</table>
		</div>
		<br>
		<div style="font-size:16px;" align="left">&nbsp;&nbsp;<b>说明：本确认书一式二份，由受托人填写，委托人和受托人各留存一份。</b></div>
		<br>
		<div align="right"  id="buttonDiv">
			<button type="button"  class="xpbutton3" name="btnPrint" id="btnPrint" onclick="javascript:doPrint();">打印</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"   class="xpbutton3" name="btnCancel" onclick="javascript:window.close();">关闭</button>
			&nbsp;&nbsp;
		</div>
	</DIV>
</DIV>
<br>
</FROM>
</BODY>
</HTML>
<%
contract.remove();
product.remove();
%>