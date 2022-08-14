<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

Integer q_productId = Utility.parseInt(request.getParameter("q_productId"), new Integer(0));
Integer q_sub_productId = Utility.parseInt(request.getParameter("q_sub_productId"), new Integer(0)); 
Integer link_man = Utility.parseInt(request.getParameter("link_man"), new Integer(0)); 
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

sUrl += "&" + Utility.getQueryString(request, new String[]{"q_productId", "q_sub_productId", "link_man", "cust_name"}); // 换页用

SaleParameterLocal saleParam = EJBFactory.getSaleParameter();
SaleParameterVO vo = new SaleParameterVO();
vo.setProductID(q_productId);
vo.setSub_product_id(q_sub_productId);
vo.setInput_man(input_operatorCode);
if ("POST".equals(request.getMethod())) {
	saleParam.calManagerCommission(vo);
}

vo.setService_man(link_man);
vo.setCust_name(cust_name);

IPageList pageList = saleParam.queryManagerCommission(vo, _page, pagesize);
List list = pageList.getRsList();
saleParam.remove();
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
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
window.onload = function() {
		selectProduct(<%=q_productId%>);
		initQueryCondition();
	};


/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value != ""){
		var j = value.length;
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.indexOf(value) >= 0)
			{
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		document.theform.q_productCode.focus();
	}
}

