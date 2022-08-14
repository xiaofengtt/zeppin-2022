<?xml version="1.0" encoding="gbk" ?>
<%@ page contentType="text/xml; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.affair.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>

<%
TcustmanagersLocal local = EJBFactory.getTcustmanagers();

Integer depart_id = Utility.parseInt(request.getParameter("depart_id"), new Integer(1));
TcustmanagersVO vo = new TcustmanagersVO();

List list = null;
Map map = null ;
list = local.listBySql(depart_id);

Integer departId = new Integer(0);
String departName = "";
Integer bottom_flag = new Integer(0);
StringBuffer sb = new StringBuffer(20000);
sb.append(
	"<TreeNode NodeId=\"msdnlib587\" Href=\"/nhp/default.asp?contentid=28000519\" Title=\".NET Development\" ParentXmlSrc=\"top.xml\" NodeXmlSrc=\"manager_tree.jsp\">");
for(int i = 0 ; i<list.size();i++){
	map = (Map)list.get(i);
	sb.append("<TreeNode NodeId=\"1\" Href=\"#");
	sb.append(Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null)+"$"+Utility.parseInt(Utility.trimNull(map.get("MANAGERID")),null));
	sb.append("\"");
	sb.append(" label=\""+Utility.trimNull(map.get("LEVEL_NO"))+"\"");
	sb.append(" Title=\"");
	sb.append(Utility.trimNull(map.get("LEVEL_NO"))+"_"+Utility.trimNull(map.get("LEVEL_NAME")));
	sb.append("\"");
	
	Integer b = Utility.parseInt(Utility.trimNull(map.get("LEFT_ID")),new Integer(0));
	Integer bb = Utility.parseInt(Utility.trimNull(map.get("RIGHT_ID")),new Integer(0));
	
	if(b.intValue()+1 != bb.intValue())
		sb.append(" NodeXmlSrc=\"manager_tree.jsp?depart_id=" +Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null)
		 + "\"");
	sb.append("></TreeNode>");
}
sb.append("</TreeNode>");
local.remove();
System.out.println(sb.toString());
out.print(sb.toString());
%>





