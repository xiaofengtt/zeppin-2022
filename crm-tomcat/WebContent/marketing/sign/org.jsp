<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

Integer aml_serial_no = new Integer(0);
String cbsc1 = "";
String crft = "";
BigDecimal crfd = new BigDecimal(0);;
String ctrn = "";
String crnm = "";
String crit = "";
String crid = "";
Integer crvt = new Integer(0);
String pcnm = "";
String pitp = "";
String picd = "";
Integer pivt = new Integer(0);
Integer cogc_vd = new Integer(0);
String cogc = "";
//��ԤԼ���еõ��ͻ�Id Ȼ���ڿͻ����л�ȡ�ͻ���Ϣ��
Integer scust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
//Integer scust_id = Utility.parseInt(Utility.trimNull("2"), new Integer(0));
CustomerLocal local = EJBFactory.getCustomer();
AmCustInfoLocal amCust = EJBFactory.getAmCustInfo(); 
Map cust_map = new HashMap();
CustomerVO vo = new CustomerVO();
vo.setCust_id(scust_id);
vo.setInput_man(input_operatorCode);
List cust_list=local.listCustomerLoad(vo);

AmCustInfoVO amvo = new AmCustInfoVO();
amvo.setCust_id(scust_id);
amvo.setInput_man(input_operatorCode);
List amlist = amCust.load(amvo); // query INTRUST..TAmCustInfo
Map ammap = null;
if (amlist!=null && amlist.size()>0){
	ammap = (Map)amlist.get(0);
	if(ammap!= null){
			aml_serial_no = Utility.parseInt(Utility.trimNull(ammap.get("SERIAL_NO")),new Integer(0));
			cbsc1 = Utility.trimNull(ammap.get("CBSC"));
			crft = Utility.trimNull(ammap.get("CRFT"));
			crfd = Utility.parseDecimal(Utility.trimNull(ammap.get("CRFD")), new BigDecimal(0),2,"1");
			ctrn = Utility.trimNull(ammap.get("CTRN"));
			crnm = Utility.trimNull(ammap.get("CRNM"));
			crit = Utility.trimNull(ammap.get("CRIT"));
			crid = Utility.trimNull(ammap.get("CRID"));
			crvt = Utility.parseInt(Utility.trimNull(ammap.get("CRVT")),new Integer(0));
			pcnm = Utility.trimNull(ammap.get("PCNM"));
			pitp = Utility.trimNull(ammap.get("PITP"));
			picd = Utility.trimNull(ammap.get("PICD"));
			pivt = Utility.parseInt(Utility.trimNull(ammap.get("PIVT")),new Integer(0));
			cogc = Utility.trimNull(ammap.get("COGC"));
			cogc_vd = Utility.parseInt(Utility.trimNull(ammap.get("COGC_VD")),new Integer(0));
		}
	}
if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ����ˡ�������֯�͸��幤�̻���</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="0">
<!--media=print ������Կ����ڴ�ӡʱ��Ч-->
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<style>
	td{font-size:15px;padding:5px;padding-top:10px;}
</style>
<script language=javascript>
function printreturn(){
	window.close();	
}
</script>

</head>
<body>
<input type="hidden" name="cust_id" value="<%=scust_id%>"> 
<form name="theform"  method="post">
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
				<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('ȷ��Ҫ��ӡ��'))	{	document.all.WebBrowser.ExecWB(6,6);}">ֱ�Ӵ�ӡ(<u>P</u>)</button>
				&nbsp;&nbsp;&nbsp;			
				<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" name="btnReturn" onclick="printreturn()">�ر�</button>
				&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
</table>
<table style="width:210mm" border="0" cellspacing="0" cellpadding="2" summary="�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ���Ȼ�ˣ�" align="center">
	<tr>
		<td align="center" colspan="4"><b><font size="3">���������������޹�˾</font></b></td>
	</tr>
	<tr>
		<td align="center" colspan="4"><b><font size="3">�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ����ˡ�������֯�͸��幤�̻���<font></b></td>
	</tr>
	<tr>
		<td width="33%"></td>
		<td width="34%" align="center"><%=Format.formatDateCn(Utility.getCurrentDate())%></td>
		<td width="23%" align="right">���:</td>	
		<td width="10%"></td>
	</tr>		
