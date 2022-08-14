<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
String sReadonly = "readonly class='edline'";
String sDisabled= "disabled='disabled'";
Map map = null;
List rsList = null;//���ؽ����
//����ֵ
Map map_single = null;
List rsList_single = null;//���ؽ����
boolean bSuccess = false;	

String task=Utility.trimNull(request.getParameter("task"));//��������
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0));
String product_code = Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
String s_product_code = Utility.trimNull(request.getParameter("s_product_code"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
//if(task.equals("list")){
	vo.setProduct_code(product_code);
	vo.setProduct_name(product_name);
	vo.setInput_man(input_operatorCode);
	rsList = product.searchList(vo);
//}

ProductInfoReposLocal preProduct = EJBFactory.getProductInfoRepos();
ProductVO preVO = new ProductVO();

if(request.getMethod().equals("POST")){
	preVO.setPreproduct_id(preproduct_id);
	preVO.setProduct_id(product_id);
	preProduct.bindProduct(preVO);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </TITLE><!--�ͻ���Ϣ-->
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript">
<%
if (bSuccess){
%>
	var v = new Array();
	v[0] = '<%=product_id%>';
	v[1] = '<%=preproduct_id%>';

	window.returnValue = v;		
	window.close();
<%}%>

function queryInfo(){
	document.theform.s_product_code.value = "";
	document.theform.method = "get";
	document.theform.task.value = "list";
	document.theform.action = "product_bind.jsp";
	disableAllBtn(true);
	document.theform.submit();
}

function changeProduct(value){	
	document.theform.s_product_code.value = value;
	document.theform.method = "get";
	document.theform.task.value = "list";
	document.theform.action = "product_bind.jsp";
	
	document.theform.submit();
}


function validateForm(form){		
	if(document.theform.product_val.value == ""){
		sl_alert("��ѡ��Ҫ����ƥ��Ĳ�Ʒ");
	}else{
		document.theform.task.value = "";
		return sl_check_update();	
	}
}

</SCRIPT>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform"  method="post" action="product_bind.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="task" value="<%=task%>">
<input type="hidden" name="preproduct_id" value="<%=preproduct_id%>"> 
<input type="hidden" name="s_product_code" value="<%=s_product_code%>">
<div align="left">
	<br>
	<div>
		<table border="0" width="100%" cellspacing="0" cellpadding="1">
			<tr>
				<td align=right>��Ʒ��ţ�</td>
				<td><input onkeydown="javascript:nextKeyPress(this)" type="text" name="product_code" size="15"  value="<%=Utility.trimNull(product_code)%>"></td>
				<td align=right>��Ʒ���ƣ�</td>
				<td><input onkeydown="javascript:nextKeyPress(this)" type="text" name="product_name" size="45"  value="<%=product_name%>"></td>
				<td><button class="xpbutton3" accessKey=q onclick="javascript:queryInfo();">���� (<u>Q</u>)</button>	&nbsp;<!--����--></td>							
			</tr>
			<tr>
				<td colspan="5"><br></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("message.searchResults",clientLocale)%> :</td><!--�������-->
				<td colspan="3">
					<select  style="width:430px;" onkeydown="javascript:nextKeyPress(this)" size="1"  name="product_val" id="product_val" onchange="javascript: changeProduct(this.value);">
						<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
					<%
						Iterator iterator = rsList.iterator();
						
						while(iterator.hasNext()){	
							map = (Map)iterator.next();
							product_name = Utility.trimNull(map.get("PRODUCT_NAME")); 
							product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
					%>
						<option value="<%=product_code%>" <%if(product_code.equals(s_product_code)){%>selected<%}%>>						
							<%=product_name%>	
						</option>
					<%
						}					
					
					%>
					</select>
				</td>
				<td><button class="xpbutton3" accessKey=q onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();">ƥ�� (<u>P</u>)</button>	&nbsp;</td>							
			</tr>		
		</table>		
	</div>
	
	<hr noshade color="#808080" size="1">
</div>

<%
String product_name1 = "";
Integer pre_start_date = new Integer(0);
Integer pre_end_date = new Integer(0);
String intrust_flag3 = "";
String intrust_flag4 = "";
String currency_id = ""; //����
String product_jc = ""; //���
String pre_num = ""; //Ԥ���з���
BigDecimal pre_money = new BigDecimal(0); //Ԥ�ڷ��н��
BigDecimal pre_max_rate = new BigDecimal(0); //ԤԼ������
BigDecimal min_money = new BigDecimal(0); //��ͷ��н��
String pre_code = ""; //��ͬ���ǰ׺
String product_status_name = ""; //״̬
String admin_manager = ""; //ִ�о���
Integer period_unit = new Integer(0); // ʱ�䵥λ
Integer valid_period = new Integer(0); //��Ʒ����

ProductVO vo1 = new ProductVO();
if (s_product_code != null  && !s_product_code.equals("")){
	vo1.setProduct_code(s_product_code);
	vo1.setInput_man(input_operatorCode);
	rsList_single = product.searchList(vo1);
	map_single = (Map)rsList_single.get(0);
	product_name1 = Utility.trimNull(map_single.get("PRODUCT_NAME"));
	pre_start_date = Utility.parseInt(Utility.trimNull(map_single.get("PRE_START_DATE")),new Integer(0));
	pre_end_date = Utility.parseInt(Utility.trimNull(map_single.get("PRE_END_DATE")),new Integer(0));
	intrust_flag3 = Utility.trimNull(map_single.get("INTRUST_FLAG3"));
	intrust_flag4 = Utility.trimNull(map_single.get("INTRUST_FLAG4"));
	currency_id = Utility.trimNull(map_single.get("CURRENCY_ID"));
	product_jc = Utility.trimNull(map_single.get("PRODUCT_JC"));
	pre_num = Utility.trimNull(map_single.get("PRE_NUM"));
	pre_money = Utility.parseDecimal(Utility.trimNull(map_single.get("PRE_MONEY")),new BigDecimal(0));
	pre_max_rate = Utility.parseDecimal(Utility.trimNull(map_single.get("PRE_MAX_MONEY")),new BigDecimal(0));
	min_money = Utility.parseDecimal(Utility.trimNull(map_single.get("MIN_MONEY")),new BigDecimal(0));
	pre_code = Utility.trimNull(map_single.get("PRE_CODE"));
	product_status_name = Utility.trimNull(map_single.get("PRODUCT_STATUS_NAME"));
	admin_manager = Utility.trimNull(Argument.getOperator_Name((Integer)map_single.get("ADMIN_MANAGER")));
	period_unit = Utility.parseInt(Utility.trimNull(map_single.get("PERIOD_UNIT")),new Integer(0));
	valid_period = Utility.parseInt(Utility.trimNull(map_single.get("VALID_PERIOD")),new Integer(0)); 
	product_id = Utility.parseInt(Utility.trimNull(map_single.get("PRODUCT_ID")),new Integer(0)); 
}
%>
<!--�ͻ���Ϣ-->


<table border="1" width="100%" cellspacing="0" cellpadding="1">
	<br>
	<tr height="25">
			<td align="right" width="75px"><b>��Ʒ����</b></td>
			<td colspan="3" align="center">&nbsp;<b><%=product_name1 %></b></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">�ƽ���</td>
			<td colspan="3">&nbsp;<%=Format.formatDateCn(pre_start_date)%>-<%=Format.formatDateCn(pre_end_date)%></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">������ʽ</td>
			<td>&nbsp;<%if(intrust_flag3!=null && !intrust_flag3.equals("")){out.print(intrust_flag3.equals("1")?"˽ļ":"��ļ");}%></td>
			<td align="right" width="75px">����Ŀ��</td>
			<td>&nbsp;<%if(intrust_flag4!=null && !intrust_flag4.equals("")){out.print(intrust_flag4.equals("1")?"˽��":"����");}%></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">����</td>
			<td>&nbsp;<%=Argument.getCurrencyName1(currency_id)%></td>
			<td align="right" width="75px">���</td>
			<td>&nbsp;<%=product_jc%></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">Ԥ�ڷ��з���</td>
			<td>&nbsp;<%=pre_num%></td>
			<td align="right" width="75px">Ԥ�ڷ��н��</td>
			<td>&nbsp;<%=Format.formatMoney(pre_money)%></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">ԤԼ������</td>
			<td>&nbsp;<%=Format.formatMoney(pre_max_rate)%></td>
			<td align="right" width="75px">��ͷ��н��</td>
			<td>&nbsp;<%=Format.formatMoney(min_money)%></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">��ͬ���ǰ׺</td>
			<td>&nbsp;<%=pre_code%></td>
			<td align="right" width="75px">״̬</td>
			<td>&nbsp;<%=product_status_name%></td>
		</tr>
		<tr height="25">
			<td align="right" width="75px">ִ�о���</td>
			<td>&nbsp;<%=admin_manager%></td>
			<td align="right" width="75px">��Ʒ����</td>
			<td>&nbsp;<%if(period_unit!=null)
					        {if(period_unit.intValue()!=0){%>
					    <%=valid_period%>
						<%=Argument.getProductUnitName(period_unit)%>
						<%}else{%><%=Argument.getProductUnitName(period_unit)%><%}}%></td>
		</tr>
</table>
<input type="hidden" name="product_id" value="<%=product_id%>">
 
<%@ include file="/includes/foot.inc"%>
<%product.remove();%>
</BODY>
</HTML>
<SCRIPT LANGUAGE="javascript">
	var productCode;
	var obj = document.getElementById("product_val");
	if(obj.length > 1){
		if(document.theform.task.value == 'task'){
			productCode = obj.options[1].value;
			changeProduct(productCode);
		}
		document.theform.task.value = '';
	}
</script>