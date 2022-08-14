<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ include file="/includes/operator.inc" %>

<%
try{
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer qualified_flag = Utility.parseInt(request.getParameter("qualified_flag"),new Integer(1));
Integer qualified_num = Utility.parseInt(request.getParameter("qualified_num"),new Integer(0));
Integer asfund_flag = Utility.parseInt(request.getParameter("asfund_flag"),new Integer(1)) ;
Integer gain_flag = Utility.parseInt(request.getParameter("gain_flag"),new Integer(1)) ;
Integer open_gain_flag = Utility.parseInt(request.getParameter("open_gain_flag"),new Integer(2)) ;
BigDecimal qualified_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("qualified_money")),new BigDecimal(0));
Integer is_bank_consign = Utility.parseInt(request.getParameter("is_bank_consign"),new Integer(2)); //是否银行代销 1是、2否 默认否

BigDecimal jg_min_subamount = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("jg_min_subamount"))),new BigDecimal(0)); 
BigDecimal gr_min_subamount = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("gr_min_subamount"))),new BigDecimal(0)); 
BigDecimal jg_min_bidsamount = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("jg_min_bidsamount"))),new BigDecimal(0)); 
BigDecimal gr_min_bidsamount = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("gr_min_bidsamount"))),new BigDecimal(0)); 
BigDecimal jg_min_appbidsamount = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("jg_min_appbidsamount"))),new BigDecimal(0)); 
BigDecimal gr_min_appbidsamount = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("gr_min_appbidsamount"))),new BigDecimal(0)); 
BigDecimal min_redeem_vol = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("min_redeem_vol"))),new BigDecimal(0));
BigDecimal min_redeem_vol2 = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("min_redeem_vol2"))),new BigDecimal(0)); 
Integer large_redeem_flag = Utility.parseInt(Utility.trimNull(request.getParameter("large_redeem_flag")), new Integer(2));
Integer large_redeem_condition = Utility.parseInt(Utility.trimNull(request.getParameter("large_redeem_condition")), new Integer(1));
BigDecimal large_redeem_percent = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("large_redeem_percent"))),new BigDecimal(0)); 
Integer large_redeem_deal = Utility.parseInt(Utility.trimNull(request.getParameter("large_redeem_deal")), new Integer(1));
Integer coerce_redeem_flag = Utility.parseInt(Utility.trimNull(request.getParameter("coerce_redeem_flag")), new Integer(2));
String contract_terms = Utility.trimNull(request.getParameter("contract_terms"));
Integer period_unit =  Utility.parseInt(Utility.trimNull(request.getParameter("period_unit")), new Integer(0));
Integer lot_decimal_flag = Utility.parseInt(Utility.trimNull(request.getParameter("lot_decimal_flag")),new Integer(1));
Integer redeem_freeze = Utility.parseInt(Utility.trimNull(request.getParameter("redeem_freeze")),new Integer(0));
Integer rg_bond_flag = Utility.parseInt(Utility.trimNull(request.getParameter("rg_bond_flag")),new Integer(2));
Integer contract_single_flag = Utility.parseInt(Utility.trimNull(request.getParameter("contract_single_flag")),new Integer(2));
String styleStr = "none" ;
if(qualified_flag.intValue()==1){
	styleStr = "" ;
}

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
boolean bSuccess = false ;
String error_mess = "";
if(!request.getMethod().equals("POST")){
	vo.setProduct_id(product_id);
	List list = product.queryProductLimit(vo);
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		
		qualified_money = Utility.parseDecimal(Utility.trimNull(map.get("QUALIFIED_MONEY")),new BigDecimal(0.00));
		qualified_flag =  Utility.parseInt(Utility.trimNull(map.get("QUALIFIED_FLAG")),new Integer(0));
		qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUALIFIED_MUN")),new Integer(0));
		asfund_flag = Utility.parseInt(Utility.trimNull(map.get("ASFUND_FLAG")),new Integer(0));
		gain_flag = Utility.parseInt(Utility.trimNull(map.get("GAIN_FLAG")),new Integer(0));
		open_gain_flag = Utility.parseInt(Utility.trimNull(map.get("OPEN_GAIN_FLAG")),new Integer(2));
		lot_decimal_flag = Utility.parseInt(Utility.trimNull(map.get("LOT_DECIMAL_FLAG")),new Integer(1));
		is_bank_consign = Utility.parseInt(Utility.trimNull(map.get("IS_BANK_CONSIGN")),new Integer(2));
		jg_min_subamount = Utility.parseDecimal(Utility.trimNull(map.get("JG_MIN_SUBAMOUNT")),new BigDecimal(0.00));
		gr_min_subamount = Utility.parseDecimal(Utility.trimNull(map.get("GR_MIN_SUBAMOUNT")),new BigDecimal(0.00));
		jg_min_bidsamount = Utility.parseDecimal(Utility.trimNull(map.get("JG_MIN_BIDSAMOUNT")),new BigDecimal(0.00));
		gr_min_bidsamount = Utility.parseDecimal(Utility.trimNull(map.get("GR_MIN_BIDSAMOUNT")),new BigDecimal(0.00));
		jg_min_appbidsamount = Utility.parseDecimal(Utility.trimNull(map.get("JG_MIN_APPBIDSAMOUNT")),new BigDecimal(0.00));
		gr_min_appbidsamount = Utility.parseDecimal(Utility.trimNull(map.get("GR_MIN_APPBIDSAMOUNT")),new BigDecimal(0.00));
		min_redeem_vol = Utility.parseDecimal(Utility.trimNull(map.get("MIN_REDEEM_VOL")),new BigDecimal(0.00));
		min_redeem_vol2 = Utility.parseDecimal(Utility.trimNull(map.get("MIN_REDEEM_VOL2")),new BigDecimal(0.00));
		large_redeem_flag = Utility.parseInt(Utility.trimNull(map.get("LARGE_REDEEM_FLAG")),new Integer(0));
		large_redeem_condition = Utility.parseInt(Utility.trimNull(map.get("LARGE_REDEEM_CONDITION")),new Integer(0));
		large_redeem_percent = Utility.parseDecimal(Utility.trimNull(map.get("LARGE_REDEEM_PERCENT")),new BigDecimal(0.00));
		large_redeem_deal = Utility.parseInt(Utility.trimNull(map.get("LARGE_REDEEM_DEAL")),new Integer(0));
		coerce_redeem_flag = Utility.parseInt(Utility.trimNull(map.get("COERCE_REDEEM_FLAG")),new Integer(0));
		period_unit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
		redeem_freeze = Utility.parseInt(Utility.trimNull(map.get("REDEEM_FREEZE")),new Integer(0));
		rg_bond_flag = Utility.parseInt(Utility.trimNull(map.get("RG_BOND_FLAG")),new Integer(2));
		contract_terms = Utility.trimNull(map.get("CONTRACT_TERMS"));
		contract_single_flag = Utility.parseInt(Utility.trimNull(map.get("CONTRACT_SINGLE_FLAG")),new Integer(2));
	}
}
if("POST".equals(request.getMethod())){
	vo.setQualified_flag(qualified_flag);
	vo.setAsfund_flag(asfund_flag);
	vo.setGain_flag(gain_flag);
	vo.setProduct_id(product_id) ;
	vo.setOpen_gain_flag(open_gain_flag);
	vo.setLot_decimal_flag(lot_decimal_flag);
	vo.setInput_man(input_operatorCode);
	vo.setIs_bank_consign(is_bank_consign);
	vo.setMin_redeem_vol(min_redeem_vol);
	vo.setMin_redeem_vol2(min_redeem_vol2);
	vo.setCoerce_redeem_flag(coerce_redeem_flag);
	vo.setRedeem_freeze(redeem_freeze);
	vo.setPeriod_unit(period_unit);
	vo.setRg_bond_flag(rg_bond_flag);
	vo.setContract_terms(contract_terms);
	if(is_bank_consign.intValue() == 1)
	{
		vo.setJg_min_subamount(jg_min_subamount);
		vo.setGr_min_subamount(gr_min_subamount);
		vo.setJg_min_bidsamount(jg_min_bidsamount);
		vo.setGr_min_bidsamount(gr_min_bidsamount);
		vo.setGr_min_appbidsamount(gr_min_appbidsamount);
		vo.setJg_min_appbidsamount(jg_min_appbidsamount);
	}else{
		vo.setJg_min_subamount(new BigDecimal(0));
		vo.setGr_min_subamount(new BigDecimal(0));
		vo.setJg_min_bidsamount(new BigDecimal(0));
		vo.setGr_min_bidsamount(new BigDecimal(0));
		vo.setGr_min_appbidsamount(new BigDecimal(0));
		vo.setJg_min_appbidsamount(new BigDecimal(0));
	}

	if(large_redeem_flag.intValue() == 1)
	{
		vo.setLarge_redeem_flag(large_redeem_flag);
		vo.setLarge_redeem_condition(large_redeem_condition);
		vo.setLarge_redeem_percent(large_redeem_percent.divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP));
		vo.setLarge_redeem_deal(large_redeem_deal);
	}else{
		vo.setLarge_redeem_flag(new Integer(2));
		vo.setLarge_redeem_condition(new Integer(1));
		vo.setLarge_redeem_percent(new BigDecimal(0));
		vo.setLarge_redeem_deal(new Integer(1));
	}

	if(qualified_flag.intValue()==1){//自然人数量可控制 则设置数量和金额
		vo.setQualified_num(qualified_num);	
		vo.setQualified_money(qualified_money);	
	}else{
		vo.setQualified_num(null);	
		vo.setQualified_money(null);	
	}
	vo.setContract_single_flag(contract_single_flag);
	try{
			product.updateProductLimit(vo);
			bSuccess = true ;
	}catch(Exception e){
		error_mess = e.getMessage() ;
	}
}
 %>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>产品发行设置</title>
