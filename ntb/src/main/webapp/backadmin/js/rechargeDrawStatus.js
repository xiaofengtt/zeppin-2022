$(document).ready(function() {
    $(".filter ul").hide();
    var pageNum = 1;
    flag = true;
    deadline = "3";
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
    // $("#start").change(function() {
    //     deadline = "";
    //     console.log(deadline);
    // });
    // $("#end").change(function() {
    //     deadline = "";
    //     console.log(deadline);
    //
    // });
    getChars();

    function getChars() {
        $.ajax({
                url: '../rest/backadmin/investorProduct/listDailyInvestorRecharge',
                type: 'get',
                data: {
                    "pageNum": 1,
                    "pageSize": 1000000,
                    "deadline": deadline,
                    "starttime": $("#start").val(),
                    "endtime": $("#end").val()
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

                var timeArr = [];
                var valueArr = [];
                // $.each(r.data.dataList, function(index, el) {
                //     timeArr.push(el.createtimeCN);
                //     valueArr.push(parseFloat(el.price));
                // });
                console.log(timeArr);
                //????????????
                $('#container').highcharts({
                    chart: {
                        zoomType: 'xy'
                    },
                    title: {
                        text: '??????????????????????????????????????????'
                    },
                    subtitle: {
                        text: '????????????: backadmin.niutoulicai.com'
                    },
                    xAxis: [{
                        label: {

                        },
                        categories: r.data.listDate,
                        crosshair: true
                    }],
                    yAxis: [{ // Primary yAxis
                        labels: {
                            format: '{value}??????',
                            style: {
                                color: "#E0615F"
                            },
                            formatter: function() {
                                if (this.value > 10000) {
                                    return this.value / 10000 + "??????";
                                } else {
                                    return this.value + "???";
                                }

                            }
                        },
                        title: {
                            text: '??????',
                            style: {
                                color: "#E0615F"
                            }
                        },
                        opposite: true
                    }],
                    tooltip: {
                        shared: false
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
                    series: [{
                        name: '???????????????',
                        type: 'column',
                        yAxis: 0,
                        data: r.data.listTotalReturn,
                        color: '#C1CCE6',
                        tooltip: {
                            valueSuffix: ' ???'
                        }
                    }, {
                        name: '??????',
                        type: 'spline',
                        yAxis: 0,
                        data: r.data.listWithdraw,
                        color: 'green',
                        marker: {
                            enabled: true
                        },
                        tooltip: {
                            valueSuffix: ' ???'
                        }
                    }, {
                        name: '??????',
                        type: 'spline',
                        yAxis: 0,
                        data: r.data.listRecharge,
                        color: '#E0615F',
                        tooltip: {
                            valueSuffix: ' ???'
                        }
                    }]
                });

            })
            .fail(function() {

            });
    }

    function getList() {
        var name = $("#search").val();
        $.ajax({
                url: '../rest/backadmin/investorProduct/listInvestorRecharge',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": name
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queBoxTpl");
                        var html = template.render(r.data.dataList);
                        $("#queBoxCnt").html(html);

                        //??????????????????
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
                }
                //??????????????????
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