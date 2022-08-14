<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
TcoContractPayPlanLocal local = EJBFactory.getTcoContractPayPlan();
TcoContractPayPlanVO vo = new TcoContractPayPlanVO();
TcoContractMoneyDetailLocal tcoContractMoneyDetailLocal=EJBFactory.getTcoContractMoneyDetail();
TcoContractMoneyDetailVO tcoContractMoneyDetailVO=new TcoContractMoneyDetailVO();

String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
String pay_num_name = Utility.trimNull(request.getParameter("pay_num_name"));
String acct_name=Utility.trimNull(request.getParameter("acct_name"));
Integer exp_date_begin =Utility.parseInt(Utility.trimNull(request.getParameter("exp_date_begin")),new Integer(0)) ;
Integer exp_date_end = Utility.parseInt(Utility.trimNull(request.getParameter("exp_date_end")),new Integer(0));
Integer invoice_time_begin = Utility.parseInt(Utility.trimNull(request.getParameter("invoice_time_begin")),new Integer(0));
Integer invoice_time_end=Utility.parseInt(Utility.trimNull(request.getParameter("invoice_time_end")),new Integer(0));
Integer arriveMoney_flag = Utility.parseInt(Utility.trimNull(request.getParameter("arriveMoney_flag")),new Integer(0));

vo.setCust_name(cust_name);
vo.setCoContract_sub_bh(contract_bh);
vo.setPay_num_name(pay_num_name);
vo.setAcct_name(acct_name);
vo.setExp_date_begin(exp_date_begin);
vo.setExp_date_end(exp_date_end);
vo.setInvoice_time_begin(invoice_time_begin);
vo.setInvoice_time_end(invoice_time_end);
vo.setArriveMoney_flag(arriveMoney_flag);
vo.setInput_man(input_operatorCode);

