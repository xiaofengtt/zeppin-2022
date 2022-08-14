<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String query_contract_bh = Utility.trimNull(request.getParameter("query_contract_bh"));
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer q_sub_productId = Utility.parseInt(request.getParameter("q_sub_productId"),new Integer(0));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),overall_product_id);
Integer q_check_flag=Utility.parseInt(request.getParameter("q_check_flag"),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
Integer q_flag1 = Utility.parseInt(request.getParameter("q_flag1"),new Integer(2));
Integer q_contract_type = Utility.parseInt(request.getParameter("q_contract_type"),new Integer(0));

String options = Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,45);
options = options.replaceAll("\"", "'");

String viewStr = "CONTRACT_SUB_BH$PRODUCT_NAME$CUST_NAME$BANK_NAME$BANK_ACCT$SG_MONEY$QS_DATE$HT_STATUS_NAME$JK_TYPE_NAME";
String tempView = Argument.getMyMenuViewStr(menu_id,input_operatorCode);
if (tempView!=null && !tempView.equals("")){
	viewStr = tempView;
}
Map fieldsMap = Argument.getMenuViewMap(menu_id, "");
if(fieldsMap == null || fieldsMap.isEmpty()){
	fieldsMap = new HashMap();
	viewStr = ""; // 启用“显示设定”前必须在数据库表TFIELDSDIM中加入可显示字段的记录
}

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
String tempUrl = "";
int iCount = 0;
int iCurrent = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = {"SG_MONEY"};
List list = null;
Map map = null;

//url设置
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&q_check_flag="+q_check_flag;
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&query_contract_bh="+query_contract_bh;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
tempUrl = tempUrl+"&q_flag1="+q_flag1;
tempUrl = tempUrl+"&q_sub_productId="+q_sub_productId;
//tempUrl = tempUrl+"&q_contract_type="+q_contract_type;
sUrl = sUrl + tempUrl;

//获得对象及结果集
ApplyreachLocal apply_reach = EJBFactory.getApplyreach();
ApplyreachVO vo = new ApplyreachVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_code("");
vo.setProduct_id(q_productId);
vo.setCheck_flag(q_check_flag);
vo.setCust_name(q_cust_name);
vo.setContract_sub_bh(query_contract_bh);
vo.setProduct_name(q_product_name);
vo.setFlag1(q_flag1);
vo.setSub_product_id(q_sub_productId);

IPageList pageList  = apply_reach.listAll(vo,totalColumn,t_sPage,t_sPagesize);
list = pageList.getRsList();
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.applyPurchaseList",clientLocale)%> </TITLE>
<!--申购列表-->
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

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language=javascript>
/*启动加载*/
window.onload = function(){
	initQueryCondition();
	selectProduct(<%=q_productId%>);
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);		
	var url = "apply_purchase_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;	
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;	
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;
	//var url = url + '&q_product_name=' + document.theform.q_product_name.value;
	if(document.theform.q_productId.value == 0){
		var url = url + '&q_sub_productId=0'
	}else{
		var url = url + '&q_sub_productId=' + document.theform.q_sub_productId.value;
	}
	
	//var url = url + '&q_flag1=' + document.theform.q_flag1.value;
	location = url;	
}

/*新增*/
function newInfo(){	
	disableAllBtn(true);
	
	var sQuery = document.theform.q_productId.value ;
	var sQuery = sQuery + "$" +document.theform.q_productCode.value;
	var sQuery = sQuery + "$" +document.theform.q_productId.value;	
	var sQuery = sQuery + "$" +document.theform.q_cust_name.value;
	var sQuery = sQuery + "$" +document.theform.query_contract_bh.value;
	var sQuery = sQuery + "$" +document.theform.pagesize.value;		
	var sQuery = sQuery + "$" +document.theform.q_check_flag.value;		
	var sQuery = sQuery + "$" +document.theform.q_product_name.value;		
	//var sQuery = sQuery + "$" +document.theform.q_flag1.value;		
	
	location = 'apply_purchase_add.jsp?page=1&sQuery=' + sQuery;
}

/*删除功能*/
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action="apply_purchase_del.jsp"		
		document.theform.submit();
	}
}

