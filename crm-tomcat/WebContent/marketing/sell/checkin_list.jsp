<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_customer_cust_source =  Utility.trimNull(request.getParameter("q_customer_cust_source"));//客户来源
Integer int_flag=Utility.parseInt(request.getParameter("int_flag"),new Integer(1));
Integer q_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_cust_type")),new Integer(0));
String q_invest_type_name = Utility.trimNull(request.getParameter("q_invest_type_name"));
String q_invest_type =  Utility.trimNull(request.getParameter("q_invest_type"));
BigDecimal min_reg_money = Utility.parseDecimal(request.getParameter("min_reg_money"), new BigDecimal(0),2,"1");//最低登记额度
BigDecimal max_reg_money = Utility.parseDecimal(request.getParameter("max_reg_money"), new BigDecimal(0),2,"1");//最高登记额度
Integer q_group_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_group_id")),new Integer(0));
Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_class_detail_id")),new Integer(0));
Integer link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),new Integer(0));
String q_cust_no = Utility.trimNull(request.getParameter("q_cust_no"));//客户编号
String customer_cust_source = null;

//页面辅助参数
boolean print_flag = false;//打印标志
String[] totalColumn = new String[0];
String tempUrl = "";
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
List list = null;
Map map = null;

//帐套暂时设置
input_bookCode = new Integer(1);

//url设置
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&int_flag="+int_flag;
tempUrl = tempUrl+"&link_man="+link_man;
sUrl = sUrl + tempUrl;

//获得对象及结果集
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO vo = new PreContractVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setCust_name(q_cust_name);
vo.setInt_flag(int_flag);
vo.setCust_type(q_cust_type);
vo.setCust_source(q_customer_cust_source);
vo.setInvest_type(q_invest_type);
vo.setMax_reg_money(max_reg_money);
vo.setMin_reg_money(min_reg_money);
vo.setClassdetail_id(q_class_detail_id);
vo.setCust_group_id(q_group_id);
vo.setLink_man(link_man);
vo.setCust_no(q_cust_no);

IPageList pageList = preContract.query_reginfo_crm(vo,totalColumn,t_sPage,t_sPagesize);
list = pageList.getRsList();

