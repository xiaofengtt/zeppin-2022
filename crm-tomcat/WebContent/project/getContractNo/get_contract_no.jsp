<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递变量
Integer serial_nos = Utility.parseInt(Utility.trimNull(request.getParameter("object_id")),new Integer(0));
Integer serial_no = Utility.parseInt(ConfigUtil.getDataBaseValue("PRECONTRACT_ID","TPRECONTRACTFLOW","SERIAL_NO='"+serial_nos+"'"),new Integer(0));
String[] contract_no = request.getParameterValues("contract_no");
Integer contractNum = Utility.parseInt(Utility.trimNull(request.getParameter("contract_num")),new Integer(0));
String node_no = Utility.trimNull(request.getParameter("node_no"));
String contract_no_ = "";
//声明页面参数
input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;
Integer pre_num = new Integer(0);
//声明变量
Integer preDate = new Integer(0);
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
BigDecimal pay_money  = new BigDecimal(0);
Integer reg_data = new Integer(0);
Integer reg_valid_days = new Integer(0);
String legal_man = "";
String contact_man = "";
Integer service_man = new Integer(0);
Integer service_man_value = new Integer(0);
String pre_pruduct_name = null;
Integer pre_product_id = new Integer(0);
BigDecimal pre_money = new BigDecimal(0);
Integer link_man = new Integer(0);
Integer valid_days = new Integer(0);
String pre_type_name = "";
String cust_type_name = "";
String pre_code = "";
String pre_level_name = "";
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String per_code = Utility.trimNull(request.getParameter("per_code"));
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
BigDecimal channel_fare = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("channel_fare"))), null);
//获得对象
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
PreContractCrmVO pre_vo = new PreContractCrmVO();

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO c_vo = new CustomerVO();

if(request.getMethod().equals("POST")){
	for(int i=0;i<contract_no.length;i++){
		contract_no_ += contract_no[i]+"&";
	}
	//保存数据	
	System.out.println(contract_no_);
	//preContract.setContractNo(serial_no,contract_no_);
	CRMBusinessCheck.saveContractFlow(serial_nos,contract_no_);
	bSuccess = true;
}

	//预约信息数据集
	List list = null;
	Map map = null;
	//客户单个值数据集
	Map map_single = null;
	List rsList_single = null;
	//得等到数据集
	pre_vo.setSerial_no(serial_no);	
	list = preContract.listPreContractCrm(pre_vo);	
	if(list.size()>0){
		map = (Map)list.get(0);
		
		if(map!=null){
			//给字段赋值
			pre_product_id = Utility.parseInt(Utility.trimNull(map.get("PREPRODUCT_ID")),new Integer(0));

			pre_pruduct_name = Utility.trimNull(map.get("PREPRODUCT_NAME"));
			cust_id  = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
			pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), null);
			pay_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0));
			link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
			valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));
			pre_type_name = Utility.trimNull(map.get("PRE_TYPE_NAME"));
			pre_num = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
			summary = Utility.trimNull(map.get("SUMMARY"));		
			pre_code = Utility.trimNull(map.get("PRE_CODE"));
			preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
			expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
			customer_cust_source = Utility.trimNull(map.get("CUST_SOURCE"));
			channel_type = Utility.trimNull(map.get("CHANNEL_TYPE"));
			channel_fare = Utility.parseDecimal(Utility.trimNull(map.get("CHANNEL_FARE")), null);
			pre_level_name = Utility.trimNull(map.get("PRE_LEVEL_NAME"));
			contractNum = Utility.parseInt(Utility.trimNull(map.get("CONTRACT_NUM")),new Integer(0));
			contract_no_ = Utility.trimNull(ConfigUtil.getDataBaseValue("CONTRACT_NO","TPRECONTRACTFLOW","SERIAL_NO='"+serial_nos+"'"));
			//再查找客户信息并赋值给相关字段
			c_vo.setCust_id(cust_id);
			//c_vo.setInput_man(input_operatorCode);
		
			rsList_single = customer.listByControl(c_vo);
			
			if(rsList_single.size()>0){		
		
				map_single = (Map)rsList_single.get(0);
			
				if(map_single!=null){
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
					cust_type_name = Utility.trimNull(map_single.get("CUST_TYPE_NAME"));		
	
				}
			}
		}		
	}


//认购金额中文处理
String reg_money_cn = "";
if (pre_money.intValue()!= 0){
	reg_money_cn =  Utility.numToChinese(pre_money.toString());
}
String pay_money_cn = "";
if (pay_money.intValue()!= 0){
	pay_money_cn =  Utility.numToChinese(pay_money.toString());
}
String channel_fare_cn = "";
if (Utility.parseBigDecimal(channel_fare, new BigDecimal(0)).intValue()!= 0){
	channel_fare_cn =  Utility.numToChinese(channel_fare.toString());
}