/*修改*/
function showInfo(serial_no){
	disableAllBtn(true);
	
	var sQuery = document.theform.q_productId.value ;
	var sQuery = sQuery + "$" +document.theform.q_productCode.value;
	var sQuery = sQuery + "$" +document.theform.q_productId.value;	
	var sQuery = sQuery + "$" +document.theform.q_cust_name.value;
	var sQuery = sQuery + "$" +document.theform.query_contract_bh.value;	
	var sQuery = sQuery + "$" +document.theform.pagesize.value;	
	var sQuery = sQuery + "$" +document.theform.q_check_flag.value;		
	var sQuery = sQuery + "$" +document.theform.q_product_name.value;		
	//var sQuery = sQuery + "$" +document.theform.q_flag1.value;		
	
	location = 'apply_purchase_edit.jsp?updateflag=1&serial_no='+serial_no+'&sQuery=' + sQuery;
}

//打印
function print(serial_no){
	disableAllBtn(true);  	
  	<%if(user_id.intValue()==1){%>
  		location = 'apply_purchase_print2.jsp?serial_no='+serial_no;	
  	<%} 
  	else{%>
  		location = 'apply_purchase_print.jsp?serial_no='+serial_no;	
  	<%}%>
}

/**************************************************************************************************************/

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

/*搜索产品名称*/
function searchProductName(value){
	var list = [];
	var list1 = [];
	$("product_select").innerHTML = "<select size=\"1\" name=\"q_productId\" onkeydown=\"javascript:selectProduct(this.value);\" onchange=\"javascript:selectProduct(this.value);\" class=\"productname\">"+"<%=options%>"+"</select>";
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
			document.theform.q_productId.options.length=0;//清空下拉框选项			
			for(var i=0;i<list.length;i++){
				document.theform.q_productId.options.add(new Option(list[i],list1[i]));
				if(i==0){
					selectProduct(list1[0]);
				}
			}
		}
		document.theform.q_productId.focus();
	}else{
		document.theform.q_productId.options[0].selected=true;
	}
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if (result!=null) location.reload();
}

function selectProduct(product_id){
	if(product_id>=0){
		utilityService.getSubProductFlag(product_id,function(data){
				var arrayL = data.split("$");
				var sub_product_flag = arrayL[0];
				product_id = DWRUtil.getValue("q_productId");
				if(sub_product_flag==1){
					document.getElementById("sub_product_flag").style.display = "";
					utilityService.getSubProductOptionS(product_id,<%=q_sub_productId%>,function(data1){
						$("select_id").innerHTML = "<select size='1' name='q_sub_productId' onkeydown='javascript:nextKeyPress(this)' class='productname'"+
						data1+"</select>&nbsp;&nbsp;";
					});
				}else{
					document.getElementById("sub_product_flag").style.display = "none";
				}	
			}
		);
	}else{
		sl_alert("产品不存在,请重新选择!");
		return false;
	}
}
//查看产品信息
function showProduct(product_id){	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">

<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	<tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right">
	  	 <button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose"   onclick="javascript:cancelQuery();"></button>
	   </td>
	</tr>
</table>

<table>
	<tr>
		<td align="right">产品编号 :</td>
		<td>
			<input type="text" name="q_productCode" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct2(document.theform.q_productCode.value);" ></button>
		</td>
		<td  align="right">产品名称 :</td>
		<td  align="left" >
			<input type="text" name="q_product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProductName(document.theform.q_product_name.value);" ></button>		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
		<td align="left" colspan=3 id="product_select">
			<select size="1" name="q_productId" onkeydown="javascript:selectProduct(this.value);" onchange="javascript:selectProduct(this.value);" class="productname">
				<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
				<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,45)%>
			</select>
		</td>
	</tr>
	<tr id="sub_product_flag" style="display:none;">
		<td align="right">子产品名称 :</td>
		<td align="left" colspan=3 id="select_id">
			<select size="1" name="q_sub_productId" id="q_sub_productId" onkeydown="javascript:nextKeyPress(this)" class="productname">
				<option value="<%=q_sub_productId%>"><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->				
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td><input   name="q_cust_name" value="<%=q_cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="15"/></td>
		<td  align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="q_check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<%=Argument.getCheckFlagOptions(q_check_flag)%>
			</select>
		</td>
	</tr>
	<tr>
		<td><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td colspan="3"><input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="15" value="<%=query_contract_bh%>"></td>
	</tr>
