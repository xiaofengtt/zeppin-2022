<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.system.*,enfo.crm.service.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.intrust.AttachmentLocal" %>
<jsp:directive.page import="java.io.InputStream"/>
<jsp:directive.page import="java.io.DataInputStream"/>
<jsp:directive.page import="java.io.DataOutputStream"/>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.displayImage",clientLocale)%> </TITLE><!--客户图片显示-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type="text/javascript">  
function goUrl(url){
	var ifr = document.getElementById('download');   
	ifr.src = url;  
}
</script>  

</HEAD>
<BODY class="BODY">
<%
//获得CUST_ID
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
%>
<table border="0" width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td colspan="2">
		<%
		if(cust_id.intValue() > 0){
			AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
			AttachmentVO attachment_vo = new AttachmentVO();
			attachment_vo.setDf_talbe_id(new Integer(4));
			attachment_vo.setDf_serial_no(cust_id);
		
			List attachmentList = attachmentLocal.load(attachment_vo);
			if(attachmentList.size() > 0) out.print("证件附件下载：");
			Iterator attachment_it = attachmentList.iterator();
			HashMap attachment_map = new HashMap();
	       while(attachment_it.hasNext()){
				attachment_map = (HashMap)attachment_it.next();
				String attachment_id = Utility.trimNull(attachment_map.get("ATTACHMENTID"));

				String origin_name = Utility.trimNull(attachment_map.get("ORIGIN_NAME"));
		%>
			<a title="下载附件" href="#" onclick="javascript:goUrl('/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=origin_name%>');" ><%=origin_name%></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<%}%>
			<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
			<%if(attachmentList.size() > 0) {%> <hr><%} %>
		<%} %>
		</td>
	</tr>
	<tr>
		<td align="center">
			<img border=0 height=220 onerror='javascript:this.style.display="none"' src="<%=request.getContextPath()%>/client/clientinfo/show_client_image1.jsp?cust_id=<%=cust_id %>">
		</td>
		<td align="center">
			<img border=0 height=220 onerror='javascript:this.style.display="none"'  src="<%=request.getContextPath()%>/client/clientinfo/show_client_image2.jsp?cust_id=<%=cust_id %>">  
		</td>
	</tr>
</table>
</BODY>
</HTML>
