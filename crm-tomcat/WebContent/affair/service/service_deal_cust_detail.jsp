<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
CustomerLocal cust_local = EJBFactory.getCustomer();//客户Bean
CustomerVO cust_vo = new CustomerVO();

String cust_no = request.getParameter("cust_no");
int iiCount = 0;
int subflag = Utility.parseInt(request.getParameter("subflag"), 0);
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), null);
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);

//获得客户详细信息、赋值、转换
cust_vo.setCust_id(cust_id);
cust_vo.setInput_man(input_operatorCode);
//cust_vo.setQuery_flag(new Integer(1));
List cust_list = cust_local.listCustomerLoad(cust_vo);
java.io.InputStream in = null;
Map cust_map = new HashMap();
Integer service_man = null;
String custno = "";
String wtflag_checked = "";
String print_deploy_bill_checked = "";
String print_post_info_checked = "";
int isServiceMan = 0;
if(cust_list != null && cust_list.size() > 0)
{
	cust_map = (Map) cust_list.get(0);
	custno = cust_map.get("CUST_NO").toString();
	if (new Integer(cust_map.get("WT_FLAG").toString()).intValue() == 1)
		wtflag_checked = " Y ";
	else
		wtflag_checked = " N ";
	if (cust_map.get("PRING_DEPLOY_BILL") != null) {
		if (new Integer(cust_map.get("PRING_DEPLOY_BILL").toString()).intValue()
			== 1)
			print_deploy_bill_checked = " Y ";
		else
			print_deploy_bill_checked = " N ";
	}
	if (cust_map.get("PRING_POST_INFO") != null) {
		if (new Integer(cust_map.get("PRING_POST_INFO").toString()).intValue()
			== 1)
			print_post_info_checked = " Y ";
		else
			print_post_info_checked = " N ";
	}	
	service_man = Utility.parseInt(Utility.trimNull(cust_map.get("SERVICE_MAN")),new Integer(0));
}
if(input_operatorCode.equals(service_man))
	isServiceMan = 1;//此客户的客户经理和操作员是同一人
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<base target="_self">
<style>
.headdarkbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 90px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}

.headbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 90px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}
</style>
</HEAD>
<script language=javascript>
function show(parm)
{
   for(i=0;i<8;i++)
  {  
     if(i!= parm)
     {
        eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
       if(document.getElementById("r"+i)!=null)
		 eval("document.getElementById('r" + i + "').style.display = 'none'");
	 }
	 else
	 {
	    eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");
	    if(document.getElementById("r"+i)!=null)
		  eval("document.getElementById('r" + i + "').style.display = ''");
	 } 
  }
}
function openitem(product_id)
{
	showModalDialog('product_stage_item2.jsp?readonly=1&product_id='+product_id, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}

function displayCustomerCommunionDetail(serial_no){
   window.open("communion_detail.jsp?serial_no="+serial_no,"_blank","left=200,top=160,width=728,height=480,scrollbars=yes"); 
}
function callCust(target_custid){
	<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
	var url = "<%=request.getContextPath()%>/affair/sms/cust_tel.jsp?target_custid="+target_custid;
	showModalDialog(url,'','dialogWidth:420px;dialogHeight:300px;status:0;help:0');	
	<%}else{%>
		sl_alert('<%=LocalUtilis.language("message.callCenterNotEnabled",clientLocale)%> ');//呼叫中心未启用
	<%}%>
}
</script>

<BODY class="BODY body-nox">

