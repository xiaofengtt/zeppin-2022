<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_pre_code = Utility.trimNull(request.getParameter("q_pre_code"));
Integer q_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_cust_type")),new Integer(0));
BigDecimal min_reg_money = Utility.parseDecimal(request.getParameter("min_reg_money"), new BigDecimal(0),2,"1");//最低登记额度
BigDecimal max_reg_money = Utility.parseDecimal(request.getParameter("max_reg_money"), new BigDecimal(0),2,"1");//最高登记额度
String q_customer_cust_source =  Utility.trimNull(request.getParameter("q_customer_cust_source"));//客户来源
Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_class_detail_id")),new Integer(0));
Integer q_pre_money_class = Utility.parseInt(request.getParameter("q_pre_money_class"), new Integer(0));
int import_flag = Utility.parseInt(request.getParameter("import_flag"),-1);
Integer import_date = Utility.parseInt(request.getParameter("import_date"),new Integer(Utility.getCurrentDate()));

boolean bSuccess = false;
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;

//帐套暂时设置
input_bookCode = new Integer(1);

sUrl += "&q_cust_name="+q_cust_name;
sUrl += "&q_pre_code="+q_pre_code;
sUrl += "&q_cust_type="+q_cust_type;
sUrl += "&min_reg_money="+min_reg_money;
sUrl += "&max_reg_money="+max_reg_money;
sUrl += "&q_customer_cust_source="+q_customer_cust_source;
sUrl += "&q_class_detail_id="+q_class_detail_id;
sUrl += "&q_pre_money_class="+q_pre_money_class;

PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO vo = new PreContractVO();

vo.setCust_name(q_cust_name);
vo.setPre_code(q_pre_code);
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setCust_type(q_cust_type);
vo.setMax_reg_money(max_reg_money);
vo.setMin_reg_money(min_reg_money);
vo.setCust_source(q_customer_cust_source);
vo.setClassdetail_id(q_class_detail_id);

IPageList pageList = new JdbcPageList();
List list = new ArrayList();
if (Argument.getSyscontrolValue("PRECONTRACT_CHECK")==1) {
	pageList = preContract.preContract_page_query_crm(vo,totalColumn,t_sPage,t_sPagesize);
	list = pageList.getRsList();
}

boolean print_flag = ! list.isEmpty();

preContract.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakList",clientLocale)%> </TITLE><!--预约审核列表-->
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
var bNeedCheck = <%=Argument.getSyscontrolValue("PRECONTRACT_CHECK")%>==1;

/*启动加载*/
<%if(bSuccess){%>
	sl_alert("预约数据导入成功!");
<%}%>

window.onload = function(){
		initQueryCondition();
		if (! bNeedCheck) {
			sl_alert("系统当前设置为不需要对预约进行审核！");
		}
	};

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*审核*/
function showInfo(serial_no){
	disableAllBtn(true);
	location="bespeak_check.jsp?serial_no="+ serial_no;
}

function op_check()
{
	if(checkedCount(document.theform.serial_no) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();}
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
		
	var url = "bespeak_check_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	url += '&q_cust_name=' + document.theform.q_cust_name.value;
	url += '&q_pre_code=' + document.theform.q_pre_code.value;
	url += '&q_cust_type=' +document.theform.q_cust_type.value;
	url += '&q_customer_cust_source=' + document.theform.q_customer_cust_source.value;
	location.href = url;	
}

/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "detailsTr"+serial_no;
	var v_table = "detailsTable"+serial_no;
	var v_flag = "detailsFlag_display"+serial_no;
	var v_div = "detailsDiv"+serial_no;
	var v_image = "detailsImage"+serial_no;
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

