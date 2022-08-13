$(function() {

	// 页面加载结束执行

	var selectId = "";
	var projects = "";
	var provinces = "";

	var ids = new Array();// 被选中的行id

	function initGrid() {

		$("#list2").jqGrid({
			url : "exp_expertList.action",
			datatype : 'json',
			mtype : 'POST',

			height : 'auto',
			altRows : 'true',

			colNames : [ '登陆ID', '姓名', '状态', '工作单位', '所学专业', '职称' ],

			colModel : [ {
				name : 'loginId',
				index : 'loginId',
				search : false,
				width : 50
			}, {
				name : 'name',
				index : 'name',
				search : true,
				width : 80
			}, {
				name : 'status',
				index : 'status',
				search : true,
				width : 100,
				stype : 'select',
				editoptions : {
					value : provinces
				}
			}, {
				name : 'workplace',
				index : 'workplace',
				search : false,
				width : 100,
				frozen : true
			}, {
				name : 'zhuanye',
				index : 'zhuanye',
				search : false,
				width : 100,
				frozen : true
			}, {
				name : 'zhicheng',
				index : 'zhicheng',
				search : false,
				width : 50
			} ],

			rowNum : 50,
			rowList : [ 50, 100, 500 ],
			pager : '#pager2',
			viewrecords : true,
			sortname : 'id',
			sortorder : "desc",
			sortable : false,
			caption : "专家列表",
			multiselect : true, // 多选框
			multiboxonly : false,
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
			}

		});

		$("#list2").jqGrid('navGrid', '#pager2', {
			edit : false,
			add : false,
			del : false,
			search : false
		});

		$("#list2").jqGrid('setFrozenColumns'); // 锁定类 frozen:true
		$("#list2").jqGrid('filterToolbar', {
			searchOperators : true
		});

		$('#list2')
				.navButtonAdd(
						'#pager2',
						{
							caption : '指定为评审专家',
							buttonicon : "ui-icon-plusthick",
							onClickButton : function() {
								if (ids.length == 0) {
									alert("请先选择一个专家进行分配!");
								} else {
									var pIds = "";
									for (var i = 0; i < ids.length; i++) {
										if (i < ids.length - 1) {
											pIds += ids[i] + ",";
										} else {
											pIds += ids[i];
										}
									}
									$
											.post(
													'exp_expertAllot.action',
													{
														"pid" : pIds,
														"method" : "add"
													},
													function(r) {
														if (r) {
															self.location = "/mw_gpjh/vote/adminDeclareManager.jsp";
														}
													});
								}

							},
							position : "last"
						});

		$('#list2').navButtonAdd('#pager2', {
			caption : '重置密码',
			buttonicon : "ui-icon-plusthick",
			onClickButton : function() {
				if (ids.length == 0) {
					alert("请先选择一个专家进行分配!");
				} else {
					var pIds = "";
					for (var i = 0; i < ids.length; i++) {
						if (i < ids.length - 1) {
							pIds += ids[i] + ",";
						} else {
							pIds += ids[i];
						}
					}
					$.post('exp_expertPassword.action',
							{
								"pid" : pIds,
							},
							function(r) {
								if (r) {
									alert("密码重置成功！");
								}
							});
				}
			},
			position : "last"
		});

	}

	$.getJSON("exp_headSerach.action", function(r) {

		var preInitData = r.preInitData;
		var province = preInitData.province;
		for (var i = 0; i < province.length; i++) {
			if (i < province.length - 1) {
				provinces += province[i].id + ":" + province[i].name + ";";
			} else {
				provinces += province[i].id + ":" + province[i].name;
			}
		}

		initGrid();

	});

});