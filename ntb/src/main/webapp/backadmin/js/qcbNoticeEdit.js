var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function() {
	$("#starttime").click(function() {
        var start = laydate.now(0, 'YYYY-MM-DD hh:mm:ss');
        laydate({
            start: start,
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    });
    $("#endtime").click(function() {
        var start = laydate.now(0, 'YYYY-MM-DD hh:mm:ss');
        laydate({
            start: start,
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    });
    getDate();
});

//比较日期大小
function compareDate(checkStartDate, checkEndDate) {
    var arys1 = new Array();
    var arys2 = new Array();
    if (checkStartDate != null && checkEndDate != null) {

        arys1 = checkStartDate.split('-');

        var sdate = new Date(arys1[0], parseInt(arys1[1] - 1), arys1[2]);
        arys2 = checkEndDate.split('-');
        var edate = new Date(arys2[0], parseInt(arys2[1] - 1), arys2[2]);
        if (sdate > edate) {
            return false;
        } else {
            return true;
        }
    }
}

function GetDateDiff(startDate, endDate) {
    var startTime = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
    var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
    var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
    return dates;
}

//获取值
function getDate() {
    $.get('../rest/backadmin/qcbNotice/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $('.uuid').val(r.data.uuid);
            $('#title').val(r.data.title);
            $('#starttime').val(r.data.starttimeCN);
            $('#endtime').val(r.data.endtimeCN);
            $('#status').val(r.data.status);
            tinymecTool.load('content', r.data.content);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}
$("#formsubmit").submit(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    $('input[name=content]').val(string2json2(tinyMCE.get('content').getContent()));
    var str = $(this).serialize();
    $.post('../rest/backadmin/qcbNotice/edit', str, function(r) {
        if (r.status == "SUCCESS") {
            layer.msg('保存成功', {
                time: 1000
            }, function() {
                window.location.href = 'qcbNoticeList.jsp';
            });
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
    return false;
});
