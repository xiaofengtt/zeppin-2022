<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String from_cust_name = Utility.trimNull(request.getParameter("from_cust_name"));//转让人名
String to_cust_name = Utility.trimNull(request.getParameter("to_cust_name"));//受益人名
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));//合同编号
Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);//产品编号
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"), new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"), new Integer(0));
String[] totalColumn = {"TO_AMOUNT"};

BenChangeLocal local = EJBFactory.getBenChanage();
BenChangeVO vo = new BenChangeVO();

vo.setBook_code(new Integer(1));
vo.setProduct_id(product_id);
//vo.setContract_bh(contract_bh);
vo.setInput_man(input_operatorCode);
vo.setTrans_date_begin(begin_date);
vo.setTrans_date_end(end_date);
vo.setFrom_cust_name(from_cust_name);
vo.setTo_cust_name(to_cust_name);
vo.setContract_sub_bh(contract_bh);

IPageList pageList = local.listAll(vo,totalColumn, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();

sUrl += "&from_cust_name=" + from_cust_name + "&to_cust_name=" + to_cust_name
			+ "&contract_bh=" + contract_bh + "&product_id=" + product_id
			+ "&begin_date=" + begin_date + "&end_date=" + end_date;

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");

local.remove();
%>
<html>
<head>
<title><%=LocalUtilis.language("message.benTrans",clientLocale)%> </title><!--受益权转让-->
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
window.onload = function(){
		initQueryCondition();
	};

//分页
function refreshPage() {
	disableAllBtn(true);
	location.search = '?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value
				+ '&from_cust_name=' + document.theform.from_cust_name.value
				+ '&to_cust_name=' + document.theform.to_cust_name.value
				+ '&contract_bh=' + document.theform.contract_bh.value
				+ '&product_id=' + document.theform.product_id.value
				+ '&begin_date=' + document.theform.begin_date.value
				+ '&end_date=' + document.theform.end_date.value;
}

//查询
function StartQuery() {
	syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	refreshPage();
}

//新建
function newInfo(){
	location = "beneficial_transfer_add.jsp";
}

//查看
function showInfo(value,check_flag){
	if (check_flag==2 ) {
		location.href = "beneficial_transfer_info_check.jsp?serial_no="+value+"&product_id="+document.theform.product_id.value+"&contract_bh="+document.theform.contract_bh.value + "&first=true";
	}	
	else{
		location.href = "beneficial_transfer_info.jsp?serial_no="+value+"&product_id="+document.theform.product_id.value+"&contract_bh="+document.theform.contract_bh.value + "&first=true";
	}	
} 

//产品编号回车
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

//产品编号查看
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
	}
	nextKeyPress(this);
}

//展示事务
function showprojectinfo(problem_id,project_id,flag,cust_id){
	//layout.getRegion('west').hide();
	var url = '<%=Utility.trimNull(application.getAttribute("INTRUST_ADDRESS"))%>/k2/logintrust.jsp?problem_id='+problem_id+'&uid=<%=input_operator.getLogin_user()%>';
	location = url;
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
</head>
<body class="BODY body-nox">
<form name="theform" action="beneficial_transfer_remove.jsp" method="post">
<!--查询条件-->
<div id="queryCondition" class="qcMain" style="display: none; width: 550px">
<table id="qcTitle" class="qcTitle" align="center" width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--查询条件-->
		<td align="right">
			<button type="button"  class="qcClose" accesskey="c" id="qcClose" name=qcClose onclick="javascript:cancelQuery();"/>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellpadding="2" cellspacing="2">
	<tr>
		<td align="right"><%=LocalUtilis.language("class.fromCustName2",clientLocale)%> :</td><!--转让方-->
		<td>
			<input name="from_cust_name" size="20" value="<%=from_cust_name%>" onkeydown="javascript:nextKeyPress(this);">
		</td>
		<td align="right"><%=LocalUtilis.language("class.toCustName2",clientLocale)%> :</td><!--受让方-->
		<td>
			<input name="to_cust_name" size="20" value="<%=to_cust_name%>" onkeydown="javascript:nextKeyPress(this);">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td>
			<input name="contract_bh" size="20" value="<%=contract_bh%>" onkeydown="javascript:nextKeyPress(this);">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
		<td>
			<input name="productid" size="10" onkeydown="javascript:setProduct(this.value);">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct(theform.productid.value)"></button>
		</td>
		<td  align="right">产品名称 :</td>
		<td>
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--产品选择-->
		<td colspan="3" id="select_id">
			<select name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this);">
				<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> :</td><!--开始日期-->
		<td>
			<INPUT TYPE="text" NAME="begin_date_picker" value="<%=Format.formatDateLine(begin_date)%>">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="begin_date"   value="">	
		</td>
		<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--结束日期-->
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" value="<%=Format.formatDateLine(end_date)%>">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="end_date"   value="">	
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4">
			<button type="button"  class="xpbutton3" id="btnQuery" accesskey="O" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确认-->
		</td>
	</tr>
