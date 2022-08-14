<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象
SaleParameterVO vo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
ProductLocal prdocutLocal = EJBFactory.getProduct();
ProductVO productVO = new ProductVO();

//页面变量
Integer serial_no = new Integer(0);
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer team_id = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
String team_name = Utility.trimNull(request.getParameter("team_name"));
Integer sub_productId = Utility.parseInt(request.getParameter("sub_productId"),new Integer(0));
BigDecimal quota_money = Utility.parseDecimal(request.getParameter("quota_money"),new BigDecimal(0));//销售配额
Integer team_q_num = Utility.parseInt(request.getParameter("team_q_num"),new Integer(0));//可分配数量配额
Integer quota_qualified_num = Utility.parseInt(request.getParameter("quota_qualified_num"),new Integer(0));//合格投资人数量配额
int sub_product_list = Utility.parseInt(request.getParameter("sub_product_list"),0);

boolean bSuccess = false;
List listAll = null;
List subProductListAll = null;
Map map = null; 

//获得产品所在的团队配额信息
if(productId.intValue()!= 0){
	productVO.setProduct_id(productId);
	productVO.setSub_product_id(sub_productId);
	subProductListAll = prdocutLocal.listSubProduct(productVO);//获得子产品集
	
}

//保存配额信息
if(request.getMethod().equals("POST")){
	vo.setProductID(productId);
	vo.setTeamID(team_id);
	vo.setSerial_NO(serial_no);
	
	if(sub_product_list>0){
		for(int i =0;i<sub_product_list;i++){

			vo.setSerial_NO(Utility.parseInt(Utility.trimNull(request.getParameter("serial_no_"+i)),new Integer(0)));
			vo.setQuota_qualified_num(Utility.parseInt(Utility.trimNull(request.getParameter("quota_qualified_num_"+i)),new Integer(0)));
			vo.setQuotaMoney(Utility.parseDecimal(Utility.trimNull(request.getParameter("quota_money_"+i)), new BigDecimal(0),2,"1"));
			vo.setSub_product_id(Utility.parseInt(request.getParameter("sub_product_id_"+i),new Integer(0)));
			sale_parameter.modi(vo,input_operatorCode);
		}
	}else{
		vo.setSerial_NO(Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0)));
		vo.setQuota_qualified_num(Utility.parseInt(Utility.trimNull(request.getParameter("quota_qualified_num")),new Integer(0)));
		vo.setQuotaMoney(Utility.parseDecimal(Utility.trimNull(request.getParameter("quota_money")), new BigDecimal(0),2,"1"));
		vo.setSub_product_id(sub_productId);
		sale_parameter.modi(vo,input_operatorCode);
	}
	bSuccess = true;
}
%>

<html>
<head>
<TITLE><%=LocalUtilis.language("message.saleQuotaInfo",clientLocale)%></TITLE>
<!--修改销售配额-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>
	function validateForm(form){
		//验证销售配额
		if(document.theform.quota_qualified_num != null&&document.theform.quota_qualified_num.value<0){
			alert("您输入的配额不合法，请输入不小于零的整数！");
			return false;
		}
		//验证数量配额
		if(document.theform.quota_money != null&&document.theform.quota_money.value<0){
			alert("您输入的配额不合法，请输入不小于零的整数！");
			return false;
		}
		sub_product_list = document.theform.sub_product_list.value;
		if(sub_product_list>0){
			for( i=0;i<sub_product_list;i++)
			{
				if(!sl_checkDecimal(findElement("quota_money_"+i), '<%=LocalUtilis.language("class.quotaMoney",clientLocale)%> ', 13, 3, 1))
					return false;//销售配额 
				//alert(document.theform.quota_money_0.value);
				if(document.theform.quota_money_0 != null && document.theform.quota_money_0.value<0){
					alert("您输入的配额不合法，请输入不小于零的整数！");
					return false;//销售配额 
				}
				if(document.theform.quota_qualified_num_0 != null && document.theform.quota_qualified_num_0.value<0){
					alert("您输入的配额不合法，请输入不小于零的整数！");
					return false;//数量配额
				}
			}
		}else{	
			if(!sl_checkDecimal(form.quota_money, '<%=LocalUtilis.language("class.quotaMoney",clientLocale)%> ', 13, 3, 1))
				return false;//销售配额 
		}
		return sl_check_update();
		document.theform.submit();
	}

	
	function showCnMoneyZ(value,name)
	{
		temp = value;
		if (trim(value) == ""){
			name.innerText = "<font color='red'>(元)</font>";
		}else{
			name.innerText = "(" + numToChinese(temp) + ")";
		}
	}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="sale_parameter_QuotaUpdate.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="productId" value="<%=productId %>">
