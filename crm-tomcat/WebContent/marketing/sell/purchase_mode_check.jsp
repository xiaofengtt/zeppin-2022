<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//帐套暂时设置
input_bookCode = new Integer(1);

Integer product_id        = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer sub_product_id   = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String product_name1 = Utility.trimNull(request.getParameter("product_name1"));
String  product_code     = Utility.trimNull(request.getParameter("product_code"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String query_contract_bh= Utility.trimNull(request.getParameter("query_contract_bh"));
Integer pre_flag=Utility.parseInt(request.getParameter("pre_flag"),new Integer(0));
Integer check_flag=Utility.parseInt(request.getParameter("check_flag"),new Integer(0));

int is_sub_flag = Argument.getSyscontrolValue_1("SUBCP01");
Integer sub_flag = new Integer(0);

if (product_id.intValue()!=0)
	sub_flag = Utility.parseInt(Argument.getProductFlag(product_id,"sub_flag"),new Integer(0));

int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

sUrl += "&product_id="+product_id;
sUrl += "&sub_product_id="+sub_product_id;
sUrl += "&product_code="+product_code;
sUrl += "&cust_name="+cust_name;
sUrl += "&card_id="+card_id;
sUrl += "&query_contract_bh="+query_contract_bh;
sUrl += "&pre_flag="+pre_flag;
sUrl += "&check_flag="+check_flag;

TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
TMoneyDataVO vo = new TMoneyDataVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setCheck_flag(check_flag);
vo.setCust_name(cust_name);
vo.setContract_sub_bh(query_contract_bh);
IPageList pageList  = moneyData.listContractMendForPage(vo,new String[0],t_sPage,t_sPagesize);
List list = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,17).replaceAll("\"", "'");

moneyData.remove();
%>
<HTML>
<HEAD>
<TITLE>合同信息补全审核</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
window.onload = function(){ initQueryCondition(); };

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
}

