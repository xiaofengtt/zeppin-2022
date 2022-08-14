<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ȡҳ�洫��SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer ben_serial_no = Utility.parseInt(request.getParameter("ben_serial_no"), new Integer(0));

//�ͻ���Ϣ
Integer cust_id = new Integer(0);
String cust_name = "";

//��Ʒ��Ϣ
Integer product_id = new Integer(0);
String bg_bank_name = "";
String product_name = "";
BigDecimal nav_price = new BigDecimal(0.00);

//�˻���Ϣ
String gain_acct = "";
String bank_acct = "";
String bank_name = "";
String bank_sub_name = "";

//�����Ϣ
String contract_bh = "";//��ͬ���
String contract_name = "";//��ͬ����
Integer sq_date = new Integer(0);
BigDecimal redeem_money = new BigDecimal(0.0);//��ؽ��
BigDecimal redeem_fee = new BigDecimal(0.0);//��ط���
String  redeem_money_string = "";//��ؽ�� ����
String redeem_fee_string = ""; //��ط�������
BigDecimal redeem_amount = new BigDecimal(0.0);

//�ѳ��зݶ�
BigDecimal total_amount = new BigDecimal(0.0);
BigDecimal past_amount = new BigDecimal(0.0);

//��ö��󼰽����
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO ben_vo = new BenifitorVO();
RedeemLocal redeem = EJBFactory.getRedeem();
RedeemVO redeem_vo = new RedeemVO();

ProductLocal product = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();

if(ben_serial_no.intValue()>0){
	List rsList_ben = null;
	Map map_ben = null;
	
	ben_vo.setSerial_no(ben_serial_no);
	rsList_ben = benifitor.load(ben_vo);
	
	if(rsList_ben.size()>0){
		map_ben = (Map)rsList_ben.get(0);
	}
	
	if(map_ben!=null){
		cust_id = Utility.parseInt(Utility.trimNull(map_ben.get("CUST_ID")),new Integer(0));
		cust_name = Utility.trimNull(map_ben.get("CUST_NAME"));
	}
	
}

if(serial_no.intValue()>0){
		List rsList_redeem = null;
		Map map_redeem = null;
		
		redeem_vo.setSerial_no(serial_no);
		rsList_redeem = redeem.listBySql(redeem_vo);
		
		if(rsList_redeem.size()>0){
			map_redeem = (Map)rsList_redeem.get(0);
		}
		
		if(map_redeem!=null){
			contract_bh = Utility.trimNull(map_redeem.get("CONTRACT_BH"));
			product_id = Utility.parseInt(Utility.trimNull(map_redeem.get("PRODUCT_ID")),new Integer(0));
			redeem_money = Utility.parseDecimal(Utility.trimNull(map_redeem.get("REDEEM_MONEY")),new BigDecimal(0),2,"1");
			redeem_fee = Utility.parseDecimal(Utility.trimNull(map_redeem.get("FEE")),new BigDecimal(0),2,"1");
			redeem_money_string = Utility.numToChinese(Utility.trimNull(redeem_money));
			redeem_fee_string = Utility.numToChinese(Utility.trimNull(redeem_fee));
			
			sq_date =  Utility.parseInt(Utility.trimNull(map_redeem.get("SQ_DATE")),new Integer(0)); 
			redeem_amount= Utility.parseDecimal(Utility.trimNull(map_redeem.get("REDEEM_AMOUNT")),new BigDecimal(0),2,"1");
			nav_price = Utility.parseDecimal(Utility.trimNull(map_redeem.get("NAV_PRICE")),new BigDecimal(0),2,"1");
			total_amount = (benifitor.getTotalBenAmount(cust_id,product_id)).setScale(2);
			
			if(redeem_money.intValue()>0){
				past_amount = (total_amount.add(redeem_amount)).setScale(2);
			}
			else{
				past_amount = total_amount;
				redeem_amount = new BigDecimal(0.00);
			}
			
		}

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
				product_name = Utility.trimNull(map_product.get("PRODUCT_NAME"));
			}			
			
		}
}
%>

<HTML>
<HEAD>
<TITLE>�����������ȷ�����ӡ</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<script language=javascript>
function doPrint(){	
	window.open("redemption_print2_do.jsp?serial_no=<%=serial_no%>&ben_serial_no=<%= ben_serial_no%>");
}
</script>
</HEAD>

