<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.customer.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
String qPreproductName = Utility.trimNull(request.getParameter("qPreproductName"));
Integer qPreproductId = Utility.parseInt(request.getParameter("qPreproductId"), new Integer(0));
String qCustName = Utility.trimNull(request.getParameter("qCustName"));
String qContractSubBh = Utility.trimNull(request.getParameter("qContractSubBh"));
Integer qQsDate1 = Utility.parseInt(request.getParameter("qQsDate1"), new Integer(0));
Integer qQsDate2 = Utility.parseInt(request.getParameter("qQsDate2"), new Integer(0));
Integer qStatus =Utility.parseInt(request.getParameter("qStatus"), new Integer(0));//默认 全部

// 切换当前页号用到
sUrl += "&qContractSubBh=" + qContractSubBh;
sUrl += "&qPreproductName="+ qPreproductName;
sUrl += "&qPreproductId="+qPreproductId;
sUrl += "&qCustName="+qCustName;
sUrl += "&qStatus="+qStatus;
sUrl += "&qQsDate1="+qQsDate1;
sUrl += "&qQsDate2="+qQsDate2;

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

//获得对象及结果集
ContractLocal contract = EJBFactory.getContract();
ContractUnrealVO vo = new ContractUnrealVO();

vo.setContractSubBh(qContractSubBh);
vo.setPreproductName(qPreproductName);
vo.setPreproductId(qPreproductId);
vo.setCustName(qCustName);
vo.setQsDate1(qQsDate1);
vo.setQsDate2(qQsDate2);
vo.setStatus(qStatus);
vo.setInputMan(input_operatorCode);

IPageList pageList  = contract.queryContractUnreal(vo,t_sPage,t_sPagesize);
List list = pageList.getRsList();
%>

<HTML>
<HEAD>
<TITLE>非正式合同录入</TITLE>
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language=javascript>
/*启动加载*/
window.onload = function(){
		initQueryCondition();
	};

function getQueryParams(){
	var s = "";
	s += "qContractSubBh="+document.theform.qContractSubBh.value;
	s += "&qPreproductName="+document.theform.qPreproductName.value;
	s += "&qPreproductId="+document.theform.qPreproductId.value;
	s += "&qCustName="+document.theform.qCustName.value;
	s += "&qStatus="+document.theform.qStatus.value;
	syncDatePicker(document.theform.q_start_date_picker,document.theform.qQsDate1);	
	syncDatePicker(document.theform.q_start_date_picker2,document.theform.qQsDate2);
	s += "&qQsDate1="+document.theform.qQsDate1.value;
	s += "&qQsDate2="+document.theform.qQsDate2.value;
	return s;
}

function getPageParams(page, pagesize) {
	var s = "";
	if (page) 
		s += "page="+page;
	else 
		s += "page="+document.theform.page_list.value;

	if (pagesize) 
		s += "&pagesize="+pagesize;
	else 
		s += "&pagesize="+document.theform.pagesize.value;
	return s;
}

/*查询功能*/
function startQuery(){
	disableAllBtn(true);
	refreshPage();
}

function refreshPage() {		
	location.href = "sales_contract_unreal.jsp?"+ getPageParams(1) + '&'+ getQueryParams();
}

/*新增*/
function newInfo(){
	disableAllBtn(true);
	location.href = 'sales_contract_unreal_add.jsp?'+ getPageParams(1) + '&'+ getQueryParams();
}

/*修改*/
function edit(serialNo) {
	disableAllBtn(true);
	location.href = 'sales_contract_unreal_edit.jsp?serialNo='+serialNo+'&' + getPageParams() + '&'+ getQueryParams();
}

/*删除功能*/
function del() {
	if (confirmRemove(document.theform.serialNo)) {
		disableAllBtn(true);
		location.href = "sales_contract_unreal_delete.jsp?serialNos="+checkedValue(document.theform.serialNo) + "&"+ getPageParams() + '&'+ getQueryParams();
	}
}

