<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<title>查询条件设置</title>
<link href="/includes/default.css" type=text/css rel=stylesheet>
<base target="_self">
</head>
<script src="/includes/default.vbs" language="vbscript"></script>
<script src="/includes/default.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/audit.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language="javascript">
function StartQuery()
{	
	if(!sl_checkChoice(document.theform.product_id, "产品名称")) return false;
	product_id=document.theform.product_id.value;
	if(document.theform.sy_type1.checked)
  		document.theform.sytype1.value="111601";
  		//alert(document.theform.sytype1.value);
  	if(document.theform.sy_type2.checked)
  		document.theform.sytype2.value="111602";
  	if(document.theform.sy_type3.checked)
  		document.theform.sytype3.value="111603";
  	if(document.theform.sy_type4.checked)
  		document.theform.sytype4.value="111604";
  	if(document.theform.sy_type5.checked)
  		document.theform.sytype5.value="111605";			
  	if(document.theform.sy_type6.checked)
  		document.theform.sytype6.value="111606";	
  	if(document.theform.sy_type9.checked)
  		document.theform.sytype9.value="111609";
	bank_id=document.theform.bank_id.value;
	jk_type = document.theform.jk_type.value;
	if(document.theform.sy_date_picker1.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker1,"发行期日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker1, document.theform.sy_date1);
		
	}
	if(document.theform.sy_date_picker235.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker235,"收益日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker235, document.theform.sy_date235);
		
	}
	if(document.theform.sy_date_picker4.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker4,"兑付期日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker4, document.theform.sy_date4);
		
	}
	if(document.theform.sy_date_picker6.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker6,"赎回日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker6, document.theform.sy_date6);
		
	}
	if(document.theform.sy_date_picker9.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker9,"申购期日期 ")) return false;
		syncDatePicker(document.theform.sy_date_picker9, document.theform.sy_date9);
		
	}
	sy_date = document.theform.sy_date1.value + "|" + document.theform.sy_date235.value + "|" + document.theform.sy_date4.value + "|" + document.theform.sy_date6.value + "|" + document.theform.sy_date9.value;
	prov_flag = document.theform.prov_flag.value;
	prov_level = document.theform.prov_level.value;
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	document.theform.btnQuery.disabled = true;
	//waitting.style.visibility='visible';
	bonus_flag = document.theform.bonus_flag.value;
	ret=product_id+" $"+document.theform.sytype1.value+" $"+document.theform.sytype2.value+" $"+document.theform.sytype3.value+" $"+document.theform.sytype4.value+" $"+document.theform.sytype5.value+" $"+document.theform.sytype6.value+" $"+document.theform.sytype9.value+" $"+bank_id+" $"+jk_type+" $"+sy_date+" $"+prov_flag+" $"+prov_level
	                            +" $"+document.theform.contract_sub_bh.value+" $"+document.theform.cust_name.value+" $"+document.theform.fp_flag.value+" $"+sub_product_id+" $1"+" $"+bonus_flag+" $"+ document.theform.link_man.value +" $";
	
	window.returnValue = ret;
	
	window.close();
		
	//location = 'square_1_12_rp.jsp?product_id='+product_id+'&sytype1='+document.theform.sytype1.value+'&sytype2='+document.theform.sytype2.value+'&sytype3='+document.theform.sytype3.value+'&sytype4='+document.theform.sytype4.value+'&sytype5='+document.theform.sytype5.value+'&bank_id='+bank_id+'&jk_type='+jk_type+'&sy_date='+sy_date+'&prov_level='+prov_level
	                            //+"&contract_bh="+document.theform.contract_bh.value+"&query_flag=1&cust_name="+document.theform.cust_name.value+"&fp_flag="+document.theform.fp_flag.value;
	return true;
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
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function selectAll1()
{
	if(document.theform.sy_type7.checked)
	{
		document.theform.sy_type1.checked=true;
		document.theform.sy_type2.checked=true;
		document.theform.sy_type3.checked=true;
		document.theform.sy_type4.checked=true;
		document.theform.sy_type5.checked=true;
		document.theform.sy_type6.checked=true;
		document.theform.sy_type9.checked=true;
		document.getElementById("date1").style.display="";
		document.getElementById("date235").style.display="";
		document.getElementById("date4").style.display="";
		document.getElementById("date6").style.display="";
		document.getElementById("date9").style.display="";
		document.getElementById("date1_n").style.display="";
		document.getElementById("date235_n").style.display="";
		document.getElementById("date4_n").style.display="";
		document.getElementById("date6_n").style.display="";
		document.getElementById("date9_n").style.display="";
	}else{
		document.theform.sy_type1.checked=false;
		document.theform.sy_type2.checked=false;
		document.theform.sy_type3.checked=false;
		document.theform.sy_type4.checked=false;
		document.theform.sy_type5.checked=false;
		document.theform.sy_type6.checked=false;
		document.theform.sy_type9.checked=false;
		document.getElementById("date1").style.display="none";
		document.getElementById("date235").style.display="none";
		document.getElementById("date4").style.display="none";
		document.getElementById("date6").style.display="none";
		document.getElementById("date9").style.display="none";
		document.getElementById("date1_n").style.display="none";
		document.getElementById("date235_n").style.display="none";
		document.getElementById("date4_n").style.display="none";
		document.getElementById("date6_n").style.display="none";
		document.getElementById("date9_n").style.display="none";
	}
}

