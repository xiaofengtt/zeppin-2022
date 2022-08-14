<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
//获得页面传递变量
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),overall_product_id);
String query_contract_bh = Utility.trimNull(request.getParameter("query_contract_bh"));
Integer q_check_flag = Utility.parseInt(request.getParameter("q_check_flag"),new Integer(1));
Integer q_sq_date = Utility.parseInt(request.getParameter("q_sq_date"),new Integer(0));
String query_cust_name = Utility.trimNull(request.getParameter("query_cust_name"));

//页面辅助参数
String tempUrl = "";
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
String[] totalColumn = {"REDEEM_AMOUNT","REDEEM_AMOUNT0"};
List list = null;
Map map = null;

//帐套暂时设置
input_bookCode = new Integer(1);

//url设置
tempUrl = tempUrl+"&sPagesize="+sPagesize;
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&query_contract_bh="+query_contract_bh;
tempUrl = tempUrl+"&q_check_flag="+q_check_flag;
tempUrl = tempUrl+"&q_sq_date="+q_sq_date;
tempUrl = tempUrl+"&query_cust_name="+query_cust_name;

sUrl = sUrl + tempUrl;

//获得对象及结果集
RedeemLocal redeem = EJBFactory.getRedeem();
RedeemVO vo = new RedeemVO();

vo.setProduct_id(q_productId);
vo.setContract_bh(query_contract_bh);
vo.setSq_date(q_sq_date);
vo.setCheck_flag(q_check_flag);
vo.setInput_man(input_operatorCode);
vo.setCust_name(query_cust_name);
IPageList pageList = redeem.listAll(vo,totalColumn,t_sPage,t_sPagesize);
list = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0);
options = options.replaceAll("\"", "'");
%>


<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.redemptionList",clientLocale)%> </TITLE><!--赎回列表-->
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

<script language="javascript">
/*启动加载*/
window.onload = function(){	
	initQueryCondition();	
};

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*新增*/
function newInfo(){		
	location="redemption_add.jsp";
}

/*修改*/
function showInfo(serial_no,ben_serial_no){	
	location = 'redemption_edit.jsp?flag=1&serial_no=' + serial_no + '&ben_serial_no='+ben_serial_no;
}

/*删除功能*/
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action="redemption_del.jsp"		
		document.theform.submit();
	}		
}

/*刷新*/
function refreshPage(){	
	var url = "redemption_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;			
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value; 	
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;	
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&query_cust_name=' + document.theform.query_cust_name.value;	
	if((document.theform.change_date_picker.value!=="")&&(!sl_checkDate(document.theform.change_date_picker,'<%=LocalUtilis.language("class.sqDate",clientLocale)%> '))){return false;}//赎回日期
	else{
		syncDatePicker(document.theform.change_date_picker, document.theform.q_sq_date);
	}
	
	disableAllBtn(true);		
	location = url;	
}

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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
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

//打印
function print(serial_no,ben_serial_no){
	disableAllBtn(true);
  	<%if(user_id.intValue()==1){%>
  		location = 'redemption_print2.jsp?serial_no='+serial_no+"&ben_serial_no="+ben_serial_no;	
  	<%} 
  	else{%>
  		location = 'redemption_print.jsp?serial_no='+serial_no+"&ben_serial_no="+ben_serial_no;	
  	<%}%>
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='q_productId' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.q_productId.options.length;i++){
			var j = document.theform.q_productId.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.q_productId.options[i].text);
				list1.push(document.theform.q_productId.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.q_productId.value="";
			document.theform.q_productId.options[0].selected=true;
		}else{
			document.theform.q_productId.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.q_productId.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.q_productId.focus();
	}else{
		document.theform.q_productId.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="get">

<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
		<td align="right">产品编号 :</td>
		<td>
			<input type="text" name="q_productCode" value="<%= q_productCode%>" onkeydown="javascript:searchProduct(this.value);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct2(document.theform.q_productCode.value);" />
		</td>
		<td  align="right">产品名称 :</td>
		<td  align="left" >
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
		<td align="left" colspan=3 id="select_id">
			<select size="1" name="q_productId" onkeydown="javascript:nextKeyPress(this)" class="productname">					
				<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="right">	<%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td  align="left" >
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="15" value="<%=query_contract_bh%>">
		</td>
		<td  align="right">	<%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td  align="left" >
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_cust_name" size="15" value="<%=query_cust_name%>">
		</td>	
	</tr>	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.sqDate",clientLocale)%> : </td><!--赎回日期-->
		<td>
			<INPUT TYPE="text" NAME="change_date_picker"value="<%=Format.formatDateLine(q_sq_date)%>">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="q_sq_date"   value="">							
		</td>
		<td align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> : </td><!--审核标志-->
		<td>			
			<select name="q_check_flag" style="120px">
				<option  value="0" <%if(new Integer(0).equals(q_check_flag)) out.print("selected");%>><%=LocalUtilis.language("message.all",clientLocale)%> </option><!--全部-->
				<option  value="1" <%if(new Integer(1).equals(q_check_flag)) out.print("selected");%>><%=LocalUtilis.language("message.unaudited",clientLocale)%> </option><!--未审核-->
				<option  value="2" <%if(new Integer(2).equals(q_check_flag)) out.print("selected");%>><%=LocalUtilis.language("message.checked",clientLocale)%> </option><!--已审核-->
				<option  value="3" <%if(new Integer(3).equals(q_check_flag)) out.print("selected");%>><%=LocalUtilis.language("message.finanAudit",clientLocale)%> </option><!--已财务审核-->
				<option  value="4" <%if(new Integer(4).equals(q_check_flag)) out.print("selected");%>><%=LocalUtilis.language("message.honored",clientLocale)%> </option><!--已兑付-->
			</select>
		</td>

	</tr>
	<tr>						
		<td align="center" colspan="4">
			<!--确认-->
            <button type="button"   class="xpbutton3" accessKey=s name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>
			&nbsp;&nbsp;&nbsp;&nbsp;								
		</td>
	</tr>						
	</table>
</div>

<div>
	<div align="left" class="page-title">
		
		<!--销售管理--><!--赎回-->
        <font color="#215dc6"><b><%=LocalUtilis.language("message.redemption",clientLocale)%> </b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<!--查询-->
        <button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
		<!--新建-->
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
            <!--新建-->
			<button type="button"   class="xpbutton3" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.new",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
		&nbsp;&nbsp;&nbsp; <%}%>		
		<!--删除-->
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
			<!--删除-->
            <button type="button"   class="xpbutton3" accessKey=d name="btnCancel" title='<%=LocalUtilis.language("message.delete",clientLocale)%>'	onclick="javascript:delInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<%}%>						
	</div>
	<br/>
