<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获取页面参数
String catalog_code =  Utility.trimNull(request.getParameter("catalog_code"));
String template_code = Utility.trimNull(request.getParameter("template_code"));
String template_name = Utility.trimNull(request.getParameter("template_name"));
String template_content = Utility.trimNull(request.getParameter("template_content"));
String summary = Utility.trimNull(request.getParameter("summary"));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();


vo.setTemplate_code(template_code);
vo.setTemplate_name(template_name);
vo.setCatalog_code(catalog_code);
vo.setTemplate_content(template_content);
vo.setSummary(summary);
vo.setInput_man(input_operatorCode);
dictparam.addPrintTemplate(vo);

dictparam.remove();
%>
<script language=javascript>
alert("模板新建成功！");
location = "printtemplateset_list.jsp?catalog_code=<%=catalog_code%>";
</script>
