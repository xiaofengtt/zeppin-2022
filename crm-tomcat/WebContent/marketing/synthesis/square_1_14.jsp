<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
sPage = request.getParameter("page");
sPagesize = request.getParameter("pagesize");
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

int firstFlag = Utility.parseInt(request.getParameter("firstFlag"),0);
String sQuery = request.getParameter("sQuery");

String[] paras = Utility.splitString(sQuery + " ", "$");

Integer beginDate = Utility.parseInt(request.getParameter("beginDate"),new Integer(0));
Integer endDate = Utility.parseInt(request.getParameter("endDate"),new Integer(0));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String sy_type1=Utility.trimNull(request.getParameter("sytype1"));
String sy_type2=Utility.trimNull(request.getParameter("sytype2"));
String sy_type3=Utility.trimNull(request.getParameter("sytype3"));
String sy_type4=Utility.trimNull(request.getParameter("sytype4"));
String sy_type5=Utility.trimNull(request.getParameter("sytype5"));
String sy_type =  sy_type1+" $"+sy_type2+" $"+sy_type3+" $"+sy_type4+" $"+sy_type5+" $";//
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
Integer link_man = Utility.parseInt(request.getParameter("link_man"),new Integer(0));

if(paras.length>1){ //设置查询条件并按下查询按钮后会执行的部分：
   
 
   
   sy_type1=Utility.trimNull(paras[9].trim());
   sy_type2=Utility.trimNull(paras[10].trim());
   sy_type3=Utility.trimNull(paras[11].trim());
   sy_type4=Utility.trimNull(paras[12].trim());
   sy_type5=Utility.trimNull(paras[13].trim());
   
   sy_type =  sy_type1+" $"+sy_type2+" $"+sy_type3+" $"+sy_type4+" $"+sy_type5+" $";//
   contract_sub_bh=Utility.trimNull(paras[3].trim());
   overall_product_id= Utility.parseInt(paras[5].trim(),new Integer(0));
   prov_level=Utility.trimNull(paras[1].trim());
   beginDate=Utility.parseInt(paras[2].trim(),new Integer(0));
   endDate=Utility.parseInt(paras[8].trim(),new Integer(0));
   cust_name=Utility.trimNull(paras[7].trim());
   sub_product_id=Utility.parseInt(paras[14].trim(),new Integer(0));
   link_man=Utility.parseInt(paras[15].trim(),new Integer(0));
}

DeployLocal deploy = EJBFactory.getDeploy();
DeployVO vo = new DeployVO();
vo.setBegin_date(beginDate);
vo.setEnd_date(endDate);
vo.setBook_code(input_bookCode);
vo.setSy_type(sy_type);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(overall_product_id);
vo.setCust_name(cust_name);
vo.setContract_sub_bh(contract_sub_bh);
vo.setProv_level(prov_level);
vo.setSub_product_id(sub_product_id);
vo.setLink_man(link_man);
IPageList pageList = deploy.queryDetail(vo, _page, pagesize);
List list = pageList.getRsList();

sUrl = sUrl+ "&firstFlag=1&pagesize="+ sPagesize+"&sQuery=" + sQuery;
status=11;

%>

<HTML>
<HEAD>
<TITLE>发行期利息查询与打印</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>
function selectAll()
{

if(document.theform.sy_type6.checked=true)
{
		document.theform.sy_type1.checked=true;
		document.theform.sy_type2.checked=true;
		document.theform.sy_type3.checked=true;
		document.theform.sy_type4.checked=true;
		document.theform.sy_type5.checked=true;
}
}

function refreshPage()
{
	location = 'square_1_14.jsp?firstFlag=1&pagesize=' + document.theform.pagesize.value+'&sQuery=<%=sQuery%>';
}

function printPaycardall()
{  
	var url = 'square_1_14_cell.jsp?&product_id=<%=overall_product_id%>&sytype1=<%=sy_type1%>&sytype2=<%=sy_type2%>&sytype3=<%=sy_type3%>&sytype4=<%=sy_type4%>&sytype5=<%=sy_type5%>&begin_date=<%=beginDate%>&end_date=<%=endDate%>&contract_sub_bh=<%=contract_sub_bh%>&sub_product_id=<%=sub_product_id%>&link_man=<%=link_man%>';
	<%if(user_id.intValue() == 24){%>
		url = 'square_1_14_cell_xiamen.jsp?&product_id=<%=overall_product_id%>&sytype1=<%=sy_type1%>&sytype2=<%=sy_type2%>&sytype3=<%=sy_type3%>&sytype4=<%=sy_type4%>&sytype5=<%=sy_type5%>&begin_date=<%=beginDate%>&end_date=<%=endDate%>&contract_sub_bh=<%=contract_sub_bh%>&sub_product_id=<%=sub_product_id%>&link_man=<%=link_man%>';
	<%}%>
   window.open(url);
}

