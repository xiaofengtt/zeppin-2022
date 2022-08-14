<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
// 返回用到的原查询条件
String sProductId = Utility.trimNull(request.getParameter("product_id")); 
String sCustName = Utility.trimNull(request.getParameter("cust_name"));
String sContractBh= Utility.trimNull(request.getParameter("query_contract_bh"));
//String sPage = Utility.trimNull(request.getParameter("page")); // 这两个变量已在parameter.inc
//String sPagesize = Utility.trimNull(request.getParameter("pagesize"));
String sRetQuery = "page="+sPage+"&pagesize="+sPagesize+"&product_id="+sProductId+"&query_contract_bh="+sContractBh+"&cust_name="+sCustName;

ApplyreachLocal apply = EJBFactory.getApplyreach();
ApplyreachVO vo = new ApplyreachVO();
String[] s = request.getParameterValues("serial_no");

if (s != null) {
	for (int i = 0;i < s.length; i++) {
		Integer serial_no = Utility.parseInt(s[i], new Integer(0));
		if (serial_no.intValue() > 0) {
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			apply.checkApplyreachContract(vo);

			List list = apply.listBySql(vo);
			Integer cust_id = new Integer(0);
			Integer product_id = new Integer(0);
			if (list.size()>0) {
				Map map = (Map)list.get(0);
				cust_id = (Integer)map.get("CUST_ID");
				product_id = (Integer)map.get("PRODUCT_ID");
			}

			String product_name = Argument.getProductName(product_id);
			String cust_name = Argument.getCustomerName(cust_id, input_operatorCode);
			Argument.addSysMessage(cust_id, "申购合同审核通过"
				, "申购合同审核通过，客户名称："+cust_name+"，产品名称："+product_name, input_operatorCode);
		}
	}
}

apply.remove();
%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.pass",clientLocale)%> ");//审核通过
	location.href = "apply_purchase_check_list.jsp?<%=sRetQuery%>";
</SCRIPT>