<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
String column=Utility.trimNull(request.getParameter("column"));
String product_name=Utility.trimNull(request.getParameter("product_name"));
String cust_name=Utility.trimNull(request.getParameter("cust_name"));
String query_contract_bh=Utility.trimNull(request.getParameter("query_contract_bh"));
String fiel_info=Utility.trimNull(request.getParameter("file_info"));
int flag=Utility.parseInt(request.getParameter("flag"),0);
int dt_intrust = Argument.getSyscontrolValue("DT_INTRUST");
String[] col = column.split("\\$");

//页面辅助参数
String btnDisabled = "";
boolean bSuccess = false;
scaption=enfo.crm.tools.LocalUtilis.language("message.dataProc",clientLocale);//处理数据
String smessage=enfo.crm.tools.LocalUtilis.language("message.uploadSucc",clientLocale)+"！";//上传成功
input_bookCode = new Integer(1);//帐套暂时设置
String[] totalColumn = new String[0];
int iCount = 0;

List contractList = null;
Map contractMap = new HashMap();
//获得对象及结果集
DocumentFile file = new DocumentFile(pageContext);
ContractLocal contractLocal = EJBFactory.getContract();
ContractVO vo = new ContractVO();
//文件操作
if(request.getMethod().equals("POST")){
	file = new DocumentFile(pageContext);
	file.parseRequest();
	Integer inputFlag = Utility.parseInt(Utility.trimNull(file.getParameter("inputflag")),new Integer(0));

	product_name=Utility.trimNull(file.getParameter("product_name"));
	cust_name=Utility.trimNull(file.getParameter("cust_name"));
	query_contract_bh=Utility.trimNull(file.getParameter("query_contract_bh"));
	column=Utility.trimNull(file.getParameter("column"));
	sPage = Utility.trimNull(file.getParameter("page"));
	sPagesize = Utility.trimNull(file.getParameter("pagesize"));

	if(inputFlag.intValue()==5){		
		col = column.split("\\$");
		String[] contract = file.getParameterValues("合同编号");		
		if (contract!=null && col!=null) {
			if (! (col.length==1 && "".equals(col[0]))) {

				for (int j=0; j<contract.length; j++) {
					String sql = "UPDATE OLD SET ";
					String set = "";
					
					for(int i=0;i<col.length;i++){
						String[] a1 = file.getParameterValues(col[i]);
						if (! set.equals(""))
							set += ",";
						set += "["+col[i] + "]='" + a1[j] + "'";
					}
					sql+=set + " WHERE [合同编号] = '" + contract[j] + "'";
					System.out.println("sql语句是++++++++++"+sql);
					contractLocal.updateOld(sql);
				}
			}
		}
		smessage="保存成功！";
		bSuccess = true;
	}else{		
	if (file.uploadFile("c:\\temp")){
	    //excel文件的列必须是顺序的排序。例如有5列，应是A,B,C,D,E,不可是A,C,E,F,G.否则导进OLD表的数据不全
		if(file.readExcel2(pageContext,"c:\\temp",3000))
			smessage=enfo.crm.tools.LocalUtilis.language("message.uploadSucc",clientLocale)+"！";//上传成功
	}
	if(file.insertContractRecord(pageContext,input_operatorCode))
		smessage=enfo.crm.tools.LocalUtilis.language("message.importOk",clientLocale)+"！";//导入成功
	if(file.delOldData(pageContext))
		smessage=enfo.crm.tools.LocalUtilis.language("message.deleteOk",clientLocale)+"！";//删除成功
	if(file.correctOldData(pageContext,input_operatorCode))
		smessage=enfo.crm.tools.LocalUtilis.language("message.endCorrection",clientLocale)+"！";//纠正结束
	bSuccess = true;
	}
}
//设置参数
vo.setProduct_name(product_name);
vo.setCust_name(cust_name);
vo.setContract_bh(query_contract_bh);

int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//查询结果
IPageList pageList  = contractLocal.queryOldTemp(vo,totalColumn,t_sPage,t_sPagesize);
contractList = pageList.getRsList();
contractLocal.remove();

sUrl = "subscribe_import.jsp?pagesize="+sPagesize+"&column="+column
			+"&product_name="+product_name+"&cust_name="+cust_name+"&query_contract_bh="+query_contract_bh;	
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.salesAgent",clientLocale)%> </TITLE>
<!-- 代理销售 金汇专用-->
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
window.onload = function(){
	initQueryCondition();
	updateRgMoneyTotal();
}

<%if (bSuccess){%>
	sl_alert("<%=smessage%>");
<%}%>

function validateForm(){
	var filePath = document.getElementsByName("file_info")[0].value;
	if(filePath.length<=0){
		sl_alert('<%=LocalUtilis.language("message.uploadTip",clientLocale)%> ');//请选择上传文件
		return false;
	}

	if(confirm('<%=LocalUtilis.language("message.uploadTip2",clientLocale)%> ？')){//确认要上传文件吗
		disableAllBtn(true);
		document.theform.inputflag.value=2;
		var strfilename=document.theform.file_info.value;
		document.theform.file_name.value=strfilename;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}
}

