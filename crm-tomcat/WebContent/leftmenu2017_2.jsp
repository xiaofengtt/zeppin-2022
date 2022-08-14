<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/operator.inc" %>

<%
String menu_id2=request.getParameter("menu_id");
String parent_id="";
//if (parent_id==null) parent_id="";
enfo.crm.system.MenuInfoLocal menu = enfo.crm.tools.EJBFactory.getMenuInfo();
List list=menu.listMenuRight(input_operatorCode,menu_id2,parent_id,new Integer(0));
List list2=new ArrayList();
if (list==null || list.isEmpty()){
	return;
}
Map map=null;
String url="";
Integer bottom=new Integer(0);
for (int i=0;i<list.size();i++){
	map=(Map)list.get(i);
	if (map==null)
		continue;
	url=(String)map.get("URL");
%>
<li><a href="<%=url %>"><%=map.get("MENU_NAME") %></a>
<%
	bottom=(Integer)map.get("BOTTOM_FLAG");
	if (bottom!=null){
		if (bottom.intValue()==0){
			parent_id=(String)map.get("MENU_ID");
			list2=menu.listMenuRight(input_operatorCode,menu_id2,parent_id,new Integer(0));
			if (list2!=null && !list2.isEmpty()){
				out.print("<li><ul>");
				Map map2=null;
				for (int j=0;j<list2.size();j++){
					map2=(Map)list2.get(j);
					if (map2==null) continue;
					bottom=(Integer)map.get("BOTTOM_FLAG");
					url=(String)map2.get("URL");
					if (url==null) url="";
					if (url.indexOf("?")>0){
						url=url+"&menu_id="+(String)map2.get("MENU_ID");
					}else{
						url=url+"?menu_id="+(String)map2.get("MENU_ID");
					}
%>
					<li><a href="<%=url%>"><%=map2.get("MENU_NAME") %></a>
<%				}
				out.print("</ul>");
			}
			
		}
	}
}
%>