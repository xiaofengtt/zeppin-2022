$(document).ready(function() {
    var pageNum = 1;
    var flag = true;
    var sort = "";

    var conditionList = {
        "list": [],
        "pageNum": "",
        "url": getHtmlDocName()
    };

    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            $.each(json.list, function(index, el) {
                $("#" + el).addClass('statusLight').siblings().removeClass('statusLight');
            });
            pageNum = json.pageNum;
        } else {
            sessionStorage.removeItem("condition");
        }
    }


    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        $(".statusLight").each(function(index, el) {
            if ($(".statusLight").eq(index).prop("id") != "all") {
                conditionList.list.push($(".statusLight").eq(index).prop("id"));
            }
        });
        conditionList.pageNum = pageNum;
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });
    //获取筛选项
    getStatusList();
    getTypeList();
    getList();

    $(".statusDiv a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        getTypeList();
        init();
        getList();
    });

    function getStatusList() {
        $("#allCount").html("(0)");
        $("#draftCount").html("(0)");
        $("#uncheckedCount").html("(0)");
        $("#checkedCount").html("(0)");
        $("#unpassedCount").html("(0)");
        $.ajax({
                url: "../rest/backadmin/companyAccountTransfer/operateStatusList",
                type: "get",
                data: {
                    "transferType": "outside"
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
                        $("#allCount").html("(" + totalCount + ")");
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
            $.get('../rest/backadmin/companyAccountTransfer/operateDelete?uuid=' + uuid, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 2000
                    }, function() {
                        getStatusList();
                        getTypeList();
                        getList();
                    });
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
            $.get('../rest/backadmin/companyAccountTransfer/operateSubmitCheck?uuid=' + uuid, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 1000
                    }, function() {
                        init();
                        getStatusList();
                        getList();
                    });
                } else {
                    alert('操作失败,' + r.data);
                }
            });
            layer.close(index);
        });
        return false;
    }

    function init() {
        pageNum = '1';
        flag = true;
    }

    function getTypeList() {
        $("#typeCount").html("(0)");
        $("#rechargeCount").html("(0)");
        $("#expendCount").html("(0)");

        $.ajax({
                url: "../rest/backadmin/companyAccountTransfer/operateTypeList",
                type: "get",
                data: {
                    "status": $(".filter").find(".statusLight").prop("id"),
                    "transferType": "outside"
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


    } //getTypeList()




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
                url: '../rest/backadmin/companyAccountTransfer/operateList',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "sorts": sort,
                    "status": statusValue,
                    "type": typeValue,
                    "transferType": "outside"
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

                //提交审核
                $(".checked").click(function() {
                    submitThis(this);
                });

                $(".delete").click(function() {
                    deleteThis(this);
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getList()





}); //document.ready