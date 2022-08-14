<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.contractManage.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
Integer query_product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));

ContractLocal contract = EJBFactory.getContract();
CustomerLocal customer = EJBFactory.getCustomer();
ProductLocal product = EJBFactory.getProduct();
ContractVO vo = new ContractVO();
CustomerVO custvo = new CustomerVO();
ProductVO productvo = new ProductVO();

Map contractMap =new HashMap();
Map custMap = new HashMap();
Map productMap = new HashMap();
vo.setSerial_no(serial_no);
vo.setInput_man(input_operatorCode);

List contractList = contract.load(vo);
Integer sub_product_id = new Integer(0);
Integer prov_flag = new Integer(0);
Integer r_channel_id = new Integer(0);
String channel_type = "";
String channel_cooperation = "";
Integer product_id = new Integer(0);
String prov_level = "";
String bank_city = "",bank_province = "";
Integer recommend_man = new Integer(0);

if(contractList!=null&&contractList.size()==1){
	contractMap = (Map)contractList.get(0);
	prov_flag = Utility.parseInt(Utility.trimNull(contractMap.get("PROV_FLAG")), new Integer(0));
	sub_product_id =  Utility.parseInt(Utility.trimNull(contractMap.get("SUB_PRODUCT_ID")),new Integer(0));
	channel_type = Utility.trimNull(contractMap.get("CHANNEL_TYPE"));
	channel_cooperation = Utility.trimNull(contractMap.get("CHANNEL_COOPERTYPE"));
	r_channel_id = Utility.parseInt(Utility.trimNull(contractMap.get("CHANNEL_ID")),new Integer(0));
	product_id =  Utility.parseInt(Utility.trimNull(contractMap.get("PRODUCT_ID")), new Integer(0));	
	prov_level = Utility.trimNull(contractMap.get("PROV_LEVEL"));
	recommend_man = Utility.parseInt(Utility.trimNull(
						contractMap.get("RECOMMEND_MAN")),new Integer(0));		
	bank_province = Utility.trimNull(contractMap.get("BANK_PROVINCE_NAME"));
	bank_city = Utility.trimNull(contractMap.get("BANK_CITY_NAME"));
}

//custvo.setCust_id(new Integer(1));
custvo.setCust_id((Integer)contractMap.get("CUST_ID"));
custvo.setInput_man(input_operatorCode);
productvo.setProduct_id((Integer)contractMap.get("PRODUCT_ID"));

ContractVO contract_vo = new ContractVO();//查询认购信息
contract_vo.setProduct_id(null);
contract_vo.setBook_code(new Integer(1));
contract_vo.setCust_id((Integer)contractMap.get("CUST_ID"));

List custList = customer.listCustomerLoad(custvo);
List productList = product.load(productvo);
if(custList!=null&&custList.size()==1)	custMap = (Map)custList.get(0);

if(productList!=null&&productList.size()==1) productMap = (Map)productList.get(0);

//获得认购附件
AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
AttachmentVO attachment_vo = new AttachmentVO();
attachment_vo.setDf_talbe_id(new Integer(5));
attachment_vo.setDf_serial_no(serial_no);

List attachmentList = attachmentLocal.load(attachment_vo);	

contract.remove();
customer.remove();
product.remove();
//获得双录视频信息
VideoRecordingBean vr = new VideoRecordingBean();
List list=vr.getVideoRecordingListByContractId(serial_no, input_operatorCode );
Map vdmap=new HashMap();
Integer checkflag_vide = new Integer(0);
String checkflagName_vide="";
if (list!=null && !list.isEmpty()){
	vdmap=(Map)list.get(0);
	checkflag_vide=(Integer)vdmap.get("CheckFlag");
	if (checkflag_vide==null) checkflag_vide= new Integer(0);
	else if (checkflag_vide.intValue()==1) checkflagName_vide="未审核";
	else if (checkflag_vide.intValue()==2) checkflagName_vide="审核通过";
	else if (checkflag_vide.intValue()==3) checkflagName_vide="审核否决";
}

