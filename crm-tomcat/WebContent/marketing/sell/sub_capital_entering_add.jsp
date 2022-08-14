<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();

//声明页面参数
input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;
String sQuery = Utility.trimNull(request.getParameter("sQuery"));

Integer   serial_no      = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer   cust_id        = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
Integer   product_id      = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer   sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

int sub_flag       = Utility.parseInt(request.getParameter("sub_flag"), 0);
int check_flag     = Utility.parseInt(request.getParameter("check_flag"), 1);
int action_from    = Utility.parseInt(request.getParameter("action_from"), 0);

String product_code   = request.getParameter("product_code");
String jk_type        = request.getParameter("jk_type");
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String summary        = Utility.trimNull(request.getParameter("summary"));
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"), new Integer(0));
Integer dz_date        = Utility.parseInt(request.getParameter("dz_date"), new Integer(0));

BigDecimal to_amount      = Utility.parseDecimal(request.getParameter("to_amount"),new BigDecimal(0));
BigDecimal to_money       = Utility.parseDecimal(request.getParameter("to_money"),new BigDecimal(0));
Integer city_serialno     = Utility.parseInt(request.getParameter("city_serialno"), new Integer(0));
Integer sbf_serial_no = Utility.parseInt(request.getParameter("sbf_serial_no"), new Integer(0));

String product_name = "";
String tg_bank_acct = "";
String currency_id = "";
String strButton = "请选择";
Integer item_id = new Integer(0);
Integer check_flag2 = new Integer(0);//记录审核标志

if (request.getMethod().equals("POST")){
	TMoneyDataVO vo = new TMoneyDataVO();

	vo.setSerial_no(serial_no);
	vo.setBook_code(input_bookCode);
	vo.setProduct_id(product_id);
	vo.setSub_product_id(sub_product_id);
	vo.setCust_id(cust_id);
	vo.setTo_money(to_money);
	vo.setTo_amount(to_amount);
	vo.setJk_date(dz_date);
	vo.setJk_type(jk_type);
	vo.setSummary(summary);
	vo.setCity_serialno(city_serialno);
	vo.setInput_man(input_operatorCode);
	vo.setCust_type(cust_type);
	vo.setCust_name(cust_name);
	vo.setSbf_serial_no(sbf_serial_no);

	if (serial_no.intValue()!=0)
		moneyData.modify(vo);
	else //方法使用CRM 移植的新过程 同步新建客户信息
		moneyData.append(vo);
	
	bSuccess = true;
}

if(serial_no.intValue()!=0){
	TMoneyDataVO vo = new TMoneyDataVO();
	vo.setSerial_no(serial_no);
	List rsList = moneyData.listAll(vo);

	if(rsList.size()>0){
		Map rsMap = (Map)rsList.get(0);
		
		product_id      = Utility.parseInt(Utility.trimNull(rsMap.get("PRODUCT_ID")),new Integer(0));
		sub_product_id = Utility.parseInt(Utility.trimNull(rsMap.get("SUB_PRODUCT_ID")),new Integer(0));
		product_code   = Utility.trimNull(rsMap.get("PRODUCT_CODE"));
		jk_type        = Utility.trimNull(rsMap.get("JK_TYPE"));
		dz_date        = Utility.parseInt(Utility.trimNull(rsMap.get("DZ_DATE")),new Integer(0));
		cust_id        = Utility.parseInt(Utility.trimNull(rsMap.get("CUST_ID")),new Integer(0));
		summary        = Utility.trimNull(rsMap.get("SUMMARY"));
		city_serialno  = Utility.parseInt(Utility.trimNull(rsMap.get("CITY_SERIAL_NO")),new Integer(0));
		to_amount      = Utility.parseDecimal(Utility.trimNull(rsMap.get("TO_AMOUNT")),new BigDecimal(0));
		to_money       = Utility.parseDecimal(Utility.trimNull(rsMap.get("TO_MONEY")),new BigDecimal(0));
		check_flag2    = Utility.parseInt(Utility.trimNull(rsMap.get("CHECK_FLAG")),new Integer(0));
		sbf_serial_no  = Utility.parseInt((Integer)rsMap.get("SBF_SERIAL_NO"), new Integer(0));
	}
}