String[] totalNum = {"PAY_MONEY", "INVOICE_MONEY", "ARRIVE_MONEY"};
IPageList pageList = local.queryUnReceive(vo,totalNum,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&cust_name=" + cust_name+"&contract_bh="+contract_bh+"&pay_num_name="+pay_num_name
			+ "&acct_name=" + acct_name+"&exp_date_begin="+exp_date_begin+"&exp_date_end="+exp_date_end
		    + "&invoice_time_begin=" + invoice_time_begin+"&invoice_time_end="+invoice_time_end+"&arriveMoney_flag="+arriveMoney_flag;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script language=javascript>
function accountDetail(payPlan_id)
{
	var ret = showModalDialog('tcoAccountDetail_new.jsp?payPlan_id='+payPlan_id, '', 'dialogWidth:650px;dialogHeight:440px;status:0;help:0');
	if(ret != null)
	{
		sl_update_ok();
		location.reload();
	}
	disableAllBtn(false);
}
function invoice(payPlan_id){
	var url = "<%=request.getContextPath()%>/contractManagement/tcoInvoice_new.jsp?payPlan_id="+payPlan_id;
	if(showModalDialog(url,'','dialogWidth:450px;dialogHeight:360px;status:0;help:0;') != null)
	{
		sl_alert("开票成功！");
		refreshPage();	
	}
	disableAllBtn(false);	
}
function refreshPage()
{
	disableAllBtn(true);
	syncDatePicker(document.theform.exp_date_begin_picker, document.theform.exp_date_begin);	
	syncDatePicker(document.theform.exp_date_end_picker, document.theform.exp_date_end);
	syncDatePicker(document.theform.invoice_time_begin_picker, document.theform.invoice_time_begin);
	syncDatePicker(document.theform.invoice_time_end_picker, document.theform.invoice_time_end);
	var v1=document.theform.cust_name.value
					var v2=document.theform.contract_bh.value
					var v3=document.theform.pay_num_name.value
					var v4=document.theform.acct_name.value
					var v5=document.theform.exp_date_begin.value
					var v6=document.theform.exp_date_end.value
					var v7=document.theform.invoice_time_begin.value
					var v8=document.theform.invoice_time_end.value
					var v9=document.theform.arriveMoney_flag.value;
	location = 'tcocontractcollecttrack.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
					+'&cust_name='+document.theform.cust_name.value
					+'&contract_bh='+document.theform.contract_bh.value
					+'&pay_num_name='+document.theform.pay_num_name.value
					+'&acct_name='+document.theform.acct_name.value
					+'&exp_date_begin='+document.theform.exp_date_begin.value
					+'&exp_date_end='+document.theform.exp_date_end.value
					+'&invoice_time_begin='+document.theform.invoice_time_begin.value
					+'&invoice_time_end='+document.theform.invoice_time_end.value
					+'&arriveMoney_flag='+document.theform.arriveMoney_flag.value;
}

window.onload = function(){
initQueryCondition();
}

function StartQuery()
{
	refreshPage();
}
/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "activeTr"+serial_no;
	var v_table = "activeTablePro"+serial_no;
	var v_flag = "activeFlag_display"+serial_no;
	var v_div = "activeDiv"+serial_no;
	var v_image = "activeImage"+serial_no;
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
function viewInfo(coContract_id)
{
	location = 'tcoContract_view.jsp?coContract_id='+coContract_id;
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="contract_remove.jsp">
		<div id="queryCondition" class="qcMain" style="display:none;width:560px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right">合同编号: </td><!--合同编号-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="contract_bh" size="15" value="">&nbsp;&nbsp;
					</td>
					<td align="right">客户名称:</td><!--客户名称-->
					<td><input type="text" name="cust_name" size="15" value="" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>	
					<td align="right">期数说明: </td><!--期数说明-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="pay_num_name" size="15" value="">&nbsp;&nbsp;
					</td>
					<td align="right">银行账户名称:</td><!--银行账户名称-->
					<td><input type="text" name="acct_name" size="15" value="" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="right">预计付款日期从:</td>
					<td>
						<INPUT TYPE="text" NAME="exp_date_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_date_begin_picker,theform.exp_date_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="exp_date_begin" id="exp_date_begin"   value="">
					</td>
					<td align="right">预计付款日期到:</td>
					<td>
						<INPUT TYPE="text" NAME="exp_date_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_date_end_picker,theform.exp_date_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="exp_date_end" id="exp_date_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">开票时间从:</td>
					<td>
						<INPUT TYPE="text" NAME="invoice_time_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.invoice_time_begin_picker,theform.invoice_time_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="invoice_time_begin" id="invoice_time_begin"   value="">
					</td>
					<td align="right">开票时间到:</td>
					<td>
						<INPUT TYPE="text" NAME="invoice_time_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.invoice_time_end_picker,theform.invoice_time_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="invoice_time_end" id="invoice_time_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">到账标志:</td><!--付款方式-->
					<td><select name="arriveMoney_flag"><option value="0">全部</option><option value="1">未到帐</option><option value="2">部分到帐</option><option value="3">已到帐</option></select></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<button type="button" class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>


	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button" class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td width="">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.contract_id);">
							合同编号
						</td>
						<td width="">客户名称</td><!--备注-->
						<td width="">期数</td>
						<td width="">金额</td>
						<td width="">预计付款日期</td>
						<td width="">开票日期</td>
						<td width="">开票金额</td>
						<td width="">到帐标志</td>
						<td width="">到帐金额</td>
						<td width="6%">开票</td><!--开票-->
						<td width="">到账明细</td>
						<td width="">添加到账</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map=new HashMap();
int coContract_id=0;
int flag=0;
String payPlan_id="";
String invoice_money="";
for(int i=0;i<list.size();i++,iCurrent++){
	map = (Map)list.get(i);
	coContract_id = Utility.parseInt(Utility.trimNull(map.get("COCONTRACT_ID")),0);
	flag= Utility.parseInt(Utility.trimNull(map.get("ARRIVEMONEY_FLAG")),0);
	payPlan_id=Utility.trimNull(map.get("PAYPLAN_ID"));
	invoice_money=Utility.trimNull(map.get("INVOICE_MONEY"));
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="">
						  <input type="checkbox" name="coCOntract_id" value="<%=coContract_id%>" class="flatcheckbox">
						  <a href="#" onclick="javascript:viewInfo(<%=coContract_id %>)">
						  <%=Utility.trimNull(map.get("COCONTRACT_SUB_BH")) %>
						  </a>
						</td> 
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
						<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("PAY_NUM")),new Integer(0))%></td>
						<td align="right"><%=Utility.trimNull(map.get("PAY_MONEY")) %></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("EXP_DATE")),new Integer(0)))%></td>
						<td align="right"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("INVOICE_TIME")),new Integer(0)))%></td>
						<td align="center"><%=invoice_money%></td>
						<td align="left"><%if(flag==1){%>未到帐<%}else if(flag==2){%>部分到账<%}else if(flag==3){%>已到账<%}%></td>
						<td align="left"><%=Utility.trimNull(map.get("ARRIVE_MONEY")) %></td>
						
						<td align="center">
							<%if((input_operator.hasFunc(menu_id, 102))&&(invoice_money.equals(""))){%>
							<a href="javascript:disableAllBtn(true);invoice(<%=payPlan_id%>)">
				         		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
				      		</a>
				      		<%}%>	
						</td>
						<td align="center"> 
							<button type="button" class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=payPlan_id%>);">
								<IMG id="activeImage<%=payPlan_id%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9"> 
							</button>
							<input type="hidden" id="activeFlag_display<%=payPlan_id%>" name="activeFlag_display<%=payPlan_id%>" value="0">
						</td>
						<td align="center">
							<%if(flag!=3&&(!invoice_money.equals(""))){%>
							<a href="javascript:disableAllBtn(true);accountDetail(<%=payPlan_id%>)">
				         		<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
				      		</a>
				      		<%} %>
						</td>
					</tr>
					<tr id="activeTr<%=payPlan_id%>" style="display:none">
						<td align="center" bgcolor="#FFFFFF" colspan="11" >
							<div id="activeDiv<%=payPlan_id%>" style="overflow-y:auto;" align="center">
								<table id="activeTablePro<%=payPlan_id%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;">
										<td>到账时间</td>
										<td>到账金额</td>
										<td>备注</td>
									</tr>
									<%
									tcoContractMoneyDetailVO.setPayPlan_id(Utility.parseInt(payPlan_id,new Integer(0)));
									tcoContractMoneyDetailVO.setMoneymx_id(new Integer(0));
									tcoContractMoneyDetailVO.setInput_man(input_operatorCode);
									List tcoContractMoneyDetailList=tcoContractMoneyDetailLocal.queryByList(tcoContractMoneyDetailVO);
									for(int j=0;j<tcoContractMoneyDetailList.size();j++){
										Map tcoContractMoneyDetailMap=(Map)tcoContractMoneyDetailList.get(j);
									 %>
									<tr style="background:F7F7F7;">
									
										<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(tcoContractMoneyDetailMap.get("ARRIVE_TIME")),new Integer(0)))%></td>
										<td><%=Utility.trimNull(tcoContractMoneyDetailMap.get("ARRIVE_MONEY"))%></td>
										<td><%=Utility.trimNull(tcoContractMoneyDetailMap.get("ARRIVE_SUMMARY"))%></td>
									</tr>
									<%}%>
								</table>
							</div>
						</td>
					</tr>  
<%}
for (int i=0;i < pageList.getBlankSize(); i++){
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
						<td></td>
						<td></td>
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left">
							<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
						</td>
						<td></td>
						<td></td>
						<td align="right"><%=Format.formatMoney(pageList.getTotalValue("PAY_MONEY")) %></td>
						<td></td>
						<td></td>
						<td align="right"><%=Format.formatMoney(pageList.getTotalValue("INVOICE_MONEY")) %></td>
						<td></td>
						<td align="right"><%=Format.formatMoney(pageList.getTotalValue("ARRIVE_MONEY")) %></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<div>
					<%=pageList.getPageLink(sUrl,clientLocale)%>
				</div>
				<table border="0" width="100%">
					<tr valign="top">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>
<div>
	
</div>				
</form>

</BODY>
</HTML>
<%local.remove();%>

