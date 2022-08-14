<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取产品对象，用于新增合同要点
TcoProductLocal tcoProductLocal = EJBFactory.getTcoProduct();
TcoProductVO tcoProductVO = new TcoProductVO();
Map map = null;
List rsList = null;
tcoProductVO.setInput_man(input_operatorCode);
tcoProductVO.setCoProduct_id(new Integer(0));
tcoProductVO.setCoProduct_name("");
rsList = tcoProductLocal.listProcAllExt(tcoProductVO);	
	
TcoContractLocal tcoContractLocal = EJBFactory.getTcoContract();
TcoContractVO tcoContractVO = new TcoContractVO();
TcoContractPayPlanLocal tcoContractPayPlanLocal = EJBFactory.getTcoContractPayPlan();
TcoContractPayPlanVO tcoContractPayPlanVO = new TcoContractPayPlanVO();
TcoContractPointsLocal tcoContractPointsLocal = EJBFactory.getTcoContractPoints();
TcoContractPointsVO tcoContractPointsVO = new TcoContractPointsVO();
Integer coContract_id=Utility.parseInt(request.getParameter("coContract_id"),new Integer(0));
tcoContractVO.setCocontract_id(coContract_id);
tcoContractVO.setInput_man(input_operatorCode);
List tcoContractList=tcoContractLocal.queryByList(tcoContractVO);
Map tcoContractMap=(Map)tcoContractList.get(0);
String cust_name=Utility.trimNull(tcoContractMap.get("CUST_NAME"));
String cocontract_sub_bh=Utility.trimNull(tcoContractMap.get("COCONTRACT_SUB_BH"));
String ht_money=Utility.trimNull(tcoContractMap.get("HT_MONEY"));
String payment_type=Utility.trimNull(tcoContractMap.get("PAYMENT_TYPE"));
String cocontract_type=Utility.trimNull(tcoContractMap.get("COCONTRACT_TYPE"));
String sign_date=Utility.trimNull(tcoContractMap.get("SIGN_DATE"));
String start_date=Utility.trimNull(tcoContractMap.get("START_DATE"));
String end_date=Utility.trimNull(tcoContractMap.get("END_DATE"));
String main_end_date=Utility.trimNull(tcoContractMap.get("MAIN_END_DATE"));
//modi 20111029
String comment = Utility.trimNull(tcoContractMap.get("SUMMARY"));

tcoContractPointsVO.setCoContract_id(coContract_id);
tcoContractPointsVO.setSubcoContract_id(new Integer(0));
tcoContractPointsVO.setInput_man(input_operatorCode);
List tcoContractPointsList=tcoContractPointsLocal.queryByList(tcoContractPointsVO);
String tcoContractPointsJson=JsonUtil.list2json(tcoContractPointsList);

tcoContractPayPlanVO.setCocontract_id(coContract_id);
tcoContractPayPlanVO.setPayPlan_id(new Integer(0));
tcoContractPayPlanVO.setPay_summary("");
tcoContractPayPlanVO.setInput_man(input_operatorCode);
List tcoContractPayPlanList=tcoContractPayPlanLocal.queryByList(tcoContractPayPlanVO);
String tcoContractPayPlanJson=JsonUtil.list2json(tcoContractPayPlanList);

