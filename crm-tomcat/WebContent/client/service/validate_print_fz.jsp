<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
ContractLocal contract = EJBFactory.getContract();
ValidateprintLocal validate = EJBFactory.getValidateprint();

//上页传的值
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String contract_bh = request.getParameter("contract_bh");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);

//设置属性
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

//查询信息
validate.setSerial_no(serial_no);
validate.setInput_man(input_operatorCode);
List list = validate.listAllFz(1, -1).getRsList();

for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	if (!"".equals(sub_product_name) && sub_product_name!=null) sub_product_name = "("+sub_product_name+")";	
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

	/* /申购、赎回时的确认单起始时间
	if(chg_type == 2 || chg_type == 3){
		if(intrust_flag1 == 1){	//单一产品，起始日期为申请日期到划款日期  	
			Integer sq_date = Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")),new Integer(0));
			Integer hk_date = Utility.parseInt(Utility.trimNull(map.get("HK_DATE")),new Integer(0));
			if(sq_date.intValue() <= hk_date.intValue()){
				validate.setStart_date(sq_date.intValue()==0?null:sq_date);
				validate.setEnd_date(hk_date.intValue()==0?null:hk_date);
			}else{
				validate.setStart_date(hk_date.intValue()==0?null:hk_date);
				validate.setEnd_date(sq_date.intValue()==0?null:sq_date);
			}
		}else{	   //集合产品，起始日期为上次开放日到本次开放日
			validate.setStart_date(Utility.parseInt(Utility.trimNull(map.get("LAST_OPEN_DATE")),null));
			validate.setEnd_date(Utility.parseInt(Utility.trimNull(map.get("OPEN_DATE")),null));
			//如果上面日期为空，取 划款日 至 到帐日
			if (validate.getStart_date()==null||validate.getStart_date().intValue()==0) {
				validate.setStart_date(Utility.parseInt(Utility.trimNull(map.get("HK_DATE")),null));
			}
			if (validate.getEnd_date()==null||validate.getEnd_date().intValue()==0) {
				validate.setEnd_date(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),null));
			}
		}
	}
	//认申购
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
	//赎回
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
//拆分char数组(金额)
String money = "￥"+Utility.trimNull(after_money);
char[] after = money.toCharArray();
char[] after2 = new char[15];
for(int b = after.length-1;b>=0;b--)
	after2[after.length-1-b] = after[b];

char[] totoal = new char[15];
char[] date = money.toCharArray();

String chg = "￥"+Utility.trimNull(chg_money2);
char[] chg2 = chg.toCharArray();

for (int b=chg2.length-1; b>=0; b--) 
	totoal[chg2.length-1-b] = chg2[b];

String chg3 = "￥"+Utility.trimNull(chg_money);
char[] chg4 = chg3.toCharArray();
char[] totoal2 = new char[15];
for(int b = chg4.length-1;b>=0;b--)
	totoal2[chg4.length-1-b] = chg4[b];

//时间拆分
//validate.getHk_date().intValue();
String hk_date = Utility.trimNull(validate.getHk_date());
char[] new_date = hk_date.toCharArray();
char[] s = new char[8];
for (int a=0;a<new_date.length;a++)
	s[a]=new_date[a];

contract.remove();
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
<style media="screen">
.show     { display: none }
</style>
<style media="print">
.noprint     { display: none }
.trheight	 { height:80px }
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery.ztree-2.6.min.js"></script>
<script language=javascript>
window.onload = function() {
		setProjStartDate();
	};

function _print() {
	$.ajax({
	  type: 'POST',
	  url: "validate_print_fz_update_print_times.jsp",
	  data: {
			serial_no: <%=serial_no%>
	  }
	});

	window.print();
}

function setProjStartDate() {
	var retval = showModalDialog('choose_proj_start_date.jsp', '','dialogWidth:350px;dialogHeight:220px;status:0;help:0');
	if (retval) document.getElementById("start_date").innerHTML = retval;
}

function doPrintSetup(){	//打印设置
	WB.ExecWB(8,1)
}

function doPrintPreview(){ //打印预览
	WB.ExecWB(7,1)
}

function doPrint(){
	window.open("validate_print_fz.jsp?flag=2&product_id=<%=product_id%>&contract_bh=<%=contract_bh%>&serial_no=<%=serial_no%>");
}
</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post" action="purchase_print.jsp">

<!---套打方法-->

<head>
<style type="text/css">
.style1 {
	font-family: 宋体;
	font-size: 11pt;
}
</style>
</head>

<div align="center">
<p>
<table style="MARGIN: 2.75pt 6.75pt; WIDTH: 529.65pt; BORDER-COLLAPSE: collapse; mso-yfti-tbllook: 1184; mso-table-lspace: 9.0pt; mso-table-rspace: 9.0pt; mso-table-anchor-vertical: margin; mso-table-anchor-horizontal: margin; mso-table-left: center; mso-table-top: 18.15pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-table-tspace: 5.0pt; mso-table-bspace: 5.0pt" border="0" cellspacing="0" cellpadding="0" width="706" align="center" class="MsoNormalTable">
    <tbody>
        <tr style="HEIGHT: 177.2pt; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 529.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 177.2pt; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff; PADDING-TOP: 0cm" width="706" colspan="15" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">方正东亚信托有限责任公司信托单位确认单</span></strong><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span></strong><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 1">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: windowtext 1pt solid; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">产品名称</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 446.25pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: windowtext 1pt solid; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="595" colspan="14" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt"><%=Utility.trimNull(product_name)%><%=sub_product_name%>&nbsp;</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 2">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">信托合同编号</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 224.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="299" colspan="5" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt"><%=Utility.trimNull(contract_sub_bh)%>&nbsp;</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 73.95pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="99" colspan="3" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">委托人</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 147.9pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="197" colspan="6" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt"><%=Utility.trimNull(validate.getCust_name())%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 3">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">资金到账日</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 224.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="299" colspan="5" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt"><%=Format.formatDateCn(validate.getDz_date())%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 73.95pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="99" colspan="3" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">项目成立日</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 147.9pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="197" colspan="6">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" id="start_date"></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 32.6pt; mso-yfti-irow: 4">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" rowspan="2" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">认<span lang="EN-US">/</span>申购金额</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 92.1pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" rowspan="2" width="123" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt"><!-- 人民币&nbsp; --><%=Utility.trimNull(toChina)%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 58.35pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" rowspan="2" width="78" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">（小写）</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">百</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">十</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">亿</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">千</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">百</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">十</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">万</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">千</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">百</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">十</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">元</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 32.6pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">角</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 31.85pt; mso-yfti-irow: 5">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[14]%><%=totoal2[13]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[12]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[11]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[10]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[9]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[8]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[7]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[6]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[5]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[4]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[3]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 24.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 31.85pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="33" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><%=totoal2[1]%></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 6">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 83.4pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt" width="111" nowrap="nowrap">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="center"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">认<span lang="EN-US">/</span>申购单位</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 446.25pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="595" colspan="14" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt"><%=Utility.trimNull(chg_amount) %>&nbsp;份</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 7">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 307.8pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="410" colspan="6" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">信托计划存续期间，信托利益方式的取得（适用时）：</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 221.85pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-top-alt: solid windowtext .5pt; mso-border-bottom-alt: solid windowtext .5pt; mso-border-right-alt: solid windowtext .5pt" width="296" colspan="9" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><span style="mso-spacerun: yes">&nbsp; </span></span><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">□现金分红方式<span lang="EN-US"><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp; </span></span>□分红转投资方式<span lang="EN-US"><span style="mso-spacerun: yes">&nbsp; </span></span></span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
        <tr style="HEIGHT: 39.7pt; mso-yfti-irow: 8; mso-yfti-lastrow: yes">
            <td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 529.65pt; PADDING-RIGHT: 5.4pt; HEIGHT: 39.7pt; BORDER-TOP: #ffffff; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" width="706" colspan="15" nowrap="nowrap">
            <p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-element-frame-vspace: 5.0pt" class="MsoNormal" align="left"><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"></span><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 11pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt">受托人签章：</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></p>
            </td>
        </tr>
    </tbody>
</table>
</p>
<p style="TEXT-ALIGN: left; MARGIN: 0cm 0cm 0pt; mso-pagination: widow-orphan" class="MsoNormal" align="left"><font face="Calibri"><span style="FONT-SIZE: 12pt; mso-ascii-font-family: Calibri; mso-hansi-font-family: Calibri; mso-bidi-font-family: Calibri; mso-fareast-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US">&nbsp;</span><span style="FONT-FAMILY: 宋体; FONT-SIZE: 12pt; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p></o:p></span></font></p>
<p style="MARGIN: 0cm 0cm 0pt" class="MsoNormal"><span lang="EN-US"><o:p><font size="3" face="Calibri">&nbsp;</font></o:p></span></p>
<p>&nbsp;</p>
</div>

<div class="noprint" align="left"><br><br>
	<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
	<!--套打设置方法-->
	<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:_print();">打印(<u>P</u>)</button>
	&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
	&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;
	<!--<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrintPreview()">打印预览(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
	<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
</div>				

</FORM>
</BODY>
</HTML><%validate.remove();%>