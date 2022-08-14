<%@ page contentType="text/plain; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ page import="java.sql.*"%>
<%
//上页传的值
String[] serial_nos = request.getParameterValues("serial_no");
if (serial_nos!=null) {
	Connection conn = null;
	Statement stmt = null;
	try {
		conn = IntrustDBManager.getConnection();
		stmt = conn.createStatement();
		for (int i=0; i<serial_nos.length; i++) {		
			int serial_no = Utility.parseInt(serial_nos[i], 0);
			if (serial_no>0) {
				String sql = "UPDATE TMONEYDETAIL SET PRINT_TIMES=ISNULL(PRINT_TIMES,0)+1 WHERE SERIAL_NO="+serial_no;
				stmt.executeUpdate(sql);
			}
		}
	} finally {
		if (stmt!=null) stmt.close();
		if (conn!=null) conn.close();
	}
}
%>