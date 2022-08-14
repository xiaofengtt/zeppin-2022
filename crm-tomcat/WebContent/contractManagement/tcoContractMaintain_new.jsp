<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String cust_name=request.getParameter("cust_name");
String cust_id=request.getParameter("cust_id");
cust_name=cust_name==null?"":cust_name;
cust_id=cust_id==null?"":cust_id;
TcoContractMaintainLocal tcoContractMaintainLocal = EJBFactory.getTcoContractMaintain();
TcoContractMaintainVO tcoContractMaintainVO = new TcoContractMaintainVO();

TcoMaintainDetailLocal tcoMaintainDetailLocal = EJBFactory.getTcoMaintainDetail();
TcoMaintainDetailVO tcoMaintainDetailVO = new TcoMaintainDetailVO();

Integer maintain_id=new Integer(0);
boolean bSuccess = false;
/*********因为增加了一个附件上传，所以原先request方法取值的改为 file*******************/
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
    file.parseRequest();
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
	tcoContractMaintainVO.setSub_bh(Utility.trimNull(file.getParameter("sub_bh")));
	maintain_id=tcoContractMaintainLocal.append(tcoContractMaintainVO);

	file.uploadAttchment(new Integer(101054),"TCOMAINTAINDETAIL",maintain_id,"",input_operatorCode);
	
	
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
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">


<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	alert("保存成功");
	window.location.href="tcocontractmaintainset.jsp";
<%}%>


/*客户信息*/
function getMarketingCustomer(prefix,readonly){	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');	
	/**	
	if (v!=null){

		document.theform.cust_name.value = v[0];
		document.theform.cust_id.value = v[7];
	}	
	
	return (v != null);
	*/
	if(v!=null){
		location="tcoContractMaintain_new.jsp?cust_name="+v[0]+"&cust_id="+v[7];
	}
}


function validateForm(form)
{
	if(!sl_checkDate(document.theform.collect_time_picker,"签署日期"))return false;//签署日期
	syncDatePicker(document.theform.collect_time_picker, document.theform.collect_time);	
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);	

	return sl_check_update();
}


function addMaintainDetail(){
	var cust_name=document.getElementById("cust_name").value;
	if(cust_name==""){
		alert("请先选择客户");
		return false;
	}
	var url = "<%=request.getContextPath()%>/contractManagement/tcoMaintainDetail_new.jsp?cust_name="+cust_name;
	var v = showModalDialog(url,'','dialogWidth:450px;dialogHeight:300px;status:0;help:0;');		
	var tab = document.getElementById('table1'),tr_new,newTd;
	var count = tab.rows.length+1;
	
	if (v!=null){
	    tr_new = tab.insertRow();
		tr_new.insertCell(0); 
		tr_new.insertCell(1); 
		tr_new.insertCell(2); 
		tr_new.insertCell(3);
		tr_new.insertCell(4); 
		tr_new.insertCell(5);
		if(count%2==0){
	    	tr_new.setAttribute("className","tr0");
		}else{
			tr_new.setAttribute("className","tr1");
		}
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox1' value="+count+"/>&nbsp;&nbsp;"+"<input type='hidden' name='coContract_id' value='"+v[0]+"'/>";
	    tr_new.cells[1].innerHTML=  v[1];
		tr_new.cells[2].innerHTML = v[2]; 
		tr_new.cells[3].innerHTML = v[3];   
		tr_new.cells[4].innerHTML = v[4]+"%"; 
		tr_new.cells[5].innerHTML = v[5];
	}	
}

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
function saveAction(){
	if(!sl_checkDate(document.theform.collect_time_picker,"预计收款时间"))return false;//预计收款时间
	syncDatePicker(document.theform.collect_time_picker, document.theform.collect_time);	

	if(!sl_checkDate(document.theform.start_date_picker,"开始日期"))return false;//开始日期
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	
	delEmptyMaintainDetail();
	
	var table1 = document.getElementById("table1");
	var rows1=table1.rows.length;
	var coContract_id=document.getElementsByName("coContract_id");
	var coContract_ids=new Array();
	var main_pro_names=new Array();
	var xm_ht_moneys=new Array();
	var main_rates=new Array();
	var main_moneys=new Array();
	for(var i=1;i<rows1;i++)
	{	
		coContract_ids.push(coContract_id[i-1].value);
		main_pro_names.push(table1.rows[i].cells[2].innerText);
		xm_ht_moneys.push(table1.rows[i].cells[3].innerText);
		//main_rates.push(table1.rows[i].cells[4].innerText);
		
		var main_rate_temp=table1.rows[i].cells[4].innerText;
		main_rate_temp=main_rate_temp.substring(0,main_rate_temp.length-1);
		main_rates.push(main_rate_temp);
		
		main_moneys.push(table1.rows[i].cells[5].innerText);
	}
	document.getElementById("coContract_ids").value=coContract_ids;
	document.getElementById("main_pro_names").value=main_pro_names;
	document.getElementById("xm_ht_moneys").value=xm_ht_moneys;
	document.getElementById("main_rates").value=main_rates;
	document.getElementById("main_moneys").value=main_moneys;
	document.theform.action="tcoContractMaintain_new.jsp";	
	document.theform.submit();
}
function saveAction111(){
	
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
	document.theform.action="tcoContractMaintain_new.jsp";	
	document.theform.submit();
}
function cancelAction(){
	window.location.href="tcocontractmaintainset.jsp";
}
//添加多个附件,数量不限
function addAttachment()
{
	document.getElementById('btn_addAttachment').innerText = "继续添加附件";
	tb = document.getElementById('attachments');   
	newRow = tb.insertRow();                                                                                                                                                                                     
	newRow.insertCell().innerHTML = " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name='File' size='50' type='file'>&nbsp;&nbsp;<input type=button value='删除' class='mailInput' onclick='delAttachment(this.parentElement.parentElement.rowIndex)'>"; 
}
//删除附件
function delAttachment(index)
{
	document.getElementById('attachments').deleteRow(index);
	tb = document.getElementById('attachments');
	tb.rows.length >1?document.getElementById('btn_addAttachment').innerText = "继续添加附件":document.getElementById('btn_addAttachment').innerText = "添加附件";
} 
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		ht_money_cn.innerText = "(元)";
	else
		ht_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
