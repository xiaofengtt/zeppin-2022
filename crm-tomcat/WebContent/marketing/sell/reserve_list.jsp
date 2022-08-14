<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
int main_flag  = Utility.parseInt(Utility.trimNull(request.getParameter("main_flag")),0);
//获得页面传递变量
Integer q_pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_pre_product_id")),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));
Integer begin_date = Utility.parseInt(Utility.trimNull(request.getParameter("begin_date")),new Integer(0));
BigDecimal pre_money1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money1")),new BigDecimal(0));
BigDecimal pre_money2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money2")),new BigDecimal(0));
String pre_status = Utility.trimNull(request.getParameter("pre_status"));
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
BigDecimal channel_money1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("channel_money1")),new BigDecimal(0));
BigDecimal channel_money2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("channel_money2")),new BigDecimal(0));
String q_pre_level = Utility.trimNull(request.getParameter("q_pre_level"));
 
//页面辅助参数
boolean print_flag = false;
String tempUrl = "";
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;

//帐套暂时设置
input_bookCode = new Integer(1);

sUrl += "&q_pre_product_id="+q_pre_product_id
				+"&q_product_name="+q_product_name
				+"&q_cust_name="+q_cust_name
				+"&team_id="+team_id
				+"&begin_date="+begin_date
				+"&end_date="+end_date
				+"&begin_date="+begin_date
				+"&end_date="+end_date
				+"&pre_money1="+pre_money1
				+"&pre_money2="+pre_money2
				+"&pre_status="+pre_status
 				+"&start_date="+start_date
				+"&end_date="+end_date
				+"&pre_money1="+pre_money1
				+"&pre_money2="+pre_money2
				+"&team_id="+team_id
				+"&pre_status="+pre_status
				+"&channel_type="+channel_type
				+"&channel_money1="+channel_money1
				+"&channel_money2="+channel_money2
				+"&q_pre_level="+q_pre_level;

//获得对象及结果集
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm(); 
PreContractCrmVO vo = new PreContractCrmVO(); 
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
enfo.crm.vo.PreMoneyDetailVO preVo = new enfo.crm.vo.PreMoneyDetailVO();

vo.setPre_product_id(q_pre_product_id);
vo.setProduct_name(q_product_name);
vo.setCust_name(q_cust_name);
vo.setInput_man(input_operatorCode);
vo.setDate1(start_date);
vo.setDate2(end_date);
vo.setMoney1(pre_money1);
vo.setMoney2(pre_money2);
vo.setOnly_my_team(new Integer(1));
vo.setTeam_id(team_id);
vo.setPre_status(pre_status);
vo.setChannel_type(channel_type);
vo.setChannel_money1(channel_money1);
vo.setChannel_money2(channel_money2);
vo.setPre_level(q_pre_level);
String[] totalColumn = {"PRE_MONEY","RG_MONEY","VALID_PRE_MONEY"};
IPageList pageList = null;
if(UNQUERY.intValue()==0)
    pageList = preContract.queryPreContractCrm(vo,totalColumn,t_sPage,t_sPagesize);
else
    pageList = new JdbcPageList();
List list = pageList.getRsList();
if(list == null) list = new ArrayList();
if (list.size()>0) print_flag = true;

menu_info = "预约列表";

