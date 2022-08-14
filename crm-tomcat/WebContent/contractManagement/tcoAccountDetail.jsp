<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractLocal tcoContractLocal = EJBFactory.getTcoContract();
TcoContractVO tcoContractVO = new TcoContractVO();
TcoContractPayPlanLocal tcoContractPayPlanLocal = EJBFactory.getTcoContractPayPlan();
TcoContractPayPlanVO tcoContractPayPlanVO = new TcoContractPayPlanVO();
TcoContractPointsLocal tcoContractPointsLocal = EJBFactory.getTcoContractPoints();
TcoContractPointsVO tcoContractPointsVO = new TcoContractPointsVO();
Integer coContract_id=Utility.parseInt(request.getParameter("coContract_id"),new Integer(0));
tcoContractVO.setCocontract_id(coContract_id);
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
	coContract_id=tcoContractLocal.append(tcoContractVO);

	file.uploadAttchment(new Integer(101053),"TCOCONTRACT",coContract_id,"",input_operatorCode);

	String coProduct_id=Utility.trimNull(file.getParameter("coProduct_ids"));
	String coProduct_name=Utility.trimNull(file.getParameter("coProduct_names"));
	String sub_ht_money=Utility.trimNull(file.getParameter("sub_ht_moneys"));
	String point_summary=Utility.trimNull(file.getParameter("point_summarys"));
	String[] coProduct_ids= coProduct_id.split(",");
	String[] coProduct_names= coProduct_name.split(",");
	String[] sub_ht_moneys= sub_ht_money.split(",");
	String[] point_summarys= point_summary.split(",");
	tcoContractPointsVO.setCoContract_id(coContract_id);
	tcoContractPointsVO.setInput_man(input_operatorCode);
	for(int i=0;i<coProduct_ids.length;i++){
		tcoContractPointsVO.setCoProduct_id(Utility.parseInt(coProduct_ids[i],new Integer(0)));
		tcoContractPointsVO.setSub_ht_money(Utility.parseDecimal(sub_ht_moneys[i],new BigDecimal(0.00)));
		tcoContractPointsVO.setPoint_summary(point_summarys[i]);
		tcoContractPointsLocal.append(tcoContractPointsVO);
	}
		
	String pay_num=Utility.trimNull(file.getParameter("pay_nums"));
	String pay_num_name=Utility.trimNull(file.getParameter("pay_num_names"));
	String exp_date=Utility.trimNull(file.getParameter("exp_dates"));
	String pay_rate=Utility.trimNull(file.getParameter("pay_rates"));
	String pay_money=Utility.trimNull(file.getParameter("pay_moneys"));
	String pay_summary=Utility.trimNull(file.getParameter("pay_summarys"));

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
		tcoContractPayPlanVO.setPay_rate(Utility.parseDecimal(pay_rates[i],new BigDecimal(0.00)));
		tcoContractPayPlanVO.setPay_money(Utility.parseDecimal(pay_moneys[i],new BigDecimal(0.00)));
		tcoContractPayPlanVO.setPay_summary(pay_summarys[i]);
		tcoContractPayPlanLocal.append(tcoContractPayPlanVO);
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
	window.returnValue = 1;
	localtion="tcocontractset.jsp";
<%}%>
/*动态生成合同要点明细和付款计划 start */


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
	
	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))return false;//结束日期
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);	

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
	    
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox1' value="+count+"/>&nbsp;&nbsp;"+"<input type='hidden' name='coProduct_id' value='"+v[0]+"'/>";
	    tr_new.cells[1].innerHTML=  v[1];
		tr_new.cells[2].innerHTML = v[2]; 
		tr_new.cells[3].innerHTML = v[3];   
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
	    
	    tr_new.cells[0].innerHTML = "tcoContractPointsList&nbsp;&nbsp;";
	    tr_new.cells[1].innerHTML = v[0];
	    tr_new.cells[2].innerHTML = v[1]; 
		tr_new.cells[3].innerHTML = v[2];
		tr_new.cells[4].innerHTML = v[3];
	    tr_new.cells[5].innerHTML = v[4]; 
		tr_new.cells[6].innerHTML = v[5];     
	    tr_new.cells[0].setAttribute("align","left");
	    tr_new.cells[2].setAttribute("align","left");
	}	
}

