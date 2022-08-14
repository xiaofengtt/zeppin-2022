<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),new Integer(0));
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));

input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;
Integer pre_num = new Integer(0);

Integer cust_id = new Integer(0);
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String card_id = "";
Integer card_type = new Integer(0);
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
String post_code = "";
String post_address = "";
String summary = "";
BigDecimal reg_money = new BigDecimal(0);
BigDecimal pre_money2  = new BigDecimal(0);
Integer reg_data = new Integer(0);
Integer reg_valid_days = new Integer(0);
String legal_man = "";
String contact_man = "";
Integer service_man = new Integer(0);
Integer service_man_value = new Integer(0);
Integer productid = new Integer(0);
Integer sub_product_id = new Integer(0);
BigDecimal pre_money = new BigDecimal(0);
Integer link_man = new Integer(0);
Integer valid_days = new Integer(0);
String pre_type = "";
String cust_type_name = "";
String invest_type = "";
String pre_code = "";
Integer preproduct_id = new Integer(0);
String product_name = "";
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String per_code = Utility.trimNull(request.getParameter("per_code"));

PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();

CustomerLocal customer = EJBFactory.getCustomer();
if(request.getMethod().equals("POST")){
	pre_vo.setSerial_no(serial_no);
	pre_vo.setInput_man(input_operatorCode);
	preContract.checkPreContract(pre_vo);
	bSuccess = true;
} else {
	//预约信息数据集
	pre_vo.setSerial_no(serial_no);	
	List list = preContract.load(pre_vo);	
	
	if(list.size()>0){
		Map map = (Map)list.get(0);
		
		productid = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
		preproduct_id = Utility.parseInt(Utility.trimNull(map.get("PREPRODUCT_ID")),new Integer(0));
		product_name =(String)map.get("PRODUCT_NAME");
		sub_product_id = Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0));
		cust_id  = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), null);
		link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
		valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));
		pre_type = Utility.trimNull(map.get("PRE_TYPE"));
		pre_num = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
		summary = Utility.trimNull(map.get("SUMMARY"));
		invest_type = Utility.trimNull(map.get("PRE_TYPE"));
		pre_code = Utility.trimNull(map.get("PRE_CODE"));
		preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
		expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
		customer_cust_source = Utility.trimNull(map.get("CUST_SOURCE"));
		
		//再查找客户信息并赋值给相关字段
		CustomerVO c_vo = new CustomerVO();
		c_vo.setCust_id(cust_id);
		c_vo.setInput_man(input_operatorCode);	
		List rsList_single = customer.listByControl(c_vo);
		
		if(rsList_single.size()>0){			
			Map map_single = (Map)rsList_single.get(0);
		
			cust_name = Utility.trimNull(map_single.get("CUST_NAME"));
			service_man	= Utility.parseInt(Utility.trimNull(map_single.get("SERVICE_MAN")),new Integer(0));
			card_id = Utility.trimNull(map_single.get("CARD_ID"));
			o_tel =  Utility.trimNull(map_single.get("O_TEL"));
			h_tel =  Utility.trimNull(map_single.get("H_TEL"));
			mobile  =  Utility.trimNull(map_single.get("MOBILE"));
			bp  =  Utility.trimNull(map_single.get("BP"));
			post_code =  Utility.trimNull(map_single.get("POST_CODE"));
			post_address =  Utility.trimNull(map_single.get("POST_ADDRESS"));	
			card_type  = Utility.parseInt(Utility.trimNull(map_single.get("CARD_TYPE")),new Integer(0));
			cust_type = Utility.parseInt(Utility.trimNull(map_single.get("CUST_TYPE")),new Integer(0));		
		}
	}
}

//认购金额中文处理
String reg_money_cn = pre_money.intValue()!=0? reg_money_cn =  Utility.numToChinese(pre_money.toString()): "";

preContract.remove();
customer.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakEdit",clientLocale)%> </TITLE><!--预约编辑-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
var bSuccess = <%=bSuccess%>;
var preproduct_id = <%=preproduct_id%>;

window.onload = function(){
		showPreinfo();
	
		if (bSuccess) {		
			sl_alert("审核通过 ！");//预约信息 修改成功
			location.href = "bespeak_check_list.jsp";
		}
	};

/*审核*/
function check(){
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();
	}		
}

/*返回*/
function CancelAction(){
	location.href="bespeak_check_list.jsp";
}

