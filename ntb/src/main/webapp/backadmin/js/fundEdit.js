var uuid = (url('?uuid') != null) ? url('?uuid') : '';
$(function() {

    $(".datepicker").click(function() {
        laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm"),
            istime: false,
            istoday: false,
            format: 'YYYY-MM-DD'
        });
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

    $('.defaultkey1').focus(function() {
        var value = $(this).val();
        if (value == '0.00') {
            value = ''
        }
        $(this).val(value);
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
    $.get('../rest/backadmin/fund/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            $(".uuid").val(r.data.uuid);
            $("#titlename").html(r.data.name);

            $("#shortnames").html(r.data.shortname);
            $("#shortname").val(r.data.shortname);
            $("#names").html(r.data.name);
            $("#name").val(r.data.name);
            $("#scodes").html(r.data.scode);
            $("#ascode").val(r.data.scode);
            $("#types").html(r.data.typeCN);
            $("#type").val(r.data.type);
            $("#setuptimes").html(r.data.setuptimeCN);
            $("#setuptime").val(r.data.setuptimeCN);
            $("#performanceLevels").html(r.data.performanceLevel);
            $("#performanceLevel").val(r.data.performanceLevel);
            $("#gps").html(r.data.gpName);
            $("#flagStructureds").html(r.data.flagStructuredCN);
            if (r.data.flagStructured == false) {
                $("#flagStructured").val("false");
            } else {
                $("#flagStructured").val("true");
            }
            $("#structuredTypes").html(r.data.structuredTypeCN);
            $("#structuredType").val(r.data.structuredType);
            $("#structuredRemarks").html(r.data.structuredRemark);
            $("#structuredRemark").val(r.data.structuredRemark);
            $("#styles").html(r.data.styleCN);
            $("#style").val(r.data.style);
            $("#riskLevels").html(r.data.riskLevel);
            $("#riskLevel").val(r.data.riskLevel);
            $("#creditLevels").html(r.data.creditLevelCN);
            $("#creditLevel").val(r.data.creditLevel);
            $("#planingScales").html(r.data.planingScale);
            $("#planingScale").val(FormatAfterDotNumber(r.data.planingScale, 2));
            $("#collectStarttimes").html(r.data.collectStarttimeCN);
            $("#collectStarttime").val(r.data.collectStarttimeCN);
            $("#collectEndtimes").html(r.data.collectEndtimeCN);
            $("#collectEndtime").val(r.data.collectEndtimeCN);
            $("#purchaseStarttimes").html(r.data.purchaseStarttimeCN);
            $("#purchaseStarttime").val(r.data.purchaseStarttimeCN);
            $("#purchaseEndtimes").html(r.data.purchaseEndtimeCN);
            $("#purchaseEndtime").val(r.data.purchaseEndtimeCN);
            $("#goals").html(escape2Html(r.data.goal));
            $("#investStaregys").html(escape2Html(r.data.investStaregy));
            $("#investStandards").html(escape2Html(r.data.investStandard));
            $("#investIdeas").html(escape2Html(r.data.investIdea));
            $("#investScopes").html(escape2Html(r.data.investScope));
            $("#revenueFeatures").html(escape2Html(r.data.revenueFeature));
            $("#riskManagements").html(escape2Html(r.data.riskManagement));

            getbankList(r.data.gp);

            tinymecTool.load('goal', r.data.goal);
            tinymecTool.load('investStaregy', r.data.investStaregy);
            tinymecTool.load('investStandard', r.data.investStandard);
            tinymecTool.load('investIdea', r.data.investIdea);
            tinymecTool.load('investScope', r.data.investScope);
            tinymecTool.load('revenueFeature', r.data.revenueFeature);
            tinymecTool.load('riskManagement', r.data.riskManagement);
        } else {
            layer.msg(r.message, {
                time: 2000
            })
        }
    }).done(function() {})

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

    $(".edit").click(function() {
        $(this).parent().find(".content-item-info").css("display", "none");
        $(this).parent().find(".content-item-edit").css("display", "block");
        $(this).css("display", "none").siblings(".save").css("display", "block");
        $(".inputGroup").each(function() {
            var width = $(this).width() - 150;
            $(this).find("input").width(width / 2 + "px");
        });
    });

    // $(".save").click(function() {
    //     $(this).parent().find(".content-item-info").css("display", "block");
    //     $(this).parent().find(".content-item-edit").css("display", "none");
    //     $(this).css("display", "none").siblings(".edit").css("display", "block");
    // });

    // $(".col-md-12 .content-items").each(function() {
    //     $(this).width($(".col-md-12 .form-group").width() - $(this).siblings("label").width());
    // });


    $(".edit").click(function() {
        $(".content-item-info").css("display", "none");
        $(".content-item-edit").css("display", "block");
        $(".content-items b").css("right", "30px");
        $(this).css("display", "none");
        $(".save").css("display", "block");
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
    if (flag) {
        $('#goal').val(string2json2(tinyMCE.get('goal').getContent()));
        $('#investStaregy').val(string2json2(tinyMCE.get('investStaregy').getContent()));
        $('#investStandard').val(string2json2(tinyMCE.get('investStandard').getContent()));
        $('#investIdea').val(string2json2(tinyMCE.get('investIdea').getContent()));
        $('#investScope').val(string2json2(tinyMCE.get('investScope').getContent()));
        $('#revenueFeature').val(string2json2(tinyMCE.get('revenueFeature').getContent()));
        $('#riskManagement').val(string2json2(tinyMCE.get('riskManagement').getContent()));

        var str = $(this).serialize();
        $.post('../rest/backadmin/fund/edit', str, function(data) {
            if (data.status == "SUCCESS") {
                layer.msg(data.message, {
                    time: 2000
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