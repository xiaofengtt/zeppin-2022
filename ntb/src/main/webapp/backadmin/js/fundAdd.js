// var goal = UE.getEditor('goal');
// var investStaregy = UE.getEditor('investStaregy');
// var investStandard = UE.getEditor('investStandard');
// var investIdea = UE.getEditor('investIdea');
// var investScope = UE.getEditor('investScope');
// var revenueFeature = UE.getEditor('revenueFeature');
// var riskManagement = UE.getEditor('riskManagement');
tinymecTool.load('goal', '');
tinymecTool.load('investStaregy', '');
tinymecTool.load('investStandard', '');
tinymecTool.load('investIdea', '');
tinymecTool.load('investScope', '');
tinymecTool.load('revenueFeature', '');
tinymecTool.load('riskManagement', '');
$(function() {
    getbankList();
    $(".datepicker").click(function() {
        laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm"),
            istime: false,
            istoday: false,
            format: 'YYYY-MM-DD'
        });
    });
    $("#formsubmit").submit(function(event) {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        var flag = true;
        var name = $("#name").val().replace(/(^\s*)|(\s*$)/g, "");
        var shortname = $("#shortname").val().replace(/(^\s*)|(\s*$)/g, "");
        var scode = $("#iscode").val().replace(/(^\s*)|(\s*$)/g, "");
        var type = $("#type").val().replace(/(^\s*)|(\s*$)/g, "");
        var setuptime = $("#setuptime").val().replace(/(^\s*)|(\s*$)/g, "");
        var planingScale = $("#planingScale").val().replace(/(^\s*)|(\s*$)/g, "");
        var collectStarttime = $("#collectStarttime").val().replace(/(^\s*)|(\s*$)/g, "");
        var collectEndtime = $("#collectEndtime").val().replace(/(^\s*)|(\s*$)/g, "");
        var purchaseStarttime = $("#purchaseStarttime").val().replace(/(^\s*)|(\s*$)/g, "");
        var purchaseEndtime = $("#purchaseEndtime").val().replace(/(^\s*)|(\s*$)/g, "");
        if (shortname == "") {
            layer.msg("活期理财简称不能为空", {
                time: 2000
            });
            return false;
        }
        if (name == "") {
            layer.msg("活期理财全称不能为空", {
                time: 2000
            });
            return false;
        }
        if (scode == "") {
            layer.msg("活期理财编号不能为空", {
                time: 2000
            });
            return false;
        }

        if (setuptime == "") {
            layer.msg("请输入成立日期", {
                time: 2000
            });
            return false;
        }
        if (planingScale == "" || isNaN(planingScale) == true) {
            layer.msg("请正确输入总募集规模", {
                time: 2000
            });
            return false;
        }
        if (collectStarttime == "") {
            layer.msg("请选择募集起始日", {
                time: 2000
            });
            return false;
        }
        if (collectEndtime == "") {
            layer.msg("请选择募集截止日", {
                time: 2000
            });
            return false;
        }
        if (purchaseStarttime == "") {
            layer.msg("日常申购起始日", {
                time: 2000
            });
            return false;
        }
        if (purchaseEndtime == "") {
            layer.msg("日常申购截止日", {
                time: 2000
            });
            return false;
        }
        if (flag) {
            $('#goal').val(string2json2(tinyMCE.get('goal').getContent()));
            $('#investStaregy').val(string2json2(tinyMCE.get('investStaregy').getContent()));
            $('#investStandard').val(string2json2(tinyMCE.get('investStandard').getContent()));
            $('#investIdea').val(string2json2(tinyMCE.get('investIdea').getContent()));
            $('#investScope').val(string2json2(tinyMCE.get('investScope').getContent()));
            $('#revenueFeature').val(string2json2(tinyMCE.get('revenueFeature').getContent()));
            $('#riskManagement').val(string2json2(tinyMCE.get('riskManagement').getContent()));

            var str = $(this).serialize();
            $.post('../rest/backadmin/fund/add', str, function(data) {
                if (data.status == "SUCCESS") {
                    // $('.uuid').val(data.data.uuid);
                    layer.msg(data.message, {
                        time: 1000
                    }, function() {
                        window.location.href = document.referrer;
                    });
                } else {
                    layer.msg(data.message, {
                        time: 2000
                    })
                }
            })
        }

        return false;
    });


    $("#planingScale").blur(function() {
        var planingScale = $("#planingScale").val().replace(/(^\s*)|(\s*$)/g, "");
        if (planingScale == "") {
            $("#planingScale").parent().parent().find("span").css("display", "block");
        } else {
            $("#planingScale").parent().parent().find("span").css("display", "none");
            $("#planingScale").val(FormatAfterDotNumber(planingScale, 2));
        }
    })


    function getbankList() {
        //初始化资金托管方数据
        $.get('../rest/backadmin/bank/list?pageNum=1&pageSize=1000', function(r) {
            if (r.status == 'SUCCESS') {
                var htmls = '';
                for (var i = 0; i < r.totalResultCount; i++) {
                    htmls += '<option value="' + r.data[i].uuid + '" data-logo="' + r.data[i].logoUrl + '">' + r.data[i].name + '</option>';
                    // custodian[r.data[i].uuid] = r.data[i].name;
                }
                $('#gp').append(htmls);
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }


    $("#fundRate").blur(function() {
        var fundRate = $("#fundRate").val().replace(/(^\s*)|(\s*$)/g, "");
        if (fundRate != "") {
            $("#fundRate").val(FormatAfterDotNumber(fundRate, 4));
        }
    })
    $("#fundRateLowlimit").blur(function() {
        var fundRateLowlimit = $("#fundRateLowlimit").val().replace(/(^\s*)|(\s*$)/g, "");
        if (fundRateLowlimit != "") {
            $("#fundRateLowlimit").val(FormatAfterDotNumber(fundRateLowlimit, 4));
        }
    })
    $("#fundOpenRate").blur(function() {
        var fundOpenRate = $("#fundOpenRate").val().replace(/(^\s*)|(\s*$)/g, "");
        if (fundOpenRate != "") {
            $("#fundOpenRate").val(FormatAfterDotNumber(fundOpenRate, 4));
        }
    })
    $("#fundRateUpperlimit").blur(function() {
        var fundRateUpperlimit = $("#fundRateUpperlimit").val().replace(/(^\s*)|(\s*$)/g, "");
        if (fundRateUpperlimit != "") {
            $("#fundRateUpperlimit").val(FormatAfterDotNumber(fundRateUpperlimit, 4));
        }
    })

    $(".secondstep .prev2").click(function() {
        $(".firststep").css("display", "block");
        $(".secondstep").css("display", "none");
        $(".order li:eq(0)").removeClass("done").addClass("on").find("img").attr("src", "img/on.png");
        $(".order li:eq(1)").removeAttr("class").find("img").attr("src", "img/Oval.png");
    });
    $(".thirdstep .prev2").click(function() {
        $(".secondstep").css("display", "block");
        $(".thirdstep").css("display", "none");
        $(".order li:eq(1)").removeClass("done").addClass("on").find("img").attr("src", "img/on.png");
        $(".order li:eq(2)").removeAttr("class").find("img").attr("src", "img/Oval.png");
    });
    $(".forthstep .prev2").click(function() {
        $(".thirdstep").css("display", "block");
        $(".forthstep").css("display", "none");
        $(".order li:eq(2)").removeClass("done").addClass("on").find("img").attr("src", "img/on.png");
        $(".order li:eq(3)").removeAttr("class").find("img").attr("src", "img/Oval.png");
    });

    $('.defaultkey1').focus(function() {
        var value = $(this).val();
        if (value == '0.00') {
            value = ''
        }
        $(this).val(value);
    })
})


var fundRateIndex = 0;
var fundSp = '_';

function addFundRate() {
    var type = $("#fundRateType").val();
    var typeName = $("#fundRateType").html();
    var fundRateLowlimit = $("#fundRateLowlimit").val();
    var fundRateUpperlimit = $("#fundRateUpperlimit").val();
    var fundRate = $("#fundRate").val();
    var fundOpenRate = $("#fundOpenRate").val();
    if (fundRateLowlimit == '') {
        layer.msg('资金下限不能为空', {
            time: 2000
        });
    } else if (fundRateUpperlimit == '') {
        layer.msg('资金上限不能为空', {
            time: 2000
        });
    } else if (fundRate == '') {
        layer.msg('费率', {
            time: 2000
        });
    } else if (fundOpenRate == '') {
        layer.msg('优惠费率', {
            time: 2000
        });
    } else {
        var fundRateStr = type + fundSp + fundRateLowlimit + fundSp + fundRateUpperlimit + fundSp + fundRate + fundSp + fundOpenRate;
        $("#fundRateDiv").append('<input type="hidden" name="fundRates" id="fundRateInput' + fundRateIndex + '" value="' + fundRateStr + '"/>');
        $("#fundRateTable").append('<tr id="fundRateTr' + fundRateIndex + '"><td class="typeTd">' + typeName + '</td><td class="cssa01a7f8496995">' + fundRateLowlimit + '元(含)至' + fundRateUpperlimit + '元</td><td>' +
            fundRate + '</td><td class="cssa01a7f8496995">' + fundOpenRate + '</td><td><a class="delete" onclick="deleteFundRate(' + fundRateIndex + ')">删除</a></td></th>');
        fundRateIndex++;
    }
}

function deleteFundRate(fri) {
    $("#fundRateInput" + fri).remove();
    $("#fundRateTr" + fri).remove();
}

//比较日期大小
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

//判断日期，时间大小
function compareTime(startDate, endDate) {

    if (startDate.length > 0 && endDate.length > 0) {

        var startDateTemp = startDate.split(" ");
        var endDateTemp = endDate.split(" ");

        var arrStartDate = startDateTemp[0].split("-");
        var arrEndDate = endDateTemp[0].split("-");

        var arrStartTime = startDateTemp[1].split(":");
        var arrEndTime = endDateTemp[1].split(":");

        var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
        var allEndDate = new Date(arrEndDate[0], arrEndDate[1], arrEndDate[2], arrEndTime[0], arrEndTime[1], arrEndTime[2]);

        if (allStartDate.getTime() >= allEndDate.getTime()) {
            return false;
        } else {
            return true;
        }
    } else {
        return false;
    }
}