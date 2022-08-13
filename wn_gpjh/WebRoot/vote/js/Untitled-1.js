// JavaScript Document
$(document)
		.ready(
				function(e) {
					var ids = new Array();// 被选中的行id
					var paperId = getUrlParam("paperId");
					var PostData = {
						paperId : paperId
					};
					var userData = null;
					var projectString = null;
					var projectMap = "";// 所有项目
					var trainingUnit = "";// 培训单位
					var course = "";// 科目
					$
							.getJSON(
									"paper_doHeadSearch.action?paperid="
											+ paperId,
									function(data) {
										preInitData = data.preInitData;
										// alert(preInitDate.project);
										var projects = preInitData.project;
										for ( var i = 0; i < projects.length; i++) {
											if (i < projects.length - 1) {
												projectMap += projects[i].id
														+ ":"
														+ projects[i].title
														+ ";";
											} else {
												projectMap += projects[i].id
														+ ":"
														+ projects[i].title;
											}
										}
										;
										var trainingUnits = preInitData.trainingUnit;
										for ( var i = 0; i < trainingUnits.length; i++) {
											if (i < trainingUnits.length - 1) {
												trainingUnit += trainingUnits[i].id
														+ ":"
														+ trainingUnits[i].title
														+ ";";
											} else {
												trainingUnit += trainingUnits[i].id
														+ ":"
														+ trainingUnits[i].title;
											}

										}
										;
										var croses = preInitData.course;
										for ( var i = 0; i < croses.length; i++) {
											if (i < croses.length - 1) {
												course += croses[i].id + ":"
														+ croses[i].title + ";";
											} else {
												course += croses[i].id + ":"
														+ croses[i].title;
											}

										}
										;

										initGrid();
									});

					function initGrid() {
						jQuery("#list2")
								.jqGrid(
										{
											// url:"doTel.action?paperId="+paperId,
											url : "paper_doTel.action?paperid="
													+ paperId,
											datatype : 'json',
											altRows : 'true',
											height : "auto",
											total : 44,
											records : 12,
											colNames : [ 'ids', '用户名', '手机号码',
													'所属项目', '培训单位', '科目',
													'短信状态', '问卷状态', '结业状态' ],
											colModel : [
													{
														name : 'ids',
														index : 'ids',
														width : 55,
														frozen : true
													},
													{
														name : 'userName',
														index : 'userName',
														width : 55,
														frozen : true
													},
													{
														name : 'mobile',
														index : 'mobile',
														width : 100
													},
													{
														name : 'project',
														index : 'project',
														width : 150,
														stype : 'select',
														editoptions : {
															value : ""
																	+ projectMap
																	+ ""
														}
													},
													{
														name : 'trainingUnit',
														index : 'trainingUnit',
														width : 150,
														stype : 'select',
														editoptions : {
															value : ""
																	+ trainingUnit
																	+ ""
														}
													},
													{
														name : 'course',
														index : 'course',
														width : 55,
														stype : 'select',
														editoptions : {
															value : "" + course
																	+ ""
														}
													},
													{
														name : 'smsStatus',
														index : 'smsStatus',
														width : 80,
														align : "lift",
														stype : 'select',
														editoptions : {
															value : "3:所有;1:已发送;2:未发送;4:发送失败"
														}
													},
													{
														name : 'paperStatus',
														index : 'paperStatus',
														width : 80,
														align : "lift",
														stype : 'select',
														editoptions : {
															value : "3:所有;1:已填写;2:未填写"
														}
													},
													{
														name : 'gradutedStatus',
														index : 'gradutedStatus',
														width : 80,
														align : "lift",
														stype : 'select',
														editoptions : {
															value : "all:所有;1:未结业;2:已结业"
														}
													} ],

											rowNum : 50,
											rowList : [ 50, 100, 1000, 10000 ],
											pager : '#pager2',
											sortname : 'id',
											viewrecords : true,
											sortorder : "desc",
											viewrecords : true,
											multiselect : true,
											PostData : PostData,
											autowidth : true,
											rownumbers : true,
											caption : "用户短信表",
											loadComplete : function(xhr) {
												userData = $(this)
														.getGridParam(
																'userData');
												projectString = userData.project[0].id
														+ ":"
														+ escape(userData.project[0].title);
												// alert(projectString);
											},
											onSelectAll : function(rowids,
													statue) {
												if (statue) {
													ids = rowids;
												} else {
													ids = [];

												}
												var s = "";

											},

											onSelectRow : function(rowid,
													status, e) {
												// alert(rowid+","+status);
												if (status) {
													ids.push(rowid);

												} else {
													for ( var i = 0; i < ids.length; i++) {
														if (ids[i] == rowid) {
															ids.splice(i, 1);

														}
													}
													// delete ids[rowid];
												}
											}

										});
						$("#list2").jqGrid('navGrid', '#pager2', {
							edit : false,
							add : false,
							del : false
						});
						$("#list2").jqGrid('filterToolbar', {
							searchOperators : true
						});
						$("#list2").jqGrid('setFrozenColumns');
						$("#list2")
								.navButtonAdd(
										'#pager2',
										{
											caption : "发送短信",
											buttonicon : "ui-icon-signal",
											onClickButton : function() {
												$("#sendSms")
														.dialog(
																{
																	modal : true,
																	buttons : {

																		确定 : function() {
																			// 获取所有选中行的id
																			var pIds = "";
																			// 获取短信内容
																			var content = "";
																			// 获取签名
																			var sign = "";
																			for ( var i = 0; i < ids.length; i++) {
																				if (i < ids.length - 1) {
																					pIds += ids[i]
																							+ ",";
																				} else {
																					pIds += ids[i];
																				}
																			}
																			// alert(pIds);
																			content = $(
																					this)
																					.find(
																							'#content')
																					.val();
																			if (content == "") {
																				alert("请填写短信内容！！")
																				$(
																						this)
																						.find(
																								'#content')
																						.focus();
																				return false;
																			}
																			// alert(content);
																			sign = $(
																					this)
																					.find(
																							'#sign')
																					.val();
																			// alert(sign);
																			$
																					.post(
																							"paper_doSendSms.action",
																							{
																								"pId" : pIds,
																								"smsContent" : content,
																								"sign" : sign
																							})

																			$(
																					this)
																					.dialog(
																							"close");
																			alert("短信发送有一定延时，请等待一段时间后刷新本页面。")
																		}
																	}
																});

											},
											position : "last"
										});

						$("#list2")
								.navButtonAdd(
										'#pager2',
										{
											caption : "导出数据",
											buttonicon : "ui-icon-extlink",
											onClickButton : function() {
												bsuExportCsv("paper_doExcel.action?paperid="
														+ paperId);
											},

											position : "last"
										});
					}

					// 带入url根据查询的数据返回csv
					function bsuExportCsv(url) {
						// 如果页面中没有用于下载iframe，增加iframe到页面中
						if ($('#downloadcsv').length <= 0)
							$('body')
									.append(
											"<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
						$('#downloadcsv').attr('src', url);
					}

					// 获取url参数
					function getUrlParam(name) {
						var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象

						var r = window.location.search.substr(1).match(reg); // 匹配目标参数

						if (r != null)
							return unescape(r[2]);

						return null; // 返回参数值

					}

				});