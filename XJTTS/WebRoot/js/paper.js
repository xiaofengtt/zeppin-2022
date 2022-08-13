function showul(t) {
	$('.uul:visible').hide();//clear all show uul(fix bug)
	var uul = t.next('.uul');
	if (uul.is(':visible'))
		uul.hide();
	else
		uul.show();
}

function getul(th, type, qsid) {
	var th = $(th);
	$.getJSON('../paper/paper_getPsqProjects.action?type=' + type,
			function(ret) {
				if (ret.length > 0) {
					var ufc = '';
					$.each(ret, function(i, item) {
						aClick = ' onclick="addP(this,\'' + item.id
								+ '\',\'' + type + '\',\'' + qsid + '\');showul(this)"'
						ufc += '<li '+ aClick +' >' + item.name + '</li>';
						$(th).next('.uul').html(ufc);

					})
				} else {
					$(th).next('.uul').html('<li>暂无项目</li>');
				}

			})
}

function addP(obj, prjid, type, qsid) {
	var html = $(obj).html();
	$.get('../paper/paper_changePsqProject.action?type=' + type + '&psqid='
			+ qsid + '&s=1&projectid=' + prjid, function(r) {
		if (r.Result == 'OK') {
			var closex = '<span onclick="delP(this,' + prjid + ', ' + type
					+ ', ' + qsid + ')" class="uclose">&times;</span>'
			$('#resmtr_' + qsid).append(
					'<div class="mtc">' + html + closex + '</div>').show();
			//$('#projecttypecnt_' + qsid).hide();

		} else {
			alert(r.Message);
		}

	})

}
function delP(obj, prjid, type, qsid) {
	var th = $(obj);
	$.get('../paper/paper_changePsqProject.action?type=' + type + '&psqid='
			+ qsid + '&s=2&projectid=' + prjid, function(r) {
		if (r.Result == 'OK') {
			th.parent('.mtc').remove();
			
		} else {
			alert(r.Message);
		}

	})
}

$(function() {
	$('body').click(function(e) {
		var o = $(e.target);
		if (o.hasClass('usel') || o.parent('.usel').length) {
			return;
		}
		if (!o.parent('.uul').length) {
			$('.uul').hide();
		}
	});

})