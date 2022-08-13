//学段
function changeGradebtn(e) {
	e.next('.listSub').toggle();
}

// 学段
function getsnode(bid,bname) {
	var cUrl = '../gradeList'
	bid = (typeof (bid) == 'undefined') ? '' : bid;
	bname = (typeof (bname) == 'undefined') ? '' : bname+'>';
	var e = (bid) ? $('#bido' + bid) : $('#bido');
	cUrl += (bid) ? '?status=1&grade.id=' + bid : '?status=1&grade.id=&level=1';
	if (bid)
		e.css('display') == 'none' ? e.show() : e.hide();
		$.getJSON(cUrl,function(data) {
			var cLis = '';
			if (data.Records.length > 0) {
				$.each(data.Records,
					function(i, c) {
						emClass = (c.haschild) ? ' class="c"' : '';
						//emClass = ' class="c"';
						emClick = (c.haschild) ? ' onclick="getsnode(\''+ c.id +'\',\''+ bname + c.name + '\');changeIcon($(this))"' : '';
						//emClick = ' onclick="getsnode(\''+ c.id + '\');changeIcon($(this))"'
						aClick = ' onclick="setnode(\'' + c.id + '\', \'' + bname + c.name + '\')"';
						cLis += '<div class="listnode" id="' + c.id + '"><em' + emClass + emClick + '></em><a href="javascript:void(0)" ' + aClick + '>' + c.name + '</a><div id="bido' + c.id	+ '" class="cSub" style="display:none">加载中...</div></div>';

					});
			}
			e.html(cLis)
	});
}
// 设定工作单位
function setnode(id, name) {
	$('#bido').html('');
	$('#clId').html(name);
	$('#gradeid').val(id);
	$('.listSub').hide();
	changeSubject(id);
}

// areacity 所属地区 添加项目管理员
function areacityy(t, name) {
	var id = $(t).val();
	name = (typeof (name) == 'undefined') ? 'city' : name;
	var url = '../base/area_getArea.action?' + name + 'Id=' + id;
	$.getJSON(url, function(data) {
		var cLis = '';

		if (data.Result == 'OK') {
			var cLis = '';
			$.each(data.Options, function(i, c) {
				cLis += '<option value="' + c.Value + '">' + c.DisplayText
						+ '</option>';
			});
			if (name == 'city') {
				var cList2 = '<option value="0">请选择...</option>';
				$('#areacounty').html(cList2);
			}
			$('#area' + name).html(cLis);
		}

	})

}

// 所属机构院校
function areaSchool(t, name) {
	var id = $(t).val();
	var area = "";
	var url = "";
	var lastSelect = "";

	if (name == "provinceSchool") {
		area = "cityId";
		url = '../base/area_getArea.action?';
		lastSelect = "<option value='ps'>省直属学校</option>";
	}

	if (name == "citySchool") {
		area = "countyId";
		if ($('#provinceSchool').val() == "ps") {
			$('#school').hide();
			var pid = $('#chinaSchool').val();
			url = "../base/Organization_getSchool.action?pid=" + pid + "&";
		} else {
			$('#school').show();
			url = '../base/area_getArea.action?';
			lastSelect = "<option value='cs'>市直属学校</option>";
		}
	}

	if (name == "school") {

		if ($('#citySchool').val() == "cs") {
			var pid = $('#provinceSchool').val();
			url = "../base/Organization_getSchool.action?pid=" + pid + "&";
			area = "countyId";
		} else {
			var pid = $('#citySchool').val();
			url = "../base/Organization_getSchool.action?pid=" + pid + "&";
			area = "schoolId";
		}
	}
	url = url + area + '=' + id;
	$.getJSON(url, function(data) {
		var cLis = '';
		if (data.Result == 'OK') {
			cLis = '';
			$.each(data.Options, function(i, c) {
				cLis += '<option value="' + c.Value + '">' + c.DisplayText
						+ '</option>';

			});
			cLis += lastSelect;
			$('#' + name).html(cLis);

		}

	});

}

function changeIcon(e) {
	if (e.attr('class') == 'o') {
		e.attr('class', 'c');
	} else {
		e.attr('class', 'o');
	}
}

function showul(t) {
	$('.uul:visible').hide();// clear all show uul(fix bug)
	var uul = t.next('.uul');
	if (uul.is(':visible'))
		uul.hide();
	else
		uul.show();
}
// 设置权限范围
function showform() {
	$('.ufcwrap').empty();
	return false;
}

