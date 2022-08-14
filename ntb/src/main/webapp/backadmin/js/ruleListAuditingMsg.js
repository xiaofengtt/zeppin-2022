$(document).ready(function() {
    var uuid = url("?uuid");
    var cardListArr = [];
    var data = "";
    $("#statusConfirm").hide();

    //弹窗
    $(".add-card").click(function() {
        $(".card-list").toggle();
    });
    $(document).click(function() {
        $(".card-list").hide();
    });

    //阻止冒泡
    $(".add-card-box").click(function(event) {
        event.stopPropagation();
        return false;
    });

    //获取信息
    getCardList();
    get();




    function add() {
        laydate({
            elem: '#expiryDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
    }

    $("#edit").click(function() {
        $(".box").eq(0).hide();
        $(".box").eq(1).show();
    });


    $(".msg_table").on("change", ".card-num", function() {
        cardListArr[$(this).index(".card-num")].count = Number($(this).val());
    });

    function getList() {
        if (cardListArr.length > 0) {
            var template = $.templates("#queboxTpl");
            var html = template.render(cardListArr);
            $("#queboxCnt").html(html);
        } else {
            $("#queboxCnt").html("<tr><td colspan='3'>请选择优惠券...</td></tr>");
        }
    }


    function getCardList() {
        $.ajax({
                url: '../rest/backadmin/coupon/list',
                type: 'get',
                data: {
                    pageNum: 1,
                    pageSize: 1000
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var cardTemplate = $.templates("#cardTemplate");
                        var cardHtml = cardTemplate.render(r.data);
                        $("#card-list").html(cardHtml);

                        //添加列表逻辑
                        $(".card-list li").unbind("click").click(function() {
                            var _flag = true;
                            var thisItem = r.data[$(this).index(".card-list li")]; //当前点击的li的数据
                            $.each(cardListArr, function(index, el) {
                                if (thisItem.uuid == el.uuid) { //如果点击当前条的uuid等于当前数组元素里的uuid，那么当前元素的num＋1
                                    el.count += 1;
                                    getList();
                                    _flag = false;
                                    return false;
                                }
                            });
                            if (_flag == true) { //如果没有相等的uuid 向数组添加本条数据，并渲染模板
                                cardListArr.push(thisItem); //添加到数组
                                getList(); //执行函数渲染模板
                            }
                        });
                        $(".msg_table").off("click").on("click", ".delete", function() {
                            var this_uuid = $(this).attr("data-uuid"); //当前点击条目的uuid
                            $.each(r.data, function(index, el) {
                                if (el.uuid == this_uuid) {
                                    el.count = 1;
                                }
                            });
                            cardListArr.splice($(this).index(".delete"), 1);
                            getList(); //重新渲染模板
                        });

                    } else {
                        $("#card-list").html("<li class='color_red'>没有可选择的优惠券</li>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error");
            });
    }

    function get() {
        $.ajax({
                url: '../rest/backadmin/couponStrategy/operateGet',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    add();
                    if (r.data.type == "delete") {
                        cardListArr = r.data.oldData.couponMap.couponList;
                        var template = $.templates("#queboxTpl");
                        var html = template.render(cardListArr);
                        $("#queboxCnt").html(html);
                        var template_confirm = $.templates("#queboxTpl_confirm");
                        var html_confirm = template_confirm.render(cardListArr);
                        $("#queboxCnt_confirm").html(html_confirm);

                        $("#strategyIdentification").val(r.data.oldData.strategyIdentification);
                        $("#expiryDate").val(r.data.oldData.expiryDateCN);
                        $("#strategyIdentification_confirm").html(r.data.oldData.strategyIdentification);
                        $("#expiryDate_confirm").html(r.data.oldData.expiryDateCN);
                        $("#uuid").val(r.data.uuid);
                        if (r.data.status != "unpassed") {
                            $("#edit").hide();
                        }

                        if (r.data.type == "close" || r.data.type == "open" || r.data.type == "delete") {
                            $("#edit").hide();
                            if (r.data.type == "close") {
                                $("#statusMsg").html("关闭");
                            } else if (r.data.type == "open") {
                                $("#statusMsg").html("开启");
                            } else {
                                $("#statusMsg").html("删除");
                            }
                            $("#statusConfirm").show();

                        }
                    } else {
                        cardListArr = r.data.newData.couponMap.couponList;
                        var template = $.templates("#queboxTpl");
                        var html = template.render(cardListArr);
                        $("#queboxCnt").html(html);
                        var template_confirm = $.templates("#queboxTpl_confirm");
                        var html_confirm = template_confirm.render(cardListArr);
                        $("#queboxCnt_confirm").html(html_confirm);
                        $("#uuid").val(r.data.uuid);
                        $("#strategyIdentification").val(r.data.newData.strategyIdentification);
                        $("#expiryDate").val(r.data.newData.expiryDateCN);
                        $("#strategyIdentification_confirm").html(r.data.newData.strategyIdentification);
                        $("#expiryDate_confirm").html(r.data.newData.expiryDateCN);
                        if (r.data.status != "unpassed") {
                            $("#edit").hide();
                        }




                        if (r.data.type == "close" || r.data.type == "open" || r.data.type == "delete") {
                            $("#edit").hide();
                            if (r.data.type == "close") {
                                $("#statusMsg").html("关闭");
                            } else if (r.data.type == "open") {
                                $("#statusMsg").html("开启");
                            } else {
                                $("#statusMsg").html("删除");
                            }
                            $("#statusConfirm").show();

                        }
                    }
                } else {
                    layer.msg(r.status, {
                        time: 2000
                    });
                }

                $("#allRight").click(function() {
                    sub();
                });


            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //get();

    function sub() {
        if ($("#strategyIdentification").val() == "") {
            layer.msg("投放ID不能为空", {
                time: 2000
            });
            return false;
        }

        if ($("#expiryDate").val() == "") {
            layer.msg("截止时间不能为空", {
                time: 2000
            });
            return false;
        }

        if (cardListArr.length == 0) {
            layer.msg("请选择添加优惠券", {
                time: 2000
            });
            return false;
        }

        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);


        //优惠券uuid和数量
        var cardList = [];
        $.each(cardListArr, function(index, el) {
            cardList.push(el.uuid + "_" + el.count);
        });
        data = cardList.join(",");
        $("#coupon").val(data);
        $("#form").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    window.location.href = document.referrer;
                });
            } else {
                data = "";
                $("#coupon").val("");
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });



    }
});