<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer product_id = new Integer(0);
Integer preproduct_id = new Integer(0);
String sProduct_id = "";
String sPreproduct_id = "";
if (request.getParameter("product_id")!=null) {
	String[] t = request.getParameter("product_id").split("-");
	sPreproduct_id = t[0];
	preproduct_id = Utility.parseInt(sPreproduct_id, new Integer(0));

	if (t.length>1) {
		sProduct_id = t[1];
		product_id = Utility.parseInt(sProduct_id, new Integer(0));
	}
}

//获得页面传递变量
Integer customer_cust_id = Utility.parseInt(request.getParameter("customer_cust_id"), new Integer(0));
BigDecimal r_pre_money = Utility.parseDecimal(request.getParameter("pre_money"), null,0,"1");
r_pre_money = r_pre_money.setScale(2,BigDecimal.ROUND_HALF_UP);
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),null);
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));//客户来源
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
Integer valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));

//帐套暂时设置
input_bookCode = new Integer(1);

//声明页面参数
boolean bSuccess = false;
Integer pre_num = new Integer(1);
Integer cust_id = null;

if(customer_cust_id.intValue()>0){
	cust_id = customer_cust_id;
}

//获取对象
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();

CustomerLocal customer = EJBFactory.getCustomer();//客户
CustomerVO cust_vo = new CustomerVO();

//声明变量
//客户信息
String cust_no = "";
String cust_name =  "";
Integer cust_type = new Integer(0);
String card_id =  "";
Integer card_type = new Integer(0);
String cust_tel =  "";
String o_tel =  "";
String mobile =  "";
String e_mail =  "";
String bp =  "";
String post_code =  "";
String post_address =  "";
String cust_type_name = "";
String h_tel = "";
Integer is_link = new Integer(0);
String service_man=  "";
Integer service_man_value = new Integer(0);
Integer is_deal = new Integer(0);

//预约信息
String summary = "";
BigDecimal reg_money = new BigDecimal(0);
BigDecimal pre_money2 = null;
Integer reg_data = null;
String legal_man= null;
String contact_man= null;
BigDecimal pre_money;
Integer link_man;
String pre_type ="";

//产品预约份数 产品预约金额
Integer fact_pre_num = new Integer(0);
BigDecimal fact_pre_money = new BigDecimal(0);
Integer pre_end_date = new Integer(0);

//保存变量
if(request.getMethod().equals("POST")){
	//保存预约信息
	cust_id  = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
	pre_money = Utility.parseDecimal(request.getParameter("pre_money"), null);
	
	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),new Integer(0));
	valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));
	pre_type = Utility.trimNull(request.getParameter("pre_type"));
	pre_num = Utility.parseInt(Utility.trimNull(request.getParameter("pre_num")),new Integer(0));
	summary = Utility.trimNull(request.getParameter("summary"));

	pre_vo.setProduct_id(product_id);
	pre_vo.setPre_product_id(preproduct_id);
	pre_vo.setCust_id(cust_id);
	pre_vo.setPre_money(pre_money);
	pre_vo.setLink_man(link_man);
	pre_vo.setValid_days(valid_days);
	pre_vo.setPre_type(pre_type);
	pre_vo.setPre_num(pre_num);
	pre_vo.setSummary(summary);
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setPre_date(preDate);
	pre_vo.setExp_reg_date(expRegDate);
	pre_vo.setCust_source(customer_cust_source);
	pre_vo.setSub_product_id(new Integer(0)); //
	try{
		preContract.append(pre_vo);
	}catch(Exception e){
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">location = 'checkin_list.jsp';</script>");
		return;
	}
	
	bSuccess = true;
}
else{
	//客户信息显示
	if(cust_id.intValue()>0){
		List rsList_cust = null;
		Map map_cust = null;

		//客户单个值
		cust_vo.setCust_id(cust_id);
		cust_vo.setInput_man(input_operatorCode);
		rsList_cust = customer.listByControl(cust_vo);

		if(rsList_cust.size()>0){
			map_cust = (Map)rsList_cust.get(0);
		}

		if(map_cust!=null){
			cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
			e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
			cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
			service_man_value = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));
			card_id = Utility.trimNull(map_cust.get("CARD_ID"));
			o_tel = Utility.trimNull(map_cust.get("O_TEL"));
			h_tel = Utility.trimNull(map_cust.get("H_TEL"));
			mobile = Utility.trimNull(map_cust.get("MOBILE"));
			bp = Utility.trimNull(map_cust.get("BP"));
			post_code = Utility.trimNull(map_cust.get("POST_CODE"));
			post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
			card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
			cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
			cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
			is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
			legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
			contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
			is_deal =  Utility.parseInt(Utility.trimNull(map_cust.get("IS_DEAL")),new Integer(0));
		}
	}
}

