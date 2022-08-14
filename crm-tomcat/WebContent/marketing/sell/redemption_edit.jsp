<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer ben_serial_no = Utility.parseInt(request.getParameter("ben_serial_no"),new Integer(0));
int flag = Utility.parseInt(request.getParameter("flag"),1);
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));

//页面辅助参数
boolean bSuccess = false;
String readonly = "";
String stylenone = "";
String disabled = "";

//帐套暂时设置
input_bookCode = new Integer(1);

//声明参数
String contract_bh = "";
Integer qs_date = new Integer(0);//合同签署日期
BigDecimal ben_amount = new BigDecimal(0);
Integer check_flag = new Integer(0);
Integer sq_date = new Integer(0);//sq_date
BigDecimal redeem_amout = new BigDecimal(0);
BigDecimal redeem_amout0 = new BigDecimal(0);
BigDecimal frozen_tmp = new BigDecimal(0);
Integer coerce_flag = new Integer(0);
BigDecimal redeem_amount_coerce = new BigDecimal(0);
Integer transfer_product_id = null;
Integer transfer_sub_product_id = null;
BigDecimal transfer_money = null;
String bankName = "";
String bankAcct = "";
//数据保存
List list_redeem = null;
Map map_redeem = null;
List list_contract = null;
Map map_contract = null;
List list_benifitor = null;
Map map_benifitor = null;

//获取对象
RedeemLocal redeem = EJBFactory.getRedeem();
ContractLocal contract = EJBFactory.getContract();
BenifitorLocal ben =EJBFactory.getBenifitor();

RedeemVO vo_redeem = new RedeemVO();
ContractVO vo_contract = new ContractVO();
BenifitorVO vo_benifitor = new BenifitorVO();