//附件相关变量
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(coContract_id);
attachmentVO.setDf_talbe_id(new Integer(101053));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);
boolean bSuccess = false;
/*********因为增加了一个附件上传，所以原先request方法取值的改为 file*******************/
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
    file.parseRequest();
    coContract_id=Utility.parseInt(file.getParameter("coContract_id"),new Integer(0));
	tcoContractVO.setCocontract_id(coContract_id);	
	tcoContractVO.setCust_id(Utility.parseInt(file.getParameter("cust_id"),new Integer(0)));
	tcoContractVO.setCocontract_sub_bh(Utility.trimNull(file.getParameter("cocontract_sub_bh")));
	tcoContractVO.setSign_date(Utility.parseInt(file.getParameter("sign_date"),new Integer(0)));
	tcoContractVO.setStart_date(Utility.parseInt(file.getParameter("start_date"),new Integer(0)));
	tcoContractVO.setEnd_date(Utility.parseInt(file.getParameter("end_date"),new Integer(0)));
	tcoContractVO.setMain_end_date(Utility.parseInt(file.getParameter("main_end_date"),new Integer(0)));
	tcoContractVO.setHt_money(Utility.parseDecimal(file.getParameter("ht_money"),new BigDecimal(0.00)));
	tcoContractVO.setPayment_type(Utility.trimNull(file.getParameter("payment_type")));
	tcoContractVO.setCocontract_type(Utility.trimNull(file.getParameter("cocontract_type")));
	tcoContractVO.setInput_man(input_operatorCode);
	tcoContractVO.setComment(Utility.trimNull(file.getParameter("comment")));
	tcoContractLocal.modi(tcoContractVO);


	file.uploadAttchment(new Integer(101053),"TCOCONTRACT",coContract_id,"",input_operatorCode);
	//保存合同要点明细和付款计划时采取先全部删除再全部添加的方式
	//删除
	tcoContractPointsVO.setCoContract_id(coContract_id);
	tcoContractPointsVO.setSubcoContract_id(new Integer(0));
	tcoContractPointsVO.setInput_man(input_operatorCode);
	tcoContractPointsLocal.delete(tcoContractPointsVO);
	//添加
	String coProduct_id=Utility.trimNull(file.getParameter("coProduct_ids"));
	String coProduct_name=Utility.trimNull(file.getParameter("coProduct_names"));
	String sub_ht_money=Utility.trimNull(file.getParameter("sub_ht_moneys"));
	String point_summary=Utility.trimNull(file.getParameter("point_summarys"));
	if(!coProduct_id.equals("")){
		String[] coProduct_ids= coProduct_id.split(",");
		String[] coProduct_names= coProduct_name.split(",");
		String[] sub_ht_moneys= sub_ht_money.split(",");
		String[] point_summarys= point_summary.split(",");
		tcoContractPointsVO.setCoContract_id(coContract_id);
		tcoContractPointsVO.setInput_man(input_operatorCode);
		for(int i=0;i<coProduct_ids.length;i++){
			tcoContractPointsVO.setCoProduct_id(Utility.parseInt(coProduct_ids[i],new Integer(0)));
			tcoContractPointsVO.setSub_ht_money(Utility.parseDecimal(sub_ht_moneys[i],new BigDecimal(0.00)));
			String temp=point_summarys[i];
			if(temp.equals("@南#无$阿%弥^陀&佛*")){
				temp="";
			}
			tcoContractPointsVO.setPoint_summary(temp);
			tcoContractPointsLocal.append(tcoContractPointsVO);
		}
	}
	//保存合同要点明细和付款计划时采取先全部删除再全部添加的方式
	//删除
	tcoContractPayPlanVO.setCocontract_id(coContract_id);
	tcoContractPayPlanVO.setPayPlan_id(new Integer(0));
	tcoContractPayPlanVO.setInput_man(input_operatorCode);
	tcoContractPayPlanLocal.delete(tcoContractPayPlanVO);
	//添加
	String pay_num=Utility.trimNull(file.getParameter("pay_nums"));
	String pay_num_name=Utility.trimNull(file.getParameter("pay_num_names"));
	String exp_date=Utility.trimNull(file.getParameter("exp_dates"));
	String pay_rate=Utility.trimNull(file.getParameter("pay_rates"));
	String pay_money=Utility.trimNull(file.getParameter("pay_moneys"));
	String pay_summary=Utility.trimNull(file.getParameter("pay_summarys"));
	if(!exp_date.equals("")){
		String[] pay_nums= pay_num.split(",");
		String[] pay_num_names= pay_num_name.split(",");
		String[] exp_dates= exp_date.split(",");
		String[] pay_rates= pay_rate.split(",");
		String[] pay_moneys= pay_money.split(",");
		String[] pay_summarys= pay_summary.split(",");
		tcoContractPayPlanVO.setCocontract_id(coContract_id);
		tcoContractPayPlanVO.setInput_man(input_operatorCode);
		for(int i=0;i<pay_nums.length;i++){
			tcoContractPayPlanVO.setPay_num(Utility.parseInt(pay_nums[i],new Integer(0)));
			tcoContractPayPlanVO.setPay_num_name(pay_num_names[i]);
			tcoContractPayPlanVO.setExp_date(Utility.parseInt(exp_dates[i],new Integer(0)));
			tcoContractPayPlanVO.setPay_rate(Utility.parseDecimal(pay_rates[i],new BigDecimal(0.00)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP));
			tcoContractPayPlanVO.setPay_money(Utility.parseDecimal(pay_moneys[i],new BigDecimal(0.00)));
			String temp=pay_summarys[i];
			if(temp.equals("@南#无$阿%弥^陀&佛*")){
				temp="";
			}
			tcoContractPayPlanVO.setPay_summary(temp);
			tcoContractPayPlanLocal.append(tcoContractPayPlanVO);
		}
	}
	bSuccess = true;
}
%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">


