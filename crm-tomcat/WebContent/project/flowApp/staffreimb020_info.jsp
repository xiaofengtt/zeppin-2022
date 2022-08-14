<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ʼ��������ֵ
String interfaceType_code = "staffreimb020";
Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer("0"));
Integer object_id = Utility.parseInt(request.getParameter("object_id"), new Integer(0));
String object_type = Utility.trimNull(request.getParameter("object_type"));
String edit_right = Utility.trimNull(request.getParameter("edit_right"));
String showFlag = Utility.trimNull(request.getParameter("ShowFlag"));
ConfigLocal configLocal = EJBFactory.getConfig();


//ͨ��ҳ�������Ҹñ��Ӧ�������ֶ�
List catalogList = ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
boolean bSuccess = false;
//��ȡ����ID
String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));
List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//��ȡҳ�����е���ЧԪ�ؼ���
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//��ȡҳ���������ص���ЧԪ�ؼ���
if(identity_code.intValue() == 0){
 	fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"REIMBURSE_MAN","DEFAULT_FLAG","1");
	fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"REIMBURSE_MAN","DEFAULT_VALUE",String.valueOf(input_operatorCode));
	fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"DATE","DEFAULT_FLAG","1");
	fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"DATE","DEFAULT_VALUE",Utility.getCurrentDate2());
}
fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"REIMBURSE_MAN","VALUE_CONTENT","select cast(op_code as nvarchar),op_name from toperator where 1=1  and depart_id='"+depart_id+"' order by cast(op_code as nvarchar)");
fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"DEPART_PRODUCT","VALUE_CONTENT","select  item_id,item_name from DEPTITEM_INFO where item_id in (select item_id from deptitem_role where op_code='"+input_operatorCode+"')");
if(request.getMethod().equals("POST")){
	if(identity_code.intValue() == 0){//����
		ConfigUtil.addInfo(table_code,fieldAllList,request);//����ҳ������
	}else{
		ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//�޸�ҳ������
	}
	CRMBusinessCheck.getReimbSum(String.valueOf(object_id));
	bSuccess = true;
}

List modShowDataList = new ArrayList();//�޸�ҳ����ʾ����
if(identity_code.intValue() != 0){//�޸���ʾȡֵ
	modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
}
%>
<html>
<head>
<%if(identity_code.intValue() ==0){%>
<title>��������</title>
<%}else{%>
<title>�޸�����</title>
<%}%>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<STYLE type="text/css">
	.p {display: none}
	.s {display: inline}
	.k {color:red; display: inline}
</STYLE>

</head>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess ){%>
	window.returnValue = 1;
	window.close();
<%}%>