</HEAD>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess && error_mess.equals("")){%>
	//window.returnValue = 1;
	sl_alert('保存成功！');
	window.close();
<%}else if(!error_mess.equals("")){%>
   	sl_alert("<%=error_mess%>");
<%}%>

window.onload = function(){
	changeView(<%=qualified_flag.intValue() %>);
};

function validateForm(form){
	if(!sl_checkChoice(form.qualified_flag,"自然人数量控制"))	return false;
	if(!sl_checkChoice(form.asfund_flag,"收益级别合算单位"))	return false;	
	if(theform.qualified_flag.value==1){
		if(!sl_checkDecimal(form.qualified_money, "合格投资人资金额下限", 16, 2, 0))		return false;
		if(!sl_checkNum(form.asfund_flag, '自然人数量 ', 3, 0))	return false;
		if(theform.asfund_flag.value=='' || theform.asfund_flag.value==0){
			alert("自然人数量无效，请检查！");
			return false;			
		}	
		if(theform.qualified_money.value=='' || theform.qualified_money.value==0){
			alert("合格投资人资金额下限无效，请检查！");
			return false;			
		}	
	}
	if(form.coerce_redeem_flag.value == 1){
		if(!sl_checkDecimal(form.min_redeem_vol, "最少保留份额(个人)", 13, 2, 1))	return false;
		if(!sl_checkDecimal(form.min_redeem_vol2, "最少保留份额(机构)", 13, 2, 1))	return false;
	}else{
		if(!sl_checkDecimal(form.min_redeem_vol, "最少保留份额(个人)", 13, 2, 0))	return false;
		if(!sl_checkDecimal(form.min_redeem_vol2, "最少保留份额(机构)", 13, 2, 0))	return false;
	}
	<%if(Argument.getSyscontrolValue_intrust("TA_FLAG") == 1){%>
	    
		if(form.is_bank_consign.value == 1)
		{
			if(!sl_checkDecimal(form.jg_min_subamount, "机构认购最少金额", 13, 2, 1))		return false;
			if(!sl_checkDecimal(form.gr_min_subamount, "个人认购最少金额", 13, 2, 1))		return false;
			if(!sl_checkDecimal(form.jg_min_bidsamount, "机构首次申购最少金额", 13, 2, 1))		return false;
			if(!sl_checkDecimal(form.gr_min_bidsamount, "个人首次申购最少金额", 13, 2, 1))		return false;
			if(!sl_checkDecimal(form.jg_min_appbidsamount, "机构追加申购最低金额", 13, 2, 1))		return false;
			if(!sl_checkDecimal(form.gr_min_appbidsamount, "个人追加申购最低金额", 13, 2, 1))		return false;
		}
		if(form.large_redeem_flag.value == 1)
		{
			if(!sl_checkChoice(form.large_redeem_condition, "巨额赎回判断条件"))			return false;
			if(!sl_checkDecimal(form.large_redeem_percent, "上日总份额的百分比", 3, 2, 1))		return false;
			if(form.large_redeem_percent.value <= 0)
			{
				sl_alert("上日总份额的百分比不能小于等于零！");
				form.large_redeem_percent.select();
				return false;
			}
			if(form.large_redeem_percent.value >100)
			{
				sl_alert("上日总份额的百分比不能大于100！");
				form.large_redeem_percent.select();
				return false;
			}
			if(!sl_checkChoice(form.large_redeem_deal, "巨额赎回处理方式"))			return false;
		}
	<%}%>
	if(form.gain_flag.value == 4){
		if(form.period_unit.value == -1){
			alert("请选择持有期限单位");
			form.period_unit.focus();
			return false;
		}
	}
		
	return sl_check_update();	
}

