<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*"%>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer startDate = Utility.parseInt(request.getParameter("startDate"),null);
Integer endDate = Utility.parseInt(request.getParameter("endDate"),null);
boolean ssuccess = false;
boolean esuccess = false;

if (request.getMethod().equals("POST")){
	if (0!=startDate.intValue() || null!=startDate) {
		ssuccess = true;
	}
	if (0!=endDate.intValue() || null!=endDate) {
		esuccess = true;
	}
}

%>

<html>
	<head>
		<meta HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="0">
		<base TARGET="_self">
		<title>选择预估时间</title>
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
		<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
		<script language="javascript">
			
			<%if (ssuccess && esuccess) {  %>
				var date = "&startDate="+<%=startDate%> + "&endDate="+<%=endDate%>;
				window.returnValue = date;
				window.close();
			<%}%>
			 
			function validateForm(form){
				syncDatePicker(document.theform.start_date_picker, document.theform.startDate);
				syncDatePicker(document.theform.end_date_picker, document.theform.endDate);
				var startDate = document.getElementById("startDate").value;
				var endDate = document.getElementById("endDate").value;
				if (startDate==""){
					alert("请选择预估开始时间!");
					return false;
				}
				if (endDate=="") {
					alert("请选择预估结束时间!")
					return false;
				}
				if (startDate > endDate) {
					alert("请选择预估开始时间小于结束时间!")
					return false;
				}
				document.theform.submit();
			}
			
		</script>
	</head>
	
	<body class="body">
		<form name="theform" id="theform" method="POST" action="cash_choose_date.jsp" onsubmit="javascript:return validateForm(this);">
			<table height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
				<tr>
					<td vAlign=top align=left>
						<table cellSpacing=0 cellPadding=4 align=left border=0 width="100%">
							<tr>
								<td>
									<table border="0" width="100%" cellspacing="0" cellpadding="0">
										<tr>
                                			<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>选择预估时间</b></font></td>
										</tr>
										<tr>
											<td><hr noshade color="#808080" size="1"></td>
										</tr>
									</table>
						
									<table border="0" width="100%" cellspacing="3">
										<tr>
											<td width="39%" align="right">开始日期:</td>
											<td width="60%" >
												<input type="text" style="width:120" id="start_date_picker" name="start_date_picker" class=selecttext value="<%=Format.formatDateLine(startDate)%>" onKeyDown="javascript:nextKeyPress(this)"> 
		       									<input type="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
		        								<input type="hidden" id="startDate" name="startDate" value="">
											</td>
										</tr>
										<tr>
											<td width="39%" align="right">结束日期:</td>
											<td width="60%" >
												<input type="text" style="width:120" id="end_date_picker" name="end_date_picker" class=selecttext value="<%=Format.formatDateLine(endDate)%>" onKeyDown="javascript:nextKeyPress(this)"> 
						        				<input type="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
												<input type="hidden" id="endDate" name="endDate" value="">
											</td>
										</tr>
									</table>
									<br>
									<table border="0" width="100%">
										<tr>
											<td align="center"><button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.btnSave.disabled='true'; document.theform.submit();}">确定</button></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>