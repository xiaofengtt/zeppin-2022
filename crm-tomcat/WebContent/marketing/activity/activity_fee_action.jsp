<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ȡ��ҳ�洫�ݲ���
Integer serial_no =  Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));//��η���������
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(1));//1 �½���2 �༭
Integer transFlag = Utility.parseInt(Utility.trimNull(request.getParameter("transFlag")),new Integer(1));//1 ������б�2 ��ܽ��б�
Integer q_activitySerialNo = Utility.parseInt(Utility.trimNull(request.getParameter("q_activitySerialNo")),new Integer(0));

//�����ֶ�
//----���Ϣ
Integer manage_code = new Integer(0);
String active_type ="";
String active_type_name ="";
String active_theme ="";
String beginDate ="";
String endDate ="";
String customer_type ="";
String active_plan ="";
String active_code= "";
BigDecimal active_fee = new BigDecimal(0.00);
//----�����
String feeItems= "";
BigDecimal feeAmount= new BigDecimal(0.00);
String remark= "";

//������������
boolean bSuccess = false;
int iCount = 0;
String[] totalColumn = new String[0];
List activityList = null;
List activityFeeList = null;
List feeList = null;
Map activityMap = null;
Map activityFeeMap = null;
Map feeMap = null;

//��ö���
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityFeeLocal activityFeeLocal = EJBFactory.getActivityFee();
ActivityFeeVO vo = new ActivityFeeVO();

//�������޸���Ϣ
if(request.getMethod().equals("POST")){
	 vo = new ActivityFeeVO();
	
	feeItems = Utility.trimNull(request.getParameter("feeItems"));
	remark = Utility.trimNull(request.getParameter("remark"));
	feeAmount = Utility.parseDecimal(Utility.trimNull(request.getParameter("feeAmount")),new BigDecimal(0.00));
	
	vo.setActive_serial_no(q_activitySerialNo);
	vo.setFee_items(feeItems);
	vo.setRemark(remark);
	vo.setFee_amount(feeAmount);
	vo.setInput_man(input_operatorCode);
	
	if(actionFlag.intValue()==1){
		activityFeeLocal.append(vo);
		bSuccess = true;
	}else if(actionFlag.intValue()==2){
		vo.setSerial_no(serial_no);
		activityFeeLocal.modi(vo);
		bSuccess = true;
	}
}

//���Ϊ�༭״̬ ��ȡ�������ϸ��Ϣ
if(actionFlag.intValue()==2 && serial_no.intValue()>0 ){
	vo = new ActivityFeeVO();
	vo.setSerial_no(serial_no);

	feeList = activityFeeLocal.query(vo);
	
	if(feeList.size()>0){
		feeMap = (Map)feeList.get(0);
	}
	
	if(feeMap!=null){
		q_activitySerialNo = Utility.parseInt(Utility.trimNull(feeMap.get("Active_Serial_no")),new Integer(0));
		feeAmount = Utility.parseDecimal( Utility.trimNull(feeMap.get("FeeAmount")),new BigDecimal(0.00));
		feeItems = Utility.trimNull(feeMap.get("FeeItems"));	
		remark = Utility.trimNull(feeMap.get("Remark"));	
	}
}

//��ȡ���Ϣ
if(q_activitySerialNo.intValue()>0){	
	activityList = activityLocal.load(q_activitySerialNo);
	if(activityList.size()>0){
		activityMap = (Map)activityList.get(0);
	}	
	
	if(activityMap!=null){
		active_type_name = Utility.trimNull(activityMap.get("ACTIVE_TYPE_NAME"));
		active_type = Utility.trimNull(activityMap.get("ACTIVE_TYPE"));
		active_theme = Utility.trimNull(activityMap.get("ACTIVE_THEME"));
		beginDate = Utility.trimNull(activityMap.get("START_DATE")).toString().substring(0,16).trim();
		endDate = Utility.trimNull(activityMap.get("END_DATE")).toString().substring(0,16).trim();
		manage_code = Utility.parseInt(Utility.trimNull(activityMap.get("MANAGE_CODE")),new Integer(0));
		customer_type  = Utility.trimNull(activityMap.get("CUSTOMER_TYPE"));
		active_plan = Utility.trimNull(activityMap.get("ACTIVE_PLAN"));
		active_code = Utility.trimNull(activityMap.get("ACTIVE_CODE"));
		active_fee = Utility.parseDecimal( Utility.trimNull(activityMap.get("ACTIVITY_FEE")),new BigDecimal(0.00));
	}	
	
	vo = new ActivityFeeVO();
	vo.setActive_serial_no(q_activitySerialNo);
	//������Ϣ
	IPageList pageList = activityFeeLocal.pageList_query(vo,totalColumn,1,-1);
	activityFeeList = pageList.getRsList();
}


