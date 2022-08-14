<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
	
	//参数传递
	String className = Utility.trimNull(request.getParameter("className"));
	String methodName = Utility.trimNull(request.getParameter("methodName"));
	String paramName = Utility.trimNull(request.getParameter("paramName"));
	System.out.println("className:"+className);
	System.out.println("methodName:"+methodName);
	System.out.println("paramName:"+paramName);
	
	//变量定义
	String returnValue="";
	if(className.equals("FlowUtil") && methodName.equals("initFlow")){
		try{
			returnValue=FlowUtil.initFlow(paramName);
		}catch(Exception e){
 			e.printStackTrace();
		}
	}else if(className.equals("FlowUtil") && methodName.equals("actionFlow")){
		try{
			returnValue=FlowUtil.actionFlow(paramName);

		}catch(Exception e){
 			e.printStackTrace();
		}

	}else if(className.equals("FlowUtil") && methodName.equals("backFlow")){
		try{
			returnValue=FlowUtil.backFlow(paramName);
		}catch(Exception e){
 			e.printStackTrace();
		}
	}else if(className.equals("FlowUtil") && methodName.equals("untreadFlow")){
		try{
			returnValue=FlowUtil.untreadFlow(paramName);
		}catch(Exception e){
 			e.printStackTrace();
		}
	}else if(className.equals("FlowUtil") && methodName.equals("getFlowDesc")){
		try{
			returnValue=FlowUtil.getFlowDesc(paramName);
			returnValue = java.net.URLEncoder.encode(returnValue,"utf8");
		}catch(Exception e){
 			e.printStackTrace();
		}
	}else if(className.equals("FlowUtil") && methodName.equals("initFlowRelationID")){
		try{
			returnValue=FlowUtil.initFlowRelationID(paramName);
		}catch(Exception e){
 			e.printStackTrace();
		}
	}else if(className.equals("FlowUtil") && methodName.equals("consignFlowTask")){
		try{
			returnValue=FlowUtil.consignFlowTask(paramName);
		}catch(Exception e){
 			e.printStackTrace();
		}
	}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	self.returnValue = "<%=returnValue%>";
	self.close();
</SCRIPT>
