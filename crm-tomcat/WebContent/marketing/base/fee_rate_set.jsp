<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer productId =  Utility.parseInt(request.getParameter("productId"), new Integer(0));
Integer subproductId =  Utility.parseInt(request.getParameter("subproductId"), new Integer(0));
//String productName = Argument.getProductName(productId);

boolean readonly = request.getParameter("readonly")!=null;
String sReadonly = "";
if (readonly) sReadonly = "readonly";
String sDisabled = "";
if (readonly) sDisabled = "disabled";

boolean success = false;

Integer periodUnit = new Integer(2); // 月

SaleParameterLocal saleParam = EJBFactory.getSaleParameter();
CommissionRateVO vo = new CommissionRateVO();
vo.setProductId(productId);
vo.setSubproductId(subproductId);
vo.setInputMan(input_operatorCode);

if ("POST".equals(request.getMethod())) {	
	saleParam.delCommissionRate(vo);

	String[] periods = request.getParameterValues("period");
	String[] periodUnits = request.getParameterValues("periodUnit");
	String[] feeRates = request.getParameterValues("feeRate");
	String[] feeAmounts = request.getParameterValues("feeAmount");
	String[] tradeStartMoney = request.getParameterValues("tradeStartMoney");
	String[] tradeEndMoney = request.getParameterValues("tradeEndMoney");
	String[] calMethods = request.getParameterValues("calMethod");	
	for (int i=0; periods!=null && i<periods.length; i++) {
		vo.setPeriod(Utility.parseInt(periods[i], new Integer(0)));
		vo.setPeriodUnit(Utility.parseInt(periodUnits[i], new Integer(0)));
		vo.setTradeStartMoney(Utility.parseInt(tradeStartMoney[i], new Integer(0)));
		vo.setTradeEndMoney(Utility.parseInt(tradeEndMoney[i], new Integer(0)));
		if ("1".equals(calMethods[i])) { // rate
			vo.setFeeRate(Utility.parseDecimal(feeRates[i], new BigDecimal(0.0)).divide(new BigDecimal(100.0), 4, BigDecimal.ROUND_HALF_EVEN));
			vo.setFeeAmount(null);
		} else {
			vo.setFeeRate(null);
			vo.setFeeAmount(Utility.parseDecimal(feeAmounts[i], new BigDecimal(0.0)));
		}
		saleParam.addCommissionRate(vo);
	}
	success = true;
} 

List list = saleParam.queryCommissionRate(vo);

saleParam.remove();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>销售提成比例设置</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
var success = <%=success%>;
var readonly = <%=readonly%>;

window.onload = function() {
		if (success) {
			sl_alert("保存成功！");
			document.getElementById("btnBack").focus();
		}

		if (! readonly) {
			var table = document.getElementById("main");
			if (table.rows.length==1) addPeriod();
		}
	};

function getIndex(elem) {
	var elems = document.getElementsByName(elem.name);
	for (var i=0; i<elems.length; i++)
		if (elems[i]==elem) return i;

	return -1;
}

function addPeriod() {
	var table = document.getElementById("main");
	var row = table.insertRow(table.rows.length-1);
	var col = row.insertCell(0);
	col.innerHTML = '合同期限: <input name="_period" type="text" size="5"/>\n'+
				'单位: <select name="_periodUnit"><%=Argument.getPeriodValidUnitOptions(periodUnit)%></select>' +
				'<button type="button"  class="xpbutton2" onclick="javascript:delPeriod(this)">删除</button>' +
				'<table style="margin-left: 25px">' +
					'<tr><td><input name="period" type="hidden"/><input name="periodUnit" type="hidden"/>' + 
							'合同金额 从<input name="tradeStartMoney" type="text" size="10"/>\n' + 
							'到<input name="tradeEndMoney" type="text" size="10"/>(万元)\n' +
							'提成方式: <select name="calMethod" onchange="javascript:changeCalMethod(this)">\n' + 
											'<option value="1" selected>按比例</option>' +
											'<option value="2">固定</option>' +
										 '</select>\n' +
								'<span><input name="feeRate" type="text" size="5"/>%</span>' +
								'<span style="display:none"><input name="feeAmount" type="text" size="10"/>(元)</span>' +					
								'<button type="button"  class="xpbutton2" onclick="javascript:delRate(this)">删除</button>' +		
						'</td></tr>' +
					'<tr><td><button type="button"  class="xpbutton4" onclick="javascript:addRate(this)">新建比率</button></td></tr>' +
				'</table>';
}

