$(document).ready(function(){
var init=new Object();
init.leftInit=function(){
$(".cca-side li ").removeClass("active");	
$('#eduSys').addClass("active");	
};

init.gridInit=function(){

	  jQuery("#list").jqGrid({
		url:"admin_eduSysManage.action?opt=init",
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
		editurl: "admin_eduSysManage.action?opt=add",
		keys:true,
		autowidth: true,
		caption: "学制表",
	     	colNames:['编号','名称','年限'],
	     	colModel:[
	     		{name:'id',index:'id', width:20, align:"center", sorttype:"int", editable:false,editoptions:{readonly:true,size:10}},
	     		{name:'name',index:'name', width:40,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true}},
	     		{name:'year',index:'year', width:40,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true,number:true}}
	     		]
		
	  });
	 
	  jQuery("#list").jqGrid('navGrid',"#pager",{
		  edit:true,add:true,del:true,view:true,search:false		  
		  
	  },
	  
	  {
		  //edit
		  url : "admin_eduSysManage.action?opt=edit", 
		  height:200,
		  top:100,
		  left:500,
		  reloadAfterSubmit:false,
		  closeAfterEdit:true
		  
	  },
	  {
		  //add
		  height:200,
		  top:100,
		  left:500,
		  reloadAfterSubmit:true
	  },
	  {
		  //del
		  top:300,
		  left:500,
		  reloadAfterSubmit:false,
		  url : "admin_eduSysManage.action?opt=del"
	  },
	  {
		  //search
		  
	  }
	  ); 
	  
  };
 
init.leftInit();
init.gridInit();
document.title="学制管理";
});