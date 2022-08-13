$(function() {

	// 页面加载结束执行

	var selectId = "";
	var projects = "";
	var provinces = "";

	var ids = new Array();// 被选中的行id

	function initGrid() {

		$("#list2").jqGrid({
			url : "project_aduList.action",
			datatype : 'json',
			mtype : 'POST',
			height : 'auto',
			altRows : 'true',

			colNames : [ '省份', '所属项目', '项目编号', '名称', '活动状态', '时间', '审核状态' ],

			colModel : [ {
				name : 'provinceName',
				index : 'provinceName',
				align : 'center',
				width : 80,
				stype : 'select',
				editoptions : {
					value : provinces
				}
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
				frozen : true
			}, {
				name : 'progameStatus',
				index : 'progameStatus',
				align : 'center',
				search : false,
				width : 50,
				frozen : true
			}, {
				name : 'datetime',
				index : 'datetime',
				align : 'center',
				search : false,
				width : 55,
				frozen : true
			}, {
				name : 'selectFlag',
				index : 'selectFlag',
				align : 'center',
				width : 60,
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
			caption : "中西部项目审核管理",
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
					ids.push(rowid);

				} else {
					for (var i = 0; i < ids.length; i++) {
						if (ids[i] == rowid) {
							ids.splice(i, 1);
						}
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
				if (ids.length == 0) {
					alert("请选择要审核的子项目");
				} else {
					var pIds = "";
					for (var i = 0; i < ids.length; i++) {
						if (i < ids.length - 1) {
							pIds += ids[i] + ",";
						} else {
							pIds += ids[i];
						}
					}

					$.post("project_proChildAdu.action", {
						"pId" : pIds,
						"method" : "adu"
					}, function(r) {
						$("#list2").trigger("reloadGrid");
						ids = new Array();
					});
				}
				// alert("审核已经提交,稍后刷新列表！");

			},
			position : "last"
		});

		$('#list2').navButtonAdd('#pager2', {
			caption : '不通过审核',
			buttonicon : "ui-icon-pencil",
			onClickButton : function() {
				if (ids.length == 0) {
					alert("请选择要审核的子项目");
				} else {
					var pIds = "";
					for (var i = 0; i < ids.length; i++) {
						if (i < ids.length - 1) {
							pIds += ids[i] + ",";
						} else {
							pIds += ids[i];
						}
					}

					$.post("project_proChildAdu.action", {
						"pId" : pIds,
						"method" : "noadu"
					}, function(r) {
						if (r) {
							$("#list2").trigger("reloadGrid");
						}
						ids = new Array();
					});
				}

			},
			position : "last"
		});

		$('#list2').navButtonAdd('#pager2', {
			caption : '添加子项目',
			buttonicon : "ui-icon-plusthick",
			onClickButton : addProject,
			position : "last"
		});

		$('#list2').navButtonAdd('#pager2', {
			caption : '导出EXCEL',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				
				if ($('#downloadcsv').length <= 0)
					$('body').append("<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
				$('#downloadcsv').attr('src', 'project_ImportExcelAduList.action');
				
			},
			position : "last"
		});

	}

	$.getJSON("project_headsearch.action", function(r) {
		var preInitData = r.preInitData;
		var province = preInitData.province;
		var provinceHtml = "";
		for (var i = 0; i < province.length; i++) {
			var ht = '<option value="' + province[i].id + '">'
					+ province[i].name + '</option>';
			provinceHtml += ht;
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

		$('#addProvince').html(provinceHtml);

		initGrid();

	});

	function addProject() {
		var addDlg = $("#addDiv");
		addDlg.dialog("option", "title", "新增学员信息").dialog("open");
	}

	function addProjectOK() {

		var parentId = $('#addProjectParent option:selected').val();
		var parentText = $('#addProjectParent option:selected').text();
		var name = $('#addProjectNameText').val();
		var code = $('#addProjectCode').val();
		var proviceid = $('#addProvince option:selected').val();
		// var year = $('#addProjectYear').val();

		$.post('project_proChildAdd.action', {
			'proviceid' : proviceid,
			'name' : name,
			'code' : code,
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
					'datetime' : time,
					'subProjectName' : name,
					'progameStatus' : '自动',
					'selectFlag' : '未审核'
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

	$("#addDiv").dialog({
		autoOpen : false, // 是否自动弹出窗口
		modal : true, // 设置为模态对话框
		resizable : true,
		width : 500,
		height : 500,
		buttons : {
			确定 : addProjectOK
		},
		position : "center" // 窗口显示的位置
	});

});