preContract.remove();
local.remove();
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakList",clientLocale)%></TITLE>
<!--预约列表-->
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
	};

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*新建预约*/
function newInfo(){
	if(showModalDialog('direct_reserve_add.jsp', '', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

/*修改*/
function showInfo(serial_no){
	if(showModalDialog('reserve_edit1.jsp?serial_no='+ serial_no,'', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}

//删除
function deleteInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action = "reserve_del.jsp?del_flag=1";
		document.theform.submit();
	}
}

//作废
function cancelInfo(){
	if(confirmCancel(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action = "reserve_del.jsp?del_flag=2";
		document.theform.submit();
	}
}

function confirmCancel(element){
	if (element == null){
		sl_alert("未选定任何记录！");
		return false;
	}
	if(checkedCount(element) == 0){
		sl_alert("请选定要作废的记录！");
		return false;
	}
	
	return sl_confirm('作废选定的记录');
}

function confirmRuncate(element){
	if (element == null){
		sl_alert("未选定任何记录！");
		return false;
	}
	if(checkedCount(element) == 0){
		sl_alert("请选定要退款的记录！");
		return false;
	}
	
	return sl_confirm('退款选定的记录');
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	if(!sl_checkDecimal(document.theform.pre_money1, "", 13, 3, 0))return false;
	if(!sl_checkDecimal(document.theform.pre_money2, "", 13, 3, 0))return false;

	location.search = "?page=1&pagesize="+ document.theform.pagesize.value
					 + '&q_pre_product_id=' + document.theform.q_pre_product_id.value
					 + '&q_cust_name=' + document.theform.q_cust_name.value
					 + '&start_date=' + document.theform.start_date.value
					 + '&end_date=' + document.theform.end_date.value
					 + '&pre_money1=' + document.theform.pre_money1.value
					 + '&pre_money2=' + document.theform.pre_money2.value
					 + '&pre_status=' + document.theform.pre_status.value
					 + '&team_id=' + document.theform.team_id.value
					 + '&channel_type=' + document.theform.channel_type.value
					 + '&channel_money1=' + document.theform.channel_money1.value
					 + '&channel_money2=' + document.theform.channel_money2.value
					 + '&q_pre_level=' + document.theform.q_pre_level.value;
}

/*返回*/
function CancelAction(){
	location.href = "<%=request.getContextPath()%>/login/main.jsp?display_flag=1";
}

/*设置产品*/
function setProduct(value){
	var prodid=0;
	
	if(event.keyCode == 13 && value != ""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			if(document.theform.q_pre_product_id.options[i].text.substring(0,j)==value){
				document.theform.q_pre_product_id.options[i].selected=true;
				prodid = document.theform.q_pre_product_id.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			if(document.theform.q_pre_product_id.options[i].text.substring(0,j)==value){
				document.theform.q_pre_product_id.options[i].selected=true;
				prodid = document.theform.q_pre_product_id.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
}


//新建到账处理
function newPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money){
	if(showModalDialog('pre_money_detail_add.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 '', 'dialogWidth:480px;dialogHeight:420px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//修改到账处理
function showPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){
	if (print_count>0 && !sl_confirm("修改到账信息（提示：该到账记录已经打印过！）")) return;

	if (showModalDialog('/marketing/sell/pre_money_detail_update.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 '', 'dialogWidth:380px;dialogHeight:320px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//退款处理
function refundPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){
	if(print_count>0 && !sl_confirm("退款处理（提示：该到账记录已经打印过！）")) return;

	if(showModalDialog('pre_money_detail_refund.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
			 '', 'dialogWidth:380px;dialogHeight:320px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//删除到账信息
function delPreMoneyInfo(serial_no,print_count){
	var msg="删除到账记录";
	if(print_count>0)
		msg += "（提示：该到账记录已经打印过！）";
	if(sl_confirm(msg))
		location = "pre_money_detail_del.jsp?serial_no="+serial_no;
}

//对预约进行退款处理
function BatchRefundPreMoneyInfo(){
	if(confirmRuncate(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action = "pre_money_detail_refund_batch.jsp";
		document.theform.submit();
	}
}

/*明细*/
function setiteminfor(tr10,tablePro,flagdisplay,imagex){
	i= flagdisplay.value;
    if (i==0) {
		tablePro.style.display="";
      	tr10.style.display="";
      	flagdisplay.value=1;
      	imagex.src='<%=request.getContextPath()%>/images/chevronUp.gif';
      	imagex.style.width = "16px";
      	imagex.style.height = "16px";
    } else if(i==1) {
       	tablePro.style.display="none";
        tr10.style.display="none";
      	flagdisplay.value=0;
      	imagex.src='<%=request.getContextPath()%>/images/chevronDown.gif';
      	imagex.style.width = "16px";
      	imagex.style.height = "16px";
    }
}

//查看客户信息
function getCustomer(cust_id){
	var url = '/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	showModalDialog(url,'','dialogWidth:720px;dialogHeight:620px;status:0;help:0;');
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<!--查询功能模块-->
<div id="queryCondition" class="qcMain" style="display:none;width:540px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
   <button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%>:</td><!--产品编号-->
		<td colspan="3">
			<input type="text" name="q_productCode" value="<%= q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="16">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" ></button>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td><!--产品名称-->
		<td colspan="3">
			<select size="1" name="q_pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" style="width: 340px;">
				<%=Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode)%>
			</select>		
		</td>
	</tr>
	<tr>
		<td align="right" width="20%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--客户名称-->
		<td align="left" width="30%">
			<input type="text" name="q_cust_name" value="<%=q_cust_name%>" size="20">
		</td>
		<td align="right" width="20%">渠道类别:</td>
		<td align="left" width="30%">
			<select size="1" name="channel_type" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getDictParamOptions(5500, channel_type)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">渠道金额:</td>
		<td width="30%">
			<input type="text" name="channel_money1" value="<%=Format.formatMoney(channel_money1)%>" size="20">
		</td>
		<td align="center" width="20%">到</td>
		<td align="left" width="30%">
			<input type="text" name="channel_money2" value="<%=Format.formatMoney(channel_money2)%>" size="20">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">认购开始日期:</td>
		<td width="30%">
			<INPUT TYPE="text" NAME="start_date_picker"value="<%=Format.formatDateLine(start_date)%>" size="16">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="start_date" value="">	
		</td>
		<td align="right" width="20%">认购结束日期:</td>
		<td align="left" width="30%">
			<INPUT TYPE="text" NAME="end_date_picker"value="<%=Format.formatDateLine(end_date)%>" size="16">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="end_date" value="">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">预约金额:</td>
		<td width="30%">
			<input type="text" name="pre_money1" value="<%=Format.formatMoney(pre_money1)%>" size="20">
		</td>
		<td align="center" width="20%">到</td>
		<td align="left" width="30%">
			<input type="text" name="pre_money2" value="<%=Format.formatMoney(pre_money2)%>" size="20">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">预约状态:</td>
		<td width="30%">
			<select size="1" name="pre_status" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getDictParamOptions(1113, pre_status)%>
			</select>
		</td>
		<td align="right" width="20%">团队名称:</td>
		<td align="left" width="30%">
			<select size="1" name="team_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getManagerTeams(team_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">预约级别:</td>
		<td width="30%">
			<select size="1" name="q_pre_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getDictParamOptions(1182, q_pre_level)%>
			</select>
		</td>
		<td align="right" width="20%">&nbsp;</td>
		<td align="left" width="30%">&nbsp;</td>
	</tr>
	<tr>
		<td align="center" colspan="4">		
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
				<button type="button"   class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				<!--确认-->
		<%}%>
		</td>
	</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"   class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
			<button type="button"   class="xpbutton3" accessKey=n name="btnNew" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
			</button>&nbsp;&nbsp;&nbsp;<!--新建-->
		<%}%>		
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
			<button type="button"   class="xpbutton3" accessKey=c name="btnCancel" title="<%=LocalUtilis.language("class.invalid",clientLocale)%>"	onclick="javascript:cancelInfo();"><%=LocalUtilis.language("class.invalid",clientLocale)%> (<u>C</u>)
			</button>&nbsp;&nbsp;&nbsp; <!--作废-->
			<!--  
			<button type="button"   class="xpbutton3" accessKey=d name="btnCancel" title="<%=LocalUtilis.language("message.delete",clientLocale)%>"	onclick="javascript:deleteInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
			
			</button>&nbsp;&nbsp;&nbsp;--> <!--删除-->
		<%}%>
		<%if(main_flag ==1 ){%>		
		<button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>	
		<%}%>
		<!-- 
		<button type="button"  class="xpbutton3" accessKey=d name="btnDancel" title="对预约进行退款处理"	onclick="javascript:BatchRefundPreMoneyInfo();">退款(<u>D</u>)
		</button>&nbsp;&nbsp;&nbsp;
		 -->
	</div>
	 <br/>
</div>
<%out.flush(); %>
<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td>
			<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
			<%=LocalUtilis.language("class.custName4",clientLocale)%> 
		</td><!--客户姓名-->	
		<td align="center" width="*">客户类型</td>
		<td align="center" width="*">产品名称</td>
		<td align="center" sort="num">预约金额</td>
		<td align="center" sort="num">到账金额 </td>
		<td align="center" width="*">预约日期</td>
		<td align="center" width="*">预计认购日期</td>
		<td align="center" >预约登记人 </td><!--预约登记人-->
		<td align="center" ><%=LocalUtilis.language("login.telephone",clientLocale)%> </td><!--联系电话-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerSource",clientLocale)%> </td><!-- 客户来源 -->
		<td align="center" sort="num">有效预约金额 </td>
		<td align="center" ><%=LocalUtilis.language("class.preStatus",clientLocale)%> </td><!--状态-->
		<td align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		<td align="center" >到账明细</td>
		<td align="center" >到账处理</td>
	</tr>
<%
	//声明参数
	Integer serial_no;
	Integer cust_id; 
	String cust_name = null;
	String product_name = null;
	String cust_type_name ="";
	String pre_code= null;
	Integer rg_num = new Integer(0);
	Integer pre_num = new Integer(0);
	BigDecimal pre_money = new BigDecimal(0);
	BigDecimal rg_money = new BigDecimal(0);
	Integer rg_num_total = new Integer(0);
	Integer pre_num_total = new Integer(0); 
	BigDecimal pre_money_total = new BigDecimal(0);
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
	Integer money_status;

	Iterator iterator = list.iterator();

	while(iterator.hasNext()){
		iCount++;
		Map map = (Map)iterator.next();
		
		//读数据
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		product_name = Utility.trimNull(map.get("PREPRODUCT_NAME"));
		cust_type_name =  Utility.trimNull(map.get("CUST_TYPE_NAME"));
		pre_code = Utility.trimNull(map.get("PRE_CODE"));

		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00),2,"1");
		rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")), new BigDecimal(0.00),2,"1");
		link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
		h_tel = Utility.trimNull(map.get("H_TEL"));
		o_tel = Utility.trimNull(map.get("O_TEL"));
		mobile = Utility.trimNull(map.get("MOBILE"));
		bp = Utility.trimNull(map.get("BP"));
		preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
		expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
		pre_status_name = Utility.trimNull(map.get("PRE_STATUS_NAME"));
		summary = Utility.trimNull(map.get("SUMMARY"));		
		inputTime =  Utility.trimNull(map.get("INPUT_TIME")).substring(0,16);
		custSourceName = Utility.trimNull(map.get("CUST_SOURCE_NAME"));
		BigDecimal valid_pre_money = Utility.parseDecimal(Utility.trimNull(map.get("VALID_PRE_MONEY")), new BigDecimal(0.00),2,"1");
%>
	<tr class="tr<%=(iCount%2)%>">
		<td class="tdh">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%">
						<%if( Utility.trimNull(map.get("PRE_STATUS")).equals("111301") && (rg_money.doubleValue() <= 0 )) {%>
						<input type="checkbox" name="serial_no"	value="<%=serial_no%>" class="selectAllBox">
						<%}%>
					</td>
					<td width="90%" align="left"><a href="#" onclick="javascript:getCustomer(<%=cust_id%>)"><%=cust_name%></a></td>
				</tr>
			</table>
		</td>
		<td align="center" ><%=cust_type_name%></td>
		<td align="left" ><%=product_name%></td>
		<td align="right" ><%=Format.formatMoney(pre_money)%></td>
		<td align="right" ><%=Format.formatMoney(rg_money)%></td>
		<td align="center" ><%=Format.formatDateLine(preDate)%></td>
		<td align="center" ><%=Format.formatDateLine(expRegDate)%></td>
		<td align="center" ><%=Argument.getOperator_Name(link_man)%></td>
		<td align="center" ><input type="text" class="ednone" value="<%=Utility.trimNull(Argument.getALLTEL(h_tel,o_tel,mobile,bp))%>" readonly></td>		
		<td align="center" ><%=custSourceName%></td>
		<td align="right" ><%=Format.formatMoney(valid_pre_money)%></td>
		<td align="center" ><%=pre_status_name%></td>	
		<td align="center" >		
			<%if ((input_operator.hasFunc(menu_id, 102))) {%>
		       	<a href="#" onclick="javascript:showInfo(<%=serial_no%>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
               	</a>
			<%}%>
		</td>
		<td align="center" >
			<img id="image<%=serial_no%>" style="cursor: pointer; width: 16px; height: 16px;" src="<%=request.getContextPath()%>/images/chevronDown.gif" title="到账明细" onclick="javascript:setiteminfor(tr<%=serial_no%>_0,tablePro<%=serial_no%>,document.theform.flag<%=serial_no%>,document.theform.image<%=serial_no%>);"/>
        	<input type="hidden" name="flag<%=serial_no%>" value="0"/>
		</td>
		<td align="center" >
			<!-- 说明：1拥有菜单权限；2预约状态有有效；3预约金额大于到账金额 -->
			<%if(input_operator.hasFunc(menu_id, 104) && (Utility.trimNull(map.get("PRE_STATUS")).equals("111301")) && (pre_money.compareTo(rg_money) == 1)) {%>
		       	<a href="#" onclick="javascript:newPreMoneyInfo('<%=serial_no%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">
					<img src="<%=request.getContextPath()%>/images/FUNC20076.gif" width="16" height="16" title="到账处理"/>
				</a>
			<%}%>
		</td>
	</tr>
<%out.flush(); %>
	<!-- start 到账处理信息 -->
	<tr id="tr<%=serial_no%>_0" style="display: none" bgcolor="#FFFF99">
		<td class="tdh" colspan="16">
			<table id="tablePro<%=serial_no%>" bgcolor="#000000" style="display: none" border="0" cellpadding="2" cellspacing="1" width="100%">
				<tr>
					<td align="center" bgcolor="#FFFFFF">到帐时间</td>
					<td align="center" bgcolor="#FFFFFF">到帐金额</td>
					<td align="center" bgcolor="#FFFFFF">退款日期</td>
					<td align="center" bgcolor="#FFFFFF">退款金额</td>
					<td align="center" bgcolor="#FFFFFF">缴款方式</td>
					<td align="center" bgcolor="#FFFFFF">编辑</td>
					<td align="center" bgcolor="#FFFFFF">删除</td>
					<td align="center" bgcolor="#FFFFFF">退款</td>
				</tr>
				<!-- 详细 -->
			<%
			if (Utility.trimNull(map.get("PRE_STATUS")).equals("111301")) {
				preVo.setPre_serial_no(serial_no);
				Map preMap = new HashMap();
				List preList = local.queryPreMoneyDetail(preVo);
				for (int j=0; j<preList.size(); j++) {
					preMap = (Map)preList.get(j);			
					String dz_time = Utility.trimNull(preMap.get("DZ_TIME"));
					int idx = dz_time.lastIndexOf(':');
					if (idx>=0) dz_time = dz_time.substring(0, idx);
			%>
				<tr>
					<td bgcolor="#FFFFFF"><%=dz_time%></td>
					<td bgcolor="#FFFFFF" align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(preMap.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
					<td bgcolor="#FFFFFF"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("REFUND_DATE")),new Integer(0)))%></td>
					<td bgcolor="#FFFFFF" align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(preMap.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
					<td bgcolor="#FFFFFF"><%=Utility.trimNull(preMap.get("JK_TYPE_NAME")) %></td>
					<td bgcolor="#FFFFFF" align="center">
						<%if (input_operator.hasFunc(menu_id, 104)) {%>
		       			<a href="#" onclick="javascript:showPreMoneyInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>',<%=Utility.parseInt(Utility.trimNull(preMap.get("PRINT_COUNT")),new Integer(0))%>);">
							<img src="<%=request.getContextPath()%>/images/edit.gif" width="16" height="16" title="编辑到账信息"/>
						</a>
						<%} %>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<%if (input_operator.hasFunc(menu_id, 104)) {%>
		       			<a href="#" onclick="javascript:delPreMoneyInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>',<%=Utility.parseInt(Utility.trimNull(preMap.get("PRINT_COUNT")),new Integer(0))%>);">
							<img src="<%=request.getContextPath()%>/images/icon9.gif" width="16" height="16" title="删除到账信息"/>
						</a>
						<%} %>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<%if (input_operator.hasFunc(menu_id, 104)) {%>
		       			<a href="#" onclick="javascript:refundPreMoneyInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>',<%=Utility.parseInt(Utility.trimNull(preMap.get("PRINT_COUNT")),new Integer(0))%>);">
							<img src="<%=request.getContextPath()%>/images/auditing.gif" width="16" height="16" title="退款处理信息"/>
						</a>
						<%} %>
					</td>
				</tr>
			<%	}
			}%>
			</table>
		</td>
	</tr>		
	<!-- end 到账处理信息 -->
<%}
	
for(int i=0;i<(t_sPagesize-iCount);i++){%>  
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
<%if(pageList.getTotalSize()!=0){%>	
	<tr class="trbottom">
		<td class="tdh" align="center"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
        <td align="center"></td> 
		<td align="center"></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(pageList.getTotalValue("PRE_MONEY")))%></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(pageList.getTotalValue("RG_MONEY")))%></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
		 <td align="right" ><%=Utility.trimNull(Format.formatMoney(pageList.getTotalValue("VALID_PRE_MONEY")))%></td>	
         <td align="center"></td>
		 <td align="center"></td>
		 <td align="center"></td>
		 <td align="center"></td>
	</tr>	
	<%}%>			
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>