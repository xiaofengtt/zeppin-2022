<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(1));//1.���� 2.�޸�
Integer partn_id =  Utility.parseInt(Utility.trimNull(request.getParameter("partn_id")),new Integer(0));
Integer showFlag =  Utility.parseInt(Utility.trimNull(request.getParameter("showFlag")),new Integer(1));

//����\ý��
Integer q_partn_type2_flag = Utility.parseInt(Utility.trimNull(request.getParameter("q_partn_type2_flag")),new Integer(2));
String q_partn_type2_name = "";

if(q_partn_type2_flag.intValue()==1){
	q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.channels",clientLocale);//����
}
else if(q_partn_type2_flag.intValue()==2){
	q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.media",clientLocale);//ý��
}

//��������
boolean bSuccess = false;
 
//�����ֶ�
String partn_name = Utility.trimNull(request.getParameter("partn_name"));
String card_type =Utility.trimNull(request.getParameter("card_type"));
String card_id =Utility.trimNull(request.getParameter("card_id"));
Integer sex = Utility.parseInt(Utility.trimNull(request.getParameter("sex")),new Integer(1));
Integer age = Utility.parseInt(Utility.trimNull(request.getParameter("age")),new Integer(0));
Integer birthday = Utility.parseInt(Utility.trimNull(request.getParameter("birthday")),new Integer(Utility.getCurrentDate()));
String voc_type = Utility.trimNull(request.getParameter("voc_type"));
String contract_man = Utility.trimNull(request.getParameter("contract_man"));
String summary = Utility.trimNull(request.getParameter("summary"));
String touch_type = Utility.trimNull(request.getParameter("touch_type"),"110901");
String o_tel = Utility.trimNull(request.getParameter("o_tel"));
String h_tel = Utility.trimNull(request.getParameter("h_tel"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String bp = Utility.trimNull(request.getParameter("bp"));
String fax = Utility.trimNull(request.getParameter("fax"));
String e_mail = Utility.trimNull(request.getParameter("e_mail"));
String post_code = Utility.trimNull(request.getParameter("post_code"));
String post_address = Utility.trimNull(request.getParameter("post_address"));
String report_type = Utility.trimNull(request.getParameter("report_type"));

//��ö���
PartnerLocal partnerLocal = EJBFactory.getPartner();
PartnerVO vo = null;

//���ò���
if (request.getMethod().equals("POST")) {
	vo = new PartnerVO();
		
	vo.setPartn_name(partn_name);
	vo.setCard_type(card_type);
	vo.setCard_id(card_id);
	vo.setSex(sex);
	vo.setAge(age);
	vo.setBirthday(birthday);
	vo.setVoc_type(voc_type);
	//vo.setContract_man(contract_man);
	vo.setSummary(summary);
	vo.setTouch_type(touch_type);
	vo.setO_tel(o_tel);
	vo.setH_tel(h_tel);
	vo.setMobile(mobile);
	vo.setBp(bp);
	vo.setFax(fax);
	vo.setE_mail(e_mail);
	vo.setPost_address(post_address);
	vo.setPost_code(post_code);
	vo.setReport_type(report_type);
	
	vo.setInput_man(input_operatorCode);
	vo.setPartn_type(new Integer(1));
	vo.setPartn_type2_flag(q_partn_type2_flag);
	
	if(actionFlag.intValue()==1){
		System.out.println("action_flag1"+actionFlag);
		partnerLocal.append(vo);
		
		bSuccess = true;
	}else if(actionFlag.intValue()==2){
		vo.setPartn_id(partn_id);
		System.out.println("action_flag2"+actionFlag);
		partnerLocal.modi(vo);
		
		bSuccess = true;
	}
}

if(partn_id.intValue()>0){
	vo = new PartnerVO();
	
	vo.setPartn_id(partn_id);
	List modiList = partnerLocal.query(vo);
	Map modiMap = null; 
	
	if(modiList.size()>0){
		modiMap = (Map)modiList.get(0);
	}
	
	if(modiMap!=null){
		partn_name = Utility.trimNull(modiMap.get("PARTN_NAME"));
		card_type =Utility.trimNull(modiMap.get("CARD_TYPE"));
		card_id =Utility.trimNull(modiMap.get("CARD_ID"));
		sex = Utility.parseInt(Utility.trimNull(modiMap.get("SEX")),new Integer(1));
		age = Utility.parseInt(Utility.trimNull(modiMap.get("AGE")),new Integer(0));
		birthday = Utility.parseInt(Utility.trimNull(modiMap.get("BIRTHDAY")),new Integer(Utility.getCurrentDate()));
		voc_type = Utility.trimNull(modiMap.get("VOC_TYPE"));
		contract_man = Utility.trimNull(modiMap.get("CARD_ID"));
		summary =Utility.trimNull(modiMap.get("SUMMARY"));
		touch_type = Utility.trimNull(modiMap.get("TOUCH_TYPE"));
		o_tel =Utility.trimNull(modiMap.get("O_TEL"));
		h_tel =Utility.trimNull(modiMap.get("H_TEL"));
		mobile = Utility.trimNull(modiMap.get("MOBILE"));
		bp = Utility.trimNull(modiMap.get("BP"));
		fax = Utility.trimNull(modiMap.get("FAX"));
		e_mail = Utility.trimNull(modiMap.get("E_MAIL"));
		post_code =Utility.trimNull(modiMap.get("POST_CODE"));
		post_address =Utility.trimNull(modiMap.get("POST_ADDRESS"));
		report_type = Utility.trimNull(modiMap.get("REPORT_TYPE"));
	}
}


%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.PersMediChannInfoSet",clientLocale)%> </TITLE><!--����ý��������Ϣ����-->
<BASE TARGET="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<script language="javascript">
/*��������*/	
window.onload = function(){
	show('<%=showFlag%>');
	var v_bSuccess = document.getElementById("bSuccess").value;
	var actionFlag = document.getElementById("actionFlag").value;
	var q_partn_type2_flag = document.getElementById("q_partn_type2_flag").value;
		
	if(v_bSuccess=="true"){		
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ��");		//����ɹ�
		CancelAction();
	}
}

/*ȡ��*/
function CancelAction(){
	//var q_partn_type2_flag = document.getElementById("q_partn_type2_flag").value;

	//disableAllBtn(true);
	//if(q_partn_type2_flag==1){
	//	window.location.href = "channel_info_list.jsp"; 
	//}
	//else if(q_partn_type2_flag==2){
	//	window.location.href = "media_info_list.jsp"; 
	//}
	
	window.close();
}

function show(parm){
	document.getElementById("showFlag").value = parm;
	
   for(i=1;i<3;i++) {  
	     if(i!= parm){	     	
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");		   
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display = ''");
		 } 
	}
}

/*��֤����*/
function validateForm(form){
	var saveFlag = true;
	var showFlag = document.getElementById("showFlag").value;
	var i ;
	
	for(folderNo=1;saveFlag&&folderNo<3;folderNo++) {  
		i = folderNo;
		show(i);
		saveFlag = checkfolder(folderNo);	

	}
	
	return saveFlag;
}

function checkfolder(folder_no){
	form = document.theform;
	
	if(folder_no ==1){
		//if(!sl_checkChoice(form.zyhy_type, "ְҵ���"))		return false;
		//if(!sl_checkChoice(form.card_type, "֤������"))		return false;
		if(!sl_check(form.partn_name, "<%=q_partn_type2_name%><%=LocalUtilis.language("class.name",clientLocale)%> ", 100, 1))	return false;//����
		if(form.card_type.value=="110801"){
			if(!sl_check(form.card_id, '<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ', 18, 15))	return false;//֤������
			if(!(form.card_id.value.length==15||form.card_id.value.length==18)){
				sl_alert('<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ');//���֤�������Ϊ15��18λ
				form.card_id.focus();
				return false;
			}
		}
		if((form.card_type.value!=0)&&(!sl_check(form.card_id, '<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ', 40, 1)))		return false;//֤������
		
		if(document.theform.birthday_picker.value!="")	{
			if(!sl_checkDate(document.theform.birthday_picker,'<%=LocalUtilis.language("class.birthday",clientLocale)%> ')) return false;//��������
			syncDatePicker(form.birthday_picker, form.birthday);
		}	
		if(!sl_checkNum(form.age, '<%=LocalUtilis.language("class.age",clientLocale)%> ', 3, 0))	return false;//����
		if(!sl_checkChoice(form.sex, '<%=LocalUtilis.language("class.sex",clientLocale)%> '))	return false;//�Ա�
		}
	else if(folder_no ==2){
		//if(!sl_checkChoice(form.touch_type, '<%=LocalUtilis.language("class.serviceWay",clientLocale)%> '))	return false;//��ϵ��ʽ
		
		//if(!sl_checkNum(form.post_code, '<%=LocalUtilis.language("class.postcode",clientLocale)%> ', 6, 0))	return false;//��������
		
		if(form.e_mail.value.length!=0){
			if(!isEmail(form.e_mail,"Email"))	return false;
		}
		
		if(form.touch_type.value=="110901"){	
			<%if(user_id.intValue()==1){%>	
				//if(!sl_checkNum(form.o_tel, "<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ", 11, 1))	return false;//��˾�绰
				//if(!sl_checkNum(form.mobile, '<%=LocalUtilis.language("class.mobile",clientLocale)%> ', 11, 1))	return false;//�ֻ�����
			<%}
			//else{%>
			//	if(form.h_tel.value.length==0&&form.o_tel.value.length==0&&form.mobile.value.length==0&&form.bp.value.length==0){
			//		sl_alert("<%=LocalUtilis.language("message.telMoreThanOne",clientLocale)%> ");//��������һ����ϵ�绰
			//		return false;
			//	}
				
			//	if(!sl_checkNum(form.mobile, '<%=LocalUtilis.language("class.mobile",clientLocale)%> ', 11, 0))	return false;//�ֻ�����
			//	if(!sl_checkNum(form.bp, '<%=LocalUtilis.language("class.mobile",clientLocale)%> ', 11, 0))	return false;	//�ֻ�����
			<%//}%>
		}
		
		if(form.touch_type.value=="110902"){
			if(!sl_check(form.post_address, '<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ', 60, 1))	return false;	//�ʼĵ�ַ
			if(!sl_check(form.post_code, '<%=LocalUtilis.language("class.postcode",clientLocale)%> ', 60, 1))	return false;	  //��������
		}
		
		if(form.touch_type.value=="110903"){
			if(!sl_check(form.e_mail, "Email", 30, 1))	return false;		
		}
		
		if(form.touch_type.value=="110904"){
			if(!sl_check(form.fax, '<%=LocalUtilis.language("class.fax",clientLocale)%> ', 60, 1))	return false;//����
		}
		
		if(form.touch_type.value=="110905"){
			if(!sl_checkNum(form.mobile, '<%=LocalUtilis.language("class.mobile",clientLocale)%> ', 11, 1))	return false;//�ֻ�����
		}
	}
	
	return true;
}

function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()&&sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){//����������Ϣ
				document.getElementsByName('theform')[0].action = "partner_info_action.jsp";
				document.getElementsByName('theform')[0].submit();
		}	
}

