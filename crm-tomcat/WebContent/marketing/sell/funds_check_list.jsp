<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
//获得查询参数
/*int ismonyfh = Argument.getSyscontrolValue_intrust("ISMONYFH");
if(ismonyfh == 1)
	response.sendRedirect(request.getContextPath() + "/marketing/sell/square_manage_check.jsp");*/

Integer qs_date = Utility.parseInt(request.getParameter("qs_date"),new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));
java.math.BigDecimal min_rg_money=Utility.parseDecimal(request.getParameter("min_rg_money"),null);
java.math.BigDecimal max_rg_money=Utility.parseDecimal(request.getParameter("max_rg_money"),null);
String sContract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer q_product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
int query_flag = Utility.parseInt(request.getParameter("query_flag"),0);
//页面辅助参数
input_bookCode = new Integer(1);//帐套暂时设置
int iCount = 0;
String[] totalColumn = {"TO_MONEY","TO_AMOUNT"};
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&qs_date="+qs_date;
tempUrl = tempUrl+"&end_date="+end_date;
tempUrl = tempUrl+"&contract_sub_bh="+sContract_sub_bh;
tempUrl = tempUrl+"&product_id="+q_product_id;
tempUrl = tempUrl+"&min_rg_money="+min_rg_money;
tempUrl = tempUrl+"&max_rg_money="+min_rg_money;
sUrl = sUrl + tempUrl;
//获得对象
ToMoneyAccountLocal local = EJBFactory.getToMoneyAccount();
ToMoneyAccountVO vo = new ToMoneyAccountVO();
//查询参数
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setCheck_flag(new Integer(1));
vo.setContract_sub_bh(sContract_sub_bh);
vo.setProduct_id(q_product_id);
vo.setDz_date(qs_date);
vo.setEnd_date(end_date);
vo.setMin_to_money(min_rg_money);
vo.setMax_to_money(max_rg_money);
//查询结果
IPageList pageList = local.query_page(vo,totalColumn,t_sPage,t_sPagesize);
List list  = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode, q_product_id, "",input_operatorCode,0);
options = options.replaceAll("\"", "'");

local.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.financialAuditForMoney",clientLocale)%></TITLE><!--资金到帐财务审核-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

function checkInfo(){
	if(checkedCount(document.theform.s_id) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	var smess="";
	var v_date =  document.getElementsByName('to_bank_date_picker')[0].value;

	if(v_date.length!=0){
		if(!sl_checkDate(theform.to_bank_date_picker,"<%=LocalUtilis.language("class.toBankDate2",clientLocale)%> "))	return false;//到账日期
	    syncDatePicker(theform.to_bank_date_picker, theform.to_bank_date);
	    smess='<%=LocalUtilis.language("class.toBankDate2",clientLocale)%> :'+theform.to_bank_date.value+',';//到账日期
	}
	else if(v_date.length==""){
		smess="<%=LocalUtilis.language("message.toBankDateTip",clientLocale)%> ！";//到帐日期为空，将以缴款日期为到帐日期
	}
	if(confirm(smess+'\n<%=LocalUtilis.language("message.auditRecordsTip",clientLocale)%> ？')){//您确定对选定的记录进行审核吗
		disableAllBtn(true);
		document.theform.action="funds_check_action.jsp";
		document.theform.submit();
	}
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);		
	var url = 'funds_check_list.jsp?page=1&query_flag=1&pagesize='+ document.getElementsByName('pagesize')[0].value;	
	var url = url + '&qs_date=' + document.getElementsByName('qs_date')[0].value;
	var url = url + '&end_date=' + document.getElementsByName('end_date')[0].value;
	var url = url + '&min_rg_money=' + document.getElementsByName('min_rg_money')[0].value;
	var url = url + '&max_rg_money=' + document.getElementsByName('max_rg_money')[0].value;
	var url = url + '&contract_sub_bh=' + document.getElementsByName('contract_sub_bh')[0].value;
	var url = url + '&product_id=' + document.getElementsByName('product_id')[0].value;
	window.location = url;
}