//----------�ύʱ������֤------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	var typeValue = document.getElementsByName('REIMBURSE_TYPE')[0].value;
	var custValue = document.getElementsByName('ISNEWCUST')[0].value;
	var itemValue = document.getElementsByName('PRODUCT_ID')[0].value;//���ż���Ŀ����
	var deptValue = document.getElementsByName('DEPART_PRODUCT')[0].value;//������Ŀ��
	var assuValue = document.getElementsByName('ASSUME')[0].value;//�е�
	var deveValue = document.getElementsByName('DEVELOPMENT')[0].value;//�г��ڿ�����ά��
	var invoices = document.getElementsByName('INVOICES')[0].value;
	var cityArea = document.getElementsByName('CITY_AREA')[0].value;
	if((typeValue =='310110')&&((cityArea == '') || (cityArea =='null'))){
		alert('��������С�����Ϊ�գ�');
		document.getElementsByName('CITY_AREA')[0].focus();
		return false;
	}
	if((typeValue !='310110'&& typeValue !='310101' && typeValue !='310115' )&&((invoices == '') || (invoices =='null'))){
		alert('����Ʊ���롿����Ϊ�գ�');
		document.getElementsByName('INVOICES')[0].focus();
		return false;
	}
	if(typeValue !='310110' && typeValue !='310101' && typeValue !='310115' && invoices.length !=8){
		alert('�����뷢Ʊ�����8λ����');
		document.getElementsByName('INVOICES')[0].focus();
		return false;
	}
	if((typeValue =='310107')&&((custValue == '') || (custValue =='null'))){//ҵ���д��� ���Ͽͻ���ѡ�����ż���Ŀ��ѡ
		alert('��ҵ���д��ѡ��¡����Ͽͻ�������Ϊ�գ�');
		document.getElementsByName('ISNEWCUST')[0].focus();
		return false;
	}
	if((typeValue =='310108')&&((custValue == '') || (custValue =='null'))){//����� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('������ѡ��¡����Ͽͻ�������Ϊ�գ�');
		document.getElementsByName('ISNEWCUST')[0].focus();
		return false;
	}
	if((typeValue =='310108')&&((assuValue == '') || (assuValue =='null'))){//����� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('������ѡ��¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310108')&&((itemValue == '') || (itemValue =='null'))&&((deptValue == '') || (deptValue =='null'))){//����� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('������ѡ��¡����ż���Ŀ���ơ���������Ŀ����2��ѡ1��');//�ų�����ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310108')&&(((itemValue != '') && (itemValue !='null'))&&((deptValue != '') && (deptValue !='null')))){//����� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('������ѡ��¡����ż���Ŀ���ơ���������Ŀ����ֻѡ����һ����');//�ų���ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((custValue == '') || (custValue =='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡����Ͽͻ�������Ϊ�գ�');
		document.getElementsByName('ISNEWCUST')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((assuValue == '') || (assuValue =='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((itemValue == '') || (itemValue =='null'))&&((deptValue == '') || (deptValue =='null'))&&((deveValue == '') || (deveValue =='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����3��ѡ1��');//����ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((itemValue != '') && (itemValue !='null'))&&((deptValue != '') && (deptValue !='null'))&&((deveValue != '') && (deveValue !='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//��ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((itemValue != '') && (itemValue !='null'))&&((deptValue != '') && (deptValue !='null'))&&((deveValue == '')||(deveValue =='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//ѡ����
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((itemValue != '') && (itemValue !='null'))&&((deptValue == '') || (deptValue =='null'))&&((deveValue != '') && (deveValue !='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//ѡ����
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310109')&&((itemValue == '') || (itemValue =='null'))&&((deptValue != '') && (deptValue !='null'))&&((deveValue != '') && (deveValue !='null'))){//��Ʒ�� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('����Ʒ�ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//ѡ����
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((custValue == '') || (custValue =='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡����Ͽͻ�������Ϊ�գ�');
		document.getElementsByName('ISNEWCUST')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((assuValue == '') || (assuValue =='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((itemValue == '') || (itemValue =='null'))&&((deptValue == '') || (deptValue =='null'))&&((deveValue == '') || (deveValue =='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����3��ѡ1��');//����ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((itemValue != '') && (itemValue !='null'))&&((deptValue != '')&& (deptValue !='null'))&&((deveValue != '') && (deveValue !='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//��ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((itemValue != '') && (itemValue !='null'))&&((deptValue != '')&& (deptValue !='null'))&&((deveValue == '') ||(deveValue =='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//ѡ����
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((itemValue != '') && (itemValue !='null'))&&((deptValue == '')|| (deptValue =='null'))&&((deveValue != '') && (deveValue !='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//ѡ����
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310110')&&((itemValue == '') ||(itemValue =='null'))&&((deptValue != '')&& (deptValue !='null'))&&((deveValue != '') && (deveValue !='null'))){//���÷� ���Ͽͻ���ѡ�����ż���Ŀ���ƣ�������Ŀ���� �г��� 3��ѡ1 �е���ѡ
		alert('�����÷ѡ��¡����ż���Ŀ���ơ���������Ŀ�������г��ڿ�����ά����ֻѡ����һ����');//ѡ����
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310111')&&((assuValue == '') || (assuValue =='null'))){//��ѯ�� ���ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('����ѯ�ѡ��¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310111')&&((itemValue == '') || (itemValue =='null'))&&((deptValue == '') || (deptValue =='null'))){///��ѯ�� ���ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('����ѯ�ѡ��¡����ż���Ŀ���ơ���������Ŀ����2��ѡ1��');//����ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310111')&&((itemValue != '') && (itemValue !='null'))&&((deptValue != '') && (deptValue !='null'))){///��ѯ�� ���ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('����ѯ�ѡ��¡����ż���Ŀ���ơ���������Ŀ����ֻѡ����һ����');//��ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310112')&&((assuValue == '') || (assuValue =='null'))){//ӡˢ�� ���ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('��ӡˢ�ѡ��¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310112')&&((itemValue == '') || (itemValue =='null'))&&((deptValue == '') || (deptValue =='null'))){///ӡˢ�� ���ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('��ӡˢ�ѡ��¡����ż���Ŀ���ơ���������Ŀ����2��ѡ1��');//����ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310112')&&((itemValue != '') && (itemValue !='null'))&&((deptValue != '') && (deptValue !='null'))){///ӡˢ�� ���ż���Ŀ���ƣ�������Ŀ���� 2��ѡ1 �е���ѡ
		alert('��ӡˢ�ѡ��¡����ż���Ŀ���ơ���������Ŀ����ֻѡ����һ����');//��ѡ
		document.getElementsByName('PRODUCT_ID')[0].focus();
		return false;
	}
	if((typeValue =='310113')&&((assuValue == '') || (assuValue =='null'))){//ͼ�� ���ż���Ŀ���ƣ��г��ڣ�2��ѡ1�����Ͽͻ����е���ѡ
		alert('��ͼ�顿�¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310113')&&((custValue == '') || (custValue =='null'))){//���Ͽͻ���ѡ
		alert('��ͼ�顿�¡����Ͽͻ�������Ϊ�գ�');
		document.getElementsByName('ISNEWCUST')[0].focus();
		return false;
	}
	if((typeValue =='310113')&&((deptValue == '') || (deptValue =='null'))&&((deveValue == '') || (deveValue =='null'))){///ͼ�� ���ż���Ŀ���ƣ��г��ڣ�2��ѡ1
		alert('��ͼ�顿�¡����ż���Ŀ���ơ����г��ڿ�����ά����2��ѡ1��');//����ѡ
		document.getElementsByName('DEPART_PRODUCT')[0].focus();
		return false;
	}
	if((typeValue =='310113')&&((deptValue != '') && (deptValue !='null'))&&((deveValue != '') && (deveValue !='null'))){///ͼ�� ���ż���Ŀ���ƣ��г��ڣ�2��ѡ1
		alert('��ͼ�顿�¡����ż���Ŀ���ơ����г��ڿ�����ά����ֻѡ����һ����');//��ѡ
		document.getElementsByName('DEPART_PRODUCT')[0].focus();
		return false;
	}
	if((typeValue =='310114')&&((assuValue == '') || (assuValue =='null'))){//ʳƷ ���ż���Ŀ���ƣ��г��ڣ�2��ѡ1���е���ѡ
		alert('��ʳƷ���¡��е�������Ϊ�գ�');
		document.getElementsByName('ASSUME')[0].focus();
		return false;
	}
	if((typeValue =='310114')&&((deptValue == '') || (deptValue =='null'))&&((deveValue == '') || (deveValue =='null'))){///ʳƷ�� ���ż���Ŀ���ƣ��г��ڿ�����ά�� 2��ѡ1 
		alert('��ʳƷ���¡����ż���Ŀ���ơ����г��ڿ�����ά����2��ѡ1��');//����ѡ
		document.getElementsByName('DEPART_PRODUCT')[0].focus();
		return false;
	}
	if((typeValue =='310114')&&((deptValue != '') && (deptValue !='null'))&&((deveValue != '') && (deveValue !='null'))){///ʳƷ�� ���ż���Ŀ���ƣ��г��ڿ�����ά�� 2��ѡ1 
		alert('��ʳƷ���¡����ż���Ŀ���ơ����г��ڿ�����ά����ֻѡ����һ����');//��ѡ
		document.getElementsByName('DEPART_PRODUCT')[0].focus();
		return false;
	}
	
	if(!(document.theform.INPUT_TIME ==undefined) && (<%=identity_code.intValue()==0%>)){//����ʱ��ֵ
		document.theform.INPUT_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	if(!(document.theform.INPUT_MAN ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_MAN.value='<%=input_operatorCode%>';
	}
    if(!(document.theform.INPUT_DEPT==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_DEPT.value='<%=depart_id%>';
	}
    if(!(document.theform.APPLY_ID==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.APPLY_ID.value='<%=object_id%>';
	}
	
	document.theform.submit();
}
//
function getChange(type){
	var typeValue = type.value;
	var isNewCust = document.getElementsByName('ISNEWCUST');
	isNewCust[0].parentNode.parentNode.className="p";
	var productId = document.getElementsByName('PRODUCT_ID');
	productId[0].parentNode.parentNode.className="p";
	var developme = document.getElementsByName('DEVELOPMENT');
	developme[0].parentNode.parentNode.className="p";
	var cityArea = document.getElementsByName('CITY_AREA');
	cityArea[0].parentNode.parentNode.className="p";
	var invoices = document.getElementsByName('INVOICES');
	var invoTr= invoices[0].parentNode.parentNode;
	var invoTds = invoTr.getElementsByTagName("td");
	var invoTd1 = invoTds[2];
	var invoTd2 = invoices[0].parentNode;
	invoTd1.className="s";
	invoTd2.className="s";	
	var tr1 = isNewCust[0].parentNode.parentNode;
	var td1 = tr1.firstElementChild || tr1.firstChild;
	td1.className="p";
	var td2 =isNewCust[0].parentNode;
	td2.className="p";
	var assume = document.getElementsByName('ASSUME');
	var trass = assume[0].parentNode.parentNode;
	var tdass1 = trass.getElementsByTagName("td");
	var tdass12= tdass1[2];
	var tdass2 = assume[0].parentNode;
	var ts1=document.getElementById("ts1");
	var ts2=document.getElementById("ts2");
	var ts3=document.getElementById("ts3");
	ts1.className="p";
	ts2.className="p";
	ts3.className="p";
	var productTr = productId[0].parentNode.parentNode;
	var productTd1 = productTr.firstElementChild || productTr.firstChild;
	productTd1.className="p";
	var productTd2 =productId[0].parentNode;
	productTd2.className="p";
	if('310107'==typeValue){//ҵ���д���
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		td1.className="s";
		td2.className="s";
		tdass12.className="p";
		tdass2.className="p";
	}else if('310108'==typeValue){//�����
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		td1.className="s";
		td2.className="s";
		ts1.className="s";
		productTd1.className="s";
		productTd2.className="s";
	}else if('310109'==typeValue){//��Ʒ
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		developme[0].parentNode.parentNode.className="s";
		td1.className="k";
		td2.className="s";
		ts2.className="s";
		productTd1.className="s";
		productTd2.className="s";
	}else if('310110'==typeValue){//���÷�
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		developme[0].parentNode.parentNode.className="s";
		cityArea[0].parentNode.parentNode.className="s";
		td1.className="k";
		td2.className="s";
		ts2.className="s";
		productTd1.className="s";
		productTd2.className="s";
		invoTd1.className="p";
		invoTd2.className="p";
	}else if('310111'==typeValue || '310112'==typeValue){//��ѯ��//ӡˢ��
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		ts1.className="s";
		productTd1.className="s";
		productTd2.className="s";
	}else if('310113'==typeValue){//ͼ��
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		developme[0].parentNode.parentNode.className="s";
		td1.className="s";
		td2.className="s";
		ts3.className="2";
	}else if('310114'==typeValue){//ʳƷ
		isNewCust[0].parentNode.parentNode.className="s";
		productId[0].parentNode.parentNode.className="s";
		developme[0].parentNode.parentNode.className="s";
		ts3.className="2";
	}
}

window.onload = function(){
	getChange(document.theform.REIMBURSE_TYPE);

}
//��Ʊ�������� ��ʾ

var invoic = document.getElementsByName('INVOICES');
document.body.onkeyup= function()
{		
	if (trim(invoic[0].value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(invoic[0].value) + " λ )";
}

</script>

<%
	//-----------------------��ʼ������-----------------------
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("bSuccess",new Boolean(bSuccess));
%>
<jsp:include page="/system/config/basicdetail_js.jsp"></jsp:include>

<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="interfacetype_code" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>
<input name="edit_right" type="hidden" value="<%=edit_right%>" />
<input name="object_type" type="hidden" value="<%=object_type%>" />
<input name="object_id" type="hidden" value="<%=object_id%>" />

<%
	request.setAttribute("fieldHideList",fieldHideList);
	request.setAttribute("modShowDataList",modShowDataList);
 %>
<jsp:include page="/system/config/basicdetail_hidden.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title">
				<font color="#215dc6"><b>���ñ�����ϸ��Ϣ</b></font>
			</td>
		</tr>
		
</table>
<br/>
<%
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("modShowDataList",modShowDataList);
%>
<jsp:include page="/system/config/basicdetail_show.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%">
		<tr class="tr-tabs">
			<td align="center" id="ts1"><font color="red" style="font-family:����;font-size:15;font-weight:">��ע:��������Ŀ���ơ������ż���Ŀ���ơ�2��ѡ1</font></td>
			<td align="center" id="ts2"><font color="red" style="font-family:����;font-size:15;font-weight:">��ע:���г��ڿ�����ά������������Ŀ���ơ������ż���Ŀ���ơ� 3��ѡ1</font></td>
			<td align="center" id="ts3"><font color="red" style="font-family:����;font-size:15;font-weight:">��ע:���г��ڿ�����ά���������ż���Ŀ���ơ� 2��ѡ1</font></td>
		</tr>
	<tr>
		<td align="right">
		<%if(edit_right.equals("yes")){%>
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">����(<u>S</u>)</button>
		&nbsp;&nbsp;
		<%} %>
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>

