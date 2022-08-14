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
    getStatusList("../rest/backadmin/companyAccount/operateCheckStatusList");
    getTypeList();
    getList();

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
            $.get('../rest/backadmin/companyAccount/operateCheck?uuid=' + uuid + '&status=' + status + '&reason=' + reason, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 2000
                    });
                    init();
                    getStatusList("../rest/backadmin/companyAccount/operateCheckStatusList");
                    getList();
                } else {
                    alert('操作失败,' + r.message);
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

        $.ajax({
                url: "../rest/backadmin/companyAccount/operateCheckTypeList",
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
                url: '../rest/backadmin/companyAccount/operateCheckList',
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

                //审核弹窗
                $(".auditing").unbind("click").click(function() {
                    if ($(this).closest('.btn-group').find('.popover').hasClass("open")) {
                        $(this).closest('.btn-group').find('.popover').removeClass("open");
                    } else {
                        $(this).closest('.btn-group').find('.popover').addClass("open");
                    }
                    return false;
                });
                $(document).unbind("click").click(function() {
                    $(".auditing").closest('.btn-group').find('.popover').removeClass("open");
                });
                $(".popover").unbind("click").click(function(event) {
                    event.stopPropagation();
                    return false;
                });

                //审核通过不通过
                $(".auditing_check").unbind("click").click(function() {
                    checkThis(this);
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