function StartQuery(){
 	refreshPage();
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value){
	prodid=0;
	if (value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}	
		document.theform.product_id.focus();		
	}
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr> 
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--查询条件-->
			<td align="right">
				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>	
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--缴款日期-->
			<td align="left" colspan="3">
					<!--从-->
                    <%=LocalUtilis.language("message.start",clientLocale)%> <INPUT TYPE="text" NAME="qs_date_picker" class=selecttext size=14 onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(qs_date)%>">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="qs_date"   value="">
					<!--到-->
                    <%=LocalUtilis.language("message.end",clientLocale)%> <INPUT TYPE="text" NAME="end_date_picker" class=selecttext size=14 onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(end_date)%>">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="end_date"  value=""></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%> :</td><!--合同金额-->
			<!--从-->
            <td align="left" colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" maxlength="16" name="min_rg_money" value="<%=Utility.trimNull(min_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
            <!--到-->
            <%=LocalUtilis.language("message.end",clientLocale)%> <input type="text" maxlength="16" name="max_rg_money" value="<%=Utility.trimNull(max_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10"></td>
		</tr>
			<tr>	
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
				<td ><input type="text" name="contract_sub_bh" size="15" onkeydown="javascript:nextKeyPress(this)" value="<%=sContract_sub_bh%>"></td>										
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
				<td><input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button></td>
				<td  align="right">产品名称 :</td>
				<td>
					<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
				</td>
			</tr>
			<tr>	
				<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--产品选择-->
				<td align="left" colspan="3" id="select_id">
					<SELECT name="product_id" class="productname"><%=Argument.getProductListOptions(input_bookCode, q_product_id, "",input_operatorCode,0)%></SELECT>
				</td>				
			</tr>		
			<tr>
				<td align="center" colspan="4">
					<!--确认-->
                    <button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
				</td>
			</tr>	
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	
			<!--到账日期-->
			<div style="float:right; align=right" class="btn-wrapper">
				&nbsp;&nbsp;
					<font size=3 color="red">到账银行帐号</font>:<select size="1" name="sbf_serial_no"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 300px">
					<%=Argument.getbankOption(input_bookCode,q_product_id,null)%>  
				</select>&nbsp;&nbsp;<font size=3 color="red">
				<%=LocalUtilis.language("class.toBankDate2",clientLocale)%></font>:&nbsp;&nbsp;<INPUT TYPE="text" NAME="to_bank_date_picker" class=selecttext value="">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.to_bank_date_picker,theform.to_bank_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="to_bank_date"   value="">	
				<!--查询-->
                <button type="button"  class="xpbutton3" accessKey=q name="queryButton" id="queryButton" onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
				<%if (input_operator.hasFunc(menu_id, 103)) {%>
				<!--审核-->
                <button type="button"  class="xpbutton2" name="btnNew" title='<%=LocalUtilis.language("message.check",clientLocale)%>' onclick="javascript:checkInfo();"><%=LocalUtilis.language("message.check",clientLocale)%> </button>
				<%}%>
			</div>
	</div>
<br/>
<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr class="trh">
						<!--产品名称-->
                        <td align="center" width="*"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.s_id,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> </td>
						<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
						<td align="center" >银行名称</td><!-- 银行名称 -->
						<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
						<td align="center" >受益级别</td><!--受益级别-->
						<td align="center" >收益级别</td><!--收益级别-->
						<td align="center" width=""><%=LocalUtilis.language("class.dzDate",clientLocale)%> </td><!--缴款日期-->
						<td align="center"  width=""sort="num"><%=LocalUtilis.language("class.toMoney",clientLocale)%> </td><!--到账金额-->
						<td align="center"  width=""sort="num"><%=LocalUtilis.language("class.toAmount2",clientLocale)%> </td><!--到账份额-->
						<td align="center" width=""><%=LocalUtilis.language("class.feeType",clientLocale)%> </td><!--缴款方式-->
						<td align="center"  width="*"sort="num"><%=LocalUtilis.language("class.feeMoney",clientLocale)%> </td><!--手续费-->
						<td align="center" width="*"><%=LocalUtilis.language("class.description",clientLocale)%> </td><!--备注-->
					</tr>
<% 
//设置显示变量
Integer serial_no = null;
String product_name = "";
String contract_sub_bh = "";
String cust_name ="";
String jk_type_name = "";
String fee_type_name = "";
String summary = "";
Integer dz_date = new Integer(0);
Integer fee_type = new Integer(0);
Integer check_flag = new Integer(0);
java.math.BigDecimal to_money = null;
java.math.BigDecimal to_account = null;
java.math.BigDecimal fee_money = null;
//设置迭代器

//遍历迭代器
String sub_product_name = "";   
String prov_level_name = "";
String prov_flag_name = "";
String bank_name = "";
String bank_acct = "";
for(int i=0;i<list.size();i++){
		iCount++;
		Map map = (Map)list.get(i);
		fee_type_name ="";

		serial_no = Utility.parseInt((Integer)map.get("SERIAL_NO"),new Integer(0));
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		jk_type_name = Utility.trimNull(map.get("JK_TYPE_NAME"));
		summary = Utility.trimNull(map.get("SUMMARY"));
		dz_date = Utility.parseInt(Utility.trimNull(map.get(user_id.intValue()==17?"TO_BANK_DATE":"DZ_DATE")),new Integer(0));
		check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
		to_money = Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),new BigDecimal(0));
		to_account = Utility.parseDecimal(Utility.trimNull(map.get("TO_AMOUNT")),new BigDecimal(0));
		fee_money = Utility.parseDecimal(Utility.trimNull(map.get("FEE_MONEY")),new BigDecimal(0));
		sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"),"");
		prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
		prov_flag_name = Utility.trimNull(map.get("PROV_FLAG_NAME"));
		bank_name = Utility.trimNull(map.get("BANK_NAME"));
		bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
		if(!"".equals(sub_product_name) && sub_product_name!=null)
			sub_product_name = "("+sub_product_name+")";
		if(fee_money.intValue()>0){
			fee_type =  Utility.parseInt(Utility.trimNull(map.get("FEE_TYPE")),new Integer(0));

			if(fee_type.intValue()!=0){
				if(fee_type.intValue()==1)
					fee_type_name=enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//认购费
				if(fee_type.intValue()==2)
					fee_type_name=enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//申购费
				if(fee_type.intValue()==4)
					fee_type_name=enfo.crm.tools.LocalUtilis.language("class.transFee2",clientLocale);//份额转增费
			}
		}
