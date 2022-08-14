<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得页面传递变量
Integer q_nonproductId = Utility.parseInt(Utility.trimNull(request.getParameter("q_nonproductId")),new Integer(0));		//编号
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));									//非信托产品名称
String q_investmentManager = Utility.trimNull(request.getParameter("q_investmentManager"));								//投资管理人
String q_partnerCustName = Utility.trimNull(request.getParameter("q_partnerCustName"));									//合伙人企业ID
String q_status = Utility.trimNull(request.getParameter("q_status"));													//状态
Integer q_checkFlag = Utility.parseInt(Utility.trimNull(request.getParameter("q_checkFlag")),new Integer(1));			//审核标志1未审核2已审核

//页面辅助参数
boolean bSuccess = false;
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;
IPageList pageList = null;

//获得对象及结果集
NonProductVO vo = new NonProductVO();
NonProductLocal nonproduct = EJBFactory.getNonProductLocal();
CustomerVO custvo = new CustomerVO();
CustomerLocal cust = EJBFactory.getCustomer();

vo.setNonproduct_id(Utility.parseInt(q_nonproductId, new Integer(0)));
vo.setNonproduct_name(Utility.trimNull(q_nonproductName));
vo.setInvestment_manager(Utility.trimNull(q_investmentManager));
vo.setPartner_cust_name(q_partnerCustName);
vo.setStatus(Utility.trimNull(q_status));
vo.setCheck_flag(Utility.parseInt(q_checkFlag, new Integer(1)));
pageList = nonproduct.query(vo, totalColumn, input_operatorCode, t_sPage, t_sPagesize);

sUrl = sUrl+ "&q_nonproductId =" +q_nonproductId+ "&q_nonproductName =" +q_nonproductName 
			+ "&q_investmentManager =" +q_investmentManager
			+ "&q_partnerCustName =" +q_partnerCustName
			+ "&q_status =" +q_status
			+ "&q_checkFlage =" +q_checkFlag;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.nonProductSet",clientLocale)%> </TITLE>
<!--非信托产品审核-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
function showInfo(q_nonproductId,q_partner_cust_id)
{
	disableAllBtn(true);
	location = 'nonproduct_check_list.jsp?q_nonproductId=' + q_nonproductId+ '&cust_id=' +q_partner_cust_id;
}
function op_check()
{
	if(checkedCount(document.theform.q_nonproductId) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	if (sl_check_pass()){
	disableAllBtn(true);
	var url = 'nonproduct_check_action.jsp';
	document.getElementsByName("theform")[0].setAttribute("action",url);
	document.getElementsByName("theform")[0].submit();	
	return true;
	}
}

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);

	location = 'noproduct_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&q_nonproductId=' + document.theform.q_nonproductId.value
							+ '&q_nonproductName=' + document.theform.q_nonproductName.value
							+ '&q_investmentManager=' + document.theform.q_investmentManager.value
							+ '&q_partnerCustName=' + document.theform.q_partnerCustName.value
							+ '&q_status=' + document.theform.q_status.value
							+ '&q_checkFlag=' + document.theform.q_checkFlag.value;
}

window.onload = function(){
//initQueryCondition()};
}

function StartQuery()
{
	refreshPage();
}
/*查看明细*/
function setiteminfor(q_nonproductId){
	var v_tr =  "activeTr"+q_nonproductId;
	var v_table = "activeTablePro"+q_nonproductId;
	var v_flag = "activeFlag_display"+q_nonproductId;
	var v_div = "activeDiv"+q_nonproductId;
	var v_image = "activeImage"+q_nonproductId;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}
</script>
<BODY class="BODY body-nox" >
<form name="theform" method="POST" action="noproduct_check.jsp">
<input type="hidden" name="q_nonproductId" value=<%=q_nonproductId%>>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title">			
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
						<%if (input_operator.hasFunc(menu_id, 103)){%>
                        <!-- 审核 --><!-- 审核通过 -->
						<button type="button" class="xpbutton4" name="btnCheck" id="btnCheck" title='<%=LocalUtilis.language("class.auditBy2",clientLocale)%>' onclick="javascript:op_check();"><%=LocalUtilis.language("class.auditBy2",clientLocale)%> </button> 
						<%}%>
						</div>
						<br/>
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td width="50px">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.q_nonproductId,this);">
							<%=LocalUtilis.language("class.ID",clientLocale)%> 
						</td><!--编号-->
						<td><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
						<td><%=LocalUtilis.language("class.toward",clientLocale)%> </td><!--运作投向-->
						<td><%=LocalUtilis.language("class.partners",clientLocale)%> </td><!--合伙人信息（即非信托产品客户）-->
						<td><%=LocalUtilis.language("class.periodUnit",clientLocale)%> </td><!--产品（合伙企业）期限-->
						<td><%=LocalUtilis.language("class.productExpRate",clientLocale)%> </td><!--预期收益率-->
						<td><%=LocalUtilis.language("class.expectMoney",clientLocale)%> </td><!--预期产品金额-->
						<td><%=LocalUtilis.language("class.factMoney",clientLocale)%> </td><!--实际产品金额-->
						<td><%=LocalUtilis.language("class.investmentManager",clientLocale)%> </td><!--投资管理人-->
						<td align="center" width="50px"><%=LocalUtilis.language("message.check",clientLocale)%> </td><!--审核-->
					</tr>
