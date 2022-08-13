	function changeArea(th,e){
		var ide = $(th).val();
		
		$.getJSON("trainee_areasearch.action?type=" + e + "&id=" + ide +"&userid="+ userid + "&role="
				+ role ,
				function(r) {

					var preInitData = r.preInitData;

					if(e == "province"){
						var cityHtml = "";
						var city = preInitData.area;
						for (var i = 0; i < city.length; i++) {

							var ht = '<option value="' + city[i].id + '">'
									+ city[i].name + '</option>';
							cityHtml += ht;
						}
						$("#addCity").html(cityHtml);
					}else{
						var countyHtml = "";
						var county = preInitData.area;
						for (var i = 0; i < county.length; i++) {

							var ht = '<option value="' + county[i].id + '">'
									+ county[i].name + '</option>';
							countyHtml += ht;

						}
						$("#addCounty").html(countyHtml);
					}
					
				});
	}
	function changeArea2(th,e){
		var ide = $(th).val();
		
		$.getJSON("trainee_areasearch.action?type=" + e + "&id=" + ide +"&userid="+ userid + "&role="
				+ role ,
				function(r) {

					var preInitData = r.preInitData;

					if(e == "province"){
						var cityHtml = "";
						var city = preInitData.area;
						for (var i = 0; i < city.length; i++) {

							var ht = '<option value="' + city[i].id + '">'
									+ city[i].name + '</option>';
							cityHtml += ht;
						}
						$("#editCity").html(cityHtml);
					}else{
						var countyHtml = "";
						var county = preInitData.area;
						for (var i = 0; i < county.length; i++) {

							var ht = '<option value="' + county[i].id + '">'
									+ county[i].name + '</option>';
							countyHtml += ht;

						}
						$("#editCounty").html(countyHtml);
					}
					
				});
	}
