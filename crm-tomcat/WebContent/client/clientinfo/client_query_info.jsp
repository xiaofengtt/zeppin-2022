<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
CustomerLocal cust_local = EJBFactory.getCustomer();//客户Bean
CustomerVO cust_vo = new CustomerVO();

String cust_no = request.getParameter("cust_no");
int iiCount = 0;
int subflag = Utility.parseInt(request.getParameter("subflag"), 0);
int showflag = Utility.parseInt(request.getParameter("showflag"), 0);
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), null);
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);

//获得客户详细信息、赋值、转换
cust_vo.setCust_id(cust_id);
//cust_vo.setInput_man(new Integer(888));
cust_vo.setInput_man(input_operatorCode);
List cust_list = cust_local.listProcAll(cust_vo);
List cust_cardInfo_list = cust_local.listCustCardInfo(cust_id);
java.io.InputStream in = null;
Map cust_map = new HashMap();
Map cust_cardInfo_map = new HashMap();
String custno = "";
String wtflag_checked = "";
String print_deploy_bill_checked = "";
String print_post_info_checked = "";
if (cust_cardInfo_list != null && cust_cardInfo_list.size()>0){
	cust_cardInfo_map = (Map)cust_cardInfo_list.get(0);
}
if(cust_list != null && cust_list.size() > 0)
{
	cust_map = (Map) cust_list.get(0);
	//将图片二进制保存
	//session.setAttribute("bi", cust_map.get("ImageIdentification"));
	//net.sourceforge.jtds.jdbc.BlobImpl bl = (net.sourceforge.jtds.jdbc.BlobImpl)cust_map.get("ImageIdentification");
	//if(bl != null)
	//	in = bl.getBinaryStream();
	custno = cust_map.get("CUST_NO").toString();
	if (new Integer(cust_map.get("WT_FLAG").toString()).intValue() == 1)
		wtflag_checked = " Y ";
	else
		wtflag_checked = " N ";
	if (cust_map.get("PRINT_DEPLOY_BILL") != null) {
		if (new Integer(cust_map.get("PRINT_DEPLOY_BILL").toString()).intValue()
			== 1)
			print_deploy_bill_checked = " Y ";
		else
			print_deploy_bill_checked = " N ";
	}
	if (cust_map.get("PRINT_POST_INFO") != null) {
		if (new Integer(cust_map.get("PRINT_POST_INFO").toString()).intValue()
			== 1)
			print_post_info_checked = " Y ";
		else
			print_post_info_checked = " N ";
	}
	
}
//查询最近一次认购信息
ContractVO contract_vo_m = new ContractVO();
contract_vo_m.setProduct_id(product_id);
contract_vo_m.setBook_code(new Integer(1));
contract_vo_m.setCust_id(cust_id);
Map<String,String> mapRG = Argument.getRGInfo(contract_vo_m);
String productName = Utility.trimNull(mapRG.get("PRODUCT_NAME"));
String contractSubBH = Utility.trimNull(mapRG.get("CONTRACT_SUB_BH"));
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
window.onload = function() {
		show(<%=showflag%>);
	};

function opencity(product_id)
{
	showModalDialog('product_city2.jsp?check_flag=2&product_id=' + product_id, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}
function opendate(product_id,trade_type,trade_type_name,readonly)
{
	showModalDialog('product_date2.jsp?product_id=' + product_id+'&trade_type=' + trade_type+'&trade_type_name='+trade_type_name+'&readonly='+readonly, '', 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}
function show(parm)
{
	//<-%=input_operator.hasFunc(menu_id, 103)?0:1%>
   for(i=0;i<12;i++)
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
	<%String extension = Utility.trimNull(session.getAttribute("extension"));
	if(Argument.getSyscontrolValue("DT_CALL")!=0 && !"".equals(extension)){%>	
	document.parentWindow.parent.document.getElementById("target_custid").value = target_custid; 
	document.parentWindow.parent.document.getElementById("callTalkType").value = 1;
	document.parentWindow.parent.document.getElementById("callcenterLink").onclick();
	<%}%>
}
function showProduct(product_id)
{	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}
function showPreProductInfo(preproduct_id){
	var url = "<%=request.getContextPath()%>/wiki/product_info_view.jsp?preproduct_id="+preproduct_id;
	window.open(url);
}

//采集、查看档案
function openLsc(url){
	window.open(url,"","width="+ window.screen.width +",height="+ window.screen.height  +",scroll=no,status=no,location=no");
}

function showDeployDetail(cust_id, product_id, contract_sub_bh) {
	var url = '/client/clientinfo/deploy_detail.jsp?cust_id='+cust_id+'&product_id='+product_id	+'&contract_sub_bh='+contract_sub_bh;
	showModalDialog(url, '','dialogWidth:700px;dialogHeight:500px;status:0;help:0');
}

//查看客户维护记录
function showInfo(cust_id,serial_no){
	showModalDialog("/client/service/service_info.jsp?cust_id="+cust_id+"&serial_no="+serial_no,'','dialogWidth:700px;dialogHeight:500px;status:0;help:0');
}

/**客户信息编辑**/
function showInfo2(cust_id,cust_name) {
	var url = '/client/clientinfo/customers_connection_info_add.jsp';
	if (showModalDialog(url+'?cust_id='+cust_id+'&cust_name='+cust_name, 
			'', 'dialogWidth:550px;dialogHeight:400px;status:0;help:0')) {
			sl_update_ok();
			location.reload();
	}
	//showModalDialog("/client/clientinfo/customers_connection_info_add.jsp?cust_id="+cust_id+"&cust_name="+cust_name,'','dialogWidth:700px;dialogHeight:500px;status:0;help:0');
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
				<td class="client-title">
				<table id="titleNameTable" border="0" width="100%" cellspacing="1" cellpadding=0>
					<tr>
						
						<td align="left">
							<b><font size="+1"><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></font>
							<font face="新宋体">(<%=Utility.trimNull(custno)%>)</font></b>
						</td>
					</tr>
				</table>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0"
					class="edline">
					<TBODY>
						<TR class="tr-tabs">
							<TD vAlign=top width=100>&nbsp;</TD>
							<TD vAlign=top width=100>&nbsp;</TD>
							<TD vAlign=top>&nbsp;</TD>
							<%//if(input_operator.hasFunc(menu_id, 103)){%>
							<!--基本信息-->
                            <TD id=d0 style="background-repeat: no-repeat" onclick=show(0) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 0) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
							<%//}%>
							<!--认购信息-->
                            <TD id=d1 style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 1) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.subscriptionInfo",clientLocale)%> </TD>
							<!--受益信息-->
                            <TD id=d2 style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 2) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.benInfo",clientLocale)%> </TD>
							<!--收益信息-->
                            <TD id=d3 style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 3) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.incomeInfo",clientLocale)%> </TD>
							<!--修改明细-->
                            <TD id=d4 style="background-repeat: no-repeat" onclick=show(4) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 3) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.mofifyDteails",clientLocale)%> </TD>
							<!--定义要素-->
                            <TD id=d5 style="background-repeat: no-repeat" onclick=show(5) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 5) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.defineEle",clientLocale)%> </TD>
							<!--份额变更-->
                            <TD id=d6 style="background-repeat: no-repeat" onclick=show(6) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 6) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.shareChange",clientLocale)%> </TD>
							<!--通话记录-->
                            <TD id=d7 style="background-repeat: no-repeat" onclick=show(7) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 8) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.callRecords",clientLocale)%> </TD>
							<!--非信托产品-->
                            <TD id=d8 style="background-repeat: no-repeat" onclick=show(8) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 9) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("menu.nonProduct",clientLocale)%></TD>
							<!--客户详细信息-->
                            <TD id=d9 style="background-repeat: no-repeat" onclick=show(9) vAlign=top width=70 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 10) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.detailInfo",clientLocale)%></TD>
							<!--财务与偏好信息-->
                            <TD id=d10 style="background-repeat: no-repeat" onclick=show(10) vAlign=top width=100 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 11) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.finPrefInfo",clientLocale)%></TD>
							<!--客户沟通记录-->
                            <TD id=d11 style="background-repeat: no-repeat" onclick=show(11) vAlign=top width=100 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 12) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;客户沟通记录</TD>
						</TR>
					</TBODY>
				</TABLE>
				</TD>
			</TR>
			<!--start 基本信息
				subflag == 0&&input_operator.hasFunc(menu_id, 103)
			-->
			<TR id=r0 <%if (subflag == 0) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				