//受益认购总金额
BigDecimal to_amount = new BigDecimal(0.000);//认购金额
BigDecimal exchange_amount = new BigDecimal(0.000);//转让金额
BigDecimal back_amount = new BigDecimal(0.000);//兑付金额
BigDecimal ben_amount = new BigDecimal(0.000);//受益金额
// Integer serial_no = new Integer(0);
BenifitorLocal benifitor=EJBFactory.getBenifitor();
BenifitorVO benifitor_vo = new BenifitorVO();
benifitor_vo.setBook_code(new Integer(1));
benifitor_vo.setCust_id((Integer)contractMap.get("CUST_ID"));
benifitor_vo.setProduct_id(null);

List ben_list = benifitor.QueryBenifitor(benifitor_vo);
Map ben_map = new HashMap();
String period_unit = "";//租赁类型1短期租赁2长期租赁
for(int i=0; i<ben_list.size(); i++)
{
	ben_map = (Map)ben_list.get(i);
	if(ben_map.get("TO_AMOUNT") != null)
			to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT"))));	
	if(ben_map.get("EXCHANGE_AMOUNT") != null)
		 exchange_amount = exchange_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("EXCHANGE_AMOUNT"))));	
	if(ben_map.get("BACK_AMOUNT") != null)
		 back_amount = back_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BACK_AMOUNT"))));	
	if(ben_map.get("BEN_AMOUNT") != null)
			 ben_amount = ben_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT"))));	
}
benifitor.remove();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
function op_check(){
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function showBenifiter(contract_id) {
   location.href = "benifiter_check.jsp?from_flag_benifitor=1&contract_id=" + contract_id + '&page=<%//=sPage%>&pagesize=' + document.theform.pagesize.value;
}

/*客户信息*/
function getMarketingCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo_details_view.jsp?cust_id=' + cust_id + '&readonly=1' ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');	
}
//审核双录
function tochekvideo(contractid){
	location="<%=request.getContextPath()%>/video/videorecording_check_info.jsp?from=contract&contract_id="+contractid;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="subscribe_check_action.jsp">
<input name="serial_no" type="hidden" value="<%=serial_no%>">
<input name="product_id" type="hidden" value="<%=query_product_id%>">
<input name="contract_bh" type="hidden" value="<%=Utility.trimNull(contract_bh)%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
					<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
						<tr>
							<td class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>
						</tr>
					</TABLE>
					<br/>
					<table border=0 cellSpacing=0 cellPadding=3 width="100%" class="product-list">
						<tr>
							<td align=right><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
							<td><INPUT class=edline value='<%=Utility.trimNull(custMap.get("CUST_NO"))%>' readOnly name=cust_no></td>
							<td align=right><%=LocalUtilis.language("class.customerName",clientLocale)%> :</TD><!--客户名称-->
							<td>
								<input class=edline value='<%=Utility.trimNull(custMap.get("CUST_NAME"))%>'  readOnly size="25" name=cust_name>
								<button type="button"  class="xpbutton3" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('<%=Utility.trimNull(custMap.get("CUST_ID"))%>');"><%=LocalUtilis.language("message.viewInfo",clientLocale)%> </button>
							</td>
						</tr>
						<tr>
							<td align=right><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</TD><!--证件类型-->
							<td><input class=edline value='<%=Utility.trimNull(custMap.get("CARD_TYPE_NAME"))%>'  readOnly name=card_type_name></td>
							<td align=right><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</TD><!--证件号码-->
							<td><input class=edline value='<%=Utility.trimNull(custMap.get("CARD_ID"))%>' readOnly size=30 name=card_id></td>
						</tr>
						<tr>
<%-- 							<td align="right"><%=LocalUtilis.language("class.current_money",clientLocale)%> :</td><!--存量金额--> --%>
<%-- 							<td><input readonly name="current_money" size="20" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(custMap.get("TOTAL_MONEY")),new BigDecimal(0)))%>" class="edline"></td> --%>
							<td align="right"><%=LocalUtilis.language("class.total_current_money",clientLocale)%> :</td><!--存续金额-->
							<td><input readonly name="current_money" size="20" value="<%=Format.formatMoney(to_amount)%>" class="edline"></td>
							<td align="right"><%=LocalUtilis.language("message.lastRgDate",clientLocale)%> :</td><!--最近购买时间-->
							<td><input readonly name="last_rg_date" size="20" value="<%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(custMap.get("LAST_RG_DATE")),new Integer(0)))%>" class="edline"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
							<td><input readonly name="cust_type_name" size="20" value="<%=Utility.trimNull(custMap.get("CUST_TYPE_NAME"))%>" class="edline"></td>
							<td align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> :</td><!--客户级别-->
							<td><input readonly name="cust_level_name" size="20" value="<%=Utility.trimNull(custMap.get("CUST_LEVEL_NAME"))%>" class="edline"></td>		
						</tr>	
						<tr>
							<td align="right">首次认购时间 :</td><!--首次认购时间-->
							<td><input readonly name="cust_type_name" size="20" value="<%=Argument.getFirstRGTime(contract_vo)%>" class="edline"></td>
						</tr>
						<tr>
							<td align="right">合同联系人 :</td>
							<td colspan="3"><input readonly name="cust_type_name" size="25" value="<%=Utility.trimNull(contractMap.get("HT_CUST_NAME"))%>" class="edline">
								&nbsp;&nbsp;&nbsp;&nbsp;电话 : <input readonly name="cust_type_name" size="20" value="<%=Utility.trimNull(contractMap.get("HT_CUST_TEL"))%>" class="edline">
								&nbsp;&nbsp;&nbsp;&nbsp;地址 : <input readonly name="cust_type_name" size="50" value="<%=Utility.trimNull(contractMap.get("HT_CUST_ADDRESS"))%>" class="edline">
							</td>
						</tr>	
						<tr>
						<td colspan="4"><hr size="1"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.constractBH",clientLocale)%> :</td><!--合同序号-->
							<td><INPUT readonly class="edline" name="contract_bh" size="20" value="<%=Utility.trimNull(contractMap.get("CONTRACT_BH"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
							<td ><input readonly class="edline" name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"))%>"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
							<td><input readonly class="edline" name="product_name" size="40" readonly value="<%=Utility.trimNull(contractMap.get("PRODUCT_NAME"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.preCode2",clientLocale)%> :</td><!-- 预约编号 -->
							<td><input readonly class="edline" name="pre_code" size="20" value="<%=Utility.trimNull(contractMap.get("PRE_CODE"))%>"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--预约金额-->
							<td><input readonly class="edline" name="pre_money" size="20" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("PRE_MONEY")),new BigDecimal(0)))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.rg_money",clientLocale)%> :</td><!--认购金额-->
							<td><input readonly class="edline" name="rg_money" size="20" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("RG_MONEY")),new BigDecimal(0)))%>"> <span id="rg_money_cn" class="span">(<%=Utility.numToChinese(Utility.trimNull(contractMap.get("RG_MONEY")))%>)</span></td>
						</tr>
						<tr>	
							<td align="right"><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--银行名称-->
							<td><input readonly class="edline" name="bank_name" size="40" value="<%=Utility.trimNull(contractMap.get("BANK_NAME"))%><%=Utility.trimNull(contractMap.get("BANK_SUB_NAME"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--缴款方式-->
							<td><input readonly class="edline" name="jk_type_name" size="20" value="<%=Utility.trimNull(contractMap.get("JK_TYPE_NAME"))%>">
							</td>			
						</tr>
						<tr>	
							<td align="right">开户行所在地 :</td><!--开户行所在地-->
							<td><input readonly class="edline" name="bank_province" size="40" value="<%=Utility.trimNull(bank_province)%><%=Utility.trimNull(bank_city)%>"></td>
							<td align="right">推荐人 :</td><!--推荐人-->
							<td><input readonly class="edline" name="recommend_man" size="20" value="<%=Argument.getInturstOperatorName(recommend_man) %>">
							</td>			
						</tr>
						<%if(Utility.parseInt(Utility.trimNull(productMap.get("OPEN_FLAG")),new Integer(0)).intValue()==1){%>
						<tr>				
							<td align="right"><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :</td><!-- 扣缴方式 -->
							<td >		
								<input readonly class="edline" name="fee_jk_type" size="20" value="<%=Argument.getFeeJkTypeName(contractMap.get("FEE_JK_TYPE")==null?0:((Integer)contractMap.get("FEE_JK_TYPE")).intValue())%>">				
							</td>			
							<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--收益分配方式-->
							<td>
								<select size="1" disabled name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
									<%=Argument.getBonus_flag(Utility.parseInt(Utility.trimNull(contractMap.get("BONUS_FLAG")),new Integer(0)))%>
								</select>		
							</td>				
						</tr>
						<%}%>
						<tr>	
							<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!-- 银行帐号 -->
							<td>
								<input readonly class="edline" name="bank_acct" size="40" value="<%=Utility.ShowBankAcct(Utility.trimNull(contractMap.get("BANK_ACCT")))%>">
							</td>
							<td align="right"></td>
							<td>								
							</td>			
						</tr>
						<tr>	
							<td align="right">银行账户名称 :</td>
							<td>
								<input readonly class="edline" type="text" size="30" value="<%=Utility.trimNull(contractMap.get("GAIN_ACCT"))%>">
							</td>
							<td align="right"><%=LocalUtilis.language("class.validPeriod",clientLocale)%> :</td><!--合同期限-->
							<td>
								<input readonly class="edline" type="text" name="valid_period" size="20" value="<%=Utility.trimNull(contractMap.get("VALID_PERIOD"))%>"><%=Utility.trimNull(Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0))))%>
							</td>
						</tr>
						<tr>	
							<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
							<td>
								<input readonly class="edline" name="service_man" size="20" value="<%=Utility.trimNull(Argument.getOpName((Integer)contractMap.get("SERVICE_MAN")))%>">
							</td>
							<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--合同销售人员-->
							<td>
								<input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(Argument.getOpName((Integer)contractMap.get("LINK_MAN")))%>">
							</td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--签署日期-->
							<td>
								<input readonly class="edline" name="qs_date" size="20" value="<%=Utility.trimNull(Format.formatDateCn((Integer)contractMap.get("QS_DATE")))%>">
							</td>
							<td align="right"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--缴款日期-->
							<td><input readonly class="edline"  size="40" value="<%=Utility.trimNull(Format.formatDateCn((Integer)contractMap.get("JK_DATE")))%>"></td>
						</tr>
						<tr>
							<td  width="100px" align="right">渠道名称 :</td> 
							<td><input readonly class="edline" value="<%=Argument.getDictContent(channel_type)%>-<%=Argument.getChannel(r_channel_id)%>"/></td>
							<td align="right">渠道费用 :</td><!--渠道来源-->
							<td><input readonly class="edline" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("MARKET_MONEY")),new BigDecimal(0)))%>"/></td>
						</tr>
						<tr>
							<td align="right">受益优先级 :</td>
							<td>					
								<select name="prov_flag" style="WIDTH: 100px" disabled="disabled" onkeydown="javascript:nextKeyPress(this)">
									<%=Argument.getProductProvFlag(product_id,sub_product_id,prov_flag)%>
								</select>
							</td>
							<td align="right">*<%=LocalUtilis.language("class.incomeLevel",clientLocale)%> :</td><!-- 收益级别 -->
							<td>
								<select name="prov_level" style="width:125px;" disabled>
									<%=Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level)%>
								</select>	
							</td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.channelCooperation",clientLocale)%> :</td><!-- 渠道合作方式 -->
							<td>
								<input readonly class="edline" value="<%=Argument.getChanneCooperationName(channel_cooperation)%>"/>
							</td>
							<td align="right">渠道附加信息 :</td>
							<td><INPUT class="edline" readonly="readonly" TYPE="text" NAME="channel_type_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_MEMO"))%>"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--合同推介地-->
							<td><input readonly class="edline" name="bank_acct" size="40" value="<%=Utility.trimNull(contractMap.get("CITY_NAME"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--备注-->
							<td><input readonly class="edline" type="text" name="summary" value="<%=Utility.trimNull(contractMap.get("SUMMARY"))%>" size="40"></td>
						</tr>
						<tr>
							<td align="right">用款方关联 :</td>
							<td><input readonly class="edline" name="is_ykgl" size="40" value="<%if(Utility.trimNull(contractMap.get("IS_YKGL")).equals("1")){out.print("是");}else{out.print("否");}%>"></td>
							<td align="right">合同预计收益率 :</td><!--合同预计收益率-->
							<td><input readonly class="edline" type="text" name="xthtyjsyl" value="<%=Utility.trimNull(contractMap.get("XTHTYJSYL"))%>" size="40"></td>
						</tr>
						<tr>
							<td align="right">预期收益率 :</td>
							<td colspan="3"><input readonly="readonly" class="edline" type="text" name="exp_rate" 
									value="<%=(contractMap.get("EXPECT_ROR_LOWER")==null?"":((BigDecimal)contractMap.get("EXPECT_ROR_LOWER")).multiply(new BigDecimal(100)).setScale(2).toString())+"% - "
												+(contractMap.get("EXPECT_ROR_LOWER")==null?"":((BigDecimal)contractMap.get("EXPECT_ROR_UPPER")).multiply(new BigDecimal(100)).setScale(2).toString())+"%"%>" size="40"></td>
						</tr>
						<tr>
							<td align="right">合同是否寄回 :</td>
							<td colspan="3">
								<select size="1" name="if_mail" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
									<option value="0" selected>请选择</option>
									<option value="1" >是</option>
									<option value="2" >否</option>
								</select>
							</td>
						</tr>
						<tr id="reader1">
	          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>附件:<%}%></td>
	            <td colspan="3">
				<%
				Iterator attachment_it = attachmentList.iterator();
				HashMap attachment_map = new HashMap();
	            while(attachment_it.hasNext()){
	            	attachment_map = (HashMap)attachment_it.next();
	            %>
	            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
	            	<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
	            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave" 
	            		onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">删除</button>
	            	</div><br>
				<%}	%>
	            </td>	
	         </tr>
	         <tr><td align="right">双录视频 :</td>
	         	<td colspan="3">
	         	<%if (!vdmap.isEmpty()){ %>
	         		<embed src="<%=Utility.trimNull(vdmap.get("SaveName")) %>" autostart="true" loop="true" width="200" height="150" />
	         	<%}else{%>
	         		<span style="color:#ff0000">双录视频未上传！</span>
	         	<%} %>
	         	</td>
	         </tr>
	         <%if (checkflag_vide.intValue()!=0){ %>
	         <tr><td align="right">双录审核状态 :</td>
	         	<td><%=checkflagName_vide %>
	         		<%if (checkflag_vide.intValue()!=2){ %>
	         			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:tochekvideo(<%=serial_no.intValue() %>);">进入审核</a>
	         		<%} %>
	         	</td>
	         	<td align="right">双录审核时间 :</td>
	         	<td><%=Utility.trimNull(vdmap.get("CheckTime")) %></td>
	         </tr>
	         <%}if (checkflag_vide.intValue()==3){ %>
	         <tr><td align="right">双录审核说明 :</td>
	         	<td colspan="3"><span style="color:#ff0000"><%=Utility.trimNull(vdmap.get("CheckComment")) %></span></td>
	         </tr>
	         <%} %>
			 <tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
                            <!-- 审核通过 -->
							<button type="button"  class="xpbutton4"  name="btnCheck" title='<%=LocalUtilis.language("message.check",clientLocale)%>' onclick="javascript:op_check();"><%=LocalUtilis.language("class.auditBy2",clientLocale)%> </button>&nbsp;&nbsp;&nbsp; 
							<!--返回-->
                            <button type="button"  class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:location='subscribe_check_list.jsp?product_id=<%=product_id%>&contract_bh=<%=contract_bh%>'"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</TABLE>
				</TD>
			</TR>
		</TABLE>
	</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>