String contractBeg = "<input type='text' name='contract_no' size='10' value='";
String contractEnd = "'>";

%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakEdit",clientLocale)%> </TITLE>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
//初始化
window.onload = function(){
	showPreinfo(document.theform.pre_product_id.value);
	var v_bSuccess = document.getElementById("bSuccess").value;
	var contractNos = [];
<%
	contract_no = contract_no_.split("&");
	for (int i=0; contract_no!=null && i<contract_no.length; i++) {
%>
	contractNos.push("<%=contract_no[i]%>");
<%}%>

	for(var i=0;i< <%=contractNum%>;i++){
		if(contractNos[i]==undefined) contractNos[i]="";
		var temp = "<%=contractBeg%>" + contractNos[i] + "<%=contractEnd%>";
		document.getElementById("contractNo").innerHTML+=temp;
	}
	
	if(v_bSuccess=="true"){	
		alert("保存成功！");
		
	}
}

/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action = "get_contract_no.jsp";
		document.theform.submit();
	}
}

/*返回*/
function CancelAction(){
	window.close();
}

/*下面是新增客户时用到的函数*/
function validateForm(form){
	var c = form.contract_no;
	for(var i=0;i<c.length;i++){
		if(c[i].value==""){
			sl_alert("合同编号不能为空！");
		return false;
		}
	}
	return sl_check_update();
}

/*获取已预约数目 已预约金额*/
function showPreinfo(pre_product_id){
	//产品信息
	var edit_pre_money = document.theform.this_pre_money.value
	utilityService.getPreInfoByPreId(pre_product_id,edit_pre_money,{callback: function(data){
		document.theform.product_total_money.value = data[0];	
		document.theform.pre_micro_num.value = data[1];
		document.theform.pre_total_num.value = data[1]+" / "+data[2];
		document.theform.pre_total_money.value = data[2];
		document.theform.pre_end_date.value = data[3];	
		document.theform.pre_total_money.value = data[4];
		document.theform.product_sy_money.value = data[5];
	}});
	//配额信息
	getTempMountByProductIdAndServerMan();
}


