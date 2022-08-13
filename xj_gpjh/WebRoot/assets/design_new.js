$$ = function (a, c) {
    if (c) {
        var b = c.getElementsByTagName(a);
        if (!b || b.length == 0) {
            b = new Array();
            getbyTagName(c, a, b);
            return b;
        }
        return b;
    } else {
        return document.getElementsByTagName(a);
    }
};
var prevSaveData = "";
$ce = function (c, d, a) {
    var b = document.createElement(c);
    if (d) {
        b.innerHTML = d;
    }
    a.appendChild(b);
    return b;
};

function StringBuilder() {
    this._stringArray = new Array();
}
StringBuilder.prototype.append = function (a) {
    this._stringArray.push(a);
};
StringBuilder.prototype.toString = function (a) {
    a = a || "";
    return this._stringArray.join(a);
};
StringBuilder.prototype.clear = function () {
    this._stringArray.length = 0;
};

function getGapFillCount(b) {
    var d = 0;
    var e = 0;
    var a = b.length;
    do {
        e = b.indexOf(GapFillStr, e);
        if (e != -1) {
            d++;
            e += GapFillStr.length;
            for (var c = e; c < a; c++) {
                if (b.charAt(c) != "_") {
                    break;
                }
                e++;
            }
        }
    } while (e != -1);
    return d;
}
function replaceGapFill(l, b) {
    var d = 0;
    var a = 0;
    var g = new StringBuilder();
    var m = 0;
    do {
        a = d;
        d = l.indexOf(GapFillStr, d);
        var f = GapFillStr;
        if (d != -1) {
            var h = 0;
            g.append(l.substr(a, d - a));
            d += GapFillStr.length;
            for (var c = d; c < l.length; c++) {
                if (l[c] != "_") {
                    break;
                }
                h++;
                f += "_";
                d++;
            }
            var e = GapWidth + h * (GapWidth / 3);
            var k = false;
            if (b._rowVerify[m] && b._rowVerify[m]._verify == "日期") {
                e = 70;
                k = true;
            }
            var i = GapFillReplace.replace("width:" + GapWidth + "px", "width:" + e + "px");
            if (b._useTextBox) {
                i = i.replace("/>", " class='inputtext'/>");
            } else {
                i = i.replace("/>", " class='underline'/>");
            }
            g.append(i);
            m++;
        } else {
            if (a < l.length) {
                g.append(l.substr(a));
            }
        }
    } while (d != -1);
    return g.toString();
}
function replace_specialChar(a) {
    return a.replace(/(§)/g, "ξ").replace(/(¤)/g, "○").replace(/(〒)/g, "╤");
}
function getCoords(a) {
    var d = a.getBoundingClientRect(),
        i = a.ownerDocument,
        f = i.body,
        e = i.documentElement,
        c = e.clientTop || f.clientTop || 0,
        g = e.clientLeft || f.clientLeft || 0,
        h = d.top + (self.pageYOffset || e.scrollTop || f.scrollTop) - c,
        b = d.left + (self.pageXOffset || e.scrollLeft || f.scrollLeft) - g;
    return {
        top: h,
        left: b
    };
}
function mouseCoords(a) {
    if (!a) {
        return;
    }
    if (a.pageX || a.pageY) {
        return {
            x: a.pageX,
            y: a.pageY
        };
    }
    return {
        x: a.clientX + document.body.scrollLeft - document.body.clientLeft,
        y: a.clientY + document.body.scrollTop - document.body.clientTop
    };
}
function sb_setmenunav(a, b, e, c) {
    var h = $(a);
    if (!h) {
        return;
    }
    if (b) {
        if (h.timeArray) {
            window.clearTimeout(h.timeArray);
            h.timeArray = 0;
        }
        h.style.display = "block";
        if (!h.onmouseover) {
            h.onmouseover = function () {
                sb_setmenunav(a, true);
            };
            h.onmouseout = function () {
                sb_setmenunav(a, false);
            };
        }
        if (c) {
            var j = window.event || sb_setmenunav.caller.arguments[0];
            var i = mouseCoords(j);
            h.style.left = i.x + 1 + "px";
            h.style.top = i.y + 1 + "px";
        } else {
            if (e) {
                var g = e;
                if (g.parentNode.tagName.toLowerCase() == "li") {
                    g = e.parentNode;
                }
                var m = getCoords(g);
                var k = m.left;
                var f = m.top + g.offsetHeight;
                var l = document.documentElement.clientHeight || document.body.clientHeight;
                if (k + h.offsetWidth > bodyWidth) {
                    k = bodyWidth - h.offsetWidth - 30;
                }
                if (f + h.offsetHeight > l) {
                    h.style.height = l - 10 - f + "px";
                }
                h.style.left = k + "px";
                h.style.top = f + "px";
                if (a == "qType") {
                    h.style.left = k - 70 + "px";
                }
            }
        }
    }
    if (e && e.tagName.toLowerCase() == "a") {
        h.needSaveClass = e;
        h.prevClass = e.className;
    } else {
        if (h.needSaveClass) {
            if (b) {
                h.needSaveClass.className = h.prevClass ? h.prevClass + " hover" : "hover";
            } else {
                h.needSaveClass.className = h.prevClass || "";
            }
        }
    }
    if (!b) {
        h.timeArray = window.setTimeout(function () {
            h.style.display = "none";
            h.style.height = "";
        }, 100);
    }
}
var GapFillStr = "___";
var GapWidth = 21;
var GapFillReplace = "<input style='width:" + GapWidth + "px;' />";

function getFillStr(b) {
    var c = "";
    for (var a = 0; a < b; a++) {
        c += GapFillStr;
    }
    if (!c) {
        c = GapFillStr;
    }
    return c;
}
var EditorIndex = 1;
var EditToolBarItems = ["fontname", "fontsize", "textcolor", "bgcolor", "bold", "italic", "underline", "emoticons", "link", "image", "flash", "source"];
var EditToolBarItemsPageCut = ["fontname", "fontsize", "textcolor", "bgcolor", "bold", "italic", "underline", "strikethrough", "subscript", "superscript", "title", "plainpaste", "-", "justifyleft", "justifycenter", "justifyright", "indent", "outdent", "link", "emoticons", "image", "flash", "media", "table", "hr", "source"];

function getByClass(b, f, d) {
    var a = $$(b, f);
    var e = new Array();
    for (var c = 0; c < a.length; c++) {
        if (a[c].className.toLowerCase() == d.toLowerCase()) {
            e.push(a[c]);
        }
    }
    return e;
}
function getbyTagName(b, c, e) {
    var d;
    for (var a = 0; a < b.childNodes.length; a++) {
        d = b.childNodes[a];
        if (d.tagName === c) {
            e.push(d);
        }
        if (d.childNodes.length > 0 && d.nodeType == 1) {
            getbyTagName(d, c, e);
        }
    }
}
var defaultFileExt = ".jpg|.jpeg|.gif|.bmp|.png|.pdf|.doc|.docx|.xls|.xlsx|.ppt|.txt|.rar|.zip|.gzip";

function Request(d) {
    var b = window.document.location.href;
    var f = b.indexOf("?");
    var e = b.substr(f + 1);
    var c = e.split("&");
    for (var a = 0; a < c.length; a++) {
        var g = c[a].split("=");
        if (g[0].toUpperCase() == d.toUpperCase()) {
            return g[1];
        }
    }
    return "";
}
function isEmpty(a) {
    return trim(a) == "";
}
function isInt(a) {
    var b = /^-?[0-9]+$/;
    return b.test(a);
}
function isPositive(a) {
    var b = /^\+?[1-9][0-9]*$/;
    return b.test(a);
}
function toInt(a) {
    return parseInt(trim(a));
}
var status_tip = $("status_tip");
var topnav = $("topnav");
var divSurvey = $("sur");
var divMenu = $("divMenu");
var questions = $("question");
var firstPage = null;
var questionHolder = new Array();
var cur = null;
var curover = null;
var curinsert = null;
var langVer = 0;
var WjxActivity = new Object();
var DataArray = new Array();
var total_page = 0;
var total_question = 0;
var select_item_num = 1;
var isMergeAnswer = false;
var isCompleteLoad = false;
var referRelHT = new Object();
var designversion = "7";
var hasInsPromoteJump = false;
var lastAddNewQTime = null;
var prevcurmove = null;
var useShortCutAddNewQ = false;
var liSetCat = $("liSetCat");
var totalPingFen = 0;
var QIndentity = 1;

function trim(a) {
    if (a && a.replace) {
        return a.replace(/(^\s*)|(\s*$)/g, "");
    } else {
        return a;
    }
}
var interval_time;
init_page();

