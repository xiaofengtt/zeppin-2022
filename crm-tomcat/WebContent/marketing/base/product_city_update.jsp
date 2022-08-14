<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
boolean bSuccess = false;
boolean badded = false;
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

Integer city_serial_no = new Integer(0);
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String city_name = Utility.trimNull(request.getParameter("city_name"));
String gov_regional = Utility.trimNull(request.getParameter("gov_regional"));
String gov_prov_regional = Utility.trimNull(request.getParameter("gov_prov_regional"));

if (request.getMethod().equals("POST")){
	vo.setProduct_id(product_id);
	vo.setInput_man(input_operatorCode);
	vo.setCity_name(city_name);
	vo.setGov_prov_pegional(gov_prov_regional);
	vo.setGov_pegional(gov_regional);

	city_serial_no = product.adddeletecity1(vo);
	bSuccess = true;
}
%>

<html>
<head>
<base target="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title></title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT language="JavaScript">

<%if (bSuccess){%>
	var ret = new Array();
	ret[0] = '<%=city_serial_no%>';
	ret[1] = '<%=city_name%>';
	window.returnValue = ret;
	window.close();
<%}
else if (badded){%>
	sl_update_ok();
<%}%>

function save(){
   	if(!sl_check(document.theform.city_name,'推介地名称 ',20,1)) return false;
  	if(confirm('您确定要新建吗 ？')){
		 disableAllBtn(true);
		 document.theform.submit();
	}
}

/*通过省市过滤相关的行政区域*/
function setGovRegional(value,flag){
	if(value != "" && value.length > 0){
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
	}
}

/*通过省市过滤相关的行政区域 回调函数*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='gov_regional' style='width: 128px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}
</SCRIPT>
</head>

<body topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)" class="body body-nox">
<form name="theform" action="product_city_update.jsp" method="post">
<input type="hidden" name="product_id" value=<%=product_id%>>
<table border="0" width="400" cellspacing="0" cellpadding="4">
	<tr>
		<td colspan="2" background="<%=request.getContextPath()%>/images/headerbg.gif"><b><font color="#FFFFFF"><%=LocalUtilis.language("menu.productCityTjd",clientLocale)%> </font></b></td>
		<!--产品推介地信息设置-->
	</tr>

	<tr>
		<td align="right"><%=LocalUtilis.language("class.governmentRegion",clientLocale)%> :</td><!-- 政府行政区域 -->
 		<td> 			
			<select size="1"  name="gov_prov_regional" style="width: 120px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
				<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),Utility.trimNull(gov_prov_regional))%>
			</select>
			<span id="gov_regional_span"></span><!-- 省级行政区域过滤出来的相关区域 -->
 		</td> 
	</tr>

	<tr>
		<td width="" align="right"><%=LocalUtilis.language("class.tjdName",clientLocale)%> :</td><!--推介地名称-->
		<td width=""><input onkeydown="javascript:nextKeyPress(this)" maxlength=8 name="city_name" size="20" value="<%=city_name%>"></td>
	</tr>
	
	<tr>
		<td colspan="2">
		<table border="0" width="100%">
			<tr>
				<td align="right">
				<button type="button"  class="xpbutton3" accessKey=s  name="btnSave" onclick="javascript:save();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%product.remove();%>
