<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ҳ�洫�ݱ���
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer sub_productId = Utility.parseInt(request.getParameter("sub_productId"),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),null);;
Integer intrust_flag1 = Utility.parseInt(request.getParameter("intrust_flag1"),new Integer(0));

//��Ϣ��ʾ��������
Integer qualified_flag = new Integer(1);
Integer qualified_num = new Integer(0);
Integer asfund_flag = new Integer(1);
Integer gain_flag = new Integer(1);
Integer open_gain_flag = new Integer(2);
BigDecimal qualified_money = new BigDecimal(0);
Integer is_bank_consign = new Integer(2); //�Ƿ����д��� 1�ǡ�2�� Ĭ�Ϸ�
String risk_level = "";//���յȼ�
BigDecimal jg_min_subamount = new BigDecimal(0); 
BigDecimal gr_min_subamount = new BigDecimal(0); 
BigDecimal jg_min_bidsamount = new BigDecimal(0); 
BigDecimal gr_min_bidsamount = new BigDecimal(0); 
BigDecimal jg_min_appbidsamount = new BigDecimal(0); 
BigDecimal gr_min_appbidsamount = new BigDecimal(0); 
BigDecimal min_redeem_vol = new BigDecimal(0); 
Integer large_redeem_flag = new Integer(2);
Integer large_redeem_condition = new Integer(1);
BigDecimal large_redeem_percent = new BigDecimal(0); 
Integer large_redeem_deal = new Integer(1);
Integer coerce_redeem_flag = new Integer(2);
String styleStr = "none" ;
if(qualified_flag.intValue()==1){
	styleStr = "" ;
}
String productCode = "";
String productName = "";
String productJC = "";
BigDecimal preMoney = new BigDecimal(0);
Integer preNum = new Integer(0);
BigDecimal minMoney = new BigDecimal(0);
Integer preStartDate = new Integer(0);
Integer preEndDate = new Integer(0);
Integer periodUnit = new Integer(0);
String team_name = "";
BigDecimal quota_money = new BigDecimal(0);
String valid_period = "";
BigDecimal pre_max_rate = new BigDecimal(0);
String pre_code = "";
String pre_start_time = "";
String pre_end_time = "";
Integer check_man = new Integer(0);
List markList = null;
Map markMap = null;

//��ö���
ProductVO vo = new ProductVO();
ProductLocal product = EJBFactory.getProduct();

//ҳ�����
List listProduct = null;
Map map = null;
List listIssue = null;
Map map_issue = null;
List list_crm_product = null;
Map map_issue_crm = null;
boolean bSuccess = false ;
String error_mess = "";
Integer sub_flag = new Integer(0);//�Ӳ�Ʒ��־ 0��1��

