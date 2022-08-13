$(function() {

	// 页面加载结束执行

	var selectId = "";
	var provinces = "";
	var projects = "";
	var parents = "";
	var subs = "";

	var ids = new Array();// 被选中的行id
	var projectNameselect = "所有:所有;“国培计划（2010）”中西部项目（置换脱产）:“国培计划（2010）”中西部项目（置换脱产）;"
		+"“国培计划（2010）”中西部项目（短期集中）:“国培计划（2010）”中西部项目（短期集中）;"
		+"“国培计划（2011）”中西部项目（置换脱产）:“国培计划（2011）”中西部项目（置换脱产）;"
		+"国培计划（2011）”中西部项目（短期集中）:国培计划（2011）”中西部项目（短期集中）;"
		+"“国培计划（2011）”幼师国培项目（置换脱产）:“国培计划（2011）”幼师国培项目（置换脱产）;"
		+"“国培计划（2011）”幼师国培项目（短期集中）:“国培计划（2011）”幼师国培项目（短期集中）;"
		+"“国培计划（2011）”幼师国培项目（转岗教师）:“国培计划（2011）”幼师国培项目（转岗教师）;"
		+"“国培计划（2012）”中西部项目（置换脱产）:“国培计划（2012）”中西部项目（置换脱产）;"
		+"“国培计划（2012）”中西部项目（短期集中）:“国培计划（2012）”中西部项目（短期集中）;"
		+"“国培计划（2012）”幼师国培项目（置换脱产）:“国培计划（2012）”幼师国培项目（置换脱产）;"
		+"“国培计划（2012）”幼师国培项目（短期集中）:“国培计划（2012）”幼师国培项目（短期集中）;"
		+"“国培计划（2012）”幼师国培项目（转岗教师）:“国培计划（2012）”幼师国培项目（转岗教师）";

	function initGrid() {

		$("#list2").jqGrid(
				{
					url : "trainee_listHistory.action?userid=" + userid
							+ "&role=" + role,
					datatype : 'json',
					mtype : 'POST',

					height : 'auto',
					altRows : 'true',

					colNames : [ '省份', '所属项目', '子项目', '培训单位', '学科', '姓名', '手机',
							'电子邮件', '工作单位', '办公室电话', '职务', '职称', '证书编号', '备注',
							'状态' ],

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
						width : 250,
						search : true,
						stype : 'select',
						editoptions : {
							value : projectNameselect
						}
					}, {
						name : 'projectName',
						index : 'projectName',
						width : 180,
						search : true,
						frozen : true
					}, {
						name : 'unitName',
						index : 'unitName',
						align : 'center',
						search : true,
						width : 100,
						frozen : true
					}, {
						name : 'subjectName',
						index : 'subjectName',
						align : 'center',
						search : true,
						frozen : true
					}, {
						name : 'name',
						index : 'name',
						search : true,
						align : 'center',
						width : 50

					}, {
						name : 'phone',
						index : 'phone',
						align : 'center',
						search : true,
						width : 90

					}, {
						name : 'mail',
						index : 'mail',
						align : 'center',
						search : false,
						width : 100

					}, {
						name : 'workplace',
						index : 'workplace',
						align : 'center',
						search : false,
						width : 150

					}, {
						name : 'telphone',
						index : 'telphone',
						align : 'center',
						search : true,
						width : 80

					}, {
						name : 'zhiwu',
						index : 'zhiwu',
						align : 'center',
						search : false,
						width : 80

					}, {
						name : 'zhicheng',
						index : 'zhicheng',
						align : 'center',
						search : false,
						width : 80

					}, {
						name : 'dpcode',
						index : 'dpcode',
						align : 'center',
						search : true,
						width : 100

					}, {
						name : 'beizhu',
						index : 'beizhu',
						align : 'center',
						search : false,
						width : 150

					}, {
						name : 'status',
						index : 'status',
						align : 'center',
						search : true,
						width : 80,
						stype : 'select',
						editoptions : {
							value : "-1:所有;0:未审核;1:通过;2:不通过"
						}

					} ],

					rowNum : 50,
					rowList : [ 50, 100, 500 ],
					pager : '#pager2',
					viewrecords : true,
					sortname : 'id',
					sortorder : "desc",
					sortable : false,
					caption : "",
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
							selectId = rowid;

						} else {
							for (var i = 0; i < ids.length; i++) {
								if (ids[i] == rowid) {
									ids.splice(i, 1);
									break;
								}
							}
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

		if (role == "1") {

			$('#list2').navButtonAdd('#pager2', {
				caption : '添加学员',
				buttonicon : "ui-icon-plusthick",
				onClickButton : addProject,
				position : "last"
			});
			$('#list2').navButtonAdd('#pager2', {
				caption : '编辑学员',
				buttonicon : "ui-icon-plusthick",
				onClickButton : editProject,
				position : "last"
			});
			$('#list2').navButtonAdd(
					'#pager2',
					{
						caption : '删除',
						buttonicon : "ui-icon-plusthick",
						onClickButton : function() {
							if (ids.length == 0) {
								alert("请先选择项目在删除!");
							} else {
								if (confirm('是否确定删除项目?')) {

									var pIds = "";
									for (var i = 0; i < ids.length; i++) {

										var rowData = $('#list2').jqGrid('getRowData', ids[i]);
										var status = rowData['status'];

										if (status == "通过") {
											alert("学员已经通过,您无法删除,请联系超级管理员！");
											return;
										}

										if (i < ids.length - 1) {
											pIds += ids[i] + ",";
										} else {
											pIds += ids[i];
										}
									}

									$.post('trainee_trainHistoryDelete.action',
											{
												'id' : pIds
											}, function(r) {
												$("#list2").trigger(
														"reloadGrid");
												ids = new Array();
											})
								}
							}
						},
						position : "last"
					});

		} else {
			$('#list2').navButtonAdd('#pager2', {
				caption : '导出EXCEL',
				buttonicon : "ui-icon-plusthick",
				onClickButton : function() {
					
					if ($('#downloadcsv').length <= 0)
						$('body').append("<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
					$('#downloadcsv').attr('src', 'trainee_importtrainHistory.action');
					
				},
				position : "last"
			});
			$('#list2').navButtonAdd('#pager2', {
				caption : '设置未通过',
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
					$.post('trainee_trainHistoryTG.action', {
						'id' : pIds,
						'method' : 'notg'
					}, function(e, t) {
						$("#list2").trigger("reloadGrid");
						ids = [];
					});
				},
				position : "last"
			});

			$('#list2').navButtonAdd('#pager2', {
				caption : '设置通过',
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
					$.post('trainee_trainHistoryTG.action', {
						'id' : pIds,
						'method' : 'tg'
					}, function(e, t) {
						$("#list2").trigger("reloadGrid");
						ids = [];
					});
				},
				position : "last"
			});

		}

	}

	$.getJSON("trainee_headsearch.action?userid=" + userid + "&role=" + role,
			function(r) {

				var preInitData = r.preInitData;

				var provinceHtml = "";
				var province = preInitData.province;
				for (var i = 0; i < province.length; i++) {

					var ht = '<option value="' + province[i].id + '">'
							+ province[i].name + '</option>';
					provinceHtml += ht;

					if (i < province.length - 1) {
						provinces += province[i].id + ":" + province[i].name
								+ ";";
					} else {
						provinces += province[i].id + ":" + province[i].name;
					}
				}

				$('#addProvince').html(provinceHtml);
				$('#editProvince').html(provinceHtml);

				initGrid();

			});

	function addProject() {
		var addDlg = $("#addDiv");
		addDlg.dialog("option", "title", "新增学员信息").dialog("open");
	}

	function addProjectOK() {

		var provinceId = $('#addProvince option:selected').val();
		var parentId = $('#addProjectParent').val();
		var projectId = $('#addProject').val();
		var unitId = $('#addUnit').val();
		var subjectId = $('#addSubject').val();
		var name = $('#addNamet').val();
		var phone = $('#addPhone').val();
		var mail = $('#addMail').val();
		var workplace = $('#addWorkPlace').val();

		var addOfficePhone = $('#addOfficePhone').val();
		var addZhiWu = $('#addZhiWu').val();
		var addZhiCheng = $('#addZhiCheng').val();
		var addDipCode = $('#addDipCode').val();
		var addBeiZhu = $('#addBeiZhu').val();

		var add_items = $("#addDiv [name^=add]");
		for (var i = 0; i < add_items.length; i++) {
			qty = add_items[i];
			if ($(qty).val() == '') {
				alert("请检查要输入的必填项！");
				return;
			}
		}

		var provinceName = $('#addProvince option:selected').text();

		$.post('trainee_trainHistoryEdit.action', {
			'provinceId' : provinceId,
			'parentId' : parentId,
			'projectId' : projectId,
			'unitId' : unitId,
			'subjectId' : subjectId,
			'name' : name,
			'phone' : phone,
			'mail' : mail,
			'workplace' : workplace,
			'officephone' : addOfficePhone,
			'zhiwu' : addZhiWu,
			'zhicheng' : addZhiCheng,
			'beizhu' : addBeiZhu,
			'dipcode' : addDipCode,
			'method' : 'add'
		}, function(e, t) {
			if (e != false) {
				var id = e.id;
				$("#list2").jqGrid('addRowData', id, {
					'provinceName' : provinceName,
					'parentName' : parentId,
					'projectName' : projectId,
					'unitName' : unitId,
					'subjectName' : subjectId,
					'name' : name,
					'phone' : phone,
					'mail' : mail,
					'workplace' : workplace,
					'telphone' : addOfficePhone,
					'zhiwu' : addZhiWu,
					'zhicheng' : addZhiCheng,
					'dpcode' : addDipCode,
					'beizhu' : addBeiZhu,
					'status' : '未审核'
				}, 'last');
			} else {
				alert('添加失败');
			}

		});

		var addDlg = $("#addDiv");
		addDlg.dialog().dialog("close");
		$('#addNamet').attr("value", "");
		$('#addPhone').attr("value", "");
		$('#addMail').attr("value", "");
		$('#addWorkPlace').attr("value", "");
		$('#addDipcode').attr("value", "");

	}

	function editProject() {

		if (selectId == "") {
			alert("请先选择一个项目在编辑!");
		} else {

			var rowData = $('#list2').jqGrid('getRowData', selectId);

			var status = rowData['status'];

			if (status == "通过") {
				alert("学员已经通过,您无法编辑,请联系超级管理员！");
				return;
			}

			var name = rowData['name'];
			var phone = rowData['phone'];
			var mail = rowData['mail'];
			var workplace = rowData['workplace'];

			var addOfficePhone = rowData['telphone'];
			var addZhiWu = rowData['zhiwu'];
			var addZhiCheng = rowData['zhicheng'];
			var addDipCode = rowData['dpcode'];
			var addBeiZhu = rowData['beizhu'];

			var provinceName = rowData['provinceName'];
			var parentName = rowData['parentName'];
			var projectName = rowData['projectName'];
			var unitName = rowData['unitName'];
			var subjectName = rowData['subjectName'];

			$("#editProvince option").each(function(index) {
				if ($(this).text() == provinceName) {
					$(this).attr('selected', 'selected');
				}
			});

			$('#editProjectParent').attr("value", parentName);
			$('#editProject').attr("value", projectName);
			$('#editUnit').attr("value", unitName);
			$('#editSubject').attr("value", subjectName);

			$('#editNamet').attr("value", name);
			$('#editPhone').attr("value", phone);
			$('#editMail').attr("value", mail);
			$('#editWorkPlace').attr("value", workplace);

			$('#editOfficePhone').attr("value", addOfficePhone);
			$('#editZhiWu').attr("value", addZhiWu);
			$('#editZhiCheng').attr("value", addZhiCheng);
			$('#editDipCode').attr("value", addDipCode);
			$('#editBeiZhu').attr("value", addBeiZhu);

			var editDlg = $("#editDiv");
			editDlg.dialog("option", "title", "编辑学员信息").dialog("open");
		}

	}

	function editProjectOK() {

		var provinceId = $('#editProvince option:selected').val();
		var parentId = $('#editProjectParent').val();
		var projectId = $('#editProject').val();
		var unitId = $('#editUnit').val();
		var subjectId = $('#editSubject').val();

		var name = $('#editNamet').val();
		var phone = $('#editPhone').val();
		var mail = $('#editMail').val();
		var workplace = $('#editWorkPlace').val();

		var addOfficePhone = $('#editOfficePhone').val();
		var addZhiWu = $('#editZhiWu').val();
		var addZhiCheng = $('#editZhiCheng').val();
		var addDipCode = $('#editDipCode').val();
		var addBeiZhu = $('#editBeiZhu').val();
		
		var edit_items = $("#editDiv [name^=edit]");
		for (var i = 0; i < edit_items.length; i++) {
			qty = edit_items[i];
			if ($(qty).val() == '') {
				alert("请检查要输入的必填项！");
				return;
			}
		}

		var provinceName = $('#editProvince option:selected').text();

		$.post('trainee_trainHistoryEdit.action', {
			'provinceId' : provinceId,
			'parentId' : parentId,
			'projectId' : projectId,
			'unitId' : unitId,
			'subjectId' : subjectId,
			'name' : name,
			'phone' : phone,
			'mail' : mail,
			'tid' : selectId,
			'workplace' : workplace,
			'officephone' : addOfficePhone,
			'zhiwu' : addZhiWu,
			'zhicheng' : addZhiCheng,
			'beizhu' : addBeiZhu,
			'dipcode' : addDipCode,
			'method' : 'edit'
		}, function(e, t) {
			if (e != false) {
				$("#list2").jqGrid('setRowData', selectId, {
					'provinceName' : provinceName,
					'parentName' : parentId,
					'projectName' : projectId,
					'unitName' : unitId,
					'subjectName' : subjectId,
					'name' : name,
					'phone' : phone,
					'mail' : mail,
					'workplace' : workplace,
					'telphone' : addOfficePhone,
					'zhiwu' : addZhiWu,
					'zhicheng' : addZhiCheng,
					'dpcode' : addDipCode,
					'beizhu' : addBeiZhu
				}, 'last');
			} else {
				alert('添加失败');
			}

		});

		var addDlg = $("#editDiv");
		addDlg.dialog().dialog("close");
		$('#editNamet').attr("value", "");
		$('#editPhone').attr("value", "");
		$('#editMail').attr("value", "");
		$('#editWorkPlace').attr("value", "");
		$('#editDipcode').attr("value", "");
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

	$("#editDiv").dialog({
		autoOpen : false, // 是否自动弹出窗口
		modal : true, // 设置为模态对话框
		resizable : true,
		width : 500,
		height : 500,
		buttons : {
			确定 : editProjectOK
		},
		position : "center" // 窗口显示的位置
	});

	// 最后

});
