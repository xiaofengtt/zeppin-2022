<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
AuthorizationShareVO authorizationShareVO = new AuthorizationShareVO();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();

	String[] s = request.getParameterValues("btnCheckbox");
	int _id;
	if (s != null){
		for(int i = 0;i < s.length; i++){
			_id = Utility.parseInt(s[i], 0);
			if(_id != 0){	
				authorizationShareVO.setSerial_no(new Integer(_id));
				authorizationShareVO.setInput_man(input_operatorCode);	
				authorizationShareLocal.delete(authorizationShareVO);	
			}
		}
	}

authorizationShareLocal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	sl_remove_ok("<%=request.getContextPath()%>/affair/auth/auth_allocate.jsp");
</SCRIPT>