<BODY  leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post">
<DIV style="width:100%;height:90%" align="center">
		<DIV style="width:704px;height:955px">
			<div align="center"  style="font-size:22px;line-height:50px"><%=application.getAttribute("COMPANY_NAME")%>�������ȷ����</div>
			<div style="font-size:16px;" align="left">
				<b>�𾴵�<input class="edline" readonly name="" style="font-size:15px" size="10" value=" <%=Utility.trimNull(cust_name)%>"  />����/Ůʿ��</b>
			</div>
			<br>
			<div style="font-size:16px" align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ��л���Թ������е�֧�ֺ�������<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	��������<input class="edline" readonly name="" style="font-size:15px" size="5"/>��<input class="edline" readonly name="" style="font-size:15px" size="5"/> ��<input class="edline" readonly name="" style="font-size:15px" size="5"/>��
				�ύ�ġ���������顷����˾��Ϊ�����˰������к�ͬ��Լ�����ѽ�����ص����е�λ������Ӧ�Ŀ������Ԥ����������������˻�������ա�<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	���˴�������е�λ����Ϣȷ�����£�<br>
			</div>
			<br>
			<div style="font-size:16px;width:704px;" align="left">
				<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
						<tr class="tr1"><td align="center" colspan="3" style="font-size:16px;"><b>������Ϣ</b></td></tr>
						<tr class="tr1">
							<td width="250px" colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���мƻ�����</td>
							<td style="font-size:16px;">&nbsp;&nbsp;<%=Utility.trimNull(product_name)%></td>
						</tr>
						<tr class="tr1">
							<td  colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���к�ͬ����</td>
							<td style="font-size:16px;">&nbsp;<%=contract_name%></td>
						</tr>
						<tr class="tr1">
							<td  colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ͬ���</td>
							<td style="font-size:16px;">&nbsp;&nbsp;<%=contract_bh%></td>
						</tr>
						<tr class="tr1">
							<td  colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ί��������</td>
							<td style="font-size:16px;">&nbsp;&nbsp;<%=Utility.trimNull(cust_name)%></td>
						</tr>
						<tr class="tr1">
							<td  colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ؿ��������е�λ��ֵ</td>
							<td style="font-size:16px;">
										<div  style="width=60%;">
											<div style="float:left; width=60px" align="left">&nbsp;�����</div>
											<div style="float:right; " align="right"><%= nav_price%>Ԫ/��</div>			
										</div>									
							</td>
						</tr>
						<tr class="tr1">
								<td width="150px" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ʽ���</td>
								<td width="100px" align="center" style="font-size:16px;">��д</td>
								<td style="font-size:16px;">
										<div  style="width=60%;">
											<div style="float:left; width=60px" align="left">&nbsp;�����</div>
											<div style="float:right; " align="right"><%if(redeem_money !=null){out.print(Utility.numToChinese(Utility.trimNull(redeem_money)));}%></div>			
										</div>									
								</td>
							</tr>
							
							<tr class="tr1">
								<td width="100px" align="center" style="font-size:16px;">Сд</td>
								<td style="font-size:16px;">
										<div  style="width=60%;">
											<div style="float:left; width=20px" align="left">&nbsp;��</div>
											<div style="float:right;"  align="right"><%= Utility.trimNull(redeem_money)%>Ԫ&nbsp;&nbsp;&nbsp;</div>
										</div>								
								</td>
							</tr>
							<tr class="tr1">
								<td width="150px" rowspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ط�</td>
								<td width="100px" align="center" style="font-size:16px;">��д</td>
								<td style="font-size:16px;">
										<div  style="width=60%;">
											<div style="float:left; width=60px" align="left">&nbsp;�����</div>
											<div style="float:right; " align="right"><%if(redeem_fee !=null){out.print(Utility.numToChinese(Utility.trimNull(redeem_fee)));}%></div>			
										</div>					
								</td>
							</tr>
							
							<tr class="tr1">
								<td width="100px" align="center" style="font-size:16px;">Сд</td>
								<td style="font-size:16px;">
										 <div  style="width=60%;">
											<div style="float:left; width=20px" align="left">&nbsp;��</div>
											<div style="float:right;"  align="right"><%= Utility.trimNull(redeem_fee)%>Ԫ&nbsp;&nbsp;&nbsp;</div>
										</div>		
								</td>
							</tr>
							
							<tr class="tr1"><td align="center" colspan="3" style="font-size:16px;"><b>��ؽ��</b></td></tr>
							
							<tr class="tr1">
								<td  colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�����������</td>
								<td style="font-size:16px;" align="left"><div style="width=60%;" align="right">��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;</div></td>
							</tr>
							<tr class="tr1">
								<td colspan="2" style="font-size:16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ӧ��ؿ�����</td>
								<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%= Format.formatDateCn(sq_date.intValue())%>&nbsp;&nbsp;&nbsp;</div></td>
							</tr>
							<tr class="tr1">
								<td  style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ԭ�ѳ��е����е�λ����</td>
								<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=past_amount%>��&nbsp;&nbsp;&nbsp;</div></td>
							</tr>
							<tr class="tr1">
								<td  style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ؿ�������ص����е�λ����</td>
								<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=redeem_amount%>��&nbsp;&nbsp;&nbsp;</div></td>
							</tr>
							<tr class="tr1">
								<td  style="font-size:16px;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������غ���е����е�λ����</td>
								<td style="font-size:16px;" align="left"><div style="width=60%;" align="right"><%=total_amount%>��&nbsp;&nbsp;&nbsp;</div></td>
							</tr>
				</table>
		</div>
		 <br>
		 	<div style="font-size:16px;" align="left">
			<table  border="0" cellSpacing=0 cellPadding=2>				
				<tr>
					<td  style="font-size:16px;width:400px;">&nbsp;&nbsp;</td>
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
redeem.remove();
product.remove();
%>