function save(){
	var v_dt_intrust = document.getElementById("dt_intrust").value;
	if(v_dt_intrust==0){
		sl_alert('<%=LocalUtilis.language("message.flagIs0",clientLocale)%>，<%=LocalUtilis.language("message.confDB",clientLocale)%> ！');//您的信托分布式操作标志为0//请配置信托业务系统数据库服务器
	}
	if(confirm('<%=LocalUtilis.language("message.importDataTip",clientLocale)%> ？')){//确认要导入数据吗
		disableAllBtn(true);
		document.theform.inputflag.value=1;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}
}
function deleteOld(){
	if(confirm('<%=LocalUtilis.language("message.clearDataTip",clientLocale)%> ？')){//确认要清除数据吗
		disableAllBtn(true);
		document.theform.inputflag.value=3;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}
}
function correctOld(){
	if(confirm('<%=LocalUtilis.language("message.correctDataTip",clientLocale)%> ？'))　//确认要纠正历史数据吗
	{disableAllBtn(true);
		document.theform.inputflag.value=4;
		waitting.style.visibility='visible';
		return document.theform.submit();
	}
}

function StartQuery(){
    disableAllBtn(true);
	var product_name=document.theform.product_name.value;
	var cust_name=document.theform.cust_name.value;
	var query_contract_bh=document.theform.query_contract_bh.value;

	var url = "subscribe_import.jsp?column=<%=column%>&page=1&pagesize=" + document.theform.pagesize.value;
	url += "&cust_name=" + cust_name;
	url += "&query_contract_bh=" + query_contract_bh;
	url += "&product_name=" + product_name;

	location = url;
}
function refreshPage(){
	StartQuery();
}

function showInfo(product_name,contract_bh,cust_name){
	disableAllBtn(true);
	location = 'subscribe_import_detailed.jsp?cust_name='+cust_name+'&contract_bh='+contract_bh+'&product_name='+product_name;
}

function modiList(){
	var column=document.theform.column.value;
	var url='subscribe_import_set.jsp?column='+column;
	var list = showModalDialog(url, '', 'dialogWidth:512px;dialogHeight:300px;status:0;help:0;resizable:yes');

	if (list != null) {
		if(list.length>0){
			column = "";
			var product_name=document.theform.product_name.value;
			var cust_name=document.theform.cust_name.value;
			var query_contract_bh=document.theform.query_contract_bh.value;
			for(var i=0;i<list.length;i++){
				column += list[i] + "$";
			}

			location="subscribe_import.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&column="+column
						+"&product_name="+product_name+"&cust_name="+cust_name+"&query_contract_bh="+query_contract_bh;

		}else{
			var product_name=document.theform.product_name.value;
			var cust_name=document.theform.cust_name.value;
			var query_contract_bh=document.theform.query_contract_bh.value;

			location="subscribe_import.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&product_name="+product_name+"&cust_name="+cust_name+"&query_contract_bh="+query_contract_bh;

	}
	}
}

function saveAction(){
	if(confirm("确定要保存吗？")){
		disableAllBtn(true);
		document.theform.inputflag.value=5;
		document.theform.submit();
	}
}

function updateRgMoneyTotal() {
	var arr = document.getElementsByName("合同金额");
	if (arr==null || arr.length==0) return;

	var total = 0.0;
	for (var i=0; i<arr.length; i++) {
		total += parseFloat(arr[i].value);
		arr[i].value = setScale(arr[i].value, 2);
	}
	document.getElementById("total").value = "总计："+ setScale(total,2);
}

function setScale(num, scale) {
	var s = num+"";
	var idx = s.indexOf(".");
	if (idx<0) {
		s += ".";
		for (var i=0; i<scale; i++) 
			s += "0";
	} else {
		var l = s.length-1-idx;
		if (l<scale) {
			for (var i=0; i<scale-l; i++)
				s += "0";
		} else if (l>scale) {
			s = s.substring(0, idx+scale+1);
		}
	}

	return s;
}
</script>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="subscribe_import.jsp"  ENCTYPE="multipart/form-data">
<input type="hidden" name="page" value="<%=sPage%>"/>
<input type="hidden" name="column" value="<%=column%>">
<input type="hidden" name="inputflag" value="">
<input type="hidden" name="file_name" value="">
<input type="hidden" name="dt_intrust" id="dt_intrust" value="<%=dt_intrust%>">
<div id="queryCondition" class="qcMain" style="display:none;width:300px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right">
	   		<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
	   </td>
	  </tr>
	</table>
	<table>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
			<td  align="left">
				<input name="query_contract_bh" type="text" onkeydown="javascript:nextKeyPress(this)"  size="10" value="<%=query_contract_bh%>">
			</td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td  align="left">
				<input   name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="30">
			</td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
			<td  align="left">
				<input name="product_name" value="<%=Utility.trimNull(product_name)%>" onkeydown="javascript:nextKeyPress(this);" size="30">
			</td>
		</tr>
		<tr>
			<td  align="center" colspan=4>
				<!--确认-->
                <button class="xpButton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
			</td>
		</tr>
	</table>
