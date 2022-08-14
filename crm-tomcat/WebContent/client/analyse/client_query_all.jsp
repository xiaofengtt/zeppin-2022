<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String viewStr = "CUST_NO$CUST_NAME$CARD_ID$TOTAL_MONEY$EXCHANGE_AMOUNT$BACK_AMOUNT$BEN_AMOUNT$LAST_RG_DATE";
Integer v_op_code = Utility.parseInt(Utility.trimNull(request.getParameter("v_op_code")),input_operatorCode);
Integer v_setView = Utility.parseInt(Utility.trimNull(request.getParameter("v_setView")),new Integer(0));
//获得查询参数
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"), new Integer(0));
String cust_level = Utility.trimNull(request.getParameter("cust_level"));
String cust_source = Utility.trimNull(request.getParameter("cust_source"));
String card_type = Utility.trimNull(request.getParameter("card_type"));//证件类型
String card_id = Utility.trimNull(request.getParameter("card_id"));//证件号码
String vip_card_id = Utility.trimNull(request.getParameter("vip_card_id"));//VIP卡编号
String hgtzr_bh = Utility.trimNull(request.getParameter("hgtzr_bh"));//合格投资人编号
String birthday = Utility.trimNull(request.getParameter("birthday"));//出生日期
String birthday_end = Utility.trimNull(request.getParameter("birthday_end"));
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//最低购买金额
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//最高购买金额
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//最低受益金额
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//最高受益金额
String touch_type = Utility.trimNull(request.getParameter("touch_type"));//联系方式
//String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));//联系电话
String post_address = Utility.trimNull(request.getParameter("post_address"));//联系地址
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//产品ID
Integer is_link = Utility.parseInt(request.getParameter("is_link"), new Integer(0));//是否按关联方查询
Integer onlyemail = Utility.parseInt(request.getParameter("onlyemail"), new Integer(0));//是否存在邮寄
String family_name = Utility.trimNull(request.getParameter("family_name"));//家庭名称
String sort_name = Utility.trimNull(request.getParameter("sortName"));//排序字段
String prov_level = Utility.trimNull(request.getParameter("prov_level"));//受益级别
Integer wtr_flag = Utility.parseInt(Utility.trimNull(request.getParameter("wtr_flag")),new Integer(0));//委托人标志
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
String q_country = Utility.trimNull(request.getParameter("q_country"));
String q_money_source = Utility.trimNull(request.getParameter("q_money_source"));
Integer accountManager = Utility.parseInt(request.getParameter("accountManager"),new Integer(0));
String referee = Utility.trimNull(request.getParameter("referee"));

String productTo = Utility.trimNull(request.getParameter("productTo"));
Integer start_age = Utility.parseInt(request.getParameter("start_age"),null);
Integer end_age = Utility.parseInt(request.getParameter("end_age"),null);
String telephone = Utility.trimNull(request.getParameter("telephone"));
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),null);
int cell_flag = Utility.parseInt(request.getParameter("cell_flag"),1);

Integer rg_begin_date = Utility.parseInt(request.getParameter("rg_begin_date"),new Integer(0));
Integer rg_end_date = Utility.parseInt(request.getParameter("rg_end_date"),new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String city_name = Utility.trimNull(request.getParameter("city_name"));
Integer true_flag = Utility.parseInt(request.getParameter("true_flag"),new Integer(0)); //

StringBuffer sortlist = Argument.newStringBufferBit();//排序字段列表;
Argument.appendOptions(sortlist, "CUST_NAME", enfo.crm.tools.LocalUtilis.language("class.customerName",clientLocale), sort_name);//客户名称
Argument.appendOptions(sortlist, "TOTAL_MONEY", enfo.crm.tools.LocalUtilis.language("message.amountPurchased",clientLocale), sort_name);//已购金额
Argument.appendOptions(sortlist, "LAST_RG_DATE", enfo.crm.tools.LocalUtilis.language("message.lastRgDate",clientLocale), sort_name);//最近购买时间
Argument.appendOptions(sortlist, "CURRENT_MONEY", enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale), sort_name);//存量金额
Argument.appendOptions(sortlist, "BEN_AMOUNT", enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale), sort_name);//受益份额

