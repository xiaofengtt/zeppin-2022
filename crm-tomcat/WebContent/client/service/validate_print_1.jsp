<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
ContractLocal contract = EJBFactory.getContract();
ValidateprintLocal validate = EJBFactory.getValidateprint();
PurconfigLocal purconFig = EJBFactory.getPurconfig();

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
	//申购、赎回时的确认单起始时间
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
	}
}
//拆分char数组(金额)
String money = "￥"+Utility.trimNull(after_money);
char[] after = money.toCharArray();
char[] after2 = new char[15];
for(int b = after.length-1;b>=0;b--){
	after2[after.length-1-b] = after[b];
}

char[] totoal = new char[15];
char[] date = money.toCharArray();

String chg = "￥"+Utility.trimNull(chg_money2);
char[] chg2 = chg.toCharArray();

for(int b = chg2.length-1;b>=0;b--){
	totoal[chg2.length-1-b] = chg2[b];
}

String chg3 = "￥"+Utility.trimNull(chg_money);
char[] chg4 = chg3.toCharArray();
char[] totoal2 = new char[15];
for(int b = chg4.length-1;b>=0;b--){
	totoal2[chg4.length-1-b] = chg4[b];
}



//时间拆分
//validate.getHk_date().intValue();
String hk_date = Utility.trimNull(validate.getHk_date());
char[] new_date = hk_date.toCharArray();
char[] s = new char[8];
for(int a=0;a<new_date.length;a++){
	s[a]=new_date[a];
}
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
.trheight	 { height:50px }
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>
function doPrintSetup(){
//打印设置
WB.ExecWB(8,1)
}
function doPrintPreview(){
//打印预览

WB.ExecWB(7,1)
}
function doPrint(){
	window.open("validate_print_1_do.jsp?flag=2&product_id=<%=product_id%>&contract_bh=<%=contract_bh%>&serial_no=<%=serial_no%>");
}
</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>

