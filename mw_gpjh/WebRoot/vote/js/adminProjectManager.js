$(function() {

	// 页面加载结束执行

	var selectId = "";
	var provinces = "";
	var projects = "";

	function initGrid() {

		$("#list2").jqGrid({
			url : "project_list.action",
			datatype : 'json',
			mtype : 'POST',

			height : 'auto',
			altRows : 'true',

			colNames : [ '省份', '所属项目', '子项目', '培训单位', '学科' ],

			colModel : [ {
				name : 'provinceName',
				index : 'provinceName',
				width : 80,
				stype : 'select',
				editoptions : {
					value : provinces
				}
			}, {
				name : 'projectName',
				index : 'projectName',
				width : 150,
				stype : 'select',
				editoptions : {
					value : projects
				}
			}, {
				name : 'subProjectName',
				index : 'subProjectName',
				width : 200,
				frozen : true
			}, {
				name : 'unitName',
				index : 'unitName',
				width : 80,
				frozen : true
			}, {
				name : 'subjectName',
				index : 'subjectName',
				width : 50,
				frozen : true
			} ],

			rowNum : 50,
			rowList : [ 50, 100, 500 ],
			pager : '#pager2',
			viewrecords : true,
			sortname : 'id',
			sortorder : "desc",
			sortable : false,
			caption : "中西部项目管理",
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

	}

	$.getJSON("project_headsearch.action", function(r) {
		var preInitData = r.preInitData;
		var province = preInitData.province;
		for ( var i = 0; i < province.length; i++) {
			if (i < province.length - 1) {
				provinces += province[i].id + ":" + province[i].name + ";";
			} else {
				provinces += province[i].id + ":" + province[i].name;
			}
		}

		var project = preInitData.project;
		for ( var i = 0; i < project.length; i++) {
			if (i < project.length - 1) {
				projects += project[i].id + ":" + project[i].name + ";";
			} else {
				projects += project[i].id + ":" + project[i].name;
			}
		}

		initGrid();

	});

});