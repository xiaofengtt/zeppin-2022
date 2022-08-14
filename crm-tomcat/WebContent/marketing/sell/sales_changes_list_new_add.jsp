<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
SalesResultForStatisticVO vo = new SalesResultForStatisticVO();

SalesChangesLocal sclocal = EJBFactory.getSalesChanges();
SalesChangesVO scVo = new SalesChangesVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal dz_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("dz_money")),new BigDecimal(0));
String dz_time = Utility.trimNull(request.getParameter("dz_date"));
Integer records_count = 1;
String yh_money_cn = "";
BigDecimal yh_money = BigDecimal.ZERO;

boolean bSuccess = false;
Map map = new HashMap();

if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	dz_time = Utility.trimNull(file.getParameter("dz_date"));
	dz_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("dz_money")),new BigDecimal(0));
	
	Integer from_service_man = Utility.parseInt(file.getParameter("from_service_man"),new Integer(0));
	Integer to_service_man = Utility.parseInt(file.getParameter("to_service_man"),new Integer(0));
	
	BigDecimal zr_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("zr_money")),new BigDecimal(0));
	
	String change_reason = Utility.trimNull(file.getParameter("change_reason"));
	
	scVo.setSerial_no(serial_no);
	scVo.setFrom_service_man(from_service_man);
	scVo.setTo_service_man(to_service_man);
	scVo.setZr_money(zr_money);
	scVo.setChange_reason(change_reason);
	scVo.setInput_man(input_operatorCode);
	
	sclocal.addSalesChanges(scVo);
	bSuccess = true;

}else{
	if (serial_no.intValue() > 0){
		vo.setSerial_no(serial_no);
		List list = local.querySalesResultForStatistic(vo);
		if (list.size() > 0) map = (Map)list.get(0);
		records_count = Utility.parseInt(Utility.trimNull(map.get("RECORDS_COUNT")),new Integer(0));
		yh_money = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1");
		vo.setSerial_no(0);
		vo.setFk_tpremoneydetail(Utility.parseInt(Utility.trimNull(map.get("FK_TPREMONEYDETAIL")), null));
		vo.setStatus(-1);//��ѯ����Ч��¼ ��������ý��
		List listMore = local.querySalesResultForStatisticMore(vo);
		if (listMore.size() > 0){
			for(int i = 0; i < listMore.size(); i++){
				Map mapMore = (Map)listMore.get(i);
				BigDecimal dz_money_more = Utility.parseDecimal(Utility.trimNull(mapMore.get("DZ_MONEY")), new BigDecimal(0.00),2,"1");
				yh_money = yh_money.subtract(dz_money_more);
			}
		}
	}
}
//��������ܻ����
yh_money_cn = Utility.trimNull(Format.formatMoney(yh_money));
// BigDecimal yh_money = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1");

local.remove();
sclocal.remove();
%>
<html>
<head>
<title>ת��������</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form){
	if (!sl_checkChoice(form.to_service_man, "ת������ͻ�����")) return false;
	
	var from_service_man = form.from_service_man.value;
	var to_service_man = form.to_service_man.value;
	if(from_service_man == to_service_man){
		sl_alert("ת������ͻ�������Ϊԭ�����ͻ�����");
		return false;
	}
	
	if (!sl_checkDecimal(document.theform.zr_money, "ת�ý��", 13, 3, 1)) return false;
	if(document.theform.zr_money.value <= 0){
		sl_alert("ת�ý���С�ڵ����㣡");
		return false;
	}

	if(parseFloat(form.zr_money.value) > parseFloat(form.yh_money.value)){
		sl_alert("����ת�ý��ܴ��ڵ��˽�"+form.yh_money_cn.value+"��");
		return false;
	}
	return sl_check_update();
}

function _checked(obj) {
	document.getElementById("onway_attach").style.display = obj.checked?"":"none";
}

</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="yh_money_cn" value="<%=yh_money_cn%>">
<input type=hidden name="yh_money" value="<%=yh_money%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>ת��������</b></font></td>
	</tr>
	<br/>
</table>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">��Ʒ����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(product_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">�ͻ�����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>����ʱ��:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="dz_date" id="dz_date" size="30" value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0)))%>"/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>���˽��:</td>
		<td align="left">
			<input type="text"  readonly="readonly" name="dz_money" size="30"  value="<%=yh_money_cn %>">
			<br><span id="dz_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right">ԭ�����ͻ�����:</td>
		<td align="left">
			<%if( records_count == -1) {%>
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(map.get("RG_SERVICE_MAN_NAME")) %>">
			<input type=hidden name="from_service_man" value="<%=Utility.trimNull(map.get("RG_SERVICE_MAN"))%>">
			<% } else {%>
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(map.get("SERVICE_MAN_NAME")) %>">
			<input type=hidden name="from_service_man" value="<%=Utility.trimNull(map.get("SERVICE_MAN"))%>">
			<% } %>

		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>ת������ͻ�����:</td>
		<td align="left">
			<select name="to_service_man" style="width: 147px">
				<%=Argument.getManager_Value(null) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>ת�ý��:</td>
		<td align="left">
			<input type="text" name="zr_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,zr_money_cn)" value="">
			<br><span id="zr_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right">ת��������:</td>
		<td align="left">
			<input type="text" name="change_reason" class="edline" size="50" value="">
		</td>
	</tr>
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>