<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
Integer sub_flag 		= new Integer(0);
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer product_id=Utility.parseInt(Utility.trimNull(request.getParameter("product_id")), new Integer(0)); 
Integer sub_product_id=Utility.parseInt(Utility.trimNull(request.getParameter("sub_product_id")), new Integer(0)); 
if (product_id.intValue()==0){
	product_id=session_product_id;
}
//获得产品SUB_FLAG
if(product_id.intValue()!=0){
	sub_flag = Utility.parseInt(Argument.getProductFlag(product_id,"SUB_FLAG"),new Integer(0));
}

DocumentFile file = null;
String btnDisabled = "";
boolean bSuccess = false;
file = new DocumentFile(pageContext);
int flag = Utility.parseInt(request.getParameter("flag"),0);
String fiel_info = Utility.trimNull(request.getParameter("file_info"));
scaption = "处理数据";
String smessage = "上传成功！";

if (request.getMethod().equals("POST"))
{
	file = new DocumentFile(pageContext);	
	file.parseRequest();
	if (file.uploadFile("c:\\temp"))
	{
	    //excel文件的列必须是顺序的排序。例如有5列，应是A,B,C,D,E,不可是A,C,E,F,G.否则导进OLD表的数据不全
		if(file.readExcel4(pageContext,"c:\\temp",input_operatorCode))
			smessage = "文件导入成功！";
	}
	if(file.deleteRead4(pageContext,input_operatorCode))
		smessage = "删除成功！";	
	
	if(file.saveRead4(pageContext,input_operatorCode))
		smessage = "确认成功！";	
	bSuccess = true;
}

int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48);
options = options.replaceAll("\"", "'");

String fxip = Argument.getDictParamValue("800901");

System.out.println("*********fxip**************"+fxip);

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
</HEAD>

<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx1.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
	initQueryCondition();
	initQueryCondition1();
	selectProductItem("<%=product_id%>");
}
<%if (bSuccess){%>
	sl_alert("<%=smessage%>");
	location = "purchase_mode_import.jsp";
<%}%>

function validateForm()
{
	var filePath = document.getElementsByName("file_info")[0].value;
	if(filePath.length<=0){
		sl_alert("请选择上传文件");
		return false;
	}

	if(confirm("确认要上传文件吗？"))　
	{
		disableAllBtn(true);
		document.theform.inputflag.value = 2;
		var strfilename = document.theform.file_info.value;
		document.theform.file_name.value = strfilename;
		waitting.style.visibility = 'visible';
		return document.theform.submit();
	}	
}

function save(){
	if(!sl_checkChoice(document.theform.product_id, "产品"))	return false;
	if (checkedCount(document.theform.serial_no)==0){
		alert("请选择要导入的数据");
		return false;
	}
	if(confirm("确认要导入数据吗？"))　
	{disableAllBtn(true);
		document.theform.inputflag.value = 1;
		waitting.style.visibility = 'visible';
		return document.theform.submit();
	}	
}

function deleteOld()
{
	if(confirmRemove(document.theform.serial_no))　
	{
		disableAllBtn(true);
		document.theform.inputflag.value = 3;
		waitting.style.visibility = 'visible';
		return document.theform.submit();
	}	
}

 

function StartQuery()
{
    disableAllBtn(true);
	cust_name = document.theform.cust_name.value;
	location = "purchase_mode_import.jsp?page=1&cust_name=" + cust_name 
					+ '&pagesize=' + document.theform.pagesize.value;

}

function refreshPage()
{
	StartQuery();
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
			document.theform.product_id.value="";
			document.theform.product_id.options[0].selected=true;	
		}else{
			selectProductItem(document.theform.product_id.value);
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
			sl_alert('输入的产品编号不存在！');
			document.theform.product_id.value="";
			document.theform.product_id.options[0].selected=true;	
		}else{
			selectProductItem(document.theform.product_id.value);
		}
		document.theform.product_id.focus();					
	}	
}

function filterProduct(product_name){
    if(event.keyCode==13){
        j$("[name='product_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='product_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='product_id']").children(":not([text*='"+product_name+"'])").remove();
		selectProductItem(document.theform.product_id.value);
    }else{
        return false;    
    }           
}

