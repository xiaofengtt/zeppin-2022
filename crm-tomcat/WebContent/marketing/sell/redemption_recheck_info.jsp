<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer ben_serial_no = Utility.parseInt(request.getParameter("ben_serial_no"),new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));

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



//显示信息
if(serial_no!=null&&serial_no.intValue()!=0){
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
/* 审核*/
function CheckAction(){	
	if(confirm("<%=LocalUtilis.language("message.recoverDate",clientLocale)%> ？")){	//确认要对已审核数据恢复吗
		disableAllBtn(true);
		document.theform.action ="redemption_recheck_action.jsp";
		document.theform.submit();
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

window.onload = function(){
	getGainFlag();
}
</SCRIPT>
</HEAD>

<BODY>
<FORM name="theform" method="post">
<input type="hidden" name="serial_no" value="<%= serial_no%>"/>
<input type="hidden" id="has_transfer" name="has_transfer">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>

<div>
<TABLE cellpadding="0" cellspacing="10" width="100%">	
	<TR>
		<TD colspan="4"><font color="red"><b><%=LocalUtilis.language("message.basicInformation",clientLocale)%> :</b></font></TD><!--基本信息-->
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</TD><!--产品名称-->
		<TD>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="product_id" class=productname onChange="javascript:selectOthers(1);" disabled>
				<%=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,28)%>
			</select>
		</TD>
		<TD align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</TD><!--合同编号-->
		<TD>
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="contract_bh" style="width: 150px" onChange="javascript:selectOthers(2);" disabled>
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
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="ben_serial_no"  onChange="javascript:selectOthers(3);" disabled>
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
		<TD colspan="4"><font color="red"><b><%=LocalUtilis.language("message.redemptionInfo",clientLocale)%> :</b></font></TD><!--赎回信息-->
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.redeemAmout",clientLocale)%> :</TD><!--申请赎回份额-->
		<TD>
			<input type="text" class="edline" readonly name="redeem_amout" value="<%=Format.formatMoney(redeem_amout)%>" size="25" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,contractsum_cn)">
		</TD>
		<TD align="right"><%=LocalUtilis.language("class.changeDate3",clientLocale)%> :</TD><!--申请办理日期-->
		<TD>
			<INPUT TYPE="text" class="edline" readonly NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(sq_date)%>">
		</TD>
	</TR>
	<TR style="display:none;">
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
			<select id="transfer_product_id" name="transfer_product_id" style="width: 350px;" onchange="javascript:getSubProduct(this.value);" disabled>
				<%=Argument.getProductListOptions(input_bookCode,transfer_product_id,"",input_operatorCode,25) %>
			</select>
		</td>
	</tr>
	<tr id="tr_transfer2" style="display:none;">	
		<td align="right">子产品:</td>
		<td colspan="3">
			<select id="transfer_sub_product_id" name="transfer_sub_product_id" style="width: 350px;" disabled></select>
		</td>
	</tr>
	<tr id="tr_transfer3" style="display:none;">	
		<td align="right">转入金额:</td>
		<td colspan="3">
			<input type="text" id="transfer_money" name="transfer_money" value="<%=Format.formatMoney(transfer_money)%>" class='edline' readonly>
			<span id="transfer_money_cn" class="span"></span>
		</td>
	</tr>
</TABLE>
</div>

<br>

<div align="right">
	<!--审核恢复-->
    <button type="button"  class="xpbutton5" accessKey=s id="btnSave" name="btnSave" onclick="javascript:CheckAction();"><%=LocalUtilis.language("message.auditRecovery",clientLocale)%> (<u>S</u>)</button>
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



