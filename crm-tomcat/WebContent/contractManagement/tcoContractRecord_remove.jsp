<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractRecordLocal tcoContractRecordLocal = EJBFactory.getTcoContractRecord();
TcoContractRecordVO tcoContractRecordVO = new TcoContractRecordVO();
String[] s = request.getParameterValues("mainRecord_id");
int mainRecord_id;

tcoContractRecordVO.setInput_man(input_operatorCode);
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		mainRecord_id = Utility.parseInt(s[i], 0);
		if(mainRecord_id != 0)
		{
			tcoContractRecordVO.setMainRecord_id(new Integer(mainRecord_id));
			tcoContractRecordLocal.delete(tcoContractRecordVO);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "tcocontractrecordset.jsp";
</SCRIPT>
<%
tcoContractRecordLocal.remove();
 %>
