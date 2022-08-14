<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
ContractLocal contract = EJBFactory.getContract();
ValidateprintLocal validate = EJBFactory.getValidateprint();
PurconfigLocal purconFig = EJBFactory.getPurconfig();

//��ҳ����ֵ
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String contract_bh = request.getParameter("contract_bh");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);

//��������
String product_name = "";
BigDecimal appl_amount = new BigDecimal(0.00);
BigDecimal fee_money = new BigDecimal(0.00);
BigDecimal price = null;
BigDecimal chg_amount = null;
BigDecimal chg_money = null;
BigDecimal after_amount = null;
BigDecimal after_money = null;
BigDecimal fee_rate = null;

BigDecimal appl_amount2 = new BigDecimal(0.00);
BigDecimal fee_money2 = new BigDecimal(0.00);
BigDecimal price2 = null;
BigDecimal chg_amount2 = null;
BigDecimal chg_money2 = null;
BigDecimal after_amount2 = null;
BigDecimal after_money2 = null;
BigDecimal fee_rate2 = null;

int chg_type = 0;
String jk_type = "1114";
String contract_sub_bh = "";
String sub_product_name = "";
String toChina = "";
String toChina2 = "";
String toChina3 = "" ;

//��ѯ��Ϣ
validate.setProduct_id(product_id);
validate.setContract_bh(contract_bh);
validate.setSerial_no(serial_no);
validate.setInput_man(input_operatorCode);
List list = validate.listBySql();
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	if(!"".equals(sub_product_name)&& sub_product_name!=null)
		sub_product_name = "("+sub_product_name+")";
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	validate.setCust_name(Utility.trimNull(map.get("CUST_NAME")));
	validate.setDz_date(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),null));
	validate.setHk_date(Utility.parseInt(Utility.trimNull(map.get("HK_DATE")),null));
	chg_type = Utility.parseInt(Utility.trimNull(map.get("CHG_TYPE")),0);
	jk_type = Utility.trimNull(map.get("JK_TYPE"));
	validate.setAppl_amout(Utility.parseDecimal(Utility.trimNull(map.get("APPL_AMOUNT")),null));
	validate.setFee_money(Utility.parseDecimal(Utility.trimNull(map.get("FEE_MONEY")),null));
	validate.setPrice(Utility.parseDecimal(Utility.trimNull(map.get("PRICE")),null));
	validate.setChg_money(Utility.parseDecimal(Utility.trimNull(map.get("CHG_MONEY")),null));
	validate.setChg_amount(Utility.parseDecimal(Utility.trimNull(map.get("CHG_AMOUNT")),null));
	validate.setAfter_amount(Utility.parseDecimal(Utility.trimNull(map.get("AFTER_AMOUNT")),null));
	validate.setAfter_money(Utility.parseDecimal(Utility.trimNull(map.get("AFTER_MONEY")),null));
	validate.setFee_rate(Utility.parseDecimal(Utility.trimNull(map.get("FEE_RATE")),null));
	validate.setBen_start_date(Utility.parseInt(Utility.trimNull(map.get("BEN_START_DATE")),null));
	int intrust_flag1 = Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG1")),1);

	if(validate.getAfter_amount()!=null)
		after_amount = validate.getAfter_amount().setScale(2,2);

	if(validate.getAfter_money()!=null){
		after_money = validate.getAfter_amount().multiply(validate.getPrice()).setScale(2,4);
		toChina3 = Utility.numToChinese(Utility.trimNull(after_money));
	}
}
	chg_money = validate.getChg_money().setScale(2,2);
	toChina = Utility.numToChinese(Utility.trimNull(chg_money));




//���ô�ӡ��־Ϊ 1�Ѵ�ӡ
validate.updatePrintFlag(new Integer(1),serial_no);
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media="print">
.noprint     { display: none }
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>
</script>
<BODY class="body" leftmargin="" topmargin=0 rightmargin=0 onload="javascript:print();close();">
	<p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
		<strong style="mso-bidi-font-weight: normal">
			<span style="FONT-FAMILY: ����; FONT-SIZE: 15pt; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
				��Ͷ�����������ι�˾</span></br>
			<span style="FONT-FAMILY: ����; FONT-SIZE: 15pt; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">
				�����ʽ����мƻ����е�λ�Ϲ�/�깺ȷ����</span>
		</strong>
	</p><br>
	<p style="MARGIN: 0cm 0cm 0pt 21pt" class="MsoNormal">
		<font size="3";>
			<font face="Times New Roman"><%=Format.formatDateCn(validate.getBen_start_date().intValue()) %></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span style="FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 
				'Times New Roman'">��λ�������Ԫ</span>
		</font>
	</p><br>	
	<table style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; 
			BORDER-RIGHT: medium none; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 480; 
			mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext" class="MsoTableGrid" border="1" cellspacing="0" cellpadding="0">
    	<tbody>
       		<tr style="HEIGHT: 22.85pt; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.85pt; BORDER-TOP: windowtext 1pt solid; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'">��Ŀ����
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent;
						 PADDING-LEFT: 5.4pt; WIDTH: 531.55pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.85pt; BORDER-TOP: windowtext 1pt solid; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: 
						solid windowtext .5pt" valign="top" width="709" colspan="3">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(product_name)%><%=sub_product_name %>
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 22.45pt; mso-yfti-irow: 1">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">ί��������
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 
					5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 
					0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
					valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(validate.getCust_name())%>
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: 
						solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
							mso-hansi-font-family: 'Times New Roman'">��ͬ���
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 
					5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 
					0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
					valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
							mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(contract_sub_bh)%>
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 22.75pt; mso-yfti-irow: 2">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'">�Ϲ�/�깺�ʽ𣨴�д��
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: 
						solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(toChina)%>
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; 
						mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'">Сд
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
						WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
						mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(validate.getChg_money().setScale(2,2))%>&nbsp;Ԫ
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 23.05pt; mso-yfti-irow: 3">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						 BORDER-TOP: #ece9d8;BORDER-RIGHT: windowtext 1pt solid; ">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3" face="Times New Roman"><%=Format.formatDateCn(validate.getBen_start_date().intValue())%>��ʼ���е�λ��ֵ</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 531.55pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.05pt; BORDER-TOP: #ece9d8; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; 
						mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="709" colspan="3">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; mso-bidi-font-size: 10.5pt" lang="EN-US">
								<font face="Times New Roman">1.00&nbsp;Ԫ</font>
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 23.35pt; mso-yfti-irow: 4">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
					<font size="3">
						<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">�Ϲ�/�깺���е�λ����/��
						</span>
					</font>
				</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(validate.getChg_amount().setScale(0,2)) %>&nbsp;��
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">�������е�λ�ܷ���/��
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
           		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
					<font size="3">
						<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
							mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(validate.getAfter_amount().setScale(0,2)) %>&nbsp;��
						</span>
					</font>
				</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 22.25pt; mso-yfti-irow: 5; mso-yfti-lastrow: yes">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.25pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
 					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">��ע
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 531.55pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.25pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
 					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="709" colspan="3">
           			<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">
							</span>
						</font>
					</p>
            	</td>
        	</tr>
    	</tbody>
	</table></br>
		<p style="margin-left=750">
			<font size="3"></br></br>
				��Ͷ�����������ι�˾�����£�
			</font></br></br></br></br>
		</p>
	</body>
</html>