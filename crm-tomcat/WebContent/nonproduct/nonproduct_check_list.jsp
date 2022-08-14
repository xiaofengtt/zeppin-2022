<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*, enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
NonProductVO vo = new NonProductVO();
NonProductLocal nonproduct = EJBFactory.getNonProductLocal();
CustomerVO cust_vo = new CustomerVO();
CustomerLocal customer = EJBFactory.getCustomer();//客户

//获得页面传递变量
Integer q_nonproductId = Utility.parseInt(Utility.trimNull(request.getParameter("q_nonproductId")),new Integer(0));		//编号
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));									//非信托产品名称
String q_investmentManager = Utility.trimNull(request.getParameter("q_investmentManager"));								//投资管理人
String q_partnerCustName = Utility.trimNull(request.getParameter("q_partnerCustName"));	//合伙人企业ID
String q_status = Utility.trimNull(request.getParameter("q_status"));													//状态
Integer q_checkFlag = Utility.parseInt(Utility.trimNull(request.getParameter("q_checkFlag")),new Integer(0));			//审核标志1未审核2已审核
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer customer_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));

String q_investment_direction = "";
String q_investment_direction_name = "";
Integer q_valid_priod_unit = new Integer(0);
Integer q_valid_priod = new Integer(0);
BigDecimal q_expectMoney = new BigDecimal(0);
BigDecimal q_expect_rate1 = new BigDecimal(0);
BigDecimal q_expect_rate2 = new BigDecimal(0);
Integer q_startDate = new Integer(0);
Integer q_endDate = new Integer(0);
Integer q_partnerCustId = new Integer(0);
BigDecimal q_face_money = new BigDecimal(0);		//实际产品金额

//客户信息
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
Integer card_type = new Integer(0);
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;
//------------------------------------
String card_id = "";
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
//------------------------------------
String h_card_id = "";
String h_cust_tel = "";
String h_o_tel = "";
String h_h_tel = "";
String h_mobile= "";
String h_e_mail = "";
String h_bp = "";
//------------------------------------
String re_contract_bh = "";
String nonproductID = "";

boolean bSuccess = false;
Map map = null;
Map map_cust = null;
List rsList_cust = null;

if(q_nonproductId.intValue()!= 0){
	vo.setNonproduct_id(Utility.parseInt(q_nonproductId, new Integer(0)));
	vo.setNonproduct_name(Utility.trimNull(q_nonproductName));
	vo.setInvestment_manager(Utility.trimNull(q_investmentManager));
	vo.setPartner_cust_name(q_partnerCustName);
	vo.setStatus(Utility.trimNull(q_status));
	vo.setCheck_flag(Utility.parseInt(q_checkFlag, new Integer(0)));
	List list = nonproduct.load(vo, input_operatorCode);
	map = (Map)list.get(0);
}

if(map != null){
	q_nonproductId = Utility.parseInt(Utility.trimNull(map.get("NONPRODUCT_ID")),
			new Integer(0));
	q_nonproductName = Utility.trimNull(map.get("NONPRODUCT_NAME"));
	q_investmentManager = Utility.trimNull(map.get("INVESTMENT_MANAGER"));
	q_investment_direction = Utility.trimNull(map.get("INVESTMENT_DIRECTION"));
	q_investment_direction_name = Utility.trimNull(map.get("INVESTMENT_DIRECTION_NAME"));
	q_valid_priod_unit = Utility.parseInt(Utility.trimNull(map.get("VALID_PRIOD_UNIT")),
			new Integer(0));
	q_valid_priod = Utility.parseInt(Utility.trimNull(map.get("VALID_PRIOD")),
			new Integer(0));
	q_expectMoney = Utility.parseDecimal(Utility.trimNull(map.get("EXPECT_MONEY")),
			new BigDecimal(0), 2, "1");
	q_expect_rate1 = Utility.parseDecimal(Utility.trimNull(map.get("EXPECT_RATE1")),
			new BigDecimal(0), 4, "100");
	q_expect_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXPECT_RATE2")),
			new BigDecimal(0), 4, "100");
	q_face_money = Utility.parseDecimal(Utility.trimNull(map.get("FACE_MONEY")),new BigDecimal(0));
	q_partnerCustId = Utility.parseInt(Utility.trimNull(map.get("PARTNER_CUST_ID")),
			new Integer(0));
	q_startDate = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),
			new Integer(0));
	q_endDate = Utility.parseInt(Utility.trimNull(map.get("END_DATE")),
			new Integer(0));
}

