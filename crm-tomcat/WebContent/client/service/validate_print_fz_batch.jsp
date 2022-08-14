<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
ContractLocal contract = EJBFactory.getContract();
ValidateprintLocal validate = EJBFactory.getValidateprint();

//��ҳ����ֵ
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String contract_bh = request.getParameter("contract_bh");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);
Integer pint_page_index = Utility.parseInt(request.getParameter("pint_page_index"),null);
int _i = Utility.parseInt(request.getParameter("i"), 0);

String printStyle = "page-break-after: always;";
if (pint_page_index!=null && pint_page_index.intValue()==-1) printStyle = "";

//��������
String product_name = "";
String sub_product_name = "";
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

String toChina = "";
String toChina2 = "";
String toChina3 = "" ;

//��ѯ��Ϣ
validate.setSerial_no(serial_no);
validate.setInput_man(input_operatorCode);
List list = validate.listAllFz(1, -1).getRsList();

for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	if(!"".equals(sub_product_name)&& sub_product_name!=null)
		sub_product_name = "("+sub_product_name+")";
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	validate.setCust_name(Utility.trimNull(map.get("CUST_NAME")));
	validate.setDz_date(Utility.parseInt(Utility.trimNull(map.get("TO_BANK_DATE")),null));
	validate.setHk_date(Utility.parseInt(Utility.trimNull(map.get("HK_DATE")),null));
	chg_type = Utility.parseInt(Utility.trimNull(map.get("CHG_TYPE")),0);
	jk_type = Utility.trimNull(map.get("JK_TYPE"));
	validate.setAppl_amout(Utility.parseDecimal(Utility.trimNull(map.get("APPL_AMOUNT")),null));
	validate.setFee_money(Utility.parseDecimal(Utility.trimNull(map.get("FEE_MONEY")),null));
	validate.setPrice(Utility.parseDecimal(Utility.trimNull(map.get("PRICE")),null));
	validate.setChg_money(Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),null));
	validate.setChg_amount(Utility.parseDecimal(Utility.trimNull(map.get("TO_AMOUNT")),null));
	validate.setAfter_amount(Utility.parseDecimal(Utility.trimNull(map.get("AFTER_AMOUNT")),null));
	validate.setAfter_money(Utility.parseDecimal(Utility.trimNull(map.get("AFTER_MONEY")),null));
	validate.setFee_rate(Utility.parseDecimal(Utility.trimNull(map.get("FEE_RATE")),null));
	validate.setBen_start_date(Utility.parseInt(Utility.trimNull(map.get("BEN_START_DATE")),null));
	validate.setStart_date(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),null));
	int intrust_flag1 = Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG1")),1);

	if(validate.getChg_amount()!=null)
		chg_amount = validate.getChg_amount().setScale(2,2);

	if (validate.getChg_money()!=null){
		chg_money = validate.getChg_money().setScale(2,2);
		toChina = Utility.numToChinese(Utility.trimNull(chg_money));
	}

	/*/�깺�����ʱ��ȷ�ϵ���ʼʱ��
	if(chg_type == 2 || chg_type == 3){
		if(intrust_flag1 == 1){	//��һ��Ʒ����ʼ����Ϊ�������ڵ���������  	
			Integer sq_date = Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")),new Integer(0));
			Integer hk_date = Utility.parseInt(Utility.trimNull(map.get("HK_DATE")),new Integer(0));
			if(sq_date.intValue() <= hk_date.intValue()){
				validate.setStart_date(sq_date.intValue()==0?null:sq_date);
				validate.setEnd_date(hk_date.intValue()==0?null:hk_date);
			}else{
				validate.setStart_date(hk_date.intValue()==0?null:hk_date);
				validate.setEnd_date(sq_date.intValue()==0?null:sq_date);
			}
		}else{	   //���ϲ�Ʒ����ʼ����Ϊ�ϴο����յ����ο�����
			validate.setStart_date(Utility.parseInt(Utility.trimNull(map.get("LAST_OPEN_DATE")),null));
			validate.setEnd_date(Utility.parseInt(Utility.trimNull(map.get("OPEN_DATE")),null));
			//�����������Ϊ�գ�ȡ ������ �� ������
			if (validate.getStart_date()==null||validate.getStart_date().intValue()==0) {
				validate.setStart_date(Utility.parseInt(Utility.trimNull(map.get("HK_DATE")),null));
			}
			if (validate.getEnd_date()==null) {
				validate.setEnd_date(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),null));
			}
		}
	}
	//���깺
	if(chg_type == 1 || chg_type==2 ){

		if(validate.getChg_amount()!=null)
			chg_amount = validate.getChg_amount().setScale(2,2);
		if(validate.getChg_money()!=null)
			chg_money = validate.getChg_money().setScale(2,2);
		if(validate.getFee_money()!=null)
			fee_money = validate.getFee_money().setScale(2,2);
		if(validate.getPrice()!=null)
			price = validate.getPrice().setScale(4,4);
		if(validate.getChg_money()!=null){
			chg_money = validate.getChg_money().setScale(2,2);
			toChina = Utility.numToChinese(Utility.trimNull(chg_money));
		}
		if(validate.getFee_rate()!=null)
			fee_rate = validate.getFee_rate().multiply(new BigDecimal(100)).setScale(2);
	}
	//���
	if(chg_type==3){
		if(validate.getChg_amount()!=null)
			chg_amount2 = validate.getChg_amount().setScale(2,2);
		if(validate.getChg_money()!=null){
			chg_money2 = validate.getChg_money().setScale(2,2);
			toChina2 = Utility.numToChinese(Utility.trimNull(chg_money2));
		}
		if(validate.getFee_money()!=null)
			fee_money2 = validate.getFee_money().setScale(2,2);

		if(validate.getPrice()!=null)
			price2 = validate.getPrice().setScale(4,4);

		if(validate.getChg_money()!=null)
			chg_money2 = validate.getChg_money().setScale(2,2);



		fee_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("FEE_RATE")),null);
		if(validate.getFee_rate()!=null)
			fee_rate2 = validate.getFee_rate().multiply(new BigDecimal(100)).setScale(2,2);

	}

	if(validate.getAfter_amount()!=null)
		after_amount = validate.getAfter_amount().setScale(2,2);

	if(validate.getAfter_money()!=null){
		after_money = validate.getAfter_amount().multiply(validate.getPrice()).setScale(2,4);
		toChina3 = Utility.numToChinese(Utility.trimNull(after_money));
	}*/
}
//���char����(���)
String money = "��"+Utility.trimNull(after_money);
char[] after = money.toCharArray();
char[] after2 = new char[15];
for (int b = after.length-1;b>=0;b--)
	after2[after.length-1-b] = after[b];