/*根据产品ID和预约登记人获取改团队的配额信息*/
function getTempMountByProductIdAndServerMan(){
	var pre_product_id = document.theform.pre_product_id.value;
	var link_man = document.theform.link_man.value;
	if(pre_product_id != 0 && link_man != ""){
		utilityService.getTempMountByProductIdAndServerMan(pre_product_id,link_man,{callback: function(data){
			var num1 = data.PRE_SALEMONEY_STR;
			if(num1 == "")
				num1 = "0.00";
			var num2 = data.QUOTAMONEY_STR;
			if(num2 == "")
				num2 = "0.00";
			document.getElementById("input_info1").value = num1+"/"+num2;
			document.getElementById("input_info2").value = data.PRE_QUALIFIEDNUM+"/"+data.QUOTA_QUALIFIED_NUM;
			
			document.getElementById("input_info4").value = data.CURRENT_NUM;
		
			document.getElementById("pre_salemoney").value = data.PRE_SALEMONEY;
			document.getElementById("quotamoney").value = data.QUOTAMONEY;
			document.getElementById("pre_qualifiednum").value = data.PRE_QUALIFIEDNUM;
			document.getElementById("quota_qualified_num").value = data.QUOTA_QUALIFIED_NUM;
			document.getElementById("current_money").value = parseInt(data.CURRENT_MONEY) + parseInt(document.getElementById("this_pre_money").value);
			document.getElementById("input_info3").value = document.getElementById("current_money").value;
			document.getElementById("current_num").value = data.CURRENT_NUM;
		}});
	}
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript: return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type='hidden' name="customer_cust_type"  value="<%= cust_type%>"/>
<input type='hidden' name="customer_card_type" value="<%=card_type%>"/>
<input type="hidden" name="pre_micro_num" value="">
<input type="hidden" name="this_pre_money" value="<%=pre_money%>">
<input type="hidden" name="pre_num" value="<%=pre_num%>">
<input type="hidden" name="contractNum" value="<%=contractNum%>">
<input type="hidden" name="object_id" value="<%=serial_nos%>">
<input type="hidden" name="pre_salemoney" id="pre_salemoney" value="">
<input type="hidden" name="quotamoney" id="quotamoney" value="">
<input type="hidden" name="pre_qualifiednum" id="pre_qualifiednum" value="">
<input type="hidden" name="quota_qualified_num" id="quota_qualified_num" value="">
<input type="hidden" name="current_money" id="current_money" value="">
<input type="hidden" name="current_num" id="current_num" value="">
<input type="hidden" name="node_no" id="node_no" value="<%=node_no%>">
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
			<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getOpName(service_man))%>">&nbsp;&nbsp;&nbsp;
			</td>
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
			<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%= cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
			<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= card_id%>" size="20"></td>
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
				<input type="hidden" name="pre_product_id" value="<%=pre_product_id%>"/>
				<input readonly class="edline" name="product_name" size="50" value="<%=Utility.trimNull(pre_pruduct_name)%>"/>	
			</td>
			<td align="right">产品总规模 :</td>
			<td><input readonly class="edline" name="product_total_money" size="27" value=""></td>
		</tr>
		<tr>
			<td align="right">小额份数/累计份数 :</td>
			<td><input readonly class="edline" name="pre_total_num"  size="27" value=""></td>
			<td align="right">截止日期 :</td>
			<td><input readonly class="edline" name="pre_end_date"  size="27" value=""></td>
		</tr>	
		<tr>
			<td align="right">已预约规模 :</td>
			<td><input readonly class="edline" name="pre_total_money" size="27" value=""/></td>
			<td align="right">可预约规模 :</td>
			<td><input readonly class="edline" name="product_sy_money" size="27" value=""/></td>
		</tr>
		<tr>
			<td colspan="4"><hr size="1"></td>
		</tr>
		<tr>
			<td align="right">已预约金额/团队金额配额:</td>
			<td><input readonly class="edline" name="input_info1" id="input_info1" size="27" value=""/></td>
			<td align="right">已预约小额份数/合同投资人数量配额:</td>
			<td><input readonly class="edline" name="input_info2" id="input_info2" size="27" value=""/></td>
		</tr>
		<tr>
			<td align="right">当前可预约金额:</td>
			<td><input readonly class="edline" name="input_info3" id="input_info3" size="27" value=""/></td>
			<td align="right">当前可预约份数:</td>
			<td><input readonly class="edline" name="input_info4" id="input_info4" size="27" value=""/></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--预约金额-->
			<td><input  readonly class="edline" name="pre_money" size="27" value="<%=Format.formatMoney(pre_money)%>" onkeydown="javascript:nextKeyPress(this)">	
				 <%if (pre_money!= null){%>
					<span id="pre_money_cn" class="span">(<%=reg_money_cn%>)</span>
    		    <%}
    			else {%>
    				<span id="pre_money_cn" class="span">&nbsp;</span>
    			<%}%>
			</td>
			<td align="right">到账金额 :</td>
			<td><input readonly class="edline" name="pay_money" size="27" value="<%=Format.formatMoney(pay_money)%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--预约方式-->
			<td>
				<input readonly class="edline" name="pre_type_name" size="15" value="<%=pre_type_name%>">
			</td>
			<td align="right">预约登记人 :</td><!--预约登记人-->
			<td>
				<input readonly class="edline" name="link_man_name" size="15" value="<%=Argument.getOperatorName(link_man)%>">
				<input type="hidden" name="link_man" value="<%=link_man%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--预约日期-->
			<td>
				<INPUT readonly class="edline" TYPE="text" NAME="pre_date_picker" class=selecttext size="23"
				<%if(preDate==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
				<INPUT TYPE="hidden" NAME="pre_date"   value="<%=preDate%>">
			</td>
			<td align="right">预计认购日期:</td><!--预计认购日期-->
			<td>
				<INPUT readonly class="edline" TYPE="text" NAME="exp_reg_date_picker" class=selecttext size="23"
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="<%=expRegDate%>">
			</td>
			
		</tr>
		<tr>
			<td align="right">渠道类别:</td>
			<td>
				<input readonly class="edline" name="channel_type_name" value="<%=Utility.trimNull(Argument.getDictParamName(5500,channel_type))%>">
			</td>
			<td align="right" id="channel_td1" >渠道费用:</td>
			<td id="channel_td2" >
				<input readonly class="edline" type="text" name="channel_fare" value="<%=Format.formatMoney(channel_fare)%>" size="20" onkeydown="javascript:nextKeyPress(this)">
				
				 <%if (channel_fare != null){%>
					<span id="channel_fare_cn" class="span">(<%=channel_fare_cn%>)</span>
    		    <%}
    			else {%>
    				<span id="channel_fare_cn" class="span">&nbsp;</span>
    			<%}%>
			</td>
		</tr>
		<tr>
			<td align="right">预约级别:</td><!--预约级别-->
			<td >
				<input readonly class="edline" name="pre_level_name" value="<%=pre_level_name%>">
			</td>
			<td align="right"><font color="red">合同编号:</td>
			<td id="contractNo">
				
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
			<td colspan="3"><textarea readonly rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<%if("020".equalsIgnoreCase(node_no)){ %>
	<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<%} %>
	<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--返回-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
preContract.remove();
customer.remove();
%>