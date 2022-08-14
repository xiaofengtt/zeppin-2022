<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//���ҳ�洫�ݱ���
Integer q_nonproductId = Utility.parseInt(Utility.trimNull(request.getParameter("q_nonproductId")),new Integer(0));		//���
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));									//�����в�Ʒ����
String q_investmentManager = Utility.trimNull(request.getParameter("q_investmentManager"));								//Ͷ�ʹ�����
String q_partnerCustName = Utility.trimNull(request.getParameter("q_partnerCustName"));									//�ϻ�����ҵID
String q_status = Utility.trimNull(request.getParameter("q_status"));													//״̬
Integer q_checkFlag = Utility.parseInt(Utility.trimNull(request.getParameter("q_checkFlag")),new Integer(1));			//��˱�־1δ���2�����

//ҳ�渨������
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

//��ö��󼰽����
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
<!--�����в�Ʒ���-->
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
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ��");//��ѡ��Ҫ��˵ļ�¼
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
/*�鿴��ϸ*/
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
                        <!-- ��� --><!-- ���ͨ�� -->
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
						</td><!--���-->
						<td><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
						<td><%=LocalUtilis.language("class.toward",clientLocale)%> </td><!--����Ͷ��-->
						<td><%=LocalUtilis.language("class.partners",clientLocale)%> </td><!--�ϻ�����Ϣ���������в�Ʒ�ͻ���-->
						<td><%=LocalUtilis.language("class.periodUnit",clientLocale)%> </td><!--��Ʒ���ϻ���ҵ������-->
						<td><%=LocalUtilis.language("class.productExpRate",clientLocale)%> </td><!--Ԥ��������-->
						<td><%=LocalUtilis.language("class.expectMoney",clientLocale)%> </td><!--Ԥ�ڲ�Ʒ���-->
						<td><%=LocalUtilis.language("class.factMoney",clientLocale)%> </td><!--ʵ�ʲ�Ʒ���-->
						<td><%=LocalUtilis.language("class.investmentManager",clientLocale)%> </td><!--Ͷ�ʹ�����-->
						<td align="center" width="50px"><%=LocalUtilis.language("message.check",clientLocale)%> </td><!--���-->
					</tr>
<%
//��������
String q_investment_direction = "";	        //Ͷ��
String q_investment_direction_name = "";	//Ͷ������
Integer q_valid_priod_unit = new Integer(0);			//���޵�λ��1��2��3��4��9��
Integer q_valid_priod = new Integer(0);				//����
BigDecimal q_expectMoney = new BigDecimal(0);
BigDecimal q_expect_rate1 = new BigDecimal(0);			//Ԥ������1
BigDecimal q_expect_rate2 = new BigDecimal(0);			//Ԥ������2
BigDecimal q_face_money = new BigDecimal(0);		//ʵ�ʲ�Ʒ���
Integer q_partner_cust_id = new Integer(0);			//�ϻ�����ҵID��TCustomers.CUST_ID��

String q_custName = "";             		//����
String q_cardType = "";              	//֤������
String q_cardId = "";                	//֤������
String q_cust_tel = "";              	//��ϵ�绰
String q_postAddress = "";           	//��ϵ��ַ

list = pageList.getRsList();

for(int i=0;i<list.size(); i++)
{
	//������
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
	
	//��ѯ�ͻ���Ϣ
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
										<td class="text-right"><%=LocalUtilis.language("message.customer",clientLocale)%> ��</td><!--�ϻ���-->
										<td  width="*"><%=q_custName %></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> ��</td><!--֤������\֤������-->
										<td  width="*"><%=q_cardType%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> ��</td><!--֤������-->
										<td  width="*"><%=q_cardId %></td>
									</tr>
									<tr style="background:F7F7F7;">
										<td  width="120px" class="text-right"><%=LocalUtilis.language("login.telephone",clientLocale)%> ��</td><!--��ϵ�绰-->
										<td  width="*"><%=q_cust_tel %></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td  width="120px" class="text-right"><%=LocalUtilis.language("class.postAddress3",clientLocale)%> ��</td><!--ͨѶ��ַ-->
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
						<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
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

