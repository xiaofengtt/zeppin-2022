<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.intrust.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得打印参数
String scust_id = request.getParameter("cust_id");
String [] cust_items = Utility.splitString(scust_id, ",");
Integer startdate = Utility.parseInt(Utility.trimNull(request.getParameter("startdate")), new Integer(0));
Integer enddate = Utility.parseInt(Utility.trimNull(request.getParameter("enddate")), new Integer(0));

CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//页面辅助变量
Integer cust_id = new Integer(0);
List list = new ArrayList();
Map map = new HashMap();
List ben_list = new ArrayList();
Map ben_map = new HashMap();
String period_unit="";

%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY class="BODY" topmargin="8" rightmargin="8"
	onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" action="#" method="post">
<%
if(cust_items != null && cust_items.length > 0)
{
	for(int i=0; i<cust_items.length; i++){
		cust_id = Utility.parseInt(cust_items[i], new Integer(0));
		if(!(cust_id.equals(new Integer(0))))
		{
			vo.setCust_id(cust_id);//循环获得客户ID的相关信息
			list = local.listProcAll(vo);
			Iterator it = list.iterator();
			while(it.hasNext()){
				map = (Map)it.next();
			%>
			<table <%if(i!=0){out.print("style='page-break-before:always'");}%> border="0" width="100%" cellspacing="0" cellpadding="4" height="20%">
				
			<tr>		
				<td width="6%" align="right" height="8%"></td>
				<td width="40%" align="left" height="3%"><font size="3" face="宋体"><b><%=Utility.trimNull(map.get("POST_CODE"))%></b></font></td>
				<td width="15%"  align="left" height="3%">&nbsp;</td>
				<td width="45%"  align="left" height="3%">&nbsp;</td>
			</tr>
			<tr>		
				<td width="6%" align="right" height="8%"></td>
				<td width="40%" align="left" height="3%"><font size="3" face="宋体"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></font></td>
				<td width="15%"  align="left" height="3%">&nbsp;</td>
				<td width="49%" align="left" height="3%" rowspan="3">
				<FONT size="1"></FONT></td>
			</tr>
			<tr>		
				<td width="6%" align="right" height="8%"></td>
				<td width="40%" align="left" height="3%"><font size="3" face="宋体"><b><%=Utility.trimNull(map.get("CUST_NAME"))%></b>&nbsp; 
				<!--如果是个人-->
				<%if(Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0)).equals(new Integer(1)))
				{	
					if(Utility.parseInt(Utility.trimNull(map.get("SEX")),new Integer(0)).equals(new Integer(1))) out.print("先生"); else out.print("女士");
				}%> &nbsp;&nbsp;&nbsp;台启</font></td>
				<td width="15%"  align="left" height="3%">&nbsp;</td>
			</tr>
			<tr >
				<td colspan=4>
					<!--strat 受益人详细--> 
					<table id="table3" border="0" cellspacing="1" cellpadding="2" bgcolor="#000000" width="100%">
					<tr class="trh">
						<td align="left"  width="106">产品名称</td>
						<td align="left" width="104">合同编号</td>
						<td align="right" width="99">存量金额</td>
						<td align="center" width="116">成立日</td>
						<td align="center" width="74">受益<br>期限</td>
						<td align="center" width="114">开户银行</td>
						<td align="center"   width="75">帐号</td>		
					</tr>
					<%
					BigDecimal amount = new BigDecimal("0.000");
					BigDecimal to_amount = new BigDecimal("0.000");
					int iCount = 0;
					int iCurrent = 0;
					Integer serial_no=new Integer(0);
					BenifitorLocal ben_local = EJBFactory.getBenifitor();//受益人
					BenifitorVO ben_vo = new BenifitorVO();
					ContractLocal contract_local = EJBFactory.getContract();//合同
					ContractVO contract_vo = new ContractVO();
					ben_vo.setBook_code(new Integer(1));
					ben_vo.setCust_id(cust_id);
					ben_vo.setInput_man(input_operatorCode);
					ben_list = ben_local.query(ben_vo);
					Iterator ben_it = ben_list.iterator();
					String transferName="";
					while(ben_it.hasNext())
					{
						ben_map = (Map)ben_it.next();
						transferName="未转让";
						if(Utility.trimNull(ben_map.get("TRANSFER_FLAG")) != "")
						{
							if(Utility.parseInt(Utility.trimNull(ben_map.get("TRANSFER_FLAG")), new Integer(0)).intValue()==2)
								transferName="已转让";
						}
						contract_vo.setProduct_id(Utility.parseInt(Utility.trimNull(ben_map.get("PRODUCT_ID")), new Integer(0)));
						contract_vo.setContract_bh(Utility.trimNull(ben_map.get("CONTRACT_BH")));
						contract_vo.setProduct_code(null);
						contract_vo.setCheck_flag(new Integer(0));
						contract_vo.setCust_id(cust_id);
						contract_vo.setInput_man(input_operatorCode);
						//获得合同信息
						List contract_list = contract_local.queryContract(contract_vo);
						Map contract_map = new HashMap();
						String ht_status="";
						String check_name="";
						if(contract_list != null && contract_list.size() > 0)
						{	
							contract_map = (Map)contract_list.get(0);
							ht_status = Utility.trimNull(contract_map.get("HT_STATUS_NAME"));
							check_name = Utility.trimNull(contract_map.get("CHECK_FLAG_NAME"));
							serial_no = Utility.parseInt(Utility.trimNull(contract_map.get("SERIAL_NO")), new Integer(0));
						}
						if(ben_map.get("BEN_AMOUNT") != null)
							amount = amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT"))));	
						if(ben_map.get("REMAIN_AMOUNT") != null)
						{
							to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("REMAIN_AMOUNT"))));	
							period_unit=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(ben_map.get("PERIOD_UNIT")), new Integer(0)));//获得租赁类型
						}
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" width="106"><%=Utility.trimNull(ben_map.get("PRODUCT_NAME"))%></td>
						<td align="left" width="104"><%=Utility.trimNull(ben_map.get("CONTRACT_BH"))%></td>
						<td align="right" width="99"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT")))))%></td>
						<td align="center" width="116"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0)))%></td>
						<td align="center" width="74"><%=Utility.trimNull(ben_map.get("VALID_PERIOD"))%><%=period_unit%></td>
						<td align="center" width="114"><%=Utility.trimNull(ben_map.get("BANK_NAME"))%></td>
						<td align="center" width="75"><%=Utility.trimNull(ben_map.get("BANK_ACCT"))%></td>
					</tr>
					<%
					iCurrent++;
					iCount++;
					}
					local.remove();
					ben_local.remove();
					%>
					<tr class="trbottom">
						<td class="tdh" align="center" height="25" width="106"><b>合计 <%=iCount%> 项</b></td>
						<td align="center" width="104">-</td>
						<td align="right" width="99"><%=Format.formatMoney(amount)%></td>
						<td align="center" width="116"><%=Format.formatMoney(to_amount)%></td>
						<td align="center" width="74">-</td>
						<td align="center" width="114">-</td>
						<td align="center" width="75">-</td>
					</tr>
					</table>
					<!--end 受益人详细--> 		
				</td>
			</tr>
			</table>
			<!--start 收益分配明细表--> 	
			<table  border="1" width="100%" cellspacing="0" cellpadding="4">
			<tr>
				<td align="center" colspan="6"><font size="3"><b>收益分配明细表</b></font></td>		
			</tr>	
			<tr>
				<td  align="center" width="159">项目名称</td>
				<td  align="center" width="105">受益金额</td>
				<td  align="center" width="79">年收益率(%)</td>
				<td  align="center" width="103">返还本金</td>
				<td  align="center" width="129">分配收益额</td>
				<td  align="center" width="125">分配日期</td>
			</tr>	
			<%
			local.remove();
			DeployLocal deploy_local = EJBFactory.getDeploy();
			DeployVO deploy_vo = new DeployVO();
			deploy_vo.setCust_id(cust_id);
			deploy_vo.setStartdate(startdate);
			deploy_vo.setEnddate(enddate);
			List deploy_list = deploy_local.querycust(deploy_vo);
			Map deploy_map = new HashMap();
			Iterator deploy_it = deploy_list.iterator();
			while(deploy_it.hasNext()){
				deploy_map = (Map)deploy_it.next();
			%>
			<tr>
				<td align="left" width="159"><%=Utility.trimNull(deploy_map.get("PRODUCT_NAME"))%>&nbsp;</td>
				<td align="right" width="105"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(deploy_map.get("SY_AMOUNT"))))%>&nbsp;</td>
        		<td align="left" width="79"><%=Utility.trimNull(Utility.stringToDouble(Utility.trimNull(deploy_map.get("RATE"))).multiply(new BigDecimal(100).setScale(3, BigDecimal.ROUND_HALF_UP)))%>&nbsp;</td>
        		<td align="left" width="103"><%=(Utility.trimNull(deploy_map.get("SY_TYPE")).equals("111605")?Format.formatMoney(Utility.stringToDouble(Utility.trimNull(deploy_map.get("SY_AMOUNT")))):"0")%>&nbsp;</td>
        		<td align="left" width="129"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(deploy_map.get("SY_AMOUNT"))))%>&nbsp;</td>
        		<td align="left" width="125"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(deploy_map.get("SY_DATE")), new Integer(0)))%>&nbsp;</td>
			</tr>
			<%
			}
			%>
			</table>
			<!--end 收益分配明细表--> 
			<br>		
			<%}
		}
	}
}
%>
<br>
<table border="0" width="100%" cellspacing="1" cellpadding="4" id=table99>
	<tr>
		<td>
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="打印" onclick="javascript:table99.style.display = 'none';window.print();table99.style.display = '';">打印(<u>P</u>)</button>	 
					&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();">返回(<u>B</u>)</button>
					&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</HTML>
