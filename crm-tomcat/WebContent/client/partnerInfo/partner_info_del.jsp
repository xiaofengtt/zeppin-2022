<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
//获取主键ID 
Integer q_partn_type2_flag = Utility.parseInt(Utility.trimNull(request.getParameter("q_partn_type2_flag")),new Integer(1));
String[] temp_partnIds = request.getParameterValues("partn_id");
Integer t_partn_id;

//获得对象
PartnerLocal partnerLocal = EJBFactory.getPartner();
PartnerVO vo = new PartnerVO();

//逐个删除
if(temp_partnIds!=null){
	for(int i=0;i<temp_partnIds.length;i++){
		t_partn_id = Utility.parseInt(temp_partnIds[i], new Integer(0));
		
		if(t_partn_id.intValue()!=0){				
			vo.setPartn_id(t_partn_id);
			vo.setInput_man(input_operatorCode);
			
			partnerLocal.delete(vo);			
		}
	}
}

%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
	<%if(q_partn_type2_flag.intValue()==1){%>
		location =  "channel_info_list.jsp";
	<%}
	else if(q_partn_type2_flag.intValue()==2){%>
		location =  "media_info_list.jsp";
	<%}%>
</SCRIPT>
