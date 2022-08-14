<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.cash.*,enfo.crm.tools.*,enfo.crm.cash.Format"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String ben_account = Utility.trimNull(request.getParameter("ben_account"));
Integer startdate = Utility.parseInt(Utility.trimNull(request.getParameter("startdate")), new Integer(0));
Integer enddate = Utility.parseInt(Utility.trimNull(request.getParameter("enddate")), new Integer(0));
//
String qcustNo = Utility.trimNull(request.getParameter("qcustNo"));
String qcustName = Utility.trimNull(request.getParameter("qcustName"));
String qcardId = Utility.trimNull(request.getParameter("qcardId"));//֤������
Integer qcustType = Utility.parseInt(request.getParameter("qcustType"), new Integer(0));
Integer qserviceMan = Utility.parseInt(request.getParameter("qserviceMan"),new Integer(0));
String qcustTel = Utility.trimNull(request.getParameter("qcustTel"));
String qaddress = Utility.trimNull(request.getParameter("qaddress"));
String qUrl = "?custNo=" + qcustNo + "&custName=" + qcustName + "&custType=" + qcustType +  "&serviceMan=" + qserviceMan + "&custTel=" + qcustTel + "&cardId=" + qcardId + "&address=" + qaddress;
CashBean cash_ben = new CashBean();
CashVo cash_vo = new CashVo();

cash_vo.setCustId(cust_id);
cash_vo.setProductId(product_id);
cash_vo.setInputMan(input_operatorCode);
cash_vo.setSubProductId(sub_product_id);
cash_vo.setBenAccount(ben_account);
cash_vo.setBeginDate(startdate);
cash_vo.setEndDate(enddate);

Map fundy_map = new HashMap();
Map fundy_map2 = new HashMap();
Map acct_map = new HashMap();
List cash_list = cash_ben.CustFundYieldsubscribe(cash_vo);
List cash_list2 = cash_ben.CustFundflow(cash_vo);
List cash_acct = cash_ben.getCustBank(cash_vo);

String custName = "";
String contract_sub_bh = "";
BigDecimal sg_money = new BigDecimal(0);
BigDecimal total_gain = new BigDecimal(0);
String bank_acct  = "";


String summary = "";
BigDecimal amount = new BigDecimal(0);
String contract_sub_bh1 = "";
BigDecimal ben_amount = new BigDecimal(0);
BigDecimal aft_pre_gain = new BigDecimal(0);



 %>
<HTML>
<HEAD>

<TITLE>cash_fundflow.jsp</TITLE>

<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<style media=print>
.Noprint{display:none;}
</style>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
	/*��������*/
	window.onload = function(){
			initQueryCondition();
	};

	function tochangeacct(bank_account){//alert("11");
		var cust_id = document.theform.cust_id.value;
		var url = "cash_fundflow.jsp?cust_id="+cust_id+"&ben_account="+bank_account;
		location=url;
	}

	function chooseDate(){
		syncDatePicker(document.theform.start_date_picker, document.theform.startdate);
		syncDatePicker(document.theform.end_date_picker, document.theform.enddate);
		disableAllBtn(true);
		document.theform.submit();
	}

	function goback() {
		location = "cash_cust_choose.jsp" + document.theform.qUrl.value;
	}
</script>
</HEAD>
<BODY class="BODY">

<form name="theform"  method="post">
<input type="hidden" name="cust_id" value="<%=cust_id %>">