String tempView = Argument.getMyMenuViewStr(menu_id,v_op_code);
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}

StringBuffer custIdSAll = new StringBuffer();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.queryCondition",clientLocale)%> </TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js'></script>
<script language=javascript>

window.onload = function(){
<%if(cell_flag==1){%>
	getSubProductOption(<%=product_id%>,<%=sub_product_id%>);
<%}%>
	showQueryInfo();
};



/*获得查询条件*/
function getQueryCondition()
{
	var sub_product_id = 0;
	if(document.theform.cell_flag.value==2){
		if(document.theform.cell_id.value==""||document.theform.cell_id.value==0){
			sl_alert("<%=LocalUtilis.language("message.PleaseSelectAPU",clientLocale)%>！");
			return false;
		}
	}else{
		sub_product_id = DWRUtil.getValue("sub_product_id");
	}

	if(document.theform.is_link.checked)
		document.theform.is_link.value=1;
	else
		document.theform.is_link.value=0;
	if(document.theform.onlyemail.checked)
		document.theform.onlyemail.value=1;
	else
		document.theform.onlyemail.value=0;
	if(document.theform.sort_name.value!=-1)
	document.theform.sortName.value = document.theform.sort_name.value + document.theform.sortName.value;
	else
	document.theform.sortName.value = "";
	var v = new Array;
	v[0] = document.theform.cust_no.value ;
	v[1] = document.theform.cust_name.value;
	v[2] = document.theform.cust_source.value;
	v[3] = document.theform.cust_type.value;
	v[4] = document.theform.card_type.value;
	v[5] = document.theform.card_id.value ;
	v[6] = document.theform.vip_card_id.value ;
	v[7] = document.theform.hgtzr_bh.value ;
	v[8] = document.theform.birthday.value;
	v[9] = document.theform.cust_level.value;
	v[10] = document.theform.start_rg_times.value;
	v[11] = document.theform.end_rg_times.value;
	v[12] = document.theform.min_total_money.value;
	v[13] = document.theform.max_total_money.value;
	v[14] = document.theform.ben_amount_min.value;
	v[15] = document.theform.ben_amount_max.value;
	v[16] = document.theform.touch_type.value;
	v[17] = document.theform.post_address.value;
	v[18] = document.theform.product_id.value;
	v[19] = document.theform.is_link.value;
	v[20] = document.theform.onlyemail.value ;
	v[21] = document.theform.family_name.value;
	v[22] = document.theform.sortName.value;
	v[23] = document.theform.prov_level.value;
	v[24] = document.theform.wtr_flag.value;
	v[25] = document.theform.group_id.value;
	v[26] = document.theform.q_money_source.value;
	v[27] = document.theform.q_country.value;
	v[28] = document.theform.is_deal.value;
	v[29] = document.theform.accountManager.value;
	v[30] = document.theform.referee.value;
	
	if(document.theform.classEs.value!='' && document.theform.classEs.value2=='')
		v[31] = document.theform.classEs.value;
	else if(document.theform.classEs.value=='' && document.theform.classEs2.value!='')
		v[31] = document.theform.classEs2.value;
	else if(document.theform.classEs.value!='' && document.theform.classEs2.value!='')
		v[31] = document.theform.classEs.value+','+document.theform.classEs2.value;	
	else
		v[31] = document.theform.classEs.value;
	
	v[32] = document.theform.productTo.value;
	v[33] = document.theform.start_age.value;
	v[34] = document.theform.end_age.value;
	v[35] = document.theform.telephone.value;
	v[36] = document.theform.cell_id.value;
	v[37] = document.theform.cell_flag.value;
	v[38] = document.theform.birthday_end.value;
	syncDatePicker(theform.rg_begin_date_picker, theform.rg_begin_date);
	syncDatePicker(theform.rg_end_date_picker, theform.rg_end_date);
	v[39] = document.theform.rg_begin_date.value;
	v[40] = document.theform.rg_end_date.value;
	v[41] = document.theform.region.value;
	v[42] = document.theform.channel.value;
	v[43] = sub_product_id;
	v[44] = document.theform.city_name.value;
	v[45] = document.theform.true_flag.value;
	window.returnValue = v ;
	window.close();
}


