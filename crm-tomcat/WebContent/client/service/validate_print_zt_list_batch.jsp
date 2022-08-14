<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<% 
//上页传的值
String printInfo = Utility.trimNull(request.getParameter("printInfos"));
String[] printInfoArray  = request.getParameterValues("printInfo");
String printDetail = "";
String[] printInfoDetail = null;
Integer product_id = new Integer(0);
String contract_sub_bh = "";
Integer serial_no = new Integer(0);

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">

<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media="screen">
.show     { display: none }
</style>
<style media="print">
.noprint     { display: none }
.trheight	 { height:50px }
</style>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>

<script language=javascript>

function doPrint(){
window.open("about:blank",'printwindow');
document.theform.action = 'validate_print_zt_list_batch_do.jsp';
document.theform.target = 'printwindow';
document.theform.submit();
}
</script>
</HEAD>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post" >
<input type="hidden" name="printInfo" value="<%=printInfo%>">
	<!---套打方法-->
	<TABLE cellspacing=0 cellpadding=0 border=0 width="100%">
		<TR>
			<TD vAlign=top align=left>
				<TABLE cellspacing=0 cellpadding=0 align=center width="100%" border=0>
					<tr>
						<td align="center" height="60px" class="show">
						</td>
					</tr>
					<% 					
					String url = "";					

					for(int i = 0; i<printInfoArray.length;i++){
							url = "validate_print_zt_list_single_info.jsp";	
							printDetail = printInfoArray[i];
							printInfoDetail = Utility.splitString(printDetail,"|");						

							serial_no = Utility.parseInt(printInfoDetail[0],new Integer(0));
							product_id = Utility.parseInt(printInfoDetail[1],new Integer(0));
							contract_sub_bh = printInfoDetail[2];

							url = url + "?serial_no=" + serial_no + "&product_id=" + product_id + "&contract_sub_bh=" + contract_sub_bh;						
					%>
					<jsp:include page="<%=url%>"></jsp:include>
					<%}%>
					<TR class="noprint">
						<TD align="right">
							<table id=printbtn border="0" width="100%">
								<tr>
									<td align="right">
										<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
										<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrint();">打印(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;

										<!--<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrintPreview()">打印预览(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
										<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:history.back();">返回(<u>B</u>)</button>

									</td>
								</tr>
							</table>
						</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
	</TABLE>
</FORM>
</BODY>
</HMTL>