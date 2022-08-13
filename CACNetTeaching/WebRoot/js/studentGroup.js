$(document).ready(function(){
	$.jgrid.ajaxOptions.type = 'post';
var init=new Object();
/*init.leftInit=function(){
$(".cca-side li ").removeClass("active");	
$('#plan').addClass("active");	
};
*/
init.gridInit=function(){

	  jQuery("#list").jqGrid({
		url:"teacher_groupManaged.action?opt=init&coursedesignId="+courseId,
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
		editurl: "teacher_groupManaged.action?opt=add&coursedesignId="+courseId,
		keys:true,
		autowidth: true,
		autoScroll: true,
		caption: "分组管理表",
	     	colNames:['操作','课程','组名','负责教师'],
	     	colModel:[
                {name:'act',index:'act', width:50,sortable:false, editable:false,search:false},      
	     		/*{name:'id',index:'id', width:40, align:"center", sorttype:"int", editable:false,search:false,editoptions:{readonly:true,size:10}},*/
	     		{name:'courseName',index:'courseName', width:100,align:"center", editable:false},
	     		{name:'groupName',index:'groupName', width:100,align:"center", sorttype:"string",editable:true,editrules:{required:true}},
	     		{name:'teacheres',index:'teacheres', width:100,align:"center", sorttype:"string",
		     		edittype:"select",editable:true,editoptions:{dataUrl:"teacher_groupManaged.action?opt=getkv&coursedesignId="+courseId},editrules:{required:true}}
	     		],
	     		gridComplete: function(){
	     			var ids = jQuery("#list").jqGrid('getDataIDs');
	     			for(var i=0;i < ids.length;i++){
	     				var cl = ids[i];
	     				se = "<a href='teacher_studentManageInit.action?id="+cl+"&courseId="+courseId+"'><img src='img/icon7.png' alt='学生管理'></img>学生入组</a>"; 
	     			
	     				jQuery("#list").jqGrid('setRowData',ids[i],{act:se});
	     			}	
	     		},
	     		
		
	  }
	  
	  
	  );
	 
	  jQuery("#list").jqGrid('navGrid',"#pager",{
		  edit:true,add:true,del:true,view:true,search:false		  
		  
	  },
	 {
		  //edit
		  url : "teacher_groupManaged.action?opt=edit&coursedesignId="+courseId, 
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
		  reloadAfterSubmit:true,
		  url: "teacher_groupManaged.action?opt=add&coursedesignId="+courseId,
	  },
	  {
		  //del
		  top:300,
		  left:500,
		  reloadAfterSubmit:false,
		  url : "teacher_groupManaged.action?opt=del&coursedesignId="+courseId
	  },
	  {
		  reloadAfterSubmit:true,
		  url : "teacher_groupManaged.action?opt=search"
		  //search
		  
	  }
	  ); 
	  
	  
  };
  
//获取url参数
	init.getUrlPara=function(name){
		
		
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象

			var r = window.location.search.substr(1).match(reg); // 匹配目标参数

			if (r != null)
				return unescape(r[2]);

			return null; // 返回参数值

		
		
	};
   
 
//init.leftInit();
  var courseId=init.getUrlPara("coursedesignId");
init.gridInit();
document.title="学生分组管理";
});