<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	alert("修改成功");
	window.location.href="tcocontractset.jsp";
<%}%>


/*客户信息*/
function getMarketingCustomer(prefix,readonly){	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');	
		
	if (v!=null){

		document.theform.cust_name.value = v[0]
		document.theform.cust_id.value = v[7];
	}	
	
	return (v != null);
}


function validateForm(form)
{
	if(!sl_checkDate(document.theform.sign_date_picker,"签署日期"))return false;//签署日期
	syncDatePicker(document.theform.sign_date_picker, document.theform.sign_date);	

	if(!sl_checkDate(document.theform.start_date_picker,"开始日期"))return false;//开始日期
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);

	if(!sl_checkDate(document.theform.main_end_date_picker,"免费维护到期日"))return false;//免费维护到期日
	syncDatePicker(document.theform.main_end_date_picker, document.theform.main_end_date);	

	return sl_check_update();
}

function addContractPoints(){
	var url = "<%=request.getContextPath()%>/contractManagement/tcoContractPoints_new.jsp";
	var v = showModalDialog(url,'','dialogWidth:450px;dialogHeight:300px;status:0;help:0;');		
	
	var tab = document.getElementById('table1'),tr_new,newTd;
	var count = tab.rows.length+1;
	
	if (v!=null){
	    tr_new = tab.insertRow();
		tr_new.insertCell(0); 
		tr_new.insertCell(1); 
		tr_new.insertCell(2); 
		tr_new.insertCell(3);
		if(count%2==0){
	    	tr_new.setAttribute("className","tr0");
		}else{
			tr_new.setAttribute("className","tr1");
		}
	    
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox1' value="+count+">&nbsp;&nbsp;"+"<input type='hidden' contentEditable='false' name='coProduct_id' value='"+v[0]+"'>";
	    tr_new.cells[1].innerHTML=  v[1]; 
		tr_new.cells[2].innerHTML = "<input type='text' name='sub_ht_money' value="+v[2]+">&nbsp;元"; 
		tr_new.cells[3].innerHTML = "<input type='text' name='point_summary' value="+v[3]+">";  
	    tr_new.cells[0].setAttribute("align","left");
	    tr_new.cells[2].setAttribute("align","left");
	}	
}
function delContractPoints(obj){
	 var table=document.getElementById("table1")
	 var roleIds=document.getElementsByName("checkbox1");
	 var tableLen = table.rows.length;
	 for(var i=tableLen-1;i>0;i--){
		 if(roleIds[i-1].checked){			 	
		    table.deleteRow(i); 
	 	}
	 }
}
function addContractPayPlan(){
	var url = "<%=request.getContextPath()%>/contractManagement/tcoContractPayPlan_new.jsp";
	var v = showModalDialog(url,'','dialogWidth:450px;dialogHeight:300px;status:0;help:0;');		
	
	var tab = document.getElementById('table2'),tr_new,newTd;
	var count = tab.rows.length+1;
	
	if (v!=null){
	    tr_new = tab.insertRow();
		if(count%2==0){
	    	tr_new.setAttribute("className","tr0");
		}else{
			tr_new.setAttribute("className","tr1");
		}
		tr_new.insertCell(0); 
		tr_new.insertCell(1); 
		tr_new.insertCell(2); 
		tr_new.insertCell(3); 
		tr_new.insertCell(4); 
		tr_new.insertCell(5); 
		tr_new.insertCell(6);
	    
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox2' value="+count+">&nbsp;&nbsp;";
	    
	    tr_new.cells[1].innerHTML = "<input type='text' name='pay_num' value="+v[0]+">";
	    tr_new.cells[2].innerHTML = "<input type='text' name='pay_num_name' value="+v[1]+">"; 
		tr_new.cells[3].innerHTML = "<input type='text' name='exp_date' value="+v[2]+">";
		tr_new.cells[4].innerHTML = "<input type='text' name='pay_rate' value="+v[3]+">%";
	    tr_new.cells[5].innerHTML = "<input type='text' name='pay_money' value="+v[4]+">"; 
		tr_new.cells[6].innerHTML = "<input type='text' name='pay_summary' value="+v[5]+">";     
	    tr_new.cells[0].setAttribute("align","left");
	    tr_new.cells[2].setAttribute("align","left");
	}	
}
function delContractPayPlan(obj){
	 var table=document.getElementById("table2");
	 var roleIds=document.getElementsByName("checkbox2");
	 var tableLen = table.rows.length;
	 for(var i=tableLen-1;i>0;i--){
		 if(roleIds[i-1].checked){			 	
		    table.deleteRow(i); 
	 	}
	 }
}
function saveAction(){
	if(!sl_checkDate(document.theform.sign_date_picker,"签署日期"))return false;//签署日期
	syncDatePicker(document.theform.sign_date_picker, document.theform.sign_date);	

	if(!sl_checkDate(document.theform.start_date_picker,"开始日期"))return false;//开始日期
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);

	if(!sl_checkDate(document.theform.main_end_date_picker,"免费维护到期日"))return false;//免费维护到期日
	syncDatePicker(document.theform.main_end_date_picker, document.theform.main_end_date);

	
	var table1 = document.getElementById("table1");
	var rows1=table1.rows.length;
	var cols1=table1.rows[0].cells.length;
	var coProduct_id=document.getElementsByName("coProduct_id");
	var sub_ht_money=document.getElementsByName("sub_ht_money");
	var point_summary=document.getElementsByName("point_summary");
	var coProduct_ids=new Array();
	var coProduct_names=new Array();
	var sub_ht_moneys=new Array();
	var point_summarys=new Array();
	var contractPoint="";
	for(var i=1;i<rows1;i++)
	{	
		coProduct_ids.push(coProduct_id[i-1].value);
		coProduct_names.push(table1.rows[i].cells[1].innerText);
		sub_ht_moneys.push(sub_ht_money[i-1].value);
		var tempStr=point_summary[i-1].value;
		tempStr=trimNull(tempStr);
		point_summarys.push(tempStr);
	}
	document.getElementById("coProduct_ids").value=coProduct_ids;
	document.getElementById("coProduct_names").value=coProduct_names;
	document.getElementById("sub_ht_moneys").value=sub_ht_moneys;
	document.getElementById("point_summarys").value=point_summarys;

	var table2 = document.getElementById("table2");
	var rows2=table2.rows.length;
	var cols2=table2.rows[0].cells.length;
	var pay_num=document.getElementsByName("pay_num");
	var pay_num_name=document.getElementsByName("pay_num_name");
	var exp_date=document.getElementsByName("exp_date");
	var pay_rate=document.getElementsByName("pay_rate");
	var pay_money=document.getElementsByName("pay_money");
	var pay_summary=document.getElementsByName("pay_summary");
	
	var pay_nums=new Array();
	var pay_num_names=new Array();
	var exp_dates=new Array();
	var pay_rates=new Array();
	var pay_moneys=new Array();
	var pay_summarys=new Array();

	for(var i=1;i<rows2;i++)
	{	
		pay_nums.push(pay_num[i-1].value);
		pay_num_names.push(pay_num_name[i-1].value);
		exp_dates.push(exp_date[i-1].value);
		pay_rates.push(pay_rate[i-1].value);
		pay_moneys.push(pay_money[i-1].value);
		var tempStr=pay_summary[i-1].value;
		tempStr=trimNull(tempStr);
		pay_summarys.push(tempStr);
	}
	document.getElementById("pay_nums").value=pay_nums;
	document.getElementById("pay_num_names").value=pay_num_names;
	document.getElementById("exp_dates").value=exp_dates;
	document.getElementById("pay_rates").value=pay_rates;
	document.getElementById("pay_moneys").value=pay_moneys;
	document.getElementById("pay_summarys").value=pay_summarys;


	document.theform.action="tcoContract_update.jsp";	
	document.theform.submit();
}
function trimNull(str){
	if(str==""){
		return "@南#无$阿%弥^陀&佛*";
	}else{
		return str;
	}
}
function cancelAction(){
	history.back();
}
/**
function addContractPoints(){
	//document.theform.action="tco_updateContractPoints.jsp?action=add";	
	//document.theform.submit();
	var url = "<%=request.getContextPath()%>/contractManagement/tco_updateContractPoints_new.jsp?coContract_id="+<%=coContract_id%>;
	if(showModalDialog(url,'','dialogWidth:360px;dialogHeight:300px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}
function delContractPoints(obj){
	if(confirmRemove(document.theform.subcoContract_id)){
		document.theform.action="tco_updateContractPoints.jsp?action=del";	
		document.theform.submit();
	}
}
function addContractPayPlan(){
	//if(!sl_checkDate(document.theform.exp_date_picker,"预计付款日期"))return false;//预计付款日期
	//syncDatePicker(document.theform.exp_date_picker, document.theform.exp_date);
	//document.theform.action="tco_updatePayPlan.jsp?action=add";	
	//document.theform.submit();
	var url = "<%=request.getContextPath()%>/contractManagement/tco_updatePayPlan_new.jsp?coContract_id="+<%=coContract_id%>;
	if(showModalDialog(url,'','dialogWidth:360px;dialogHeight:400px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}
function delContractPayPlan(){
	if(confirmRemove(document.theform.payPlan_id)){
		document.theform.action="tco_updatePayPlan.jsp?action=del";	
		document.theform.submit();
	}
}
*/
function updateContractInfo(){
	validateForm(document.theform);
	document.theform.action="tco_updateContractInfo.jsp";	
	document.theform.submit();
}
function closeAction(){
	window.location.href="tcocontractset.jsp";
}
//添加多个附件,数量不限
function addAttachment()
{
	document.getElementById('btn_addAttachment').innerText = "继续添加附件";
	tb = document.getElementById('attachments');   
	newRow = tb.insertRow();                                                                                                                                                                                     
	newRow.insertCell().innerHTML = " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name='File' size='50' type='file'>&nbsp;&nbsp;<input type=button value='删除' class='mailInput' onclick='delAttachment(this.parentElement.parentElement.rowIndex)'>"; 
}
//删除本页面里新加的附件
function delAttachment(index)
{
	document.getElementById('attachments').deleteRow(index);
	tb = document.getElementById('attachments');
	tb.rows.length >1?document.getElementById('btn_addAttachment').innerText = "继续添加附件":document.getElementById('btn_addAttachment').innerText = "添加附件";
	tb.rows.length >1?document.getElementById("btn_saveAttachment").style.display="":document.getElementById("btn_saveAttachment").style.display="none";
} 
/*查看附件方法*/
function DownloadAttachment(attachmentId){
	var url = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;		
	window.location.href = url;	
}
//删除数据库中已有附件的方法
function deleteDbAttachment(attachmentId,save_name,coContract_id){
    if(window.confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //确认删除附件吗
		window.location.href="tco_updateAttachment.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name+"&coContract_id="+coContract_id;	
	}
}
//向数据库中添加附件
function saveDbAttachment(){
	document.theform.action="tco_updateAttachment.jsp?action=add";	
	document.theform.submit();
}
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		ht_money_cn.innerText = "(元)";
	else
		ht_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" id="coProduct_ids" name="coProduct_ids"/>