function showCnMoney(value)
{
	temp = value;
	if (trim(value) == ""){
		to_money_cn.innerText = "(元)";
	}else{
		to_money_cn.innerText = "(" + numToChinese(temp) + ")";
	}
}

function changeView(v){
	if(v==1){
		//document.getElementById('v_1').style.display = '';
		document.getElementById('v_2').style.display = '';
		//document.theform.qualified_money.value= <%=Format.formatMoney(qualified_money) %>;
		//document.theform.qualified_num.value= <%=qualified_num %>;
	}else{
		//document.getElementById('v_1').style.display = 'none';
		document.getElementById('v_2').style.display = 'none';
		//document.theform.qualified_money.value='' ;
		//document.theform.qualified_num.value='' ;		
	}
}

function setTa(obj)
{
	if(obj.value == 1)
	{
		document.getElementById("is_ta1").style.display = "block";
		document.getElementById("is_ta2").style.display = "block";
		//document.getElementById("is_ta3").style.display = "block";
	}
	else
	{
		document.getElementById("is_ta1").style.display = "none";
		document.getElementById("is_ta2").style.display = "none";
		//document.getElementById("is_ta3").style.display = "none";
		document.theform.jg_min_subamount.value = "";
		document.theform.gr_min_subamount.value = "";
		document.theform.jg_min_bidsamount.value = "";
		document.theform.gr_min_bidsamount.value = "";
		document.theform.gr_min_appbidsamount.value = "";
		document.theform.jg_min_appbidsamount.value = "";
		//document.theform.min_redeem_vol.value = "";
		//document.theform.large_redeem_condition.options[1].selected="selected";
		//document.theform.large_redeem_percent.value = "";
		//document.theform.large_redeem_deal.options[1].selected="selected";
	}
}

