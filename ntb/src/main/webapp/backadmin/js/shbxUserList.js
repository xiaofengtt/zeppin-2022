$(document).ready(function() {
    var pageNum = 1;
    var sort = "";
    var flag = true;

    var conditionList = {
        "pageNum": "",
        "url": getHtmlDocName()
    };

    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            pageNum = json.pageNum;
        } else {
            sessionStorage.removeItem("condition");
        }

    }


    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        conditionList.pageNum = pageNum;
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });

    getList();

    function init() {
        pageNum = '1';
        flag = true;
    }
    
    function searchBtn() {
        init();
        getList();
        return false;
    }
    $("#searchButton").click(function() {
        searchBtn();
    });
    $("#search").bind("keypress", function(event) {
        if (event.keyCode == 13) {
            searchBtn();
            return false;
        }
    });
    
    //获取列表
    function getList(sorts) {
        var sort = "";
        var name = $("#search").val();
        if (sorts != undefined) {
            sort = sorts;
        }
        $.ajax({
                url: '../rest/backadmin/shbxUser/list',
                type: 'get',
                data: {
                	"name": name,
                    "pageNum": pageNum,
                    "pageSize": 10
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data);
                        $("#queboxCnt").html(html);
                    } else {
                        $("#queboxCnt").html("<tr><td colspan='5'>没有数据</td></tr>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }


                //分页构造函数
                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        current: pageNum,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getList();
                            flag = false;
                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getList()
});