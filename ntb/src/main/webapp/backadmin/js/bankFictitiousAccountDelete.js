$(document).ready(function() {
    $(".bank").click(function(event) {
        if ($(".bank-list").css("display") == "none") {
            $(".bank-list").show();
        } else {
            $(".bank-list").hide();
        }
        event.stopPropagation();
    });

    $(".bank-list").on("click", "li", function() {
        var thisName = $(this).attr("data-name"),
            thisNum = $(this).attr("data-num"),
            thisUrl = $(this).attr("data-url"),
            thisUuid = $(this).attr("data-uuid");
        $(".bank-name").html(thisName);
        $(".bank-num").html(thisNum);
        $(".bank-img").prop("src", ".." + thisUrl);
        $("#companyAccount").val(thisUuid);
    });

    $(document).click(function() {
        $(".bank-list").hide();
    });

    getAccount();

    function getAccount() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/list',
                type: 'get',
                data: {
                    pageNum: 1,
                    pageSize: 1000
                }
            })
            .done(function(r) {
                $.each(r.data, function(index, el) {
                    $(".bank-list").append("<li data-name=" + el.accountName + " data-num=" + el.accountNumLess + " data-url=" + el.bankIconUrl + " data-uuid=" + el.uuid + "><span>" + el.accountName + "</span> <span>" + el.accountNumLess + "</span></li>");
                });
                $(".bank-name").html("【" + r.data[0].accountName + "】");
                $(".bank-num").html(r.data[0].accountNumLess);
                $(".bank-img").prop("src", ".." + r.data[0].bankIconUrl);
                $("#companyAccount").val(r.data[0].uuid);
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }

    $("#sub").click(function() {
        var start = $("#start-num").val(),
            end = $("#end-num").val(),
            startNum = Number(start),
            endNum = Number(end);
        if (start == "") {
            layer.msg("请填写起始账号", {
                time: 2000
            });
            return false;
        }
        if (end == "") {
            layer.msg("请填写结束账号", {
                time: 2000
            });
            return false;
        }
        if (start.length > 6 || end.length > 6) {
            layer.msg("起始账号与结束账号不能大于6位", {
                time: 2000
            });
            return false;
        }
        if (start.length > end.length || start.length < end.length) {
            layer.msg("起始账号与结束账号长度不相等", {
                time: 2000
            });
            return false;
        }
        if (startNum > endNum) {
            layer.msg("起始账号不能大于结束账号", {
                time: 2000
            });
            return false;
        }


        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);

        $("#form").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    parent.$.colorbox.close();
                    parent.getList();
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    });
});