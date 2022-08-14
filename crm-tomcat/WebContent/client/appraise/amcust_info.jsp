<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
AmShareHolderLocal local = EJBFactory.getAmShareHolder();
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
Integer read_flag = Utility.parseInt(request.getParameter("read_flag"),new Integer(0));
Integer flag = Utility.parseInt(request.getParameter("flag"),new Integer(0));
boolean from_jh_cust = "jh_cust".equals(Utility.trimNull(request.getParameter("from")));
Integer signed_spot_flag = Utility.parseInt(request.getParameter("signed_spot_flag"),new Integer(0));
String sQueryUrl ="'aml_customer_list.jsp'";
String sQuery = Utility.trimNull(request.getParameter("iQuery"));
//String sQuery2 = Utility.trimNull(request.getParameter("iQuery2"));
Utility.debug("sQuery:"+sQuery);
if(!"".equals(sQuery)){
	java.util.Vector  temp = Utility.splitString2(sQuery,"$");
	sQueryUrl = "'aml_customer_list.jsp?cust_no="+temp.elementAt(0)+ "&cust_name="+temp.elementAt(1)+"&card_id="+temp.elementAt(2)
						+"&product_id="+temp.elementAt(3)+"&is_link="+temp.elementAt(4)+"&page="+temp.elementAt(5)+"&pagesize="+temp.elementAt(6)+"'";
} else if (from_jh_cust) {
	sQueryUrl = "'/client/clientinfo/customer_info_jh_modi.jsp?cust_id="+cust_id+"'";
}

List listAll = null;
Map map = null;
boolean bSuccess = false;
CustomerInfoVO vo = new CustomerInfoVO();
CustomerInfoLocal cust = EJBFactory.getCustomerInfo();

List amlist = null;
Map ammap = null;
AmCustInfoVO amvo = new AmCustInfoVO();
AmCustInfoLocal amCust = EJBFactory.getAmCustInfo(); 
CustomerLocal customer = EJBFactory.getCustomer();

String action = Utility.trimNull(request.getParameter("actionflag"));
Integer product_id = new Integer(0);
String cust_name = "";
String sCust_no = "";
String card_id = "";
Integer is_link = new Integer(0);
Integer cust_type = new Integer(0);
String card_type = "";
String cogc = "";
Integer card_valid_date = new Integer(0);
String country = "";
String voc_type = "";
String post_address = "";
String cust_tel = "";
String post_address2 = "";
String cbsc = "";
String jg_cust_type = "";
String fcName = Utility.trimNull(request.getParameter("fcName")); // ��ҵ��ʵ�ʿ����˵�����
String fcCardType = Utility.trimNull(request.getParameter("fcCardType"));
String fcCardId = Utility.trimNull(request.getParameter("fcCardId"));
Integer fcCardValidDate = Utility.parseInt(request.getParameter("fcCardValidDate"), new Integer(0));

Integer aml_serial_no = new Integer(0);
String cbsc1 = "";
String crft = "";
BigDecimal crfd = new BigDecimal(0);;
String ctrn = "";
String crnm = "";
String crit = "";
String crid = "";
Integer crvt = new Integer(0);
String pcnm = "";
String pitp = "";
String picd = "";
Integer pivt = new Integer(0);
Integer cogc_vd = new Integer(0);