%>
		<tr class="tr<%=(iCount % 2)%>">
			<td class="tdh" align="left" >
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><%if (check_flag.intValue() == 1){%><input class="flatcheckbox" type="checkbox" name="s_id" value="<%=serial_no%>"><%}%></td>
							<td width="90%"><p align="left"><%=product_name%><%=sub_product_name %></p></td>
						</tr>
					</table>
				</td>
				<td align="left" ><%=contract_sub_bh%></td>
				<td align="left" ><%=cust_name%></td>
				<td align="left" ><%=bank_name%></td>
				<td align="left" ><%=bank_acct%></td>
				<td align="left" ><%=prov_flag_name %></td>
				<td align="left" ><%=prov_level_name %></td>
				<td align="center" ><%=Format.formatDateCn(dz_date)%></td>						
				<td align="right" ><%=Format.formatMoney(to_money)%></td>
				<td align="right" ><%=Format.formatMoney(to_account)%></td>
				<td align="center" ><%=Utility.trimNull(jk_type_name)%></td>		
				<td align="right" ><%=Utility.trimNull(fee_type_name)%><%=Utility.trimNull(Format.formatMoney(fee_money))%></td>				
				<td align="right" ><%=Utility.trimNull(summary)%></td>
		</tr>
<% }
for(iCount=0;iCount<pageList.getBlankSize();iCount++){%>  
	      <tr class="tr<%=iCount%2%>">
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>                   
	         <td align="center">&nbsp;</td> 
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>                   
	         <td align="center">&nbsp;</td>                              
	      </tr>           
<%}%>   	

<tr class="trbottom">
		<!--合计--><!--项-->
        <td class="tdh" align="left" colspan="1"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		<td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>                   
	    <td align="center"><%=Format.formatMoney(pageList.getTotalValue("TO_MONEY")) %></td> 
	    <td align="center"><%=Format.formatMoney(pageList.getTotalValue("TO_AMOUNT")) %></td>
	    <td align="center">-</td>
	    <td align="center">-</td>                   
	    <td align="center">-</td>
	</tr>
	</table>
</div>
<br>
<div  class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>