if(list.size()>0){
	print_flag = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.checkinList",clientLocale)%> </TITLE>
<!--预登记列表-->
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

<script type="text/javascript">
	/*启动加载*/
	window.onload = function(){
		initQueryCondition();
		var q_cust_type = document.getElementById("q_cust_type").value;
		var tdId = "td"+q_cust_type;
		document.getElementById(tdId).bgColor ="#197fe6";
		document.getElementById(tdId).style.color="#FFFFFF";
	}
	
	/*查询功能*/
	function StartQuery(){
		refreshPage();
	}

	/*新增*/
	function newInfo(){	
		disableAllBtn(true);
		location="checkin_add.jsp";
	}
	
	/*修改*/
	function showInfo(cust_id){
		if(showModalDialog('checkin_edit.jsp?cust_id=' + cust_id, '', 'dialogWidth:820px;dialogHeight:400px;status:0;help:0')!= null){
			sl_update_ok();
			location.reload();		
		}
		
		showWaitting(0);
	}
	
	//预登记转预约
	function preInfo(cust_id,pre_money,customer_cust_source,reg_valid_days){
		disableAllBtn(true);
		window.location.href="checkin_pre.jsp?customer_cust_id="+cust_id+"&pre_money="+pre_money+"&customer_cust_source="+customer_cust_source+"&valid_days="+reg_valid_days;
	}
	
	
	//删除
	function delInfo(){
		if(confirmRemove(document.theform.cust_id)){
			disableAllBtn(true);
			document.theform.action = "checkin_del.jsp";
			document.theform.submit();
		}
	}

	/*刷新*/
	function refreshPage(){
		disableAllBtn(true);
		if(!sl_checkDecimal(document.theform.min_reg_money,"<%=LocalUtilis.language("class.regMoney",clientLocale)%> ",13,3,0))return false;//最小登记额度
		if(!sl_checkDecimal(document.theform.max_reg_money,"<%=LocalUtilis.language("class.regMoney",clientLocale)%> ",13,3,0))return false;//最高登记额度
		
		if(document.theform.int_flag.checked){
			document.theform.int_flag.value=1;
		}
		else{
			document.theform.int_flag.value=0;
		}
		var input_man = document.theform.input_man.value;
		var url = "checkin_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
		var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
		var url = url + '&q_customer_cust_source=' + document.theform.q_customer_cust_source.value;
		var url = url + '&q_invest_type=' + document.theform.q_invest_type.value;
		var url = url + '&q_invest_type_name=' + document.theform.q_invest_type_name.value;
		var url = url + '&min_reg_money=' + document.theform.min_reg_money.value;
		var url = url + '&max_reg_money=' + document.theform.max_reg_money.value;
		var url = url + '&int_flag=' + document.theform.int_flag.value;
		var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
		var url = url + '&q_group_id=' +document.theform.q_group_id.value;
		var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
		var url = url + '&link_man=' +document.theform.link_man.value;
		var url = url + '&q_cust_no=' +document.theform.q_cust_no.value;
		var url = url + '&input_man='+ input_man;
		location = url;	
	}
	
	/*打印*/
	function op_print(){
		var q_cust_name = document.theform.q_cust_name.value;
		if(document.theform.int_flag.checked)
			document.theform.int_flag.value=1;
		else
			document.theform.int_flag.value=0;
			
		var int_flag = document.theform.int_flag.value;
		
		var url = "checkin_print.jsp?q_cust_name="+q_cust_name+"&int_flag="+int_flag;
		showModalDialog(url, '', 'dialogWidth:400;dialogHeight:500;status:0;help:0');
		showWaitting(0);	
	}
	
	/*导出*/	
	function writefile(){
		var q_cust_name = document.theform.q_cust_name.value;
		var book_code = document.theform.book_code.value;
		var input_man = document.theform.input_man.value;
		
		if(document.theform.int_flag.checked)
			document.theform.int_flag.value=1;
		else
			document.theform.int_flag.value=0;
		var int_flag = document.theform.int_flag.value;
		
		if(sl_confirm("<%=LocalUtilis.language("message.exportData",clientLocale)%> ")){//导出数据
				var url = "checkin_outExcel.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
				var url = url + '&book_code=' + book_code;
				var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
				var url = url + '&q_customer_cust_source=' + document.theform.q_customer_cust_source.value;
				var url = url + '&q_invest_type=' + document.theform.q_invest_type.value;
				var url = url + '&q_invest_type_name=' + document.theform.q_invest_type_name.value;
				var url = url + '&min_reg_money=' + document.theform.min_reg_money.value;
				var url = url + '&max_reg_money=' + document.theform.max_reg_money.value;
				var url = url + '&int_flag=' + document.theform.int_flag.value;
				var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
				var url = url + '&q_group_id=' +document.theform.q_group_id.value;
				var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
				var url = url + '&input_man='+ input_man;
				//alert(url);
				location = url;
		}		
		
		showWaitting(0);	
	}
	
	/*多选投向*/
	function chooseAction(){
		var q_invest_type = document.getElementById("q_invest_type").value;
		var url = "<%=request.getContextPath()%>/marketing/invest_type_check.jsp?invest_type="+q_invest_type;
		var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');
		
		if(ret!=null){
			document.getElementById("q_invest_type").value = ret[0];
			document.getElementById("q_invest_type_name").value = ret[1];
		}
	}
	
	function changeCustType(flag){
		document.getElementById("q_cust_type").value=flag;
		refreshPage();
	}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<input type="hidden" name="book_code" id="book_code" value="<%=input_bookCode%>" />
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />
<input type="hidden" id="input_man" name="input_man" value="<%=input_operatorCode%>" />

<!--查询条件-->
<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	 	<tr>
		   	<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		   	<td align="right">
		   		<button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
		    </td>
	  	</tr>
	</table>
	
	<table>
		<tr>
			<td align="right">客户编号 :</td>
			<td>
				<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_cust_no" value="<%=Utility.trimNull(q_cust_no)%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td>
				<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_cust_name" value="<%=Utility.trimNull(q_cust_name)%>" size="25">
			</td>
			<td align="right"><%=LocalUtilis.language("class.intFlag",clientLocale)%> :</td><!--只显示有登记额度-->
			<td>
				<input class="selectAllBox" onkeydown="javascript:nextKeyPress(this)" type="checkbox" name="int_flag" <%if(int_flag.intValue()==1)out.print("checked");%>>
			</td>
		 </tr>
		 <tr>
		 	 <td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
		 	 <td>
		 	 	<select onkeydown="javascript:nextKeyPress(this)" size="1" name="q_customer_cust_source" style="width: 110px">
					<%=Argument.getCustomerSourceOptions(q_customer_cust_source)%>
				</select>
		 	 </td>
			<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%>:</td><!--联系人-->
			<td>
			    <select name="link_man" style="width:120px">
			        <%=Argument.getManager_Value(new Integer(0))%>				    
			    </select>
			</td>
		 </tr>
		 <tr>
		 	 <td align="right"><%=LocalUtilis.language("class.investType",clientLocale)%> :</td><!--预计投向-->
		 	 <td colspan="3">
		 	 	<input type="text" name="q_invest_type_name" id="q_invest_type_name" size="27"  value="<%=q_invest_type_name%>" 
		 	 			onkeydown="javascript:nextKeyPress(this)" readonly/>
				<input type="hidden" name="q_invest_type" id="q_invest_type" value="<%=q_invest_type%>" /> 
				<!-- 选择 -->&nbsp;&nbsp;&nbsp;
                <button type="button"   class="xpbutton2" id="btnChoInvestType" name="btnChoInvestType" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
		 	 </td>
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
		  	<td align=center colspan=4>
			  	<%if (input_operator.hasFunc(menu_id, 108)) {%>
					<button type="button"   class="xpbutton3" accessKey=o name="btnQuery"onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
					<!--确认-->
				<%}%>
			</td>
		</tr>
	</table>
</div>

<div>
	<div align="left" class="page-title ">
		<font color="#215dc6"><b><%=menu_info%></b></font><!--销售管理>>预约-->
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
		<!--新建-->
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
			<button type="button"   class="xpbutton3" accessKey=n name="btnNew" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
		&nbsp;&nbsp; <%}%>		
		<!--删除-->
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<button type="button"   class="xpbutton3" accessKey=d name="btnCancel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> "	onclick="javascript:delInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<%}%>	
		<%if (input_operator.hasFunc(menu_id, 103)) {%>
		&nbsp;&nbsp;&nbsp;
		<button type="button"   class="xpbutton4" <%if(!print_flag)out.print("disabled");%> name="btnOk" title="<%=LocalUtilis.language("menu.generateXSL&Export",clientLocale)%> " Onclick="javascript:writefile();"><%=LocalUtilis.language("message.exportData",clientLocale)%> </button>
		&nbsp;&nbsp;&nbsp;<!--生成XSL文件并导出--><!--导出数据-->
		<%}%>	
		<%if (input_operator.hasFunc(menu_id, 104)) {%>
		<button type="button"   class="xpbutton3" <%if (!print_flag)out.print("disabled");%> name="btnCancel" title="<%=LocalUtilis.language("message.print",clientLocale)%> " 
		    onclick="javascript:op_print();"><%=LocalUtilis.language("message.print",clientLocale)%> 
		</button><!--打印-->				
		<%}%>				
	</div>