<%@ include file="/includes/waiting.inc"%>
<form name="theform" action="client_query_info.jsp">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding=0>
			<tr>
				<td>
				<table id="titleNameTable" border="0" width="100%" cellspacing="1" cellpadding=0>
					<tr>
						<td align="left"><b><font size="+1"><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></font>
						<font face="新宋体">(<%=Utility.trimNull(custno)%>) </b></td>
					</tr>
				</table>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0"
					class="edline">
					<TBODY>
						<TR class="tr-tabs">
							<TD vAlign=top width=100>&nbsp;</TD>
							<TD vAlign=top width=100>&nbsp;</TD>
							<TD vAlign=top width=100>&nbsp;</TD>
							<!--基本信息-->
                            <TD id=d0 style="background-repeat: no-repeat" onclick=show(0) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 0) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
							<!--认购信息-->
                            <TD id=d1 style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 1) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.subscriptionInfo",clientLocale)%> </TD>
							<!--受益信息-->
                            <TD id=d2 style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 2) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.benInfo",clientLocale)%> </TD>
							<!--收益信息-->
                            <TD id=d3 style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 3) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.incomeInfo",clientLocale)%> </TD>
							<!--修改明细-->
                            <TD id=d4 style="display:none" onclick=show(4) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 3) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.mofifyDteails",clientLocale)%> </TD>
							<TD id=d5 ></TD>
							<!--份额变更-->
                            <TD id=d6 style="display:none" onclick=show(6) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 6) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.shareChange",clientLocale)%> </TD>
							<!--证件图片-->
                            <TD id=d7 style="display:none" onclick=show(7) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 7) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.docImg",clientLocale)%> </TD>
						</TR>
					</TBODY>
				</TABLE>
				<br/>
				</TD>
			</TR>
			<!--start 基本信息-->
			<TR id=r0 <%if (subflag == 0) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=2 width="100%"
					class="tablelinecolor table-popup" border=0>
					<TBODY>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
							<td colspan="3"><%=Utility.trimNull(cust_map.get("CUST_SOURCE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP卡编号-->
							<td><%=Utility.trimNull(cust_map.get("VIP_CARD_ID"))%>［<%if(cust_map.get("VIP_DATE")!=null){Format.formatDateCn(new Integer(cust_map.get("VIP_DATE").toString()));}%>］</td>
							<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--合格投资人编号-->
							<td><%=Utility.trimNull(cust_map.get("HGTZR_BH"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--证件类型-->
							<td><%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
							<td><%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						</tr>
						<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==1)  {%>	
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--出生日期-->
							<td><%if(cust_map.get("BIRTHDAY")!=null)Format.formatDateCn(new Integer(cust_map.get("BIRTHDAY").toString()));%></td>
							<td align="right"></td>
							<td></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.age",clientLocale)%> :</td><!--年龄-->
							<td><%=Utility.trimNull(cust_map.get("AGE"))%></td>
							<td align="right"><%=LocalUtilis.language("class.sex",clientLocale)%> :</td><!--性别-->
							<td><%=Utility.trimNull(cust_map.get("SEX_NAME"))%></td>
						</tr>
						<%}%>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
							<td><%=Utility.trimNull(cust_map.get("MOBILE"))%>
							</td>
							<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--手机-->
							<td><%=Utility.trimNull(cust_map.get("BP"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :</td><!--传真-->
							<td><%=Utility.trimNull(cust_map.get("FAX"))%></td>
							<td align="right">Email:</td>
							<td><%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
							<td><%=Utility.trimNull(cust_map.get("O_TEL"))%></td>
							<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--家庭电话-->
							<td><%=Utility.trimNull(cust_map.get("H_TEL"))%></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.current_money",clientLocale)%> :</td><!--存量金额-->
							<td><%if(cust_map.get("BEN_AMOUNT")!=null)Utility.trimNull(Format.formatMoney(new BigDecimal(cust_map.get("BEN_AMOUNT").toString())));%></td>
							<td align="right"><%=LocalUtilis.language("message.lastRgDate",clientLocale)%> :</td><!--最近购买时间-->
							<td><%if(cust_map.get("LAST_RG_DATE")!=null)Format.formatDateCn(new Integer(cust_map.get("LAST_RG_DATE").toString()));%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
							<td><%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.lastProduct",clientLocale)%> :</td><!--最近一次认购产品-->
							<td><%=Utility.trimNull(cust_map.get("LAST_PRODUCT_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"></td>
							<td></td>
							<td align="right"><%=LocalUtilis.language("class.wtrFlag",clientLocale)%> :</td><!-- 委托人标志 -->
							<td><b><%=wtflag_checked%></b></td>
						</tr>					
						<%if(Utility.parseInt(Utility.trimNull(cust_map.get("CUST_TYPE")),new Integer(0)).intValue()==2)  {%>	
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</td><!--法人姓名-->
							<td><%=Utility.trimNull(cust_map.get("LEGAL_MAN"))%></td>
							<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
							<td><%=Utility.trimNull(cust_map.get("LEGAL_ADDRESS"))%></td>
						</tr>
						<%}%>							
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> :</td><!--客户级别-->
							<td><%=Utility.trimNull(cust_map.get("CUST_LEVEL_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</td><!--联系方式-->
							<td><%=Utility.trimNull(cust_map.get("TOUCH_TYPE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> :</td><!--邮寄地址-->
							<td><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></td>
							<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
							<td><%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
						</tr>		
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2:</td><!--邮寄地址-->
							<td><%=Utility.trimNull(cust_map.get("POST_ADDRESS2"))%></td>
							<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2:</td><!--邮政编码-->
							<td><%=Utility.trimNull(cust_map.get("POST_CODE2"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.summary",clientLocale)%> :</td><!--备注信息-->
							<td colspan="3"><%=Utility.trimNull(cust_map.get("SUMMARY"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.printDeployBill",clientLocale)%>:</td>
							<td><b><%=print_deploy_bill_checked%></b></td>
							<td align="right"><%=LocalUtilis.language("class.customerIsLink",clientLocale)%>：</td>
							<td><b><%if(cust_map.get("IS_LINK")!=null){if(new Integer(cust_map.get("IS_LINK").toString()).intValue()==1) out.print(" Y ");else out.print(" N ");}%></b></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.printPostInfo",clientLocale)%> :</td><!--是否打印披露信息-->
							<td><b><%=print_post_info_checked%></b></td>
							<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
							<td><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(cust_map.get("SERVICE_MAN")),new Integer(0))))%></td>
						</tr>						
					</TBODY>
				</TABLE>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				</TD>
			</TR>
			<!--end 基本信息-->
			<!--start 认购信息-->
			<TR id=r1 <%if (subflag == 1) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD class="table-popup">
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr bgColor=#ffffff>
						<td align="left" colspan="6" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.reservationInfo",clientLocale)%> </font></td><!--预约信息-->
					</tr>
					<tr class="trh">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td align="center" height="15"><%=LocalUtilis.language("class.preCode2",clientLocale)%> </td><!--预约编号-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> </td><!--预约金额-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!--已认购金额-->
						<td align="center" height="15"><%=LocalUtilis.language("class.preDate",clientLocale)%> </td><!--预约日期-->
						<td align="center" height="15"><%=LocalUtilis.language("class.preStatus",clientLocale)%> </td><!--预约状态-->
					</tr>
					<%
					PreContractLocal precontract_local = EJBFactory.getPreContract();//预约Bean
					PreContractVO precontract_vo = new PreContractVO();
					precontract_vo.setProduct_id(product_id);
					precontract_vo.setBook_code(new Integer(1));
					precontract_vo.setCust_id(cust_id);
					List pre_list = precontract_local.queryPrecontractByCustID(precontract_vo);
					Map pre_map = new HashMap();
					for(int i=0; i<pre_list.size(); i++)
					{
						pre_map = (Map)pre_list.get(i);
					%>
					<tr bgColor=#ffffff>
						<td align="left" height="25"><%=Argument.getProductName(Utility.parseInt(Utility.trimNull(pre_map.get("PRODUCT_ID")), new Integer(0)))%></td>
						<td align="left" height="25"><%=Utility.trimNull(pre_map.get("PRE_CODE"))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(pre_map.get("PRE_MONEY")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(pre_map.get("RG_MONEY")))))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(pre_map.get("PRE_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(pre_map.get("PRE_STATUS_NAME"))%></td>
					</tr>
					<%}%>
				</table>
				
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr bgColor=#ffffff>
						<td align="left" colspan="7" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.subscriptionInfo",clientLocale)%> </font></td><!--认购信息-->
					</tr>
					<tr class="trh"">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td align="center" height="15"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center" height="15"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!--签署日期-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
						<td align="center" height="15">预期收益率</td>
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.endDate4",clientLocale)%> </td><!--到期日期-->
						<td align="center" height="15"><%=LocalUtilis.language("class.htStatus",clientLocale)%> </td><!--合同状态-->
						<td align="center" height="15"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> </td><!--审核标志-->
					</tr>
					<%
					String sub_product_name = "";
					BigDecimal exp_rate1 = new  BigDecimal(0);
					BigDecimal exp_rate2 = new  BigDecimal(0);
					ContractLocal contract_local = EJBFactory.getContract();//合同Bean
					ContractVO contract_vo = new ContractVO();
					contract_vo.setProduct_id(product_id);
					contract_vo.setBook_code(new Integer(1));
					contract_vo.setCust_id(cust_id);
					List contract_list = contract_local.queryContractByCustID(contract_vo);
					Map contract_map = new HashMap();
					for(int i=0; i<contract_list.size(); i++){
						contract_map = (Map)contract_list.get(i);
						sub_product_name = Utility.trimNull(contract_map.get("LIST_NAME"));
						exp_rate1 = Utility.parseDecimal(Utility.trimNull(contract_map.get("PRODUCT_EXP_RATE1")), new BigDecimal(0.00),2,"100");
						exp_rate2 = Utility.parseDecimal(Utility.trimNull(contract_map.get("PRODUCT_EXP_RATE2")), new BigDecimal(0.00),2,"100");

						if(sub_product_name.length()>0){
							exp_rate1 = Utility.parseDecimal(Utility.trimNull(contract_map.get("SUB_EXP_RATE1")), new BigDecimal(0.00),2,"100");
							exp_rate2 = Utility.parseDecimal(Utility.trimNull(contract_map.get("SUB_EXP_RATE2")), new BigDecimal(0.00),2,"100");
						}
					%>
					<tr bgColor=#ffffff>
					    <td align="left" height="25"><%=Utility.trimNull(contract_map.get("PRODUCT_NAME"))%></td>
						<td align="left" height="25"><%=Utility.trimNull(contract_map.get("CONTRACT_BH"))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0)))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(contract_map.get("RG_MONEY")))))%></td>
						<td align="center" height="25"><%=exp_rate1%>%-<%=exp_rate2%>%</td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("END_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(contract_map.get("HT_STATUS_NAME"))%></td>
						<!--未审核--><!--已审核-->
                        <td align="center" height="25">
                        	<%if(Utility.parseInt(Utility.trimNull(contract_map.get("CHECK_FLAG")), 0)==1){%>
                        	<%=LocalUtilis.language("message.unaudited",clientLocale)%><%}else{%>
                        	<%=LocalUtilis.language("message.checked",clientLocale)%> <%}%>
                        </td>
					</tr>
					<%}
					%>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				</TD>
			</TR>
			<!--end 认购信息-->
			<!-- start 受益信息-->
			<TR id=r2 <%if (subflag == 2) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor="#ffffff">
				<TD>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td align="center" height="15"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.exchangeAmount",clientLocale)%> </td><!--转让金额-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.backAmount",clientLocale)%> </td><!--兑付金额-->
						<td align="center" height="15" sort="num">
							<%if(languageType.equals("zh_CN")&&user_id.intValue()==19){%>
								受益本金额
							<%}
							else{%>
							<%=LocalUtilis.language("class.benAmount",clientLocale)%>
							<%}%> 
						</td><!--受益金额-->
						<td align="center" height="15"><%=LocalUtilis.language("class.benDate",clientLocale)%> </td><!--受益日期-->
						<td align="center" height="15"><%=LocalUtilis.language("class.benPeriod2",clientLocale)%> </td><!--受益期限-->
						<td align="center" height="15"><%=LocalUtilis.language("class.benState",clientLocale)%> </td><!--受益权状态-->
					</tr>
					<%
					int iCount = 0;
					int iCurrent = 0;
					BigDecimal amount = new BigDecimal(0.000);
					BigDecimal to_amount = new BigDecimal(0.000);//认购金额
					BigDecimal exchange_amount = new BigDecimal(0.000);//转让金额
					BigDecimal back_amount = new BigDecimal(0.000);//兑付金额
					BigDecimal ben_amount = new BigDecimal(0.000);//受益金额
					Integer serial_no = new Integer(0);
					BenifitorLocal benifitor=EJBFactory.getBenifitor();
					BenifitorVO benifitor_vo = new BenifitorVO();
					benifitor_vo.setBook_code(new Integer(1));
					benifitor_vo.setCust_id(cust_id);
					benifitor_vo.setProduct_id(product_id);

					List ben_list = benifitor.QueryBenifitor(benifitor_vo);
					Map ben_map = new HashMap();
					String period_unit = "";//租赁类型1短期租赁2长期租赁
					for(int i=0; i<ben_list.size(); i++)
					{
						ben_map = (Map)ben_list.get(i);
						if(ben_map.get("TO_AMOUNT") != null)
      						to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT"))));	
      					if(ben_map.get("EXCHANGE_AMOUNT") != null)
     						 exchange_amount = exchange_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("EXCHANGE_AMOUNT"))));	
     					if(ben_map.get("BACK_AMOUNT") != null)
     						 back_amount = back_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BACK_AMOUNT"))));	
						if(ben_map.get("BEN_AMOUNT") != null)
  	 						 ben_amount = ben_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT"))));	
  	 					period_unit=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(ben_map.get("PERIOD_UNIT")), new Integer(0)));
    					//根据客户购买产品和合同编号获得客户合同信息
    					contract_vo.setProduct_id(Utility.parseInt(Utility.trimNull(ben_map.get("PRODUCT_ID")), new Integer(0)));
						contract_vo.setContract_bh(Utility.trimNull(ben_map.get("CONTRACT_BH")));
						contract_vo.setCust_id(cust_id);
						contract_vo.setBook_code(new Integer(1));
						contract_vo.setInput_man(input_operatorCode);
    					List contract_ben_list = contract_local.queryContract(contract_vo);
    					Map contract_ben_map = new HashMap();
    					if(contract_ben_list != null && contract_ben_list.size() > 0)
    					{
    						contract_ben_map = (Map)contract_ben_list.get(0);
    						
    					}
    				%>
    				<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" height="25"><%=Utility.trimNull(ben_map.get("PRODUCT_NAME"))%></td>
						<td align="left" height="25"><%=Utility.trimNull(ben_map.get("CONTRACT_BH"))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("EXCHANGE_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BACK_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT")))))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(ben_map.get("BEN_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(ben_map.get("VALID_PERIOD"))%><%=period_unit%></td>
						<td align="center" height="25"><%=Utility.trimNull(ben_map.get("BEN_STATUS_NAME"))%></td>
					</tr>
					<%
					iCount ++;
					iCurrent ++;
					}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%><%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" height="25"></td>
						<td align="right" height="25"><%=Format.formatMoney(to_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(exchange_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(back_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(ben_amount)%></td>
						<td align="center" height="25"></td>
						<td align="right" height="25"></td>
						<td align="center" height="25"></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				</TD>
			</TR>
			<!-- end 受益信息-->
			<!-- start 收益信息-->
			<TR id=r3 <%if (subflag == 3) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing= 1 cellPadding=2 width="100%"
					class="tablelinecolor" border=0>
					<tr class="trh">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td align="center" height="15"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center" height="15"><%=LocalUtilis.language("class.bond1",clientLocale)%> </td><!--发行期利息-->
						<td align="center" height="15"><%=LocalUtilis.language("class.gain1",clientLocale)%> </td><!--中间收益-->
						<td align="center" height="15"><%=LocalUtilis.language("class.gain2",clientLocale)%> </td><!--到期收益-->
						<td align="center" height="15"><%=LocalUtilis.language("class.bond2",clientLocale)%> </td><!--兑付期利息-->
						<td align="center" height="15"><%=LocalUtilis.language("class.toMoney2",clientLocale)%> </td><!--到期本金-->
						<td align="center" height="15"><%=LocalUtilis.language("class.bondTax",clientLocale)%> </td><!--扣税-->
						<td align="center" height="15"><%=LocalUtilis.language("class.totalMoney2",clientLocale)%> </td><!--合计金额-->

					</tr>
					<%
					to_amount = new BigDecimal("0.000");
					java.math.BigDecimal Bond1_amount=new BigDecimal("0.000");//发行期利息
					java.math.BigDecimal Gain1_amount=new BigDecimal("0.000");//中间收益
					java.math.BigDecimal Gain2_amount=new BigDecimal("0.000");//到期收益
					java.math.BigDecimal Bond2_amount=new BigDecimal("0.000");//兑付期利息
					java.math.BigDecimal To_money2_amount=new BigDecimal("0.000");//到期本金
					java.math.BigDecimal Bond_tax_amount=new BigDecimal("0.000");//扣税
					java.math.BigDecimal Total_money_amount=new BigDecimal("0.000");//合计金额
					
					DeployLocal deploy_local = EJBFactory.getDeploy();
					DeployVO deploy_vo = new DeployVO();
					deploy_vo.setInput_man(input_operatorCode);
    				deploy_vo.setCust_id(cust_id);
					deploy_vo.setProduct_id(product_id);
					
    				List deploy_list = deploy_local.queryDeployByCustId(deploy_vo);
					Map deploy_map = new HashMap();
					for(int i=0; i<deploy_list.size(); i++)
					{
						deploy_map = (Map)deploy_list.get(i);
						if(deploy_map.get("BOND1") != null)
							Bond1_amount=Bond1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND1"))));
						if(deploy_map.get("GAIN1") !=null)
							Gain1_amount=Gain1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN1"))));
						if(deploy_map.get("GAIN2") !=null)
							Gain1_amount=Gain1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN2"))));
						if(deploy_map.get("BOND2") != null)
							Bond1_amount=Bond1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND2"))));
						if(deploy_map.get("TO_MONEY2") != null)
							To_money2_amount=To_money2_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("TO_MONEY2"))));
						if(deploy_map.get("BOND_TAX") != null)
							Bond_tax_amount=Bond_tax_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND_TAX"))));
						if(deploy_map.get("TOTAL_MONEY") != null)
							Total_money_amount=Total_money_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("TOTAL_MONEY"))));
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" height="18"><%=Utility.trimNull(Argument.getProductName(Utility.parseInt(Utility.trimNull(deploy_map.get("PRODUCT_ID")), new Integer(0))))%></td>
						<td  align="left" height="25"><%=Utility.trimNull(deploy_map.get("CONTRACT_BH"))%></td>

						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("BOND1") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND1"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("GAIN1") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN1"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("GAIN2") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN2"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("BOND2") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND2"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("TO_MONEY2") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("TO_MONEY2"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("BOND_TAX") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND_TAX"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("TOTAL_MONEY") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("TOTAL_MONEY"))).doubleValue(), 2)%></td>
					</tr>
					<%
					iCurrent++;
					iCount++;
					}
					%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%><%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" height="25"></td>
						<td align="right" height="25"><%=Format.formatMoney(Bond1_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Gain1_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Gain2_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Bond2_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(To_money2_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Bond_tax_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Total_money_amount)%></td>
					</tr>
				</TABLE>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				</TD>
			</TR>
			<!-- end 收益信息-->
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>

<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>



