<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractLocal tcoContractLocal = EJBFactory.getTcoContract();
TcoContractVO tcoContractVO = new TcoContractVO();
String[] s = request.getParameterValues("coContract_id");
int coContract_id;

tcoContractVO.setInput_man(input_operatorCode);
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		coContract_id = Utility.parseInt(s[i], 0);
		if(coContract_id != 0)
		{
			tcoContractVO.setCocontract_id(new Integer(coContract_id));
			tcoContractLocal.delete(tcoContractVO);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "tcocontractset.jsp";
</SCRIPT>
<%
tcoContractLocal.remove();
 %>
