$(document).ready(function() {
    $(".balance").hide();
    var pageNum = 1;
    var flag = true;
    var balanceType = "balance";
    var order = 'desc';

    var conditionList = {
        "list": [],
        "type": "",
        "name": "",
        "pageNum": "",
        "order": "",
        "url": getHtmlDocName()
    };



    $(".unbalance,.balance").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        $(".statusLight").each(function(index, el) {
            if ($(".statusLight").eq(index).prop("id") != "all") {
                conditionList.list.push($(".statusLight").eq(index).prop("id"));
            }
        });
        conditionList.pageNum = pageNum;
        conditionList.type = balanceType;
        conditionList.name = $("#search").val();
        conditionList.sort = $(".light a").attr("data-name");
        conditionList.order = order;
        sessionStorage.setItem("condition", JSON.stringify(conditionList));

    });

    function init() {
        pageNum = 1;
        flag = true;
    }

    getTypeList();

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

    $(".sort .sorting-btns li").click(function() {
        $(this).addClass("light").siblings().removeClass("light");
        $(this).removeClass("asc").removeClass("desc");
        order = order == 'desc' ? 'asc' : 'desc';
        $(this).addClass(order).siblings().removeClass("asc").removeClass("desc");
        getList();
    });

    getBankList();

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

                if (sessionStorage.getItem("condition")) {
                    var json = JSON.parse(sessionStorage.getItem("condition"));
                    if (json.url == getHtmlDocName()) {
                        $.each(json.list, function(index, el) {
                            $("#" + el).addClass('statusLight').siblings().removeClass('statusLight');
                        });
                        pageNum = json.pageNum;
                        balanceType = json.type;
                        order = json.order;
                        $("#" + json.type).addClass('highLight').parent().siblings("li").find("a").removeClass('highLight');
                        $("[data-name='" + json.sort + "']").parent().addClass('light').siblings().removeClass('light');
                        $("[data-name='" + json.sort + "']").parent().addClass(order).siblings().removeClass("desc").removeClass('asc');
                        $("#search").val(json.name);
                        if (json.type == "balance") {
                            $(".unbalance").show().siblings('.balance').hide();
                        }
                        if (json.type == "finished") {
                            $(".balance").show().siblings('.unbalance').hide();
                        }
                    } else {
                        sessionStorage.removeItem("condition");
                    }
                }
                getList();
                $(".filter a").click(function() {
                    $(this).addClass("statusLight").siblings().removeClass("statusLight");
                    init();
                    getList();
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }

    $(".item").click(function() {
        $(this).find("a").addClass('highLight').parent().siblings("li").find("a").removeClass('highLight');
    });
    $(".select_page li a").eq(0).click(function() {
        $(".unbalance").show().siblings('.balance').hide();
        balanceType = "balance";
        init();
        getList();
    });


    //收益中
    $(".select_page li a").eq(1).click(function() {
        $(".balance").show().siblings('.unbalance').hide();
        init();
        balanceType = "finished";

        getList();
    });

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

    function getTypeList() {
        $("#banlanceCount").html("(0)");
        $("#finishedCount").html("(0)");
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductPublish/balanceList',
                type: 'get',
                data: {
                    "invested": 1
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

    function getList() {
        var name = $("#search").val();
        var incomeValue = $(".filter-income").find(".statusLight").prop("id");
        var termValue = $(".filter-term").find(".statusLight").prop("id");
        var custodianValue = $(".filter-custodian").find(".statusLight").prop("id");
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductPublish/list',
                type: 'get',
                data: {
                    "name": name,
                    "balanceType": balanceType,
                    "pageNum": pageNum,
                    "pageSize": "10",
                    "custodian": custodianValue,
                    "income": incomeValue,
                    "term": termValue,
                    "sorts": $(".light a").attr("data-name") + "-" + order,
                    "status": "checked",
                    "invested": 1
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (balanceType == "balance") {
                        if (r.totalResultCount > 0) {
                            $(".balance").hide();
                            var template = $.templates("#unbalanceTpl");
                            var html = template.render(r.data);
                            $("#unbanlanceCnt").html(html);
                        } else {
                            $("#unbanlanceCnt").html("<tr><td colspan='4'>没有数据</td></tr>");
                        }
                    }

                    if (balanceType == "finished") {
                        if (r.totalResultCount > 0) {
                            $(".unbalance").hide();
                            var template = $.templates("#balanceTpl");
                            var html = template.render(r.data);
                            $("#balanceCnt").html(html);
                        } else {
                            $("#balanceCnt").html("<tr><td colspan='4'>没有数据</td></tr>");
                        }
                    }
                    //募集信息入口弹框
                    $(".collectMessage").colorbox({
                        iframe: true,
                        width: "1050px",
                        height: "650px",
                        opacity: '0.5',
                        overlayClose: false,
                        escKey: true
                    });
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
});