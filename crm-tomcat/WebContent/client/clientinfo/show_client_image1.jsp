<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.service.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<jsp:directive.page import="java.io.InputStream"/>
<%@ include file="/includes/operator.inc" %>
<%
//获得CUST_ID
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
if(cust_id.intValue()>0){
	String strSql = "SELECT A.ImageIdentification, B.IMAGE1, B.IMAGE2 FROM TCustomers A LEFT JOIN TCUSTCARDINFO B ON A.CUST_ID = B.CUST_ID where A.CUST_ID ="+cust_id;
 
	java.io.InputStream image1 = null;
		 
	java.sql.Connection conn = CrmDBManager.getConnection();
	java.sql.Statement stmt = conn.createStatement();
	java.sql.ResultSet rs = null;

	byte[] in = null;
	try{
		rs = stmt.executeQuery(strSql);
		
		while(rs.next()){
			in = rs.getBytes("IMAGE1");
			break;
		}
		java.io.OutputStream outputstream = null;
		out.clear();
		out = pageContext.pushBody();
		response.reset();
		response.setContentType("image/jpeg");
		outputstream = response.getOutputStream();
		if (in != null) {
			if (in != null) {
				outputstream.write(in);
				outputstream.flush();
			}
		}
		in = null;
	}
	catch (Exception e) {
			throw new BusiException("读取客户身份证图片失败:" + e.getMessage());
	} finally {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
}
%>