function sortTable__2() {
	var start = new Date;
	event.cancelBubble = true;
	var the_obj = event.srcElement;
	if (the_obj.tagName != "TD")
		return;

	var tab = this.parentElement.parentElement;
	if (tab.tagName=='TBODY') tab = tab.parentElement;
	if(tab.tagName != "TABLE") return;

	window.status = "正在对数据进行排序，请您稍候...";

	if(the_obj.mode == undefined)
		the_obj.mode = false;

	var len = tab.rows.length;

	if (len > 0) {
		if (tab.rows[len-1].cells[0].innerText.indexOf("合") >= 0)
			len--;
	}

	if(the_obj.sort == "num")
		table_sort_num__2(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string(tab, the_obj.cellIndex, the_obj.mode, len);
	the_obj.mode = !the_obj.mode;

	window.status = "";
}

function table_sort_num__2(obj, n, mode, len) {
	var i, j;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_num_fast__2(obj, n, mode, len);

	for(i = 1; i < len - 1; i++) {
		if(tab.rows[i].cells[0].innerText == "")
			continue;
		if(tab.rows[i].className == "")
			continue;
		for(j = i + 1;j < len; j++) {
			if(tab.rows[j].cells[0].innerText == "")
				continue;
			if(tab.rows[j].className == "")
				continue;
			if (mode) {
				if(sl_parseFloat(tab.rows[i].cells[n].innerText) < sl_parseFloat(tab.rows[j].cells[n].innerText)) {
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	
						swapHistoryRow(tab, i, j, len);
				}
			} else {
				if(sl_parseFloat(tab.rows[i].cells[n].innerText) > sl_parseFloat(tab.rows[j].cells[n].innerText)) {
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi)	
						swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

function table_sort_num_fast__2(the_tab, col, mode, len) {
	var tab_arr = new Array();
	for (var i=1; i<len; i+=2) {
		if (the_tab.rows[i].cells[col]==null || the_tab.rows[i].cells[col].innerText == "")
			continue;
		tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i], the_tab.rows[i+1]));
	}
	function SortArr(mode) {
		return function (arr1, arr2)
		{
			var flag;
			var a,b;
			a = sl_parseFloat(arr1[0]);
			b = sl_parseFloat(arr2[0]);
			//flag=mode?b.localeCompare(a):a.localeCompare(b);//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			if (mode)
				return b-a;
			else
				return a-b;
			//return flag;
		}
	}
	tab_arr.sort(SortArr(mode));
		
	for(i=0;i<tab_arr.length;i++) {
		the_tab.firstChild.appendChild(tab_arr[i][1]);
		the_tab.firstChild.appendChild(tab_arr[i][2]);
	}
	for(i = 1; i < the_tab.rows.length - tab_arr.length*2; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="bespeak_check_action.jsp">
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />
<!--查询功能模块-->
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
   <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
	 	 <td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
	 	 <td>
	 	 	<select onkeydown="javascript:nextKeyPress(this)" size="1" name="q_customer_cust_source" style="width: 110px">
				<%=Argument.getCustomerSourceOptions(q_customer_cust_source)%>
			</select>
	 	 </td>
        <td align="right">&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.qPreCode",clientLocale)%> <!--预约号-->:</td>
		<td align="left"><input type="text" name="q_pre_code" value="<%=q_pre_code%>"onkeydown="javascript:nextKeyPress(this)" size="15"/>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> <!--客户名称-->:</td>
		<td align="left" colspan="3"><input type="text" name="q_cust_name" value="<%=q_cust_name%>" size="52"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.regMoney",clientLocale)%> :</td><!--登记额度-->
		<!--从-->
        <td colspan="3">
        		<%=LocalUtilis.language("message.start",clientLocale)%> 
        		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_reg_money" size="20"
				value="<%=Utility.trimNull(min_reg_money)%>" /> 
                <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_reg_money" size="20" 
				value="<%=Utility.trimNull(max_reg_money)%>" />
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4">		
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				<!--确认-->				 
		</td>
	</tr>
</table>
</div>

<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right">
		<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
		<!-- 审核 --><!-- 审核通过 -->
		<button type="button"  class="xpbutton4" name="btnCheck" title='<%=LocalUtilis.language("class.auditBy2",clientLocale)%>' onclick="javascript:op_check();"><%=LocalUtilis.language("class.auditBy2",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;
	</div>
	<hr noshade color="#808080" size="1" width="100%">
</div>
<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td align="center" width="120px">
			<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
			&nbsp;&nbsp;<%=LocalUtilis.language("class.custName4",clientLocale)%> 
		</td><!--客户姓名-->
		<td align="center" width="*">客户类别</td><!--客户类别-->
		<td align="center" ><%=LocalUtilis.language("class.qPreCode",clientLocale)%> </td><!--预约号-->
		<td align="center" ><%=LocalUtilis.language("class.factNum",clientLocale)%> </td><!--份数-->
		<td align="center" style="ie2:expression(onclick=sortTable__2)" sort="num"><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--金额--><!--金额(万元)-->
		<td align="center">到账金额 </td>
		<td align="center" width="*">预期收益率</td>
		<td align="center" ><%=LocalUtilis.language("class.contact",clientLocale)%> </td><!--联系人-->
		<td align="center" ><%=LocalUtilis.language("login.telephone",clientLocale)%> </td><!--联系电话-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerSource",clientLocale)%> </td><!-- 客户来源 -->
		<td align="center" ><%=LocalUtilis.language("class.custGroups",clientLocale)%></td>
		<td align="center" ><%=LocalUtilis.language("class.validDays2",clientLocale)%> </td><!--有效期-->
		<td align="center" ><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--状态-->
		<td align="center"><%=LocalUtilis.language("class.details",clientLocale)%></td><!--详细-->		
		<td align="center" >审核 </td>
	</tr>
<%
	//声明参数
	Integer serial_no;
	Integer cust_id; 
	String cust_name = null;
	String cust_type_name ="";
	String pre_code= null;
	Integer rg_num = new Integer(0);
	Integer pre_num = new Integer(0);
	BigDecimal pre_money = new BigDecimal(0);
	BigDecimal pay_money = new BigDecimal(0);
	Integer rg_num_total = new Integer(0);
	Integer pre_num_total = new Integer(0); 
	BigDecimal pre_money_total = new BigDecimal(0);
	BigDecimal pay_money_total = new BigDecimal(0);
	BigDecimal exp_rate1;
	BigDecimal exp_rate2;
	Integer link_man;
	String h_tel = null;
	String o_tel = null;
	String mobile = null;
	String bp = null;
	Integer preDate;
	Integer expRegDate;
	Integer valid_days;
	String pre_status_name;
	String summary;
	String inputTime;
	String custSourceName;	
	
	Iterator iterator = list.iterator();

	while(iterator.hasNext()){
		iCount++;
		Map map = (Map)iterator.next();
		
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		cust_type_name =  Utility.trimNull(map.get("CUST_TYPE_NAME"));
		pre_code = Utility.trimNull(map.get("PRE_CODE"));
		rg_num = Utility.parseInt(Utility.trimNull(map.get("RG_NUM")),new Integer(0));
		rg_num_total =new Integer(rg_num_total.intValue() + rg_num.intValue());
		pre_num = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
		pre_num_total = new Integer(pre_num_total.intValue()+pre_num.intValue());
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00),2,"1");
		pay_money = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1");
		pre_money_total = pre_money_total.add(pre_money);
		pay_money_total = pay_money_total.add(pay_money);

		exp_rate1 = ((BigDecimal)map.get("EXP_RATE1")).multiply(new BigDecimal(100.0));
		exp_rate1 = exp_rate1.setScale(2);
		exp_rate2 = ((BigDecimal)map.get("EXP_RATE2")).multiply(new BigDecimal(100.0));
		exp_rate2 = exp_rate2.setScale(2);
		//exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")), new BigDecimal(0.00),2,"100"); // 2:小数点后2位
		//exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")), new BigDecimal(0.00),2,"100");
		
		link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
		h_tel = Utility.trimNull(map.get("H_TEL"));
		o_tel = Utility.trimNull(map.get("O_TEL"));
		mobile = Utility.trimNull(map.get("MOBILE"));
		bp = Utility.trimNull(map.get("BP"));
		preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
		expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
		valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));
		pre_status_name = Utility.trimNull(map.get("PRE_STATUS_NAME"));
		summary = Utility.trimNull(map.get("SUMMARY"));		
		inputTime =  Utility.trimNull(map.get("INPUT_TIME")).substring(0,16);
		custSourceName = Utility.trimNull(map.get("CUST_SOURCE_NAME"));
%>
	<tr class="tr<%=(iCount%2)%>">
		<td class="tdh" align="center">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><input type="checkbox" name="serial_no"	value="<%=serial_no%>" class="selectAllBox"></td>
					<td width="90%" align="left"><input type="text" class="ednone" value="<%=cust_name%>" readonly></td>
				</tr>
			</table>
		</td>
		<td align="center" ><%=cust_type_name%></td>
		<td align="center" ><%=pre_code%></td>
		<td align="center" ><%=rg_num%>/<%=pre_num%></td>
		<td align="right"><%=Format.formatMoney(pre_money)%></td>
		<td align="right"><%=Format.formatMoney(pay_money)%></td>
		<td align="center" width="100px"><%=exp_rate1%>%<%=exp_rate1.equals(exp_rate2)?"":"-"+exp_rate2+"%"%></td>
		<td align="center" ><%=Argument.getOperator_Name(link_man)%></td>
		<td align="center" ><input type="text" class="ednone" value="<%=Utility.trimNull(Argument.getALLTEL(h_tel,o_tel,mobile,bp))%>" readonly></td>		
		<td align="center" ><%=custSourceName%></td>
		<td align="center" ><%=Utility.trimNull(Argument.getCustGroups(cust_id))%></td>
		<td align="center" ><%=valid_days%><%=LocalUtilis.language("message.days",clientLocale)%> </td><!--天-->
		<td align="center" ><%=pre_status_name%></td>
         <td align="center">
         	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);">
     			<IMG id="detailsImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
         	</button>
         	<input type="hidden" id="detailsFlag_display<%=serial_no%>" name="detailsFlag_display<%=serial_no%>" value="0">             
         </td>		
		<td align="center" >		
		       	<a href="javascript:showInfo(<%=serial_no%>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
               	 </a>
		</td> 
	</tr>

	<tr id="detailsTr<%=serial_no%>" style="display: none">
		<td align="center" bgcolor="#FFFFFF" <%if(q_class_detail_id.intValue()>0){ %>colspan="16" <%}else{%>colspan="15"<%} %>>
			<div id="detailsDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
				<table id="detailsTable<%=serial_no%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
					<tr style="background:F7F7F7;" >
						<td align="right" width="157px" ><%=LocalUtilis.language("class.preDate",clientLocale)%> ：</td><!--预约日期-->
						<td  width="*"><%=Utility.trimNull(Format.formatDateCn(preDate))%></td>
					</tr>
					<tr style="background:F7F7F7;" >
						<td align="right">预计认购日期 ： </td>
						<td  width="*"><%=Utility.trimNull(Format.formatDateCn(expRegDate))%></td>
					</tr>
					<tr style="background:F7F7F7;" >
						<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%>  ： </td><!-- 备注 -->
						<td  width="*"><input type="text" class="ednone" value="<%=summary%>" readonly></td>
					</tr>
					<tr style="background:F7F7F7;" >
						<td align="right">录入时间 ： </td>
						<td  width="*"><%=inputTime%></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>  
<%}%>
	
<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
       <tr class="tr<%=i%2%>">
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
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>       
       </tr>           
<%}%>   
	
	<tr class="trbottom">
		<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		 <td align="center"></td>
         <td align="center"></td>
         <td align="center" ><%=rg_num_total%>/<%=pre_num_total%></td>
		 <td align="right" ><%=Format.formatMoney(pre_money_total)%></td>
		 <td align="right" ><%=Format.formatMoney(pay_money_total)%></td>
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
</table>
</div>
<br>
<div>
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>