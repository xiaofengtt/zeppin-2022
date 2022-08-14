var pageNum = 1;
var flag = true;

var conditionList = {
    "name": "",
    "pageNum": "",
    "url": getHtmlDocName()
};

function init() {
    pageNum = 1;
    flag = true;
}

function searchBtn() {
    init();
    getList();
    return false;
}

$("#queboxCnt").on("click", "a", function() {
    sessionStorage.removeItem("condition");
    conditionList.pageNum = pageNum;
    conditionList.name = $("#search").val();
    sessionStorage.setItem("condition", JSON.stringify(conditionList));
});

function deleteThis(t) {
    layer.titleConfirm('确认要删除吗?', "删除确认", function(index) {
        var obj = $(t),
            cUrl = obj.attr('data-url');
        $.get(cUrl, function(ret) {
            if (ret.status == 'SUCCESS') {
                init();
                getList();
            } else {
                alert('失败,' + ret.message);
            }
        })
        layer.close(index);
    });
    return false;
}

$(document).ready(function() {
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            pageNum = json.pageNum;
            $("#search").val(json.name);
        } else {
            sessionStorage.removeItem("condition");
        }
    }

    $("#addButton").attr("href", "../backadmin/branchBankAdd.jsp?bank=" + uuid)
    getList();
})

function getList() {
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    var name = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
    $.get('../rest/backadmin/branchBank/list?pageNum=' + page + '&pageSize=10&bank=' + uuid + '&name=' + name, function(r) {
        if (r.status == 'SUCCESS') {
            r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">' + r.pageNum + '</span>/' + r.totalPageCount);
            if (r.totalResultCount > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.data);
                $('#queboxCnt').html(html);
            } else {
                var html = '<tr class="nodata"><td colspan=4>没有数据！</td></tr>'
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
                }
            });
            $("#pageTool").find(".ui-paging-container:last").siblings().remove();
        }
    })
}