char[] totoal = new char[15];
char[] date = money.toCharArray();

String chg = "��"+Utility.trimNull(chg_money2);

char[] chg2 = chg.toCharArray();

for (int b = chg2.length-1;b>=0;b--)
	totoal[chg2.length-1-b] = chg2[b];

String chg3 = "��"+Utility.trimNull(chg_money);
char[] chg4 = chg3.toCharArray();
char[] totoal2 = new char[15];
for (int b = chg4.length-1;b>=0;b--)
	totoal2[chg4.length-1-b] = chg4[b];

//ʱ����
//validate.getHk_date().intValue();
String hk_date = Utility.trimNull(validate.getHk_date());
char[] new_date = hk_date.toCharArray();
char[] s = new char[8];
for (int a=0;a<new_date.length;a++)
	s[a]=new_date[a];

contract.remove();
%>
<div STYLE="<%=printStyle%>">
<FORM name="theform" method="post" >
<table style="MARGIN: 2.75pt 6.75pt; WIDTH: 529.65pt; BORDER-COLLAPSE: collapse; mso-yfti-tbllook: 1184; mso-table-lspace: 9.0pt; mso-table-rspace: 9.0pt; mso-table-anchor-vertical: margin; mso-table-anchor-horizontal: margin; mso-table-left: center; mso-table-top: 18.15pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-table-tspace: 5.0pt; mso-table-bspace: 5.0pt" border="0" cellspacing="0" cellpadding="0" width="706" align="center" class="MsoNormalTable">
    <tbody>
        <tr style="HEIGHT: 177.2pt; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 529.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 177.2pt; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff; PADDING-TOP: 0cm" width="706" colspan="15" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">�������������������ι�˾���е�λȷ�ϵ�</span></strong><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 1">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: windowtext 1pt solid; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��Ʒ����</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 446.25pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: windowtext 1pt solid; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="595" colspan="14" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt"><%=Utility.trimNull(product_name)%><%=sub_product_name%>&nbsp;</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 2">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">���к�ͬ���</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 224.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="299" colspan="5" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt"><%=Utility.trimNull(contract_sub_bh)%>&nbsp;</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 73.95pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="99" colspan="3" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">ί����</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 147.9pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="197" colspan="6" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt"><%=Utility.trimNull(validate.getCust_name())%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 3">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">�ʽ�����</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 224.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="299" colspan="5" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt"><%=Format.formatDateCn(validate.getDz_date())%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 73.95pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="99" colspan="3" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��Ŀ������</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 147.9pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="197" colspan="6">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" id="<%=_i%>start_date"></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 32.6pt; mso-yfti-irow: 4">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" rowspan="2" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��<span lang="EN-US">/</span>�깺���</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 92.1pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" rowspan="2" width="123" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt"><!-- �����&nbsp; --><%=Utility.trimNull(toChina)%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 58.35pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" rowspan="2" width="78" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��Сд��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">ʮ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">ǧ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">ʮ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">ǧ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">ʮ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">Ԫ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 31.85pt; mso-yfti-irow: 5">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[14]%><%=totoal2[13]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[12]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[11]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[10]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[9]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[8]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[7]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[6]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[5]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[4]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[3]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[1]%></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 6">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">��<span lang="EN-US">/</span>�깺��λ</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 446.25pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="595" colspan="14" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt"><%=Utility.trimNull(chg_amount) %>&nbsp;��</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 7">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 307.8pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="410" colspan="6" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">���мƻ������ڼ䣬�������淽ʽ��ȡ�ã�����ʱ����</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 221.85pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="296" colspan="9" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><span style="mso-spacerun: yes">&nbsp; </span></span><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">���ֽ�ֺ췽ʽ<span lang="EN-US"><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp; </span></span>���ֺ�תͶ�ʷ�ʽ<span lang="EN-US"><span style="mso-spacerun: yes">&nbsp; </span></span></span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 8; mso-yfti-lastrow: yes">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 529.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="706" colspan="15" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"></span><span style="FONT-FAMILY: ����; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt">������ǩ�£�</span><span style="FONT-FAMILY: ����; FONT-SIZE: 12pt; mso-bidi-font-family: ����; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
    </tbody>
</table>
	</FORM>
</div><%validate.remove();%>