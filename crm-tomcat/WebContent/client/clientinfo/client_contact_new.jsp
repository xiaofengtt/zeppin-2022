<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
//��õ�ַ������
Integer contact_id = Utility.parseInt(request.getParameter("contact_id"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"), new Integer(1));
Integer contactType = Utility.parseInt(request.getParameter("contactType"), new Integer(0));
String session_cust_name = Utility.trimNull(session.getAttribute("name")); //��session��ȡ�ͻ�����

//��ö���
DictparamLocal dict_local = EJBFactory.getDictparam();
DictparamVO dict_vo = new DictparamVO();
ServiceManageLocal service_local = EJBFactory.getServiceManage();
ServiceManageVO service_vo = new ServiceManageVO();
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
OperatorLocal operator_local = EJBFactory.getOperator();
OperatorVO operator_vo = new OperatorVO();
CustomerContactLocal local = EJBFactory.getCustomerContact(); 
CustomerContactVO vo = new CustomerContactVO();

Map dict_map = new HashMap();
Map service_map = new HashMap();
Map cust_map = new HashMap();
Map contact_map = new HashMap();
String title = enfo.crm.tools.LocalUtilis.language("message.new",clientLocale);
if(!contact_id.equals(new Integer(0)))
	title = enfo.crm.tools.LocalUtilis.language("message.edit",clientLocale);

Integer sex = Utility.parseInt(request.getParameter("sex"), new Integer(-1));
int subflag = Utility.parseInt(request.getParameter("subflag"), 0);
int receiveContactWay = 0;
int receiveServices = 0;

//�����ϵ����Ϣ
if (contact_id.intValue() != 0) {
	vo.setContactId(contact_id);
	vo.setCust_id(cust_id);
	vo.setReceiveContactWay(new Integer(0));
	vo.setReceiveService(new Integer(0));
	vo.setInsertMan(input_operatorCode);
	IPageList listPage = local.queryCustomerContact(vo,0,0);
	List list = listPage.getRsList();
	if (list.size() > 0) {
		contact_map = (Map)list.get(0);
		receiveContactWay = Utility.parseInt(Utility.trimNull(contact_map.get("ReceiveContactWay")),0);//�����ϵ��ѡ�еĽ��ܷ�ʽ�ܺ�
		receiveServices = Utility.parseInt(Utility.trimNull(contact_map.get("ReceiveServices")),0);//�����ϵ��ѡ�еķ����ܺ�
	}
}

//�����ϵ��ʽ
dict_vo.setType_id(new Integer(1109));
List dict_list = dict_local.listDictparamAll(dict_vo);
Iterator dict_it = dict_list.iterator();

//��÷������
service_vo.setSerial_no(new Integer(0));
service_vo.setServiceType(new Integer(0));
service_vo.setIsValid(new Integer(1));//���õ�
service_vo.setInputMan(new Integer(0));
List service_list = service_local.query_TServiceDefine(service_vo);
Iterator service_it = service_list.iterator();

//��ÿͻ���Ϣ
cust_vo.setCust_id(cust_id);
cust_vo.setInput_man(input_operatorCode);
List cust_list = cust_local.listProcAll(cust_vo);
if (! cust_list.isEmpty())
	cust_map = (Map)cust_list.get(0);

//��ÿͻ���Ϣ
String[] cust_info = new String[2];
if (contactType.intValue()==1)
	cust_info = Argument.getCustInfoById(cust_id);
else if(contactType.intValue()==2)
	cust_info = Argument.getPartnerInfoById(cust_id);	
else if(contactType.intValue()==3)
	cust_info = Argument.getChannelInfoById(cust_id);	

String cust_name = cust_info[0];
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<style>
.headdarkbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}

.headbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}
</style>
<script LANGUAGE="javascript">
function show(parm) { //��ǩѡ��
   for (var i=0; i<4; i++) {  
     if (i!= parm) {
        document.getElementById('d'+i).background = '<%=request.getContextPath()%>/images/headdark_00_01.gif';
        if (document.getElementById("r"+i)) 
			document.getElementById("r"+i).style.display = 'none';
	 } else {
	    document.getElementById('d'+i).background = '<%=request.getContextPath()%>/images/head_00_01.gif';
	    if (document.getElementById("r"+i))  
			document.getElementById("r"+i).style.display = '';
	 } 
  }
}

//������֤
function validateForm() {	
	if(document.getElementsByName("contactor")[0].value == "")
		show(0);
	if(!sl_check(document.getElementsByName("contactor")[0], '<%=LocalUtilis.language("class.contact",clientLocale)%> ', 60, 1))//��ϵ��
	{
		return false; 
	}
	syncDatePicker(theform.birthday_date_picker, theform.birthday);
	syncDatePicker(theform.anniversary_picker, theform.anniversary);
	return sl_check_update();
}

