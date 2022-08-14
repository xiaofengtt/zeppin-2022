<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, java.math.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
	String product_code= Utility.trimNull(request.getParameter("product_code"));
	String product_name = Utility.trimNull(request.getParameter("product_name"));
	String class_type1 = Utility.trimNull(request.getParameter("class_type1"));
	Integer start_date = Utility.parseInt(request.getParameter("start_date"),null);
	Integer start_date2=Utility.parseInt(request.getParameter("start_date2"),null);
	BigDecimal fact_money = Utility.parseDecimal(request.getParameter("fact_money"),null);
	BigDecimal fact_money2 = Utility.parseDecimal(request.getParameter("fact_money2"),null);
	String summary = Utility.trimNull(request.getParameter("summary"));
%>

<html>
<head>
<title>��ѯ����</title>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

function op_save()
{
	syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
   	syncDatePicker(document.theform.start_date2_picker,document.theform.start_date2);
	
	if(document.theform.start_date.value>document.theform.start_date2.value)
   {
	  sl_alert("���ڷ�Χ����-��Ʒ�����������޲��ܴ��ڲ�Ʒ������������!");
	  return false;		
    }
	var v = new Array();	
	v[0]=document.theform.product_code.value;
	v[1]=document.theform.class_type1.value;	
	v[2]=document.theform.product_name.value;
	v[3]=document.theform.start_date.value;
	v[4]=document.theform.start_date2.value;
	v[5]=document.theform.fact_money.value;
	v[6]=document.theform.fact_money2.value;
	v[7]=document.theform.summary.value;

	window.returnValue = v;
	window.close();
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
</table>
<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right">��Ʒ��ţ�</td><!--��Ʒ���-->
			<td>
				<input type="text" style="width:120" name="product_code" value="<%=product_code%>">
			</td>
			<td align="right">��Ʒ���ࣺ</td><!--���з�ʽ-->	
			<td>
				<select name="class_type1" style="width:122">
					<%=Argument.getClassType1Options(class_type1)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">��Ʒ���ƣ�</td><!--��Ʒ����-->
			<td colspan="3">
				<input type="text" name="product_name" size="68" value="<%=product_name%>">
			</td>
		</tr>
		<tr>
			<td align="right">�����������ޣ�</td>
			<td>
				<input TYPE="text" style="width:120" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="start_date" value="">	</td>
			<td align="right">�����������ޣ�</td>
			<td>
				<input TYPE="text" style="width:120" NAME="start_date2_picker" class=selecttext value="<%=Format.formatDateLine(start_date2)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date2_picker,theform.start_date2_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="start_date2" value="">	</td>
		</tr>
		<tr>
			<td align="right">��Ʒ��ģ���ޣ�</td>
			<td>				
				<input type="text" style="width:120" name="fact_money" value="<%=Utility.trimNull(fact_money)%>">	
			</td>
			<td align="right">��Ʒ��ģ���ޣ�</td>
			<td>				
				<input type="text" style="width:120" name="fact_money2" value="<%=Utility.trimNull(fact_money2)%>">	
			</td>
		</tr>
		<tr>
			<td align="right">ȫ��������</td><!--��Ʒ����-->
			<td colspan="3">
				<input type="text" name="summary" size="68" value="<%=summary%>">
			</td>
		</tr>
		</tr>
  </table>	
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:op_save();">ȷ��(<u>O</u>)</button>
				&nbsp;&nbsp;<!--����-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">ȡ��(<u>C</u>)</button>
				&nbsp;&nbsp;<!--ȡ��-->
				</td>
			</tr>
</table>
</form>
</body>
</html>
