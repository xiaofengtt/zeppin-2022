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
    $("#valueDate,#maturityDate,#recordDate").click(function() {
        laydate({
            start: laydate.now(0, "YYYY/MM/DD"),
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
});

//获取值
function getDate() {
    $.get('../rest/backadmin/bankFinancialProduct/operateGet?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            if (r.data.type == 'delete' || r.data.type == 'edit') {
                $('#titlename').html(r.data.oldData.name);
                $('#custodiansLogo').attr('src', '..' + r.data.oldData.custodianLogo);
                $('#names').html(r.data.oldData.name);
                $('#seriess').html(r.data.oldData.series);
                $('#scodes').html(r.data.oldData.scode);
                $('#shortnames').html(r.data.oldData.shortname);
                $('#urls').html(r.data.oldData.url);
                $('#types').html(r.data.oldData.typeCN);
                $('#targets').html(r.data.oldData.targetCN);
                $('#custodians').html(r.data.oldData.custodianCN);
                $('#paymentTypes').html(r.data.oldData.paymentTypeCN);
                $('#areas').html(r.data.oldData.areaCN);
                $('#currencyTypes').html(jsonDate.currencyType[r.data.oldData.currencyType]);
                $('#targetAnnualizedReturnRates').html(r.data.oldData.targetAnnualizedReturnRate);
                $('#minAnnualizedReturnRates').html(r.data.oldData.minAnnualizedReturnRate);
                $('#minInvestAmounts').html(r.data.oldData.minInvestAmount);
                $('#minInvestAmountAdds').html(r.data.oldData.minInvestAmountAdd);
                $('#maxInvestAmounts').html(r.data.oldData.maxInvestAmount);
                $('#subscribeFees').html(r.data.oldData.subscribeFee);
                $('#purchaseFees').html(r.data.oldData.purchaseFee);
                $('#redemingFees').html(r.data.oldData.redemingFee);
                $('#managementFees').html(r.data.oldData.managementFee);
                $('#custodyFees').html(r.data.oldData.custodyFee);
                $('#networkFees').html(r.data.oldData.networkFee);
                $('#totalAmounts').html(r.data.oldData.totalAmount);
                $('#collectStarttimes').html(r.data.oldData.collectStarttimeCN);
                $('#collectEndtimes').html(r.data.oldData.collectEndtimeCN);
                $('#terms').html(r.data.oldData.term);
                $('#recordDates').html(r.data.oldData.recordDateCN);
                $('#valueDates').html(r.data.oldData.valueDateCN);
                $('#maturityDates').html(r.data.oldData.maturityDateCN);
                $('#flagPurchases').html(jsonDate.flagPurchase[r.data.oldData.flagPurchase]);
                $('#flagRedemptions').html(jsonDate.flagRedemption[r.data.oldData.flagRedemption]);
                $('#flagFlexibles').html(jsonDate.flagFlexible[r.data.oldData.flagFlexible]);
                $('#investScopes').html(escape2Html(r.data.oldData.investScope));
                $('#riskLevels').html(r.data.oldData.riskLevelCN);
                $('#revenueFeatures').html(escape2Html(r.data.oldData.revenueFeature));
                $('#remarks').html(escape2Html(r.data.oldData.remark));
                if (r.data.oldData.document != null) {
                    $("#documentsLink").attr('href', '..' + r.data.oldData.documentURL);
                    $("#documentsLink").prop("target", "_blank");
                    $("#documents").html(r.data.oldData.documentCN + '.' + r.data.oldData.documentType);
                } else {
                    $("#documents").html(r.data.oldData.documentCN);
                }
            } else if (r.data.type == 'add') {
                $('#titlename').html(r.data.newData.name);
                $('#custodiansLogo').attr('src', '..' + r.data.newData.custodianLogo);
                $('#names').html(r.data.newData.name);
                $('#seriess').html(r.data.newData.series);
                $('#scodes').html(r.data.newData.scode);
                $('#shortnames').html(r.data.newData.shortname);
                $('#urls').html(r.data.newData.url);
                $('#types').html(r.data.newData.typeCN);
                $('#targets').html(r.data.newData.targetCN);
                $('#custodians').html(r.data.newData.custodianCN);
                $('#paymentTypes').html(r.data.newData.paymentTypeCN);
                $('#areas').html(r.data.newData.areaCN);
                $('#currencyTypes').html(jsonDate.currencyType[r.data.newData.currencyType]);
                $('#targetAnnualizedReturnRates').html(r.data.newData.targetAnnualizedReturnRate);
                $('#minAnnualizedReturnRates').html(r.data.newData.minAnnualizedReturnRate);
                $('#minInvestAmounts').html(r.data.newData.minInvestAmount);
                $('#minInvestAmountAdds').html(r.data.newData.minInvestAmountAdd);
                $('#maxInvestAmounts').html(r.data.newData.maxInvestAmount);
                $('#subscribeFees').html(r.data.newData.subscribeFee);
                $('#purchaseFees').html(r.data.newData.purchaseFee);
                $('#redemingFees').html(r.data.newData.redemingFee);
                $('#managementFees').html(r.data.newData.managementFee);
                $('#custodyFees').html(r.data.newData.custodyFee);
                $('#networkFees').html(r.data.newData.networkFee);
                $('#totalAmounts').html(r.data.newData.totalAmount);
                $('#collectStarttimes').html(r.data.newData.collectStarttimeCN);
                $('#collectEndtimes').html(r.data.newData.collectEndtimeCN);
                $('#terms').html(r.data.newData.term);
                $('#recordDates').html(r.data.newData.recordDateCN);
                $('#valueDates').html(r.data.newData.valueDateCN);
                $('#maturityDates').html(r.data.newData.maturityDateCN);
                $('#flagPurchases').html(jsonDate.flagPurchase[r.data.newData.flagPurchase]);
                $('#flagRedemptions').html(jsonDate.flagRedemption[r.data.newData.flagRedemption]);
                $('#flagFlexibles').html(jsonDate.flagFlexible[r.data.newData.flagFlexible]);
                $('#investScopes').html(escape2Html(r.data.newData.investScope));
                $('#riskLevels').html(r.data.newData.riskLevelCN);
                $('#revenueFeatures').html(escape2Html(r.data.newData.revenueFeature));
                $('#remarks').html(escape2Html(r.data.newData.remark));
                if (r.data.newData.document != null) {
                    $("#documentsLink").attr('href', '..' + r.data.newData.documentURL);
                    $("#documentsLink").prop("target", "_blank");
                    $("#documents").html(r.data.newData.documentCN + '.' + r.data.newData.documentType);
                } else {
                    $("#documents").html(r.data.newData.documentCN);
                }
            }
            if (r.data.type == 'edit') {
                if (r.data.oldData.name != r.data.newData.name) {
                    $('#name').html("变更为：" + r.data.newData.name);
                }
                if (r.data.oldData.series != r.data.newData.series) {
                    $('#series').html("变更为：" + r.data.newData.series);
                }
                if (r.data.oldData.scode != r.data.newData.scode) {
                    $('#ascode').html("变更为：" + r.data.newData.scode);
                }
                if (r.data.oldData.shortname != r.data.newData.shortname) {
                    $('#shortname').html("变更为：" + r.data.newData.shortname);
                }
                if (r.data.oldData.url != r.data.newData.url) {
                    $('#url').html("变更为：" + r.data.newData.url);
                }
                if (r.data.oldData.type != r.data.newData.type) {
                    $('#type').html("变更为：" + r.data.newData.typeCN);
                }
                if (r.data.oldData.target != r.data.newData.target) {
                    $('#target').html("变更为：" + r.data.newData.targetCN);
                }
                if (r.data.oldData.custodian != r.data.newData.custodian) {
                    $('#custodian').html("变更为：" + r.data.newData.custodianCN);
                }
                if (r.data.oldData.paymentType != r.data.newData.paymentType) {
                    $('#paymentType').html("变更为：" + r.data.newData.paymentTypeCN);
                }
                if (r.data.oldData.area != r.data.newData.area) {
                    $('#area').html("变更为：" + r.data.newData.areaCN);
                }
                if (r.data.oldData.currencyType != r.data.newData.currencyType) {
                    $('#currencyType').html("变更为：" + jsonDate.currencyType[r.data.newData.currencyType]);
                }
                if (r.data.oldData.targetAnnualizedReturnRate != r.data.newData.targetAnnualizedReturnRate) {
                    $('#targetAnnualizedReturnRate').html("变更为：" + r.data.newData.targetAnnualizedReturnRate + "%");
                }
                if (r.data.oldData.minAnnualizedReturnRate != r.data.newData.minAnnualizedReturnRate) {
                    $('#minAnnualizedReturnRate').html("变更为：" + r.data.newData.minAnnualizedReturnRate + "%");
                }
                if (r.data.oldData.minInvestAmount != r.data.newData.minInvestAmount) {
                    $('#minInvestAmount').html("变更为：" + r.data.newData.minInvestAmount + "元");
                }
                if (r.data.oldData.minInvestAmountAdd != r.data.newData.minInvestAmountAdd) {
                    $('#minInvestAmountAdd').html("变更为：" + r.data.newData.minInvestAmountAdd + "元");
                }
                if (r.data.oldData.maxInvestAmount != r.data.newData.maxInvestAmount) {
                    $('#maxInvestAmount').html("变更为：" + r.data.newData.maxInvestAmount + "元");
                }
                if (r.data.oldData.subscribeFee != r.data.newData.subscribeFee) {
                    $('#subscribeFee').html("变更为：" + r.data.newData.subscribeFee + "%");
                }
                if (r.data.oldData.purchaseFee != r.data.newData.purchaseFee) {
                    $('#purchaseFee').html("变更为：" + r.data.newData.purchaseFee + "%");
                }
                if (r.data.oldData.redemingFee != r.data.newData.redemingFee) {
                    $('#redemingFee').html("变更为：" + r.data.newData.redemingFee + "%");
                }
                if (r.data.oldData.managementFee != r.data.newData.managementFee) {
                    $('#managementFee').html("变更为：" + r.data.newData.managementFee + "%");
                }
                if (r.data.oldData.custodyFee != r.data.newData.custodyFee) {
                    $('#custodyFee').html("变更为：" + r.data.newData.custodyFee + "%");
                }
                if (r.data.oldData.networkFee != r.data.newData.networkFee) {
                    $('#networkFee').html("变更为：" + r.data.newData.networkFee + "%");
                }
                if (r.data.oldData.totalAmount != r.data.newData.totalAmount) {
                    $('#totalAmount').html("变更为：" + r.data.newData.totalAmount + "亿元");
                }
                if (r.data.oldData.collectStarttimeCN != r.data.newData.collectStarttimeCN || r.data.oldData.collectEndtimeCN != r.data.newData.collectEndtimeCN) {
                    $('#collectStarttime').html("变更为：" + r.data.newData.collectStarttimeCN + "至" + r.data.newData.collectEndtimeCN);
                }


                if (r.data.oldData.recordDateCN != r.data.newData.recordDateCN) {
                    $('#recordDate').html("变更为：" + r.data.newData.recordDateCN);
                }
                if (r.data.oldData.valueDateCN != r.data.newData.valueDateCN || r.data.oldData.maturityDateCN != r.data.newData.maturityDateCN || r.data.oldData.term != r.data.newData.term) {
                    $('#valueDate').html("变更为：" + r.data.newData.valueDateCN + " 至 " + r.data.newData.maturityDateCN + " 产品期限 " + r.data.newData.term + "天");
                }

                if (r.data.oldData.flagPurchase != r.data.newData.flagPurchase) {
                    $('#flagPurchase').html("变更为：" + jsonDate.flagPurchase[r.data.newData.flagPurchase]);
                }
                if (r.data.oldData.flagRedemption != r.data.newData.flagRedemption) {
                    $('#flagRedemption').html("变更为：" + jsonDate.flagRedemption[r.data.newData.flagRedemption]);
                }
                if (r.data.oldData.flagFlexible != r.data.newData.flagFlexible) {
                    $('#flagFlexible').html("变更为：" + jsonDate.flagFlexible[r.data.newData.flagFlexible]);
                }
                if (r.data.oldData.riskLevel != r.data.newData.riskLevel) {
                    $('#riskLevel').html("变更为：" + r.data.newData.riskLevelCN);
                }
                if (r.data.oldData.investScope != r.data.newData.investScope) {
                    $('#investScope').html("<label>变更为：</label>" + escape2Html(r.data.newData.investScope));
                }
                if (r.data.oldData.revenueFeature != r.data.newData.revenueFeature) {
                    $('#revenueFeature').html("<label>变更为：</label>" + escape2Html(r.data.newData.revenueFeature));
                }
                if (r.data.oldData.remark != r.data.newData.remark) {
                    $('#remark').html("<label>变更为：</label>" + escape2Html(r.data.newData.remark));
                }
                if (r.data.oldData.document != r.data.newData.document) {
                    if (r.data.newData.document != null) {
                        $("#documentLink").attr('href', '..' + r.data.newData.documentURL);
                        $("#document").html("<label>变更为：" + r.data.newData.documentCN + '.' + r.data.newData.documentType + "</label>");
                    } else {
                        $("#document").html("<label>变更为：" + r.data.newData.documentCN + "</label>");
                    }
                }
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    });
}