/*设置产品*/
function setProduct(value){
	var prodid=0;
	if(event.keyCode == 13 && value != ""){
        var j = value.length;

		if (escape(value).indexOf("%u") >= 0) //存在有中文的情况按产品名称检索
		{
			for(i=0;i<document.theform.q_productId.options.length;i++){
				if(document.theform.q_productId.options[i].text.indexOf(value) >= 0)
				{
					document.theform.q_productId.options[i].selected=true;
					prodid = document.theform.q_productId.options[i].value;
					break;
				}
			}
		}else{
			for(i=0;i<document.theform.q_productId.options.length;i++){
				if(document.theform.q_productId.options[i].text.substring(0,j)==value){
					document.theform.q_productId.options[i].selected=true;
					prodid = document.theform.q_productId.options[i].value;
					break;
				}
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		StartQuery();
	}
	
}

//查询子产品
function selectProduct(product_id){
	if(product_id>=0){
		utilityService.getSubProductFlag(product_id,function(data){
				var arrayL = data.split("$");
				var sub_product_flag = arrayL[0];
				if(sub_product_flag==1){
					document.getElementById("sub_product_flag").style.display = "";
					utilityService.getSubProductOptionS(product_id,<%=q_sub_productId%>,function(data1){
						$("sub_product_flag").innerHTML = "<select size='1' name='q_sub_productId' onkeydown='javascript:nextKeyPress(this)' class='productname' onchange='javascript:StartQuery();'>"+
						data1+"</select>&nbsp;&nbsp;";
					});
				}else{
					document.getElementById("sub_product_flag").style.display = "none";
				}	
			}
		);
	}else{
		sl_alert("产品不存在,请重新选择!");
		return false;
	}
}

function refreshPage() {
  	disableAllBtn(true);
  	location = 'sales_stat.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value
		+ '&q_productId='+document.theform.q_productId.value+"&q_sub_productId="+document.theform.q_sub_productId.value
		+ "&link_man="+document.theform.link_man.value + "&cust_name="+document.theform.cust_name.value; // 换分页大小
}

function StartQuery() {
	refreshPage();
}

//查看客户信息
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
}

function cal() {
	if (document.theform.q_productId.value=="0") {
		sl_alert("请先选择要计算的产品！");
		document.theform.q_productId.focus();
		return;
	}
	document.theform.submit();
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
			<input type="text" name="cust_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(cust_name)%>">
		</td>		

		<td align="right">客户经理:</td>
		<td colspan="2">
			<select name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getIntrustRoledOperatorOptions(input_bookCode, 2, link_man)%>
			</select>
		</td>
	</tr>

	<tr>		
		<td align="center" colspan=4><button type="button" class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
	</tr>						
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
			<TR>
				<TD>
				<table cellspacing="1" cellpadding="2" border="0" width="100%">
					<tr>
						<td align="left">
						<table border="0" cellspacing="0" width="100%" style="border-collapse: collapse" bordercolor="#111111" cellpadding="0">
							<tr>
								<td colspan="6"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
							</tr>

							<tr><td align=right>
									<button type="button" class="xpbutton3" accessKey=c onclick="javascript:cal()">计算(<u>C</u>)</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
							</td></tr>

							<tr>
								<td colspan="6">
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
							<tr><td colspan="6">							
								<div id="headDiv" style="margin-top:5px">
								<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
										<tr style="background:F7F7F7;">
											<td>
												<select size="1" name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">
													<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
												</select>
											</td>
											<td id="sub_product_flag"style="display:none">
												<select size="1" id="q_sub_productId" name="q_sub_productId" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">
													<option value="<%=q_sub_productId%>">请选择 </option>				
												</select>
											</td>
											<td>
												<input type="text" name="q_productCode" value="<%--=q_productCode--%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
												<button type="button" class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);StartQuery();" />
											</td>
											<!--产品-->
										</tr>
								</table>
								</div>
							</td></tr>
						</table>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="970px">
							<tr class="trh">
								<td width="400px">产品名称</td>
								<td width="120px">客户名称</td>
								<td sort="num" width="180px">销售额</td>
								<TD sort="num" width="150px">提成</TD>
								<td width="120px">客户经理</td>
							</tr>
<%
int iCurrent = 0;
BigDecimal sales_total = new BigDecimal(0.000);    
BigDecimal fee_total = new BigDecimal(0.000);    
for (int i=0; i<list.size(); i++) {
	iCurrent++;
	Map	map = (Map)list.get(i);
	BigDecimal _sells_amount = (BigDecimal)map.get("SELLS_AMOUNT"); // 销售总额
	if(_sells_amount != null)
		sales_total = sales_total.add(_sells_amount);

	BigDecimal _commission_amount = (BigDecimal)map.get("COMMISSION_AMOUNT"); // 提成金额
	if(_commission_amount!=null)
		fee_total = fee_total.add(_commission_amount);

	BigDecimal _commission_rate = (BigDecimal)map.get("COMMISSION_RATE"); // 提成比例

	Integer _product_id = (Integer)map.get("PRODUCT_ID");
	Integer _subproduct_id = (Integer)map.get("SUBPRODUCT_ID");

	String _subproduct_name = "";
	if (_subproduct_id!=null && _subproduct_id.intValue()!=0)
		_subproduct_name = Utility.trimNull(Argument.getSubProductName(_product_id, _subproduct_id));

	Integer _op_code = (Integer)map.get("OP_CODE");
	String _op_name = (String)map.get("OP_NAME");
	String _cust_name = (String)map.get("CUST_NAME");
    String _product_name = (String)map.get("PRODUCT_NAME");
	Integer _cust_id = (Integer)map.get("CUST_ID");
%>

							<tr class="tr<%=(iCurrent % 2)%>">
								<td align="center" width="400px" noWrap="true"><%=Utility.trimNull(_product_name)%><%=_subproduct_name.equals("")?"":"("+_subproduct_name+")"%></td>														 
								<td align="center" width="120px">
									<a href="#" onclick="javascript:getCustomer(<%=_cust_id%>);return false;"><%=Utility.trimNull(_cust_name)%></a>
								</td>
								
								<td align="right" width="180px"><%=Utility.trimNull(Format.formatMoney(_sells_amount))%></td>
								<TD align="right" width="150px"><%=Utility.trimNull(Format.formatMoney(_commission_amount))%></TD>
								<td align="center" width="120px"><%=Utility.trimNull(Argument.getInturstOperatorName(_op_code))%></td>
							</tr>
<%
}

for (; iCurrent < pageList.getPageSize(); iCurrent++) { %>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" align="center"></td>							 
								<td align="center" ></td>
								<td align="center" ></td>
								<td align="center" ></td>
								<td align="center" ></td>
							</tr>
<%
}%>
							<tr class="trbottom">
								<td class="tdh" width="400px" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
								<td align="center" >-</td>
								<td align="right" ><%=Format.formatMoney(sales_total)%></td>
								<TD align="right" ><%=Format.formatMoney(fee_total)%></TD>
								<td align="center" >-</td>
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr valign="top">
								<td><%=pageList.getPageLink(sUrl, clientLocale)%></td>
								<td align="right"></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
	</TR>
	</TBODY>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
