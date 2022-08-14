<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>

<%
//声明变量
PortalLocal local = null;
List list = null;
Map map  = null;

StringBuffer northSb = new StringBuffer();
StringBuffer leftSb = new StringBuffer();
StringBuffer rightSb = new StringBuffer();
boolean left  = false;
boolean rigth  = false;
boolean north = false;

String portal_code = "";
String portal_name = "";
String url = "";
Integer  pos = null;
int EXPAND = 0;
int VISIBLE = 0 ;
input_bookCode =  new Integer(1);

try{
	//声明对象
	local = EJBFactory.getPortal();
	list =  local.queryMyPortal(input_bookCode,input_operatorCode);
	leftSb.append("[");
	rightSb.append("[");
	northSb.append("[");
	
	for(int i=0; i<list.size(); i++){
		map  = (Map)list.get(i);
		pos = (Integer)map.get("POS_TYPE");
		portal_code = (String)map.get("PORTAL_CODE");
		portal_name = (String)map.get("PORTAL_NAME");
		EXPAND = Utility.parseInt(Utility.trimNull(map.get("EXPAND")),0);
		VISIBLE = Utility.parseInt(Utility.trimNull(map.get("VISIBLE")),0);

		if(VISIBLE == 1){
			if(pos.intValue()==1){
				north = true;
				northSb.append("{");
				northSb.append("id:'P_");
				northSb.append(portal_code);
				northSb.append("',\n");
				northSb.append("title: '");
				northSb.append(portal_name);
				northSb.append("',\n");
				if(EXPAND != 1){
					northSb.append(" collapsed : true,\n");
				}
				northSb.append("layout:'fit',\n");		
				northSb.append("tools: tools,\n");
		        northSb.append("contentEl:Ext.get(document.getElementById(\"");
				northSb.append(portal_code);
				northSb.append("\")),\n");
	            
				northSb.append(" listeners: { \n ");
				northSb.append("  	'expand': function(){   \n");
			    northSb.append("	 expendPortal('");
				northSb.append(portal_code);
				northSb.append("');\n} ,\n ");
	           	northSb.append(" 			'collapse': function(){ \n");
	           	northSb.append("	 collapsePortal('");
				northSb.append(portal_code);
			  	northSb.append("')}} \n ");
				northSb.append("}\n,");
			}
			if(pos.intValue() == 2 ){
				left = true;
				leftSb.append("{");
				leftSb.append("id:'P_");
				leftSb.append(portal_code);
				leftSb.append("',\n");
				leftSb.append("title: '");
				leftSb.append(portal_name);
				leftSb.append("',\n");
				//信托移过来的 待查
				//if("PRTL_PRODUCTVIEW".equals(portal_code)){
				//	leftSb.append(" draggable: false,\n");
				//}
				if(EXPAND != 1){
					leftSb.append(" collapsed : true,\n");
				}
				leftSb.append("layout:'fit',\n");
				//信托移过来的 待查
				//if("PRTL_WAITPROBLEM".equals(portal_code)){
		        // 	leftSb.append("tools: tools1,\n");
				//}else{
				//	leftSb.append("tools: tools,\n");
				//}
				leftSb.append("tools: tools,\n");
		        leftSb.append("contentEl:Ext.get(document.getElementById(\"");
				leftSb.append(portal_code);
				leftSb.append("\")),\n");
	            
				leftSb.append(" listeners: { \n ");
				leftSb.append("  	'expand': function(){   \n");
			    leftSb.append("	 expendPortal('");
				leftSb.append(portal_code);
				leftSb.append("');\n} ,\n ");
	           	leftSb.append(" 			'collapse': function(){ \n");
	           	leftSb.append("	 collapsePortal('");
				leftSb.append(portal_code);
			  	leftSb.append("')}} \n ");
				leftSb.append("}\n,");
			}else if(pos.intValue() == 3){
				rigth = true;
				rightSb.append("{");
				rightSb.append("id:'P_");
				rightSb.append(portal_code);
				rightSb.append("',\n");
				rightSb.append("title: '");
				rightSb.append(portal_name);
				rightSb.append("',\n");
				//if("PRTL_PRODUCTVIEW".equals(portal_code)){
				//	rightSb.append(" draggable: false,\n");
				//}
				if(EXPAND != 1){
					rightSb.append(" collapsed : true,\n");
				}
				rightSb.append("layout:'fit',\n");
		        // if("PRTL_WAITPROBLEM".equals(portal_code)){
		        // 	rightSb.append("tools: tools1,\n");
				//	}else{
				//	rightSb.append("tools: tools,\n");
				//}
				rightSb.append("tools: tools,\n");
		        rightSb.append("contentEl:Ext.get(document.getElementById(\"");
				rightSb.append(portal_code);
				rightSb.append("\")),\n");
	
				rightSb.append(" listeners: { \n ");
				rightSb.append("  	'expand': function(){   \n");
			    rightSb.append("	 expendPortal('");
				rightSb.append(portal_code);
				rightSb.append("');\n} ,\n ");
	           	rightSb.append(" 			'collapse': function(){ \n");
	           	rightSb.append("	 collapsePortal('");
				rightSb.append(portal_code);
			  	rightSb.append("')}} \n ");
				rightSb.append("}\n,");
			}
		}
	}

	if(northSb.lastIndexOf(",") == northSb.length()-1){
		northSb.deleteCharAt(northSb.lastIndexOf(","));
	}
	
	if(leftSb.lastIndexOf(",") == leftSb.length()-1){
		leftSb.deleteCharAt(leftSb.lastIndexOf(","));
	}
	
	if(rightSb.lastIndexOf(",") == rightSb.length()-1){
		rightSb.deleteCharAt(rightSb.lastIndexOf(","));
	}
	
	northSb.append("]");
	leftSb.append("]");
	rightSb.append("]");
%>
<html>
<head>
	<title><%=application.getAttribute("APPLICATION_NAME")%></title>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<base target="_self">
	<SCRIPT>		
		window.trustCtx = '<%=request.getContextPath()%>';
		window.trustOpCode = '<%=input_operatorCode%>';
	</SCRIPT>
	<!-- CSS -->
	<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext2.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext2.0/protal/portal.css" />
	<style>	
		.tableProduct{
			border-collapse:collapse;
			border:solid #D1EEEE
			border-width:1px 1px 1px 1px;
		} 
		.tableProduct td{
			border:solid #D1EEEE;
			border-width:1px 1px 0px 0px;
			padding:1px 2px 1px 1px;
		}
		.listclass li{
			color:#999999;
			text-align:left;
			background-image:url(../images/array.gif);
			background-position:10px 10px;
			background-repeat:no-repeat;
			padding:5px 0px 0px 25px;
			background-color:#FFFFFF;
			font-size:12px;
			font-family:Verdana, Arial, Helvetica, sans-serif;
			font-style:normal;
			font-weight:normal;
			line-height:15px;
			padding-bottom:5px;
		
		}
	</style>
	<!-- 常用JS -->
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
	<!-- DWR -->
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/loginService.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/menuService.js'></script>	
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
	<!-- jQuery -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
	<!-- EXT基本类 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/ext-base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/ext-all.js"></script>
	<!-- Portal -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/protal/Portal.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/protal/PortalColumn.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/protal/Portlet.js"></script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0"	marginheight="0">
 	<%
		for(int i=0; i<list.size(); i++){
			map  = (Map)list.get(i);	
			url =Utility.trimNull(map.get("BODY_URL"));

			if(url ==null  || "".equals(url.trim())){
				url = "/login/portal/empty.jsp";
			}

			 VISIBLE = Utility.parseInt((Integer)map.get("VISIBLE"),new Integer(0)).intValue();
			if(VISIBLE == 1){
	 %>
		<DIV ID="<%=map.get("PORTAL_CODE")%>">			
			<jsp:include page="<%=url%>"></jsp:include>			  
		</DIV>
	<%	
			}
		}
	 %>
</body>
<script language="javascript">
	function expendPortal(id){
		$.post("<%=request.getContextPath()%>/login/portal/portal3_do.jsp", 
		{type:"expand",portalCode :id,opCode: <%=input_operatorCode%> },
	   	function(data) {
	    	if(1 != data){
				alert(data);
			}
	   });
	}
	function collapsePortal(id){
		$.post("<%=request.getContextPath()%>/login/portal/portal3_do.jsp", 
		{type:"collapse",portalCode :id,opCode: <%=input_operatorCode%> },
	   	function(data) {
	    	if(1 != data){
				alert(data);
			}
	   });
	}

	function setPortal(){
		var url = "<%=request.getContextPath()%>/login/portal/portal3_select.jsp";
		var v = showModalDialog(url, '', 'dialogWidth:300px;dialogHeight:300px;status:0;help:0');
		if(v){
			window.location.href  = window.location.href ;
		}
	}
	
	Ext.onReady(function(){
		var gear = {
	        id:'gear',
	        handler: function(e, target, panel){
				setPortal();
 
	        }
	    };

		var close = {
	        id:'close',
	        handler: function(e, target, panel){
				$.post("<%=request.getContextPath()%>/login/portal/portal3_do.jsp", 
				{type:"close",
				 portalCode :panel.getId() ,
				 opCode: <%=input_operatorCode%>},
			   	 function(data) {
			    	if(1!= data){
						alert(data);
					}
			   	  });
	            panel.ownerCt.remove(panel, true);
	        }
	    };

	    var tools = [gear,close];
	    var tools1 = [gear];
		var portalItems = [];
		var northportalItem = [];
		
		if(<%=north?1:0%>){
			northportalItem.push({
	                columnWidth:.99,
	                style:'padding:10px 0 10px 10px',
	                items:  
						<%=northSb.toString()%>
					 
	            });
		}else{
			northportalItem.push({
	                columnWidth:.66,
	                style:'padding:10px 0 10px 10px'
	            });
		}
		if(<%=left?1:0%>){
			portalItems.push({
	                columnWidth:.66,
	                style:'padding:10px 0 10px 10px',
	                items:  
						<%=leftSb.toString()%>
					 
	            });
		}else{
			portalItems.push({
	                columnWidth:.66,
	                style:'padding:10px 0 10px 10px'
	            });
		}
		if(<%=rigth?1:0%>){
			portalItems.push({
                columnWidth:.33,
                style:'padding:10px',
                items:  
				<%=rightSb.toString()%>	
			});
		}else{
			portalItems.push({
                columnWidth:.33,
                style:'padding:10px'
			});
		}
	    var viewport = new Ext.Viewport({
	        layout:'border',
	        items:[
			<%if(north){%>
			{
	            xtype:'portal',
	            region:'north',
	            margins:'0 0 0 0',
				height:130,
	            items:northportalItem
	        },
			<%}%>{
	            xtype:'portal',
	            region:'center',
	            margins:'0 0 0 0',
	            items:portalItems
	        }]
	    });
	});
</script>
</html>
<%
}
//catch(Exception e){
//	throw e;
//}
finally{
	if(local!=null)	local.remove();
}
%>