<!-- 
	<tr>
		<td align="right"><%=LocalUtilis.language("message.singleCollectFlag",clientLocale)%> :</td>
		<td><select size="1" name="q_flag1" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<option value="2" <%//if(q_flag1.intValue()==2){%>selected<%//}%>>集合</option>
				<option value="1" <%//if(q_flag1.intValue()==1){%>selected<%//}%>>单一</option>
			</select>
		</td>
		<td  align="right">&nbsp;</td>
		<td  align="left" >&nbsp;</td>
	</tr>
 -->
	<!-- 
	<tr>
		<td align=right>合同分类 : </td>
		<td colspan="3">
			<select size="1" name="q_contract_type" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
				<option value="0" <%//if(q_contract_type.intValue() == 0){%>selected<%//}%> >请选择</option>
				<option value="1" <%//if(q_contract_type.intValue() == 1){%>selected<%//}%> >前台销售人员合同</option>
				<option value="2" <%//if(q_contract_type.intValue() == 2){%>selected<%//}%> >产品部门合同</option>
				<option value="3" <%//if(q_contract_type.intValue() == 3){%>selected<%//}%> >代销合同</option>
			</select>
		</td>
	</tr>
 -->
	<tr></tr>
	<tr>						
		<td  align="center" colspan=4>
			<button type="button"   class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
		</td><!--确认-->
	</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=LocalUtilis.language("menu.applyPurchase",clientLocale)%> </b></font><!--销售管理>>申购-->
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 		
		<button type="button"   class="xpbutton5" accessKey="v" onclick="javascript:setView()"><%=LocalUtilis.language("message.displaySet",clientLocale)%> (<u>V</u>)</button>&nbsp;&nbsp;&nbsp;<!-- 显示设定 -->
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
			<button type="button"   class="xpbutton3" accessKey=n name="btnNew" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
			</button><!--新建-->
		&nbsp;&nbsp;&nbsp; <%}%>	
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
			<button type="button"   class="xpbutton3" accessKey=d name="btnCancel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> "	onclick="javascript:delInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
			</button><!--删除-->
		<%}%>						
	</div>
	<br/>
</div>

<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
		<tr class="trh">
	  <%
		String[] fieldsArr =Utility.splitString(viewStr,"$");
		for(int j=0;j<fieldsArr.length;j++){
			String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
			String setWith = "";

			if("产品名称".equals(setWithName))
				setWith = "";
			else if("客户名称".equals(setWithName))
				setWith = "";
			else if("合同编号".equals(setWithName)||"银行账号".equals(setWithName)||"证件号码".equals(setWithName))
				setWith = "100px;";
			else if("签署日期".equals(setWithName)||"受益级别".equals(setWithName))
				setWith = "70px;";
			else
				setWith = "";
			
			if(j==0){
		%>
			<td align="center" width="<%=Utility.trimNull(setWith)%>">
			    <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;<%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%>
			</td><!--合同编号-->
		<%
			}else{
		%>
				<td align="center" width="<%=Utility.trimNull(setWith)%>"><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td>
		<%
			}
		}%>
				
			<td align="center" width="4%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
			<td align="center" width="4%"><%=LocalUtilis.language("message.print",clientLocale)%> </td><!--打印-->
		</tr>
<%
//声明变量	
Integer serial_no = new Integer(0);
Integer product_id = new Integer(0);
Integer cust_id = new Integer(0);
Integer sq_date = new Integer(0);
Integer check_flag = new Integer(0);
BigDecimal sg_money = new BigDecimal(0);

String contract_bh = "";
String contract_sub_bh = "";
String product_name = "";
String cust_name = "";
String ht_status_name = "";
String jk_type_name = "";
String ht_status = "";
String sub_product_name = "";
String bank_name = "";
String bank_acct = "";
String check_flag_name = enfo.crm.tools.LocalUtilis.language("message.checked",clientLocale);//已审核


