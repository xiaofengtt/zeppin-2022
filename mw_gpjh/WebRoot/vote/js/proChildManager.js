$(function() {

	// 页面加载结束执行

	var selectId = "";
	var ids = new Array();// 被选中的行id
	var projects = "";

	function initGrid() {

		$("#list2").jqGrid({
			url : "project_proChildList.action?userid=" + userid,
			datatype : 'json',
			mtype : 'POST',

			height : 'auto',
			altRows : 'true',

			colNames : [ '省份', '所属项目', '项目编号', '名称', '活动状态', '时间', '审核状态' ],

			colModel : [ {
				name : 'provinceName',
				index : 'provinceName',
				align : 'center',
				search : false,
				width : 80
			}, {
				name : 'projectName',
				index : 'projectName',
				width : 220,
				stype : 'select',
				editoptions : {
					value : projects
				}
			}, {
				name : 'code',
				index : 'code',
				align : 'center',
				search : false,
				width : 50
			}, {
				name : 'subProjectName',
				index : 'subProjectName',
				search : true,
				width : 200,
				frozen : true,
			}, {
				name : 'progameStatus',
				index : 'progameStatus',
				align : 'center',
				search : false,
				width : 50,
				frozen : true,
			}, {
				name : 'datetime',
				index : 'datetime',
				align : 'center',
				search : false,
				width : 55,
				frozen : true,
			}, {
				name : 'selectFlag',
				index : 'selectFlag',
				align : 'center',
				width : 50,
				stype : 'select',
				editoptions : {
					value : "0:所有;1:未审核;2:通过;3:不通过"
				}
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
			caption : '添加项目',
			buttonicon : "ui-icon-plusthick",
			onClickButton : addProject,
			position : "last"
		});

		$('#list2').navButtonAdd('#pager2', {
			caption : '编辑项目',
			buttonicon : "ui-icon-pencil",
			onClickButton : editProject,
			position : "last"
		});

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
								$('#downloadcsv').attr('src',
										'project_importproChildList.action');

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

		initGrid();

	});

	function addProject() {
		var addDlg = $("#addDiv");
		addDlg.dialog("option", "title", "新增项目信息").dialog("open");
	}

	function addProjectOK() {

		var parentId = $('#addProjectParent option:selected').val();
		var parentText = $('#addProjectParent option:selected').text();
		var name = $('#addProjectNameText').val();
		var code = $('#addProjectCode').val();
		// var year = $('#addProjectYear').val();

		$.post('project_proChildAdd.action', {
			'userid' : userid,
			'name' : name,
			'code' : code,
			// 'year' : year,
			'parentid' : parentId,
			'parentname' : parentText,
			'method' : 'add'
		}, function(e, t) {
			if (e != false) {
				var id = e.id;
				var proviceName = e.name;
				var time = e.time;
				$("#list2").jqGrid('addRowData', id, {
					'provinceName' : proviceName,
					'projectName' : parentText,
					'code' : code,
					// 'year' : year,
					'datetime' : time,
					'subProjectName' : name,
					'progameStatus' : '自动',
					'selectFlag' : '未审核',
				}, 'last');
			} else {
				alert('添加失败');
			}

		});

		var addDlg = $("#addDiv");
		addDlg.dialog().dialog("close");
		$('#addProjectNameText').attr("value", "");
		$('#addProjectCode').attr("value", "");
		$('#addProjectYear').attr("value", "");
	}

	function editProject() {
		if (selectId == "") {
			alert("请先选择一个项目在编辑!");
		} else {
			var rowData = $('#list2').jqGrid('getRowData', selectId);

			var status = rowData['selectFlag'];

			if (status == "通过") {
				alert("子项目已经通过,您无法进行修改,请联系超级管理员！");
				return;
			}

			var name = rowData['subProjectName'];
			var code = rowData['code'];
			// var year = rowData['year'];
			var parentText = rowData['projectName'];

			$("#ediProjectParent option").each(function(index) {
				if ($(this).text() == parentText) {
					$(this).attr('selected', 'selected');
				}
			});

			$('#ediProjectNameText').attr("value", name);
			$('#ediProjectCode').attr("value", code);
			// $('#ediProjectYear').attr("value", year);

			var ediDlg = $("#ediDiv");
			ediDlg.dialog("option", "title", "编辑项目信息").dialog("open");
		}
	}

	function editProjectOK() {

		var parentId = $('#ediProjectParent option:selected').val();
		var parentText = $('#ediProjectParent option:selected').text();
		var name = $('#ediProjectNameText').val();
		var code = $('#ediProjectCode').val();
		var year = $('#ediProjectYear').val();

		$.post('project_proChildAdd.action', {
			'userid' : userid,
			'name' : name,
			'code' : code,
			'year' : year,
			'parentid' : parentId,
			'parentname' : parentText,
			'id' : selectId,
			'method' : 'edit'
		}, function(e, t) {
			if (e != false) {
				$("#list2").jqGrid('setRowData', selectId, {
					'projectName' : parentText,
					'code' : code,
					'year' : year,
					'subProjectName' : name,
					'progameStatus' : '自动',
					'selectFlag' : '未审核',
				}, 'last');
			} else {
				alert('添加失败');
			}

		});

		var addDlg = $("#ediDiv");
		addDlg.dialog().dialog("close");
		$('#ediProjectNameText').attr("value", "");
		$('#ediProjectCode').attr("value", "");
		$('#ediProjectYear').attr("value", "");
	}

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
					} else {
						if (i < ids.length - 1) {
							pIds += ids[i] + ",";
						} else {
							pIds += ids[i];
						}
					}
				}

				$.post('project_proChildAdd.action', {
					'id' : pIds,
					'method' : 'del'
				}, function(r) {
					$("#list2").trigger("reloadGrid");
					ids = new Array();
				})
			}
		}
	}

	$("#addDiv").dialog({
		autoOpen : false, // 是否自动弹出窗口
		modal : true, // 设置为模态对话框
		resizable : true,
		width : 500,
		height : 300,
		buttons : {
			确定 : addProjectOK
		},
		position : "center" // 窗口显示的位置
	});

	$("#ediDiv").dialog({
		autoOpen : false, // 是否自动弹出窗口
		modal : true, // 设置为模态对话框
		resizable : true,
		width : 500,
		height : 300,
		buttons : {
			确定 : editProjectOK
		},
		position : "center" // 窗口显示的位置
	});

});