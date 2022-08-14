<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*"  %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
try {
Integer sub_flag = new Integer(0);
String[] serial_nos = request.getParameterValues("serial_no");
Integer serial_no = new Integer(0);

BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();

String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));

String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
Integer sub_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("sub_product_id")),new Integer(0));
Integer prov_flag = Utility.parseInt(Utility.trimNull(request.getParameter("prov_flag")),new Integer(0));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
Integer prov_flag2 = Utility.parseInt(Utility.trimNull(request.getParameter("prov_flag2")),new Integer(0));
String prov_level2 = Utility.trimNull(request.getParameter("prov_level2"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);

boolean bSuccess = false;

if (request.getMethod().equals("POST")){
	vo.setProv_flag(prov_flag2);
	vo.setProv_level(prov_level2);
	vo.setInput_man(input_operatorCode);

	for(int i=0;i<serial_nos.length;i++){
		serial_no = Utility.parseInt(serial_nos[i],new Integer(0));
		vo.setSerial_no(serial_no);
		benifitor.modi_prov_level(vo);
	}
	bSuccess = true;
}

vo.setBook_code(input_bookCode);
vo.setProduct_id(product_id); 
vo.setContract_sub_bh(contract_sub_bh);
vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setInput_man(input_operatorCode);
vo.setCheck_flag(new Integer(1));
vo.setProv_flag(prov_flag);
vo.setProv_level(prov_level); 
vo.setSub_product_id(sub_product_id);
IPageList pageList = benifitor.QueryBenifitorProv(vo,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,8));

String sProv_flag  = null;
 
if(prov_flag.intValue() == 2)
	sProv_flag ="一般";
else if(prov_flag.intValue() == 3)
	sProv_flag ="劣后";
else
	sProv_flag ="优先";

ProductLocal product = EJBFactory.getProduct();
ProductVO pVO = new ProductVO();
pVO.setProduct_id(product_id);
List pList = product.load(pVO);
if(pList.size()>0){
	Map pMap = (Map)pList.get(0);
	sub_flag = Utility.parseInt(Utility.trimNull(pMap.get("SUB_FLAG")),new Integer(0));
}
product.remove();

String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,0);
options = options.replaceAll("\"", "'");

benifitor.remove();

sUrl += "&product_id=" + product_id +"&contract_sub_bh="+ contract_sub_bh+"&cust_name="+cust_name+"&prov_level="+prov_level+"&cust_no="+cust_no;
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/div.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
<%if (bSuccess){%>
	sl_alert("调整收益级别成功");
<%}%> 

window.onload = function(){
		initQueryCondition();
		initDiv('changeLevelDiv','changeLevelButton');
	};

