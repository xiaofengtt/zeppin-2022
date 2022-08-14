<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv="Content-Type" content="text/html; charset=GB18030">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<script language="javascript">

function doPrint(){
	document.getElementById("btnPrint").style.display="none";
	window.print();
	window.returnValue=1;
	window.close();
	
}

</script>
<TITLE>���������Ϲ��ɿ�ȷ����</TITLE>
</HEAD>

<% 
	String[] s = request.getParameterValues("serial_no");
	
	ContractLocal contract = EJBFactory.getContract();
	CustomerLocal customer = EJBFactory.getCustomer();
	ContractVO vo = new ContractVO();
	CustomerVO custvo = new CustomerVO();
	Map contractMap =new HashMap();
	Map custMap = new HashMap();
	
	String gain_acct = "";
	String bank_name = "";
	String bank_acct = "";
	String product_name = "";
	String contract_sub_bh = "";
	String to_amount = "";
	String rg_money = "";
	String rg_money_cn = "";
	String cust_name = "";
	String card_type_name = "";
	String card_id = "";
	String mobile = "";
	String e_mail = "";
	String post_address = "";
	String post_code = "";
	String jk_type_name = "";
	for(int i=0;i<s.length;i++){
		
		vo.setSerial_no(Utility.parseInt(s[i],new Integer(0)));
		vo.setInput_man(input_operatorCode);
		List contractList = contract.load(vo);
	
		if(contractList!=null&&contractList.size()==1){
			contractMap = (Map)contractList.get(0);
			gain_acct = Utility.trimNull(contractMap.get("GAIN_ACCT"));
			bank_name = Utility.trimNull(contractMap.get("BANK_NAME"));
			bank_acct = Utility.trimNull(contractMap.get("BANK_ACCT"));
			product_name = Utility.trimNull(contractMap.get("PRODUCT_NAME"));
			contract_sub_bh = Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"));
			to_amount = Utility.trimNull(contractMap.get("TO_AMOUNT"));
			rg_money = Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("RG_MONEY")),new BigDecimal(0)));
			rg_money_cn = Utility.numToChinese(Utility.trimNull(contractMap.get("RG_MONEY")));
			jk_type_name = Utility.trimNull(contractMap.get("JK_TYPE_NAME"));
		}

		custvo.setCust_id((Integer)contractMap.get("CUST_ID"));
		custvo.setInput_man(input_operatorCode);
		List custList = customer.listCustomerLoad(custvo);
		if(custList!=null&&custList.size()==1){
			custMap = (Map)custList.get(0);
			cust_name = Utility.trimNull(custMap.get("CUST_NAME"));
			card_type_name = Utility.trimNull(custMap.get("CARD_TYPE_NAME"));
			card_id = Utility.trimNull(custMap.get("CARD_ID"));
			mobile = Utility.trimNull(custMap.get("MOBILE"));
			e_mail = Utility.trimNull(custMap.get("E_MAIL"));
			post_address = Utility.trimNull(custMap.get("POST_ADDRESS"));
			post_code = Utility.trimNull(custMap.get("POST_CODE"));
		}
			
%>
<BODY >
<div STYLE="page-break-after: always;" align="center">
<p style="LINE-HEIGHT: 20pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
	<strong style="mso-bidi-font-weight: normal">
		<u><span style="FONT-FAMILY: ��������; FONT-SIZE: 26pt">&nbsp; �� �� �� ��</span></u>
	</strong>
	<strong style="mso-bidi-font-weight: normal">
		<span style="FONT-FAMILY: ����; FONT-SIZE: 18pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����;
			 mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">
			&nbsp;&nbsp;&nbsp;&nbsp;���в�Ʒ�Ϲ����ɿ�ȷ����
		</span>
	</strong>
</p>
<p style="LINE-HEIGHT: 20pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
	<span style="FONT-FAMILY: ����; FONT-SIZE: 18pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
		mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">
			�� �� �� �� �� �� &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</span>
</p><br>
<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal">
	<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
		mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">&nbsp;���:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</span>