if (request.getMethod().equals("POST"))
{
	amvo.setCust_id(Utility.parseInt(request.getParameter("cust_id"), new Integer(0)));
	amvo.setCbsc(Utility.trimNull(request.getParameter("cbsc")));
	amvo.setCrft(Utility.trimNull(request.getParameter("crft")));
	amvo.setCrfd(Utility.parseDecimal(request.getParameter("crfd"), new BigDecimal(0),2,"1"));
	amvo.setCtrn(Utility.trimNull(request.getParameter("ctrn")));
	amvo.setCrnm(Utility.trimNull(request.getParameter("crnm")));
	amvo.setCrit(Utility.trimNull(request.getParameter("crit")));
	amvo.setCrid(Utility.trimNull(request.getParameter("crid")));

	amvo.setCrvt(Utility.parseInt(request.getParameter("crvt"), new Integer(0)));
	amvo.setPcnm(Utility.trimNull(request.getParameter("pcnm")));
	amvo.setPitp(Utility.trimNull(request.getParameter("pitp")));
	amvo.setPicd(Utility.trimNull(request.getParameter("picd")));
	amvo.setPivt(Utility.parseInt(request.getParameter("pivt"), new Integer(0)));
	
	amvo.setInput_man(input_operatorCode);
	amvo.setCogc(Utility.trimNull(request.getParameter("cogc")));
	amvo.setCogc_vd(Utility.parseInt(request.getParameter("cogc_vd"), new Integer(0)));

	amvo.setCard_valid_date(Utility.parseInt(request.getParameter("card_valid_date"), new Integer(0)));
	amvo.setCountry(Utility.trimNull(request.getParameter("country")));
	amvo.setJg_cust_type(Utility.trimNull(request.getParameter("jg_cust_type")));
	
	amvo.setCard_type(Utility.trimNull(request.getParameter("card_type")));
	amvo.setCard_id(Utility.trimNull(request.getParameter("card_id")));
	amvo.setVoc_type(Utility.trimNull(request.getParameter("zyhy_type")));
	
	vo.setPost_address(Utility.trimNull(request.getParameter("post_address")));
	vo.setCust_tel(Utility.trimNull(request.getParameter("tel")));
	vo.setPost_address2(Utility.trimNull(request.getParameter("post_address2")));
	
	vo.setFcName(fcName);
	vo.setFcCardType(fcCardType);
	vo.setFcCardId(fcCardId);
	vo.setFcCardValidDate(fcCardValidDate);

	amvo.setInput_man(input_operatorCode);
	
	if(!action.equals("refresh"))
	{
		amCust.modi2(amvo, vo); // modi INTRUST..tcustomerinfo
		amCust.update(amvo); // add/modi INTRUST..TAmCustInfo
		customer.modi2(amvo, vo); // modi EFCRM..tcustomers
		bSuccess = true;

	}
}else{
	//��ѯ�ͻ���Ϣ
	if(cust_id.intValue() != 0){
		vo.setCust_id(cust_id);
		vo.setInput_man(input_operatorCode);
		listAll = cust.load(vo); // query INTRUST..tcustomerinfo
		map = (Map)listAll.get(0);

		if(map!= null){
			cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
			cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		    cust_name = Utility.trimNull(map.get("CUST_NAME"));
		    cust_tel = Utility.trimNull(map.get("CUST_TEL"));
		    card_id = Utility.trimNull(map.get("CARD_ID"));
		    is_link = Utility.parseInt(Utility.trimNull(map.get("IS_LINK")),new Integer(0));
		    card_type = Utility.trimNull(map.get("CARD_TYPE"));
		    //cogc = Utility.trimNull(map.get("COGC"));
			//cogc_vd = Utility.parseInt(Utility.trimNull(ammap.get("COGC_VD")),new Integer(0));
		    card_valid_date = Utility.parseInt(Utility.trimNull(map.get("CARD_VALID_DATE")),new Integer(0));
		    country = Utility.trimNull(map.get("COUNTRY"));
		    voc_type = Utility.trimNull(map.get("VOC_TYPE"));
		    post_address = Utility.trimNull(map.get("POST_ADDRESS"));
		    cust_tel = Utility.trimNull(map.get("CUST_TEL"));
		    post_address2 = Utility.trimNull(map.get("POST_ADDRESS2"));
		    cbsc = Utility.trimNull(map.get("CBSC"));
		    jg_cust_type = Utility.trimNull(map.get("JG_CUST_TYPE"));
			fcName = Utility.trimNull(map.get("FACT_CONTROLLER"));
			fcCardType = Utility.trimNull(map.get("FC_CARD_TYPE"));
			fcCardId = Utility.trimNull(map.get("FC_CARD_ID"));
			fcCardValidDate = Utility.parseInt((Integer)map.get("FC_CARD_VALID_DATE"), new Integer(0));
		}
	}
	if(cust_id.intValue() != 0)
	{
	    amvo.setCust_id(cust_id);
	    amvo.setInput_man(input_operatorCode);
		amlist = amCust.load(amvo); // query INTRUST..TAmCustInfo
		if (amlist!=null && amlist.size()>0)
		{
			ammap = (Map)amlist.get(0);
		}
		if(ammap!= null){
			aml_serial_no = Utility.parseInt(Utility.trimNull(ammap.get("SERIAL_NO")),new Integer(0));
			cbsc1 = Utility.trimNull(ammap.get("CBSC"));
			crft = Utility.trimNull(ammap.get("CRFT"));
			crfd = Utility.parseDecimal(Utility.trimNull(ammap.get("CRFD")), new BigDecimal(0),2,"1");
			ctrn = Utility.trimNull(ammap.get("CTRN"));
			crnm = Utility.trimNull(ammap.get("CRNM"));
			crit = Utility.trimNull(ammap.get("CRIT"));
			crid = Utility.trimNull(ammap.get("CRID"));
			crvt = Utility.parseInt(Utility.trimNull(ammap.get("CRVT")),new Integer(0));
			pcnm = Utility.trimNull(ammap.get("PCNM"));
			pitp = Utility.trimNull(ammap.get("PITP"));
			picd = Utility.trimNull(ammap.get("PICD"));
			pivt = Utility.parseInt(Utility.trimNull(ammap.get("PIVT")),new Integer(0));
			cogc = Utility.trimNull(ammap.get("COGC"));
			cogc_vd = Utility.parseInt(Utility.trimNull(ammap.get("COGC_VD")),new Integer(0));
		}
	}
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<STYLE> 
DIV#main{

}
DIV#maininfo{
	border:1px solid #808080;
}
DIV#detailinfo{
	border:1px dashed #808080;
}
</STYLE>
<title>������Ϣ�༭����</title>
<base target="_self">
<link href="/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT language="JavaScript">
<%if (bSuccess)
{
%>
	alert("����ɹ�");
	<%if(signed_spot_flag.intValue()==1){%>
		window.returnValue=1;
		window.close();
	<%}else{%>
		window.location.href='amcust_info.jsp?cust_id='+<%=cust_id%>;
		document.theform.actionflag.value = "refresh";	
<%}}%>
function checkInfo(){
	syncDatePicker(document.theform.card_valid_date_picker, document.theform.card_valid_date);
	if(!sl_checkDecimal(document.theform.crfd, "ע���ʽ�", 20, 3, 0))	return false;
	syncDatePicker(document.theform.crvt_picker, document.theform.crvt);
	syncDatePicker(document.theform.pivt_picker, document.theform.pivt);
}
//����֤���֤
function validateIDCard(IDCard,idtype)
{
	var intStrLen = IDCard.length;
	if((intStrLen != 15) && (intStrLen != 18) && (idtype==110801) ){
        return "���֤���ȱ���Ϊ15λ��18λ";
    }
    if(intStrLen == 18){
        //check date
        var date = IDCard.substring(6,14);
        var month = IDCard.substring(10,12);
        var day = IDCard.substring(12,14);
        if(month<1 || month>12){
            return "������֤����";
        }
        if(day<1 || day>31){
        	return "������֤����";
        }        
    }
    if(intStrLen == 15){
    	var date = IDCard.substring(6,12);
        var month = IDCard.substring(8,10);
        var day = IDCard.substring(10,12);
        if(month<1 || month>12){
            return "������֤����";
        }
        if(day<1 || day>31){
        	return "������֤����";
        }
    }
    return "1";
}

