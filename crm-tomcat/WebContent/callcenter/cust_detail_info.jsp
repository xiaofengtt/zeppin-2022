<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
CustomerLocal cust_local = EJBFactory.getCustomer();//�ͻ�Bean
CustomerVO cust_vo = new CustomerVO();

String cust_no = request.getParameter("cust_no");
int iiCount = 0;
int subflag = Utility.parseInt(request.getParameter("subflag"), 0);
int showflag = Utility.parseInt(request.getParameter("showflag"), 0);
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), null);
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);

//��ÿͻ���ϸ��Ϣ����ֵ��ת��
cust_vo.setCust_id(cust_id);
cust_vo.setInput_man(input_operatorCode);
List cust_list = cust_local.listProcAll(cust_vo);
java.io.InputStream in = null;
Map cust_map = new HashMap();
String custno = "";
String wtflag_checked = "";
String print_deploy_bill_checked = "";
String print_post_info_checked = "";
if(cust_list != null && cust_list.size() > 0)
{
	cust_map = (Map) cust_list.get(0);
	//��ͼƬ�����Ʊ���
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
   for(i=0;i<10;i++)
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
		document.parentWindow.parent.document.getElementById("target_custid").value = target_custid; 
		document.parentWindow.parent.document.getElementById("callTalkType").value = 1;
		//document.parentWindow.parent.document.getElementById("callcenterLink").onclick();
		iframeUrl = '/affair/sms/cust_tel.jsp?input_flag=2&target_custid='+target_custid;
		location = iframeUrl;
<%}%>
}
function showPreProductInfo(preproduct_id){
	var url = "<%=request.getContextPath()%>/wiki/product_info_view.jsp?preproduct_id="+preproduct_id;
	window.open(url);
}
</script>

