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

//设置打印标志为 1已打印
validate.updatePrintFlag(new Integer(1),serial_no);
%>

<style media="print">
.noprint     { display: none }
</style>


<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>
<style>
td {
	font-size:16px;
}
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>

<script language=javascript>
function doPrintSetup(){
//打印设置
WB.ExecWB(8,1)
}
function doPrintPreview(){
//打印预览

WB.ExecWB(7,1)
}

</script>

<table cellspacing=0 cellpadding=0 border=0 width="730px" style="page-break-after:always;">
	<tr>
		<td valign=top>
			<table cellspacing=0 cellpadding=0 align=center width="730px" border=0>
				<tr>
					<td align="center" height="100px">
					</td>
				</tr>
				<tr>
					<td  height="50px">
						<table border=0><tr><td width="40px"></td><td><%=Utility.trimNull(product_name)%><%=sub_product_name %></td></tr></table>
				</td>
			</tr>
			<tr>
				<td height="48px">
					<table border=0>
						<tr>
							<td width="85px"></td>
							<td width="170px" align="left"><b>
								<%=Utility.trimNull(contract_sub_bh)%></b></td>
							<td width="90px" align="left"></td>
							<td><%=Utility.trimNull(validate.getCust_name())%></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="45px">
					<table>
						<tr>
							<td width="45px"></td>
							<td>
								<table>
									<tr>
										<td width="70px"><%if(chg_type==1) out.print("√"); %></td>
										<td width="70px"><%if(chg_type==2) out.print("√"); %></td>
										<td width="80px"><%if(chg_type==3) out.print("√"); %></td>
										<td width="100px"><%if(chg_type==4) out.print("√"); %></td>
										<td><%if(chg_type==0) out.print("√"); %></td>
									</tr>
								</table>
							</td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td  height="45px">
					<table>
						<tr>
							<td width="50px"></td>
							<td width="70px"><%if(jk_type.equals("111420")) out.print("√");%></td>
							<td width="70px"><%if(jk_type.equals("111402"))  out.print("√");%></td>
							<td width="80px"><%if(jk_type.equals("111410"))  out.print("√"); %></td>
							<td><%if((!jk_type.equals("111410"))&&(!jk_type.equals("111402")) ) out.print("√");%></td>
							<td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="50px">
					<table>
						<tr>
							<td  width="55px"></td>
							<td>
								<table>
									<tr>
								<td width="60px">&nbsp;<%=validate.getDz_date()!=null?Utility.trimNull(validate.getDz_date()).substring(0,4):""%></td>
								<td width="60px">&nbsp;<%=validate.getDz_date()!=null?Utility.trimNull(validate.getDz_date()).substring(4,6):""%></td>
								<td width="60px">&nbsp;<%=validate.getDz_date()!=null?Utility.trimNull(validate.getDz_date()).substring(6):""%></td>
									</tr>
								</table>
							</td>
							<td width="40px"></td>
							<td>
								<table>
									<tr>
								<td width="75px">&nbsp;<%=validate.getHk_date()!=null?Utility.trimNull(validate.getHk_date()).substring(0,4):""%></td>
								<td width="70px">&nbsp;<%=validate.getHk_date()!=null?Utility.trimNull(validate.getHk_date()).substring(4,6):""%></td>
								<td width="75px">&nbsp;<%=validate.getHk_date()!=null?Utility.trimNull(validate.getHk_date()).substring(6):""%></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="36px">
					<table>
						<tr>
							<td width="70px">
							</td>
							<td width="50px">
								<%=Utility.trimNull(fee_rate)%>
							</td>
							<td width="150px">
							</td>
							<td width="120px">
								<%=chg_type == 1 || chg_type==2 ? Utility.numToChinese(Utility.trimNull(fee_money)) : ""%>&nbsp;
							</td>
							<td width="120px">
							</td>
							<td>
								<%=Utility.trimNull(price)%>&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td  height="39px">
						<table>
						<tr>
							<td width="110px">
							</td>
							<td width="130px">
								<%=Utility.trimNull(toChina)%>&nbsp;
							</td>
							<td width="26px">
							</td>
							<td>
								<table>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td width="25px"><%=totoal2[14]%><%=totoal2[13]%></td>
										<td width="25px"><%=totoal2[12]%></td>
										<td width="25px"><%=totoal2[11]%></td>
										<td width="25px"><%=totoal2[10]%></td>
										<td width="25px"><%=totoal2[9]%></td>
										<td width="25px"><%=totoal2[8]%></td>
										<td width="25px"><%=totoal2[7]%></td>
										<td width="25px"><%=totoal2[6]%></td>
										<td width="25px"><%=totoal2[5]%></td>
										<td width="25px"><%=totoal2[4]%></td>
										<td width="25px"><%=totoal2[3]%></td>
										<td width="25px"><%=totoal2[1]%></td>
										<td width="25px"><%=totoal2[0]%></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="39px">
					<table>
						<tr>
							<td width="65px" valign="top"></td><td><%=Utility.trimNull(chg_amount) %></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="40px">
						<table>
						<tr>
							<td width="70px">
							</td>
							<td width="50px">
								<%=Utility.trimNull(fee_rate2)%>&nbsp;
							</td>
							<td width="130px">
							</td>
							<td width="120px">
								<%=chg_type == 3 ? Utility.numToChinese(Utility.trimNull(fee_money2)) : ""%>&nbsp;
							</td>
							<td width="140px">
							</td>
							<td>
								<%=Utility.trimNull(price2)%>&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="40px">
					<table>
						<tr>
							<td width="65px"></td><td><%=Utility.trimNull(chg_amount2)%>&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="50px">
					<table>
						<tr>
							<td  width="110px">
							</td>
							<td width="130px">
								<%=Utility.trimNull(toChina2)%>&nbsp;
							</td>
							<td width="26px">
							</td>
							<td>
								<table>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td width="25px"><%=totoal[14]%><%=totoal[13]%></td>
										<td width="25px"><%=totoal[12]%></td>
										<td width="25px"><%=totoal[11]%></td>
										<td width="25px"><%=totoal[10]%></td>
										<td width="25px"><%=totoal[9]%></td>
										<td width="25px"><%=totoal[8]%></td>
										<td width="25px"><%=totoal[7]%></td>
										<td width="25px"><%=totoal[6]%></td>
										<td width="25px"><%=totoal[5]%></td>
										<td width="25px"><%=totoal[4]%></td>
										<td width="25px"><%=totoal[3]%></td>
										<td width="25px"><%=totoal[1]%></td>
										<td width="25px"><%=totoal[0]%></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="40px">
					<table>
						<tr>
							<td width="65px"></td>
							<td><%=Utility.trimNull(after_amount) %></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="50px">
					<table>
						<tr>
							<td width="110px">
							</td>
							<td width="130px">
								<%=Utility.trimNull(toChina3)%>
							</td>
							<td width="26px">
							</td>
							<td>
									<table>
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td width="25px"><%=after2[14]%><%=after2[13]%></td>
											<td width="25px"><%=after2[12]%></td>
											<td width="25px"><%=after2[11]%></td>
											<td width="25px"><%=after2[10]%></td>
											<td width="25px"><%=after2[9]%></td>
											<td width="25px"><%=after2[8]%></td>
											<td width="25px"><%=after2[7]%></td>
											<td width="25px"><%=after2[6]%></td>
											<td width="25px"><%=after2[5]%></td>
											<td width="25px"><%=after2[4]%></td>
											<td width="25px"><%=after2[3]%></td>
											<td width="25px"><%=after2[1]%></td>
											<td width="25px"><%=after2[0]%></td>
										</tr>
									</table>
										</td>
									</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="50px">
					<table>
						<tr>
							<td></td><td></td><td></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table>
						<tr>
							<td>
								<table>
									<tr>
										<td width="65px"></td>
										<!-- 申购、赎回时确认单起始时间为空到本次开放日 -->
										<%if(chg_type == 2 || chg_type == 3) {%>
										<td>
											<table>
												<tr>
											<td width="60px">&nbsp;<%=validate.getStart_date()!=null?Utility.trimNull(validate.getStart_date()).substring(0,4):""%></td>
											<td width="60px">&nbsp;<%=validate.getStart_date()!=null?Utility.trimNull(validate.getStart_date()).substring(4,6):""%></td>
											<td width="60px">&nbsp;<%=validate.getStart_date()!=null?Utility.trimNull(validate.getStart_date()).substring(6):""%></td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr>
											<td width="60px">&nbsp;<%=validate.getEnd_date()!=null?Utility.trimNull(validate.getEnd_date()).substring(0,4):""%></td>
											<td width="60px">&nbsp;<%=validate.getEnd_date()!=null?Utility.trimNull(validate.getEnd_date()).substring(4,6):""%></td>
											<td width="60px">&nbsp;<%=validate.getEnd_date()!=null?Utility.trimNull(validate.getEnd_date()).substring(6):""%></td>
												</tr>
											</table>
										</td>
										<%}else{ %>
										<!-- 20100628 dongyg 交银要求这个改成从“资金划款日”到“产品成立日”(对认购) -->
										<td>
											<table>
												<tr>
											<td width="60px">&nbsp;<%=validate.getHk_date()!=null?Utility.trimNull(validate.getHk_date()).substring(0,4):""%></td>
											<td width="60px">&nbsp;<%=validate.getHk_date()!=null?Utility.trimNull(validate.getHk_date()).substring(4,6):""%></td>
											<td width="60px">&nbsp;<%=validate.getHk_date()!=null?Utility.trimNull(validate.getHk_date()).substring(6):""%></td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr>
											<td width="60px">&nbsp;<%=validate.getBen_start_date()!=null?Utility.trimNull(validate.getBen_start_date()).substring(0,4):""%></td>
											<td width="60px">&nbsp;<%=validate.getBen_start_date()!=null?Utility.trimNull(validate.getBen_start_date()).substring(4,6):""%></td>
											<td width="60px">&nbsp;<%=validate.getBen_start_date()!=null?Utility.trimNull(validate.getBen_start_date()).substring(6):""%></td>
												</tr>
											</table>
										</td>
										<%} %>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						</td>
						<tr>
				<tr>
					<td>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!--<tr>
		<td height="272px">
			&nbsp;&nbsp;&nbsp;
		</td>
	</tr>-->
</table>
<%
purconFig.remove();
validate.remove();
contract.remove();
%>