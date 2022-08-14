<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String q_contract_sub_bh = Utility.trimNull(request.getParameter("q_contract_sub_bh"));
String q_cust_no = Utility.trimNull(request.getParameter("q_cust_no"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_productCode = Utility.trimNull(request.getParameter("q_productCode"));
String q_product_name=Utility.trimNull(request.getParameter("q_product_name"));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),overall_product_id);
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"),new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));
//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
String[] totalColumn = new String[0];
List rsList = null;
Map map = null;

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&q_cust_no="+q_cust_no;
tempUrl = tempUrl+"&q_contract_sub_bh="+q_contract_sub_bh+"&begin_date="+begin_date+"&end_date="+end_date;
sUrl = sUrl + tempUrl;

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo_ben = new BenifitorVO();

vo_ben.setBook_code(input_bookCode);
vo_ben.setContract_sub_bh(q_contract_sub_bh);
vo_ben.setCust_no(q_cust_no);
vo_ben.setSerial_no(new Integer(0));
vo_ben.setFunction_id(new Integer(100));
vo_ben.setInput_man(input_operatorCode);
vo_ben.setProduct_id(q_productId);
vo_ben.setProduct_name(q_product_name);
vo_ben.setCust_name(q_cust_name);

vo_ben.setStart_date(begin_date);
vo_ben.setEnd_date(end_date); 

IPageList pageList  = new JdbcPageList();
int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
if(first_flag != 1){
	pageList = benifitor.queryModi(vo_ben,totalColumn,t_sPage,t_sPagesize);
	rsList = pageList.getRsList();
	}else{
	rsList = new ArrayList();
	}

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.accountChangeList",clientLocale)%> </TITLE>
<!--受益权账户变更-->
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
/*启动加载*/
window.onload = function(){
	initQueryCondition();
}

/*查询功能*/
function StartQuery(){
	disableAllBtn(true);	
	syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);	
	var url = "account_change_list.jsp?pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_contract_sub_bh=' + document.theform.q_contract_sub_bh.value;	
	var url = url + '&q_cust_no=' + document.theform.q_cust_no.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;	
	var url = url + '&q_product_name=' + document.theform.q_product_name.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + "&begin_date=" + document.theform.begin_date.value;
	var url = url + "&end_date=" + document.theform.end_date.value;
	
	location = url;	
}

/*刷新功能*/
function refreshPage(){	
	StartQuery();
}

/*编辑*/
function showInfo(serialno,product_id){
	var url = 'account_change_edit.jsp?serial_no='+serialno+'&product_id='+product_id;
	
	if(showModalDialog(url, '', 'dialogWidth:750px;dialogHeight:600px;status:0;help:0') != null){
		sl_update_ok();
		refreshPage();
	}
}

/**************************************************************************************************************/

/*设置产品*/
function setProduct(value){
	var prodid=0;
	
	if(event.keyCode == 13 && value != ""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！");//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

/*搜索产品*/
function searchProduct(value){
	if(event.keyCode == 13){
		searchProduct2(value);
	}
}
/*搜索产品2*/
function searchProduct2(value){
	var prodid=0;
	if( value != ""){
        var j = value.length;
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.indexOf(value) >= 0)
			{
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productId.options[0].selected=true;
		}
	}
	
}

/*导出受益人信息Excel*/
function exportExcel()
{
	if(sl_confirm("您确认要导出数据"))//确认要导出数据吗
	{
		syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
		syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
		setWaittingFlag(false);
	    document.theform.method = "post";
		document.theform.action = "download_benefic.jsp";
	    document.theform.submit();
	}
	
}

function batchEdit(){
	var url = "<%=request.getContextPath()%>/marketing/sell/account_change_batch_edit.jsp?filename=bacthedit.CLL&q_contract_sub_bh=" + document.theform.q_contract_sub_bh.value;	
	var url = url + '&q_cust_no=' + document.theform.q_cust_no.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;	
	var url = url + '&q_product_name=' + document.theform.q_product_name.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + "&begin_date=" + document.theform.begin_date.value;
	var url = url + "&end_date=" + document.theform.end_date.value;
	//location = url;
	if(showModalDialog(url, '', 'dialogWidth:1024px;dialogHeight:600px;status:0;help:0;resizable:yes') != null)
	{
		sl_alert("修改成功！");
		location = location;
	}
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="get">
<INPUT type="hidden" name="input_operatorCode" value="input_operatorCode"/>
<div id="queryCondition" class="qcMain" style="display:none;width:520px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"  onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
<tr>
	<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
	<td align="left">
		<input name="q_contract_sub_bh"  onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(q_contract_sub_bh)%>">
	</td>
	<td align="right"><%=LocalUtilis.language("class.custNO",clientLocale)%> :</td><!--受益人编号-->
	<td align="left" >
		<input  onkeydown="javascript:nextKeyPress(this)" name="q_cust_no" value="<%=Utility.trimNull(q_cust_no)%>">
	</td>
</tr>
<tr>
	<td align="right"><%=LocalUtilis.language("class.qCustName",clientLocale)%> :</td><!--受益人姓名-->
	<td align="left" >
		<input  onkeydown="javascript:nextKeyPress(this)" name="q_cust_name" value="<%=Utility.trimNull(q_cust_name)%>">
	</td>
	<td align="right">产品编码 :</td >
	<td align="left">
		<input type="text" maxlength="16" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:searchProduct(this.value);"  maxlength=8 size="10">&nbsp;
		<button type="button"  class="searchbutton"  onclick="javascript:searchProduct2(document.theform.productcode.value);" /></button>
	</td >
</tr>
<tr>
	<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--产品选择-->
	<td align="left" colspan=3>
		<SELECT name="q_productId" onkeydown="javascript:nextKeyPress(this)" class="productname">			
			<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
		</SELECT>
	</td>
</tr>
<tr> 
	<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td ><!--产品名称-->
	<td align="left" colspan=3>
		<INPUT type="text" name="q_product_name" size="35" onkeydown="javascript:nextKeyPress(this)" value=<%=q_product_name%>>
	</td>
	<td align="left"></td>
</tr>
<tr>
	<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> :</td><!--开始日期-->
	<td>
		<input TYPE="text" style="width:120" NAME="begin_date_picker" class=selecttext value="<%=Format.formatDateLine(begin_date)%>" onKeyDown="javascript:nextKeyPress(this)">
      <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)">
      <input TYPE="hidden" NAME="begin_date" value="">	</td>
	<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--结束日期-->
	<td>
		<input TYPE="text" style="width:120" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)">
      <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)">
      <input TYPE="hidden" NAME="end_date" value="">	</td>