<input type="hidden" name="qcustNo" value="<%=qcustNo %>"/>
<input type="hidden" name="qcustName" value="<%=qcustName %>"/>
<input type="hidden" name="qcustType" value="<%=qcustType %>"/>
<input type="hidden" name="qserviceMan" value="<%=qserviceMan %>"/>
<input type="hidden" name="qcustTel" value="<%=qcustTel %>"/>
<input type="hidden" name="qcardId" value="<%=qcardId %>"/>
<input type="hidden" name="qUrl" value="<%=qUrl%>" />
<div id="queryCondition" class="qcMain" style="display:none;width:250px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td align="left">��ѯ����</td>
			<td align="right">
			   <button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td>
				��ʼ����:
				<input type="text" id="start_date_picker" name="start_date_picker" class=selecttext value="<%=Format.formatDateLine(startdate)%>" onKeyDown="javascript:nextKeyPress(this)"> 
				<input type="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				<input type="hidden" id="startdate" name="startdate" value="">
			</td>
		</tr>
		<tr>
			<td>
				��������:
				<input type="text" id="end_date_picker" name="end_date_picker" class=selecttext value="<%=Format.formatDateLine(enddate)%>" onKeyDown="javascript:nextKeyPress(this)"> 
				<input type="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				<input type="hidden" id="enddate" name="enddate" value="">
			</td>
		</tr>
		<tr>
			<td align="center">
				<button class="xpButton3" accessKey=o name="btnQuery" onclick="javascript:chooseDate();">ȷ��(<u>O</u>)</button>
			</td>
		</tr>
	</table>
</div>
<table border="0" width="100%" cellspacing="1" cellpadding="4" id=table99 align="center" class="Noprint">
	<tr>
		<td>
			<table border="0" width="100%">
				<tr align="center">
					<td>
						�л������˺ţ�
						<select name="ben_account" onchange="javascript:tochangeacct(this.value);" >	
							<OPTION value=" ">--��ѡ��--</OPTION>
							<%for(int i=0;i<cash_acct.size();i++){ 
								acct_map = (Map)cash_acct.get(i);
							%>		
							<OPTION value="<%=Utility.trimNull(acct_map.get("BANK_ACCT")) %>" 
								<%if((ben_account).equals(Utility.trimNull(acct_map.get("BANK_ACCT"))))out.print("selected"); %>
							>
								<%=Utility.trimNull(acct_map.get("TYPE_CONTENT"))%>-<%=Utility.trimNull(acct_map.get("BANK_ACCT"))%>
							</OPTION>
							<%} %>
						</select>
						&nbsp;&nbsp;
						<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton">��ѯ����(<u>Q</u>)</button>
						&nbsp;&nbsp;
						<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>	
						<button class="xpbutton3" accessKey=p name="btnPrint" title="��ӡ" onclick="javascript:window.print();">��ӡ(<u>P</u>)</button>
						&nbsp;&nbsp;
						<button class="xpbutton3" accessKey=b id="backButton" name="backButton" onclick="javascript:goback();">���� (<u>B</u>)</button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>		
		<td align="right" style="height:30mm;"></td>
	</tr>	
</table>
	

<div align="center" style="height:7mm;"><font size="3"><b>�ֽ������ϸ</b></font></div>	

<table border="1" cellspacing="0" cellpadding="2" width="90%"
		 align="center" style="border-collapse:collapse;"  bordercolor="black">
				  <tr>
					<td align="center" style="height:9mm">�ͻ�����</td>
					<td align="center">��ͬ���</td>
					<td align="center">�깺���</td>
					<td align="center">������ʼ��</td>
					<td align="center">�ۼ����棨Ԫ��</td>
					<td align="center">�����˺�</td>
				  </tr>
<%

Integer cust_ids = new Integer(0);
int count = 0;
Integer rowspan = new Integer(0);
int sum = 0;
boolean bl =true;
boolean bl2 =true;
BigDecimal sumsg = new BigDecimal("0.000");
BigDecimal sumto = new BigDecimal("0.000");

