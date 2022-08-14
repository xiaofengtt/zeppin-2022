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

$(".filter a").click(function() {
    $(this).addClass("statusLight").siblings().removeClass("statusLight");
    init();
    getList();
});


$(document).ready(function() {
    getList();
})

function getList() {
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var status = $(".statusLight").attr("id");
    var qcbCompany = url("?uuid");
    var str = '';
    str += '&accountBalance=' + status;
    $.get('../rest/backadmin/employee/list?qcbCompany='+qcbCompany+'&pageNum=' + pageNum + '&pageSize=10' + str, function(r) {
        if (r.status == 'SUCCESS') {
            r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">' + r.pageNum + '</span>/' + r.totalPageCount);
            if (r.totalResultCount > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.data);
                $('#queboxCnt').html(html);
            } else {
                var html = '<tr><td colspan="4" style="text-align:center;">没有数据！</td></tr>';
                $('#queboxCnt').html(html);
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function(r) {
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
        }
    })
}