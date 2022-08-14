<%@ page contentType="text/html; charset=GBK" import="enfo.crm.project.*,enfo.crm.vo.*, enfo.crm.system.*,enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*,enfo.crm.system.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
	String regionCode = Utility.trimNull(request.getParameter("RegionCode"));
	String showCount = Utility.trimNull(request.getParameter("ShowCount"));
	String addCount = Utility.trimNull(request.getParameter("AddCount"));
	String redCount = Utility.trimNull(request.getParameter("RedCount"));
	String actionFlag = Utility.trimNull(request.getParameter("ActionFlag"));
	//System.out.println("addCount:"+addCount+"<br>");
	//System.out.println("redCount:"+redCount+"<br>");
	if("".equals(actionFlag)){
		if("".equals(addCount)){
		 	addCount="0";
		}else if(Integer.parseInt(addCount)>0){
			if("".equals(redCount)){
				redCount="0";
			}else if(Integer.parseInt(redCount)>0){
				addCount =String.valueOf(Integer.parseInt(addCount)-1);
			}
			if("0".equals(redCount)){
				addCount =String.valueOf(Integer.parseInt(addCount)+1);
			}
		}
	}
	if("".equals(showCount)) showCount="5";
	if("".equals(regionCode)) regionCode="";
	//System.out.println("addCount1:"+addCount+"<br>");
	//System.out.println("redCount1:"+redCount+"<br>");		
	ConfigLocal configLocal = EJBFactory.getConfig();
	if("delete".equals(actionFlag)){
		if(request.getMethod().equals("POST")){//删除信息
			String[] s = request.getParameterValues("regionID");
			if(s.length>0){
				ConfigUtil.delInfo(configLocal,s,"CONFIG_REGION","REGION_ID");
			}
		}	
	}	
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/includes/default.css"></link>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/index.css"></link>	
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/prototype.lite.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/moo.fx.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/accordion.js"></script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="POST" action="">
<input type="hidden" name="AddCount" value="<%=addCount%>" />
<input type="hidden" name="RedCount" value="<%=redCount%>" />
<input type="hidden" name="ActionFlag" value="<%=actionFlag%>" />
<input type="hidden" name="regionID" value="" />
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>	
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						设置代码：<input type="text" size="20" name="RegionCode" value="<%=regionCode%>" onclick="javascript:queryInfo();">&nbsp;&nbsp;&nbsp; 
						设置个数：<input type="text" size="3" name="ShowCount" value="<%=showCount%>" onclick="javascript:configInfo();">&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=n id="btnNew" name="btnNew" title="新增一个View" onclick="javascript:newInfo();">新增一个View(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" accessKey=l id="btnDel" name="btnDel" title="删除一个View" onclick="javascript:deleteInfo();">删除一个View(<u>L</u>)</button>
						&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=t id="btnDel" name="btnDel" title="删除对应View" onclick="javascript:delInfo();">删除对应View(<u>T</u>)</button>
						&nbsp;&nbsp;&nbsp; 	
					</td>	
				</tr>	
				<tr>&nbsp;&nbsp;&nbsp;</tr>					
			</table>
	    </TD>
	    </TR>			
	</TABLE>
	</TD>
	</TR>
</TABLE>
<br>
<body >
	<div id="home-column-left" style="margin 0 0 0 0;padding-top: 7px;padding-left: 7px;top: 0px;"></div>
</body>
<script language="javascript">
	Ext.onReady(function(){
		Ext.BLANK_IMAGE_URL = "/images/s.gif";
		
    	var Tree = Ext.tree;
    
    	var tree = new Tree.TreePanel('home-column-left', { 
    	    animate: true,// 展开,收缩动画
        	enableDD:true,
        	border: true,
        	rootVisible: true,//根节点是否可见
        	containerScroll: true
        	
    	});
    	var root = new Ext.tree.TreeNode({
       		id : 'root',
       		text : '菜单目录', 
       		draggable:true,
	        collapsible : true//允许展开和收缩
  		});
  		
<%
	String codeName="",showmenu_id="",menu_name="";
	String regionName="";
	String sql=" select * from CONFIG_REGION where Region_Code= '"+regionCode+"' order by order_no";
	List menuList = configLocal.showSelectInfo("sql","",sql);
	String[][] ShowViewContent = new String[menuList.size()][6];
	//初始化显示菜单信息
	for(int i=0;i<menuList.size();i++)
	{
		Map menuMap = (Map)menuList.get(i);
		showmenu_id=Utility.trimNull(menuMap.get("ITEM_CODE"));
		ShowViewContent[i][0]=showmenu_id;
		ShowViewContent[i][1]=Utility.trimNull(menuMap.get("ITEM_NAME"));
		ShowViewContent[i][4]=Utility.trimNull(menuMap.get("REGION_ID"));
		regionName=Utility.trimNull(menuMap.get("REGION_NAME"));
		if(showmenu_id.length()==3){
			ShowViewContent[i][3]="root";     //继承上级节点编号
		}else if(showmenu_id.length()==6){
			ShowViewContent[i][3]="treeNode"+showmenu_id.substring(0,3);
		}else if(showmenu_id.length()==9){
			ShowViewContent[i][3]="treeNode"+showmenu_id.substring(0,6);
		}else if(showmenu_id.length()==12){
			ShowViewContent[i][3]="treeNode"+showmenu_id.substring(0,9);
		}else if(showmenu_id.length()==15){
			ShowViewContent[i][3]="treeNode"+showmenu_id.substring(0,12);
		}
	}
	//判断节点是否为叶子节点
	for(int i=0;i<ShowViewContent.length;i++){
		for(int j=0;j<ShowViewContent.length;j++){
			if(("treeNode"+ShowViewContent[i][0]).equals(ShowViewContent[j][3])){
				ShowViewContent[i][2]="branch";
				break;
			}else{
				ShowViewContent[i][2]="leaf";
			}
				
		}
	}	
	if(menuList.size()>0){
	//显示菜单信息
	for(int i=0;i<ShowViewContent.length;i++){
%>		   	
	   		var treeNode<%=ShowViewContent[i][0]%> = new Ext.tree.TreeNode({
		       id : '<%=ShowViewContent[i][0]%>',
		       text : '<%=ShowViewContent[i][1]+"【"+ShowViewContent[i][0]+"】"%>',
		       draggable:true,
	           collapsible : true,//允许展开和收缩
	           listeners:{'click': function(){loadMain('<%=ShowViewContent[i][2]%>','<%=ShowViewContent[i][4]%>','<%=ShowViewContent[i][1]%>');}}
		   });
		   <%=ShowViewContent[i][3]%>.appendChild(treeNode<%=ShowViewContent[i][0]%>);	   
<%
	}
	}else{
	for(int i=0;i<Integer.parseInt(showCount);i++){
%>
		   		var <%="treeNode"+i%> = new Ext.tree.TreeNode({
			       id : '<%=i%>',
			       text : 'view'+'<%=(i+1)%>',
			       draggable:true,
		           collapsible : true,//允许展开和收缩
		           listeners:{'click': function(){loadMain('leaf','0','view'+'<%=(i+1)%>');}}
			   });
			   root.appendChild(<%="treeNode"+i%>);
<%
	}
	}
	for(int i=1;i<Integer.parseInt(addCount);i++){
%>
		   		var <%="treeNode"+(i+49)%> = new Ext.tree.TreeNode({
			       id : '<%=(i+49)%>',
			       text : 'view'+'<%=(i+1+49)%>',
			       draggable:true,
		           collapsible : true,//允许展开和收缩
		           listeners:{'click': function(){loadMain('leaf','0','view'+'<%=(i+1+49)%>');}}
			   });
			   root.appendChild(<%="treeNode"+(i+49)%>);
<%
	}	
%>		   
			tree.setRootNode(root);
	    	tree.render();
	    	//root.expand();
			root.expand(false);
	    <%	    	
	    	//菜单是否展开设置
	    	for(int i=0;i<ShowViewContent.length;i++){
	    		if("branch".equals(ShowViewContent[i][2])){
	    %>		    	
	    	    	<%="treeNode"+ShowViewContent[i][0]%>.expand(false);
	    <%
	    		}
	    	}
	    %>
	    	
		});	
	
		
	</script>
<script language=javascript>
	//----------------------查询---------------//
	function queryInfo(){
		var regionCode=trim(document.theform.RegionCode.value);
		if(typeof(regionCode)!="undefined" && regionCode!="")
		{
			if(confirm("确定要执行操作吗？")){
				document.theform.submit();
			}
		}
	}
	//----------------------设置---------------//
	function configInfo(){
		var showCount=trim(document.theform.ShowCount.value);
		if(typeof(showCount)!="undefined" && showCount!="")
		{	
			if(confirm("确定要执行操作吗？")){
				document.theform.submit();
			}
		}
	}
	//----------------------新增---------------//
	function newInfo(){
		if("<%=addCount%>"==0){
			document.theform.AddCount.value="1";
			document.theform.RedCount.value="0";
			document.theform.ActionFlag.value="";
		}else{
			document.theform.AddCount.value="<%=addCount%>";
			document.theform.RedCount.value="0";
			document.theform.ActionFlag.value="";
		}
		document.theform.submit();
	}
	//----------------------删除---------------//
	function deleteInfo(){
		document.theform.RedCount.value="1";
		document.theform.ActionFlag.value="";
		document.theform.submit();
	}	
	//----------------------删除---------------//
	function delInfo(){
		var RegionID=trim(document.theform.regionID.value);
		if(typeof(RegionID)=="undefined" || RegionID==""){
			alert("请选择对应的view！");
			return;	
		}
		document.theform.regionID.value=RegionID;
		document.theform.ActionFlag.value="delete";
		if(confirm("确定要执行删除吗？")){
			document.theform.submit();
		}
	}				
</script>
<script language=javascript>
	var _t = document.getElementById("home-column-left");
	try{
		window.document.title = "界面信息";
	}catch(e){
	}
	//选择菜单信息
  	function loadMain(nodetype,regionID,itemName) {
  		var regionName="<%=regionName%>";
  		var regionCode=trim(document.theform.RegionCode.value);
  		if("leaf"=="leaf"){
  			if(confirm("设置菜单名为："+itemName)){
				if(showModalDialog('/system/config/interface_region_info.jsp?interfacetype_code=130&identity_code='+regionID+'&table_code=CONFIG_REGION&region_name='+regionName+'&region_code='+regionCode, '', 'dialogWidth:560px;dialogHeight:550px;status:0;help:0') != null)
				{
					sl_update_ok();
					document.theform.ActionFlag.value="Save";
					document.theform.submit();
				}
			}
			document.theform.regionID.value=regionID;
		}
	}
	//设置为首先左边菜单自动弹出
	function inner_collapsed(){
		return false;   
	}

	function loadMenuAccordion(){
		var stretchers = document.getElementsByClassName('stretcher');
		var toggles = document.getElementsByClassName('toggle');
		
		new Fx.Accordion(toggles, stretchers, {
			opacity: false,
			duration: 200
		});
	}
  //初始化菜单信息	
  Ext.onReady(function(){
        var mainLayout = new Ext.BorderLayout(document.body,
		{
		   
		    west: {
		        split:true,
				titlebar: true,	
				collapsible: true,
				animate: true,
				autoScroll: true,
				initialSize: 250, 
				collapsed:(inner_collapsed()),
				margins: {top:0,bottom:0,right:0,left:4}
		    },
		    center: {
		    	split: false, 
				autoScroll: true, 
				margins: {top:0,bottom:0,right:4,left:0}
		    }
		});
			mainLayout.beginUpdate();
			mainLayout.add('west', new Ext.ContentPanel('home-column-left', {fitToFrame:true}));
			mainLayout.add('center', new Ext.ContentPanel('home-column-main'));
			mainLayout.restoreState();
			mainLayout.endUpdate();
			
			if(Ext.isSafari || Ext.isOpera){
			layout.layout();
		}
		loadMenuAccordion();
  });
 </script> 
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>