function showdate(value)
{
	if(value == 1)
	{
		if(document.theform.sy_type1.checked){
			document.getElementById("date1_n").style.display="";
			document.getElementById("date1").style.display="";
		}else{
			document.getElementById("date1_n").style.display="none";
			document.getElementById("date1").style.display="none";
		}
	}
	if(value == 2 || value == 3 || value == 5)
	{
		if(document.theform.sy_type2.checked || document.theform.sy_type3.checked || document.theform.sy_type5.checked){
			document.getElementById("date235_n").style.display="";
			document.getElementById("date235").style.display="";
		}else{
			document.getElementById("date235_n").style.display="none";
			document.getElementById("date235").style.display="none";
		}
	}
	if(value == 4)
	{
		if(document.theform.sy_type4.checked){
			document.getElementById("date4_n").style.display="";
			document.getElementById("date4").style.display="";
		}else{
			document.getElementById("date4_n").style.display="none";
			document.getElementById("date4").style.display="none";
		}
	}
	if(value == 6)
	{
		if(document.theform.sy_type6.checked){
			document.getElementById("date6_n").style.display="";
			document.getElementById("date6").style.display="";
		}else{
			document.getElementById("date6_n").style.display="none";
			document.getElementById("date6").style.display="none";
		}
	}
	if(value == 9)
	{
		if(document.theform.sy_type9.checked){
			document.getElementById("date9_n").style.display="";
			document.getElementById("date9").style.display="";
		}else{
			document.getElementById("date9_n").style.display="none";
			document.getElementById("date9").style.display="none";
		}
	}
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
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	var prov_flag;
	var prov_level;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");
	prov_flag = document.theform.prov_flag;
	prov_level = document.theform.prov_level;
	

	DWRUtil.removeAllOptions(sub_product_id);
	DWRUtil.removeAllOptions(prov_flag);
	DWRUtil.removeAllOptions(prov_level);
	
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
		if(data==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	});
	utilityService.getSubProductJson(product_id,3,function(data){
		//alert(data);
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
		}
		utilityService.getSubProductProvFlag(product_id,0,0,function(_data){
			var _json = eval(_data);
			DWRUtil.addOptions(prov_flag,{"0":"请选择"});
			for(i=0;i<_json.length;i++){
				DWRUtil.addOptions(prov_flag,_json[i]);
			}
			utilityService.getProvLevelJson(product_id,0,0,function(__data){
				var __json = eval(__data);
				DWRUtil.addOptions(prov_level,{"":"请选择"});
				for(i=0;i<__json.length;i++){
					DWRUtil.addOptions(prov_level,__json[i]);
				}			
			})
		})
	});
}