function init_page() {
    addEventSimple(window, "resize", setSidePos);
    setSidePos();
    show_status_tip("正在读取数据，请稍后...");
    processData();
    interval_time = setInterval(autoSave, 120000);
}
function processData() {//解析数据
    if (serverVersion && serverVersion != designversion) {
        alert("很抱歉，由于系统版本升级，您本机使用的脚本文件已过期，请您刷新页面或者重启浏览器再编辑问卷！");
        return;
    }
    var a = hfData.value;
	
    if (a == "error") {
        window.location = "/error/error.aspx?source=designQHandler";//报错页面
    } else {
        if (a == "timeout") {
            alert("您的登录信息超时，请重新登录，谢谢！");
            window.location = "/wjx/manage/myquestionnaires.aspx";//超时页面
        } else {
            show_status_tip("数据读取成功，初始化...");
            set_data_fromServer(a);
            set_data_toDesign();
            isCompleteLoad = true;
            if (questionHolder.length > 0) {
                questionHolder[0].focus();
            }
            loadComplete();
            document.title = "设计问卷 － 新疆国培计划";
        }
    }
}
function autoSave() {//自动保存
    var a = $("chkAutoSave");
    if (a.checked) {
        save_paper("edit", false);
    } else {
        show_status_tip("您编辑问卷已经超过五分钟，建议您保存后继续编辑，以免数据丢失给您带来不便！", 10000);
    }
}
function set_data_fromServer(c) {//分拆数据
    var b = new Array();
    var h = c.split("?")[0];//解析后得到 前面的数据 后面是一个css
    b = h.split("¤");//这个符号 区分题目
	
    var f = new Array();
    var g = c.split("?")[1];
    var f = g.split("§");
	
    if (f[0] == "true") {
        isMergeAnswer = true;
    } else {
        isMergeAnswer = false;
    }
    if (isMergeAnswer) {
        var e = $("chkAutoSave");
        e.checked = false;
    }
    userGuid = f[1];
	
    langVer = Number(f[2]);
	
    var a = new Array();
    a = b[0].split("§");
	//console.log(a);
    WjxActivity._start_time = a[0];
    WjxActivity._title = a[1];
    WjxActivity._tag = a[2];
    WjxActivity._random_begin = a[3];
    WjxActivity._random_end = a[4];
    WjxActivity._random_mode = a[5];
    WjxActivity._use_self_topic = a[6] == "true" ? true : false;
    WjxActivity._display_part = false;
    WjxActivity._display_part_num = 0;
    WjxActivity._partset = "";
    if (WjxActivity._random_mode == "1" || WjxActivity._random_mode == "2") {
        WjxActivity._display_part = a[7] == "true" ? true : false;
        if (WjxActivity._display_part) {
            WjxActivity._display_part_num = parseInt(a[8]);
        }
    } else {
        if (WjxActivity._random_mode == "3") {
            WjxActivity._partset = a[7] || "";
        }
    }
	
    for (var d = 1; d < b.length; d++) {//以题目为单位分成数组
        DataArray[d - 1] = set_string_to_dataNode(b[d]);
		//console.log(DataArray[d - 1])
    }
}
function setLiCat(a) {//维度暂时不做考虑
    liSetCat.style.display = totalPingFen > 1 ? "none" : "none";
    if (a) {
        if (totalPingFen == 20) {
            alert("您的问卷包含评分题，在编辑完问卷后您可以通过设置维度来进行维度分析");
        }
    }
}
function isQuestionLikert(b) {
    var c = b._type;
    var a = b._tag || 0;
    return c == "radio" && a;
}
function set_string_to_dataNode(m) {
    var d = new Object();
    var b = new Array();
    b = m.split("§");
	//console.log(b);
    d._type = b[0];
    switch (b[0]) {
    case "page":
        d._topic = b[1];
        d._title = b[2];
        d._tag = b[3];
        d._iszhenbie = b[4] == "true";
        d._mintime = b[5] ? parseInt(b[5]) : "";
        d._maxtime = b[6] ? parseInt(b[6]) : "";
        total_page++;
		
        break;
    case "cut":
		
        d._title = b[1];
        d._tag = b[2];
        break;
    case "gapfill":
        d._topic = b[1];
        var o = b[2].split("〒");
        d._title = o[0];
        d._keyword = o.length == 2 ? o[1] : "";
        d._relation = o[2] || "";
        d._tag = b[3];
        if (b[4] == "true") {
            d._requir = true;
        } else {
            d._requir = false;
        }
        d._gapcount = b[5] ? parseInt(b[5]) : 1;
        d._ins = b[6];
        if (b[7] == "true") {
            d._hasjump = true;
        } else {
            d._hasjump = false;
        }
        d._anytimejumpto = b[8];
        var g = b[9] || "";
        d._rowVerify = new Array();
        if (g) {
            var k = g.split("〒");
            for (var r = 0; r < k.length; r++) {
                if (!k[r]) {
                    continue;
                }
                var s = new Object();
                var l = k[r].split(",");
                s._verify = l[0];
                s._minword = l[1];
                s._maxword = l[2];
                d._rowVerify[r] = s;
            }
        }
        d._useTextBox = b[10] == "true";
        break;
    case "fileupload":
        d._topic = b[1];
        var o = b[2].split("〒");
        d._title = o[0];
        d._keyword = o.length == 2 ? o[1] : "";
        d._relation = o[2] || "";
        d._tag = b[3];
        if (b[4] == "true") {
            d._requir = true;
        } else {
            d._requir = false;
        }
        d._width = b[5] ? parseInt(b[5]) : 200;
        d._ext = b[6] || "";
        d._maxsize = b[7] ? parseInt(b[7]) : 1024;
        d._ins = b[8];
        if (b[9] == "true") {
            d._hasjump = true;
        } else {
            d._hasjump = false;
        }
        d._anytimejumpto = b[10];
        break;
    case "slider":
        d._topic = b[1];
        var o = b[2].split("〒");
        d._title = o[0];
        d._keyword = o.length == 2 ? o[1] : "";
        d._relation = o[2] || "";
        d._tag = b[3];
        if (b[4] == "true") {
            d._requir = true;
        } else {
            d._requir = false;
        }
        d._minvalue = b[5];
        d._maxvalue = b[6];
        d._minvaluetext = b[7];
        d._maxvaluetext = b[8];
        d._ins = b[9];
        if (b[10] == "true") {
            d._hasjump = true;
        } else {
            d._hasjump = false;
        }
        d._anytimejumpto = b[11];
        break;
    case "question":
        d._topic = b[1];
        var o = b[2].split("〒");
        d._title = o[0];
        d._keyword = o.length == 2 ? o[1] : "";
        d._relation = o[2] || "";
        d._tag = b[3];
        d._height = b[4] ? parseInt(b[4]) : 1;
        d._maxword = b[5];
        if (b[6] == "true") {
            d._requir = true;
        } else {
            d._requir = false;
        }
        if (b[7] == "true") {
            d._norepeat = true;
        } else {
            d._norepeat = false;
        }
        d._default = b[8];
        d._ins = b[9];
        if (b[10] == "true") {
            d._hasjump = true;
        } else {
            d._hasjump = false;
        }
        d._anytimejumpto = b[11];
        d._verify = b[12];
        d._needOnly = b[13] == "true" ? true : false;
        d._hasList = b[14] == "true" ? true : false;
        d._listId = b[15] ? parseInt(b[15]) : -1;
        d._width = b[16] ? parseInt(b[16]) : "";
        d._underline = b[17] == "true" ? true : false;
        d._minword = b[18] ? parseInt(b[18]) : "";
        if (b[19]) {
            var e = b[19].split("〒");
            d._isCeShi = true;
            d._ceshiValue = e[0] || 5;
            d._answer = e[1] || "请输入标准答案";
            d._ceshiDesc = e[2] || "";
        }
        break;
    case "sum":
        d._topic = b[1];
        var o = b[2].split("〒");
        d._title = o[0];
        d._keyword = o.length == 2 ? o[1] : "";
        d._relation = o[2] || "";
        d._tag = b[3];
        if (b[4] == "true") {
            d._requir = true;
        } else {
            d._requir = false;
        }
        d._total = parseInt(b[5]);
        d._rowtitle = b[6];
        d._rowwidth = b[7].indexOf("%") > -1 ? b[7] : "";
        d._ins = b[9];
        if (b[10] == "true") {
            d._hasjump = true;
        } else {
            d._hasjump = false;
        }
        d._anytimejumpto = b[11];
        break;
    case "radio":
    case "check":
    case "radio_down":
    case "matrix":
    case "boolean":
        if (b[0] == "boolean") {
            d._isbool = true;
            d._type = "radio";
        } else {
            d._type = b[0];
        }
        d._topic = b[1];
        var o = b[2].split("〒");
		
        d._title = o[0];
        d._keyword = o.length == 2 ? o[1] : "";
        d._relation = o[2] || "";
        d._mainWidth = o[3] || "";
        d._tag = isInt(b[3]) ? toInt(b[3]) : 0;
        if (d._type == "matrix") {
            var i = b[4].split("〒");
            d._rowtitle = i[0];
            if (i.length >= 2) {
                d._rowtitle2 = i[1];
            } else {
                d._rowtitle2 = "";
            }
            if (i.length == 3) {
                d._columntitle = i[2];
            } else {
                d._columntitle = "";
            }
        } else {
            var q = b[4].split("〒");
			//console.log(q,d._numperrow);
            d._numperrow = isInt(q[0]) ? toInt(q[0]) : 1;
            d._randomChoice = false;
            if (q.length == 2) {
                d._randomChoice = q[1] == "true" ? true : false;
            }
        }
        if (b[5] == "true") {
            d._hasvalue = true;
        } else {
            d._hasvalue = false;
        }
        if (b[6] == "true") {
            d._hasjump = true;
        } else {
            d._hasjump = false;
        }
        d._anytimejumpto = b[7];
        if (b[0] == "check" || (b[0] == "matrix" && d._tag == "102")) {
            if (b[8].split(",")[0] == "true") {
                d._requir = true;
            } else {
                d._requir = false;
            }
            d._lowLimit = b[8].split(",")[1];
            d._upLimit = b[8].split(",")[2];
        } else {
			//console.log(b)
            if (b[8] == "true") {
                d._requir = true;
            } else {
                d._requir = false;
            }
        }
        if (d._type == "matrix") {
            var t = b[9].split("〒");
            var u = t[0].split(",");
            d._rowwidth = u[0].indexOf("%") > -1 ? u[0] : "";
            d._randomRow = u[1] == "true";
            d._rowwidth2 = "";
            if (t.length >= 2) {
                d._rowwidth2 = t[1].indexOf("%") > -1 ? t[1] : "";
            }
            d._minvalue = 0;
            d._maxvalue = 10;
            if (d._tag == "202" || d._tag == "301") {
                d._minvalue = t[2] || "";
                d._maxvalue = t[3] || "";
            } else {
                if (d._tag == "102" || d._tag == "103") {
                    d._daoZhi = t[2] == "true";
                } else {
                    if (d._tag == "201") {
                        d._hasvalue = false;
                        var g = t[2] || "";
                        d._rowVerify = new Array();
                        if (g) {
                            var k = g.split(";");
                            for (var r = 0; r < k.length; r++) {
                                if (!k[r]) {
                                    continue;
                                }
                                var s = new Object();
                                var l = k[r].split(",");
                                s._verify = l[1];
                                s._minword = l[2];
                                s._maxword = l[3];
                                s._width = l[4] || "";
                                var c = parseInt(l[0]);
                                d._rowVerify[c] = s;
                            }
                        }
                    }
                }
            }
            d._isTouPiao = false;
            d._isCeShi = false;
        } else {
            var f = b[9].split("〒");
            if (f[0] == "true") {
                d._isTouPiao = true;
                d._touPiaoWidth = isInt(f[1]) ? parseInt(f[1]) : 50;
                d._displayTiao = f[2] == "true";
                d._displayNum = f[3] == "true";
                d._displayPercent = f[4] == "true";
            } else {
                if (f[0] == "ceshi") {
                    d._isCeShi = true;
                    d._ceshiValue = isInt(f[1]) ? parseInt(f[1]) : 5;
                    d._ceshiDesc = f[2];
                } else {
                    if (f[0] == "ceping") {
                        d._isCePing = true;
                    }
                }
            }
        }
        d._ins = b[10];
        d._verify = b[11];
        d._referTopic = b[12];
        d._referedTopics = b[13];
        d._select = new Array();
        var a = 14;
        for (var n = a; n < b.length; n++) {
            var p = new Array();
            p = b[n].split("〒");
            var h = n - a + 1;
            d._select[h] = new Object();
            d._select[h]._item_title = p[0];
            if (p[1] == "true") {
                d._select[h]._item_radio = true;
            } else {
                d._select[h]._item_radio = false;
            }
            d._select[h]._item_value = p[2];
            d._select[h]._item_jump = p[3];
            d._select[h]._item_tb = false;
            d._select[h]._item_tbr = false;
            d._select[h]._item_img = "";
            d._select[h]._item_imgtext = false;
            d._select[h]._item_desc = "";
            d._select[h]._item_label = "";
            if (p.length >= 9) {
                d._select[h]._item_tb = p[4] == "true";
                d._select[h]._item_tbr = p[5] == "true";
                d._select[h]._item_img = p[6];
                d._select[h]._item_imgtext = p[7] == "true";
                d._select[h]._item_desc = p[8];
                d._select[h]._item_label = p[9];
                d._select[h]._item_huchi = p[10] == "true";
            }
            select_item_num++;
        }
        break;
    default:
        break;
    }
    return d;
}
function set_data_toDesign() {
    var d = $("paper_attr_title");
    d.value = WjxActivity._title;
    var b = $("pater_title");
    b.innerHTML = d.value;
    var a = $("paper_attr_desc");
    a.value = WjxActivity._tag;
    var c = $("pater_desc");
    c.innerHTML = a.value;
    $("pater_title").ondblclick = function () {
        paper_attr("paper_attr_title");
    };
    $("pater_desc").ondblclick = function () {
        paper_attr("paper_attr_desc");
    };
    a.onblur = a.onclick = a.onchange = function () {
        paper_attr_desc_onblur(this);
    };
    $("chkUseSelfTopic").checked = WjxActivity._use_self_topic;
    $("chkUseSelfTopic").onclick = function () {
        WjxActivity._use_self_topic = this.checked;
        for (var e = 0; e < questionHolder.length; e++) {
            var f = questionHolder[e].dataNode._type;
            if (f != "cut" && f != "page") {
                questionHolder[e].divTopic.style.display = WjxActivity._use_self_topic ? "none" : "";
            }
        }
        if (this.checked) {
            show_status_tip("设置成功！请在问题标题前添加自定义题号。", 4000);
        }
    };
    if (a.value.indexOf("<") > -1) {
        a.style.display = "none";
    }
    if (WjxActivity._random_mode == "1") {
        $("radioRandomDisplaySubject").checked = true;
    } else {
        if (WjxActivity._random_mode == "2") {
            $("radioRandomDisplayPageNum").checked = true;
        } else {
            if (WjxActivity._random_mode == "3") {
                $("radioRandomPageQuestion").checked = true;
            }
        }
    }
    $("chkPart").onclick = function () {
        $("spanPartNum").style.display = this.checked ? "" : "none";
    };
    if (WjxActivity._display_part) {
        $("chkPart").checked = true;
        $("txtPartNum").value = WjxActivity._display_part_num;
        $("chkPart").onclick();
    }
    $("txtPartNum").onblur = $("txtPartNum").onchange = function () {
        var f = $("txtPartNum").value;
        if (!isInt(f)) {
            $("txtPartNum").value = "";
            show_status_tip("显示的题目数必须为正整数", 4000);
        } else {
            var e = trim($("txtRandomEnd").value) - trim($("txtRandomBegin").value) + 1;
            if (f > e) {
                $("txtPartNum").value = e;
                show_status_tip("显示的题目数不能超过参与随机的总题目数" + e, 4000);
            }
        }
    };
    $("txtRandomBegin").value = WjxActivity._random_begin;
    $("txtRandomEnd").value = WjxActivity._random_end;
    document.title = "正在加载问卷，请耐心等待....";
    set_dataNode_to_Design();
    radioRandomDisplay_Click();
}
function set_dataNode_to_Design() {
    var f;
    var h = 0;
    var d = 0;
    var b = document.createDocumentFragment();
    for (var c = 0; c < DataArray.length; c++) {
		
        f = create_question(DataArray[c]);
        b.appendChild(f);
        if (DataArray[c]._type == "page" && firstPage == null) {
            firstPage = f;
            if (window.isTiKu) {
                firstPage.style.display = "none";
            }
        } else {
            questionHolder[h++] = f;
        }
        if (DataArray[c]._referedTopics) {
            var g = DataArray[c]._referedTopics.split(",");
            for (var e = 0; e < g.length; e++) {
                referRelHT[g[e]] = f;
            }
        }
        if (DataArray[c]._type != "page") {
            if (referRelHT[DataArray[c]._topic]) {
                var a = referRelHT[DataArray[c]._topic];
                f._referDivQ = a;
                if (!a._referedArray) {
                    a._referedArray = new Array();
                }
                a._referedArray.push(f);
                if (DataArray[c]._type == "sum") {
                    f.createSum();
                } else {
                    f.createTableRadio();
                }
            }
        }
        if (DataArray[c]._isCePing || isQuestionLikert(DataArray[c])) {
            totalPingFen++;
            setLiCat();
        }
    }
    questions.appendChild(b);
}
function getDataNodeByTopic(b) {
    for (var c = 0, a = questionHolder.length; c < a; c++) {
        var d = questionHolder[c].dataNode;
        if (d._type == "page" || d._type == "cut") {
            continue;
        }
        if (b == d._topic) {
            return d;
        }
    }
    return null;
}
function getJumpTitle(c) {
    if (c == "0" || c == "") {
        return "直接跳到下一题";
    } else {
        if (c == "1") {
            return "直接跳到问卷末尾";
        } else {
            if (isInt(c)) {
                var b = getDataNodeByTopic(c);
                if (b) {
                    var a = b._title;
                    if (!WjxActivity._use_self_topic) {
                        a = b._topic + "." + a;
                    }
                    return a;
                }
            }
        }
    }
    return "";
}
var status_tip_timeout = null;

