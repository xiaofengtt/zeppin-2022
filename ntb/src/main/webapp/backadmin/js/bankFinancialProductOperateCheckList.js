var pageNum = 1;
var flag = true;

function init() {
    pageNum = 1;
    flag = true;
}
var conditionList = {
    "filter": "",
    "filter1": "",
    "pageNum": "",
    "url": getHtmlDocName()
};

if (sessionStorage.getItem("condition")) {
    var json = JSON.parse(sessionStorage.getItem("condition"));
    if (json.url == getHtmlDocName()) {
        $(".filter").find("#" + json.filter).addClass('statusLight').siblings().removeClass("statusLight");
        $(".filter1").find("#" + json.filter1).addClass('statusLight1').siblings().removeClass("statusLight1");
        pageNum = json.pageNum;
    } else {
        sessionStorage.removeItem("condition");
    }
}


$("#queboxCnt").on("click", "a", function() {
    sessionStorage.removeItem("condition");
    conditionList.filter = $(".filter").find(".statusLight").prop("id");
    conditionList.filter1 = $(".filter1").find(".statusLight1").prop("id");
    conditionList.pageNum = pageNum;
    sessionStorage.setItem("condition", JSON.stringify(conditionList));
});

function openThis(event) {
    $(event.target).attr("onclick", "closeThis(event)");
    $(event.target).closest('.btn-group').find('.popover').addClass("open");
    event.stopPropagation();
    return false;
}

function closeThis(event) {
    $(event.target).attr("onclick", "openThis(event)");
    $(event.target).closest('.btn-group').find('.popover').removeClass("open");
    event.stopPropagation();
    return false;
}

function hiddenBox() {
    $(document).click(function() {
        $("[onclick='closeThis(event)']").attr("onclick", "openThis(event)");
        $(".popover").removeClass('open');
    });

}
hiddenBox();

function checkThis(t) {
    var reason = $(t).closest('.btn-group').find('.reason').val();
    layer.confirm('确定要变更状态吗?', function(index) {
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
        $.get('../rest/backadmin/bankFinancialProduct/operateCheck?uuid=' + uuid + '&status=' + status + '&reason=' + reason, function(r) {
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

function submitThis(t) {
    layer.confirm('确定要提交审核吗?', function(index) {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        var obj = $(t);
        var uuid = obj.attr('data-uuid');
        $.get('../rest/backadmin/bankFinancialProduct/operateSubmitCheck?uuid=' + uuid, function(r) {
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

$(".filter a").click(function() {
    $(this).addClass("statusLight").siblings().removeClass("statusLight");
    init();
    getTypeList();
    getList();
});
$(".filter1 a").click(function() {
    $(this).addClass("statusLight1").siblings().removeClass("statusLight1");
    init();
    getList();
});

$(document).ready(function() {
    getStatusList();
    getList();
    $(".shortStatusDiv").css({
        "max-width": $(".main-contain").width() - 300 + "px",
        "margin": "0"
    });
    $(".shortStatusDiv div").css({
        "max-width": $(".main-contain").width() - 390 + "px",
        "margin": "0"
    });
});
$(window).resize(function() {
    if ($(window).width() > 1250) {
        $(".shortStatusDiv").css({
            "max-width": $(".main-contain").width() - 300 + "px",
            "margin": "0"
        });
        $(".shortStatusDiv div").css({
            "max-width": $(".main-contain").width() - 390 + "px",
            "margin": "0"
        });
    }
});

function getStatusList() {
    $("#draftCount").html("(0)");
    $("#checkedCount").html("(0)");
    $("#uncheckedCount").html("(0)");
    $("#unpassedCount").html("(0)");
    $("#allCount").html("(0)");
    $.get('../rest/backadmin/bankFinancialProduct/operateCheckStatusList', function(r) {
        if (r.status == 'SUCCESS') {
            var totalCount = 0;
            if (r.totalResultCount > 0) {
                $.each(r.data, function(i, v) {
                    totalCount += v.count;
                    $("#" + v.status + "Count").html("(" + v.count + ")");
                })
                $("#allCount").html("(" + totalCount + ")");
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
    getTypeList();
}

function getTypeList() {
    var status = $(".statusLight").attr("id");
    $("#addCount").html("(0)");
    $("#editCount").html("(0)");
    $("#deleteCount").html("(0)");
    $("#checkCount").html("(0)");
    $("#netvalueCount").html("(0)");
    $.get('../rest/backadmin/bankFinancialProduct/operateCheckTypeList?status=' + status, function(r) {
        if (r.status == 'SUCCESS') {
            $(".filter1").removeClass("hidden");
            var totalCount = 0;
            if (r.totalResultCount > 0) {
                $.each(r.data, function(i, v) {
                    totalCount += v.count;
                    $("#" + v.status + "Count").html("(" + v.count + ")");
                })
            }
            $("#typeCount").html("(" + totalCount + ")");
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}

function getList() {
    //	var name = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
    var name = "";
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var status = $(".statusLight").attr("id");
    var type = $(".statusLight1").attr("id");
    var str = '';
    str += '&status=' + status;
    str += '&type=' + type;

    $.get('../rest/backadmin/bankFinancialProduct/operateCheckList?sorts=submittime-desc&pageNum=' + page + '&pageSize=10&name=' + name + str, function(r) {
        if (r.status == 'SUCCESS') {
            r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">' + r.pageNum + '</span>/' + r.totalPageCount);
            if (r.totalResultCount > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.data);
                $('#queboxCnt').html(html);
            } else {
                var html = '<div class="nodata">没有数据！</div>';
                $('#queboxCnt').html(html);
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function(r) {
        $(".popover").unbind("click").click(function(event) {
            event.stopPropagation();
            return false;
        });
        if (flag) {
            $('#pageTool').Paging({
                pagesize: r.pageSize,
                count: r.totalResultCount,
                current: pageNum,
                callback: function(page, size, count) {
                    pageNum = page;
                    getList();
                    flag = false;
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                },
                render: function(ops) {

                }
            });
            $("#pageTool").find(".ui-paging-container:last").siblings().remove();
        };
    })
}