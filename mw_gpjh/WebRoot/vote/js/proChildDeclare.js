$(function() {

	// 页面加载结束执行

	var selectId = "";
	var ids = new Array();// 被选中的行id
	var projects = "";
	var units = "";

	function initGrid() {

		$("#list2")
				.jqGrid(
						{
							url : "project_proChildDeclareList.action?userid="
									+ userid,
							datatype : 'json',
							mtype : 'POST',

							height : 'auto',
							altRows : 'true',

							colNames : [ '所属项目', '名称', '培训单位', '培训学科', '审核状态',
									'项目申报书', '项目实施方案', '时间' ],

							colModel : [ {
								name : 'projectName',
								index : 'projectName',
								width : 180,
								stype : 'select',
								editoptions : {
									value : projects
								}
							}, {
								name : 'subProjectName',
								index : 'subProjectName',
								search : true,
								width : 180,
								frozen : true
							}, {
								name : 'unitname',
								index : 'unitname',
								search : true,
								width : 80,
								frozen : true
							}, {
								name : 'subjectname',
								index : 'subjectname',
								stype : 'select',
								editoptions : {
									value : units
								}
							}, {
								name : 'selectFlag',
								index : 'selectFlag',
								width : 50,
								stype : 'select',
								editoptions : {
									value : "0:所有;1:未审核;2:通过;3:不通过;4:已中标;5:未中标"
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
								width : 50,
								hidden : true,
								frozen : true
							}, {
								name : 'datetime',
								index : 'datetime',
								align : 'center',
								search : false,
								width : 55,
								frozen : true
							} ],

							rowNum : 50,
							rowList : [ 50, 100, 500 ],
							pager : '#pager2',
							viewrecords : true,
							sortname : 'id',
							sortorder : "desc",
							sortable : false,
							caption : "查看项目申报",
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
										var ht = '<a target="_blank" style="color:blue"  href="'
												+ rowData['declare']
												+ '">下载</a>';
										$("#list2").jqGrid('setRowData', cl, {
											declare : ht
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

		$('#list2').navButtonAdd('#pager2', {
			caption : '删除项目',
			buttonicon : "ui-icon-trash",
			onClickButton : deleteProject,
			position : "last"
		});

		$('#list2')
				.navButtonAdd(
						'#pager2',
						{
							caption : '导出EXCEL',
							buttonicon : "ui-icon-plusthick",
							onClickButton : function() {

								if ($('#downloadcsv').length <= 0)
									$('body')
											.append(
													"<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
								$('#downloadcsv')
										.attr('src',
												'project_importproChildDeclareList.action');

							},
							position : "last"
						});

	}

	$.getJSON("project_proChildHeader.action", {
		'userid' : userid
	}, function(r) {
		var preInitData = r.preInitData;

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

	function deleteProject() {
		if (ids.length == 0) {
			alert("请先选择项目在删除!");
		} else {
			if (confirm('是否确定删除项目?')) {

				var pIds = "";
				for (var i = 0; i < ids.length; i++) {

					var rowData = $('#list2').jqGrid('getRowData', ids[i]);
					var status = rowData['selectFlag'];

					if (status == "通过") {
						alert("子项目已经通过,您无法删除,请联系超级管理员！");
						return;
					}

					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}

				$.post('project_proChildDeclareOpreate.action', {
					'id' : pIds,
					'method' : 'del'
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = new Array();
				})
			}
		}
	}

});