if(request.getMethod().equals("POST")){
	//���ͨ��
	vo.setProduct_id(productId);
	vo.setSub_product_id(sub_productId);
	vo.setSale_status(new Integer(2));
	vo.setInput_man(input_operatorCode);
	try{
		product.modiCRMProductCheck(vo);
		bSuccess = true ;
		error_mess = "��˳ɹ�";
	}catch(Exception e){
		error_mess = e.getMessage() ;
	}
}
if(!request.getMethod().equals("POST")){
	//productId������0�����ѯ��ѯ������Ʒ��Ϣ
	if(productId.intValue()!= 0){
		vo.setProduct_id(productId);
		
		listProduct = product.load(vo);
		if(listProduct.size() >0){
			map = (Map)listProduct.get(0);
		}
		
		if(intrust_flag1.intValue() == 2){//�Ƿ�Ϊ���ϲ�Ʒ
			listIssue = product.queryProductLimit(vo);
			if(listIssue.size() >0){
				map_issue = (Map)listIssue.get(0);
			}
		}
		
		vo.setBook_code(new Integer(1));
		vo.setStart_date(new Integer(0));
		vo.setEnd_date(new Integer(0));
		vo.setProduct_status("");
		vo.setCheck_flag(new Integer(0));
		vo.setOpen_flag(new Integer(0));
		vo.setIntrust_flag1(new Integer(0));
		vo.setProduct_id(productId);
		vo.setProduct_name("");
		vo.setProduct_code("");
		vo.setSale_status(new Integer(0));
		vo.setInput_man(input_operatorCode);
		list_crm_product = product.listCrmProduct(vo);
		if(list_crm_product.size()>0){
			map_issue_crm = (Map)list_crm_product.get(0); 
		}

		//��Ʒ����������Ϣ
		markList = product.queryMarketTrench(vo);
	}
	
	//��Ʒ��Ϣ
	if(map!= null){
		productCode = Utility.trimNull(map.get("PRODUCT_CODE"));
		productName = Utility.trimNull(map.get("PRODUCT_NAME"));
		productJC = Utility.trimNull(map.get("PRODUCT_JC"));
		preMoney = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0),2,"1");
		preNum = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
		minMoney = Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")), new BigDecimal(0),2,"1");
		preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
		preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
		periodUnit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
		valid_period = Utility.trimNull(map.get("VALID_PERIOD"));
		pre_code =  Utility.trimNull(map.get("PRE_CODE"));	
		pre_max_rate = Utility.parseDecimal(Utility.trimNull(map_issue_crm.get("PRE_MAX_RATE")), new BigDecimal(0),4,"100");
		pre_max_rate = pre_max_rate.setScale(2,BigDecimal.ROUND_HALF_UP);
		sub_flag = Utility.parseBit(Utility.trimNull(map.get("SUB_FLAG")));
	}
	//��Ʒ������Ϣ
	if(map_issue != null){
			qualified_money = Utility.parseDecimal(Utility.trimNull(map_issue.get("QUALIFIED_MONEY")),new BigDecimal(0));
			qualified_flag = Utility.parseInt(Utility.trimNull(map_issue.get("QUALIFIED_FLAG")),new Integer(1));
			qualified_num = Utility.parseInt(Utility.trimNull(map_issue.get("QUALIFIED_NUM")),new Integer(0));
			asfund_flag = Utility.parseInt(Utility.trimNull(map_issue.get("ASFUND_FLAG")),new Integer(1)) ;
			gain_flag = Utility.parseInt(Utility.trimNull(map_issue.get("GAIN_FLAG")),new Integer(1)) ;
			open_gain_flag = Utility.parseInt(Utility.trimNull(map_issue.get("OPEN_GAIN_FLAG")),new Integer(2)) ;
			is_bank_consign = Utility.parseInt(Utility.trimNull(map_issue.get("IS_BANK_CONSIGN")),new Integer(2));
			jg_min_subamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("JG_MIN_SUBAMOUNT")),new BigDecimal(0));
			gr_min_subamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("GR_MIN_SUBAMOUNT")),new BigDecimal(0));
			jg_min_bidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("JG_MIN_BIDSAMOUNT")),new BigDecimal(0));
			gr_min_bidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("GR_MIN_BIDSAMOUNT")),new BigDecimal(0));
			jg_min_appbidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("JG_MIN_APPBIDSAMOUNT")),new BigDecimal(0));
			gr_min_appbidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("GR_MIN_APPBIDSAMOUNT")),new BigDecimal(0));
			min_redeem_vol = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map_issue.get("MIN_REDEEM_VOL"))),new BigDecimal(0)); 
			large_redeem_flag = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_FLAG")), new Integer(2));
			large_redeem_condition = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_CONDITION")), new Integer(1));
			large_redeem_percent = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map_issue.get("LARGE_REDEEM_PERENT"))),new BigDecimal(0)); 
			large_redeem_deal = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_DEAL")), new Integer(1));
			coerce_redeem_flag = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_DEAL")), new Integer(2));
			check_man = Utility.parseInt(Utility.trimNull(map_issue_crm.get("INPUT_MAN")), new Integer(0));
			pre_start_time = Utility.trimNull(map_issue_crm.get("PRE_START_TIME"));
			pre_end_time = Utility.trimNull(map_issue_crm.get("PRE_END_TIME"));
			if(pre_start_time.length() >=5)
				pre_start_time = pre_start_time.substring(0,5);
			if(pre_end_time.length() >=5)
				pre_end_time = pre_end_time.substring(0,5);
	}
	//�鿴���յȼ�
	if(map_issue_crm != null){
		risk_level = Utility.trimNull(map_issue_crm.get("RISK_LEVEL"));
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.saleParameterSet",clientLocale)%></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language="javascript">
	var oState = {};
 
	function refreshPage()
	{
		var url ="sale_parameter.jsp?page=1&pagesize=" + document.theform.pagesize.value
						+"&team_id="+document.theform.team_id.value;
						
		location = url
	}
	
	//�鿴�Ŷӳ�Ա
	function showTeamInfo(team_name){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/marketing/base/team_member.jsp?team_id="+arguments[0];
		if(showModalDialog(url,oState, 'dialogWidth:360px;dialogHeight:350px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}	
	}
	//����
	function CancelAction(){
		window.location.href="sale_parameter_check.jsp?";
	}

window.onload = function(){
	//changeView(<%=qualified_flag.intValue() %>);
	<%if (!"".equals(error_mess)){%>
		sl_alert('<%=error_mess%>');
		window.location.href="sale_parameter_check.jsp";
	<%}%>
};

function pass(){
	if (sl_check_pass()){
		if(<%=check_man.intValue()%>==<%=input_operatorCode.intValue()%>){alert("¼���˺�����˲�����ͬ");return false;}
		disableAllBtn(true);
		document.theform.action="sale_parameter_checkAction.jsp" ;
		document.theform.submit();
		//submit֮���JS��������submit֮��Ķ�����������Ϊsubmit�Ľ����Ӱ�졣���仰˵������submit�ɹ���ʧ�ܣ������JS��䲻��ı�
		//���ԣ�����Ķ�����ȥ���ˣ���Ϊˢ�º�Ϳ�����submit�ı�����Ϣ
		//window.location.href="sale_parameter_check.jsp";
	}
}
function noPass(){
	if(<%=check_man.intValue()%>==<%=input_operatorCode.intValue()%>){alert("¼���˺�����˲�����ͬ");return false;}
	sl_alert("<%=LocalUtilis.language("message.notPass2",clientLocale)%> ");//���δͨ��
	window.location="sale_parameter_check.jsp";
}

function viewFeeRate(productId, subproductId) {
	//popWindow("<%=request.getContextPath()%>/marketing/base/fee_rate_set.jsp?readonly=true&productId="+productId+"&subproductId="+subproductId);

	var url = "<%=request.getContextPath()%>/marketing/base/fee_rate_set.jsp?readonly=true&productId="+productId+"&subproductId="+subproductId;
	showModalDialog(url,oState, 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="productId" value="<%=productId%>">
<input type="hidden" name="sub_productId" value="<%=sub_productId%>">
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>">
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<br/>
	<div align="left" >
	<p class="title-table"><font color="#215dc6"><b>��Ʒ������Ϣ</b></font></p>
	</div>
	<div>
		<table  border="0" width="100%" cellspacing="0" cellpadding="2" class="table-popup">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
				<td align="left" >
					<input type="text" name="productcode" readonly="readonly" class="edline" value="<%=productCode%>" onkeydown="javascript:setProduct(this.value);" size="30">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ����-->
				<td  align="left">
					<input type="text" name="productName" readonly="readonly" class="edline" value="<%=productName%>" onkeydown="javascript:setProduct(this.value);" size="60">
				</td>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
				<td  align="left">
					<input type="text" name="productJC" readonly="readonly" class="edline" value="<%=productJC%>" onkeydown="javascript:setProduct(this.value);" size="40">
				</td>
			</tr>
			<tr>				
				<td align="right" width="120px"><%=LocalUtilis.language("class.preMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--Ԥ�ڷ��н��-->
				<td><input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(preMoney) %>" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" size="30"> </td>							
				<td align="right" width="120px"><%=LocalUtilis.language("class.preNum",clientLocale)%> :&nbsp;&nbsp;</td><!--Ԥ�ڷ��з���-->
				<td><input type="text" name="preNum" readonly="readonly" class="edline" value="<%=preNum%>" size="40"></td>			
			</tr>
			<tr>				
				<td align="right" width="120px"><%=LocalUtilis.language("class.minMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--��ͷ��н��-->
				<td><input type="text" name="minMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(minMoney) %>" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" size="30"></td>			
			</tr>
			<tr>
				<td align="right" width="120px">ԤԼ���� :&nbsp;&nbsp;</td>
				<td><input type="text" id="pre_max_rate" name="pre_max_rate" readonly="readonly" class="edline" value="<%=pre_max_rate %>" style="text-align: left;"/>%</td>
				<td align="right" width="120px">��ͬ���ǰ׺ :&nbsp;&nbsp;</td>
				<td><input  type="text" name="pre_code" value="<%=pre_code %>" readonly="readonly" class="edline" onkeydown="javascript:nextKeyPress(this);" size="20"></td>
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> :&nbsp;&nbsp;</td><!--�ƽ���ʼ����-->
				<td><input type="text" name="preStartDate" readonly="readonly" class="edline" value="<%=Format.formatDateCn(preStartDate) %>" size="30"></td>			
	
				<td align="right" width="120px"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> :&nbsp;&nbsp;</td><!--�ƽ���ֹ����-->
				<td><input type="text" name="preEndDate" readonly="readonly" class="edline" value="<%=Format.formatDateCn(preEndDate) %>" size="40"></td>			
		
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.periodUnit",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ����-->
				<td>
				<input type="text" name="periodUnit" readonly="readonly" class="edline" value="<%if(periodUnit!=null){
				if(periodUnit.intValue()!=0){
				%><%=valid_period%><%=Argument.getProductUnitName(periodUnit)
				%><%}else{
				%><%=Argument.getProductUnitName(periodUnit)%><%}}%>" size="30"></td>	
				<td align="right" width="120px"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> :&nbsp;&nbsp;</td><!-- ���յȼ� -->
				<td >
					<SELECT name="risk_level" style="width:120px;" disabled><%=Argument.getProductRiskGrade(risk_level)%></SELECT></td> 				
			</tr>
		</table>
	<%if(intrust_flag1.intValue() == 2){ %>
	<br/>
	<div align="left">
		<font color="#215dc6"><b><%=LocalUtilis.language("intrsut.home.productrelease",clientLocale)%><%=LocalUtilis.language("message.set",clientLocale)%></b></font><!-- ��Ʒ�������� -->
	</div>
	<div style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;" class="direct-panel">
		<fieldset style="border-width: 0px; border-color: #000000; ">   
			<legend><font color="red"><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</font></legend><!-- ���漶�� -->
				<table border="0" width="100%" cellspacing="0" cellpadding="3" >
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.incomeLevelAccountingUnit",clientLocale)%>:</td><!-- ���漶����㵥λ -->
						<td width="25%">
							<SELECT name="asfund_flag" style="width:120px;" disabled><!--											
								<OPTION  value="1" <%if(asfund_flag!=null&&asfund_flag.intValue()==1){ out.print("selected");}//�����¼%>  ><%=LocalUtilis.language("class.benefitRecord",clientLocale)%></OPTION>
								<OPTION  value="2"  <%if(asfund_flag!=null&&asfund_flag.intValue()==2){ out.print("selected");}//Ͷ����%> ><%=LocalUtilis.language("class.investor",clientLocale)%></OPTION>
								<OPTION  value="3" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){ out.print("selected");}//����ͬ����%>  ><%=LocalUtilis.language("class.contractDefined",clientLocale)%></OPTION>
								--><%=Argument.getTableOptions2(3004,asfund_flag)%>
							</SELECT></td> 
						<td align="right" width="25%"><%=LocalUtilis.language("class.incomeCalcualtion",clientLocale)%>:</td><!-- ���������� -->
						<td width="25%">
							<SELECT name="gain_flag" style="width:120px;" disabled><!--											
								<OPTION value="1" <%if(gain_flag!=null&&gain_flag.intValue()==1){out.print("selected");}//��ʵ��������%>><%=LocalUtilis.language("class.actualReturnRate",clientLocale)%></OPTION>
								<OPTION value="2" <%if(gain_flag!=null&&gain_flag.intValue()==2){out.print("selected");}//����׼������%>><%=LocalUtilis.language("class.benchmarkReturnRate",clientLocale)%></OPTION>
								<OPTION value="3" <%if(gain_flag!=null&&gain_flag.intValue()==3){out.print("selected");}//��ʱ��ֶ�������%>><%=LocalUtilis.language("class.timeBlockReturnRate",clientLocale)%></OPTION>	
								--><%=Argument.getTableOptions2(3005,gain_flag)%>										
							</SELECT></td> 
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<legend><font color="red"><%=LocalUtilis.language("class.openDate",clientLocale)%>:</font></legend><!-- ������ -->			
				<table border="0" width="100%" cellspacing="0" cellpadding="3" >
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.openIncome",clientLocale)%>:</td><!-- �Ƿ���Ὺ�������� -->
						<td width="75%">
							<SELECT name="open_gain_flag" style="width:120px;" disabled>								
								<OPTION  value="1" <%if(open_gain_flag!=null&&open_gain_flag.intValue()==1){ out.print("selected");}%>  ><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
								<OPTION  value="2"  <%if(open_gain_flag!=null&&open_gain_flag.intValue()==2){ out.print("selected");}%> ><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
							</SELECT></td> 
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<legend><font color="red"><%=LocalUtilis.language("class.customerHgtzr",clientLocale)%>:</font></legend>	<!-- �ϸ�Ͷ���� -->
				<table border="0" width="100%" cellspacing="0" cellpadding="3">
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.naturalPersonNumControl",clientLocale)%>:</td><!-- ��Ȼ���������� -->
						<td colspan="3" width="75%">
							<SELECT name="qualified_flag" style="width:120px;" onchange="javascript:changeView(this.value);" disabled>
								<OPTION  value="1" <%if(qualified_flag.intValue()==1){ out.print("selected");}%>  ><%=LocalUtilis.language("class.control",clientLocale)%></OPTION><!-- ���� -->
								<OPTION  value="2" <%if(qualified_flag.intValue()==2){ out.print("selected");}%>  ><%=LocalUtilis.language("class.noControl",clientLocale)%></OPTION><!-- ������ -->
							</SELECT></td> 
					</tr>
					<tr id="v_2" style="display:<%=styleStr %>;">
						<td align="right" width="25%"><%=LocalUtilis.language("class.naturalPersonNumControl",clientLocale)%>:</td><!-- ��Ȼ������ -->
						<td width="25%">
						   <input readonly="readonly" class="edline" type="text" name="qualified_num" value="<%=qualified_num %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
						<td align="right" width="25%"><%=LocalUtilis.language("class.amountQualfiedInvestorsLower",clientLocale)%>:</td><!-- �ϸ�Ͷ���˽������ -->
						<td width="25%">
						   <input readonly="readonly" class="edline" type="text" name="qualified_money" value="<%=Format.formatMoney(qualified_money) %>"  onkeyup="javascript:showCnMoney(this.value)" onkeydown="javascript:nextKeyPress(this)" size="20"> 
						<span id="to_money_cn" class="span" style="display: none;">&nbsp;(Ԫ)</span></td>
					</tr>

				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<legend><font color="red">������ɱ���:</font></legend><!-- ������ɱ��� -->			
				<table border="0" width="100%" cellspacing="0" cellpadding="3" >
					<tr>
						<%--td align="right" width="25%">������ɱ���(%):</td><!-- ������ɱ��� -->
						<td colspan="3" width="75%">
							<input readonly="readonly" class="edline" type="text" name="commission_rate" value="<%=Utility.trimNull(map_issue_crm.get("COMMISSION_RATE")) %>">
						</td--%> 
						<td width="25%">&nbsp;</td>
						<td width="75%" colspan="3" align="left"><button type="button"  class="xpbutton6" onclick="javascript:viewFeeRate(<%=productId%>,0<%--=sub_product_id--%>)">��ɱ�������</button></td>
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<legend><font color="red">ÿ�տ�ԤԼʱ��:</font></legend>		
				<table border="0" width="100%" cellspacing="0" cellpadding="3" >
					<tr>
						<td align="right" width="25%">ԤԼ��ʼʱ��:</td>
						<td width="25%">
							<input readonly="readonly" class="edline" type="text" name="pre_start_time" value="<%=pre_start_time %>">
						</td> 
						<td align="right" width="25%">ԤԼ��ֹʱ��:</td>
						<td width="25%">
							<input readonly="readonly" class="edline" type="text" name="pre_end_time" value="<%=pre_end_time%>">
						</td>
					</tr>
				</table>
		</fieldset>
	<%if(Argument.getSyscontrolValue("TA_FLAG") == 1 || user_id.intValue() == 2){%>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<legend><font color="red"><%=LocalUtilis.language("class.emissionBank",clientLocale)%>:</font></legend><!-- ���д��� -->
				<table border="0" width="100%" cellspacing="0" cellpadding="3" >
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.whereEmissionsbank",clientLocale)%>:</td><!-- �Ƿ����д��� -->
						<td colspan="3" width="75%">
							<SELECT name="is_bank_consign" style="width:120px;" onclick="javascript:setTa(this);" disabled>								
								<OPTION  value="1" <%if(is_bank_consign!=null&&is_bank_consign.intValue()==1){ out.print("selected");}%>><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
								<OPTION  value="2" <%if(is_bank_consign!=null&&is_bank_consign.intValue()==2){ out.print("selected");}%>><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
							</SELECT></td> 
					</tr>
				</table>
		</fieldset>
	<%} %>
	<fieldset style="border-width: 0px; border-color: #000000;display: <%if(Argument.getSyscontrolValue("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta1">
		<legend><font color="red"><%=LocalUtilis.language("message.subscription",clientLocale)%>:</font></legend><!-- �Ϲ� -->	
			<table border="0" width="100%" cellspacing="0" cellpadding="3" >
				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.orgSubscribeMoney",clientLocale)%>:</td><!-- �����Ϲ����ٽ�� -->
					<td width="25%">
					   <input readonly="readonly" class="edline" type="text" name="jg_min_subamount" value="<%=Format.formatMoney(jg_min_subamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.personalSubscribeMoney",clientLocale)%>:</td><!-- �����Ϲ����ٽ�� -->
					<td width="25%">
					   <input readonly="readonly" class="edline" type="text" name="gr_min_subamount" value="<%=Format.formatMoney(gr_min_subamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
				</tr>
			</table>
	</fieldset>
	<fieldset style="border-width: 0px; border-color: #000000;display: <%if(Argument.getSyscontrolValue("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta2">
		<legend><font color="red">�깺First explain buy at least amount institutions:</font></legend>			
		<table border="0" width="100%" cellspacing="0" cellpadding="3" >
			<tr>
				<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.firstApplyMoney",clientLocale)%>:</td><!-- �����״��깺���ٽ�� -->
				<td width="25%">
				   <input readonly="readonly" class="edline" type="text" name="jg_min_bidsamount" value="<%=Format.formatMoney(jg_min_bidsamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
				<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.firstPersonalApplyMoney",clientLocale)%>:</td><!-- �����״��깺���ٽ�� -->
				<td width="25%">
				   <input readonly="readonly" class="edline" type="text" name="gr_min_bidsamount" value="<%=Format.formatMoney(gr_min_bidsamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
				</td>
			</tr>
                           <tr>
				<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.orgSubscribeMinMoney",clientLocale)%>:</td><!-- ����׷���깺��ͽ�� -->
				<td width="25%">
				   <input readonly="readonly" class="edline" type="text" name="jg_min_appbidsamount" value="<%=Format.formatMoney(jg_min_appbidsamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
				<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.PersonalSubscribeMinMoney",clientLocale)%>:</td><!-- ����׷���깺��ͽ�� -->
				<td width="25%">
				   <input readonly="readonly" class="edline" type="text" name="gr_min_appbidsamount" value="<%=Format.formatMoney(gr_min_appbidsamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset style="border-width: 0px; border-color: #000000;display: <%if(Argument.getSyscontrolValue("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta3">
		<legend><font color="red"><%=LocalUtilis.language("message.redemption",clientLocale)%>:</font></legend>	<!-- ��� -->		
			<table border="0" width="100%" cellspacing="0" cellpadding="3" >
				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.leastAmount",clientLocale)%>:</td><!-- ���ٱ����ݶ� -->
					<td width="25%">
					   <input readonly="readonly" class="edline" type="text" name="min_redeem_vol" value="<%=Format.formatMoney(min_redeem_vol) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.whetherForcedRedeem",clientLocale)%>:</td><!-- �Ƿ����ǿ����� -->
					<td width="25%">
					  	<select name="coerce_redeem_flag" onkeydown="javascript:nextKeyPress(this);" style="width: 120px;" disabled>
							<option value="1" <%=coerce_redeem_flag.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
							<option value="2" <%=coerce_redeem_flag.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
						</select>
					</td>
				</tr>

				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.hugeRedemptive",clientLocale)%>:</td><!-- �Ƿ���޶���� -->
					<td width="25%">
						<select disabled name="large_redeem_flag" onkeydown="javascript:nextKeyPress(this);" style="width: 120px;" onclick="javascript:setLarge(this);">
							<option value="1" <%=large_redeem_flag.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
							<option value="2" <%=large_redeem_flag.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
						</select>
					</td>
					<td align="right" width="25%" id="tr1" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;"><font color="red">*</font><%=LocalUtilis.language("class.hugeRedemptiveCondition",clientLocale)%>:</td><!-- �޶�����ж����� -->
					<td width="25%" id="tr2" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;">
					    <select disabled style="width: 120px;" name="large_redeem_condition" onkeydown="javascript:nextKeyPress(this)">
							<option>��ѡ��</option>
							<option value="1" <%=large_redeem_condition.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("class.redemptiveShareJudgment",clientLocale)%></option><!-- ������طݶ��ж� -->
							<option value="2" <%=large_redeem_condition.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("class.totalRedemptionShare",clientLocale)%></option><!-- ������طݶ��ж� -->
						</select>
					</td>
				</tr>

				<tr id="tr3" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;">
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.percentageTotalShare",clientLocale)%>:</td><!-- �����ܷݶ�İٷֱ� -->
					<td width="25%">
					   <input readonly="readonly" class="edline" type="text" name="large_redeem_percent" value="<%=Utility.trimNull(Format.formatMoney(large_redeem_percent.multiply(new BigDecimal(100)).doubleValue(), 2))%>" onkeydown="javascript:nextKeyPress(this);" size="20"><font color="blue">(%)</font></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.hugeRedemptiveApproach",clientLocale)%>:</td><!-- �޶���ش���ʽ -->
					<td width="25%">
					  	<select disabled style="width: 120px;" name="large_redeem_deal" onkeydown="javascript:nextKeyPress(this)">
							<option>��ѡ��</option>
							<option value="1" <%=large_redeem_deal.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("class.postpone",clientLocale)%></option><!-- ˳�� -->
							<option value="2" <%=large_redeem_deal.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("class.applicationDateClient",clientLocale)%></option><!-- ���ݿͻ��������ݴ��� -->
						</select>
					</td>
				</tr>
			</table>
		</fieldset>
</div>
<%} %>
<br/>
			<div align="left">
		<p class="title-table"><font color="#215dc6"><b>�����Ŷ���Ϣ</b></font></p>
	</div>
			<div >
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class=trtagsort>
							<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--�Ŷ�����-->
							<td align="center"><%=LocalUtilis.language("class.quotaMoney",clientLocale)%> </td><!--�������-->
							<td align="center"><%=LocalUtilis.language("class.TeamMember",clientLocale)%> </td><!--�Ŷӳ�Ա-->
						</tr>
<%
//�鿴�Ŷ������Ϣ
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
int iCount = 0;
int iCurrent = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,12);
List list = null;
String[] totalColumn = new String[0];
salevo.setTeamID(team_id);
salevo.setProductID(productId);
IPageList pageList = sale_parameter.query(salevo,totalColumn,input_operatorCode,t_sPage,t_sPagesize);
list = pageList.getRsList();
Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
	team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
	team_name = Utility.trimNull(map.get("TEAM_NAME"));
 %>
						<tr class="tr0">
							<td align="center"><%=team_name %><input type="hidden" name="team_id" value="<%=team_id%>"></td>
							<td align="center"><%=Format.formatMoney(quota_money) %></td>
				            <td align="center">
				            	<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showTeamInfo('<%=Utility.trimNull(map.get("TEAM_ID"))%>');">&gt;&gt;</button>
				            </td>  
						</tr>
<%}
 %>
					<tr class="trbottom">
						<!--�ϼ�--><!--��-->
				        <td class="tdh" align="left" colspan="4"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					</tr>
				</table>
			</div>
	<br>
	</div>
	<div align="left">
		<p class="title-table"><font color="#215dc6"><b>����������Ϣ</b></font></p>
	</div>
	<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class=trtagsort>
						<%if(sub_flag.intValue() == 1){ %>
						<td align="center">�Ӳ�Ʒ����</td>
						<%} %>
						<td align="center">��������</td>
						<td align="center">��������</td>
						<td align="center">�������۷���(%)</td>
						<td align="center">��ע</td>
					</tr>
					<%
					if(markList != null && markList.size() != 0){
						for(int i=0; i<markList.size(); i++){
							markMap = (Map)markList.get(i);
					%>
					<tr class="tr<%=(i % 2)%>">
						<%if(sub_flag.intValue() == 1){ %>
						<td align="left"><%=Utility.trimNull(markMap.get("LIST_NAME")) %></td>
						<%} %>
						<td align="left">
							<%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %>
						</td>
						<td align="left">
							<%=Utility.trimNull(markMap.get("CHANNEL_NAME")) %>
						</td>
						<td align="right"><%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %></td>
						<td align="left"><%=Utility.trimNull(markMap.get("SUMMARY")) %></td>
						
					</tr>
					<%
						}
					}%>
				</table>
			</div>
</form>
<div align="right">
	<!--���ͨ��-->
    <button type="button"  class="xpbutton4" id="btnPass" name="btnPass" onclick="javascript:pass();"><%=LocalUtilis.language("message.pass",clientLocale)%></button>
    <!--���δͨ��-->
    <button type="button"  class="xpbutton4" id="btnNoPass" name="btnNoPass" onclick="javascript:noPass();"><%=LocalUtilis.language("message.notPass",clientLocale)%></button>
	&nbsp;&nbsp;
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<p>
<%
sale_parameter.remove();
product.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
