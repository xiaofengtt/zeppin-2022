<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ȡҳ�洫��SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//�ͻ���Ϣ
Integer cust_id = new Integer(0);
String cust_name = "";

//��Ʒ��Ϣ
Integer product_id = new Integer(0);
String bg_bank_name = "";

//�˻���Ϣ
String gain_acct = "";
String bank_acct = "";
String bank_name = "";
String bank_sub_name = "";

//�Ϲ���ͬ��Ϣ
String product_name = "";
String contract_sub_bh = "";//��ͬ���
String contract_bh = "";
String contract_name = "";//��ͬ����
BigDecimal rg_money2 = new BigDecimal(0.0);//�Ϲ���� 
BigDecimal to_amount = new BigDecimal(0.0);
BigDecimal rg_fee_rate = new BigDecimal(0.0);
BigDecimal rg_fee_money = new BigDecimal(0.0);
BigDecimal jk_total_money = new BigDecimal(0.0);
String  rg_money2_string = "";//�Ϲ���� ����
String jk_total_money_string = ""; //�����ܶ�
Integer qs_date = new Integer(0);//��������
Integer start_date = new Integer(0);
Integer to_bank_date = new Integer(0); 

//�ѳ��зݶԭ���зݶ�
BigDecimal total_amount = new BigDecimal(0.0);
BigDecimal past_amount = new BigDecimal(0.0);