/*设置产品*/
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
				onChangeProduct()
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

/*查询产品*/
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
				onChangeProduct()
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

/*选择产品显示委托标志*/
function onChangeProduct()
{
	var product_id = DWRUtil.getValue("product_id");
	if(product_id != 0)
	{
		document.getElementById("sub_product_flag").style.display = "";
		getSubProductOption(product_id,0);
		document.all.WTRFlag.style.display="";
		document.all.levelsFlag.style.display="";
	}
	else
	{
		document.getElementById("sub_product_flag").style.display = "none";
		document.all.WTRFlag.style.display="none";
		document.all.levelsFlag.style.display="none";
	}	
}


//添加子产品下拉 
function getSubProductOption(product_id,sub_product_id)
{
	utilityService.getSubProductOptionS(product_id,sub_product_id,{callback:function(data){
		$("select_id_2").innerHTML = "<select style='width:410px;' name='sub_product_id' onkeydown='javascript:nextKeyPress(this)'>"+data+"</SELECT>";
	}});
}

/*选择委托标志显示受益人标志*/
function changerLevels(flag)
{
	if(flag==2)
		document.all.levelsFlag.style.display="none";
	else
		document.all.levelsFlag.style.display="";	
}


//选择联系方式并联动显示相应的值
function getTouchTypeSlectedText(name){
	var obj=document.getElementById(name);
	for(i=0;i<obj.length;i++){
		if(obj[i].selected==true){
			if(obj[i].innerText == "<%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> "){//请选择
				//document.getElementById("cust_tel").innerText = "<%=LocalUtilis.language("class.tele",clientLocale)%> ";//电话
			}else{
				//document.getElementById("cust_tel").innerText = obj[i].innerText; //通过option对象的innerText属性获取到选项文本
			}
		}
	}
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=20301','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');	
	if(result!=null)
	location.reload();
}

function changeCustType(flag){
	document.getElementById("cust_type").value=flag;
	refreshPage();
}

function getTreeData()
{
	var ret = showModalDialog('/client/appraise/client_query_tree_all.jsp?pageFlag=1','','dialogWidth:440px;dialogHeight:580px;status:0;help:0');	
	if(ret!=null)
	{
		for(var i=0;i<ret.length;i++){
			if(i==0)
				document.theform.classEs.value = ret[i]
			else
				document.theform.classEs.value = document.theform.classEs.value+','+ret[i];
		}
	}
}

function getTreeData2()
{
	var ret = showModalDialog('/client/appraise/client_query_tree_all.jsp?pageFlag=2','','dialogWidth:440px;dialogHeight:580px;status:0;help:0');	
	if(ret!=null)
	{
		for(var i=0;i<ret.length;i++){
			if(i==0)
				document.theform.classEs2.value = ret[i]
			else
				document.theform.classEs2.value = document.theform.classEs.value+','+ret[i];
		}
	}
}

function SelectCellFlag(value){
	if(value == "1")
	{
		tr1.style.display="";
		tr2.style.display="";
		tr3.style.display="none";
		sub_product_flag.style.display = "";
		getSubProductOption(DWRUtil.getValue("product_id"),<%=sub_product_id%>);
		
	}else if(value == "2")
	{
		tr1.style.display="none";
		tr2.style.display="none";
		tr3.style.display="";
		sub_product_flag.style.display = "none";
	}
	document.theform.cell_flag.value=value;
}

function openReportTree()
{
	var v = showModalDialog('report_tree.jsp', '', 'dialogWidth:530px;dialogHeight:550px;status:0;help:0');
	if (v != null)
	{
		document.theform.cell_id.value = v;
	}
}
//排序
function onChangeSort()
{
	
	if(document.theform.sort_name.selectedIndex == 0){
		
		var c=document.getElementById("biaozhi");
	    c.style.display="none";

	}else
	{ 
	    var a=document.getElementById("biaozhi");
	    a.style.display="";
		document.theform.sort_flag.checked = false;
		document.theform.sort_flag.disabled = false;
	}
}
function Selectsort_flag(value){
	if(value == "1")
	{
		document.theform.sortName.value=" DESC";
		
	}else if(value == "2")
	{
		document.theform.sortName.value=" ASC";
	}
}