function setLarge(obj)
{
	if(obj.value == 1)
	{
		document.getElementById("tr1").style.display = "block";
		document.getElementById("tr2").style.display = "block";
		document.getElementById("tr3").style.display = "block";
	}else{
		document.getElementById("tr1").style.display = "none";
		document.getElementById("tr2").style.display = "none";
		document.getElementById("tr3").style.display = "none";
	}
}

function showperiodunit(value){
	if(value == 4){
	document.getElementById("syjslb1").style.width="auto";
	document.getElementById("syjslb1").style.align="center";
	document.getElementById("syjslb2").style.width="auto";
	document.getElementById("syjslb2").style.align="center";
	document.getElementById("period_td").style.display = "block";
	}else{
	document.getElementById("period_td").parentNode.style.display = "none";
	document.getElementById("syjslb1").style.width="25%";
	document.getElementById("syjslb1").style.align="right";
	document.getElementById("syjslb2").style.width="25%";
	document.getElementById("syjslb2").style.align="left";
	}	
}

function corerceRedeem(flag){
    if(flag==1){
        document.theform.min_redeem_vol.readOnly=false;
        document.theform.min_redeem_vol2.readOnly=false;
    }else{
        document.theform.min_redeem_vol.value="0";
        document.theform.min_redeem_vol.readOnly=true;
        document.theform.min_redeem_vol2.value="0";
        document.theform.min_redeem_vol2.readOnly=true;
    }
    
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="product_set.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
<input type=hidden name="product_id" value="<%=product_id %>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%" class="direct-panel">
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=left border=0 width="100%">
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
                                <td class="page-title"><font color="#215dc6"><b>产品发行设置</b></font></td>
							</tr>
						</table>
</TD></TR><TR><TD>			
					<fieldset style="border-width: 1px; border-color: #000000;">
						<legend><font color="red">收益级别:</font></legend>			
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
									<td align="right" width="25%">收益级别核算单位:</td>
									<td width="25%">
										<SELECT name="asfund_flag" style="width:120px;"><!--											
											<OPTION  value="1" <%if(asfund_flag!=null&&asfund_flag.intValue()==1){ out.print("selected");}%>  >受益记录</OPTION>
											<OPTION  value="2"  <%if(asfund_flag!=null&&asfund_flag.intValue()==2){ out.print("selected");}%> >投资人</OPTION>
											<OPTION  value="3" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){ out.print("selected");}%>  >按合同定义</OPTION>
											-->
											<%=Argument.getTableOptions2(3004,asfund_flag)%>
										</SELECT></td> 
									<td align="right" id="syjslb1" width="25%" align="right">收益计算类别:</td>
									<td id="syjslb2" width="25%" align="left">
										<SELECT name="gain_flag" style="width:120px;" onchange="showperiodunit(this.value)"><!--											
											<OPTION value="1" <%if(gain_flag!=null&&gain_flag.intValue()==1){out.print("selected");}%>>按实际收益率</OPTION>
											<OPTION value="2" <%if(gain_flag!=null&&gain_flag.intValue()==2){out.print("selected");}%>>按基准收益率</OPTION>
											<OPTION value="3" <%if(gain_flag!=null&&gain_flag.intValue()==3){out.print("selected");}%>>按时间分段收益率</OPTION>	
											-->
 											<%if(gain_flag == null){ gain_flag = new Integer(0);} %>
											<%=Argument.getTableOptions2(3005,gain_flag)%>										
										</SELECT>
									</td>
									</tr>
									<tr>
									
									<td  align="right" width="25%" id="period_td" style='display:<%if(!gain_flag.equals(new Integer(4))){%>"none"<%}%>'>持有期限单位:</td>
									<td colspan="3" width="75%" >	<select size="1" name="period_unit" onkeydown="javascript:nextKeyPress(this)">
									    	<%=Argument.getTinipapamOptions_intrust(117,period_unit.toString())%>
								      	</select>
									</td>
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>
					<fieldset style="border-width: 1px; border-color: #000000;">
						<legend><font color="red">开放日:</font></legend>			
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
									<td align="right" width="25%">是否计提开放期收益:</td>
									<td aligh="left" width="25%">
										<SELECT name="open_gain_flag" style="width:120px;">								
											<OPTION  value="1" <%if(open_gain_flag!=null&&open_gain_flag.intValue()==1){ out.print("selected");}%>  >是</OPTION>
											<OPTION  value="2"  <%if(open_gain_flag!=null&&open_gain_flag.intValue()==2){ out.print("selected");}%> >否</OPTION>
										</SELECT></td> 
									<td width="25%" align="right">份额精度:</td>
									<td width="25%" align="left">
										<select name="lot_decimal_flag" >
											<option value='1' <%if(lot_decimal_flag.equals(new Integer(1))){%>selected<%}%>>四舍五入后保留2位小数</option>
											<option value='2' <%if(lot_decimal_flag.equals(new Integer(2))){%>selected<%}%>>截断后保留2位小数</option>
										</select>
									</td>		
								</tr>
								<tr>
									<td width="25%" align="right">发行期利息处置:</td>
									<td colspan="75%" align="left">
										<select name="rg_bond_flag">
											<option value="1"	<%if(new Integer(1).equals(rg_bond_flag)){%>selected<%} %> >纳入信托财产</option>
											<option value="2"	<%if(new Integer(2).equals(rg_bond_flag)){%>selected<%} %> >归受益人所有</option>
										</select>		
		
									</td>
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>
					<fieldset style="border-width: 1px; border-color: #000000;">
						<legend><font color="red">合格投资人:</font></legend>	
							<table border="0" width="100%" cellspacing="0" cellpadding="3">
								<tr>
									<td align="right" width="25%">自然人数量控制:</td>
									<td colspan="3" width="75%">
										<SELECT name="qualified_flag" style="width:120px;" onchange="javascript:changeView(this.value);" >
											<OPTION  value="1" <%if(qualified_flag.intValue()==1){ out.print("selected");}%>  >控制</OPTION>
											<OPTION  value="2" <%if(qualified_flag.intValue()==2){ out.print("selected");}%>  >不控制</OPTION>
										</SELECT></td> 
								</tr>
								<tr id="v_2" style="display:<%=styleStr %>;">
									<td align="right" width="25%">自然人数量:</td>
									<td width="25%">
									   <input type="text" name="qualified_num" value="<%=qualified_num %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
									<td align="right" width="25%">合格投资人金额下限:</td>
									<td width="25%">
									   <input type="text" name="qualified_money" value="<%=Format.formatMoney(qualified_money) %>"  onkeyup="javascript:showCnMoney(this.value)" onkeydown="javascript:nextKeyPress(this)" size="20"> 
									<span id="to_money_cn" class="span" style="display: none;">&nbsp;(元)</span></td>
								</tr>
								<tr>
									<td align="right" width="25%">单份合同控制:</td>
									<td colspan="3" width="75%">
										<select name="contract_single_flag" style="width:120px;">
											<option value="1" <%=contract_single_flag.intValue() == 1 ? "selected" : ""%>>控制</option>
											<option value="2" <%=contract_single_flag.intValue() != 1 ? "selected" : ""%>>不控制</option>
										</select>
									</td> 
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>
<%if(Argument.getSyscontrolValue_intrust("TA_FLAG") == 1){%>
					<fieldset style="border-width: 1px; border-color: #000000;">
						<legend><font color="red">银行代销:</font></legend>			
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
									<td align="right" width="25%">是否银行代销:</td>
									<td colspan="3" width="75%">
										<SELECT name="is_bank_consign" style="width:120px;" onchange="javascript:setTa(this);">								
											<OPTION  value="1" <%if(is_bank_consign!=null&&is_bank_consign.intValue()==1){ out.print("selected");}%>>是</OPTION>
											<OPTION  value="2" <%if(is_bank_consign!=null&&is_bank_consign.intValue()==2){ out.print("selected");}%>>否</OPTION>
										</SELECT></td> 
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>
<%} %>
<fieldset style="border-width: 1px; border-color: #000000;display: <%if(Argument.getSyscontrolValue_intrust("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta1">
						<legend><font color="red">认购:</font></legend>			
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
									<td align="right" width="25%"><font color="red">*</font>机构认购最少金额:</td>
									<td width="25%">
									   <input type="text" name="jg_min_subamount" value="<%=Format.formatMoney(jg_min_subamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
									<td align="right" width="25%"><font color="red">*</font>个人认购最少金额:</td>
									<td width="25%">
									   <input type="text" name="gr_min_subamount" value="<%=Format.formatMoney(gr_min_subamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
									</td>
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>