function convert() {
	if (sl_confirm("转换所有非正式合同")) {
		disableAllBtn(true);
		location.href = "sales_contract_unreal_convert.jsp?preproductId=0&"+ getPageParams() + '&'+ getQueryParams();
	}
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
  	 <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"   onclick="javascript:cancelQuery();"></button>
   </td>
  </tr>
</table>

<table width="99%">
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td>
		<td colspan="3">
			<input type="text" name="qPreproductName" size="40" value="<%=qPreproductName%>"/>
		</td>
	</tr>
	<tr>
		<td  align="right">产品:</td>
		<td colspan="3">
			<select size="1" name="qPreproductId"	onkeydown="javascript:nextKeyPress(this)" class="productname">			
				<%=Argument.getCRMPreproductListOptions(qPreproductId, "", "110202", input_operatorCode)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--客户名称-->
		<td colspan="3"><input name="qCustName" value="<%=qCustName%>" onkeydown="javascript:nextKeyPress(this);" size="25"/></td>
	</tr>
	<tr>
		<td  align="right">合同实际编号:</td>
		<td  align="left">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="qContractSubBh" size="25" value="<%=qContractSubBh%>">
		</td>
		<td  align="right">合同状态:</td>
		<td  align="left">
			<select size="1" name="qStatus" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getContractUnrealStatusOptions(qStatus)%>
			</select>
		</td>	
	</tr>
	<tr>
		<td  align="right">签署日期从:</td>
		<td  align="left" >
			<input type="text" name="q_start_date_picker"  class=selecttext value="<%=Format.formatDateLine(qQsDate1)%>"/>&nbsp;&nbsp;
			<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
			<input TYPE="hidden" name="qQsDate1" id="qQsDate1" value=""/>	
		</td>	
		<td  align="right">到:</td>
		<td  align="left" >
			<input type="text" name="q_start_date_picker2"  class=selecttext value="<%=Format.formatDateLine(qQsDate2)%>"/>&nbsp;&nbsp;
			<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker2,theform.q_start_date_picker2.value,this);" tabindex="13">
			<input TYPE="hidden" name="qQsDate2" id="qQsDate2" value=""/>	
		</td>
	</tr>
	<tr>
		<td  align="center" colspan=4>
		<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:startQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->
(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align=right>
				<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)<!--查询--></button>&nbsp;&nbsp;&nbsp;

				<%if (input_operator.hasFunc(menu_id, 100)) {%> <!--新建-->
		        <button type="button"  class="xpbutton3" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.new",clientLocale)%>' onclick="javascript:newInfo();">
					<%=LocalUtilis.language("message.new",clientLocale)%>(<u>N</u>)</button>&nbsp;&nbsp;&nbsp;
				<%}%>
				<%--if (input_operator.hasFunc(menu_id, 101)) {%>
				<!-- 删除 -->
		        <button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title='<%=LocalUtilis.language("message.delete",clientLocale)%>'	onclick="javascript:del();">
					<%=LocalUtilis.language("message.delete",clientLocale)%>(<u>D</u>)</button>&nbsp;&nbsp;&nbsp;
				<%}--%>	
				<%if (input_operator.hasFunc(menu_id, 120)) {%>
		        <button type="button"  class="xpbutton6" accessKey=c name="btnConvert" title='转成正式认购合同'	onclick="javascript:convert()">
					全部转成正式合同(<u>C</u>)</button>
				<%}%>	
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<hr noshade color="#808080" size="1">
			</td>
		</tr>
	</table>
	
</div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="98%" <%if(user_id.intValue()==16){ %>style="table-layout:fixed"<%} %>>
	<tr class=trh>
		<td align="center" width="280<%--330--%>px"><%--input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serialNo,this);"--%>
			预发行产品名称
		</td>
		<td align="center" width="180px">合同实际编号</td>		
		<td align="center" width="70px">签署日期</td>
		<td align="center" width="70px">缴款日期</td>
		<td align="center" width="120px">认购金额</td>
		<td align="center" width="120px">客户名称</td>
		<td align="center" width="100px">状态</td>
		<td align="center" width="35px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
	</tr>
<%
int iCount = 0;
for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);
	Long serialNo = Utility.parseLong((Long)map.get("SERIAL_NO"), new Long(-1));
	String statusName = "";
	Integer _status = (Integer)map.get("STATUS");
	if (_status.intValue()==1) {
		statusName = "未转正式认购";
	} else {
		statusName = "已转认购";
	}
%>
        <tr class="tr<%=(iCount % 2)%>">
			<%--td class="tdh" align="center" height="48">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="5%" align="left"><input type="checkbox" name="serialNo" value="<%=serialNo%>" class="flatcheckbox"></td>
						<td width="95%" align="left"><%=map.get("PREPRODUCT_NAME")%></td>
					</tr>
				</table>
			</td--%>

			<td align="left"><%=map.get("PREPRODUCT_NAME")%></td>
			<td align="left"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
			<td align="center"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
			<td align="center"><%=Format.formatDateLine((Integer)map.get("JK_DATE"))%></td>
			<td align="right"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>		
			<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>	
			<td align="center"><%=statusName%></td>	
			<td align="center">
				<%if (_status.intValue()==2) { %>
					<a href="javascript:edit(<%=serialNo%>);">
		           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
		           	 </a> </a>	
				<%} else if (input_operator.hasFunc(menu_id, 102)) {%>
	               	 <!--<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<!%=serial_no%>,<!%=check_flag%>,<!%=ht_status%>,<!%=pre_flag%>);">&gt;&gt;</button>-->
		             <a href="javascript:edit(<%=serialNo%>);">
		           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
		           	 </a> </a>
				<%}%>
			</td>
		</tr>
<%	
	iCount++;
}
int page_size = pageList.getPageSize();
for (; iCount < page_size; iCount++) { %>
<tr class="tr<%=(iCount % 2)%>">
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
</tr>
<% } %>
</table>
<br>
<br>
<div>
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%contract.remove();%>
