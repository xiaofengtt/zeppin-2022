$(document).ready(function() {
    var cardListArr = [];
    var data = "";
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



    $(".msg_table").on("change", ".card-num", function() {
        cardListArr[$(this).index(".card-num")].count = Number($(this).val());
    });

    $("#allRight").click(function() {
        sub();
    });

    add();
    getCardList();



    function add() {
        laydate({
            elem: '#expiryDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
    }


    function getList() {
        if (cardListArr.length > 0) {
            var template = $.templates("#queboxTpl");
            var html = template.render(cardListArr);
            $("#queboxCnt").html(html);
        } else {
            $("#queboxCnt").html("<tr><td colspan='3'>请选择优惠券...</td></tr>");
        }
    }

    //获取优惠券列表
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
                        layer.msg("请先添加优惠券", {
                            time: 2000
                        }, function() {
                            parent.$.colorbox.close();
                        });
                    }
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
    }

    //提交！！！
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
                    parent.$.colorbox.close();
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


}); //document.ready