Iterator iterator = list.iterator();

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	if(map!=null){			
		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),null);
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
		contract_bh = Utility.trimNull(map.get("CONTRACT_BH"));
		contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),null);
		sg_money = Utility.parseDecimal(Utility.trimNull(map.get("SG_MONEY")),null).setScale(2,2);
		sq_date = Utility.parseInt(Utility.trimNull(map.get("QS_DATE")),null);
		ht_status_name = Utility.trimNull(map.get("HT_STATUS_NAME"));
		check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),null);	
		jk_type_name = Utility.trimNull(map.get("JK_TYPE_NAME"));
		ht_status = Utility.trimNull(map.get("HT_STATUS"));
		check_flag_name = enfo.crm.tools.LocalUtilis.language("message.checked",clientLocale);//已审核
		sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
		bank_name = Utility.trimNull(map.get("BANK_NAME"));
		bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
		if(sub_product_name.length()>0){
			sub_product_name = "(" + sub_product_name + ")";
		}
		if(check_flag != null){
			if(check_flag.intValue()==1){
				check_flag_name = enfo.crm.tools.LocalUtilis.language("message.unaudited",clientLocale);//未审核
			}
		}
		if(sg_money!=null){
			sg_money = sg_money.setScale(2);
		}
	}

%>
	<tr class="tr<%=(iCurrent % 2)%>">
	<%
	for(int j=0;j<fieldsArr.length;j++){
		if(j==0){
	%>
		<td class="tdh" align="center" >
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"> <input type="checkbox" name="serial_no" <%if(check_flag.intValue()!=1 && ht_status.equals("120101")){%> style="visibility:hidden"<%}%>value="<%=serial_no%>" class="flatcheckbox"> </td>
					<td width="90%" align="left">&nbsp;&nbsp;<%=map.get(fieldsArr[j])%></td>
				</tr>
			</table>
		</td>
	<%
		}else{
			int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
			switch (iType){
				case 1:
					if (fieldsArr[j].equals("PRODUCT_NAME")) {
	%>
			<td align="left"><a href="#" onclick="javascript:showProduct('<%=map.get("PRODUCT_ID")%>');"><%=product_name%><%=Utility.trimNull(sub_product_name)%></a></td>
	<%				} else if (fieldsArr[j].equals("HT_STATUS_NAME")) { %>
			<td align="center"><%=ht_status_name%><%=check_flag_name%></td>

	<% 				} else if (fieldsArr[j].equals("JK_TYPE_NAME")) { %>
			<td align="center"><%=jk_type_name%></td>			
	<% 				} else { %>
			<td align="left"><%=Utility.trimNull(map.get(fieldsArr[j]))%></td>
	<%
					}
				break;

				case 2:
	%>
				<td align="right"><%=Format.formatMoney(((BigDecimal)map.get(fieldsArr[j])))%></td>
		<%
					break;

					case 3:
		%>
				<td align="center"><%=Format.formatDateCn(((Integer)map.get(fieldsArr[j])))%></td>
		<%
					break;

					case 4:
					break;

					default:
					break;
				}
			}
		}
		%>	
									
		<td align="center" >
			<%if (input_operator.hasFunc(menu_id, 102)){%>
				 <a href="javascript:showInfo(<%=serial_no%>);">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
               	</a>
			<%}%>
		</td>
		<td align="center">
			<%if (input_operator.hasFunc(menu_id, 108)){%>
			 <a href="javascript:print(<%=serial_no%>);">
           		<img border="0" src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16">
           	 </a>
           	 <%}%>
		</td>
	</tr>
<%}%>	

<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
      <tr class="tr<%=i%2%>">
     <%
	 for(int j=0;j<fieldsArr.length;j++){ %>		 
         <td align="center"></td>    
	 <%} %>
         <td align="center"></td>                   
         <td align="center"></td>                          
      </tr>           
<%}%>   	
	<tr class="trbottom">
		<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
	<%
 	for(int j=1;j<fieldsArr.length;j++) { // 注意从1开始 
		if (fieldsArr[j].equals("SG_MONEY")) { %>		
		<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("SG_MONEY"),new BigDecimal(0.00)))%></td>
	<%  } else { %> 
     	<td align="center"></td>    
 	<%
		}
	} %>
         <td align="center"></td>                   
         <td align="center"></td>  					
	</tr>			
		
	</table>
</div>

<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%apply_reach.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>