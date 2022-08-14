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
    getDate();
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
            if (r.data.type == 'delete' || r.data.type == 'edit') {
                $("#shortnames").html(r.data.oldData.shortname);
                $("#names").html(r.data.oldData.name);
                $("#scodes").html(r.data.oldData.scode);
                $("#types").html(r.data.oldData.typeCN);
                $("#setuptimes").html(r.data.oldData.setuptimeCN);
                $("#performanceLevels").html(r.data.oldData.performanceLevel);
                $("#gps").html(r.data.oldData.gpName);
                $("#flagStructureds").html(r.data.oldData.flagStructuredCN);
                $("#structuredTypes").html(r.data.oldData.structuredTypeCN);
                $("#structuredRemarks").html(r.data.oldData.structuredRemark);
                $("#styles").html(r.data.oldData.styleCN);
                $("#riskLevels").html(r.data.oldData.riskLevel);
                $("#creditLevels").html(r.data.oldData.creditLevelCN);
                $("#planingScales").html(r.data.oldData.planingScale);
                $("#collectStarttimes").html(r.data.oldData.collectStarttimeCN);
                $("#collectEndtimes").html(r.data.oldData.collectEndtimeCN);
                $("#purchaseStarttimes").html(r.data.oldData.purchaseStarttimeCN);
                $("#purchaseEndtimes").html(r.data.oldData.purchaseEndtimeCN);
                $("#goals").html(escape2Html(r.data.oldData.goal));
                $("#investStaregys").html(escape2Html(r.data.oldData.investStaregy));
                $("#investStandards").html(escape2Html(r.data.oldData.investStandard));
                $("#investIdeas").html(escape2Html(r.data.oldData.investIdea));
                $("#investScopes").html(escape2Html(r.data.oldData.investScope));
                $("#revenueFeatures").html(escape2Html(r.data.oldData.revenueFeature));
                $("#riskManagements").html(escape2Html(r.data.oldData.riskManagement));
            } else if (r.data.type == 'add') {
                $("#shortnames").html(r.data.newData.shortname);
                $("#names").html(r.data.newData.name);
                $("#scodes").html(r.data.newData.scode);
                $("#types").html(r.data.newData.typeCN);
                $("#setuptimes").html(r.data.newData.setuptimeCN);
                $("#performanceLevels").html(r.data.newData.performanceLevel);
                $("#gps").html(r.data.newData.gpName);
                $("#flagStructureds").html(r.data.newData.flagStructuredCN);
                $("#structuredTypes").html(r.data.newData.structuredTypeCN);
                $("#structuredRemarks").html(r.data.newData.structuredRemark);
                $("#styles").html(r.data.newData.styleCN);
                $("#riskLevels").html(r.data.newData.riskLevel);
                $("#creditLevels").html(r.data.newData.creditLevelCN);
                $("#planingScales").html(r.data.newData.planingScale);
                $("#collectStarttimes").html(r.data.newData.collectStarttimeCN);
                $("#collectEndtimes").html(r.data.newData.collectEndtimeCN);
                $("#purchaseStarttimes").html(r.data.newData.purchaseStarttimeCN);
                $("#purchaseEndtimes").html(r.data.newData.purchaseEndtimeCN);
                $("#goals").html(escape2Html(r.data.newData.goal));
                $("#investStaregys").html(escape2Html(r.data.newData.investStaregy));
                $("#investStandards").html(escape2Html(r.data.newData.investStandard));
                $("#investIdeas").html(escape2Html(r.data.newData.investIdea));
                $("#investScopes").html(escape2Html(r.data.newData.investScope));
                $("#revenueFeatures").html(escape2Html(r.data.newData.revenueFeature));
                $("#riskManagements").html(escape2Html(r.data.newData.riskManagement));
            }
            if (r.data.type == 'edit') {
                if (r.data.oldData.shortname != r.data.newData.shortname) {
                    $('#shortname').html("变更为：" + r.data.newData.shortname);
                }
                if (r.data.oldData.name != r.data.newData.name) {
                    $('#name').html("变更为：" + r.data.newData.name);
                }
                if (r.data.oldData.scode != r.data.newData.scode) {
                    $('#ascode').html("变更为：" + r.data.newData.scode);
                }
                if (r.data.oldData.type != r.data.newData.type) {
                    $('#type').html("变更为：" + r.data.newData.typeCN);
                }
                if (r.data.oldData.setuptime != r.data.newData.setuptime) {
                    $('#setuptime').html("变更为：" + r.data.newData.setuptimeCN);
                }
                if (r.data.oldData.performanceLevel != r.data.newData.performanceLevel) {
                    $('#performanceLevel').html("变更为：" + r.data.newData.performanceLevel);
                }
                if (r.data.oldData.gp != r.data.newData.gp) {
                    $('#gp').html("变更为：" + r.data.newData.gpName);
                }
                if (r.data.oldData.flagStructured != r.data.newData.flagStructured) {
                    $('#flagStructured').html("变更为：" + r.data.newData.flagStructured);
                }
                if (r.data.oldData.structuredType != r.data.newData.structuredType) {
                    $('#structuredType').html("变更为：" + r.data.newData.structuredType);
                }
                if (r.data.oldData.structuredRemark != r.data.newData.structuredRemark) {
                    $('#structuredRemark').html("变更为：" + r.data.newData.structuredRemark);
                }
                if (r.data.oldData.style != r.data.newData.style) {
                    $('#style').html("变更为：" + r.data.newData.styleCN);
                }
                if (r.data.oldData.riskLevel != r.data.newData.riskLevel) {
                    $('#riskLevel').html("变更为：" + r.data.newData.riskLevel);
                }
                if (r.data.oldData.creditLevel != r.data.newData.creditLevel) {
                    $('#creditLevel').html("变更为：" + r.data.newData.creditLevelCN);
                }
                if (r.data.oldData.planingScale != r.data.newData.planingScale) {
                    $('#planingScale').html("变更为：" + r.data.newData.planingScale);
                }
                if (r.data.oldData.collectStarttime != r.data.newData.collectStarttime) {
                    $('#collectStarttime').html("变更为：" + r.data.newData.collectStarttimeCN);
                }
                if (r.data.oldData.purchaseStarttime != r.data.newData.purchaseStarttime) {
                    $('#purchaseStarttime').html("变更为：" + r.data.newData.purchaseStarttimeCN);
                }
                if (r.data.oldData.purchaseEndtime != r.data.newData.purchaseEndtime) {
                    $('#purchaseEndtime').html("变更为：" + r.data.newData.purchaseEndtimeCN);
                }
                if (r.data.oldData.goal != r.data.newData.goal) {
                    $('#goal').html("变更为：" + escape2Html(r.data.newData.goal));
                }
                if (r.data.oldData.investStaregy != r.data.newData.investStaregy) {
                    $('#investStaregy').html("变更为：" + escape2Html(r.data.newData.investStaregy));
                }
                if (r.data.oldData.investIdea != r.data.newData.investIdea) {
                    $('#investIdea').html("变更为：" + escape2Html(r.data.newData.investIdea));
                }
                if (r.data.oldData.investScope != r.data.newData.investScope) {
                    $('#investScope').html("变更为：" + escape2Html(r.data.newData.investScope));
                }
                if (r.data.oldData.revenueFeature != r.data.newData.revenueFeature) {
                    $('#revenueFeature').html("变更为：" + escape2Html(r.data.newData.revenueFeature));
                }
                if (r.data.oldData.riskManagement != r.data.newData.riskManagement) {
                    $('#riskManagement').html("变更为：" + escape2Html(r.data.newData.riskManagement));
                }
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}

$("#backBtn").click(function() {
    window.location.href = document.referrer;
});