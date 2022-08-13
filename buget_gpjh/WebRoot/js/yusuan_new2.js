$(
	/*加载结束执行*/
	function(){
		
		var selectId = "";
		
		/*初始化Grid*/
		function initGrid() {
			
			$("#list2").jqGrid({
				url : "budgetnewtwo_yusuanList.action?userid=" + userid + "&role=" + role,
//				url : "budgetnewtwo_yusuanList.action?userid=bnu2&role=1",
				datatype : 'json',
				mtype : 'POST',
				height : 'auto',
				altRows : 'true',
//				colNames : ['承训单位', '项目名称', '培训项目编号', '所属年度', '项目类型', '申报时限', '人均经费','申报学科上限','预算操作' ],
				colNames : ['承训单位', '项目名称', '所属年度', '人均经费','培训人数','提交时间','审核时间','审核状态','预算操作' ],
				
				colModel : [ {
					name : 'unitName',
					index : 'unitName',
					search : false,
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
					name : 'total_persons',
					index : 'total_persons',
					align : 'center',
					search : false,
					width : 50
				}, {
					name : 'inputTime',
					index : 'inputTime',
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
					name : 'statusName',
					index : 'statusName',
					search : false,
					align : 'center',
					width : 100
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
				caption : "预算列表",
				multiselect : false, // 多选框
				multiboxonly : false,
				autowidth : true,
				rownumbers : true,
				onSelectAll : function(rowids, statue) {

				},
				onSelectRow : function(rowid, status, e) {
					if(status){
						selectId = rowid;
					}
					else{
						selectId = "";
					}
				},
				gridComplete : function() {
					var ids = $("#list2").jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++) {
						var cl = ids[i];
						
						var rowData = $("#list2").jqGrid('getRowData', cl);

						var st1 = '<a style="color:blue" target="_blank"  href="budgetnewtwo_addForward.action?proId=' + cl + '">提交预算</a>';
						var st2 = '<a style="color:blue" target="_blank"  href="budgetnewtwo_toDetail.action?proId=' + cl + '">查看详情</a>';
						var st3 = '<a style="color:blue" target="_blank"  href="budgetnewtwo_toPdf.action?proId=' + cl + '">导出PDF</a>';
						var st = st1 +'   '+ st2+'  '+st3;
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

			$("#list2").jqGrid('filterToolbar', {
				searchOperators : true
			});

			$("#list2").jqGrid('setFrozenColumns'); // 锁定类 frozen:true

			
		}
		
		initGrid();
		
	}
	
);