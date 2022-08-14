<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*" %>
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
Integer contract_id=new Integer(0);
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	

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


<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>


/*�ͻ���Ϣ*/
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
	if(!sl_checkDate(document.theform.sign_date_picker,"ǩ������"))return false;//ǩ������
	syncDatePicker(document.theform.sign_date_picker, document.theform.sign_date);	
	
	if(!sl_checkDate(document.theform.end_date_picker,"��������"))return false;//��������
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);	

	return sl_check_update();
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
	    //tab.rows[0].cloneNode(true);
	   
	   // tab.rows[0].parentNode.appendChild(tr_new);
	    //tr_new.setAttribute("class","trtagsort");
	    
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox1' value="+count+"/>&nbsp;&nbsp;"+"<input type='text' name='collection_condition' value='"+v[0]+"'/>";
	    tr_new.cells[1].innerHTML = "<input type='text' name='plan_date' value='"+v[1]+"'/>";
	    tr_new.cells[2].innerHTML = "<input type='text' name='collection_money' value='"+v[2]+"'/>";	    
	    tr_new.cells[0].setAttribute("align","left");
	    tr_new.cells[2].setAttribute("align","left");
	    
	    //tr_new.onclick = function(){
	    //  alert('�㵥����');
	   // }  
	}	
	
	/**
	var newTr=table1.insertRow(); 
	var newTd = newTr.insertCell(); 
	newTr.cells[0].innerHTML="<input type='checkbox' name='checkbox1' value="+count+"/>&nbsp;&nbsp;"+v[0];
*/
}

function delPlan(obj){
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
		tr_new.insertCell(0); 
		tr_new.insertCell(1); 
		tr_new.insertCell(2); 
		tr_new.insertCell(3); 
	    
	    tr_new.cells[0].innerHTML = "<input type='checkbox' name='checkbox2' value="+count+"/>&nbsp;&nbsp;"+"<input type='text' name='m_money' value='"+v[0]+"'/>";
	    tr_new.cells[1].innerHTML = "<input type='text' name='m_date' value='"+v[1]+"'/>";
	    tr_new.cells[2].innerHTML = "<input type='text' name='m_cycle' value='"+v[2]+"'>"+v[3]+"</input>";	 
		tr_new.cells[3].innerHTML = "<input type='hidden' name='m_cycle_unit' value='"+v[3]+"'/>";    
	    tr_new.cells[0].setAttribute("align","left");
	    tr_new.cells[2].setAttribute("align","left");
	    
	  
	}	
	
	/**
	var newTr=table2.insertRow(); 
	var newTd = newTr.insertCell(); 
	newTr.cells[0].innerHTML="<input type='checkbox' name='checkbox1' value="+count+"/>&nbsp;&nbsp;"+v[0];
*/
}