</p>
<table style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; WIDTH: 459pt; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; BORDER-RIGHT: 
	medium none; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 1184" class="MsoTableGrid" border="1" 
	cellspacing="0" cellpadding="0" width="612">
    <tbody>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
				PADDING-LEFT: 5.4pt; WIDTH: 26.7pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: windowtext 1pt solid; BORDER-RIGHT: windowtext 1pt 
				solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt" rowspan="4" width="36">
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font:
					 minor-latin">ί</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-SIZE: 9pt" lang="EN-US">
					<font face="Calibri">/</font></span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">Ϣ</span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				 WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: windowtext 1pt solid; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP:
				 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">����/����: <%=cust_name %></span>
				</p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 1">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				WIDTH: 300.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="1">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">֤�����ƣ�<%=card_type_name %></span>
				</p>
            </td>
			<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="2">
				<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">֤�����룺<%=card_id %></span>
				</p>
			</td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 2">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				WIDTH: 300.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="1">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 	
						minor-latin">��ϵ�绰��<%=mobile%></span>
				</p>
            </td>
			<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="2">
				<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 	
						minor-latin">�����ʼ���<%=e_mail%></span>
				</p>
			</td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 3">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				WIDTH: 300.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="1">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">�������룺<%=post_code%></span>
				</p>
            </td>
			<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt;
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="2">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��ϵ��ַ��<%=post_address%></span>
				</p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 4">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
				PADDING-LEFT: 5.4pt; WIDTH: 26.7pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; 
				PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" rowspan="4" width="36">
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">��</span></p>
            <p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
				<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">Ϣ</span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��   ����</span>
				</p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 5">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal"><span style="FONT-FAMILY: ����; 
					FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
					mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">֤�����ƣ�</span><span style="FONT-SIZE: 9pt" lang="EN-US"><font face="Calibri"><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;</span><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">֤�����룺</span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 6">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal"><span style="FONT-FAMILY: ����; 
					FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
					mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��ϵ��ʽ��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><font face="Calibri"><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;</span><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�����ʼ���</span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 7">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal"><span style="FONT-FAMILY: ����; 
					FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
					mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��ϵ��ַ��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><font face="Calibri"><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;</span><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�������룺</span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 8">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
				PADDING-LEFT: 5.4pt; WIDTH: 26.7pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; 
				PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" rowspan="2" width="36">
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��</span></p>
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��</span></p>
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��</span></p>
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">Ϣ</span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">�˻����ƣ�<%=gain_acct %></span>
				</p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 9">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 
				5.4pt; WIDTH: 300.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="1">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">�������У�<%=bank_name%></span>
            </td>
			<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 
				5.4pt; WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="2">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">�����˺ţ�<%=bank_acct%></span>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 10">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
				PADDING-LEFT: 5.4pt; WIDTH: 26.7pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; 
				PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" rowspan="4" width="36">
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��</span></p>
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��</span></p>
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��</span></p>
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal" align="center">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">Ϣ</span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="3">
            	<p style="LINE-HEIGHT: 10pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; 
					FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
					mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">
					���мƻ����ƣ�<%=product_name%></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 11">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="3">
            	<p style="LINE-HEIGHT: 12pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">��ͬ��ţ�<%=contract_sub_bh %></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 12">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
				WIDTH: 300.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="1">
            	<p style="LINE-HEIGHT: 12pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">&nbsp;�Ϲ��ݶ<%=to_amount %></span>
				</p>
            	<p style="LINE-HEIGHT: 12pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">(���е�λ)</span>
				</p>			
            </td>
			<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
				WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="2">
            	<p style="LINE-HEIGHT: 12pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">�Ϲ����ͣ�</span>
				</p>
			</td>
        </tr>
        <tr style="HEIGHT: 22.7pt; mso-yfti-irow: 13">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 
				5.4pt; WIDTH: 432.3pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				width="576" colspan="3">
            	<p style="LINE-HEIGHT: 12pt; MARGIN: 0cm 0cm 0pt; mso-line-height-rule: exactly" class="MsoNormal"><span style="FONT-FAMILY: ����; 
					FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; 
					mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">
					��&nbsp;����ң�&nbsp;<%=rg_money_cn %>&nbsp;(Сд��&nbsp;<%=rg_money%>)&nbsp;&nbsp;&nbsp;�ɿʽ��<%=jk_type_name %></span>
				</p>
            </td>
        </tr>
        <tr style="HEIGHT: 86.05pt; mso-yfti-irow: 14">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 459pt; PADDING-RIGHT: 5.4pt; HEIGHT: 86.05pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="612" colspan="4">
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin"></span></p>
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��Ҫ��ʾ��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 70.4pt; mso-yfti-irow: 15">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 210.95pt; PADDING-RIGHT: 5.4pt; HEIGHT: 70.4pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" rowspan="2" width="281" colspan="2">
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><strong style="mso-bidi-font-weight: normal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin"></span></strong></p>
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><strong style="mso-bidi-font-weight: normal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">ί���˳�ŵ��</span></strong><strong style="mso-bidi-font-weight: normal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></strong></p>
            <p style="TEXT-INDENT: 18.05pt; MARGIN: 0cm 0cm 0pt; mso-char-indent-count: 2.0" class="MsoNormal"><strong style="mso-bidi-font-weight: normal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">����������Ϣ������ʵ����Ч������α�졢��թ���������δ��ʱ֪ͨί���˰���ȷ������֮���Σ�ί������Ը�е���˲�����ȫ���������Ρ������������ʧ��</span></strong><strong style="mso-bidi-font-weight: normal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></strong></p>
            <p style="TEXT-INDENT: 21pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><strong style="mso-bidi-font-weight: normal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">����ί��������ϸ�Ķ�����������Ϲ����мƻ��ļ���ȫ�����ݣ�ͬ��ǩ��ȫ�������ļ���</span></strong><strong style="mso-bidi-font-weight: normal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></strong></p>
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p><font face="Calibri">&nbsp;</font></o:p></span></p>
            <p style="TEXT-INDENT: 21pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">����������ί���ˣ�</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-INDENT: 21pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p><font face="Calibri">&nbsp;</font></o:p></span></p>
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">���������˻���Ȩ����</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p><font face="Calibri">&nbsp;</font></o:p></span></p>
            <p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�������������������ڣ�</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 1cm; PADDING-RIGHT: 5.4pt; HEIGHT: 70.4pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" rowspan="2" width="38">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p><font face="Calibri">&nbsp;</font></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">Ϣ</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">ȷ</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��</span><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 219.7pt; PADDING-RIGHT: 5.4pt; HEIGHT: 70.4pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="293">
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><strong style="mso-bidi-font-weight: normal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin"></span></strong></p>
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><strong style="mso-bidi-font-weight: normal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">����֧������ȷ�ϣ�</span></strong><strong style="mso-bidi-font-weight: normal"><span style="FONT-SIZE: 9pt" lang="EN-US"><o:p></o:p></span></strong></p>
            	<p style="TEXT-INDENT: 22.5pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">ί���˵������Ϲ��ʽ���ȫ���������</span></p>
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">����ר����</span></p>
            	<p style="TEXT-INDENT: 22.5pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�˻����ƣ��������йɷ����޹�˾</span></p>
            	<p style="TEXT-INDENT: 22.5pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�������У�</span></p>
            	<p style="TEXT-INDENT: 22.5pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�����˺�</span></p>
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">���죺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�տ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����
					</span>
				</p>
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; 
					mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; 
					mso-hansi-theme-font: minor-latin">���ڣ�&nbsp;&nbsp;&nbsp;&nbsp; ��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;����֧������(����)</span>
					</p>
			</td>
		</tr>
        <tr style="HEIGHT: 88.25pt; mso-yfti-irow: 16; mso-yfti-lastrow: yes">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 
				5.4pt; WIDTH: 219.7pt; PADDING-RIGHT: 5.4pt; HEIGHT: 88.25pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; 
				mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
				valign="top" width="293">
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal">
					<strong style="mso-bidi-font-weight: normal">
						<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
						mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
						minor-latin">�Ƹ�����ȷ�ϣ�</span></strong></p>
            	<p style="TEXT-INDENT: 18pt; MARGIN: 0cm 0cm 0pt" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">ί���˵������Ϲ��ʽ���ȫ�������ί����ǩ��������ļ����������мƻ����������15���������ڽ�����ί���ˡ�</span>
				</p><br>
           		<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal">
					<span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; 
					mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: 
					minor-latin">���죺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�տ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����</span></p><br>
            	<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; 
					mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: 
					Calibri; mso-hansi-theme-font: minor-latin">���ڣ���</span>
					<span style="FONT-SIZE: 9pt" lang="EN-US"><span style="mso-spacerun: yes"><font face="Calibri">&nbsp; </font></span></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�ꡡ</span><span style="FONT-SIZE: 9pt"><font face="Calibri"> </font></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">��</span><span style="FONT-SIZE: 9pt"><font face="Calibri"> </font></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">���ա�</span><span style="FONT-SIZE: 9pt" lang="EN-US"><span style="mso-spacerun: yes"><font face="Calibri">&nbsp;&nbsp;&nbsp; </font></span></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�Ƹ����� (����)</span></p>
            </td>
        </tr>
        <tr>
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; BACKGROUND-COLOR: transparent; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff" width="36"><font face="Calibri"></font></td>
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; BACKGROUND-COLOR: transparent; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff" width="246"><font face="Calibri"></font></td>
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; BACKGROUND-COLOR: transparent; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff" width="38"><font face="Calibri"></font></td>
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; BACKGROUND-COLOR: transparent; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff" width="293"><font face="Calibri"></font></td>
        </tr>
    </tbody>
</table>
<p style="TEXT-INDENT: 36pt; MARGIN: 0cm 0cm 0pt; mso-char-indent-count: 4.0" class="MsoNormal"><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�������йɷ����޹�˾�Ƹ���������</span><span style="FONT-SIZE: 9pt" lang="EN-US"><span style="mso-spacerun: yes"><font face="Calibri">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font></span></span><span style="FONT-FAMILY: ����; FONT-SIZE: 9pt; mso-ascii-font-family: Calibri; mso-ascii-theme-font: minor-latin; mso-fareast-font-family: ����; mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; mso-hansi-theme-font: minor-latin">�Ƹ����ߣ�</span><span style="FONT-SIZE: 9pt" lang="EN-US"><font face="Calibri">0510-82830130 82833093</font></span></p>
</div>
<br>
</BODY>
<%} %>	
	<TABLE cellspacing=0 cellpadding=0 align=center width="100%" border=0>
		<tr>
			<td align="right">
				<input type="button" class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrint();" value="��ӡ(P)">&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</TABLE>		
</HTML>
