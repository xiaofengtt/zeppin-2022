$(
	/*加载结束执行*/
	function(){
		
		var ids = new Array();// 被选中的行id
		
		/*初始化Grid*/
		function initGrid() {
			
			$("#list2").jqGrid({
				url : "actualnew_actualList2.action",
				datatype : 'json',
				mtype : 'POST',
				height : 'auto',
				altRows : 'true',
				colNames : ['承训单位', '项目名称', '所属年度', '人均经费', '培训人数', '提交时间', '审核时间','审核状态','决算操作' ],

				colModel : [ {
					name : 'unitName',
					index : 'unitName',
					search : true,
					width : 80
				}, {
					name : 'name',
					index : 'name',
					search : true,
					width : 200
				}, {
					name : 'year',
					index : 'year',
					align : 'center',
					search : false,
					width : 50
				}, {
					name : 'standand',
					index : 'standand',
					align : 'center',
					search : false,
					width : 50
				}, {
					name : 'pcountstr',
					index : 'pcountstr',
					align : 'center',
					search : false,
					width : 50
				}, {
					name : 'intime',
					index : 'intime',
					search : false,
					align : 'center',
					width : 100
				}, {
					name : 'audTime',
					index : 'audTime',
					align : 'center',
					search : false,
					width : 100
				}, {
					name : 'status',
					index : 'status',
					search : true,
					align : 'center',
					width : 100,
					frozen : true,
					stype : 'select',
					editoptions : {
						value : "-1:所有;0:未审核;1:通过;2:未通过"
					}
				}, {
					name : 'opt',
					index : 'opt',
					search : false,
					align : 'center',
					width : 100
				} ],

				rowNum : 50,
				rowList : [ 50, 100, 500 ],
				pager : '#pager2',
				viewrecords : true,
				sortname : 'id',
				sortorder : "desc",
				sortable : false,
				caption : "决算列表",
				multiselect : true, // 多选框
				multiboxonly : false,
				autowidth : true,
				rownumbers : true,
				toolbar: [true,"top"],
				onSelectAll : function(rowids, statue) {
					if (statue) {
						ids = rowids;
					} else {
						ids = [];

					}
				},
				onSelectRow : function(rowid, status, e) {
					if (status) {
						var flag = false;
						for (var i = 0; i < ids.length; i++) {
							if (ids[i] == rowid) {
								flag = true;
								break;
							}
						}
						if (!flag) {
							ids.push(rowid);
						}

					} else {
						for (var i = 0; i < ids.length; i++) {
							if (ids[i] == rowid) {
								ids.splice(i, 1);
								break;
							}
						}
					}
				},
				gridComplete : function() {
					var ids = $("#list2").jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++) {
						var cl = ids[i];
						
						var rowData = $("#list2").jqGrid('getRowData', cl);

						var st2 = '<a style="color:blue" target="_blank"  href="actualnew_toDetail2.action?peId=' + cl + '">查看详情</a>';
						var st1 = '<a style="color:blue" target="_blank"  href="actualnew_toPdf2.action?peId=' + cl + '">导出PDF</a>';
						var st =  st2 +'  '+st1;
						$("#list2").jqGrid('setRowData', cl, {
							'opt' : st,
						});

					}
				}

			});
			
			//
			$("#list2").jqGrid('navGrid', '#pager2', {
				edit : false,
				add : false,
				del : false,
				search : false
			});
			
			$("#t_list2").append("<a class='toPDF' target='_blank' style='border:1px;color:blue;height:20px;font-size:-3;border-color:red;cursor:pointer;'>导出PDF</a>");
			$('#t_list2').on('click','.toPDF',function(){
				
				if(ids.length>0){
					var pIds = "";
					for (var i = 0; i < ids.length; i++) {
						if (i < ids.length - 1) {
							pIds += ids[i] + ",";
						} else {
							pIds += ids[i];
						}
					}
					$(this).attr("href","actualnew_toMorePdf.action?id="+pIds);
				}
				else{
					alert("请选择要打印的选项");
				}
			});

			$("#list2").jqGrid('filterToolbar', {
				searchOperators : true
			});

			$("#list2").jqGrid('setFrozenColumns'); // 锁定类 frozen:true
			
//			$('#list2').navButtonAdd('#pager2', {
//				caption : '设置通过',
//				buttonicon : "ui-icon-plusthick",
//				onClickButton : function() {
//
//					var pIds = "";
//					for (var i = 0; i < ids.length; i++) {
//						if (i < ids.length - 1) {
//							pIds += ids[i] + ",";
//						} else {
//							pIds += ids[i];
//						}
//					}
//					$.post('actualnew_changeStatus.action', {
//						'id' : pIds,
//						'method' : 'pass'
//					}, function(e, t) {
//						$("#list2").trigger("reloadGrid");
//						ids = [];
//					});
//
//				},
//				position : "last"
//			});
//			
//			$('#list2').navButtonAdd('#pager2', {
//				caption : '设置不通过',
//				buttonicon : "ui-icon-plusthick",
//				onClickButton : function() {
//
//					var pIds = "";
//					for (var i = 0; i < ids.length; i++) {
//						if (i < ids.length - 1) {
//							pIds += ids[i] + ",";
//						} else {
//							pIds += ids[i];
//						}
//					}
//					$.post('actualnew_changeStatus.action', {
//						'id' : pIds,
//						'method' : 'nopass'
//					}, function(e, t) {
//						$("#list2").trigger("reloadGrid");
//						ids = [];
//					});
//
//				},
//				position : "last"
//			});
			
		}
		
		initGrid();
		
	}
	
);