String cityNameOptions = Argument.getCitynameOptions(product_id,city_serialno);
int        readonly       = 0;
String     to_money_cn    = "(" + Utility.numToChinese(to_money.toString()) + ")";
String read_flag = "";
if (check_flag==2 ||check_flag2.intValue()==2)	read_flag = "readonly class='edline'";

if(product_id.intValue() != 0){
	sub_flag = Utility.parseInt(Argument.getProductFlag(product_id,"sub_flag"),new Integer(0)).intValue();
	product_name = Argument.getProductFlag(product_id,"product_name");

	tg_bank_acct = Argument.getProductFlag(product_id,"tg_bank_acct"); 
	currency_id = Argument.getProductFlag(product_id,"currency_id");
	item_id = Utility.parseInt(Argument.getProductFlag(product_id,"item_id"),new Integer(0));

	if ("".equals(cust_id))	readonly = 1;
}

if(cust_id.intValue()>0){
	cust_name = Argument.getCustomerFlag(cust_id,input_operatorCode,"cust_name");
	cust_type = Utility.parseInt(Argument.getCustomerFlag(cust_id,input_operatorCode,"cust_type"),new Integer(0));
}

moneyData.remove();
%>
<HTML>
<HEAD>
<TITLE>认/申购资金合同录入</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language=javascript>
var action_from = <%=action_from%>;

window.onload = function(){
		<%if (bSuccess){%>  
		  sl_alert("保存成功");
		  location = "<%=request.getContextPath()%>/marketing/sell/sub_capital_entering_list.jsp?back_flag=1&sQuery=<%=sQuery%>";	
		<%}else {%>	
			setCustomer();
			initSelect();
		<%}%>
	};

//展示产品信息
function openQuery(product_id,item_id){	
	if (product_id!=0)
		showModalDialog('<%=request.getContextPath()%>/marketing/base/product_list_detail.jsp?product_id='+product_id+'&item_id='+item_id
						, '','dialogWidth:720px;dialogHeight:580px;status:0;help:0');
}

function newCity(productId,city_serial_no){	
	var ret = showModalDialog('<%=request.getContextPath()%>/marketing/base/product_city_update.jsp?product_id=' + productId+'&city_serial_no='+city_serial_no, '', 'dialogWidth:500px;dialogHeight:150px;status:0;help:0');
	if (ret){
		seloption = new Option(ret[1],ret[0]);
		document.all.city_serialno.options[document.all.city_serialno.length] = seloption;
		document.all.city_serialno.options[document.all.city_serialno.length-1].selected = true;
	}
}

//根据产品编号获取产品信息
function setProduct(value) {
	var prodid=0;
	if (event.keyCode==13 && value!="")	{
       j = value.length;
		for (var i=0; i<document.theform.product_id.options.length; i++) {
			if (document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected = true;
				prodid = document.theform.product_id.options[i].value;
				selectProductItem();
				//break;
			}	
		}
		if (prodid==0) {
			sl_alert('输入的产品编号不存在或者该产品不在推介期！');
			document.theform.product_code.value = "";
			document.theform.product_id.options[0].selected = true;
		}
	}
	nextKeyPress(this);
}

function selectProductItem(){
	if (document.theform.product_id.value == 0) return false;
	document.theform.method = "get";
	document.theform.submit();
}

function openSubProduct(sub_product_id){	
    subpro_product_id = sub_product_id.value;

    if(subpro_product_id == 0){
      sl_alert('请选择子产品!');
      return false;
    }else{
      //showModalDialog('product_sub_info.jsp?sub_product_id='+subpro_product_id, '', 'dialogWidth:480px;dialogHeight:350px;status:0;help:0')
    }	  
}


