<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象及结果集
RatingVO vo = new RatingVO();
ManualScoringLocal manual_scoring = EJBFactory.getManualScoring();

String[] s = request.getParameterValues("manual_id");
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
int manual_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		manual_id = Utility.parseInt(s[i], 0);
		if(manual_id != 0)
		{
			vo.setManual_id(new Integer(manual_id));
			vo.setInput_man(input_man);
			manual_scoring.delete_tmanualscoring(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//删除成功
	window.returnValue = 1;
	location =  "manual_scoring.jsp";
</SCRIPT>
<%manual_scoring.remove();%>
