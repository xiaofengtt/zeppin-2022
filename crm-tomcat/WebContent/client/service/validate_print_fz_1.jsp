<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<% 
String printInfo = Utility.trimNull(request.getParameter("printInfos"));
//String[] printInfoArray = Utility.splitString(printInfo,",");
String[] printInfoArray  = request.getParameterValues("printInfo");
Integer product_id = new Integer(0);
Integer serial_no = new Integer(0);
String contract_bh = "";
String printDetail = "";
String[] printInfoDetail = null;

String serial_nos = "";
if (printInfoArray != null) 
	for (int i=0; i<printInfoArray.length; i++) 
		if ("".equals(serial_nos)) 
			serial_nos += printInfoArray[i].split("\\|")[0];
		else 
			serial_nos += "," + printInfoArray[i].split("\\|")[0];	
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
.trheight	 { height:80px }
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.2.6.min.js"></script>
<script language=javascript>
window.onload = function() {
		setProjStartDate();
	};

function doPrintSetup(){	//打印设置
	WB.ExecWB(8,1);
}

function doPrintPreview(){	//打印预览
	WB.ExecWB(7,1);
}

function doPrint(){
	//window.open("validate_print_2_do.jsp?flag=2&printInfo=<%=printInfo%>",'printwindow');
	window.open("about:blank",'printwindow');
	document.theform.action = 'validate_print_2_do.jsp?'+new Date();
	document.theform.target = 'printwindow';
	document.theform.submit();
}

function _print() {
	$.ajax({
	  type: 'POST',
	  url: "validate_print_fz_update_print_times.jsp",
	  data: {
			serial_no: [<%=serial_nos%>]
	  }
	});
	
	var i = 0;
	while (document.getElementById("page"+i)) {
		document.getElementById("page"+i).style.top = "0cm";
		document.getElementById("page"+i).style.position = "relative";
		i ++;
	}

	window.print();
}

function setProjStartDate() {
	var retval = showModalDialog('choose_proj_start_date.jsp', '','dialogWidth:350px;dialogHeight:220px;status:0;help:0');
	if (retval) {
		var i = 0;
		while (document.getElementById(i+"start_date")) {
			document.getElementById(i+"start_date").innerHTML = retval;
			i ++;
		}
	}
}
</script>
</HEAD>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>
<input type="hidden" name="printInfo" value="<%=printInfo%>">
<!---套打方法-->
<% 
for (int i = 0; i<printInfoArray.length;i++){
		String url = "validate_print_fz_batch.jsp";	
		printDetail = printInfoArray[i];
		printInfoDetail = Utility.splitString(printDetail,"|");						

		serial_no = Utility.parseInt(printInfoDetail[0],new Integer(0));
		//product_id = Utility.parseInt(printInfoDetail[1],new Integer(0));
		//contract_bh = printInfoDetail[2];
		int pageIndex = i;
		if (i == printInfoArray.length-1) pageIndex = -1;		
		url += "?serial_no=" + serial_no + "&product_id=" + product_id + "&contract_bh=" + contract_bh+"&pint_page_index="+pageIndex
				+ "&i=" + i;							
%>
	<jsp:include page="<%=url%>"></jsp:include>
<%}%>
	<table class="noprint" id=printbtn border="0" width="100%">
		<tr>
			<td align="right">
				<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
				<!--套打设置方法-->
				<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:_print();">打印(<u>P</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<!--<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrintPreview()">打印预览(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
				<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>

			</td>
		</tr>
	</table>
</BODY>
</HMTL>