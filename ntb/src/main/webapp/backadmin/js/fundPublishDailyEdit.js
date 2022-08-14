$(function() {
    var statistime = (url('?statistime') != null) ? url('?statistime') : '';
    var netValue = (url('?netValue') != null) ? url('?netValue') : '';
    $('#statistime').val(statistime);
    $('#netValue').val(netValue);

})
//提交
$('#formsubmit').unbind("submit").submit(function() {
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    var oldValue = (url('?netValue') != null) ? url('?netValue') : '';
    var netValue = $("#netValue").val().replace(/(^\s*)|(\s*$)/g, "");
    var statistime = $("#statistime").val().replace(/(^\s*)|(\s*$)/g, "");

    if (netValue == "") {
        layer.msg('净值不能为空', {
            time: 2000
        });
    } else if (netValue == oldValue) {
        layer.msg('净值没有变化', {
            time: 2000
        });
    } else if (statistime == "") {
        layer.msg('添加日期不能为空', {
            time: 2000
        });
    } else {
        window.parent.editValue(uuid, netValue, statistime, function(e) {
            if (e != '') {
                layer.msg(e, {
                    time: 2000
                })
            } else {
                layer.msg('修改成功', {
                    time: 1000
                }, function() {
                    parent.$.colorbox.close();
                });
            }
        });
    }
    return false;
});