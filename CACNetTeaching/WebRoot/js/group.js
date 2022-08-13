$(document).ready(function(){
	$.jgrid.ajaxOptions.type = 'post';
	var init=new Object();
	init.studentList=function()
	{
		$("#studentList a").click(function(){
			var obj=$(this);
			var id=$(this).parent().parent().find('td').eq(0).html();
			if(confirm("是否真的移除？")){
				$.post("teacher_removeStudent.action",
						{id:id,courseId:courseId},
						function(data){
					obj.parent().parent().fadeOut("slow");	
					var count=$('#studentCount').html();
					$('#studentCount').html(count-1);
				});
				
			};
			
		});
		$("div.holder").jPages({
		      containerID : "studentlst",
		      previous : "←",
		      next : "→",
		      perPage :20,
		      delay : 20
		    });
		$('#goback').click(function(){
		    history.go(-1); 	
		});
	
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
			url:"teacher_studentlistManage.action?opt=init&courseId="+courseId,
		  	datatype: "json",
		  	altRows : 'true',
		  	width:1024,
			height : "500",
			rowNum:10,
		   	rowList:[10,20,30,100,200,500],
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "desc",
			rownumbers : true,
		    multiselect: true,
			editurl: "teacher_studentManage.action?opt=add",
			keys:true,
			autowidth: true,
			shrinkToFit:false,
			autoScroll: true,
			hiddengrid: true,
			caption: "选择学生(请点击右侧箭头展开)",
		     	colNames:['编号','姓名','学号','专业','班级','学制','生源地','','入学时间','学籍状态','性别','电话','身份证','家庭联系人','家庭电话','备注'],
		     	colModel:[
		     		{name:'id',index:'id', width:40, align:"center", sorttype:"int", editable:false,search:false,editoptions:{readonly:true,size:10}},
		     		{name:'name',index:'name', width:80,align:"center", sorttype:"string",editable:true,editoptions:{size:10},editrules:{required:true}},
		     		{name:'studentCode',index:'studentCode', width:80,align:"center", sorttype:"string",editable:true,editoptions:{size:40},editrules:{required:true}},
		     		{name:'dicMajor',index:'dicMajor', width:100,align:"center", sorttype:"string",
		     		edittype:"select",editable:true,searchoptions:{dataUrl:"admin_majorManage.action?opt=getkv&search=1"},stype : 'select',editoptions:{dataUrl:"admin_majorManage.action?opt=getkv"},editrules:{required:true}},
		     		{name:'classes',index:'classes', width:80,align:"center", sorttype:"string",editable:true,editoptions:{size:40},editrules:{required:false}},	
		     		{name:'edusystem',index:'edusystem', width:100,align:"center", sorttype:"string",
			     		edittype:"select",stype : 'select',searchoptions:{dataUrl:"admin_eduSysManage.action?opt=getkv&search=1"},editable:true,editoptions:{dataUrl:"admin_eduSysManage.action?opt=getkv"},editrules:{required:true}},
			     	{name:'dicAddress',index:'dicAddress', width:100,align:"center", sorttype:"string",edittype:"select",editable:true,editoptions:{
			     		dataUrl:"baseData_addressManage.action?opt=getkv",	
			     		dataEvents: [
			     	                   { type: 'change',          //下拉选择的时候
			     	                       fn: function(e)
			     	                       {
			     	                    	   //alert($(e.target).val());
			     	                           var str = "";
			     	                           $.post('baseData_addressManage.action?opt=getkv',
			     	                        		   {id:$(e.target).val()},
			     	                        		   function(data)
			     	                        		   {
				     	                                  str=data;
				     	                                  //alert(data);
				     	                                 $("#citycode").html(str); 
				     	                                  });
			     	                        
			     	                       }
			     	                   }      
			     	                ]
			     	
			     	},
			     	editrules:{required:true}},
			     	{name:'citycode',index:'citycode', width:50,align:'center',editable : true,editrules:{edithidden:true,required:true},hidden:true,edittype : "select",
						editoptions : {dataUrl:"baseData_addressManage.action?opt=getkv&son=1"}},
				    {name:'eduStartTime',index:'eduStartTime', width:120,align:"center", sorttype:"string",editable:true,
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
				    {name:'state',index:'state', width:100,align:"center", sorttype:"string",edittype:"select",editable:true,stype : 'select',searchoptions:{value:"all:所有;1:正常;2:异动"},editoptions:{value:"1:正常;2:异动"},editrules:{required:true}},
				    {name:'sex',index:'sex', width:90,align:"center", sorttype:"string",editable:true,edittype:"select",stype : 'select',searchoptions:{value:"all:所有;1:男;2:女"},editoptions:{value:"1:男;2:女"}},
				    {name:'phone',index:'phone', width:120,align:"center", sorttype:"string",editable:true,editoptions:{size:10}},
				    {name:'idCode',index:'idCode', width:80,align:"center", sorttype:"string",editable:true,editoptions:{size:40},editrules:{required:false}},	
				    {name:'familyName',index:'familyName', width:160,align:"center", editable:true,editoptions:{rows:"2",cols:"20"}},
				    {name:'familyphone',index:'familyphone', width:160,align:"center", editable:true,editoptions:{rows:"2",cols:"20"}},
			     	{name:'remark',index:'remark', width:160,align:"center", editable:true, edittype:"textarea",editoptions:{rows:"2",cols:"40"}}
				   
		     		]
		     		
			
		  });
		  
		 
		  jQuery("#list").jqGrid('navGrid',"#pager",{
			  edit:false,add:false,del:false,view:true,search:false		  
			  
		  },{
			  //edit
			  url : "teacher_studentManage.action?opt=edit", 
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
			  url : "teacher_studentManage.action?opt=del",
			  top:300,
			  left:500
				  
		  },
		  {
			  reloadAfterSubmit:true,
			  url : "teacher_studentManage.action?opt=search"
			  //search
			  
		  }
		  ); 
		  
		  //密码初始化
		  jQuery("#list").jqGrid('navButtonAdd','#pager',{caption:"", title:"导出选择的学生到当前组",
			    buttonicon : "ui-icon-shuffle",
				onClickButton:function(){
					if(confirm("是否将选择的学生加入到当前组？")){
						var s;
						s = jQuery("#list").jqGrid('getGridParam','selarrrow');
						$.ajax({
							   type: "post",
							   url: "teacher_addStudent.action?id="+groupId,
							   data: "userType=1&ids[]="+s,
							   success: function(msg){
								       alert("添加成功！");
									   location.reload();
								   
							    
							   }
							});
						
						
					}
					
								
				} 
			});
		  //jQuery("#list").jqGrid('filterToolbar',{autosearch: true, searchOnEnter: true});
		  
	  };
	  var groupId=init.getUrlPara("id");
	  var courseId=init.getUrlPara("courseId");
	  init.studentList();
	  init.gridInit();
      document.title="学生-组操作";
     // var id=init.getUrlPara("id");
     
});