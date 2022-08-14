<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<html>
<head>
<title>信托业务系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<link rel="stylesheet" href="<%=request.getContextPath()%>/includes/default.css" type="text/css">
<head>
	<style type="text/css">
	TD{	font-size: 10pt; 
		font-family: verdana,helvetica; 
		text-decoration: none;
		white-space:nowrap;}
	A{	text-decoration: none;
		color: black}
  
  BODY{
  overflow-y:   scroll;   
  overflow-x:   hidden;   
  SCROLLBAR-FACE-COLOR:   #DEDEDE;
  SCROLLBAR-HIGHLIGHT-COLOR:   #F5F5F5;
  SCROLLBAR-SHADOW-COLOR:   #EBF5FF;
  SCROLLBAR-3DLIGHT-COLOR:  #C3C3C3;
  SCROLLBAR-ARROW-COLOR:   black;
  SCROLLBAR-TRACK-COLOR:   #DEDEDE;
  SCROLLBAR-DARKSHADOW-COLOR:  #9D9D9D;
  SCROLLBAR-BASE-COLOR: black;
  }   
</style>
<!-- Code for browser detection -->
<script src="<%=request.getContextPath()%>/includes/JSpublic/treejs/ua.js"></script>

<!-- Infrastructure code for the tree -->
<script src="<%=request.getContextPath()%>/includes/JSpublic/treejs/ftiens4.js"></script>

<!-- Execution of the code that actually builds the specific tree.
     The variable foldersTree creates its structure with calls to 
	 gFld, insFld, and insDoc -->

</head>
<!--extanim32.gif-->
<body marginheight="26" class="MENU"> 
<div id="mlay" style="position:absolute;display:none;cursor:default;" onClick="return false;"></div>
<%-- <div id=tdcent style='position:relative;left:0;top:0'> 
  <div id="waitting" style="position:absolute; top:0%; left:0%; z-index:10; visibility:visible"> 
    <table width="160" border="0" cellspacing="2" cellpadding="0" height="70" bgcolor="0959AF">
      <tr> 
        <td bgcolor="#FFFFFF" align="center"> <table width="60" border="0" height="30">
            <tr> 
              <td valign="top" width="40"><img src="<%=request.getContextPath()%>/images/eextanim32.gif" width="32" height="32"></td>
              <td valign="top" class="g1">操作执行中，<br>
                请稍候... </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
</div> --%>
<div class="loading" id="waitting" >
<img src="<%=request.getContextPath()%>/images/eextanim32.gif" alt="" />
<p class="desc" id="waittingTitle">操作执行中，请稍后...</p>
</div>
	<form name = "menuForm">
            <!--隐藏框，用来保存选择的菜单的id值-->
            <input type = "hidden" name = "pass_menuid" value = "">
     </form>
	<div id="itemMenu" style="display:none">
           <table border="1" width="100%" height="100%" bgcolor="#cccccc" style="border:thin" cellspacing="0">
                  <tr>
                     <td width="120" style="cursor:default;border:outset 1;" align="center" onclick="parent.opmenu(1);"><font size=2>设置菜单夹</font></td>
                   </tr>
				 <tr><td width="120" style="cursor:default;border:outset 1;" align="center" onclick="parent.opmenu(2);"><font size=2>取消菜单夹</font></td>
				</tr>
                <tr><td width="120" style="cursor:default;border:outset 1;" align="center" onclick="parent.opmenu(0);"><font size=2>刷新</font></td>
                </tr>
                <tr><td width="100" style="cursor:default;border:outset 1;" align="center" onclick="parent.opmenu(5);"><font size=2>菜单重排位</font></td>
                </tr>
           </table>
    </div>
<script  language="javascript">
USETEXTLINKS = 1;
STARTALLOPEN = 0;
<%
String parent_id = request.getParameter("parent_id");
String rootCaption=" 我的主页";

if("1".equals(parent_id))
	rootCaption=" 系统配置管理";
	
if("2".equals(parent_id))
	rootCaption=" 投资理财";
	
if("3".equals(parent_id))
	rootCaption="固定资产";
	
if("8".equals(parent_id))
	rootCaption=" 总账财务";
	
if("9".equals(parent_id))
	rootCaption=" 报表管理";	
%>
foldersTree = gFld("<B><%=rootCaption%></B>", "  ");
foldersTree.iconSrc ="<%=request.getContextPath()%>/images/homepage.gif";
<%
MenuInfoLocal menu = EJBFactory.getMenuInfo();
MenuInfoLocal menu1 = EJBFactory.getMenuInfo();
MenuInfoLocal menu2 = EJBFactory.getMenuInfo();