function checkIdcard(idtype,idcard,sfz_chk)
{	
	sfz_chk.innerHTML="";
	if(idtype!=110801) return false;
	var idcard,Y,JYM;
	var S,M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	var area={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}; 
	
	if(area[parseInt(idcard.substr(0,2))]==null) 
	{
	   sfz_chk.innerHTML = "���֤�����Ƿ�!"; 
	   return false;
	}
	
	//��ݺ���λ������ʽ����
	switch(idcard.length){
		
		case 15:
			if((parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
				ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//���Գ������ڵĺϷ���
			}else{
				ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//���Գ������ڵĺϷ���			
			}
			if(ereg.test(idcard)){
		    	sfz_chk.innerHTML = "��֤ͨ��15λ!"; 
		    	return false;
		    } else {
		     	sfz_chk.innerHTML = "���֤����������ڳ�����Χ���зǷ��ַ�!";
		      	return false;
		     }
			
			break;
		case 18:
			if(parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
			   ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//����������ڵĺϷ���������ʽ
			}else{
			   ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//ƽ��������ڵĺϷ���������ʽ
			}
			if(ereg.test(idcard)){//���Գ������ڵĺϷ���
    			//����У��λ	
				S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
				    + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
				    + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
				    + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
				    + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
				    + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
				    + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
				    + parseInt(idcard_array[7]) * 1 
				    + parseInt(idcard_array[8]) * 6
				    + parseInt(idcard_array[9]) * 3 ;
				Y = S % 11;
			    M = "F";
			    JYM = "10X98765432";
			    M = JYM.substr(Y,1);/*�ж�У��λ*/
			    if(M == idcard_array[17]){
			    	sfz_chk.innerHTML = "��֤ͨ��18λ!"; 
			     	return false; /*���ID��У��λ*/
			    }else {
			     	sfz_chk.innerHTML = "���֤����У�����!";
			     	return false;
			    }
		   }else {
		    	sfz_chk.innerHTML = "���֤����������ڳ�����Χ���зǷ��ַ�!";
		    	return false;
		   }
		   break;
		default:
			sfz_chk.innerHTML ="���֤��λ��������15λ����18λ";
			return false;
			break;
	}
}


function save(obj)
{	

	if(!sl_checkChoice(theform.card_type, "֤������"))	return false;
	
	//�ͻ���ݻ�����Ϣ
	if(document.theform.cust_type.value ==1 && document.theform.card_id.value != ""){
		var returnMassage = validateIDCard(document.theform.card_id.value);
		if(returnMassage != 1){
			sl_alert("�ͻ���ݻ�����Ϣ-֤�������У�" + returnMassage);
			return false;
		}
	}
	<%if(cust_type != null && cust_type.intValue() != 1){%>
		if(!sl_check(document.theform.crid,"����������֤������",18,6)) return false;
		if(!sl_check(document.theform.picd,"������֤����",18,6)) return false;
	<%}%>	
	if(document.theform.cust_type.value != 1){
		if(document.theform.cogc.value.length > 9){
			sl_alert("�ͻ���ݻ�����Ϣ�У���֯�������벻�ô���9λ����");
			return false;
		}
	}
	
	if(document.theform.card_valid_date.value != "" && document.theform.card_valid_date.value != "@I" && document.theform.card_valid_date.value != "@N"){
		if(document.theform.card_valid_date.value.length != 8){
			sl_alert("�ͻ���ݻ�����Ϣ��֤����Ч���ޱ���Ϊ8λ��������");
			return false;			
		}
	}
	if(!sl_checkChoice(theform.country, "����"))	return false;
	//������������Ϣ
	if(document.theform.cust_type.value != 1 && document.theform.crid.value != "" && document.theform.crit.value == '110801'){
		var returnMassage = validateIDCard(document.theform.crid.value);
		if(returnMassage != 1){
			sl_alert("������������Ϣ-֤�������У�" + returnMassage);
			return false;
		}
	}
	
	if(document.theform.cust_type.value != 1 && document.theform.crvt.value != "" && document.theform.crvt.value != "@I" && document.theform.crvt.value != "@N"){
		if(document.theform.crvt.value.length != 8){
			sl_alert("������������Ϣ��֤����Ч���ޱ���Ϊ8λ��������");
			return false;
	
		}
	}
	
	//��������Ϣ
	if(document.theform.cust_type.value != 1 && document.theform.picd.value != ""  && document.theform.crit.value == '110801'){
		var returnMassage = validateIDCard(document.theform.picd.value);
		if(returnMassage != 1){
			sl_alert("��������Ϣ-֤�������У�" + returnMassage);
			return false;
	
		}
	}
		
	if(document.theform.cust_type.value != 1 && document.theform.pivt.value != "" && document.theform.pivt.value != "@I" && document.theform.pivt.value != "@N"){
		if(document.theform.pivt.value.length != 8){
			sl_alert("��������Ϣ��֤����Ч���ޱ���Ϊ8λ��������");
			return false;
	
		}
	}

	if(confirm("ȷ��Ҫ������"))  {
		//document.theform.actionflag.value = "refresh";
	    document.theform.submit();
		//window.location.href='amcust_info.jsp?cust_id='+<%=cust_id%>;
		//document.theform.actionflag.value = "refresh";
	}
	else{
		document.theform.btnSave.disabled = false; 
	}
}
function newInfo(){
	if(showModalDialog('shareholders_update.jsp?cust_id='+<%=cust_id%>,'','dialogWidth:800px;dialogHeight:400px;status:0;help:0;scroll:0')!=null)
    {

		sl_update_ok();
		location.reload();
	}
}
function modiInfo(serial_no){
	if(showModalDialog('shareholders_update.jsp?cust_id='+<%=cust_id%>+'&serial_no='+serial_no,'','dialogWidth:500px;dialogHeight:400px;status:0;help:0;scroll:0')!=null)
   {
		sl_update_ok();
		location.reload();
	}
}
function remove(){
	if(confirmRemove(document.theform.serial_nos))
	{
		 disableAllBtn(true);
		 document.theform.action='amcust_shareholder_remove.jsp'; 
		 document.theform.submit();
	 }
}	 
</SCRIPT>
</head>
<body class="BODY" topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" action="amcust_info.jsp" method="post" >
<input type="hidden" name="actionflag">
<input type="hidden" name="cust_id" value=<%=cust_id%>>
<input type="hidden" name="cust_type" value=<%=cust_type%>>
<input type="hidden" name="aml_serial_no" value="<%=aml_serial_no%>">
<input type="hidden" name="flag" value=<%=flag%>>
<input type="hidden" name="signed_spot_flag" value=<%=signed_spot_flag%>>
<table border="0" width="96%" cellspacing="0" cellpadding="4" align="center">
	<tr>
		<td colspan=4><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr><td colspan="4" align="left">�ͻ���ݻ�����Ϣ</td></tr>
				<tr><td width="98%" colspan="4" align="center">
					<div id="detailinfo">
						<table width="98%">
						<tr>
							<td align="right" width="25%">����/���ƣ�</td>
							<td align="left" width="25%">
								<input class="edline" readonly size="35" name="cust_name" value="<%=cust_name %>">
							</td>
							<td align="right" width="25%">���ͣ�</td>
							<td align="left" width="25%">
								<select size="1" name="jg_cust_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 200px">
									<%=Argument.getJgCustType(jg_cust_type)%>	
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" width="25%">֤�����ͣ�</td>
							<td align="left" width="25%">
								<select size="1" name="card_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 200px">
									<%=Argument.getCardTypeJgOptions2(card_type)%>
								</select>
							</td>
							<td align="right" width="25%">֤�����룺</td>
							<td align="left" width="25%">
								<input  size="35" id="" name ="card_id" value="<%=Utility.trimNull(card_id)%>" >
							<%--if(cust_type != null && cust_type.intValue() == 2){%>
								<input  size="35" name ="cogc" value="<%=Utility.trimNull(cogc)%>" >
								<input  type="hidden" name ="card_id" value="<%=Utility.trimNull(card_id)%>" >
							<%}else{%>
								<input  size="35" id="" name ="card_id" value="<%=Utility.trimNull(card_id)%>" >
							<%}--%>
							</td>
						</tr>
						
						<tr>
							<td align="right" width="25%">֤����Ч���ޣ�</td>
							<td align="left" width="25%">
								<INPUT  size="35" name="card_valid_date" 
								<%if(card_valid_date == null || card_valid_date.intValue() == 0){%>
									value=""
								<%}else{%>
									value="<%=card_valid_date%>"
								<%}%>>
							</td>
							<td align="right" width="25%">������</td>
							<td align="left" width="25%">
								<select size="1" name="country" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 150px">
									<%=Argument.getCountry(country)%>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" width="25%">ְҵ��</td>
							<td align="left" width="25%">
								<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="zyhy_type" style="WIDTH: 238px" >
						   <% if(cust_type != null && cust_type.intValue()==1)  {%>
							  <%=Argument.getGrzyOptions2(voc_type)%>
							<%} else {%>
							   <%=Argument.getJghyOptions2(voc_type)%>
						   <%}%>	   
						</select>
							</td>
						</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr><td colspan="4" align="left">��ϵ��ʽ</td></tr>
	<tr>
		<td width="98%" colspan="4" align="center">
			<div id="detailinfo">
				<table width="98%">
				<tr>
					<td align="right" width="25%">��ַ��</td>
					<td align="left" width="25%">
						<input type="text" name="post_address" size="35" value="<%=post_address%>">
					</td>
					<td align="right" width="25%">��ϵ�绰��</td>
					<td align="left" width="25%">
						<input type="text" name="tel" size="35" value="<%=cust_tel%>">
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">������ϵ��ʽ��</td>
					<td align="left" width="25%">
						<input type="text" name="post_address2" size="35" value="<%=post_address2%>">
					</td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr><td colspan="4" align="left">�ͻ���Ӫ��Ϣ</td></tr>
	<tr>
		<td width="98%" colspan="4" align="center">
			<div id="detailinfo">
				<table width="98%">
				<tr>
					<td align="right" width="25%">�ͻ���Ӫ��Χ��</td>
					<td align="left" width="25%">
						<input type="text" name="cbsc" size="35" value="<%=cbsc1%>">
					</td>
					<td align="right" width="25%">ע���ʽ���֣�</td>
					<td align="left" width="25%">
						<select  style="width: 200" size="1" name="crft">
							<%=amCust.getCurrencyOptions(crft)%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">ע���ʽ�</td>
					<td align="left" width="25%">
						<input name="crfd" size="35" value="<%=crfd%>">
					</td>
					<td align="right" width="25%">˰��Ǽ�֤���룺</td>
					<td align="left" width="25%">
						<input type="text" name="ctrn" size="35" value="<%=ctrn %>">
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">��֯�������룺</td>
					<td align="left" width="25%">
						<input name="cogc" size="35" value="<%=cogc%>">
					</td>
					<td align="right" width="25%">��֯����֤����Ч���ڣ�</td>
					<td align="left" width="25%">
						<input type="text" name="cogc_vd" size="35" maxlength="8"
						<%if(cogc_vd.intValue()==0){%>
								value=""
						<%}else{%>
								value="<%=cogc_vd%>"
						<%}%>>
					</td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr><td colspan="4" align="left">������������Ϣ</td></tr>
	<tr>
		<td width="98%" colspan="4" align="center">
			<div id="detailinfo">
				<table width="98%">
				<tr>
					<td align="right" width="25%">������</td>
					<td align="left" width="25%">
						<input type="text" name="crnm" size="35" value="<%=crnm %>">
					</td>
					<td align="right" width="25%">֤�����ͣ�</td>
					<td align="left" width="25%">
						<select style="width: 200" size="1" name="crit" onchange="javascript:checkIdcard(this.value,crid.value,crid_chk);">
							<%=Argument.getCardTypeOptions2(crit)%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">֤�����룺</td>
					<td align="left" width="25%">
						<input type="text" name="crid" size="35" value="<%=crid%>" onchange="javascript:checkIdcard(crit.value,this.value,crid_chk);">
					</td>
					<td align="right" width="25%">֤����Ч���ޣ�</td>
					<td align="left" width="25%">
						<input type="text" name="crvt" size="35" maxlength="8"
						<%if(crvt != null && crvt.intValue()==0){%>
								value=""
						<%}else{%>
								value="<%=Utility.trimNull(crvt)%>"
						<%}%>>
					</td>
				</tr>
				<tr>
					<td colspan=""></td>
					<td><font color="#FF0000"><span id=crid_chk></span></font></td>
					<td colspan="2"></td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr><td colspan="4" align="left">��������Ϣ</td></tr>
	<tr>
		<td width="98%" colspan="4" align="center">
			<div id="detailinfo">
				<table width="98%">
				<tr>
					<td align="right" width="25%">������</td>
					<td align="left" width="25%">
						<input type="text" name="pcnm" size="35" value="<%=pcnm%>">
					</td>
					<td align="right" width="25%">֤�����ͣ�</td>
					<td align="left" width="25%">
						<select style="width: 200" size="1" name="pitp" onchange="javascript:checkIdcard(this.value,picd.value,picd_chk);">
							<%=Argument.getCardTypeOptions2(pitp)%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%">֤�����룺</td>
					<td align="left" width="25%">
						<input type="text" name="picd" size="35" value="<%=picd%>" onchange="javascript:checkIdcard(pitp.value,this.value,picd_chk);">
					</td>
					<td align="right" width="25%">֤����Ч���ޣ�</td>
					<td align="left" width="25%">
						<input type="text" name="pivt" size="35" maxlength="8"
						<%if(pivt != null && pivt.intValue()==0){%>
								value=""
						<%}else{%>
								value="<%=Utility.trimNull(pivt)%>"
						<%}%>>
						
					</td>
				</tr>
				<tr>
					<td colspan=""></td>
					<td><font color="#FF0000"><span id=picd_chk></span></font></td>
					<td colspan="2"></td>
				</tr>
				</table>
				</div>
			</td>
		</tr>
		<tr><td colspan="4" align="left">ʵ�ʿ�������Ϣ</td></tr>
			<tr>
				<td width="98%" colspan="4" align="center">
					<div id="detailinfo">
						<table width="98%">
						<tr>
							<td align="right" width="25%">������</td>
							<td align="left" width="25%">
								<input type="text" name="fcName" size="35" value="<%=fcName%>">
							</td>
							<td align="right" width="25%">֤�����ͣ�</td>
							<td align="left" width="25%">
								<select style="width: 200" size="1" name="fcCardType" onchange="javascript:checkIdcard(this.value,fcCardId.value,fc_chk);">
									<%=Argument.getCardTypeOptions2(fcCardType)%>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" width="25%">֤�����룺</td>
							<td align="left" width="25%">
								<input type="text" name="fcCardId" size="35" value="<%=fcCardId%>" onchange="javascript:checkIdcard(fcCardType.value,this.value,fc_chk);">
							</td>
							<td align="right" width="25%">֤����Ч���ޣ�</td>
							<td align="left" width="25%">
								<input type="text" name="fcCardValidDate" size="35" maxlength="8"
								<%if(fcCardValidDate.intValue()==0){%>
										value=""
								<%}else{%>
										value="<%=fcCardValidDate%>"
								<%}%>>
								
							</td>
						</tr>
						<tr>
							<td colspan=""></td>
							<td><font color="#FF0000"><span id=fc_chk></span></font></td>
							<td colspan="2"></td>
						</tr>
						</table>
						</div>
					</td>
				</tr>
	</table>
	<br>	
	<table border="0" width="100%">
		<tr>
			<td align="right">
			<%if (read_flag.intValue() != 1){%>
				<button type="button"  class="xpbutton3" accessKey=s  name="btnSave" onclick="javascript:save(this);">����(<u>S</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
			<%}%>
			<%if(signed_spot_flag.intValue()!=1){%>		
				<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" 
					onclick="javascript:location=<%=sQueryUrl%>">����(<u>B</u>)</button>
				&nbsp;&nbsp;
			<%}%>
			</td>
			<td align="right">
						
			</td>
		</tr>
		<tr>
			<td>
				
			</td>
		</tr>
	</table>
	<br>
	<TABLE cellSpacing=0 cellPadding=4 width="97%" align=center border=0>

		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<hr noshade color="#808080" size="1"><br>
						</td>
					</tr>	
					<tr>
						<td colspan=6><b>�ͻ��ɶ���Ϣ</b></td>
					</tr>
					<tr><td align=right> 
						<%if (read_flag.intValue() != 1){%>
						<button type="button"  class="xpbutton3" accessKey=n name="btnNew" title="�½�" onclick="javascript:newInfo();">�½�(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp; 

						<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="ɾ����ѡ��¼" onclick="javascript:remove();">ɾ��(<u>D</u>)</button>
						&nbsp;&nbsp;&nbsp;<%}%>
					</td></tr>		
				</table>
			<br>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<td><%if (read_flag.intValue() != 1){%>
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_nos,this);">
						<%}%>
					�ɶ�����</td>
					<td>�ɶ����֤������</td>
					<td>�ɶ����֤������</td>
					<td>�ɶ����֤����Ч����</td>
					<%if (read_flag.intValue() != 1){%><td>�༭</td><%}%>
				</tr>
				<!--�ӽ��������ʾ��¼-->
<%
AmlVO amlvo =	new  AmlVO();
amlvo.setCust_id(cust_id);
amlvo.setInput_man(input_operatorCode);
IPageList pageList = local.listAll(amlvo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
int iCount = 0;
int iCurrent = 0;
List rsList = pageList.getRsList();
Map rowMap = null;
String type_value = "";
Integer serial_no = new Integer(0);
for(int i=0;i<rsList.size();i++)
{
	
	rowMap = (Map)rsList.get(i);
	serial_no = Utility.parseInt(Utility.trimNull(rowMap.get("SERIAL_NO")),new Integer(0));
	String hdnm = Utility.trimNull(rowMap.get("HDNM"));
	String hitp = Utility.trimNull(rowMap.get("HITP"));
	String hdid = Utility.trimNull(rowMap.get("HDID"));
	Integer hivt = Utility.parseInt(Utility.trimNull(rowMap.get("HIVT")),new Integer(0));
%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td width="30%">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%">
								<%if (read_flag.intValue() != 1){%>
									<input type="checkbox" name="serial_nos" value="<%=serial_no%>" class="flatcheckbox">
								<%}%>
								</td>
								<td width="90%" align="left"><%=hdnm%></td>
							</tr>
						</table>
					</td>		
					<td align="center"><%=Argument.getCardTypeName(hitp)%></td>
					<td align="center"><%=hdid%></td>
					<td align="center"><%=Format.formatDateLine(hivt)%></td>		
					<%if (read_flag.intValue() != 1){%>
					<td align="center">
					<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:modiInfo(<%=serial_no%>);">&gt;&gt;</button>
					</td>	
					<%}%>
				</tr>
				
<%
iCurrent++;
iCount++;
}
%>
				<tr class="trbottom">	
					<td class="tdh" align="center"><b>�ϼ� <%=iCount%>��</b></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<%if (read_flag.intValue() != 1){%><td align="center" >-</td><%}%>
				</tr>
			</table>
			<br>	
	<table border="0" width="100%">
		<tr>
			<td align="right">
			
			</td>
			<td align="right">
						
			</td>
		</tr>
		
	</table>
			</TD>
		</TR>
	</TABLE>	
		

</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<%
cust.remove();
amCust.remove();
local.remove();
customer.remove();
%>