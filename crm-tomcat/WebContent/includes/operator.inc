<%
boolean isVPhone = true;

response.setContentType ("text/html;charset=GBK");
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
//���ػ���Ϣ
Integer languageFlag = enfo.crm.tools.Utility.parseInt(enfo.crm.tools.Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
String languageType = "auto";
java.util.Locale clientLocale = null;
String language = enfo.crm.tools.Utility.trimNull(session.getAttribute("LANGUAGE"),"0"); //��ǰ����
//�������Ի���
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//�õ����ػ���Ϣ
	languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
}
else if(languageFlag.intValue()==1){
	clientLocale = new java.util.Locale("zh","CN");
	languageType = "zh_CN";
}
else if(languageFlag.intValue()==2){
	clientLocale = new java.util.Locale("en","US");
	languageType = "en_US";
}

response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);

Integer capital_flag= new Integer(1);
String DB00001="1";
enfo.crm.system.InputMan input_operator = (enfo.crm.system.InputMan)session.getAttribute("OPERATOR");
Integer dbdriverflag = (Integer)session.getAttribute("DBDRIVER");
if(dbdriverflag!= null)
	DB00001 = dbdriverflag.toString();

Integer user_id= (Integer)application.getAttribute("USER_ID");
Integer projectAccessPower= (Integer)session.getAttribute("projectAccessPower");
int status=11;

if (input_operator == null)
    throw new Exception(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"��");//����Ա��δ��¼

String input_operatorName = input_operator.getOp_name();
if (input_operatorName == null)
	throw new Exception(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"��");//����Ա��δ��¼

Integer input_operatorCode = input_operator.getOp_code();
if (input_operatorCode == null)
	throw new Exception(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"��");//����Ա��δ��¼

Integer input_bookCode = input_operator.getBook_code();

String menu_id = (String)session.getAttribute("menu_id");
String menu_info= (String)session.getAttribute("menu_info");
String menu_url= (String)session.getAttribute("menu_url");
String work_condition= (String)session.getAttribute("work_condition");
if(work_condition==null) work_condition="";

Integer UNQUERY = enfo.crm.tools.Utility.parseInt(request.getParameter("UNQUERY"),new Integer(0));  //�Ƿ��ѯ
int menuIn = enfo.crm.tools.Utility.parseInt(request.getParameter("menuIn"),0);  //�Ƿ��ɲ˵��������
int IF_BTNCLICK_ADDLOG = 0; //ȫ�ֱ����������Ƿ�����ť��ʱ�򱣴���־��Ϣ
String scaption=enfo.crm.tools.LocalUtilis.language("message.query", clientLocale);//��ѯ
Integer capitalserialno = new Integer(0);

String cc_status = (String)session.getAttribute("cc_status");
cc_status = cc_status!=null ? cc_status : "0";
String cc_extension= (String)session.getAttribute("extension");
cc_extension = cc_extension!=null ? cc_extension : "";
String cc_recordExtension = (String)session.getAttribute("recordExtension");
cc_recordExtension = cc_recordExtension!=null ? cc_recordExtension : "";

//���ò�Ʒid
Integer session_product_id=(Integer)session.getAttribute("overall_product_id");
Integer overall_product_id = enfo.crm.tools.Utility.parseInt(request.getParameter("product_id"),session_product_id);
if(overall_product_id==null || overall_product_id.intValue()==0){
	Integer get_product_id=input_operator.getProductidByOpcode(input_operatorCode);
	session.setAttribute("overall_product_id",get_product_id);
	}

%>