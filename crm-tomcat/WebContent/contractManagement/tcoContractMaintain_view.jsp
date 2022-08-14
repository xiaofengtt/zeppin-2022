<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractMaintainLocal tcoContractMaintainLocal = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO tcoContractMaintainVO = new TcoContractMaintainVO();
Integer maintain_id=Utility.parseInt(request.getParameter("maintain_id"),new Integer(0));
tcoContractMaintainVO.setMaintain_id(maintain_id);
tcoContractMaintainVO.setInput_man(input_operatorCode);
List tcoContractMaintainList=tcoContractMaintainLocal.queryByList(tcoContractMaintainVO);
Map tcoContractMaintainMap=(Map)tcoContractMaintainList.get(0);
String cust_id=Utility.trimNull(tcoContractMaintainMap.get("CUST_ID"));
String cust_name=Utility.trimNull(tcoContractMaintainMap.get("CUST_NAME"));
String coContractMaintain_sub_bh=Utility.trimNull(tcoContractMaintainMap.get("COCONTRACTMAINTAIN_SUB_BH"));
String main_pro_name=Utility.trimNull(tcoContractMaintainMap.get("MAIN_PRO_NAME"));
String main_period=Utility.trimNull(tcoContractMaintainMap.get("MAIN_PERIOD"));
String main_period_unit=Utility.trimNull(tcoContractMaintainMap.get("MAIN_PERIOD_UNIT"));
String collect_time=Utility.trimNull(tcoContractMaintainMap.get("COLLECT_TIME"));
String start_date=Utility.trimNull(tcoContractMaintainMap.get("START_DATE"));
String end_date=Utility.trimNull(tcoContractMaintainMap.get("END_DATE"));
String ht_money=Utility.trimNull(tcoContractMaintainMap.get("HT_MONEY"));
String wh_money=Utility.trimNull(tcoContractMaintainMap.get("WH_MONEY"));
String main_summary=Utility.trimNull(tcoContractMaintainMap.get("MAIN_SUMMARY"));
String sub_bh = Utility.trimNull(tcoContractMaintainMap.get("COCONTRACT_SUB_BH"));


TcoMaintainDetailLocal tcoMaintainDetailLocal = EJBFactory.getTcoMaintainDetail();
TcoMaintainDetailVO tcoMaintainDetailVO = new TcoMaintainDetailVO();
tcoMaintainDetailVO.setMaintain_id(maintain_id);
tcoMaintainDetailVO.setMaintainDetail_id(new Integer(0));
tcoMaintainDetailVO.setInput_man(input_operatorCode);
List tcoMaintainDetailList = tcoMaintainDetailLocal.queryByList(tcoMaintainDetailVO);
Map tcoMaintainDetailMap= new HashMap();