//����
function onSave(){
	if(validateForm()){
		document.theform.submit();
	}
}

window.onload = function(){
		document.getElementById("btnSave").attachEvent("onclick",onSave);
	};

//ѡ��δ��ʱ����������ż����
function checkMarried() {
	var item = document.getElementsByName("ismarried");
	for (var i=0; i<item.length; i++) {
		if (item[i].type=="radio" && item[i].checked) {	
			if (item[i].value == 1){
			    document.getElementById("spouse").disabled = false;
			} else {
				document.getElementById("spouse").value = "";
				document.getElementById("spouse").disabled = true;
			}
		}
	}
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" action="client_contact_new_do.jsp" method="post" id="theform">
<input type="hidden" name="contact_id" value="<%=contact_id%>">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type="hidden" name="contact_name" value="">
<input type="hidden" name="contactType" id="contactType" value="<%=contactType%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding=0>
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding=0>
					<tr>
						<!--��ϵ��-->
                        <td colspan=6 class="page-title page-title-noborder"><b>&nbsp;<%=Utility.trimNull(cust_name)%>-<%=title%><%=LocalUtilis.language("class.contact",clientLocale)%> </b></td>
					</tr>
				</table>
				<br>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0"
					class="edline">
					<TBODY>
						<TR class="tr-tabs">
							<td width="60%"></td>
							<TD id=d0 style="CURSOR: hand; background-repeat: no-repeat"
								onclick=show(0) vAlign=top height=20
								background='<%=request.getContextPath()%>/images/<%if (subflag == 0)
	out.print("head_00_01.gif");
else
	out.print("headdark_00_01.gif");%>' width="10%">&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.generalInfo",clientLocale)%> </TD><!--������Ϣ-->
							
							<TD id=d1 style="CURSOR: hand; background-repeat: no-repeat"
								onclick=show(1) vAlign=top height=20
								background='<%=request.getContextPath()%>/images/<%if (subflag == 1)
	out.print("head_00_01.gif");
else
	out.print("headdark_00_01.gif");%>' width="10%">&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.detailInfo",clientLocale)%> </TD><!--��ϸ��Ϣ-->
							<TD id=d2 style="CURSOR: hand; background-repeat: no-repeat"
								onclick=show(2) vAlign=top height=20
								background='<%=request.getContextPath()%>/images/<%if (subflag == 2)
	out.print("head_00_01.gif");
else
	out.print("headdark_00_01.gif");%>' width="10%">&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.messagementInfo",clientLocale)%> </TD><!--������Ϣ-->
					<TD id=d3 style="CURSOR: hand; background-repeat: no-repeat"
								onclick=show(3) vAlign=top height=20
								background='<%=request.getContextPath()%>/images/<%if (subflag == 3)
	out.print("head_00_01.gif");
else
	out.print("headdark_00_01.gif");%>' width="10%">&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.noteInfo",clientLocale)%> </TD><!--ע����Ϣ-->
							<TD vAlign=top width=20% align="right"></TD>
							<TD vAlign=top width=30%>
							</TD>
						</TR>


					</TBODY>
				</TABLE>

				</TD>
			</TR>
			<tr style="height: 10px;">
				<td></td>
			<tr>
			<TR id="r0" style="display:<%=subflag==0?"":"none"%>">
				<TD>
				<table cellSpacing=0 cellPadding=1 width="100%" border=0 class="table-popup">
					<tr>
						<td width="20%" align="right">*<%=LocalUtilis.language("class.contact",clientLocale)%> ��</td><!--��ϵ��-->
						<td width="20%"><input name="contactor" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Contactor"))%>"></td>
						<td align="right"  width="20%"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> ��</td><!--��ͥ�绰-->
						<td width="40%"><input name="phone_home" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("PhoneHome"))%>"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> ��</td><!--��˾�绰-->
						<td ><input name="phone_office" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("PhoneOffice"))%>"></td>
						<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> ��</td><!--�ֻ�-->
						<td><input name="mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Moblie"))%>"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> ��</td><!--����-->
						<td><input name="fax" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Fax"))%>"></td>
						<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> ��</td><!--��������-->
						<td><input name="zip_code" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("ZipCode"))%>"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> ��</td><!--�����ʼ�-->
						<td><input name="email" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Email"))%>"></td>
						<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> ��</td><!--�ʼĵ�ַ-->
						<td colspan="3"><input name="address" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="40" value="<%=Utility.trimNull(contact_map.get("Address"))%>"></td>
					</tr>
				</table>
				</TD>
			</TR>

			<TR id="r1" style="display:<%=subflag==1?"":"none"%>">
				<td>
				<table cellSpacing=0 cellPadding=1 width="100%" border=0 class="table-popup">
					<tr>
						<td width="10%" align="right"><%=LocalUtilis.language("class.sex",clientLocale)%> ��</td><!--�Ա�-->
						<td width="10%"><SELECT size="1" onkeydown="javascript:nextKeyPress(this)" name="sex" id="sex" style="WIDTH: 120px">
											<%=Argument.getSexOptions(Utility.parseInt(Utility.trimNull(contact_map.get("Sex")), new Integer(-1)))%>
										</SELECT></td>
						<td width="10%" align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> ��</td><!--��������-->
						<td width="20%">
							<input type="text" name="birthday_date_picker"
								class="selecttext"
							<%if (Utility.trimNull(contact_map.get("Birthday")).equals("0")||Utility.trimNull(contact_map.get("Birthday")).equals("")){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
							<%} else {%>
								value="<%=Format.formatDateLine(
										Utility.parseInt(Utility.trimNull(contact_map.get("Birthday")), 0))%>"
							<%}%>> <INPUT TYPE="button" value="��" class=selectbtn
							onclick="javascript:CalendarWebControl.show(theform.birthday_date_picker,theform.birthday_date_picker.value,this);"
							tabindex="13"> <INPUT TYPE="hidden" NAME="birthday" value="">
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.ismarried",clientLocale)%> ��</td><!--����״��-->
						<td>
							<INPUT type="radio" id="ismarried" onclick="javascript:checkMarried();" checked="checked" name="ismarried" value="0" <%if(Utility.trimNull(contact_map.get("IsMarried")).equals("0")) out.print("checked");%>>δ��
							<INPUT type="radio" id="ismarried" onclick="javascript:checkMarried();" name="ismarried" value="1" <%if(Utility.trimNull(contact_map.get("IsMarried")).equals("1")) out.print("checked");%>>�ѻ�
						</td>
						<td align="right"><%=LocalUtilis.language("class.anniversary",clientLocale)%> ��</td><!--������-->
						<td>
							<input type="text" name="anniversary_picker"
								class="selecttext"
							<%if (Utility.trimNull(contact_map.get("Anniversary")).equals("0")||Utility.trimNull(contact_map.get("Anniversary")).equals("")){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
							<%} else {%>
								value="<%=Format.formatDateLine(
										Utility.parseInt(Utility.trimNull(contact_map.get("Anniversary")), 0))%>"
							<%}%>> <INPUT TYPE="button" value="��" class=selectbtn
							onclick="javascript:CalendarWebControl.show(theform.anniversary_picker,theform.anniversary_picker.value,this);"
							tabindex="13"> <INPUT TYPE="hidden" NAME="anniversary" value="">
						</td>
						
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.spouse",clientLocale)%> ��</td><!--��ż����-->
						<td><input name="spouse" id="spouse" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("IsMarried")),0) == 0){out.print("disabled='true'");}%> onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Spouse"))%>"></td>
						<td align="right"><%=LocalUtilis.language("class.boss",clientLocale)%> ��</td><!--��˾-->
						<td>
							<select name="boss" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
								<%=Argument.getBossOption(cust_id,input_operatorCode,Utility.parseInt(Utility.trimNull(contact_map.get("Boss")),new Integer(0)))%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.department",clientLocale)%> ��</td><!--����-->
						<td><input name="department" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Department"))%>"></td>
						<td align="right"><%=LocalUtilis.language("class.duty",clientLocale)%> ��</td><!--ְ��-->
						<td><input name="duty" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Duty"))%>"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.country",clientLocale)%> ��</td><!--����-->
						<td><input name="country" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Country"))%>"></td>
						<td align="right"><%=LocalUtilis.language("class.province",clientLocale)%> ��</td><!--ʡ/��/������-->
						<td><input name="province" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Province"))%>"></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.city",clientLocale)%> ��</td><!--��/��-->
						<td colspan="3"><input name="city" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="50" value="<%=Utility.trimNull(contact_map.get("City"))%>"></td>
					</tr>
				</table>
				</td>
			</TR>

			<TR id="r2" style="display:<%=subflag==2?"":"none"%>">
				<td>
				<table cellSpacing=0 cellPadding=1 width="100%" border=0 class="table-popup">
					<tr>
						<td width="20%" align="right"><%=LocalUtilis.language("class.roleName",clientLocale)%> ��</td><!--��ɫ����-->
						<!--��ѡ��-->
                        <td width="20%">
							<select name="role" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
								<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%></option>
								<!--������-->
                                <option value="1" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("Role")),0) == 1) out.print("selected");%>><%=LocalUtilis.language("message.decisionMakers",clientLocale)%> </option>
								<!--Ա��-->
                                <option value="2" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("Role")),0) == 2) out.print("selected");%>><%=LocalUtilis.language("message.operator",clientLocale)%> </option>
								<!--Ӱ����-->
                                <option value="3" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("Role")),0) == 3) out.print("selected");%>><%=LocalUtilis.language("message.influencers",clientLocale)%> </option>
							</selelct>
						</td>						
						
						
					</tr>
					<tr>
						<td width="20%" align="right"><%=LocalUtilis.language("class.manager",clientLocale)%> ��</td><!--����-->
						<td width="20%"><input name="manager" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Manager"))%>"></td>
						<td width="20%" align="right"><%=LocalUtilis.language("class.managerTel",clientLocale)%> ��</td><!--����绰-->
						<td width="40%"><input name="managerTelphone" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("ManagerTelphone"))%>"></td>
					</tr>
					<tr>
						<td align="right" width="20%"><%=LocalUtilis.language("class.assistant",clientLocale)%> ��</td><!--����-->
						<td width="20%"><input name="assistant" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("Assistant"))%>"></td>
						<td align="right" width="20%"><%=LocalUtilis.language("class.assistantTele",clientLocale)%> ��</td><!--����绰-->
						<td width="40%"><input name="assistantTelphone" onkeydown="javascript:nextKeyPress(this)" maxlength="100" size="30" value="<%=Utility.trimNull(contact_map.get("AssistantTelphone"))%>"></td>
					</tr>
				</table>
				</td>
			</TR>
			<TR id="r3" style="display:<%=subflag==3?"":"none"%>">
				<td>
				<table cellSpacing=0 cellPadding=7 width="100%" border=0 class="table-popup">
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=7 width="100%" border=0>
							<tr>
								<td align="right"><%=LocalUtilis.language("class.contactWay",clientLocale)%> ��</td><!--��ѡ��ϵ��ʽ-->
								<td>
								<select name="contactWay">
									<%=Argument.getContactOption(new Integer(1109), Utility.trimNull(contact_map.get("ContactWay")))%>
								</select>
								</td>
								<td align="right"><%=LocalUtilis.language("class.preferredDate",clientLocale)%> ��</td><!--���ܷ�����ѡ��-->
								<td>
								<select name="preferredDate">
									<%=Argument.getWeekOption(Utility.parseInt(Utility.trimNull(contact_map.get("PreferredDate")),new Integer(-1))) %>
								</select>
								</td>
								<td align="right"><%=LocalUtilis.language("class.receiPreferredTime",clientLocale)%> ��</td><!--���ܷ�����ѡʱ��-->
								<td><select name="preferredTime"><option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
										<!--����-->
                                        <option value="1" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("PreferredTime")),0) == 1) out.print("selected");%>><%=LocalUtilis.language("class.preferredTime",clientLocale)%> </option>
										<!--����-->
                                        <option value="2" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("PreferredTime")),0) == 2) out.print("selected");%>><%=LocalUtilis.language("class.preferredTime2",clientLocale)%> </option>
										<!--����-->
                                        <option value="3" <%if(Utility.parseInt(Utility.trimNull(contact_map.get("PreferredTime")),0) == 3) out.print("selected");%>><%=LocalUtilis.language("class.preferredTime3",clientLocale)%> </option>
										</selelct></td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=7 width="100%" border=0>
								<tr>
									<td align="right"><%=LocalUtilis.language("class.receiveContactWay",clientLocale)%> ��</td><!--���ܵ���ϵ��ʽ-->
									<td>
										<%=Argument.getContactCheckBox(receiveContactWay)%>
									</td>
								</tr>
								<tr>
									<td  align="right"><%=LocalUtilis.language("class.receiveServices",clientLocale)%> ��</td><!--��ǰ�ͻ����յķ������-->
									<td>	
										<%=Argument.getServiceCheckBox(receiveServices)%>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</TR>
		</TABLE>
		<table border="0" width="100%">
			<tr>
				<td align="right">
					<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--����-->
					&nbsp;&nbsp;
					<button type="button"  class="xpbutton3" accessKey=c id="btnCancel"
							name="btnCancel"
							onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button><!--ȡ��-->
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
<script language="javascript" >
<%if (!"".equals(session_cust_name)){//session�������ݣ����form��ֵ%>
	//��form��ֵ
	document.theform.contactor.value='<%=Utility.trimNull((String)session.getAttribute("name"))%>';
	document.theform.birthday_date_picker.value='<%=Utility.trimNull((String)session.getAttribute("birth"))%>';
	document.theform.address.value='<%=Utility.trimNull((String)session.getAttribute("address"))%>';
	//���� �ͻ��Ա�
	<%String c_sex = Utility.trimNull((String)session.getAttribute("sex")); 
	  if ("��".equals(c_sex)){%>
		document.getElementById("sex").options[1].selected=true;
	<%}else if  ("Ů".equals(c_sex)){%>
		document.getElementById("sex").options[2].selected=true;
	<%}%>
<%}%>
</script>
</BODY>
</HTML>