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
							url : "project_aduDeclareList.action",
							datatype : 'json',
							mtype : 'POST',

							height : 'auto',
							altRows : 'true',

							colNames : [ '省份', '所属项目', '名称', '培训单位', '培训学科',
									'项目申报书', '项目实施方案', '评分状态','评审意见', '管理员终审状态',
									'专家打分', '审核意见' ],

							colModel : [ {
								name : 'provinceName',
								index : 'provinceName',
								width : 80,
								align : 'center',
								stype : 'select',
								editoptions : {
									value : provinces
								}
							}, {
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
								name : 'pinfenzhuangtai',
								index : 'pinfenzhuangtai',
								align : 'center',
								search : true,
								width : 50,
								frozen : true,
								stype : 'select',
								editoptions : {
									value : "3:所有;1:可评分;0:不可评分"
								}
							},
							{
								name : 'pinshengzhuangtai',
								index : 'pinshengzhuangtai',
								align : 'center',
								search : true,
								width : 50,
								frozen : true,
								stype : 'select',
								editoptions : {
									value : "0:所有;2:发布;1:停止"
								}
							},{
								name : 'adminSelectFlag',
								index : 'adminSelectFlag',
								align : 'center',
								search : true,
								width : 50,
								frozen : true,
								stype : 'select',
								editoptions : {
									value : "0:所有;2:未审核;4:通过;5:不通过"
								}
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
										var ht = '<a style="color:blue" target="_blank"  href="exp_chakanyijian.action?id='
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

		$('#list2').navButtonAdd('#pager2', {
			caption : '通过审核',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				var pIds = "";
				for (var i = 0; i < ids.length; i++) {
					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}
				$.post("project_aduDeclareMethod.action", {
					"pId" : pIds,
					"method" : "adu"
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = [];
				});

			},
			position : "last"
		});

		$('#list2').navButtonAdd('#pager2', {
			caption : '不通过审核',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				var pIds = "";
				for (var i = 0; i < ids.length; i++) {
					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}
				$.post("project_aduDeclareMethod.action", {
					"pId" : pIds,
					"method" : "noadu"
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = [];
				});

			},
			position : "last"
		});
		$('#list2').navButtonAdd('#pager2', {
			caption : '可评分',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				var pIds = "";
				for (var i = 0; i < ids.length; i++) {
					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}
				$.post("project_pinfenDeclareMetod.action", {
					"pId" : pIds,
					"method" : "pinfen"
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = [];
				});

			},
			position : "last"
		});
		$('#list2').navButtonAdd('#pager2', {
			caption : '不可评分',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				var pIds = "";
				for (var i = 0; i < ids.length; i++) {
					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}
				$.post("project_pinfenDeclareMetod.action", {
					"pId" : pIds,
					"method" : "nopinfen"
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = [];
				});

			},
			position : "last"
		});
		
		$('#list2').navButtonAdd('#pager2', {
			caption : '发布评审意见',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				var pIds = "";
				for (var i = 0; i < ids.length; i++) {
					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}
				$.post("project_pinshengDeclareMetod.action", {
					"pId" : pIds,
					"method" : "pinsheng"
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = [];
				});

			},
			position : "last"
		});
		$('#list2').navButtonAdd('#pager2', {
			caption : '停止评审意见',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				var pIds = "";
				for (var i = 0; i < ids.length; i++) {
					if (i < ids.length - 1) {
						pIds += ids[i] + ",";
					} else {
						pIds += ids[i];
					}
				}
				$.post("project_pinshengDeclareMetod.action", {
					"pId" : pIds,
					"method" : "nopinsheng"
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = [];
				});

			},
			position : "last"
		});
		
		$('#list2').navButtonAdd('#pager2', {
			caption : '导出EXCEL',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				
				if ($('#downloadcsv').length <= 0)
					$('body').append("<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
				$('#downloadcsv').attr('src', 'project_importaduDeclareList.action');
				
			},
			position : "last"
		});
		
		$('#list2').navButtonAdd('#pager2', {
			caption : '导出详细EXCEL',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				
				if ($('#downloadcsv').length <= 0)
					$('body').append("<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
				$('#downloadcsv').attr('src', 'project_importaduDeclareListInfo.action');
				
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