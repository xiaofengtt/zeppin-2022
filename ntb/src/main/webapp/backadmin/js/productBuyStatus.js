$(document).ready(function() {
    var pageNum = 1;
    $(".filter ul").hide();
    flag = true;
    var deadline = "3";
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

    $("#searchButton1").click(function() {
        searchBtn();
    });
    $("#search1").bind("keypress", function(event) {
        if (event.keyCode == 13) {
            searchBtn();
            return false;
        }
    });
    $("#searchButton").click(function() {
        getChars();
    });
    $("#search").bind("keypress", function(event) {
        if (event.keyCode == 13) {
            getChars();
            return false;
        }
    });

    $(".filter ul span").css({
        "cursor": "pointer"
    });
    $(".filter > span").css({
        "cursor": "pointer",
        "padding-left": "5px"
    });
    $(".filter ul span").click(function(event) {
        deadline = $(this).attr("data-value");
        $(".filter > span").html($(this).html());
        $(this).addClass('color_blue').parent().siblings().find("span").removeClass("color_blue");
        $("#start").val("");
        $("#end").val("");
        init();
        getChars();

    });
    $(".filter ul").click(function(event) {
        event.stopPropagation();
        return false;
    });
    $(".filter>span").click(function(event) {
        $(".filter ul").toggle();
        event.stopPropagation();
        return false;
    });
    $(document).click(function() {
        $(".filter ul").hide();
    });
    $("#container").click(function() {
        $(".filter ul").hide();
    });
    getChars();

    function getChars() {
        var name = $("#search").val();
        $.ajax({
                url: '../rest/backadmin/investorProduct/listDailyProductBuyRecords',
                type: 'get',
                data: {
                    "pageNum": 1,
                    "pageSize": 1000000,
                    "deadline": deadline,
                    "starttime": $("#start").val(),
                    "endtime": $("#end").val(),
                    "productName": name
                }
            })
            .done(function(r) {
                ! function() {

                    //laydate.skin('molv');

                    laydate({
                        elem: '#start',
                        format: 'YYYY-MM-DD',
                        istime: false,
                        choose: function() {
                            deadline = "";
                            console.log(deadline);
                            $(".filter span").removeClass("color_blue");
                            $(".filter>span").html($("#start").val() + "/" + $("#end").val());
                            getChars();
                        }
                    });
                    laydate({
                        elem: '#end',
                        format: 'YYYY-MM-DD',
                        istime: false,
                        choose: function() {
                            deadline = "";
                            console.log(deadline);
                            $(".filter span").removeClass("color_blue");
                            $(".filter>span").html($("#start").val() + "/" + $("#end").val());
                            getChars();
                        }
                    });

                }();

                $("#recharge").html(r.data.totalRechargeCN);
                $("#withDraw").html(r.data.totalWithdrawCN);
                $("#balance").html(r.data.accountBalanceCN);
                if (r.data.totalWithdraw > 0) {
                    $("#withDraw").addClass("color_orange");
                } else if (r.data.totalWithdraw < 0) {
                    $("#withDraw").addClass("color_green");
                }

                // var timeArr = [];
                // var valueArr = [];
                // $.each(r.data.dataList, function(index, el) {
                //     timeArr.push(el.time);
                //     valueArr.push(parseFloat(el.price));
                // });
                // console.log(timeArr);
                //生成图表
                $('#container').highcharts({
                    chart: {
                        zoomType: 'x'
                    },
                    title: {
                        text: '用户认购查询数据'
                    },
                    subtitle: {
                        text: document.ontouchstart === undefined ?
                            '鼠标拖动可以进行缩放' : '手势操作进行缩放'
                    },
                    xAxis: {
                        categories: r.data.listDate
                    },
                    tooltip: {
                        shared: false
                    },
                    yAxis: {
                        title: {
                            text: '金额'
                        },
                        labels: {
                            format: '{value}元',
                            formatter: function() {
                                if (this.value > 10000) {
                                    return this.value / 10000 + "万元";
                                } else {
                                    return this.value + "元";
                                }
                            }
                        },
                        opposite: true
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'left',
                        x: 80,
                        verticalAlign: 'top',
                        y: 55,
                        floating: true,
                        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
                    },
                    plotOptions: {
                        area: {
                            fillColor: {
                                linearGradient: {
                                    x1: 0,
                                    y1: 0,
                                    x2: 0,
                                    y2: 1
                                },
                                stops: [
                                    [0, Highcharts.getOptions().colors[0]],
                                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                ]
                            },
                            marker: {
                                radius: 2
                            },
                            lineWidth: 1,
                            states: {
                                hover: {
                                    lineWidth: 1
                                }
                            },
                            threshold: null
                        }
                    },

                    series: [{
                        type: 'spline',
                        name: '金额',
                        data: r.data.listBuyRecords,
                        tooltip: {
                            valueSuffix: ' 元'
                        }
                    }]
                });

            })
            .fail(function() {

            });
    }


    function getList() {
        var name = $("#search1").val();
        $.ajax({
                url: '../rest/backadmin/investorProduct/listProductBuyRecords',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": name
                }
            })
            .done(function(r) {
                $("#totalAmount").html("投资金额：" + r.data.totalAmountCN);



                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queBoxTpl");
                        var html = template.render(r.data.dataList);
                        $("#queBoxCnt").html(html);

                        //投资入口弹框
                        $(".bill_message").colorbox({
                            iframe: true,
                            width: "600px",
                            height: "580px",
                            opacity: '0.5',
                            overlayClose: false,
                            escKey: true
                        });
                    } else {
                        layer.msg(r.message, {
                            time: 2000
                        });
                    }
                } else {
                    layer.msg("error", {
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

    }
});