function searchProduct(value) {
	prodid=0;
	if (value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function selectProductItem(product_id){
	disableAllBtn(true);  	
	location.search = '?page=1&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value
			+ '&product_name1='+ document.theform.product_name1.value +'&product_id='+ document.theform.product_id.value 
			+'&cust_name='+document.theform.cust_name.value+'&query_contract_bh='+ document.theform.query_contract_bh.value
			+'&card_id='+document.theform.card_id.value +'&pre_flag='+document.theform.pre_flag.value
			+'&check_flag='+document.theform.check_flag.value;
} 

function StartQuery(){
	disableAllBtn(true);
	var sub_product_id = 0;
	if(document.theform.sub_product_id)
		sub_product_id = document.theform.sub_product_id.value;

	location.search = '?page=1&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value
			+ '&product_name1='+ document.theform.product_name1.value +'&product_id='+ document.theform.product_id.value 
			+'&cust_name='+document.theform.cust_name.value+'&query_contract_bh='+ document.theform.query_contract_bh.value
			+'&card_id='+document.theform.card_id.value +'&pre_flag='+document.theform.pre_flag.value
			+'&check_flag='+document.theform.check_flag.value+"&sub_product_id="+sub_product_id;
}

function refreshPage(){
	StartQuery();
}

function showInfo(serialno){
	var sQuery = document.theform.productid.value + "$" +document.theform.product_id.value + "$" 
					+ document.theform.cust_name.value + "$" + document.theform.query_contract_bh.value  
					+ "$" + document.theform.card_id.value + "$" + document.theform.check_flag.value 
					+ "$" + document.theform.pagesize.value + "$<%=sPage%>";
	location = 'purchase_mend_check_action.jsp?serial_no='+serialno+'&sQuery='+sQuery;
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'  onchange='javascript:selectProductItem(this.value);'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for (var i=0;i<list.length;i++)
				document.theform.product_id.options.add(new Option(list[i],list1[i]));			
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">

<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
		   <td>查询条件 ：</td>
		   <td align="right">
		  	 <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
		   </td>
		</tr>
	</table>

	<table>
		<tr>
			<td align="right">产品编号:</td>
			<td>
				<input type="text" name="productid" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="15">						
				&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button></td>
			<td align="right">产品名称 :</td>
			<td align="left" >
				<input type="text" name="q_product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProductName(document.theform.q_product_name.value);" />		
			</td>
			<td style="display:none" align="right">认购方式:</td>
			<td style="display:none"  align="left">
				<select size="1" name="pre_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH:110px">
					<%=Argument.getPreFlagOptions(pre_flag)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">产品选择:</td>
			<td align="left" colspan=3 id="select_id">
				<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectProductItem(this.value);">
					<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,17)%>
				</select>
			</td>
		</tr>
	<%if(sub_flag.intValue() == 1 && is_sub_flag == 1){%>
		<tr id="sub_product">	
			<td align="right">子产品选择:</td>
			<td align=left colspan=3>
				<select size="1" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions2(product_id, new Integer(0),sub_product_id,3)%> 
				</select>&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	<%}%>
		<tr style="display:none" >
		<td>产品名称:</td>
			<td align="left" colspan=3 >
				<input type="text" name="product_name1" value="<%=Utility.trimNull(product_name1)%>" onkeydown="javascript:nextKeyPress(this)" size="23">
			</td>
		</tr>
		<tr>
			<td style="display:none" align="right">客户名称:</td>
			<td style="display:none">
				<input name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="15">
			</td>
			<td style="display:none" align="right">证件号码:</td>
			<td style="display:none" align="left">
				<input type="text" name="card_id" value="<%=Utility.trimNull(card_id)%>" onkeydown="javascript:nextKeyPress(this);"  size="23">
			</td>
		</tr>
		<tr>
			<td align="right">合同编号:</td>
			<td align="left" >
				<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="15" value="<%=query_contract_bh%>">
			</td>
			<td align="right">审核标志:</td>
			<td align="left" >
				<select size="1" name="check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
					<%=Argument.getCheckFlagOptions(check_flag)%>
				</select>
			</td>
		</tr>
		<tr>						
			<td align="center" colspan=4>
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button>
			</td>
		</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>合同信息补全审核</b></font>
	</div>

	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
	</div>
</div>
<br/>
<div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td align="center"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;合同编号</td>
		<td align="center">产品名称</td>
		<td align="center">客户编号</td>
		<td align="center">客户名称</td>
		<td align="center" sort="num">认购金额</td>
		<td align="center">签署日期</td>
		<td align="center">审核备注</td>
	<!-- 	<td align="center">合同状态</td>
		<td align="center">审核人</td> -->
        <td align="center">信息补全状态</td>
		<td align="center">认购方式</td>						
		<td align="center">审核</td>
	</tr>
<%
BigDecimal rg_money = new BigDecimal(0);
Iterator iterator = list.iterator();
while (iterator.hasNext()) {
	iCount++;
	Map map = (Map)iterator.next();

	check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
	String ht_status = Utility.trimNull(map.get("HT_STATUS"));
	Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	String sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	String cust_no = Utility.trimNull(map.get("CUST_NO")); 
	cust_name = Utility.trimNull(map.get("CUST_NAME")); 
	rg_money  = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),null).setScale(2,2);
	Integer qs_date = Utility.parseInt(Utility.trimNull(map.get("QS_DATE")),new Integer(0));
	String check_flag_name = Argument.getCheckFlagName(check_flag);
	String ht_status_name = Utility.trimNull(map.get("HT_STATUS_NAME"));
	Integer mend_flag = Utility.parseInt(Utility.trimNull(map.get("MEND_CHECK_FLAG")),new Integer(0));
	pre_flag = Utility.parseInt(Utility.trimNull(map.get("PRE_FLAG")),new Integer(0));
	String bank_id = Utility.trimNull(map.get("BANK_ID")); 
	Integer check_man = Utility.parseInt(Utility.trimNull(map.get("CHECK_MAN")),new Integer(0));
	String check_man_name = Argument.getOperatorName(check_man);
	
    String mend_flag_name = "";//mend_flag.intValue()==1? "补全信息未审核": "补全信息已审核"; 
	if(mend_flag.intValue()==1)
        mend_flag_name = "补全信息未审核";
    else if (mend_flag.intValue()==-1 || mend_flag.intValue()==0)
        mend_flag_name = "补全信息未补全";
	else
		mend_flag_name = "补全信息已审核";
	String color = mend_flag.intValue()==-1 || mend_flag.intValue()==0? "red": "black";
%>
	<tr class="tr<%=iCount%2%>" style="color:<%=color%>">
		<td class="tdh" align="center">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><%if(check_flag.intValue()==1 &&ht_status.equals("120101")){%><input  type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"> <%}%></td>
					<td width="90%" align="center"><%=contract_sub_bh%></td>
				</tr>
			</table>
		</td>
		<td align="left"><%=Utility.trimNull(sub_product_name).equals("")?Utility.trimNull(product_name):Utility.trimNull(product_name)+"("+Utility.trimNull(sub_product_name)+")"%></td>
		<td align="center"><%=Utility.trimNull(cust_no)%></td>
		<td align="left">
		<%//if(input_operator.hasFunc("20204", 106)||input_operator.hasFunc("20201", 106)) { %>						
				<!--  a href="customer_info_print.jsp?cust_id=<%//contract.getCust_id()%>"><%//Utility.trimNull(cust_name)%></a>-->
		<%//}else  {%>						
			<%=Utility.trimNull(cust_name)%>
		<%//}%>	
		</td>
		<td align="right"><%=Utility.trimNull(Format.formatMoney(rg_money))%></td>
		<td align="center"><%=Format.formatDateCn(qs_date)%></td>
		<td align="center"><%=Utility.trimNull(map.get("CHECK_SUMMARY"))%></td>
		<!-- <td align="center"><%=ht_status_name%><%=check_flag_name%></td>
		<td align="center"><%=check_man_name%></td> -->
		<td align="center"><%=mend_flag_name%></td>
		<td align="center"><%=Argument.getPreflag(pre_flag)%></td>
		<td align="center">
        <%if(input_operator.hasFunc(menu_id, 103)&&mend_flag.intValue()!=2){%>
	    	<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:disableAllBtn(true);showInfo(<%=serial_no%>);">&gt;&gt;</button>
        <%}%>
		</td>
	</tr>
<%}
for(int i=0; i<t_sPagesize-iCount; i++){%>  
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
      </tr>           
<%}%>   	
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="11"><b>&nbsp;合计&nbsp;<%=pageList.getTotalSize()%>&nbsp;项</b></td>			
	</tr>	
</table>
</div>
<br>
<div><%=pageList.getPageLink(sUrl,clientLocale)%></div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>