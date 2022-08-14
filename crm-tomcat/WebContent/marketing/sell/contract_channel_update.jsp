<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.customer.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
String totalValueStr = "RG_MONEY";

//获得对象
StringBuffer custIdSAll = new StringBuffer();
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo2 = new CustomerVO();


String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
Integer q_pre_flag= Utility.parseInt(request.getParameter("q_pre_flag"),new Integer(0));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),new Integer(0));
Integer q_check_flag=Utility.parseInt(request.getParameter("q_check_flag"),new Integer(0));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_card_id = Utility.trimNull(request.getParameter("q_card_id"));
String query_contract_bh = Utility.trimNull(request.getParameter("query_contract_bh"));
BigDecimal min_rg_money = Utility.parseDecimal(request.getParameter("min_rg_money"), new BigDecimal(0),2,"1");//最低登记额度
BigDecimal max_rg_money = Utility.parseDecimal(request.getParameter("max_rg_money"), new BigDecimal(0),2,"1");//最高登记额度
Integer q_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_cust_type")),new Integer(0));
Integer q_group_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_group_id")),new Integer(0));
Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_class_detail_id")),new Integer(0));
Integer q_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_channel_id")),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
Integer q_managerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_managerID")),new Integer(0));

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
boolean print_flag = false;//打印标志
String tempUrl = "";
String[] totalColumn = {"RG_MONEY"};
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
List list = null;
Map map = null;

//url设置
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_pre_flag="+q_pre_flag;
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&q_check_flag="+q_check_flag;
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&q_card_id="+q_card_id;
tempUrl = tempUrl+"&query_contract_bh="+query_contract_bh;
tempUrl = tempUrl+"&max_rg_money="+max_rg_money;
tempUrl = tempUrl+"&min_rg_money="+min_rg_money;
tempUrl = tempUrl+"&q_cust_type="+q_cust_type;
tempUrl = tempUrl+"&q_class_detail_id="+q_class_detail_id;
tempUrl = tempUrl+"&q_group_id="+q_group_id;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
tempUrl = tempUrl+"&q_managerID="+q_managerID;
sUrl = sUrl + tempUrl;

//获得对象及结果集
ContractLocal contract = EJBFactory.getContract();
ContractVO vo = new ContractVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_code(q_productCode);
vo.setPre_flag(q_pre_flag);
vo.setProduct_id(q_productId);
vo.setCheck_flag(q_check_flag);
vo.setCust_name(q_cust_name);
vo.setCard_id(q_card_id);
vo.setContract_sub_bh(query_contract_bh);
vo.setMax_rg_money2(max_rg_money);
vo.setMin_rg_money2(min_rg_money);
vo.setCust_type(q_cust_type);
vo.setClassdetail_id(q_class_detail_id);
vo.setCust_group_id(q_group_id);
vo.setChannel_id(q_channel_id);
vo.setProduct_name(q_product_name);
vo.setManagerID(q_managerID);

IPageList pageList = contract.queryPurchanseContract_crm(vo,totalColumn,t_sPage,t_sPagesize);
list = pageList.getRsList();

int rgCustCount = 0;
if(list.size()>0){
	print_flag = true;
}

