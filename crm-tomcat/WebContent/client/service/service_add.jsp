<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//����ҳ�����
input_bookCode = new Integer(1);//������ʱ����
boolean bSuccess = false;
//��ȡҳ�洫�ݱ���
Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")),new Integer(0));
Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
Integer cc_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cc_cust_id")),new Integer(0));
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
Integer cust_id = null;
if(cc_cust_id.intValue()>0){
	cust_id = cc_cust_id;
}
else{
	cust_id = customer_cust_id;
}
//��������

//--2.�ͻ���Ϣ
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
Integer card_type = new Integer(0);
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;
//------------------------------------
String card_id = "";
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
//------------------------------------
String h_card_id = "";
String h_cust_tel = "";
String h_o_tel = "";
String h_h_tel = "";
String h_mobile= "";
String h_e_mail = "";
String h_bp = "";
//������Ϣ
String serviceTime="";
String serviceInfo="";
String serviceSummary="";
String content = "";
String subject = "";
String step_flag = "";
String info_level = "";
Integer service_man2 = new Integer(0);
Integer executor = new Integer(0);
//��ȡ����
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
CustomerVO c_vo = new CustomerVO();
CustomerVO cust_vo = new CustomerVO();

CustServiceLogLocal custServiceLogLocal=EJBFactory.getCustServiceLog();
CustServiceLogVO custServiceLogVO=new CustServiceLogVO();
//�ͻ���Ϣ��ϸ
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	//�ͻ�����ֵ		
	c_vo.setCust_id(cust_id);
	c_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(c_vo);
			
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
	}

	if(map_cust!=null){	
		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		//������Ϣ
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		
		h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
		h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
		h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
		h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
		h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
		h_bp = Utility.trimNull(map_cust.get("H_BP"));
	}		
}