/*客户信息显示控制*/
function showQueryInfo(){
	var s=5;
	var minheight=1;
	var maxheight=200;	
	var flag = document.getElementById("show_image").title;

	if(document.getElementById("query_info").style.pixelHeight==0){
		document.getElementById("query_info").style.pixelHeight=minheight;
	}
	
	if(flag=="next"){
		 document.getElementById("query_info").style.pixelHeight+=s;
		 
		 if(document.getElementById("query_info").style.pixelHeight<maxheight){
		   setTimeout("showQueryInfo()",1);
		 }else{		
		    document.getElementById("show_image").src = "<%=request.getContextPath()%>/images/previous_down.gif";
		    document.getElementById("show_image").title = "previous_down";
		 }		
		
	}else if(flag=="previous_down"){
		document.getElementById("query_info").style.pixelHeight-=s;
		if(document.getElementById("query_info").style.pixelHeight>minheight){
		   setTimeout("showQueryInfo()",1);
		}else{		
		    document.getElementById("show_image").src = "<%=request.getContextPath()%>/images/next.gif";
		    document.getElementById("show_image").title = "next";
		}			
	}
}

var j$ = jQuery.noConflict();
function filterProduct(product_name){
    if(event.keyCode==13){    
        j$("[name='product_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='product_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='product_id']").children(":not([text*='"+product_name+"'])").remove();
		onChangeProduct();
    }else{
        return false;    
    }           
}

