<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractMaintainLocal tcoContractMaintainLocal = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO tcoContractMaintainVO = new TcoContractMaintainVO();
String[] s = request.getParameterValues("maintain_id");
int maintain_id=0;
tcoContractMaintainVO.setCheck_flag(new Integer(2));
Date now = new Date();
SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
String str_now=format.format(now);
tcoContractMaintainVO.setCheck_date(Utility.parseInt(str_now,new Integer(0)));
tcoContractMaintainVO.setInput_man(input_operatorCode);
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		maintain_id = Utility.parseInt(s[i], 0);
		if(maintain_id != 0)
		{
			tcoContractMaintainVO.setMaintain_id(new Integer(maintain_id));
			tcoContractMaintainLocal.check(tcoContractMaintainVO);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("审核完成");//审核完成
	window.returnValue = 1;
	location =  "tcocontractmaintainset.jsp";
</SCRIPT>
<%
tcoContractMaintainLocal.remove();
 %>
