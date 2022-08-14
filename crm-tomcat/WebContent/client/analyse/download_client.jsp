<%@ page language="java" contentType="text/csv; charset=GBK" import="enfo.crm.web.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String viewStr = "CUST_NO$CUST_NAME$CARD_ID$TOTAL_MONEY$EXCHANGE_AMOUNT$BACK_AMOUNT$BEN_AMOUNT$LAST_RG_DATE";
String tempView = Argument.getMyMenuViewStr(menu_id,input_operatorCode);
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}
String fields_s[][] = null;
Map fieldsMap = Argument.getMenuViewMap(menu_id,viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap();
	viewStr = "";
}else{
	//���û����úõ���ʾ�ֶ���Ϣ���ŵ�һ����ά������
	String[] fieldsArr =Utility.splitString(viewStr,"$");
	fields_s = new String[3][fieldsArr.length];
	int len=0;
	for(int j=0;j<fieldsArr.length;j++){
		Map map1 = (Map)fieldsMap.get(fieldsArr[j]);
		if (map1==null) continue;
		String field_name = Utility.trimNull(map1.get("FIELD_NAME"));
		String field_type = Utility.trimNull(map1.get("FIELD_TYPE"));
		if ("3".equals(field_type)) field_type="6";
		fields_s[0][len]=fieldsArr[j]; //�ֶ���
		fields_s[1][len]=field_name; //�ֶ���ʾ��
		fields_s[2][len]=field_type; //�ֶ�����
		len++;
	}
}
DocumentFile file = new DocumentFile(pageContext);
String fileName = "�ͻ���Ϣ";
file.exportExcelClientInfo(fileName,fields_s);
%>