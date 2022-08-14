<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//帐套暂时设置
input_bookCode = new Integer(1);
//获得页面传递变量
Integer product_id        = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer sub_product_id   = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String  cust_name        = Utility.trimNull(request.getParameter("cust_name"));
String  product_code     = Utility.trimNull(request.getParameter("product_code"));
String  sub_product_name = Utility.trimNull(request.getParameter("sub_product_name"));
Integer check_flag       = Utility.parseInt(request.getParameter("check_flag"),new Integer(11));
Integer sub_flag 		= new Integer(0);

int     back_flag        = Utility.parseInt(request.getParameter("back_flag"),0);
String  sQuery           = Utility.trimNull(request.getParameter("sQuery"));

if(back_flag == 1){
	String[] ss = sQuery.split("\\$");

	product_code = ss[0];
	product_id = ss.length>1?Integer.valueOf(ss[1]):new Integer(0);
	cust_name = ss.length>2?ss[2]:"";
	check_flag = ss.length>3?Integer.valueOf("".equals(ss[3])?"0":ss[3]):new Integer(11);
}

//页面辅助参数
String tempUrl = "";
int iCount = 0;
int iCurrent = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
List list = null;
Map map = null;

//url设置
tempUrl = tempUrl+"&product_id="+product_id;
tempUrl = tempUrl+"&sub_product_id="+sub_product_id;
tempUrl = tempUrl+"&cust_name="+cust_name;
tempUrl = tempUrl+"&product_code="+product_code;
tempUrl = tempUrl+"&sub_product_name="+sub_product_name;
tempUrl = tempUrl+"&check_flag="+check_flag;
sUrl = sUrl + tempUrl;

//获得产品SUB_FLAG
if(product_id.intValue()!=0){
	sub_flag = Utility.parseInt(Argument.getProductFlag(product_id,"SUB_FLAG"),new Integer(0));
}

//获得对象及结果集
TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
TMoneyDataVO vo = new TMoneyDataVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setCheck_flag(check_flag);
vo.setCust_name(cust_name);

IPageList pageList  = moneyData.listForPage(vo,totalColumn,t_sPage,t_sPagesize);
list = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,17);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE>认/申购资金录入初审</TITLE>
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language=javascript>
var sQuery = '';
/*启动加载*/
window.onload = function(){
	initQueryCondition();
};

function spellQuery(){
	sQuery = document.theform.product_code.value + "$" +document.theform.product_id.value + "$" + document.theform.cust_name.value + "$" +document.theform.check_flag.value + "$" + document.theform.pagesize.value;
}

function checkAction(serialno,checkflag){
	spellQuery();
	location = 'sub_capital_entering_add.jsp?serial_no='+serialno+'&check_flag=2'+'&action_from=4&sQuery=' + sQuery;
}

function StartQuery(){
	spellQuery();
  	document.theform.btnQuery.disabled = true;
	disableAllBtn(true);

	var sub_product_id = 0;
	if(document.theform.sub_product_id) sub_product_id = document.theform.sub_product_id.value;

	location = 'sub_capital_entering_preliminary_check.jsp?page=1&pagesize=' + document.theform.pagesize.value 
			+'&product_code='+document.theform.product_code.value
			+'&product_id='+ document.theform.product_id.value 
			+'&cust_name='+document.theform.cust_name.value 
			+'&check_flag='+document.theform.check_flag.value
			+"&sub_product_id="+sub_product_id
			+'&sQuery=' + sQuery;
}

function refreshPage(){
	StartQuery();
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}
		}
		if (prodid==0){
			sl_alert('输入的产品编号不存在！');
			document.theform.product_code.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
}