function show_status_tip(b, d) {
    clearTimeout(status_tip_timeout);
    status_tip.style.display = "block";
    status_tip.innerHTML = b;
    var a = document.documentElement.scrollTop || document.body.scrollTop;
    var c = document.documentElement.clientHeight || document.body.clientHeight;
    if (status_tip.hasSetWidth) {
        status_tip.style.width = "";
    }
    status_tip.style.top = a + c - status_tip.offsetHeight - 25 + "px";
    if (status_tip.offsetWidth < 420) {
        status_tip.style.width = "420px";
        status_tip.hasSetWidth = true;
    } else {
        status_tip.style.width = status_tip.offsetWidth + 5 + "px";
    }
    if (d > 0) {
        status_tip_timeout = setTimeout("status_tip.style.display='none';status_tip.style.width = '';status_tip.hasSetWidth=false;", d);
    }
}
divSurvey.onscroll = function () {
    if (!divSurvey.scrollTip) {
        show_status_tip("提示：通过按End键可以定位到最后一题，按Home键可以定位到第一题", 8000);
        divSurvey.scrollTip = true;
    }
};

function setSidePos() {
    status_tip.style.left = getCoords(divSurvey).left + "px";
    var a = document.documentElement.clientHeight || document.body.clientHeight;
    divSurvey.style.height = a - 200 + "px";
}
function show(a) {
    return;
}
var descEditorCreated = false;

function paper_attr(a) {
    PDF_launch("divQAttr", 520, 350);
    var c = "paper_attr_desc";
    if (!descEditorCreated) {
        KE.init({
            id: c,
            newlineTag: "p",
            width: "400px",
            height: "180px",
            items: EditToolBarItemsPageCut
        });
        KE.create(c);
        setInterval(new Function('KE.util.setData("paper_attr_desc")'), 500);
        descEditorCreated = true;
        KE.util.focus(c);
    }
    var b = $(a);
    b.select();
}
function paper_attr_title_onblur(a) {
    var b = $("pater_title");
    b.innerHTML = a.value = replace_specialChar(trim(a.value));
}
function paper_attr_desc_onblur(a) {
    var b = $("pater_desc");
    b.innerHTML = a.value;
}
var titleEditorCreated = false;

