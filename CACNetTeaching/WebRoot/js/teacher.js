$(document).ready(function(){
	$.jgrid.ajaxOptions.type = 'post';
var init=new Object();
init.leftInit=function(){
$(".cca-side li ").removeClass("active");	
$('#teacher').addClass("active");	
};

init.gridInit=function(){

	  jQuery("#list").jqGrid({
		url:"admin_teacherManage.action?opt=init",
	  	datatype: "json",
	  	altRows : 'true',
	  	width:1024,
		height : "500",
		rowNum:10,
	   	rowList:[10,20,30],
	   	pager: '#pager',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
		rownumbers : true,
	    multiselect: true,
		editurl: "admin_teacherManage.action?opt=add",
		keys:true,
		autowidth: true,
		autoScroll: true,
		caption: "教师表",
	     	colNames:['编号','姓名','工号','职务','职称','部门','电话','身份证','性别','权限类型','备注'],
	     	colModel:[
	     		{name:'id',index:'id', width:40, align:"center", sorttype:"int", editable:false,search:false,editoptions:{readonly:true,size:10}},
	     		{name:'name',index:'name', width:80,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true}},
	     		{name:'workCode',index:'workCode', width:80,align:"center", sorttype:"string",editable:true,editoptions:{size:40},editrules:{required:true}},
	     		{name:'dicDuty',index:'dicDuty', width:100,align:"center", sorttype:"string",
	     		edittype:"select",editable:true,searchoptions:{dataUrl:"admin_dutyManage.action?opt=getkv&search=1"},stype : 'select',editoptions:{dataUrl:"admin_dutyManage.action?opt=getkv"},editrules:{required:true}},
	     		{name:'dicTechnicalTiltle',index:'dicTechnicalTiltle', width:100,align:"center", sorttype:"string",
		     		edittype:"select",stype : 'select',searchoptions:{dataUrl:"admin_technicalManage.action?opt=getkv&search=1"},editable:true,editoptions:{dataUrl:"admin_technicalManage.action?opt=getkv"},editrules:{required:true}},
		     	{name:'dicAcademy',index:'dicAcademy', width:100,align:"center", sorttype:"string",
			     		edittype:"select",stype : 'select',searchoptions:{dataUrl:"admin_academyManage.action?opt=getkv&search=1"},editable:true,editoptions:{dataUrl:"admin_academyManage.action?opt=getkv"},editrules:{required:true}},
			    {name:'phone',index:'phone', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:10}},
			    {name:'idCode',index:'idCode', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:40},searchoptions:{size:60}},
			    {name:'sex',index:'sex', width:80,align:"center", sorttype:"string",editable:true,edittype:"select",stype : 'select',searchoptions:{value:"all:所有;1:男;2:女"},editoptions:{value:"1:男;2:女"}},
			    {name:'manageType',index:'manageType', width:100,align:"center", sorttype:"string",
			    	edittype:"select",editable:true,stype : 'select',searchoptions:{dataUrl:"admin_teacherManage.action?opt=getkv&search=1", size:40},editoptions:{dataUrl:"admin_teacherManage.action?opt=getkv"},editrules:{required:true}},
		     	{name:'remark',index:'remark', width:160,align:"center", editable:true, edittype:"textarea",editoptions:{rows:"2",cols:"40"}}
	     		]
	     		
		
	  });
	 
	  jQuery("#list").jqGrid('navGrid',"#pager",{
		  edit:true,add:true,del:true,view:true,search:false		  
		  
	  },
	 {
		  //edit
		  url : "admin_teacherManage.action?opt=edit", 
		  height:450,
		  width:500,
		  top:100,
		  left:500,
		  reloadAfterSubmit:true,
		  closeAfterEdit:true
		  
	  },
	  {
		  //add
		  height:450,
		  width:500,
		  top:100,
		  left:500,
		  reloadAfterSubmit:true
	  },
	  {
		  //del
		  top:300,
		  left:500,
		  reloadAfterSubmit:false,
		  url : "admin_teacherManage.action?opt=del"
	  },
	  {
		  reloadAfterSubmit:true,
		  url : "admin_teacherManage.action?opt=search"
		  //search
		  
	  }
	  ); 
	  
	  //密码初始化
	  jQuery("#list").jqGrid('navButtonAdd','#pager',{caption:"", title:"密码初始化",
		    buttonicon : "ui-icon-key",
		    onClickButton:function(){
		    	if(confirm("是否初始化选中人员密码？")){
				var s;
				s = jQuery("#list").jqGrid('getGridParam','selarrrow');
				//alert(s);
				$.ajax({
					   type: "post",
					   url: "admin_initPwd.action",
					   data: "userType=2&ids[]="+s,
					   success: function(msg){
					     alert("更改完成");
					   }
					});
				
		    	}
							
			} 
		});
	  jQuery("#list").jqGrid('filterToolbar',{autosearch: true, searchOnEnter: true});
	  
  };
  
   
 
init.leftInit();
init.gridInit();
document.title="教师管理";
});