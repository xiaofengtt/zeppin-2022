<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoMaintainDetailLocal tcoMaintainDetailLocal = EJBFactory.getTcoMaintainDetail();
TcoMaintainDetailVO tcoMaintainDetailVO = new TcoMaintainDetailVO();

int maintainDetail_id;

DocumentFileToCrmDB file=null;
tcoMaintainDetailVO.setInput_man(input_operatorCode);
file = new DocumentFileToCrmDB(pageContext);
file.parseRequest();
Integer maintain_id=Utility.parseInt(file.getParameter("maintain_id"),new Integer(0));
String action=request.getParameter("action");
if(action.equals("del")){
	String[] s = file.getParameterValues("maintainDetail_id");
	if (s != null)
	{
		for(int i = 0;i < s.length; i++)
		{
			maintainDetail_id = Utility.parseInt(s[i], 0);
			if(maintainDetail_id != 0)
			{
				tcoMaintainDetailVO.setMaintainDetail_id(new Integer(maintainDetail_id));
				tcoMaintainDetailLocal.delete(tcoMaintainDetailVO);
			}
		}
	}
}
if(action.equals("add")){	
	tcoMaintainDetailVO.setMaintain_id(maintain_id);
	tcoMaintainDetailVO.setInput_man(input_operatorCode);
	tcoMaintainDetailVO.setCoContract_id(Utility.parseInt(file.getParameter("coContract_id"),new Integer(0)));
	tcoMaintainDetailVO.setMain_pro_name(file.getParameter("main_pro_name"));
	tcoMaintainDetailVO.setXm_ht_money(Utility.parseDecimal(file.getParameter("xm_ht_money"),new BigDecimal(0.00)));
	tcoMaintainDetailVO.setMain_rate(Utility.parseDecimal(file.getParameter("main_rate"),new BigDecimal(0.00)));
	tcoMaintainDetailVO.setMain_money(Utility.parseDecimal(file.getParameter("main_money"),new BigDecimal(0.00)));
	tcoMaintainDetailLocal.append(tcoMaintainDetailVO);
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("²Ù×÷³É¹¦");
	window.returnValue = 1;
	location =  "tcoContractMaintain_update.jsp?maintain_id="+<%=maintain_id%>;
</SCRIPT>
<%
tcoMaintainDetailLocal.remove();
 %>