if(request.getMethod().equals("POST")){
	c_vo = new CustomerVO();
	//�ͻ���Ϣ���ݴ��
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(h_card_id);
	c_vo.setCard_type(card_type.toString());
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(h_mobile);
	c_vo.setE_mail(h_e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	
	//����ͻ������¼
	serviceTime=request.getParameter("serviceTime");
	serviceInfo=request.getParameter("serviceInfo");
	serviceSummary=request.getParameter("serviceSummary");
	content = request.getParameter("scontent");
	subject = request.getParameter("subject");
	step_flag = Utility.trimNull(request.getParameter("step_flag"),"0"); 
	info_level = Utility.trimNull(request.getParameter("info_level"),"0"); 
	executor = Utility.parseInt(Utility.trimNull(request.getParameter("executor")),new Integer(0));
	service_man2 = Utility.parseInt(Utility.trimNull(request.getParameter("service_man2")),new Integer(0));
	
	custServiceLogVO.setCustId(cust_id);
	custServiceLogVO.setCustNo(cust_no);
	custServiceLogVO.setCustName(cust_name);
	custServiceLogVO.setServiceTime(serviceTime);
	custServiceLogVO.setServiceInfo(serviceInfo);
	custServiceLogVO.setServiceSummary(serviceSummary);
	custServiceLogVO.setInputMan(input_operatorCode);
	custServiceLogVO.setSubject(subject);
	custServiceLogVO.setContent(content);
	custServiceLogVO.setStep_flag(step_flag);
	custServiceLogVO.setInfo_level(info_level);
	custServiceLogVO.setExecutor(executor);
	custServiceLogVO.setServiceMan(service_man2);
	custServiceLogLocal.append(custServiceLogVO);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakAdd",clientLocale)%> </TITLE>
<!--��ӿͻ������¼-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>

<script language=javascript>
/**���湦��
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="service_add.jsp";
		document.theform.method="post";
		document.theform.submit();
	}
}
*/
/*��֤����*/
	function validateForm(form){		
		if(!sl_check(document.theform.customer_cust_name, "<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> ",200,1)){return false;}//�ͻ����Ʋ���Ϊ��
		if(!sl_check(document.theform.serviceTime, "<%=LocalUtilis.language("class.serviceTime",clientLocale)%> ",200,1)){return false;}//ά��ʱ�䲻��Ϊ��
		if(!sl_check(document.theform.serviceInfo, "<%=LocalUtilis.language("class.ServiceProject",clientLocale)%> ",200,1)){return false;}//������Ŀ����Ϊ��
		if(!sl_checkChoice(form.step_flag, "������չ��ʽ"))	return false;
		if(!sl_checkChoice(form.info_level, "��Ϣ�ȼ�"))	return false;
		if(!sl_checkChoice(form.service_man2, "�ͻ�����"))	return false;
		if(!sl_checkChoice(form.executor, "ִ����"))	return false;
		return sl_check_update();	
	}
/*����*/
	function SaveAction(){
		if(document.theform.onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}
	}
/*����*/
function CancelAction(){
	location = 'service_list.jsp?sQuery=<%=sQuery%>' ;
}

/*ˢ��ҳ��*/
function refreshPage(){
	disableAllBtn(true);
	document.theform.action="service_add.jsp";
	document.theform.method="get";
	document.theform.submit();
}
/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2&prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	if (v != null){
		//showMarketingCustomer(prefix, v);
		document.theform.customer_cust_id.value =  v[7];
		refreshPage();
	}	
	
	return (v != null);
}


/*ҳ���ʼ��*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){		
		sl_alert("<%=LocalUtilis.language("message.serviceInfoSaveOK",clientLocale)%> ��");//�ͻ�ά����¼��ӳɹ�
		location = "service_list.jsp";
	}
}

</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="service_add.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--�����ɹ���־-->
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="sQuery" value="<%=sQuery%>"/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font><!--�ͻ�����>>�ͻ�ά����¼-->
	<hr noshade color="#808080" size="1">
</div>
<!--�ͻ�ѡ��-->
<div vAlign=top align=left>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
			<tr>
				<td align="left"> <b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td>	<!--�ͻ���Ϣ-->			
				<!--button class="xpbutton3" accessKey=e name="btnEdit" title="�ͻ���Ϣ" onclick="javascript:getTransactionCustomer2('customer','<!%=readonly%>','<!%=Argument.getSyscontrolValue("CUSTINFO")%>');"><!%=strButton%></button-->
				<td align="left">
					<button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.select2",clientLocale)%> 
					</button><!--�ͻ���Ϣ--><!--��ѡ��-->
				</td>
			<tr>
			<tr>
				<td align="right"><font color="red">*</font>   <%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--��ͥ�绰-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--��˾�绰-->
				<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
				</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--�ֻ�1-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--�ֻ�2-->
				<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=bp%>" size="20"></td>
			
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			
			<tr>
				<td colspan="4"><hr size="1"></td>
			</tr> 
		<tr>
			<td align="right"><font color="red">*</font> ������չ:</td>
			<td >
				<select size="1" name="step_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getDictParamOptions(1117,step_flag)%>
			  	</select>
			</td>							
			<td  align="right"><font color="red">*</font>��Ϣ�ȼ�:</td>
			<td colspan=3>
				<select size="1" name="info_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getDictParamOptions(1118,info_level)%>
				</select>
			</td>	
			</tr>
		<tr>
			<td align="right"><font color="red">*</font><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td>
			<td>
				<select name="service_man2" style="width: 110px;">
					<%=Argument.getManager_Value(service_man2)%>
				</select>
			</td>
			<td align="right"><font color="red">*</font>ִ���� :</td>
			<td>
				<select name="executor" style="width: 110px;">
					<%=Argument.getManager_Value(executor)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><font color="red">*</font> <%=LocalUtilis.language("class.serviceTime",clientLocale)%> :</td><!--ά��ʱ��-->
			<td ><input type="text" name="serviceTime" id="serviceTime" size="30" value="<%=serviceTime%>" onclick="javascript:setday(this);" readOnly/></td>
			<td align="right"><font color="red">*</font> <%=LocalUtilis.language("class.ServiceProject",clientLocale)%> :</td><!--������Ŀ-->
			<td ><INPUT type="text" name="serviceInfo" size="30" value="<%=serviceInfo%>"></td>
		</tr>
	
		<tr>						
			<td  align="right">��������:</td>
			<td colspan=5><input name="subject" maxlength="60" size="88" onkeydown="javascript:nextKeyPress(this)" value="<%=subject%>"></td>
		</tr>
		<tr>
			<td align="right" valign="top"><%=LocalUtilis.language("class.serviceSummary",clientLocale)%> :</td><!--������Ŀ��ϸ����-->
			<td colspan="3"><textarea rows="3" name="serviceSummary" cols="80"><%=serviceSummary%></textarea>
							<script type="text/javascript">
									var oFCKeditor = new FCKeditor( 'serviceSummary' ) ;
									oFCKeditor.BasePath = '/webEditor/' ;
									//oFCKeditor.ToolbarSet = 'Basic' ;
									oFCKeditor.Width = '90%' ;
									oFCKeditor.Height = '200' ;	
									oFCKeditor.ReplaceTextarea() ;										
							</script>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top">�������� :</td>
			<td colspan="3"><textarea rows="3" name="scontent" cols="80"><%=content%></textarea>
							<script type="text/javascript">
									var oFCKeditor = new FCKeditor( 'scontent' ) ;
									oFCKeditor.BasePath = '/webEditor/' ;
									//oFCKeditor.ToolbarSet = 'Basic' ;
									oFCKeditor.Width = '90%' ;
									oFCKeditor.Height = '200' ;	
									oFCKeditor.ReplaceTextarea() ;										
							</script>
			</td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--����-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
custServiceLogLocal.remove();
customer_inst.remove();
customer.remove();
%>