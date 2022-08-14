var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function() {
    getDate();
});

//获取值
function getDate() {
    $.get('../rest/backadmin/bankFinancialProduct/operateNetvalueGet?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $("#submitName").html(r.data.creatorName);
            $("#submitTime").html(r.data.createtimeCN);
            $("#reason").html(r.data.reason);
            if (r.data.status == "unchecked") {
                $("#reasonItem").hide();
            } else {
                $("#reasonItem").show();
            }
            if (r.data.status == "checked") {
                $("#reason").css({
                    "color": "green"
                });
            } else {
                $("#reason").css({
                    "color": "red"
                });
            }

            if (r.data.status == 'unchecked') {
                $('.operateCheck').css("display", "block")
            }
            $('#titlename').html(r.data.bankFinancialProductName);
            $('#name').html(r.data.bankFinancialProductName);
            $('#custodian').html(r.data.custodian);
            $('#ascode').html(r.data.scode);
            if (r.data.dataList.length > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.data.dataList);
                $('#queboxCnt').html(html);
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}

function checked(t) {
    layer.confirm("确认要变更状态吗？", function(index) {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        var reason = $('#checkReason').val();
        $.get('../rest/backadmin/bankFinancialProduct/operateCheck?uuid=' + uuid + '&status=checked&reason=' + reason, function(r) {
            if (r.status == 'SUCCESS') {
                layer.msg('操作成功', {
                    time: 1000
                }, function() {
                    window.location.href = "bankFinancialProductOperateCheckList.jsp";
                });
            } else {
                alert('操作失败,' + r.message);
            }
        });
        layer.close(index);
    });
}

function unpassed(t) {
    layer.confirm("确认要变更状态吗？", function(index) {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        var reason = $('#checkReason').val();
        $.get('../rest/backadmin/bankFinancialProduct/operateCheck?uuid=' + uuid + '&status=unpassed&reason=' + reason, function(r) {
            if (r.status == 'SUCCESS') {
                layer.msg('操作成功', {
                    time: 1000
                }, function() {
                    window.location.href = "bankFinancialProductOperateCheckList.jsp";
                });
            } else {
                alert('操作失败,' + r.message);
            }
        });
        layer.close(index);
    });
}