//客户信息设置
function setCustomer(){
	var cust_name = document.theform.cust_name.value;
	var query_condition = "<%=input_bookCode%>|"+cust_name;
	var cust_id = document.theform.cust_id ;

	if (cust_name.length==0) return false;
	
	utilityService.getCustListOptions(query_condition,function(data){
		var json = eval(data);
		DWRUtil.removeAllOptions(cust_id);
		DWRUtil.addOptions(cust_id,{"0":"请选择"});
		for (var i=0; i<json.length; i++) DWRUtil.addOptions(cust_id,json[i]);
	 	DWRUtil.setValue('cust_id',<%=cust_id%>);
		initSelect();
	});
}

function custTypeVerify(){
	var cust_id = document.getElementById("cust_id").options.value;
	
	if(cust_id==0){
		document.getElementById("cust_type").options[0].selected = true;
		//document.theform.cust_name.value = "";
	} else {
		var select_text = document.theform.cust_id.options[document.theform.cust_id.selectedIndex].text;
		var arr = select_text.split("-");
		document.theform.cust_name.value = arr[0];
		var str = arr[2];

		if(str==" 个人")
			document.getElementById("cust_type").options[1].selected = true;
		else if(str==" 机构")
			document.getElementById("cust_type").options[2].selected = true;
	}
}

//下拉选择初始化
function initSelect(){
	var cust_len = document.getElementById("cust_id").options.length;
	var sbf_len = document.getElementById("sbf_serial_no").options.length;

	if(cust_len>1){
		//document.getElementById("cust_id").options[1].selected = true;
		custTypeVerify();
	}

	/*if(sbf_len>1){
		document.getElementById("sbf_serial_no").options[1].selected = true;
	}*/
}

//选择委托人
function openCustInfo(prefix,readonly){
	var cust_id = document.theform.cust_id.value;

	if(cust_id!=0){
		var url = '<%=request.getContextPath()%>/marketing/customer_info_simple.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly;
		showModalDialog(url,'','dialogWidth:700px;dialogHeight:688px;status:0;help:0;');
	}
	else{
		sl_alert("请先选择客户！");
	}	
}

//选择展示客户信息
//客户选择
function getTransactionCustomer2_1(prefix,readonly,cust_flag){
	var cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly;
	if(cust_flag == 2)
	 	url = '<%=request.getContextPath()%>/marketing/customer_info_simple.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly;
	var v = showModalDialog(url,'','dialogWidth:700px;dialogHeight:688px;status:0;help:0;');
	if (v) showTransactionCustomer2(prefix, v);
}

function op_return() {
	if (action_from == 1){
		location = "<%=request.getContextPath()%>/marketing/sell/sub_capital_entering_list.jsp?back_flag=1&sQuery=<%=sQuery%>";
	} else if(action_from == 2){
		location = "<%=request.getContextPath()%>/marketing/sell/sub_capital_entering_check.jsp?back_flag=1&sQuery=<%=sQuery%>";
	} else if (action_from == 3){
		location = "<%=request.getContextPath()%>/marketing/sell/sub_capital_make_contract.jsp?back_flag=1&sQuery=<%=sQuery%>";
	} else if (action_from == 4){
		location = "<%=request.getContextPath()%>/marketing/sell/sub_capital_entering_preliminary_check.jsp?back_flag=1&sQuery=<%=sQuery%>";
	}
}


