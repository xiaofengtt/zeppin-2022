<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String sQuery = "";
sQuery += "qContractSubBh=" + request.getParameter("qContractSubBh");
sQuery += "&qPreproductName="+ request.getParameter("qPreproductName");
sQuery += "&qPreproductId="+request.getParameter("qPreproductId");
sQuery += "&qCustName="+request.getParameter("qCustName");
sQuery += "&qStatus="+request.getParameter("qStatus");
sQuery += "&qQsDate1="+request.getParameter("qQsDate1");
sQuery += "&qQsDate2="+request.getParameter("qQsDate2");

sQuery += "&page="+request.getParameter("page");
sQuery += "&pagesize="+request.getParameter("pagesize");

ContractLocal contract = EJBFactory.getContract();
ContractUnrealVO vo  = new ContractUnrealVO();

boolean bSuccess = false;
Integer preproductId = Utility.parseInt(request.getParameter("preproductId"), new Integer(-1));
vo.setPreproductId(preproductId);
vo.setInputMan(input_operatorCode);
contract.convertContractUnreal(vo);	
bSuccess = true;
%>

<HTML>
<HEAD>
<TITLE>非正式合同转成正式认购合同</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" />
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
var sQuery = "<%=sQuery%>";
var retUrl = "sales_contract_unreal.jsp?"+sQuery;

<%if (bSuccess) {%>
	sl_alert("已全部转成正式认购合同！");
	location.href = retUrl;
<%}%>
</script>
</HEAD>

<body>
</BODY>
</HTML>
<%
	contract.remove();
%>
