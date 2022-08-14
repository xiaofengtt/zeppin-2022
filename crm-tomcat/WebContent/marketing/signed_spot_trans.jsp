<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String return_url = request.getHeader("Referer");
//页面传递变量
Integer pre_serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("pre_serial_no")),new Integer(0));
Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")),new Integer(0));
Integer preproduct_id = Utility.parseInt(Utility.trimNull(request.getParameter("preproduct_id")),new Integer(0));
BigDecimal rg_money = Utility.parseDecimal(request.getParameter("rg_money"),new BigDecimal(0));
Integer dz_date = Utility.parseInt(request.getParameter("dz_date"), new Integer(Utility.getCurrentDate()));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
//获得对象及结果集
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm(); 
PreContractCrmVO vo = new PreContractCrmVO(); 
HashMap rowMap = new HashMap();
Integer bind_serial_no = new Integer(0);
//查询
vo.setSerial_no(pre_serial_no);
vo.setInput_man(input_operatorCode);

List rsList = preContract.listPreContractCrm(vo);

if (rsList.size()>0){
	rowMap = (HashMap)rsList.get(0);
	
	bind_serial_no = Utility.parseInt(Utility.trimNull(rowMap.get("BIND_SERIAL_NO")),new Integer(0));
}
try{
	bind_serial_no = preContract.syncPreContract(vo);
}catch(Exception e){
	preContract.remove();
	out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
	out.println("<script type=\"text/javascript\">window.close();</script>");
	return;
}

preContract.remove();
%>
<HTML>
<HEAD>
<TITLE>预约转换预约</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
</HEAD>
<BODY  class="BODY body-nox">
<form name="theform" method="get" action="signed_spot_aciton.jsp">
<input type="hidden" name = "pre_serial_no" value = "<%=pre_serial_no %>">
<input type="hidden" name = "bind_serial_no" value = "<%=bind_serial_no %>">
<input type="hidden" name = "product_id" value = "<%=product_id%>">
<input type="hidden" name = "preproduct_id" value = "<%=preproduct_id%>">
</form>
<script language=javascript>
var url = "signed_spot_aciton.jsp?pre_serial_no=<%=pre_serial_no%>&bind_serial_no=<%=bind_serial_no%>&product_id=<%=product_id%>&preproduct_id=<%=preproduct_id%>";
<%if(bind_serial_no.intValue()==0){%>
	sl_alert("预约数据未同步！");
	url = '<%=return_url%>';
<%}%>
document.theform.submit();
</script>
</BODY>
</HTML>