<input type=hidden name="sub_productId" value="<%=sub_productId %>">
<input type=hidden name="team_id" value="<%=team_id %>">
<input type=hidden name="sub_product_list" value="<%=subProductListAll.size() %>">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.modiSalesQuota",clientLocale)%> </b></font></td>
		</tr><!--修改销售配额信息-->
	</table>
	<table border="0" width="100%" >
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("class.teamName",clientLocale)%> :&nbsp;&nbsp;</td><!--团队名称-->
			<td align="left">
				<input type="text" name="team_name" readonly="readonly" class="edline" value="<%=team_name%>" onkeydown="javascript:setProduct(this.value);" size="25">
			</td>
		</tr>

<%
System.out.println(subProductListAll.size());
if(subProductListAll.size()>0){ 
for(int i=0;i<subProductListAll.size();i++){ 
	Map subProductMap = (Map)subProductListAll.get(i);
	String sub_product_name = Utility.trimNull(subProductMap.get("LIST_NAME"));
	Integer sub_product_id = Utility.parseInt(Utility.trimNull(subProductMap.get("SUB_PRODUCT_ID")),new Integer(0));

	//每个子产品的销售配额数值
	vo.setTeamID(team_id);
	vo.setProductID(productId);
	vo.setSub_product_id(sub_product_id);
	listAll = sale_parameter.queryQuota(vo,input_operatorCode);

	//if(subProductListAll.size() == listAll.size()){
		Map map2 = (Map)listAll.get(0); 
		serial_no = Utility.parseInt(Utility.trimNull(map2.get("SERIAL_NO")),new Integer(0));
		team_name = Utility.trimNull(map2.get("TEAM_NAME"));
		quota_money = Utility.parseDecimal(Utility.trimNull(map2.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
		quota_qualified_num = Utility.parseInt(Utility.trimNull(map2.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
	//}
%>
	</table>
<fieldset>	
		<legend><b style="color: #215dc6;">子产品(<%=i+1 %>):<%=sub_product_name %></b></legend>	
		<input type="hidden" name="sub_product_id_<%=i%>" value="<%=sub_product_id%>"/>
		<input type="hidden" name="serial_no_<%=i%>" value="<%=serial_no%>"/>
		<table>
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> :&nbsp;&nbsp;</td><!--销售配额-->
			<td align="left"><input type="text" name="quota_money_<%=i%>" size="25" value="<%=quota_money%>" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,quota_money_cn_<%=i%>);"></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<span id="quota_money_cn_<%=i %>"  class="span"></span>
			</td>
		</tr>	
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人数量配额-->
			<td align="left"><input type="text" name="quota_qualified_num_<%=i%>" size="25" value="<%=quota_qualified_num%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
	</table>
</fieldset>
<%}}else{ 

		//map = (Map)listAll.get(0); 
		//serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		//team_name = Utility.trimNull(map.get("TEAM_NAME"));
		//quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
		//quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
%>
		<tr>
			<td align="right" width="180px">
				<input type=hidden name="serial_no" value="<%=serial_no %>">
				<%=LocalUtilis.language("class.saleQuota",clientLocale)%> :&nbsp;&nbsp;</td><!--销售配额-->
			<td align="left"><input type="text" name="quota_money" size="25" value="<%=quota_money%>" onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,quota_money_cn);"></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<span id="quota_money_cn"  class="span"></span>
			</td>
		</tr>	
		<tr>
			<td align="right" width="180px"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人数量配额-->
			<td align="left"><input type="text" name="quota_qualified_num" size="25" value="<%=quota_qualified_num%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>	
			
	</table>
<%} %>
	<table border="0" width="100%">
		<tr>
			<td align="center">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
				</td>
	</tr>
</table>
</form>
</body>
</html>

