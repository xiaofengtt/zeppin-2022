<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//���ҳ�洫�ݱ���
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
//ҳ�渨������
String[] totalColumn = new String[0];
List contractList = null;
Map contractMap = new HashMap();
//��ö��󼰽����
ContractLocal contractLocal = EJBFactory.getContract();
ContractVO vo = new ContractVO();
//���ò���
vo.setProduct_name(product_name);
vo.setCust_name(cust_name);
vo.setContract_bh(contract_bh);
//��ý��
IPageList pageList  = contractLocal.queryOldTemp(vo,totalColumn,1,-1);
contractList = pageList.getRsList();
if(contractList.size()>0){
	contractMap = (Map)contractList.get(0);	
}
Integer str_qs_date = Utility.parseInt(Utility.trimNull(contractMap.get("��ͬǩ������")),new Integer(0));
Integer str_jk_date = Utility.parseInt(Utility.trimNull(contractMap.get("�ɿ�����")),new Integer(0));
Integer str_end_date = Utility.parseInt(Utility.trimNull(contractMap.get("��ͬ��ֹ����")),new Integer(0));
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<style media="print">
.noprint { display: none }
</style>

<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

</script>
<BODY>
<FORM>
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD><b><%=Utility.trimNull(contractMap.get("��Ŀ����"))%></b></TD>
		</TR>
		<TR>
			<TD width="100%">
				<FIELDSET >
					<LEGEND><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </LEGEND><!--��ͬ��Ϣ-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</TD><!--��ͬ���-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("��ͬ���"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.signedDate",clientLocale)%> :</TD>	<!--ǩ������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_qs_date)%>"></TD>						
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</TD><!--�ɿ�����-->
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_jk_date)%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.endDate3",clientLocale)%> :</TD><!--��ֹ����-->
							<TD><input class="edline" readonly  type="text" size="25" value="<%=Format.formatDateCn(str_end_date)%>"></TD>
						</TR>
						<TR>	
							<TD align="right"><%=LocalUtilis.language("class.validPeriod",clientLocale)%> :</TD><!--��ͬ����-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("��ͬ����"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%> :</TD><!--��ͬ���-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("��ͬ���"))%>"></TD>
						</TR>
						<TR>	
							<TD align="right">��Ʒ���� :</TD><!--��Ʒ����-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("��Ʒ����"))%>"></TD>
						</TR>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND><%=LocalUtilis.language("message.principalInfo",clientLocale)%> </LEGEND><!--ί������Ϣ-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.custName3",clientLocale)%> :</TD><!--ί��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί����"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.principalType",clientLocale)%> :</TD><!--ί��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί��������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</TD><!--��ϵ��ʽ-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί������ϵ��ʽ"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.address",clientLocale)%> :</TD><!--��ַ-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί���˵�ַ"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</TD><!--��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί�����ʱ�"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> </TD><!--֤������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί����֤������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</TD><!--֤������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί����֤�����"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</TD><!--��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί���˷��˴���"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.oTel2",clientLocale)%> :</TD><!--�̶��绰-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί���˹̶��绰"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</TD><!--�ֻ�-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί�����ֻ�"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</TD><!--�����ʼ�-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("ί���˵����ʼ�"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>
						
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND><%=LocalUtilis.language("message.custInfo",clientLocale)%> </LEGEND><!--��������Ϣ-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.qCustName",clientLocale)%> :</TD><!--����������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("������"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.benType",clientLocale)%> :</TD><!--����������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("����������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</TD><!--��ϵ��ʽ-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("��������ϵ��ʽ"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.address",clientLocale)%> :</TD><!--��ַ-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�����˵�ַ"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</TD><!--��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�������ʱ�"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</TD><!--֤������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("������֤������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</TD><!--֤������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("������֤�����"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</TD><!--��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�����˷��˴���"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.oTel2",clientLocale)%> :</TD><!--�̶��绰-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�����˹̶��绰"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</TD><!--�ֻ�-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�������ֻ�"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</TD><!--�����ʼ�-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�����˵����ʼ�"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND><%=LocalUtilis.language("message.otherInfo",clientLocale)%> </LEGEND><!--������Ϣ-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.bankName",clientLocale)%> :</TD><!--��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("������������"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.branchName",clientLocale)%> :</TD><!--֧������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("֧������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</TD><!--�����˺�-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�����˺�"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.acctUsername",clientLocale)%> :</TD><!--��������-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("��������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.propertyType",clientLocale)%> :</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�Ʋ����"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.propertyName",clientLocale)%> :</TD><!--�Ʋ�����-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�Ʋ�����"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.feeType",clientLocale)%> :</TD><!--�ɿʽ-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�ɿʽ"))%>"></TD>
							<TD align="right">�ʲ���Դ:</TD><!--�ʲ���Դ-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�ʲ���Դ"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�ʲ���Դϸ��:</TD><!--�ʲ���Դϸ��-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("�ʲ���Դϸ��"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>							
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<!--����-->
            <TD align="right"><button type="button"  class="xpbutton3" accessKey=b onclick="javascript:disableAllBtn(true);history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button></TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
</BODY>
</HTML>