</div>
<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>

	<div>
		<table border="0" width="100%" cellspacing="1" cellpadding="1">
			<tr>
				 <!--销售数据-->
                 <td  align="left"><%=LocalUtilis.language("message.salesData",clientLocale)%> :<input type="file" style="font-size:9pt" name="file_info" size="40"  onkeydown="javascript:return false;">&nbsp;
					<%if (input_operator.hasFunc(menu_id, 101)){%>
					<!--上传-->
                    <button class="xpbutton2" accessKey=u  id="btnSave" name="btnSave" onclick="javascript:validateForm();"><%=LocalUtilis.language("message.upload",clientLocale)%> </button>
					<%}%>
				</td>
				<td align=right>
					<button class="xpbutton5" id="btnmodi" name="btnmodi" onclick="javascript:modiList();">编辑显示列</button>
					<%--if (input_operator.hasFunc(menu_id, 102)){%>
					<!--纠正数据-->
                    <button class="xpbutton5" accessKey=c  id="btndete" name="btndete" onclick="javascript:correctOld();"><%=LocalUtilis.language("message.correctedData",clientLocale)%> </button>&nbsp;&nbsp;
					<%}--%>
					<%if (input_operator.hasFunc(menu_id, 100)){%>
					<!--清除数据-->
                    <button class="xpbutton5" id="btndete" name="btndete" onclick="javascript:deleteOld();"><%=LocalUtilis.language("message.clearData",clientLocale)%> </button>&nbsp;&nbsp;
					<!--确认导入-->
                    <button class="xpbutton5"  id="btnSave" name="btnSave" onclick="javascript:save();"><%=LocalUtilis.language("message.confImport",clientLocale)%> </button>&nbsp;&nbsp;
					<%}%>
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--查询-->
				 </td>
			 </tr>
		</table>
	</div>
	<hr noshade color="#808080" size="1">
</div>
<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="1" class="tablelinecolor" width="100%">
		<tr class="trh">	
			<td align="center" >项目名称 </td>
			<td align="center" ><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
			
			<%if(!"".equals(column)){for(int i=0;i<col.length;i++){%>
				<td align="center" ><%=col[i]%></td>
			<%}}%>
			<td align="center"><%=LocalUtilis.language("message.detail",clientLocale)%> </td><!--明细-->
		</tr>
<%
Iterator iterator = contractList.iterator();
String contract_bh = "";
String cust_name2 = "";
String product_name2 = "";

while (iterator.hasNext()){
	iCount++;
	contractMap = (Map)iterator.next();
	contract_bh = Utility.trimNull(contractMap.get("合同编号"));
	cust_name2 = Utility.trimNull(contractMap.get("委托人"));
	product_name2 = Utility.trimNull(contractMap.get("项目名称"));	
%>
	<tr class="tr<%=(iCount % 2)%>">
		<td align="center" ><input type="text" value="<%=Utility.trimNull(contractMap.get("项目名称"))%>" readonly="readonly" class="edline" size="40"></td>
		<td align="center" ><input type="text" name="合同编号" value="<%=contract_bh%>" readonly="readonly" class="edline" size="25"></td>
	<%if(!"".equals(column)){
		for (int i=0; i<col.length; i++) {	%>
		<td align="<%="合同金额".equals(col[i])?"right":"center"%>">
			<input type="text" name="<%=col[i]%>" value="<%=Utility.trimNull(contractMap.get(col[i]))%>"
				<%="合同金额".equals(col[i])?"onchange='javascript:updateRgMoneyTotal()' size='15' style='text-align:right'":"size='10'"%>/>
		</td>
	<%  }
	}%>
		<td align="center">
			<button class="xpbutton2" name="btnEdit"
			onclick="javascript:showInfo('<%=product_name2%>','<%=contract_bh%>','<%=cust_name2%>');">&gt;&gt;</button>
		</td>
	</tr>
<%} for(int i=0;i<(t_sPagesize-iCount);i++){%>
      <tr class="tr<%=i%2%>">
		 <td align="center">&nbsp;</td>
         <td align="center">&nbsp;</td>
         <td align="center">&nbsp;</td>
     <%if(!"".equals(column)) {for(int j=0;j<col.length;j++){%>
		 <td align="center">&nbsp;</td>
	 <%}}%>
      </tr>
<%}%>
	<tr class="trbottom">
		<!--合计--><!--项-->
        <td class="tdh" align="left" colspan="2"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b>
		</td>
<%if(!"".equals(column)){
	for (int i=0; i<col.length; i++) {
		if ("合同金额".equals(col[i])) {	%>
		<td align="right">
			<input type="text" id="total" size="21" class="edline" readonly style='text-align:right'/>
		</td>
<%		} else { %>
		<td align="center">&nbsp;</td>
<%		}
  }
}%>
		<td align="center">
			<button class="xpbutton3" id="btnSave" name="btnSave" onclick="javascript:saveAction();">保存</button>	
		</td>
	</tr>
	</table>
</div>
<br>
<div><%=pageList.getPageLink(sUrl,clientLocale)%></div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</html>