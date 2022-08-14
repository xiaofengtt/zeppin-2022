<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ȡҳ�洫��SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
input_bookCode = new Integer(1);

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

//�깺��ͬ��Ϣ
String product_name = "";
String contract_sub_bh = "";//��ͬ���
String contract_bh = "";
String contract_name = "";//��ͬ����
BigDecimal sg_money2 = new BigDecimal(0.0);//�깺��� 
BigDecimal sg_fee_rate = new BigDecimal(0.0);
BigDecimal sg_fee_money = new BigDecimal(0.0);
BigDecimal jk_total_money = new BigDecimal(0.0);
BigDecimal to_amount = new BigDecimal(0.0);
String  sg_money2_string = "";//�깺��� ����
String jk_total_money_string = ""; //�����ܶ�
Integer qs_date = new Integer(0);//��������
Integer start_date = new Integer(0);
Integer to_bank_date  = new Integer(0); //�ʽ�������

//�ѳ��зݶ�
BigDecimal total_amount = new BigDecimal(0.0);
BigDecimal past_amount = new BigDecimal(0.0);

//��ö��󼰽����
BenifitorLocal benifitor = EJBFactory.getBenifitor();
ApplyreachLocal apply_reach = EJBFactory.getApplyreach();
ApplyreachVO apply_vo = new ApplyreachVO();

ProductLocal product = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();

if(serial_no.intValue()>0){
		List rsList_contractSg = null;
		Map map_contractSg = null;

		apply_vo.setSerial_no(serial_no);
		apply_vo.setBook_code(input_bookCode);
		apply_vo.setInput_man(input_operatorCode);
		
		rsList_contractSg = apply_reach.listBySql(apply_vo);		
		if(rsList_contractSg.size()>0){
			map_contractSg = (Map)rsList_contractSg.get(0);
		}

		if(map_contractSg!=null){
			gain_acct = Utility.trimNull(map_contractSg.get("GAIN_ACCT"));
			bank_acct = Utility.trimNull(map_contractSg.get("BANK_ACCT"));
			bank_name = Utility.trimNull(map_contractSg.get("BANK_NAME"));
			bank_sub_name = Utility.trimNull(map_contractSg.get("BANK_SUB_NAME"));
			product_name = Utility.trimNull(map_contractSg.get("PRODUCT_NAME"));
			sg_money2 = Utility.parseDecimal(Utility.trimNull(map_contractSg.get("SG_MONEY2")),new BigDecimal(0),2,"1");
			sg_money2_string = Utility.numToChinese(Utility.trimNull(sg_money2));
			sg_fee_rate = Utility.parseDecimal(Utility.trimNull(map_contractSg.get("SG_FEE_RATE")),new BigDecimal(0));
			sg_fee_money = Utility.parseDecimal(Utility.trimNull(map_contractSg.get("SG_FEE_MONEY")),new BigDecimal(0),2,"1");
			jk_total_money = Utility.parseDecimal(Utility.trimNull(map_contractSg.get("JK_TOTAL_MONEY")),new BigDecimal(0),2,"1");
			jk_total_money_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(jk_total_money)));	
			to_amount = Utility.parseDecimal(Utility.trimNull(map_contractSg.get("TO_AMOUNT")),new BigDecimal(0),2,"1");
			cust_id = Utility.parseInt(Utility.trimNull(map_contractSg.get("CUST_ID")),new Integer(0));
			qs_date = Utility.parseInt(Utility.trimNull(map_contractSg.get("QS_DATE")),new Integer(0)); 
			contract_sub_bh = Utility.trimNull(map_contractSg.get("CONTRACT_SUB_BH"));
			contract_bh = Utility.trimNull(map_contractSg.get("CONTRACT_BH"));
			product_id = Utility.parseInt(Utility.trimNull(map_contractSg.get("PRODUCT_ID")),new Integer(0));		
			cust_name = Utility.trimNull(map_contractSg.get("CUST_NAME"));		
			start_date = Utility.parseInt(Utility.trimNull(map_contractSg.get("START_DATE")),new Integer(0)); 
			
			to_bank_date = Utility.parseInt(Utility.trimNull(map_contractSg.get("TO_BANK_DATE")),new Integer(0)); 
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
<TITLE>���������깺ȷ�����ӡ</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<script language=javascript>
function doPrint(){	
	window.open("apply_purchase_print2_do.jsp?serial_no=<%=serial_no%>");
}
</script>
</HEAD>

