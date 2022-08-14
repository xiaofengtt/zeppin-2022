<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*" %>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
TcoContractMaintainLocal local = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO vo = new TcoContractMaintainVO();
TcoMaintainMoneyDetailLocal tcoMaintainMoneyDetailLocal=EJBFactory.getTcoMaintainMoneyDetail();
TcoMaintainMoneyDetailVO tcoMaintainMoneyDetailVO=new TcoMaintainMoneyDetailVO();
Integer record_id=Utility.parseInt(Utility.trimNull(request.getParameter("record_id")),new Integer(0)) ;
String cust_name=Utility.trimNull(request.getParameter("cust_name"));
String main_pro_name = Utility.trimNull(request.getParameter("main_pro_name"));
String contractMaintain_sub_bh = Utility.trimNull(request.getParameter("contractMaintain_sub_bh"));
String arriveMoney_flag=Utility.trimNull(request.getParameter("arriveMoney_flag"));
String start_date_begin = Utility.trimNull(request.getParameter("start_date_begin"));
String start_date_end = Utility.trimNull(request.getParameter("start_date_end"));
String end_date_begin = Utility.trimNull(request.getParameter("end_date_begin"));
String end_date_end = Utility.trimNull(request.getParameter("end_date_end"));
String collect_time_begin = Utility.trimNull(request.getParameter("collect_time_begin"));
String collect_time_end = Utility.trimNull(request.getParameter("collect_time_end"));
String invoice_time_begin = Utility.trimNull(request.getParameter("invoice_time_begin"));
String invoice_time_end = Utility.trimNull(request.getParameter("invoice_time_end"));
vo.setCust_name(cust_name);
vo.setMain_pro_name(main_pro_name);
vo.setCoContractMaintain_sub_bh(contractMaintain_sub_bh);
vo.setArriveMoney_flag(Utility.parseInt(arriveMoney_flag,new Integer(0)));
vo.setStart_date_begin(Utility.parseInt(start_date_begin,new Integer(0)));
vo.setStart_date_end(Utility.parseInt(start_date_end,new Integer(0)));
vo.setEnd_date_begin(Utility.parseInt(end_date_begin,new Integer(0)));
vo.setEnd_date_end(Utility.parseInt(end_date_end,new Integer(0)));
vo.setCollect_time_begin(Utility.parseInt(collect_time_begin,new Integer(0)));
vo.setCollect_time_end(Utility.parseInt(collect_time_end,new Integer(0)));
vo.setInvoice_time_begin(Utility.parseInt(invoice_time_begin,new Integer(0)));
vo.setInvoice_time_end(Utility.parseInt(invoice_time_end,new Integer(0)));
vo.setInput_man(input_operatorCode);
vo.setMaintain_id(record_id);
IPageList pageList = local.queryUnReceive(vo,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,1));

sUrl = sUrl+"&cust_name="+cust_name+ "&main_pro_name=" + main_pro_name+"&contractMaintain_sub_bh="+contractMaintain_sub_bh+"&arriveMoney_flag="+arriveMoney_flag
		+"&start_date_begin="+start_date_begin+ "&start_date_end="
		 + start_date_end+"&end_date_begin="+end_date_begin+"&end_date_end="+end_date_end
			+"&collect_time_begin="+collect_time_begin
					+"&collect_time_end="+collect_time_end
					+"&invoice_time_begin="+invoice_time_begin
					+"&invoice_time_end="+invoice_time_end;
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
function accountDetail(maintain_id)
{
	var ret = showModalDialog('tcoMaintainMoneyDetail_new.jsp?maintain_id='+maintain_id, '', 'dialogWidth:500px;dialogHeight:340px;status:0;help:0');
	if(ret != null)
	{
		sl_update_ok();
		refreshPage();	
	}
	disableAllBtn(false);
}
function invoice(maintain_id){
	var url = "<%=request.getContextPath()%>/contractManagement/tcoMaintainInvoice_new.jsp?maintain_id="+maintain_id;
	if(showModalDialog(url,'','dialogWidth:450px;dialogHeight:360px;status:0;help:0;') != null)
	{
		sl_alert("��Ʊ�ɹ���");
		refreshPage();	
	}
	disableAllBtn(false);	
}
function refreshPage()
{
	disableAllBtn(true);
	window.returnValue=1;
	window.close();
}

