<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,java.util.*," %>
<%@ include file="/includes/operator.inc" %>
<% 
MenuInfoLocal menu = EJBFactory.getMenuInfo();
menu_id = request.getParameter("menu_id");
/*menu.setMenu_id(Utility.trimNull(request.getParameter("menu_id")));
menu.setOperator(input_operatorCode);
menu.setLanguage(language);
*/
List list = menu.listMenuInfo(menu_id, languageFlag);
if (list!=null &&list.size()>0){
	Map map = (Map)list.get(0);
	session.setAttribute("menu_info",(String)map.get("MENU_INFO"));
	session.setAttribute("menu_url",Utility.trimNull(map.get("URL")));
}

session.setAttribute("menu_id",menu_id);
//session.setAttribute("menu_info",menu.getMenu_info());

menu.remove();
%>
