<%@ page contentType="text/plain; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ page import="java.sql.*"%>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

Connection conn = null;
Statement stmt = null;
try {
	conn = CrmDBManager.getConnection();
	stmt = conn.createStatement();
	String sql = "UPDATE TSYSMESSAGE SET IS_READ=2 WHERE SERIAL_NO="+serial_no;
	stmt.executeUpdate(sql);
} finally {
	if (stmt!=null) stmt.close();
	if (conn!=null) conn.close();
}
%>