$(document).ready(function(){
var init=new Object();
init.leftInit=function(){
$(".cca-side li ").removeClass("active");	
$('#major').addClass("active");	
};

init.gridInit=function(){

	  jQuery("#list").jqGrid({
		url:"admin_majorManage.action?opt=init",
	  	datatype: "json",
	  	altRows : 'true',
		height : "500",
		width:"800",
		rowNum:10,
	   	rowList:[10,20,30],
	   	pager: '#pager',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
		editurl: "admin_majorManage.action?opt=add",
		keys:true,
		autowidth: true,
		caption: "专业表",
	     	colNames:['编号','专业名称','英文名称','所属部门（系）'],
	     	colModel:[
	     		{name:'id',index:'id', width:20, align:"center", sorttype:"int", editable:false,editoptions:{readonly:true,size:10}},
	     		{name:'name',index:'name', width:40,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true}},
	     		{name:'enName',index:'enName', width:40,align:"center", sorttype:"string",editable:true,editoptions:{size:20},editrules:{required:true}},
	     		{name:'daId',index:'daId', width:90,align:"center", sorttype:"string",edittype:"select",editable:true,editoptions:{dataUrl:"admin_academyManage.action?opt=getkv"},editrules:{required:true}}
	     		]
		
	  });
	 
	  jQuery("#list").jqGrid('navGrid',"#pager",{
		  edit:true,add:true,del:true,view:true,search:false		  
		  
	  },
	  { 
		  //edit
		  url : "admin_majorManage.action?opt=edit", 
		  height:260,
		  width:400,
		  top:100,
		  left:500,
		  reloadAfterSubmit:true,
		  closeAfterEdit:true
		  
	  },
	  {
		  //add
		  height:260,
		  width:400,
		  top:100,
		  left:500,
		  reloadAfterSubmit:true
	  },
	  {
		  //del
		  top:300,
		  left:500,
		  reloadAfterSubmit:false,
		  url : "admin_majorManage.action?opt=del"
	  },
	  {
		  //search
		  
	  }
	  ); 
	  
  };
 
init.leftInit();
init.gridInit();
document.title="课程管理";
});