<%@ page contentType="text/html; charset=GBK" import="javax.mail.Session,java.math.*,java.util.*,enfo.crm.cash.Argument,enfo.crm.cash.Format,enfo.crm.cash.*,enfo.crm.tools.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	//��ȡurl�е�ֵ
	Integer endDate = Utility.parseInt(request.getParameter("endDate"),new Integer(0));//�������ڣ�ѡ������ڣ�
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));//�ͻ�ID
	Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//��ƷID
	Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//�Ӳ�ƷID
	String benAccount = Utility.trimNull(request.getParameter("benAccount"));//�����˺�
	Integer startDate = Utility.parseInt(request.getParameter("startDate"),new Integer(20151111));//��ʼ����
	String content = Utility.trimNull(request.getParameter("content"));//����
	Integer sendTo = Utility.parseInt(request.getParameter("sendTo"),new Integer(0));
	//
	String qcustNo = Utility.trimNull(request.getParameter("qcustNo"));
	String qcustName = Utility.trimNull(request.getParameter("qcustName"));
	String qcardId = Utility.trimNull(request.getParameter("qcardId"));//֤������
	Integer qcustType = Utility.parseInt(request.getParameter("qcustType"), new Integer(0));
	Integer qserviceMan = Utility.parseInt(request.getParameter("qserviceMan"),new Integer(0));
	String qcustTel = Utility.trimNull(request.getParameter("qcustTel"));
	String qaddress = Utility.trimNull(request.getParameter("qaddress"));
	String qUrl = "?custNo=" + qcustNo + "&custName=" + qcustName + "&custType=" + qcustType +  "&serviceMan=" + qserviceMan + "&custTel=" + qcustTel + "&cardId=" + qcardId + "&address=" + qaddress;

	BigDecimal currBen = null;
	BigDecimal currGain = null;
	BigDecimal totalGain = null;
	String productName = "";
	String productJc = "";
	String custEmail = "";//�û�����
	String serviceEmail = "";//����Ա����
	boolean success = false;

	CashBean cash = new CashBean();
	CashVo vo = new CashVo();

	vo.setBeginDate(startDate);
	vo.setCustId(custId);
	vo.setEndDate(endDate);
	vo.setInputMan(input_operatorCode);
	vo.setProductId(productId);
	vo.setSubProductId(subProductId);

	List list = cash.queryNetValueDisclosures(vo);
	Map map = null;
	if(null != list && list.size() != 0){
		map = (Map)list.get(0);
		currBen = Utility.parseDecimal(Utility.trimNull(map.get("CURR_BEN")), new BigDecimal(0.00),2,"1");
		currGain = Utility.parseDecimal(Utility.trimNull(map.get("CURR_GAIN")), new BigDecimal(0.00),2,"1");;
		totalGain = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_GAIN")), new BigDecimal(0.00),2,"1");;
		productName = Utility.trimNull(map.get("PRODUCT_NAME"));
		productJc = Utility.trimNull(map.get("PRODUCT_JC"));
		custEmail = Utility.trimNull(map.get("CUST_EMAIL"));
		serviceEmail = Utility.trimNull(map.get("SERVICE_EMAIL"));
	}

	SendMail2 sendEmail = new SendMail2();
	String email = "";//���������ַ
	if(sendTo.intValue() == 1) {
		email = serviceEmail;
	}
	if(sendTo.intValue() == 2) {
		email = custEmail;
	}
	Map SMTPmap = null;
	if(request.getMethod().equals("POST")){	
		SMTPmap = Argument.getMailMessage();
		String strSMTPHost = Utility.trimNull(SMTPmap.get("SMTP_SERVER"));
		String mailFrom = Utility.trimNull(SMTPmap.get("MAIL_FROM"));
		String user = Utility.trimNull(SMTPmap.get("SMTP_USER"));
		String password = Utility.trimNull(SMTPmap.get("SMTP_USERPWD"));
		sendEmail.setSMTPHost(strSMTPHost,user,password);
		sendEmail.setFromAddress(mailFrom);
		Session session1 = sendEmail.contectServer("true",user,password);
		sendEmail.setAddress(email,sendEmail.TO);
		sendEmail.setSubject("��λ��ֵ��¶");
		sendEmail.setHtmlBody(content);
		sendEmail.sendMail(session1,input_operatorCode);
		success = true;
		content = "";
	}