</table>
</div>
<!--相关按钮-->
<table width="100%" border="0">
	<tr>
		<td class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">
		<div class="btn-wrapper">
			<button type="button"  class="xpbutton3" accessKey=q  id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;<!--查询-->
			<%if (input_operator.hasFunc(menu_id, 100)) {%>
            <!--新建记录--><!--新建-->
			<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" 
				title='<%=LocalUtilis.language("message.newRecord",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>&nbsp;&nbsp;&nbsp; <%}%> 
			<%if (input_operator.hasFunc(menu_id, 101)) {%>
            <!--删除所选记录--><!--删除-->
			<button type="button"  class="xpbutton3"  accessKey=d id="btnDelete" name="btnDelete" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> '
				onclick="javascript:if(confirmRemove(document.theform.selectbox)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>&nbsp;&nbsp;&nbsp; <%}%>
			</div>
		</td>
	</tr>
</table>
<!--内容-->
<table border="0" width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<td>
			<table width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
				<tr class="trh">
					<td align="center" >
						<input type="checkbox" name="btnCheckbox" class="selectAllBox"
						 onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
					<td align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.fromCustName2",clientLocale)%> </td><!--转让方-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.toCustName2",clientLocale)%> </td><!--受让方-->
					<td align="center" ><%=LocalUtilis.language("class.exchangeType",clientLocale)%> </td><!--转让方式-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.contractSum",clientLocale)%> </td><!--转让份额-->
					<td align="center"  ><%=LocalUtilis.language("message.date",clientLocale)%> </td><!--日期-->
					<td align="center" ><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--状态-->
					<td align="center" ><%=LocalUtilis.language("class.description",clientLocale)%> </td><!--备注-->
					<td align="center" ><%=LocalUtilis.language("message.view",clientLocale)%> </td><!--查看-->
					<td align="center">审批记录</td>
				<%if(user_id.intValue()==5){%>
					<td align="center" height="25"><%=LocalUtilis.language("class.contract",clientLocale)%> </td><!--合同-->
					<td align="center" height="25"><%=LocalUtilis.language("class.crtificates",clientLocale)%> </td><!--资金证实书-->
				<%}%>
				</tr>
				<%
				int iCount = 0;
				int iCurrent = 0;
				
				while(it.hasNext()){
					Map map = (Map)it.next();
					Integer check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")), new Integer(0));		%>
				<tr class="tr<%=(iCurrent%2)%>">
					<td class="tdh" align="center" >
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%">
									<!--审核标志为“为审核”时显示-->
									<%if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")), 0) == 1 && !Utility.trimNull(map.get("APPR_STATUS")).equals("110001")){%>
									<input type="checkbox" name="selectbox" class="flatcheckbox" value="<%=map.get("SERIAL_NO")%>">
									<%}else{%>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<%}%>
								</td>
								<td width="90%"><%=map.get("CONTRACT_SUB_BH")%></td>
							</tr>
						</table>
					</td>
					<td><%=Utility.trimNull(Argument.getProductName(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0))))%></td>
					<td><%=Utility.trimNull(map.get("FROM_CUST_NAME"))%></td>
					<td><%=Utility.trimNull(map.get("TO_CUST_NAME"))%></td>
                    <!-- 金额转让 --><!-- 分割转让 --><!-- 再转让-->
					<td align="center"><%=Utility.trimNull(Utility.trimNull(map.get("TRANS_FLAG")).equals("111501")?enfo.crm.tools.LocalUtilis.language("message.amountTrans",clientLocale):Utility.trimNull(map.get("TRANS_FLAG")).equals("111502")?enfo.crm.tools.LocalUtilis.language("message.divisionTrans",clientLocale):enfo.crm.tools.LocalUtilis.language("message.reTrans",clientLocale))%></td>
					<td align="right"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get("TO_AMOUNT")))))%></td>
					<td align="center"><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("CHANGE_DATE")),new Integer(0))))%></td>
					<td align="center"><%=check_flag.intValue()==2? "审核通过": check_flag.intValue()==4? "审核不通过": Utility.trimNull(Argument.getCheckFlagName(check_flag))%></td>
					<td><%=Utility.trimNull(map.get("SUMMARY"))%></td>
					<td align="center">
						<%//if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).intValue()!=2 && !Utility.trimNull(map.get("APPR_STATUS")).equals("110001")){ %>
						<a href="#" onclick="javascript:showInfo('<%=Utility.trimNull(map.get("SERIAL_NO"))%>','<%=Utility.trimNull(map.get("CHECK_FLAG"))%>');">
							<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" alt='<%=LocalUtilis.language("message.view",clientLocale)%> '><!--查看-->
						</a>
						<%//}%>
					</td>
					<td align="center">
					<%if(Utility.parseInt(Utility.trimNull(map.get("PROBLEM_ID")),0) != 0){ %>
					<button type="button"  class="xpbutton2" title="审批记录" onclick="javascript:showprojectinfo('<%=Utility.trimNull(map.get("PROBLEM_ID"))%>','<%=Utility.trimNull(map.get("PROJECTID"))%>','2','0');">&gt;&gt;</button>
						<%} %>
					</td>
				<%if(user_id.intValue() == 5 ){%>
					<td>
						<button type="button"  class="xpbutton2" name="btnSelectAll" onclick="javascript:showPrintInfo('<%=Utility.trimNull(map.get("SERIAL_NO"))%>');">&gt;&gt;</button>
					</td>
					<td align="center" height="25">
						<button type="button"  class="xpbutton2" name="btnSelectAll" onclick="javascript:showPrintPayInfo('<%=Utility.trimNull(map.get("SERIAL_NO"))%>');">&gt;&gt;</button>
					</td>
				<%}%>
				</tr>	
				<%
				iCount ++;
				iCurrent ++;
			}
			for(; iCurrent < pageList.getPageSize(); iCurrent++){ %>
				<tr class="tr<%=(iCurrent%2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				<%if(user_id.intValue()==5){%>
					<td align="center" ></td>
					<td align="center" ></td>
				<%}%>	
				</tr>	
			<%}%>
				<tr class="trbottom">
                    <!--合计--><!--项-->
					<td class="tdh" align="center" ><b><%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("TO_AMOUNT"),new BigDecimal(0.00)))%></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
			 <%if(user_id.intValue()==5){%>
					<td align="center" ></td>
					<td align="center" ></td>
			 <%}%>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="page-link">
				<tr>
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>