function showInfo(serialno, contract_bh , sub_product_id,product_id) {
	if (showModalDialog('benifiter_level_info.jsp?serial_no='+serialno+'&contract_bh='+contract_bh
			+'&sub_product_id='+sub_product_id+'&product_id='+product_id+'&check_flag='+3, '', 'dialogWidth:730px;dialogHeight:380px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

function refreshPage(){
	StartQuery();
}

function StartQuery(){	
	var sub_product_id = 0;
	if(document.theform.sub_product_id)	sub_product_id = document.theform.sub_product_id.value;
	
	disableAllBtn(true);
	location = 'benifiter_level_modi.jsp?contract_sub_bh='+document.theform.contract_sub_bh.value+'&page=1&pagesize='+
		document.theform.pagesize.value+'&product_id='+document.theform.product_id.value+'&cust_name='+document.theform.cust_name.value+
	 	'&cust_no='+document.theform.cust_no.value+'&sub_product_id='+sub_product_id+'&prov_flag='+document.theform.prov_flag.value+'&prov_level='+document.theform.prov_level.value;
}

//变更收益级别
function changeLevel() {
	<%if (product_id.intValue()==0){%>
		sl_alert("批量调整时，必需在查询中选择产品");
			return false;
	<%}%>
	if (document.theform.prov_flag2.value=='0') {
		sl_alert("请选择需调整的受益优先级");
		return false;
	}
	if (! document.theform.prov_level2.value) {
		sl_alert("请选择需调整的收益级别");
		return false;
	}
	if (selectConfirm(document.theform.serial_no,"批量调整收益级别")) {
		document.theform.method="post";
		document.theform.submit();
	}
}

function setProduct(value) {
	prodid=0;
	if (event.keyCode == 13 && value != "") {
        j = value.length;
		for (i=0; i<document.theform.product_id.options.length; i++) {
			if (document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('输入的产品编号不存在!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		} else {
			productChange(prodid);	
		}		
	}
}
 
/*搜索产品*/
function searchProduct(value){
	if (event.keyCode == 13) searchProduct2(value);	
}

/*搜索产品2*/
function searchProduct2(value){
	var prodid=0;
	if (value != "") {
        var j = value.length;

		for (i=0; i<document.theform.product_id.options.length; i++) {
			if(document.theform.product_id.options[i].text.indexOf(value) >= 0)	{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.product_id.options[0].selected=true;
		}
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
	
	utilityService.getSubProductFlag(_prodcut_id,function(data){
		if (data==1)
			tr_sub_product_id.style.display="";
		else
			tr_sub_product_id.style.display="none";			
	});
	utilityService.getSubProductJson(product_id,3,function(data){
		//alert(data);
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for (i=0;i<json.length;i++)
			DWRUtil.addOptions(sub_product_id,json[i]);
		
		utilityService.getSubProductProvFlag(product_id,0,0,function(_data){
			var _json = eval(_data);
			DWRUtil.addOptions(prov_flag,{"0":"请选择"});
			for(i=0;i<_json.length;i++)
				DWRUtil.addOptions(prov_flag,_json[i]);
			
			utilityService.getProvLevelJson(product_id,0,0,function(__data){
				var __json = eval(__data);
				DWRUtil.addOptions(prov_level,{"":"请选择"});
				for(i=0;i<__json.length;i++) 
					DWRUtil.addOptions(prov_level,__json[i]);						
			})
		})
	});
}

function subProductChange(product_id,sub_product_id){	
	var _prodcut_id = product_id;
	var _sub_product_id = sub_product_id;
	var prov_flag;
	var prov_level;
	
	prov_flag = document.theform.prov_flag;
	prov_level = document.theform.prov_level;	

	DWRUtil.removeAllOptions(prov_flag);
	DWRUtil.removeAllOptions(prov_level);
	
	utilityService.getSubProductProvFlag(product_id,_sub_product_id,0,function(_data){
		var _json = eval(_data);
		DWRUtil.addOptions(prov_flag,{"0":"请选择"});
		for(i=0;i<_json.length;i++)
			DWRUtil.addOptions(prov_flag,_json[i]);
		
		utilityService.getProvLevelJson(product_id,_sub_product_id,0,function(__data){
			var __json = eval(__data);
			DWRUtil.addOptions(prov_level,{"":"请选择"});
			for(i=0;i<__json.length;i++)
				DWRUtil.addOptions(prov_level,__json[i]);
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
		for(i=0;i<__json.length;i++)
			DWRUtil.addOptions(prov_level,__json[i]);
	});
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)' style='width:345px;'>"+"<%=options%>"+"</SELECT>";
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
			for(var i=0;i<list.length;i++)
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox"><%@ include file="/includes/waiting.inc"%> 
<form name="theform" action="benifiter_level_modi.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
	   <td>查询条件：</td>
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button>
		</td>
  </tr>
</table>

<table>
	<tr>
		<td align="right">产品编号:</td>
		<td align="left" >
			<input type="text" name="product_code" value="" onKeyDown="javascript:searchProduct(this.value);" size="10">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct2(document.theform.product_code.value);"></button>
		</td>
		<td  align="right">产品名称 :</td>
		<td  align="left" >
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right">产品名称:</td>
		<td colspan="3" align="left" id="select_id">
			<SELECT size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class=productname style="width:345px;">
			<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,0)%>
			</SELECT> 
		</TD>
   	</tr>
	<tr id="tr_sub_product_id" style="<%if(sub_flag!=null && sub_flag.intValue() != 1&&sub_product_id.intValue()==0){%>display:none<%}%>">			
			<td align="right">子产品:</td>
			<td align="left" colspan="3" id="td_sub_product_id">
				<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getSubProductOptions2(product_id, new Integer(0),sub_product_id,3,"113801")%> 
				</SELECT>
			</td>
		</tr>
		<tr>
		<td align="right">受益优先级:</td>
		<td>
			<SELECT size="1" name="prov_flag" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<option value="0">请选择</option>
				<%=Argument.getTableOptions2(3003,prov_flag)%>
			</SELECT>
		</td>
		<td align="right">收益级别:</td>
		<td>
			<SELECT size="1" name="prov_level" id="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<%=Argument.getProvlevelOptions(prov_level)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td  align="right">
		合同编号: 
		</td>
		<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="15" value="<%=Utility.trimNull(contract_sub_bh)%>" style="width:120px">&nbsp;&nbsp;</td>
		<td  align="right">
		受益人编号: 
		</td>
		<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="cust_no" size="15" value="<%=Utility.trimNull(cust_no)%>" style="width:120px">&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td  align="left">受益人名称: </td>
		<td align="left" colspan='3'>
			<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" style="width:120px">
		</td>
    </tr>
    <tr>
		<td align=center colspan=4>
		<button type="button"  class="xpbutton3" accesskey="o" name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</BUTTON>
		</td>
	</tr>
</table>
</div>

<div id="changeLevelDiv" class="qcMain" style="display:none;width:270px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
	   <td>调整收益级别：</td>
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelDiv();"></button>
		</td>
  </tr>
</table>

<table>
	<tr>
		<td align="right">受益优先级:</td>
		<td>
			<SELECT size="1" name="prov_flag2" onchange="javascript:provFlagChange(document.theform.product_id.value,document.theform.sub_product_id.value,this.value,'prov_level2')" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<option value="0">请选择</option>
				<%=Argument.getProductProvFlag(product_id,sub_product_id,prov_flag2)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;收益级别：</td>
		<td align="left">
			<SELECT size="1" name="prov_level2" id="prov_level2" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<%=Argument.getProductProvLevel(product_id,sub_product_id,prov_flag2,prov_level2)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align=center colspan=2>
		<button type="button"  class="xpbutton3" accesskey="o" name="btnQuery" onclick="javascript:changeLevel();">确定(<u>O</u>)</BUTTON>
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
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=8 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr><td align=right> 
					<div class="btn-wrapper">
						<%if (input_operator.hasFunc(menu_id,108) ){%>
						<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>&nbsp;&nbsp;
						<%}%>
						<%if (input_operator.hasFunc(menu_id,102) ){%>
						<button type="button"  class="xpbutton5"  accessKey=f id="changeLevelButton" name="changeLevelButton">调整收益级别</button>
						<%}%>
						</div>
						<br/>
					</td></tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<TD align="center" >
						<input type="checkbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
						产品名称</TD>
						<td align="center"  noWrap>合同编号</td>
						<td align="center"  >受益人编号</td>
						<td align="center" >受益人名称</td>
						<td align="center" >受益份额</td>
						<td align="center" >受益日期</td>
						<td align="center" >受益优先级</td>
						<td align="center" >收益级别</td>
						<td align="center" >收益率</td>
						<td align="center" >编辑</td>	
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
int iBenflag=0;
List list = pageList.getRsList();
for (;iCount<list.size();) {
	Map map = (Map)list.get(iCount);
	
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	sub_product_id = Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0));
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	String list_id = Utility.trimNull(map.get("LIST_ID"));
	cust_no = Utility.trimNull(map.get("CUST_NO"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	BigDecimal ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0.00));
	Integer ben_date = Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0));
	Integer map_prov_flag = Utility.parseInt(Utility.trimNull(map.get("PROV_FLAG")),new Integer(0));
	String prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
	String map_prov_level = Utility.trimNull(map.get("PROV_LEVEL"));
	String map_sProv_flag = null;
	if(map_prov_flag.intValue() == 2)
		map_sProv_flag ="一般";
	else if(map_prov_flag.intValue() == 3)
		map_sProv_flag ="劣后";
	else
		map_sProv_flag ="优先";
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<TD align="left">
						<input type="checkbox" name="serial_no" value='<%=serial_no%>' class="flatcheckbox">
						<%=Utility.trimNull(product_name)%>
						</TD>
						<td align="left"><%=Utility.trimNull(contract_sub_bh)%>-<%=Utility.trimNull(list_id)%></td>
						<td class="tdh" align="left"><%=Utility.trimNull(cust_no)%></td>
						<TD align="left"  noWrap><%=Utility.trimNull(cust_name)%></TD>
						<td align="right"><%=Format.formatMoney(ben_amount)%></td>
						<td align="center"><%=Format.formatDateCn(ben_date)%></td>
						<td align="center"><%=Utility.trimNull(map_sProv_flag)%></td>
						<td align="center" width="10%"><%=Utility.trimNull(prov_level_name)%></td>
						<td align="center"><%=Argument.getGainRate(product_id+"",map_prov_flag+"",map_prov_level)%>%</td>
						<td align="center">
						<%if (input_operator.hasFunc(menu_id,102) ){%>
							<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<%=serial_no%>,'<%=contract_sub_bh%>','<%=sub_product_id%>','<%=product_id %>');">&gt;&gt;</button>
						<%}%>
						</td>
					
					</tr>
<%iCurrent++;
iCount++;
}

for (; iCurrent < pageList.getPageSize(); iCurrent++) {
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<TD align="center" ></TD>
						<TD align="center" ></TD>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="center"><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<TD align="center">-</TD>
						<TD align="center">-</TD>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%" class="paeg-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
						
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%
}catch(Exception e){
	throw e;
}
%>