<input type="hidden" id="coProduct_names" name="coProduct_names"/>
<input type="hidden" id="sub_ht_moneys" name="sub_ht_moneys"/>
<input type="hidden" id="point_summarys" name="point_summarys"/>
<input type="hidden" id="pay_nums" name="pay_nums"/>
<input type="hidden" id="pay_num_names" name="pay_num_names"/>
<input type="hidden" id="exp_dates" name="exp_dates"/>
<input type="hidden" id="pay_rates" name="pay_rates"/>
<input type="hidden" id="pay_moneys" name="pay_moneys"/>
<input type="hidden" id="pay_summarys" name="pay_summarys"/>
<input type="hidden" id="coContract_id" name="coContract_id" value="<%=coContract_id%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>合同修改</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="68%" cellspacing="3">
	<tr>
		<td align="right">客户名称: </td><!--客户名称-->
		<td align="left" colspan="3">
			<input type="hidden" id="cust_id" name="cust_id"/>
			<input maxlength="100" readonly class='edline' id="cust_name" name="cust_name" size="60" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">
			<button type="button" class="xpbutton3" accessKey=e name="btnEdit" title="客户选择 " 
		        onclick="javascript:getMarketingCustomer('customer','0');">客户选择 
		    </button>
		</td>
	</tr>	
	<tr>
		<td  align="right">合同编号: </td><!--合同编号-->
		<td  align="left"><input type="text" name="cocontract_sub_bh" size="25" value="<%=cocontract_sub_bh%>" onkeydown="javascript:nextKeyPress(this)"></td>
		<td align="right">合同金额: </td><!--客户名称-->
		<td align="left" >
			<input type="text" name="ht_money" size="25" value="<%=ht_money%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.ht_money,'合同金额',13,3,0);showCnMoney(this.value)">
			<span id="ht_money_cn" class="span">&nbsp;(元)</span>
		</td>
	</tr>
	<tr>	
		<td align="right">付款方式: </td>
		<td align="left" >
			<select name="payment_type" style="width: 145px;">
				<%=Argument.getDictParamOptions(5003,payment_type) %>
			</select>
		</td>
		<td align="right">合同类型: </td>
		<td align="left" >
			<select name="cocontract_type" style="width: 145px;">
				<%=Argument.getDictParamOptions(5004,cocontract_type) %>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="right">签署日期:</td>
		<td>
			<INPUT TYPE="text" NAME="sign_date_picker" class=selecttext value="<%=sign_date%>" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.sign_date_picker,theform.sign_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="sign_date" id="sign_date"   value="">
		</td>
		<td align="right">开始日期:</td>
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="<%=start_date%>" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="start_date"    value="">
		</td>
	</tr>
	<tr>	
		<td align="right">结束日期:</td>
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%=end_date%>" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="end_date"   value="">
		</td>
		<td align="right">免费维护到期日:</td>
		<td>
			<INPUT TYPE="text" NAME="main_end_date_picker" class=selecttext value="<%=main_end_date%>" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.main_end_date_picker,theform.main_end_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="main_end_date"   value="">
		</td>
	</tr>
	<tr>
		<td align="right">备注:</td>
		<td rowspan="3">
			<TEXTAREA rows="5" cols="50" name="comment"><%=comment %></TEXTAREA>
		</td>
	</tr>