//附件相关变量
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(maintain_id);
attachmentVO.setDf_talbe_id(new Integer(101054));
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
	maintain_id=Utility.parseInt(file.getParameter("maintain_id"),new Integer(0));
	tcoContractMaintainVO.setMaintain_id(maintain_id);
	tcoContractMaintainVO.setCust_id(Utility.parseInt(file.getParameter("cust_id"),new Integer(0)));
	tcoContractMaintainVO.setCoContractMaintain_sub_bh(Utility.trimNull(file.getParameter("coContractMaintain_sub_bh")));
	tcoContractMaintainVO.setMain_period(Utility.parseInt(file.getParameter("main_period"),new Integer(0)));
	tcoContractMaintainVO.setMain_period_unit(Utility.parseInt(file.getParameter("main_period_unit"),new Integer(0)));
	tcoContractMaintainVO.setMain_pro_name(Utility.trimNull(file.getParameter("main_pro_name")));
	tcoContractMaintainVO.setCollect_time(Utility.parseInt(file.getParameter("collect_time"),new Integer(0)));
	tcoContractMaintainVO.setStart_date(Utility.parseInt(file.getParameter("start_date"),new Integer(0)));
	tcoContractMaintainVO.setEnd_date(Utility.parseInt(file.getParameter("end_date"),new Integer(0)));
	tcoContractMaintainVO.setHt_money(Utility.parseDecimal(file.getParameter("ht_money"),new BigDecimal(0.00)));
	tcoContractMaintainVO.setWh_money(Utility.parseDecimal(file.getParameter("wh_money"),new BigDecimal(0.00)));
	tcoContractMaintainVO.setMain_summary(Utility.trimNull(file.getParameter("main_summary")));
	tcoContractMaintainVO.setInput_man(input_operatorCode);
	tcoContractMaintainLocal.modi(tcoContractMaintainVO);
	
	//保存新增的附件
	file.uploadAttchment(new Integer(101054),"TCOMAINTAINDETAIL",maintain_id,"",input_operatorCode);
	
	//保存维护合同明细项目 要先删掉之前已经存在的明细项，再全部重新添加
	//删除
	tcoMaintainDetailVO.setMaintainDetail_id(new Integer(0));
	tcoMaintainDetailVO.setInput_man(input_operatorCode);
	tcoMaintainDetailVO.setMaintain_id(maintain_id);
	tcoMaintainDetailLocal.delete(tcoMaintainDetailVO);
	//添加
	String coContract_id=Utility.trimNull(file.getParameter("coContract_ids"));
	String coProduct_id=Utility.trimNull(file.getParameter("coProduct_ids"));
	String coProduct_name=Utility.trimNull(file.getParameter("coProduct_names"));
	String xm_ht_money=Utility.trimNull(file.getParameter("xm_ht_moneys"));
	String main_rate=Utility.trimNull(file.getParameter("main_rates"));
	String main_money=Utility.trimNull(file.getParameter("main_moneys"));
	if(!coProduct_name.equals("")){
		String[] coContract_ids= coContract_id.split(",");
		String[] coProduct_ids= coProduct_id.split(",");
		String[] coProduct_names= coProduct_name.split(",");
		String[] xm_ht_moneys= xm_ht_money.split(",");
		String[] main_rates= main_rate.split(",");
		String[] main_moneys= main_money.split(",");
		
		tcoMaintainDetailVO.setMaintain_id(maintain_id);
		tcoMaintainDetailVO.setInput_man(input_operatorCode);
		for(int i=0;i<coProduct_names.length;i++){
			tcoMaintainDetailVO.setCoContract_id(Utility.parseInt(coContract_ids[i],new Integer(0)));
			tcoMaintainDetailVO.setCoProduct_id(Utility.parseInt(coProduct_ids[i],new Integer(0)));
			tcoMaintainDetailVO.setCoProduct_name(coProduct_names[i]);
			tcoMaintainDetailVO.setXm_ht_money(Utility.parseDecimal(xm_ht_moneys[i],new BigDecimal(0.00)));
			tcoMaintainDetailVO.setMain_rate(Utility.parseDecimal(main_rates[i],new BigDecimal(0.00)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP));
			tcoMaintainDetailVO.setMain_money(Utility.parseDecimal(main_moneys[i],new BigDecimal(0.00)));
			tcoMaintainDetailLocal.append(tcoMaintainDetailVO);
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	alert("修改成功");
	window.location.href="tcocontractmaintainset.jsp";
<%}%>
window.onload = function(){
	//initQueryCondition();
};


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
	if(!sl_checkDate(document.theform.collect_time_picker,"预计收款时间"))return false;//预计收款时间
	syncDatePicker(document.theform.collect_time_picker, document.theform.collect_time);	

	if(!sl_checkDate(document.theform.start_date_picker,"开始日期"))return false;//开始日期
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	return sl_check_update();
}


