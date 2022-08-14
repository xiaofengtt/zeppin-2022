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
			if (validate.getEnd_date()==null) {
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
<div STYLE="page-break-after: always;">
<FORM name="theform" method="post" >
	<div style="mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly">
<table cellspacing="0" cellpadding="0" align="center" vspace="0" hspace="0">
    <tbody>
        <tr>
            <td style="BORDER-BOTTOM: #ffffff; BORDER-LEFT: #ffffff; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 9pt; PADDING-RIGHT: 9pt; BORDER-TOP: #ffffff; BORDER-RIGHT: #ffffff; PADDING-TOP: 0cm" valign="top" align="left">
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-pagination: widow-orphan" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-hansi-font-family: Calibri; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p>&nbsp;</o:p></span></strong></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-pagination: widow-orphan" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-hansi-font-family: Calibri; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p>&nbsp;</o:p></span></strong></p>
            <p style="TEXT-ALIGN: center; MARGIN: 0cm 0cm 0pt; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-pagination: widow-orphan" class="MsoNormal" align="center"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-hansi-font-family: Calibri; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p>&nbsp;</o:p></span></strong></p>
            <p style="MARGIN: 0cm 0cm 0pt; mso-element: frame; mso-element-frame-hspace: 9.0pt; mso-element-wrap: around; mso-element-anchor-horizontal: margin; mso-element-left: center; mso-element-top: 18.15pt; mso-height-rule: exactly; mso-pagination: widow-orphan" class="MsoNormal"><strong><span style="FONT-FAMILY: 宋体; COLOR: black; FONT-SIZE: 18pt; mso-hansi-font-family: Calibri; mso-bidi-font-family: 宋体; mso-font-kerning: 0pt" lang="EN-US"><o:p>&nbsp;</o:p></span></strong></p>
            </td>
        </tr>
    </tbody>
</table>
</div>
	<TABLE cellspacing=0 cellpadding=0 align=center width="100%" border=0>
					<tr>
						<td align="center" height="60px" class="show">
						</td>
					</tr>
					<TR>
						<TD  align=center height="50px">
							<TABLE border="0" width="100%" cellspacing="0" cellpadding="0">
							<TR>
								<TD class="tdbottom" align="center" colspan="20">
									<FONT size="5" face="黑体"><B><%=application.getAttribute("COMPANY_NAME")%>信托单位确认单</B></FONT>
								</TD>
							</TR>
							</TABLE>

							<TABLE border="0"  width="100%" cellspacing="0" cellpadding="1">

								<TR class="trheight">
									<TD class="tdbottom" colspan="3" width="12%" ><FONT size="2.5" face="黑体"><b>产品名称</b></FONT></TD>
									<TD class="tdbottom" colspan="24" width="88%"><FONT size="2.5" face="宋体"><%=Utility.trimNull(product_name)%><%=sub_product_name%>&nbsp;</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdbottom" colspan="3" width="16%" ><FONT size="2.5" face="黑体"><b>信托合同编号</b></FONT></TD>
									<TD class="tdbottom" colspan="10" width="40%"><FONT size=2.5 face="宋体" ><%=Utility.trimNull(contract_sub_bh)%>&nbsp;</FONT></TD>
									<TD class="tdbottom" colspan="3" width="8%"><FONT size="2.5" face="黑体"><b>委托人</b></FONT></TD>
									<TD class="tdbottom" colspan="11" width="36%"><FONT size="2.5"face="宋体"><%=Utility.trimNull(validate.getCust_name())%></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdbottom" colspan="3" width="15%" >
										<FONT size="2.5" face="黑体"><b>资金到账日:</b></FONT>
										
									</TD>
									<td class="tdbottom" colspan="10" width="15%"><FONT size="2.5" face="宋体"><%=Format.formatDateCn(validate.getDz_date())%></FONT></td>
									<TD class="tdbottom" colspan="14">
										<FONT size="2.5" face="黑体"></FONT>
										<FONT size="2.5" face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
									</TD>
								</TR>
								<TR class="trheight">
									<TD class="tdbottom" rowspan="2" colspan="3" width="16%" ><FONT size="2.5" face="黑体"><b>认/申购金额</b></FONT></TD>
									<TD class="tdbottom" rowspan="2" colspan="8" width="32%"><FONT size="2.5" face="宋体">人民币&nbsp;<%=Utility.trimNull(toChina)%></FONT></TD>
									<TD class="tdbottom" rowspan="2" colspan="3" width="8%" ><FONT size="2.5" face="宋体">(小写)</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">亿</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">万</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">千</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">百</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">十</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">元</FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">角</FONT></TD>
									<TD class="tdbottom"  align="center" width="4%"><FONT size="2.5" face="宋体">分</FONT></TD>
								</TR>
								<TR>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[14]%><%=totoal2[13]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[12]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[11]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[10]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[9]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[8]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[7]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[6]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[5]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[4]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[3]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[1]%></FONT></TD>
									<TD class="tdbottom" align="center" width="4%"><FONT size="2.5" face="宋体">&nbsp;<%=totoal2[0]%></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdbottom" colspan="3" ><FONT size="2.5" face="黑体"><b>认/申购单位</b></FONT></TD>
									<TD class="tdbottom" colspan="24" align="right"><FONT size=""face="宋体"><%=Utility.trimNull(chg_amount) %>&nbsp;份</FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdbottom" colspan="13" >
										<FONT size="2.5" face="黑体"> <b>信托计划存续期间,信托利益方式的取得(适用时)：</b></FONT> 
									</TD>
									<td class="tdbottom" colspan="14"><FONT size="2.5" face="宋体"> □现金分红方式 □分红转投资方式 </FONT></td>
								</TR>
								<TR>
									<TD class="tdbottom" colspan="27">
										<FONT size="2.5" face="宋体">										
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
				</TABLE>
	</FORM>
</div>