<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%
	String object_id = Utility.trimNull(request.getParameter("object_id"),"");//���
	String edit_right = Utility.trimNull(request.getParameter("edit_right"),"");//�༭Ȩ��
	String object_type = Utility.trimNull(request.getParameter("object_type"),"");//��������
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	
	<title>������Ϣ</title> 
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<meta http-equiv="Content-Type" content="text/html;charset=GBK"/> 
	<meta http-equiv="Cache-Control" content="no-store"/> 
	<meta http-equiv="Pragma" content="no-cache"/>   
	<meta http-equiv="Expires" content="0"/>
	

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/includes/default.css"></link>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/index.css"></link>	
	
	<style type="text/css">
		a#close{ padding:0 5px;background:#fff;filter:Alpha(opacity=40); opacity:0.4; right:6px; top:6px; font-family:Verdana, Arial, Helvetica, sans-serif; font-weight:bold; color:#ccc; font-size:18px; text-align:center; position:absolute; text-decoration:none;}
		a#close:hover{ color:#000;}
	</style>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/prototype.lite.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/moo.fx.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/accordion.js"></script>
	
	
	
	<script language="javascript">
	Ext.onReady(function(){
		Ext.BLANK_IMAGE_URL = "/images/s.gif";
		
    	var Tree = Ext.tree;
    
    	var tree = new Tree.TreePanel('home-column-left', { 
    	    animate: true,// չ��,��������
        	enableDD:true,
        	border: true,
        	rootVisible: true,//���ڵ��Ƿ�ɼ�
        	containerScroll: true
        	
    	});
    	var root = new Ext.tree.TreeNode({
       		id : 'root',
       		text : '�˵�Ŀ¼', 
       		draggable:true,
	        collapsible : true//����չ��������
  		});
  		
<%
	ConfigLocal configLocal = EJBFactory.getConfig();
	List menuList = null;
	Map menuMap = null;
	
	String codeName="ItemViews010",menu_id="",menu_name="";
	menuList = configLocal.showSelectInfo("code",codeName,"");
	String[][] ShowContent = new String[menuList.size()][6];
	//��ʼ����ʾ�˵���Ϣ
	for(int i=0;i<menuList.size();i++)
	{
		menuMap = (Map)menuList.get(i);
		menu_id=Utility.trimNull(menuMap.get("Item_Code"));
		ShowContent[i][0]=menu_id;
		ShowContent[i][1]=Utility.trimNull(menuMap.get("Item_Name"));
		ShowContent[i][4]=Utility.trimNull(menuMap.get("Relative_Name"));
		ShowContent[i][5]=Utility.trimNull(menuMap.get("Attribute_Name"));
		if(menu_id.length()==3){
			ShowContent[i][3]="root";     //�̳��ϼ��ڵ���
		}else if(menu_id.length()==6){
			ShowContent[i][3]="treeNode"+menu_id.substring(0,3);
		}else if(menu_id.length()==9){
			ShowContent[i][3]="treeNode"+menu_id.substring(0,6);
		}else if(menu_id.length()==12){
			ShowContent[i][3]="treeNode"+menu_id.substring(0,9);
		}else if(menu_id.length()==15){
			ShowContent[i][3]="treeNode"+menu_id.substring(0,12);
		}
	}
	//�жϽڵ��Ƿ�ΪҶ�ӽڵ�
	for(int i=0;i<ShowContent.length;i++){
		for(int j=0;j<ShowContent.length;j++){
			if(("treeNode"+ShowContent[i][0]).equals(ShowContent[j][3])){
				ShowContent[i][2]="branch";
				break;
			}else{
				ShowContent[i][2]="leaf";
			}
				
		}
	}	
	//��ʾ�˵���Ϣ
	for(int i=0;i<ShowContent.length;i++){
%>		   	
	   		var treeNode<%=ShowContent[i][0]%> = new Ext.tree.TreeNode({
		       id : '<%=ShowContent[i][0]%>',
		       text : '<%=ShowContent[i][1]%>',
		       draggable:true,
	           collapsible : true,//����չ��������
	           listeners:{'click': function(){loadMain('<%=ShowContent[i][0]%>','<%=ShowContent[i][1]%>','<%=ShowContent[i][2]%>','<%=ShowContent[i][4]%>','<%=ShowContent[i][5]%>');}}
		   });
		   <%=ShowContent[i][3]%>.appendChild(treeNode<%=ShowContent[i][0]%>);	   
<%
	}
%>				
			tree.setRootNode(root);
	    	tree.render();
	    	//root.expand();
			root.expand(false);
	    <%	    	
	    	//�˵��Ƿ�չ������
	    	for(int i=0;i<ShowContent.length;i++){
	    		if("branch".equals(ShowContent[i][2])){
	    %>		    	
	    	    	<%="treeNode"+ShowContent[i][0]%>.expand(false);
	    <%
	    		}
	    	}
	    %>
	    	
		});	
	function onPageClose()
    {
		<%if("yes".equals(edit_right)){%>
     			window.returnValue=1;
     	<%}%>
    }
		
	</script>
</head>
<body onbeforeunload ="onPageClose()">
	<div id="home-column-left" style="margin 0 0 0 0;padding-top: 7px;padding-left: 7px;top: 0px;"></div>
	<iframe id="home-column-main" name="content" frameborder="0" style="width:100%;height:100%;" target="content"></iframe>
</body>
</html>
<script language="javascript">
	var _t = document.getElementById("home-column-left");
	try{
		window.document.title = "������Ϣ";
	}catch(e){
	}
	//ѡ��˵���Ϣ
  	function loadMain(nodeno,nodename,nodetype,jspurl,jspparam) {
  		jspparam=jspparam.replace("#OBJECT_ID#","<%=object_id%>");
  		jspparam=jspparam.replace("#EDIT_RIGHT#","<%=edit_right%>");
  		jspparam=jspparam.replace("#OBJECT_TYPE#","<%=object_type%>");
  		if(nodetype=="leaf"){
			Ext.get('home-column-main').dom.src = jspurl+"?"+jspparam;
		}
	}
	//����Ϊ������߲˵��Զ�����
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
  //��ʼ���˵���Ϣ	
  Ext.onReady(function(){
        var mainLayout = new Ext.BorderLayout(document.body,
		{
		   
		    west: {
		        split:true,
				titlebar: true,	
				collapsible: true,
				animate: true,
				autoScroll: false,
				initialSize: 220, 
				collapsed:(inner_collapsed()),
				margins: {top:0,bottom:0,right:0,left:4}
		    },
		    center: {
		    	split: false
				, autoScroll: true
				, margins: {top:0,bottom:0,right:4,left:0}
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
  
  //��ʼ��չʾ�ͻ�������Ϣ
  
  	Ext.get('home-column-main').dom.src = "/project/flowApp/staffreimbs010_info.jsp"+"?"+"object_type=<%=object_type%>&object_id=<%=object_id%>&edit_right=<%=edit_right%>";
</script>
