$(function() {

	// 页面加载结束执行

	var selectId = "";
	var provinces = "";
	var parents = "";

	function initGrid() {

		$("#list2").jqGrid({
			url : "trainee_adminTraineelv.action",
			datatype : 'json',
			mtype : 'POST',

			height : 'auto',
			altRows : 'true',

			colNames : [ '省份', '所属项目', '计划学员人数', '参训学员人数', '参训率' ],

			colModel : [ {
				name : 'provinceName',
				index : 'provinceName',
				width : 50,
				align : 'center',
				stype : 'select',
				editoptions : {
					value : provinces
				}
			}, {
				name : 'parentName',
				index : 'parentName',
				width : 200,
				frozen : true,
				stype : 'select',
				editoptions : {
					value : parents
				}
			}, {
				name : 'jhcount',
				index : 'jhcount',
				align : 'center',
				search : false,
				width : 150

			}, {
				name : 'cxcount',
				index : 'cxcount',
				align : 'center',
				search : false,
				width : 100,
				frozen : true
			}, {
				name : 'lv',
				index : 'lv',
				align : 'center',
				search : false,
				width : 100,
				frozen : true
			} ],

			rowNum : 50,
			rowList : [ 50, 100, 500 ],
			pager : '#pager2',
			viewrecords : true,
			sortname : 'id',
			sortorder : "desc",
			sortable : false,
			caption : "学员统计",
			multiselect : true, // 多选框
			multiboxonly : true,
			autowidth : true,
			rownumbers : true,
			onSelectRow : function(rowid, status, e) {
				if (status) {
					selectId = rowid;
				} else {
					selectId = "";
				}
			}

		});

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
		
		$('#list2').navButtonAdd('#pager2', {
			caption : '导出EXCEL',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				
				if ($('#downloadcsv').length <= 0)
					$('body').append("<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
				$('#downloadcsv').attr('src', 'trainee_importadminTraineelv.action');
				
			},
			position : "last"
		});

	}

	$.getJSON("trainee_headsearch.action", function(r) {

		var preInitData = r.preInitData;

		var province = preInitData.province;
		for (var i = 0; i < province.length; i++) {

			if (i < province.length - 1) {
				provinces += province[i].id + ":" + province[i].name + ";";
			} else {
				provinces += province[i].id + ":" + province[i].name;
			}
		}

		var parent = preInitData.parent;
		for (var i = 0; i < parent.length; i++) {
			if (i < parent.length - 1) {
				parents += parent[i].id + ":" + parent[i].name + ";";
			} else {
				parents += parent[i].id + ":" + parent[i].name;
			}
		}

		initGrid();

	});

});