List listAll = contract.queryPurchanseContract_crm(vo);
for(int i=0;i<listAll.size();i++){
	Map mapAll = (Map)listAll.get(i);
	int cust_type = Utility.parseInt(Utility.trimNull(mapAll.get("CUST_TYPE")),0);
	BigDecimal rgMoney = Utility.parseDecimal(Utility.trimNull(mapAll.get("RG_MONEY")),new BigDecimal(0.00),2,"1");
	if(cust_type==1 && rgMoney.intValue()<3000000){
		rgCustCount++;
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.subscription",clientLocale)%> </TITLE><!--认购-->
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
/*启动加载*/
window.onload = function(){
	initQueryCondition();
	var q_cust_type = document.getElementById("q_cust_type").value;
	var tdId = "td"+q_cust_type;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function getSQuery(){
	var sQuery = document.theform.q_productId.value ;
	var sQuery = sQuery + "$" +document.theform.q_productCode.value;
	var sQuery = sQuery + "$" +document.theform.q_cust_name.value;
	var sQuery = sQuery + "$" +document.theform.query_contract_bh.value;
	var sQuery = sQuery + "$" +document.theform.q_pre_flag.value;
	var sQuery = sQuery + "$" +document.theform.pagesize.value;
	var sQuery = sQuery + "$" +document.theform.q_check_flag.value;
	var sQuery = sQuery + "$" +document.theform.q_product_name.value;
	var sQuery = sQuery + "$" +document.theform.q_managerID.value;

	return sQuery;
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}



/*修改渠道信息*/
function showInfo(serialno){
	disableAllBtn(true);
	location = 'contract_channel_update_action.jsp?serial_no='+serialno;

}


/*刷新*/
function refreshPage(){
	disableAllBtn(true);
	var url = "contract_channel_update.jsp?page=1&pagesize="+ document.theform.pagesize.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&q_pre_flag=' + document.theform.q_pre_flag.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_card_id=' + document.theform.q_card_id.value;
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;
	var url = url + '&max_rg_money=' +  document.theform.max_rg_money.value;
	var url = url + '&min_rg_money=' +  document.theform.min_rg_money.value;
	var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
	var url = url + '&q_group_id=' +document.theform.q_group_id.value;
	var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	var url = url + '&q_channel_id=' +document.theform.q_channel_id.value;
	var url = url + '&q_product_name='+document.theform.q_product_name.value;
	var url = url + '&q_managerID=' +document.theform.q_managerID.value;
	
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
				StartQuery();
				break;
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}

	nextKeyPress(this);
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;

		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}

		document.theform.q_productCode.focus();
	}
}


function changeCustType(flag){
	document.getElementById("q_cust_type").value=flag;
	refreshPage();
}