</table>
<table style="width:210mm;" border="1" cellspacing="0" cellpadding="2" align="center">
  <tr>
    <td rowspan="13" align="center"><strong>ί������д</strong></td>
    <td>ί��������</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
  </tr>
  <tr>
    <td>Ӫҵִ��/֤���ļ�����</td>
    <td>&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
    <td>Ӫҵִ��/֤���ļ�����</td>
    <td>&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
    <td>Ӫҵִ��/֤���ļ���Ч��</td>
    <td>&nbsp;<%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0))))%></td>
  </tr>
  <tr>
    <td>ס�����ʱ�</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>&nbsp;&nbsp;&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
  </tr>
  <tr>
    <td>��Ӫ��Χ</td>
    <td colspan="5">&nbsp;<%=cbsc1%></td>
  </tr>
  <tr>
    <td>����������/����������</td>
    <td>&nbsp;<%=crnm %></td>
    <td>���֤��/֤���ļ�����<br /></td>
    <td>&nbsp;<%=Utility.trimNull(Argument.getDictParamName(1108,crit))%></td>
    <td>���֤��/֤���ļ�����</td>
    <td>&nbsp;<%=crid%></td>
  </tr>
  <tr>
    <td>�������֤��/֤���ļ���Ч��</td>
    <td colspan="2">&nbsp;<%if(crvt != null && crvt.intValue()==0){out.print("");}else{out.print(Utility.trimNull(crvt));}%></td>
    <td>��ϵ�绰</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>��֯��������<br /></td>
    <td colspan="2">&nbsp;<%=cogc%></td>
    <td>˰��Ǽ�֤����<br /></td>
    <td colspan="2">&nbsp;<%=ctrn %></td>
  </tr>
  <tr>
    <td>�عɹɶ���ʵ�ʿ�����</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("FACT_CONTROLLER"))%></td>
  </tr>
  <tr>
    <td colspan="6"><font size="5">��</font>Ͷ����һ�����мƻ�����ͽ����������100��Ԫ<br />
    <font size="5">��</font>Ͷ���ڱ���˾���е����мƻ����ڴ����ڼ�Ľ����������100��Ԫ</td>
  </tr>
  <tr>
    <td>����������</td>
    <td colspan="2">&nbsp;</td>
    <td>��ϵ�绰</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>���֤��/֤���ļ�����</td>
    <td>&nbsp;</td>
    <td>���֤��/֤���ļ�����</td>
    <td>&nbsp;</td>
    <td>֤��/֤���ļ���Ч��</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>�ʽ���Դ����</td>
    <td colspan="5"><font size="5">��</font>��Ӫ������  <font size="5">��</font>�Ʋ�������  <font size="5">��</font>Ͷ������   <font size="5">��</font>��������  <font size="5">��</font>�������� </td>
  </tr>
  <tr>
    <td>֣������</td>
    <td colspan="5"><strong>��ί���˽�������˾�������ʽ�Ϊ��ί���˱��˺Ϸ����еĲƲ��������ڷǷ��㼯�����ʽ�������мƻ������Ρ������ʽ��������κθ��ˡ����˼�������֯�����ڷ����ϵ��κξ��ס�����Υ��������ŵ��������һ�з��ɺ�����ɱ�ί����ȫ���е���<br />
    </strong>
		<table width="100%">
			<tr>
				<td width="40%"> <strong>ί���˻������ǩ����</strong></td>
				<td width="20%"><strong>��ӡ����</strong></td>
				<td width="10%"><strong>��</strong></td>
				<td width="10%"><strong>��</strong></td>
				<td width="10%"><strong>��</strong></td>
				<td width="*"></td>
			</tr>
		</table></td>
  </tr>
  <tr>
    <td><strong>�ʸ��������д</strong></td>
    <td colspan="6"><strong>������ϣ�</strong><br />
      <font size="5">��</font>���������׵�����<br />
      <font size="5">��</font>Ӫҵִ�ո�ӡ�����ǹ��£�<br />
      <font size="5">��</font>��֯��������֤��ӡ�����ǹ��£�<br />
      <font size="5">��</font>˰��Ǽ�֤��ӡ�����ǹ��£�<br />
      <font size="5">��</font>����������/���������֤��ӡ�����ǹ��£�<br />
      <font size="5">��</font>���������֤ԭ������ӡ��<br />
      <font size="5">��</font>������������Ȩί����ԭ�����ǹ��£�<br />
      <font size="5">��</font>��˾�³̸�ӡ�����ǹ��£�<br />
    	<table width="100%">
			<tr>
				<td width="25%"> �����ˣ�</td>
				<td width="25%"> �����ˣ�</td>
				<td width="15%">�����£�</td>
				<td width="8%">��</td>
				<td width="8%">��</td>
				<td width="8%">��</td>
				<td width="*"></td>
			</tr>
		</table>	
	</td>
  </tr>
	<tr>
		<td style="width:5mm;"></td>
		<td style="width:35mm;"></td>
		<td style="width:40mm;"></td>
		<td style="width:30mm;"></td>
		<td style="width:30mm;"></td>
		<td style="width:35mm;"></td>
		<td style="width:35mm;"></td>
	</tr>	
</table>
</form>
</body>
</html>
<%
local.remove();
%>