function provFlagChange(product_id,sub_product_id,prov_flag,prov_level_id){
	var _prodcut_id = product_id;
	var _sub_product_id = sub_product_id;
	var _prov_flag = prov_flag;
	var prov_level;
	
	prov_level = document.getElementById(prov_level_id);
	
	DWRUtil.removeAllOptions(prov_level);
	
	utilityService.getProvLevelJson(product_id,_sub_product_id,prov_flag,function(__data){
		var __json = eval(__data);
		DWRUtil.addOptions(prov_level,{"":"请选择"});
		for(i=0;i<__json.length;i++){
			DWRUtil.addOptions(prov_level,__json[i]);
		}
	});
}

window.onload = function(){
if(<%=overall_product_id%>==document.theform.product_id.value)
productChange(<%=overall_product_id%>);
};
</script>
<body class="BODY" onkeydown="chachEsc(window.event.keyCode)">
<form name="theform"><input type="hidden" name="subflag" value=""> <input
	type="hidden" name="book_code" value="<%=input_bookCode%>"> <input
	type="hidden" name="outporttype" value="2"> <input type=hidden
	name='sytype1' value=''> <input type=hidden name='sytype2' value=''> <input
	type=hidden name='sytype3' value=''> <input type=hidden name='sytype4'
	value=''> <input type=hidden name='sytype5' value=''> <input type=hidden name='sytype9' value=''><input type=hidden name='sytype6' value=''>
