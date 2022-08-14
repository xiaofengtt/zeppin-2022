$(document).ready(function() {
    var pageNum = 1;
    var flag = true;
    var sort = "";

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
            $(".filter1").find("#" + json.filter1).addClass('statusLight').siblings().removeClass("statusLight");
            pageNum = json.pageNum;
        } else {
            sessionStorage.removeItem("condition");
        }
    }


    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        conditionList.filter = $(".filter").find(".statusLight").prop("id");
        conditionList.filter1 = $(".filter1").find(".statusLight").prop("id");
        conditionList.pageNum = pageNum;
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });


    //获取筛选项
    getStatusList("../rest/backadmin/bkpayment/operateStatusList");
    getTypeList();
    getList();

    function deleteThis(t) {
        layer.confirm('确定要删除吗?', function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            var obj = $(t);
            var uuid = obj.attr('data-uuid');
            $.get('../rest/backadmin/bkpayment/operateDelete?uuid=' + uuid, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 2000
                    });
                    getStatusList("../rest/backadmin/bkpayment/operateStatusList");
                    getTypeList();
                    getList();
                } else {
                    alert('操作失败,' + r.message);
                }
            });
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
            $.get('../rest/backadmin/bkpayment/operateSubmitCheck?uuid=' + uuid, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 1000
                    });
                    init();
                    getStatusList("../rest/backadmin/bkpayment/operateStatusList");
                    getList();
                } else {
                    alert('操作失败,' + r.data);
                }
            });
            layer.close(index);
        });
        return false;
    }



    $(".statusDiv a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        getTypeList();
        init();
        getList();
    });



    function init() {
        pageNum = '1';
        flag = true;
    }

    function getTypeList() {
        $("#typeCount").html("(0)");
        $("#addCount").html("(0)");
        $("#editCount").html("(0)");
        $("#openCount").html("(0)");
        $("#closedCount").html("(0)");
        $("#deleteCount").html("(0)");

        $.ajax({
                url: "../rest/backadmin/bkpayment/operateTypeList",
                type: "get",
                data: {
                    "status": $(".filter").find(".statusLight").prop("id")
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var totalCount = 0;
                        $.each(r.data, function(index, el) {
                            $("#" + el.status + "Count").html("(" + el.count + ")");
                            totalCount += el.count;
                        });
                        $("#typeCount").html("(" + totalCount + ")");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function(r) {
                layer.msg("error", {
                    time: 2000
                });
            });


    } //getStatusList()




    //获取列表
    function getList(name, sorts) {
        var sort = "";
        var nameValue = "";
        var statusValue = $(".filter").find(".statusLight").prop("id");
        var typeValue = $(".filter1").find(".statusLight").prop("id");
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/bkpayment/operateList',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "sorts": "submittime-desc",
                    "status": statusValue,
                    "type": typeValue
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
                        $("#queboxCnt").html("<tr><td colspan='9'>没有数据</td></tr>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                //提交审核
                $(".checked").click(function() {
                    submitThis(this);
                });

                $(".delete").click(function() {
                    deleteThis(this);
                });



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





}); //document.ready