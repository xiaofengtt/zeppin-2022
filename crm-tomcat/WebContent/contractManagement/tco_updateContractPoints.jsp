<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractPointsLocal tcoContractPointsLocal = EJBFactory.getTcoContractPoints();
TcoContractPointsVO tcoContractPointsVO = new TcoContractPointsVO();

int subcoContract_id;
DocumentFileToCrmDB file=null;
tcoContractPointsVO.setInput_man(input_operatorCode);
file = new DocumentFileToCrmDB(pageContext);
file.parseRequest();
Integer coContract_id=Utility.parseInt(file.getParameter("coContract_id"),new Integer(0));
String action=request.getParameter("action");
if(action.equals("del")){
	String[] s = file.getParameterValues("subcoContract_id");
	if (s != null)
	{
		for(int i = 0;i < s.length; i++)
		{
			subcoContract_id = Utility.parseInt(s[i], 0);
			if(subcoContract_id != 0)
			{
				tcoContractPointsVO.setSubcoContract_id(new Integer(subcoContract_id));
				tcoContractPointsLocal.delete(tcoContractPointsVO);
			}
		}
	}
}
if(action.equals("add")){	
	Integer coProduct_id=Utility.parseInt(file.getParameter("coProduct_name"),new Integer(0));
	BigDecimal sub_ht_money=Utility.parseDecimal(Utility.trimNull(file.getParameter("sub_ht_money")),new BigDecimal(0.00));
	String point_summary=file.getParameter("point_summary");
	tcoContractPointsVO.setCoContract_id(coContract_id);
	tcoContractPointsVO.setPoint_summary(point_summary);
	tcoContractPointsVO.setCoProduct_id(coProduct_id);
	tcoContractPointsVO.setSub_ht_money(sub_ht_money);
	
	tcoContractPointsLocal.append(tcoContractPointsVO);
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("²Ù×÷³É¹¦");
	window.returnValue = 1;
	location =  "tcoContract_update.jsp?coContract_id="+<%=coContract_id%>;
</SCRIPT>
<%
tcoContractPointsLocal.remove();
 %>
