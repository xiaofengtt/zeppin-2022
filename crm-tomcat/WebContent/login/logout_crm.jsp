
<%
Integer languageFlag = enfo.crm.tools.Utility.parseInt(enfo.crm.tools.Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
String languageType = "auto";
java.util.Locale clientLocale = null;
String language = enfo.crm.tools.Utility.trimNull(session.getAttribute("LANGUAGE"),"0"); //当前语言
//设置语言环境
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//得到本地化信息
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
enfo.crm.system.InputMan input_operator = (enfo.crm.system.InputMan)session.getAttribute("OPERATOR");
if (input_operator == null)
    throw new Exception(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"！");

String input_operatorName = input_operator.getOp_name();
if (input_operatorName == null)
	throw new Exception(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"！");

Integer input_operatorCode = input_operator.getOp_code();
if (input_operatorCode == null)
	throw new Exception(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"！");

String ret= "1";
try{
session.removeAttribute("OPERATOR");
enfo.crm.system.OperatorLocal op = enfo.crm.tools.EJBFactory.getOperator();
op.logOut(input_operatorCode);
}catch(Exception e){
	ret= "0";
}
%>
<%=ret %>