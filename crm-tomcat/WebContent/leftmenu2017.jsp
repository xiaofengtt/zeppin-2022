<%@ include file="/includes/operator2.inc" %>
<%
String op_code=request.getParameter("op_code");
String menu_id2=request.getParameter("menu_id");
String parent_id=request.getParameter("parent_id");
if (parent_id==null) parent_id="";
//enfo.crm.service.MenuService ms=new enfo.crm.service.MenuService(); 
//String ret= ms.getMenuList(input_operatorCode,menu_id2,parent_id,new Integer(0));
enfo.crm.system.MenuInfoLocal menu = enfo.crm.tools.EJBFactory.getMenuInfo();
java.util.List list=(java.util.List)menu.listMenuRight(input_operatorCode,menu_id2,parent_id,new Integer(0));
/*if (list!=null){
	java.util.Map map=null;
	String url="";
	for (int i=0;i<list.size();i++){
		map=(java.util.Map)list.get(i);
		url=(String)map.get("URL");
		if (url==null || "".equals(url)) continue;
		if (url.indexOf("?")>0){
			url=url+"&menu_id="+(String)map.get("MENU_ID");
		}else{
			url=url+"?menu_id="+(String)map.get("MENU_ID");
		}
		map.put("URL",url);
	}
}*/
String ret=enfo.crm.util.JsonUtil.object2json(list);
%> 
<%=ret %>
