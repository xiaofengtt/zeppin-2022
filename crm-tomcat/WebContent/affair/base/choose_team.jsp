<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*,enfo.crm.affair.*"%>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
System.out.println("--------------ִ����choose_team.jsp--------------------");
//��ȡURl�е���Ϣ
Integer serial_no =  Utility.parseInt(request.getParameter("serial_no"),null);
Integer team_id =  Utility.parseInt(request.getParameter("team_id"),new Integer(0));
Integer productId =  Utility.parseInt(request.getParameter("productId"),null);
Integer sub_productId = Utility.parseInt(request.getParameter("sub_productId"),null);
String choseTeam = request.getParameter("choseTeam");
Integer choseTeamId = new Integer(0);
String showT = "true";
if(choseTeam != null && choseTeam.length() != 0){
	String[] strArr = choseTeam.split(",");
	choseTeamId = new Integer(Integer.parseInt(strArr[0]));
	showT = strArr[1];
}
//��ʼ������
List list = null;
List quotaMoneylist = null;
boolean success = false;
boolean bsuccess = false;
boolean lsuccess = false;
Integer queryAll = null;

Map Qmap = null;
Map map = null;

//����ʵ������
TmanagerteammembersVO vo = new TmanagerteammembersVO();
TmanagerteamsLocal tmanagerteammembers_Bean = EJBFactory.getTmanagerteams();

vo.setSerial_no(serial_no);
vo.setTeam_id(team_id);
vo.setInput_man(input_operatorCode);
vo.setFlag(new Integer(2));

//�õ���ѯ�Ŷӵ����Ŷ��б�����ID�����֣�����
if(input_operator.hasFunc("40115",131)){
	queryAll = new Integer(1);
}else{
	queryAll = new Integer(0);
}
vo.setQueryAll(queryAll);
list = tmanagerteammembers_Bean.my_list_query(vo);
if(list.size()==0){
	lsuccess = true;
}

if(request.getMethod().equals("POST")){

	quotaMoneylist = tmanagerteammembers_Bean.quotaMoneyQueryById(choseTeamId,productId);
	if(null == quotaMoneylist || quotaMoneylist.size() ==0 ){	
		bsuccess = true;
	}else{
			BigDecimal quotaMoney = new BigDecimal(0);
		for(int i = 0;i<quotaMoneylist.size();i++){
			Qmap = (Map)quotaMoneylist.get(i);
			quotaMoney = Utility.parseDecimal(Utility.trimNull(Qmap.get("QUOTAMONEY")), new BigDecimal(0),0,"1");//�������
		}
		if(quotaMoney.intValue()==0){
			bsuccess = true;
		}else{
			success = true;
		}
	}
}
%>

<html>
	<head>
		<meta HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<meta HTTP-EQUIV="Expires" CONTENT="0">
		<base TARGET="_self">
		<title>ѡ���Ŷ�</title>
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
		<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
		<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
		<script SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></script>
		<script language="javascript">
			<%if(lsuccess){%>
				window.returnValue = 0;
				window.close();
			<%}%>
			
			<%if(bsuccess){%>
				alert("���Ŷ�δ�����Ŷ���");
				
			<%}else{
				if(success){	
			%>
				var return_url = "/affair/base/sale_parameter_quota_all.jsp?productId="+<%=productId%> + "&subProductId="+<%=sub_productId%>+"&teamId="+<%=choseTeamId%>+"&showT="+<%=showT%>;
				window.returnValue = return_url;
				window.close();
			<%	}
			  }%>
			function validateForm(form){
				
				var result = false;
				var select = document.getElementsByName("choseTeam");
				for ( var i = 0; i < select.length; i++) {
					if (select[i].checked) {
						result = true;
					}
				}
				if (!result) {
					alert("��ѡ���Ŷӣ�")
					return false;
				}
				document.theform.submit();
			}
		</script>
	</head>
	
	<body class="body">
		<form name="theform" id="theform" method="POST" action="choose_team.jsp" onsubmit="javascript:return validateForm(this);">
			<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
			<input type="hidden" id="team_id" name="team_id" value="<%=team_id%>"/>
			<input type="hidden" id="productId" name="productId" value="<%=productId%>"/>
			<input type="hidden" id="sub_productId" name="sub_productId" value="<%=sub_productId%>"/>
	
			<table cellSpacing="0" cellPadding="4" width="100%" align="center" border="0">
				<tr>
					<td align="left">
						<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
						<font color="#215dc6"><b>ѡ���Ŷ�</b></font>
					</td>
				</tr>
				<tr>
					<td colspan="5"><hr noshade color="#808080" size="1"></td>
				</tr>
			</table>
			<table width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
				<tr class="trh" >	
					<td style="width: 105px;" align="center"><%=LocalUtilis.language("class.memberName",clientLocale)%> </td><!-- ��Ա���� -->
					<td align="center"><%=LocalUtilis.language("class.memberDescription",clientLocale)%> </td><!-- ���� -->
					<td align="center">����</td>
				</tr>
				
				<%	
					List newlist = Argument.getTeamListBySort(list);
					for(int i = 0; i<newlist.size(); i++){
						map = (Map)newlist.get(i);
						team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
						String team_name = Utility.trimNull(map.get("TEAM_NAME"));
						String description = Utility.trimNull(map.get("DESCRIPTION"));
						int son_num = Utility.parseInt(map.get("SON_NUM").toString(),0);
						if(son_num==0){
							showT = ",false";
						}else{
							showT = ",true";
						}
				%>
					<tr class="tr0" style="height: 25px;">
						<td align="center"><%=team_name %></td>
						<td align="center"><%=description %></td>
						<td align="center"><input style="border:none;" type="radio" name="choseTeam" value="<%=team_id%><%=showT %>"/></td>
					</tr>

				<%} %>
				<tr class="tr0" style="height: 25px;">
					<td align="center" colspan="3">
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.btnSave.disabled='true'; document.theform.submit();}">ȷ��</button>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>