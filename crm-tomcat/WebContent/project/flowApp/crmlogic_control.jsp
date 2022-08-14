<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.project.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
	
	//参数传递
	String className = Utility.trimNull(request.getParameter("className"));
	String methodName = Utility.trimNull(request.getParameter("methodName"));
	String paramName = Utility.trimNull(request.getParameter("paramName"));
	System.out.println("className:"+className);
	System.out.println("methodName:"+methodName);
	System.out.println("paramName:"+paramName);
	String[] s = new String[3];
	s=paramName.split("@@");
	//变量定义
	String returnValue="";
	if(className.equals("CRMBusinessCheck") && methodName.equals("getLogType")){
		returnValue=CRMBusinessCheck.getLogType(paramName);
		//System.out.println("returnValue:"+returnValue);
	}else if(className.equals("CRMBusinessCheck") && methodName.equals("getObjectKeyID")){
		returnValue=CRMBusinessCheck.getObjectKeyID(paramName);
	}else if(className.equals("CRMBusinessCheck") && methodName.equals("getStaffreimbSum")){
		returnValue=CRMBusinessCheck.getStaffreimbSum(paramName);
	}else if(className.equals("CRMBusinessCheck") && methodName.equals("checkBusinessInfo")){
		returnValue=CRMBusinessCheck.checkBusinessInfo(s[0],s[1],s[2]);
	}else if(className.equals("CRMBusinessCheck") && methodName.equals("getPhoneNum")){
		returnValue=CRMBusinessCheck.getPhoneNum(paramName);
	}else if(className.equals("CRMBusinessCheck") && methodName.equals("getPreContractFlow")){
		returnValue=CRMBusinessCheck.getPreContractFlow(paramName);
	}else if(className.equals("CRMBusinessCheck") && methodName.equals("getFlowValues")){
		returnValue=CRMBusinessCheck.getFlowValues(paramName);
	}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	self.returnValue = "<%=returnValue%>";
	self.close();
</SCRIPT>
