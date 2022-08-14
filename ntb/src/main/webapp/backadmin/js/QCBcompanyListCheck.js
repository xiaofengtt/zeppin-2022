var pageNum = 1;
var flag = true;

function init() {
    pageNum = 1;
    flag = true;
}


var conditionList = {
    "filter": "",
    "name": "",
    "pageNum": "",
    "url": getHtmlDocName()
};




$("#queboxCnt").on("click", "a", function() {
    sessionStorage.removeItem("condition");
    conditionList.filter = $(".filter").find(".statusLight").prop("id");
    conditionList.pageNum = pageNum;
    conditionList.name = $("#search").val();
    sessionStorage.setItem("condition", JSON.stringify(conditionList));
});

function searchBtn() {
    init();
    getList();
    return false;
}

$(".filter a").click(function() {
    $(this).addClass("statusLight").siblings().removeClass("statusLight");
    init();
    // getTypeList();
    getList();
});

function getStatusList() {
    $("#normalCount").html("(0)");
    // $("#uncheckCount").html("(0)");
    $("#unauthCount").html("(0)");
    $("#nopassCount").html("(0)");
    $("#deletedCount").html("(0)");
    $("#allCount").html("(0)");
    $.get('../rest/backadmin/qcbcompany/operateStatusList', function(r) {
        if (r.status == 'SUCCESS') {
            var totalCount = 0;
            if (r.totalResultCount > 0) {
                $.each(r.data, function(i, v) {
                    totalCount += v.count;
                    $("#" + v.status + "Count").html("(" + v.count + ")");
                });
                $("#allCount").html("(" + totalCount + ")");
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
}


$(document).ready(function() {
    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            $(".filter").find("#" + json.filter).addClass('statusLight').siblings().removeClass("statusLight");
            pageNum = json.pageNum;
            $("#search").val(json.name);
        } else {
            sessionStorage.removeItem("condition");
        }
    }

    getStatusList();
    getList();
});

function getList() {
    var name = $("#search").val().replace(/(^\s*)|(\s*$)/g, "");
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var status = $(".statusLight").attr("id");
    var str = '';
    str += '&status=' + status;
    $.get('../rest/backadmin/qcbcompany/operateList?pageNum=' + page + '&pageSize=10&name=' + name + str, function(r) {
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
            });
        }
    }).done(function(r) {
        $(".checkbtn").colorbox({
            iframe: true,
            width: "800px",
            height: "714px",
            opacity: '0.5',
            borderRadius: "10px",
            overlayClose: false,
            escKey: true
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
        }
    });
}