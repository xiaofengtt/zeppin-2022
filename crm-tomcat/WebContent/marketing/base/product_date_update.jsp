<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
boolean bSuccess = false;
boolean badded = false;
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String trade_type=request.getParameter("trade_type");
String trade_type_name=request.getParameter("trade_type_name");

String city_name = request.getParameter("city_name");

if (request.getMethod().equals("POST"))
{
	vo.setProduct_id(product_id);
	vo.setSerial_no(new Integer(0));
	vo.setInput_man(input_operatorCode);
	vo.setTrade_type(request.getParameter("trade_type"));
	vo.setTrade_type_name(request.getParameter("trade_type_name"));
	vo.setTrade_date(Utility.parseInt(request.getParameter("begin_date"),new Integer(Utility.getCurrentDate())));
	vo.setDescription(request.getParameter("description"));
	product.addDeleteDate1(vo);
	bSuccess = true;
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


<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT language="JavaScript">

<%if (bSuccess)
{%>
	window.returnValue = true;
	window.close();
<%}
else if (badded)
{%>
	sl_update_ok();
<%}%>
function save(form)
{
   if(!sl_checkDate(form.begin_date_picker,"<%=LocalUtilis.language("message.date",clientLocale)%> "))	return false;//日期
   syncDatePicker(form.begin_date_picker,form.begin_date);
   
  if(confirm("<%=LocalUtilis.language("message.ifToNew",clientLocale)%> ？"))//您确定要新建吗
	{disableAllBtn(true);
		 document.theform.submit();
	}
}
</SCRIPT>

<base target="_self">
<body topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" action="product_date_update.jsp" method="post">
<input type="hidden" name="product_id" value=<%=product_id%>>
<input type="hidden" name="trade_type" value=<%=request.getParameter("trade_type")%>>
<input type="hidden" name="trade_type_name" value=<%=request.getParameter("trade_type_name")%>>

<table border="0" width="400" cellspacing="0" cellpadding="4">
	<tr>
		<td colspan="2" background="<%=request.getContextPath()%>/images/headerbg.gif">
			<b><font color="#FFFFFF"><%=LocalUtilis.language("class.product",clientLocale)%><!--产品-->
			<%=request.getParameter("trade_type_name")%>
			<%=LocalUtilis.language("message.infoSet",clientLocale)%> <!--信息设置--></font></b>
		</td>
	</tr>

	<tr>
		<td width="31%" align="right"><%=request.getParameter("trade_type_name")%><%=LocalUtilis.language("message.date",clientLocale)%> <!--日期-->:</td>
		<td width="64%">
		<INPUT TYPE="text" NAME="begin_date_picker" class=selecttext   value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
		<INPUT TYPE="hidden" NAME="begin_date"   value="">
		 <INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" tabindex="13">
		</td>
	</tr>
	<tr>
		<td width="241" align="right"><%=LocalUtilis.language("class.description",clientLocale)%> <!--备注-->:</td>
		<td width="259"><input onkeydown="javascript:nextKeyPress(this)" maxlength=8 name="description" size="20" value="<%=Utility.trimNull(request.getParameter("description"))%>"></td>
	</tr>
	<%if(!trade_type.equals("211003")){%>
	<tr>
		<td width="241" align="right"><%=LocalUtilis.language("class.ratio",clientLocale)%>/<%=LocalUtilis.language("class.money",clientLocale)%> :</td><!--比率/金额-->
		<td width="259"><input onkeydown="javascript:nextKeyPress(this)" maxlength=8 name="rate" size="20" value="<%=Utility.trimNull(request.getParameter("rate"),"0")%>"></td>
	</tr>
	<%}%>
	<tr>
		<td colspan="2">
		<table border="0" width="100%">
			<tr>
				<td align="right">
				<button type="button"  class="xpbutton3" accessKey=s  name="btnSave" onclick="javascript:save(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> <!--保存-->(<u>S</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> <!--取消-->(<u>C</u>)</button>
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
<%product.remove();%>
