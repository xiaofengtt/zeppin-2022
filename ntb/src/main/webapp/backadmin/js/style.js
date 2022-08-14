$(function() {
    if ($(window).width() > 1250) {
        $(".contain-right").width($(window).width() - 278 + "px");
        $(".contain-right .main-contain").css({
            "min-height": $(window).height() - 210 + "px"
        });
    }
    menu();

});
$(window).resize(function() {
    if ($(window).width() > 1250) {
        $(".contain-right").width($(window).width() - 278 + "px");
    }
});

$(".header .header-right .userInfo a").click(function() {
    $(".userInfoDropdown").css("display", "block");
    $(this).css({
        "color": "#fff",
        "text-decoration": "underline"
    });
});
$(document).on("click", function(e) {
    if (!$(e.target).parents().is('.header-right')) {
        $('.userInfoDropdown').hide();
        $(".header .header-right .userInfo a").css({
            "color": "#a7a6a5",
            "text-decoration": "none"
        });
    }
});

//一级菜单
function menu() {
    $.ajax({
        type: "GET",
        url: "../rest/backadmin/menu/list",
        data: {
            level: 1
        },
        dataType: "text",
        async: true,
        success: function(data) {
            var json = (new Function("", "return " + data.split("&&&")[0]))();
            var str = "";
            if (json.status == "SUCCESS") {
                for (var i = 0; i < json.data.length; i++) {
                    str += '<li class="lip" name="' + json.data[i].scode + '"><a onclick="menuSecond(\'' + json.data[i].scode + '\',this)" style="background:#222222 url(' + json.data[i].icon + '.png) 29px center no-repeat;">' + json.data[i].title + '<img src="img/aleft.png"></a></li>';
                }
                $("#menu").html(str);
                var scodeSecond = $("#scode").val();
                var scodeFrist = scodeSecond.substring(0, 4);
                $("#menu li").each(function() {
                    if ($(this).attr("name") == scodeFrist) {
                        $(this).children('a').click().addClass("lia");
                    }
                });
            }
        }
    });
}
//二级菜单
function menuSecond(scode, e) {
    var _this = $(e);
    if (_this.parent().find('ul').attr('class') == undefined) {
        $.ajax({
            type: "GET",
            url: "../rest/backadmin/menu/list",
            data: {
                level: 2,
                scode: scode
            },
            dataType: "text",
            async: true,
            success: function(data) {
                var json = (new Function("", "return " + data.split("&&&")[0]))();

                if (json.status == "SUCCESS") {
                    var str = '<ul class="ul">';
                    for (var i = 0; i < json.data.length; i++) {
                        str += '<li class="lic" name="' + json.data[i].scode + '"><a href="../' + json.data[i].url + '" icon-url="' + json.data[i].icon + '" style="background: #161616 url(' + json.data[i].icon + '.png) 29px center no-repeat;">' + json.data[i].title + '</a></li>';
                    }
                    str += '</ul>';
                    _this.parent().append(str);
                    _this.parent().find('ul').show();
                    match();
                }
            }
        });
        _this.parent().find("img").attr("src", "img/adown.png");
    } else {
        if (_this.parent().find("ul").css("display") == "block") {
            _this.parent().find("img").attr("src", "img/aleft.png");
            _this.parent().find("ul").css("display", "none");
            event.stopPropagation();
        } else {
            _this.parent().find("img").attr("src", "img/adown.png");
            _this.parent().find("ul").css("display", "block");
            event.stopPropagation();
        }
    }
}

//匹配菜单
function match() {
    var scodeSecond = $("#scode").val();
    var scodeFrist = scodeSecond.substring(0, 4);
    $(".contain-left ul li").each(function() {
        if ($(this).attr("name") == scodeSecond) {
            var thisa = $(this).addClass("lights").find('a');
            thisa.addClass("lia");
            thisa.css("background", "#161616 url(" + thisa.attr("icon-url") + "_0.png) 29px center no-repeat");
            $(this).parent().parent().find("img").attr("src", "img/adown.png");
        }
    });
    $(".nav ul li").each(function() {
        if ($(this).attr("name") == scodeFrist) {
            $(this).addClass("light").siblings().removeClass("light");
        }
    });
}

function escape2Html(str) {
    if (str != null) {
        var arrEntities = {
            'lt': '<',
            'gt': '>',
            'nbsp': ' ',
            'amp': '&',
            'quot': '"'
        };
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function(all, t) {
            return arrEntities[t];
        });
    } else {
        return '';
    }
}


function FormatAfterDotNumber(ValueString, nAfterDotNum) {

    var ValueString, nAfterDotNum;
    //	return parseFloat(ValueString).toFixed(nAfterDotNum);
    var resultStr, nTen;
    ValueString = "" + ValueString + "";
    strLen = ValueString.length;
    dotPos = ValueString.indexOf(".", 0);
    if (dotPos == -1) {
        resultStr = ValueString + ".";
        for (i = 0; i < nAfterDotNum; i++) {
            resultStr = resultStr + "0";
        }
        return resultStr;
    } else {
        if ((strLen - dotPos - 1) >= nAfterDotNum) {

            nAfter = dotPos + nAfterDotNum + 1;
            nTen = 1;
            for (j = 0; j < nAfterDotNum; j++) {

                nTen = nTen * 10;
            }


            var pointStr = ValueString.split('.')[1];
            if (pointStr.substring(0, 2) == '00') {
                var last = ValueString.substring(ValueString.length - 1, ValueString.length);
                if (last == '5') {
                    ValueString += '1';
                }
                resultStr = Math.round(parseFloat(ValueString) * nTen) / nTen;
                if (pointStr.substring(2, 3) != '' && pointStr.substring(2, 3) < 5) {
                    return resultStr + '.00';
                }
                if (pointStr.substring(2, 3) != '' && pointStr.substring(2, 3) == 5) {
                    return ValueString.split('.')[0] + '.01';
                }
                return resultStr + '.00';
            }
            resultStr = Math.round(parseFloat(ValueString) * nTen) / nTen;
            return resultStr;
        } else {
            resultStr = ValueString;
            for (i = 0; i < (nAfterDotNum - strLen + dotPos + 1); i++) {

                resultStr = resultStr + "0";
            }
            return resultStr;
        }
    }
}