//�绰У��
function checkTel(obj){
	if(obj==null) return false;
	
	var re=new RegExp("^(\d{3,4})\-{0,1}(\d{7,8})$");	
	var tel = obj.value;
	
	if(tel.length>0){
		if(re.test(tel)){			
			return true;
		}
		else{
			sl_alert(re.test(tel));
			sl_alert("<%=LocalUtilis.language("message.phoneNumberError",clientLocale)%> ��");//��������ȷ�ĵ绰����
			return false;
		}		
	}
	
	return false;
}


function getDateByMask(s, m)
{
	if (s.length!=m.length)
	{
		return false;
	}
	var t = m.replace(/d/ig,"\\d").replace(/m/ig,"\\d").replace(/y/ig,"\\d")
	var r = new RegExp("^"+t+"$");
	if (!r.test(s)) return null;
	try
	{
		m=m.replace(/Y/g,"y").replace(/D/g,"d");
		if (m.indexOf("yyyy")>-1)
			return new Date(s.substr(m.indexOf("yyyy"),4),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
		else
			return new Date(s.substr(m.indexOf("yy"),2),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
	}catch (e)
	{
		return null;
	}
}

function checkCardId(){
	if (document.theform.card_type.value != '110801'||document.theform.card_id.value=="")	return;

	var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
	var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
	cardID = cTrim(document.theform.card_id.value,0);
	var current_date = new Date();

	if (r18.test(cardID))
	{
		var t = cardID.match(r18)[1];
		var card_date = getDateByMask(t, "yyyyMMdd");
		
		if (card_date != null )
		{
			if(card_date.getYear() >= 2000)
				document.theform.age.value =( current_date.getYear() - card_date.getYear());
			else
				document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
		}
		else
			document.theform.age.value = "";
			
		var flag = cardID.charAt(16);
		document.theform.birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
	}
	else if (r15.test(cardID))
	{
		var t = cardID.match(r15)[1];
		var card_date = getDateByMask(t,"yyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear()>= 2000)
			{
				document.theform.age.value =( current_date.getYear() - (2000+card_date.getYear()));				
				document.theform.birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
			else
			{
				document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
				document.theform.birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
		}
		else
			document.theform.age.value = "";
		var flag = cardID.charAt(14);	
	}
	else
	{
		sl_alert("<%=LocalUtilis.language("message.customerCardIDError",clientLocale)%> ��");//���֤�����ʽ���Ϸ�
		event.keyCode = 0;
		document.theform.card_id.focus();
	}

}

function calculateAge()
{
	if (event.keyCode == 13||event.keyCode == 9){
		checkCardId();
	}
}
function calculateAge2()
{
		if (document.theform.card_type.value != '110801')	return;
		var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
		var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
		
		cardID = cTrim(document.theform.card_id.value,0);
		var current_date = new Date();
	
		if (r18.test(cardID))
		{
			var t = cardID.match(r18)[1];
			var card_date = getDateByMask(t, "yyyyMMdd");
	
			if (card_date != null )
			{
				if(card_date.getYear() >= 2000)
					document.theform.age.value =( current_date.getYear() - card_date.getYear());
				else
					document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
			}
			else
				document.theform.age.value = "";
			var flag = cardID.charAt(16);
			document.theform.birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
		}
		else if (r15.test(cardID))
		{
			var t = cardID.match(r15)[1];
			var card_date = getDateByMask(t,"yyMMdd");
			if (card_date != null )
			{
				if(card_date.getYear()>= 2000)
				{
					document.theform.age.value =( current_date.getYear() - (2000+card_date.getYear()));				
					document.theform.birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
				}	
				else
				{
					document.theform.age.value =( current_date.getYear() - (1900+card_date.getYear()));
					document.theform.birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
				}	
			}
			else
				document.theform.age.value = "";
			var flag = cardID.charAt(14);
		}
}
//���ݳ������ڼ������䣨Tab��س���
function CalAge()
{
	if (event.keyCode == 13||event.keyCode == 9){
		if(document.theform.partn_type.value==1)
		{
			if(document.theform.birthday_picker.value!="")
			{
				if(!sl_checkDate(document.theform.birthday_picker,'<%=LocalUtilis.language("class.birthday",clientLocale)%> '))	return false;//��������
				var current_date = new Date();					
				document.theform.age.value = current_date.getYear() - document.theform.birthday_picker.value.substring(0,4);			
			}
		}	
	}
}
//ʧȥ����ʱ��������
function CalAge2()
{
		if(document.theform.partn_type.value==1)
		{
			if(document.theform.birthday_picker.value!="")
			{
				if(!sl_checkDate(document.theform.birthday_picker,'<%=LocalUtilis.language("class.birthday",clientLocale)%> ')){//��������
					document.theform.birthday_picker.value="";
					return false;
				}	
				var current_date = new Date();					
				document.theform.age.value = current_date.getYear() - document.theform.birthday_picker.value.substring(0,4);			
			}
		}	
}

function showAcctNum(value)
{		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="partn_type"	value="1"> 
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="actionFlag"	id="actionFlag" value="<%= actionFlag%>" />
<input type="hidden" name="showFlag"	id="showFlag" value="<%= showFlag%>" />
<input type="hidden" name="partn_id"	id="partn_id" value="<%= partn_id%>" />
<input type="hidden" name="q_partn_type2_flag"	id="q_partn_type2_flag" value="<%= q_partn_type2_flag%>" />

<div class="page-title page-title-noborder">
	<font color="#215dc6"><b><%=q_partn_type2_name%><%=LocalUtilis.language("message.personalInfo",clientLocale)%>  </b></font><!--������Ϣ-->
</div>	
<br>

<div class="page-title-select" style="margin-right:20px;margin-top:5px;">
	<font  size="2" face="����"><%=q_partn_type2_name%><%=LocalUtilis.language("class.partn_name",clientLocale)%> ��</font><!--��������-->
	<input type="text" name="partn_name"  value="<%=partn_name%>"/>
</div>
<div align="left" >
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
				<TBODY>
					<TR  class="tr-tabs">							
						<TD vAlign=top>&nbsp;</TD>					
						<!--������Ϣ-->
                        <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
						<!-- ��ϵ��ʽ -->
                        <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> </TD>						
					</TR>
			</TBODY>
	</TABLE>
</div>
<br>

<div  id="r1" align="left"  style="display:none;">
		<table border="0" width="100%" cellspacing="0" cellpadding="0" class="table-popup">
		<tr>
				<td width="20%"><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCardType",clientLocale)%> ��</font></td><!--֤������-->
				<td width="*%">
					<select size="1" onkeydown="javascript:nextKeyPress(this)"name="card_type" style="WIDTH: 150px">
						<%=Argument.getCardTypeOptions2(card_type)%>
					</select>
				</td>
				<td width="20%"><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ��</font></td><!--֤������-->
				<td width="*%">
					<!--�ͻ�����Ǹ��ˣ�������ɺ�س��Զ���ʾ�ͻ���Ӧ���Ա����գ�����-->
					<INPUT name="card_id"
						   onkeydown="javascript:calculateAge();nextKeyPress(this)"
					       onblur="javascript:checkCardId();calculateAge2();"
					       title='<%=LocalUtilis.language("class.custTip",clientLocale)%> '
					       onkeyup="javascript:showAcctNum(this.value)" maxlength="40"
						   value="<%= card_id%>"  size="25">
					<span id="bank_acct_num" class="span"></span>
				</td>
			</tr>		
			
			<tr>			
				<td><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.sex",clientLocale)%> ��</font></td><!--�Ա�-->
				<td>
					<SELECT size="1" onkeydown="javascript:nextKeyPress(this)" name="sex" style="WIDTH: 150px">
						<%=Argument.getSexOptions(sex)%>
					</SELECT>
				</td>
				
				<td><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.age",clientLocale)%> ��</font></td><!--����-->
				<td>
					<INPUT name="age" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="4"	value="<%= Utility.trimNull(age)%>" />
				</td>
			</tr>
			
			<tr>
					<td><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.birthday",clientLocale)%> ��</font></td><!--��������-->
					<td>
						<INPUT TYPE="text" id="birthday_picker" NAME="birthday_picker" class=selecttext onkeydown="javascript:CalAge();nextKeyPress(this)"
						onblur="javascript:CalAge2();"	 size="25" value="<%=Format.formatDateLine(birthday)%>">
						<!-- <INPUT	TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.birthday_picker,theform.birthday_picker.value,this);" onkeydown="javascript:CalAge();nextKeyPress(this)">--> 
						<INPUT	TYPE="hidden" NAME="birthday" value="">
					</td>
					<td>&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;</td>
			</tr>
			<script language="javascript">
				laydate({elem:'#birthday_picker',format:'YYYY-MM-DD',istime:false});
			</script>	
			<tr>

			<%if(q_partn_type2_flag.intValue()==1){%>
				<td><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.profession",clientLocale)%> ��</font></td><!--ְҵ-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="zyhy_type"	style="WIDTH: 230px">
							<%=Argument.getGrzyOptions2(voc_type)%>
					</select>
				</td>
			<%}
			else {%>
			<td ><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.reportType",clientLocale)%> ��</font> </td><!--������ʽ-->
			<td  >
				<select name="report_type" id="report_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%=Argument.getDictParamOptions(2011,report_type)%>
				</select>
			</td>	
			<%}%>
			
				<TD><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.contact",clientLocale)%> ��</font></TD> <!--��ϵ��-->
				<TD><input type="text"  name="contract_man" size="25" value="<%= contract_man%>"></TD>
			</tr>			
				
			<TR>
					<TD valign="top"><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.summary",clientLocale)%> ��</font></TD><!--��ע��Ϣ-->
					<TD colspan="3">
						<TEXTAREA rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="50"><%= summary%></TEXTAREA>
					</TD>
			</TR>
		</table>
		<br/>
</div>

<div id="r2" align="left" style="display:none;">
		<table border="0" width="100%" cellspacing="0" cellpadding="0" class="table-popup">
			<tr>
				<td width="15%"><font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ��</font></td><!--��ϵ��ʽ-->
				<td width="*" colspan="3">
					<select size="1" name="touch_type" style="WIDTH: 120px"
							onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=Argument.getTouchTypeOptions(touch_type)%>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.telephone",clientLocale)%> ��</font></td><!--סլ�绰-->
				<td>
					<input name="h_tel" onkeydown="javascript:nextKeyPress(this)"
						   maxlength="20" size="20" value="<%= h_tel%>" />
				</td>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ��</font></td><!--��˾�绰-->
				<td>
					<INPUT name="o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="<%= o_tel%>" />
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerMobile",clientLocale)%> ��</font></td><!--�ֻ�-->
				<td>
					<input name="mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20"	value="<%= mobile%>" />
				</td>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2��</font></td><!--�ֻ�-->
				<td>
					<INPUT name="bp" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value="<%=bp%>" />
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.fax",clientLocale)%> ��</font></td><!--����-->
				<td>
					<input name="fax" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value="<%= fax%>" />
				</td>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;E-MAIL��</font></td>
				<td>
					<INPUT name="e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%= e_mail%>" />
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.postcode",clientLocale)%> ��</font></td><!--��������-->
				<td colspan="3">
					<input name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%= post_code%>">
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;<font size="2" face="����">&nbsp;&nbsp;<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ��</font></td><!--�ʼĵ�ַ-->
				<td colspan="3">
					<input name="post_address" onkeydown="javascript:nextKeyPress(this)" size="50"  value="<%= post_address%>">
				</td>
			</tr>			
		</table>

<br>
</div>

<div align="right">
	<button type="button"  class="xpbutton3" accesskey="s" id="btnSave" name="btnSave"
		onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<U>S</U>)</BUTTON><!--����-->
	&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accesskey="b" id="btnReturn" name="btnReturn"
		onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<U>B</U>)</BUTTON><!--�ر�-->
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<br>
<% partnerLocal.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>