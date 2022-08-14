$(document).ready(function() {
    $(".msg_table").eq(1).hide();
    $(".msg_table").eq(2).hide();
    $("#free").hide();

    var pageNum = 1;
    var flag = true;
    var stage = "collect";

    var conditionList = {
        "list": [],
        "type": "",
        "name": "",
        "pageNum": "",
        "url": getHtmlDocName()
    };


    $(".msg_table").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        $(".statusLight").each(function(index, el) {
            if ($(".statusLight").eq(index).prop("id") != "all") {
                conditionList.list.push($(".statusLight").eq(index).prop("id"));
            }
        });
        conditionList.pageNum = pageNum;
        conditionList.type = stage;
        conditionList.name = $("#search").val();
        sessionStorage.setItem("condition", JSON.stringify(conditionList));

    });
    getBankList();
    getStageList();


    function init() {
        pageNum = 1;
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

    $(".filter a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        getList();
        init();
    });


    function getBankList() {
        $.get('../rest/backadmin/bank/list?pageNum=1&pageSize=1000', function(r) {
            if (r.status == 'SUCCESS') {
                var htmls = '';
                for (var i = 0; i < r.totalResultCount; i++) {
                    htmls += '<a id="' + r.data[i].uuid + '">' + r.data[i].name + '</a>';
                }
                $('#custodians').append(htmls);
                $(".filter a").click(function() {
                    $(this).addClass("statusLight").siblings().removeClass("statusLight");
                    init();
                    getList();
                });

                if (sessionStorage.getItem("condition")) {
                    var json = JSON.parse(sessionStorage.getItem("condition"));
                    if (json.url == getHtmlDocName()) {
                        $.each(json.list, function(index, el) {
                            $("#" + el).addClass('statusLight').siblings().removeClass('statusLight');
                        });
                        pageNum = json.pageNum;
                        $("#" + json.type).addClass('highLight').parent().siblings("li").find("a").removeClass('highLight');
                        stage = json.type;
                        $("#search").val(json.name);
                        if (json.type == "collect") {
                            $(".msg_table").eq(0).show().siblings('.msg_table').hide();
                        }
                        if (json.type == "income") {
                            $(".msg_table").eq(1).show().siblings('.msg_table').hide();
                        }
                        if (json.type == "finished") {
                            $(".msg_table").eq(2).show().siblings('.msg_table').hide();
                        }
                    } else {
                        sessionStorage.removeItem("condition");
                    }

                }
                getList();
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }

    function getStageList() {
        $("#collectCount").html("(0)");
        $("#incomeCount").html("(0)");
        $("#finishedCount").html("(0)");
        $.ajax({
                url: '../rest/backadmin/bankFinancialProduct/stageList',
                type: 'get',
                data: {
                    invested: 1
                }
            })
            .done(function(r) {
                $.each(r.data, function(i, el) {
                    $("#" + el.status + "Count").html("(" + el.count + ")");
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }


    function filterControl(t) {
        if ($('#moreFilter').hasClass('hide')) {
            $('#moreFilter').removeClass('hide');
            $('#filterController').html('收起');
        } else {
            $('#moreFilter').addClass('hide');
            $('#filterController').html('展开');
        }
        init();

    }

    $("#filterController").click(function() {
        filterControl(this);
    });

    $(".item").click(function() {
        $(this).find("a").addClass('highLight').parent().siblings("li").find("a").removeClass('highLight');
    });

    //投资中
    $(".select_invest_list li a").eq(0).click(function() {
        $(".msg_table").eq(0).show().siblings('.msg_table').hide();
        stage = "collect";
        init();
        getList();
    });


    //收益中
    $(".select_invest_list li a").eq(1).click(function() {
        $(".msg_table").eq(1).show().siblings('.msg_table').hide();
        stage = "income";
        init();
        getList();
    });


    //已结束
    $(".select_invest_list li a").eq(2).click(function() {
        $(".msg_table").eq(2).show().siblings('.msg_table').hide();
        stage = "finished";
        init();
        getList();
    });


    function getList() {
        var name = $("#search").val();
        var incomeValue = $(".filter-income").find(".statusLight").prop("id");
        var termValue = $(".filter-term").find(".statusLight").prop("id");
        var custodianValue = $(".filter-custodian").find(".statusLight").prop("id");
        var isRedeemValue = $(".filter-isRedeem").find(".statusLight").prop("id");
        $.ajax({
                url: '../rest/backadmin/bankFinancialProduct/list',
                type: 'get',
                data: {
                    "name": name,
                    "stage": stage,
                    "pageNum": pageNum,
                    "status": "checked",
                    "pageSize": "10",
                    "custodian": custodianValue,
                    "income": incomeValue,
                    "term": termValue,
                    "invested": 1,
                    "isRedeem": (stage == "finished") ? isRedeemValue : ""
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    function formatDateTime()  {      
                        var  date  =  new  Date();    
                        var  y  =  date.getFullYear();      
                        var  m  =  date.getMonth()  +  1;      
                        m  =  m  <  10  ?  ('0'  +  m)  :  m;      
                        var  d  =  date.getDate();      
                        d  =  d  <  10  ?  ('0'  +  d)  :  d;      
                        var  h  =  date.getHours();    
                        h  =  h  <  10  ?  ('0'  +  h)  :  h;    
                        var  minute  =  date.getMinutes();    
                        var  second  =  date.getSeconds();    
                        minute  =  minute  <  10  ?  ('0'  +  minute)  :  minute;      
                        second  =  second  <  10  ?  ('0'  +  second)  :  second;     
                        return  y  +  '-'  +  m  +  '-'  +  d;  
                    }

                    function GetDateDiff(startDate, endDate) {
                        var startTime = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
                        var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
                        var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
                        return dates;
                    }

                    function compareDate(checkStartDate, checkEndDate) {
                        var arys1 = new Array();
                        var arys2 = new Array();
                        if (checkStartDate != null && checkEndDate != null) {

                            arys1 = checkStartDate.split('-');

                            var sdate = new Date(arys1[0], parseInt(arys1[1] - 1), arys1[2]);
                            arys2 = checkEndDate.split('-');
                            var edate = new Date(arys2[0], parseInt(arys2[1] - 1), arys2[2]);
                            if (sdate > edate) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }

                    if (stage == "collect") {
                        if (r.totalResultCount > 0) {
                            $(".filter-isRedeem").hide();
                            var template = $.templates("#collectTpl");
                            var html = template.render(r.data);
                            $("#collectCnt").html(html);
                        } else {
                            $("#collectCnt").html("<tr><td colspan='4'>没有数据</td></tr>");
                            return false;
                        }


                        //判断时间
                        $.each(r.data, function(index, el) {
                            var now = formatDateTime();
                            var realTime;
                            var startTime = el.collectStarttime.replace(/\./g, "-");
                            var endTime = el.collectEndtime.replace(/\./g, "-");
                            if (compareDate(endTime, now) == false) {
                                realtime = GetDateDiff(startTime, now);
                                console.log(realtime);
                                $(".invest_date").eq(index).html("<span style='font-size:16px;' class='color_orange'>" + realtime + "</span>天后开始");
                            } else {
                                if (compareDate(endTime, now) == false) {
                                    realtime = GetDateDiff(endTime, now);
                                    console.log(realtime);
                                    $(".invest_date").eq(index).html("还剩<span style='font-size:16px;' class='color_orange'>" + realtime + "</span>天");
                                } else {
                                    if (compareDate(endTime, now) == true) {
                                        $(".invest_date").eq(index).html("认购已截止");
                                    } else {
                                        if (el.collectEndtimestamp > Date.now()) {
                                            var now_time = (el.collectEndtimestamp - Date.now()) / 1000 / 60 / 60;
                                            $(".invest_date").eq(index).html("<span style='font-size:16px;' class='color_orange'>" + now_time.toFixed(1) + "</span>小时后截至");
                                        } else if (el.collectEndtimestamp < Date.now()) {
                                            $(".invest_date").eq(index).html("认购已截止");
                                        }
                                    }
                                }
                            }
                        });

                    }

                    if (stage == "income") {
                        if (r.totalResultCount > 0) {
                            $(".filter-isRedeem").hide();
                            var template = $.templates("#incomeTpl");
                            var html = template.render(r.data);
                            $("#incomeCnt").html(html);

                            $.each(r.data, function(index, el) {
                                var returnRate = parseFloat(el.targetAnnualizedReturnRate) / 100;
                                var invest = el.investment;
                                var result = (returnRate * invest) * (el.term / 365);
                                var resultR = formatNum(result.toFixed(2));
                                $(".totalReturn").eq(index).html(resultR);
                            });


                            //到期日
                            $.each(r.data, function(index, el) {
                                var now = formatDateTime();
                                var targetTime = GetDateDiff(now, el.maturityDate);
                                console.log(targetTime);
                                if (targetTime >= 1) {
                                    $(".target_date").eq(index).html(Math.ceil(targetTime) + "天");
                                } else if (targetTime < 1) {
                                    $(".target_date").eq(index).html("<span class='color_orange'>今天到期</span>");
                                }
                            });
                        } else {
                            $("#incomeCnt").html("<tr><td colspan='5'>没有数据</td></tr>");
                            return false;
                        }
                    }

                    if (stage == "finished") {
                        if (r.totalResultCount > 0) {
                            $(".filter-isRedeem").show();
                            var template = $.templates("#finishedTpl");
                            var html = template.render(r.data);
                            $("#finishedCnt").html(html);
                        } else {
                            $("#finishedCnt").html("<tr><td colspan='6'>没有数据</td></tr>");
                            return false;
                        }
                    }

                    if (flag) {
                        $('#pageTool').Paging({
                            pagesize: r.pageSize,
                            count: r.totalResultCount,
                            callback: function(page, size, count) {
                                pageNum = page;
                                getList();
                                flag = false;
                                document.body.scrollTop = document.documentElement.scrollTop = 0;
                            },
                            render: function(ops) {

                            }
                        });
                        $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                    }

                    //入口弹框
                    $(".investAllMsg").colorbox({
                        iframe: true,
                        width: "1050px",
                        height: "720px",
                        opacity: '0.5',
                        overlayClose: false,
                        escKey: true
                    });

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
}); //document.ready

// 活期逻辑代码
// 活期逻辑代码
// 活期逻辑代码

$(document).ready(function() {
    var pageNum = 1;
    var flag = true;
    getFreeList();
    $("[name='my-checkbox']").bootstrapSwitch({
        onSwitchChange: function(event, state) {
            if (state == true) {
                $("#fix").show();
                $("#free").hide();
            } else {
                $("#fix").hide();
                $("#free").show();
            }
        }
    });

    function getFreeList() {
        $.ajax({
                url: '../rest/backadmin/fund/list',
                type: 'get',
                data: {
                    invested: '1',
                    pageSize: '10',
                    pageNum: pageNum
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#freeTpl");
                        var html = template.render(r.data);
                        $("#freeCnt").html(html);
                    } else {
                        $("#freeCnt").html("<tr><td colspan='6'>没有数据</td></tr>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                if (flag) {
                    $('#pageTools').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getFreeList();
                            flag = false;
                            document.body.scrollTop = document.documentElement.scrollTop = 0;
                        }
                    });
                    $("#pageTools").find(".ui-paging-container:last").siblings().remove();
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }
});