<%
//声明参数
String q_investment_direction = "";	        //投向
String q_investment_direction_name = "";	//投向名称
Integer q_valid_priod_unit = new Integer(0);			//期限单位：1天2月3季4年9无
Integer q_valid_priod = new Integer(0);				//期限
BigDecimal q_expectMoney = new BigDecimal(0);
BigDecimal q_expect_rate1 = new BigDecimal(0);			//预期收益1
BigDecimal q_expect_rate2 = new BigDecimal(0);			//预期收益2
BigDecimal q_face_money = new BigDecimal(0);		//实际产品金额
Integer q_partner_cust_id = new Integer(0);			//合伙人企业ID（TCustomers.CUST_ID）

String q_custName = "";             		//名称
String q_cardType = "";              	//证件类型
String q_cardId = "";                	//证件号码
String q_cust_tel = "";              	//联系电话
String q_postAddress = "";           	//联系地址

list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{
	//读数据
    map = (Map)list.get(i);
	q_nonproductId = Utility.parseInt(Utility.trimNull(map.get("NONPRODUCT_ID")),
			new Integer(0));
	q_nonproductName = Utility.trimNull(map.get("NONPRODUCT_NAME"));
	q_investmentManager = Utility.trimNull(map.get("INVESTMENT_MANAGER"));
	q_investment_direction = Utility.trimNull(map.get("INVESTMENT_DIRECTION"));
	q_investment_direction_name = Utility.trimNull(map.get("INVESTMENT_DIRECTION_NAME"));
	q_valid_priod_unit = Utility.parseInt(Utility.trimNull(map.get("VALID_PRIOD_UNIT")),
			new Integer(0));
	q_valid_priod = Utility.parseInt(Utility.trimNull(map.get("VALID_PRIOD")),
			new Integer(0));
	q_expectMoney = Utility.parseDecimal(Utility.trimNull(map.get("EXPECT_MONEY")),new BigDecimal(0));
	q_expect_rate1 = Utility.parseDecimal(Utility.trimNull(map.get("EXPECT_RATE1")),
			new BigDecimal(0), 4, "100");
	q_expect_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXPECT_RATE2")),
			new BigDecimal(0), 4, "100");
	q_face_money = Utility.parseDecimal(Utility.trimNull(map.get("FACE_MONEY")),new BigDecimal(0));
	q_partner_cust_id = Utility.parseInt(Utility.trimNull(map.get("PARTNER_CUST_ID")),
			new Integer(0));
	
	//查询客户信息
	if(q_partner_cust_id.intValue() != 0){
		custvo.setCust_id(q_partner_cust_id);
		custvo.setInput_man(input_operatorCode);
		custlist = cust.listCustomerLoad(custvo);
		custmap = (Map)custlist.get(0);

		if(custmap!= null){
		    q_custName = Utility.trimNull(custmap.get("CUST_NAME"));
		    q_cust_tel = Utility.trimNull(custmap.get("CUST_TEL"));
		    q_cardType = Utility.trimNull(custmap.get("CARD_TYPE"));
		    q_cardId = Utility.trimNull(custmap.get("CARD_ID"));
		    q_postAddress = Utility.trimNull(custmap.get("POST_ADDRESS"));
		}
	}
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="50px">
						  <input type="checkbox" name="q_nonproductId" value="<%=q_nonproductId%>" class="flatcheckbox">
						</td>
						<td align="left">&nbsp;&nbsp;<%=q_nonproductName%></td>
						<td align="left">&nbsp;&nbsp;<%=q_investment_direction_name %></td>
						<td align="center">
			         	<button type="button" class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=q_partner_cust_id%>);">
			         		<IMG id="activeImage<%=q_partner_cust_id%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
			         	</button>
			         	<input type="hidden" id="activeFlag_display<%=q_partner_cust_id%>" name="activeFlag_display<%=q_partner_cust_id%>" value="0">
			         	</td>  
						<td align="left">&nbsp;&nbsp;<%=q_valid_priod %>&nbsp;<%=Argument.getValidPriodUnit(q_valid_priod_unit) %></td>
						<td align="left">&nbsp;&nbsp;<%=q_expect_rate1 %>%-<%=q_expect_rate2 %>%</td>
						<td align="right">&nbsp;&nbsp;<%=Format.formatMoney(q_expectMoney)%></td>
						<td align="right">&nbsp;&nbsp;<%=Format.formatMoney(q_face_money)%></td>
						<td align="left">&nbsp;&nbsp;<%=q_investmentManager %></td>
						<td align="center" >
							<a href="javascript:disableAllBtn(true);showInfo(<%=q_nonproductId%>,<%=q_partner_cust_id %>)">
		         				<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
		      				</a>
						</td>
					</tr>
					<tr id="activeTr<%=q_partner_cust_id%>" style="display: none">
						<td align="center" bgcolor="#FFFFFF" colspan="17" >
							<div id="activeDiv<%=q_partner_cust_id%>" style="overflow-y:auto;" align="center">
								<table id="activeTablePro<%=q_partner_cust_id%>" border="0" width="100%" bgcolor="#DEEFF7" cellspacing="1">
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("message.customer",clientLocale)%> ：</td><!--合伙人-->
										<td  width="*"><%=q_custName %></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> ：</td><!--证件类型\证件号码-->
										<td  width="*"><%=q_cardType%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> ：</td><!--证件号码-->
										<td  width="*"><%=q_cardId %></td>
									</tr>
									<tr style="background:F7F7F7;">
										<td  width="120px" class="text-right"><%=LocalUtilis.language("login.telephone",clientLocale)%> ：</td><!--联系电话-->
										<td  width="*"><%=q_cust_tel %></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td  width="120px" class="text-right"><%=LocalUtilis.language("class.postAddress3",clientLocale)%> ：</td><!--通讯地址-->
										<td  width="*"><%=q_postAddress %></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>  
<%
iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
</BODY>
</HTML>
<%
nonproduct.remove();
cust.remove();
%>

