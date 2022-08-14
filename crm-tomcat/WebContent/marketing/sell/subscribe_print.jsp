<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ȡҳ�洫��SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

//������ʾ����
//�ͻ���Ϣ
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

//�˻���Ϣ
String gain_acct = "";
String bank_acct = "";
String bank_name = "";
String bank_sub_name = "";

//�Ϲ���ͬ��Ϣ
String product_name = "";
BigDecimal rg_money2 = new BigDecimal(0.0);//�Ϲ���� 
String  rg_money2_string = "";//�Ϲ���� ����
BigDecimal rg_fee_rate = new BigDecimal(0.0);
BigDecimal rg_fee_money = new BigDecimal(0.0);
BigDecimal jk_total_money = new BigDecimal(0.0);
String jk_total_money_string = ""; //�����ܶ�

//��ö��󼰽����
ContractLocal contract = EJBFactory.getContract();
ContractVO cont_vo = new ContractVO();

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
String rg_money_cn = "��Ԫ";
String jk_money_cn = "��Ԫ";

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
				rg_money_cn = "Ԫ";
			}			
			rg_fee_rate = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_RATE")),new BigDecimal(0));
			rg_fee_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_FEE_MONEY")),new BigDecimal(0));
			jk_total_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("JK_TOTAL_MONEY")),new BigDecimal(0));
			if(jk_total_money.doubleValue()-10000>0){
				jk_total_money_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(jk_total_money)));
			}
			else{
				jk_total_money_string = Utility.numToChinese(Utility.trimNull(jk_total_money));
				jk_money_cn = "Ԫ";
			}	
			cust_id = Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")),new Integer(0));
		}
		
		//�ͻ���Ϣ
		if(cust_id.intValue()>0){
			List rsList_cust = null;
			Map map_cust = null;	
			
			//�ͻ�����ֵ		
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
<TITLE>�Ϲ���ͬ��ӡ</TITLE>
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
							<%=Utility.trimNull(application.getAttribute("COMPANY_NAME"))%>���е�λ��/�깺��
						</td>
					</tr>
					<tr>
						<td  style="font-size:16px;">
							һ��Ͷ���߻�����Ϣ
						</td>
					</tr>		
					<tr>
						<td>
							<table  cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">����/���ƣ�</td><td colspan="4"><input class="edline" readonly name="cust_name" style="font-size:15px" size="29" value="<%=Utility.trimNull(cust_name)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">���������ˣ�</td><td><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(legal_man) %>"></td>
									<td style="font-size:16px">��ϵ�ˣ�</td><td><input class="edline" readonly name="" style="font-size:15px" size="30" value="<%=Utility.trimNull(contact_man) %>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">֤�����ͣ�</td><td><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(card_type_name) %>"></td>
									<td style="font-size:16px">֤�����룺</td><td><input class="edline" readonly name="" style="font-size:15px" size="30" value="<%=Utility.trimNull(card_id) %>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">ͨѶ��ַ��</td><td colspan="4"><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(post_address)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">��������</td><td><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(post_code)%>"></td>
									<td style="font-size:16px">�ƶ��绰��</td><td><input class="edline" readonly name=mobile"" style="font-size:15px" size="30" value="<%=Utility.trimNull(mobile)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">E-mail:</td><td colspan="4"><input class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(eMail)%>"></td>
								</tr>
							</table>
						</td>
					</tr>		
					<tr>
						<td style="font-size:16px">
							������/�깺���
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">���мƻ����ƣ�</td><td  colspan="3"style="font-size:16px"><INPUT class="edline" readonly name="product_name" style="font-size:15px" size="69" value="<%=Utility.trimNull(product_name)%>"></td>
								</tr>
								<tr>
									<td style="font-size:16px">��/�깺��</td><td  colspan="3"style="font-size:16px">�����<INPUT class="edline" readonly name="rg_money" style="font-size:15px" size="28" value="<%=rg_money2_string.substring(0,rg_money2_string.indexOf("Ԫ"))%>"><%=rg_money_cn%>��(Сд��<INPUT class="edline" readonly name="rg_money" style="font-size:15px" size="10" value="<%=Utility.trimNull(rg_money2)%>">)��</td>
								</tr>
								<tr>
									<td style="font-size:16px">��/�깺���ʣ�</td><td style="font-size:16px"><INPUT class="edline" readonly name="fee_rate" style="font-size:15px" size="28" value="<%=Utility.trimNull(rg_fee_rate)%>">%</td><td width="80px" style="font-size:16px">�Ϲ���</td><td><INPUT class="edline" readonly name="fee_amount" style="font-size:15px" size="29" <%if(rg_fee_money ==null){%>value=""<%}else{%>value="<%=Utility.numToChinese(Utility.trimNull(rg_fee_money))%>"<%}%>></td>
								</tr>
								<tr>
									<td style="font-size:16px">�����ܶ�</td><td  colspan="3"style="font-size:16px">�����<INPUT class="edline" readonly  name="to_money" style="font-size:15px" size="33" value="<%=jk_total_money_string.substring(0,jk_total_money_string.indexOf("Ԫ"))%>"><%=jk_money_cn%>����Сд��<INPUT class="edline" readonly name="to_money" style="font-size:15px" size="10" value="<%=Utility.trimNull(jk_total_money)%>">)��</td>
								</tr>
							</table>
						</td>	
					</tr>	
					<tr>
						<td style="font-size:16px">
							����������������˻�
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td  style="font-size:16px" colspan="2">Ͷ����ָ�������˻����ڽ��������˷������������</td>
								</tr>
								<tr>
									<td style="font-size:16px">�˻�����</td><td style="font-size:16px"><INPUT class="edline" readonly name="gain_acct" style="font-size:15px" size="50" value="<%=Utility.trimNull(gain_acct)%>"></td>	
								</tr>
								<tr>
									<td style="font-size:16px">�ˡ��ţ�</td><td style="font-size:16px"><INPUT class="edline" readonly name="" style="font-size:15px" size="50" value="<%=Utility.trimNull(bank_acct)%>"></td>	
								</tr>
								<tr>
									<td style="font-size:16px">�����У�</td><td style="font-size:16px"><INPUT class="edline" readonly name="" style="font-size:15px" size="50" value="<%=Utility.trimNull(bank_name)%>&nbsp;<%=Utility.trimNull(bank_sub_name)%>"></td>	
								</tr>
							</table>	
						</td>
					</tr>	
					<tr>
						<td style="font-size:16px">
							�ġ�����������䷽ʽ��ѡ��(����ʱ)
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px" colspan="2">���мƻ������ڼ䣬Ͷ����ѡ�����·�ʽȡ����������:</td>
								</tr>
								<tr>
									<td style="font-size:16px">���ֽ�ֺ췽ʽ</td><td style="font-size:16px">���ֺ�תͶ�ʷ�ʽ</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td style="font-size:16px">
							�塢�깺���ɹ�ʱ��ѡ��(����ʱ)
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px" colspan="2">��Ͷ���ߵ��깺�ʽ�δ���ڿ�����ǰһ�������յ����������˻��������깺�ļ�δ���ڿ�����ǰһ���������ʹ�������ʱ��������������깺���ɹ�ʱ��Ͷ����ѡ��:</td>
								</tr>
								<tr>
									<td style="font-size:16px">��ȫ���깺�ʽ��˻�Ͷ����</td>
								</tr>
								<tr>
									<td style="font-size:16px">��ȫ���깺�ʽ�ֱ����Ϊ��һ�������յ��깺�ʽ�</td>
								</tr>								
							</table>
						</td>
					</tr>	
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px;font-weight:bold">�ر���ʾ��ί����ǩ����/�깺�����������Ķ���ǩ�������������ļ���ί�����ڱ���/�깺����ǩ�ּ�����Ա���/�깺����д��Ϣ��ʵ�Ժ�׼ȷ�Ե�ȷ�ϡ���������˿�������ί����ǩ���Ϲ��������ί����ͬ�⽫�Ϲ��ʽ��˻�������Ԥ��������������˻��������е�λ��/�깺������Ϊ�ͻ��Ϲ�ƾ�ݣ�ʵ���Ϲ������������<%=application.getAttribute("COMPANY_NAME")%>���ߵ����е�λȷ�ϵ�Ϊ׼��</td>
								</tr>						
							</table>
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">ί����ǩ�£�</td><td style="font-size:16px">������ǩ�£�</td>	
								</tr>	
								<tr>
									<td></td>
									<td></td>
								</tr>
							</table>
						</td>
					</tr>			
					<tr><td align="right" style="font-size:16px">���ڣ�&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��</td></tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px">���죺</td><td style="font-size:16px">���ˣ�</td>	
								</tr>	
							</table>
						</td>
					</tr>																																																																										
				</table>
				<tr>
					<td align="right"><button type="button"   class="xpbutton3" name="btnPrint" id="btnPrint" onclick="javascript:doPrint();">��ӡ</button>&nbsp;&nbsp;&nbsp;<button type="button" class="xpbutton3" name="btnCancel" onclick="javascript:history.back();">ȡ��</button>&nbsp;&nbsp;</td>
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
		sl_alert("�ͻ���Ϣ������");
	}
}
</script>
</BODY>
</HTML>