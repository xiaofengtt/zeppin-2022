//申报审核

$(function() {

	var selectId = "";

	var ids = new Array();// 被选中的行id

	var provinces = "";
	var projects = "";
	var units = "";

	function initGrid() {

		$("#list2")
				.jqGrid(
						{
							url : "project_proChildAduYijian.action?userid="
									+ userid,
							datatype : 'json',
							mtype : 'POST',

							height : 'auto',
							altRows : 'true',

							colNames : [ '所属项目', '名称', '培训单位', '培训学科',
									'项目申报书', '项目实施方案', '专家打分', '审核意见' ],

							colModel : [ {
								name : 'projectName',
								index : 'projectName',
								width : 200,
								stype : 'select',
								editoptions : {
									value : projects
								}
							}, {
								name : 'subProjectName',
								index : 'subProjectName',
								search : true,
								width : 200,
								frozen : true
							}, {
								name : 'unitname',
								index : 'unitname',
								align : 'center',
								search : true,
								width : 100,
								frozen : true
							}, {
								name : 'subjectname',
								index : 'subjectname',
								align : 'center',
								width : 120,
								stype : 'select',
								editoptions : {
									value : units
								}
							}, {
								name : 'declare',
								index : 'declare',
								width : 50,
								align : 'center',
								search : false,
								frozen : true
							}, {
								name : 'scheme',
								index : 'scheme',
								align : 'center',
								search : false,
								hidden : true,
								width : 50,
								frozen : true
							}, {
								name : 'zhuanjiadafen',
								index : 'zhuanjiadafen',
								align : 'center',
								search : false,
								width : 30,
								frozen : true
							}, {
								name : 'shenheyijian',
								index : 'shenheyijian',
								align : 'center',
								search : false,
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
							caption : "管理员审核",
							multiselect : true, // 多选框
							multiboxonly : true,
							autowidth : true,
							rownumbers : true,
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
									var rowData = $("#list2").jqGrid(
											'getRowData', cl);

									if (rowData['declare'] != "") {
										var ht = '<a target="_blank" style="color:blue"  href="'
												+ rowData['declare']
												+ '">下载</a>';
										$("#list2").jqGrid('setRowData', cl, {
											'declare' : ht
										});
									}

									if (rowData['zhuanjiadafen'] != "") {
										var ht = '<a style="color:blue" target="_blank"  href="exp_chakanyijian.action?type=1&id='
												+ cl + '">查看意见</a>';
										$("#list2").jqGrid('setRowData', cl, {
											'shenheyijian' : ht
										});
									}
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
		for (var i = 0; i < province.length; i++) {
			if (i < province.length - 1) {
				provinces += province[i].id + ":" + province[i].name + ";";
			} else {
				provinces += province[i].id + ":" + province[i].name;
			}
		}

		var project = preInitData.project;
		for (var i = 0; i < project.length; i++) {
			if (i < project.length - 1) {
				projects += project[i].id + ":" + project[i].name + ";";
			} else {
				projects += project[i].id + ":" + project[i].name;
			}
		}
		
		var unit = preInitData.unit;
		for (var i = 0; i < unit.length; i++) {
			if (i < unit.length - 1) {
				units += unit[i].id + ":" + unit[i].name + ";";
			} else {
				units += unit[i].id + ":" + unit[i].name;
			}
		}

		initGrid();

	});

});