%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<!--���������-->
<title><%=LocalUtilis.language("menu.activityFeeAction",clientLocale)%> </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
/*��������*/	
window.onload = function(){
	//document.getElementById("table2").style.height =document.getElementById("table1").clientHeight+28;
	//����༭״̬ �����ѡ
	var actionFlag = document.getElementById("actionFlag").value;
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(actionFlag==2){
		document.getElementById("q_activityOptions").disabled = true;
	}
	
	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ��");//����ɹ�
		if((actionFlag==1&&confirm("<%=LocalUtilis.language("message.ifAddFeeDetails2",clientLocale)%> ?"))){//����Ҫ�����������ϸ��
			changeActivity();
		}else{
			CancelAction();
		}		
		
	}
}

/*ѡ��*/
function changeActivity(){
	var transFlag = document.getElementById("transFlag").value;
	var q_activitySerialNo = document.getElementById("q_activityOptions").value;	
	document.getElementById("q_activitySerialNo").value = q_activitySerialNo;
	var url = "activity_fee_action.jsp?q_activitySerialNo="+q_activitySerialNo+"&transFlag="+transFlag;
	window.location.href = url;
}

/*����*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action = "activity_fee_action.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
}

/*��֤����*/
function validateForm(form){
	if(!sl_checkChoice(document.theform.q_activitySerialNo,"<%=LocalUtilis.language("message.activeInfo",clientLocale)%> ")){return false;}//���Ϣ
	if(!sl_check(document.theform.feeItems, "<%=LocalUtilis.language("class.feeItems",clientLocale)%> ",30,1)){return false;}	//������Ŀ
	if(!sl_checkDecimal(document.theform.feeAmount,"<%=LocalUtilis.language("class.feeAmount2",clientLocale)%> ",13,2,1))return false;//���ý��
	if(!sl_check(document.theform.remark, "<%=LocalUtilis.language("class.feeRemark",clientLocale)%> ",200,0)){return false;}	//����˵��	
	
	var feeAmount = document.theform.feeAmount.value;
	if(feeAmount<=0){
		sl_alert("<%=LocalUtilis.language("message.inputFeeAmount",clientLocale)%> ��");//��������ý��
		return false;
	}
	
	return true;
}


/*����*/
function CancelAction(){
	var url;
	var transFlag = document.getElementById("transFlag").value;
	
	if(transFlag==2){
		url = "activity_end.jsp";
	}
	else {
		url = "activity_fee_list.jsp";
	}
	
	window.location.href = url;	
}

/*�޸ķ���*/
function ModiAction(serial_no){		
	var transFlag = document.getElementById("transFlag").value;		
	var url = "activity_fee_action.jsp?actionFlag=2&serial_no="+serial_no;	
	var url = url +"&transFlag="+transFlag;
	window.location.href = url;	
}

</script>
</HEAD>

