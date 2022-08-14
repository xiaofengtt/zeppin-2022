<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String tel = Utility.trimNull(request.getParameter("tel"));
List list = new ArrayList();

CallCenterLocal callcenter = EJBFactory.getCallCenter();
CCVO vo = new CCVO();

vo.setPhoneNumStr(tel);
vo.setInputMan(input_operatorCode);
if(tel.length()>0){
	IPageList pageList = callcenter.listCustByPhone2(vo, 1, -1);
	list = pageList.getRsList();
}

%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--�绰ƥ��-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
function matchAction(){
	var tel = document.getElementById("tel").value;
	var telnum = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	
	if(tel.length==0){
		sl_alert('������ƥ��绰��');
		return false;
	}
	/*else if(!telnum.test(tel)){
		//������ĵ绰����  �����Ϲ����������������
        sl_alert('<fmt:message key="index.callcenter.yourTel"/>'+tel+'<fmt:message key="index.callcenter.confirmRuls"/>');
		return false;
	}*/
	if(!sl_checkNum(document.getElementById("tel"), "�绰����",20,7))	return false;//�ֻ�����
	document.getElementById("theform").action = "callingMatch.jsp";
	document.getElementById("theform").submit();
}
</script>
</head>
<body class="body">
<form id="theform" name="theform" method="post">
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b>�绰ƥ��</b></font><!--�绰ƥ��-->
	</div>	
	<hr noshade color="#808080" size="1" width="100%"">

	<div>
		&nbsp;&nbsp;�绰���룺&nbsp;&nbsp;		
		<input type="text" id="tel" name="tel" value="<%=tel%>" style="width:120px;"/>
		<!-- ѡ�� -->&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="xpbutton2" id="btnMatch" onclick="javascript:matchAction();">ƥ��</button>		
	</div>
	
	<div style="margin-top:5px;" align="center">
		<table border="0" width="900px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trtagsort">
				<td align="center" width="100px"><fmt:message key="class.ID"/></td><!--���-->
				<td align="center" width="100px"><fmt:message key="class.name"/></td><!--����-->
				<td align="center" width="100px"><fmt:message key="class.custType"/></td><!--�ͻ�����-->
				<td align="center" width="100px"><fmt:message key="class.customerType"/></td><!--�ͻ����-->
				<td align="center" width="100px"><fmt:message key="class.accountManager"/></td><!--�ͻ�����-->
				<td align="center" width="100px"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> </td><!--��ͥ�绰-->
				<td align="center" width="100px"><%=LocalUtilis.language("class.companyPhone",clientLocale)%></td><!--��˾�绰-->
				<td align="center" width="100px"><%=LocalUtilis.language("class.customerMobile",clientLocale)%></td><!--�ֻ�-->
				<td align="center" width="100px"><%=LocalUtilis.language("class.customerMobile",clientLocale)%>2</td><!--�ֻ�2-->
		     </tr>
		  </table>   
		     <span id="tableList" style="overflow-y:auto;height:200;">
				<table border="0" width="900px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
						<%
							Iterator it = list.iterator();
							Map map = new HashMap();
							int iCount = 0;
							String is_deal_name = "";
							Integer is_deal2;
							Integer service_man;
							
							while(it.hasNext()){
								map = (Map)it.next();
								is_deal2 = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
								is_deal_name = Argument.getWTName(is_deal2);
								service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
						%>
							<tr class="tr<%=(iCount % 2)%>">
								<td width="100px" align="center"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
								<td width="100px" align="center"><input type="text" class="ednone" style="width:95%" value="<%=Utility.trimNull(map.get("CUST_NAME"))%>" readonly></td>
								<td width="100px" align="center"><%=is_deal_name%></td>
								<td width="100px" align="center"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
								<td width="100px" align="center"><%=Argument.getOperatorName(service_man)%></td>
								<td width="100px" align="center"><%=Utility.trimNull(map.get("H_TEL"))%></td>
								<td width="100px" align="center"><%=Utility.trimNull(map.get("O_TEL"))%></td>
								<td width="100px" align="center"><%=Utility.trimNull(map.get("MOBILE"))%></td>
								<td width="100px" align="center"><%=Utility.trimNull(map.get("BP"))%></td>
							</tr>
						<%	
								iCount++;
							}
							
							if(iCount==0){
						%>
							<tr class="tr0">
								<td colspan="9" align="center">
									<font size="4" face="΢���ź�">δ�ҵ�ƥ��ͻ�</font>
								</td>
							</tr>
						<%}%>
				</table>
			</span>
	
	</div>
</form>
</body>
</html>