</div>

<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
		<tr class="trh">
			<!--合同编号-->
            <td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td>
			<td><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
			<td><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
			<td align="center" >银行名称</td><!-- 银行名称 -->
			<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
			<td align="center" >受益级别</td><!--受益级别-->
			<td align="center" >收益级别</td><!--收益级别-->
			<td sort="num">申请赎回份额</td>
			<td sort="num">实际赎回份额</td>
			<td>是否强制赎回</td>
			<td>是否全额赎回</td>
			<td><%=LocalUtilis.language("class.sqDate",clientLocale)%> </td><!--赎回日期-->
			<td><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
			<td><%=LocalUtilis.language("message.print",clientLocale)%> </td><!--打印-->
		</tr>
		
<%
//声明变量
Integer serial_no = new Integer(0);
Integer ben_serial_no = new Integer(0);
String product_name = "";
String contract_bh = "";
String cust_name = "";
Integer sq_date = new Integer(0);
BigDecimal redeem_amount;
BigDecimal redeem_amount0 = null;
String coerce_flag_name = "";
String fullamount_flag_name = "";
java.math.BigDecimal redeem_amount_coerce = null;

Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
	ben_serial_no = Utility.parseInt(Utility.trimNull(map.get("BEN_SERIAL_NO")),null);
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	redeem_amount = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT")), new BigDecimal(0.00),2,"1");
	sq_date = Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")),null);
	String prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
	String prov_flag_name = Utility.trimNull(map.get("PROV_FLAG_NAME"));
	String bank_name = Utility.trimNull(map.get("BANK_NAME"));
	String bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
	String sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	if(!"".equals(sub_product_name) && sub_product_name!=null)
		sub_product_name = "("+sub_product_name+")";
	redeem_amount0 = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT0")),null);
	redeem_amount_coerce = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT_COERCE")),null);

	if(Utility.parseInt(Utility.trimNull(map.get("COERCE_FLAG")),new Integer(2)).intValue() ==1)
		coerce_flag_name = "<font color='red'>是:" + Format.formatMoney(redeem_amount_coerce) + "</font>";
	else
		coerce_flag_name = "否";

	if(Utility.parseInt(Utility.trimNull(map.get("FULLAMOUNT_FLAG")),new Integer(2)).intValue() ==1)
		fullamount_flag_name = "<font color='red'>是</font>";
	else
		fullamount_flag_name = "否";
	
%>		
	<tr class="tr<%=(iCount % 2)%>">
		<td class="tdh">
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="10%"><input type="checkbox" name="serial_no"
					value="<%=serial_no%>" class="flatcheckbox">
				</td>
				<td width="90%" align="left">&nbsp;&nbsp;<%=contract_sub_bh%></td>
			</tr>
		</table>
		</td>
		<td align="left" >&nbsp;&nbsp;<%=product_name%><%=sub_product_name%></td>		
		<td align="left" ><%=cust_name%></td>
		<td align="left" ><%=bank_name%></td>
		<td align="left" ><%=bank_acct%></td>
		<td align="left" ><%=prov_flag_name %></td>
		<td align="left" ><%=prov_level_name %></td>
		<td align="right" ><%=Format.formatMoney(redeem_amount)%>&nbsp;&nbsp;</td>
		<td align="right" ><%=Format.formatMoney(redeem_amount0)%></td>
		<td align="center"><%=Utility.trimNull(coerce_flag_name)%></td>
		<td align="center"><%=Utility.trimNull(fullamount_flag_name)%></td>
		<td align="center" ><%=Format.formatDateCn(sq_date)%></td>
		<td align="center" >
			<%if (input_operator.hasFunc(menu_id, 102)){%>
			<a href="javascript:showInfo(<%=serial_no%>,<%=ben_serial_no%>);">
           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16"/>
           	</a>
           	<%}%>
		</td>	
		<td align="center">
		<%if (input_operator.hasFunc(menu_id, 108)){%>
			 <a href="javascript:print(<%=serial_no%>,<%=ben_serial_no%>);">
           		<img border="0" src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16">
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
             <td align="center"></td>       
             <td align="center"></td>            
           </tr>           
<%}%>   	
			<tr class="trbottom">
				<!--合计--><!--项-->
                <td class="tdh"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
				<td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>       
	            <td align="center"></td> 
				<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("REDEEM_AMOUNT"),new BigDecimal(0.00)))%></td>
				<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("REDEEM_AMOUNT0"),new BigDecimal(0.00)))%></td>
				<td align="center"></td>
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
<% redeem.remove();%>
</form>
</BODY>
</HTML>