</table>
<table>
	<%
		for(int i=0;i<attachmentList.size();i++){
			attachmentMap=(Map)attachmentList.get(i);
			attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
			origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
		   	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
	%>
	<tr>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现有附件:</td> <!-- 现有附件 ： -->
		<td  align="left" colspan="3">
			<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly"/>&nbsp;&nbsp;&nbsp;&nbsp;
           	<button type="button" class="xpbutton3"  id="btn_DownDbAttachment" name="btn_DownDbAttachment" onclick="javascript:DownloadAttachment(<%=attachmentId%>)"><%=LocalUtilis.language("class.viewAttachments",clientLocale)%></button><!-- 查看附件 -->&nbsp;&nbsp;&nbsp;&nbsp;
           	<button type="button" class="xpbutton3"  id="btn_DelDbAttachment" name="btn_DelDbAttachment" onclick="javascript:deleteDbAttachment('<%=attachmentId%>','<%=save_name%>','<%=coContract_id%>');"><%=LocalUtilis.language("class.deleteAttachment",clientLocale)%></button><!-- 删除附件 -->
		</td>
	</tr>
	<%	}
	 %>
</table>
<table border="0" width="68%" cellspacing="3" id="attachments" name="attachments">
	<tr>
		<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" class="xpbutton3" accessKey=e name="btnEdit" title="添加附件 " 
		        onclick="javascript:addAttachment();" id="btn_addAttachment">添加附件 
		    </button>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="宋体"><b>合同要点明细</b></font></td>
					<td align="right" colspan="2">
						<button type="button" class="xpbutton3" accessKey=f id="btn_addContractPoints" name="btn_addContractPoints" onclick="addContractPoints()"<%if(!input_operator.hasFunc(menu_id, 100)){%> style="display:none"<%}%>>新 增</button>
							&nbsp;&nbsp;&nbsp;<!--新 增-->
						<button type="button" class="xpbutton3"  onclick="javascript:delContractPoints(document.theform.subcoContract_id)" <%if(!input_operator.hasFunc(menu_id, 101)){%> style="display:none"<%}%>>删 除</button>
							&nbsp;&nbsp;&nbsp;<!--删 除-->
					</td>
				</tr>
			</table>
			<table id="table1" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">				
				<tr class=trtagsort>
					<td width="5%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.checkbox1);">&nbsp;</td>
					<td>产品名称</td>
					<td>合同金额</td>
					<td>要点明细说明</td>
				</tr>
				<%for(int i=0;i<tcoContractPointsList.size();i++){
					Map tcoContractPointsMap=(Map)tcoContractPointsList.get(i);
				 %>
				<tr class="tr<%=i%>">
					<td>
						<input type="checkbox" name="checkbox1" value="<%=Utility.trimNull(tcoContractPointsMap.get("SUBCOCONTRACT_ID"))%>"/>
					</td>
					<td><input type="hidden" name="coProduct_id" value="<%=Utility.trimNull(tcoContractPointsMap.get("COPRODUCT_ID"))%>"><%=Utility.trimNull(tcoContractPointsMap.get("COPRODUCT_NAME"))%></td>
					<td><input type="text" name="sub_ht_money" value="<%=Utility.trimNull(tcoContractPointsMap.get("SUB_HT_MONEY"))%>">&nbsp;元</td>
					<td><input type="text" name="point_summary" value="<%=Utility.trimNull(tcoContractPointsMap.get("POINT_SUMMARY"))%>"></td>
				</tr>
				<%}%>
			</table>
		</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
