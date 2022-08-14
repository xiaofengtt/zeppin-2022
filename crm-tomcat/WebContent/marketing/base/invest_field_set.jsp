<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String invest_field = Utility.trimNull(request.getParameter("invest_field"));
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
boolean bSuccess = false ;

if("POST".equals(request.getMethod())){
	vo.setProduct_id(product_id);
	vo.setInvest_field(invest_field);
	vo.setInput_man(input_operatorCode);
	product.modiProductInvestment(vo);
	bSuccess = true;
}
 %>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>产品发行设置</title>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<script language=javascript>
<%if(bSuccess){%>
	window.returnValue=1;
	window.close();
<%}%>
</script>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="invest_field_set.jsp">
<input type="hidden" name="product_id" value="<%=product_id%>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
		<TR>
			<TD vAlign=top align=left>
				<TABLE cellSpacing=0 cellPadding=4 align=left border=0 width="100%">
					<TR>
						<TD align=middle>
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
                                	<td class="page-title"><font color="#215dc6"><b>CRM投资领域设置</b></font></td>
								</tr>
							</table>
							<br/>
						</TD>
					</TR>
					<TR>
						<TD>				
							<table border="0" width="100%" cellspacing="0" cellpadding="3" >
								<tr>
									<td align="right">CRM投资领域:</td>
									<td>
										<SELECT name="invest_field" style="width:120px;">											
											<%=Argument.getDictParamOptions(1139,invest_field)%>
										</SELECT>
									</td> 
								</tr>
							</table>				
						</TD>
					</TR>
					<TR>
						<TD>		
							<table border="0" width="100%">
								<tr>
									<td align="right">
									<!--保存-->
                                	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:document.theform.submit();">保存 (<u>S</u>)</button>
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
product.remove();

%>