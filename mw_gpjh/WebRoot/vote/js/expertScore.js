$(function() {

	// 页面加载结束执行

	var selectId = "";
	var projects = "";
	var provinces = "";
	var units = "";

	function initGrid() {

		$("#list2")
				.jqGrid(
						{
							url : "exp_expertDeclareList.action?userid="
									+ userid,
							datatype : 'json',
							mtype : 'POST',

							height : 'auto',
							altRows : 'true',

							colNames : [ '省份', '所属项目', '培训单位', '项目名称', '学科',
									'项目申报书', '项目实施方案','得分', '评分' ],

							colModel : [

							{
								name : 'provinceName',
								index : 'provinceName',
								width : 80,
								align : 'center',
								stype : 'select',
								editoptions : {
									value : provinces
								}
							}, {
								name : 'parentName',
								index : 'parentName',
								width : 200,
								stype : 'select',
								editoptions : {
									value : projects
								}
							}, {
								name : 'unitname',
								index : 'unitname',
								search : true,
								width : 200
							}, {
								name : 'projectname',
								index : 'projectname',
								search : true,
								width : 200,
								frozen : true
							}, {
								name : 'subjuect',
								index : 'subjuect',
								stype : 'select',
								editoptions : {
									value : units
								}
							}, {
								name : 'declare',
								index : 'declare',
								search : false,
								align : 'center',
								width : 80,
								frozen : true
							}, {
								name : 'theme',
								index : 'theme',
								align : 'center',
								search : false,
								hidden : true,
								width : 80
							},{
								name : 'theme',
								index : 'theme',
								align : 'center',
								search : false,
								width : 50
							}, {
								name : 'pingfen',
								index : 'pingfen',
								align : 'center',
								search : false,
								width : 80
							} ],

							rowNum : 50,
							rowList : [ 50, 100, 500 ],
							pager : '#pager2',
							viewrecords : true,
							sortname : 'id',
							sortorder : "desc",
							sortable : false,
							caption : "评分列表",
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
							},
							gridComplete : function() {

								var ids = $("#list2").jqGrid('getDataIDs');
								for (var i = 0; i < ids.length; i++) {
									var cl = ids[i];
									var rowData = $("#list2").jqGrid(
											'getRowData', cl);

									if (rowData['declare'] != "") {
										var ht = '<a style="color:blue" target="_blank"  href="'
												+ rowData['declare']
												+ '">下载</a>';
										$("#list2").jqGrid('setRowData', cl, {
											'declare' : ht
										});
									}

									var st = '<a style="color:blue" target="_blank"  href="exp_expertGrade.action?expId='
											+ cl + '">打分</a>';
									$("#list2").jqGrid('setRowData', cl, {
										'pingfen' : st
									});

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
				$('#downloadcsv').attr('src', 'exp_importexpertDeclareList.action');
				
			},
			position : "last"
		});

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