function searchProduct(value){
	prodid=0;
	if (value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('输入的产品编号不存在！');
			document.theform.product_code.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function selectProductItem(product_id){
	if(product_id == "0"){
		document.getElementById("sub_product").style.display = "none";
		return false;
	}
	utilityService.getSubProductList(product_id,3,callback);
}  

function callback(data){
	var span = document.getElementById("sub_product_span");
	var display = "none";
	span.innerHTML  = "<select size='1' name='sub_product_id' onkeydown='javascript:nextKeyPress(this)' class='productname'>"+data+"</select>";
	if(data != "<option value=\"\">请选择</option>")display = "";

	document.getElementById("sub_product").style.display = display;
}	 

function check_confim(){
	spellQuery();
	if(!confirmCheck(document.theform.serial_no)) return false;
	document.theform.action = "sub_capital_entering_preliminary_check_do.jsp?sQuery=" + sQuery;
	document.theform.checkflag.value="2";
	document.theform.submit();
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:selectProductItem(this.value);'>"+"<%=options%>"+"</SELECT>";
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
			document.theform.product_code.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
} 
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">

<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<input type="hidden" name="checkflag" value="">
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
		   <td>查询条件 ：</td>
		   <td align="right">
		  	 <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"   onclick="javascript:cancelQuery();"></button>
		   </td>
		</tr>
	</table>

	<table>
	<tr>
		<td align="right">产品编号:</td>
		<td><input type="text" name="product_code" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="15">
		&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.product_code.value);" /></button></td>
		<td align="right">产品名称 :</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
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
	<tr id="sub_product" style="<%if(sub_flag.intValue() == 1){out.print("display:");}else{out.print("display:none");} %>">	
		<td align="right">子产品选择:</td>
		<td align=left colspan=3>
			<div id="sub_product_span">
				<select size="1" name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions2(product_id, new Integer(0),sub_product_id,3)%> 
				</select>&nbsp;&nbsp;&nbsp;
			</div>
		</td>
	</tr>
	<tr>
	<td  align="right">客户名称:</td><td>
		<input name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="15">
	</td>
	<td  align="right">审核状态:</td>
	<td  align="left" >
	<select size="1" name="check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
			<option value="11" <%if(check_flag.intValue()==11) out.println("selected");%>>未初审</option>
			<option value="1" <%if(check_flag.intValue()==1) out.println("selected");%>>已初审</option>
		</select></td>
	</tr><tr>
		<tr>	
			<td  align="center" colspan=4>
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button>
			</td>
		</tr>
	</table>
</div>

<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b>认/申购资金录入初审</b></font>
	</div>

	<div align="right">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 		
	    <%if (input_operator.hasFunc(menu_id, 103)){%>
	    <button type="button"  class="xpbutton3" onclick="javascript:check_confim();">初审通过</button>&nbsp;&nbsp;&nbsp;
	    <%}%>				
	</div>
	<hr noshade color="#808080" size="1">
</div>

<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			<td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;产品编号</td>
			<td align="center" >产品名称</td>
			<td align="center" >客户编号</td>
			<td align="center" >客户名称</td>
			<td align="center"  sort="num">认/申购金额</td>
			<td align="center" >到账日期</td>
			<td align="center" >状态</td>					
			<td align="center" >查看</td>
		</tr>
<%
//声明变量
Integer serial_no = new Integer(0);
String product_name = "";
String product_code2 = "";
String sub_product_name2 = "";
String cust_no = "";
String cust_name2 = "";
BigDecimal to_money = new BigDecimal(0);
Integer jk_date2 = new Integer(0);
Integer check_flag2 = new Integer(0);
Integer complete_flag = new Integer(0);
 
Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();

	if(map!=null){			
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		product_code2 = Utility.trimNull(map.get("PRODUCT_CODE"));
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		sub_product_name2 = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));		
		cust_no = Utility.trimNull(map.get("CUST_NO"));
		cust_name2 = Utility.trimNull(map.get("CUST_NAME"));
		to_money = Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),null).setScale(2,2);
		jk_date2 = Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0));
		check_flag2 = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(11));
		complete_flag = Utility.parseInt(Utility.trimNull(map.get("COMPLETE_FLAG")),new Integer(0));
		
	}
%>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td class="tdh" align="center" >
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="10%"> <%if (check_flag2.intValue()==11){%><input  type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"> <%}%></td>
				<td width="90%" align="center">
				<%=Utility.trimNull(product_code2)%></td>
			</tr>
		</table>
		</td>
		<td align="left" >&nbsp;<%=Utility.trimNull(sub_product_name2).equals("")?Utility.trimNull(product_name):Utility.trimNull(product_name)+"("+Utility.trimNull(sub_product_name2)+")"%></td>
		<td align="center"><%=Utility.trimNull(cust_no)%></td>
		<td align="left" ><%=Utility.trimNull(cust_name2)%></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(to_money))%></td>
		<td align="center" ><%=Format.formatDateCn(jk_date2)%></td>
		<td align="center" ><%=check_flag2.intValue()==11?"未初审":"已初审"%></td>		
		<td align="center" >
		<%if (input_operator.hasFunc(menu_id, 103)) {%>
		<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:disableAllBtn(true);checkAction('<%=serial_no%>','<%=check_flag2%>');">&gt;&gt;</button>
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
      </tr>           
<%}%>   	
	<tr class="trbottom">
		<td class="tdh" align="left"  colspan="8" ><b>&nbsp;合计&nbsp;<%=pageList.getTotalSize()%>&nbsp;项</b></td>
							
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


