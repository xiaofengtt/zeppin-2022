<?xml version="1.0" encoding="gbk" ?>
<%@ page contentType="text/xml; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>

<%DepartmentLocal local = EJBFactory.getDepartment();
Integer depart_id = Utility.parseInt(request.getParameter("depart_id"), new Integer(0));
DepartmentVO vo = new DepartmentVO();
List list = null;
Map map = null ;
list = local.listBySql(depart_id);

Integer departId = new Integer(0);
String departName = "";
Integer bottom_flag = new Integer(0);
StringBuffer sb = new StringBuffer(20000);
sb.append(
	"<TreeNode NodeId=\"msdnlib587\" Href=\"/nhp/default.asp?contentid=28000519\" Title=\".NET Development\" ParentXmlSrc=\"top.xml\" NodeXmlSrc=\"department_tree.jsp\">");
for(int i = 0 ; i<list.size();i++){
	map = (Map)list.get(i);
	sb.append("<TreeNode NodeId=\"1\" Href=\"#");
	sb.append(Utility.parseInt(Utility.trimNull(map.get("DEPART_ID")),null));
	sb.append("\" Title=\"");
	sb.append(Utility.trimNull(map.get("DEPART_ID"))+"_"+Utility.trimNull(map.get("DEPART_NAME")));
	sb.append("\"");
	Integer b = (Integer)map.get("BOTTOM_FLAG");
	if(b!=null&&b.intValue() == 2)
		sb.append(" NodeXmlSrc=\"department_tree.jsp?depart_id=" +Utility.parseInt(Utility.trimNull(map.get("DEPART_ID")),null)
		 + "\"");
	sb.append("></TreeNode>");
}
sb.append("</TreeNode>");
local.remove();
out.print(sb.toString());
%>