$(function() {

	// 页面加载结束执行

	var selectId = "";
	var provinces = "";
	var cities = "";
	var counties = "";
	var projects = "";
	var parents = "";
	var subs="";
	
	var unitAttributes = "";
	var unitTypes = "";
	var folks = "";
	var jobTitles = "";
	var mainGrades = "";
	var mainSubjects = "";
	var educations = "";

	var ids = new Array();// 被选中的行id

	function initGrid() {

		$("#list2").jqGrid(
				{
					url : "trainee_list.action?userid=" + userid + "&role="
							+ role,
					datatype : 'json',
					mtype : 'POST',

					height : 'auto',
					altRows : 'true',

					colNames : [ '所属地区（省）', '所属地区（市）', '所属地区（县）', '所属项目', '子项目', '培训单位', '学科', '姓名','身份证号', '手机',
							'电子邮件', '工作单位', '学校所在区域','学校类别', '民族', '职称', '主要任教学段', '主要任教学科', '最高学历', '毕业院校','所学专业','证书编号','状态' ],

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
						name : 'cityName',
						index : 'cityName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : cities
						}
					},{
						name : 'countyName',
						index : 'countyName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : counties
						}
					},{
						name : 'parentName',
						index : 'parentName',
						width : 200,
						frozen : true,
						stype : 'select',
						editoptions : {
							value : parents
						}
					}, {
						name : 'projectName',
						index : 'projectName',
						width : 200,
						frozen : true,
						stype : 'select',
						editoptions : {
							value : projects
						}
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
						width : 100,
						stype : 'select',
						editoptions : {
							value : subs
						}
					}, {
						name : 'name',
						index : 'name',
						search : true,
						align : 'center',
						width : 50

					}, {
						name : 'idcard',
						index : 'idcard',
						search : true,
						align : 'center',
						width : 100

					}, {
						name : 'phone',
						index : 'phone',
						align : 'center',
						search : false,
						width : 65

					}, {
						name : 'mail',
						index : 'mail',
						align : 'center',
						search : false,
						width : 80

					}, {
						name : 'workplace',
						index : 'workplace',
						align : 'center',
						search : false,
						width : 150

					}, {
						name : 'unitAttributeName',
						index : 'unitAttributeName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : unitAttributes
						}
					}, {
						name : 'unitTypeName',
						index : 'unitTypeName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : unitTypes
						}
					},{
						name : 'folkName',
						index : 'folkName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : folks
						}
					},{
						name : 'jobTitleName',
						index : 'jobTitleName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : jobTitles
						}
					},{
						name : 'mainGradeName',
						index : 'mainGradeName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : mainGrades
						}
					},{
						name : 'mainSubjectName',
						index : 'mainSubjectName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : mainSubjects
						}
					},{
						name : 'educationName',
						index : 'educationName',
						width : 50,
						align : 'center',
						stype : 'select',
						editoptions : {
							value : educations
						}
					},{
						name : 'graduation',
						index : 'graduation',
						search : false,
						align : 'center',
						width : 50

					}, {
						name : 'major',
						index : 'major',
						search : false,
						align : 'center',
						width : 50

					}, {
						name : 'dipcode',
						index : 'dipcode',
						align : 'center',
						search : false,
						width : 100,
						frozen : true
					}, {
						name : 'status',
						index : 'status',
						align : 'center',
						search : true,
						width : 80,
						stype : 'select',
						editoptions : {
							value : "0:所有;1:未结业;2:已结业"
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
							selectId=rowid;

						} else {
							for (var i = 0; i < ids.length; i++) {
								if (ids[i] == rowid) {
									ids.splice(i, 1);
									break;
								}
							}
							selectId="";
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
				$('#downloadcsv').attr('src', 'trainee_importList.action');
				
			},
			position : "last"
		});
		
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
			$('#list2').navButtonAdd('#pager2', {
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

								if (status == "已结业") {
									alert("学员已经通过,您无法删除,请联系超级管理员！");
									return;
								}

								if (i < ids.length - 1) {
									pIds += ids[i] + ",";
								} else {
									pIds += ids[i];
								}
							}

							$.post('trainee_trainDelete.action', {
								'id' : pIds
							}, function(r) {
								$("#list2").trigger("reloadGrid");
								ids = new Array();
							})
						}
					}
				},
				position : "last"
			});

		} else {
			$('#list2').navButtonAdd('#pager2', {
				caption : '设置未结业',
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
					$.post('trainee_trainJY.action', {
						'id' : pIds,
						'method' : 'nojieye'
					}, function(e, t) {
						$("#list2").trigger("reloadGrid");
						ids = [];
					});
				},
				position : "last"
			});

			$('#list2').navButtonAdd('#pager2', {
				caption : '设置已结业',
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
					$.post('trainee_trainJY.action', {
						'id' : pIds,
						'method' : 'jieye'
					}, function(e, t) {
						$("#list2").trigger("reloadGrid");
						ids = [];
					});
				},
				position : "last"
			});	
			
			
		}
	}

	function getHead(e,t){
		$.getJSON("trainee_headsearch.action?userid=" + userid + "&role="
				+ role + "&province="+e +"&city="+t,
				function(r) {

					var preInitData = r.preInitData;

					var provinceHtml = "";
					var province = preInitData.province;
					for (var i = 0; i < province.length; i++) {

						var ht = '<option value="' + province[i].id + '">'
								+ province[i].name + '</option>';
						provinceHtml += ht;

						if (i < province.length - 1) {
							provinces += province[i].id + ":"
									+ province[i].name + ";";
						} else {
							provinces += province[i].id + ":"
									+ province[i].name;
						}
					}
					//新增地区市、县信息
					var cityHtml = "";
					var city = preInitData.city;
					for (var i = 0; i < city.length; i++) {

						var ht = '<option value="' + city[i].id + '">'
								+ city[i].name + '</option>';
						cityHtml += ht;

						if (i < city.length - 1) {
							cities += city[i].id + ":"
									+ city[i].name + ";";
						} else {
							cities += city[i].id + ":"
									+ city[i].name;
						}
					}
					if(t == "all" || e == "all"){
						$("#gs_cityName").html(cityHtml);
					}
					
					var countyHtml = "";
					var county = preInitData.county;
					for (var i = 0; i < county.length; i++) {

						var ht = '<option value="' + county[i].id + '">'
								+ county[i].name + '</option>';
						countyHtml += ht;

						if (i < county.length - 1) {
							counties += county[i].id + ":"
									+ county[i].name + ";";
						} else {
							counties += county[i].id + ":"
									+ county[i].name;
						}
					}
					$("#gs_countyName").html(countyHtml);
				});
	}
	
	$.getJSON("trainee_headsearch.action?userid=" + userid + "&role="
					+ role,
					function(r) {

						var preInitData = r.preInitData;

						var provinceHtml = "";
						var province = preInitData.province;
						for (var i = 0; i < province.length; i++) {

							var ht = '<option value="' + province[i].id + '">'
									+ province[i].name + '</option>';
							provinceHtml += ht;

							if (i < province.length - 1) {
								provinces += province[i].id + ":"
										+ province[i].name + ";";
							} else {
								provinces += province[i].id + ":"
										+ province[i].name;
							}
						}
						
						//新增地区市、县信息
						var cityHtml = "";
						var city = preInitData.city;
						for (var i = 0; i < city.length; i++) {

							var ht = '<option value="' + city[i].id + '">'
									+ city[i].name + '</option>';
							cityHtml += ht;

							if (i < city.length - 1) {
								cities += city[i].id + ":"
										+ city[i].name + ";";
							} else {
								cities += city[i].id + ":"
										+ city[i].name;
							}
						}
						
						var countyHtml = "";
						var county = preInitData.county;
						for (var i = 0; i < county.length; i++) {

							var ht = '<option value="' + county[i].id + '">'
									+ county[i].name + '</option>';
							countyHtml += ht;

							if (i < county.length - 1) {
								counties += county[i].id + ":"
										+ county[i].name + ";";
							} else {
								counties += county[i].id + ":"
										+ county[i].name;
							}
						}
						
						var unitAttributeHtml = "";
						var unitAttribute = preInitData.unitAttribute;
						for (var i = 0; i < unitAttribute.length; i++) {

							var ht = '<option value="' + unitAttribute[i].id + '">'
									+ unitAttribute[i].name + '</option>';
							unitAttributeHtml += ht;

							if (i < unitAttribute.length - 1) {
								unitAttributes += unitAttribute[i].id + ":"
										+ unitAttribute[i].name + ";";
							} else {
								unitAttributes += unitAttribute[i].id + ":"
										+ unitAttribute[i].name;
							}
						}
						
						var unitTypeHtml = "";
						var unitType = preInitData.unitType;
						for (var i = 0; i < unitType.length; i++) {

							var ht = '<option value="' + unitType[i].id + '">'
									+ unitType[i].name + '</option>';
							unitTypeHtml += ht;

							if (i < unitType.length - 1) {
								unitTypes += unitType[i].id + ":"
										+ unitType[i].name + ";";
							} else {
								unitTypes += unitType[i].id + ":"
										+ unitType[i].name;
							}
						}
						
						var folkHtml = "";
						var folk = preInitData.folk;
						for (var i = 0; i < folk.length; i++) {

							var ht = '<option value="' + folk[i].id + '">'
									+ folk[i].name + '</option>';
							folkHtml += ht;

							if (i < folk.length - 1) {
								folks += folk[i].id + ":"
										+ folk[i].name + ";";
							} else {
								folks += folk[i].id + ":"
										+ folk[i].name;
							}
						}
						
						var jobTitleHtml = "";
						var jobTitle = preInitData.jobTitle;
						for (var i = 0; i < jobTitle.length; i++) {

							var ht = '<option value="' + jobTitle[i].id + '">'
									+ jobTitle[i].name + '</option>';
							jobTitleHtml += ht;

							if (i < jobTitle.length - 1) {
								jobTitles += jobTitle[i].id + ":"
										+ jobTitle[i].name + ";";
							} else {
								jobTitles += jobTitle[i].id + ":"
										+ jobTitle[i].name;
							}
						}
						
						var mainGradeHtml = "";
						var mainGrade = preInitData.mainGrade;
						for (var i = 0; i < mainGrade.length; i++) {

							var ht = '<option value="' + mainGrade[i].id + '">'
									+ mainGrade[i].name + '</option>';
							mainGradeHtml += ht;

							if (i < mainGrade.length - 1) {
								mainGrades += mainGrade[i].id + ":"
										+ mainGrade[i].name + ";";
							} else {
								mainGrades += mainGrade[i].id + ":"
										+ mainGrade[i].name;
							}
						}
						
						var mainSubjectHtml = "";
						var mainSubject = preInitData.mainSubject;
						for (var i = 0; i < mainSubject.length; i++) {

							var ht = '<option value="' + mainSubject[i].id + '">'
									+ mainSubject[i].name + '</option>';
							mainSubjectHtml += ht;

							if (i < mainSubject.length - 1) {
								mainSubjects += mainSubject[i].id + ":"
										+ mainSubject[i].name + ";";
							} else {
								mainSubjects += mainSubject[i].id + ":"
										+ mainSubject[i].name;
							}
						}
						
						var educationHtml = "";
						var education = preInitData.education;
						for (var i = 0; i < education.length; i++) {

							var ht = '<option value="' + education[i].id + '">'
									+ education[i].name + '</option>';
							educationHtml += ht;

							if (i < education.length - 1) {
								educations += education[i].id + ":"
										+ education[i].name + ";";
							} else {
								educations += education[i].id + ":"
										+ education[i].name;
							}
						}
						

						var projectHtml = "";
						var project = preInitData.project;
						for (var i = 0; i < project.length; i++) {
							if (i < project.length - 1) {
								projects += project[i].id + ":"
										+ project[i].name + ";";
							} else {
								projects += project[i].id + ":"
										+ project[i].name;
							}

							var ht = '<option value="' + project[i].id + '">'
									+ project[i].name + '</option>';
							projectHtml += ht;
						}

						var parentHtml = "";
						var parent = preInitData.parent;
						for (var i = 0; i < parent.length; i++) {
							if (i < parent.length - 1) {
								parents += parent[i].id + ":" + parent[i].name
										+ ";";
							} else {
								parents += parent[i].id + ":" + parent[i].name;
							}
							var ht = '<option value="' + parent[i].id + '">'
									+ parent[i].name + '</option>';
							parentHtml += ht;
						}

						var unitHtml = "";
						var units = preInitData.unit;
						for (var i = 0; i < units.length; i++) {

							var ht = '<option value="' + units[i].id + '">'
									+ units[i].name + '</option>';
							unitHtml += ht;
						}

						var subjectHtml = "";
						var subjects = preInitData.subject;
						for (var i = 0; i < subjects.length; i++) {
							
							if (i < subjects.length - 1) {
								subs += subjects[i].id + ":"+ subjects[i].name + ";";
							} else {
								subs += subjects[i].id + ":"+ subjects[i].name;
							}

							var ht = '<option value="' + subjects[i].id + '">'
									+ subjects[i].name + '</option>';
							subjectHtml += ht;
						}

						$('#addProvince').html(provinceHtml);
						$('#addCity').html(cityHtml);
						$('#addCounty').html(countyHtml);
						$('#addProjectParent').html(parentHtml);
						$('#addProject').html(projectHtml);
						$('#addUnit').html(unitHtml);
						$('#addSubject').html(subjectHtml);
						
						$('#addUnitAttribute').html(unitAttributeHtml);
						$('#addUnitType').html(unitTypeHtml);
						$('#addFolk').html(folkHtml);
						$('#addJobTitle').html(jobTitleHtml);
						$('#addMainGrade').html(mainGradeHtml);
						$('#addMainSubject').html(mainSubjectHtml);
						$('#addEducation').html(educationHtml);
						

						$('#editProvince').html(provinceHtml);
						$('#editCity').html(cityHtml);
						$('#editCounty').html(countyHtml);
						$('#editProjectParent').html(parentHtml);
						$('#editProject').html(projectHtml);
						$('#editUnit').html(unitHtml);
						$('#editSubject').html(subjectHtml);
						
						$('#editUnitAttribute').html(unitAttributeHtml);
						$('#editUnitType').html(unitTypeHtml);
						$('#editFolk').html(folkHtml);
						$('#editJobTitle').html(jobTitleHtml);
						$('#editMainGrade').html(mainGradeHtml);
						$('#editMainSubject').html(mainSubjectHtml);
						$('#editEducation').html(educationHtml);

						initGrid();
						$("#gs_provinceName").change(function(){
							var province0 = $("#gs_provinceName").val();
							var city0 = $("#gs_cityName").val();
							getHead(province0,city0);
						})
						$("#gs_cityName").change(function(){
							var province0 = $("#gs_provinceName").val();
							var city0 = $("#gs_cityName").val();
							getHead(province0,city0);
						})
					});

	function addProject() {
		var addDlg = $("#addDiv");
		addDlg.dialog("option", "title", "新增学员信息").dialog("open");
	}

	function addProjectOK() {

		var provinceId = $('#addProvince option:selected').val();
		var parentId = $('#addProjectParent option:selected').val();
		var projectId = $('#addProject option:selected').val();
		var unitId = $('#addUnit option:selected').val();
		var subjectId = $('#addSubject option:selected').val();
		var name = $('#addNamet').val();
		var phone = $('#addPhone').val();
		var mail = $('#addMail').val();
		var tel = $('#addTelephone').val();
		var workplace = $('#addWorkPlace').val();
		var dipcode = $('#addDipcode').val();

		var provinceName = $('#addProvince option:selected').text();
		var parentName = $('#addProjectParent option:selected').text();
		var projectName = $('#addProject option:selected').text();
		var unitName = $('#addUnit option:selected').text();
		var subjectName = $('#addSubject option:selected').text();
		
		var cityId = $('#addCity option:selected').val();
		var cityName = $('#addCity option:selected').text();
		var countyId = $('#addCounty option:selected').val();
		var countyName = $('#addCounty option:selected').text();
		var unitAttributeId = $('#addUnitAttribute option:selected').val();
		var unitTypeId = $('#addUnitType option:selected').val();
		var unitAttributeName = $('#addUnitAttribute option:selected').text();
		var unitTypeName = $('#addUnitType option:selected').text();
		var folkId = $('#addFolk option:selected').val();
		var folkName = $('#addFolk option:selected').text();
		var jobTitleId = $('#addJobTitle option:selected').val();
		var jobTitleName = $('#addJobTitle option:selected').text();
		var mainGradeId = $('#addMainGrade option:selected').val();
		var mainGradeName = $('#addMainGrade option:selected').text();
		var mainSubjectId = $('#addMainSubject option:selected').val();
		var mainSubjectName = $('#addMainSubject option:selected').text();
		var educationId = $('#addEducation option:selected').val();
		var educationName = $('#addEducation option:selected').text();
		
		var idcard = $('#addIdcard').val();
		var graduation = $('#addGraduation').val();
		var major = $('#addMajor').val();
//		var hiredate = $('#addHiredate').val();
		
		

		$.post('trainee_trainEdit.action', {
			'provinceId' : provinceId,
			'cityId' : cityId,
			'countyId' : countyId,
			'unitAttributeId' : unitAttributeId,
			'unitTypeId' : unitTypeId,
			'folkId' : folkId,
			'jobTitleId' : jobTitleId,
			'mainGradeId' : mainGradeId,
			'mainSubjectId' : mainSubjectId,
			'educationId' : educationId,
			'parentId' : parentId,
			'projectId' : projectId,
			'unitId' : unitId,
			'subjectId' : subjectId,
			'name' : name,
			'idcard' : idcard,
			'graduation' : graduation,
			'major' : major,
//			'hiredate' : hiredate,
			'phone' : phone,
			'mail' : mail,
			'tel' : tel,
			'workplace' : workplace,
			'dipcode' : dipcode,
			'method' : 'add'
		}, function(e, t) {
			if (e != false) {
				var id = e.id;
				$("#list2").jqGrid('addRowData', id, {
					'provinceName' : provinceName,
					'cityName' : cityName,
					'countyName' : countyName,
					'unitAttributeName' : unitAttributeName,
					'unitTypeName' : unitTypeName,
					'folkName' : folkName,
					'jobTitleName' : jobTitleName,
					'mainGradeName' : mainGradeName,
					'mainSubjectName' : mainSubjectName,
					'educationName' : educationName,
					'parentName' : parentName,
					'projectName' : projectName,
					'unitName' : unitName,
					'subjectName' : subjectName,
					'name' : name,
					'idcard' : idcard,
					'graduation' : graduation,
					'major' : major,
//					'hiredate' : hiredate,
					'phone' : phone,
					'mail' : mail,
					'workplace' : workplace,
					'dipcode' : dipcode
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
		$('#addIdcard').attr("value","");
		$('#addGraduation').attr("value","");
		$('#addMajor').attr("value","");
//		$('#addHiredate').attr("value","");
	}
	
	function editProject() {

		if (selectId == "") {
			alert("请先选择一个项目在编辑!");
		} else {

			var rowData = $('#list2').jqGrid('getRowData', selectId);

			var status = rowData['status'];

			if (status == "已结业") {
				alert("学员已经通过,您无法编辑,请联系超级管理员！");
				return;
			}

			var name = rowData['name'];
			var idcard = rowData['idcard'];
			var phone = rowData['phone'];
			var mail = rowData['mail'];
			var workplace = rowData['workplace'];
			var dipcode = rowData['dipcode'];
			
			var graduation = rowData['graduation'];
			var major = rowData['major'];
//			var hiredate = rowData['hiredate'];

			var provinceName = rowData['provinceName'];
			var cityName = rowData['cityName'];
			var countyName = rowData['countyName'];
			var parentName = rowData['parentName'];
			var projectName = rowData['projectName'];
			var unitName = rowData['unitName'];
			var subjectName = rowData['subjectName'];
			
			var unitAttributeName = rowData['unitAttributeName'];
			var unitTypeName = rowData['unitTypeName'];
			var folkName = rowData['folkName'];
			var jobTitleName = rowData['jobTitleName'];
			var mainGradeName = rowData['mainGradeName'];
			var mainSubjectName = rowData['mainSubjectName'];
			var educationName = rowData['educationName'];

			$("#editProvince option").each(function(index) {
				if ($(this).text() == provinceName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editCity option").each(function(index) {
				if ($(this).text() == cityName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editCounty option").each(function(index) {
				if ($(this).text() == countyName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editProjectParent option").each(function(index) {
				if ($(this).text() == parentName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editProject option").each(function(index) {
				if ($(this).text() == projectName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editUnit option").each(function(index) {
				if ($(this).text() == unitName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editSubject option").each(function(index) {
				if ($(this).text() == subjectName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editUnitAttribute option").each(function(index) {
				if ($(this).text() == unitAttributeName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editUnitType option").each(function(index) {
				if ($(this).text() == unitTypeName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editFolk option").each(function(index) {
				if ($(this).text() == folkName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editJobTitle option").each(function(index) {
				if ($(this).text() == jobTitleName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editMainGrade option").each(function(index) {
				if ($(this).text() == mainGradeName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editMainSubject option").each(function(index) {
				if ($(this).text() == mainSubjectName) {
					$(this).attr('selected', 'selected');
				}
			});
			$("#editEducation option").each(function(index) {
				if ($(this).text() == educationName) {
					$(this).attr('selected', 'selected');
				}
			});

			$('#editNamet').attr("value", name);
			$('#editPhone').attr("value", phone);
			$('#editMail').attr("value", mail);
			$('#editWorkPlace').attr("value", workplace);
			$('#editDipcode').attr("value", dipcode);
			
			$('#editIdcard').attr("value", idcard);
			$('#editGraduation').attr("value", graduation);
			$('#editMajor').attr("value", major);
//			$('#editHiredate').attr("value", hiredate);

			var editDlg = $("#editDiv");
			editDlg.dialog("option", "title", "编辑学员信息").dialog("open");
		}

	}

	function editProjectOK() {
		$('#editProject').removeAttr('disabled');
		$('#editUnit').removeAttr('disabled');
		$('#editSubject').removeAttr('disabled');
		var provinceId = $('#editProvince option:selected').val();
		var parentId = $('#editProjectParent option:selected').val();
		var projectId = $('#editProject option:selected').val();
		var unitId = $('#editUnit option:selected').val();
		var subjectId = $('#editSubject option:selected').val();

		var name = $('#editNamet').val();
		var phone = $('#editPhone').val();
		var mail = $('#editMail').val();
		var workplace = $('#editWorkPlace').val();
		var dipcode = $('#editDipcode').val();

		var provinceName = $('#editProvince option:selected').text();
		var parentName = $('#editProjectParent option:selected').text();
		var projectName = $('#editProject option:selected').text();
		var unitName = $('#editUnit option:selected').text();
		var subjectName = $('#editSubject option:selected').text();
		
		var cityId = $('#editCity option:selected').val();
		var cityName = $('#editCity option:selected').text();
		var countyId = $('#editCounty option:selected').val();
		var countyName = $('#editCounty option:selected').text();
		var unitAttributeId = $('#editUnitAttribute option:selected').val();
		var unitAttributeName = $('#editUnitAttribute option:selected').text();
		var unitTypeId = $('#editUnitType option:selected').val();
		var unitTypeName = $('#editUnitType option:selected').text();
		var folkId = $('#editFolk option:selected').val();
		var folkName = $('#editFolk option:selected').text();
		var jobTitleId = $('#editJobTitle option:selected').val();
		var jobTitleName = $('#editJobTitle option:selected').text();
		var mainGradeId = $('#editMainGrade option:selected').val();
		var mainGradeName = $('#editMainGrade option:selected').text();
		var mainSubjectId = $('#editMainSubject option:selected').val();
		var mainSubjectName = $('#editMainSubject option:selected').text();
		var educationId = $('#editEducation option:selected').val();
		var educationName = $('#editEducation option:selected').text();
		
		var idcard = $('#editIdcard').val();
		var graduation = $('#editGraduation').val();
		var major = $('#editMajor').val();
//		var hiredate = $('#editHiredate').val();

		$.post('trainee_trainEdit.action', {
			'provinceId' : provinceId,
			'cityId' : cityId,
			'countyId' : countyId,
			'unitAttributeId' : unitAttributeId,
			'unitTypeId' : unitTypeId,
			'folkId' : folkId,
			'jobTitleId' : jobTitleId,
			'mainGradeId' : mainGradeId,
			'mainSubjectId' : mainSubjectId,
			'educationId' : educationId,
			'parentId' : parentId,
			'projectId' : projectId,
			'unitId' : unitId,
			'subjectId' : subjectId,
			'name' : name,
			'idcard' : idcard,
			'graduation' : graduation,
			'major' : major,
//			'hiredate' : hiredate,
			'parentId' : parentId,
			'projectId' : projectId,
			'unitId' : unitId,
			'subjectId' : subjectId,
			'name' : name,
			'phone' : phone,
			'mail' : mail,
			'tid' : selectId,
			'workplace' : workplace,
			'dipcode' : dipcode,
			'method' : 'edit'
		}, function(e, t) {
			$('#editProject').attr('disabled','disabled');
			$('#editUnit').attr('disabled','disabled');
			$('#editSubject').attr('disabled','disabled');
			if (e != false) {
				$("#list2").jqGrid('setRowData', selectId, {
					'provinceName' : provinceName,
					'cityName' : cityName,
					'countyName' : countyName,
					'unitAttributeName' : unitAttributeName,
					'unitTypeName' : unitTypeName,
					'folkName' : folkName,
					'jobTitleName' : jobTitleName,
					'mainGradeName' : mainGradeName,
					'mainSubjectName' : mainSubjectName,
					'educationName' : educationName,
					'parentName' : parentName,
					'projectName' : projectName,
					'unitName' : unitName,
					'subjectName' : subjectName,
					'name' : name,
					'idcard' : idcard,
					'graduation' : graduation,
					'major' : major,
//					'hiredate' : hiredate,
					'phone' : phone,
					'mail' : mail,
					'workplace' : workplace,
					'dipcode' : dipcode
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
		$('#editIdcard').attr("value","");
		$('#editGraduation').attr("value","");
		$('#editMajor').attr("value","");
//		$('#editHiredate').attr("value","");
	}

	function uploadFile() {

		var addDlg = $("#impDiv");
		addDlg.dialog("option", "title", "上传EXCEL文件").dialog("open");

	}
	
	$("#addDiv").dialog({
		autoOpen : false, // 是否自动弹出窗口
		modal : true, // 设置为模态对话框
		resizable : true,
		width : 550,
		height : 700,
		buttons : {
			确定 : addProjectOK
		},
		position : "center" // 窗口显示的位置
	});

	$("#editDiv").dialog({
		autoOpen : false, // 是否自动弹出窗口
		modal : true, // 设置为模态对话框
		resizable : true,
		width : 550,
		height : 700,
		buttons : {
			确定 : editProjectOK
		},
		position : "center" // 窗口显示的位置
	});

	// 最后
});
