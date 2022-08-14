<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%
	String menuId = Utility.trimNull(request.getParameter("menu_id"));
	String log = Utility.trimNull(request.getParameter("log"));

	//本地化信息
//	Integer languageFlag = Utility.parseInt(Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
//	String languageType = "zh_CN";
//	java.util.Locale clientLocale = null;
	
	//设置语言环境
	if(languageFlag.intValue()==0){
		clientLocale = request.getLocale();//得到本地化信息
		languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
		
		if(languageType.equals("zh_CN")){
			languageFlag = Integer.valueOf("1");
		}else{
			languageFlag = Integer.valueOf("2");
		}
	}else if(languageFlag.intValue()==1){
		clientLocale = new java.util.Locale("zh","CN");
		languageType = "zh_CN";
	}else if(languageFlag.intValue()==2){
		clientLocale = new java.util.Locale("en","US");
		languageType = "en_US";
	}
	
	String log0001 = Utility.trimNull(log);
	
	MenuInfoLocal menu = EJBFactory.getMenuInfo();
	
	List menuURLList = menu.listMenuInfo(menuId,languageFlag);
	Map menuURLMap = (Map)menuURLList.get(0);
	
	if(log0001.equals("1")){
		//LogListLocal logList = EJBFactory.getLogList();
		//logList.addLog(new Integer(91001),"点击菜单",input_operatorCode,Utility.trimNull(menuURLMap.get("MENU_NAME")));
	}
	
	String MENU_ID = Utility.trimNull(menuURLMap.get("MENU_ID"));
	String MENU_INFO = Utility.trimNull(menuURLMap.get("MENU_INFO"));
	String MENU_URL = Utility.trimNull(menuURLMap.get("MENU_URL"));
	
	session.setAttribute("menu_id",MENU_ID);
	session.setAttribute("menu_info",MENU_INFO);
	session.setAttribute("menu_url",MENU_URL);

	response.getWriter().write("1");
%>
<%menu.remove();%>