</div>

<div id="headDiv" style="margin-top:5px">
<table cellSpacing="1" cellPadding="2"  bgcolor="#FFFFFF">
		<tr style="background:F7F7F7;">
			<td width="50px" align="center" id="td0" <%if (q_cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
			<!--全部-->
			<td width="50px" align="center" id="td1" <%if (q_cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
			<!--个人-->
			<td width="50px" align="center" id="td2" <%if (q_cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
			<!--机构-->
			<td align="center" id="td4">
				<select name="group_id" id="q_group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
					<%=Argument.getCustGroupOption2(new Integer(0),q_group_id)%>
				</select>
			</td>
			<td align="center" id="td4">
				<select name="q_class_detail_id" id="q_class_detail_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
					<option>请选择关注度</option>
					<%=Argument.getCustClassOption(new Integer(30),q_class_detail_id)%>
				</select>
			</td>
		</tr> 
</table>
</div>

<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
	    <td align="center" width="*">
	    	<input type="checkbox" name="btnCheckbox" class="selectAllBox" 
	    		onclick="javascript:selectAllBox(document.theform.cust_id,this);">
	    		&nbsp;&nbsp;<%=LocalUtilis.language("class.customerID",clientLocale)%> <!--客户编号-->
	    </td>
		<td align="left" width="*"><%=LocalUtilis.language("class.custName4",clientLocale)%> </td><!--客户姓名-->
		<td align="center" width="*"><%=LocalUtilis.language("class.custType",clientLocale)%> </td><!--客户类型-->
		<!--<td align="center" width="*"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td>客户类别-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> </td>	<!--家庭电话-->					
		<td align="center" width="*"><%=LocalUtilis.language("class.oTel",clientLocale)%> </td><!--办公电话-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> </td><!--手机-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2</td><!--手机2-->
		<td align="center" width="*"><%=LocalUtilis.language("class.regMoney2",clientLocale)%> </td><!--登记金额-->	
		<td align="center" width="*"><%=LocalUtilis.language("class.regDate",clientLocale)%> </td><!--登记日期-->	
		<td align="center" width="*"><%=LocalUtilis.language("class.validDays",clientLocale)%></td> <!--有效天数-->
		<td align="center" width="*"><%=LocalUtilis.language("message.edit",clientLocale)%>/ <%=LocalUtilis.language("message.view",clientLocale)%></td><!--编辑/查看-->	
		<td align="center" width="*"><%=LocalUtilis.language("message.turnBespeak",clientLocale)%> </td><!--转预约-->	
	</tr>
	
<%
//声明变量
Integer cust_id;
String cust_no = null;
String cust_name = null;
String cust_tel = null;
String o_tel = null;
String mobile = null;
String bp = null;
String cust_type_name = "";
BigDecimal reg_money = new BigDecimal(0);
BigDecimal reg_money_total = new BigDecimal(0);
String cust_source_name = "";
String invest_type_name = "";
Integer reg_data = null;
Integer is_deal2;
Integer service_man;
String is_deal_name = "";

Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	cust_no = Utility.trimNull(map.get("CUST_NO"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	cust_tel = Utility.trimNull(map.get("CUST_TEL"));
	o_tel = Utility.trimNull(map.get("O_TEL"));
	mobile = Utility.trimNull(map.get("MOBILE"));
	bp =  Utility.trimNull(map.get("BP"));
	reg_money = Utility.parseDecimal(Utility.trimNull(map.get("REG_MONEY")),new BigDecimal(0),2,"1");
	reg_money_total = reg_money_total.add(reg_money);
	reg_data = Utility.parseInt(Utility.trimNull(map.get("REG_DATE")),new Integer(0));
	cust_type_name = Utility.trimNull(map.get("CUST_TYPE_NAME"));
	//cust_source_name =Utility.trimNull(map.get("CUST_SOURCE_NAME"));
	invest_type_name= Utility.trimNull(map.get("INVEST_TYPE_NAME"));
	is_deal2 = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
	is_deal_name = Argument.getWTName(is_deal2);
	service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
	Integer reg_valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));	
	customer_cust_source =  Utility.trimNull(map.get("CUST_SOURCE"));	
	if("".equals(customer_cust_source)){
		customer_cust_source="0";
	}
%>
	<tr class="tr<%= iCount%2%>">
		 <td align="center">
			 <input type="checkbox" name="cust_id" value="<%=cust_id%>" class="selectAllBox">&nbsp;&nbsp;<%=cust_no%>
		 </td>
		 <td align="center"><input type="text" style="width:100%" class="ednone" value="<%=cust_name%>" readonly></td>
		 <td align="center"><%=cust_type_name%></td>	
		 <td align="center"><%=cust_tel%></td>
		 <td align="center"><%= o_tel%></td>
		 <td align="center"><%= mobile%></td>
		 <td align="center"><%= bp%></td>
		 <td align="right"><%= Format.formatMoney(reg_money)%>&nbsp;&nbsp;</td>
		 <td align="center"><%= Format.formatDateLine(reg_data)%></td>	
		 <td align="center"><%=reg_valid_days.intValue()==0?"一直有效":reg_valid_days.toString()%></td>	
		 <td align="center">
			 <%if (input_operator.hasFunc(menu_id, 102)) {%>
	             <a href="#" onclick="javascript:showInfo(<%=cust_id%>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
               	 </a>
			 <%}%>
		 </td>
		 <td align="center">
			 <%if (input_operator.hasFunc(menu_id, 102)) {%>
			 <a href="#" onclick="javascript:preInfo(<%=cust_id%>,<%=reg_money%>,<%=customer_cust_source%>,<%=reg_valid_days %>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/save_file.gif" width="16" height="16">
               	 </a>
			 <%}%>
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
       </tr>           
<%}%>   	
	<tr class="trbottom">
		<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
	    <td align="center"></td>
        <td align="center"></td>
       	<td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
     	<td align="center"></td>
       	<td align="right"><%= Format.formatMoney(reg_money_total)%>&nbsp;&nbsp;</td>
        <td align="center"></td>
       	<td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>	
	</tr>				
</table>
</div>

<br>

<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<% preContract.remove();%>

</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>