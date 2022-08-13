$(document).ready(function(){
	$.jgrid.ajaxOptions.type = 'post';
var init=new Object();
init.leftInit=function(){
$(".cca-side li ").removeClass("active");	
$('#plan').addClass("active");	
};

init.gridInit=function(){

	  jQuery("#list").jqGrid({
		url:"course_planManage.action?opt=init",
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
	    multiselect: false,
		editurl: "course_planManage.action?opt=add",
		keys:true,
		autowidth: true,
		autoScroll: true,
		caption: "开课计划表",
	     	colNames:['课程名称','开课院系','课程','开课时间','开课周数','课时','学分','修读方式','授课对象','操作'],
	     	colModel:[        
	     		//{name:'id',index:'id', width:40, align:"center", sorttype:"int", editable:false,search:false,editoptions:{readonly:true,size:10}},
	     		{name:'courseName',index:'courseName', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true}},
	     		{name:'dicAcademy',index:'dicAcademy', width:100,align:"center", sorttype:"string",
		     		edittype:"select",editable:true,searchoptions:{dataUrl:"admin_academyManage.action?opt=getkv&search=1"},stype : 'select',editoptions:{dataUrl:"admin_academyManage.action?opt=getkv"},editrules:{required:true}},
	     		{name:'subject',index:'subject', width:100,align:"center", sorttype:"string",
		     		edittype:"select",stype : 'select',searchoptions:{dataUrl:"admin_subjectManage.action?opt=getkv&search=1"},editable:true,editoptions:{dataUrl:"admin_subjectManage.action?opt=getkv"},editrules:{required:true}},
	     		{name:'startTime',index:'startTime', width:120,align:"center", sorttype:"string",editable:true,editrules:{required:true},
			    	editoptions:{size:12,
						dataInit:function(el){
							$(el).datepicker({
							
								 showMonthAfterYear: true, // 月在年之后显示
								    changeMonth: true,     // 允许选择月份
								    changeYear: true,     // 允许选择年份
								    dateFormat:'yy-mm-dd',    // 设置日期格式
								    closeText:'关闭',     // 只有showButtonPanel: true才会显示出来
								    duration: 'fast',
								    showAnim:'fadeIn',
								    buttonText:'选择日期',
								    showButtonPanel: true,
								    showOtherMonths: true
							
							});
						}
						
					},
					
					searchoptions:{
						size:380,
						dataInit:function(el){$(el).datepicker({ showMonthAfterYear: true, // 月在年之后显示
					    changeMonth: true,     // 允许选择月份
					    changeYear: true,     // 允许选择年份
					    dateFormat:'yy-mm-dd',    // 设置日期格式
					    closeText:'关闭',     // 只有showButtonPanel: true才会显示出来
					    duration: 'fast',
					    showAnim:'fadeIn',
					    buttonText:'选择日期',
					    showButtonPanel: true,
					    showOtherMonths: true});} }
			    	
			    	},
		     	{name:'weeks',index:'weeks', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true,number:true}},
			    {name:'classHour',index:'classHour', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:40},editrules:{required:true,number:true}},
		     	{name:'credit',index:'credit', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true,number:true}},
			    {name:'studyMode',index:'studyMode', width:90,align:"center", sorttype:"string",editable:true,edittype:"select",stype : 'select',searchoptions:{value:"all:所有;1:必修;2:选修"},editoptions:{value:"1:必修;2:选修"}},
			    {name:'studentDescirpt',index:'studentDescirpt', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:10}},
			   // {name:'remark',index:'remark', width:160,align:"center", editable:true, edittype:"textarea",editoptions:{rows:"2",cols:"40"}},
			    {name:'act',index:'act', width:450,sortable:false, editable:false,search:false}
	     		],
	     		gridComplete: function(){
	     			var ids = jQuery("#list").jqGrid('getDataIDs');
	     			for(var i=0;i < ids.length;i++){
	     				var cl = ids[i];
	     				te="<a href='course_teacherManageInit.action?id="+cl+"'>教师管理</a>"; 
	     				se = "<a href='course_studentManageInit.action?id="+cl+"'>学生管理</a>"; 
	     				ce = "<a href='course_scheduleManageInit.action?id="+cl+"'>课表管理</a>"; 
	     				he = "<a href='course_homeworkInit.action?id="+cl+"'>作业管理</a>"; 
	     				sce = "<a href='course_scoreInit.action?id="+cl+"'>成绩管理</a>"; 
	     				jQuery("#list").jqGrid('setRowData',ids[i],{act:te+"  "+se+"   "+ce+"   "+he+"   "+sce});
	     			}	
	     		},
	     		
		
	  }
	  
	  
	  );
	 
	  jQuery("#list").jqGrid('navGrid',"#pager",{
		  edit:true,add:true,del:true,view:true,search:false		  
		  
	  },
	 {
		  //edit
		  url : "course_planManage.action?opt=edit", 
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
		  url : "course_planManage.action?opt=del"
	  },
	  {
		  reloadAfterSubmit:true,
		  url : "course_planManage.action?opt=search"
		  //search
		  
	  }
	  ); 
	  
	  //密码初始化
	 /* jQuery("#list").jqGrid('navButtonAdd','#pager',{caption:"", title:"密码初始化",
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
		});*/
	  jQuery("#list").jqGrid('filterToolbar',{autosearch: true, searchOnEnter: true});
	  
  };
  
   
 
init.leftInit();
init.gridInit();
document.title="开课计划管理";
});