function validateForm(form) {
	product_id = form.product_id.value;
<%if(sub_flag == 1){%>
	sub_product_id = form.sub_product_id.value;
<%}%>

    if (product_id == 0) {
      	sl_alert('请选择产品!');
		form.product_id.focus();
      	return false;
    }

<%if(sub_flag == 1){%>
  	if (sub_product_id=="" || sub_product_id==0) {
      	sl_alert('请选择子产品!');
		form.sub_product_id.focus();
      	return false;
    }
<%}%>

	if (! sl_checkDate(form.dz_date_picker,"到账日期")) return false;
	syncDatePicker(form.dz_date_picker, form.dz_date);

	if (form.cust_name.value=="") {
		sl_alert("请选择客户！");
		return false;
	}

	if (document.theform.cust_id.value==0 && document.theform.cust_type.value==0) {
		sl_alert("请选择客户类别！");
		return false;
	}

	if (! sl_checkChoice(form.jk_type, "缴款方式")) return false;
	if (! sl_checkDecimal(form.to_money,"到账金额",16,3,1))	return false;
	if (! sl_checkDecimal(form.to_amount,"到账份额",16,3,1)) return false;
	if(sl_check_update()){
		document.theform.method = "POST";
		//document.theform.submit();
		return  true;
	} else {
		return false;
	}	
}