function filterProduct2(product_name){
    j$("[name='product_id']").children().remove().append("<option value='0'>全部</option>");
    j$("[name='all_product_id']").children().each(function(){
        j$("[name='product_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
    });
    j$("[name='product_id']").children(":not([text*='"+product_name+"'])").remove();
	onChangeProduct();         
}

function productFilter(product_name){    
	if(document.theform.cell_flag.value==2) { // 产品选择方式：产品单元
        j$("[name='cell_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='cell_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='cell_id']").children(":not([text*='"+product_name+"'])").remove();     
	}
	onChangeProduct();  
}

</script>
</HEAD>
<BODY class="BODY body-nox" topmargin="1">&nbsp; 
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="client_query_list.jsp">
<input type="hidden" name="input_man" value="<%=input_operatorCode%>">
<input type="hidden" name="titleNames">
<input type="hidden" name="fieldNames">
<input type="hidden" name="fieldTypes">
<input type="hidden" name="classEs" value="">
<input type="hidden" name="sortName" value="">
<input type="hidden" name="classEs2" value="">
<input type="hidden" name="cell_id" value="">
<input type="hidden" name="cell_flag" value="<%=cell_flag %>">
<fieldset style="width: 100%;top: 0px;left: 1px;">
<legend>
	查询基本条件
</legend>
<table border="0" cellspacing="0" cellpadding="4">
	<tr>
		<td align=right >产品选择方式:</td>
		<td colspan="3" >
		<input type="radio" value="1" onclick="javascript:SelectCellFlag(this.value);" checked" onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" <%if(cell_flag==1) out.print("checked");%>>独立产品&nbsp;&nbsp;
		<input type="radio" value="2" onclick="javascript:SelectCellFlag(this.value);" onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" <%if(cell_flag==2) out.print("checked");%>>产品单元

		</td>
	</tr>
	<tr id = "tr1" <%if(cell_flag==1)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :<!--产品编号--></td>
		<td>	
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8
				   size="17" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" />	
		</td>
		<td  align="right">产品名称: </td>
		<td  align="left">
			<input type="text"  name="product_name" value="" size="20" onkeydown="javascript:filterProduct(this.value);nextKeyPress(this)" >
			<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:filterProduct2(document.theform.product_name.value);" /></button>
		</td>
	</tr>
	<tr id = "tr2" <%if(cell_flag==1)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
		<td colspan=3 align="left">
			<select name="product_id" style="width: 410px"  onkeydown="javascript:nextKeyPress(this)" onchange="javascript:onChangeProduct();">
			<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,0)%>
			</select>
			<SELECT name="all_product_id" style="display:none" style="width: 410px;" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:onChangeProduct();"> 
				<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,0)%>
			</SELECT>
		</td>
	</tr>
	<tr id="sub_product_flag" <%if(cell_flag==2||product_id.intValue()==0){%>style="display: none;"<%} %>>
		<td align="right"><%=LocalUtilis.language("class.subProductID",clientLocale)%> </td><!--子产品选择-->
		<td align=left colspan=3 id="select_id_2">
			
		</td>
	</tr>
	<tr id = "tr3" <%if(cell_flag==2)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align = "right">产品单元:</td >
		<td align="left"  colspan="3">
		<button type="button"  class="xpbutton3" name="btnQuery1" onclick="javascript:openReportTree();">请选择...</button>
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
		<td valign="bottom" align="left"><input type="text" name="cust_no"
			onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(cust_no)%>" size="25">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left"><input type="text" maxlength="100"
			name="cust_name" onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(cust_name)%>" size="25">
		</td>	
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--证件类型-->
		<td valign="bottom" align="left">
			<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="card_type" style="WIDTH: 147px">
			   <%=Argument.getCardTypeJgOrGrOptions(card_type)%>
			</select>  
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td valign="bottom" align="left">
		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="card_id" size="25" value="<%=card_id%>">
		</td>			
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
		<td valign="bottom" align="left">
			<select name="accountManager" style="width: 147px">
				<%=Argument.getManager_Value(accountManager) %>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale) %> :</td><!-- 客户类别 -->
		<td>
			<select name="cust_type" style="width: 147px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCustTypeOptions(cust_type) %>
			</select>
		</td>			
	</tr>
	<tr>
		<td align="right">销售区域 :</td>
		<td colspan="3">
			<input type="text" maxlength="100" name="city_name" onkeydown="javascript:nextKeyPress(this)"
						value="<%=Utility.trimNull(city_name)%>" size="25">
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.AgeGroup",clientLocale)%> :</td><!--年龄段-->
		<td valign="bottom" align="left">
			从<input type="text" name="start_age"onkeydown="javascript:nextKeyPress(this)"value="<%=Utility.trimNull(start_age)%>" size="7">
			到 <input type="text" name="end_age"onkeydown="javascript:nextKeyPress(this)"value="<%=Utility.trimNull(end_age)%>" size="7">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.tel",clientLocale)%> :</td><!--电话号码-->
		<td valign="bottom" align="left"><input type="text" maxlength="100"
			name="telephone" onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(telephone)%>" size="25">
		</td>	
	</tr>
	<tr>  
		<td align="right"><%=LocalUtilis.language("class.rg_times",clientLocale)%> :</td><!--购买次数-->
		<!--从-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="start_rg_times" size="33" <%
				if(start_rg_times.intValue() == 0){%>
				  value="" 
				<%}else{%>
				value="<%=Utility.trimNull(start_rg_times)%>"
                <%}%>> <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 --> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="end_rg_times" size="33" <%
				if(end_rg_times.intValue() == 0){%>
				  value=""
				<%}else{%>
				value="<%=Utility.trimNull(end_rg_times)%>"
				<%}%>>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.totalMoney",clientLocale)%> :</td><!--购买金额-->
		<!--从-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_total_money" size="33"
				<%if(min_total_money.intValue() != 0){%>
                value="<%=Utility.trimNull(min_total_money)%>"<%}%>> <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_total_money" size="33" 
				<%if(min_total_money.intValue() != 0){%>
				value="<%=Utility.trimNull(max_total_money)%>"<%}%>>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.benefitShare",clientLocale)%> :</td><!--受益份额-->
		<!--从-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_min" size="33" 
			<%
			if(ben_amount_min.intValue() == 0){%>
			  value=""
			<%}else{%>
			value="<%=Utility.trimNull(ben_amount_min)%>"
			
            <%}%>> <%=LocalUtilis.language("message.end",clientLocale)%>  <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_max" size="33" 
			<%
			if(ben_amount_max.intValue() == 0){%>
			 value=""
			<%}else{%>
			value="<%=Utility.trimNull(ben_amount_max)%>"
			<%}%>>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.subscriptionDate",clientLocale)%> :</td><!--认购日期-->
		<!--从-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%>
			<input type="text" name="rg_begin_date_picker" class=selecttext	 onkeydown="javascript:nextKeyPress(this)" size="28" 
		        <%if(rg_begin_date.intValue() == 0){%>
				  value=""
				<%}else{ %>
				  value="<%=Format.formatDateLine(rg_begin_date)%>";
				<%}%>>
			<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rg_begin_date_picker,theform.rg_begin_date_picker.value,this);">
			<input type="hidden" name="rg_begin_date" value="">
			<%=LocalUtilis.language("message.end",clientLocale)%>
			<input type="text" name="rg_end_date_picker" class=selecttext	 onkeydown="javascript:nextKeyPress(this)"  size="28"
		        <%if(rg_end_date.intValue() == 0){%>
				  value=""
				<%}else{ %>
				  value="<%=Format.formatDateLine(rg_end_date)%>";
				<%}%>>
			<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rg_end_date_picker,theform.rg_end_date_picker.value,this);">
			<input type="hidden" name="rg_end_date" value="">
			
		</td>
	</tr>
	<tr id="reader2" style="display:">
		<td align="right"><%=LocalUtilis.language("message.sort_name",clientLocale)%> :</td><!--排序字段-->
		<td style='display:'>
        	<select size="1" name="sort_name" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:onChangeSort();" style="WIDTH: 160px" >
				<%=sortlist.toString()%>
			</select>
		</td>
		<td id="biaozhi"align="right" style='display:none'>&nbsp;&nbsp;
			降序标志:<input type="radio" value="1" onclick="javascript:Selectsort_flag(this.value);" onkeydown="javascript:nextKeyPress(this)" name="sort_flag" class="flatcheckbox" >
			升序标志:<input type="radio" value="2" onclick="javascript:Selectsort_flag(this.value);" onkeydown="javascript:nextKeyPress(this)" name="sort_flag" class="flatcheckbox" >
		</td>
	</tr>
	<tr id="levelsFlag" <%if(product_id.intValue() == 0 || wtr_flag.intValue()==2){%> style="display: none;"<%}%>>
		<td align="right"><%=LocalUtilis.language("class.prov_level",clientLocale)%> :</td><!--受益级别-->
		<td  align="left"> 
			<SELECT  size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
			   <%=Argument.getProvlevelOptions(prov_level)%>
			</SELECT>						
		</td>
	</tr>
