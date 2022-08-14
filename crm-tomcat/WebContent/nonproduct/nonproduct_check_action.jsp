<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
NonProductVO vo = new NonProductVO();
NonProductLocal nonproduct = EJBFactory.getNonProductLocal();

String[] s = request.getParameterValues("q_nonproductId");
int nonproductId;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		nonproductId = Utility.parseInt(s[i], 0);
		if(nonproductId != 0)
		{
			vo.setNonproduct_id(new Integer(nonproductId));
			vo.setCheck_flag(new Integer(2));
			vo.setInput_man(input_operatorCode);
			nonproduct.check(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.pass",clientLocale)%> ");//ษ๓บหอจนý
	window.returnValue = 1;
	location =  "noproduct_check.jsp";
</SCRIPT>
<%nonproduct.remove();%>

