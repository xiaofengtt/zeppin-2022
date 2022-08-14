<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
//��õ�ַ������
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer survey_date = Utility.parseInt(request.getParameter("survey_date"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String link_address = Utility.trimNull(request.getParameter("link_address"));
String link_phone = Utility.trimNull(request.getParameter("link_phone"));
String gr_nation = Utility.trimNull(request.getParameter("gr_nation"));
String profession = Utility.trimNull(request.getParameter("profession"));
String fz_card_type = Utility.trimNull(request.getParameter("fz_card_type"));
String fz_card_id = Utility.trimNull(request.getParameter("fz_card_id"));
Integer fz_card_yxq = Utility.parseInt(request.getParameter("fz_card_yxq"),new Integer(0));
String business = Utility.trimNull(request.getParameter("business"));
BigDecimal capital = Utility.parseDecimal(request.getParameter("capital"),new BigDecimal(0));
String swdjhm = Utility.trimNull(request.getParameter("swdjhm"));
String fr_name = Utility.trimNull(request.getParameter("fr_name"));
String gd_name = Utility.trimNull(request.getParameter("gd_name"));
String gd_card_type = Utility.trimNull(request.getParameter("gd_card_type"));
String gd_card_id = Utility.trimNull(request.getParameter("gd_card_id"));
Integer gd_card_yxq = Utility.parseInt(request.getParameter("gd_card_yxq"),new Integer(0));
String bl_name = Utility.trimNull(request.getParameter("bl_name"));
String bl_card_type = Utility.trimNull(request.getParameter("bl_card_type"));
String bl_card_id = Utility.trimNull(request.getParameter("bl_card_id"));
Integer bl_card_yxq = Utility.parseInt(request.getParameter("bl_card_yxq"),new Integer(0));
Integer khfxdj = Utility.parseInt(request.getParameter("khfxdj"),new Integer(0));


String[] cust_info = Argument.getCustInfoById(cust_id);

		
//��ö���
CustomerLocal customer_local = EJBFactory.getCustomer();
if (cust_id.intValue() != 0 && request.getMethod().equals("POST")){
	PropertySurveyVO vo = new PropertySurveyVO();
	vo.setCust_id(cust_id);
	vo.setSurvey_date(survey_date);
	vo.setLink_address(link_address);
	vo.setLink_phone(link_phone);
	vo.setGr_nation(gr_nation);
	vo.setProfession(profession);
	vo.setBusiness(business);
	vo.setCapital(capital);
	vo.setSwdjhm(swdjhm);
	vo.setFr_name(fr_name);
	vo.setFz_card_type(fz_card_type);
	vo.setFz_card_id(fz_card_id);
	vo.setFz_card_yxq(fz_card_yxq);
	vo.setGd_name(gd_name);
	vo.setGd_card_type(gd_card_type);
	vo.setGd_card_id(gd_card_id);
	vo.setGd_card_yxq(gd_card_yxq);
	vo.setBl_name(bl_name);
	vo.setBl_card_type(bl_card_type);
	vo.setBl_card_id(bl_card_id);
	vo.setBl_card_yxq(bl_card_yxq);
	vo.setKhfxdj(khfxdj);
	vo.setInput_man(input_operatorCode);
	customer_local.modiPropertySurvey(vo);
}
Map surveyMap = new HashMap();
if(cust_id.intValue() != 0){
	List list = customer_local.querySurveyByCustId(cust_id);
	if(list.size()==0){
	survey_date = null;
	fz_card_yxq = null;
	khfxdj = new Integer(0);
	gr_nation = null;
	}else{
		surveyMap = (Map)list.get(0);
		survey_date = (Integer)surveyMap.get("SURVEY_DATE");
		link_address = (String)surveyMap.get("LINK_ADDRESS");
		link_phone = (String)surveyMap.get("LINK_PHONE");
		gr_nation = (String)surveyMap.get("GR_NATION");
		profession = (String)surveyMap.get("PROFESSION");
		fz_card_type = (String)surveyMap.get("FZ_CARD_TYPE");
		fz_card_id = (String)surveyMap.get("FZ_CARD_ID");
		fz_card_yxq = (Integer)surveyMap.get("FZ_CARD_YXQ");
		business = (String)surveyMap.get("BUSINESS");
		capital = Utility.parseDecimal(Utility.trimNull(surveyMap.get("CAPITAL")),new BigDecimal(0));
		swdjhm = (String)surveyMap.get("SWDJZHM");
		fr_name = (String)surveyMap.get("FR_NAME");
		gd_name = (String)surveyMap.get("GD_NAME");
		gd_card_type = (String)surveyMap.get("GD_CARD_TYPE");
		gd_card_id = (String)surveyMap.get("GD_CARD_ID");
		gd_card_yxq = (Integer)surveyMap.get("GD_CARD_YXQ");
		bl_name = (String)surveyMap.get("BL_NAME");
		bl_card_type = (String)surveyMap.get("BL_CARD_TYPE");
		bl_card_id = (String)surveyMap.get("BL_CARD_ID");
		bl_card_yxq = (Integer)surveyMap.get("BL_CARD_YXQ");
		khfxdj = Utility.parseInt((Integer)surveyMap.get("KHFXDJ"),new Integer(0));
	}
}else{
	gr_nation = null;
	survey_date = null;
	fz_card_yxq = null;
	khfxdj = new Integer(0);
}
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
.tdc{
	text-align:center;
}
</style>
<script LANGUAGE="javascript">
//����
function onSave(form){
	if(validateForm(form)){
		document.theform.submit();
	}
}



function validateForm(form) {
	if (! sl_checkDate(document.theform.survey_date_picker,"����")) return false;
	syncDatePicker(document.theform.survey_date_picker, document.theform.survey_date);
	
	if (! sl_check(form.link_address, "��ϵ��ַ", 100, 1)) return false;//��ϵ��ַ
	if (!/^\d{6,18}$/.test(form.link_phone.value)) {//��ϵ�绰
		alert("��ϵ�绰������6��18λ������!");
		return false;
	}
	if(<%=cust_info[1]%>=='2'){
		syncDatePicker(form.gd_card_yxq_picker, form.gd_card_yxq);
		if(!sl_checkChoice(form.profession, "��ҵ"))	return false;//��ҵ
		if(!sl_checkChoice(form.business, "��Ӫ��Χ"))	return false;//��Ӫ��Χ
		if (! sl_checkDecimal(document.theform.capital,"ע���ʽ� ",13,2,0)) return false;//ע���ʽ�
		if (! sl_check(form.swdjhm, "˰��ǼǺ���", 100, 1)) return false;//˰��ǼǺ���
		if (! sl_check(form.fr_name, "���������ˣ������ˣ�����", 100, 1)) return false;//���������ˣ������ˣ�����
		if(!sl_checkChoice(form.fz_card_type, "���������ˣ������ˣ����֤������"))	return false;//���������ˣ������ˣ����֤������
		if(form.fz_card_type.value=='110801'){
			if (! checkCardId1(cTrim(document.theform.fz_card_id.value,0))) return false;
		}else{
			if (! sl_check(form.fz_card_id, "���������ˣ������ˣ����֤������", 100, 1)) return false;//���������ˣ������ˣ����֤������
		}
		if (! sl_checkDate(document.theform.fz_card_yxq_picker,"���������ˣ������ˣ����֤����Ч��")) return false;//���������ˣ������ˣ����֤����Ч��
		syncDatePicker(form.fz_card_yxq_picker, form.fz_card_yxq);
		if (! sl_check(form.gd_name, "�عɹɶ���ʵ�ʿ���������", 100, 1)) return false;//�عɹɶ���ʵ�ʿ���������
		if(!sl_checkChoice(form.gd_card_type, "�عɹɶ���ʵ�ʿ��������֤������"))	return false;//�عɹɶ���ʵ�ʿ��������֤������
		if(form.gd_card_type.value=='110801'){
			if (! checkCardId1(cTrim(document.theform.gd_card_id.value,0))) return false;
		}else{
			if (! sl_check(form.gd_card_id, "�عɹɶ���ʵ�ʿ��������֤������", 100, 1)) return false;//�عɹɶ���ʵ�ʿ��������֤������
		}
		if (! sl_checkDate(document.theform.gd_card_yxq_picker,"�عɹɶ���ʵ�ʿ��������֤����Ч��")) return false;//�عɹɶ���ʵ�ʿ��������֤����Ч��
		syncDatePicker(form.gd_card_yxq_picker, form.gd_card_yxq);
	}else{
		if(!sl_checkChoice(form.gr_nation, "����"))	return false;//����
		if(!sl_checkChoice(form.profession, "<%=LocalUtilis.language("class.profession",clientLocale)%> ")) return false;//ְҵ
		if(!sl_checkChoice(form.fz_card_type, "���֤������")) return false;//���֤������
		if(form.fz_card_type.value=='110801'){
			if (! checkCardId1(cTrim(document.theform.fz_card_id.value,0))) return false;
		}else{
			if (! sl_check(form.fz_card_id, "���֤������", 100, 1)) return false;//���֤������
		}
		if (! sl_checkDate(document.theform.fz_card_yxq_picker,"���֤����Ч��")) return false;//���֤����Ч��
		syncDatePicker(form.fz_card_yxq_picker, form.fz_card_yxq);
	}
	if (! sl_check(form.bl_name, "��Ȩҵ�����������", 100, 1)) return false;//��Ȩҵ�����������
	if(!sl_checkChoice(form.bl_card_type, "��Ȩҵ����������֤������")) return false;//��Ȩҵ����������֤������
	if(form.bl_card_type.value=='110801'){
		if (! checkCardId1(cTrim(document.theform.bl_card_id.value,0))) return false;
	}else{
		if (! sl_check(form.bl_card_id, "��Ȩҵ����������֤������", 100, 1)) return false;//��Ȩҵ����������֤������
	}
	if (! sl_checkDate(document.theform.bl_card_yxq_picker,"��Ȩҵ����������֤����Ч��")) return false;//��Ȩҵ����������֤����Ч��
	syncDatePicker(form.bl_card_yxq_picker, form.bl_card_yxq);
	if(!sl_checkChoice(form.khfxdj, "�ͻ����յȼ�"))	return false;//�ͻ����յȼ�
	return true;
}

function showAcctNum(value,i){
	if (trim(value) == "")
		document.getElementById("bank_acct_num"+i).innerText = "";
	else
		document.getElementById("bank_acct_num"+i).innerText = "(" + showLength(value) + " λ )";
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" action="cust_property_survey.jsp" method="post" id="theform">
	<input type="hidden" name="cust_id" value="<%=cust_id%>">
	<input type="hidden" name="contact_name" value="">
	<div class="page-title">
		<font color="#215dc6"><b>�ͻ����вƲ������</b></font>
	</div>
	<br/>
	<table width="100%">
		<tr>
			<td colspan="2" align="center">
			<%if("1".equals(cust_info[1])){%>
				<h1>�ͻ����вƲ������ (��Ȼ��)</h1><!--�ͻ����вƲ������ (��Ȼ��)-->
			<%}else{%>
				<h1>�ͻ����вƲ������ (����)</h1><!--�ͻ����вƲ������ (����)-->
			<%}%>
			</td>
		</tr>
		<tr>
			<td><!--����-->
				���ڣ�
				<INPUT TYPE="text"  NAME="survey_date_picker" class=selecttext <%if(survey_date==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(survey_date),new Integer(0)))%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
				<INPUT TYPE="button" value="��" class=selectbtn tabindex="13"
					onclick="javascript:CalendarWebControl.show(theform.survey_date_picker,theform.survey_date_picker.value,this);" />
				<INPUT TYPE="hidden" NAME="survey_date" value="" />
			</td>
			<td><!--������-->
				�����ˣ�<%=Argument.getOpNameByOpCode(input_operatorCode)%>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse;">
					<tr>
						<td class="td" colspan="2"><!--һ���ͻ�����/����-->
							<b>һ���ͻ�����/���ƣ�</b>
						</td>
					</tr>
					<tr>
						<td class="td" colspan="2">
							<%=cust_info[0]%>
						</td>
					</tr>
					<tr>
						<td class="td" colspan="2">
							<b>�����ͻ�������Ϣ��</b><!--�����ͻ�������Ϣ-->
						</td>
					</tr>
					<tr>
					<%if("1".equals(cust_info[1])){%>
						<td class="td" colspan="2">��һ����Ȼ�ˣ�</td><!--��һ����Ȼ��-->
					<%}else{%>
						<td class="td" colspan="2">��һ�����ˡ�������֯����幤�̻���</td><!--��һ����Ȼ��-->
					<%}%>
					</tr>
					<tr>
						<td class="tdc">1</td>
						<td class="td"><!--��ϵ��ַ-->
							��ϵ��ַ��<input type="text" name="link_address" value="<%=Utility.trimNull(link_address)%>"/>
						</td>
					</tr>
					<tr>
						<td class="tdc">2</td>
						<td class="td"><!--��ϵ�绰-->
							��ϵ�绰��<input type="text" name="link_phone" value="<%=Utility.trimNull(link_phone)%>"/>
						</td>
					</tr>
					<%if("1".equals(cust_info[1])){%>
					<tr>
						<td class="tdc">3</td>
						<td class="td"><!--����-->
							������
							<select size="1"  name="gr_nation"  id="gr_nation" style="width: 125px" onkeydown="javascript:nextKeyPress(this)">
								<%=Argument.getDictParamOptions(9997,Utility.trimNull(gr_nation,"9997CHN"))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">4</td>
						<td class="td"><!--ְҵ-->
							ְҵ��
							<select onkeydown="javascript:nextKeyPress(this)" name="profession" style="WIDTH: 150px">
								<%=Argument.getGrzyOptions2(Utility.trimNull(profession))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">5</td>
						<td class="td"><!--���֤������-->
							���֤�����ͣ�
							<select onkeydown="javascript:nextKeyPress(this)"name="fz_card_type" style="WIDTH: 150px">
								<%=Argument.getCardTypeOptions2(Utility.trimNull(fz_card_type))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">6</td>
						<td class="td"><!--���֤������-->
							���֤�����룺
							<INPUT name="fz_card_id"
								   onkeyup="javascript:showAcctNum(this.value,1)" 
								   value="<%=Utility.trimNull(fz_card_id)%>" size="25">
							<span id="bank_acct_num1" class="span"></span>
						</td>
					</tr>
					<tr>
						<td class="tdc">7</td>
						<td class="td"><!--���֤����Ч��-->
							���֤����Ч�ڣ�
							<INPUT TYPE="text"  NAME="fz_card_yxq_picker" class=selecttext <%if(fz_card_yxq==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(fz_card_yxq),new Integer(0)))%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
							<INPUT TYPE="button" value="��" class=selectbtn tabindex="13" onclick="javascript:CalendarWebControl.show(theform.fz_card_yxq_picker,theform.fz_card_yxq_picker.value,this);" />
							<INPUT TYPE="hidden" NAME="fz_card_yxq" value="" />
							<button type="button"   class="xpbutton3" onclick="javascript:document.theform.fz_card_yxq_picker.value='2500-01-01';">������Ч</button>
						</td>
					</tr>
					<%}else{%>
					<tr>
						<td class="tdc">3</td>
						<td class="td"><!--��ҵ-->
							��ҵ��
							<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="profession" style="WIDTH: 225px" >
								<%=Argument.getJghyOptions2(Utility.trimNull(profession))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">4</td>
						<td class="td"><!--��Ӫ��Χ-->
							��Ӫ��Χ��
							<select onkeydown="javascript:nextKeyPress(this)" name="business" style="WIDTH: 150px">
								<%=Argument.getGrzyOptions2(Utility.trimNull(business))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">5</td>
						<td class="td"><!--ע���ʽ�-->
							ע���ʽ�
							<input type="text" name="capital" value="<%=capital%>" />
						</td>
					</tr>
					<tr>
						<td class="tdc">6</td>
						<td class="td"><!--˰��ǼǺ���-->
							˰��ǼǺ��룺
							<input type="text" name="swdjhm" value="<%=Utility.trimNull(swdjhm)%>"/>
						</td>
					</tr>
					<tr>
						<td class="tdc">7</td>
						<td class="td"><!--����������(������)����-->
							���������ˣ������ˣ�������
							<input type="text" name="fr_name" value="<%=Utility.trimNull(fr_name)%>"/>
						</td>
					</tr>
					<tr>
						<td class="tdc">8</td>
						<td class="td"><!--���������ˣ������ˣ����֤������-->
							���������ˣ������ˣ����֤�����ͣ�
							<select onkeydown="javascript:nextKeyPress(this)"name="fz_card_type" style="WIDTH: 150px">
								<%=Argument.getCardTypeOptions2(Utility.trimNull(fz_card_type))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">9</td>
						<td class="td"><!--���������ˣ������ˣ����֤������-->
							���������ˣ������ˣ����֤�����룺
							<INPUT name="fz_card_id"
								   onkeyup="javascript:showAcctNum(this.value,2)" 
								   value="<%=Utility.trimNull(fz_card_id)%>" size="25">
							<span id="bank_acct_num2" class="span"></span>
						</td>
					</tr>
					<tr>
						<td class="tdc">10</td>
						<td class="td"><!--���������ˣ������ˣ����֤����Ч��-->
							���������ˣ������ˣ����֤����Ч�ڣ�
							<INPUT TYPE="text"  NAME="fz_card_yxq_picker" class=selecttext <%if(fz_card_yxq==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(fz_card_yxq),new Integer(0)))%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
							<INPUT TYPE="button" value="��" class=selectbtn tabindex="13" onclick="javascript:CalendarWebControl.show(theform.fz_card_yxq_picker,theform.fz_card_yxq_picker.value,this);" />
							<INPUT TYPE="hidden" NAME="fz_card_yxq" value="" />
							<button type="button"   class="xpbutton3" onclick="javascript:document.theform.fz_card_yxq_picker.value='2500-01-01';">������Ч</button>
						</td>
					</tr>
					<tr>
						<td class="tdc">11</td>
						<td class="td"><!--�عɹɶ���ʵ�ʿ���������/����-->
							�عɹɶ���ʵ�ʿ���������/���ƣ�
							<input type="text" name="gd_name" value="<%=Utility.trimNull(gd_name)%>"/>
						</td>
					</tr>
					<tr>
						<td class="tdc">12</td>
						<td class="td"><!--�عɹɶ���ʵ�ʿ��������֤������-->
							�عɹɶ���ʵ�ʿ��������֤�����ͣ�
							<select onkeydown="javascript:nextKeyPress(this)"name="gd_card_type" style="WIDTH: 150px">
								<%=Argument.getCardTypeOptions2(Utility.trimNull(gd_card_type))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">13</td>
						<td class="td"><!--�عɹɶ���ʵ�ʿ��������֤������-->
							�عɹɶ���ʵ�ʿ��������֤�����룺
							<INPUT name="gd_card_id"
								   onkeyup="javascript:showAcctNum(this.value,3)" 
								   value="<%=Utility.trimNull(gd_card_id)%>" size="25">
							<span id="bank_acct_num3" class="span"></span>
						</td>
					</tr>
					<tr>
						<td class="tdc">14</td>
						<td class="td"><!--�عɹɶ���ʵ�ʿ��������֤����Ч��-->
							�عɹɶ���ʵ�ʿ��������֤����Ч�ڣ�
							<INPUT TYPE="text"  NAME="gd_card_yxq_picker" class=selecttext <%if(gd_card_yxq==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(gd_card_yxq),new Integer(0)))%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
							<INPUT TYPE="button" value="��" class=selectbtn tabindex="13" onclick="javascript:CalendarWebControl.show(theform.gd_card_yxq_picker,theform.gd_card_yxq_picker.value,this);" />
							<INPUT TYPE="hidden" NAME="gd_card_yxq" value="" />
							<button type="button"   class="xpbutton3" onclick="javascript:document.theform.gd_card_yxq_picker.value='2500-01-01';">������Ч</button>
						</td>
					</tr>
					<%}%>
					<tr>
						<td class="td" colspan="2">��������Ȩҵ������ˣ�</td><!--��������Ȩҵ�������-->
					</tr>
					<tr>
						<td class="tdc">1</td>
						<td class="td"><!--����-->
							������
							<input type="text" name="bl_name" value="<%=Utility.trimNull(bl_name)%>"/>
						</td>
					</tr>
					<tr>
						<td class="tdc">2</td>
						<td class="td"><!--���֤������-->
							���֤�����ͣ�
							<select onkeydown="javascript:nextKeyPress(this)" name="bl_card_type" style="WIDTH: 150px">
								<%=Argument.getCardTypeOptions2(Utility.trimNull(bl_card_type))%>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdc">3</td>
						<td class="td"><!--���֤������-->
							���֤�����룺
							<INPUT name="bl_card_id"
								   onkeyup="javascript:showAcctNum(this.value,4)" 
								   value="<%=Utility.trimNull(bl_card_id)%>" size="25">
							<span id="bank_acct_num4" class="span"></span>
						</td>
					</tr>
					<tr>
						<td class="tdc">4</td>
						<td class="tdl"><!--���֤����Ч��-->
							���֤����Ч�ڣ�
							<INPUT TYPE="text"  NAME="bl_card_yxq_picker" class=selecttext <%if(bl_card_yxq==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(bl_card_yxq),new Integer(0)))%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
							<INPUT TYPE="button" value="��" class=selectbtn tabindex="13" onclick="javascript:CalendarWebControl.show(theform.bl_card_yxq_picker,theform.bl_card_yxq_picker.value,this);" />
							<INPUT TYPE="hidden" NAME="bl_card_yxq" value="" />
							<button type="button"   class="xpbutton3" onclick="javascript:document.theform.bl_card_yxq_picker.value='2500-01-01';">������Ч</button>
						</td>
					</tr>
					<tr>
						<td class="tdl" colspan="2">
							<b>�����ͻ����յȼ���</b><!--�����ͻ����յȼ�-->
							<select onkeydown="javascript:nextKeyPress(this)" name="khfxdj" style="WIDTH: 150px">
								<option value="">��ѡ��</option>
								<option value="1" <%if (khfxdj.intValue()==1) out.print("selected");%>>��</option>
								<option value="2" <%if (khfxdj.intValue()==2) out.print("selected");%>>��</option>
								<option value="3" <%if (khfxdj.intValue()==3) out.print("selected");%>>��</option>
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<div align="right" style="margin-right:5px;margin-top:5px;">
					<button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:onSave(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--����-->
					&nbsp;&nbsp;
					<button type="button"   class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>B</u>)
					</button><!--ȡ��-->
				</div>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>