<BODY class="BODY">

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
						<td><img border="0" src="<%=request.getContextPath()%>/images/Feichuang5.jpg" width="38"
							height="36"></td>
						<td align="left"><b><font size="+1"><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></font>
						<font face="������">(<%=Utility.trimNull(custno)%>) </b></td>
					</tr>
				</table>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0"
					class="edline">
					<TBODY>
						<TR>
							<TD vAlign=top width=100>&nbsp;</TD>
							<TD vAlign=top width=100>&nbsp;</TD>
							<TD vAlign=top>&nbsp;</TD>
							<%//if(input_operator.hasFunc(menu_id, 103)){%>
							<!--������Ϣ-->
                            <TD id=d0 style="background-repeat: no-repeat" onclick=show(0) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 0) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
							<%//}%>
							<!--�Ϲ���Ϣ-->
                            <TD id=d1 style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 1) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.subscriptionInfo",clientLocale)%> </TD>
							<!--������Ϣ-->
                            <TD id=d2 style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 2) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.benInfo",clientLocale)%> </TD>
							<!--������Ϣ-->
                            <TD id=d3 style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 3) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.incomeInfo",clientLocale)%> </TD>
							<!--�޸���ϸ-->
                            <TD id=d4 style="background-repeat: no-repeat" onclick=show(4) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 3) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.mofifyDteails",clientLocale)%> </TD>
							<!--����Ҫ��-->
                            <TD id=d5 style="background-repeat: no-repeat" onclick=show(5) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 5) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.defineEle",clientLocale)%> </TD>
							<!--�ݶ���-->
                            <TD id=d6 style="background-repeat: no-repeat" onclick=show(6) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 6) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.shareChange",clientLocale)%> </TD>
							<!--֤��ͼƬ-->
                            <TD id=d7 style="background-repeat: no-repeat" onclick=show(7) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 7) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.docImg",clientLocale)%> </TD>
							<!--ͨ����¼-->
                            <TD id=d8 style="background-repeat: no-repeat" onclick=show(8) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 8) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("message.callRecords",clientLocale)%> </TD>
							<!--�����в�Ʒ-->
                            <TD id=d9 style="background-repeat: no-repeat" onclick=show(9) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/<%if( subflag == 9) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;<%=LocalUtilis.language("menu.nonProduct",clientLocale)%></TD>
							
						</TR>
					</TBODY>
				</TABLE>
				</TD>
			</TR>
			<!--start ������Ϣ
				subflag == 0&&input_operator.hasFunc(menu_id, 103)
			-->
			<TR id=r0 <%if (subflag == 0) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=2 width="100%"
					class="tablelinecolor" border=0>
					<TBODY>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--�ͻ���Դ-->
							<td colspan="3"><%=Utility.trimNull(cust_map.get("CUST_SOURCE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP�����-->
							<td><%=Utility.trimNull(cust_map.get("VIP_CARD_ID"))%>��<%if(cust_map.get("VIP_DATE")!=null){Format.formatDateCn(new Integer(cust_map.get("VIP_DATE").toString()));}%>��</td>
							<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--�ϸ�Ͷ���˱��-->
							<td><%=Utility.trimNull(cust_map.get("HGTZR_BH"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--֤������-->
							<td><%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
							<td><%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						</tr>
						<tr bgColor=#ffffff>
								<td align="right"><%=LocalUtilis.language("class.regional",clientLocale)%> ��</td><!--��������-->
								<td><%=Utility.trimNull(cust_map.get("GOV_PROV_REGIONAL_NAME"))%>-<%=Utility.trimNull(cust_map.get("GOV_REGIONAL_NAME"))%></td>
								<td align="right"><%=LocalUtilis.language("class.referee",clientLocale)%> ��</td><!-- �Ƽ��� -->
								<td><%=Utility.trimNull(cust_map.get("RECOMMENDED")) %></td>
						</tr>
						<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==1)  {%>	
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--��������-->
							<td><%if(cust_map.get("BIRTHDAY")!=null)Format.formatDateCn(new Integer(cust_map.get("BIRTHDAY").toString()));%></td>
							<td align="right"></td>
							<td></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.age",clientLocale)%> :</td><!--����-->
							<td><%=Utility.trimNull(cust_map.get("AGE"))%></td>
							<td align="right"><%=LocalUtilis.language("class.sex",clientLocale)%> :</td><!--�Ա�-->
							<td><%=Utility.trimNull(cust_map.get("SEX_NAME"))%></td>
						</tr>
						<%}%>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--�ֻ�-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("MOBILE"))%></a></td>
							<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--�ֻ�-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("BP"))%></a></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :</td><!--����-->
							<td><%=Utility.trimNull(cust_map.get("FAX"))%></td>
							<td align="right">Email:</td>
							<td><%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--��˾�绰-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("O_TEL"))%></a></td>
							<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--��ͥ�绰-->
							<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("H_TEL"))%></a></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.current_money",clientLocale)%> :</td><!--�������-->
							<td><%if(cust_map.get("BEN_AMOUNT")!=null)Utility.trimNull(Format.formatMoney(new BigDecimal(cust_map.get("BEN_AMOUNT").toString())));%></td>
							<td align="right"><%=LocalUtilis.language("message.lastRgDate",clientLocale)%> :</td><!--�������ʱ��-->
							<td><%if(cust_map.get("LAST_RG_DATE")!=null)Format.formatDateCn(new Integer(cust_map.get("LAST_RG_DATE").toString()));%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
							<td><%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.lastProduct",clientLocale)%> :</td><!--���һ���Ϲ���Ʒ-->
							<td><%=Utility.trimNull(cust_map.get("LAST_PRODUCT_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.apportionAmount",clientLocale)%> :</td><!--�ۼƷ�̯�����-->
							<td><%=Utility.parseDecimal(Utility.trimNull(cust_map.get("ACTIVITY_FEE")),new BigDecimal(0.00))%></td>
							<td align="right"><%=LocalUtilis.language("class.wtrFlag",clientLocale)%> :</td><!--ί���˱�־-->
							<td><b><%=wtflag_checked%></b></td>
						</tr>
						<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==2)  {%>	
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</td><!--��������-->
							<td><%=Utility.trimNull(cust_map.get("LEGAL_MAN"))%></td>
							<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
							<td><%=Utility.trimNull(cust_map.get("LEGAL_ADDRESS"))%></td>
						</tr>
						<%}%>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> :</td><!--�ͻ�����-->
							<td><%=Utility.trimNull(cust_map.get("CUST_LEVEL_NAME"))%></td>
							<td align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</td><!--��ϵ��ʽ-->
							<td><%=Utility.trimNull(cust_map.get("TOUCH_TYPE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> :</td><!--�ʼĵ�ַ-->
							<td><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></td>
							<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
							<td><%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2:</td><!--�ʼĵ�ַ-->
							<td><%=Utility.trimNull(cust_map.get("POST_ADDRESS2"))%></td>
							<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2:</td><!--��������-->
							<td><%=Utility.trimNull(cust_map.get("POST_CODE2"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.summary",clientLocale)%> :</td><!--��ע��Ϣ-->
							<td colspan="3"><%=Utility.trimNull(cust_map.get("SUMMARY"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.printDeployBill",clientLocale)%> :</td><!--�Ƿ��ӡ�ͻ����ʵ�-->
							<td><b><%=print_deploy_bill_checked%></b></td>
							<td align="right"><%=LocalUtilis.language("class.customerIsLink",clientLocale)%> ��</td><!--������-->
							<td><b><%if(cust_map.get("IS_LINK")!=null){if(new Integer(cust_map.get("IS_LINK").toString()).intValue()==1) out.print(" Y ");else out.print(" N ");}%></b></td>
						</tr>

						<tr bgColor=#ffffff>
							<td align="right"><%=LocalUtilis.language("class.printPostInfo",clientLocale)%> :</td><!--�Ƿ��ӡ��¶��Ϣ-->
							<td><b><%=print_post_info_checked%></b></td>
							<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!-- �ͻ����� -->
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
			<!--end ������Ϣ-->
			<!--start �Ϲ���Ϣ
				subflag == 1||!input_operator.hasFunc(menu_id, 103)
			-->
			<TR id=r1 <%if (subflag == 1) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
				<TD>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr bgColor=#ffffff>
						<td align="left" colspan="6" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.reservationInfo",clientLocale)%> </font></td><!--ԤԼ��Ϣ-->
					</tr>
					<tr class="trh">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
						<td align="center" height="15"><%=LocalUtilis.language("class.preCode2",clientLocale)%> </td><!--ԤԼ���-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> </td><!--ԤԼ���-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!--���Ϲ����-->
						<td align="center" height="15"><%=LocalUtilis.language("class.preDate",clientLocale)%> </td><!--ԤԼ����-->
						<td align="center" height="15"><%=LocalUtilis.language("class.preStatus",clientLocale)%> </td><!--ԤԼ״̬-->
					</tr>
					<%
					PreContractLocal precontract_local = EJBFactory.getPreContract();//ԤԼBean
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
						<td align="left" colspan="8" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.subscriptionInfo",clientLocale)%> </font></td><!--�Ϲ���Ϣ-->
					</tr>
					<tr class="trh"">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
						<td align="center" height="15"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
						<td align="center" height="15"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!--ǩ������-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!--���Ϲ����-->
						<td align="center" height="15">Ԥ��������</td>
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.endDate4",clientLocale)%> </td><!--��������-->
						<td align="center" height="15"><%=LocalUtilis.language("class.htStatus",clientLocale)%> </td><!--��ͬ״̬-->
						<td align="center" height="15"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> </td><!--��˱�־-->
					</tr>
					<%
					String sub_product_name = "";
					BigDecimal exp_rate1 = new  BigDecimal(0);
					BigDecimal exp_rate2 = new  BigDecimal(0);
					ContractLocal contract_local = EJBFactory.getContract();//��ͬBean
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
					    <td align="left" height="25"><%=Utility.trimNull(contract_map.get("PRODUCT_NAME"))%><%if(sub_product_name.length()>0){ %>[<%=sub_product_name%>]<%}%></td>
						<td align="left" height="25"><%=Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0)))%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(contract_map.get("RG_MONEY")))))%></td>
						<td align="center" height="25"><%=exp_rate1%>%-<%=exp_rate2%>%</td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("END_DATE")), new Integer(0)))%></td>
						<td align="center" height="25"><%=Utility.trimNull(contract_map.get("HT_STATUS_NAME"))%></td>
						<!--δ���--><!--�����-->
                        <td align="center" height="25"><%if(Utility.parseInt(Utility.trimNull(contract_map.get("CHECK_FLAG")), 0)==1){%><%=LocalUtilis.language("message.unaudited",clientLocale)%><%}else{%><%=LocalUtilis.language("message.checked",clientLocale)%> <%}%></td>
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
			<!--end �Ϲ���Ϣ-->
			<!-- start ������Ϣ-->
			<TR id=r2 <%if (subflag == 2) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor="#ffffff">
				<TD>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" height="15"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
						<td align="center" height="15"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.rgMoney",clientLocale)%> </td><!-- �Ϲ���� -->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.exchangeAmount",clientLocale)%> </td><!--ת�ý��-->
						<td align="center" height="15" sort="num"><%=LocalUtilis.language("class.backAmount",clientLocale)%> </td><!--�Ҹ����-->
						<td align="center" height="15" sort="num">
							<%if(languageType.equals("zh_CN")&&user_id.intValue()==19){%>
								���汾���
							<%}
							else{%>
							<%=LocalUtilis.language("class.benAmount",clientLocale)%>
							<%}%> 
						</td><!--������-->
						<td align="center" height="15"><%=LocalUtilis.language("class.benDate",clientLocale)%> </td><!--��������-->
						<td align="center" height="15"><%=LocalUtilis.language("class.benPeriod2",clientLocale)%> </td><!--��������-->
						<td align="center" height="15"><%=LocalUtilis.language("class.bankSubName2",clientLocale)%> </td><!--������������-->
						<td align="center" height="15"><%=LocalUtilis.language("class.bankAcct2",clientLocale)%> </td><!--���������˺�-->
						<td align="center" height="15"><%=LocalUtilis.language("class.benState",clientLocale)%> </td><!--����Ȩ״̬-->
					</tr>
					<%
					int iCount = 0;
					int iCurrent = 0;
					BigDecimal amount = new BigDecimal(0.000);
					BigDecimal to_amount = new BigDecimal(0.000);//�Ϲ����
					BigDecimal exchange_amount = new BigDecimal(0.000);//ת�ý��
					BigDecimal back_amount = new BigDecimal(0.000);//�Ҹ����
					BigDecimal ben_amount = new BigDecimal(0.000);//������
					Integer serial_no = new Integer(0);
					BenifitorLocal benifitor=EJBFactory.getBenifitor();
					BenifitorVO benifitor_vo = new BenifitorVO();
					benifitor_vo.setBook_code(new Integer(1));
					benifitor_vo.setCust_id(cust_id);
					benifitor_vo.setProduct_id(product_id);

					List ben_list = benifitor.QueryBenifitor(benifitor_vo);
					Map ben_map = new HashMap();
					String period_unit = "";//��������1��������2��������
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
    					//���ݿͻ������Ʒ�ͺ�ͬ��Ż�ÿͻ���ͬ��Ϣ
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
                        <!--�ϼ�--><!--��-->
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
			<!-- end ������Ϣ-->
			<!-- start ������Ϣ-->
			<TR id=r3 <%if (subflag == 3) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=4 width="100%"
					class="tablelinecolor" border=0>
					<tr class="trh">
						<td align="center" height="22"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
						<td align="center" height="25"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
						<td align="center" height="22"><%=LocalUtilis.language("class.bond1",clientLocale)%> </td><!--��������Ϣ-->
						<td align="center" height="22"><%=LocalUtilis.language("class.gain1",clientLocale)%> </td><!--�м�����-->
						<td align="center" height="22"><%=LocalUtilis.language("class.gain2",clientLocale)%> </td><!--��������-->
						<td align="center" height="22"><%=LocalUtilis.language("class.bond2",clientLocale)%> </td><!--�Ҹ�����Ϣ-->
						<td align="center" height="22"><%=LocalUtilis.language("class.toMoney2",clientLocale)%> </td><!--���ڱ���-->
						<td align="center" height="22"><%=LocalUtilis.language("class.bondTax",clientLocale)%> </td><!--��˰-->
						<td align="center" height="22"><%=LocalUtilis.language("class.totalMoney2",clientLocale)%> </td><!--�ϼƽ��-->

					</tr>
					<%
					to_amount = new BigDecimal("0.000");
					java.math.BigDecimal Bond1_amount=new BigDecimal("0.000");//��������Ϣ
					java.math.BigDecimal Gain1_amount=new BigDecimal("0.000");//�м�����
					java.math.BigDecimal Gain2_amount=new BigDecimal("0.000");//��������
					java.math.BigDecimal Bond2_amount=new BigDecimal("0.000");//�Ҹ�����Ϣ
					java.math.BigDecimal To_money2_amount=new BigDecimal("0.000");//���ڱ���
					java.math.BigDecimal Bond_tax_amount=new BigDecimal("0.000");//��˰
					java.math.BigDecimal Total_money_amount=new BigDecimal("0.000");//�ϼƽ��
					
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
						<td  align="left" height="25"><%=Utility.trimNull(deploy_map.get("CONTRACT_SUB_BH"))%></td>

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
						<!--�ϼ�--><!--��-->
                        <td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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
			<!-- end ������Ϣ-->
			<!-- start �޸���ϸ-->
			<TR id=r4 <%if (subflag == 4) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center"><%=LocalUtilis.language("menu.inputMan",clientLocale)%> </td><!--�޸���-->
						<td align="center"><%=LocalUtilis.language("menu.inputDate",clientLocale)%> </td><!--�޸�����-->
						<td align="center"><%=LocalUtilis.language("class.modifyField",clientLocale)%> </td><!--�޸��ֶ�-->
						<td align="center"><%=LocalUtilis.language("class.originalValue",clientLocale)%> </td><!--�ֶ�ԭֵ-->
						<td align="center"><%=LocalUtilis.language("class.newValue",clientLocale)%> </td><!--�ֶ���ֵ-->
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
						<td align="left" bgColor=#ffffff ><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(cust_change_map.get("INPUT_MAN")), new Integer(0))))%></td>
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
			<!-- end �޸���ϸ-->
			<!-- start ����Ҫ��-->
			<TR id=r5 <%if (subflag == 5) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<table border="0" width="100%">
					<tr>
						<td colspan=4><b><%=LocalUtilis.language("message.setCustElements",clientLocale)%> :<br><!--�ͻ��Զ���Ҫ������-->
						&nbsp;</b></td>
					</tr>
					<tr>
						<td align="center" height="25"></td>
						<td colspan=3>
						<table id="table3" border="0" cellspacing="1" cellpadding="2"
							class="tablelinecolor" width="100%">
							<tr class="trh">
								<td align="center" height="25"><%=LocalUtilis.language("message.eleName",clientLocale)%> </td><!--Ҫ������-->
								<td align="center" height="25"><%=LocalUtilis.language("class.exchangeTime",clientLocale)%> </td><!--Ҫ��ֵ-->
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
			<!-- end ����Ҫ��-->
			<!-- start �ͻ�����-->
			<TR id=r999 <%if (subflag == 999) out.print("style='display:'"); else out.print("style='display:none'");%>
				bgColor=#ffffff>
				<TD>
				<TABLE cellSpacing=1 cellPadding=4 width="100%"
					class="tablelinecolor" border=0>
					<tr bgcolor="#97e8ab">
						<td align="center"><%=LocalUtilis.language("class.exchangeTime",clientLocale)%> </td><!--����ʱ��-->
						<td align="center"><%=LocalUtilis.language("class.author",clientLocale)%> </td><!--�ύ��-->
						<td align="center"><%=LocalUtilis.language("class.acTheme",clientLocale)%> </td><!--��������-->
						<td align="center"><%=LocalUtilis.language("class.progress",clientLocale)%> </td><!--������չ-->
						<td align="center"><%=LocalUtilis.language("class.infoLevel",clientLocale)%> </td><!--��Ϣ�ȼ�-->
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
			<!-- end �ͻ�����-->
			<!-- start �ݶ���-->
			<TR id=r6 <%if (subflag == 6) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor="#ffffff">
				<td><br>
				<TABLE cellSpacing=1 cellPadding=4 width="100%"
					class="tablelinecolor" border=0>
					<tr class="trh">
						<td class="tdh"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
						<td class="tdh"><%=LocalUtilis.language("class.changeType",clientLocale)%> </td><!--������-->
						<td><%=LocalUtilis.language("class.changeAmount",clientLocale)%> </td><!--�ݶ�����-->
						<td><%=LocalUtilis.language("class.benAmount2",clientLocale)%> </td><!--��ֵ-->
						<td><%=LocalUtilis.language("class.date2",clientLocale)%> </td><!--����-->
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
						if(Utility.trimNull(change_ben_map.get("CHANGE_TYPE_NAME")).equals(enfo.crm.tools.LocalUtilis.language("message.redemption",clientLocale)))/*���*/
							sy_type_name = "-"+Utility.trimNull(change_ben_map.get("PROV_LEVEL_NAME"));
					%>
					<tr bgColor=#ffffff>				
						<td align="left" height="25"><%=Utility.trimNull(change_ben_map.get("PRODUCT_NAME"))%></td>
						<td align="left" height="25"><%=Utility.trimNull(change_ben_map.get("CHANGE_TYPE_NAME"))%><%=Utility.trimNull(sy_type_name)%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(change_ben_map.get("CHANGE_AMOUNT")))))%></td>
						<td align="right" height="25"><%=Utility.trimNull(change_ben_map.get("BEN_AMOUNT"))%></td>				
						<td align="center" height="25"><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(change_ben_map.get("CHANGE_DATE")), new Integer(0))))%></td>
					</tr>
					<%
					iCount++;
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
			<!-- end �ݶ���-->
			<tr id="r7" <%if (subflag == 7) out.print("style='display:block'"); else out.print("style='display:none;'");%> bgColor="#ffffff">
				<td><br>
					<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<%if (cust_id != null && !"".equals(cust_id) && cust_id.intValue() != 0 && (in != null && !"".equals(in))) {%>
					<tr bgColor=#ffffff>
						<td align="left" colspan="1" height="15"><font color="#CC99CC"
							size="-1"><%=LocalUtilis.language("message.docImg",clientLocale)%> </font><!--֤��ͼƬ-->
						</td>
					</tr>
					<tr align="center">
						<td>
							<iframe src ="show_client_image.jsp?cust_id=<%=cust_id%>" name="sonIframe" 	frameborder="no" border="0" height="300" width="820"></iframe>
						</td>	
					</tr>
					<%}%>
					</table>
					<table border="0" width="100%">
						<tr valign="top">
							<td></td>
							<td align="right"></td>
						</tr>
					</table>
				</td>
			</tr>
			<TR id=r8 <%if (subflag == 8) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor="#ffffff">
				<TD>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td><%=LocalUtilis.language("class.callDirection",clientLocale)%> </td><!--���з���-->
						<td><%=LocalUtilis.language("class.callTime",clientLocale)%> </td><!--ͨ����ʼʱ��-->
						<td><%=LocalUtilis.language("class.callLength",clientLocale)%>(<%=LocalUtilis.language("message.seconds",clientLocale)%> )</td><!--ͨ��ʱ��--><!--��-->
						<td><%=LocalUtilis.language("class.extension",clientLocale)%> </td><!--�ֻ�����-->
						<td><%=LocalUtilis.language("class.tel",clientLocale)%> </td><!--�绰����-->
						<td><%=LocalUtilis.language("class.callContent",clientLocale)%> </td><!--ͨ������-->
					</tr>
					<%
						CallCenterLocal callcenterLocal = EJBFactory.getCallCenter();
						CCVO ccvo = new CCVO();
						ccvo.setCust_id(cust_id);
						ccvo.setInput_man(input_operatorCode);
						IPageList cc_pageList = callcenterLocal.listCallRecords(ccvo,1,-1);
						List cc_list = cc_pageList.getRsList();
						Map cc_map = null;
						String directStr = enfo.crm.tools.LocalUtilis.language("message.calling",clientLocale); //����
		
						for(int i=0;i<cc_list.size();i++){
							cc_map = (Map)cc_list.get(i);
							if(Utility.trimNull(cc_map.get("DIRECTION")).equals("1")){
								directStr=enfo.crm.tools.LocalUtilis.language("message.called",clientLocale);//����
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
			<!--start �����в�Ʒ��Ϣ	-->
			<TR id=r9 <%if (subflag == 9) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor="#ffffff">
				<td>
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
				<font color="#CC99CC" size="-1">
				<%=LocalUtilis.language("message.subscriptionInfo",clientLocale) %>
				</font>
				<tr class="trh">
					<!--��ͬ���-->
			        <td align="center" width="*">
			        <%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
					<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--�����ͬ���-->
					<td align="center" width="*"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--�Ϲ����-->
					<td align="center" width="*"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!-- ǩ������ -->
					<td align="center" width="*"><%=LocalUtilis.language("class.dzDate",clientLocale)%></td><!-- �ɿ����� -->
					<td align="center" ><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--��������-->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--����֧������-->
					<td align="center" width="*"><%=LocalUtilis.language("class.htStatus",clientLocale)%> </td><!--�Ϲ���ͬ״̬-->
				</tr>
				<%

				//��ö��󼰽����
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
			<!-- �����в�Ʒ������Ϣ -->
			<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
				<font color="#CC99CC" size="-1">
				<%=LocalUtilis.language("message.benInfo",clientLocale) %>
				</font>
				<tr class="trh">
					<!--��ͬ���-->
			        <td align="center" width="*">
				        <%=LocalUtilis.language("class.productName",clientLocale)%> 
			        </td><!--��Ʒ����-->
					<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--�����ͬ���-->
					<td align="center" width="*"><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!--����ݶ�-->
					<td align="center" width="*"><%=LocalUtilis.language("class.benMoney",clientLocale)%> </td><!-- ������ -->
					<td align="center" width="*"><%=LocalUtilis.language("class.startDate",clientLocale)%></td><!-- ��ʼ����-->
					<td align="center" width="*"><%=LocalUtilis.language("class.endDate",clientLocale)%></td><!-- ��ֹ���� -->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--��������--> 
					<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--����֧������-->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%></td><!-- �ʺ� -->
					<td align="center" width="60px"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--�Ϲ���ͬ״̬-->
				</tr>
				<%
				//��ö��󼰽����
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
				<!-- �����в�Ʒ������Ϣ -->
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<font color="#CC99CC" size="-1">
					<%=LocalUtilis.language("message.incomeInfo",clientLocale) %>
					</font>
					<tr class="trh">
						<td align="center" width="*">
						<%=LocalUtilis.language("class.productName",clientLocale)%> 
						</td><!--��Ʒ����-->
						<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--�����ͬ���-->
						<td align="center" width="*"><%=LocalUtilis.language("class.ben_amount",clientLocale)%> </td><!-- ������ -->
						<td align="center" width="*"><%=LocalUtilis.language("class.incomeDistributionDate",clientLocale)%></td><!-- �����������-->
						<td align="center" width="*"><%=LocalUtilis.language("class.syTypeName",clientLocale)%></td><!-- ������� -->
						<td align="center" width="*"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--��������-->
						<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--����֧������-->
						<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%></td><!-- �ʺ� -->
						<td align="center" width="*"><%=LocalUtilis.language("class.checkFlag2",clientLocale)%></td><!-- �ʺ� -->
					</tr>
						<%
						//��������
						
						Integer q_checkFlag;
						String checkFlag;
						
						//��ö��󼰽����
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
							
							//���
							if(q_checkFlag.intValue() != 2){
								checkFlag = "δͨ�����";
							}
							else{
								checkFlag = "ͨ�����";
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
			<TR bgColor=#ffffff>
				<td align=center>

				<!--����-->
				<button type="button" class="xpbutton3" accessKey=b id="btnSave" name="btnSave" <%if(showflag==1){%>style="display:none"<%}%>
                    onclick="javascript:window.returnValue=null;history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
				</td>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>



