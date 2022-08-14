<?xml version="1.0" encoding="gbk" ?>
<%@ page contentType="text/xml; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>

<%
CustGroupLocal local = EJBFactory.getCustGroup();
CustGroupVO vo = new CustGroupVO();
Integer group_id = Utility.parseInt(request.getParameter("depart_id"), new Integer(1));
Integer input_operatorCode = Utility.parseInt(request.getParameter("input_operatorCode"), new Integer(0));
vo.setGroupId(group_id);
vo.setInputMan(input_operatorCode);

List list = null;
Map map = null ;
list = local.queryAll(vo);

Integer departId = new Integer(0);
String departName = "";
Integer bottom_flag = new Integer(0);
StringBuffer sb = new StringBuffer(20000);
sb.append(
	"<TreeNode NodeId=\"msdnlib587\" Href=\"/nhp/default.asp?contentid=28000519\" Title=\".NET Development\" ParentXmlSrc=\"top.xml\" NodeXmlSrc=\"client_group_tree.jsp\">");
for(int i = 0 ; i<list.size();i++){
	map = (Map)list.get(i);
	sb.append("<TreeNode NodeId=\"1\" Href=\"#");
	sb.append(Utility.parseInt(Utility.trimNull(map.get("GroupID")),null));
	sb.append("\"");
	sb.append(" label=\""+Utility.trimNull(map.get("GroupID"))+"\"");
	sb.append(" Title=\"");
	sb.append(Utility.trimNull(map.get("GroupName")));
	sb.append("\"");
	
	Integer b = Utility.parseInt(Utility.trimNull(map.get("LEFT_ID")),new Integer(0));
	Integer bb = Utility.parseInt(Utility.trimNull(map.get("RIGHT_ID")),new Integer(0));
	
	if(b.intValue()+1 != bb.intValue())
		sb.append(" NodeXmlSrc=\"client_group_tree.jsp?depart_id=" +Utility.parseInt(Utility.trimNull(map.get("GroupID")),null)
		 + "\"");
	sb.append("></TreeNode>");
}
sb.append("</TreeNode>");
local.remove();
out.print(sb.toString());
%>
