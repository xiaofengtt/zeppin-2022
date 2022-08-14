var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var jsonDate = {
    "flagCloseend": { //是否封闭产品
        "true": "是",
        "false": "否"
    },
    "currencyType": { //理财币种
        "rmb": "人民币",
        "dollar": "外币"
    },
    "flagRedemption": {
        "false": "不可提前赎回",
        "true": "可提前赎回"
    },
    "flagFlexible": {
        "false": "否",
        "true": "是"
    },
    "flagPurchase": {
        "false": "不可在投资期间申购",
        "true": "可在投资期间申购"
    }
}
$(function() {
    $(".datepicker").click(function() {
        laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm"),
            istime: false,
            istoday: false,
            format: 'YYYY-MM-DD'
        });
    });
    $("#collectStarttime").click(function() {
        var start = laydate.now(0, "YYYY/MM/DD"),
            start = start + " 09:00:00";
        laydate({
            start: start,
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    });
    $("#collectEndtime").click(function() {
        var start = laydate.now(0, "YYYY/MM/DD"),
            start = start + " 21:00:00";
        laydate({
            start: start,
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    });

    $("#resourceId").uploadFile({
        id: "1",
        url: "../rest/backadmin/resource/add",
        allowedTypes: "doc,docx,pdf",
        maxFileSize: 1024 * 1024 * 1024 * 10,
        fileName: "file",
        maxFileCount: 1,
        dragDropStr: "",
        extErrorStr: "文件格式不正确，请上传指定类型的文件",
        multiple: false,
        showDone: false,
        showDelete: true,
        deletelStr: '删除',
        doneStr: "完成",
        showAbort: false,
        showStatusAfterSuccess: false,
        maxFileCountErrorStr: "文件数量过多，请先删除。",
        onSuccess: function(files, data, xhr) {
            $('input[name="document"]').val(data.data.uuid);
            $('#imageShow').attr('href', '..' + data.data.url).html(data.data.filename + '.' + data.data.filetype).show();
            $('#imageShow').prop("target", "_blank");
            $(".ajax-upload-dragdrop").css("display", "none");
        }
    });
    $(".edit").click(function() {
        $(".content-item-info").css("display", "none");
        $(".content-item-edit").css("display", "block");
        $(".content-items b").css("right", "30px");
        $(this).css("display", "none");
        $(".save").css("display", "block");
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
});





$("#valueDate").click(function() {
    laydate({
        start: laydate.now(0, "YYYY/MM/DD hh:mm"),
        istime: false,
        istoday: false,
        format: 'YYYY-MM-DD',
        choose: function(datas) {
            var maturityDate = $("#maturityDate").val().replace(/(^\s*)|(\s*$)/g, "");
            if (datas != "" && maturityDate != "") {
                if (compareDate(datas, maturityDate)) {
                    var days = GetDateDiff(datas, maturityDate);
                    $("#term").val(days);
                }
            }
        }
    });
});
$("#maturityDate").click(function() {
    laydate({
        start: laydate.now(0, "YYYY/MM/DD hh:mm"),
        istime: false,
        istoday: false,
        format: 'YYYY-MM-DD',
        choose: function(datas) {
            if (valueDate != "" && datas != "") {
                var valueDate = $("#valueDate").val().replace(/(^\s*)|(\s*$)/g, "");
                if (compareDate(valueDate, datas)) {
                    var days = GetDateDiff(valueDate, datas);
                    $("#term").val(days);
                }
            }
        }
    });
});
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

function GetDateDiff(startDate, endDate) {
    var startTime = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
    var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
    var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
    return dates;
}
getDate();
//获取值
function getDate() {
    $.get('../rest/backadmin/fund/operateGet?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $('.uuid').val(r.data.uuid);
            $("#titlename").html(r.data.newData.name);
            if (r.data.status == 'checked') {
                $(".edit").remove();
                $(".sureBtn").remove();
            }
            $("#shortnames").html(r.data.newData.shortname);
            $("#shortname").val(r.data.newData.shortname);
            $("#names").html(r.data.newData.name);
            $("#name").val(r.data.newData.name);
            $("#scodes").html(r.data.newData.scode);
            $("#ascode").val(r.data.newData.scode);
            $("#types").html(r.data.newData.typeCN);
            $("#type").val(r.data.newData.type);
            $("#setuptimes").html(r.data.newData.setuptimeCN);
            $("#setuptime").val(r.data.newData.setuptimeCN);
            $("#performanceLevels").html(r.data.newData.performanceLevel);
            $("#performanceLevel").val(r.data.newData.performanceLevel);
            $("#gps").html(r.data.newData.gpName);
            $("#flagStructureds").html(r.data.newData.flagStructuredCN);
            if (r.data.newData.flagStructured == false) {
                $("#flagStructured").val("false");
            } else {
                $("#flagStructured").val("true");
            }
            $("#structuredTypes").html(r.data.newData.structuredTypeCN);
            $("#structuredType").val(r.data.newData.structuredType);
            $("#structuredRemarks").html(r.data.newData.structuredRemark);
            $("#structuredRemark").val(r.data.newData.structuredRemark);
            $("#styles").html(r.data.newData.styleCN);
            $("#style").val(r.data.newData.style);
            $("#riskLevels").html(r.data.newData.riskLevel);
            $("#riskLevel").val(r.data.newData.riskLevel);
            $("#creditLevels").html(r.data.newData.creditLevelCN);
            $("#creditLevel").val(r.data.newData.creditLevel);
            $("#planingScales").html(r.data.newData.planingScale);
            $("#planingScale").val(FormatAfterDotNumber(r.data.newData.planingScale, 2));
            $("#collectStarttimes").html(r.data.newData.collectStarttimeCN);
            $("#collectStarttime").val(r.data.newData.collectStarttimeCN);
            $("#collectEndtimes").html(r.data.newData.collectEndtimeCN);
            $("#collectEndtime").val(r.data.newData.collectEndtimeCN);
            $("#purchaseStarttimes").html(r.data.newData.purchaseStarttimeCN);
            $("#purchaseStarttime").val(r.data.newData.purchaseStarttimeCN);
            $("#purchaseEndtimes").html(r.data.newData.purchaseEndtimeCN);
            $("#purchaseEndtime").val(r.data.newData.purchaseEndtimeCN);
            $("#goals").html(escape2Html(r.data.newData.goal));
            $("#investStaregys").html(escape2Html(r.data.newData.investStaregy));
            $("#investStandards").html(escape2Html(r.data.newData.investStandard));
            $("#investIdeas").html(escape2Html(r.data.newData.investIdea));
            $("#investScopes").html(escape2Html(r.data.newData.investScope));
            $("#revenueFeatures").html(escape2Html(r.data.newData.revenueFeature));
            $("#riskManagements").html(escape2Html(r.data.newData.riskManagement));
            getbankList(r.data.newData.gp);
            tinymecTool.load('goal', r.data.newData.goal);
            tinymecTool.load('investStaregy', r.data.newData.investStaregy);
            tinymecTool.load('investStandard', r.data.newData.investStandard);
            tinymecTool.load('investIdea', r.data.newData.investIdea);
            tinymecTool.load('investScope', r.data.newData.investScope);
            tinymecTool.load('revenueFeature', r.data.newData.revenueFeature);
            tinymecTool.load('riskManagement', r.data.newData.riskManagement);

        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}

function getbankList(gp) {
    //初始化资金托管方数据
    $.get('../rest/backadmin/bank/list?pageNum=1&pageSize=1000', function(r) {
        if (r.status == 'SUCCESS') {
            var htmls = '';
            for (var i = 0; i < r.totalResultCount; i++) {
                htmls += '<option value="' + r.data[i].uuid + '" data-logo="' + r.data[i].logoUrl + '">' + r.data[i].name + '</option>';
                // custodian[r.data[i].uuid] = r.data[i].name;
            }
            $('#gp').append(htmls);
            $("#gp").val(gp);
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
}
$("#backBtn").click(function() {
    window.location.href = document.referrer;
});
$("#formsubmit").submit(function() {
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
    var scode = $("#ascode").val().replace(/(^\s*)|(\s*$)/g, "");
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
    $('#goal').val(string2json2(tinyMCE.get('goal').getContent()));
    $('#investStaregy').val(string2json2(tinyMCE.get('investStaregy').getContent()));
    $('#investStandard').val(string2json2(tinyMCE.get('investStandard').getContent()));
    $('#investIdea').val(string2json2(tinyMCE.get('investIdea').getContent()));
    $('#investScope').val(string2json2(tinyMCE.get('investScope').getContent()));
    $('#revenueFeature').val(string2json2(tinyMCE.get('revenueFeature').getContent()));
    $('#riskManagement').val(string2json2(tinyMCE.get('riskManagement').getContent()));
    var str = $(this).serialize();
    $.post('../rest/backadmin/fund/operateEdit', str, function(r) {
        if (r.status == "SUCCESS") {
            layer.msg(r.message, {
                time: 2000
            }, function() {
                window.location.href = document.referrer;
            });
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
    return false;
});

function changeCustodian() {
    var url = $('#custodian option:selected').attr('data-logo');
    if (url != undefined && url != '' && url != 'null') {
        $('#custodianLogo').attr('src', '..' + url);
    } else {
        $('#custodianLogo').attr('src', '');
    }

}