function delMaintence(obj){
	 var table=document.getElementById("table2")
	 var roleIds=document.getElementsByName("checkbox2");
	 var tableLen = table.rows.length;
	 for(var i=tableLen-1;i>0;i--){
		 if(roleIds[i-1].checked){			 	
		    table.deleteRow(i); 
	 	}
	 }
}
function saveAction(){
	var condition=document.getElementsByName("collection_condition");
	var date=document.getElementsByName("plan_date");
	var money=document.getElementsByName("collection_money");
	 
	var conditions=new Array();
	var dates=new Array();
	var moneys=new Array();
	var plans=new Array();
	var table1 = document.getElementById("table1");   // idΪtable1�ı��
	var rows = table1.rows;   // ��ȡtable���У����Ϊ����

	for (var i=0; i<rows.length-1; i++) {  
		conditions.push(condition[i].value);
		dates.push(date[i].value);
		moneys.push(money[i].value);
	}
	plans.push(conditions);
	plans.push(dates);
	plans.push(moneys);

    var m_money=document.getElementsByName("m_money");
	var m_date=document.getElementsByName("m_date");
	var m_cycle=document.getElementsByName("m_cycle");
	var m_cycle_unit=document.getElementsByName("m_cycle_unit");
    var m_moneys=new Array();
	var m_dates=new Array();
	var m_cycles=new Array();
	var m_cycle_units=new Array();
	var table2=document.getElementById("table2");
	var rows2=table2.rows;
	for(var j=0;j<rows2.length-1;j++){
		m_moneys.push(m_money[j].value);
		m_dates.push(m_date[j].value);
		m_cycles.push(m_cycle[j].value);
		m_cycle_units.push(m_cycle_unit[j].value);
	}
	
	document.theform.action="contract_new.jsp?conditions="+conditions+'&&dates='+dates+'&&moneys='+moneys+'&&m_moneys='+m_moneys+'&&m_dates='+m_dates+'&&m_cycles='+m_cycles+'&&m_cycle_units='+m_cycle_units;

	if(!sl_checkDate(document.theform.sign_date_picker,"ǩ������"))return false;//ǩ������
	syncDatePicker(document.theform.sign_date_picker, document.theform.sign_date);	
	
	if(!sl_checkDate(document.theform.end_date_picker,"��������"))return false;//��������
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);	
	document.theform.submit();
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>��ͬ¼��</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="68%" cellspacing="3">
	<tr>
		<td align="right">�ͻ�����: </td><!--�ͻ�����-->
		<td align="left" colspan="3">
			<input type="hidden" id="cust_id" name="cust_id" name="cust_id"/>
			<input maxlength="100" readonly class='edline' id="cust_name" name="cust_name" size="60" onkeydown="javascript:nextKeyPress(this);" value="">
			<button type="button" class="xpbutton3" accessKey=e name="btnEdit" title="�ͻ�ѡ�� " 
		        onclick="javascript:getMarketingCustomer('customer','0');">�ͻ�ѡ�� 
		    </button>
		</td>
	</tr>	
	<tr>
		<td  align="right">��ͬ���: </td><!--��ͬ���-->
		<td  align="left"><input type="text" name="contract_bh" size="25" value="" onkeydown="javascript:nextKeyPress(this)"></td>
		<td align="right">��ͬ���: </td><!--�ͻ�����-->
		<td align="left" ><input type="text" name="sum_money" size="25" value="" onkeydown="javascript:nextKeyPress(this)"></td>
	</tr>
	<tr>	
		<td align="right">���ʽ: </td>
		<td align="left" >
			<select name="payment_type" style="width: 145px;">
				<%=Argument.getDictParamOptions(5003,"") %>
			</select>
		</td>
		<td align="right">��ͬ����: </td>
		<td align="left" >
			<select name="payment_type" style="width: 145px;">
				<%=Argument.getDictParamOptions(5004,"") %>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="right">ǩ������:</td>
		<td>
			<INPUT TYPE="text" NAME="sign_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.sign_date_picker,theform.sign_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="sign_date"   value="">
		</td>
		<td align="right">��ʼ����:</td>
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="start_date"   value="">
		</td>
	</tr>
	<tr>	
		<td align="right">��������:</td>
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="end_date"   value="">
		</td>
		<td align="right">���ά��������:</td>
		<td>
			<INPUT TYPE="text" NAME="main_end_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.main_end_date_picker,theform.main_end_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="main_end_date"   value="">
		</td>
	</tr>
	<tr id="reader2" style="display:">
          	        <td align="right">��Ӹ���:</td><!-- ��Ӹ���: --> 
                    <td colspan="3">          	
            	    <input type="file" name="file_info" size="60">&nbsp;<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%>" src="<%=request.getContextPath()%>/images/minihelp.gif"><!-- ѡ�񸽼��ļ� -->
                </td>
    </tr>	
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="����"><b>������ϸ</b></font></td>
					<td align="right" colspan="2">
						<a href="#" onclick="javascript:addAccountDetail()">�� ��(A)</a>&nbsp;&nbsp;
						<a href="#" onclick="javascript:delPlan(document.theform.checkbox1)">ɾ ��(D)</a>
					</td>
				</tr>
			</table>
			<table id="table1" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">				
				<tr class=trtagsort>
					<td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.checkbox1);">&nbsp;����ʱ��</td>
					<td>���˽��</td>
					<td>�������</td>
				</tr>
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
					<td  colspan="2"><font color='red' face="����"><b>��Ʊ���</b></font></td>
					<td align="right" colspan="2">
						<a href="#" onclick="javascript:addContractPayPlan()">�� ��(A)</a>&nbsp;&nbsp;
						<a href="#" onclick="javascript:delMaintence(document.theform.id)">ɾ ��(D)</a>
					</td>
				</tr>
			</table>
			<table id="table2" border="0" cellspacing="1" cellpadding="2" 
					class="tablelinecolor" width="100%">
				<tr class=trtagsort>
					<td><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.checkbox2);">&nbsp;����</td>
					<td>�����˺�</td>
					<td>�����˺�����</td>
					<td>��Ʊʱ��</td>
					<td>��Ʊ���</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="saveAction()"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
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