if(request.getMethod().equals("POST")){		
	//保存信息
	sq_date = Utility.parseInt(request.getParameter("sq_date"),null);
	redeem_amout = Utility.parseDecimal(request.getParameter("redeem_amout"),null);
	
	vo_redeem.setSerial_no(serial_no);
	vo_redeem.setSq_date(sq_date);
	vo_redeem.setRedeem_amout(redeem_amout);
	vo_redeem.setInput_man(input_operatorCode);
	if(Utility.trimNull(request.getParameter("has_transfer")).equals("1")){
		vo_redeem.setTransfer_product_id(Utility.parseInt(request.getParameter("transfer_product_id"),null));
		vo_redeem.setTransfer_sub_product_id(Utility.parseInt(request.getParameter("transfer_sub_product_id"),null));
		vo_redeem.setTransfer_money(Utility.parseDecimal(request.getParameter("transfer_money"),null));
	}
	redeem.modi(vo_redeem);
	bSuccess = true;
}
else{
	//显示信息
	if(serial_no.intValue()!=0&&flag==1){
		//赎回信息
		vo_redeem.setSerial_no(serial_no);
		vo_redeem.setCheck_flag(null);
		vo_redeem.setInput_man(input_operatorCode);		
		list_redeem = redeem.listBySql(vo_redeem);
		
		if(list_redeem.size()>0){
			map_redeem = (Map)list_redeem.get(0);
		}
		
		if(map_redeem!=null){
			product_id = Utility.parseInt(Utility.trimNull(map_redeem.get("PRODUCT_ID")),new Integer(0));
			contract_bh = Utility.trimNull(map_redeem.get("CONTRACT_BH"));
			redeem_amout = Utility.parseDecimal(Utility.trimNull(map_redeem.get("REDEEM_AMOUNT")),null);
			redeem_amout0 = Utility.parseDecimal(Utility.trimNull(map_redeem.get("REDEEM_AMOUNT0")),null);
			sq_date = Utility.parseInt(Utility.trimNull(map_redeem.get("SQ_DATE")),null);	
			check_flag = Utility.parseInt(Utility.trimNull(map_redeem.get("CHECK_FLAG")),null);	
			coerce_flag = Utility.parseInt(Utility.trimNull(map_redeem.get("COERCE_FLAG")),new Integer(0));
			redeem_amount_coerce = Utility.parseDecimal(Utility.trimNull(map_redeem.get("REDEEM_AMOUNT_COERCE")),null);
			transfer_product_id = Utility.parseInt(Utility.trimNull(map_redeem.get("TRANSFER_PRODUCT_ID")),null);
			transfer_sub_product_id = Utility.parseInt(Utility.trimNull(map_redeem.get("TRANSFER_SUB_PRODUCT_ID")),null);
			transfer_money = Utility.parseDecimal(Utility.trimNull(map_redeem.get("TRANSFER_MONEY")),null);
				
		}
		if(check_flag.intValue()>1){
			readonly = "class='edline' readonly";
			stylenone = "style='display: none;'";
			disabled = "disabled";
		}
	}
	
	//取得可赎回份额
	vo_benifitor.setSerial_no(ben_serial_no);
	list_benifitor = ben.load(vo_benifitor);
		  
	if(list_benifitor.size()>0){
	  map_benifitor = (Map)list_benifitor.get(0);
	}
	
	if(map_benifitor!=null){
		ben_amount = Utility.parseDecimal(Utility.trimNull(map_benifitor.get("BEN_AMOUNT")),new BigDecimal(0));
		frozen_tmp = Utility.parseDecimal(Utility.trimNull(map_benifitor.get("FROZEN_TMP")),new BigDecimal(0));
		bankName = Utility.trimNull(map_benifitor.get("BANK_NAME"));
		bankAcct = Utility.trimNull(map_benifitor.get("BANK_ACCT"));
		if(frozen_tmp!=null){
			ben_amount = ben_amount.subtract(frozen_tmp);
		}		
	}
	
	//取得合同签署日期	
	vo_contract.setBook_code(input_bookCode);
	vo_contract.setProduct_id(product_id);
	vo_contract.setContract_bh(contract_bh);	  
	list_contract = contract.queryPurchanseContract(vo_contract);
	 
	  
	if(list_contract.size()>0){
		map_contract = (Map)list_contract.get(0);
		qs_date = Utility.parseInt(Utility.trimNull(map_contract.get("QS_DATE")),new Integer(0));
	}
}
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
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript">
/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action ="redemption_edit.jsp";
		document.theform.submit();
	}
}
//下面是新增客户时用到的函数
function validateForm(theform){
	if(!sl_checkChoice(theform.product_id,'<%=LocalUtilis.language("class.productName",clientLocale)%> ')) return false;//产品名称
	if(!sl_checkChoice(theform.contract_bh,'<%=LocalUtilis.language("class.contractID",clientLocale)%> ')) return false;//合同编号
	//if(!sl_checkChoice(theform.ben_serial_no,'<%=LocalUtilis.language("class.qCustName",clientLocale)%> ')) return false;//受益人姓名
	theform.redeem_amout.value = sl_parseFloat(theform.redeem_amout.value);
	if(sl_parseFloat(theform.redeem_amout.value)>sl_parseFloat(theform.money.value)){
		//申请赎回份额//不能大于//可赎回金额
        sl_alert("<%=LocalUtilis.language("class.redeemAmout'/><fmt:message key='massage.notGreater'/><fmt:message key='class.money2",clientLocale)%> ");
		theform.redeem_amout.focus();	
		return false;
	}	
	if(!sl_checkDate(document.theform.change_date_picker,"<%=LocalUtilis.language("class.changeDate3",clientLocale)%> "))return false;
	syncDatePicker(document.theform.change_date_picker, document.theform.sq_date);
	syncDatePicker(document.theform.qs_date, document.theform.qs_date);
	if(document.theform.sq_date.value <= document.theform.qs_date.value)
	{	
		 //申请办理日期//必须晚于//合同签署日期
		sl_alert("<%=LocalUtilis.language("class.changeDate3'/><fmt:message key='message.beLater'/><fmt:message key='class.qsDate",clientLocale)%> ");//申请办理日期
		return false;
	}	

	if(theform.has_transfer.value == 1){
		if(theform.transfer_product_id.value != 0){
			if(!(document.getElementById("tr_transfer2").style.display == "none")){
				if(!sl_checkChoice(theform.transfer_sub_product_id,"子产品"))
					return false;
			}
			if(!sl_checkDecimal(theform.transfer_money,"转入金额",13,3,1)) return false;
			if(parseFloat(theform.transfer_money.value) > parseFloat(theform.redeem_amout.value)){
				if(!confirm("确认转入金额大于申请赎回金额!"))
					return false;
			}
		}
	}

	if(theform.coerce_redeem_flag.value == 1){
		var money = theform.money.value.replace(/,/g,"");
		var rest_amout = null;
		if(theform.cust_type.value == 1){
			rest_amout = theform.min_redeem_vol.value;
		}else if(theform.cust_type.value == 2){
			rest_amout = theform.min_redeem_vol2.value;
		}else{
			alert("获取客户类型失败，或者客户类型未设置!");
			return false;
		}
		if((parseFloat(money) - parseFloat(theform.redeem_amout.value)) < parseFloat(rest_amout) && (parseFloat(money) - parseFloat(theform.redeem_amout.value)) != parseFloat(0)){
			var msg = "当前产品设置了强制赎回，目前剩余份额" + (parseFloat(money) - parseFloat(theform.redeem_amout.value)) +
				      "小于强制赎回的最少保留份额" + rest_amout + ",将进行强制赎回，是否继续操作?";
			if(!confirm(msg)){
				return false;
			}
		}
	}

	return sl_check_update();
}

 //根据输入数字显示相应的中文名  taochen