function addMaintainDetail(){
	//document.theform.action="tco_updateMaintainDetail.jsp?action=add";	
	//document.theform.submit();
	var url = "<%=request.getContextPath()%>/contractManagement/tco_updateMaintainDetail_new.jsp?maintain_id="+<%=maintain_id%>;
	if(showModalDialog(url,'','dialogWidth:360px;dialogHeight:400px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}
/**
function delMaintainDetail(obj){
	if(confirmRemove(document.theform.maintainDetail_id)){
		document.theform.action="tco_updateMaintainDetail.jsp?action=del";	
		document.theform.submit();
	}
}
*/
function delMaintainDetail(obj){
	 var table=document.getElementById("table1")
	 var roleIds=document.getElementsByName("checkbox1");
	 var tableLen = table.rows.length;
	 for(var i=tableLen-1;i>0;i--){
		 if(roleIds[i-1].checked){			 	
		    table.deleteRow(i); 
	 	}
	 }
}
//删除空行（没有添加维护费率和维护金额的行）
function delEmptyMaintainDetail(){
	 var table=document.getElementById("table1")
	 var roleIds=document.getElementsByName("checkbox1");
	 var main_rate=document.getElementsByName("main_rate111");
	 var main_money=document.getElementsByName("main_money111");
	 var tableLen = table.rows.length;
	 for(var i=tableLen-1;i>0;i--){
		 if(main_rate[i-1].value==""&&main_money[i-1].value==""){
		    table.deleteRow(i); 
	 	}
	 }
}
function updateContractInfo(){
	validateForm(document.theform);
	document.theform.action="tco_updateContractMaintainInfo.jsp";	
	document.theform.submit();
}
function saveAction(){
	//modi 20111029
	if(document.theform.coContractMaintain_sub_bh.value == '' && document.theform.sub_bh.value == ''){
		alert("请输入合同号");
		return false;
	}	

	if(!sl_checkDate(document.theform.collect_time_picker,"预计收款时间"))return false;//预计收款时间
	syncDatePicker(document.theform.collect_time_picker, document.theform.collect_time);	

	if(!sl_checkDate(document.theform.start_date_picker,"开始日期"))return false;//开始日期
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	
	delEmptyMaintainDetail();
	
	var table1 = document.getElementById("table1");
	var rows1=table1.rows.length;
	var coContract_id=document.getElementsByName("coContract_id111");
	var coProduct_id=document.getElementsByName("coProduct_id111");
	var coProduct_name=document.getElementsByName("coProduct_name111");
	var xm_ht_money=document.getElementsByName("xm_ht_money111");
	var main_rate=document.getElementsByName("main_rate111");
	var main_money=document.getElementsByName("main_money111");

	var coContract_ids=new Array();
	var coProduct_ids=new Array();
	var coProduct_names=new Array();
	var xm_ht_moneys=new Array();
	var main_rates=new Array();
	var main_moneys=new Array();
	for(var i=0;i<rows1-1;i++)
	{	
		coContract_ids.push(coContract_id[i].value);
		coProduct_ids.push(coProduct_id[i].value);
		coProduct_names.push(coProduct_name[i].value);
		xm_ht_moneys.push(xm_ht_money[i].value);
		main_rates.push(main_rate[i].value);
		main_moneys.push(main_money[i].value);
	}
	document.getElementById("coContract_ids").value=coContract_ids;
	document.getElementById("coProduct_ids").value=coProduct_ids;
	document.getElementById("coProduct_names").value=coProduct_names;
	document.getElementById("xm_ht_moneys").value=xm_ht_moneys;
	document.getElementById("main_rates").value=main_rates;
	document.getElementById("main_moneys").value=main_moneys;
	document.theform.action="tcoContractMaintain_update.jsp";	
	document.theform.submit();
}
function closeAction(){
	history.back();
}
//添加多个附件,数量不限
function addAttachment()
{
	document.getElementById('btn_addAttachment').innerText = "继续添加附件";
	document.getElementById("btn_saveAttachment").style.display="";
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
function deleteDbAttachment(attachmentId,save_name,maintain_id){
    if(window.confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //确认删除附件吗
		window.location.href="tco_updateMaintainAttachment.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name+"&maintain_id="+maintain_id;	
	}
}
//向数据库中添加附件
function saveDbAttachment(){
	document.theform.action="tco_updateMaintainAttachment.jsp?action=add";	
	document.theform.submit();
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="maintain_id" value="<%=maintain_id%>"> 
<input type="hidden" id="coContract_ids" name="coContract_ids"/>
<input type="hidden" id="coProduct_ids" name="coProduct_ids"/>
<input type="hidden" id="coProduct_names" name="coProduct_names"/>
<input type="hidden" id="xm_ht_moneys" name="xm_ht_moneys"/>
<input type="hidden" id="main_rates" name="main_rates"/>
<input type="hidden" id="main_moneys" name="main_moneys"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>维护合同修改</b></font></td>
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
			<input type="hidden" id="cust_id" name="cust_id" name="cust_id" value="<%=cust_id %>"/>
			<input maxlength="100" class='edline' id="cust_name" name="cust_name" size="60" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">
		</td>
	</tr>	
	<tr>
		<td  align="right">维护合同编号: </td><!--维护合同编号-->
		<td  align="left"><input type="text" name="coContractMaintain_sub_bh" value="<%=coContractMaintain_sub_bh%>" size="25" value="" onkeydown="javascript:nextKeyPress(this)" readonly="readonly"></td>
		<td align="right">对应原合同号: </td>
		<td align="left" >
			<input type="text" name="sub_bh" size="25" value="<%=sub_bh%>" onkeydown="javascript:nextKeyPress(this)">
		</td>		
	</tr>
	<tr>
		<td align="right">维护项目名称: </td>
		<td align="left" >
			<input type="text" name="main_pro_name" value="<%=main_pro_name%>" readonly="readonly" size="25" value="" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>	
		<td align="right">维护周期: </td><!--维护周期-->
		<td align="left" >
			<input type="text" name="main_period" size="16" value="<%=main_period%>" readonly="readonly" onkeydown="javascript:nextKeyPress(this)">
			<select name="main_period_unit" style="width: 40px;" disabled="disabled">
				<option value=1 <%if(main_period_unit.equals("1")){out.print("selected");} %>>日</option>
				<option value=2 <%if(main_period_unit.equals("2")){out.print("selected");} %>>月</option>
				<option value=3 <%if(main_period_unit.equals("3")){out.print("selected");} %>>年</option>
			</select>
		</td>
		<td align="right">预计收款时间:</td>
		<td>
			<INPUT TYPE="text" NAME="collect_time_picker" class=selecttext size="25" value="<%=collect_time%>" readonly="readonly" >
			<INPUT TYPE="hidden" NAME="collect_time" id="collect_time"   value="">
		</td>
	</tr>
	
	<tr>
		
		<td align="right">起始日期:</td>
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" size="25" class=selecttext value="<%=start_date %>" readonly="readonly">
			<INPUT TYPE="hidden" NAME="start_date"    value="">
		</td>
		<td align="right">到期日期:</td>
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" size="25" class=selecttext value="<%=end_date %>" readonly="readonly">
			<INPUT TYPE="hidden" NAME="end_date"   value="">
		</td>
	</tr>
	<tr>	
		<td align="right">项目合同总金额: </td>
		<td align="left" >
			<input type="text" name="ht_money" size="25" readonly="readonly" value="<%=ht_money%>" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right">维护费金额:</td>
		<td>
			<input type="text" name="wh_money" size="25" readonly="readonly" value="<%=wh_money%>" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right">合同说明</td>
		<td align="left" colspan="3">
			<textarea rows="" cols="100" name="main_summary" readonly="readonly"><%=main_summary %></textarea>
		</td>
	</tr>
</table>
<div style="display:none">
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="updateContractInfo()"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		</td>
	</tr>
</table>
</div>
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
           	<button type="button" class="xpbutton3"  id="btn_DownDbAttachment" name="btn_DownDbAttachment" onclick="javascript:DownloadAttachment(<%=attachmentId%>)"><%=LocalUtilis.language("class.viewAttachments",clientLocale)%></button><!-- 查看附件 -->&nbsp;&nbsp;&nbsp;
           	
		</td>
	</tr>
	<%	}
	 %>
</table>

<div style="display:none">
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button" class="xpbutton3" id="btn_saveAttachment" style="display: none;" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveDbAttachment();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		</td>
	</tr>
</table>
</div>
<% 
//String cust_name=request.getParameter("cust_name");
//返回结果集
Map map = null;
List rsList = null;
TcoContractLocal local = EJBFactory.getTcoContract();
TcoContractVO vo = new TcoContractVO();
vo.setInput_man(input_operatorCode);
//如果cust_name=="" 会查出来所有，所以替换一下
cust_name=cust_name==""?"@南#无$阿%弥^陀&佛*":cust_name;
vo.setCust_name(cust_name);
rsList = local.queryByList(vo);	

TcoContractPointsLocal tcoContractPointsLocal=EJBFactory.getTcoContractPoints();
TcoContractPointsVO tcoContractPointsVO=new TcoContractPointsVO();
tcoContractPointsVO.setSubcoContract_id(new Integer(0));
tcoContractPointsVO.setInput_man(input_operatorCode);
%>
<div style="display: none">
	<table  width="100%" border="0" width="68%" cellspacing="3">
			<tr>
				<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择主合同:</b>
					<select id="coContract_id000" name="coContract_id000" style="width: 146px">
						<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
						<%	
						    Iterator iterator = rsList.iterator();
							while(iterator.hasNext()){	
								map = (Map)iterator.next();		
								Integer r_coContract_id = Utility.parseInt(Utility.trimNull(map.get("COCONTRACT_ID")), new Integer(0)); 
								String r_coContract_sub_bh = Utility.trimNull(map.get("COCONTRACT_SUB_BH"));
						%>
							<option value="<%=r_coContract_id%>">							
								<%=r_coContract_sub_bh%>	
							</option>
						<%} %>
					</select>
				</td>
				<td align="left"></td>
			</tr>
	</table>
</div>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="宋体"><b>维护费项目明细</b></font></td>
				</tr>
			</table>
			<table id="table1" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">				
				<tr class=trtagsort>
					<td width="5%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.checkbox1);">&nbsp;</td>
					<td>主合同编号</td>
					<td>维护项目名称</td>
					<td>项目合同金额</td>
					<td>维护费率</td>
					<td>维护金额</td>
				</tr>
				<%
					int count=0;
					for(int j=0;j<rsList.size();j++){	
						map = (Map)rsList.get(j);		
								
						Integer r_coContract_id = Utility.parseInt(Utility.trimNull(map.get("COCONTRACT_ID")), new Integer(0)); 
						String r_coContract_sub_bh = Utility.trimNull(map.get("COCONTRACT_SUB_BH"));
						tcoContractPointsVO.setCoContract_id(r_coContract_id);
						List pointsList=tcoContractPointsLocal.queryByList(tcoContractPointsVO);
						for(int i=0;i<pointsList.size();i++){
							Map pointsMap=(Map)pointsList.get(i);
							String coProduct_id=Utility.trimNull(pointsMap.get("COPRODUCT_ID"));
							String coProduct_name=Utility.trimNull(pointsMap.get("COPRODUCT_NAME"));
							String xm_ht_money=Utility.trimNull(pointsMap.get("SUB_HT_MONEY"));
							count++;
							//用于获取已经添加的维护费比例和维护费金额
							tcoMaintainDetailVO.setCoContract_id(r_coContract_id);
							tcoMaintainDetailVO.setCoProduct_id(Utility.parseInt(coProduct_id,new Integer(0)));
							tcoMaintainDetailList=tcoMaintainDetailLocal.queryByList(tcoMaintainDetailVO);
							String main_rate="";
							String main_money="";
							BigDecimal i_main_rate=new BigDecimal(0.00);
							if(tcoMaintainDetailList.size()>0){
								tcoMaintainDetailMap=(Map)tcoMaintainDetailList.get(0);
								main_rate=Utility.trimNull(tcoMaintainDetailMap.get("MAIN_RATE"));
								i_main_rate=Utility.parseDecimal(main_rate,new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2,2);
								main_rate=i_main_rate==new BigDecimal(0.00)?"":(i_main_rate+"");
								main_money=Utility.trimNull(tcoMaintainDetailMap.get("MAIN_MONEY"));
							}
							
				 %>
				 	<%if(i_main_rate.compareTo(new BigDecimal(0.00))>0||main_money!=""){ %>
				 	<tr class="tr<%=count%2%>">
				 		<td width="5%"><input type="checkbox" name="checkbox1" value="">&nbsp;</td>
						<td><%=r_coContract_sub_bh%><input type="hidden" name="coContract_id111" value="<%=r_coContract_id%>"/></td>
						<td><%=coProduct_name %>
							<input type="hidden" name="coProduct_id111" value="<%=coProduct_id %>"/>
							<input type="hidden" name="coProduct_name111" value="<%=coProduct_name%>"/></td>
						<td><%=xm_ht_money%></td>
						<td><%=main_rate %>%</td>
						<td><%=main_money %>&nbsp;元</td> 
					</tr>
				 <%		
						}
						}
						
				 %>
				 <%} %>
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
					<td  colspan="2"><font color='red' face="宋体"><b>开票信息</b></font></td>
				</tr>
			</table>
			<table id="table2" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">				
				<tr class=trtagsort>
					<td>开票时间</td>
					<td>银行</td>
					<td>银行帐号</td>
					<td>帐户名称</td>
					<td>开票金额</td>
					<td>备注</td>
				</tr>
			 	<tr class="tr0">
					<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(tcoContractMaintainMap.get("INVOICE_TIME")),new Integer(0)))%></td>
					<td><%=Utility.trimNull(tcoContractMaintainMap.get("BANK_NAME"))%></td>
					<td><%=Utility.trimNull(tcoContractMaintainMap.get("BANK_ACCT"))%></td>
					<td><%=Utility.trimNull(tcoContractMaintainMap.get("ACCT_NAME"))%></td>
					<td><%=Utility.trimNull(tcoContractMaintainMap.get("INVOICE_MONEY"))%></td>
					<td><%=Utility.trimNull(tcoContractMaintainMap.get("INVOICE_SUMMARY"))%></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="closeAction()">返回</button>
		</td>
	</tr>
</table>
</form>
</body>
<%
tcoContractMaintainLocal.remove();
tcoMaintainDetailLocal.remove();
local.remove();
 %>
</html>