function checkConfim(){
	if (action_from==2) {
		document.theform.action = "sub_capital_entering_check_do.jsp?sQuery=<%=sQuery%>";
		document.theform.checkflag.value="1";
		document.theform.submit();
	} else if (action_from==4) {
		document.theform.action = "sub_capital_entering_preliminary_check_do.jsp?sQuery=<%=sQuery%>";
		document.theform.checkflag.value="2";
		document.theform.submit();
	}
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="sub_capital_entering_add.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no"          value="<%=serial_no%>">
<input type="hidden" name="sQuery"             value="<%=sQuery%>">
<input type="hidden" name="action_from"        value="<%=action_from%>">
<input type="hidden" name="checkflag" value="">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
		<tr>
			<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="32"><b>认购资金信息</b></td>
		</tr>
		<tr>
			<td><hr size="1"></td>
		</tr>
	</TABLE>

	<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
		<tr>
			<td align="right">产品编号:</td>
			<td align="LEFT">
				<input type="text" <%if(serial_no.intValue()!=0) out.print("readonly class='edline'");%> maxlength="16" name="product_code" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="20">
			</td>
			<td align="right"></td>
			<td align="right"></td>
		</tr>

		<tr>	
			<td align="right"><b>*产品选择</b></td>
			<td align=left colspan=3>
				<select size="1" <%if(serial_no.intValue()!=0){out.print("disabled");}%> name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductItem();" class="productname" <%=check_flag==2?"disabled":""%> >
					<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,55)%> <!--推介期和正常期资金结算的产品-->
				</select>&nbsp;
				<button type="button"  name="show_info" title="查看产品信息" onclick="javascript:openQuery('<%=product_id%>','<%=item_id%>');
					"<%if(product_id!=null){if(product_id.intValue()!=0) {out.print("style=display:'';");}else{out.print("style=display:'none';");}}else{out.print("style=display:'none';");}%>>查看</button>
			</td>
		</tr>

		<%if(sub_flag == 1){%>
		<tr>
			<td align="right"><b>*子产品选择</b></td>
			<td align=left colspan=3>
				<select size="1"  name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" <%=check_flag==2||check_flag==3?"disabled":""%>>
					<%=Argument.getSubProductOptions2(product_id, new Integer(0),sub_product_id,3,"113801")%> 
				</select>&nbsp;
				<button type="button"  name="show_sub_info" title="查看子产品信息" onclick="javascript:openSubProduct(document.theform.sub_product_id);">查看</button>
			</td>
		</tr>
		<%}%>

		<tr>
			<td><b>客户信息</b></td>
		</tr>
		<tr>
			<td align="right">客户名称:</td> 
			<td >
				<input <%=read_flag%> maxlength="100" name="cust_name" style="WIDTH: 120px" onkeydown="javascript:nextKeyPress(this);" 
					 value="<%=Utility.trimNull(cust_name)%>">&nbsp;&nbsp;&nbsp;
				<button type="button"  onclick="javascript:setCustomer();">查询</button>
			</td>
		</tr>	
		<tr>
			<td align="right">客户列表:</td>
			<td >
				<select <%=check_flag==2||check_flag==3?"disabled":""%> id="cust_id"  onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px"  name="cust_id" onchange="javascript:custTypeVerify();">
					<option value="0">请选择</option>		
				</select>&nbsp;&nbsp;&nbsp;
				<button type="button"  name="cust_show_info" title="查看客户信息" onclick="javascript:openCustInfo('customer','1');">查看</button>
			</td>
		</tr>
		<tr id="tr_cust_type">
			<td align="right">客户类别:</td>
			<td>
				<select <%=check_flag==2||check_flag==3?"disabled":""%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px"  name="cust_type" id="cust_type">
					<%=Argument.getCustTypeOptions(cust_type)%>
				</select>
			</td>
		</tr>

		<tr>
			<td><b>资金信息</b></td>
		</tr>
		<tr>
			<td align="right">到账日期:</td> 
			<td >
				<INPUT TYPE="text" NAME="dz_date_picker"  <%=read_flag%>
				value="<%=Format.formatDateLine((dz_date.intValue()== 0?Utility.getCurrentDate():dz_date.intValue()))%>" >
				  
				<INPUT TYPE="button" value="▼" class=selectbtn <%=check_flag == 2?"style=display:none":""%> onclick="javascript:CalendarWebControl.show(theform.dz_date_picker,theform.dz_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="dz_date" value="">
			</td>
			<td align="right">缴款方式:</td> 
			<td>
				<select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" <%=check_flag==2?"disabled":""%>>
					<%=Argument.getJkTypeOptions(Utility.trimNull(jk_type))%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">到账金额:</td> 
			<td ><input maxlength="16" <%=read_flag%> name="to_money" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Format.formatMoney(to_money))%>" onkeyup="javascript:showCnMoney(this.value,to_money_cn);" onblur="javascript:theform.to_amount.value=this.value">
				<span id="to_money_cn" class="span"><%=to_money_cn%></span>
			</td>
			<td align="right">到账份额:</td>
			<td ><input maxlength="16" <%=read_flag%>  readonly name="to_amount" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Format.formatMoney(to_amount))%>">
			</td>
		</tr>
		<tr>
			<td align="right">到账银行帐号:</td>
			<td>
				<select <%=check_flag==2||check_flag==3?"disabled":""%> size="1" name="sbf_serial_no" id="sbf_serial_no"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 300px">
					<%=Argument.getbankOption(input_bookCode,product_id,currency_id,Utility.parseInt(sbf_serial_no,new Integer(0)))%>  
				</select>&nbsp;&nbsp;
			</td>
			<td align="right">合同推介地:</td>
			<td><select <%=check_flag==2||check_flag==3?"disabled":""%> size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=cityNameOptions%>
			</select>&nbsp;&nbsp;
			<%if(check_flag == 1){%>
				<button type="button"  class="xpbutton5" accessKey=r id="btnSetcity" name="btnSetcity" onclick="javascript:newCity(<%=product_id%>,'');" >推介地设置</button>
			<%}%>
			</td>
		</tr>
		<tr style="display:none">
			<td align="right" valign="top">说明:</td> 
			<td colspan="3"><textarea rows="4" <%=read_flag%> name="summary" cols="80"><%=summary%></textarea></td>
		</tr>

		<tr>
			<td colspan="4">
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<%if(check_flag == 1){%>
					<button type="button"  type="submit" class="xpbutton3" accessKey=s id="btnSave" name="btnSave">保存(<u>S</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<%}else if(check_flag == 2) {
						if (check_flag2.intValue()==11){ %>
					    <button type="button"  class="xpbutton4"  name="btnCheck" title="审核" onclick="javascript:checkConfim();">初审通过</button>
					&nbsp;&nbsp;&nbsp; 
						<%}else if (check_flag2.intValue()==1) { %>
					    <button type="button"  class="xpbutton4"  name="btnCheck" title="审核" onclick="javascript:checkConfim();">审核通过</button>
					&nbsp;&nbsp;&nbsp; 
					<%	}
					}%>
					<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);op_return();">返回(<u>B</u>)</button>
					&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</TABLE>
	</TD>
</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>