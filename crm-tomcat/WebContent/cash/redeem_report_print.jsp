<%@ page contentType="text/html; charset=GBK" import="javax.mail.Session,java.util.*,enfo.crm.cash.SendMail2,enfo.crm.cash.Argument,enfo.crm.cash.Format,enfo.crm.tools.*,java.math.BigDecimal,enfo.crm.cash.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	//��ȡurl�е�ֵ
	Integer serialNo = Utility.parseInt(request.getParameter("serialNo"),new Integer(0));//�ͻ�ID
	String productName = Utility.trimNull(request.getParameter("productName"));
	String content = Utility.trimNull(request.getParameter("content"));//����
	Integer sendTo = Utility.parseInt(request.getParameter("sendTo"),new Integer(0));
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));
	//��ؼ�¼����Ϣ
	Integer transDate = new Integer(0);
	Integer firstDzDate = new Integer(0);
	String custName = "";
	BigDecimal AFTAmount = null;
	BigDecimal redeemAmount = null;
	BigDecimal redeemMoney = null;
	BigDecimal firstToMoney = null;
	String bankAcct = "";
	Integer bankId = new Integer(0);
	String bankName = "";
	String custEmail = "";
	String serviceEmail = "";

	CashBean cash = new CashBean();
	CashVo vo = new CashVo();

	vo.setSerialNo(serialNo);
	vo.setInputMan(input_operatorCode);

	List list = cash.queryRedeemReport(vo);
	Map map = null;

	for(int i = 0; i<list.size(); i++){
		map = (Map)list.get(i);
		redeemAmount = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT")), new BigDecimal(0.00),2,"1");
		redeemMoney = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_MONEY")), new BigDecimal(0.00),2,"1");
		AFTAmount = Utility.parseDecimal(Utility.trimNull(map.get("AFT_AMOUNT")), new BigDecimal(0.00),2,"1");
		transDate = Utility.parseInt(Utility.trimNull(map.get("TRANS_DATE")),new Integer(0));
		custName = Utility.trimNull(map.get("CUST_NAME"));
		bankAcct = Utility.trimNull(map.get("BANK_ACCT"));
		bankId = Utility.parseInt(Utility.trimNull(map.get("BANK_ID")),new Integer(0));
		bankName = Utility.trimNull(map.get("BANK_NAME"));
		firstDzDate = Utility.parseInt(Utility.trimNull(map.get("FIRST_DZ_DATE")),new Integer(0));
		firstToMoney = Utility.parseDecimal(Utility.trimNull(map.get("FIRST_TO_MONEY")), new BigDecimal(0.00),2,"1");
		custEmail = Utility.trimNull(map.get("CUST_EMAIL"));
		serviceEmail = Utility.trimNull(map.get("SERVICE_EMAIL"));
	}
	boolean success = false;
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
		sendEmail.setSubject("��ر���");
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
				if(sl_vb_comfirm("ϵͳȷ�ϣ�\n\n�Ƿ��͵�ǰ����ر��� ��")){
					var content = document.getElementById("printMain").outerHTML;
					document.theform.content.value = content;
					disableAllBtn(true);
					document.getElementById("theform").submit();
				}else{
					return false;
				}
			}

			function goback() {
				location = "redeem_record_list.jsp?custId=" +document.theform.custId.value ;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="post" action="redeem_report_print.jsp">
			<input type="hidden" id="custEmail" name="custEmail" value="<%=custEmail %>">
			<input type="hidden" id="serviceEmail" name="serviceEmail" value="<%=serviceEmail %>">
			<input type="hidden" name="content" value="<%=content %>">
			<input type="hidden" name="custId" value="<%=custId %>">
			<input type="hidden" name="sendTo" value="<%=sendTo %>">
			<input type="hidden" name="serialNo" value="<%=serialNo %>">
			<input type="hidden" name="productName" value="<%=productName %>">
			<div id="printMain" style="width: 850px;height: 800px;margin-left: 120px;margin-top: 10px;">
				<div align="center" style="font-weight: bold;font-size: 25px;margin-top: 10px;margin-bottom: 10px;"><%=productName %></div>
				<div align="center" style="font-weight: bold;font-size: 25px;margin-top: 10px;margin-bottom: 10px;">��ر���</div>
				<div style="font-size: 20px;margin-top: 50px;line-height: 200%;">�𾴵Ŀͻ�:</div>
				<div style="text-indent: 2em;font-size: 20px;line-height: 200%;">��л��Ͷ���ҹ�˾������"<%=productName %>"����������������룬�������Ϣ�������£�</div>
				<div style="font-size: 20px;line-height: 200%;">ί����(������) ����/����:&nbsp;&nbsp;<%=custName %></div>
				<div style="font-size: 20px;line-height: 200%;">�����˻����п���:&nbsp;&nbsp;<%=bankAcct %></div>
				<div style="font-size: 20px;line-height: 200%;">�����˻�������:&nbsp;&nbsp;<%=bankName %></div>
				<div style="font-size: 20px;line-height: 200%;">���������:&nbsp;&nbsp;<%=Format.formatDateCn(transDate.intValue()) %></div>
				<div style="font-size: 20px;line-height: 200%;">������طݶ�:&nbsp;&nbsp;<%=Format.formatInt(redeemAmount.intValue())%> ��</div>
				<div style="font-size: 20px;line-height: 200%;">��ͬʣ��ݶ�:&nbsp;&nbsp;<%=Format.formatInt(AFTAmount.intValue())%> ��</div>
				<div>
					<table border="1" cellspacing="0" cellpadding="2" style="border-collapse:collapse;" bordercolor="black">
						<tr>
							<td style="padding-left: 10px;width: 180px;font-size: 20px;line-height: 200%;">��һ���Ϲ�ʱ��</td>
							<td style="padding-left: 10px;width: 650px;font-size: 20px;line-height: 200%;"><%=Format.formatDateCn(firstDzDate.intValue()) %></td>
						</tr>
						<tr>
							<td style="padding-left: 10px;width: 180px;font-size: 20px;line-height: 200%;">��һ���Ϲ����</td>
							<td style="padding-left: 10px;width: 650px;font-size: 20px;line-height: 200%;"><%=Format.formatMoney2(firstToMoney)%> Ԫ</td>
						</tr>
												<tr style="">
							<td style="padding-left: 10px;width: 180px;font-size: 20px;line-height: 200%;">������طݶ�</td>
							<td style="padding-left: 10px;width: 650px;font-size: 20px;line-height: 200%;"><%=Format.formatInt(redeemAmount.intValue())%> ��</td>
						</tr>
						<tr>
							<td style="padding-left: 10px;width: 180px;font-size: 20px;line-height: 200%;">���������ֹ��</td>
							<td style="padding-left: 10px;width: 650px;font-size: 20px;line-height: 200%;"><%=Format.formatDateCn(transDate.intValue()) %></td>
						</tr>
						<tr>
							<td style="padding-left: 10px;width: 180px;font-size: 20px;line-height: 200%;font-weight: bold;">���ʵ�ý��</td>
							<td style="padding-left: 10px;width: 650px;font-size: 20px;line-height: 200%;"><%=Format.formatMoney2(redeemMoney) %> Ԫ</td>
						</tr>
					</table>
				</div>
				<br/>
				<div style="text-indent: 2em;font-size: 20px;line-height: 200%;">�ҹ�˾����<%=Format.formatDateCn(transDate.intValue()) %>��5��������֮�ڣ��������˵����ʵ�ý��ͨ��ת�˷�ʽ�������������˷����˻���ͬʱ����ر������ί����(������)Ԥ������ϵ��ַ����ί����(������)����������������飬�뼰ʱ���ҹ�˾��ϵ������ر������֮�����ʮ����������δ�����������ģ�ί���˾���ر������������������Ρ�</div>
				<div style="text-indent: 2em;font-size: 20px;line-height: 200%;">�ش˱��档</div>
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