for(int i=0; i<cash_list.size(); i++){
	fundy_map = (Map)cash_list.get(i);
	cust_ids = (Integer)fundy_map.get("CUST_ID");

	custName = (String)fundy_map.get("CUST_NAME");
	contract_sub_bh = (String)fundy_map.get("CONTRACT_SUB_BH");
	sg_money = (BigDecimal)fundy_map.get("SG_MONEY");
	total_gain = (BigDecimal)fundy_map.get("TOTAL_GAIN");
	bank_acct = (String)fundy_map.get("BANK_ACCT");	
	rowspan = (Integer)fundy_map.get("ROWSPAN");
	if(count==0){ 
		count++;
		if(count==rowspan.intValue()){
		 count=0;
		}
		bl = true;
	}else{ 	
		count++;
		if(count==rowspan.intValue()){
		 count=0;
		}
		bl = false;
	}
	sum = cash_list.size();
	if(sg_money != null)
		sumsg = sumsg.add(sg_money);
 %>
						  <tr>
						<%if(i==0){ %>		
						    <td rowspan="<%=sum %>" align="left" style="height:9mm">
								<%=Utility.trimNull(custName)%>
							</td>
						<%} %>	
						<%if(bl==true){ %>
						    <td rowspan="<%=rowspan %>" align="center"><%=Utility.trimNull(contract_sub_bh)%></td>
						<%}else{ %>
					
						<%} %>
						
						    <td align="right"><%=Format.formatMoney(sg_money)%></td>
						    <td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(fundy_map.get("START_DATE")), new Integer(0)))%></td>
						<%if(bl==true){ %>
							<td rowspan="<%=rowspan %>" align="right">
								<%=Format.formatMoney(total_gain)%>
							</td>
						<%}else{ %>
								
						<%} %>
							<td align="center"><%=Utility.trimNull(bank_acct)%></td>
						  </tr>
<%} %>
					<tr>
						<td align="center"  style="height:9mm"><b>�ϼ� <%=sum%> ��</b></td>
						<td align="center">-</td>
						<td align="right"><%="".equals(Format.formatMoney(sumsg))?"0.00":Format.formatMoney(sumsg)%></td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
					</tr>
</TABLE>

<table border="0" cellspacing="0" cellpadding="2" width="90%" align="center">
	<tr>		
		<td align="right" style="height:10mm;"></td>
	</tr>
	<tr>
		<td><font size="3"><b>��ʷ������ϸ</b></font></td>
		<td align="right">��ֹ���ڣ�
			<%if(startdate!=null && startdate.intValue()!=0) out.print(startdate); %>��<%if (enddate!=null && enddate.intValue()!=0) out.print(enddate); else out.print("��"); %>
		</td>	
	</tr>
</table>
		
<table border="1" cellspacing="0" cellpadding="2" width="90%" align="center" style="border-collapse:collapse;"  bordercolor="black">
						<tr>
					      <td align="center" style="height:9mm">����ժҪ</td>
					      <td align="center">��Ԫ��</td>
					      <td align="center">��ͬ���</td>
					      <td align="center">����</td>
					      <td align="center">����</td>
					      <td align="center">δ��ת����</td>
					    </tr>
<%
for(int i=0; i<cash_list2.size(); i++){
	fundy_map2 = (Map)cash_list2.get(i);
	summary = (String)fundy_map2.get("SUMMARY");
	amount = (BigDecimal)fundy_map2.get("AMOUNT");
	contract_sub_bh1 = (String)fundy_map2.get("CONTRACT_SUB_BH");
	ben_amount = (BigDecimal)fundy_map2.get("BEN_AMOUNT");
	aft_pre_gain = (BigDecimal)fundy_map2.get("AFT_PRE_GAIN");

 %>
					    <tr>
					      <td style="height:9mm" align="center"><%=Utility.trimNull(summary)%></td>
					      <td  align="right"><%="".equals(Format.formatMoney(amount))?"0.00":Format.formatMoney(amount)%></td>
					      <td align="center"><%=Utility.trimNull(contract_sub_bh1)%></td>
					      <td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(fundy_map2.get("TRADE_DATE")), new Integer(0)))%></td>
					      <td  align="right"><%="".equals(Format.formatMoney(ben_amount))?"0.00":Format.formatMoney(ben_amount)%></td>
					      <td  align="right"><%="".equals(Format.formatMoney(aft_pre_gain))?"0.00":Format.formatMoney(aft_pre_gain)%></td>
					    </tr>

<% }%>
</TABLE>

</form>
</BODY>
</HTML>