/*显示中文钱数*/
function showCnMoney1(value){
	temp = value;
	if (trim(value) == "")
		wh_money_cn.innerText = "(元)";
	else
		wh_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" id="coContract_ids" name="coContract_ids"/>
<input type="hidden" id="coProduct_ids" name="coProduct_ids"/>
<input type="hidden" id="coProduct_names" name="coProduct_names"/>
<input type="hidden" id="xm_ht_moneys" name="xm_ht_moneys"/>
<input type="hidden" id="main_rates" name="main_rates"/>
<input type="hidden" id="main_moneys" name="main_moneys"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>维护合同录入</b></font></td>
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
			<input type="hidden" id="cust_id" name="cust_id" name="cust_id" value="<%=cust_id%>"/>
			<input maxlength="100" readonly class='edline' id="cust_name" name="cust_name" value="<%=cust_name%>" size="60" onkeydown="javascript:nextKeyPress(this);">
			<button type="button" class="xpbutton3" accessKey=e name="btnEdit" title="客户选择 " 
		        onclick="javascript:getMarketingCustomer('customer','0');">客户选择 
		    </button>
		</td>
	</tr>	
	<tr>
		<td  align="right">维护合同编号: </td><!--维护合同编号-->
		<td  align="left"><input type="text" name="coContractMaintain_sub_bh" size="25" value="" onkeydown="javascript:nextKeyPress(this)"></td>
		<td align="right">对应原合同号: </td>
		<td align="left" >
			<input type="text" name="sub_bh" size="25" value="" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>
		<td align="right">维护项目名称: </td>
		<td align="left" >
			<input type="text" name="main_pro_name" size="25" value="" onkeydown="javascript:nextKeyPress(this)">
		</td>
	</tr>
	<tr>	
		<td align="right">维护周期: </td><!--维护周期-->
		<td align="left" >
			<input type="text" name="main_period" size="16" value="" onkeydown="javascript:nextKeyPress(this)">
			<select name="main_period_unit" style="width: 40px;">
				<option value=3>年</option>
				<option value=2>月</option>
				<option value=1>日</option>
			</select>
		</td>
		<td align="right">预计收款时间:</td>
		<td>
			<INPUT TYPE="text" NAME="collect_time_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.collect_time_picker,theform.collect_time_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="collect_time" id="collect_time"   value="">
		</td>
	</tr>
	
	<tr>
		
		<td align="right">起始日期:</td>
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="start_date"    value="">
		</td>
		<td align="right">到期日期:</td>
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="end_date"   value="">
		</td>
	</tr>
	<tr>	
		<td align="right">项目合同总金额: </td>
		<td align="left" >
			<input type="text" name="ht_money" size="25" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.ht_money,'项目合同总金额',13,3,0);showCnMoney(this.value)">
			<span id="ht_money_cn" class="span">&nbsp;(元)</span>
		</td>
		<td align="right">维护费金额:</td>
		<td>
			<input type="text" name="wh_money" size="25" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.wh_money,'维护费金额',13,3,0);showCnMoney1(this.value)">
			<span id="wh_money_cn" class="span">&nbsp;(元)</span>
		</td>
	</tr>
	<tr>
		<td align="right">合同说明:</td>
		<td align="left" colspan="3">
			<textarea rows="" cols="100" name="main_summary"></textarea>
		</td>
	</tr>
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
vo.setCheck_flag(new Integer(2));
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
					<td align="right" colspan="2">
						<a href="#" onclick="javascript:delMaintainDetail(document.theform.checkbox1)">删 除(D)</a>
					</td>
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
				 %>
				 	<tr class="tr<%=count%2%>">
				 		<td width="5%"><input type="checkbox" name="checkbox1" value="">&nbsp;</td>
						<td><%=r_coContract_sub_bh%><input type="hidden" name="coContract_id111" value="<%=r_coContract_id%>"/></td>
						<td><%=coProduct_name %>
							<input type="hidden" name="coProduct_id111" value="<%=coProduct_id %>"/>
							<input type="hidden" name="coProduct_name111" value="<%=coProduct_name%>"/></td>
						<td><%=xm_ht_money %><input type="hidden" name="xm_ht_money111" value="<%=xm_ht_money%>"/></td>
						<td><input type=text name="main_rate111"/>&nbsp;&nbsp;%</td>
						<td><input type=text name="main_money111"/>&nbsp;&nbsp;(元)</td> 
					</tr>
				 <%		
						
						}
						
				 %>
				 <%} %>
			</table>
		</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="saveAction111()"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="cancelAction()"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
<%
tcoContractMaintainLocal.remove();
tcoMaintainDetailLocal.remove();
 %>
</html>
