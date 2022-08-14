<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file = "/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
DocumentFile file = null;
String btnDisabled = "";
boolean bSuccess = false;
int flag = Utility.parseInt(request.getParameter("flag"),0);
String fiel_info = Utility.trimNull(request.getParameter("file_info"));
scaption = "处理数据";
String smessage = "上传成功！";

if (request.getMethod().equals("POST")) {
	file = new DocumentFile(pageContext);
	file.parseRequest();

	if (file.uploadFile("c:\\temp")) {
	    //excel文件的列必须是顺序的排序。例如有5列，应是A,B,C,D,E,不可是A,C,E,F,G.否则导进OLD表的数据不全
		if(file.readExcel("c:\\temp",3000,"OLD_CHANGE"))
			smessage="上传成功！";
	}
	if(file.insertContractRecord(input_operatorCode,input_bookCode,2))
		smessage="导入成功！";
	if(file.delOldData(2))
		smessage="删除成功！";
	if(file.correctOldData(input_operatorCode,input_bookCode,2)) 
		smessage="纠正结束！";		
	bSuccess = true;
}

ContractLocal contract = EJBFactory.getContract();

String product_name=Utility.trimNull(request.getParameter("product_name"));
String cust_name=Utility.trimNull(request.getParameter("cust_name"));
String query_contract_bh=Utility.trimNull(request.getParameter("query_contract_bh"));
//if(product_name!=""&&cust_name!=""&&query_contract_bh!="")

int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);
//contract.queryOldTemp(product_name,query_contract_bh,cust_name,2);
IPageList pageList = contract.queryOldTemp(product_name,query_contract_bh,cust_name,2, _page, pagesize);
sUrl = sUrl + "&cust_name="+cust_name+"&query_contract_bh="+query_contract_bh;
//contract.gotoPage(sPage, sPagesize);

contract.remove();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
	window.onload = function(){
			initQueryCondition()
		};
<%if (bSuccess) { %>
	sl_alert("<%=smessage%>");
	location="purchase_import_change.jsp";
<%} %>

function validateForm() {
	var filePath = document.getElementsByName("file_info")[0].value;
	if (filePath.length<=0) {
		sl_alert("请选择上传文件");
		return false;
	}

	if (confirm("确认要上传文件吗？")) {
		disableAllBtn(true);
		document.theform.inputflag.value=2;
		var strfilename=document.theform.file_info.value;
		document.theform.file_name.value=strfilename;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}	
	return false;
}

function save() {
	if(confirm("确认要导入数据吗？")) {
		disableAllBtn(true);
		document.theform.inputflag.value=1;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}	
}

function deleteOld() {
	if (confirm("确认要清除数据吗？")) {
		disableAllBtn(true);
		document.theform.inputflag.value=3;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}	
}

function correctOld() {
	if(confirm("确认要纠正历史数据吗？"))　
	{disableAllBtn(true);
		document.theform.inputflag.value=4;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}	
}

function StartQuery() {
    disableAllBtn(true);
	cust_name=document.theform.cust_name.value;
	query_contract_bh=document.theform.query_contract_bh.value;
	location="purchase_import_change.jsp?cust_name="+cust_name+"&query_contract_bh="+query_contract_bh+'&page=1&pagesize=' + document.theform.pagesize.value;
}

function refreshPage() {
	StartQuery();
}

function showInfo(product_name,contract_bh,cust_name) {
	location = 'purchase_import_detailed.jsp?cust_name='+cust_name+'&contract_bh='+contract_bh+'&product_name='+product_name+'&dealFlag=2';
}