window.onload = function(){
//initQueryCondition();
}

function StartQuery()
{
	refreshPage();
}
/*�鿴��ϸ*/
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
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="contract_remove.jsp">
		<div id="queryCondition" class="qcMain" style="display:none;width:560px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   					<td align="right">
   						<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- Ҫ����Ĳ�ѯ���� -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right">�ͻ�����:</td><!--�ͻ�����-->
					<td><input type="text" name="cust_name" size="20" value="" onkeydown="javascript:nextKeyPress(this)"></td>
					<td align="right">ά����Ŀ����:</td><!--ά����Ŀ����-->
					<td><input type="text" name="main_pro_name" size="20" value="" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="right">ά����ͬ���:</td><!--ά����ͬ���-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="contractMaintain_sub_bh" size="20" value="">&nbsp;&nbsp;
					</td>
					<td align="right">���˱�־:</td><!--���˱�־-->
					<td>
						<select name="arriveMoney_flag"  onkeydown="javascript:nextKeyPress(this)">
							<option value="0">ȫ��</option>
							<option value="1">δ����</option>
							<option value="2">���ֵ���</option>
							<option value="3">�ѵ���</option>
						</select>
					</td>
				</tr>
					<tr>
					<td align="right">Ԥ���տ����ڴ�:</td>
					<td>
						<INPUT TYPE="text" NAME="collect_time_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.collect_time_begin_picker,theform.collect_time_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="collect_time_begin" id="collect_time_begin"   value="">
					</td>
					<td align="right">Ԥ���տ����ڵ�:</td>
					<td>
						<INPUT TYPE="text" NAME="collect_time_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.collect_time_end_picker,theform.collect_time_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="collect_time_end" id="collect_time_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">��ʼ���ڴ�:</td>
					<td>
						<INPUT TYPE="text" NAME="start_date_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_begin_picker,theform.start_date_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="start_date_begin" id="start_date_begin"   value="">
					</td>
					<td align="right">��ʼ���ڵ�:</td>
					<td>
						<INPUT TYPE="text" NAME="start_date_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_end_picker,theform.start_date_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="start_date_end" id="start_date_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">�������ڴ�:</td>
					<td>
						<INPUT TYPE="text" NAME="end_date_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_begin_picker,theform.end_date_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="end_date_begin" id="end_date_begin"   value="">
					</td>
					<td align="right">�������ڵ�:</td>
					<td>
						<INPUT TYPE="text" NAME="end_date_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_end_picker,theform.end_date_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="end_date_end" id="end_date_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="right">��Ʊ���ڴ�:</td>
					<td>
						<INPUT TYPE="text" NAME="invoice_time_begin_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.invoice_time_begin_picker,theform.invoice_time_begin_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="invoice_time_begin" id="invoice_time_begin"   value="">
					</td>
					<td align="right">��Ʊ���ڵ�:</td>
					<td>
						<INPUT TYPE="text" NAME="invoice_time_end_picker" class=selecttext value="" >
						<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.invoice_time_end_picker,theform.invoice_time_end_picker.value,this);" tabindex="15">
						<INPUT TYPE="hidden" NAME="invoice_time_end" id="invoice_time_end"   value="">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<button type="button" class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--ȷ��-->
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
							��ͬ���
						</td>
						<td width="">�ͻ�����</td>
						<td width="">��ʼ����</td>
						<td width="">��������</td>
						<td width="">Ԥ�Ƹ�������</td>
						<td width="">��Ʊ����</td>
						<td width="">��Ʊ���</td>
						<td width="">���ʱ�־</td>
						<td width="">���ʽ��</td>
						<td width="6%">��Ʊ</td><!--��Ʊ-->
						<td width="">������ϸ</td>
						<td width="">��ӵ���</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map=new HashMap();