<BODY  leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post">
<DIV style="width:100%;height:90%" align="center">
	<DIV style="width:704px;height:955px">
		<div align="center"  style="font-size:22px;line-height:50px"><%=application.getAttribute("COMPANY_NAME")%>�����깺������ȷ����</div>
		<div style="font-size:16px;" align="left">
			<b>�𾴵�<input class="edline" readonly name="" style="font-size:16px" size="15" value=" <%=Utility.trimNull(cust_name)%>" />����/Ůʿ��</b>
		</div>
		<br>
		<div style="font-size:16px" align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ���ݱ����к�ͬ�Ĺ涨����˾��Ϊ�������ѽ����깺�����мƻ����е�λ���깺�ʽ���깺�˻��������мƻ�ר�������˻������ѽ��깺�ʽ������к�ͬ�Ĺ涨��������е�λ���������˴��깺���е�λ����Ϣ���£�
		</div>
		<br>

		<div style="font-size:16px;width:704px;" align="left">
			<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="tr1"><td align="center" colspan="3" style="font-size:16px;"><b>������Ϣ</b></td></tr>
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
					<td width="140" rowspan="3" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�깺�ʻ�</td>
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
					<td width="200" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�깺�ʽ�������</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(to_bank_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				
				<tr class="tr1">
					<td width="140" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�깺�ʽ���</td>
					<td width="60" align="center" style="font-size:16px;">��д</td>
					<td style="font-size:16px;">
							<div  style="width=60%;">
								<div style="float:left; width=60px" align="left">&nbsp;�����</div>
								<div style="float:right; " align="right"><%if(sg_money2 !=null){out.print(Utility.numToChinese(Utility.trimNull(sg_money2)));}%></div>			
							</div>		
					</td>
				</tr>
				
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">Сд</td>
					<td style="font-size:16px;" align="left">
						<div  style="width=60%;">
							<div style="float:left; width=20px" align="left">&nbsp;��</div>
							<div style="float:right;"  align="right"><%= Utility.trimNull(sg_money2)%>Ԫ&nbsp;&nbsp;&nbsp;</div>
						</div>		
					</td>
				</tr>
				<tr class="tr1">
					<td width="140" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�깺��</td>
					<td width="60" align="center" style="font-size:16px;">��д</td>
					<td style="font-size:16px;">
							<div  style="width=60%;">
								<div style="float:left; width=60px" align="left">&nbsp;�����</div>
								<div style="float:right; " align="right"><%if(sg_fee_money !=null){out.print(Utility.numToChinese(Utility.trimNull(sg_fee_money)));}%></div>			
							</div>		
					</td>
				</tr>
				
				<tr class="tr1">
					<td width="60" align="center" style="font-size:16px;">Сд</td>
					<td style="font-size:16px;" align="left">
						<div  style="width=60%;">
							<div style="float:left; width=20px" align="left">&nbsp;��</div>
							<div style="float:right;"  align="right"><%= Utility.trimNull(sg_fee_money)%>Ԫ&nbsp;&nbsp;&nbsp;</div>
						</div>							
					</td>
				</tr>
				
				<tr class="tr1"><td align="center" colspan="3" style="font-size:16px;"><b>�깺���</b></td></tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�깺��������</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(qs_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="250" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ӧ�깺������</td>
					<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(start_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
				</tr>
				<tr class="tr1">
					<td width="300" style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���깺�������깺�����е�λ����</td>
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
		<div align="right">
			<button type="button"  class="xpbutton3" name="btnPrint" id="btnPrint" onclick="javascript:doPrint();">ȷ��</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"   class="xpbutton3" name="btnCancel" onclick="javascript:history.back();">ȡ��</button>
			&nbsp;&nbsp;
		</div>
	</DIV>
</DIV>
<br>
</FROM>
</BODY>
</HTML>
<%
apply_reach.remove();
product.remove();
%>
