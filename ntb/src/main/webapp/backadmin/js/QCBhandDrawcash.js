$(document).ready(function() {
    var pageNum = 1;
    var flag = true;
    var listStatus = "unprocess";
    var uuidArr = [];
    var apiType = "company";
    $(".control_box").hide();
    $(".controlling_box").hide();
    $(".item").click(function() {
        $(this).find("a").addClass('highLight').parent().siblings("li").find("a").removeClass('highLight');
    });

    $(".statusDiv a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        // getTypeList();
        apiType = $(this).attr("id");
        $("#unpayList").prop("href", "../rest/backadmin/qcbWithdraw/" + $(this).attr("id") + "Export?status=unprocess")
        init();
        getList();
        getStatusList();
    });


    function init() {
        pageNum = '1';
        flag = true;
    }

    function searchBtn() {
        init();
        getList();
        getStatusList();
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

    $(".item").eq(0).click(function() {
        $(".unControl_box").show();
        $(".control_box").hide();
        $(".controlling_box").hide();
        listStatus = "unprocess";
        getList();
        getStatusList();
        init();
    });
    $(".item").eq(1).click(function() {
        $(".unControl_box").hide();
        $(".control_box").hide();
        $(".controlling_box").show();
        listStatus = "processing";
        getList();
        getStatusList();
        init();
    });
    $(".item").eq(2).click(function() {
        $(".unControl_box").hide();
        $(".control_box").show();
        $(".controlling_box").hide();
        listStatus = "success";
        getList();
        getStatusList();
        init();
    });
    getStatusList();
    getList();
    //获取每个状态数量
    function getStatusList() {
        $("#unprocessCount").html("(0)");
        $("#processingCount").html("(0)");
        $("#successCount").html("(0)");
        var name = $("#search").val();
        $.ajax({
                url: '../rest/backadmin/qcbWithdraw/' + apiType + 'StatusList',
                type: 'get',
                data: {
                    "name": name
                }
            })
            .done(function(r) {
                $.each(r.data, function(index, el) {
                    $("#" + el.status + "Count").html("(" + el.count + ")");
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }


    function getList() {
        var name = $("#search").val();
        var typeValue = $(".filter").find(".statusLight").prop("id");
        $.ajax({
                url: '../rest/backadmin/qcbWithdraw/' + apiType + 'List',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": name,
                    "status": listStatus
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    // 未处理
                    if (listStatus == "unprocess") {
                        if (r.totalResultCount > 0) {
                            var template = $.templates("#unprocessTpl");
                            var html = template.render(r.data);
                            $("#unprocessCnt").html(html);

                            //选择checkbox添加删除uuid
                            $(".checkbox").unbind("change").change(function() {
                                var this_uuid = $(this).siblings('input').val();
                                if ($(this).is(":checked") == true) {
                                    uuidArr.push(this_uuid);
                                } else {
                                    uuidArr.splice(uuidArr.indexOf(this_uuid), 1);
                                }
                                console.log(uuidArr);
                            });

                            //全选
                            $("#selectAll").unbind("click").click(function() {
                                uuidArr = [];
                                $(".checkbox").each(function(index, el) {
                                    var this_uuid = $(".checkbox").eq(index).siblings("input").val();
                                    $(".checkbox").eq(index).prop("checked", true);
                                    uuidArr.push(this_uuid);
                                });
                                console.log(uuidArr);
                            });

                            //重试！
                            $(".retry").unbind("click").click(function() {
                                retry(this);
                            });

                            //批量重试！
                            $("#retryAll").unbind("click").click(function() {
                                if (uuidArr.length != 0) {
                                    retryAll();
                                } else {
                                    layer.msg("请勾选用户");
                                }
                            });

                            //批量设置处理中
                            $("#successAll").unbind("click").click(function() {
                                if (uuidArr.length != 0) {
                                    success();
                                } else {
                                    layer.msg("请勾选用户");
                                }
                            });

                            $(".fail").unbind("click").click(function() {
                                fail(this);
                            });

                            $("#failAll").unbind("click").click(function() {
                                if (uuidArr.length != 0) {
                                    failAll();
                                } else {
                                    layer.msg("请勾选用户");
                                }
                            });


                        } else {
                            $("#unprocessCnt").html("<tr><td colspan='8'>没有数据</td></tr>");
                        }
                    }

                    //导入
                    $("#fileSubmit").unbind("change").change(function() {
                        submitExcel();
                    });

                    //处理中
                    if (listStatus == "processing") {
                        if (r.totalResultCount > 0) {
                            var template_ = $.templates("#processingTpl");
                            var html_ = template_.render(r.data);
                            $("#processingCnt").html(html_);
                        } else {
                            $("#processingCnt").html("<tr><td colspan='6'>没有数据</td></tr>");
                        }
                    }


                    //已处理
                    if (listStatus == "success") {
                        if (r.totalResultCount > 0) {
                            var template__ = $.templates("#successTpl");
                            var html__ = template__.render(r.data);
                            $("#successCnt").html(html__);
                        } else {
                            $("#successCnt").html("<tr><td colspan='9'>没有数据</td></tr>");
                        }
                    }

                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        current: pageNum,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getList();
                            flag = false;
                            // document.body.scrollTop = document.documentElement.scrollTop = 0;
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

    }

    //重试！！！！！
    function retry(t) {
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbWithdraw/' + apiType + 'Edit',
                    type: 'post',
                    data: {
                        "uuid": $(t).parent().siblings().find('.uuid').val(),
                        "CSRFToken": $('input[name="CSRFToken"]').val()
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
                        init();
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
            layer.close(index);
        });
    } //retry


    //批量重试！！！！！
    function retryAll() {
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbWithdraw/' + apiType + 'Edit',
                    type: 'post',
                    data: {
                        "uuid": uuidArr.join(','),
                        "CSRFToken": $('input[name="CSRFToken"]').val()
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
                        init();
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
            layer.close(index);
        });
    } //retryAll



    //批量设置处理中
    function success() {
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbWithdraw/' + apiType + 'Process',
                    type: 'post',
                    data: {
                        "uuid": uuidArr.join(','),
                        "status": "processing",
                        "CSRFToken": $('input[name="CSRFToken"]').val()
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
                        init();
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
            layer.close(index);
        });
    } //success();

    //设置提现失败！！！！！
    function fail(t) {
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbWithdraw/' + apiType + 'Revoke',
                    type: 'post',
                    data: {
                        "uuid": $(t).parent().siblings().find('.uuid').val(),
                        "CSRFToken": $('input[name="CSRFToken"]').val()
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
                        init();
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
            layer.close(index);
        });
    } //fail


    //批量设置提现失败
    function failAll() {
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbWithdraw/' + apiType + 'Revoke',
                    type: 'post',
                    data: {
                        "uuid": uuidArr.join(','),
                        "CSRFToken": $('input[name="CSRFToken"]').val()
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
                        init();
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
            layer.close(index);
        });
    } //failAll

    //导入Excel
    function submitExcel() {
        $("#excelSubmit").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                $.ajax({
                        url: '../rest/backadmin/qcbWithdraw/' + apiType + 'Import',
                        type: 'post',
                        data: {
                            "file": r.data.uuid,
                            "CSRFToken": $('input[name="CSRFToken"]').val()
                        }
                    })
                    .done(function(data) {
                        layer.msg(data.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
                        init();
                        $("#fileSubmit").val("");
                    })
                    .fail(function() {
                        layer.msg(data.message, {
                            time: 2000
                        });
                    });

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }


}); //document.ready