function openTitleEditor(a, d) {
    PDF_launch("divTitleEditor", 720, 450, d);
    var c = "textTitleId";
    if (!titleEditorCreated) {
        KE.init({
            id: c,
            items: EditToolBarItemsPageCut
        });
        KE.create(c);
        setInterval(new Function('KE.util.setData("textTitleId")'), 500);
        titleEditorCreated = true;
        KE.util.focus(c);
    }
    var b = a.value || a.innerHTML || "";
    $("divTitleEditor").initContent = b;
    KE.util.setFullHtml(c, b);
}
function clickOK() {
    var a = KE.util.getData("textTitleId");
    window.parent.PDF_close(a);
}
function clickCancel() {
    var a = "-1nc";
    if (CheckIsDirty()) {
        if (confirm("您要保存刚才所做的更改吗？")) {
            a = KE.util.getData("textTitleId");
        }
    }
    window.parent.PDF_close(a);
}
function CheckIsDirty() {
    var a = KE.util.getData("textTitleId");
    return $("divTitleEditor").initContent != a;
}
function openJumpWindow(a, c, f) {
    var l = a.dataNode._topic;
    var d = "&nbsp;<span style='color:#075DB3;'>请选择要跳转到的题目：</span>";
    d += "<div style='padding:5px;'>";
    if (!f) {
        d += "<a onclick='jumpSelected(0,this);' href='javascript:void(0);' title='提示：题号“0”表示顺序填写下一题' class='link-UF90'>选择直接跳到下一题</a>\r\n&nbsp;&nbsp;";
    }
    d += "<a onclick='jumpSelected(1,this);'  href='javascript:void(0);' title='提示：题号“1”表示直接跳到问卷末尾'  class='link-UF90'>选择直接跳到问卷末尾</a>\r\n";
    d += "<div style='border-top:1px solid #ccddff;margin:10px 0;'>";
    for (var e = 0, h = questionHolder.length; e < h; e++) {
        var j = questionHolder[e].dataNode;
        if (j._type == "page" || j._type == "cut") {
            continue;
        }
        var k = j._topic;
        if (k - l > 0) {
            var b = k + ".";
            if (WjxActivity._use_self_topic) {
                b = "";
            }
            var g = j._title.replace(/<.+?>/gim, "");
            d += "<div style='margin-top:10px;'><a class='link-U00a6e6' onclick='jumpSelected(" + k + ",this);' href='javascript:void(0);'  title='" + g + "'>" + b + g.substring(0, 23) + "</a></div>\r\n";
        }
    }
    d += "</div></div>";
    toolTipLayer.innerHTML = d;
    toolTipLayer.jumpObj = c;
    toolTipLayer.style.width = "300px";
    sb_setmenunav(toolTipLayer, true, c);
}
function openValWindow(b, c) {
    var a = "<div style='padding:5px 10px;'>";
    a += "<div style='cursor:pointer;margin-top:3px;'><a onclick='valChanged(2);' class='link-06f' href='javascript:void(0);'>交换选项分数</a></div>";
    a += "<div style='cursor:pointer;margin-top:3px;'><a onclick='valChanged(0);' class='link-06f' href='javascript:void(0);'>分数<b>从1开始</b>顺序递增</a></div>";
    a += "<div style='cursor:pointer;margin-top:3px;'><a onclick='valChanged(1);' class='link-06f' href='javascript:void(0);'>选项分数全部<b>加1</b></a></div>";
    a += "<div style='cursor:pointer;margin-top:3px;'><a onclick='valChanged(-1);' class='link-06f' href='javascript:void(0);'>选项分数全部<b>减1</b></a></div>";
    a += "</div>";
    toolTipLayer.innerHTML = a;
    toolTipLayer.valObj = b;
    toolTipLayer.style.width = "150px";
    sb_setmenunav(toolTipLayer, true, c);
}
function valChanged(f) {
    if (!toolTipLayer.valObj) {
        return;
    }
    var c = toolTipLayer.valObj;
    var h = toolTipLayer.valObj.dataNode;
    var g = c.option_radio;
    if (f == 0) {
        for (var d = 1; d < g.length; d++) {
            g[d].get_item_value().value = d;
        }
    } else {
        if (f == 2) {
            var e = 1;
            var a = g.length - 1;
            while (e < a) {
                var b = g[a].get_item_value().value;
                g[a].get_item_value().value = g[e].get_item_value().value;
                g[e].get_item_value().value = b;
                e++;
                a--;
            }
        } else {
            for (var d = 1; d < g.length; d++) {
                g[d].get_item_value().value = parseInt(h._select[d]._item_value) + f;
            }
        }
    }
    c.updateItem();
    toolTipLayer.valObj = null;
    sb_setmenunav(toolTipLayer, false);
}
function openProvinceWindow(a, c) {
    var b = "北京,天津,河北省,山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,上海,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,广西壮族自治区,海南省,重庆,四川省,贵州省,云南省,西藏自治区,陕西省,宁夏回族自治区,甘肃省,青海省,新疆维吾尔自治区,香港特别行政区,澳门特别行政区,台湾省,其它国家,不指定";
    var g = "<div style='padding:5px 10px;'>";
    var f = b.split(",");
    for (var d = 1; d <= f.length; ++d) {
        var j = f[d - 1];
        var e = "link-06f";
        if (j == "不指定") {
            e = "link-f60";
        }
        var h = "<span style='cursor:pointer;margin-top:3px;'><a onclick='provinceChanged(this);' class='" + e + "' href='javascript:void(0);'>" + j + "</a></span>";
        g += h;
        if (d % 4 == 0) {
            g += "<div></div>";
        } else {
            g += "&nbsp;&nbsp;";
        }
    }
    g += "</div>";
    toolTipLayer.innerHTML = g;
    toolTipLayer.provinceObj = c;
    toolTipLayer.style.width = "360px";
    sb_setmenunav(toolTipLayer, true, c);
}
function provinceChanged(a) {
    if (!toolTipLayer.provinceObj) {
        return;
    }
    toolTipLayer.provinceObj.value = a.innerHTML;
    if (toolTipLayer.provinceObj.value == "不指定") {
        toolTipLayer.provinceObj.value = "";
    }
    if (toolTipLayer.provinceObj.onblur) {
        toolTipLayer.provinceObj.onblur();
    }
    toolTipLayer.provinceObj = null;
    sb_setmenunav(toolTipLayer, false);
}
function jumpSelected(a, b) {
    if (toolTipLayer.jumpObj) {
        toolTipLayer.jumpObj.value = a || "0";
        toolTipLayer.jumpObj.title = b.innerHTML;
        if (toolTipLayer.jumpObj.onblur) {
            toolTipLayer.jumpObj.onblur();
        }
        toolTipLayer.jumpObj = null;
        if (cur) {
            cur.updateItem();
        }
    }
    toolTipLayer.style.width = "250px";
    sb_setmenunav(toolTipLayer, false);
}
function getPageQCount() {
    var c = 0;
    var d = new Array();
    var a = 0;
    for (var b = 0; b < questionHolder.length; b++) {
        if (questionHolder[b].dataNode._type == "page") {
            c++;
            d.push(a);
            a = 0;
        } else {
            if (questionHolder[b].dataNode._type != "cut") {
                a++;
            }
        }
    }
    d.push(a);
    return d;
}
function initPageQuestionRandom() {
    radioRandomDisplay_Click();
    PDF_launch("divRandom", 540, 220);
}
function endSetRandom() {
    setRandomPart();
    if (!vipUser && !$("radioRandomDisplayNone").checked) {
        $("radioRandomDisplayNone").click();
        alert("只有企业版用户才能设置随机逻辑，请先升级到企业版用户！");
    }
    window.parent.PDF_close();
}
function radioRandomDisplay_Click() {
    if ($("radioRandomDisplaySubject").checked) {
        if (total_question < 2) {
            alert("问卷题目超过两题以上才能选择此项");
            $("radioRandomDisplayNone").click();
            return;
        }
        $("divNoPart").style.display = "";
        $("spanBegin").innerHTML = "题";
        $("spanEnd").innerHTML = "题";
        $("spanPartTip1").innerHTML = "题目";
        $("spanPartTip2").innerHTML = "题目";
        if (!(trim($("txtRandomBegin").value) >= 1 && trim($("txtRandomBegin").value) <= total_question)) {
            $("txtRandomBegin").value = 1;
        }
        if (!(trim($("txtRandomEnd").value) >= 2 && trim($("txtRandomEnd").value) <= total_question)) {
            $("txtRandomEnd").value = total_question;
        }
        $("divRandomDisplayNum").style.display = "";
        $("divRandomTip").style.display = "";
        setRandomPart();
        $("divPageQuestion").style.display = "none";
    } else {
        if ($("radioRandomDisplayPageNum").checked) {
            if (total_page < 2) {
                alert("问卷页数超过两页以上才能选择此项");
                $("radioRandomDisplayNone").click();
                return;
            }
            $("divNoPart").style.display = "";
            $("spanBegin").innerHTML = "页";
            $("spanEnd").innerHTML = "页";
            $("spanPartTip1").innerHTML = "分页";
            $("spanPartTip2").innerHTML = "分页";
            if (!(trim($("txtRandomBegin").value) >= 1 && trim($("txtRandomBegin").value) <= total_page)) {
                $("txtRandomBegin").value = 1;
            }
            if (!(trim($("txtRandomEnd").value) >= 2 && trim($("txtRandomEnd").value) <= total_page)) {
                $("txtRandomEnd").value = total_page;
            }
            $("divRandomDisplayNum").style.display = "";
            $("divRandomTip").style.display = "";
            setRandomPart();
            $("divPageQuestion").style.display = "none";
        } else {
            if ($("radioRandomPageQuestion").checked) {
                $("divNoPart").style.display = "none";
                $("divRandomTip").style.display = "";
                var b = $("divPageQuestion");
                b.style.height = "90px";
                b.style.overflow = "auto";
                b.style.display = "";
                var d = "";
                for (var c = 0; c < total_page; c++) {
                    d += "<div><input type='checkbox' class='kaitong'/>开通<b>第" + (c + 1) + "页</b>随机功能&nbsp;&nbsp;<span style='display:none;'><input type='checkbox' class='part'/>只显示此页部分题目&nbsp;<label style='display:none;'><b>题目数：</b><input type='text' class='count' style='width:50px;' cur='" + c + "'/></label></span></div>";
                }
                d += "<div class='divclear'></div>";
                b.innerHTML = d;
                var f = getByClass("input", b, "kaitong");
                var e = getByClass("input", b, "part");
                var j = getByClass("input", b, "count");
                for (var c = 0; c < f.length; c++) {
                    f[c].onclick = function () {
                        var k = this.parentNode;
                        var i = $$("span", k)[0];
                        i.style.display = this.checked ? "" : "none";
                    };
                }
                for (var c = 0; c < e.length; c++) {
                    e[c].onclick = function () {
                        var i = this.parentNode;
                        var k = $$("label", i)[0];
                        k.style.display = this.checked ? "" : "none";
                    };
                }
                for (var c = 0; c < j.length; c++) {
                    j[c].onblur = function () {
                        var l = parseInt(this.getAttribute("cur"));
                        var i = getPageQCount()[l];
                        var k = this.value;
                        if (!isInt(k)) {
                            this.value = "";
                            return;
                        }
                        if (k > i) {
                            alert("题目数不能超过第" + (l + 1) + "页的总题目数：" + i);
                            this.value = "";
                        }
                    };
                }
                if (WjxActivity._partset) {
                    var h = WjxActivity._partset.split(",");
                    for (var c = 0; c < h.length; c++) {
                        var a = h[c].split(";");
                        var g = parseInt(a[0]);
                        if (f[g]) {
                            f[g].click();
                            if (a[1]) {
                                e[g].click();
                                j[g].value = a[1];
                            }
                        }
                    }
                }
            } else {
                $("divNoPart").style.display = "none";
                $("divRandomTip").style.display = "none";
                setRandomPart();
                $("divPageQuestion").style.display = "none";
            }
        }
    }
}
function setRandomPart() {
    var a = $("divPageQuestion");
    var g = getByClass("input", a, "kaitong");
    var b = getByClass("input", a, "part");
    var f = getByClass("input", a, "count");
    var d = "";
    var e = 0;
    for (var c = 0; c < g.length; c++) {
        if (g[c].checked) {
            if (e > 0) {
                d += ",";
            }
            d += c;
            if (b[c].checked && isInt(f[c].value)) {
                d += ";" + f[c].value;
            }
            e++;
        }
    }
    WjxActivity._partset = d;
}
function txtRandom_onblur() {
    if ($("radioRandomDisplaySubject").checked) {
        if (!(trim($("txtRandomBegin").value) >= 1 && trim($("txtRandomBegin").value) <= total_question)) {
            $("txtRandomBegin").value = 1;
        }
        if (!(trim($("txtRandomEnd").value) >= 2 && trim($("txtRandomEnd").value) <= total_question)) {
            $("txtRandomEnd").value = total_question;
        }
        if ((trim($("txtRandomBegin").value) - trim($("txtRandomEnd").value)) >= 0) {
            $("txtRandomBegin").focus();
        }
    } else {
        if ($("radioRandomDisplayPageNum").checked) {
            if (!(trim($("txtRandomBegin").value) >= 1 && trim($("txtRandomBegin").value) <= total_page)) {
                $("txtRandomBegin").value = 1;
            }
            if (!(trim($("txtRandomEnd").value) >= 2 && trim($("txtRandomEnd").value) <= total_page)) {
                $("txtRandomEnd").value = total_page;
            }
            if ((trim($("txtRandomBegin").value) - trim($("txtRandomEnd").value)) >= 0) {
                show_status_tip("开始页号必须小于结束页号！", 4000);
                $("txtRandomBegin").focus();
            }
        }
    }
}
function $import(b) {
    var a = document.createElement("script");
    a.setAttribute("src", b);
    a.setAttribute("type", "text/javascript");
    document.getElementsByTagName("head")[0].appendChild(a);
}
function $importNoCache(a) {
    $import(a);
}
function loadComplete() {
    show_status_tip("成功获得数据", 2000);
    save_paper("init", false);
    setSidePos();
    divMenu.style.visibility = "visible";
    topnav.style.visibility = "visible";
    $importNoCache("assets/operation_new.js");
    $importNoCache("assets/kindeditor.js");
    $importNoCache("assets/createqattr_new.js");
    $importNoCache("assets/utility_new.js");
}
function getXmlHttp() {
    var a;
    try {
        a = new XMLHttpRequest();
    } catch (b) {
        try {
            a = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (b) {
            try {
                a = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (b) {}
        }
    }
    return a;
}
function removeEventSimple(c, a, b) {
    if (c.removeEventListener) {
        c.removeEventListener(a, b, false);
    } else {
        if (c.detachEvent) {
            c.detachEvent("on" + a, b);
        }
    }
}
function addEventSimple(c, a, b) {
    if (c.addEventListener) {
        c.addEventListener(a, b, false);
    } else {
        if (c.attachEvent) {
            c.attachEvent("on" + a, b);
        }
    }
}
function control_text(b) {
    var a = document.createElement("input");
    a.type = "text";
    a.style.width = b * 10 + "px";
    a.className = "inputtext";
    return a;
}
function control_image(b) {
    var a = document.createElement("img");
    a.src = b;
    return a;
}
function control_check() {
    var a = document.createElement("input");
    a.type = "checkbox";
    a.tabIndex = "-1";
    return a;
}
function control_textarea(c, b) {
    var a = document.createElement("textarea");
    a.wrap = "soft";
    a.rows = c;
    a.style.width = b * 10 + "px";
    a.style.height = c * 18 + "px";
    a.className = "inputtext";
    return a;
}
function control_btn(b) {
    var a = document.createElement("input");
    a.type = "button";
    a.value = b;
    return a;
}
function control_radio(a) {
    if (navigator.appName.indexOf("Microsoft") != -1) {
        try {
            var c = document.createElement('<input type="radio" name="' + a + '" />');
            return c;
        } catch (b) {
            var c = document.createElement("input");
            c.type = "radio";
            c.name = a;
            return c;
        }
    } else {
        var c = document.createElement("input");
        c.type = "radio";
        c.name = a;
        return c;
    }
}
function addTouPiao(d, c, a, b) {
    d.append("<table><tr>");
    if (c._displayTiao) {
        d.append("<td>");
        d.append("<div class='bar' style='float:none;'><div class='precent' style='width: " + a[b - 1] + "%; display: block;'>");
        d.append("<img height='13' width='149' src='/Images/wjx/viewstat/vote_cl_v2.png' alt=''/></div></div>");
        d.append("</td>");
    }
    if (c._displayPercent || c._displayNum) {
        d.append("<td>");
        d.append("<div style='margin-top: 3px; '>");
        if (c._displayNum) {
            d.append("<span style='color:#ff6600;'>" + parseInt(a[b - 1]) + "票</span>");
        }
        if (c._displayPercent) {
            if (c._displayNum) {
                d.append("(");
            }
            d.append(a[b - 1].toFixed(2) + "%");
            if (c._displayNum) {
                d.append(")");
            }
        }
        d.append("</div><div style='clear: both;'></div></td>");
    }
    d.append("</tr></table>");
}
function create_question(f) {
    var af = f._type;
    var s = f._verify;
    var v = f._height > 1;
    _likertMode = f._tag || 0;
    var D = false;
    var g = false;
    if (isMergeAnswer && isCompleteLoad) {
        g = true;
    }
    var z = document.createElement("div");
    z.className = "div_question";
    z.dataNode = f;
    z.tabIndex = -1;
    var J = $ce("div", "", z);
    J.className = "div_preview";
    z.div_question_preview = J;
    var H = af == "question";
    var F = af == "slider";
    var Q = af == "sum";
    var N = af == "page";
    var C = af == "cut";
    var b = af == "check";
    var m = af == "radio";
    var r = m && _likertMode;
    var q = m && _likertMode > 1;
    var h = af == "radio_down";
    var d = af == "matrix";
    var a = af == "matrix" && _likertMode > 300;
    var E = b && _likertMode;
    var X = af == "fileupload";
    var l = m || h || b || d;
    var ac = !C && !N;
    var L = af == "gapfill";
    z.isMergeNewAdded = g;
    if (ac) {
        total_question++;
    }
    QIndentity++;
    var k = document.createElement("div");
    if (ac) {
        var W = f._topic + "";
        if (f._topic < 100) {
            W += ".";
        }
        var B = $ce("div", W, k);
        z.divTopic = B;
        B.className = "div_topic_question";
        if (WjxActivity._use_self_topic) {
            B.style.display = "none";
        }
    }
    if (N) {
        var i = f._iszhenbie;
        var B = $ce("span", "<span style='font-size:14px; font-weight:bold;'>第" + f._topic + "页/共" + total_page + "页</span>", k);
        B.className = "div_topic_page_question";
        z.divTopic = B;
        z.divZhenBie = $ce("span", "<b style='color:red;'>--甄别页</b>", k);
        z.divZhenBie.style.display = i ? "" : "none";
        z.divTimeLimit = $ce("span", "", k);
        z.showTimeLimit = function () {
            var ag = "";
            if (this.dataNode._mintime) {
                ag = "<b style='color:green;'>最短填写时间：" + this.dataNode._mintime + "秒</b>";
            }
            if (this.dataNode._maxtime) {
                if (ag) {
                    ag += "&nbsp;";
                }
                ag += "<b style='color:red;'>最长填写时间：" + this.dataNode._maxtime + "秒</b>";
            }
            if (ag) {
                ag = "&nbsp;&nbsp;--" + ag + "";
            }
            z.divTimeLimit.innerHTML = ag;
        };
        z.showTimeLimit();
        if (f._topic == "1") {
            isPrevFirstPage = true;
        }
    }
    if (C) {
        k.className = "div_title_cut_question";
    }
    if (ac) {
        k.className = "div_title_question_all";
    }
    var o = $ce("div", "", k);
    var Z = f._title;
    if (L) {
        Z = replaceGapFill(Z, f);
    }
    var T = $ce("span", Z, o);
    if (N) {
        o.className = "div_title_page_question";
    } else {
        o.className = "div_title_question";
    }
    z.get_div_title = function () {
        return T;
    };
    if (ac) {
        var ad = $ce("span", "&nbsp;*", o);
        ad.style.color = "red";
        ad.style.display = f._requir ? "" : "none";
        z.get_span_required = function () {
            return ad;
        };
        if (H) {
            var u = $ce("span", "", o);
            u.className = "font-a0";
            z.showMinMaxWord = function (ai, ag) {
                var al = this.dataNode;
                var ah = "";
                var aj = type_wd_words;
                var ak = type_wd_minlimit;
                if (al._verify == "数字" || al._verify == "小数") {
                    aj = "";
                    ak = type_wd_minlimitDigit;
                    ah = type_wd_digitfrom;
                }
                if (!isEmpty(ai) && !isEmpty(ag)) {
                    u.innerHTML = "&nbsp;（" + ah + ag + type_wd_to + ai + aj + "）";
                    u.style.display = "";
                } else {
                    if (!isEmpty(ai)) {
                        u.innerHTML = "&nbsp;（" + ai + type_wd_limit + "）";
                        if (al._verify == "数字" || al._verify == "小数") {
                            u.innerHTML = "&nbsp;（" + type_wd_maxlimitDigit + ai + "）";
                        }
                        u.style.display = "";
                    } else {
                        if (!isEmpty(ag)) {
                            u.innerHTML = "&nbsp;（" + ak + ag + aj + "）";
                            u.style.display = "";
                        } else {
                            u.style.display = "none";
                        }
                    }
                }
            };
            z.showMinMaxWord(f._maxword, f._minword);
            z.get_span_maxword = function () {
                return u;
            };
            if (f._isCeShi) {
                var w = $ce("span", "", o);
                w.style.color = "#efa030";
                w.innerHTML = "（标准答案：" + f._answer + "）";
                z.spanCeShi = w;
            }
        }
        if (b || (d && _likertMode == "102")) {
            var ab = document.createElement("span");
            z.updateSpanCheck = function () {
                var aj = this.dataNode;
                aj._lowLimit = aj._lowLimit || "";
                aj._upLimit = aj._upLimit || "";
                var ag = type_check;
                if (d) {
                    ag = "矩阵多选题";
                }
                var ai = "";
                var ah = false;
                if (E) {
                    ai = "-1";
                }
                if (aj._lowLimit != ai && aj._upLimit != ai) {
                    if (aj._lowLimit == aj._upLimit) {
                        ab.innerHTML = "&nbsp;[" + type_check_limit1 + "<b>" + aj._lowLimit + "</b>" + type_check_limit5;
                    } else {
                        ab.innerHTML = "&nbsp;[" + type_check_limit1 + "<b>" + aj._lowLimit + "</b>-<b>" + aj._upLimit + "</b>" + type_check_limit5;
                    }
                    ah = true;
                } else {
                    if (aj._lowLimit != ai) {
                        ab.innerHTML = "&nbsp;[" + type_check_limit3 + "<b>" + aj._lowLimit + "</b>" + type_check_limit5;
                        ah = true;
                    } else {
                        if (aj._upLimit != ai) {
                            ab.innerHTML = "&nbsp;[" + type_check_limit4 + "<b>" + aj._upLimit + "</b>" + type_check_limit5;
                            ah = true;
                        } else {
                            if (!aj._isTouPiao) {
                                ab.innerHTML = "&nbsp;[" + ag;
                            }
                        }
                    }
                }
                if (E) {
                    if (ah) {
                        if (aj._lowLimit == "" || aj._lowLimit == aj._select.length - 1) {
                            ab.innerHTML = "&nbsp;[" + type_check_limit1 + "<b>" + type_order_all + "</b>" + type_check_limit5;
                        }
                        ab.innerHTML += type_order_limit_end;
                    } else {
                        ab.innerHTML = "&nbsp;[" + type_order;
                    }
                }
                if (!aj._isTouPiao) {
                    ab.innerHTML += "]";
                }
                ab.className = "font-06f";
            };
            z.updateSpanCheck();
            o.appendChild(ab);
        } else {
            if (a) {
                var ab = $ce("span", "", o);
                z.updateSpanCheck = function () {
                    if (this.dataNode._tag == "301" && this.dataNode._minvalue !== "" && this.dataNode._maxvalue !== "") {
                        ab.innerHTML = "&nbsp;[输入" + this.dataNode._minvalue + "到" + this.dataNode._maxvalue + "的数字]";
                        ab.className = "font-06f";
                    } else {
                        ab.innerHTML = "";
                    }
                    ab.style.display = this.dataNode._tag == "301" ? "" : "none";
                };
                z.updateSpanCheck();
            }
        }
        if (d) {
            if (f._tag == "102" || f._tag == "103") {
                var n = $ce("span", "", o);
                z.updateSpanMatrix = function () {
                    if (f._daoZhi) {
                        var ag = "竖向单选";
                        if (f._tag == "102") {
                            ag = "竖向多选";
                        }
                        n.innerHTML = "&nbsp;[" + ag + "]";
                        n.className = "font-06f";
                    } else {
                        n.innerHTML = "";
                    }
                };
                z.updateSpanMatrix();
            }
        }
    }
    var S = $ce("div", "", k);
    S.style.clear = "both";
    J.appendChild(k);
    if (ac) {
        var t = document.createElement("div");
        t.className = "div_table_radio_question";
        var p = $ce("div", "", t);
        p.className = "div_table_clear_top";
        J.appendChild(t);
    }
    if (H) {
        var x = control_textarea("1", "50");
        t.appendChild(x);
        var G = null;
        var ae = document.createElement("span");
        t.appendChild(ae);
        ae.style.display = "none";
        G = document.createElement("span");
        G.className = "design-icon design-date";
        t.appendChild(G);
        G.style.display = "none";
        x.style.overflow = "auto";
        if (f._verify != "省市区" && f._verify != "高校") {
            x.value = f._default;
        } else {
            if (f._default) {
                x.value = "指定省份为：" + f._default;
            }
        }
        z.showTextAreaUnder = function () {
            x.className = this.dataNode._underline ? "underline" : "inputtext";
        };
        z.showTextAreaWidth = function () {
            if (isEmpty(this.dataNode._width)) {
                x.style.width = "62%";
            } else {
                x.style.width = this.dataNode._width + "px";
                x.style.display = this.dataNode._width == 1 ? "none" : "";
            }
        };
        z.showTextAreaHeight = function () {
            x.style.height = this.dataNode._height * 18 + "px";
        };
        z.showTextAreaDate = function () {
            var ag = this.dataNode._verify;
            if (ag == "日期" || ag == "生日" || ag == "入学时间") {
                x.style.width = "100px";
                x.style.height = "18px";
                G.style.display = "";
                ae.style.display = "none";
            } else {
                if (ag == "城市单选" || ag == "城市多选" || ag == "省市区" || ag == "高校") {
                    var ah = "100px";
                    if (ag == "城市多选") {
                        ah = "400px";
                    } else {
                        if (ag == "省市区") {
                            ah = "250px";
                        } else {
                            if (ag == "高校") {
                                ah = "250px";
                            }
                        }
                    }
                    x.style.width = ah;
                    x.style.height = "18px";
                    ae.innerHTML = "&nbsp;<img src='/Images/Mysojump/QuestionnaireMng/Design/city.gif' alt=''/>";
                    ae.style.display = "";
                    G.style.display = "none";
                } else {
                    G.style.display = "none";
                    ae.style.display = "none";
                    this.showTextAreaWidth();
                    this.showTextAreaHeight();
                }
            }
        };
        z.showTextAreaUnder();
        z.showTextAreaWidth();
        z.showTextAreaHeight();
        z.showTextAreaDate();
        z.get_textarea = function () {
            return x;
        };
    }
    if (X) {
        var U = document.createElement("input");
        U.type = "file";
        var I = document.createElement("input");
        I.type = "button";
        I.value = "上传";
        t.appendChild(U);
        $ce("span", "&nbsp;&nbsp;", t);
        t.appendChild(I);
        var j = $ce("div", "请选择上传文件", t);
        z.updateFileUpload = function () {
            U.width = f._width;
            if (f._ext) {
                j.innerHTML = "请选择上传文件，扩展名为" + f._ext;
            } else {
                j.innerHTML = "请选择上传文件，扩展名为" + defaultFileExt;
            }
        };
        z.updateFileUpload();
    }
    if (L) {}
    if (F) {
        var M = $ce("span", f._minvaluetext || "", t);
        M.className = "spanLeft";
        M.style.color = "red";
        z.get_span_min_value_text = function () {
            return M;
        };
        var e = $ce("span", "(" + (f._minvalue || 0) + ")", t);
        e.className = "spanLeft";
        e.style.color = "red";
        z.get_span_min_value = function () {
            return e;
        };
        var y = $ce("span", "(" + (f._maxvalue || 100) + ")", t);
        y.className = "spanRight";
        y.style.color = "red";
        z.get_span_max_value = function () {
            return y;
        };
        var A = $ce("span", f._maxvaluetext || "", t);
        A.className = "spanRight";
        A.style.color = "red";
        z.get_span_max_value_text = function () {
            return A;
        };
        $ce("div", "", t).className = "divclear";
        var G = control_image("/Images/WJX/JoinQuestionnaire/slider1.jpg");
        G.style.width = "10px";
        var Y = control_image("/Images/WJX/JoinQuestionnaire/sliderEnd.jpg");
        Y.style.width = "97%";
        Y.style.height = "23px";
        t.appendChild(G);
        t.appendChild(Y);
        $ce("div", "", t).className = "divclear";
        t.style.width = "60%";
        var x = control_textarea("1", "10");
        x.style.display = "none";
    }
    if (Q) {
        z.createSum = function () {
            var am = new StringBuilder();
            am.append("<div  class='div_table_clear_top'></div>");
            if (this._referDivQ) {
                am.append("此题行标题来源于第" + this._referDivQ.dataNode._topic + "题的选中项");
            } else {
                am.append("<table style='width:100%;' border='0px'  cellpadding='5' cellspacing='0'>");
                var ai = "";
                var ag = "";
                am.append("<tbody>");
                var al = new Array();
                al = trim(f._rowtitle).split("\n");
                var ak = "";
                for (var ah = 0; ah < al.length; ah++) {
                    if (ah == al.length - 1) {
                        ai = "";
                        ag = "";
                    }
                    if (al[ah].length > 4 && al[ah].substring(0, 4) == "【标签】") {
                        var aj = al[ah].substring(4);
                        am.append("<tr><th align='left'><b style='color:#0066ff;'>" + aj + "</b></th><td></td></tr>");
                        ak = "padding-left:10px;";
                        continue;
                    }
                    if (f._rowwidth == "") {
                        am.append("<tr><th align='left' style='" + ag + ak + "'>" + al[ah] + "</th>");
                    } else {
                        am.append("<tr><th align='left' tyle='width:" + f._rowwidth + ";" + ag + ak + "'>" + al[ah] + "</th>");
                    }
                    am.append("<td  " + ai + "align='left' width='36'><input  type='text' style='width:36px;'/></td>");
                    am.append("<td  " + ai + "align='left'><img src='/Images/WJX/JoinQuestionnaire/slider1.jpg' style='width: 10px;'/><img src='/Images/WJX/JoinQuestionnaire/sliderEnd.jpg' style='width:250px;height: 23px;'/></td>");
                    am.append("</tr>");
                }
                am.append("</tbody></table>");
            }
            am.append("<div style='margin-top:10px;'><span style='color:#666666;'>" + sum_hint + "</span></div>");
            t.innerHTML = am.toString("");
        };
        z.createSum();
    }
    if (l) {
        z.createTableRadio = function () {
            var aq = this.dataNode;
            var al = aq._isTouPiao;
            var aj = aq._isCeShi;
            var ap = aq._isCePing;
            var aN = aq._numperrow ? aq._numperrow : 1;
            var an = aq._select;
            var aF = aq._tag;
            var aE = null;
            if (al) {
                aE = new Array();
                var aA = an.length - 1;
                var ai = 0;
                for (var aU = 1; aU <= aA; aU++) {
                    ai += aU;
                }
                for (var aU = 1; aU <= aA; aU++) {
                    aE[aU - 1] = 100 / ai * aU;
                }
            }
            var aG = new StringBuilder();
            aG.append("<div  class='div_table_clear_top'></div>");
            if (this._referDivQ) {
                var aV = "选项";
                if (aq._type == "matrix" || aq.type == "sum") {
                    aV = "行标题";
                }
                aG.append("此题" + aV + "来源于第" + this._referDivQ.dataNode._topic + "题的选中项");
            } else {
                if (h) {
                    aG.append("<select><option>" + type_radio_down + "</option>");
                    for (var aU = 1; aU < an.length; aU++) {
                        if (an[aU]._item_radio) {
                            aG.append("<option selected='selected'>" + trim(an[aU]._item_title) + "</option>");
                        } else {
                            aG.append("<option>" + trim(an[aU]._item_title) + "</option>");
                        }
                    }
                    aG.append("</select>");
                }
                if (m || (b && !E)) {
                    aG.append("<ul>");
                    var ak;
                    var aI = "%";
                    if (r) {
                        ak = 40;
                        aI = "px";
                        aN = 1;
                        if (aF == 101) {
                            ak = 105;
                        }
                    } else {
                        ak = (100 / aN) - 1;
                    }
                    var az = false;
                    var ag = 1;
                    for (var aU = 1; aU < an.length; aU++) {
                        if (af == "radio" && aF >= 1 && aF != 101 && aU == 1) {
                            var ar = "0px";
                            if (aF > 1) {
                                ar = "5px";
                            }
                            aG.append("<li class='notchoice' style='padding-right:15px;padding-top:" + ar + "'><b>" + an[1]._item_title + "</b></li>");
                        }
                        if (m && aF > 1 && aF != 101) {
                            if (aU == an.length - 1) {
                                aG.append("<li style='padding-left:3px;' class='design-icon design-off" + aq._tag + "'  ></li>");
                            } else {
                                aG.append("<li  style='padding-left:3px;' class='design-icon design-on" + aq._tag + "'  ></li>");
                            }
                        } else {
                            if (an[aU]._item_label) {
                                if (az) {
                                    aG.append("</ul></li>");
                                }
                                aG.append("<li style='width:100%;'><div><b>" + an[aU]._item_label + "</b></div><ul>");
                                az = true;
                                ag = 1;
                            }
                            if (aU == an.length - 1 && an[aU]._item_tb && !al) {
                                aG.append("<li>");
                            } else {
                                aG.append("<li  style='width:" + ak + aI + ";'>");
                            }
                            if (!an[aU]._item_img) {
                                if (al) {
                                    aG.append("<div style='float:left;width:" + aq._touPiaoWidth + "%;'>");
                                } else {
                                    if (aj && an[aU]._item_radio) {
                                        aG.append("<span style='color:#efa030;'>");
                                    }
                                }
                                if (m) {
                                    aG.append("<input type='radio'");
                                } else {
                                    aG.append("<input type='checkbox'");
                                }
                                if (af == "radio" && an[aU]._item_radio) {
                                    aG.append(" checked='checked'");
                                }
                                if (af == "check" && an[aU]._item_radio) {
                                    aG.append(" checked='checked'");
                                }
                                if (af == "radio" && aF == 1) {
                                    aG.append("'/>" + trim(an[aU]._item_value));
                                } else {
                                    aG.append("'/>" + trim(an[aU]._item_title));
                                }
                                if (al) {
                                    aG.append("</div>");
                                    aG.append("<div style='float:left;'>");
                                    addTouPiao(aG, aq, aE, aU);
                                    aG.append("</div><div style='clear:both;'></div>");
                                } else {
                                    if (aj && an[aU]._item_radio) {
                                        aG.append("&nbsp;(正确答案)</span>");
                                    } else {
                                        if (ap) {
                                            aG.append("<span style='color:#efa030;'>&nbsp;(分值：" + an[aU]._item_value + ")</span>");
                                        }
                                    }
                                }
                                if (aq._hasjump && aq._anytimejumpto < 1) {
                                    var ah = "跳转到下一题";
                                    if (an[aU]._item_jump == "1") {
                                        ah = "结束作答";
                                    } else {
                                        if (an[aU]._item_jump - 1 > 0) {
                                            ah = "跳转到第" + an[aU]._item_jump + "题";
                                        }
                                    }
                                    aG.append("<span style='color:#efa030;'>&nbsp;(" + ah + ")</span>");
                                }
                                if (an[aU]._item_desc) {
                                    aG.append("<div class='div_item_desc'>" + an[aU]._item_desc + "</div>");
                                }
                            } else {
                                aG.append("<table cellpadding='0' cellspacing='0'><tr><td valign='top'>");
                                if (m) {
                                    aG.append("<input type='radio'");
                                } else {
                                    aG.append("<input type='checkbox'");
                                }
                                if (af == "radio" && an[aU]._item_radio) {
                                    aG.append(" checked='checked'");
                                }
                                if (af == "check" && an[aU]._item_radio) {
                                    aG.append(" checked='checked'");
                                }
                                if (af == "radio" && aF == 1) {
                                    aG.append("'/>" + trim(an[aU]._item_value));
                                } else {
                                    aG.append("'/>");
                                }
                                aG.append("</td><td>");
                                if (an[aU]._item_imgtext) {
                                    aG.append("<div style='text-align:center;'><img src='" + an[aU]._item_img + "' alt='' />");
                                    aG.append(trim(an[aU]._item_title));
                                    aG.append("</div></td>");
                                } else {
                                    aG.append("<div><img src='" + an[aU]._item_img + "' alt='' /></div></td>");
                                }
                                aG.append("</tr>");
                                if (an[aU]._item_desc) {
                                    aG.append("<tr><td></td><td>");
                                    aG.append("<div class='div_item_desc'>" + an[aU]._item_desc + "</div>");
                                    aG.append("</td></tr>");
                                }
                                if (al) {
                                    aG.append("<tr><td></td><td align='center'>");
                                    addTouPiao(aG, aq, aE, aU);
                                    aG.append("</td></tr>");
                                }
                                aG.append("</table>");
                            }
                            if (an[aU]._item_tb) {
                                aG.append(" <input type='text' class='underline' style='color:#999999;' value='" + defaultOtherText + "'/>");
                            }
                            if (an[aU]._item_tbr) {
                                aG.append(" <span style='color: red;'> *</span>");
                            }
                            aG.append("</li>");
                        }
                        if (m && aF >= 1 && aF != 101 && aU == an.length - 1) {
                            var ar = "0px";
                            if (aF > 1) {
                                ar = "5px";
                            }
                            aG.append("<li  class='notchoice'  style='padding-left:15px;padding-top:" + ar + "'><b>" + an[an.length - 1]._item_title + "</b></li>");
                        }
                        if (aN > 1 && ag % aN == 0) {
                            aG.append("<div style='clear:both;'></div></ul><ul>");
                        }
                        ag++;
                    }
                    aG.append("<div style='clear:both;'></div></ul>");
                    if (az) {
                        aG.append("</li></ul>");
                    }
                }
                if (E) {
                    aG.append("<div style='width:90%;'><ul style='float:left;'>");
                    var ak;
                    ak = 100 / aN;
                    for (var aU = 1; aU < an.length; aU++) {
                        aG.append("<li style='float:none;'><input type='checkbox' />" + trim(an[aU]._item_title) + "</li>");
                    }
                    aG.append("</ul>");
                    var aD;
                    if (trim(aq._lowLimit) == "") {
                        aD = an.length - 1;
                    } else {
                        aD = trim(aq._lowLimit);
                    }
                    if (aD < 6) {
                        aD = 6;
                    }
                    var aO = aD * 20 + "px";
                    aG.append("<table style='float:left;'><tr><td verticalAlign='center'><div style='margin-left:10px;'><select size='" + aD + "' style='width:200px;height:" + aO + ";'></select></div>");
                    aG.append("</td><td verticalAlign='center'><div class='qButton'><ul><li><a><span class='design-icon design-first'></span><span>" + type_order_goTop + "</span></a></li><li><a><span class='design-icon design-up'></span><span>" + type_order_upMove + "</span></a></li>");
                    aG.append("<li style='margin-top:10px;'><a><span class='design-icon design-down'></span><span>" + type_order_downMove + "</span></a></li><li><a><span class='design-icon design-last'></span><span>" + type_order_goBottom + "</span></a></li></ul></div></td></tr></table>");
                    aG.append("<div style='clear:both;' ></div></div>");
                }
                if (d) {
                    var au = aq._daoZhi;
                    var aR = "100%";
                    if (aq._mainWidth) {
                        aR = aq._mainWidth + "%";
                    }
                    aG.append("<table style='width:" + aR + ";' border='0px'  cellpadding='5' cellspacing='0'>");
                    var aw = "";
                    var at = "";
                    var aZ = "radio";
                    var aK = new Array();
                    aK = trim(aq._rowtitle).split("\n");
                    var aJ = trim(aq._rowtitle2).split("\n");
                    var aM = false;
                    var ao = "";
                    if ((aF == 0) || (aF > 100 && aF < 200) || aF > 300) {
                        aG.append("<thead><tr><th></th>");
                        if (aF > 300) {
                            var aY = trim(aq._columntitle).split("\n");
                            for (var aU = 0; aU < aY.length; aU++) {
                                aG.append("<td align='center'>" + trim(aY[aU]) + "</td>");
                            }
                        } else {
                            if (au) {
                                for (var aU = 0; aU < aK.length; aU++) {
                                    if (aK[aU].length > 4 && aK[aU].substring(0, 4) == "【标签】") {
                                        continue;
                                    }
                                    aG.append("<td align='center'>" + trim(aK[aU]) + "</td>");
                                }
                            } else {
                                for (var aU = 1; aU < an.length; aU++) {
                                    aG.append("<td align='center'>" + trim(an[aU]._item_title) + "</td>");
                                }
                            }
                        }
                        at = "border-bottom:1px solid #efefef;";
                        aw = "style='" + at + "'";
                        aG.append("</tr></thead>");
                        if (aF == 102) {
                            aZ = "checkbox";
                        }
                    }
                    if (aF == 301) {
                        aZ = "text";
                    }
                    aG.append("<tbody>");
                    if (aF == "202") {
                        var aW = aq._minvalue;
                        var aH = aq._maxvalue;
                        aG.append("<tr><th></th><td align='left' width='410'><table width='100%'><tr><td width='60%'><div style='width:70%'><div style='float:left;color:red;'>" + aW + "</div><div style='float:right;color:red;'>" + aH + "</div><div style='clear:both;'></div></div></td></tr></table></td><th></th>");
                    }
                    if (!au) {
                        var aP = 0;
                        var aL = false;
                        var aC = "";
                        for (var aU = 0; aU < aK.length; aU++) {
                            if (aU == aK.length - 1) {
                                aw = "";
                                at = "";
                            }
                            if (aK[aU].length > 4 && aK[aU].substring(0, 4) == "【标签】") {
                                var aT = aK[aU].substring(4);
                                aG.append("<tr class='labelname'><th align='left'><b>" + aT + "</b></th><td colspan='" + an.length + "'></td>");
                                aG + "</tr>";
                                aM = true;
                                ao = "padding-left:20px;";
                                aL = !aL;
                                continue;
                            }
                            if (aq._rowwidth == "") {
                                aG.append("<tr><th align='left' style='" + at + ao + "'>" + aK[aU] + "</th>");
                            } else {
                                aG.append("<tr><th align='left' style='width:" + aq._rowwidth + ";" + at + ao + "'>" + aK[aU] + "</th>");
                            }
                            if (aF < 100 && aF) {
                                aG.append("<td><ul>");
                            }
                            if (aF > 200 && aF < 300) {
                                if (aF == 201) {
                                    var av = aq._rowVerify && aq._rowVerify[aU] ? aq._rowVerify[aU]._verify : "";
                                    var aB = "";
                                    var am = aq._rowVerify && aq._rowVerify[aU] ? aq._rowVerify[aU]._width : "";
                                    if (am) {
                                        am = "width:" + am + "%";
                                    }
                                    if (av == "日期") {
                                        aB = "<span class='design-icon design-date'></span>";
                                    } else {
                                        if (av == "城市单选" || av == "城市多选" || av == "省市区" || av == "高校") {
                                            aB = "&nbsp;<img src='/Images/Mysojump/QuestionnaireMng/Design/city.gif' alt=''/>";
                                        }
                                    }
                                    aG.append("<td  " + aw + "align='left'><textarea class='inputtext' style='overflow:auto;height:18px;" + am + "'></textarea>" + aB + "</td>");
                                } else {
                                    if (aF == 202) {
                                        aG.append("<td  " + aw + "align='left' width='60%'><img src='/Images/WJX/JoinQuestionnaire/slider1.jpg' style='width: 10px;'/><img src='/Images/WJX/JoinQuestionnaire/sliderEnd.jpg' style='width:284px;height: 23px;'/></td>");
                                    }
                                }
                            } else {
                                if (aF > 300) {
                                    var aX = "";
                                    if (aF == "303") {
                                        aX += "<select><option>" + type_radio_down + "</option>";
                                        for (var aQ = 1; aQ < an.length; aQ++) {
                                            aX += "<option>" + trim(an[aQ]._item_title) + "</option>";
                                        }
                                        aX += "</select>";
                                    }
                                    var aY = trim(aq._columntitle).split("\n");
                                    var ax = Number(300 / aY.length);
                                    for (var aS = 0; aS < aY.length; aS++) {
                                        if (aF == "303") {
                                            aG.append("<td  " + aw + "align='center'>" + aX + "</td>");
                                        } else {
                                            if (aF == "301") {
                                                ax = "30";
                                            }
                                            aG.append("<td  " + aw + "align='center'><textarea  type='text' style='overflow:auto;height:18px;width:" + ax + "px;'></textarea></td>");
                                        }
                                    }
                                } else {
                                    for (var aS = 1; aS < an.length; aS++) {
                                        if (aF > 100 || aF == 0) {
                                            aG.append("<td  " + aw + "align='center'><input  type='" + aZ + "'/>");
                                            if ((aF == 102 || aF == 103) && an[aS]._item_tb) {
                                                aG.append("<input type='text' value='" + defaultOtherText + "' style='color:#999999;width:70px;' class='underline'/> ");
                                                if (an[aS]._item_tbr) {
                                                    aG.append(" <span style='color: red;'> *</span>");
                                                }
                                            }
                                            aG.append("</td>");
                                        } else {
                                            if (aS == an.length - 1) {
                                                aG.append("<li style='padding-left:3px;' class='design-icon design-off" + aF + "'></li>");
                                            } else {
                                                aG.append("<li style='padding-left:3px;' class='design-icon design-on" + aq._tag + "'></li>");
                                            }
                                        }
                                    }
                                }
                            }
                            if (aF < 100 && aF) {
                                aG.append("</ul></td>");
                            }
                            var ay = "";
                            if (aP < aJ.length) {
                                ay = aJ[aP];
                            }
                            if (aq._rowwidth2 == "") {
                                aG.append("<th " + aw + ">" + ay + "</th>");
                            } else {
                                aG.append("<th style='width:" + aq._rowwidth2 + ";" + at + "'>" + ay + "</th>");
                            }
                            aG.append("</tr>");
                            aL = !aL;
                            aP++;
                        }
                    } else {
                        for (var aU = 1; aU < an.length; aU++) {
                            if (aU == an.length - 1) {
                                aw = "";
                                at = "";
                            }
                            if (aq._rowwidth == "") {
                                aG.append("<tr><th align='left' style='" + at + ao + "'>" + trim(an[aU]._item_title) + "</th>");
                            } else {
                                aG.append("<tr><th align='left' style='width:" + aq._rowwidth + ";" + at + ao + "'>" + trim(an[aU]._item_title) + "</th>");
                            }
                            for (var aS = 0; aS < aK.length; aS++) {
                                if (aK[aS].length > 4 && aK[aS].substring(0, 4) == "【标签】") {
                                    continue;
                                }
                                aG.append("<td  " + aw + "align='center'><input  type='" + aZ + "'/></td>");
                            }
                            aG.append("</tr>");
                        }
                    }
                    aG.append("</tbody></table>");
                }
            }
            aG.append("<div class='div_table_clear_bottom'></div>");
            t.innerHTML = aG.toString("");
        };
        z.createTableRadio(true);
    }
    if (ac) {
        var O = document.createElement("div");
        O.className = "div_ins_question";
        O.innerHTML = f._ins ? subjectInfo + f._ins : "";
        J.appendChild(O);
        z.get_div_ins = function () {
            return O;
        };
    }
    var R = $ce("div", "", J);
    R.style.height = "24px";
    R.style.visibility = "hidden";
    if (ac) {
        var P = document.createElement("div");
        P.className = "div_ins_question spanLeft";
        P.style.clear = "none";
        R.appendChild(P);
        z.set_jump_ins = function () {
            var ag = "*" + jump_info;
            P.style.display = this.dataNode._hasjump ? "" : "none";
            if (this.dataNode._hasjump) {
                if (this.dataNode._anytimejumpto < 1) {} else {
                    if (this.dataNode._anytimejumpto == "1") {
                        ag += "<span style='color:#ff6600;'>(结束作答)</span>";
                    } else {
                        ag += "<span style='color:#ff6600;'>(跳转到第" + this.dataNode._anytimejumpto + "题)</span>";
                    }
                }
            }
            P.innerHTML = ag;
        };
        z.set_jump_ins();
        var c = document.createElement("div");
        c.className = "div_ins_question spanLeft";
        c.style.clear = "none";
        c.innerHTML = "";
        z.getRelation = function () {
            var ai = this.dataNode._relation;
            if (!ai) {
                return;
            }
            var ao = ai.split(",");
            var ap = "依赖于第" + ao[0] + "题的第" + ao[1] + "个选项";
            var at = getDataNodeByTopic(ao[0]);
            if (!at) {
                return;
            }
            var aj = "";
            var ag = ao[1].split(";");
            var ak = "选择";
            var ah = "";
            if (ag.length > 1) {
                ah = "中的任何一个选项";
            }
            for (var al = 0; al < ag.length; al++) {
                var aq = ag[al];
                if (aq - 0 < 0) {
                    aq = aq * -1;
                    ak = "没有选择";
                }
                if (at._select && at._select[aq]) {
                    if (aj) {
                        aj += "；";
                    }
                    aj += at._select[aq]._item_title;
                } else {
                    return;
                }
            }
            var am = at._topic + ".";
            if (WjxActivity._use_self_topic) {
                am = "";
            }
            var ar = "当题目“" + am + at._title + "”" + ak + "“" + aj + "”" + ah + "时，此题才显示";
            var an = new Array();
            an.push(ap);
            an.push(ar);
            return an;
        };
        c.style.display = f._relation ? "" : "none";
        var V = z.getRelation();
        if (V) {
            c.innerHTML = V[0];
            c.title = V[1];
        }
        R.appendChild(c);
        z.RelationIns = c;
    }
    if (f._relation == "0") {
        z.style.display = "none";
    }
    var K = document.createElement("div");
    K.className = "div_ins_question spanLeft";
    K.innerHTML = "<a href='javascript:void(0);' onclick='insertQ(curover);' class='a555' style='text-decoration:underline;'>在此题后插入新题</a>";
    K.style.clear = "none";
    K.style.display = "none";
    R.appendChild(K);
    z.divInsertOp = K;
    var aa = document.createElement("div");
    aa.className = "spanRight";
    aa.style.clear = "none";
    R.appendChild(aa);
    z.divTableOperation = aa;
    if (f._hasjump || f._relation) {
        z.divTableOperation.parentNode.style.visibility = "";
    }
    $ce("div", "", R).style.clear = "both";
    if (N || C) {
        $ce("div", "", J).style.clear = "both";
    }
    cancelInputClick(z);
    return z;
}
function cancelInputClick(e) {
    var f = e.div_question_preview;
    var b = $$("input", f);
    for (var d = 0; d < b.length; d++) {
        b[d].onclick = function () {
            if (this.checked) {
                this.checked = false;
                return false;
            }
        };
        b[d].onkeydown = function () {
            this.value = "";
            return false;
        };
    }
    var a = $$("textarea", f);
    for (var d = 0; d < a.length; d++) {
        a[d].onclick = function () {
            return false;
        };
        a[d].onchange = function () {
            this.value = "";
        };
        a[d].onkeydown = function () {
            this.value = "";
            return false;
        };
    }
    var c = $$("select", f);
    for (var d = 0; d < c.length; d++) {
        c[d].onchange = function () {
            this.selectedIndex = 0;
            return false;
        };
    }
}
var needCheckLeave = true;
window.onbeforeunload = function () {
    finishEditing();
};
window.onunload = function () {
    if (needCheckLeave) {
        if (confirm("您要保存对此问卷所做的更改吗？")) {
            save_paper("edit", true, true);
            alert("问卷保存成功！您登录后即可再次修改或者发布！");
        }
    }
};

function windowGotoUrl(a) {
    needCheckLeave = false;
    window.location.href = a;
}
function chkAutoSave_Click(a) {}
function returnOld() {
    if (window.confirm("确认使用旧版编辑界面吗？")) {
        save_paper("old", true);
    }
}
var havereturn = false;
var timeoutTimer = null;
var errorTimes = 0;

function processError(a) {
    if (!havereturn) {
        havereturn = true;
        errorTimes++;
        var b = "网络异常，可能是您电脑上的防火墙拦截了保存的问卷数据，请关闭防火墙试试！";
        if (window.localStorage) {
            window.localStorage.setItem("lastSaveDataExceed" + activityID, a);
            b += "如果还是无法保存，请联系我们。";
        }
        show_status_tip(b, 0);
    }
    if (timeoutTimer) {
        clearTimeout(timeoutTimer);
    }
}
function save_paper(c, T, F) {
    if (c != "init" && questionHolder.length == 0) {
        alert("您还未添加题目！");
        return false;
    }
    show_status_tip("正在保存，请耐心等候...", 0);
    if (c != "init" && !save_paper_validate(T)) {
        return false;
    }
    var M = document.getElementById("paper_attr_title");
    var t = document.getElementById("paper_attr_desc");
    var ad = new Array();
    ad[0] = new Object();
    ad[0]._title = M.value;
    ad[0]._tag = t.value;
    ad[0]._display_part = false;
    ad[0]._display_part_num = 0;
    ad[0]._partset = "";
    if ($("radioRandomDisplayNone").checked) {
        ad[0]._random_mode = "0";
    } else {
        if ($("radioRandomDisplaySubject").checked) {
            ad[0]._random_mode = "1";
        } else {
            if ($("radioRandomDisplayPageNum").checked) {
                ad[0]._random_mode = "2";
            } else {
                if ($("radioRandomPageQuestion").checked) {
                    ad[0]._random_mode = "3";
                    ad[0]._partset = WjxActivity._partset;
                    var x = WjxActivity._partset.split(",");
                    var w = "";
                    for (var aa = 0; aa < x.length; aa++) {
                        var ab = x[aa].split(";");
                        var O = parseInt(ab[0]);
                        var V = parseInt(ab[1]);
                        var Y = getPageQCount()[O];
                        var d = true;
                        var S = Y + ":" + V;
                        if (!w) {
                            w = S;
                        } else {
                            if (w != S) {
                                d = false;
                            }
                        }
                    }
                    if (x.length < 2) {
                        d = false;
                    }
                    if (d) {
                        ad[0]._partset += "|true";
                    }
                }
            }
        }
    }
    if ($("chkPart").checked) {
        var G = $("txtPartNum").value;
        if (G != "") {
            ad[0]._display_part = true;
            ad[0]._display_part_num = parseInt(G);
        }
    }
    ad[0]._random_begin = trim($("txtRandomBegin").value);
    ad[0]._random_end = trim($("txtRandomEnd").value);
    ad[1] = firstPage.dataNode;
    var U = false;
    var I = false;
    var E = false;
    var C = 1;
    var n = 2;
    for (var aa = 0; aa < questionHolder.length; aa++) {
        if (questionHolder[aa].checkValid && questionHolder[aa].checkValid() == false) {
            ad[aa + 2] = questionHolder[aa].validate();
        }
        ad[aa + 2] = questionHolder[aa].dataNode;
        var ae = ad[aa + 2]._type;
        if (ae == "page") {
            if (ad[aa + 2]._topic != n) {
                ad[aa + 2]._topic = n;
            }
            n++;
        } else {
            if (ae != "cut") {
                if (ad[aa + 2]._topic != C) {
                    ad[aa + 2]._topic = C;
                }
                C++;
            }
        }
        if (ad[aa + 2]._hasjump) {
            I = true;
        }
        var y = ad[aa + 2]._relation;
        if (y && y != "0") {
            var P = y.split(",");
            var A = true;
            E = true;
            var H = P[0];
            var k = P[1].split(";");
            var l = getDataNodeByTopic(H);
            if (l && ad[aa + 2]._topic - H > 0) {
                var J = l._select;
                var ae = l._type;
                if (ae == "radio" || ae == "radio_down" || ae == "check") {
                    for (var K = 0; K < k.length; K++) {
                        var h = k[K];
                        if (h == 0 || h >= J.length) {
                            A = false;
                        }
                    }
                } else {
                    A = false;
                }
            } else {
                A = false;
            }
            if (!A) {
                ad[aa + 2]._relation = "";
            }
        }
        ad[aa + 2]._referTopic = "";
        ad[aa + 2]._referedTopics = "";
        if (questionHolder[aa]._referDivQ) {
            ad[aa + 2]._referTopic = questionHolder[aa]._referDivQ.dataNode._topic;
            U = true;
        }
        if (questionHolder[aa]._referedArray) {
            ad[aa + 2]._referedTopics = "";
            for (var L = 0; L < questionHolder[aa]._referedArray.length; L++) {
                if (L > 0) {
                    ad[aa + 2]._referedTopics += ",";
                }
                ad[aa + 2]._referedTopics += questionHolder[aa]._referedArray[L].dataNode._topic;
            }
        }
    }
    if (ad[0]._random_mode != "0") {
        if (I) {
            alert("此问卷包含跳题逻辑，不能设置随机逻辑");
            show_status_tip("此问卷包含跳题逻辑，不能设置随机逻辑", 1000);
            return false;
        } else {
            if (U) {
                alert("此问卷包含引用逻辑，不能设置随机逻辑");
                show_status_tip("此问卷包含引用逻辑，不能设置随机逻辑", 1000);
                return false;
            } else {
                if (E) {
                    alert("此问卷包含关联逻辑，不能设置随机逻辑");
                    show_status_tip("此问卷包含关联逻辑，不能设置随机逻辑", 1000);
                    return false;
                }
            }
        }
    }
    var D = 0;
    for (var aa = 1; aa < ad.length; aa++) {
        if (ad[aa]._type == "page") {
            D++;
        }
    }
    ad[0]._total_page = D;
    var m = new StringBuilder();
    var Z = false;
    var b = false;
    var v = false;
    var s = false;
    var a = ad[0]._title + "§" + ad[0]._tag + "§" + ad[0]._random_begin + "§" + ad[0]._random_end + "§" + ad[0]._random_mode + "§" + WjxActivity._use_self_topic;
    if ((ad[0]._random_mode == "1" || ad[0]._random_mode == "2") && ad[0]._display_part) {
        a += "§" + ad[0]._display_part + "§" + ad[0]._display_part_num;
    } else {
        if (ad[0]._random_mode == "3") {
            a += "§" + ad[0]._partset + "§";
        } else {
            a += "§§";
        }
    }
	
    a += "§" + designversion;
    for (var aa = 1; aa < ad.length; aa++) {
        switch (ad[aa]._type) {
        case "question":
            var y = ad[aa]._relation || "";
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "〒" + ad[aa]._keyword + "〒" + y + "§" + ad[aa]._tag + "§" + ad[aa]._height + "§" + ad[aa]._maxword + "§" + ad[aa]._requir + "§" + ad[aa]._norepeat + "§" + ad[aa]._default + "§" + ad[aa]._ins + "§" + ad[aa]._hasjump + "§" + ad[aa]._anytimejumpto + "§" + ad[aa]._verify + "§" + ad[aa]._needOnly + "§" + ad[aa]._hasList + "§" + ad[aa]._listId + "§" + ad[aa]._width + "§" + ad[aa]._underline + "§" + ad[aa]._minword);
            if (ad[aa]._isCeShi) {
                m.append("§" + ad[aa]._ceshiValue + "〒" + ad[aa]._answer + "〒" + ad[aa]._ceshiDesc);
                s = true;
            }
            break;
        case "gapfill":
            var y = ad[aa]._relation || "";
            var ac = getGapFillCount(ad[aa]._title);
            var r = ad[aa]._useTextBox ? "true" : "";
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "〒" + ad[aa]._keyword + "〒" + y + "§" + ad[aa]._tag + "§" + ad[aa]._requir + "§" + ac + "§" + ad[aa]._ins + "§" + ad[aa]._hasjump + "§" + ad[aa]._anytimejumpto);
            m.append("§");
            if (ad[aa]._rowVerify) {
                for (var e = 0; e < ac; e++) {
                    if (e > 0) {
                        m.append("〒");
                    }
                    if (!ad[aa]._rowVerify[e]) {
                        continue;
                    }
                    var q = ad[aa]._rowVerify[e];
                    m.append(q._verify || "");
                    m.append(",");
                    m.append(q._minword || "");
                    m.append(",");
                    m.append(q._maxword || "");
                }
            }
            m.append("§");
            m.append(r);
            break;
        case "slider":
            var y = ad[aa]._relation || "";
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "〒" + ad[aa]._keyword + "〒" + y + "§" + ad[aa]._tag + "§" + ad[aa]._requir + "§" + ad[aa]._minvalue + "§" + ad[aa]._maxvalue + "§" + ad[aa]._minvaluetext + "§" + ad[aa]._maxvaluetext + "§" + ad[aa]._ins + "§" + ad[aa]._hasjump + "§" + ad[aa]._anytimejumpto);
            break;
        case "fileupload":
            var y = ad[aa]._relation || "";
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "〒" + ad[aa]._keyword + "〒" + y + "§" + ad[aa]._tag + "§" + ad[aa]._requir + "§" + ad[aa]._width + "§" + ad[aa]._ext + "§" + ad[aa]._maxsize + "§" + ad[aa]._ins + "§" + ad[aa]._hasjump + "§" + ad[aa]._anytimejumpto);
            break;
        case "sum":
            var y = ad[aa]._relation || "";
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "〒" + ad[aa]._keyword + "〒" + y + "§" + ad[aa]._tag + "§" + ad[aa]._requir + "§" + ad[aa]._total + "§" + ad[aa]._rowtitle + "§" + ad[aa]._rowwidth + "§0§" + ad[aa]._ins + "§" + ad[aa]._hasjump + "§" + ad[aa]._anytimejumpto);
            m.append("§" + ad[aa]._referTopic + "§" + ad[aa]._referedTopics);
            break;
        case "cut":
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._title + "§" + ad[aa]._tag);
            break;
        case "page":
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "§" + ad[aa]._tag);
            var p = ad[aa]._iszhenbie ? "true" : "";
            m.append("§" + p);
            m.append("§" + ad[aa]._mintime);
            if (ad[aa]._mintime) {
                Z = true;
            }
            m.append("§" + ad[aa]._maxtime);
            if (ad[aa]._maxtime) {
                b = true;
            }
            break;
        case "check":
        case "radio_down":
        case "radio":
        case "matrix":
            var y = ad[aa]._relation || "";
            ad[aa]._tag = isNaN(ad[aa]._tag) ? 0 : ad[aa]._tag;
            var W = ad[aa]._mainWidth || "";
            m.append("¤" + ad[aa]._type + "§" + ad[aa]._topic + "§" + ad[aa]._title + "〒" + ad[aa]._keyword + "〒" + y + "〒" + W + "§" + ad[aa]._tag + "§");
            if (ad[aa]._type == "matrix") {
                if (ad[aa]._referTopic) {
                    ad[aa]._rowtitle2 = "";
                }
                m.append(ad[aa]._rowtitle + "〒" + ad[aa]._rowtitle2 + "〒" + ad[aa]._columntitle);
            } else {
                m.append(ad[aa]._numperrow + "〒" + ad[aa]._randomChoice);
            }
            m.append("§" + ad[aa]._hasvalue + "§" + ad[aa]._hasjump + "§" + ad[aa]._anytimejumpto + "§" + ad[aa]._requir);
            if (ad[aa]._type == "check" || (ad[aa]._type == "matrix" && ad[aa]._tag == "102")) {
                m.append("," + ad[aa]._lowLimit + "," + ad[aa]._upLimit);
            }
            if (ad[aa]._type == "matrix") {
                var B = ad[aa]._rowwidth;
                if (ad[aa]._randomRow) {
                    B += ",true";
                }
                m.append("§" + B + "〒" + ad[aa]._rowwidth2);
                if (ad[aa]._tag == "202" || ad[aa]._tag == "301") {
                    m.append("〒" + ad[aa]._minvalue + "〒" + ad[aa]._maxvalue);
                } else {
                    if (ad[aa]._tag == "102" || ad[aa]._tag == "103") {
                        var o = ad[aa]._daoZhi || "";
                        m.append("〒" + o);
                    } else {
                        if (ad[aa]._tag == "201") {
                            if (ad[aa]._rowVerify) {
                                m.append("〒");
                                var N = trim(ad[aa]._rowtitle).split("\n");
                                var R = 0;
                                for (var e = 0; e < N.length; e++) {
                                    if (N[e].substring(0, 4) == "【标签】") {
                                        continue;
                                    }
                                    if (ad[aa]._rowVerify[R]) {
                                        var q = ad[aa]._rowVerify[R];
                                        m.append(R + ",");
                                        m.append(q._verify || "");
                                        m.append(",");
                                        m.append(q._minword || "");
                                        m.append(",");
                                        m.append(q._maxword || "");
                                        m.append(",");
                                        m.append(q._width || "");
                                        m.append(";");
                                    }
                                    R++;
                                }
                            }
                        }
                    }
                }
            } else {
                if (ad[aa]._isTouPiao) {
                    m.append("§" + ad[aa]._isTouPiao + "〒" + ad[aa]._touPiaoWidth + "〒" + ad[aa]._displayTiao + "〒" + ad[aa]._displayNum + "〒" + ad[aa]._displayPercent);
                    v = true;
                } else {
                    if (ad[aa]._isCeShi) {
                        m.append("§ceshi〒" + ad[aa]._ceshiValue + "〒" + ad[aa]._ceshiDesc);
                        s = true;
                    } else {
                        if (ad[aa]._isCePing) {
                            m.append("§ceping");
                        } else {
                            m.append("§");
                        }
                    }
                }
            }
            m.append("§" + ad[aa]._ins + "§" + ad[aa]._verify);
            m.append("§" + ad[aa]._referTopic + "§" + ad[aa]._referedTopics);
            for (var X = 1; X < ad[aa]._select.length; X++) {
                var z = "";
                if (ad[aa]._select[X]._item_huchi) {
                    z = "〒true";
                }
                m.append("§" + ad[aa]._select[X]._item_title + "〒" + ad[aa]._select[X]._item_radio + "〒" + ad[aa]._select[X]._item_value + "〒" + ad[aa]._select[X]._item_jump + "〒" + ad[aa]._select[X]._item_tb + "〒" + ad[aa]._select[X]._item_tbr + "〒" + ad[aa]._select[X]._item_img + "〒" + ad[aa]._select[X]._item_imgtext + "〒" + ad[aa]._select[X]._item_desc + "〒" + ad[aa]._select[X]._item_label + z);
            }
            break;
        }
    }
    clearInterval(interval_time);
    var u = getXmlHttp();
    F = F || false;
    var g = "curID=" + activityID;
    if (window.isTiKu) {
        g = "tid=" + tikuId;
    }
    //var f = "/Handler/designQHandler.ashx?submitType=redesign&" + g + "&userguid=" + userGuid + "&validate_text=ys&t=" + (new Date()).valueOf() + "&sstate=" + encodeURIComponent(c);//接口
    
    var f = "/xj_gpjh/paper_save.action?submitType=redesign&" + g + "&userguid=" + userGuid + "&validate_text=ys&t=" + (new Date()).valueOf() + "&sstate=" + encodeURIComponent(c);//接口
    
	
    if (c == "pub") {
        f += "&pub=1";
    }
    if (c == "pub2") {
        f += "&pub=2";
    }
    if (F) {
        u.open("post", f, false);
    } else {
        u.open("post", f);
    }
    u.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
    if (!F) {
        u.onreadystatechange = function () {
            if (u.readyState == 4) {
                if (timeoutTimer) {
                    clearTimeout(timeoutTimer);
                }
                if (u.status == 200) {
					
                    afterSave(unescape(u.responseText), c, Q);
                } else {
                    clearInterval(interval_time);
                    show_status_tip("很抱歉，由于网络异常您的保存没有成功，请再试一次！", 6000);
                    interval_time = setInterval(autoSave, 120000);
                }
            }
        };
    }
    a += "§" + U + "§" + Z + "§" + b + "§" + v + "§" + s;
    var Q = a + m.toString("");
    
    
    m.clear();
    if (c == "init") {
        prevSaveData = Q;
        show_status_tip("成功加载", 1000);
        return true;
    }
    if (window.localStorage) {
        window.localStorage.setItem("lastSaveData" + activityID, Q);
    }
    havereturn = false;
	
    if (Q == prevSaveData && c != "pub2") {
        saveSuc(c);
    } else {
        if (!F) {
            timeoutTimer = setTimeout(function () {
                processError(Q);
            }, 20000);
            if (errorTimes == 0) {
                u.send("surveydata=" + encodeURIComponent(Q));
            } else {
                postWithIframe(f, Q);
            }
        }
        if (F) {
            u.send("surveydata=" + encodeURIComponent(Q));
			
            afterSave(unescape(u.responseText), c, Q);
        }
    }
    return true;
}
function postWithIframe(b, c) {
    var a = document.createElement("div");
    a.style.display = "none";
    a.innerHTML = "<iframe id='mainframe' name='mainframe' style='display:none;' > </iframe><form target='mainframe' id='frameform' action='' method='post' enctype='application/x-www-form-urlencoded'><input  value='' id='surveydata' name='surveydata' type='hidden'><input type='submit' value='提交' ></form>";
    document.body.appendChild(a);
    $("surveydata").value = c;
	//console.log(c);
    var d = $("frameform");
    d.action = b + "&iframe=1";
    d.submit();
}
function tiyanReg(a) {
    show_status_tip("保存问卷成功，请注册或者登录以便管理此问卷！", 5000);
    needCheckLeave = false;
    PDF_launch("/register/registers.aspx", 800, 520, function () {
        var b = true;
        if (isTiyan) {
            if (window.confirm("如果您不注册或者登录，您将无法再管理此问卷。\r\n点击“确定”返回继续操作，点击“取消”离开编辑问卷界面")) {
                b = false;
            }
        }
        if (b) {
            goBack();
        }
    });
}
function finishEditing(c) {
   /*  var a = getXmlHttp();
    var d = "curID=" + activityID;
    if (window.isTiKu) {
        d = "tid=" + tikuId;
    }
    var b = "/Handler/designqfinish.ashx?" + d;
    a.open("post", b, false);
    a.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    a.send("edit=true");
	
    if (c) {
        c();
    } */
}
function abort() {
    if (confirm("您真的打算放弃这次更改的内容吗？")) {
        goBack();
        finishEditing();
    }
}
function saveCallBack() {}
function afterSave(d, b, c) {
    havereturn = true;
	
     if (d == "error") {
       alert('很抱歉，由于服务器出现异常，请您刷新页面后再次尝试！')
    } else {
        if (d == "timeout") {
            show_status_tip("您的登录信息超时，请重新登录，谢谢！");
            //PDF_launch("/loginsmall.aspx", 660, 520);
        } else {
            if (d == "badcontent") {
               // alert("很抱歉，问卷内容未通过审核，可能是因为您的问卷包含不当信息。\r\n如果您确认您的问卷内容没有任何问题，请与我们电话联系！");
               // window.location = "/html/contactus.aspx";
            } else {
                
				clearInterval(interval_time);
				var a = d.split("&")[0];
				switch (a) {
				case "true":
					if (c) {
						prevSaveData = c;
					}
					saveSuc(b);
					break;
				case "false":
					alert("请重试！");
					break;
				case "version":
					alert("很抱歉，由于系统版本升级，您本次操作未能成功执行，请您刷新页面或者重启浏览器后再次尝试！\n请注意：页面上的信息可能没有保存，请您先保存重要的数据后再刷新或重启浏览器！");
					break;
				default:
					alert("服务器返回错误，请刷新页面或者重新再试一次！如果还是有错误，请单击返回“我的问卷”选择放弃更改并返回。返回码：" + d);
					break;
				}
                
                interval_time = setInterval(autoSave, 120000);
            }
        }
    } 
}
function goBack() {
    var a = "/wjx/manage/pubq.aspx?activity=" + activityID;
    if (window.isTiKu) {
        a = "/wjx/manage/finishtiku.aspx?tid=" + tikuId;
    }
    windowGotoUrl(a);
}
function saveSuc(a) {
    show_status_tip("保存问卷成功！", 6000);
    if (a == "pub" || a == "pub2") {
        if (isTiyan) {
            tiyanReg(true);
        } else {
            if (a == "pub") {
                goBack();
            } else {
                windowGotoUrl("/wjx/design/designstart.aspx?activity=" + activityID + "&action=1");
            }
        }
    } else {
        if (a == "old") {
            windowGotoUrl("design.aspx?openType=redesign&curid=" + activityID);
        } else {
            if (a == "upgrade") {
                windowGotoUrl("/register/usertype.aspx");
            } else {
                if (a == "preview") {}
            }
        }
    }
}
function doSaveValidate(a) {
    if (!a.createAttr) {
        return;
    }
    if (!a.hasCreatedAttr) {
        a.createOp();
        a.createAttr();
        a.setDataNodeToDesign();
        a.tabAttr.style.display = "none";
    }
    a.validate();
}
function isJumpToValid(b, a) {
    if (b != "" && b != 0 && b != 1) {
        if (toInt(b) <= a.dataNode._topic || toInt(b) > total_question) {
            return false;
        }
    }
    return true;
}
function save_paper_validate(k) {
    var l = document.getElementById("paper_attr_title");
    if (trim(l.value) == "") {
        popHint(l, "请填写问卷标题");
        show("tab3_div");
        l.value = "请输入您的问卷的标题";
        l.select();
        return false;
    }
    var d = true;
    var e;
    for (var h = 0; h < questionHolder.length; h++) {
        var f = questionHolder[h];
        if (f.checkValid && f.checkValid() == false) {
            doSaveValidate(f);
            if (questionHolder[h].checkValid() == false) {
                d = false;
                if (!e) {
                    e = questionHolder[h];
                }
            }
        } else {
            if (f.dataNode._hasjump) {
                if (!f.dataNode._anytimejumpto || f.dataNode._anytimejumpto == "0") {
                    var b = f.dataNode._select;
                    if (!b) {
                        continue;
                    }
                    var m = b.length;
                    for (var g = 1; g < m; g++) {
                        var c = trim(b[g]._item_jump);
                        if (!isJumpToValid(c, f)) {
                            doSaveValidate(f);
                            d = false;
                            if (!e) {
                                e = questionHolder[h];
                            }
                            break;
                        }
                    }
                } else {
                    var c = f.dataNode._anytimejumpto;
                    if (!isJumpToValid(c, f)) {
                        doSaveValidate(f);
                        d = false;
                        if (!e) {
                            e = questionHolder[h];
                        }
                    }
                }
            } else {
                if (f.dataNode._isCeShi) {
                    var b = f.dataNode._select;
                    if (!b) {
                        continue;
                    }
                    var m = b.length;
                    var a = false;
                    for (var g = 1; g < m; g++) {
                        if (b[g]._item_radio) {
                            a = true;
                        }
                    }
                    if (!a) {
                        doSaveValidate(f);
                        d = false;
                        if (!e) {
                            e = questionHolder[h];
                        }
                    }
                }
            }
        }
    }
    if (!d) {
        if (k) {
            if (e.ondblclick) {
                e.ondblclick();
            }
            e.scrollIntoView(false);
            show_status_tip("此题没有通过验证，保存失败！请将鼠标移到红色边框的控件上查看具体错误信息。", 6000);
        } else {
            show_status_tip("第" + e.dataNode._topic + "题没有通过验证，自动保存失败！请您将鼠标移到红色边框的控件上查看具体错误信息。", 6000);
        }
        return false;
    }
    d = true;
    e = null;
    if ($("radioRandomDisplaySubject").checked || $("radioRandomDisplayPageNum").checked) {
        txtRandom_onblur();
        for (var h = 0; h < questionHolder.length; h++) {
            if (questionHolder[h].dataNode._hasjump) {
                if (!e) {
                    e = questionHolder[h];
                }
                d = false;
                questionHolder[h].className += " div_question_error";
            }
        }
    }
    if (!d) {
        show_status_tip("由于您设置了填写问卷时随机调整题目顺序，故题目不能设置跳题逻辑", 4000);
        e.scrollIntoView(false);
        return false;
    }
    return true;
}