%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet"/>
		

		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		
		<script type="text/javascript">
			<%if(success) {%>
				alert("���ͳɹ���");
			<%}%>
		
			function sendMail(flag){
				if(flag == 1) {
					var email = document.getElementById("serviceEmail").value;
					document.theform.sendTo.value = 1;
				} else {
					var email = document.getElementById("custEmail").value;
					document.theform.sendTo.value = 2;
				}
				if(email == "" && flag == 1) {
					alert("�ò���Աδ�����ռ�����");
					return false;
				}
				if(email == "" && flag == 2) {
					alert("�ÿͻ���Ϣδ��������");
					return false;
				}
				var regex = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
			    if (!regex.test(email)) {  
			        alert("�����ʽ����ȷ��");  
			        return false;  
			    } 
				if(sl_vb_comfirm("ϵͳȷ�ϣ�\n\n�Ƿ��͵�ǰ�ĵ�λ��ֵ��¶���棿")){
					var content = document.getElementById("printMain").outerHTML;
					document.theform.content.value = content;
					disableAllBtn(true);
					document.getElementById("theform").submit();
				}else{
					return false;
				}
			}

			function goback() {
				location = "cash_cust_choose.jsp" + document.theform.qUrl.value;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="post" action="net_value_disclosures_print.jsp">
			<input type="hidden" name="endDate" value="<%=endDate %>">
			<input type="hidden" name="custId" value="<%=custId %>">
			<input type="hidden" name="productId" value="<%=productId %>">
			<input type="hidden" name="subProductId" value="<%=subProductId %>">
			<input type="hidden" name="benAccount" value="<%=benAccount %>">
			<input type="hidden" name="beginDate" value="<%=startDate %>">
			<input type="hidden" name="content" value="<%=content %>">
			<input type="hidden" id="custEmail" name="custEmail" value="<%=custEmail %>">
			<input type="hidden" id="serviceEmail" name="serviceEmail" value="<%=serviceEmail %>">
			<input type="hidden" name="sendTo" value="<%=sendTo %>">

			<input type="hidden" name="qcustNo" value="<%=qcustNo %>"/>
			<input type="hidden" name="qcustName" value="<%=qcustName %>"/>
			<input type="hidden" name="qcustType" value="<%=qcustType %>"/>
			<input type="hidden" name="qserviceMan" value="<%=qserviceMan %>"/>
			<input type="hidden" name="qcustTel" value="<%=qcustTel %>"/>
			<input type="hidden" name="qcardId" value="<%=qcardId %>"/>
			<input type="hidden" id="qUrl" name="qUrl" value="<%=qUrl%>" />
			<div id="printMain" style="width: 850px;height: 800px;margin-left: 120px;margin-top: 10px;">
				<div align="center" style="font-weight: bold;font-size: 35px;margin-top: 10px;margin-bottom: 10px;"><%=productName %></div>
				<div align="center" style="font-weight: bold;font-size: 35px;margin-top: 10px;margin-bottom: 10px;">��λ��ֵ��¶</div>
				<div style="font-size: 25px;margin-top: 50px;line-height: 200%;font-weight: 600;">�𾴵�ί����:</div>
				<div style="text-indent: 2em;font-size: 25px;line-height: 200%;">��л��Ͷ�����ҹ�˾������"<%=productName %>"�����¼��"<%=productJc %>"����</div>
				<div style="text-indent: 2em;font-size: 25px;line-height: 200%;">��ֹ<%=Format.formatDateCn(endDate.intValue()) %>����˾Ͷ��<%=productJc %>�ۼ���ʵ����������Ϊ<strong><b><u><%=Format.formatMoney2(totalGain)%>Ԫ</u></b></strong>������±�</div>
				<div style="text-indent:2em;font-size: 25px;line-height: 200%;font-weight: 600;">(��ֵ���ڣ�<%=Format.formatDateCn(startDate.intValue()) %>-<%=Format.formatDateCn(endDate.intValue()) %>)</div>
				<div>
					<table border="1" cellspacing="0" cellpadding="2" align="center" style="border-collapse:collapse;" bordercolor="black">
							<tr style="width: 640px;height: 110px;">
								<td align="center" style="font-weight: 600;width: 160px;font-size: 20px;line-height: 200%;">��    ��<br/>(Ԫ)</td>
								<td align="center" style="font-weight: 600;width: 160px;font-size: 20px;line-height: 200%;">����ֵ��ʵ��<br/>��������(Ԫ)</td>
								<td align="center" style="font-weight: 600;width: 160px;font-size: 20px;line-height: 200%;">�ۼ���ʵ������<br/>����(Ԫ)</td>
								<td align="center" style="font-weight: 600;width: 160px;font-size: 20px;line-height: 200%;">��Ϣ��<br/>(��)</td>
							</tr>
							<tr style="width: 640px;height: 70px;">
								<td align="center" style="width: 160px;font-size: 20px;line-height: 200%;"><%=Format.formatMoney2(currBen)%></td>
								<td align="center" style="width: 160px;font-size: 20px;line-height: 200%;"><%=Format.formatMoney2(currGain)%></td>
								<td align="center" style="width: 160px;font-size: 20px;line-height: 200%;"><%=Format.formatMoney2(totalGain)%></td>
								<td align="center" style="width: 160px;font-size: 20px;line-height: 200%;"><%=Format.formatMoney2(currBen.add(totalGain))%></td>
							</tr>
					</table>
				</div>
				<div style="text-indent: 2em;font-size: 25px;line-height: 200%;">�ش�ͨ�档</div>
				<div style="text-align: right;font-size: 20px;line-height: 200%;"><%=application.getAttribute("COMPANY_NAME")%></div>
				<div style="text-align: right;font-size: 20px;line-height: 200%;"><%=Format.formatDateCn(new Date())%></div>
			</div>
			<table border="0" width="90%" cellspacing="1" cellpadding="4" id=table99 align="center" class="Noprint">
				<tr>
					<td>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
								<button class="xpbutton4" accessKey=s name="btnSendMail" title="�����ʼ�������Ա " onClick="javascript:sendMail(1);">�ʼ����� (<u>S</u>)</button>
								&nbsp;&nbsp;&nbsp;
								<button class="xpbutton4" accessKey=m name="btnSendMail" title="�����ʼ����û�" onClick="javascript:sendMail(2);">�ͻ��ʼ� (<u>M</u>)</button>
								&nbsp;&nbsp;&nbsp;
								<button class="xpbutton3" accessKey=p name="btnPrint" title="��ӡ" onclick="javascript:window.print();">��ӡ(<u>P</u>)</button>	 
								&nbsp;&nbsp;&nbsp;
								<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
								&nbsp;&nbsp;&nbsp;
								<button class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">��ӡ������(<u>C</u>)</button>
								&nbsp;&nbsp;&nbsp; 
								<button class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:goback();">����(<u>B</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>