<style>
.table-popup tr td{padding:0 5px;}
</style>				
				<TABLE cellSpacing="0" cellPadding="0" width="100%" class="table-popup">
					<colgroup>
					    <col span="2" style="background-color:red">
					    <col style="background-color:yellow">
					  </colgroup>
					<TBODY>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
							<td colspan="3"><%=Utility.trimNull(cust_map.get("CUST_SOURCE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP卡编号-->
							<td><%=Utility.trimNull(cust_map.get("VIP_CARD_ID"))%>［<%if(cust_map.get("VIP_DATE")!=null){out.print(Format.formatDateCn(new Integer(cust_map.get("VIP_DATE").toString())));}%>］</td>
							<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--合格投资人编号-->
							<td><%=Utility.trimNull(cust_map.get("HGTZR_BH"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--证件类型-->
							<td><%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
							<td><%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right">证件地址 :</td><!--证件地址-->
							<td><%=Utility.trimNull(cust_map.get("CARD_ADDRESS"))%></td>
							<td align="right">证件有效期 :</td><!--证件有效期-->
							<td><%=Utility.trimNull(cust_map.get("CARD_VALID_DATE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
								<td align="right"><%=LocalUtilis.language("class.regional",clientLocale)%> ：</td><!--所属地区-->
								<td><%=Utility.trimNull(cust_map.get("GOV_PROV_REGIONAL_NAME"))%>-<%=Utility.trimNull(cust_map.get("GOV_REGIONAL_NAME"))%></td>
								<td align="right"><%=LocalUtilis.language("class.referee",clientLocale)%> ：</td><!-- 推荐人 -->
								<td><%=Utility.trimNull(cust_map.get("RECOMMENDED")) %></td>
						</tr>
						<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==1)  {%>	
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--出生日期-->
							<td><%if(cust_map.get("BIRTHDAY")!=null){out.print(Format.formatDateCn(new Integer(cust_map.get("BIRTHDAY").toString())));}%></td>
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
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("MOBILE"))%></a></td>
							<td align="right"><button style="width:90px;" class="xpbutton3" type="button" onclick="javascript:showInfo2('<%=Utility.trimNull(cust_map.get("CUST_ID"))%>','<%=Utility.trimNull(cust_map.get("CUST_NAME"))%>');">
<%-- 								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="编辑联系方式 " /> --%>
								<!--编辑联系方式-->
								编辑联系方式
							</button></td>
							<td></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--手机-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("BP"))%></a></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :</td><!--传真-->
							<td><%=Utility.trimNull(cust_map.get("FAX"))%></td>
							<td align="right">Email:</td>
							<td><%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("O_TEL"))%></a></td>
							<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--家庭电话-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("H_TEL"))%></a></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.current_money",clientLocale)%> :</td><!--存量金额-->
							<td><%if(cust_map.get("BEN_AMOUNT")!=null)Utility.trimNull(Format.formatMoney(new BigDecimal(cust_map.get("BEN_AMOUNT").toString())));%></td>
							<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
							<td><%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("message.lastRgDate",clientLocale)%> :</td><!--最近购买时间-->
							<td><%if(cust_map.get("LAST_RG_DATE")!=null)out.print(Format.formatDateCn(new Integer(cust_map.get("LAST_RG_DATE").toString())));%></td>
							<td align="right"><%=LocalUtilis.language("class.lastProduct",clientLocale)%> :</td><!--最近一次认购产品-->
							<td><%=productName%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
							<td><%=contractSubBH%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.apportionAmount",clientLocale)%> :</td><!--累计分摊活动费用-->
							<td><%=Utility.parseDecimal(Utility.trimNull(cust_map.get("ACTIVITY_FEE")),new BigDecimal(0.00))%></td>
							<td align="right"><%=LocalUtilis.language("class.wtrFlag",clientLocale)%> :</td><!--委托人标志-->
							<td><b><%=wtflag_checked%></b></td>
						</tr>
						<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==2)  {%>	
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
							<td align="right"><%=LocalUtilis.language("class.printDeployBill",clientLocale)%> :</td><!--是否打印客户对帐单-->
							<td><b><%=print_deploy_bill_checked%></b></td>
							<td align="right"><%=LocalUtilis.language("class.customerIsLink",clientLocale)%> :</td><!--关联方-->
							<td><b><%if(cust_map.get("IS_LINK")!=null){if(new Integer(cust_map.get("IS_LINK").toString()).intValue()==1) out.print(" Y ");else out.print(" N ");}%></b></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.printPostInfo",clientLocale)%> :</td><!--是否打印披露信息-->
							<td><b><%=print_post_info_checked%></b></td>
							<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!-- 客户经理 -->
							<td><%=Utility.trimNull(cust_map.get("SERVICE_MAN_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right">证件核查结果 :</td>
							<td><%=Utility.trimNull(cust_cardInfo_map.get("VERIFY_STATE"))%></td>
							<td align="right">证件核查日期 :</td>
							<td><%=Utility.trimNull(cust_cardInfo_map.get("VERIFY_DATE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right">证件核查结果描述 :</td>
							<td colspan="3"><%=Utility.trimNull(cust_cardInfo_map.get("VERIFY_SUMMARY"))%></td>
						</tr>
					</TBODY>
				</TABLE>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				<%if (cust_id!=null&&!"".equals(cust_id)&&cust_id.intValue()!=0) {%>
					<table border="0" cellpadding="2" cellspacing="1" class="tablelinecolor" width="100%">
					<tr bgColor=#ffffff>
						<td align="left" colspan="1" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.docImg",clientLocale)%> </font><!--证件图片-->
						</td>
					</tr>
					<tr align="center">
						<td>
							<iframe src ="show_client_image.jsp?cust_id=<%=cust_id%>" name="sonIframe" 	frameborder="no" border="0" height="300" width="100%"></iframe>
						</td>	
					</tr>
					</table>
					<table border="0" width="100%">
						<tr valign="top">
							<td></td>
							<td align="right"></td>
						</tr>
					</table>
				<%}%>
				</TD>
			</TR>
			<!--end 基本信息-->
			<!--start 预约信息
				subflag == 1||!input_operator.hasFunc(menu_id, 103)
			-->
			<TR id=r1 <%if (subflag == 1) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
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
						Integer query_product_id = Utility.parseInt(Utility.trimNull(pre_map.get("PRODUCT_ID")),new Integer(0));
						
					%>
					<tr bgColor=#ffffff>
						<td align="left" height="25">
							<a href="#" onclick="javascript:showProduct('<%=query_product_id%>');"><%=Utility.trimNull(pre_map.get("PRODUCT_NAME"))%></a>
						</td>
						<td align="left" height="25"><%=Utility.trimNull(pre_map.get("PRE_CODE"))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(pre_map.get("PRE_MONEY")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(pre_map.get("RG_MONEY")))))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(pre_map.get("PRE_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(pre_map.get("PRE_STATUS_NAME"))%></td>
					</tr>
					<%}%>
				</table>
				<!-- 认购信息 -->
				<table border="0" cellpadding="2" cellspacing="1" class="tablelinecolor" width="100%">
					
					<tr bgColor=#ffffff>
						<td align="left" colspan="11" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.subscriptionInfo",clientLocale)%> </font></td><!--认购信息-->
					</tr>
					<tr class="trh"">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td align="center" height="15"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center" height="15"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!--签署日期-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!--已认购金额-->
						<td align="center" height="15">预期收益率</td>
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.endDate4",clientLocale)%> </td><!--到期日期-->
						<td align="center" height="15">合同联系人</td>
						<td align="center" height="15">合同联系人电话</td>
						<td align="center" height="15"><%=LocalUtilis.language("class.htStatus",clientLocale)%> </td><!--合同状态-->
						<td align="center" height="15"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> </td><!--审核标志-->
					<%if(user_id.intValue() == 2){%>
						<td align="center" height="15">档案</td><!--档案-->
					<%}%>
					</tr>
					<%out.flush();
					ContractLocal contract_local = EJBFactory.getContract();
					ContractVO contract_vo = new ContractVO();
					contract_vo.setProduct_id(product_id);
					contract_vo.setBook_code(new Integer(1));
					contract_vo.setCust_id(cust_id);
					List contract_list = contract_local.queryContractByCustID(contract_vo);
					for(int i=0; i<contract_list.size(); i++){out.flush();
						Map contract_map = (Map)contract_list.get(i);
						Integer query_product_id = Utility.parseInt(Utility.trimNull(contract_map.get("PRODUCT_ID")),new Integer(0));
						Integer pre_product_id = Argument.getPreProduct_id(query_product_id);

						String sub_product_name = Utility.trimNull(contract_map.get("LIST_NAME"));
						BigDecimal exp_rate1 = Utility.parseDecimal(Utility.trimNull(contract_map.get("PRODUCT_EXP_RATE1")), new BigDecimal(0.00)).multiply(new BigDecimal("100"));
						BigDecimal exp_rate2 = Utility.parseDecimal(Utility.trimNull(contract_map.get("PRODUCT_EXP_RATE2")), new BigDecimal(0.00)).multiply(new BigDecimal("100"));

						if(sub_product_name.length()>0){
							exp_rate1 = Utility.parseDecimal(Utility.trimNull(contract_map.get("SUB_EXP_RATE1")), new BigDecimal(0.00),2,"100");
							exp_rate2 = Utility.parseDecimal(Utility.trimNull(contract_map.get("SUB_EXP_RATE2")), new BigDecimal(0.00),2,"100");
						}
					%>
					<tr bgColor=#ffffff>
					    <td align="left" height="25">				
							<%if(pre_product_id != null && pre_product_id.intValue() != 0 ){ %>
								<a href="#" onclick="javascript:showPreProductInfo('<%=pre_product_id%>');"> <%=Utility.trimNull(contract_map.get("PRODUCT_NAME"))%><%if(sub_product_name.length()>0){ %>[<%=sub_product_name%>]<%}%> </a>
							<%}else{%>
								<a href="#" onclick="javascript:showProduct('<%=query_product_id%>');"> <%=Utility.trimNull(contract_map.get("PRODUCT_NAME"))%><%if(sub_product_name.length()>0){ %>[<%=sub_product_name%>]<%}%> </a>
							<%} %>	
						</td>	
						<td align="left" height="25"><%=Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0)))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(contract_map.get("RG_MONEY")))))%></td>
						<td align="center" height="25"><%=Utility.trimZero(exp_rate1)%>%-<%=Utility.trimZero(exp_rate2)%>%</td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("END_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(contract_map.get("HT_CUST_NAME"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(contract_map.get("HT_CUST_TEL"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(contract_map.get("HT_STATUS_NAME"))%></td>
						<!--未审核--><!--已审核-->
                        <td align="center" height="25"><%if(Utility.parseInt(Utility.trimNull(contract_map.get("CHECK_FLAG")), 0)==1){%><%=LocalUtilis.language("message.unaudited",clientLocale)%><%}else{%><%=LocalUtilis.language("message.checked",clientLocale)%> <%}%></td>
					<%//20120830 与档案系统的连接：采集档案、查看档案 start
					if(user_id.intValue() == 2){
						String lscAppUrl = Argument.getDictContentIntrust("700201");
						String uploadUrl = "";
						String lscUrl = "";
						if(lscAppUrl == null || "".equals(lscAppUrl.trim())){
							lscAppUrl = "http://192.168.1.199:6600/fzzg";
						}
						IntrustProductLocal intrustproduct_local = EJBFactory.getIntrustProduct();
						intrustproduct_local.setProduct_id(query_product_id);
						intrustproduct_local.load();
						String lscUSERID = input_operator.getOp_code().intValue() + "";
						String lscLOGINID = input_operator.getLogin_user();
						String lscPZXH = query_product_id.intValue() + "";
						String lscXM_DM = Utility.trimNull(intrustproduct_local.getProduct_code());
						String lscXM_MC = Utility.trimNull(intrustproduct_local.getProduct_name());
						String lscXM_BM = intrustproduct_local.getDepart_id().intValue() + "";
						String lscXM_TYPE = intrustproduct_local.getIntrust_flag1().intValue() +"";
						uploadUrl = lscAppUrl + "/d/tab?tab=zlzl&mid=2001&LOGINTYPE=2&USERID=" + lscLOGINID + "&LOGINID="+lscLOGINID + "&PZXH=&XM_DM=" +lscXM_DM;
						uploadUrl = uploadUrl + "&XM_MC="+ lscXM_MC + "&XM_BM=" + lscXM_BM + "&XM_TYPE="+lscXM_TYPE +"&DAFLFLAG=2"+"&HT_DM="+Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"))+"&HT_MC="+Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"));
						//示例uploadUrl = "http://192.168.1.199:6600/fzzg/d/tab?tab=zlzl&mid=2001&LOGINTYPE=2&USERID=1111&LOGINID=1111&PZXH=pzxh111&XM_DM=2300&XM_MC=测试项目名称111&XM_BM=20&XM_TYPE=&HT_DM=123456&HT_MC=合同1号";
						
						lscUrl = lscAppUrl + "/d/b?m=doBjxtSearch&LOGINTYPE=2&USERID="  + lscLOGINID + "&LOGINID="+lscLOGINID +"&PZXH=&XM_DM=" + lscXM_DM +"&DAFLFLAG=2" + "&HT_DM=" + Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"));
						//示例lscUrl = "http://192.168.1.199:6600/fzzg/d/b?m=doBjxtSearch&LOGINTYPE=2&USERID=1111&LOGINID=1111&PZXH=&HT_DM=1234&XM_DM=2300&DAFLFLAG=2";
					%>
                        <td align="center" height="25">
							<a href="#" onclick="javascript:openLsc('<%=uploadUrl%>');">采集</a>&nbsp;
							<a href="#" onclick="javascript:openLsc('<%=lscUrl%>');">查看</a>
						</td>
					<%}%>
					</tr>
					<%}
					%>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%if(user_id.intValue() == 2){%>注：现金聚利类产品的预期收益率，为最近七日年化收益率<%} %></td>
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
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!-- 认购金额 -->
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
						<td align="center" height="15"><%=LocalUtilis.language("class.bankSubName2",clientLocale)%> </td><!--付款银行名称-->
						<td align="center" height="15"><%=LocalUtilis.language("class.bankAcct2",clientLocale)%> </td><!--付款银行账号-->
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
						Integer query_product_id = Utility.parseInt(contract_vo.getProduct_id(),new Integer(0));
						Integer pre_product_id = Argument.getPreProduct_id(query_product_id);
    				%>
    				<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" height="25">
							<%if(pre_product_id != null && pre_product_id.intValue() != 0 ){ %>
								<a href="#" onclick="javascript:showPreProductInfo('<%=pre_product_id%>');"><%=Utility.trimNull(ben_map.get("PRODUCT_NAME"))%></a>
							<%}else{%>
								<a href="#" onclick="javascript:showProduct('<%=query_product_id%>');"><%=Utility.trimNull(ben_map.get("PRODUCT_NAME"))%></a>
							<%} %>
						</td>
						<td align="left" height="25"><%=Utility.trimNull(ben_map.get("CONTRACT_SUB_BH"))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("EXCHANGE_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BACK_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT")))))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(ben_map.get("BEN_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(ben_map.get("VALID_PERIOD"))%><%=period_unit%></td>
						<td align="center" height="25"><%=Utility.trimNull(ben_map.get("BANK_NAME"))%><%=Utility.trimNull(ben_map.get("BANK_SUB_NAME"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(ben_map.get("BANK_ACCT"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(ben_map.get("BEN_STATUS_NAME"))%></td>
					</tr>
					<%
					iCount ++;
					iCurrent ++;
					}%>
					<tr class="trbottom">
                        <!--合计--><!--项-->
						<td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" height="25"></td>
						<td align="right" height="25"><%=Format.formatMoney(to_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(exchange_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(back_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(ben_amount)%></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
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
			<TR id=r3 <%if (subflag == 3) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=4 width="100%"
					class="tablelinecolor" border=0>
					<tr class="trh">
						<td align="center" height="22"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td align="center" height="25"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center" height="22"><%=LocalUtilis.language("class.bond1",clientLocale)%> </td><!--发行期利息-->
						<td align="center" height="22"><%=LocalUtilis.language("class.gain1",clientLocale)%> </td><!--中间收益-->
						<td align="center" height="22"><%=LocalUtilis.language("class.gain2",clientLocale)%> </td><!--到期收益-->
						<td align="center" height="22"><%=LocalUtilis.language("class.bond2",clientLocale)%> </td><!--兑付期利息-->
						<td align="center" height="22"><%=LocalUtilis.language("class.toMoney2",clientLocale)%> </td><!--到期本金-->
						<td align="center" height="22">未分配收益</td><!--未分配收益-->
						<td align="center" height="22"><%=LocalUtilis.language("class.bondTax",clientLocale)%> </td><!--扣税-->
						<td align="center" height="22"><%=LocalUtilis.language("class.totalMoney2",clientLocale)%> </td><!--合计金额-->

					</tr>
					<%
					iCurrent = 0;
					iCount = 0;
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
						Integer query_product_id = Utility.parseInt(Utility.trimNull(deploy_map.get("PRODUCT_ID")),new Integer(0));
						String query_product_name = Utility.trimNull(deploy_map.get("PRODUCT_NAME")); 
						Integer pre_product_id = Argument.getPreProduct_id(query_product_id);
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
					<tr class="tr<%=(iCurrent % 2)%>" ondblclick="javascript:showDeployDetail(<%=deploy_map.get("CUST_ID")%>,<%=query_product_id%>,'<%=deploy_map.get("CONTRACT_SUB_BH")%>');">
						<td class="tdh" align="left" height="18">
							<%if(pre_product_id != null && pre_product_id.intValue() != 0 ){ %>
								<a href="#" onclick="javascript:showPreProductInfo('<%=pre_product_id%>');"><%=query_product_name%></a>
							<%}else{%>
								<a href="#" onclick="javascript:showProduct('<%=query_product_id%>');"><%=query_product_name%></a>
							<%} %>
						</td>
						<td  align="left" height="25"><%=Utility.trimNull(deploy_map.get("CONTRACT_SUB_BH"))%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("BOND1") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND1"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("GAIN1") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN1"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("GAIN2") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN2"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("BOND2") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND2"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("TO_MONEY2") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("TO_MONEY2"))).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney(deploy_map.get("AFT_PRE_GAIN") == null ? 0 : Utility.stringToDouble(Utility.trimNull(deploy_map.get("AFT_PRE_GAIN"))).doubleValue(), 2)%></td>
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
                        <td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" height="25"></td>
						<td align="right" height="25"><%=Format.formatMoney(Bond1_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Gain1_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Gain2_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Bond2_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(To_money2_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Bond_tax_amount)%></td>
						<td align="right" height="25"><%=Format.formatMoney(Total_money_amount)%></td>
						<td align="right" height="25"></td>
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
			<!-- start 修改明细-->
			<TR id=r4 <%if (subflag == 4) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center"><%=LocalUtilis.language("menu.inputMan",clientLocale)%> </td><!--修改人-->
						<td align="center"><%=LocalUtilis.language("menu.inputDate",clientLocale)%> </td><!--修改日期-->
						<td align="center"><%=LocalUtilis.language("class.modifyField",clientLocale)%> </td><!--修改字段-->
						<td align="center"><%=LocalUtilis.language("class.originalValue",clientLocale)%> </td><!--字段原值-->
						<td align="center"><%=LocalUtilis.language("class.newValue",clientLocale)%> </td><!--字段新值-->
					</tr>
					<%
					CustomerChangesLocal cust_change_local = EJBFactory.getCustomerChanges();
					CustomerChangesVO cust_chagne_vo = new CustomerChangesVO();
					cust_chagne_vo.setCust_id(cust_id);
					List cust_change_list = cust_change_local.queryCustChangesByCustId(cust_chagne_vo);
					Map cust_change_map = new HashMap();
					for(int i=0; i<cust_change_list.size(); i++){
						cust_change_map = (Map)cust_change_list.get(i);
					%>
					<tr>
						<td align="left" bgColor=#ffffff ><%=Utility.trimNull(cust_change_map.get("OP_NAME"))%></td>
						<td align="left" bgColor=#ffffff  ><%=Utility.trimNull(cust_change_map.get("INPUT_TIME"))%></td>
						<td align="left" bgColor=#ffffff  ><%=Utility.trimNull(cust_change_map.get("FIELD_CN_NAME"))%></td>
						<td align="left" bgColor=#ffffff  ><%=Utility.trimNull(cust_change_map.get("OLD_FIELD_INFO"))%></td>
						<td align="left" bgColor=#ffffff  ><%=Utility.trimNull(cust_change_map.get("NEW_FIELD_INFO"))%></td>
					</tr>
					<%}%>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				</TD>
			</TR>
			<!-- end 修改明细-->
			<!-- start 定义要素-->
			<TR id=r5 <%if (subflag == 5) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<table border="0" width="100%">
					<tr>
						<td colspan=4><b><%=LocalUtilis.language("message.setCustElements",clientLocale)%> :<br><!--客户自定义要素设置-->
						&nbsp;</b></td>
					</tr>
					<tr>
						<td align="center" height="25"></td>
						<td colspan=3>
						<table id="table3" border="0" cellspacing="1" cellpadding="2"
							class="tablelinecolor" width="100%">
							<tr class="trh">
								<td align="center" height="25"><%=LocalUtilis.language("message.eleName",clientLocale)%> </td><!--要素名称-->
								<td align="center" height="25"><%=LocalUtilis.language("class.exchangeTime",clientLocale)%> </td><!--要素值-->
							</tr>
							<%
							ProductAddLocal prodadd_local = EJBFactory.getProductAdd();
							ProductAddVO prodadd_vo = new ProductAddVO();
							prodadd_vo.setBookcode(new Integer(1));
							prodadd_vo.setTb_flag(new Integer(2));
							List prodadd_list = prodadd_local.list(prodadd_vo);
							Map prodadd_map = new HashMap();
							for(int i=0; i<prodadd_list.size(); i++)
							{
								prodadd_map = (Map)prodadd_list.get(i);
								
								ProductAddLocal prodadd_local1 = EJBFactory.getProductAdd();
								ProductAddVO prodadd_vo1 = new ProductAddVO();
								prodadd_vo1.setBookcode(input_bookCode);
								prodadd_vo1.setProduct_id(cust_id);	
								prodadd_vo1.setField_caption(Utility.trimNull(prodadd_map.get("FIELD_CAPTION")));
								prodadd_vo1.setTb_flag(new Integer(2));
								
								List prodadd_list1 = prodadd_local1.list(prodadd_vo1);
								Map prodadd_map1 = new HashMap();
								String fieldvalue="";
								if(prodadd_list1.size() > 0){
									prodadd_map1 = (Map)prodadd_list1.get(0);
									fieldvalue=Utility.trimNull(prodadd_map1.get("FIELD_VALUE"));
								}
							%>
							<tr class="tr">
								<td align="left" height="25" width="38%"><%=Utility.trimNull(prodadd_map.get("FIELD_CAPTION"))%></td>
								<td align="left" height="25"><%=fieldvalue%></td>
							</tr>
							<%}%>
						</table>
				</table>
				</td>
			</tr>
			<!-- end 定义要素-->
			<!-- start 客户交流-->
			<TR id=r999 <%if (subflag == 999) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=4 width="100%"
					class="tablelinecolor" border=0>
					<tr bgcolor="#97e8ab">
						<td align="center"><%=LocalUtilis.language("class.exchangeTime",clientLocale)%> </td><!--交流时间-->
						<td align="center"><%=LocalUtilis.language("class.author",clientLocale)%> </td><!--提交人-->
						<td align="center"><%=LocalUtilis.language("class.acTheme",clientLocale)%> </td><!--交流主题-->
						<td align="center"><%=LocalUtilis.language("class.progress",clientLocale)%> </td><!--工作进展-->
						<td align="center"><%=LocalUtilis.language("class.infoLevel",clientLocale)%> </td><!--信息等级-->
					</tr>
					<tr class="tr" style="cursor: hand"
						ondblclick="javascript:displayCustomerCommunionDetail('');">
						<td align="center"></td>
						<td align="left"></td>
						<td align="left"></td>
						<td align="left"></td>
						<td align="center"></td>
					</tr>
				</TABLE>
				</TD>
			</TR>
			<!-- end 客户交流-->

			<!-- start 份额变更-->
			<TR id=r6 <%if (subflag == 6) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor="#ffffff">
				<td><br>
				<TABLE cellSpacing=1 cellPadding=4 width="100%"
					class="tablelinecolor" border=0>
					<tr class="trh">
						<td class="tdh"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td class="tdh"><%=LocalUtilis.language("class.changeType",clientLocale)%> </td><!--变更类别-->
						<td><%=LocalUtilis.language("class.changeAmount",clientLocale)%> </td><!--份额数量-->
						<td><%=LocalUtilis.language("class.benAmount2",clientLocale)%> </td><!--净值-->
						<td><%=LocalUtilis.language("class.date2",clientLocale)%> </td><!--日期-->
					</tr>
					<%
					iCount = 0;
					iCurrent = 0;
					benifitor_vo.setBook_code(new Integer(1));
					benifitor_vo.setCust_id(cust_id);
					benifitor_vo.setProduct_id(product_id);
					List change_ben_list = benifitor.listChangeDetail(benifitor_vo);
					Map change_ben_map = new HashMap();
					String sy_type_name = "";
					for(int i=0; i<change_ben_list.size(); i++)
					{
						change_ben_map = (Map)change_ben_list.get(i);
						if(Utility.trimNull(change_ben_map.get("CHANGE_TYPE_NAME")).equals(enfo.crm.tools.LocalUtilis.language("message.redemption",clientLocale)))/*赎回*/
							sy_type_name = "-"+Utility.trimNull(change_ben_map.get("PROV_LEVEL_NAME"));
							Integer query_product_id = Utility.parseInt(Utility.trimNull(change_ben_map.get("PRODUCT_ID")),new Integer(0));
							Integer pre_product_id = Argument.getPreProduct_id(query_product_id);
					%>
					<tr bgColor=#ffffff>				
						<td align="left" height="25">
							<%if(pre_product_id != null && pre_product_id.intValue() != 0 ){ %>
								<a href="#" onclick="javascript:showPreProductInfo('<%=pre_product_id%>');"><%=Utility.trimNull(change_ben_map.get("PRODUCT_NAME"))%></a>
							<%}else{%>
								<a href="#" onclick="javascript:showProduct('<%=query_product_id%>');"><%=Utility.trimNull(change_ben_map.get("PRODUCT_NAME"))%></a>
							<%} %>	
						</td>
						<td align="left" height="25"><%=Utility.trimNull(change_ben_map.get("CHANGE_TYPE_NAME"))%><%=Utility.trimNull(sy_type_name)%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_ben_map.get("CHANGE_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(change_ben_map.get("BEN_AMOUNT"))%></td>				
						<td align="center" height="25"><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(change_ben_map.get("CHANGE_DATE")), new Integer(0))))%></td>
					</tr>
					<%iCount++;
					iCurrent++;}%>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td align="right"></td>
					</tr>
				</table>
				</td>
			</TR>
			<!-- end 份额变更-->

			<TR id=r7 <%if (subflag == 8) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor="#ffffff">
				<TD>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td><%=LocalUtilis.language("class.callDirection",clientLocale)%> </td><!--呼叫方向-->
						<td><%=LocalUtilis.language("class.callTime",clientLocale)%> </td><!--通话开始时间-->
						<td><%=LocalUtilis.language("class.callLength",clientLocale)%>(<%=LocalUtilis.language("message.seconds",clientLocale)%> )</td><!--通话时长--><!--秒-->
						<td><%=LocalUtilis.language("class.extension",clientLocale)%> </td><!--分机号码-->
						<td><%=LocalUtilis.language("class.tel",clientLocale)%> </td><!--电话号码-->
						<td><%=LocalUtilis.language("class.callContent",clientLocale)%> </td><!--通话记事-->
					</tr>
					<%
						CallCenterLocal callcenterLocal = EJBFactory.getCallCenter();
						CCVO ccvo = new CCVO();
						ccvo.setCust_id(cust_id);
						ccvo.setInput_man(input_operatorCode);
						IPageList cc_pageList = callcenterLocal.listCallRecords(ccvo,1,-1);
						List cc_list = cc_pageList.getRsList();
						Map cc_map = null;
						String directStr = enfo.crm.tools.LocalUtilis.language("message.calling",clientLocale); //主叫
		
						for(int i=0;i<cc_list.size();i++){
							cc_map = (Map)cc_list.get(i);
							if(Utility.trimNull(cc_map.get("DIRECTION")).equals("1")){
								directStr=enfo.crm.tools.LocalUtilis.language("message.called",clientLocale);//被叫
							}	

					 %>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center" height="25"><%=directStr%></td>
						<td align="center" height="25"><%=Utility.getDateFormat(cc_map.get("CallTime"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(cc_map.get("CallLength"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(cc_map.get("Extension"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(cc_map.get("PhoneNumber"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(cc_map.get("Content"))%></td>
					</tr>
					<%}%>
				</table>
				</TD>
			</TR>
			<!--start 非信托产品信息	-->
			<TR id=r8 <%if (subflag == 9) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor="#ffffff">
				<td>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
				<font color="#CC99CC" size="-1">
				<%=LocalUtilis.language("message.subscriptionInfo",clientLocale) %>
				</font>
				<tr class="trh">
					<!--合同编号-->
			        <td align="center" width="*">
			        <%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--购买合同编号-->
					<td align="center" width="*"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
					<td align="center" width="*"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!-- 签署日期 -->
					<td align="center" width="*"><%=LocalUtilis.language("class.dzDate",clientLocale)%></td><!-- 缴款日期 -->
					<td align="center" ><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--银行支行名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.htStatus",clientLocale)%> </td><!--认购合同状态-->
				</tr>
				<%

				//获得对象及结果集
				SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();
				SubscribeVO subscribevo = new SubscribeVO();
				subscribevo.setCust_id(cust_id);
				List pageList = subscribe.load(subscribevo,input_operatorCode);
				Map sub_map = null;
				for(int i=0; i<pageList.size(); i++)
				{
					sub_map = (Map)pageList.get(i);
					iCurrent++;
					iCount++;
							%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td align="left">
					<%=Utility.trimNull(sub_map.get("NONPRODUCT_NAME"))%></td>
					<td align="center"><%=Utility.trimNull(sub_map.get("SUBSCRIBE_BH"))%></td>
					<td align="center"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(sub_map.get("SUBSCRIBE_MONEY")),new BigDecimal(0),2,"1"))%></td>
					<td align="right"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(sub_map.get("SIGN_DATE")),new Integer(0)))%></td>
					<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(sub_map.get("PAY_DATE")),new Integer(0)))%></td>
					<td align="center"><%=Utility.trimNull(sub_map.get("BANK_NAME"))%></td>
					<td align="center"><%=Utility.trimNull(sub_map.get("BANK_SUB_NAME"))%></td>					
					<td align="center"><%=Utility.trimNull(sub_map.get("STATUS_NAME"))%></td>			
				</tr>
				<%
				}%>
			</table>
			<br/>
			<!-- 非信托产品受益信息 -->
			<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
				<font color="#CC99CC" size="-1">
				<%=LocalUtilis.language("message.benInfo",clientLocale) %>
				</font>
				<tr class="trh">
					<!--合同编号-->
			        <td align="center" width="*">
				        <%=LocalUtilis.language("class.productName",clientLocale)%> 
			        </td><!--产品名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--购买合同编号-->
					<td align="center" width="*"><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!--受益份额-->
					<td align="center" width="*"><%=LocalUtilis.language("class.benMoney",clientLocale)%> </td><!-- 受益金额 -->
					<td align="center" width="*"><%=LocalUtilis.language("class.startDate",clientLocale)%></td><!-- 起始日期-->
					<td align="center" width="*"><%=LocalUtilis.language("class.endDate",clientLocale)%></td><!-- 终止日期 -->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称--> 
					<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--银行支行名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%></td><!-- 帐号 -->
					<td align="center" width="60px"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--认购合同状态-->
				</tr>
				<%
				//获得对象及结果集
				QuotientAffirmLocal quotientaffirm = EJBFactory.getQuotientAffirmLocal();
				QuotientAffirmVO quotientvo = new QuotientAffirmVO();
				quotientvo.setCust_id(cust_id);
				List quotientlist = quotientaffirm.loadQuotient(quotientvo,input_operatorCode);
				Map quotient_map = null;
				
				for(int i=0; i<quotientlist.size(); i++)
				{
					quotient_map = (Map)quotientlist.get(i);
				
				%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left"><%=Utility.trimNull(quotient_map.get("NONPRODUCT_NAME"))%></td>
						<td align="left">&nbsp;&nbsp;<%=Utility.trimNull(quotient_map.get("SUBSCRIBE_BH"))%></td>
						<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(quotient_map.get("QUOTIENT_AMOUNT")),new BigDecimal(0),2,"1"))%></td>
						<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(quotient_map.get("QUOTIENT_MONEY")),new BigDecimal(0),2,"1"))%></td>
						<td align="center"><%= Utility.parseInt(Utility.trimNull(quotient_map.get("START_DATE")),new Integer(0))%>&nbsp;&nbsp;</td>
						<td align="center"><%=Utility.parseInt(Utility.trimNull(quotient_map.get("END_DATE")),new Integer(0))%>&nbsp;&nbsp;</td>
						<td align="left"><%=Utility.trimNull(quotient_map.get("BANK_NAME"))%>&nbsp;&nbsp;</td>
						<td align="left"><%=Utility.trimNull(quotient_map.get("BANK_SUB_NAME"))%>&nbsp;&nbsp;</td>
						<td align="left"><%=Utility.trimNull(quotient_map.get("BANK_ACCT"))%>&nbsp;&nbsp;</td>
						<td align="center"><%=Utility.trimNull(quotient_map.get("STATUS_NAME"))%>&nbsp;&nbsp;</td>
					</tr>
				<%
				iCurrent++;
				iCount++;
				}%>
				</table>
				<br/>
				<!-- 非信托产品收益信息 -->
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<font color="#CC99CC" size="-1">
					<%=LocalUtilis.language("message.incomeInfo",clientLocale) %>
					</font>
					<tr class="trh">
						<td align="center" width="*">
						<%=LocalUtilis.language("class.productName",clientLocale)%> 
						</td><!--产品名称-->
						<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--购买合同编号-->
						<td align="center" width="*"><%=LocalUtilis.language("class.ben_amount",clientLocale)%> </td><!-- 收益金额 -->
						<td align="center" width="*"><%=LocalUtilis.language("class.incomeDistributionDate",clientLocale)%></td><!-- 收益分配日期-->
						<td align="center" width="*"><%=LocalUtilis.language("class.syTypeName",clientLocale)%></td><!-- 收益类别 -->
						<td align="center" width="*"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称-->
						<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--银行支行名称-->
						<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%></td><!-- 帐号 -->
						<td align="center" width="*"><%=LocalUtilis.language("class.checkFlag2",clientLocale)%></td><!-- 帐号 -->
					</tr>
						<%
						//声明变量
						
						Integer q_checkFlag;
						String checkFlag;
						
						//获得对象及结果集
						ProfitLocal profit = EJBFactory.getProfitLocal();
						QuotientAffirmVO profitvo = new QuotientAffirmVO();
						profitvo.setCust_id(cust_id);
						profitvo.setCheck_flag(new Integer(2));
						List profitlist = profit.load(profitvo,input_operatorCode);
						Map profit_map = null;
						
						for(int i=0; i<profitlist.size(); i++)
						{
							profit_map = (Map)profitlist.get(i);						
							q_checkFlag = Utility.parseInt(Utility.trimNull(profit_map.get("CHECK_FLAG")),new Integer(0));
							
							//审核
							if(q_checkFlag.intValue() != 2){
								checkFlag = "未通过审核";
							}
							else{
								checkFlag = "通过审核";
							}
							iCurrent++;
							iCount++;
						%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td  align="left"><%=Utility.trimNull(profit_map.get("NONPRODUCT_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(profit_map.get("SUBSCRIBE_BH"))%></td>
						<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(profit_map.get("PROFIT_MONEY")),new BigDecimal(0),2,"1"))%></td>
						<td align="center"><%=Utility.parseInt(Utility.trimNull(profit_map.get("SY_DATE")),new Integer(0))%>&nbsp;&nbsp;</td>
						<td align="center"><%=Utility.trimNull(profit_map.get("SY_TYPE_NAME"))%>&nbsp;&nbsp;</td>
						<td align="center"><%=Utility.trimNull(profit_map.get("BANK_NAME"))%>&nbsp;&nbsp;</td>
						<td align="center"><%=Utility.trimNull(profit_map.get("BANK_SUB_NAME"))%>&nbsp;&nbsp;</td>
						<td align="center"><%=Utility.trimNull(profit_map.get("BANK_ACCT"))%>&nbsp;&nbsp;</td>
						<td align="center"><%=checkFlag%>&nbsp;&nbsp;</td>
					</tr>
						<%
						}%>
				</table>
				</td>
			</TR>
			<!--start 详细信息
				subflag == 0&&input_operator.hasFunc(menu_id, 103)
			-->
			<TR id=r9 <%if (subflag == 10) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=2 width="100%"
					class="table-popup" border=0>
					<TBODY>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.stature",clientLocale)%>:</td><!--身高-->
							<td><%=Utility.trimNull(cust_map.get("STATURE"))%>(CM)</td>
							<td align="right"><%=LocalUtilis.language("class.weight",clientLocale)%>:</td><!--体重-->
							<td><%=Utility.trimNull(cust_map.get("WEIGHT"))%>(KG)</td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.nation",clientLocale)%>:</td><!--民族-->
							<td><%=Utility.trimNull(cust_map.get("NATION_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.ismarried",clientLocale)%>:</td><!--婚姻状况-->
							<td><%if(Utility.parseInt(Utility.trimNull(cust_map.get("MARITAL")),new Integer(0)).intValue()==1){
								out.print(LocalUtilis.language("class.married",clientLocale));
							}else{
								out.print(LocalUtilis.language("class.spinsterhood",clientLocale));
							}
							%></td>
						</tr>
						<tr bgColor=#ffffff>
								<td align="right"><%=LocalUtilis.language("class.healthType",clientLocale)%>:</td><!--健康状况-->
								<td><%=Utility.trimNull(cust_map.get("HEALTH_NAME"))%></td>
								<td align="right"><%=LocalUtilis.language("class.educationDegree",clientLocale)%>:</td><!--教育程度-->
								<td><%=Utility.trimNull(cust_map.get("EDUCATION_NAME")) %></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.spouse",clientLocale)%>:</td><!--配偶姓名-->
							<td><%=Utility.trimNull(cust_map.get("SPOUSE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.spouseInfo",clientLocale)%>:</td><!--配偶信息-->
							<td><%=Utility.trimNull(cust_map.get("SPOUSE_INFO"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.childrenName",clientLocale)%>:</td><!--子女姓名-->
							<td><%=Utility.trimNull(cust_map.get("CHILDREN_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.childrenInfo",clientLocale)%>:</td><!--子女信息-->
							<td><%=Utility.trimNull(cust_map.get("CHILDREN_INFO"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.headOfficeCustomerID",clientLocale)%>:</td><!--总行客户号-->
							<td><%=Utility.trimNull(cust_map.get("HEAD_OFFICE_CUST_ID"))%></td>
							<td align="right"><%=LocalUtilis.language("message.unit",clientLocale)%><%=LocalUtilis.language("class.jdrType2Name",clientLocale)%>:</td><!--单位性质-->
							<td><%=Utility.trimNull(cust_map.get("COMPANY_CHARACTER_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.duty",clientLocale)%>:</td><!--职务-->
							<td><%=Utility.trimNull(cust_map.get("POST"))%></td>
							<td align="right"><%=LocalUtilis.language("class.holderOfAnOffice",clientLocale)%>:</td><!-- 职称 -->
							<td><%=Utility.trimNull(cust_map.get("HOLDEROFANOFFICE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.CompanyEmployees",clientLocale)%>:</td><!--是否为公司员工-->
							<td><%if(Utility.parseInt(Utility.trimNull(cust_map.get("COMPANY_STAFF")),new Integer(0)).intValue()==1){
								out.print(LocalUtilis.language("message.yes",clientLocale));
								}else{out.print(LocalUtilis.language("message.no3",clientLocale));}%>
							</td>
							<td align="right"><%=LocalUtilis.language("class.BOCOMEmployees",clientLocale)%></td><!--是否为交行员工-->
							<td><%if(Utility.parseInt(Utility.trimNull(cust_map.get("BOCOM_STAFF")),new Integer(0)).intValue()==1){
								out.print(LocalUtilis.language("message.yes",clientLocale));
								}else{out.print(LocalUtilis.language("message.no3",clientLocale));}%>
							</td>
						</tr>
						<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==2)  {%>	
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.holdingRelatedInfo",clientLocale)%></td><!--控股相关信息机构)-->
							<td colspan = "3"><%=Utility.trimNull(cust_map.get("HOLDING"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("message.customer",clientLocale)%><%=LocalUtilis.language("class.jdrType2Name",clientLocale)%>:</td><!--客户性质(机构)(1164)-->
							<td><%=Utility.trimNull(cust_map.get("CLIENT_QUALE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("message.operator",clientLocale)%><%=LocalUtilis.language("intrsut.home.quantity",clientLocale)%></td><!--员工数量(机构)-->
							<td><%=Utility.trimNull(cust_map.get("EMPLOYEE_NUM"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.registeredFund",clientLocale)%>:</td><!--注册资金(机构)-->
							<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(cust_map.get("REGISTERED_CAPITAL")),new BigDecimal(0)))%></td>
							<td align="right"><%=LocalUtilis.language("class.registAddress",clientLocale)%>:</td><!--注册地(机构)-->
							<td><%=Utility.trimNull(cust_map.get("REGISTERED_PLACE"))%></td>
						</tr>
						<%}%>
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
			<!--start 财务与偏好信息
				subflag == 0&&input_operator.hasFunc(menu_id, 103)
			-->
			<TR id=r10 <%if (subflag == 11) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=2 width="100%"
					 border=0 class="table-popup">
					<TBODY>
						<tr bgColor=#ffffff>
							<td colspan="4"><b><%=LocalUtilis.language("message.einkommenStatus",clientLocale)%> </b></td>  <!--收支状况-->
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.houseHold",clientLocale)%>:</td><!--供养人数-->
							<td colspan = "3"><%=Utility.trimNull(cust_map.get("FEEDING_NUM_PEOPLE"))%>(<%=LocalUtilis.language("class.persons",clientLocale)%>)</td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.personalIncome",clientLocale)%>:</td><!--个人年收入-->
							<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(cust_map.get("PERSONAL_INCOME")),new BigDecimal(0)))%></td>
							<td align="right"><%=LocalUtilis.language("class.householdIncome",clientLocale)%>:</td><!--家庭年收入-->
							<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(cust_map.get("HOUSEHOLD_INCOME")),new BigDecimal(0)))%></td>
						</tr>
						<tr bgColor=#ffffff>
								<td align="right"><%=LocalUtilis.language("class.mainSourceFinancial",clientLocale)%>:</td><!--主要经济来源-->
								<td><%=Utility.trimNull(cust_map.get("MAIN_SOURCE"))%></td>
								<td align="right"><%=LocalUtilis.language("class.otherSourceFinancial",clientLocale)%>:</td><!--其他经济来源-->
								<td><%=Utility.trimNull(cust_map.get("OTHER_SOURCE")) %></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="LEFT" colspan="4"><b><%=LocalUtilis.language("message.estateStatus",clientLocale)%> </b></td>  <!--房产状况--></tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.estateArea",clientLocale)%>:</td><!--面积-->
							<td><%=Utility.trimNull(cust_map.get("HOUSE_AREA"))%>(M<sup>2</sup>)</td>
							<td align="right"><%=LocalUtilis.language("class.estateAttribute",clientLocale)%>:</td><!--房产属性（1商、2住）-->
							<td><%if(Utility.parseInt(Utility.trimNull(cust_map.get("HOUSE_PROPERTY")),new Integer(0)).intValue()==1){
								out.print("商");
								}else{out.print("住");}%>
							</td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.estateLocation",clientLocale)%>:</td><!--房产位置-->
							<td><%=Utility.trimNull(cust_map.get("HOUSE_POSITION"))%></td>
							<td align="right"><%=LocalUtilis.language("class.marketAppraisal",clientLocale)%>:</td><!--市场估价-->
							<td><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(cust_map.get("MARKET_APPRAISAL")),new BigDecimal(0)))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.platEnvaluate",clientLocale)%>:</td><!--地段评价-->
							<td colspan = "3"><%=Utility.trimNull(cust_map.get("PLAT_ENVALUATE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td colspan = "4"><b><%=LocalUtilis.language("message.vehicleStatus",clientLocale)%> </b></td>  <!--车辆状况-->
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.vehiclesQuantity",clientLocale)%>:</td><!--车辆数量-->
							<td><%=Utility.trimNull(cust_map.get("VEHICLE_NUM"))%></td>
							<td align="right"><%=LocalUtilis.language("class.brand",clientLocale)%>:</td><!--品牌-->
							<td><%=Utility.trimNull(cust_map.get("VEHICLE_MAKE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.vehicleType",clientLocale)%>:</td><!--型号-->
							<td colspan = "3"><%=Utility.trimNull(cust_map.get("VEHICLE_TYPE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td colspan = "4"><b><%=LocalUtilis.language("message.loanStatus",clientLocale)%> </b></td>  <!--贷款状况-->
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.loanType",clientLocale)%>:</td><!--贷款种类-->
							<td><%=Utility.trimNull(cust_map.get("CREDIT_TYPE"))%></td>
							<td align="right"><%=LocalUtilis.language("class.loanNumber",clientLocale)%>:</td><!--贷款数量-->
							<td><%=Utility.parseInt(Utility.trimNull(cust_map.get("CREDIT_NUM")),new Integer(0))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.beginDate",clientLocale)%>:</td><!--起始日-->
							<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(cust_map.get("CREDIT_START_DATE")),new Integer(0)))%></td>
							<td align="right"><%=LocalUtilis.language("class.endDate3",clientLocale)%>:</td><!--终止日-->
							<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(cust_map.get("CREDIT_END_DATE")),new Integer(0)))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.otherInvestmentStatus",clientLocale)%>:</td><!--其他投资状况-->
							<td colspan = "3"><%=Utility.trimNull(cust_map.get("OTHER_INVE_STATUS_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td colspan = "4"><b><%=LocalUtilis.language("message.preferenceInformation",clientLocale)%> </b></td>  <!--偏好信息-->
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.typePreference",clientLocale)%>:</td><!--类型偏好(1166)-->
							<td><%=Utility.trimNull(cust_map.get("TYPE_PREF_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.timeLimitPreference",clientLocale)%>:</td><!--期限偏好(1170)-->
							<td><%=Utility.trimNull(cust_map.get("TIME_LIMIT_PREF_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.investPreference",clientLocale)%>:</td><!--投向偏好(1167)-->
							<td><%=Utility.trimNull(cust_map.get("INVEST_PREF_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.hobbyPreference",clientLocale)%>:</td><!--兴趣偏好(1168)-->
							<td><%=Utility.trimNull(cust_map.get("HOBBY_PREF_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.servicePreference",clientLocale)%>:</td><!--服务偏好(1169)-->
							<td><%=Utility.trimNull(cust_map.get("SERVICE_PREF_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.contactPreference",clientLocale)%>:</td><!--联系方式偏好(1109)-->
							<td><%=Utility.trimNull(cust_map.get("CONTACT_PREF_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.riskPreference",clientLocale)%>:</td><!--风险偏好(1172)-->
							<td colspan = "3"><%=Utility.trimNull(cust_map.get("RISK_PREF"))%></td>
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
			<!-- start 客户沟通记录-->
			<TR id=r11 <%if (subflag == 12) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
					<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" height="15">客户编号</td>
						<td align="center" height="15">客户名称</td>
						<td align="center" height="15">服务时间</td>
						<td align="center" height="15">服务项目</td>
						<td align="center" height="15">交流主题</td>
						<td align="center" height="15"><%=LocalUtilis.language("class.accountManager",clientLocale)%></td><!-- 客户经理 -->
						<td align="center" height="15">执行人</td>
						<td align="center" height="15">类别</td>
						<td align="center" height="25">查看</td>
					</tr>
					<%
					iCount = 0;
					iCurrent = 0;

					List list = cust_local.getCustomerMaintenanceRecord(cust_vo);
					Map map = null;
					for(int i=0;i<list.size();i++){
						map = (Map)list.get(i);
						serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
					 %>
    				<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left" height="25"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
						<td align="center" height="25"><%=Utility.trimNull(map.get("SERVICE_TIME")) %></td>
						<td align="right" height="25"><%=Utility.trimNull(map.get("SERVICE_INFO")) %></td>
						<td align="right" height="25"><%=Utility.trimNull(map.get("SUBJECT")) %></td>
						<td align="center" height="25"><%=Utility.trimNull(map.get("SERVICE_MAN_NAME")) %></td>
						<td align="center" height="25"><%=Utility.trimNull(map.get("EXECUTOR_NAME")) %></td>
						<td align="center" height="25"><%=Utility.trimNull(map.get("DATA_FLAG")) %></td>
						<td align="center" height="25">
							<a href="#" onclick="javascript:showInfo(<%=cust_id%>,<%=serial_no%>);">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("menu.viewInfo",clientLocale)%> " />
								<!--查看当前信息-->
							</a>
						</td>
					</tr>
					<%
					iCount ++;
					iCurrent ++;
					}
					%>
					<tr class="trbottom">
                        <!--合计--><!--项-->
						<td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" height="25"></td>
						<td align="right" height="25"></td>
						<td align="right" height="25"></td>
						<td align="right" height="25"></td>
						<td align="right" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
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
			
			<TR bgColor=#ffffff>
				<td align=center>

				<!--返回-->
				<button type="button"  class="xpbutton3" accessKey=b id="btnSave" name="btnSave" <%if(showflag==1){%>style="display:none"<%}%>
                    onclick="javascript:window.returnValue=null;history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
				</td>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
<%cust_local.remove(); %>
</BODY>
</HTML>