//��ö��󼰽����
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
		//��Ʒ�����Ϣ��ѯ
		if(product_id.intValue()>0){
			List rsList_product = null;
			Map map_product = null;	
			
			//��Ʒ����ֵ
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
<TITLE>���������Ϲ�ȷ�����ӡ</TITLE>
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
				<div align="center"  style="font-size:22px;line-height:50px"><%=application.getAttribute("COMPANY_NAME")%>�����Ϲ�������ȷ����</div>
		<div style="font-size:16px;" align="left">
			<b>�𾴵�<input class="edline" readonly name="" style="font-size:16px" size="15" value=" <%=Utility.trimNull(cust_name)%>" />����/Ůʿ��</b>
		</div>
		<br>
		<div style="font-size:16px" align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ���ݱ����к�ͬ�Ĺ涨����˾��Ϊ�������ѽ����Ϲ������мƻ����е�λ���Ϲ��ʽ���Ϲ��˻��������мƻ�ר�������˻������ѽ��Ϲ��ʽ������к�ͬ�Ĺ涨��������е�λ���������˴��Ϲ����е�λ����Ϣ���£�
		</div>
		<br>

		<div style="font-size:16px;width:704px;" align="left">
			<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="tr1"><td align="right" colspan="3" style="font-size:16px;"><div style="margin-right:50%;"><b>������Ϣ</b></div></td></tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���мƻ�����</td>
					<td style="font-size:16px;">&nbsp;<%=Utility.trimNull(product_name)%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���к�ͬ����</td>
					<td style="font-size:16px;">&nbsp;<%=contract_name%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ͬ���</td>
					<td style="font-size:16px;">&nbsp;<%=contract_sub_bh%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ί��������</td>
					<td style="font-size:16px;">&nbsp;<%=Utility.trimNull(cust_name)%></td>
				</tr>
				<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������</td>
					<td>&nbsp;<%= bg_bank_name%></td>
				</tr>
				
				<tr class="tr1">
					<td width="140" rowspan="3" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ϲ��ʻ�</td>
					<td width="60"  align="center" style="font-size:16px;">����</td>
					<td  style="font-size:16px;">&nbsp;<%=Utility.trimNull(gain_acct)%></td>
				</tr>
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">�˺�</td>
					<td  style="font-size:16px;">&nbsp;<%=Utility.trimNull(bank_acct)%></td>
				</tr>
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">������</td>
					<td  style="font-size:16px;">&nbsp;<%=Utility.trimNull(bank_name)%>&nbsp;<%=Utility.trimNull(bank_sub_name)%></td>
				</tr>
				
			<tr class="tr1">
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ϲ��ʽ�������</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(to_bank_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				
				<tr class="tr1">
					<td width="140" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ϲ��ʽ���</td>
					<td width="60" align="center" style="font-size:16px;">��д</td>
					<td style="font-size:16px;">
							<div  style="width=60%;">
								<div style="float:left; width=60px" align="left">&nbsp;�����</div>
								<div style="float:right; " align="right"><%if(rg_money2 !=null){out.print(Utility.numToChinese(Utility.trimNull(rg_money2)));}%></div>			
							</div>							
					</td>
				</tr>
				
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">Сд</td>
					<td style="font-size:16px;">
						<div  style="width=60%;">
							<div style="float:left; width=20px" align="left">&nbsp;��</div>
							<div style="float:right;"  align="right"><%= Utility.trimNull(rg_money2)%>Ԫ&nbsp;&nbsp;&nbsp;</div>
						</div>								
					</td>
				</tr>
				<tr class="tr1">
					<td width="140" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ϲ���</td>
					<td width="60" align="center" style="font-size:16px;">��д</td>
					<td style="font-size:16px;">
							<div  style="width=60%;">
								<div style="float:left; width=60px" align="left">&nbsp;�����</div>
								<div style="float:right; " align="right"><%if(rg_fee_money !=null){out.print(Utility.numToChinese(Utility.trimNull(rg_fee_money)));}%></div>			
							</div>				
					</td>
				</tr>
				
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">Сд</td>
					<td style="font-size:16px;" align="left">
					<div  style="width=60%;">
						<div style="float:left; width=20px" align="left">&nbsp;��</div>
						<div style="float:right;"  align="right"><%= Utility.trimNull(rg_fee_money)%>Ԫ&nbsp;&nbsp;&nbsp;</div>
					</div>					
					</td>
				</tr>
				
				<tr class="tr1"><td align="center" colspan="3" style="font-size:16px;"><b>�Ϲ����</b></td></tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ϲ���������</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(qs_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ӧ�Ϲ���</td>
					<td  style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(start_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="300" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���Ϲ����Ϲ������е�λ����</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=to_amount%>��&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ԭ�ѳ��е����е�λ����</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=past_amount%>��&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ֹ����е����е�λ����</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=total_amount%>��&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
			</table>
		</div>
		<br>
		<div style="font-size:16px;" align="left">
			<table  border="0" cellSpacing=0 cellPadding=2>
				<tr>
					<td style="font-size:16px;width:400px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; �ش�ȷ�ϡ�</td>
					<td>&nbsp;&nbsp;</td>
				</tr>
				
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;�����ˣ�<%=application.getAttribute("COMPANY_NAME")%></td>
				</tr>
				
				<tr>
					<td colspan="2">&nbsp;&nbsp;</td>
				</tr>		
				<tr>
					<td colspan="2">&nbsp;&nbsp;</td>
				</tr>		
				
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;����������</td>
				</tr>
				
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;����Ȩ����ǩ�ֻ���£���</td>
				</tr>
				
				<tr>
					<td colspan="2">&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;</td>
					<td style="font-size:16px;">&nbsp;&nbsp;���ڣ�<input class="edline" readonly name="" style="font-size:15px" size="5"/>��<input class="edline" readonly name="" style="font-size:15px" size="5"/>��<input class="edline" readonly name="" style="font-size:15px" size="5"/>��</td>
				</tr>
			
			</table>
		</div>
		<br>
		<div style="font-size:16px;" align="left">&nbsp;&nbsp;<b>˵������ȷ����һʽ���ݣ�����������д��ί���˺������˸�����һ�ݡ�</b></div>
		<br>
		<div align="right"  id="buttonDiv">
			<button type="button"  class="xpbutton3" name="btnPrint" id="btnPrint" onclick="javascript:doPrint();">��ӡ</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"   class="xpbutton3" name="btnCancel" onclick="javascript:window.close();">�ر�</button>
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