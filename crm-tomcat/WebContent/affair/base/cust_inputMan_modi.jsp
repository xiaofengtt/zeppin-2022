<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得参数
String no = Utility.trimNull(request.getParameter("no"));
String name = Utility.trimNull(request.getParameter("name"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));

//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
//设置参数
vo.setCust_no(no);
vo.setCust_name(name);
vo.setCheck_flag(check_flag);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setProv_level(prov_level);
vo.setCust_type(cust_type);
vo.setIs_deal(is_deal);

//页面变量
Map map = null;
IPageList pageList =
	customer.listProcAll(
		vo,
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
String tempUrl = "&name=" + name + "&no=" + no + "&product_id=" + product_id  + "&is_deal=" + is_deal + "&cust_type=" + cust_type ;
//url设置
sUrl = sUrl + tempUrl;

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
var oState = {
		
	}
window.onload = function(){
	initQueryCondition();

};
function getCondition()
{
	//组合查询条件
	var iQuery = document.theform.no.value 
					+ "$" + document.theform.name.value 
					+ "$" + document.theform.product_id.value
					+ "$" + document.theform.check_flag.value
					+ "$" + "<%=sPage%>" 
					+ "$" + document.theform.pagesize.value
					+ "$" + document.theform.cust_type.value;
	return iQuery;
}

/**客户信息编辑**/
function UpdateAction()
{

	if(checkedCount(document.theform.cust_id) == 0){
		sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%>！");//请选择要修改的客户
		return false;
	}else{
		oState.show  = "true";
		var url = "<%=request.getContextPath()%>/affair/base/cust_inputMan_modiAction.jsp?cust_id=" + checkedValue(document.theform.cust_id);
		if(showModalDialog(url,oState, 'dialogWidth:200px;dialogHeight:150px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
}

function StartQuery()
{
	refreshPage();
}

function refreshPage()
{
	disableAllBtn(true);
	
	var url = 'cust_inputMan_modi.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
				+'&name=' + document.theform.name.value 
				+ '&no=' + document.theform.no.value
				+ '&product_id=' + document.theform.product_id.value
				+ "&check_flag=" + document.theform.check_flag.value
				+ "&cust_type=" + document.theform.cust_type.value
	location = url;				
}

function setProduct(value)
{
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value)
{
	prodid=0;
	if (value != "")
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function callCust(target_custid){

	<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>	
	document.parentWindow.parent.document.getElementById("target_custid").value = target_custid; 
	document.parentWindow.parent.document.getElementById("callTalkType").value = 1;
	document.parentWindow.parent.document.getElementById("callcenterLink").onclick();
	<%}%>
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

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
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
<form name="theform" method="post" action="cust_inputMan_modia.jsp">
<input type="hidden" name="file_name" id="file_name"value="">
<input type="hidden" name="fieldDatas" value="">
<input type="hidden" name="fieldMethod" value="">
<input type="hidden" name="fieldDataType" value="">
<div id="queryCondition" class="qcMain"
	style="display: none; width: 475px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
		<td valign="bottom" align="left">
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
			<button type="button" class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td align="right">产品名称 :</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button" class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--产品选择-->
		<td align="left" colspan=3 id="select_id">
			<SELECT name="product_id"	class="productname" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
		<td valign="bottom" align="left">
			<input name="no" value='<%=no%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left">
			<input name="name" value='<%=name%>' onkeydown="javascript:nextKeyPress(this)" size="18" maxlength="100">
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
		<td  align="left" >
			<select size="1" name="cust_type" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<%=Argument.getCustTypeOptions(cust_type) %>
			</select>
		</td>
		<td  align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<%=Argument.getCheckFlagOptions(check_flag)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button" class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan=2><img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
					</tr>
					<tr>
						<td align="right">		
							<button type="button" class="xpbutton3" accessKey=q id="queryButton"
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
								&nbsp;&nbsp;&nbsp;
							<button type="button" class="xpbutton3" id="modiButton" name="modiButton" title="<%=LocalUtilis.language("message.update",clientLocale)%> " onclick="javascript:UpdateAction();"><%=LocalUtilis.language("message.update",clientLocale)%></button>
								&nbsp;&nbsp;<!-- 修改 -->
						</td>
					</tr>
					<tr>
						<td colspan=2>
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="120px">
								<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
								<%=LocalUtilis.language("class.ID",clientLocale)%>
						 </td><!--编号-->
						<td align="center"><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center">客户等级</td><!--客户等级-->
<%}else{ %>
						<td align="center"><%=LocalUtilis.language("class.custType",clientLocale)%> </td><!--客户类型-->
<%} %>
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
						<td align="center"><%=LocalUtilis.language("class.customerSource",clientLocale)%></td><!--客户来源-->
						<td align="center"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> </td><!--风险等级-->	
						<td align="center"><%=LocalUtilis.language("class.inputMan",clientLocale)%></td><!-- 录入人-->	
						<td align="center"><%=LocalUtilis.language("message.contactInfo",clientLocale)%></td><!--联系信息-->	
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						Integer cust_id = new Integer(0);
						String is_deal_name = "";
						Integer is_deal2;
						while (it.hasNext()) {
							map = (Map) it.next();
							is_deal2 = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
							is_deal_name = Argument.getWTName(is_deal2);
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="cust_id" value="<%=map.get("CUST_ID")%>" class="flatcheckbox"></td>
								<td width="90%" align="left">&nbsp;&nbsp;<%=Utility.trimNull(map.get("CUST_NO"))%></td>
							</tr>
						</table>
						</td>
						<td align="left"><input type="text" class="ednone"  style="width:100%" value="<%=Utility.trimNull(map.get("CUST_NAME"))%>" readonly></td>
						<td align="right"><input type="text" class="ednone" style="direction:rtl" style="width:120px" value="<%=Utility.trimNull(map.get("CARD_ID"))%>" readonly></td>
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center"><%=Utility.trimNull(map.get("CUST_LEVEL_NAME"))%></td><!--客户等级-->
<%}else{ %>
						<td align="center"><%=is_deal_name%></td>
<%} %>
						<td align=center><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_SOURCE_NAME"))%></td>
						<td align="center">&nbsp;<%=Utility.trimNull(map.get("GRADE_LEVEL_NAME"))%></td>	
						<td align="center">&nbsp;<%=Argument.getManager_Name(Utility.parseInt(Utility.trimNull(map.get("INPUT_MAN")),new Integer(0)))%></td>	
			             <td align="center">
			             	<button type="button" class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=map.get("CUST_ID")%>);">
			         			<IMG id="detailsImage<%=map.get("CUST_ID")%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
				         	</button>
				         	<input type="hidden" id="detailsFlag_display<%=map.get("CUST_ID")%>" name="detailsFlag_display<%=map.get("CUST_ID")%>" value="0">             
			             </td>		
					</tr>

					<tr id="detailsTr<%=map.get("CUST_ID")%>" style="display: none">
						<td align="center" bgcolor="#FFFFFF" colspan="17" >
							<div id="detailsDiv<%=map.get("CUST_ID")%>" style="overflow-y:auto;" align="center">
								<table id="detailsTable<%=map.get("CUST_ID")%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;" >
										<td align="center"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> ：</td><!--公司电话-->
										<td  width="*"><a href="#" class="a2" onclick="javascript:callCust(<%=map.get("CUST_ID")%>)"><%=Utility.trimNull(map.get("O_TEL"))%></a></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center"><%=LocalUtilis.language("class.mobile",clientLocale)%> ：</td><!--手机号码-->
										<td  width="*"><a href="#" class="a2" onclick="javascript:callCust(<%=map.get("CUST_ID")%>)"><%=Utility.trimNull(map.get("MOBILE"))%></a></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center"><%=LocalUtilis.language("class.email",clientLocale)%> ：</td><!--电子邮件-->
										<td  width="*"><%=Utility.trimNull(map.get("E_MAIL"))%></td>
									</tr>
									<tr style="background:F7F7F7;">
										<td  width="120px" align="center"><%=LocalUtilis.language("class.postcode",clientLocale)%> ：</td><!--邮政编码-->
										<td  width="*"><%=Utility.trimNull(map.get("POST_CODE"))%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td  width="120px" align="center"><%=LocalUtilis.language("class.postAddress3",clientLocale)%> ：</td><!--通讯地址-->
										<td  width="*"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>  
					<%iCurrent++;
					iCount++;
					}%>

					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%customer.remove();%>