function activeBtn(cls) {
	$('.' + cls).prop('disabled', false).removeClass('disabledbtn');
}

function resCon(rid) {
	var cUrl = "../admin/projectType_getListByPid";
	rid = (typeof (rid) == 'undefined') ? '' : rid;
	cUrl += (rid) ? '?id=' + rid : '?id=0';
	$
			.getJSON(
					cUrl,
					function(data) {

						if (data.length > 0) {
							var ufc = '<div class="ufc"><span class="usel" onclick="showul($(this))"><strong>请选择...</strong></span><ul class="uul">';
							$
									.each(
											data,
											function(i, c) {
												aClick = (c.haschild == 1) ? ' onclick="resCon(\''
														+ c.id
														+ '\');resspan($(this));"'
														: ' onclick="resspan($(this));activeBtn(\'resBtn\')"';
												ufc += '<li ' + aClick
														+ ' dataid="' + c.id
														+ '" dataname="'
														+ c.name + '">'
														+ c.name + '</li>';
											});
						}
						ufc += '</ul></div>';
						$("#resCon").append(ufc);
					});
	$('.resBtn').prop('disabled', true).addClass('disabledbtn');
}
// 单个项目类型添加 项目信息管理
function resCon2(rid) {
	var cUrl = "../admin/projectType_getListByPid";
	rid = (typeof (rid) == 'undefined') ? '' : rid;
	cUrl += (rid) ? '?id=' + rid : '?id=0';
	$
			.getJSON(
					cUrl,
					function(data) {

						if (data.length > 0) {
							var ufc = '<div class="ufc"><span class="usel" onclick="showul($(this))"><strong>请选择...</strong></span><ul class="uul">';
							$
									.each(
											data,
											function(i, c) {
												aClick = (c.haschild == 1) ? ' onclick="resCon2(\''
														+ c.id
														+ '\');resspan($(this));"'
														: ' onclick="resspan($(this));resUbtn(\'protype\')"';
												ufc += '<li ' + aClick
														+ ' dataid="' + c.id
														+ '" dataname="'
														+ c.name + '">'
														+ c.name + '</li>';
											});
						}
						ufc += '</ul></div>';
						$("#resCon").append(ufc);
					});
	// $('.resBtn').prop('disabled', true).addClass('disabledbtn');
}
// 补录项目信息
function resCon3(rid) {
	var cUrl = "../admin/projectType_getListByPid";
	rid = (typeof (rid) == 'undefined') ? '' : rid;
	cUrl += (rid) ? '?id=' + rid : '?id=0';
	$
			.getJSON(
					cUrl,
					function(data) {

						if (data.length > 0) {
							var ufc = '<div class="ufc"><span class="usel" onclick="showul($(this))"><strong>请选择...</strong></span><ul class="uul">';
							$
									.each(
											data,
											function(i, c) {
												aClick = (c.haschild == 1) ? ' onclick="resCon3(\''
														+ c.id
														+ '\');resspan($(this));"'
														: ' onclick="resspan($(this));resUbtn(\'proapply\');"';
												ufc += '<li ' + aClick
														+ ' dataid="' + c.id
														+ '" dataname="'
														+ c.name + '">'
														+ c.name + '</li>';
											});
						}
						ufc += '</ul></div>';
						$("#resCon").append(ufc);
					});
	// $('.resBtn').prop('disabled', true).addClass('disabledbtn');
}

