<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.customer.*,java.io.File" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
File n_file = new File("D:\\CARD");
if(!n_file.exists())
	 n_file.mkdirs();
//��ԤԼ���еõ��ͻ�Id Ȼ���ڿͻ����л�ȡ�ͻ���Ϣ��
String name = Utility.trimNull(request.getParameter("name"));
String state = Utility.trimNull(request.getParameter("state"));
String summary = Utility.trimNull(request.getParameter("summary"));
String id = Utility.trimNull(request.getParameter("card_id"));
CustomerLocal local = EJBFactory.getCustomer();
if(!name.equals("")&&!id.equals("")&&!summary.equals("")&&!state.equals("")){
	local.updateCheckStatus(name,id,state,summary);
}

local.remove();
%>

<HTML>
<HEAD>
<TITLE>�ֳ�ǩԼ</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<SCRIPT LANGUAGE="javascript">
//ɾ���ļ�
    var fso = new ActiveXObject("Scripting.FileSystemObject"); // �������� 
    if( fso.FileExists("D:\\CARD\\ResultCheckImage.jpg") ){
        fso.DeleteFile("D:\\CARD\\ResultCheckImage.jpg");
    }
    if( fso.FileExists("D:\\CARD\\ResultCheckText.txt") ){
        fso.DeleteFile("D:\\CARD\\ResultCheckText.txt");
    }
//��ת
	window.parent.returnValue = 1;
	window.parent.location.href('<%=request.getContextPath()%>/marketing/signed_spot.jsp?q_cust_name=<%=name%>');
</SCRIPT>