function advancedQuery()
{
	var ret = showModalDialog('square_1_14_condition.jsp?rets=<%=sQuery%>&product_id=<%=overall_product_id%>', '', 'dialogWidth:700px;dialogHeight:350px;status:0;help:0');
	if(ret != null)
	{
	    disableAllBtn(true);
	   	location = 'square_1_14.jsp?firstFlag=1&pagesize=' + document.theform.pagesize.value+'&sQuery='+ret ;
	}
}
function printNotice(obj)
{
	if(checkedCount(obj) == 0)
	{
		sl_alert("请选择要打印的记录！");
		return false;
	}
	disableAllBtn(true);
	<%if(user_id.intValue() == 13){%>
		document.theform.action = "square_1_14_notice_print_guotou.jsp";
	<%}else if(user_id.intValue() == 24){%>
		document.theform.action="square_1_14_notice_print_xiamen.jsp";
	<%}else{%>
		document.theform.action="square_1_14_notice_print.jsp";
	<%}%>
	document.theform.submit();
}
function printInfo(serial_no)
{
	disableAllBtn(true);
	document.theform.serial_no.value = serial_no;
	document.theform.action="square_1_14_tt_print.jsp";
	document.theform.submit();
}

</script>
<BODY class="BODY" <%if(firstFlag==0){%> onLoad="javascript:advancedQuery();" <%}%>>
<%@ include file="/includes/waiting.inc"%> 

<form name="theform">
<input type=hidden name='sytype1' value=''>
<input type=hidden name='sytype2' value=''>
<input type=hidden name='sytype3' value=''>
<input type=hidden name='sytype4' value=''>
<input type=hidden name='sytype5' value=''>
<input type=hidden name='serial_no' value=''>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left >
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<%//int  showTabInfo =  showTabInfoTop.intValue();
					//if(showTabInfo != 1){ %>
					<tr>
						<td colspan=5><IMG height=28 src="/images/member.gif" width=32 align=absMiddle border=0><b><%=menu_info%></b>
						<td  align="right"></td>
					</tr>
					<%//} %>
					<tr>
				
						<td align="right">&nbsp;&nbsp;
						<button type="button"  class="xpbutton4" name="btnQuery" accessKey=f onClick="javascript:advancedQuery();">查询(<u>F</u>)</button>  
							&nbsp;&nbsp;&nbsp;
						<%if (input_operator.hasFunc(menu_id, 10701)){%>
						<button type="button"  class="xpbutton3" accessKey=p name="btnRefresh" title="打印收据" onclick="javascript:printPaycardall();">打印明细</button>
                        <%}%>
						&nbsp;&nbsp;&nbsp;
                        <%if (input_operator.hasFunc(menu_id, 107)){%>
						<button type="button"  class="xpbutton4" accessKey=p name="btnRefresh" title="打印通知单" onclick="javascript:printNotice(document.theform.selectbox);"><%=user_id.intValue() == 24 ? "打印收益报告" : "打印通知单" %></button>
						<%}%>
						</td>
					</tr>
					<tr><td>&nbsp;&nbsp;</td>
					</tr> 
				
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.selectbox,this);">受益人</td>
						<td align="center" >产品名称</td>
						<td align="center" >合同编号</td>
						<td align="center" >份额</td>
						<td align="center" >受益级别</td>
						<td align="center" >收益类别</td>
						<td align="center" >每股收益</td>
						<td align="center"  sort="num">本次收益</td>
							<td align="center" >扣税</td>
						<td align="center" >本次实付收益</td>
					
						<td align="center" >结算日期</td>
						<td align="center"  >银行名称</td>						
						<td align="center"  >银行帐号</td>
						<td align="center" style="width:12mm">打印电汇凭证</td>		
					</tr>
<%
int iCurrent = 0;
java.math.BigDecimal linetotal=new java.math.BigDecimal(0);
java.math.BigDecimal allsytotal=new java.math.BigDecimal(0);
java.math.BigDecimal alltaxtotal=new java.math.BigDecimal(0);
java.math.BigDecimal allamounttotal=new java.math.BigDecimal(0);
java.math.BigDecimal allinetotal=new java.math.BigDecimal(0);
java.math.BigDecimal abc = new java.math.BigDecimal(0);
java.math.BigDecimal sy_rate = new java.math.BigDecimal(0);