function resspan(t) {
	var span = t.parent().prev('.usel');
	span.parent().nextAll().remove();
	span.attr({
		'dataid' : t.attr('dataid'),
		'dataname' : t.attr('dataname')
	});
	t.parent().hide();
	span.find('strong').text(t.text());
}
function resUbtn(type) {
	var id = [];
	var names = [];
	type = (typeof (type) == 'undefined') ? '' : type;
	$('#resCon .usel').each(function() {
		if ($(this).attr('dataname')) {
			names.push($(this).attr('dataname'));
			id.push($(this).attr('dataid'));
		}
	});
	var lastid = id.pop();
	aClick = ' onclick="delRes($(this),\'' + type + '\')"';

	if ($('input[name="restrictRightId"]').length > 0) {// 避免添加相同的权限
		var rightid_array = [];

		$('input[name="restrictRightId"]').each(function() {
			rightid_array.push($(this).val());
		});
		if ($.inArray(lastid.toString(), rightid_array) > -1) {
			alert('已存在该权限范围');
			return;
		}
	}
	$('#resmtr').append(
			'<div class="mtc">' + names.join('<span class="sn"> &gt; </span>')
					+ '<span' + aClick + ' dataid="' + lastid
					+ '" class="uclose">&times;</span></div>');
	$('#resIds').append(
			'<input type="hidden" id="rightid_' + lastid
					+ '" name="restrictRightId" value="' + lastid + '">');
	showform();
	if (type == "protype") {
		$('#projecttypecnt').hide();
	} else if (type == 'proapply') {
		$('#projecttypecnt').hide();
		getprojects()
	} else {
		resCon();
	}

}
// 删除
function delRes(t, type) {
	type = (typeof (type) == 'undefined') ? '' : type;
	t.parent().remove();
	$('#rightid_' + t.attr('dataid')).remove();
	if (type == "protype") {
		$('#projecttypecnt').show();
		showform();
		resCon2();
	} else if (type == "proapply") {// 补录项目信息
		$('#projecttypecnt').show();
		showform();
		resCon3();
	}
}
// 报送学员
function compareData() {
	var e = $('#sumtable'), pt = e.find('tr.plan-total'), foot = e
			.find('tfoot tr'), array1 = [], array2 = [];
	pt.find('th:not([data-editable="no"])').each(function() {
		array1.push($(this).text());
	})
	foot.find('td:not([data-editable="no"])').each(function() {
		array2.push($(this).text());
	})
	if (array1.length > 0 && array1.join(',') == array2.join(',')) {
		getData();
		return true;

	} else {
		return false;
	}
}
// 获取学员数据
function getData() {

	var e = $('#sumtable'), data = [];
	e.find('tbody td:not([data-editable="no"])').each(
			function() {
				var st = $(this).parent('tr').attr('data-left-id') + ','
						+ $(this).attr('data-top-id') + ',' + $(this).text();
				data.push(st);
			})
	return data.join('#');
}
// 初始化学员报送
$.fn.initsumtable = function() {
	var obj = $(this), footer = obj.find('tfoot tr');

	footer.find('td.csum').each(function(i) {
		var vTotal = 0, column = $(this).index();
		obj.find('tbody tr').each(function(i) {
			var row = $(this);
			vTotal += parseFloat(row.children().eq(column).text());
		});
		$(this).text(vTotal);

	})

	$('.hTotal')
			.each(
					function(i) {
						var hTotal = 0;
						obj
								.find('thead tr.plan-total,tbody tr,tfoot tr')
								.eq(i)
								.find(
										'td:not([data-editable="no"]),th:not([data-editable="no"])')
								.each(function() {
									hTotal += parseFloat($(this).text());
								})
						$(this).text(hTotal);
					})

}

// 报送学员
$.fn.sumtable = function() {
	var obj = $(this), editor = $('<input>').css('position', 'absolute').hide()
			.appendTo('body'), originalContent, active;
	obj.on('click', 'tbody td:not([data-editable="no"])', function() {
		active = $(this);
		editor.val(active.text()).show().offset(active.offset()).css({
			'padding' : '8px',
			'text-align' : 'center'
		}).width(active.width()).height(active.height()).focus().select();
		// console.log(compareData());

	});
	editor.blur(function() {
		setActiveText();
		editor.hide();
	});

	var setActiveText = function() {
		var ev = ($.trim(editor.val()).length) ? editor.val() : 0;
		var text = validate(ev);
		if (active.text() === text || editor.hasClass('error')) {
			return true;
		} else {

		}
		originalContent = active.text();
		active.text(text);
		sum();
	};
	var isNormalInteger = function(str) {
		return /^\+?(0|[1-9]\d*)$/.test(str);
	};
	var validate = function(n) {
		var r = parseFloat(n);
		return isNormalInteger(r) ? r : active.text();
	};

	var sum = function() {
		var footer = obj.find('tfoot tr');
		// obj.find('td:not([data-editable="no"])').on('change',function(evt){
		// var cell = $(this),
		planTotal = obj.find('thead tr.plan-total'), column = active.index(),
				rsum = active.parent('tr'), rsumTotal = 0, total = 0,
				planTotalnum = 0, realTotal = 0;

		// console.info(cell)
		if (column === 0) {
			return;
		}
		obj.find('tbody tr').each(function() {
			var row = $(this);
			total += parseFloat(row.children().eq(column).text());
		});
		/*
		 * if (total > parseFloat(planTotal.children().eq(column).text())) {//周五
		 * 景峰提得新需求 报送人数可以大于 但是 不允许提交 active.text(originalContent); return false; }
		 */
		footer.children().eq(column).text(total);

		rsum.find('td:not([data-editable="no"])').each(function() {
			rsumTotal += parseFloat($(this).text());
		})

		footer.find('td.csum').each(function() {
			// console.log($(this).text());
			realTotal += parseFloat($(this).text());
		})
		planTotal.find('th:not([data-editable="no"])').each(function() {
			planTotalnum += parseFloat($(this).text());
		})

		// if (column === 1 && total > 5000) {
		// $('.alert').show();
		// return false; // changes can be rejected
		// } else {
		// $('.alert').hide();
		planTotal.find('th.planTotal').text(planTotalnum);
		footer.find('td.realTotal').text(realTotal);
		rsum.find('td.rsum').text(rsumTotal);

		// }

		// })

	};

};

