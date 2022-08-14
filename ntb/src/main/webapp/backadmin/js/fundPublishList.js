var pageNum = 1;
var flag = true;

function init() {
    pageNum = 1;
    flag = true;
}

function checkThis(t) {
    layer.confirm('确定要变更审核状态吗?', function(index) {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        var obj = $(t);
        var uuid = obj.attr('data-uuid');
        var status = obj.attr('data-status');
        $.get('../rest/backadmin/fundPublish/check?uuid=' + uuid + '&status=' + status, function(r) {
            if (r.status == 'SUCCESS') {
                layer.msg('操作成功', {
                    time: 1000
                }, function() {
                    init();
                    getStatusList();
                    getList();
                });
            } else {
                alert('操作失败,' + r.message);
            }
        })
        layer.close(index);
    });
    return false;
}

function searchBtn() {
    init();
    getList();
    return false;
}

//$(".btn-add").colorbox({
//	iframe : true,
//	width : "1000px",
//	height : "750px",
//	opacity : '0.5',
//	overlayClose : false,
//	escKey : true
//});

$(".statusDiv a").click(function() {
    init();
    $(this).addClass("statusLight").siblings().removeClass("statusLight");
    getList();
});

$(document).ready(function() {
    // getStatusList();
    getList();
})

function getStatusList() {
    $("#checkedCount").html("(0)");
    $("#uncheckedCount").html("(0)");
    $("#unpassedCount").html("(0)");
    $.get('../rest/backadmin/fundPublish/statusList', function(r) {
        if (r.status == 'SUCCESS') {
            if (r.totalResultCount > 0) {
                $.each(r.data, function(i, v) {
                    $("#" + v.status + "Count").html("(" + v.count + ")");
                })
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}

function getList() {
    var status = $(".statusLight").attr("id");
    $.get('../rest/backadmin/fundPublish/get?uuid=eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', function(r) {
        if (r.status == 'SUCCESS') {
            var template = $.templates('#queboxTpl');
            var html = template.render(r.data);
            $('#queboxCnt').html(html);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function(r) {

    	$(".productMessage").colorbox({
    	    iframe: true,
    	    width: "1050px",
    	    height: "720px",
    	    opacity: '0.5',
    	    overlayClose: false,
    	    escKey: true
    	});

    })
}