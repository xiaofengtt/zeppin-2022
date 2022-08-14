<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@page import="enfo.crm.intrust.ProductLocal"%>
<%@page import="enfo.crm.customer.RatingLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String risk_level = Utility.trimNull(request.getParameter("risk_level"));
Integer risk_level_score = Utility.parseInt(Utility.trimNull(request.getParameter("risk_level_score")),new Integer(0));
RatingLocal local = EJBFactory.getRating();
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO(); 
boolean bSuccess = false;
vo.setBook_code(input_bookCode);
vo.setProduct_id(product_id);
vo.setInput_man(input_operatorCode);
if("POST".equals(request.getMethod())){
	vo.setRisk_level_score(risk_level_score); 
	vo.setRisk_level(risk_level);
	local.modiLevelScore(vo);
	bSuccess = true;
}else{
	List list = local.queryLevelScore(product_id);
	if(list.size()>0){
		Map map = (Map)list.get(0);
		risk_level = Utility.trimNull(map.get("RISK_LEVEL"));
		risk_level_score = Utility.parseInt(Utility.trimNull(map.get("RISK_LEVEL_SROCE")),new Integer(0));
	}
}	
%>

<HTML>
<HEAD>
<TITLE>产品风险设置</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
<%if(bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>
function validateForm(form)
{
	if(!sl_checkNum(form.risk_level_score, "", 3, 1))	return false;//分数值
	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" >
<form name="theform" method="POST" action="fx_level.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="product_id" value="<%=product_id %>"/>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title">			
							<font color="#215dc6"><b>产品风险设置</b></font>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" width="100%"  class="product-list">
					<tr>
						<td align="right" width="120px"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> :&nbsp;&nbsp;</td><!-- 风险等级 -->
						<td >
							<SELECT name="risk_level" style="width:120px;" ><%=Argument.getProductRiskGrade(risk_level)%></SELECT></td>
					</tr>
					<tr>
						<td align="right" width="120px">分数值:&nbsp;&nbsp;</td>
						<td ><input type="text" name="risk_level_score" value="<%=risk_level_score %>"></td>
					</tr>
				</table>
				<br><br>
				<table border="0" width="100%">
					<tr>
						<td align="center">
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;<!--保存-->
						<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						&nbsp;&nbsp;<!--取消-->
						</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
</BODY>
</HTML>
<%product.remove();
local.remove(); %>