// 添加学员
function addRecord(t) {
	var obj = $(t);
	var cUrl = obj.attr('data-url');
	$.getJSON(cUrl, function(ret) {
		if (ret.Result == 'OK') {
			$('#status_' + obj.attr('data-id')).text('添加成功');
			//infotip(obj, ret.Message);
		} else if (ret.Result == 'FAIL') {
			if (confirm("人数已经达到上限，是否继续添加?")) {
				var tcUrl = cUrl + "&goon=1";
				$.getJSON(tcUrl, function(ret) {
					if (ret.Result == 'OK') {
						$('#status_' + obj.attr('data-id')).html('添加成功').addClass('sok');
						//infotip(obj, ret.Message);
					} else {
						alert('失败,' + ret.Message);
					}
				});
			}
		} else if (ret.Result == "ERROR") {
			alert('失败,' + ret.Message);
		}
	})
	return false;
}

// 单个审核学员
function aduRecord(t) {
	var obj = $(t);
	var cUrl = obj.attr('data-url');
	$.getJSON(cUrl, function(ret) {
		if (ret.Result == 'OK') {
			$('#status_' + obj.attr('data-id')).text(ret.status);
			infotip(obj, ret.Message);
		} else {
			alert('失败,' + ret.Message);
		}
	})
	return false;
}
//删除学员
function delRecord(t) {
	var obj = $(t);
	var cUrl = obj.attr('data-url');
	$.getJSON(cUrl, function(ret) {
		if (ret.Result == 'OK') {
			infotip(obj, ret.Message);
			$('#adu_'+obj.attr('data-id')).remove();
		} else {
			alert('失败,' + ret.Message);
		}
	})
	return false;
}
// 批量审核学员
function aduRecordall(t) {
	var obj = $(t);
	var cUrl = obj.attr('data-url');
	var keyarr = [];
	if ($('input[name=traininfo]:checked').length < 1) {
		alert('至少选择一个学员');
	} else {
		$.each($('input[name=traininfo]').serializeArray(), function(i, v) {
			keyarr.push(v.value);
		});
	}
	$.getJSON(cUrl + '&key=' + keyarr.join(","), function(ret) {
		if (ret.Result == 'OK') {
			window.location.reload();
		} else {
			alert('失败,' + ret.Message);
		}
	})
	return false;
}

// 报送记录
function audlist(t) {
	var obj = $(t);
	var cUrl = obj.attr('data-url');
	var tt_offset = obj.offset();
	var tt_top = tt_offset.top;
	var tt_left = tt_offset.left;

	$.getJSON(
		cUrl,
		function(ret) {
			if (ret.Result == 'OK') {
				var html = '<table class="table table-striped"><thead><tr><th width="80">序号</th><th width="160">报送时间</th><th>详细</th></tr></thead><tbody>';
				if (ret.rows.length > 0) {
					$.each(ret.rows, function(i, c) {
						html += '<tr><td><b>' + c.id + '</b></td><td>'
								+ c.time + '</td><td>' + c.info
								+ '</td></tr>';
					})
				} else {
					html += '<tr><td colspan=3>暂无报送记录</td></tr>';
				}
				html += '</tbody></table>'
				$('#recordlistcnt').html(html);
				var new_div_left = tt_left
						- $('.recordlist').outerWidth() - 16;
				var new_div_top = tt_top
						- $('.recordlist').outerHeight() / 2 + 6;
				$('.recordlist').css({
					'top' : new_div_top + 'px',
					'left' : new_div_left + 'px'
				}).toggle();

			} else {
				alert('失败,' + ret.Message);
			}
		})

}

$(function() {
	$('#sumtable').sumtable();
})