//认购金额中文处理
String reg_money_cn = "";
if (r_pre_money.intValue()!= 0){
	reg_money_cn =  Utility.numToChinese(r_pre_money.toString());
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.checkinPre",clientLocale)%> </TITLE>
<!--预登记转预约-->
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

<script language="javascript">
	/*保存功能*/
	function SaveAction(){
		if(document.theform.onsubmit()){
			disableAllBtn(true);
			document.theform.action="checkin_pre.jsp"
			document.theform.submit();
		}
	}

	/*返回*/
	function CancelAction(){
		location = 'checkin_list.jsp' ;
	}

	//下面是新增客户时用到的函数
	function validateForm(form){
		if(form.customer_cust_id.value==""){
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
			return false;
		}
		else{
			var is_deal = document.getElementsByName("is_deal")[0].value;
			//if(is_deal!=1){
				//sl_alert("<%=LocalUtilis.language("message.checkinError",clientLocale)%> ！");//非事实客户不能转预约
				//return false;
			//}
		}

		if(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.product",clientLocale)%> "))return false;//产品
		if(!sl_checkDecimal(form.pre_money, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 1))return false;//预约金额
		if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))	return false;//客户来源
		if(!sl_checkChoice(form.pre_num, "<%=LocalUtilis.language("class.preNum2",clientLocale)%> "))	return false;//预约份数
		if(!sl_checkChoice(form.pre_type, "<%=LocalUtilis.language("class.preType",clientLocale)%> "))	return false;//预约方式
		if(!sl_checkNum(form.valid_days, "<%=LocalUtilis.language("lass.validDays",clientLocale)%> ", 10, 1))return false;//有效天数
		if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//联系人
		if(!sl_checkDate(document.theform.pre_date_picker,"<%=LocalUtilis.language("class.preDate",clientLocale)%> ")){//预约日期
			return false;
		}

		syncDatePicker(form.pre_date_picker, form.pre_date);
		if(document.theform.exp_reg_date_picker.value!=""){
			if(!sl_checkDate(document.theform.exp_reg_date_picker,"预计认购日期")){//预计认购日期
				return false;
			}
			syncDatePicker(form.exp_reg_date_picker, form.exp_reg_date);
		}

		if(form.pre_date.value>form.pre_end_date.value&&(!confirm("<%=LocalUtilis.language("message.bespeakAddTip",clientLocale)%> ")))return false;//预约日期大于推介到期日期！要继续吗？

		return sl_check_update();
	}

	/*修改已预约数目 已预约金额*/
	function showPreinfo(){		
		var arr = document.theform.product_id.value.split("-");
		var preproduct_id = arr[0];
		var product_id = arr[1];
		utilityService.getPreInfoById(preproduct_id, product_id,{callback: function(data){
			if(data[0]!=null){
				document.theform.fact_pre_num.value = data[0];
			}

			if(data[1]!=null){
				document.theform.fact_pre_money_view.value = data[1];
			}

			document.theform.pre_end_date.value = data[2];
		}});
	}

	/*客户信息*/
	function getMarketingCustomer(prefix,readonly){
		cust_id = getElement(prefix, "cust_id").value;
		var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id=' + cust_id;
		v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
		referPage();
	}

	function referPage(){
		var url = "checkin_pre.jsp?customer_cust_id=" + document.getElementsByName("customer_cust_id")[0].value;
		var url = url + "&product_id=" + document.getElementsByName("product_id")[0].value;
		var url = url + "&pre_money=" + document.getElementsByName("pre_money")[0].value;
		var url = url + "&pre_num=" + document.getElementsByName("pre_num")[0].value;
		var url = url + "&pre_type=" + document.getElementsByName("pre_type")[0].value;
		var url = url + "&valid_days=" + document.getElementsByName("valid_days")[0].value;
		var url = url + "&pre_date=" + document.getElementsByName("pre_date")[0].value;
		var url = url + "&summary=" + document.getElementsByName("summary")[0].value;

		window.location.href=url;
	}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform"  method="post" onsubmit="javascript: return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--新增成功标志-->