//客户信息显示
if(cust_id.intValue()>0){
	
	//客户单个值		
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(cust_vo);
			
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
	}

	if(map_cust!=null){		
		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		//保密信息
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		
		h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
		h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
		h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
		h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
		h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
		h_bp = Utility.trimNull(map_cust.get("H_BP"));	
	}		
}

%>
<html>
<head>
<title><%=LocalUtilis.language("menu.nonProductModi",clientLocale)%> </title>
<!--修改非信托产品信息-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
/*审核通过*/
function op_check()
{
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();
	}
	
}
/*取消*/
function CancelAction(){
	location = 'noproduct_check.jsp'
}

</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="nonproduct_check_action.jsp" ><input type=hidden name="is_dialog" value="1">
<!--客户信息-->
<input type="hidden" name="cust_name" value="<%=cust_name%>">
<input type="hidden" name="cust_type" value="<%=cust_type%>">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>">
<input type="hidden" name="card_type" value="<%=card_type%>">
<input type="hidden" name="card_type_value" value="<%=card_type%>">
<input type="hidden" name="q_nonproductId" value=<%=q_nonproductId%>>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.nonProductModi",clientLocale)%> </b></font></td>
	</tr>
	
</table>
<br/>
<p class="title-table"><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></p><!--产品信息-->
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="table-popup">
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> : </td><!--名称-->
		<td align="left" ><input  class=edline readOnly name="q_nonproductName" size="15" value="<%=q_nonproductName %>"></td>
		<td align="right"><%=LocalUtilis.language("class.factMoney",clientLocale)%> : </td><!--实际产品金额-->
		<td align="left" ><input  class=edline readOnly name="q_face_money" size="15" value="<%=q_face_money %>"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.toward",clientLocale)%> :</td><!--投向-->
		<td><input  class=edline readOnly name="q_investment_direction_name" size="15" value="<%=q_investment_direction_name %>">
		</td>
		<td align="right" noWrap><%=LocalUtilis.language("class.period",clientLocale)%> :</td><!--期限-->
		<td align="left">
			<input  class=edline readOnly name="q_validPriod" value="<%=Utility.trimNull(q_valid_priod) %>" />
			<select disabled size="1" name="q_validPriodUnit" onkeydown="javascript:nextKeyPress(this)" style="width: 90px" >
			<%=Argument.getPeriodValidUnitOptions(q_valid_priod_unit)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><b>*<%=LocalUtilis.language("class.startTime",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--开始时间-->
		<td>
			<INPUT  class=edline readOnly NAME="startDate" value="<%=q_startDate%>">
		</td>

		<td align="right"><b>*<%=LocalUtilis.language("class.endTime",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--结束时间-->
		<td>
			<INPUT  class=edline readOnly NAME="endDate" value="<%=q_endDate%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.expectMoney",clientLocale)%> : </td><!--预期产品金额-->
		<td align="left" ><input class=edline readOnly name="q_expectMoney" size="15" value="<%=q_expectMoney %>" ></td>
		<td align="right"><%=LocalUtilis.language("class.investmentManager",clientLocale)%> : </td><!--投资管理人-->
		<td align="left" ><input class=edline readOnly name="q_investmentManager" size="15" value="<%=q_investmentManager %>" ></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.exceptRateLower",clientLocale)%> : </td><!--预期收益率下限-->
		<td align="left" ><input class=edline readOnly name="q_expectRate1" size="15" value="<%=q_expect_rate1 %>%" ></td>
		<td align="right"><%=LocalUtilis.language("class.exceptRateLimit",clientLocale)%> : </td><!--预期收益率上限-->
		<td align="left" ><input class=edline readOnly name="q_expectRate2" size="15" value="<%=q_expect_rate2 %>%" ></td>
	</tr>
</table>
<p class="title-table"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></p><!--客户信息-->
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="table-popup">
	<!--客户选择-->
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--客户名称-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>   	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td>  <!--公司电话-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--家庭电话-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td> <!--电子邮件-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=e_mail%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--关联方标志-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%=is_link%>" <%if(is_link!=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button" class="xpbutton4" id="btnCheck" name="btnCheck" onclick="javascript:op_check();"><%=LocalUtilis.language("class.auditBy2",clientLocale)%> </button>
		&nbsp;&nbsp;<!--审核-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
<%//@ include file="/includes/foot.inc" %>
</body>
</html>