function showCnNum(value,name){	
	if (trim(value) == "")
		name.innerText = "";
	else
		name.innerText = "(" + numToChinese2(value) + ")";
}

function numToChinese2(input){
	var s1 = "零壹贰叁肆伍陆柒捌玖";
	var s4 = "拾佰仟万拾佰仟亿拾佰仟";
	var temp = "";
	var result = "";
	var bZero = false;

	if (input == null)
		//输入字串不是数字串//只能包括以下字符//输入字串最大只能精确到仟亿，小数点只能两位
        return "<%=LocalUtilis.language("message.stringTip'/>，<fmt:message key='message.includeChara'/>（'0'～'9'，'.')，<fmt:message key='message.numToChineseTip",clientLocale)%> ！";
	
	
	var temp = trim(input);

	var f = parseFloat("0" + temp);
	var len = 0;
	if (temp.indexOf(".") == -1)
		len = temp.length;
	else
		len = temp.indexOf(".");
	if (len > s4.length)
		return "<%=LocalUtilis.language("message.numToChineseTip",clientLocale)%> ！";//输入字串最大只能精确到仟亿，小数点只能两位

	var n1, n2 = 0;
	var num = "";
	var unit = "";

	for (var i = 0; i < temp.length; i++){
		if (i > len + 2){
			break;
		}
		if (i == len){
			unit="点";
			result = result + unit;
			continue;
		}
		
		n1 = parseInt(temp.charAt(i));
		num = s1.substring(n1, n1 + 1);
		n2 = len-i-2;
		unit = s4.substring(n2, n2 + 1);	
		result = result + num + unit;
	}
	if ((len == temp.length) || (len == temp.length - 1))
		result = result + "整";
		
	result = trimZero(result)+"份";
	return result;
}


function showCnMoney(value,name){	
	if (trim(value) == ""){
		name.innerText = "";
		document.getElementById("tr_" + name.id).style.display = "none";
	}else{
		name.innerText = "(" + numToChinese2(value) + ")";
		document.getElementById("tr_" + name.id).style.display = "";
	}
}

function getGainFlag(){
	if(<%=user_id.intValue()%> == 2){
		var product_id = document.theform.product_id.value;
		utilityService.getColumnOfTproductLimit(product_id,"GAIN_FLAG",function(data){
			if(data == 5){
				document.getElementById("tr_other_opration").style.display = "";
				document.getElementById("tr_transfer1").style.display = "";
				document.getElementById("tr_transfer2").style.display = "";
				document.getElementById("tr_transfer3").style.display = "";
				document.theform.has_transfer.value = 1;
				getSubProduct(document.theform.transfer_product_id.value);
			}else{
				document.getElementById("tr_other_opration").style.display = "none";
				document.getElementById("tr_transfer1").style.display = "none";
				document.getElementById("tr_transfer2").style.display = "none";
				document.getElementById("tr_transfer3").style.display = "none";
				document.theform.has_transfer.value = "";
			}	
		})
	}
}

