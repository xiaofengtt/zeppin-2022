<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractLocal tcoContractLocal = EJBFactory.getTcoContract();
TcoContractVO tcoContractVO = new TcoContractVO();
String[] s = request.getParameterValues("coContract_id");
int coContract_id;
tcoContractVO.setCheck_flag(new Integer(2));
tcoContractVO.setCheck_man(input_operatorCode);
Date now = new Date();
SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
String str_now=format.format(now);
tcoContractVO.setCheck_date(Utility.parseInt(str_now,new Integer(0)));
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		coContract_id = Utility.parseInt(s[i], 0);
		if(coContract_id != 0)
		{
			tcoContractVO.setCocontract_id(new Integer(coContract_id));
			tcoContractLocal.check(tcoContractVO);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("审核完成");//审核完成
	window.returnValue = 1;
	location =  "tcocontractset.jsp";
</SCRIPT>
<%
tcoContractLocal.remove();
 %>
