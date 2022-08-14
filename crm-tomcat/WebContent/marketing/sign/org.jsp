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
//从预约表中得到客户Id 然后在客户表中获取客户信息；
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
<title>合格投资人资格确认登记表（法人、其他组织和个体工商户）</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="0">
<!--media=print 这个属性可以在打印时有效-->
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
				<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('确认要打印吗？'))	{	document.all.WebBrowser.ExecWB(6,6);}">直接打印(<u>P</u>)</button>
				&nbsp;&nbsp;&nbsp;			
				<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" name="btnReturn" onclick="printreturn()">关闭</button>
				&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
</table>
<table style="width:210mm" border="0" cellspacing="0" cellpadding="2" summary="合格投资人资格确认登记表（自然人）" align="center">
	<tr>
		<td align="center" colspan="4"><b><font size="3">北京国际信托有限公司</font></b></td>
	</tr>
	<tr>
		<td align="center" colspan="4"><b><font size="3">合格投资人资格确认登记表（法人、其他组织和个体工商户）<font></b></td>
	</tr>
	<tr>
		<td width="33%"></td>
		<td width="34%" align="center"><%=Format.formatDateCn(Utility.getCurrentDate())%></td>
		<td width="23%" align="right">编号:</td>	
		<td width="10%"></td>
	</tr>		
</table>
<table style="width:210mm;" border="1" cellspacing="0" cellpadding="2" align="center">
  <tr>
    <td rowspan="13" align="center"><strong>委托人填写</strong></td>
    <td>委托人名称</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
  </tr>
  <tr>
    <td>营业执照/证明文件名称</td>
    <td>&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
    <td>营业执照/证明文件号码</td>
    <td>&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
    <td>营业执照/证明文件有效期</td>
    <td>&nbsp;<%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0))))%></td>
  </tr>
  <tr>
    <td>住所和邮编</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>&nbsp;&nbsp;&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
  </tr>
  <tr>
    <td>经营范围</td>
    <td colspan="5">&nbsp;<%=cbsc1%></td>
  </tr>
  <tr>
    <td>法定代表人/负责人姓名</td>
    <td>&nbsp;<%=crnm %></td>
    <td>身份证件/证明文件类型<br /></td>
    <td>&nbsp;<%=Utility.trimNull(Argument.getDictParamName(1108,crit))%></td>
    <td>身份证件/证明文件号码</td>
    <td>&nbsp;<%=crid%></td>
  </tr>
  <tr>
    <td>法人身份证件/证明文件有效期</td>
    <td colspan="2">&nbsp;<%if(crvt != null && crvt.intValue()==0){out.print("");}else{out.print(Utility.trimNull(crvt));}%></td>
    <td>联系电话</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>组织机构代码<br /></td>
    <td colspan="2">&nbsp;<%=cogc%></td>
    <td>税务登记证号码<br /></td>
    <td colspan="2">&nbsp;<%=ctrn %></td>
  </tr>
  <tr>
    <td>控股股东或实际控制人</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("FACT_CONTROLLER"))%></td>
  </tr>
  <tr>
    <td colspan="6"><font size="5">□</font>投资于一个信托计划的最低金额不少于人民币100万元<br />
    <font size="5">□</font>投资于本公司发行的信托计划仍在存续期间的金额不少于人民币100万元</td>
  </tr>
  <tr>
    <td>代办人姓名</td>
    <td colspan="2">&nbsp;</td>
    <td>联系电话</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>身份证件/证明文件类型</td>
    <td>&nbsp;</td>
    <td>身份证件/证明文件号码</td>
    <td>&nbsp;</td>
    <td>证件/证明文件有效期</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>资金来源声明</td>
    <td colspan="5"><font size="5">□</font>经营性收入  <font size="5">□</font>财产性收入  <font size="5">□</font>投资收益   <font size="5">□</font>捐赠收入  <font size="5">□</font>其他收入 </td>
  </tr>
  <tr>
    <td>郑重声明</td>
    <td colspan="5"><strong>本委托人交付给贵司的信托资金为本委托人本人合法所有的财产，不存在非法汇集他人资金参与信托计划的情形。上述资金与其他任何个人、法人及其他组织不存在法律上的任何纠纷。如因违反上述承诺而产生的一切法律后果均由本委托人全部承担。<br />
    </strong>
		<table width="100%">
			<tr>
				<td width="40%"> <strong>委托人或代办人签名：</strong></td>
				<td width="20%"><strong>（印鉴）</strong></td>
				<td width="10%"><strong>年</strong></td>
				<td width="10%"><strong>月</strong></td>
				<td width="10%"><strong>日</strong></td>
				<td width="*"></td>
			</tr>
		</table></td>
  </tr>
  <tr>
    <td><strong>资格审查人填写</strong></td>
    <td colspan="6"><strong>审核资料：</strong><br />
      <font size="5">□</font>申请人填妥的内容<br />
      <font size="5">□</font>营业执照复印件（盖公章）<br />
      <font size="5">□</font>组织机构代码证复印件（盖公章）<br />
      <font size="5">□</font>税务登记证复印件（盖公章）<br />
      <font size="5">□</font>法定代表人/负责人身份证复印件（盖公章）<br />
      <font size="5">□</font>经办人身份证原件及复印件<br />
      <font size="5">□</font>法定代表人授权委托书原件（盖公章）<br />
      <font size="5">□</font>公司章程复印件（盖公章）<br />
    	<table width="100%">
			<tr>
				<td width="25%"> 经办人：</td>
				<td width="25%"> 负责人：</td>
				<td width="15%">（盖章）</td>
				<td width="8%">年</td>
				<td width="8%">月</td>
				<td width="8%">日</td>
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