<%@ page contentType="text/html; charset=GBK"  import="java.math.*,enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,java.util.*" %>
<%try{ %>
<%
Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")), new Integer(0));
Integer open_date = Utility.parseInt(Utility.trimNull(request.getParameter("open_date")), new Integer(0));
BigDecimal redem_ption = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("redem_ption"))), new BigDecimal(0));
Integer lflag = Utility.parseInt(Utility.trimNull(request.getParameter("lflag")), new Integer(1));

//RequestLocal local = EJBFactory.getRequest();
//RequestVO vo = new RequestVO();

//vo.setProduct_id(product_id);
//vo.setOpen_date(open_date);
//vo.setNav(redem_ption.divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP));
//vo.setSerial_no(lflag);

//local.checkSqredeemLarge(vo);
%>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>

<SCRIPT LANGUAGE="javascript">
    sl_alert("±£´æ³É¹¦£¡");
	location = "large_redeem_query.jsp?product_id=<%=product_id%>";
</SCRIPT>
<%
	//local.remove();
}catch(Exception e){
	throw e;
}%>