<input type="hidden" name="is_deal" value="<%=Utility.trimNull(is_deal)%>">
<input type="hidden" name="pre_end_date" value="">
<input type="hidden" name="customer_cust_id" value="<%=Utility.trimNull(cust_id)%>">
<input type="hidden" name="customer_card_type" value="<%=Utility.trimNull(card_type)%>">
<input type="hidden" name="customer_cust_type" value="<%=Utility.trimNull(cust_type)%>">
<input type="hidden" name="customer_cust_on" value=""/>
<input type='hidden' name="customer_service_man_value"  value=""/>
<input type='hidden' name="customer_legal_man" value=""/>
<input type='hidden' name="customer_contact_man" value=""/>
<input type='hidden' name="customer_e_mail" value=""/>

<div align="left" class="page-title">

	<font color="#215dc6"><b><%=LocalUtilis.language("menu.checkinPre",clientLocale)%> </b></font><!--预登记转预约-->
</div>
<br/>
<!--客户选择-->
<div vAlign=top align=left>
<table cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
	<tr>
		<td align="left"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--客户信息-->
		<td align="left" colspan="3">
		    <button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> "
		        onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.view",clientLocale)%>
		    </button><!--客户信息--><!--查看-->
		</td>
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
		<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Argument.getManager_Name(service_man_value)%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
		<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--家庭电话-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
		</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--手机1-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--手机2-->
		<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=bp%>" size="20"></td>

	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>

	<tr>
		<td colspan="4"><br/><br/></td>
	</tr>
	<!--预约-->
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
			<td>
				<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onChange="javascript:showPreinfo();">
					<%--=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,12)--%>
					<%=Argument.getProductListOptions2(new Integer(2), "", input_operatorCode, preproduct_id+"-"+product_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum3",clientLocale)%> :</td><!--已预约份数-->
			<td><input readonly class="edline" name="fact_pre_num" onkeydown="javascript:nextKeyPress(this)" size="27" value=""></td>
			<td align="right"><%=LocalUtilis.language("class.factPreMoney",clientLocale)%> :</td><!--已预约总金额-->
			<td>
				<input readonly class="edline" name="fact_pre_money_view" onkeydown="javascript:nextKeyPress(this)" size="27" value="">
				<input type="hidden" name="fact_pre_money" onkeydown="javascript:nextKeyPress(this)" size="27" value="">
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--预约金额-->
			<td>
			   <input name="pre_money" size="27" value="<%=r_pre_money%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,pre_money_cn)">
			   <%if (reg_money!= null){%>
				<span id="pre_money_cn" class="span">(<%=reg_money_cn%>)</span>
    		    <%}
    			else {%>
    				<span id="pre_money_cn" class="span">&nbsp;</span><!--万-->
    			<%}%>
			</td><!--万-->
			<td align="right">*<%=LocalUtilis.language("class.preNum2",clientLocale)%> :</td><!--预约份数-->
			<td><select name="pre_num" ><%=Argument.getPreNumOptions(pre_num)%></select></td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--预约方式-->
			<td>
				<select size="1" name="pre_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getPreTypeOptions(pre_type)%>
				</select>
			</td>
			<td align="right">*<%=LocalUtilis.language("class.validDays",clientLocale)%> :</td><!--有效天数-->
			<td><input name="valid_days" size="27" value="<%=valid_days%>" onkeydown="javascript:nextKeyPress(this)">&nbsp;&nbsp;<%=LocalUtilis.language("message.days",clientLocale)%> <!--天--></td>
		</tr>
		<tr>
			<td align="right">预计认购日期:</td><!--预计认购日期-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker" class=selecttext
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_reg_date_picker,theform.exp_reg_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="">
			</td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--客户来源-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.contact",clientLocale)%> :</td><!--联系人-->
			<td >
				<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getOperatorOptions(service_man_value)%>
				</select>
			</td>
			<td align="right">*<%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--预约日期-->
			<td><INPUT TYPE="text" NAME="pre_date_picker" class=selecttext
			<%if(preDate==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date_picker,theform.pre_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="pre_date"   value=""></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</div>
<br/>
<div align="right" style="margin-right:10px;margin-top:10px;">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;<!--返回-->
</div>

<%
preContract.remove();
customer.remove();
%>

</form>
<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;

	if(v_bSuccess=="true"){
		sl_update_ok();
		CancelAction();
	}

	showPreinfo();
}
</script>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