for (int i=0; i<list.size(); i++) {       
	iCurrent ++;
	Map map = (Map)list.get(i);
	linetotal=new java.math.BigDecimal(0);
		if(map.get("SY_MONEY")!=null)
			allsytotal=allsytotal.add((BigDecimal)map.get("SY_MONEY"));
		
		if(map.get("SY_AMOUNT")!=null)
			allamounttotal=allamounttotal.add((BigDecimal)map.get("SY_AMOUNT"));
			
		if(map.get("BOND_TAX")!=null)
		{
			linetotal=allsytotal.add((BigDecimal)map.get("BOND_TAX"));
			alltaxtotal=alltaxtotal.add((BigDecimal)map.get("BOND_TAX"));
		}
		if(alltaxtotal.floatValue()!=0)
		{
			linetotal=allsytotal.add(alltaxtotal);
		}
		if(alltaxtotal.floatValue()==0)
		{
			linetotal=allsytotal;
		}
		String rate = null;
		%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input class="flatcheckbox" type="checkbox" name="selectbox" value="<%=map.get("SERIAL_NO")%>"></td>
								<td width="90%">
									<p align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></p>
								</td>
							</tr>
						</table>
						</td>
						<td align="left" ><%=Utility.trimNull(map.get("SUB_PRODUCT_NAME")).equals("") ? Utility.trimNull(map.get("PRODUCT_NAME")) : Utility.trimNull(map.get("PRODUCT_NAME")) + "(" + Utility.trimNull(map.get("SUB_PRODUCT_NAME")) + ")"%></td>
						<td align="center" height="18"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
						<td align="right" height="18"><%=Format.formatMoney((BigDecimal)map.get("SY_AMOUNT"))%></td>
						<td align="center" height="18"><%=Utility.trimNull(map.get("PROV_LEVEL_NAME"))%></td>
						<td align="center" height="18"><%=Utility.trimNull(map.get("SY_TYPE_NAME"))%></td>
						<td align="right" >
							<%if((map.get("SY_AMOUNT")!=null && map.get("SY_MONEY")!=null && ((String)map.get("SY_TYPE")).equals("111602"))||(map.get("SY_AMOUNT")!=null && map.get("SY_MONEY")!=null && ((String)map.get("SY_TYPE")).equals("111603")))
							{
								if(((BigDecimal)map.get("SY_AMOUNT")).compareTo(new BigDecimal(0.0))!=0){
									rate = Format.formatMoney(((BigDecimal)map.get("SY_MONEY")).divide((BigDecimal)map.get("SY_AMOUNT"),6));
									out.print(rate);
							  	}
							}%>
							<input type="hidden" name="rate<%=map.get("SERIAL_NO")%>" value="<%=rate %>">
						</td>
						<td align="right" height="18"><%if(map.get("SY_MONEY")!=null && map.get("BOND_TAX")!=null ){%><%=Format.formatMoney(((BigDecimal)map.get("SY_MONEY")).add((BigDecimal)map.get("BOND_TAX")))%><%}else{%><%=Format.formatMoney((BigDecimal)map.get("SY_MONEY"))%><%}%></td>
						<td align="right" height="18"><%=Format.formatMoneyPrint(map.get("BOND_TAX") == null ? 0 : ((BigDecimal)map.get("BOND_TAX")).doubleValue(), 2)%></td>
						<td align="right" height="18"><%=Format.formatMoney((BigDecimal)map.get("SY_MONEY"))%></td>	
						
						<td align="center" height="18"><%=Format.formatDateCn((Integer)map.get("SY_DATE"))%></td>
						<td align="center" height="18"><%=Utility.trimNull(map.get("BANK_NAME"))%></td>	
						<td align="left" height="18"><%=Format.formatBankNo(Utility.trimNull(map.get("BANK_ACCT")))%></td>		
						<td>
                           <%if (input_operator.hasFunc(menu_id, 107)){%>
							<button type="button"  class="xpbutton2" accessKey=p name="btnRefresh" title="打印电汇凭证" onclick="javascript:printInfo('<%=map.get("SERIAL_NO")%>');">&gt;&gt;</button>	
						   <%}%>	
						</td>	
					</tr>
<%
}
for (; iCurrent < pageList.getPageSize(); iCurrent++) {%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" ></td>
						<td align="left" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="left" height="19"></td>
						<td align="left" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
					</tr>
					<%}%>

					<tr class="trbottom">
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<td align="left" height="19"></td>
						<td align="center" height="19"></td>
						<td align="right" height="19"><%=Format.formatMoney(allamounttotal)%></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="center" height="19"></td>
						<td align="right" height="19"><%=Format.formatMoneyPrint(linetotal == null ? 0 : linetotal.doubleValue(), 2)%></td>
						<td align="right" height="19"><%=Format.formatMoneyPrint(alltaxtotal == null ? 0 : alltaxtotal.doubleValue(), 2)%></td>
						<td align="right" height="19"><%=Format.formatMoneyPrint(allsytotal == null ? 0 : allsytotal.doubleValue(), 2)%></td>
						
						
						<td align="left" height="19"></td>
						<td align="left" height="19"></td>
						<td align="left" height="19"></td>
						<td align="left" height="19"></td>
					</tr>
				</table>
				<br>

				<table border="0" width="100%">
				<tr valign="top">
						
					</tr>
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
											
						<td align="right">
						
					
						</td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>