function productFilter(value){    
		var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = 
				"<SELECT name='product_id'	class='productname' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:selectProductItem(this.value);'  style='width: 335px;'>"+"<%=options%>"+"</SELECT>";
	if (value != ""){
		for (var i=0; i<document.theform.product_id.options.length; i++) {
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if (j>0) {
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if (list.length==0) {
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.product_code.value = "";
			document.theform.product_id.options[0].selected=true;
		} else {
			document.theform.product_id.options.length=0;
			for (var i=0; i<list.length; i++)
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
		}
		document.theform.product_id.focus();
	}
}

function showInfo(serial_no)
{
	if(showModalDialog('purchase_import_money_update.jsp?serial_no=' + serial_no, '', 'dialogWidth:420px;dialogHeight:360px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function showfile(file_name,name){
	document.body.onbeforeunload = function(){};
	
	location = "/marketing/sell/downtemplate.jsp?file_name="+file_name+'&name='+name;
}


function selectProductItem(product_id){
	if(product_id == "0"){
		document.getElementById("sub_product").style.display = "none";
		return false;
	}
	utilityService.getSubProductList(product_id,3,callback);
	utilityService.getbankOptions(<%=input_bookCode%>,product_id,"01",callback2);
}

function callback(data){
	var span = document.getElementById("sub_product_span");
	var display = "none";
	span.innerHTML  = "<select size='1' name='sub_product_id' onkeydown='javascript:nextKeyPress(this)' class='productname'>"+data+"</select>";
	if(data != "<option value=\"\">请选择</option>")display = "";

	document.getElementById("sub_product").style.display = display;
}
function callback2(data1){
	var span = document.getElementById("sbf_serial_span");
	span.innerHTML="<select size='1' name='sbf_serial_no'  onkeydown='javascript:nextKeyPress(this)'  style='WIDTH: 300px'>"+data1+"</select>";
}
</script>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>	

<form name="theform" method="post" action="purchase_mode_import.jsp"  ENCTYPE="multipart/form-data">
<input type="hidden" name="inputflag" value="">
<input type="hidden" name="file_name" value="">
<div id="queryCondition" class="qcMain" style="display:none;width:300px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  	<tr>
	   		<td>查询条件：</td>
	   		<td align="right"><button class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button>
	        </td>
	  	</tr>
	</table>

<table>
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
 
			<input name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="30">
		</td>
 		
	</tr>
<!-- 
	<tr>	
		<td align="right">到账日期:</td>
		<td align="left">
			<input name="dz_date" value="" onkeydown="javascript:nextKeyPress(this);" size="30">
		</td>	
   	</tr>
 -->
	<tr>
		<td align="center" colspan=4>
			<button class="xpButton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<div id="queryCondition1" class="qcMain" style="display:none;width:500px;">
<table  id="qcTitle1" class="qcTitle1" align="center" width="99%" cellspacing="0" cellpadding="2">
  	<tr>
   		<td>导入条件：</td>
   		<td align="right"><button class="qcClose" accessKey=c id="qcClose1" name="qcClose1"
       		onclick="javascript:cancelQuery1();"></button></td>
  	</tr>
</table>

<table>
	<tr>
		<tr>
			<td align="right">产品编号:</td>
			<td align="left" width="" height="" >
				<input type="text" name="product_code" value=""  size="20" onKeyDown="javascript:setProduct(this.value);" size="10">&nbsp;
				<button class="searchbutton" onclick="javascript:searchProduct(document.theform.product_code.value);" /></button>
			</td>
			<td align="right">产品名称:</td>
			<td align="left">
				<INPUT type="text" name="product_name" size="20" onkeydown="javascript:filterProduct(this.value);nextKeyPress(this)" value="">				    
				&nbsp;
				<button class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:productFilter(document.theform.product_name.value);" /></button>
			</td>
		</tr>
		
	<tr>
		<td align="right">产品选择:</td>
		<td align="left" colspan=3 id="select_id">
			<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectProductItem(this.value);">
				<%=Argument.getProductListOptions(input_bookCode,product_id, "", input_operatorCode,58)%>
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

		<tr >			
			<td align="right">到账银行:</td>
			<td align="left" colspan="3">
				<div id="sbf_serial_span">
				<select size="1" name="sbf_serial_no"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 300px">
					<%=Argument.getbankOption(input_bookCode,product_id,"",new Integer(0))%>  
				</select>
				</div>
			</td>
		</tr>
	<tr>
		<td align="center" colspan=4>
			<button class="xpButton3" accessKey=o name="btnQuery" onclick="javascript:save();">确定(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file= "menu.inc"%>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="10">
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan="2"><IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr>
					    <td align="left">认购资金:<input type="file" style="font-size:9pt" name="file_info" size="40" onkeydown="javascript:return false;">&nbsp;
							<button class="xpbutton4" accessKey=u id="btnSave" name="btnSave" onclick="javascript:validateForm();">文件导入</button>

						</td>
					    <td align=right>
							<button class="xpbutton4" id="btndete" name="btndete" onclick="javascript:deleteOld();">删除</button>&nbsp;&nbsp;	
							<button class="xpbutton4" id="queryButton1" name="queryButton1">确认导入</button>&nbsp;&nbsp;	
							<button class="xpbutton4" accessKey=d id="downloadButton" name="downloadButton" onclick="javascript:showfile('c:\\temp\\认购资金导入模板示例.xls','认购资金导入模板示例.xls')">模板下载(<u>D</u>)</button>&nbsp;&nbsp;&nbsp;					
							<button class="xpbutton3" accessKey=q  id="queryButton" name="queryButton">查询 (<u>Q</u>)</button><!--查询-->
				 		 </td>
					</tr>
					<tr>
						<td colspan="2"><hr noshade color="#808080" size="1"></td>
					</tr>
				</table>
					<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" bgcolor="#000000" width="100%">
						<tr class="trh">
							<td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);">到账日期</td>
							<td align="center" >金额</td>
							<td align="center" >份额</td>
							<td align="center" >客户名称</td>
							<td align="center" >客户类型</td>
							<td align="center" >推荐地</td>
							<td align="center" >受益人银行</td>
							<td align="center" >受益人银行支行</td>
							<td align="center" >受益人账号</td>
							<td align="center" colspan="2">资金来源</td>
<%if(fxip!=null && fxip!=""){ %>
							<td align="center" >风险预估</td>
<%} %>
						</tr>
<%
	List contractList = new ArrayList();
	ContractLocal contract = EJBFactory.getContract();


	IPageList pageList  =contract.queryMoenyImport(cust_name,totalColumn,t_sPage,t_sPagesize);
	contractList = pageList.getRsList();


	sUrl = sUrl + "&cust_name=" + cust_name ;
	
 
Iterator iterator = contractList.iterator();

java.math.BigDecimal amount = new java.math.BigDecimal("0.00");
int iCount = 0;
int iCurrent = 0;
Integer serial_no;
	
BigDecimal to_money = new BigDecimal(0);
String cust_names="";

while (iterator.hasNext()){
	iCount++;
	Map map = (Map)iterator.next();
	to_money= Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),null).setScale(2,2);
 	amount=amount.add(to_money);
	cust_names = Utility.trimNull(map.get("CUST_NAME"));
%>
	
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center">
							<input type="checkbox" name="serial_no" value="<%=Utility.trimNull(map.get("SERIAL_NO"))%>" class="flatcheckbox">
							<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),null))%>
						</td>
						<td align="left" ><%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("TO_MONEY")))%></td>
						<td align="left" ><%=Utility.trimNull(Format.formatMoney((BigDecimal)map.get("TO_AMOUNT")))%></td>
						<td align="left"  ><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
						<%
							String custTypeStr  = "";
							int iType = ((Integer)(map.get("CUST_TYPE"))).intValue();
							if(map.get("CUST_TYPE")!= null &&iType == 1){
								custTypeStr = "个人";
							}
							if(map.get("CUST_TYPE")!= null && iType == 2){
								custTypeStr = "机构";
							}
							 %>
						<td align="center"><%=custTypeStr%></td>
						<td align="center"><%=Utility.trimNull(map.get("CITY_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("BANK_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("BANK_SUB_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("BANK_ACCT"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("MONEY_ORIGIN"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("SUB_MONEY_ORIGIN"))%></td>
<%if(fxip!=null && fxip!=""){ %>
						<td align="center">
							<jsp:include page="/marketing/sell/fengxin.jsp" flush="true">
								<jsp:param name="url" value="<%=fxip%>"/>
								<jsp:param name="cust_name" value="<%=cust_names%>"/>
							</jsp:include>		
										
						</td>
<%} %>
					</tr>
<%}
for(int i=0;i<(t_sPagesize-iCount);i++){%>

					<tr class="tr<%=i%2%>">
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
<%if(fxip!=null && fxip!=""){ %>
						<td align="center" ></td>
<%} %>
					</tr>
		<%}%>
					<tr class="trbottom">
						<td class="tdh" align="center" >
							<b>合计<%=pageList.getTotalSize()%>项</b>
						</td>
						<td align="left" ><%=Format.formatMoney(amount)%></td>
						<td align="center"  >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
<%if(fxip!=null && fxip!=""){ %>
						<td align="center" >-</td>
<%}%>
					</tr>
				</table>
				
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>

				</td>
			</tr>
</table>
</TD>
</TR>
</TABLE>

</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%
	contract.remove();
}catch(Exception e){
	throw e;
}


%>