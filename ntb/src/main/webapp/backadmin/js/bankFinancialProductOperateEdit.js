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
    getbankList();
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

    $("#subscribeFee").blur(function() {
        var subscribeFee = $("#subscribeFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (subscribeFee != "") {
            $("#subscribeFee").val(FormatAfterDotNumber(subscribeFee, 4));
        } else {
            $("#subscribeFee").val("0.00");
        }
    })
    $("#purchaseFee").blur(function() {
        var purchaseFee = $("#purchaseFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (purchaseFee != "") {
            $("#purchaseFee").val(FormatAfterDotNumber(purchaseFee, 4));
        } else {
            $("#purchaseFee").val("0.00");
        }
    })
    $("#redemingFee").blur(function() {
        var redemingFee = $("#redemingFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (redemingFee != "") {
            $("#redemingFee").val(FormatAfterDotNumber(redemingFee, 4));
        } else {
            $("#redemingFee").val("0.00");
        }
    })
    $("#custodyFee").blur(function() {
        var custodyFee = $("#custodyFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (custodyFee != "") {
            $("#custodyFee").val(FormatAfterDotNumber(custodyFee, 4));
        } else {
            $("#custodyFee").val("0.00");
        }
    })
    $("#networkFee").blur(function() {
        var networkFee = $("#networkFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (networkFee != "") {
            $("#networkFee").val(FormatAfterDotNumber(networkFee, 4));
        } else {
            $("#networkFee").val("0.00");
        }
    })
    $("#managementFee").blur(function() {
        var managementFee = $("#managementFee").val().replace(/(^\s*)|(\s*$)/g, "");
        if (managementFee != "") {
            $("#managementFee").val(FormatAfterDotNumber(managementFee, 4));
        } else {
            $("#managementFee").val("0.00");
        }
    })

    $("#targetAnnualizedReturnRate").blur(function() {
        var subscribeFee = $("#targetAnnualizedReturnRate").val().replace(/(^\s*)|(\s*$)/g, "");
        if (subscribeFee != "") {
            $("#targetAnnualizedReturnRate").val(FormatAfterDotNumber(subscribeFee, 4));
        } else {
            $("#targetAnnualizedReturnRate").val("0.00");
        }
    })

    $("#minAnnualizedReturnRate").blur(function() {
        var subscribeFee = $("#minAnnualizedReturnRate").val().replace(/(^\s*)|(\s*$)/g, "");
        if (subscribeFee != "") {
            $("#minAnnualizedReturnRate").val(FormatAfterDotNumber(subscribeFee, 4));
        } else {
            $("#minAnnualizedReturnRate").val("0.00");
        }
    })
});