function exportExcel() {
	var retval = showModalDialog('/marketing/sell/select_product.jsp?product_id=0', '','dialogWidth:500px;dialogHeight:120px;status:0;help:0');
	if (retval==null || retval==0) {
	
	} else { // retval>0
		setWaittingFlag(false);
		location.href = "export_purchase_import_change.jsp?op_code=<%=input_operatorCode%>&product_id="+retval;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>	
<form name="theform" method="post" action="purchase_import_change.jsp"  ENCTYPE="multipart/form-data">
<input type="hidden" name="inputflag" value="">
<input type="hidden" name="file_name" value="">
<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>			
	<tr>
		<td  align="right">
		客户名称:</td>
		<td  align="left">
		<input   name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="30">
		</td>
		<td  align="right">
		合同编号:</td>
		<td  align="left"><input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="10" value="<%=query_contract_bh%>"></td>
		</tr><tr>
		<td  align="center" colspan=4>
		<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定 (<u>O</u>)</button>
		</td>
	</tr>
</table>

</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file= "menu.inc"%>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="10">
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="1">
		<%//int  showTabInfo =  showTabInfoTop.intValue();
		// (showTabInfo != 1) { %>
					<tr>
						<td colspan="2" class="page-title"><b><%=menu_info%></b></td>
					</tr>
		<%//} %>
					<tr> <td align=right>
					<div class="btn-wrapper">
						销售数据:<input type="file" style="font-size:9pt" name="file_info" size="40"  onkeydown="javascript:return false;">&nbsp;
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
						<button type="button"  class="xpbutton2" accessKey=u  id="btnSave" name="btnSave" onclick="javascript:validateForm();">上传</button>
		<%}%>
						
					   
					    
					   <%//if (input_operator.hasFunc(menu_id, 102)){%>
						<!-- <button type="button"  class="xpbutton4" accessKey=c  id="btndete" name="btndete" onclick="javascript:correctOld();">纠正数据</button>&nbsp;&nbsp; -->
						<%//}%>
		<%if (input_operator.hasFunc(menu_id, 100)){%>
						<button type="button"  class="xpbutton4" id="btndete" name="btndete" onclick="javascript:deleteOld();">清除数据</button>&nbsp;&nbsp;						
						<button type="button"  class="xpbutton4"  id="btnSave" name="btnSave" onclick="javascript:save();">确认导入</button>&nbsp;&nbsp;						
		<%}%> 
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
						&nbsp;&nbsp;<button type="button"  class="xpbutton4" onclick="javascript:exportExcel();">导出</button>
		<%}%>			</div>
						<br/>
					    </td>
					</tr>
					

					<tr>			
					<td colspan=6>
					<table id="table3" border="0" cellspacing="1" cellpadding="1" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" >合同编号</td>
						<td align="center" >签订日期</td>
						<td align="center" >缴款日期</td>
						<td align="center" >合同期限(月)</td>
						<td align="center"  sort="num">认购金额</td>
						<td align="center" >委托人</td>
						<td align="center" >委托人证件号</td>
						<td align="center" >受益人</td>
						<td align="center" >受益人证件号</td>
						<td align="center" >开户银行</td>
						<td align="center" >银行帐号</td>
						<td align="center">明细</td>
					</tr>
<%
BigDecimal amount = new BigDecimal("0.00");
int iCount = 0;
int iCurrent = 0;
Integer serial_no;
	
List list = pageList.getRsList();
for (int i=0; i<list.size(); i++) {
//while (contract.getOldTempNext() && iCurrent < contract.getPageSize()) {	
	iCount++;
	iCurrent++;
	
	Map map = (Map)list.get(i);
	String _product_name = (String)map.get("项目名称");
	String contract_bh = (String)map.get("合同编号");

	
	BigDecimal rgMoney = Utility.parseDecimal(((String)map.get("合同金额")).trim(), new BigDecimal("0.00"));		
	//if (contract.getRg_money() != null)
	if (rgMoney != null)
		amount = amount.add(rgMoney);	

	String str_qs_date = (String)map.get("合同签订日期");
	String str_jk_date = (String)map.get("缴款日期");
	String str_valid_period = (String)map.get("合同期限");

	String _cust_name = (String)map.get("委托人");
	String card_id = (String)map.get("委托人证件编号");
	String benifitor_name = (String)map.get("受益人");
	String benifitor_card_id = (String)map.get("受益人证件编号");

	String bank_name = (String)map.get("开户银行名称");
	String bank_sub_name = (String)map.get("支行名称");
	String bank_acct = (String)map.get("银行账号");
%>

		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="center" >
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"></td>
					<td width="90%" align="left"><%=Utility.trimNull(contract_bh)%></td>
				</tr>
			</table>
			</td>
			<td align="center" ><%=Utility.trimNull(str_qs_date)%></td>
			<td align="center" ><%=Utility.trimNull(str_jk_date)%></td>
			<td align="right" ><%=Utility.trimNull(str_valid_period)%></td>
			<td align="right" ><%=Format.formatMoney(rgMoney)%></td>
			<td align="left" ><%=Utility.trimNull(_cust_name)%></td>
			<td align="left" ><%=Utility.trimNull(card_id)%></td>
			<td align="left" ><%=Utility.trimNull(benifitor_name)%></td>
			<td align="center" ><%=Utility.trimNull(benifitor_card_id)%></td>
			<td align="left" ><%=Utility.trimNull(bank_name)%><%=Utility.trimNull(bank_sub_name)%></td>
			<td align="center" ><%=Format.formatBankNo(Utility.trimNull(bank_acct))%></td>
			<td>
				<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:disableAllBtn(true);showInfo('<%=Utility.trimNull(_product_name)%>','<%=Utility.trimNull(contract_bh)%>','<%=Utility.trimNull(_cust_name)%>');">&gt;&gt;</button>
			</td>
		</tr>
<%}
for (; iCurrent < pageList.getPageSize(); iCurrent++) {%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="right" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
			<td align="center" ></td>
		</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="right" >-</td>
						<td align="right" ><%=Format.formatMoney(amount)%></td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
					</tr>
				</table>
				
					</td>
					</tr>					
				</table>
				<table border="0" width="100%">
					<tr>
						<td>
						<%=pageList.getPageLink(sUrl, clientLocale)%>
						</td>
					</tr>
					<tr>
						
					<td align="right">
						
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>