/*修改已预约数目 已预约金额*/
function showPreinfo(){
	var product_id = document.theform.product_id.value;	
	utilityService.getPreInfoById(preproduct_id, product_id,
								{callback: function(data){
												document.theform.fact_pre_num.value = data[0];
												document.theform.fact_pre_money_view.value = formatNum(data[1]);
												document.theform.pre_end_date.value = data[2];
												}
								});
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" >
<input type="hidden" name="pre_end_date" value="">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type='hidden' name="customer_cust_type"  value="<%=cust_type%>"/>
<input type='hidden' name="customer_card_type" value="<%=card_type%>"/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("menu.bespeak",clientLocale)%> </b></font><!--销售管理>>预约-->
	<hr noshade color="#808080" size="1">
</div>

<!--客户选择-->
<div align=left>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
		<tr>
			<td align="left"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td>	<!--客户信息-->					
		<tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
			</td>
			<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
			<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getOpName(service_man))%>">&nbsp;&nbsp;&nbsp;
			</td>
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
			<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%= cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
			<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= card_id%>" size="25"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--家庭电话-->
			<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= h_tel%>" size="20"></td>
			<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
			<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= o_tel%>" size="20"></td>
			</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--手机1-->
			<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= mobile%>" size="20"></td>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--手机2-->
			<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= bp%>" size="20"></td>
		
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
			<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%= post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
			<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%= post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
		</tr>
		
		<tr>
			<td colspan="4"><hr size="1"></td>
		</tr> 
		<!--预约信息-->
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
			<td>
				<input type="hidden" name="product_id" value="<%=productid%>"/>
				<input readonly class="edline" name="product_name" size="60" value="<%=Utility.trimNull(product_name)%>"/>	
			</td>
			<td align="right"><%=LocalUtilis.language("class.preCode2",clientLocale)%> :</td><!--预约编号-->
			<td><input  readonly class="edline" name="per_code" size="27" value="<%=pre_code%>"/></td>				
		</tr>
		<tr id="sub_product_id" style="display:<%=sub_product_id.intValue()==0?"none":""%>">
			<td align="right">子产品 :</td>
			<td id="tdSubProductId" colspan="3">
				<input type="hidden" name="sub_product_id" value="<%=sub_product_id%>"/>
				<input readonly class="edline" name="product_name" size="60" 
					value="<%=Utility.trimNull(Argument.getSubProductName(productid, sub_product_id))%>"/>			
			</td>			
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum3",clientLocale)%> :</td><!--已预约份数-->
			<td><input readonly class="edline" name="fact_pre_num" onkeydown="javascript:nextKeyPress(this)" size="10" value=""></td>
			<td align="right"><%=LocalUtilis.language("class.factPreMoney",clientLocale)%> :</td><!--已预约总金额-->
			<td><input readonly class="edline" name="fact_pre_money_view" onkeydown="javascript:nextKeyPress(this)" size="27" value=""><input type="hidden" name="fact_pre_money" onkeydown="javascript:nextKeyPress(this)" size="27" value=""></td>
		</tr>		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--预约金额-->
			<td><input name="pre_money" size="27" readonly class="edline" value="<%=Format.formatMoney(pre_money)%>">
				<span id="pre_money_cn" class="span">&nbsp;</span>
			 <%if (pre_money!= null){%>
				<span id="pre_money_cn" class="span">(<%=reg_money_cn%>)</span>
		    <%}else {%>
    			<span id="pre_money_cn" class="span">&nbsp;</span><!--万-->
			<%}%>
			</td><!-- 万 -->
			<td align="right"><%=LocalUtilis.language("class.preNum2",clientLocale)%> :</td><!--预约份数-->
			<td>
				<input name="pre_num" readonly class="edline" value="<%=pre_num%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--预约方式-->
			<td>
				<input name="pre_type" readonly class="edline" value="<%if("111201".equalsIgnoreCase(invest_type)){out.print("网上预约");}
																		else if("111202".equalsIgnoreCase(invest_type)){out.print("电话预约");}
																		else if("111203".equalsIgnoreCase(invest_type)){out.print("现场预约");}%>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.validDays",clientLocale)%> :</td><!--有效天数-->
			<td><input name="valid_days" size="27" readonly class="edline" value="<%=valid_days%>"></td>
		</tr>
		<tr>
			<td align="right">预计认购日期:</td><!--预计认购日期-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker"  readonly class="edline" 
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
			</td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--客户来源-->
			<td>
				<input name="customer_cust_source" readonly class="edline" value="<%if("111001".equalsIgnoreCase(customer_cust_source)){out.print("热线客户");}
																				else if("111002".equalsIgnoreCase(customer_cust_source)){out.print("网络客户");}
																				else if("111003".equalsIgnoreCase(customer_cust_source)){out.print("现场咨询客户");}
																				else if("111004".equalsIgnoreCase(customer_cust_source)){out.print("销售人员客户");}	
																				else if("111005".equalsIgnoreCase(customer_cust_source)){out.print("业务人员客户");}
																					%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> :</td><!--联系人-->
			<td >
				<input name="link_man" readonly class="edline" value="<%=Argument.getManager_Name(link_man)%>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--预约日期-->
			<td><INPUT TYPE="text" NAME="pre_date_picker"readonly class="edline" 
			<%if(preDate==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
			<td colspan="3"><textarea rows="3" name="summary" readonly cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:check();">审核通过</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--返回-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>