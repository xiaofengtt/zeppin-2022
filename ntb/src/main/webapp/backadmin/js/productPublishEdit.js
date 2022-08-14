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
};
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
            $('#imageShow').prop("target","_blank");
            $(".ajax-upload-dragdrop").css("display", "none");
        }
    });
    $(".edit").click(function() {
        $(".info-list").css("display", "none");
        $(".edit-list").css("display", "block");
        $(".content-items b").css("right", "30px");
        $(this).css("display", "none");
        $(".synchro").css("display", "none");
        $(".save").css("display", "block");
    });

    $("#subscribeFee").blur(function() {
        var subscribeFee = $("#subscribeFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (subscribeFee != "") {
            $("#subscribeFee").val(FormatAfterDotNumber(subscribeFee, 4));
        } else {
            $("#subscribeFee").val("0.00");
        }
    });
    $("#purchaseFee").blur(function() {
        var purchaseFee = $("#purchaseFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (purchaseFee != "") {
            $("#purchaseFee").val(FormatAfterDotNumber(purchaseFee, 4));
        } else {
            $("#purchaseFee").val("0.00");
        }
    });
    $("#redemingFee").blur(function() {
        var redemingFee = $("#redemingFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (redemingFee != "") {
            $("#redemingFee").val(FormatAfterDotNumber(redemingFee, 4));
        } else {
            $("#redemingFee").val("0.00");
        }
    });
    $("#custodyFee").blur(function() {
        var custodyFee = $("#custodyFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (custodyFee != "") {
            $("#custodyFee").val(FormatAfterDotNumber(custodyFee, 4));
        } else {
            $("#custodyFee").val("0.00");
        }
    });
    $("#networkFee").blur(function() {
        var networkFee = $("#networkFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (networkFee != "") {
            $("#networkFee").val(FormatAfterDotNumber(networkFee, 4));
        } else {
            $("#networkFee").val("0.00");
        }
    });
    $("#managementFee").blur(function() {
        var managementFee = $("#managementFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (managementFee != "") {
            $("#managementFee").val(FormatAfterDotNumber(managementFee, 4));
        } else {
            $("#managementFee").val("0.00");
        }
    });

    $("#targetAnnualizedReturnRate").blur(function() {
        var subscribeFee = $("#targetAnnualizedReturnRate").val().replace(/(^\s*)|(\s*$)/g, "");
        if (subscribeFee != "") {
            $("#targetAnnualizedReturnRate").val(FormatAfterDotNumber(subscribeFee, 4));
        } else {
            $("#targetAnnualizedReturnRate").val("0.00");
        }
    });

    $("#minAnnualizedReturnRate").blur(function() {
        var subscribeFee = $("#minAnnualizedReturnRate").val().replace(/(^\s*)|(\s*$)/g, "");
        if (subscribeFee != "") {
            $("#minAnnualizedReturnRate").val(FormatAfterDotNumber(subscribeFee, 4));
        } else {
            $("#minAnnualizedReturnRate").val("0.00");
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
    $.get('../rest/backadmin/bankFinancialProductPublish/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $("#minInvestAmount").bind("input", function() {
                $("#minInvestAmount_big").html("&ensp;&ensp;" + changeMoneyToChinese($("#minInvestAmount").val()));
            });
            $("#minInvestAmountAdd").bind("input", function() {
                $("#minInvestAmountAdd_big").html("&ensp;&ensp;" + changeMoneyToChinese($("#minInvestAmountAdd").val()));
            });
            $("#minInvestAmount").bind("input", function() {
                $("#maxInvestAmount_big").html("&ensp;&ensp;" + changeMoneyToChinese($("#maxInvestAmount").val()));
            });
            $('.uuid').val(r.data.uuid);
            $('#titlename').html(r.data.name);
            $('#custodiansLogo').attr('src', '..' + r.data.custodianLogo);
            $('#custodians').html(r.data.custodianCN);
            $('#custodian').val(r.data.custodian);
            $('#names').html(r.data.name);
            $('#name').val(r.data.name);
            $('#urls').html(r.data.url);
            $('#url').val(r.data.url);

            $('#seriess').html(r.data.series);
            $('#series').val(r.data.series);
            $('#scodes').html(r.data.scode);
            $('#scodess').val(r.data.scode);
            $('#shortnames').html(r.data.shortname);
            $('#shortname').val(r.data.shortname);
            $('#types').html(r.data.typeCN);
            $('#type').val(r.data.type);
            $('#targets').html(r.data.targetCN);
            $('#target').val(r.data.target);
            $('#paymentTypes').html(r.data.paymentTypeCN);
            $('#paymentType').val(r.data.paymentType);
            $('#currencyTypes').html(jsonDate.currencyType[r.data.currencyType]);
            $('#currencyType').val(r.data.currencyType);
            $('#totalAmounts').html(r.data.totalAmount);
            $('#totalAmount').val(r.data.totalAmount);
            $('#collectAmounts').html(r.data.collectAmount);
            $('#collectAmount').val(r.data.collectAmount);
            $('#targetAnnualizedReturnRates').html(r.data.targetAnnualizedReturnRate);
            $('#targetAnnualizedReturnRate').val(r.data.targetAnnualizedReturnRate);
            $('#minAnnualizedReturnRates').html(r.data.minAnnualizedReturnRate);
            $('#minAnnualizedReturnRate').val(r.data.minAnnualizedReturnRate);
            $('#riskLevels').html(r.data.riskLevelCN);
            $('#riskLevel').val(r.data.riskLevel);
            $('#areas').html(r.data.areaCN);
            $('#area').val(r.data.area);

            $('#flagFlexibles').html(jsonDate.flagFlexible[r.data.flagFlexible]);
            $('#flagFlexible').val(r.data.flagFlexible);

            $('#flagPurchases').html(jsonDate.flagPurchase[r.data.flagPurchase]);
            for (var i = 0; i < $('#flagPurchase option').length; i++) {
                if ($('#flagPurchase option:eq(' + i + ')').attr("value") == (r.data.flagPurchase).toString()) {
                    $('#flagPurchase option:eq(' + i + ')').attr("selected", true);
                }
            }
            $('#flagRedemptions').html(jsonDate.flagRedemption[r.data.flagRedemption]);
            for (var i = 0; i < $('#flagRedemption option').length; i++) {
                if ($('#flagRedemption option:eq(' + i + ')').attr("value") == (r.data.flagRedemption).toString()) {
                    $('#flagRedemption option:eq(' + i + ')').attr("selected", true);
                }
            }
            $('#collectStarttimes').html(r.data.collectStarttimeCN);
            $('#collectStarttime').val(r.data.collectStarttimeCN);
            $('#collectEndtimes').html(r.data.collectEndtimeCN);
            $('#collectEndtime').val(r.data.collectEndtimeCN);
            $('#terms').html(r.data.term);
            $('#term').val(r.data.term);
            $('#recordDates').html(r.data.recordDateCN);
            $('#recordDate').val(r.data.recordDateCN);
            $('#valueDates').html(r.data.valueDateCN);
            $('#valueDate').val(r.data.valueDateCN);
            $('#maturityDates').html(r.data.maturityDateCN);
            $('#maturityDate').val(r.data.maturityDateCN);
            $('#minInvestAmounts').html(r.data.minInvestAmount);

            $('#minInvestAmount').val(r.data.minInvestAmount);
            $('#minInvestAmount_big').html("&ensp;&ensp;" + changeMoneyToChinese(r.data.minInvestAmount));

            $('#minInvestAmountAdds').html(r.data.minInvestAmountAdd);
            $('#minInvestAmountAdd').val(r.data.minInvestAmountAdd);
            $('#minInvestAmountAdd_big').html("&ensp;&ensp;" + changeMoneyToChinese(r.data.minInvestAmountAdd));
            $('#maxInvestAmounts').html(r.data.maxInvestAmount);
            $('#maxInvestAmount').val(r.data.maxInvestAmount);
            $('#maxInvestAmount_big').html("&ensp;&ensp;" + changeMoneyToChinese(r.data.maxInvestAmount));

            $('#subscribeFees').html(r.data.subscribeFee);
            $('#subscribeFee').val(r.data.subscribeFee);
            $('#purchaseFees').html(r.data.purchaseFee);
            $('#purchaseFee').val(r.data.purchaseFee);
            $('#redemingFees').html(r.data.redemingFee);
            $('#redemingFee').val(r.data.redemingFee);
            $('#managementFees').html(r.data.managementFee);
            $('#managementFee').val(r.data.managementFee);
            $('#custodyFees').html(r.data.custodyFee);
            $('#custodyFee').val(r.data.custodyFee);
            $('#networkFees').html(r.data.networkFee);
            $('#networkFee').val(r.data.networkFee);

            $('#guaranteeStatuss').html(r.data.guaranteeStatusCN);
            $('#guaranteeStatus').val(r.data.guaranteeStatus);
            $('#investScopes').html(escape2Html(r.data.investScope));
            $('#revenueFeatures').html(escape2Html(r.data.revenueFeature));
            $('#remarks').html(escape2Html(r.data.remark));
            tinymecTool.load('investScope', r.data.investScope);
            tinymecTool.load('revenueFeature', r.data.revenueFeature);
            tinymecTool.load('remark', r.data.remark);
            if (r.data.document == null) {
                $("#documents").html(r.data.documentCN);
                $("#imageShow").attr('href', 'javascript:void(0)').html(r.data.documentCN).show();
            } else {
                $('input[name="document"]').val(r.data.document);
                $("#documents").html(r.data.documentCN);
                $("#documentsLink").attr('href', '..' + r.data.documentURL);
                $("#documentsLink").prop("target", "_blank");
                $("#imageShow").attr('href', '..' + r.data.documentURL).html(r.data.documentCN + '.' + r.data.documentType).show();
                $('#imageShow').prop("target","_blank");
                $(".ajax-upload-dragdrop").css("display", "block");
            }
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
}

function synchro() {
    $.get('../rest/backadmin/bankFinancialProductPublish/synchro?uuid=' + uuid, function(r) {
        if (r.status == "SUCCESS") {
            layer.msg('同步成功，即将刷新页面', {
                time: 1000
            }, function() {
                window.location.reload();
            });

        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
}

$("#formsubmit").submit(function() {
    if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
    $('input[name=investScope]').val(string2json2(tinyMCE.get('investScope').getContent()));
    $('input[name=revenueFeature]').val(string2json2(tinyMCE.get('revenueFeature').getContent()));
    $('input[name=remark]').val(string2json2(tinyMCE.get('remark').getContent()));
    var str = $(this).serialize();
    $.post('../rest/backadmin/bankFinancialProductPublish/edit', str, function(r) {
        if (r.status == "SUCCESS") {
            layer.msg('保存成功', {
                time: 1000
            }, function() {
                window.location.href = 'productPublishOperateList.jsp';
            });
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
    return false;
});