</table>

<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="宋体"><b>付款计划</b></font></td>
					<td align="right" colspan="2">
						<button type="button" class="xpbutton3" accessKey=f id="btn_addPayPlan" name="btn_addPayPlan" onclick="javascript:addContractPayPlan();" <%if(!input_operator.hasFunc(menu_id, 100)){%> style="display:none"<%}%>>新 增</button>
							&nbsp;&nbsp;&nbsp;<!--新 增-->
						<button type="button" class="xpbutton3"  onclick="delContractPayPlan()" <%if(!input_operator.hasFunc(menu_id, 101)){%> style="display:none"<%}%>>删 除</button>
							&nbsp;&nbsp;&nbsp;<!--删 除-->
					</td>
				</tr>
			</table>
			<table id="table2" border="0" cellspacing="1" cellpadding="2" 
					class="tablelinecolor" width="100%">
				<tr class=trtagsort>
					<td width="5%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.checkbox2);">&nbsp;</td>
					<td>期数</td>
					<td>期数说明</td>
					<td>预计付款日期</td>
					<td>付款比例</td>
					<td>付款金额</td>
					<td>付款条件说明</td>
				</tr>
				<%for(int i=0;i<tcoContractPayPlanList.size();i++){
					Map tcoContractPayPlanMap=(Map)tcoContractPayPlanList.get(i);
				 %>
				<tr class="tr<%=i%>">
					<td><input type="checkbox" id="checkbox2" name="checkbox2" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("PAYPLAN_ID"))%>"/></td>
					<td><input type="text" name="pay_num" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_NUM"))%>"></td>
					<td><input type="text" name="pay_num_name" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_NUM_NAME"))%>"></td>
					<td><input type="text" name="exp_date" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("EXP_DATE"))%>"></td>
					<td><input type="text" name="pay_rate" value="<%=Utility.parseDecimal(Utility.trimNull(tcoContractPayPlanMap.get("PAY_RATE")),new BigDecimal(0)).multiply(new BigDecimal(100)).setScale(2).toString()%>">&nbsp;%</td>
					<td><input type="text" name="pay_money" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_MONEY"))%>"></td>
					<td><input type="text" name="pay_summary" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_SUMMARY"))%>"></td>
				</tr>
				<%}%>
			</table>
		</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="saveAction()"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="cancelAction()"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
<%
tcoContractLocal.remove();
tcoContractPayPlanLocal.remove();
tcoContractPointsLocal.remove();
 %>
</html>
