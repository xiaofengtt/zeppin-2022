<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取主键ID 
String str[] = request.getParameterValues("cust_id");
Integer cust_id = new Integer(0);

//获得对象
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();

if(str!=null){
	 for(int i=0;i<str.length;i++)
	 {
 		cust_id = Utility.parseInt(str[i],new Integer(0));
 		if(cust_id!=null){
 			pre_vo.setCust_id(cust_id);
 			pre_vo.setInput_man(input_operatorCode);

 			preContract.delete_reginfo(pre_vo);
 		}
 		
	 }
}
%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("checkin_list.jsp");
</SCRIPT>
<%preContract.remove();%>