<table border="0" width="100%" cellspacing="10" cellpadding="0">
	<tr>
		<td colspan="4"><IMG height=28 src="/images/member.gif" width=32
			align=absMiddle border=0><b><%=menu_info%></b></td>
	</tr>

	<tr>
		<td align=left colspan="4" noWrap>全选<input
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" onClick="javascript:selectAll1();"
			name="sy_type7">&nbsp;&nbsp;&nbsp; 发行期利息<input
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111601" name="sy_type1" onClick="javascript:showdate(1);">&nbsp;&nbsp;&nbsp;
		中间收益<input
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111602" name="sy_type2" onClick="javascript:showdate(2);">&nbsp;&nbsp;&nbsp;
		到期收益<input 
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111603" name="sy_type3" onClick="javascript:showdate(3);">&nbsp;&nbsp;&nbsp;
		兑付期利息<input 
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111604" name="sy_type4" onClick="javascript:showdate(4);">&nbsp;&nbsp;&nbsp;
		到期本金<input 
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111605" name="sy_type5" onClick="javascript:showdate(5);">&nbsp;&nbsp;&nbsp;
		申购期利息<input 
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111609" name="sy_type9" onClick="javascript:showdate(9);">&nbsp;&nbsp;&nbsp;
		赎回金额<input 
			onkeydown="javascript:nextKeyPress(this)" type="checkbox"
			class="flatcheckbox" value="111606" name="sy_type6" onClick="javascript:showdate(6);">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right">产品编号:</td>
		<td align="left" noWrap><input type="text" size="20" name="productid"
			value="" onkeydown="javascript:setProduct(this.value);" maxlength=8
			size="10">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td align="right">合同编号:</td>
		<td align="left" noWrap><input type="text" name="contract_sub_bh"
			value="" onkeydown="javascript:nextKeyPress(this);"
			size="24"></td>
	</tr>
	<tr>
		<td align="left" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品选择:
			<SELECT name="product_id"
			class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getProductListOptions(input_bookCode,overall_product_id,"",input_operatorCode,status)%>
			</SELECT>
		</td>
	</tr>
	<tr id="tr_sub_product_id" style="display:none">
		<td align="right">子产品:</td>
		<td align="left" colspan="3" id="td_sub_product_id">
			<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions2(overall_product_id, new Integer(0),new Integer(0),0,"")%> 
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align="right" noWrap width="12%">兑付标志:</td>
		<td align="left" noWrap><SELECT style="width: 120"
			onkeydown="javascript:nextKeyPress(this)" size="1" name="fp_flag">
			<%=Argument.getFpFlagOptions(0)%>
		</SELECT></td>
		<td align="right" noWrap width="12%">银行:</td>
		<td align="left" noWrap><SELECT
			onkeydown="javascript:nextKeyPress(this)" size="1" style="width: 142"
			name="bank_id">
			<%=Argument.getBankOptions("")%>
		</SELECT></td>
	</tr>
	<tr>
		<td align="right" noWrap>付款方式:</td>
		<td align="left" noWrap><SELECT style="width: 120"
			onkeydown="javascript:nextKeyPress(this)" size="1" name="jk_type">
			<%=Argument.getJkTypeOptions("")%>
		</SELECT></td>
		<td align="right" noWrap>客户名称:</td>
		<td align="left"><input type="text" name="cust_name"
			value="" onkeydown="javascript:nextKeyPress(this);"
			size="24"></td>
	</tr>
	<tr>
		<td align="right" noWrap>分红方式:</td>
		<td align="left" noWrap>
			<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<option value="0">请选择</option>
				<%=Argument.getBonus_flag(new Integer(0))%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">受益优先级:</td>
		<td>
			<SELECT size="1" name="prov_flag" onchange="javascript:provFlagChange(document.theform.product_id.value,document.theform.sub_product_id.value,this.value,'prov_level')" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			</SELECT>
		</td>
		<td align="right">受益级别:</td>
		<td align="left" noWrap><SELECT size="1" name="prov_level"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
		</SELECT></td>
	</tr>
	<tr>
		<td align="right">合同销售人员:</td>
		<td colspan="3">
			<select name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getIntrustRoledOperatorOptions(input_bookCode, 2,new Integer(0))%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" id="date235_n" style="display:none;">收益日期:</td>
		<td align="left" colspan="3" id="date235" style="display:none;"><INPUT TYPE="text" style="width: 120"
					NAME="sy_date_picker235"
					value=""> <INPUT
					TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.sy_date_picker235,theform.sy_date_picker235.value,this);"
					tabindex="13"> <INPUT TYPE="hidden" NAME="sy_date235" value="0">(中间收益、到期收益、到期本金)</td>
	</tr>
	<tr>
		<td align="right" id="date1_n" style="display:none;">发行期日期:</td>
		<td align="left" id="date1" style="display:none;"><INPUT TYPE="text" style="width: 120"
					NAME="sy_date_picker1"
					value=""> <INPUT
					TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.sy_date_picker1,theform.sy_date_picker1.value,this);"
					tabindex="13"> <INPUT TYPE="hidden" NAME="sy_date1" value="0"></td>
		<td align="right" id="date9_n" style="display:none;">申购期日期:</td>
		<td align="left"  id="date9" style="display:none;"><INPUT TYPE="text" style="width: 120"
					NAME="sy_date_picker9"
					value=""> <INPUT
					TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.sy_date_picker9,theform.sy_date_picker9.value,this);"
					tabindex="13"> <INPUT TYPE="hidden" NAME="sy_date9" value="0"></td>
	</tr>
	<tr>
		<td align="right" id="date4_n" style="display:none;">兑付期日期:</td>
		<td align="left"  id="date4" style="display:none;"><INPUT TYPE="text" style="width: 120"
					NAME="sy_date_picker4"
					value=""> <INPUT
					TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.sy_date_picker4,theform.sy_date_picker4.value,this);"
					tabindex="13"> <INPUT TYPE="hidden" NAME="sy_date4" value="0"></td>
		<td align="right" id="date6_n" style="display:none;">赎回日期:</td>
		<td align="left"  id="date6" style="display:none;"><INPUT TYPE="text" style="width: 120"
					NAME="sy_date_picker6"
					value=""> <INPUT
					TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.sy_date_picker6,theform.sy_date_picker6.value,this);"
					tabindex="13"> <INPUT TYPE="hidden" NAME="sy_date6" value="0"></td>
	</tr>
	<tr>
	<td align="right" colspan="4">
	<button type="button"  class="xpbutton3" accessKey=o name="btnQuery"
		onclick="javascript:StartQuery();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=b
		onclick="javascript:window.close();">返回(<u>B</u>)</button>
	</td>
	</tr>
</table>
</form>
</body>
</html>
<%%>
