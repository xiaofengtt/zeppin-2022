$(document).ready(function(){
	$.jgrid.ajaxOptions.type = 'post';
	var init=new Object();
	init.studentList=function()
	{
		$("#teacherList .del").click(function(){
			var obj=$(this);
			var id=$(this).parent().parent().find('td').eq(1).html();
			if(confirm("是否真的移除？")){
				$.post("course_removeTeacher.action",
						{id:id,courseId:courseId},
						function(data){
					obj.parent().parent().fadeOut("slow");	
					
				});
				
			};
			
		});
		
		$("#teacherList .leader").click(function(){
			
			var obj=this;
			var id=$(this).parent().parent().find('td').eq(1).html();
			
			
				$.post("course_leaderTeacher.action",
						{id:id,courseId:courseId},
						function(data){
							init.setLeaderIcon(obj);
					
				});
				
		
			
		});
		
		var leaderId=$('#leaderId').val();
		$('#teacherList .leader').each(function(index){
			var obj=this;
			var id=$(this).parent().parent().find('td').eq(1).html();
			//alert(id+leaderId);
			if(id==$('#leaderId').val()){
				init.setLeaderIcon(obj);
				$('#leaderId').val("");
			};
			
		});
			
			
			
		
	
	};
	
	//设置组长图标
	
	init.setLeaderIcon=function(obj)
	{
		$(".leaderIcon").empty();   
		$(obj).parent().parent().find(".leaderIcon").html("<img src='img/icon14.png' height='20' width='25'></img>");   
	   
		
	};
	// 获取url参数
	init.getUrlPara=function(name){
		
		
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象

			var r = window.location.search.substr(1).match(reg); // 匹配目标参数

			if (r != null)
				return unescape(r[2]);

			return null; // 返回参数值

		
		
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
			hiddengrid: true,
			caption: "选择教师(请点击右侧箭头展开)",
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
			  edit:false,add:false,del:false,view:true,search:false		  
			  
		  },{
			  //edit
			  url : "admin_studentManage.action?opt=edit", 
			  height:600,
			  width:500,
			  reloadAfterSubmit:true,
			  closeAfterEdit:true,
			  top:100,
			  left:500
				  
			  
		  },
		  {
			  //add
			  height:600,
			  width:500,
			  reloadAfterSubmit:true,
			  top:100,
			  left:500
		  },
		  {
			  //del
			
			  reloadAfterSubmit:false,
			  url : "admin_studentManage.action?opt=del",
			  top:300,
			  left:500
				  
		  },
		  {
			  reloadAfterSubmit:true,
			  url : "admin_studentManage.action?opt=search"
			  //search
			  
		  }
		  ); 
		  
		  //导出教师
		  jQuery("#list").jqGrid('navButtonAdd','#pager',{caption:"", title:"导出选择的教师到当前课程",
			    buttonicon : "ui-icon-shuffle",
				onClickButton:function(){
					if(confirm("是否将选择的教师加入到当前课程？")){
						var s;
						s = jQuery("#list").jqGrid('getGridParam','selarrrow');
						$.ajax({
							   type: "post",
							   url: "course_addTeacher.action?id="+courseId,
							   data: "userType=1&ids[]="+s,
							   success: function(msg){
								       alert("添加成功！");
									   location.reload();
								   
							    
							   }
							});
						
						
					}
					
								
				} 
			});
		  jQuery("#list").jqGrid('filterToolbar',{autosearch: true, searchOnEnter: true});
		  
	  };
	  var courseId=init.getUrlPara("id");
	  init.studentList();
	  init.gridInit();
      document.title="教师-课程操作";
    
});