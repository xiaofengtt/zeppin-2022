<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
// SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
// SalesResultForStatisticVO vo = new SalesResultForStatisticVO();

SalesChangesLocal sclocal = EJBFactory.getSalesChanges();
SalesChangesVO scVo = new SalesChangesVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal dz_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("dz_money")),new BigDecimal(0));
String dz_time = Utility.trimNull(request.getParameter("dz_date"));
Integer statusDri = Utility.parseInt(request.getParameter("statusDri"),new Integer(-2));

String re_check_reason = Utility.trimNull(request.getParameter("re_check_reason"));
boolean bSuccess = false;
// Map map = new HashMap();
Map scmap = new HashMap();
if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	
	re_check_reason = Utility.trimNull(file.getParameter("re_check_reason"));
	statusDri = Utility.parseInt(file.getParameter("statusDri"),new Integer(-2));
	
	scVo.setSerial_no(serial_no);
	scVo.setInput_man(input_operatorCode);
	
	scVo.setSerial_no(serial_no);
	scVo.setInput_man(input_operatorCode);
	scVo.setStatus(statusDri);
	scVo.setRe_check_reason(re_check_reason);
// 	try{
		sclocal.recheckSalesChanges(scVo);
		bSuccess = true;
// 	}catch(Exception e){
// 		out.println("<script language=\"javascript\">alert(\""+e.getMessage()+"��"+""+"\")</script>");
// 		out.println("<script language=\"javascript\">window.close();</script>");
// 		bSuccess = false;
// 	}

}else{
	if (serial_no.intValue() > 0){
		scVo.setSerial_no(serial_no);
		List sclist = sclocal.querySalesChanges(scVo);
		if (sclist.size() > 0) scmap = (Map)sclist.get(0);
		
// 		vo.setSerial_no(Utility.parseInt(Utility.trimNull(map.get("FK_TPREMONEYDETAIL")),new Integer(0)));
// 		List list = local.querySalesResultForStatistic(vo);
// 		if (list.size() > 0) map = (Map)list.get(0);
	}
}
//��������ܻ����
String yh_money_cn = Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(scmap.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")));
BigDecimal yh_money = Utility.parseDecimal(Utility.trimNull(scmap.get("DZ_MONEY")), new BigDecimal(0.00),2,"1");

// local.remove();
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

function op_check(s)
{
	if (s==5)
		str="��ȷ��ȷ��ͨ����";
	else 
		str="��ȷ��ȷ�ϲ�ͨ����"; 
			
	if(confirm(str)) {
		disableAllBtn(true);
		document.theform.statusDri.value=s;
		document.theform.submit();
	}
}

</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="yh_money_cn" value="<%=yh_money_cn%>">
<input type=hidden name="yh_money" value="<%=yh_money%>">
<input type=hidden name="statusDri" value="0">
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
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(scmap.get("PRODUCT_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right">�ͻ�����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(scmap.get("CUST_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>����ʱ��:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="dz_date" id="dz_date" size="30" value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(scmap.get("TC_DZ_DATE")),new Integer(0)))%>"/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>���˽��:</td>
		<td align="left">
			<input type="text"  readonly="readonly" name="dz_money" size="30"  value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(scmap.get("TC_DZ_MONEY")), new BigDecimal(0.00),2,"1")) %>">
			<br><span id="dz_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right">ԭ�����ͻ�����:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(scmap.get("FROM_SERVICE_MAN_NAME")) %>">
			<input type=hidden name="from_service_man" value="<%=Utility.trimNull(scmap.get("FROM_SERVICE_MAN"))%>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>ת������ͻ�����:</td>
		<td align="left">
			<select name="to_service_man" disabled="disabled" style="width: 147px">
				<%=Argument.getManager_Value(Utility.parseInt(Utility.trimNull(scmap.get("TO_SERVICE_MAN")),new Integer(0))) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>ת�ý��:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="zr_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,zr_money_cn)" value="<%=Utility.parseDecimal(Utility.trimNull(scmap.get("CHANGE_MONEY")), new BigDecimal(0.00),2,"1") %>">
			<br><span id="zr_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right">ת��������:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="change_reason" class="edline" size="50" value="<%=Utility.trimNull(scmap.get("CHANGE_REASON")) %>">
		</td>
	</tr>
	<tr>
		<td align="right">������:</td>
		<td align="left">
			<input type="text" name="re_check_reason" class="edline" size="50" value="">
		</td>
	</tr>
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:op_check(5);">���ͨ�� (<u>S</u>)</button>
		&nbsp;&nbsp;<!--���ͨ��-->
		<button type="button"  class="xpbutton4" accessKey=o id="btnSave" name="btnSave" onclick="javascript:op_check(4)">��˲�ͨ�� (<u>O</u>)</button>
		&nbsp;&nbsp;<!--��˲�ͨ��-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>