<FORM name="theform" method="post" action="purchase_print.jsp">
	<!---套打方法-->
	<table cellspacing=0 cellpadding=0 border=0 width="100%">
		<TR>
			<TD   vAlign=top align=left>
				<table cellspacing=0 cellpadding=0 align=center width="100%" border=0>
					<tr>
						<td align="center" height="60px" class="show">
						</td>
					</tr>
					<TR>
						<TD  align=center height="50px">
							<TABLE border="0" width="100%" cellspacing="0" cellpadding="0">
							<TR>
								<TD class="tdbottom" align="center" colspan="20">
									<FONT size="5" face="黑体"><B><%=application.getAttribute("COMPANY_NAME")%>信托单位确认单No.</B></FONT>
								</TD>
							</TR>
							</TABLE>

							<TABLE border="1"  width="100%" cellspacing="0" cellpadding="1" bordercolor="#000000">

								<TR class="trheight">
									<TD class="tdrightbottom" colspan="3" width="12%" ><FONT size="2.5" face="黑体"><b>产品名称</b></FONT></TD>
									<TD class="tdbottom" colspan="24" width="88%"><FONT size="2.5" face="宋体"><%=Utility.trimNull(product_name)%><%=sub_product_name %>&nbsp;</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="5" width="16%" ><FONT size="2.5" face="黑体"><b>信托合同编号</b></FONT></TD>
									<TD class="tdrightbottom" colspan="8" width="40%"><FONT size=2.5 face="宋体" ><%=Utility.trimNull(contract_sub_bh)%>&nbsp;</FONT></TD>
									<TD class="tdrightbottom" colspan="3" width="8%"><FONT size="2.5" face="黑体"><b>委托人</b></FONT></TD>
									<TD class="tdbottom" colspan="11" width="36%"><FONT size="2.5"face="宋体"><%=Utility.trimNull(validate.getCust_name())%></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="3"  ><FONT size="2.5" face="黑体"><b>确认事由</b></FONT></TD>
									<TD class="tdbottom" colspan="24">
										<FONT size="2.5" face="宋体">&nbsp;&nbsp;
										<%if(chg_type==1) out.print("√"); else out.print("□");%>认购&nbsp;&nbsp;&nbsp;
										<%if(chg_type==2) out.print("√"); else out.print("□");%>申购&nbsp;&nbsp;&nbsp;
										<%if(chg_type==3) out.print("√"); else out.print("□");%>赎回&nbsp;&nbsp;&nbsp;
										<%if(chg_type==4) out.print("√"); else out.print("□");%>分红转投资&nbsp;&nbsp;&nbsp;
										<%if(chg_type==0) out.print("√"); else out.print("□");%>现金分红&nbsp;&nbsp;&nbsp;
										</FONT>
									</TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="3"  ><FONT size="2.5" face=黑体><b>缴款方式</b></FONT></TD>
									<TD class="tdbottom" colspan="24">
										<FONT size="2.5" face="宋体">&nbsp;&nbsp;
										<%if(jk_type.equals("111420")) out.print("√"); else out.print("□");%>POS机&nbsp;&nbsp;&nbsp;
										<%if(jk_type.equals("111402"))  out.print("√"); else out.print("□");%>本票&nbsp;&nbsp;&nbsp;
										<%if(jk_type.equals("111410"))  out.print("√"); else out.print("□");%>电汇&nbsp;&nbsp;&nbsp;
										<%if((!jk_type.equals("111410"))&&(!jk_type.equals("111402")) )  out.print("√"); else out.print("□");%>其他&nbsp;&nbsp;&nbsp;
										</FONT>
									 </TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="12" width="15%" >
										<FONT size="2.5" face="黑体"><b>资金到账日:</b></FONT>
										<FONT size="2.5" face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;<%=Format.formatDateCn(validate.getDz_date())%><!--&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日--></FONT>
									</TD>
									<TD class="tdbottom" colspan="15"><FONT size="2.5" face="黑体"><b>划款日:</b></FONT>
										<FONT size="2.5" face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;<%=Format.formatDateCn(validate.getHk_date())%><!--&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日--></FONT>
									</TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="5" width="14%" ><FONT size="2.5" face="黑体"><b>认/申购费率</b></FONT></TD>
									<TD class="tdrightbottom"  colspan="1" align="right" width="4%"><FONT size="2.5" face="宋体" ><%=Utility.numToChinese(Utility.trimNull(fee_rate)).equals("零元")?"":Utility.trimNull(fee_rate) %>%</FONT></TD>
									<TD class="tdrightbottom" colspan="4" width="12%"><FONT size="2.5" face="黑体"><b>认/申购费</b></FONT></TD>
									<TD class="tdrightbottom" colspan="7" width="36%"><FONT size="2.5" face="宋体" >人民币&nbsp;<%=Utility.numToChinese(Utility.trimNull(fee_money)).equals("零元")?"":Utility.numToChinese(Utility.trimNull(fee_money))%></FONT>&nbsp;</TD>
									<TD class="tdrightbottom" colspan="4" width="16%"><FONT size="2.5" face="黑体"><b>认/申购价格</b></FONT></TD>
									<TD class="tdbottom" colspan="6" align="right" width="18%"><FONT size="2.5" face="宋体">&nbsp;&nbsp;<%=Utility.trimNull(price)%>元/份</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" rowspan="2" colspan="5" width="16%" ><FONT size="2.5" face="黑体"><b>认/申购金额</b></FONT></TD>
									<TD class="tdrightbottom" rowspan="2" colspan="6" width="32%"><FONT size="2.5" face="宋体">人民币&nbsp;<%=Utility.trimNull(toChina)%></FONT></TD>
									<TD class="tdrightbottom" rowspan="2" colspan="3" width="8%" ><FONT size="2.5" face="宋体">(小写)</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">亿</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">万</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">元</FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">角</FONT></TD>
									<TD class="tdbottom"  align="center" width="4%"><FONT size="2.5" face="宋体">分</FONT></TD>
								</TR>
								<TR>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体"><%=totoal2[14]%>&nbsp;<%=totoal2[13]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[12]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[11]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[10]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[9]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[8]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[7]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[6]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[5]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[4]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[3]%></FONT></TD>
									<TD class="tdrightbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[1]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[0]%></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="4" ><FONT size="2.5" face="黑体"><b>认/申购单位</b></FONT></TD>
									<TD class="tdbottom" colspan="23" align="right"><FONT size=""face="宋体"><%=Utility.trimNull(chg_amount) %>&nbsp;份</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="4" ><FONT size="2.5" face="黑体"><b>赎回费率</b></FONT></TD>
									<TD class="tdrightbottom"  colspan="2" align="right"><FONT size="" face="宋体" >&nbsp;&nbsp;<%=Utility.numToChinese(Utility.trimNull(fee_rate2)).equals("零元")?"":Utility.trimNull(fee_rate2)%>%</FONT></TD>
									<TD class="tdrightbottom" colspan="3"><FONT size="2.5" face="黑体"><b>赎回费</b></FONT></TD>
									<TD class="tdrightbottom" colspan="9"><FONT size="2.5" face="宋体" >人民币&nbsp;<%=Utility.numToChinese(Utility.trimNull(fee_money2)).equals("零元")?"":Utility.numToChinese(Utility.trimNull(fee_money2))%></FONT>&nbsp;&nbsp;</TD>
									<TD class="tdrightbottom" colspan="3"><FONT size="2.5" face="黑体"><b>赎回价格</b></FONT></TD>
									<TD class="tdbottom" colspan="6" align="right"><FONT size="2.5"face="宋体">&nbsp;&nbsp;<%=Utility.trimNull(price2)%>元/份</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="4" ><FONT size="2.5" face="黑体"><b>赎回单位</b></FONT></TD>
									<TD class="tdbottom" colspan="23" align="right"><FONT size=""face="宋体"><%=Utility.trimNull(chg_amount2) %>&nbsp;份</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" rowspan="2" colspan="4" ><FONT size="2.5" face="黑体"><b>赎回金额</b></FONT></TD>
									<TD class="tdrightbottom" rowspan="2" colspan="7" width="25%"><FONT size="2.5" face="宋体">人民币&nbsp;<%=Utility.trimNull(toChina2)%></FONT></TD>
									<TD class="tdrightbottom" rowspan="2" colspan="3" ><FONT size="2.5" face="宋体">(小写)</FONT></TD>
									<TD class="tdrightbottom"  align="center"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom"  align="center"><FONT size="2.5" face="宋体">亿</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">万</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">元</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">角</FONT></TD>
									<TD class="tdbottom"  align="center"><FONT size="2.5" face="宋体">分</FONT></TD>
								</TR>
								<TR>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体"><%=totoal[14]%>&nbsp;<%=totoal[13]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[12]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[11]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[10]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[9]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[8]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[7]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[6]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[5]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[4]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[3]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="v" face="宋体">&nbsp;<%=totoal[1]%></FONT></TD>
									<TD class="tdbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=totoal[0]%></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="4" ><FONT size="2.5" face="黑体"><b>持有单位</b></FONT></TD>
									<TD class="tdbottom" colspan="23" align="right">
										<FONT size=""face="宋体"><%=Utility.trimNull(after_amount) %>&nbsp;份</FONT>
									</TD>
								</TR>
								<TR class="trheight">

									<TD class="tdrightbottom" rowspan="2" colspan="4"  ><FONT size="2.5" face="黑体"><b>持有金额</b></FONT></TD>
									<TD class="tdrightbottom" rowspan="2" colspan="7" width="25%"><FONT size="2.5" face="宋体">人民币&nbsp;<%=Utility.trimNull(toChina3)%></FONT></TD>
									<TD class="tdrightbottom" rowspan="2" colspan="3" ><FONT size="2.5" face="宋体">(小写)</FONT></TD>
									<TD class="tdrightbottom"  align="center"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom"  align="center"><FONT size="2.5" face="宋体">亿</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">万</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">元</FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">角</FONT></TD>
									<TD class="tdbottom"  align="center"><FONT size="2.5" face="宋体">分</FONT></TD>
								</TR>
								<TR>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体"><%=after2[14]%>&nbsp;<%=after2[13]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[12]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[11]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[10]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[9]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[8]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[7]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[6]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[5]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[4]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[3]%></FONT></TD>
									<TD class="tdrightbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[1]%></FONT></TD>
									<TD  class="tdbottom" align="center"><FONT size="2.5" face="宋体">&nbsp;<%=after2[0]%></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdbottom" colspan="27" >
										<FONT size="2.5" face="黑体"> <b>信托计划存续期间,信托利益方式的取得(适用时)：</b>
										</FONT> <FONT size="2.5" face="宋体"> □现金分红方式 □分红转投资方式 </FONT>
									</TD>
								</TR>
								<TR>
									<TD class="tdbottom" colspan="27">
										<FONT size="2.5" face="宋体">
										<!-- 申购、赎回时确认单起始时间为空到本次开放日 -->
										<%if(chg_type == 2 || chg_type == 3) {%>
										本确认单确认 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=validate.getStart_date() != null ? validate.getStart_date().toString().substring(0,4):""%>年
										&nbsp;&nbsp;<%=validate.getStart_date() != null ? validate.getStart_date().toString().substring(4,6):""%>月
										&nbsp;&nbsp;<%=validate.getStart_date() != null ? validate.getStart_date().toString().substring(6):""%>日&nbsp;&nbsp;
										至 &nbsp;&nbsp;<%=validate.getEnd_date() != null ? validate.getEnd_date().toString().substring(0,4):""%>年 &nbsp;&nbsp;<%=validate.getEnd_date() != null ? validate.getEnd_date().toString().substring(4,6):""%>月
										&nbsp;&nbsp;<%=validate.getEnd_date() != null ? validate.getEnd_date().toString().substring(6):""%>日区间内的全部交易。 <br>
										<%}else{ %><!-- 20100628 dongyg 交银要求这个改成从“资金划款日”到“产品成立日”(对认购) -->
										本确认单确认 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=validate.getHk_date().toString().substring(0,4)%>年
										&nbsp;&nbsp;<%=validate.getHk_date().toString().substring(4,6)%>月
										&nbsp;&nbsp;<%=validate.getHk_date().toString().substring(6)%>日&nbsp;&nbsp;
										至 &nbsp;&nbsp;<%=validate.getBen_start_date().toString().substring(0,4)%>年 &nbsp;&nbsp;<%=validate.getBen_start_date().toString().substring(4,6)%>月
										&nbsp;&nbsp;<%=validate.getBen_start_date().toString().substring(6)%>日区间内的全部交易。 <br>
										<%}%>
										<br>
										<br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										受托人签章： <br>
										<br>
										<br>
										</FONT>
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<FONT size="2.5" style="宋体">经办人：</FONT>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<FONT size="2.5" style="宋体">复核人：</FONT>
						</TD>
					</TR>
					<TR class="noprint">
						<TD align="right">
							<table id=printbtn border="0" width="100%">
								<tr>
									<td align="right">
										<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
										<!--套打设置方法-->
										<%if(user_id.intValue() != 17){ %>
										<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrint();">打印(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
										<%}else{ %>
										<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:window.print();">打印(<u>P</u>)</button>
										&nbsp;&nbsp;&nbsp;
										<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
										&nbsp;&nbsp;&nbsp;
										<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
										&nbsp;&nbsp;&nbsp;
										<%}%>
										<!--<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:document.all.WB.ExecWB(7,1)">打印预览(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
										<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>

									</td>
								</tr>
							</table>
						</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
	</TABLE>
</FORM>
</BODY>
</HTML>