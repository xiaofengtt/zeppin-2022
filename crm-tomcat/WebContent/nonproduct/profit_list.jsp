<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer q_serialNo = Utility.parseInt(request.getParameter("q_serialNo"),new Integer(0));
Integer q_nonproductId = Utility.parseInt(request.getParameter("q_nonproductId"),new Integer(0));
String q_nonproductName = Argument.getNonProductNameByCustId(Utility.parseInt(request.getParameter("q_nonproductName"),new Integer(0)));
String q_custName = Utility.trimNull(request.getParameter("q_custName"));
String q_subscribeBh = Utility.trimNull(request.getParameter("q_subscribeBh"));
Integer q_syDate = Utility.parseInt(request.getParameter("q_syDate"),new Integer(0));
Integer q_custId = Utility.parseInt(request.getParameter("q_custId"),new Integer(0));
String q_status = Utility.trimNull(request.getParameter("q_status"));
Integer q_checkFlag = Utility.parseInt(request.getParameter("q_checkFlag"),new Integer(1));

if(q_status.equals("")){
	q_status = "111602";
}

//页面辅助参数
String tempUrl = "";
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
Map map = null;
List custlist = null;
Map custmap =null;
Iterator iterator = null;

//url设置
sUrl = sUrl + "&q_serialNo=" +q_serialNo
            + "q_nonproductId=" +q_nonproductId
            + "q_nonproductName=" +q_nonproductName
            + "q_custName=" +q_custName
            + "q_subscribeBh=" +q_subscribeBh
            + "q_syDate=" +q_syDate
            + "q_custId=" +q_custId
            + "q_status=" +q_status
            + "q_checkFlag=" +q_checkFlag;

//获得对象及结果集
ProfitLocal profit = EJBFactory.getProfitLocal();
QuotientAffirmVO vo = new QuotientAffirmVO();
CustomerVO custvo = new CustomerVO();
CustomerLocal cust = EJBFactory.getCustomer();

vo.setSerial_no(q_serialNo);
vo.setNonproduct_id(q_nonproductId);
vo.setNonproduct_name(q_nonproductName);
vo.setCust_name(q_custName);
vo.setSubscribe_bh(q_subscribeBh);
vo.setSy_date(q_syDate);
vo.setCheck_flag(q_checkFlag);
vo.setStatus(q_status);
IPageList pageList = profit.query(vo,totalColumn,input_operatorCode,t_sPage,t_sPagesize);
list = pageList.getRsList();


%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.nonProductSet",clientLocale)%> </TITLE><!--收益-->
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
initQueryCondition()};

/*查询功能*/
function StartQuery(){
	refreshPage();
}


/*添加*/
function newInfo()
{	
	disableAllBtn(true);
	location = 'profit_new.jsp'
}

/*修改*/
function showInfo(q_serialNo,q_custId,q_subscribeId){
	disableAllBtn(true);
	location = 'profit_update.jsp?q_serialNo='+q_serialNo+'&q_custId='+q_custId+'&q_subscribeId='+q_subscribeId;
}

/*修改*/
function quotientAffirm(){
	disableAllBtn(true);
	location = 'profit_cal.jsp'
}

