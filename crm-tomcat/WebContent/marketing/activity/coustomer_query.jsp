<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.customer.CustomerLocal"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
ActivityLocal activity = EJBFactory.getActivity();
List listActivity = activity.queryByListCustomerInfo(serial_no);
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.addActivity",clientLocale)%> </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">

	
</script>
</head>
  <body class="BODY body-nox">
  	<form name="theform" method="post" action="activity_custominfo_query.jsp">
  		<div align="left" class="page-title">
			<font color="#215dc6"><%=LocalUtilis.language("class.objectCustomerGroup",clientLocale)%></font>
		</div>
  		<table>
  		<tr>
			<td align="right"><%=LocalUtilis.language("class.objectCustomerGroup",clientLocale)%>  &nbsp;</td><!-- 对象客户群组 -->
			<td colspan="3">
				<TABLE>
					<TBODY>
						<TR align="right" valign="middle">
								<TD height="224" width="100%" align="left">
								<select size="16" name="currentMenu" multiple style="width:370; height: 223" >
<%for(int k=0;k<listActivity.size();k++){ 
	Map map = (Map)listActivity.get(k);
	Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
%>
									<option value="<%=Utility.trimNull(map.get("CUST_ID")) %>">
										<%=Argument.getCustomerName(cust_id,input_operatorCode) %>
									</option>
<%} %>
								</select>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right">
				 <!-- 取消 -->	
				 <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
			</td>
		</tr>
  		</table>
  	</form>
  </body>
</html>
