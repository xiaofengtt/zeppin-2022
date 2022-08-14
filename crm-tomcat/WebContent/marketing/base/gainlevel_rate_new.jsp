<%@ page contentType="text/html; charset=GBK" %>
<%@page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ page import="java.math.BigDecimal" %>
<%@ include file="/includes/operator.inc" %>

<%
try{
Integer df_serial_no = Utility.parseInt(request.getParameter("df_serial_no"),new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
BigDecimal gain_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("gain_rate")),new BigDecimal(0));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));
Integer gain_flag = Utility.parseInt(request.getParameter("gain_flag"),new Integer(0));
BigDecimal float_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("float_rate")),new BigDecimal(0));
String colDisplayStr = "style='display:none;'";
if(gain_flag.intValue()==2){
	colDisplayStr = "style='display:;'";
}
String error_mess = "" ;
boolean bSuccess = false ;
GainLevelRateLocal local = EJBFactory.getGainLevelRate() ;
GainLevelRateVO vo = new GainLevelRateVO();
if("POST".equals(request.getMethod())){
	
	vo.setGain_rate(gain_rate.multiply(new BigDecimal("0.01")));
	vo.setFloat_rate(float_rate.multiply(new BigDecimal("0.01")));
	vo.setStart_date(start_date);
	vo.setEnd_date(end_date);
	vo.setInput_man(input_operatorCode) ;

	try{
	if(serial_no.intValue()==0){//新增
		vo.setDf_serial_no(df_serial_no);

		local.add(vo);
		bSuccess = true ;
	}else{//修改
		vo.setSerial_no(serial_no);
		
		local.update(vo);
		bSuccess = true ;
	}
	}catch(Exception e){
		error_mess = e.getMessage() ;
	}
	
}
 %>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>收益率设置</title>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess && error_mess.equals("")){%>
	window.returnValue = 1;
	window.close();
<%}else if(!error_mess.equals("")){
		gain_rate=gain_rate.multiply(new BigDecimal("0.01"));
		float_rate=float_rate.multiply(new BigDecimal("0.01"));
%>
   	sl_alert('<%=error_mess%>');
<%}%>

function validateForm(form){
	if(!sl_checkDecimal(form.gain_rate, "收益率", 2, 2,0))		return false;

	if(<%=gain_flag.intValue() %>==2){
		if(!sl_checkDecimal(form.float_rate, "浮动收益率", 2, 2, 0))		return false;
	}
	if(<%=serial_no.intValue() %>!=0){
		if(document.theform.start_date_picker.value==null || document.theform.start_date_picker.value==''){sl_alert("请输入开始日期"); return false;}
		if(!sl_checkDate(document.theform.start_date_picker,'开始日期'))	return false;
		syncDatePicker(form.start_date_picker, form.start_date);
	}
	if(document.theform.end_date_picker.value==null || document.theform.end_date_picker.value==''){sl_alert("<%if(serial_no.intValue()==0){out.print("请输入调置日期");}else{out.print("请输入截止日期");}%>"); return false;}
	if(!sl_checkDate(document.theform.end_date_picker,'日期'))	return false;	
	syncDatePicker(form.end_date_picker, form.end_date);
	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="gainlevel_rate_new.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
<input type=hidden name="serial_no" value="<%=serial_no %>">
<input type=hidden name="df_serial_no" value="<%=df_serial_no %>">
<input type=hidden name="gain_flag" value="<%=gain_flag %>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=left border=0 width="100%">
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
                                <td class="page-title"><font color="#215dc6"><b>收益率设置</b></font></td>
							</tr>
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="3" class="product-list">
							<tr <%if(serial_no.intValue()==0){out.print("style='display:none;'");} %> >
								<td width="39%" align="right">起始日期:</td>
								<td width="60%" >
									<INPUT TYPE="text" id="start_date_picker" NAME="start_date_picker" class=selecttext <%if(start_date.intValue()==0) {%> value="" <%}else{%> value="<%=Format.formatDateLine(start_date) %>" <%}%>>
									<!-- <INPUT TYPE="button"  value="▼" class=selectbtn tabindex="13"> -->
									<INPUT TYPE="hidden" NAME="start_date" <%if(start_date.intValue()==0) {%> value="" <%}else{%> value="<%=start_date%>" <%}%>> 
								</td>
							</tr>
							<tr>
								<td width="39%" align="right"><%if(serial_no.intValue()==0){out.print("调置日期："); out.print("<input type=hidden name='start_date_picker'><input type=hidden name='start_date'>");}else{out.print("截止日期:");} %></td>
								<td width="60%" >
									<INPUT TYPE="text" id="end_date_picker"  NAME="end_date_picker" class=selecttext <%if(end_date.intValue()==0) {%> value="" <%}else{%> value="<%=Format.formatDateLine(end_date) %>" <%}%>>
									<!-- <INPUT TYPE="button"  value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13"> -->
									<INPUT TYPE="hidden" NAME="end_date" <%if(end_date.intValue()==0) {%> value="" <%}else{%> value="<%=end_date%>" <%}%>> 
								</td>
							</tr>
							<script LANGUAGE="javascript">
								laydate({elem:'#start_date_picker',format:'YYYY-MM-DD',istime:false});
								laydate({elem:'#end_date_picker',format:'YYYY-MM-DD',istime:false});
							</script>
							<tr>
								<td width="39%" align="right">收益率:</td>
								<td width="50%" >
								   <input type="text" maxlength="10" name="gain_rate" value="<%=gain_rate%>" onkeydown="javascript:nextKeyPress(this)" size="20">%
								</td>
							</tr>
							<tr <%=colDisplayStr %>>
								<td width="39%" align="right">浮动收益率:</td>
								<td width="50%" >
								   <input type="text" maxlength="10" name="float_rate" value="<%=Format.formatMoney(float_rate.multiply(new BigDecimal("100")))%>" onkeydown="javascript:nextKeyPress(this)" size="20">%
								</td>
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<!--保存-->
                                <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);document.theform.submit();}">保存 (<u>S</u>)</button>
								&nbsp;&nbsp;
								<!--取消-->
                                <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消 (<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
			</TABLE>
			</TD>
		</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
local.remove();
}catch(Exception e){throw e ;}
%>