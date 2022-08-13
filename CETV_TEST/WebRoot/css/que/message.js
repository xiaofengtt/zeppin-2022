function getXmlHttp() {
    var a;
    try {
        a = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (b) {
        try {
            a = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (b) {
            try {
                a = new XMLHttpRequest();
            } catch (b) {}
        }
    }
    return a;
}

function getExpDate(d, a, c) {
    var b = new Date();
    if (typeof (d) == "number" && typeof (a) == "number" && typeof (a) == "number") {
        b.setDate(b.getDate() + parseInt(d));
        b.setHours(b.getHours() + parseInt(a));
        b.setMinutes(b.getMinutes() + parseInt(c));
        return b.toGMTString();
    }
}

function getCookieVal(b) {
    var a = document.cookie.indexOf(";", b);
    if (a == -1) {
        a = document.cookie.length;
    }
    return unescape(document.cookie.substring(b, a));
}

function getCookie(d) {
    var b = d + "=";
    var f = b.length;
    var a = document.cookie.length;
    var e = 0;
    while (e < a) {
        var c = e + f;
        if (document.cookie.substring(e, c) == b) {
            return getCookieVal(c);
        }
        e = document.cookie.indexOf(" ", e) + 1;
        if (e == 0) {
            break;
        }
    }
    return "";
}

function setCookie(b, d, a, f, c, e) {
    document.cookie = b + "=" + escape(d) + ((a) ? "; expires=" + a : "") + ((f) ? "; path=" + f : "") + ((c) ? "; domain=" + c : "") + ((e) ? "; secure" : "");
}
var divSide = document.getElementById("rbbox");

function showBox() {
    divSide.style.height = divSide.clientHeight + 2 + "px";
    var a = Math.max(divSide.offsetHeight, divSide.clientHeight, divSide.scrollHeight);
    if (divSide.clientHeight < a) {
        setTimeout(function () {
            showBox();
        }, 5);
    } else {
        mm();
    }
}

function closeBox(a) {
    divSide.style.display = "none";
    if (a) {
        setMessageCookie(2);
        setMessageCookie(3);
        setMessageCookie(4);
        setMessageCookie(5);
    }
}

function setMessageCookie(a) {
    if (a == 2 && msgCount != "0") {
        setCookieDefault("leavemessage", msgCount);
    } else {
        if (a == 3 && msgInviteCount != "0") {
            setCookieDefault("invitemessage", msgInviteCount);
        } else {
            if (a == 4 && sysMsg != "") {
                setCookieDefault("ndsm", "1");
            } else {
                if (a == 5 && promoteMsg != "") {
                    setCookieDefault("promotemessage", promoteMsg);
                }
            }
        }
    }
    setCookieDefault("systemmessage", "");
}

function setCookieDefault(a, c) {
    var b = window.location.host || "sojump.com";
    setCookie(a, c, null, "/", b, null);
}
setTimeout(GetMessage, 1000);
var msgCount = "0";
var msgInviteCount = "0";
var sysMsg = "";
var promoteMsg = "";

function GetMessage() {
    var a = getXmlHttp();
    a.onreadystatechange = function () {
        if (a.readyState == 4) {
            if (a.status == 200) {
                var q = a.responseText;
                if (q == "") {
                    setCookieDefault("systemmessage", "");
                    setCookieDefault("promotemessage", "");
                    setCookieDefault("leavemessage", "0");
                    setCookieDefault("invitemessage", "0");
                    return;
                }
                divSide.innerHTML = q;
                var p = getCookie("leavemessage");
                var c = getCookie("ndsm") != "1";
                var d = getCookie("promotemessage");
                var o = getCookie("invitemessage");
                var e = document.getElementById("lblLeaveCount");
                var h = document.getElementById("lblInviteCount");
                var f = document.getElementById("lblSystemMsg");
                if (f && c) {
                    sysMsg = f.innerHTML;
                    divSide.style.display = "";
                } else {
                    if (f) {
                        document.getElementById("trSystemMsg").style.display = "none";
                    } else {
                        setCookieDefault("systemmessage", "");
                    }
                }
                var i = document.getElementById("lblPromoteMsg");
                if (i && d != "ssssss12312313qq1") {
                    promoteMsg = "ssssss12312313qq1";
                    divSide.style.display = "";
                } else {
                    if (i) {
                        document.getElementById("trPromoteMsg").style.display = "none";
                    }
                } if (e && p != e.innerHTML) {
                    msgCount = e.innerHTML;
                    divSide.style.display = "";
                } else {
                    if (e) {
                        document.getElementById("trLeaveMsg").style.display = "none";
                    } else {
                        setCookieDefault("leavemessage", "0");
                    }
                } if (h && o != h.innerHTML) {
                    msgInviteCount = h.innerHTML;
                    divSide.style.display = "";
                } else {
                    if (h) {
                        document.getElementById("trInviteMsg").style.display = "none";
                    } else {
                        setCookieDefault("invitemessage", "0");
                    }
                } if ((p < msgCount && msgCount != "0") || (o < msgInviteCount && msgInviteCount != "0") || (c && sysMsg != "") || (d != promoteMsg && promoteMsg != "")) {
                    divSide.style.display = "";
                    showBox();
                    setTimeout("closeBox()", 20000);
                } else {
                    if (window.Search_Keyword) {
                        var g = window.location.href.toLowerCase().indexOf("/jq/") > -1;
                        var m = Search_Keyword.replace("报告", "");
                        if (!m) {
                            return;
                        }
                        var l = document.getElementById("tdSearch");
                        l.parentNode.parentNode.style.width = "330px";
                        var j = encodeURIComponent(m);
                        var n = "<div style='width:300px;padding:2px 5px;text-align:left;margin:15px 0 5px; font-size:14px;'>";
                        var k = "";
                        if (!g) {
                            k = "&sort=1&jt=50&qc=";
                        }
                        n += "<div style='margin-bottom:15px;'><img src='/images/wjx/index/rightTineliang.gif' alt=''/>&nbsp;<a href='/mysojump/newselecttemplete.aspx?keyword=" + j + k + "' class='link-06f' style='font-weight:bold;'>搜索更多相关问卷模板</a></div>";
                        if (g && window.allowViewStat) {
                            n += "<div style='margin-bottom:20px;'><img src='/images/wjx/index/rightTineliang.gif' alt=''/>&nbsp;<a style='font-size:14px;' class='link-06f' href='/viewstat/" + activityId + ".aspx?wd=" + j + "'>查看此问卷调查报告</a></div>";
                        }
                        if (window.allowCopy) {
                            n += "<div style='margin-bottom:8px;'><img src='/images/wjx/index/rightTineliang.gif' alt=''/>&nbsp;<a href='javascript:void(0);' class='link-06f' onclick=\"PDF_launch('/wjx/collect.aspx?activity=" + activityId + "',500,120);\">以此问卷为模板创建您自己的问卷</a></div>";
                        } else {
                            n += "<div style='margin-bottom:20px;'><img src='/images/wjx/index/rightTineliang.gif' alt=''/>&nbsp;<a  class='link-06f' href='/wjx/join/tiyan.aspx?keyword=" + j + "'>创建您自己的问卷</a></div>";
                        }
                        n += "</div>";
                        l.innerHTML = n;
                        divSide.style.display = "";
                        showBox();
                    } else {
                        divSide.style.display = "none";
                    }
                    return;
                }
            }
        }
    };
    var b = "";
    if (window.NoSystemMessge) {
        b = "&nsmg=1";
    }
    a.open("get", "/Modules/wjx/MessagePromote.aspx?needpromote=" + needPromote + b + "&t=" + (new Date()).valueOf());
    a.send(null);
}
var timer;
window.onresize = window.onscroll = function () {
    clearTimeout(timer);
    timer = setTimeout(mm, 2);
};

function mm() {
    var d;
    var a = document.documentElement.clientHeight || document.body.clientHeight;
    var c = document.documentElement.scrollTop || document.body.scrollTop;
    var b = Math.max(divSide.offsetHeight, divSide.clientHeight, divSide.scrollHeight);
    divSide.style.top = a + c - b + "px";
}
document.execCommand("BackgroundImageCache", false, true);