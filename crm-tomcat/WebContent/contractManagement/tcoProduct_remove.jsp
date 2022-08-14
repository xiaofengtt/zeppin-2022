<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoProductLocal tcoProductLocal = EJBFactory.getTcoProduct();
TcoProductVO tcoProductVO = new TcoProductVO();
String[] s = request.getParameterValues("coProduct_id");
int coProduct_id;

tcoProductVO.setInput_man(input_operatorCode);
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		coProduct_id = Utility.parseInt(s[i], 0);
		if(coProduct_id != 0)
		{
			tcoProductVO.setCoProduct_id(new Integer(coProduct_id));
			tcoProductLocal.delete(tcoProductVO);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "tcoproductset.jsp";
</SCRIPT>
<%
tcoProductLocal.remove();
 %>