function getSubProduct(product_id){
	DWRUtil.removeAllOptions("transfer_sub_product_id");
	DWRUtil.addOptions("transfer_sub_product_id",{"0":"请选择"});
	utilityService.getIsChildProduct(product_id,function(data){
		if(data==1){
			document.getElementById("tr_transfer2").style.display = "";
		}else{
			document.getElementById("tr_transfer2").style.display = "none";
		}
	});
	if(product_id != 0){
		utilityService.getSubProductJson(product_id,3,function(data){
			var json = eval(data);
			for(i=0;i<json.length;i++){
				DWRUtil.addOptions("transfer_sub_product_id",json[i]);
		    }
			DWRUtil.setValue('transfer_sub_product_id',<%=transfer_sub_product_id%>);
	  	});
	}
}

function getCoerceRedeemInfo(){
	var product_id = document.theform.product_id.value;
	utilityService.getColumnOfTproductLimit(product_id,"COERCE_REDEEM_FLAG",function(data){
		document.theform.coerce_redeem_flag.value = data;
	})
	utilityService.getColumnOfTproductLimit(product_id,"MIN_REDEEM_VOL",function(data){
		document.theform.min_redeem_vol.value = data;
	})
	utilityService.getColumnOfTproductLimit(product_id,"MIN_REDEEM_VOL2",function(data){
		document.theform.min_redeem_vol2.value = data;
	})
}

function getCustType(){
	var benifitor_serial_no = document.theform.ben_serial_no.value; 
	utilityService.getCustomerinfoBySerialNoOfTbenifitor(benifitor_serial_no,"CUST_TYPE",function(data){
		document.theform.cust_type.value = data;
	});
}

window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){		
		sl_alert("<%=LocalUtilis.language("message.editRedemptionInfo",clientLocale)%> ！");//赎回信息编辑成功
		location = "redemption_list.jsp?product_id=<%=product_id%>";
	}
	getGainFlag();
	getCoerceRedeemInfo();
	getCustType();
}
</SCRIPT>
</HEAD>

<BODY class="body body-nox">
<FORM name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="bSuccess" id="bSuccess" value="<%= bSuccess%>"/>
<input type="hidden" name="serial_no" value="<%= serial_no%>"/>
<input type="hidden" id="has_transfer" name="has_transfer">
<input type="hidden" id="coerce_redeem_flag" name="coerce_redeem_flag">
<input type="hidden" id="min_redeem_vol" name="min_redeem_vol">
<input type="hidden" id="min_redeem_vol2" name="min_redeem_vol2">
<input type="hidden" id="cust_type" name="cust_type">
<div align="left" class="page-title">
	<!--销售管理--><!--赎回-->
    <font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("message.redemption",clientLocale)%> </b></font>