function addRate(elem) {
	var table = elem.parentNode.parentNode.parentNode;
	var row = table.insertRow(table.rows.length-1);
	var col = row.insertCell(0);
	col.innerHTML = '<input name="period" type="hidden"/><input name="periodUnit" type="hidden"/>' + 
					'合同金额 从<input name="tradeStartMoney" type="text" size="10"/>\n'+
					'到<input name="tradeEndMoney" type="text" size="10"/>(万元)\n' +
					'提成方式: <select name="calMethod" onchange="javascript:changeCalMethod(this)">'+
					'				<option value="1" selected>按比例</option>' + 
					'				<option value="2">固定</option>' +
					'			 </select>\n' +
					'<span><input name="feeRate" type="text" size="5"/>%</span>' + 
					'<span style="display:none"><input name="feeAmount" type="text" size="10"/>(元)</span>' + 
					'<button type="button"  class="xpbutton2" onclick="javascript:delRate(this)">删除</button>';
}

function changeCalMethod(elem) {
	var i = getIndex(elem);
	if (elem.value=="1") {
		document.getElementsByName("feeRate")[i].parentNode.style.display = "";
		document.getElementsByName("feeAmount")[i].parentNode.style.display = "none";
	} else {
		document.getElementsByName("feeRate")[i].parentNode.style.display = "none";
		document.getElementsByName("feeAmount")[i].parentNode.style.display = "";
	}
}

function delPeriod(elem) {
	var row = elem.parentNode.parentNode;
	var table = row.parentNode;
	table.deleteRow(row.rowIndex);
}

function delRate(elem) {
	var row = elem.parentNode.parentNode;
	var table = row.parentNode;
	table.deleteRow(row.rowIndex);
}

function validate() {
	var rates = document.getElementsByName("feeRate");
	for (var i=0; i<rates.length; i++) {
		var table = rates[i].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
		//alert(rates[i].parentNode.parentNode.parentNode.parentNode.tagName); // tbody
		var children = table.childNodes; //children; 2者略有不同
		for (var j=0; j<children.length; j++) {
			if (children[j].name !=null && children[j].name=="_period") {
				document.getElementsByName("period")[i].value = children[j].value;
				var idx = getIndex(children[j]);
				if (document.getElementsByName("_periodUnit")[idx].value!="0") {
					if (! sl_checkNum(children[j], "合同期限", 4, 1)) return false;
					if (children[j].value==0) {
						sl_alert("合同期限不能为0！");
						children[j].select();
						children[j].focus();
						return false;
					}
				}
			} else if (children[j].name !=null && children[j].name=="_periodUnit") {
				document.getElementsByName("periodUnit")[i].value = children[j].value; 
			}
		}

		var tradeStartMoney = document.getElementsByName("tradeStartMoney");		
		if (! sl_checkNum(tradeStartMoney[i], "合同份额下限", 10, 1)) {
			return false;
		}
		var tradeStartMoney = document.getElementsByName("tradeEndMoney");		
		if (! sl_checkNum(tradeStartMoney[i], "合同份额上限", 10, 1)) {
			return false;
		}

		var feeRates = document.getElementsByName("feeRate");	
		var feeAmounts = document.getElementsByName("feeAmount");		
		var calMethods = document.getElementsByName("calMethod");
		if (calMethods[i].value=="1") {
			if (! sl_checkDecimal(feeRates[i], "提成比例", 2, 2, 1)) {
				return false;
			}
		} else {
			if (! sl_checkDecimal(feeAmounts[i], "固定提成金额", 14, 2, 1)) {
				return false;
			}		
		}

		
	}

	
	return true;	
}

function save() {
	if (validate())
		document.theform.submit();
}

function back() {
	window.returnValue = true;
	window.close();
}
</script>
</head>

<body class="body body-nox">
<form name="theform" method="post">
<input type="hidden" name="productId" value="<%=productId%>" />
<input type="hidden" name="subproductId" value="<%=subproductId%>"/>

<table cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b>销售提成比例设置</b></font>
		</td>
	</tr>