function addAccountDetail(){
	var url = "<%=request.getContextPath()%>/contractManagement/tcoAccountDetail_new.jsp";
	var v = showModalDialog(url,'','dialogWidth:450px;dialogHeight:300px;status:0;help:0;');		
	
	var tab = document.getElementById('table1'),tr_new,newTd;
	var count = tab.rows.length+1;
	if (v!=null){
	    tr_new = tab.insertRow();
		tr_new.insertCell(0); 
		tr_new.insertCell(1); 
		tr_new.insertCell(2); 
	    tr_new.setAttribute("className","tr"+count%2);
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox1' value="+count+"/>&nbsp;&nbsp;"+"<input type='hidden' name='coProduct_id' value='"+v[0]+"'/>"+v[0];
	    tr_new.cells[1].innerHTML =v[1];
	    tr_new.cells[2].innerHTML =v[2];	    
	    tr_new.cells[0].setAttribute("align","left");
	    tr_new.cells[2].setAttribute("align","left");  
	}	
}
function delAccountDetail(obj){
	 var table=document.getElementById("table1");
	 var roleIds=document.getElementsByName("checkbox1");
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
	var cols1=table1.rows[1].cells.length;
	var coProduct_ids=document.getElementsByName("coProduct_id");
	var coProduct_names=new Array();
	var sub_ht_moneys=new Array();
	var point_summarys=new Array();
	var contractPoint="";
	for(var i=1;i<rows1;i++)
	{	
		coProduct_names.push(table1.rows[i].cells[1].innerText);
		sub_ht_moneys.push(table1.rows[i].cells[2].innerText);
		point_summarys.push(table1.rows[i].cells[3].innerText);
	}
	document.getElementById("coProduct_ids").value=coProduct_ids;
	document.getElementById("coProduct_names").value=coProduct_names;
	document.getElementById("sub_ht_moneys").value=sub_ht_moneys;
	document.getElementById("point_summarys").value=point_summarys;

	var table2 = document.getElementById("table2");
	var rows2=table2.rows.length;
	var cols2=table2.rows[1].cells.length;
	var pay_nums=new Array();
	var pay_num_names=new Array();
	var exp_dates=new Array();
	var pay_rates=new Array();
	var pay_moneys=new Array();
	var pay_summarys=new Array();

	for(var i=1;i<rows2;i++)
	{	
		pay_nums.push(table2.rows[i].cells[1].innerText);
		pay_num_names.push(table2.rows[i].cells[2].innerText);
		exp_dates.push(table2.rows[i].cells[3].innerText);
		pay_rates.push(table2.rows[i].cells[4].innerText);
		pay_moneys.push(table2.rows[i].cells[5].innerText);
		pay_summarys.push(table2.rows[i].cells[6].innerText);
	}
	document.getElementById("pay_nums").value=pay_nums;
	document.getElementById("pay_num_names").value=pay_num_names;
	document.getElementById("exp_dates").value=exp_dates;
	document.getElementById("pay_rates").value=pay_rates;
	document.getElementById("pay_moneys").value=pay_moneys;
	document.getElementById("pay_summarys").value=pay_summarys;


	document.theform.action="tcocontract_new.jsp";	
	document.theform.submit();
}

/*查看附件方法*/
function DownloadAttachment(attachmentId){
	var url = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;		
	window.location.href = url;	
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
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>到账明细</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td align="right">客户名称: </td><!--客户名称-->
		<td align="left" colspan="3">
			<input type="hidden" id="cust_id" name="cust_id" name="cust_id"/>
			<input maxlength="100" readonly class='edline' id="cust_name" name="cust_name" size="60" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">
		</td>
	</tr>	
	<tr>
		<td  align="right">合同编号: </td><!--合同编号-->
		<td  align="left"><input type="text" name="cocontract_sub_bh" size="25" value="<%=cocontract_sub_bh%>" onkeydown="javascript:nextKeyPress(this)" readonly="readonly"></td>
		<td align="right">合同金额: </td><!--客户名称-->
		<td align="left" ><input type="text" name="ht_money" size="25" value="<%=ht_money%>" onkeydown="javascript:nextKeyPress(this)" readonly="readonly"></td>
	</tr>
	<tr>	
		<td align="right">付款方式: </td>
		<td align="left" >
			<select name="payment_type" style="width: 145px;" disabled="disabled">
				<%=Argument.getDictParamOptions(5003,payment_type) %>
			</select>
		</td>
		<td align="right">合同类型: </td>
		<td align="left" >
			<select name="cocontract_type" style="width: 145px;" disabled="disabled">
				<%=Argument.getDictParamOptions(5004,cocontract_type) %>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="right">签署日期:</td>
		<td>
			<INPUT TYPE="text" NAME="sign_date_picker" class=selecttext value="<%=sign_date%>" readonly="readonly" size="25">
		</td>
		<td align="right">开始日期:</td>
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="<%=start_date%>" readonly="readonly" size="25">
		</td>
	</tr>
	<tr>	
		<td align="right">结束日期:</td>
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%=end_date%>" readonly="readonly" size="25">
		</td>
		<td align="right">免费维护到期日:</td>
		<td>
			<INPUT TYPE="text" NAME="main_end_date_picker" class=selecttext value="<%=main_end_date%>" readonly="readonly" size="25">
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
		</td>
	</tr>
	<%	}
	 %>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><font color="#215dc6"><b>付款计划</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="85%" cellspacing="3">
	<tr>
		<td  align="right">期数: </td>
		<td  align="left"><input type="text" name="pay_num" size="25" value=""></td>
		<td align="right">期数说明:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_num_name" size="25" class=selecttext value="" >
		</td>
	</tr>
	<tr>
		<td align="right">预计付款日期: </td>
		<td>
			<INPUT TYPE="text" NAME="exp_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_date_picker,theform.exp_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="exp_date"   value="">
		</td>
		<td align="right">付款比例:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_rate" size="25"  value="" >
		</td>
		<td align="right">付款金额:</td>
		<td>
			<INPUT TYPE="text" NAME="pay_money" size="25"  value="" >
		</td>
	</tr>
	<tr>
		<td align="right">付款条件说明: </td>
		<td align="left" colspan="3"><INPUT TYPE="text" NAME="pay_summary" size="55"  value="" ></td>
	</tr>
</table>
<table border="0" width="82%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="宋体"><b>到账明细</b></font></td>
					<td align="right" colspan="2">
						<a href="#" onclick="javascript:addAccountDetail()">新 增(A)</a>&nbsp;&nbsp;
						<a href="#" onclick="javascript:delPlan(document.theform.checkbox1)">删 除(D)</a>
					</td>
				</tr>
			</table>
			<table id="table1" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">				
				<tr class=trtagsort>
					<td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.checkbox1);">&nbsp;到账时间</td>
					<td>到账金额</td>
					<td>到账情况</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
</table>
<table border="0" width="84%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="saveAction()"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
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