</div>
<br/>
<div>
<TABLE cellpadding="0" cellspacing="10" width="100%" class="product-list">	
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</TD><!--产品名称-->
		<TD>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="product_id" class=productname onChange="javascript:selectOthers(1);getGainFlag();" <%if(flag==1) out.print("disabled");%>>
				<%=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,28)%>
			</select>
		</TD>
		<TD align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</TD><!--合同编号-->
		<TD>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="contract_bh" style="width: 150px" onChange="javascript:selectOthers(2);" <%if(flag==1) out.print("disabled");%>>
				<%=Argument.getContract(input_bookCode,product_id,contract_bh,input_operatorCode)%>
			</select>
		</TD>
	</TR>
	<TR>
		<TD colspan="4"><font color="red"><b><%=LocalUtilis.language("message.custInfo",clientLocale)%> :</b></font></TD><!--受益人信息-->
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.qCustName",clientLocale)%> :</TD><!--受益人姓名-->
		<TD>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="ben_serial_no"  onChange="javascript:selectOthers(3);" <%if(flag==1) out.print("disabled");%>>
				<%=Argument.getFromCustIdOptions(input_bookCode,product_id,contract_bh,ben_serial_no,input_operatorCode)%>
			</select>
		</TD>
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.money2",clientLocale)%> :</TD><!--可赎回份额-->
		<TD><input type="text" class="edline" readonly name="money" value="<%=Format.formatMoney(ben_amount)%>" size="25"></TD>
		<TD align="right"><%=LocalUtilis.language("class.qsDate",clientLocale)%> :</TD><!--签署日期-->
		<TD><input type="text" class="edline" readonly name="qs_date" value="<%=Format.formatDateLine(qs_date)%>" size="25"></TD>
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</TD><!--银行名称-->
		<TD><input type="text" class="edline" readonly name="bank_name" value="<%=bankName%>" size="25"></TD>
		<TD align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</TD><!--银行账户-->
		<TD><input type="text" class="edline" readonly name="bank_acct" value="<%=bankAcct%>" size="25"></TD>
	</TR>
	<TR>
		<TD colspan="4"><font color="red"><b><%=LocalUtilis.language("message.redemptionInfo",clientLocale)%> :</b></font></TD><!--赎回信息-->
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.redeemAmout",clientLocale)%> :</TD><!--申请赎回份额-->
		<TD>
			<input type="text" <%=readonly%> name="redeem_amout" value="<%=Format.formatMoney(redeem_amout)%>" size="25" 
			onkeydown="javascript:nextKeyPress(this)" 
			onblur="javascript:if(sl_checkDecimal(document.getElementsByName('redeem_amout')[0],'<%=LocalUtilis.language("class.redeemAmout",clientLocale)%> ',13,2,1)){showCnMoney(this.value,contractsum_cn);}">
		</TD>
		<TD align="right"><%=LocalUtilis.language("class.changeDate3",clientLocale)%> :</TD><!--申请办理日期-->
		<TD>
			<INPUT TYPE="text" <%=readonly%> NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(sq_date)%>">
			<INPUT TYPE="button" <%=stylenone%> value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13" >
			<INPUT TYPE="hidden" NAME="sq_date"   value="">
		</TD>
	</TR>
	<TR id="tr_contractsum_cn" style="display:none;">
		<TD></TD>
		<TD colspan="3"><span id="contractsum_cn" class="span">&nbsp;</span></TD>
	</TR>
	<TR>
		<TD align="right">实际赎回份额:</TD>
		<TD>
			<input type="text" class="edline" readonly value="<%=Format.formatMoney(redeem_amout0)%>" size="25" >
		</TD>
		<td align="right">是否强制赎回:</td>
		<td>
			<INPUT readonly TYPE="text" class=edline  value="<%=coerce_flag.intValue() == 1 ? "是:" +  Format.formatMoney2(redeem_amount_coerce): "否"%>">
		</td>
	</TR>
	<tr id="tr_other_opration" style="display:none;">
		<td colspan="4"><font color="red"><b>其它操作</b></font></td>
	</tr>
	<tr id="tr_transfer1" style="display:none;">	
		<td align="right">转入其它产品:</td>
		<td colspan="3">
			<select id="transfer_product_id" name="transfer_product_id" style="width: 350px;" onchange="javascript:getSubProduct(this.value);" <%=disabled%>>
				<%//=Argument.getProductListOptions(input_bookCode,transfer_product_id,"",input_operatorCode,25) %>
				<%=Argument.getPreProductListOptions(transfer_product_id,"","110202",input_operatorCode)%>
			</select>
		</td>
	</tr>
	<tr id="tr_transfer2" style="display:none;">	
		<td align="right">子产品:</td>
		<td colspan="3">
			<select id="transfer_sub_product_id" name="transfer_sub_product_id" style="width: 350px;" <%=disabled%>></select>
		</td>
	</tr>
	<tr id="tr_transfer3" style="display:none;">	
		<td align="right">转入金额:</td>
		<td colspan="3">
			<input type="text" id="transfer_money" name="transfer_money" onkeyup="javascript:showCnNum(this.value,transfer_money_cn)" value="<%=Format.formatMoney(transfer_money)%>" <%=readonly%>>
			<span id="transfer_money_cn" class="span"></span>
		</td>
	</tr>
</TABLE>
</div>

<br>

<div align="right">
	<!--保存-->
    <button type="button"  <%=disabled%> class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.history.back(-1);"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>

</FORM>	
<%
redeem.remove();
ben.remove();
contract.remove();
%>
</BODY>
</HTML>



