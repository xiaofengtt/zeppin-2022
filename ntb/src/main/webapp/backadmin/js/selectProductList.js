var bank = url("?bank");
var pageNum = 1;
var flag = true;

function init() {
    pageNum = 1;
    flag = true;
}

function searchBtn() {
    init();
    getList();
    return false;
}

$(document).ready(function() {
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

function publishAdd(e) {
    var url = $(e).attr('data-url');
    window.top.location.href = url;
}

function getList() {
    var name = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var status = 'checked';

    //查询可以发布的银行理财产品
    $.get('../rest/backadmin/bankFinancialProduct/list?status=checked&stage=collect&pageNum=' + page + '&pageSize=10&name=' + name + "&custodian=" + bank, function(r) {
        if (r.status == 'SUCCESS') {
            r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">' + r.pageNum + '</span>/' + r.totalPageCount);
            if (r.totalResultCount > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.data);
                $('#queboxCnt').html(html);
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
        getFundList(name, r.totalResultCount);
    }).done(function(r) {

    });
}

function getFundList(names, count) {
    $.get('../rest/backadmin/fund/list?status=checked&stage=collect&pageNum=' + 1 + '&pageSize=1000&name=' + names + "&gp=" + bank, function(r) {
        if (r.status == 'SUCCESS') {
            if (r.totalResultCount > 0) {
                var template = $.templates('#queboxTpls');
                var html = template.render(r.data);
                $('#queboxCnt').append(html);
            } else if (count == 0 && r.totalResultCount == 0) {
                var html = '<div class="nodata">没有数据！</div>';
                $('#queboxCnt').html(html);
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    })
}