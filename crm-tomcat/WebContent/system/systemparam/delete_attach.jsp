<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ page import="enfo.crm.intrust.*,java.io.*,java.sql.*" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0)); 
Integer showFlag = Utility.parseInt(request.getParameter("showFlag"),new Integer(1)); 
String atta_type = Utility.trimNull(request.getParameter("atta_type")); 
String filename = Utility.trimNull(request.getParameter("filename")); 
if (atta_type.equals("FEASSTUDY") || atta_type.equals("FEASSTUDY_EASY") || atta_type.equals("STUDY_VOICE")) {
	String sql = "update tproductIntroduction set " + atta_type + "=NULL where product_id="+product_id+ " and pre_product_id="+preproduct_id;
	Connection conn = null;
	Statement stmt = null;
	try {
		conn = CrmDBManager.getConnection();
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	} finally {
		if (stmt!=null)
			stmt.close();
		if (conn!=null)
			conn.close();
	}
	
	File f = new File(filename);
	if(f.exists())
		f.delete();
}
%>
<script type="text/javascript">
	location.href = "/wiki/product_intro_material_edit.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&showFlag=<%=showFlag%>";
</script>