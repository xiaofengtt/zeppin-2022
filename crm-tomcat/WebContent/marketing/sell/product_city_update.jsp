<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
boolean bSuccess = false;
boolean badded = false;
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String city_name = request.getParameter("city_name");
Integer city_serial_no = Utility.parseInt(request.getParameter("city_serial_no"), new Integer(0));
if (request.getMethod().equals("POST"))
{	
	vo.setInput_man(input_operatorCode);
	vo.setGov_pegional(Utility.trimNull(request.getParameter("gov_regional")));
	vo.setGov_prov_pegional(Utility.trimNull(request.getParameter("gov_prov_regional")));

	if(city_serial_no.intValue()>0){
		//product.setSerial_no(serial_no);
		vo.setCity_serial_no(city_serial_no);
		product.modiProductCity(vo);
	}else{
		vo.setProduct_id(product_id);
		vo.setCity_name(city_name);
		city_serial_no = product.adddeletecity(vo);
		vo.setCity_serial_no(city_serial_no);
	}
	bSuccess = true;
}else{
	if(city_serial_no.intValue()!=0){
		//product.setSerial_no(serial_no);
		vo.setCity_serial_no(city_serial_no);
		vo.setProduct_id(product_id);
		List list = product.querycity1(vo);
		Map map = (Map)list.get(0);
		vo.setGov_prov_pegional(Utility.trimNull(map.get("GOV_PROV_PEGIONAL")));
		vo.setCity_name(Utility.trimNull(map.get("CITY_NAME")));
		vo.setGov_pegional(Utility.trimNull(map.get("GOV_PROV")));
	}
}

%>

<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title></title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
</head>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT language="JavaScript">
 
<%if (bSuccess)
{%>
	var ret = new Array();
	ret[0] = '<%=vo.getCity_serial_no()%>';
	ret[1] = '<%=vo.getCity_name()%>';
	window.returnValue = ret;
	window.close();
<%}
else if (badded)
{%>
	sl_update_ok();
<%}%>
function save()
{
   if(!sl_check(document.theform.city_name,'<%=LocalUtilis.language("class.tjdName",clientLocale)%> ',60,1)) return false;//推介地名称
  if(confirm('<%=LocalUtilis.language("message.saveTip",clientLocale)%> ？'))//确认要保存吗
	{disableAllBtn(true);
		 document.theform.submit();
	}
}

/*通过省市过滤相关的行政区域*/
function setGovRegional(value,flag)
{
	if(value != "" && value.length > 0)
	{
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
	}

}


/*通过省市过滤相关的行政区域 回调函数*/
function backGovRegional(data)
{
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='gov_regional' style='width: 128px' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:document.theform.city_name.value = document.theform.gov_prov_regional.options[document.theform.gov_prov_regional.selectedIndex].text;setCityName(this);'>"+data+"</select>";
}

function setCityName(obj){
	document.theform.city_name.value = document.theform.city_name.value + obj.options[obj.selectedIndex].text;
}
</SCRIPT>

<base target="_self">
<body class="body body-nox" topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)" onload="javascript:setGovRegional('<%=Utility.trimNull(vo.getGov_prov_pegional()) %>','<%=Utility.trimNull(vo.getGov_pegional())%>')">
<form name="theform" action="product_city_update.jsp" method="post">
<input type="hidden" name="product_id" value=<%=product_id%>>
<input type="hidden" name="city_serial_no" value=<%=city_serial_no%>>
<table border="0" width="100%" cellspacing="0" cellpadding="4">
	<tr>
		<td colspan="2" background="<%=request.getContextPath()%>/images/headerbg.gif"><b><font color="#FFFFFF"><%=LocalUtilis.language("menu.productCityTjd",clientLocale)%> </font></b></td><!-- 产品推介地信息设置 -->
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.governmentRegion",clientLocale)%> :</td><!-- 政府行政区域 -->
 		<td>
 			
		<select size="1"  name="gov_prov_regional" style="width: 120px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');document.theform.city_name.value='';setCityName(this);">
			<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),Utility.trimNull(vo.getGov_prov_pegional()))%>
		</select>
		<span id="gov_regional_span"></span><!-- 省级行政区域过滤出来的相关区域 -->
 		</td> 
	</tr>
	<tr>
		<td width="" align="right"><%=LocalUtilis.language("class.tjdName",clientLocale)%> :</td><!-- 推介地名称 -->
		<td width=""><input <%if(city_serial_no!=null && city_serial_no.intValue()>0) out.print("class=edline readonly");%> onkeydown="javascript:nextKeyPress(this)" maxlength=60 name="city_name" size="50" value="<%=Utility.trimNull(vo.getCity_name())%>"></td>
	</tr>
	
	<tr>
		<td colspan="2">
		<table border="0" width="100%">
			<tr>
				<td align="right">
				<button type="button"   class="xpbutton3" accessKey=s  name="btnSave" onclick="javascript:save();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!-- 保存 -->
				&nbsp;&nbsp;
				<!-- 取消 -->
                <button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%product.remove();
}catch(Exception e){
	throw e;
}%>