</table>
<br/>
<table valign="middle" align="center" cellspacing="0" cellpadding="3" width="100%" height="80%">
	<tr><td>
	<TABLE id="main" width="100%" height="80%" cellspacing="1" cellpadding="1">
		<%-- %>tr>
			<td>
				合同期限: <input name="_period" type="text" size="5"/>
				单位: <select name="_periodUnit"><%=Argument.getPeriodValidUnitOptions(periodUnit)%></select><button type="button"  class="xpbutton2" onclick="javascript:delPeriod(this)">删除</button>			
				<table style="margin-left: 25px">
					<tr>
						<td>
							<input name="period" type="hidden"/><input name="periodUnit" type="hidden"/>
							合同金额 从<input name="tradeStartMoney" type="text" size="10"/>
							到<input name="tradeEndMoney" type="text" size="10"/>(万元)
							提成方式: <select name="calMethod" onchange="javascript:changeCalMethod(this)">
											<option value="1" selected>按比例</option>
											<option value="2">固定</option>
										 </select>
								<span><input name="feeRate" type="text" size="5"/>%</span>
								<span style="display:none"><input name="feeAmount" type="text" size="10"/>(元)</span><button type="button"  class="xpbutton2" onclick="javascript:delRate(this)">删除</button>
						</td>
					</tr>
					<tr><td><button type="button"  class="xpbutton4" onclick="javascript:addRate(this)">新建比率</button></td></tr>
				</table>
				
			</td>
		</tr--%>

<% Integer prePeriod = null, prePeriodUnit = null;
for (int i=0; i<list.size(); i++) { 
	Map map = (Map)list.get(i);
	Integer _period = (Integer)map.get("PERIOD");
	Integer _period_unit = (Integer)map.get("PERIOD_UNIT");
	if (i>0 && (prePeriod.intValue()!=_period.intValue() || prePeriodUnit.intValue()!=_period_unit.intValue())) { %>
					<tr><td><button type="button"  class="xpbutton4" onclick="javascript:addRate(this)" <%=sDisabled%>>新建比率</button></td></tr>
				</table>				
			</td>
		</tr>
<%  }

	if (i==0 || prePeriod.intValue()!=_period.intValue() || prePeriodUnit.intValue()!=_period_unit.intValue()) { %>
		<tr>
			<td>
				合同期限: <input name="_period" type="text" size="5" value="<%=_period%>" <%=sReadonly%>/>
				单位: <select name="_periodUnit" <%=sDisabled%>><%=Argument.getPeriodValidUnitOptions(_period_unit)%></select><button type="button"  class="xpbutton2" onclick="javascript:delPeriod(this)" <%=sDisabled%>>删除</button>		
				<table style="margin-left: 25px">			
<%	}
	Integer _trade_start_money = (Integer)map.get("TRADE_START_MONEY");
	Integer _trade_end_money = (Integer)map.get("TRADE_END_MONEY");		
	BigDecimal _fee_rate = (BigDecimal)map.get("FEE_RATE");
	if (_fee_rate!=null)
		_fee_rate = _fee_rate.multiply(new BigDecimal(100.0)).setScale(2);
	BigDecimal _fee_amount = (BigDecimal)map.get("FEE_AMOUNT");
%>
					<tr>
						<td>
							<input name="period" type="hidden"/><input name="periodUnit" type="hidden"/>
							合同金额 从<input name="tradeStartMoney" type="text" size="10" value="<%=_trade_start_money%>" <%=sReadonly%>/>
							到<input name="tradeEndMoney" type="text" size="10" value="<%=_trade_end_money%>" <%=sReadonly%>/>(万元)
							提成方式: <select name="calMethod" onchange="javascript:changeCalMethod(this)" <%=sDisabled%>>
											<option value="1" <%=_fee_rate!=null?"selected":""%>>按比例</option>
											<option value="2" <%=_fee_rate==null?"selected":""%>>固定</option>
										 </select>
								<span style="display:<%=_fee_rate==null?"none":""%>"><input name="feeRate" type="text" size="5" value="<%=Utility.trimNull(_fee_rate)%>" <%=sReadonly%>/>%</span>
								<span style="display:<%=_fee_rate!=null?"none":""%>"><input name="feeAmount" type="text" size="10" value="<%=Utility.trimNull(_fee_amount)%>" <%=sReadonly%>/>(元)</span><button type="button"  class="xpbutton2" onclick="javascript:delRate(this)" <%=sDisabled%>>删除</button>
						</td>
					</tr>
<%
	prePeriod = _period;
	prePeriodUnit = _period_unit;
	if (i==list.size()-1) {%>
					<tr><td><button type="button"  class="xpbutton4" onclick="javascript:addRate(this)" <%=sDisabled%>>新建比率</button></td></tr>
				</table>				
			</td>
		</tr>
<%	}
} %>
		<tr><td><button type="button"  class="xpbutton6" onclick="javascript:addPeriod()" <%=sDisabled%>>新建一个期限</button></td></tr>
	</TABLE>
	</td></tr>
	<tr><td height="20%" align="right">
		<button type="button"  class="xpbutton2" onclick="javascript:save()" <%=sDisabled%>>保存</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button"  class="xpbutton2" id="btnBack" onclick="javascript:back()">返回</button>
	</td></tr>
</table>

</form>
</body>
</html>