</table>
</fieldset>

<!-- ******************************************************************************************** -->
<fieldset style="width: 100%;">
 <!-- 查询信息 -->
<legend>
	查询附加条件
	<a href="#" onclick="javascript:showQueryInfo();">
		<IMG id="show_image" src="<%=request.getContextPath()%>/images/next.gif" title="previous_down")>
	</a>
</legend>
<div id="query_info" style="overflow: auto;" align="left">
<table border="0" cellspacing="0" cellpadding="4" width="100%">
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> :</td><!--客户级别-->
		<td valign="bottom" align="left">
		<select style="width:147px" name="cust_level" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getDictParamOptions2(1111, cust_level)%>
		</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale) %>:</td><!-- 客户来源 -->
		<td>
			<select style="width: 147px" name="cust_source" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions2(1110, cust_source)%>
			</select>
		</td>
	</tr>
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.custGroups",clientLocale) %>:</td><!-- 客户群组 -->
		<td>
			<select name="group_id" id="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 147px">
				<%=Argument.getCustGroupOption2(new Integer(0),group_id)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.isTransactions",clientLocale) %>:</td><!-- 是否交易 -->
		<td >
			<select name="is_deal" id="is_deal" onkeydown="javascript:nextKeyPress(this)" style="width: 147px" >	
				<%=Argument.getWTCustOptions(is_deal)%>
			</select>							
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%>:</td><!-- 国别 -->
		<td>
			<select size="1"  name="q_country"  id="q_country" style="width: 147px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(9997,q_country)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.moneySource",clientLocale) %>:</td><!-- 资金来源 -->
		<td>
			<select size="1"  name="q_money_source"  id="q_money_source" style="width: 147px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(2012,q_money_source)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</td><!--联系方式-->
		<td><select size="1" name="touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 147px;">
			  <%=Argument.getTouchTypeOptions(touch_type)%>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.productsTo",clientLocale)%> :</td><!--产品投向-->
		<td valign="bottom" align="left">
			<select name="productTo" style="width: 147px;">
				<%=Argument.getDictParamOptions_intrust(1139,productTo) %>
			</select>
		</td>	
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--出生日期-->
		<td>
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="4" name="birthday" size="8" value="<%=Utility.trimNull(birthday)%>">
			<%=LocalUtilis.language("message.to",clientLocale)%>
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="4" name="birthday_end" size="8"value="<%=Utility.trimNull(birthday_end)%>">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.referee",clientLocale)%> :</td><!--推荐人-->
		<td valign="bottom" align="left"><input type="text" maxlength="100"
			name="referee" onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(referee)%>" size="25">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP卡编号-->
		<td><input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="vip_card_id" size="25"
				value="<%=Utility.trimNull(vip_card_id)%>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--合格投资人编号-->
		<td><input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="hgtzr_bh" size="25"
			value="<%=Utility.trimNull(hgtzr_bh)%>">
		</td>			
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("message.Region",clientLocale)%> :</td>
		<td>
			<INPUT onkeydown="javascript:nextKeyPress(this)" maxlength="60" name="region" size="25"/>
		</td>
		<td align="right"><%=LocalUtilis.language("message.channels",clientLocale)%> :</td>		
		<td>
			<INPUT onkeydown="javascript:nextKeyPress(this)" maxlength="60" name="channel" size="25"/>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.familyName",clientLocale)%> :</td><!--家庭名称-->
		<td> <input type="text" onkeydown="javascript:nextKeyPress(this)" name="family_name" size="25" 
				value="<%=Utility.trimNull(family_name)%>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
		<td> <input type="text" onkeydown="javascript:nextKeyPress(this)" name="post_address" size="25"
				value="<%=Utility.trimNull(post_address)%>">
			&nbsp;&nbsp;&nbsp;
		</td>	
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("menu.customerRating",clientLocale)%> :<!--客户评级--></td>
		<td align="left">
			<button type="button"  class="xpbutton4" name="btnQuery" accessKey="q"
			onclick="javascript:getTreeData();"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> (<u>Q</u>)</button><!--查询--></td>
		<td align="right"><%=LocalUtilis.language("menu.CustomerClassification",clientLocale)%> :<!--客户分类--></td>
		<td align="left">
			<button type="button"  class="xpbutton4" name="btnQuery" accessKey="q"
			onclick="javascript:getTreeData2();"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> (<u>Q</u>)</button><!--查询--></td>
		<td><br></td>	
	</tr>
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%> :</td><!--是否关联方-->
		<td><input type="checkbox" name="is_link" value="0" class="flatcheckbox"
				<%if(is_link.equals(new Integer(1))){%>
				  checked
				<%}%>>
		</td>
		<td align="right"><%=LocalUtilis.language("class.onlyemail",clientLocale)%> :</td><!--是否存在邮件-->
		<td ><input type="checkbox" name="onlyemail" value="0" onkeydown="javascript:nextKeyPress(this)" class="flatcheckbox"
				<%if(onlyemail.equals(new Integer(1))){%>
				  checked
				<%}%>></td>
	</tr>
	<tr>
		<td align="right">客户真实性 :</td>
		<td colspan="3">
			<select size="1" name="true_flag" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCustInfoTrueFlagList(true_flag)%>
			</select>
		</td>
	</tr>
	<tr id="WTRFlag" <%if(product_id.intValue() == 0){%> style="display: none;"<%}%>>
		<td align="right"><%=LocalUtilis.language("class.wtrFlag",clientLocale)%> :</td><!--委托人标志-->
		<td><SELECT  size="1" name="wtr_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onchange="javascript:changerLevels(this.value);">
				<%=Argument.getClientFlagList(wtr_flag)%>
			</SELECT>
		</td>
	</tr>
<!-- ********************************************************************************************************************************* -->			
</table></div></fieldset>
<table border="0" cellspacing="0" cellpadding="4" align="right">
	<tr>
		<td align="right" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey="o"
			onclick="javascript:getQueryCondition();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
		</td>
	</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>