</tr>
<tr>
	<td align="center" colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button></td>
</tr><!--确认-->
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b>
	</div>
	<div align="right" class="btn-wrapper">
		<%if(user_id.intValue() == 2){%>
		<button type="button"  class="xpbutton5" id="exportButton" name="exportButton" onclick="javascript:exportExcel();">导出受益人信息</button>&nbsp;&nbsp;&nbsp; 
		<%}%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" name="batchEdit1" onclick="javascript:batchEdit();">批量编辑</button> 						
	</div><!--查询-->
	<br/>
</div>

<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			<td align="center" ><%=LocalUtilis.language("class.custName2",clientLocale)%> </td><!--受益人名称-->
			<td align="center" ><%=LocalUtilis.language("class.cardID",clientLocale)%> </td><!--证件号-->
			<td align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
			<td align="center" ><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
			<td align="center" ><%=LocalUtilis.language("class.bankSubName2",clientLocale)%> </td><!--付款银行名称-->
			<td align="center" ><%=LocalUtilis.language("class.bankAcct2",clientLocale)%> </td><!--付款银行账号-->
			<td align="center" ><%=LocalUtilis.language("class.custAcctName2",clientLocale)%> </td><!--银行账户名称-->
			<td align="center" >修改时间</td>
			<td align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
			<!--td align="center" >打印</td-->
		</tr>
<%
//声明显示参数
Integer serial_no = new Integer(0);
Integer product_id = new Integer(0);
String cust_name = "";
String card_id = "";
String product_name ="";
String contract_sub_bh = "";
String list_id = "";
String bank_name ="";
String bank_sub_name ="";
String bank_acct ="";
String cust_acct_name ="";

Iterator iterator = rsList.iterator();


while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0));
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0));
	Integer modi_bank_date = Utility.parseInt(Utility.trimNull(map.get("MODI_BANK_DATE")), new Integer(0));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	card_id = Utility.trimNull(map.get("CARD_ID"));
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	list_id = Utility.trimNull(map.get("LIST_ID"));
	bank_name = Utility.trimNull(map.get("BANK_NAME"));
	bank_sub_name =  Utility.trimNull(map.get("BANK_SUB_NAME"));
	bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
	cust_acct_name = Utility.trimNull(map.get("CUST_ACCT_NAME"));
%>
	<tr class="tr<%=(iCount % 2)%>">
		<td class="tdh" align="left"><%=cust_name%></td>
		<td align="left" ><%=card_id%></td>
		<td align="left" ><%=product_name%></td>
		<td align="center" ><%=contract_sub_bh%> - <%=list_id%></td>
		<td  ><%=bank_name%><%=bank_sub_name%></td>
		<td align="center" ><%=bank_acct%></td>
		<td align="center" ><%=cust_acct_name%></td>
		<td align="center" ><%=Format.formatDateLine(modi_bank_date)%></td>
		<td align="center" >
			<%if (input_operator.hasFunc(menu_id, 102)){%>				
				<a href="javascript:showInfo(<%=serial_no%>,'<%=product_id%>');">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="absmiddle" width="16" height="16">
               	</a>
			<%}%>				
		</td>
	</tr>		
<%}%>	

<%
for(int i=0;i<(t_sPagesize-iCount);i++){
%>
		<tr class="tr<%=(i % 2)%>">
			<td class="tdh" align="center" ></td>
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
			<td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			
		</tr>
	</table>
</div>

<br>
<div  class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<% benifitor.remove();%>
</form>
</BODY>
</HTML>