<fieldset style="border-width: 1px; border-color: #000000;display: <%if(Argument.getSyscontrolValue_intrust("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta2">
						<legend><font color="red">申购:</font></legend>			
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
									<td align="right" width="25%"><font color="red">*</font>机构首次申购最少金额:</td>
									<td width="25%">
									   <input type="text" name="jg_min_bidsamount" value="<%=Format.formatMoney(jg_min_bidsamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
									<td align="right" width="25%"><font color="red">*</font>个人首次申购最少金额:</td>
									<td width="25%">
									   <input type="text" name="gr_min_bidsamount" value="<%=Format.formatMoney(gr_min_bidsamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
									</td>
								</tr>
                                <tr>
									<td align="right" width="25%"><font color="red">*</font>机构追加申购最低金额:</td>
									<td width="25%">
									   <input type="text" name="jg_min_appbidsamount" value="<%=Format.formatMoney(jg_min_appbidsamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
									<td align="right" width="25%"><font color="red">*</font>个人追加申购最低金额:</td>
									<td width="25%">
									   <input type="text" name="gr_min_appbidsamount" value="<%=Format.formatMoney(gr_min_appbidsamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
									</td>
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>

<fieldset style="border-width: 1px; border-color: #000000;"  id="is_ta3">
						<legend><font color="red">赎回:</font></legend>			
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
							    <tr>
									<td width="25%" align="right">开放式产品赎回冻结期:</td>
									<td width="25%" align="left"><input type="text" size="15" name="redeem_freeze"  value="<%=redeem_freeze%>"/>&nbsp;(天)</td>	
								</tr>
								<tr>
									<td colspan="4"><hr></td>
								</tr>
								<tr>
								    <td align="right" width="25%"><font color="red">*</font>是否存在强制赎回:</td>
									<td width="25%">
									  	<select name="coerce_redeem_flag"  onchange="javascript:corerceRedeem(this.value);" onkeydown="javascript:nextKeyPress(this);" style="width: 120px;">
											<option value="1" <%=coerce_redeem_flag.intValue() == 1 ? "selected" : ""%>>是</option>
											<option value="2" <%=coerce_redeem_flag.intValue() == 2 ? "selected" : ""%>>否</option>
										</select>
									</td>									
								</tr>
								<tr>
								    <td align="right" width="25%"><font color="red">*</font>最少保留份额(个人):</td>
									<td width="25%">
									  	 <input type="text" name="min_redeem_vol" value="<%=Format.formatMoney(min_redeem_vol) %>" onkeydown="javascript:nextKeyPress(this);" size="20">
									</td>
									<td align="right" width="25%"><font color="red">*</font>最少保留份额(机构):</td>
									<td width="25%">
										<input type="text" name="min_redeem_vol2" value="<%=Format.formatMoney(min_redeem_vol2) %>" onkeydown="javascript:nextKeyPress(this);" size="20">
									</td> 									
								</tr>
								<tr style="display:<%=Argument.getSyscontrolValue_intrust("TA_FLAG") == 1 ? "" : "none"%>;">
									<td colspan="4"><hr></td>
								</tr>
								<tr style="display:<%=Argument.getSyscontrolValue_intrust("TA_FLAG") == 1 ? "" : "none"%>;">
									<td align="right" width="25%"><font color="red">*</font>是否处理巨额赎回:</td>
									<td width="25%">
										<select name="large_redeem_flag" onkeydown="javascript:nextKeyPress(this);" style="width: 120px;" onclick="javascript:setLarge(this);">
											<option value="1" <%=large_redeem_flag.intValue() == 1 ? "selected" : ""%>>是</option>
											<option value="2" <%=large_redeem_flag.intValue() == 2 ? "selected" : ""%>>否</option>
										</select>
									</td>
									<td align="right" width="25%" id="tr1" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;"><font color="red">*</font>巨额赎回判断条件:</td>
									<td width="25%" id="tr2" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;">
									    <select style="width: 120px;" name="large_redeem_condition" onkeydown="javascript:nextKeyPress(this)">
											<option>请选择</option>
											<option value="1" <%=large_redeem_condition.intValue() == 1 ? "selected" : ""%>>按净赎回份额判断</option>
											<option value="2" <%=large_redeem_condition.intValue() == 2 ? "selected" : ""%>>按总赎回份额判断</option>
										</select>
									</td>
								</tr>

								<tr id="tr3" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;">
									<td align="right" width="25%"><font color="red">*</font>上日总份额的百分比:</td>
									<td width="25%">
									   <input type="text" name="large_redeem_percent" value="<%=Utility.trimNull(Format.formatMoney(large_redeem_percent.multiply(new BigDecimal(100)).doubleValue(), 2))%>" onkeydown="javascript:nextKeyPress(this);" size="20"><font color="blue">(%)</font></td> 
									<td align="right" width="25%"><font color="red">*</font>巨额赎回处理方式:</td>
									<td width="25%">
									  	<select style="width: 120px;" name="large_redeem_deal" onkeydown="javascript:nextKeyPress(this)">
											<option>请选择</option>
											<option value="1" <%=large_redeem_deal.intValue() == 1 ? "selected" : ""%>>顺延</option>
											<option value="2" <%=large_redeem_deal.intValue() == 2 ? "selected" : ""%>>根据客户申请数据处理</option>
										</select>
									</td>
								</tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>
<fieldset style="border-width: 1px;display: <%if(Argument.getSyscontrolValue_intrust("CONTERMS")==2)out.println("none;");%> border-color: #000000;">
						<legend><font color="red">合同条款:</font></legend>	
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
								    <td>
								        <textarea rows="10" name="contract_terms" cols=125"><%=Utility.trimNull(contract_terms)%></textarea>
    									<script type="text/javascript">
    										var oFCKeditor = new FCKeditor('contract_terms') ;
    										oFCKeditor.BasePath = '<%=request.getContextPath()%>/webEditor/' ;
    										//oFCKeditor.ToolbarSet = 'NULL' ;
    										oFCKeditor.Width = '100%' ;
    										oFCKeditor.Height = '180';
    										oFCKeditor.ReplaceTextarea();
    									</script>
								    </td>
							    </tr>
							</table>
					</fieldset>
</TD></TR><TR><TD>
		
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<!--保存-->
                                <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);document.theform.submit();}">保存 (<u>S</u>)</button>
								&nbsp;&nbsp;
								<!--取消-->
                                <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消 (<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
			</TABLE>
			</TD>
		</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
product.remove();
}catch(Exception e){throw e ;}
%>