function getbankList() {
    //初始化资金托管人数据
    $.get('../rest/backadmin/bank/list?pageNum=1&pageSize=1000', function(r) {
        if (r.status == 'SUCCESS') {
            var htmls = '';
            for (var i = 0; i < r.totalResultCount; i++) {
                htmls += '<option value="' + r.data[i].uuid + '" data-logo="' + r.data[i].logoUrl + '">' + r.data[i].name + '</option>';
                custodian[r.data[i].uuid] = r.data[i].name;
            }
            $('#custodian').append(htmls);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function() {
        getAreaList();
    });
}

function getAreaList() {
    //初始化资金托管人数据
    $.get('../rest/backadmin/area/all', function(r) {
        if (r.status == 'SUCCESS') {
            var htmls = '';
            for (var i = 0; i < r.totalResultCount; i++) {
                htmls += '<option value="' + r.data[i].uuid + '">' + r.data[i].name + '</option>';
            }
            $('#area').append(htmls);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function() {
        getDate();
    });
}

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
    $.get('../rest/backadmin/bankFinancialProduct/operateGet?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $('.uuid').val(r.data.uuid);
            if (r.data.status == 'checked') {
                $(".edit").remove();
                $(".sureBtn").remove();
            }
            $("#minInvestAmount").bind("input", function() {
                $("#minInvestAmount_big").html("&ensp;&ensp;" + changeMoneyToChinese($("#minInvestAmount").val()));
            });
            $("#minInvestAmountAdd").bind("input", function() {
                $("#minInvestAmountAdd_big").html("&ensp;&ensp;" + changeMoneyToChinese($("#minInvestAmountAdd").val()));
            });
            $("#minInvestAmount").bind("input", function() {
                $("#maxInvestAmount_big").html("&ensp;&ensp;" + changeMoneyToChinese($("#maxInvestAmount").val()));
            });
            $('#titlename').html(r.data.newData.name);
            $('#custodianLogo').attr('src', '..' + r.data.newData.custodianLogo);
            $('#custodiansLogo').attr('src', '..' + r.data.newData.custodianLogo);
            $('#custodians').html(r.data.newData.custodianCN);
            $('#custodian').val(r.data.newData.custodian);
            $('#names').html(r.data.newData.name);
            $('#name').val(r.data.newData.name);
            $('#urls').html(r.data.newData.url);
            $('#url').val(r.data.newData.url);

            $('#seriess').html(r.data.newData.series);
            $('#series').val(r.data.newData.series);
            $('#scodes').html(r.data.newData.scode);
            $('#scodess').val(r.data.newData.scode);
            $('#shortnames').html(r.data.newData.shortname);
            $('#shortname').val(r.data.newData.shortname);
            $('#types').html(r.data.newData.typeCN);
            $('#type').val(r.data.newData.type);
            $('#targets').html(r.data.newData.targetCN);
            $('#target').val(r.data.newData.target);
            $('#paymentTypes').html(r.data.newData.paymentTypeCN);
            $('#paymentType').val(r.data.newData.paymentType);
            $('#currencyTypes').html(jsonDate.currencyType[r.data.newData.currencyType]);
            $('#currencyType').val(r.data.newData.currencyType);
            $('#totalAmounts').html(r.data.newData.totalAmount);
            $('#totalAmount').val(r.data.newData.totalAmount);
            $('#targetAnnualizedReturnRates').html(r.data.newData.targetAnnualizedReturnRate);
            $('#targetAnnualizedReturnRate').val(r.data.newData.targetAnnualizedReturnRate);
            $('#minAnnualizedReturnRates').html(r.data.newData.minAnnualizedReturnRate);
            $('#minAnnualizedReturnRate').val(r.data.newData.minAnnualizedReturnRate);
            $('#riskLevels').html(r.data.newData.riskLevelCN);
            $('#riskLevel').val(r.data.newData.riskLevel);
            $('#areas').html(r.data.newData.areaCN);
            $('#area').val(r.data.newData.area);

            $('#flagFlexibles').html(jsonDate.flagFlexible[r.data.newData.flagFlexible]);
            for (var i = 0; i < $('#flagFlexible option').length; i++) {
                if ($('#flagFlexible option:eq(' + i + ')').attr("value") == (r.data.newData.flagFlexible).toString()) {
                    $('#flagFlexible option:eq(' + i + ')').attr("selected", true);
                }
            }
            $('#flagPurchases').html(jsonDate.flagPurchase[r.data.newData.flagPurchase]);
            for (var i = 0; i < $('#flagPurchase option').length; i++) {
                if ($('#flagPurchase option:eq(' + i + ')').attr("value") == (r.data.newData.flagPurchase).toString()) {
                    $('#flagPurchase option:eq(' + i + ')').attr("selected", true);
                }
            }
            $('#flagRedemptions').html(jsonDate.flagRedemption[r.data.newData.flagRedemption]);
            for (var i = 0; i < $('#flagRedemption option').length; i++) {
                if ($('#flagRedemption option:eq(' + i + ')').attr("value") == (r.data.newData.flagRedemption).toString()) {
                    $('#flagRedemption option:eq(' + i + ')').attr("selected", true);
                }
            }
            $('#collectStarttimes').html(r.data.newData.collectStarttimeCN);
            $('#collectStarttime').val(r.data.newData.collectStarttimeCN);
            $('#collectEndtimes').html(r.data.newData.collectEndtimeCN);
            $('#collectEndtime').val(r.data.newData.collectEndtimeCN);
            $('#recordDates').html(r.data.newData.recordDateCN);
            $('#recordDate').val(r.data.newData.recordDateCN);
            $('#valueDates').html(r.data.newData.valueDateCN);
            $('#valueDate').val(r.data.newData.valueDateCN);
            $('#maturityDates').html(r.data.newData.maturityDateCN);
            $('#maturityDate').val(r.data.newData.maturityDateCN);
            $('#terms').html(r.data.newData.term);
            $('#term').val(r.data.newData.term);
            $('#minInvestAmounts').html(r.data.newData.minInvestAmount);
            $('#minInvestAmount').val(r.data.newData.minInvestAmount);
            $('#minInvestAmount_big').html("&ensp;&ensp;" + changeMoneyToChinese(r.data.newData.minInvestAmount));
            $('#minInvestAmountAdds').html(r.data.newData.minInvestAmountAdd);
            $('#minInvestAmountAdd').val(r.data.newData.minInvestAmountAdd);
            $('#minInvestAmountAdd_big').html("&ensp;&ensp;" + changeMoneyToChinese(r.data.newData.minInvestAmountAdd));
            $('#maxInvestAmounts').html(r.data.newData.maxInvestAmount);
            $('#maxInvestAmount').val(r.data.newData.maxInvestAmount);
            $('#maxInvestAmount_big').html("&ensp;&ensp;" + changeMoneyToChinese(r.data.newData.maxInvestAmount));

            $('#subscribeFees').html(r.data.newData.subscribeFee);
            $('#subscribeFee').val(r.data.newData.subscribeFee);
            $('#purchaseFees').html(r.data.newData.purchaseFee);
            $('#purchaseFee').val(r.data.newData.purchaseFee);
            $('#redemingFees').html(r.data.newData.redemingFee);
            $('#redemingFee').val(r.data.newData.redemingFee);
            $('#managementFees').html(r.data.newData.managementFee);
            $('#managementFee').val(r.data.newData.managementFee);
            $('#custodyFees').html(r.data.newData.custodyFee);
            $('#custodyFee').val(r.data.newData.custodyFee);
            $('#networkFees').html(r.data.newData.networkFee);
            $('#networkFee').val(r.data.newData.networkFee);

            $('#investScopes').html(escape2Html(r.data.newData.investScope));
            $('#revenueFeatures').html(escape2Html(r.data.newData.revenueFeature));
            $('#remarks').html(escape2Html(r.data.newData.remark));
            tinymecTool.load('investScope', r.data.newData.investScope);
            tinymecTool.load('revenueFeature', r.data.newData.revenueFeature);
            tinymecTool.load('remark', r.data.newData.remark);
            if (r.data.newData.document == null) {
                $("#imageShow").attr('href', 'javascript:void(0)').html(r.data.newData.documentCN).show();
                $("#documents").html(r.data.newData.documentCN);
            } else {
                $("#documents").html(r.data.newData.documentCN);
                $("#documentsLink").attr('href', '..' + r.data.newData.documentURL);
                $("#documentsLink").prop("target", "_blank");
                $("#imageShow").attr('href', '..' + r.data.newData.documentURL).html(r.data.newData.documentCN + '.' + r.data.newData.documentType).show();
                $('#imageShow').prop("target", "_blank");
                $(".ajax-upload-dragdrop").css("display", "block");
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
    $.post('../rest/backadmin/bankFinancialProduct/operateEdit', str, function(r) {
        if (r.status == "SUCCESS") {
            layer.msg('保存成功', {
                time: 1000
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