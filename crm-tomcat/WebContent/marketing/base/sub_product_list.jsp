<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//获得页面传递变量
Integer q_productId = Utility.parseInt(Utility.trimNull(request.getParameter("q_productId")),new Integer(0));
String q_productCode = Utility.trimNull(request.getParameter("q_productCode"));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
String sub_product_status = Utility.trimNull(request.getParameter("sub_product_status"));
//帐套暂时设置
input_bookCode = new Integer(1);
//页面辅助参数
String tempUrl = "";
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
Map map = null;
//url设置
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
//获得对象及结果集
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

vo.setProduct_id(q_productId);
vo.setProduct_code(q_productCode);
vo.setProduct_name(q_product_name);
vo.setProduct_status(sub_product_status);

IPageList pageList = product.listSubProductForPage(vo,t_sPage,t_sPagesize);
list = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
	initQueryCondition();
};
/*刷新*/
function refreshPage(){
	disableAllBtn(true);
		
	var url = "sub_product_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_product_name=' + document.theform.q_product_name.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&sub_product_status=' + document.theform.sub_product_status.value;
	location = url;	
}
/*查询功能*/
function StartQuery(){
	refreshPage();
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
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！");//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
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
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！");//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='q_productId' class='productname' onkeydown='javascript:nextKeyPress(this)' colspan='3'>"+"<%=options%>"+"</SELECT>";
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
			document.theform.q_productCode.value="";
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
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	<tr>
		<td>查询条件：</td>
	   	<td align="right">
	   		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
	   	</td>
  	</tr>
</table>

<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
    	<td align="right">产品编号:</td >
        <td align="left">
			<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
        </td >
        <td align="right">产品名称 :</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
    </tr>
	<tr>
	    <td align="right">产品选择:</td >
	    <td align="left" colspan="3" id="select_id">
				<select size="1" colspan="3"name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname">					
					<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
				</select>
	    </td>
	</tr>
	<tr>
		<td align="right">子产品名称:</td>
		<td>
				<input name="q_product_name" value="<%=Utility.trimNull(q_product_name)%>" size="25" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right">子产品状态:</td>
		<td>
				<select name="sub_product_status" style="width:122">
					<%=Argument.getProductAllStatusOptions(sub_product_status)%>
				</select>
		</td>
	</tr>
	<tr>						
		<td align="right" colspan="3">
			<button type="button"  class="xpbutton3" accessKey=s name="btnQuery"onclick="javascript:StartQuery();">确定(<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
		</td>
	</tr>					
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
	</div>
	<br/>
</div>

<div style="margin-top:5px">
	<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
			<tr class="trh">
				<td align="center" height="25">产品名称</td>
                <td align="center">子产品名称</td>
				<td align="center" height="25">产品期数</td>
				<td align="center" height="25">预期发行金额</td>
				<td align="center" height="25">期限</td>
				<td align="center" height="25">收益率</td>
				<td align="center" height="25">发行份数</td>
				<td align="center" height="25">业绩基准</td>
				<td align="center" height="25">开始日期</td>
			</tr>
<%
	Iterator iterator = list.iterator();
	while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();

		BigDecimal exp_rate1 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")), new BigDecimal(0.00),2,"100");
		BigDecimal exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")), new BigDecimal(0.00),2,"100");
		BigDecimal result_standrad = Utility.parseDecimal(Utility.trimNull(map.get("RESULT_STANDARD")), new BigDecimal(0));
		Integer start_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
		Integer valid_period = Utility.parseInt(Utility.trimNull(map.get("VALID_PERIOD")),new Integer(0));
	    Integer period_unit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
		String product_period = "";
	    if(period_unit.intValue() == 0)
	       product_period = "无固定期限";
	    else if(period_unit.intValue() == 1)
	       product_period = valid_period + "天";
	    else if(period_unit.intValue() == 2)
	       product_period = valid_period + "月";
	    else if(period_unit.intValue() == 3)
	       product_period = valid_period + "年";
 %>
		<tr class="tr<%=(iCount%2)%>">
			<td class="tdh" align="left"><%=Utility.trimNull(map.get("PRODUCT_NAME")) %></td>
			<td align="left" ><%=Utility.trimNull(map.get("LIST_NAME")) %></td>
			<td align="center" ><%=Utility.trimNull(map.get("LIST_ID")) %></td>
			<td align="right" ><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00),2,"1"))%>万元</td>
			<td align="center" ><%=product_period%></td>
			<td align="right" ><%=Utility.trimZero(exp_rate1)%>%-<%=Utility.trimZero(exp_rate2)%>%</td>
			<td align="right" ><%=Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0)) %></td>
			<td align="right" ><%=Format.formatMoney(result_standrad)%></td>
			<td align="center" ><%=Format.formatDateCn(start_date) %></td>
		</tr>
<% }%>

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
         <td align="center"></td>
         <td align="center"></td>     
	</tr>		
	</table>
</div>
<br>
<div><%=pageList.getPageLink(sUrl,clientLocale)%></div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