<BODY class="body body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="transFlag" name="transFlag" value="<%=transFlag%>"/>
<input type="hidden" id="actionFlag" name="actionFlag" value="<%=actionFlag%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.activeFeeSet",clientLocale)%> </b></font><!--���������-->
</div>
<br/>
<div style="float:left;width:100%;" class="product-list">
		<div style="float:left; width:40%; ">
			<font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("message.activeChoose",clientLocale)%> ��</font><!--��ѡ��-->
			<select name = "q_activityOptions" id="q_activityOptions" onkeydown="javascript:nextKeyPress(this)" style="width:180px" onchange="javascript:changeActivity()" >
				<%=Argument.getActivityOptions(q_activitySerialNo)%>
			</select>
			<input type="hidden"  name="q_activitySerialNo" id="q_activitySerialNo" value="<%= q_activitySerialNo%>"/>
			<p>
			 <table  id="table1" cellSpacing="1" cellPadding="2" width="95%"  >
			 	<tr >
			 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeInfo2",clientLocale)%> </b></font></td>
						<!--�������Ϣ-->
			 	</tr>
				 <tr >	
					 <td width="40%"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCode",clientLocale)%> ��</font></td><!--����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_code%></font>  </td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%> ��</font></td><!--�����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_theme%></font> </td>
				 </tr>
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activityCategories",clientLocale)%> ��</font></td><!--����-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_type_name%></font></td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.manageCode",clientLocale)%> ��</font></td><!--�������-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=Argument.getOpNameByOpCode(manage_code)%></font> </td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.beginDate2",clientLocale)%> ��</font></td><!--���ʼʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= beginDate%></font></td>
				 </tr>
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate2",clientLocale)%> ��</font></td><!--�����ʱ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= endDate%></font></td>
				 </tr>
				<tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activeFee2",clientLocale)%> ��</font></td><!--�����-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_fee%></font></td>
				 </tr>
				  <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ��</font></td><!--����ͻ�Ⱥ-->
					 <td> <font size="2" face="΢���ź�">&nbsp;&nbsp;<%= customer_type%></font></td>
				 </tr>
				 <tr >	
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ��</font></td><!--��ƻ���Ŀ��-->
					 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%= active_plan%></font></td>
				 </tr>
			</table>
			<br>
	</div>
	
	<div style=" float:right; width:60%;margin-top:40px ">
			<table  id="table2" cellSpacing="1" cellPadding="2" width="100%"  >
					<tr >
				 			<td colspan="2" align="left"><font size="4" face="΢���ź�"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.activeFeeSet",clientLocale)%> </b></font></td>
							<!--���������-->
				 	</tr>
				 	<tr >	
						 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.feeItems",clientLocale)%> ��</font></td><!--������Ŀ-->
						 <td>
						 	<input type="text" name="feeItems" size="30" value="<%= feeItems%>" onkeydown="javascript:nextKeyPress(this)"/>	
						 </td>
					 </tr>
					 <tr >	
						 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.feeAmount2",clientLocale)%> ��</font></td><!--���ý��-->
						 <td><input type="text" name="feeAmount" size="30" value="<%= feeAmount%>" onkeydown="javascript:nextKeyPress(this)"/>	</td>
					 </tr>
					  <tr >	
						 <td><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.feeRemark",clientLocale)%> ��</font></td><!--����˵��-->
						 <td><font size="2" face="΢���ź�">
						 	<textarea rows="7" name="remark" onkeydown="javascript:nextKeyPress(this)" cols="75"><%= remark%></textarea>	
						 </td>
					 </tr>
					<tr >
				 			<td colspan="2" align="right">				
				 				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
							</td>
				 	</tr>
			</table>
			<br>
	</div>	

	<div align="center" >
			<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr class="trh">
					<td align="center" width="15%"><%=LocalUtilis.language("class.feeItems",clientLocale)%> </td><!--������Ŀ-->
					<td align="center" width="15%"><%=LocalUtilis.language("class.activeThemeList",clientLocale)%> </td><!--���������-->
					<td align="center" width="15%"><%=LocalUtilis.language("class.feeAmount2",clientLocale)%>��<%=LocalUtilis.language("message.yuan",clientLocale)%> ��</td><!--���ý��--><!--Ԫ-->
					<td align="center" width="*"><%=LocalUtilis.language("class.feeRemark",clientLocale)%> </td><!--����˵��-->
					<td align="center" width="15%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
				</tr>
<%
if(activityFeeList!=null){
	//�����ֶ�
	Iterator iterator = activityFeeList.iterator();
	Integer serial_no_list;	
	String active_code_list;
	String active_theme_list;
	String feeItems_list;
	BigDecimal feeAmount_list;
	String remark_list;
	
	while(iterator.hasNext()){
		iCount++;
		activityFeeMap = (Map)iterator.next();
		
		serial_no_list = Utility.parseInt(Utility.trimNull(activityFeeMap.get("Serial_no")),new Integer(0));
		active_theme_list = Utility.trimNull(activityFeeMap.get("ACTIVE_THEME"));	
		feeAmount_list = Utility.parseDecimal( Utility.trimNull(activityFeeMap.get("FeeAmount")),new BigDecimal(0.00),2,"1");
		feeItems_list = Utility.trimNull(activityFeeMap.get("FeeItems"));	
		remark_list = Utility.trimNull(activityFeeMap.get("Remark"));	
%>
	<tr class="tr<%=iCount%2%>">
		 <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%=feeItems_list%></td>
		 <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%= active_theme_list%></td>        
		 <td align="center"><%= feeAmount_list%></td>     
		 <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%= remark_list%></td>   
		  <td align="center">              	
              	<a href="javascript:ModiAction(<%=serial_no_list%>)">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
               	</a>
           </td>    
	</tr>
<%}
}
if(iCount==0){
%>
	<tr >
		<td align="center" colspan="6" ><%=LocalUtilis.language("message.noActiveTask",clientLocale)%> </td><!--û����ػ����-->    
	</tr>
<%
}
%>
			</table>		
	</div>
</div>
<% 
activityFeeLocal.remove();
activityLocal.remove();
 %>
</form>
</BODY>
</HTML>
