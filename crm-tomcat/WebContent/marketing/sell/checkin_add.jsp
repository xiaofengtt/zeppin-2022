<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递变量
Integer cust_id =Utility.parseInt(request.getParameter("customer_cust_id"),new Integer(0));//客户ID
BigDecimal reg_money = Utility.parseDecimal(request.getParameter("s_money"), new BigDecimal(0),2,"1");
String invest_type=Utility.trimNull(request.getParameter("s_invest_type"));
String summary = Utility.trimNull(request.getParameter("s_summary"));
Integer reg_data = Utility.parseInt(request.getParameter("s_date"),new Integer(0));
Integer reg_valid_days = Utility.parseInt(request.getParameter("s_valid_days"),new Integer(0));
String invest_type_name = Utility.trimNull(request.getParameter("invest_type_name"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
//页面辅助变量
boolean bSuccess = false;
StringBuffer list = Argument.newStringBuffer();
input_bookCode = new Integer(1);//帐套暂时设置
//获取对象
PreContractLocal preContract = EJBFactory.getPreContract();//预登记信息
CustomerLocal customer = EJBFactory.getCustomer();//客户
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
PreContractVO pre_vo = new PreContractVO();
CustomerVO c_vo = new CustomerVO();
//声明变量
//--2.客户信息
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
String service_man_mobile = ""; //客户经理的手机号码
String smsContent = "短信内容";
//客户信息详细
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;
	//客户单个值
	c_vo.setCust_id(cust_id);
	c_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(c_vo);

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
//保存变量
if(request.getMethod().equals("POST")){
	c_vo = new CustomerVO();
	pre_vo = new PreContractVO();
	//客户信息数据打包
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(h_card_id);
	c_vo.setCard_type(card_type.toString());
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(h_mobile);
	c_vo.setE_mail(h_e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	//同步客户信息
	customer_inst.cope_customers(c_vo);
	//获取预约信息
	reg_money = Utility.parseDecimal(request.getParameter("reg_money"), new BigDecimal(0));
	invest_type = Utility.trimNull(request.getParameter("invest_type"));
	invest_type_name = Utility.trimNull(request.getParameter("invest_type_name"));
	summary = Utility.trimNull(request.getParameter("summary"));
	reg_data = Utility.parseInt(request.getParameter("reg_data"),new Integer(0));
	reg_valid_days = Utility.parseInt(request.getParameter("reg_valid_days"),new Integer(0));
	customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
	//预登记信息数据打包
	pre_vo.setBook_code(input_bookCode);
	pre_vo.setCust_id(cust_id);
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setReg_money(reg_money);
	pre_vo.setSummary(summary);
	pre_vo.setReg_date(reg_data);
	pre_vo.setReg_valid_days(reg_valid_days);
	pre_vo.setInvest_type(invest_type);
	pre_vo.setInvest_type_name(invest_type_name);
	pre_vo.setCust_source(customer_cust_source);
	pre_vo.setLink_man(link_man);
	//预登记信息保存
	preContract.append_reginfo(pre_vo);

	//查询客户经理信息
	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),new Integer(0));
	if(link_man.intValue() != 0){
		List op_list = Argument.getOperatorInfoByOpCode(link_man);
		Map op_map = new HashMap();
		if(op_list != null && op_list.size() != 0){
			op_map = (Map)op_list.get(0);
			service_man_mobile = Utility.trimNull(op_map.get("MOBILE"));
			smsContent = Utility.trimNull(op_map.get("OP_NAME")) + "您好！客户：" + cust_name + "已经预登记成功；联系电话：" +cust_tel+ "；登记金额：" + Format.formatMoney(reg_money) + "元。请尽快联系！";
		}
	}
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.checkinAdd",clientLocale)%> </TITLE>
<!--预登记新增-->
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language=javascript>
//获取轮到的团队联系人
/*function getTeamInfoByRegMoney(reg_money){
	var str = "";
	contract.getTeamInfoByRegMoney('<%=input_bookCode%>',reg_money,{callback: function(data){
		if(data[0] != null){
			str = "<font color='red'>(注意：当前应该分配的团队："+data[0]+"[负责人："+data[2]+"])</font>";
			document.getElementById("teaminfo").innerHTML = str;
			
			//选中联系人
			var items = document.theform.link_man;
			for(var i=0; i<items.options.length; i++){
				if(items[i].value == data[1]){
					items[i].selected = true;
					break;
				}
			}
		}else{
			str = "<font color='red'>(注意：当前没有营销团队供分配，请设置团队信息。)</font>";
			document.getElementById("teaminfo").innerHTML = str;
		}
	}});
}*/

window.onload = function(){
	//getTeamInfoByRegMoney(document.theform.reg_money.value);
	var v_bSuccess = document.getElementById("bSuccess").value;

	<%if(bSuccess==true){%>
		var messsage = "移动号码为空或格式不正确，不能及时发送短信请尽快维护！";
		//1.发送短信通知客户经理
		<%if(service_man_mobile != "" && service_man_mobile.length() == 11){%>
		ccService.sendSMSSimple("<%=input_operatorCode%>","<%=service_man_mobile%>","<%=smsContent%>","1","待发",0,"<%=input_operatorCode%>",{callback:function(data){
			var arr = data.split("|");
			message = arr[1];//sl_alert(arr[1]);
		}});
		<%}%>
		
		//2.预登记信息保存成功
		sl_alert("<%=LocalUtilis.language("message.checkinInfoSaveOK",clientLocale)%> ！");
		location = "checkin_list.jsp";
	<%}else{%>
		//getTeamInfoByRegMoney(0);
	<%}%>
}

/*保存*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="checkin_add.jsp"
		document.theform.submit();
	}
}
/*下面是新增客户时用到的函数*/
function validateForm(form){
	if(form.customer_cust_id.value==""||form.customer_cust_id.value==0){
		sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
		return false;
	}
	if(form.reg_money.value==0){
		alert("登记额度不能为0");
		return flase;
	}
	if(!sl_checkDecimal(form.reg_money,"<%=LocalUtilis.language("class.regMoney",clientLocale)%> ",13,3,1))return false;//登记额度
	//if(!sl_checkNum(form.reg_valid_days,"<%=LocalUtilis.language("class.validDays",clientLocale)%> ",8,1))return false;//有效天数
	if(!sl_checkDate(form.reg_date_picker,"<%=LocalUtilis.language("class.regDate",clientLocale)%> "))return false;//登记日期
	<%if(user_id.intValue() != 2) {%>
		//if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))return false;//客户来源
		//if(!sl_check(form.invest_type_name, "<%=LocalUtilis.language("class.investType",clientLocale)%> ",60,1))return false;//预计投向
	<%}%>
	if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//联系人

	syncDatePicker(form.reg_date_picker, form.reg_date);
	return sl_check_update();
}
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		reg_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//万
	else
		reg_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
/*刷新页面*/
function refreshPage(){
	var url = 'checkin_add.jsp?s_summary='+ document.theform.summary.value;
	var url = url + '&s_money=' + document.theform.reg_money.value;
	//var url = url + '&s_invest_type=' + document.theform.invest_type.value;
	var url = url + '&s_valid_days=' + document.theform.reg_valid_days.value;
	var url = url + '&s_date=' + document.theform.reg_date_picker.value;
	var url = url + '&customer_cust_id=' + document.theform.customer_cust_id.value;
	location = url;
}
/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;

	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2&prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;

	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v != null){
		document.theform.customer_cust_id.value =  v[7];
		refreshPage();
	}
	return (v != null);
}
/*多选投向*/
function chooseAction(){
	var url = "<%=request.getContextPath()%>/marketing/invest_type_check.jsp"
	var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');

	if(ret!=null){
		document.getElementById("invest_type").value = ret[0];
		document.getElementById("invest_type_name").value = ret[1];
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript: return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="customer_cust_id" name="customer_cust_id" value="<%=cust_id%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<!--客户选择-->
<div vAlign=top align=left>
	<table cellSpacing=2 cellPadding=2 width="100%" border=0 class="product-list">
		<tr>
			<td align="left"><font color="#215dc6"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></font></td><!--客户信息-->
			<td align="left">
			    <button type="button"   class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> "
			        onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.select2",clientLocale)%>
			    </button><!--客户信息--><!--请选择-->
			</td>
		<tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td colspan=3><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
			<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
			<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_card_id%>" size="30"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.telephone",clientLocale)%> :</td><!--住宅电话-->
			<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_h_tel%>" size="20"></td>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
			<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_mobile%>" size="30"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
			<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
			<td ><INPUT readonly class='edline' name="customer_post_code" size="30" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
		</tr>
		<tr>
			<td colspan="4">
			<br/><br/>
			</td>
		</tr>
<!--预登记信息-->
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.regMoney",clientLocale)%> :</td><!--登记额度-->
			<td>
				<input name="reg_money" size="27" value="<%=reg_money%>" onblur="javascript:getTeamInfoByRegMoney(this.value);" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.reg_money,'<%=enfo.crm.tools.LocalUtilis.language("class.regMoney",clientLocale) %>',13,3,0);showCnMoney(this.value)"/>
				<!--登记额度-->
				<span id="reg_money_cn" class="span">&nbsp;</span><!--万-->
			</td>
			<td align="right"><%=LocalUtilis.language("class.validDays",clientLocale)%> :</td><!--有效天数-->
			<td><input name="reg_valid_days" type="text" size="27" value="<%=reg_valid_days%>"/>&nbsp;&nbsp;<%=LocalUtilis.language("message.days",clientLocale)%> <!--天--></td>
		</tr>
		<tr>
			<%if(user_id.intValue() != 2) {%>
			<td align="right"><%=LocalUtilis.language("class.investType",clientLocale)%> :</td><!--预计投向-->
			<td>
				<!--
				<select size="1" name="invest_type"	onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getDictParamOptions_intrust(1131,invest_type)%>
				</select>
				-->
				<input type="text" name="invest_type_name" id="invest_type_name" size="27"  value="<%=invest_type_name%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<input type="hidden" name="invest_type" id="invest_type" value="" />
				<!-- 选择 -->&nbsp;&nbsp;
                <button type="button"   class="xpbutton2" id="btnChoInvestType" name="btnChoInvestType" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
			</td>
			<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 160px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>
			<%} %>	
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.regDate",clientLocale)%> :</td><!--登记日期-->
			<td >
			<input onkeydown="javascript:nextKeyPress(this)" size="27" name="reg_date_picker" class=selecttext
					<%if (reg_data.intValue() == 0|| reg_data == null) {%>
					value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}
					else {%>value="<%=Format.formatDateLine(reg_data)%>"<%}%>  />
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.reg_date_picker,theform.reg_date_picker.value,this);" tabindex="13">
			<input type="hidden" name="reg_date" value="">
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.contact",clientLocale)%>:</td><!--联系人-->
			<td colspan="3">
			    <select name="link_man" onkeydown="javascript:nextKeyPress(this)" style="width:160px">
			        <%=Argument.getManager_Value(link_man)%>				    
			    </select>
			</td>
		</tr>
		<tr>
			<td align="right" vAlign="top"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
			<td colspan="3"><textarea cols="100" rows="6" name="summary"><%=summary%></textarea></td>
		</tr>
	</table>
</div>

<div align="right" style="margin-right:5px">
	<br>
	<button type="button"   class="xpbutton3" id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"   class="xpbutton3" id="btnCancel" name="btnCancel" onclick="javascript:location='checkin_list.jsp'"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--返回-->
</div>

<%
preContract.remove();
customer_inst.remove();
%>

</form>
<%@ include file="/includes/foot.inc"%>
<script language=javascript>

</script>
</BODY>
</HTML>