int maintain_id=0;
int flag=0;
String invoice_money="";
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);
	maintain_id = Utility.parseInt(Utility.trimNull(map.get("MAINTAIN_ID")),0);
	flag= Utility.parseInt(Utility.trimNull(map.get("ARRIVEMONEY_FLAG")),0);
	invoice_money=Utility.trimNull(map.get("INVOICE_MONEY"));
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="">
						  <input type="checkbox" name="maintain_id" value="<%=maintain_id%>" class="flatcheckbox">
						  <%=Utility.trimNull(map.get("COCONTRACTMAINTAIN_SUB_BH")) %>
						</td> 
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
						<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0)))%></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("COLLECT_TIME")),new Integer(0)))%></td>
						<td align="right"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("INVOICE_TIME")),new Integer(0)))%></td>
						<td align="center"><%=invoice_money%></td>
						<td align="left"><%if(flag==1){%>δ����<%}else if(flag==2){%>���ֵ���<%}else if(flag==3){%>�ѵ���<%}%></td>
						<td align="left"><%=Utility.trimNull(map.get("ARRIVE_MONEY")) %></td>
						
						<td align="center">
							<%if((input_operator.hasFunc(menu_id, 102))&&(invoice_money.equals(""))){%>
							<a href="javascript:disableAllBtn(true);invoice(<%=maintain_id%>)">
				         		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
				      		</a>
				      		<%}%>	
						</td>
						<td align="center"> 
							<button type="button" class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=maintain_id%>);">
								<IMG id="activeImage<%=maintain_id%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9"> 
							</button>
							<input type="hidden" id="activeFlag_display<%=maintain_id%>" name="activeFlag_display<%=maintain_id%>" value="0">
						</td>
						<td align="center">
							<%if(flag!=3&&(!invoice_money.equals(""))){%>
							<a href="javascript:disableAllBtn(true);accountDetail(<%=maintain_id%>)">
				         		<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
				      		</a>
				      		<%} %>
						</td>
					</tr>
					<tr id="activeTr<%=maintain_id%>" style="display:none">
						<td align="center" bgcolor="#FFFFFF" colspan="12" >
							<div id="activeDiv<%=maintain_id%>" style="overflow-y:auto;" align="center">
								<table id="activeTablePro<%=maintain_id%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;">
										<td>����ʱ��</td>
										<td>���˽��</td>
										<td>��ע</td>
									</tr>
									<%
									tcoMaintainMoneyDetailVO.setMaintain_id(new Integer(maintain_id)); 
									tcoMaintainMoneyDetailVO.setMoneymx_id(new Integer(0));
									tcoMaintainMoneyDetailVO.setInput_man(input_operatorCode);
									List tcoMaintainMoneyDetailList=tcoMaintainMoneyDetailLocal.queryByList(tcoMaintainMoneyDetailVO);
									for(int j=0;j<tcoMaintainMoneyDetailList.size();j++){
										Map tcoMaintainMoneyDetailMap=(Map)tcoMaintainMoneyDetailList.get(j);
									 %>
									<tr style="background:F7F7F7;">
									
										<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(tcoMaintainMoneyDetailMap.get("ARRIVE_TIME")),new Integer(0)))%></td>
										<td><%=Utility.trimNull(tcoMaintainMoneyDetailMap.get("ARRIVE_MONEY"))%></td>
										<td><%=Utility.trimNull(tcoMaintainMoneyDetailMap.get("ARRIVE_SUMMARY"))%></td>
									</tr>
									<%}%>
								</table>
							</div>
						</td>
					</tr>  
<%}
%>
				</table>
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

