$(document).ready(function() {
    var pageNum = 1;
    var sort = "";
    var flag = true;
    var uuid = url("?uuid");
    var target_uuid = "";
    var highLightValue = false;
    getTransferList();
    getAccountMessage();

    function getAccountMessage() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    var template = $.templates("#msgTpl");
                    var html = template.render(r.data);
                    $("#msgCnt").html(html);
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage()

    function getTransferList(name, sorts) {
        var sort = "";
        var nameValue = "";
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/companyAccount/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "sorts": sort
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
                    layer.msg(r.data, {
                        time: 2000
                    });
                }

                //分页构造函数
                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getTransferList();
                            flag = false;
                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                }

                $(".transfer_card").each(function(index, el) {
                    var this_uuid = $(".transfer_card").eq(index).find("input").val();
                    if (this_uuid == uuid) {
                        $(".transfer_card").eq(index).remove();
                    }
                });

                $(".transfer_card").click(function() {
                    highLightValue = true;
                    $(this).addClass('highLight').siblings().removeClass('highLight');
                    $(this).find("img").show().parent().siblings().find("img").hide();
                    target_uuid = $(this).find("input").val();
                    $("#allRight").prop("href", "./transfer.jsp?uuid=" + uuid + "&target_uuid=" + target_uuid);
                });

                $("#allRight").click(function() {
                    if (highLightValue == false) {
                        layer.msg("请选择企业账户", {
                            time: 2000
                        });
                        return false;
                    }
                });
            })

            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getTransferList


});