List menuList = null;
Map menuMap = null;

List menuList1 = null;
Map menuMap1 = null;

List menuList2 = null;
Map menuMap2 = null;

menuList = menu.listMenuRight(input_operatorCode,parent_id,"",languageFlag);

System.out.println("-------sdfsafsfsdfsdf---:"+menuList.size());

for(int i=0;i<menuList.size();i++)
{
	menuMap = (Map)menuList.get(i);
	if(Utility.trimNull(menuMap.get("BOTTOM_FLAG")).equals("0"))
	{	
	%>
	
		aux1 = insFld(foldersTree, gFld("<%=Utility.trimNull(menuMap.get("MENU_NAME"))%>","javascript:parent.op()"));
		aux1.iconSrc ="<%=request.getContextPath()%>/images/folder-open.gif";
		aux1.iconSrcClosed ="<%=request.getContextPath()%>/images/folder.gif";
		<%
		menuList1 = menu1.listMenuRight(input_operatorCode,"",Utility.trimNull(menuMap.get("MENU_ID")),languageFlag);
		for(int j=0;j<menuList1.size();j++)
		{
			menuMap1 = (Map)menuList1.get(j);
			if(Utility.trimNull(menuMap1.get("BOTTOM_FLAG")).equals("0"))
			{
	%>
				aux2 = insFld(aux1, gFld("<%=Utility.trimNull(menuMap1.get("MENU_NAME"))%>","javascript:parent.op()"));
				aux2.iconSrc ="<%=request.getContextPath()%>/images/folder-open.gif";
				aux2.iconSrcClosed ="<%=request.getContextPath()%>/images/folder.gif";
<%				
				menuList2 = menu2.listMenuRight(input_operatorCode,"",Utility.trimNull(menuMap1.get("MENU_ID")),languageFlag);
				for(int k=0;k<menuList2.size();k++)
				{
					menuMap2 = (Map)menuList2.get(k);
					%>
					docAux = insDoc(aux2, gLnk("R", "<%=Utility.trimNull(menuMap2.get("MENU_NAME"))%>", "/addlog.jsp?menu_id=<%=Utility.trimNull(menuMap2.get("MENU_ID"))%>&catalog_name=<%=Utility.trimNull(menuMap2.get("CATALOG_NAME"))%>&menu_name=<%=Utility.trimNull(menuMap2.get("MENU_NAME"))%>&alias_name=<%=Utility.trimNull(menuMap2.get("ALIAS_NAME"))%>&list_id=<%=Utility.trimNull(menuMap1.get("LIST_ID"))%>&bottom_flag=1"))
      				docAux.iconSrc ="<%=request.getContextPath()%>/images/notify_new.gif"
<%				}	
			}
			else if(Utility.trimNull(menuMap1.get("PARENT_ID")).equals(Utility.trimNull(menuMap.get("MENU_ID"))))
			{
			%>
				docAux = insDoc(aux1, gLnk("R", "<%=Utility.trimNull(menuMap1.get("MENU_NAME"))%>", "<%=request.getContextPath()%>/addlog.jsp?menu_id=<%=Utility.trimNull(menuMap1.get("MENU_ID"))%>&catalog_name=<%=Utility.trimNull(menuMap1.get("CATALOG_NAME"))%>&menu_name=<%=Utility.trimNull(menuMap1.get("MENU_NAME"))%>&alias_name=<%=Utility.trimNull(menuMap1.get("ALIAS_NAME"))%>&list_id=<%=Utility.trimNull(menuMap1.get("LIST_ID"))%>&bottom_flag=1"))
      			docAux.iconSrc ="<%=request.getContextPath()%>/images/notify_new.gif"
<%			}	
		}
	}
}

%>
</script>
<script>
 waitting.style.visibility="hidden";
</script>
<!-- By making any changes to this code you are violating your user agreement.
     Corporate users or any others that want to remove the link should check 
	 the online FAQ for instructions on how to obtain a version without the link -->
<!-- Removing this link will make the script stop from working -->
<div style="position:absolute; top:0; left:0; "><table border=0><tr><td><font size=-2><a style="font-size:7pt;text-decoration:none;color:white" href=http://www.treeview.net/treemenu/userhelp.asp target=_top></a></font></td></table></div>

<!-- Build the browser's objects and display default view of the 
     tree. -->
<script>

initializeDocument()
</script>
</html>
<%menu2.remove();
  menu1.remove();
  menu.remove();
 %>