</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="subscribe_del.jsp">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:600px;">
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
		<td align="right"><%=LocalUtilis.language("class.applyFlag",clientLocale)%> :</td><!--认购方式-->
		<td align="left">
			<select size="1" name="q_pre_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 110px">
				<%=Argument.getPreFlagOptions(q_pre_flag)%>
			</select>
		</td>
		<td  align="right">	<%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td  align="left" >
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="15" value="<%=query_contract_bh%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td><input   name="q_cust_name" value="<%=q_cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="15"/></td>
		<td  align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td  align="left">
			<input type="text" name="q_card_id" value="<%=q_card_id%>" onkeydown="javascript:nextKeyPress(this);"  size="23">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.regMoney",clientLocale)%> :</td><!--登记额度-->
		<!--从-->
        <td colspan="3">
        		<%=LocalUtilis.language("message.start",clientLocale)%>
        		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_rg_money" size="20"
				value="<%=Utility.trimNull(min_rg_money)%>" />
                <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_rg_money" size="20"
				value="<%=Utility.trimNull(max_rg_money)%>" />
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="q_check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<%=Argument.getCheckFlagOptions(q_check_flag)%>
			</select>
		</td>
		<td align="right">渠道来源:</td><!--渠道来源-->
		<td>
			<select size="1" name="q_channel_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
				<%=Argument.getChannelOptions(input_bookCode,"","",q_channel_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td>
		<td >
			<input type="text" name="q_product_name" size="25" value="<%=Utility.trimNull(q_product_name)%>"/>
		</td>
		<td  align="right">客户经理:</td>
		<td  align="left">
			<select size="1" name="q_managerID" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<%=Argument.getManager_Value(q_managerID)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="center" colspan=4>
		<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->
(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<!--客户分析--><!--客户综合查询-->
            <td colspan="2"><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b> <%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("message.subscription",clientLocale)%> </b></font><!--销售管理--><!--认购--></td>
		</tr>
		<tr>
			<td><font color="bulue" size="3">小于3百万的客户数：</font><font color="red" size="4"><%=rgCustCount%>个</font></td>
			<td align=right>
				<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)<!--查询--></button>&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<hr noshade color="#808080" size="1">
			</td>
		</tr>
	</table>
	
</div>

<div id="headDiv" style="margin-top:5px">

<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
		<tr style="background:F7F7F7;">
			<td width="50px" align="center" id="td0" <%if (q_cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
			<!--全部-->
			<td width="50px" align="center" id="td1" <%if (q_cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
			<!--个人-->
			<td width="50px" align="center" id="td2" <%if (q_cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
			<!--机构-->
			<td>
				<select size="1" colspan="3"name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">
					<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
				</select>
			</td>
			<td>
				<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
			</td>

			<!--产品-->
			<td align="center" id="td4">
				<select name="q_group_id" id="q_group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
					<%=Argument.getCustGroupOption2(new Integer(0),q_group_id)%>
				</select>
			</td>
			<!--客户群组-->
			<td align="center" id="td5">
				<select name="q_class_detail_id" id="q_class_detail_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
					<option>请选择关注度</option>
					<%=Argument.getCustClassOption(new Integer(30),q_class_detail_id)%>
				</select>
			</td>
			<!--关注度-->
		</tr>
</table>
</div>

<%if(user_id.intValue()==16){ %>
<div style="overflow:auto;width:1100;height: 300;">
<%} %>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="98%" style="table-layout:fixed">
	<tr class=trh>
		<td align="center" width="40%">
			<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
			产品名称
		</td>

		<td align="center" width="">合同编号</td>
		<td align="center" width="">客户名称</td>
		<td align="center" width="">认购金额</td>
		<td align="center" width="">签署日期</td>
		<td align="center" width="4%;"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
	</tr>

<%
int iCount = 0;
int iCurrent = 0;
String ht_status = "";
Integer pre_flag;
Integer check_flag;
String bank_id;
for(int k=0;k<list.size();k++)
{
	map = (Map) list.get(k);
	Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	ht_status = Utility.trimNull(map.get("HT_STATUS"));
	pre_flag = Utility.parseInt(Utility.trimNull(map.get("PRE_FLAG")),new Integer(0));
	check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
	bank_id  = Utility.trimNull(map.get("BANK_ID"));
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	String cust_name = Utility.trimNull(map.get("CUST_NAME"));
	BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0.00));
	Integer qs_date = Utility.parseInt(Utility.trimNull(map.get("QS_DATE")),new Integer(0));
%>
        <tr  class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="left" >
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="9%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
					<td width="91%" align="left"><%=product_name%></td>
				</tr>
			</table>
			</td>
	
			<td align="left" ><%=Utility.trimNull(contract_sub_bh) %></td>
			<td align="left" ><%=Utility.trimNull(cust_name) %></td>
			<td align="right" ><%=Format.formatMoney(rg_money) %></td>
			<td align="center" ><%=Format.formatDateLine(qs_date) %></td>
			<td align="center">
				<%if (input_operator.hasFunc(menu_id, 100)) {%>
					<a href="javascript:showInfo(<%=serial_no%>);">
		           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
		           	 </a>
				<%}%>
			</td>
		</tr>
<%iCurrent++;} 
for(int j=0;j < pageList.getBlankSize(); j++){
%>		
		<tr class="tr<%=(j % 2)%>">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
<%} %>		
		<tr class="trbottom">
			
			<!--合计--><!--项-->
            <td class="tdh" align="left" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		
			<td align="center" >-</td>
		
			<td align="center" >-</td>
			<td align="right" ><%=Format.formatMoney(pageList.getTotalValue("RG_MONEY"))%></td>
			<td align="center" >-</td>	
			<td align="center" >-</td>		
		</tr>
	</table>
	<br>
<%if(user_id.intValue()==16){ %></div><%} %>
<br>
<div>
	<%=pageList.getPageLink(sUrl,clientLocale)%>
<input type="hidden" name="t2" value="<%=custIdSAll%>">
</div>
<%contract.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
