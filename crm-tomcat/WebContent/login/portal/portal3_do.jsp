<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%
	PortalLocal local = null;
	String portalCode  = "";
	Integer opCode = null;
	String type = "";

	try {
		local = EJBFactory.getPortal();
		//页面传递参数
		portalCode  = Utility.trimNull(request.getParameter("portalCode"));
		opCode  = new Integer(Utility.parseInt(Utility.trimNull(request.getParameter("opCode")),0));	
		type =  Utility.trimNull(request.getParameter("type"));
		//处理类别
		if("expand".equals(type)){
			local.updateProtalExpand(portalCode,opCode);
		}else if("collapse".equals(type)){
			local.updatePortalCollapse(portalCode,opCode);
		}else if("close".equals(type)){
			portalCode = portalCode.substring(2);
			local.updatePortalClose(portalCode,opCode,"2");
		}else if("add".equals(type)){
			local.updatePortalAdd(portalCode,opCode);
		}else if("state".equals(type)){
			local.updatePortalUserState(portalCode,opCode);
		}

		response.getWriter().write("1");
	}
catch(Exception e){
	throw e;
}
finally{
	local.remove();
}
%>