/*删除功能*/
function removeInfo()
{
	if(checkedCount(document.getElementsByName("q_serialNo")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	if(sl_check_remove()){			
		var url = 'profit_remove.jsp';
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	return false;
}

/*刷新*/
function refreshPage(){
	syncDatePicker(document.theform.q_syDate_picker, document.theform.q_syDate);
	disableAllBtn(true);		
	var url = "profit_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_nonproductId=' + document.theform.q_nonproductId.value;
	var url = url + '&q_nonproductName=' + document.theform.q_nonproductName.value;
	var url = url + '&q_custName=' + document.theform.q_custName.value;
	var url = url + '&q_subscribeBh=' + document.theform.q_subscribeBh.value;
	var url = url + '&q_syDate=' + document.theform.q_syDate.value;
	var url = url + '&q_status=' + document.theform.q_status.value;
	var url = url + '&q_checkFlag=' + document.theform.q_checkFlag.value;
	
	location = url;	
}

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get"  action="profit_list.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:580px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
  	 <button class="qcClose" accessKey=c id="qcClose" name="qcClose"   onclick="javascript:cancelQuery();"></button>
   </td>
  </tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
		<td align="left">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_nonproductId" size="20" value="<%=q_nonproductId%>">
		</td>
		<td  align="right">	<%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
		<td align=left>
			<select style="WIDTH:180px" size="1" name="q_nonproductName" onkeydown="javascript:nextKeyPress(this)" class="q_nonproductName">
				<!--请选择-->
                   <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option>
				<%=Argument.getNonproductName(q_nonproductId) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td colspan="4">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_custName" size="62" value="<%=q_custName%>">
		</td>
	</tr>
	<tr>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.incomeDistributionDate",clientLocale)%> :</td><!--收益分配日期-->
		<td>
		<input TYPE="text" style="width:120" NAME="q_syDate_picker" class=selecttext value="<%=Format.formatDateLine(q_syDate)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.q_syDate_picker,theform.q_syDate_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="q_syDate" value="<%=q_syDate%>">	</td>
		</td>
		<td align="right">&nbsp;&nbsp;<%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td align="left">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_subscribeBh" size="20" value="<%=q_subscribeBh%>">
		</td>
	</tr>
	<tr>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> :</td><!--状态-->
		<td  align="left" >
			<select size="1" name="q_status" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 170px">
				<%=Argument.getProfitStatusOptions(q_status)%>
			</select>
		</td>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="q_checkFlag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getCheckFlagOptions(q_checkFlag)%>
			</select>
		</td>
	</tr>
	<tr>						
		<td  align="center" colspan=4>
		<button class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->
(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)<!--查询-->
		</button>&nbsp;&nbsp;&nbsp; 	
		<%if(input_operator.hasFunc(menu_id, 100)){%> 		
		<button type="button" class="xpbutton3" accessKey=n id="btnNew"  name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
		&nbsp;&nbsp;&nbsp;<!--新建-->
		<%}%>	
		<button type="button" class="xpbutton3" id="calButton" name="calButton"  onclick="javascript:quotientAffirm()"><%=LocalUtilis.language("message.calculation",clientLocale)%> <!--计算-->
		</button>&nbsp;&nbsp;&nbsp; 
		<%if(input_operator.hasFunc(menu_id, 101)){%> 
		<button type="button" class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:removeInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<!--删除所选记录--><!--删除-->
		<%}%>
	</div>
	<br/>
</div>
<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<!--合同编号-->
        <td align="center" width="*">
	        <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.q_serialNo,this);">
	        <%=LocalUtilis.language("class.productName",clientLocale)%> 
	    </td><!--产品名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--购买合同编号-->
		<td align="center" width="*"><%=LocalUtilis.language("class.ben_amount",clientLocale)%> </td><!-- 收益金额 -->
		<td align="center" width="*"><%=LocalUtilis.language("class.incomeDistributionDate",clientLocale)%></td><!-- 收益分配日期-->
		<td align="center" width="*"><%=LocalUtilis.language("class.syTypeName",clientLocale)%></td><!-- 收益类别 -->
		<td align="center" width="*"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--银行支行名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%></td><!-- 帐号 -->
		<td align="center" width="60px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		<td width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
	</tr>
<%
//声明变量
Integer q_subscribeId = new Integer(0);
BigDecimal q_profitMoney;
String q_syType;
String q_syTypeName;
String q_bankId;
String q_bankName;
String q_bankSubName;
String q_bankAcct;
BigDecimal total_money = new BigDecimal(0);
iterator = list.iterator();

while(iterator.hasNext()){
	map = (Map)iterator.next();
	q_serialNo = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	q_subscribeId = Utility.parseInt(Utility.trimNull(map.get("SUBSCRIBE_ID")),new Integer(0));
	q_custId = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	q_nonproductId = Utility.parseInt(Utility.trimNull(map.get("NONPRODUCT_ID")),new Integer(0));
	q_nonproductName = Utility.trimNull(map.get("NONPRODUCT_NAME"));
	q_subscribeBh = Utility.trimNull(map.get("SUBSCRIBE_BH"));
	q_profitMoney = Utility.parseDecimal(Utility.trimNull(map.get("PROFIT_MONEY")),new BigDecimal(0),2,"1");
	q_syDate = Utility.parseInt(Utility.trimNull(map.get("SY_DATE")),new Integer(0));
	q_syType = Utility.trimNull(map.get("SY_TYPE"));
	q_syTypeName = Utility.trimNull(map.get("SY_TYPE_NAME"));
	q_bankId = Utility.trimNull(map.get("BANK_ID"));
	q_bankName = Utility.trimNull(map.get("BANK_NAME"));
	q_bankSubName = Utility.trimNull(map.get("BANK_SUB_NAME"));
	q_bankAcct = Utility.trimNull(map.get("BANK_ACCT"));
	total_money = total_money.add(q_profitMoney);
	//查询客户信息
	if(q_custId.intValue() != 0){
		custvo.setCust_id(q_custId);
		custvo.setInput_man(new Integer(888));
		custlist = cust.listCustomerLoad(custvo);
		custmap = (Map)custlist.get(0);

		if(custmap!= null){
		    q_custName = Utility.trimNull(custmap.get("CUST_NAME"));
		}
	}
	iCurrent++;
	iCount++;
%>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td align="left">
			<input type="checkbox" name="q_serialNo" value="<%=q_serialNo%>" class="flatcheckbox">
			<input type="hidden" name="q_custId" value=<%=q_custId%>>
			<input type="hidden" name="q_subscribeId" value=<%=q_subscribeId%>>
			<%=q_nonproductName%>
		</td>
		<td align="center"><%=q_custName%></td>
		<td align="center"><%=q_subscribeBh%></td>
		<td align="right"><%=Format.formatMoney(q_profitMoney)%></td>
		<td align="center"><%=q_syDate%>&nbsp;&nbsp;</td>
		<td align="center"><%=q_syTypeName%>&nbsp;&nbsp;</td>
		<td align="center"><%=q_bankName%>&nbsp;&nbsp;</td>
		<td align="center"><%=q_bankSubName%>&nbsp;&nbsp;</td>
		<td align="center"><%=q_bankAcct%>&nbsp;&nbsp;</td>
		<td align="center">
			<%if(input_operator.hasFunc(menu_id, 102)){%>
			<a href="#" onclick="javascript:showInfo(<%=q_serialNo%>,<%=q_custId %>,<%=q_subscribeId %>)">
				<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--份额确认-->
			</a>
			<%}%>
		</td>		
		<td align="center">
			<%if(input_operator.hasFunc(menu_id, 101)){%>
			<a href="#" onclick="javascript:removeInfo()">
				<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
			</a>
			<%}%>
		</td>
	</tr>
<%
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
     <tr class="tr<%=(i % 2)%>">
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>  
         <td align="center"></td>  
         <td align="center"></td>  
         <td align="center"></td>  
         <td align="center"></td>  
      </tr>           
<%}%>   	
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="3"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		<td align="right"><%=Format.formatMoney(total_money)%></td>
		<td align="center" colspan="8"></td>  
	</tr>	
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%profit.remove();
cust.remove();
%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
