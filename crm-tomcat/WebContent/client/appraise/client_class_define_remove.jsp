<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得GET参数
Integer define_id = Utility.parseInt(request.getParameter("define_id"), new Integer(0));//分级ID
int level_id = Utility.parseInt(request.getParameter("level_id"), 0);//节点数
Integer detail_id = Utility.parseInt(request.getParameter("detail_id"), new Integer(0));//分级ID

CustClassDefineLocal define_local = EJBFactory.getCustClassDefine();
CustClassDefineVO define_vo = new CustClassDefineVO();
CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
CustClassDetailVO detail_vo = new CustClassDetailVO();

if(level_id % 2 != 0){
	define_vo.setClass_define_id(define_id);
	define_vo.setInput_man(input_operatorCode);
	define_local.delete(define_vo);
}else{
	detail_vo.setClass_detail_id(detail_id);
	detail_vo.setInput_man(input_operatorCode);
	detail_local.delete(detail_vo);
}
%>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("client_class_define.jsp");
</SCRIPT>
<%define_local.remove();
detail_local.remove();
%>