if (typeof (Array.prototype.push) != "function") {
    Array.prototype.push = function () {
        for (var a = 0; a < arguments.length; a++) {
            this[this.length] = arguments[a];
        }
        return this.length;
    };
}
String.prototype.format = function () {
    var a = arguments;
    return this.replace(/\{(\d+)\}/g, function (b, c) {
        return a[c];
    });
};
var hrefSave = document.getElementById("hrefSave");
var cur_page = 0;
var jumpPages;
var pageHolder = new Array();
var trapHolder = new Array();
var totalQ = 0;
var completeLoaded = false;
var MaxTopic = 0;
if (displayPrevPage == "none" && (hasJoin == "1" || isSuper)) {
    displayPrevPage = "";
}
var curdiv = null;
var curfilediv = null;
var isUploadingFile = false;
var hasZhenBiePage = false;
var progressArray = new Object();
var questionsObject = new Object();
var joinedTopic = 0;
var randomparm = "";
var hasTouPiao = false;
var useSelfTopic = false;
var isChrome = window.chrome;
// document.oncontextmenu = document.ondragstart = document.onselectstart =
// document.onmouseup = avoidCopy;
var ZheZhaoControl = null;
var verifyCodeErrorTimes = 0;
/*
 * if (!isChrome) { try { document.oncopy = avoidCopy; } catch (ex) {} }
 */

function avoidCopy(b) {
    b = window.event || b;
    var a;
    if (b) {
        if (b.target) {
            a = b.target;
        } else {
            if (b.srcElement) {
                a = b.srcElement;
            }
        } if (a.nodeType == 3) {
            a = a.parentNode;
        }
        if (a.tagName == "INPUT" || a.tagName == "TEXTAREA" || a.tagName == "SELECT") {
            return true;
        }
    }
    if (document.selection && document.selection.empty) {
        document.selection.empty();
    }
    return false;
}
var needCheckLeave = true;
if (allowSaveJoin && isRunning == "true" && guid) {
    window.onunload = function () {
        if (needCheckLeave) {
            if (confirm("您要保存填写的答卷吗？")) {
                submit(2);
                alert("答卷保存成功！");
            }
        }
    };
}
$ = function (a) {
    return document.getElementById(a);
};
$$ = function (a, b) {
    if (b) {
        return b.getElementsByTagName(a);
    } else {
        return document.getElementsByTagName(a);
    }
};

function getTop(b) {
    var a = b.offsetLeft;
    var c = b.offsetTop;
    while (b = b.offsetParent) {
        a += b.offsetLeft;
        c += b.offsetTop;
    }
    return {
        x: a,
        y: c
    };
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

function removeEventSimple(c, a, b) {
    if (c.removeEventListener) {
        c.removeEventListener(a, b, false);
    } else {
        if (c.detachEvent) {
            c.detachEvent("on" + a, b);
        }
    }
}

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
var txtCurCity = null;

function openCityBox(d, c) {
    txtCurCity = d;
    ZheZhaoControl = txtCurCity;
    var a = d.getAttribute("province");
    var b = "";
    if (a) {
        b = "&pv=" + encodeURIComponent(a);
    }
    if (c == 3) {
        PDF_launch("/wjx/design/setcitycounty.aspx?activityid=" + activityId + "&ct=" + c + b, 470, 220);
    } else {
        if (c == 4) {
            ZheZhaoControl = null;
            PDF_launch("/wjx/design/school.aspx?activityid=" + activityId + b, 700, 340);
        } else {
            PDF_launch("/wjx/design/setcity.aspx?activityid=" + activityId + "&ct=" + c, 470, 220);
        }
    }
}

function setCityBox(a) {
    txtCurCity.value = a;
}
var submit_tip = $("submit_tip");
var submit_div = $("submit_div");

function trim(a) {
    return a.replace(/(^\s*)|(\s*$)/g, "");
}

function isInt(a) {
    var b = /^-?[0-9]+$/;
    return b.test(a);
}
var spChars = ["$", "}", "^", "|", "<"];
var spToChars = ["ξ", "｝", "ˆ", "¦", "&lt;"];

function replace_specialChar(c) {
    for (var a = 0; a < spChars.length; a++) {
        var b = new RegExp("(\\" + spChars[a] + ")", "g");
        c = c.replace(b, spToChars[a]);
    }
    return c;
}

function isRadioImage(a) {
    if (!a || a == "0" || a == "1" || a == "101") {
        return false;
    } else {
        return true;
    }
}

function isRadioRate(a) {
    return a != "" && a != "0" && a != "1" && a != "-1";
}
var submit_table = $("submit_table");
var pre_page = $("btnPre");
var next_page = $("btnNext");
var submit_button = $("submit_button");
var imgCode = $("imgCode");
var submit_text = $("txtCode");
var tCode = $(tdCode);
var divMinTime = $("divMinTime");
var spanMinTime = $("spanMinTime");
var divMaxTime = $("divMaxTime");
var spanMaxTime = $("spanMaxTime");
var maxCounter = 0;
var maxTimer = null;

function changeHeight(d) {
    var e = parseInt(d.style.height);
    if (!e) {
        return;
    }
    var c = 18;
    var b = 100;
    var a = d.scrollHeight;
    a = a > c ? a : c;
    a = a > b ? b : a;
    if (a - e >= 10) {
        d.style.height = a + "px";
    }
}

function fcInputboxFocus() {}

function lengthChange(c) {
    var a = c.value.length;
    var b = c.size;
    if (a >= b && b <= 80) {
        c.size = a + 2;
    } else {
        if (b > 80) {}
    }
}

function fcInputboxBlur() {
    if (!this.value) {
        this.value = defaultOtherText;
        this.style.color = "#999999";
    } else {
        this.style.color = "#000000";
        if (langVer != 0) {
            return;
        }
        if (this.tagName == "select") {
            return;
        }
        var e = this.parent;
        var c = e.itemInputs;
        var f = this.value.split(/(,|，)/ig);
        for (var d = 0; d < c.length; d++) {
            var b = c[d].nextSibling;
            if (b && b.nodeType == 3) {
                b = b.nextSibling;
            }
            if (!b) {
                return;
            }
            for (var a = 0; a < f.length; a++) {
                if (trim(f[a].toLowerCase()) == trim(b.innerHTML.toLowerCase())) {
                    alert("提示：您输入的“" + f[a] + "”已经包含在题目选项当中");
                    c[d].checked = true;
                    b.style.color = "red";
                }
            }
        }
    }
}

function isTextBoxEmpty(a) {
    a = trim(a);
    if (a == "" || a == defaultOtherText) {
        return true;
    }
    return false;
}

function refresh_validate() {
    if (imgCode && tCode.style.display != "none") {
        imgCode.src = "/AntiSpamImageGen.aspx?q=" + activityId + "&t=" + (new Date()).valueOf();
    }
}

function enter_clicksub(a) {
    a = a || window.event;
    if (a && a.keyCode == 13) {
        submit(1);
    }
}
var relationHT = new Array();
var relationQs = new Object();
var relationNotDisplayQ = new Object();
var nextPageAlertText = "";
var yucVerify = false;
var hasMaxtime = false;
Init();
var ajax_proc_func = function (a) {
    if (a == 1) {
        yucVerify = true;
        show_status_tip("<span style='color:green;background:white;font-size:16px; padding-left:10px;height:30px;line-height:30px; display:block;width:200px;'>验证码输入正确<img src='http://image.sojump.com/images/Master_Images/WjxVip/complete.gif' alt=''></span>", 3000);
        tCode.style.display = "none";
        if ($("YMADSDivId")) {
            $("YMADSDivId").style.visibility = "hidden";
        }
    } else {
        yucVerify = false;
    }
};
// 初始化函数 这个函数最重要
function Init() {
	
    if (cur_page == 0 && !displayPrevPage && pre_page) {
        pre_page.style.display = "";
        pre_page.disabled = true;
    }
    pageHolder = $$("fieldset", survey);
	
    for (var ab = 0; ab < pageHolder.length; ab++) {
		
        var U = pageHolder[ab].getAttribute("skip") == "true";
        if (U) {
            pageHolder[ab].skipPage = true;
        }
    }
    submit_button.onmouseover = function () {
        this.className = "submitbutton submitbutton_hover";
        if (isPub && $("spanTest").style.visibility != "visible") {
            $("spanTest").style.visibility = "visible";
            show_status_tip("您是发布者，可以进行试填问卷，试填的答卷不会参与结果统计！", 5000);
        }
    };
    submit_button.onclick = function () {
        if (checkDisalbed()) {
            return;
        }
        submit(1);
    };
	
    if (isPub) {
        $("submittest_button").onclick = function () {
            if (!isTest && confirm("试填后的答卷不会参与结果统计，确定试填吗？")) {
                submit(5);
            } else {
                if (isTest) {
                    submit(5);
                }
            }
        };
        if (isTest) {
            submit_button.style.display = "none";
        }
    }
    if (hasJoin == "3") {
        submit_button.onclick = function () {
            if (checkDisalbed()) {
                return;
            }
            if (window.confirm("确定编辑此答卷吗？")) {
                submit(6);
            }
        };
    }
    if (totalPage == 1 && isRunning == "true" && hasJoin != "1") {
        submit_table.style.display = "";
    } else {
        if (isRunning != "true") {
            var n = $("spanNotSubmit");
            if (n && trim(n.innerHTML) != "") {
                if (totalPage == 1 && hasJoin != "1") {
                    submit_table.style.display = "";
                }
                nextPageAlertText = n.innerHTML.replace(/<[^>]*>/g, "");
                submit_button.onclick = function () {
                    if (checkDisalbed()) {
                        return;
                    }
                    alert(nextPageAlertText);
                    n.scrollIntoView();
                };
            } else {
                submit_table.style.display = "none";
            }
        } else {
            submit_table.style.display = "none";
        }
    } if (pre_page) {
        pre_page.onclick = show_pre_page;
    }
    if (next_page) {
        next_page.onclick = show_next_page;
    }
    if (tCode && tCode.style.display != "none" && isRunning == "true") {
        submit_text.value = validate_info_submit_title3;
        addEventSimple(submit_text, "blur", function () {
            if (submit_text.value == "") {
                submit_text.value = validate_info_submit_title3;
            }
        });
        addEventSimple(submit_text, "focus", function () {
            if (submit_text.value == validate_info_submit_title3) {
                submit_text.value = "";
            }
        });
        imgCode.style.display = "none";
        if (langVer != 0) {
            imgCode.alt = "";
        }
        addEventSimple(submit_text, "click", function () {
            var i = $("YMADSDivId");
            var k = false;
            if (needAvoidCrack == 1 && !i) {
                k = true;
            }
            if (needAvoidCrack == 1 && !window.AvoidCrackSetOpen && verifyCodeErrorTimes >= 2) {
                k = true;
            }
            if (imgCode.style.display == "") {
                k = true;
            }
            if (k) {
                needAvoidCrack = 0;
                if ($("YMADSDivId")) {
                    $("YMADSDivId").style.visibility = "hidden";
                }
                submit_text.onkeyup = null;
            }
            verifyCodeErrorTimes++;
            if (!needAvoidCrack && imgCode.style.display == "none") {
                imgCode.onclick = refresh_validate;
                imgCode.onclick();
                imgCode.style.display = "";
                imgCode.title = validate_info_submit_title1;
            }
        });
        submit_text.onkeyup = function () {
            if (needAvoidCrack == 1) {
                Yucmedia_ajax_verify("txtCode", "ajax_proc_func");
            }
        };
    }
    for (var Z = 0; Z < pageHolder.length; Z++) {
		
        var e = $$("div", pageHolder[Z]);
        if (hasJoin) {
            pageHolder[Z].style.display = "";
        }
        var h = new Array();
        var W = 0;
        for (var ab = 0; ab < e.length; ab++) {
            if (e[ab].className.toLowerCase() == "div_question") {
                var d = e[ab].getAttribute("istrap") == "1";
				
                e[ab].onclick = divQuestionClick;
                if (d) {
                    trapHolder.push(e[ab]);
                    initItem(e[ab]);
                    e[ab].pageIndex = Z + 1;
                    e[ab].isTrap = true;
                } else {
                    e[ab].indexInPage = W;
                    h[W] = e[ab];
                    h[W].pageIndex = Z + 1;
                    W++;
                    totalQ++;
                }
            }
        }
        pageHolder[Z].questions = h;
		
    }
	
    set_data_fromServer(qstr);// 这句是干啥用的？ 交互数据
	
    var H = new Array();
	
    for (var o = 0; o < pageHolder.length; o++) {
		
        var ad = pageHolder[o].questions;
		
        for (var ab = 0; ab < ad.length; ab++) {
            var F = ad[ab].dataNode;
			
            var ak = F._type;
            var x = ad[ab].getAttribute("relation");
            if (x && x != "0") {
                var L = x.split(",");
                var E = L[0];
                var b = L[1].split(";");
                for (var K = 0; K < b.length; K++) {
                    var m = E + "," + b[K];
                    if (!relationHT[m]) {
                        relationHT[m] = new Array();
                    }
                    relationHT[m].push(ad[ab]);
                }
                if (!relationQs[E]) {
                    relationQs[E] = new Array();
                }
                relationQs[E].push(ad[ab]);
                relationNotDisplayQ[F._topic] = "1";
            } else {
                if (x == "0") {
                    relationNotDisplayQ[F._topic] = "1";
                }
            } if (ak != "page" && ak != "cut") {
                questionsObject[F._topic] = ad[ab];
            }
			
            if (ak == "radio" || ak == "check") {// 就是这个解析rdaio
			
                if (ak == "radio" && isRadioImage(F._mode)) {
                    initLikertItem(ad[ab]);// 这两个函数也是重要的
                } else {
					
                    initItem(ad[ab]);// 这两个函数也是重要的
					// console.log(ad[ab]);
                } if ((ak == "radio" || ak == "check") && !F.isRate) {
                    var R = $$("img", ad[ab]);
                    for (var aa = 0; aa < R.length; aa++) {
                        R[aa].onclick = function () {
                            var i = this.getAttribute("rel");
                            if (i) {
                                var q = $(i);
                                q.click();
                                var k = q.parent.parent || q.parent;
                                this.style.border = q.checked ? "solid 2px #ff9900" : "solid 2px #eeeeee";
                                if (q.type == "radio") {
                                    if (k && k.prevImage && k.prevImage != this) {
                                        k.prevImage.style.border = "";
                                    }
                                    k.prevImage = this;
                                }
                            }
                        };
                    }
                }
            }
            if (ak == "fileupload") {
                var g = $$("iframe", ad[ab]);
                if (g && g[0]) {
                    ad[ab].uploadFrame = g[0];
                    ad[ab].uploadFrame.allowTransparency = true;
                    var u = document.frames ? document.frames[g[0].id] : document.getElementById(g[0].id).contentWindow;
                    u.curdiv = ad[ab];
                    u._ext = F._ext;
                }
                var e = $$("div", ad[ab]);
                for (var aa = 0; aa < e.length; aa++) {
                    if (e[aa].className.toLowerCase() == "uploadmsg") {
                        ad[ab].uploadmsg = e[aa];
                        e[aa].style.color = "red";
                        break;
                    }
                }
                ad[ab].uploadFinish = function (i, q) {
                    this.uploadmsg.innerHTML = i;
                    this.fileName = q;
                    isUploadingFile = false;
                    this.uploadFrame.style.display = "";
                    var k = document.frames ? document.frames[this.uploadFrame.id] : document.getElementById(this.uploadFrame.id).contentWindow;
                    k.curdiv = this;
                    k._ext = this.dataNode._ext;
                    jump(this, this.uploadFrame);
                };
            }
            if (ak == "matrix") {
                var f = F._mode;
                if (F._hasjump) {
                    if (f && f - 100 < 0) {
                        initLikertItem(ad[ab]);
                    } else {
                        initItem(ad[ab]);
                    }
                }
                var c = ad[ab].getAttribute("DaoZhi");
                var ah = null;
                if (!c) {
                    ah = $$("tr", ad[ab]);
                } else {
                    var Q = $$("tr", ad[ab]);
                    ah = new Array();
                    var r = Q[0].cells.length - 1;
                    for (var v = 0; v < r; v++) {
                        ah[v] = Q[0].cells[v + 1];
                        ah[v].itemInputs = new Array();
                    }
                    for (var v = 0; v < r; v++) {
                        for (var a = 0; a < Q.length; a++) {
                            var G = Q[a].cells[v + 1];
                            G.parent = ah[v];
                            G.onclick = function () {
                                if (curMatrixItem != this.parent) {
                                    var i = this.parent.itemInputs;
                                    if (i) {
                                        for (var k = 0; k < i.length; k++) {
                                            i[k].parentNode.style.background = "#fbd5b5";
                                        }
                                    }
                                    if (curMatrixItem && curMatrixItem.daoZhi) {
                                        i = curMatrixItem.itemInputs;
                                        for (var k = 0; k < i.length; k++) {
                                            i[k].parentNode.style.background = "";
                                        }
                                    }
                                    divMatrixItemClick.call(this.parent);
                                }
                            };
                            ah[v].parent = ad[ab];
                            ah[v].daoZhi = true;
                            var ac = G.getElementsByTagName("input")[0];
                            if (ac) {
                                ah[v].itemInputs.push(ac);
                            }
                        }
                    }
                } if (!c) {
                    for (var aa = 0; aa < ah.length; aa++) {
                        if (f != "303") {
                            if (f && f - 100 < 0) {
                                initLikertItem(ah[aa]);
                            } else {
                                if (!c) {
                                    initItem(ah[aa]);
                                }
                            }
                        } else {
                            var V = $$("select", ah[aa]);
                            if (V.length > 0) {
                                ah[aa].itemSels = V;
                            }
                            if (F._hasjump) {
                                for (var ae = 0; ae < V.length; ae++) {
                                    V[ae].parent = ah[aa];
                                    V[ae].onchange = function () {
                                        var an = this.parent.parent;
                                        var am = an.itemTrs;
                                        var al = false;
                                        for (var k = 0; k < am.length; k++) {
                                            var q = am[k].itemSels;
                                            if (!q) {
                                                continue;
                                            }
                                            for (var i = 0; i < q.length; i++) {
                                                if (q[i].value) {
                                                    al = true;
                                                    break;
                                                }
                                            }
                                            if (al) {
                                                break;
                                            }
                                        }
                                        jumpAny(al, an);
                                    };
                                }
                            }
                        }
                        ah[aa].parent = ad[ab];
                        ah[aa].onclick = divMatrixItemClick;
                    }
                }
                if (f == "301" || f == "102") {
                    var t = ad[ab].getAttribute("minvalue");
                    var z = ad[ab].getAttribute("maxvalue");
                    ad[ab].dataNode._minvalue = t;
                    ad[ab].dataNode._maxvalue = z;
                }
                if (ah.length > 0) {
                    ad[ab].itemTrs = ah;
                }
            }
            if (ak == "sum") {
                initItem(ad[ab]);
                var ah = $$("tr", ad[ab]);
                var s = new Array();
                for (var aa = 0; aa < ah.length; aa++) {
                    var P = $$("input", ah[aa]);
                    if (P.length > 0) {
                        ah[aa].parent = ad[ab];
                        s.push(ah[aa]);
                    }
                }
                var p = ad[ab].itemInputs.length;
                var l = ad[ab].itemInputs;
                for (var aa = 0; aa < p; aa++) {
                    l[aa].onblur = function () {
                        txtChange(this);
                    };
                }
                if (s.length > 0) {
                    ad[ab].itemTrs = s;
                }
                var J = ad[ab].getAttribute("rel");
                ad[ab].relSum = $(J);
            }
            if (ak == "check" && F.isSort) {
                var aj = $$("a", ad[ab]);
                for (aa = 0; aa < aj.length; aa++) {
                    aj[aa].onclick = itemSortClick;
                }
            }
            if (ak == "question") {
                var T = $$("textarea", ad[ab]);
                if (T.length > 0) {
                    T[0].onkeyup = function () {
                        txtChange(this);
                    };
                    if (!T[0].onclick) {
                        T[0].onclick = T[0].onkeyup;
                    }
                    T[0].onblur = T[0].onchange = function () {
                        txtChange(this, 1);
                    };
                    T[0].parent = ad[ab];
                    ad[ab].itemTextarea = T[0];
                }
            } else {
                if (ak == "gapfill") {
                    var T = $$("input", ad[ab]);
                    for (var B = 0; B < T.length; B++) {
                        T[B].onkeyup = function () {
                            txtChange(this);
                        };
                        if (!T[B].onclick) {
                            T[B].onclick = T[B].onkeyup;
                        }
                        T[B].onblur = T[B].onchange = function () {
                            txtChange(this, 1);
                        };
                        T[B].parent = ad[ab];
                    }
                    ad[ab].gapFills = T;
                }
            } if (ak == "radio_down" || (ak == "check" && F._mode)) {
				
                var I = $$("select", ad[ab]);
                if (I.length > 0) {
                    I[0].onchange = itemClick;
                    I[0].parent = ad[ab];
                    ad[ab].itemSel = I[0];
                }
            }
			
            var af = null;
            if (ad[ab].dataNode._hasjump) {
                af = $$("label", ad[ab]);
                for (var S = 0; S < af.length; S++) {
                    if (af[S].id.indexOf("divJump") > -1) {
                        ad[ab].jumpInfo = af[S];
                    }
                }
            }
            var Y = $$("div", ad[ab]);
            var N = 0;
            var M = null;
			
            for (aa = 0; aa < Y.length; aa++) {
                if ((ak == "radio" || ak == "check") && !F.isRate) {
                    var O = Y[aa].getAttribute("rel");
                    if (O) {
                        Y[aa].onclick = function () {
                            var k = this.getAttribute("rel");
                            var i = $(k);
                            i.click();
                        };
                    }
                }
                if (Y[aa].className.toLowerCase() == "div_title_question") {
					
                    ad[ab].divTitle = Y[aa];
                } else {
                    if (Y[aa].className.toLowerCase() == "slider") {
                        if (ak == "matrix" || ak == "sum") {
                            M = Y[aa].parentNode.parentNode;
                            N++;
                        } else {
                            if (ak == "slider") {
                                M = ad[ab];
                            }
                        }
                        M.divSlider = Y[aa];
                        Y[aa].parent = M;
                        var t = Y[aa].getAttribute("minvalue");
                        var z = Y[aa].getAttribute("maxvalue");
                        ad[ab].dataNode._minvalue = t;
                        ad[ab].dataNode._maxvalue = z;
                        var X;
                        if (ak == "sum") {
                            X = M.getElementsByTagName("input")[0];
                        } else {
                            var ai = Y[aa].getAttribute("rel");
                            X = $(ai);
                        }
                        var ag = new neverModules.modules.slider({
                            targetId: Y[aa].id,
                            sliderCss: "imageSlider1",
                            barCss: "imageBar1",
                            min: parseInt(t),
                            max: parseInt(z),
                            sliderValue: X,
                            hints: slider_hint,
                            change: itemClick
                        });
                        ag.create();
                        M.sliderImage = ag;
                        var A = Y[aa].getAttribute("defvalue");
                        if (A && isInt(A)) {
                            ag.setValue(parseInt(A));
                            M.divSlider.value = parseInt(A);
                            if (ak == "sum") {
                                if (hasJoin && A) {
                                    if (ad[ab].sumLeft == undefined) {
                                        ad[ab].sumLeft = F._total - parseInt(A);
                                    } else {
                                        ad[ab].sumLeft = ad[ab].sumLeft - parseInt(A);
                                    }
                                } else {
                                    ad[ab].sumLeft = 0;
                                }
                            }
                        }
                        if (hasJoin == "1") {
                            ag._slider.onclick = function () {};
                            ag._initMoveSlider = function () {};
                        }
                    }
                }
            }
            if (ak == "matrix") {
                var w = new Array();
                var ah = ad[ab].itemTrs;
                for (var aa = 0; aa < ah.length; aa++) {
                    var y = ah[aa].itemInputs || ah[aa].itemLis || ah[aa].divSlider || ah[aa].itemSels;
                    if (y) {
                        w.push(ah[aa]);
                    }
                }
                if (w.length > 0) {
                    ad[ab].itemTrs = w;
                }
            }
            if (F && F._hasjump) {
                cur_page = o;
                if (hasJoin) {
                    jumpJoin(ad[ab], o);
                } else {
                    clearAllOption(ad[ab]);
                }
                cur_page = 0;
            }
            if (F._referedTopics) {
                H.push(ad[ab]);
            }
            if (hasJoin && window.cancelInputClick) {
                cancelInputClick(ad[ab]);
            }
        }
        if (o > 0 && hasJoin) {
            pageHolder[o].style.display = "none";
        }
    }
    completeLoaded = true;
	
    for (var ab = 0; ab < H.length; ab++) {
        var W = H[ab];
		
        createItem(W);
    }
    for (var o = 0; o < pageHolder.length; o++) {
        var ad = pageHolder[o].questions;
        for (var ab = 0; ab < ad.length; ab++) {
            var F = ad[ab].dataNode;
            var D = F._topic;
            if (relationQs[D]) {
                if (hasJoin) {
                    relationJoin(ad[ab]);
                } else {
                    clearAllOption(ad[ab]);
                }
            }
        }
    }
    if (lastSavePage > 0 && lastSavePage < totalPage) {
        pageHolder[0].style.display = "none";
        cur_page = lastSavePage - 1;
        show_next_page(true);
    }
    if (lastSaveQ >= 1) {
        var C = $("div" + lastSaveQ);
        if (C) {
            C.scrollIntoView();
            C.onclick();
            joinedTopic = lastSaveQ;
            for (var ab = 1; ab <= lastSaveQ; ab++) {
                progressArray[ab + ""] = true;
            }
            showProgressBar();
        }
    }
    if (totalQ == 0) {
        submit_table.style.display = "none";
    }
    processMinMax();
    showProgressBar();
}
var prevPostion;
var resizedMax;

function processMinMax() {
    if (maxTimer) {
        clearInterval(maxTimer);
    }
    if (isRunning == "true") {
        var c = pageHolder[cur_page]._maxtime;
        if (c) {
            var e = c > 10 ? 10 : 1;
            maxCounter = c;
            divMaxTime.style.position = "absolute";
            addEventSimple(window, "scroll", mmMaxTime);
            addEventSimple(window, "resize", resizeMaxTime);
            mmMaxTime();
            hasMaxtime = true;
            divMaxTime.style.display = "";
            spanMaxTime.innerHTML = c;
            maxTimer = setInterval(function () {
                maxCounter--;
                spanMaxTime.innerHTML = maxCounter;
                if (maxCounter <= 0) {
                    clearInterval(maxTimer);
                    divMaxTime.style.display = "none";
                    pageHolder[cur_page].hasExceedTime = true;
                    if (cur_page < totalPage - 1) {
                        show_next_page();
                    } else {
                        if (tCode.style.display == "none") {
                            submit(1);
                        }
                    }
                }
            }, 1000);
        }
        var b = pageHolder[cur_page]._mintime;
        var d = !IsSampleService || (IsSampleService && promoteSource == "t") || window.pubNeedApply;
        if (!d) {
            b = 0;
        }
        if (b) {
            if (!isSuper) {
                if (next_page) {
                    next_page.disabled = true;
                }
                submit_button.disabled = true;
            }
            divMinTime.style.display = "";
            addEventSimple(window, "resize", resizeMinTime);
            resizeMinTime();
            spanMinTime.innerHTML = b;
            var a = b;
            var f = setInterval(function () {
                a--;
                spanMinTime.innerHTML = a;
                if (a <= 0) {
                    clearInterval(f);
                    if (next_page) {
                        next_page.disabled = false;
                    }
                    submit_button.disabled = false;
                    divMinTime.style.display = "none";
                }
            }, 1000);
        }
    }
}

function resizeMaxTime() {
    resizedMax = true;
    mmMaxTime();
}

function mmMaxTime() {
    var b = document.documentElement.scrollTop || document.body.scrollTop;
    var a = getTop(survey);
    var d = a.x - 54;
    var c = b + a.y - 40;
    if (d <= 0) {
        d = qwidth - 10;
        c = b + a.y + 10;
    }
    divMaxTime.style.top = c + "px";
    divMaxTime.style.left = d + "px";
}

function resizeMinTime() {
    var b;
    b = submit_table.style.display == "none" ? next_page : submit_table.lastChild;
    var a = getTop(b);
    divMinTime.style.left = (a.x + b.offsetWidth + 10) + "px";
    divMinTime.style.top = a.y + 8 + "px";
}

function initItem(e) {
	
    var c = $$("input", e);
    if (c.length == 0) {
        c = $$("textarea", e);
    }
    for (var d = 0; d < c.length; d++) {
        c[d].parent = e;
        if (!c[d].onclick) {
            c[d].onclick = itemClick;
        }
        if (c[d].tagName == "TEXTAREA") {
            c[d].onchange = c[d].onblur = itemClick;
        }
        var b = c[d].getAttribute("rel");
        if (b) {
            var a = null;
            if (b == "psibling") {
                a = c[d].previousSibling;
                if (a.nodeType != 1) {
                    a = a.previousSibling;
                }
                c[d].onclick = itemClick;
            } else {
                a = $(b);
            }
            a.itemText = c[d];
            c[d].choiceRel = a;
            if (e.dataNode && !e.dataNode.isSort) {
                c[d].onblur = fcInputboxBlur;
            }
            if (e.dataNode && e.dataNode._referedTopics) {
                c[d].onchange = itemClick;
            }
            if (!c[d].value) {
                c[d].value = defaultOtherText;
            }
            c[d].style.color = "#999999";
            var f = c[d].getAttribute("req");
            if (f == "true") {
                a.req = true;
            } else {
                a.req = false;
            }
        }
    }
    if (c.length > 0) {
        e.itemInputs = c;
    }
}

function initLikertItem(b) {
    var e = $$("li", b);
    var a = new Array();
    var f = false;
    var d;
    for (j = 0; j < e.length; j++) {
        var c = e[j].className.toLowerCase();
        if (e[j].className && (c.indexOf("off") > -1 || c.indexOf("on") > -1)) {
            e[j].onclick = itemLiClick;
            e[j].onmouseover = itemMouseOver;
            e[j].onmouseout = itemMouseOut;
            e[j].parent = b;
            a.push(e[j]);
            if (c.indexOf("on") > -1) {
                d = e[j];
            } else {
                if (c.indexOf("off") > -1 && d) {
                    d.parent.holder = d.value;
                }
            }
        }
    }
    if (e.length > 0) {
        if (d) {
            d.parent.holder = d.value;
        }
        b.itemLis = a;
    }
}

function createItem(k) {
	
    var s = k.dataNode;
    var r = s._referedTopics.split(",");
    var t = new Array();
    for (var i = 0; i < k.itemInputs.length; i++) {
        if (k.itemInputs[i].checked) {
            t.push(k.itemInputs[i]);
        }
    }
    for (var i = 0; i < r.length; i++) {
        var l = r[i];
        var n = questionsObject[l];
        if (!n) {
            continue;
        }
        var a = false;
        var c = document.getElementById("divRef" + l);
        var f = 0;
        switch (n.dataNode._type) {
        case "matrix":
        case "sum":
            var d = n.itemTrs[0].itemInputs || n.itemTrs[0].itemLis || n.itemTrs[0].divSlider || n.itemTrs[0].itemSels;
            if (!d) {
                f = 1;
            }
            for (var p = 0; p < k.itemInputs.length; p++) {
                var e = k.itemInputs[p];
                if (!e.value || e.type != "checkbox") {
                    continue;
                }
                var o = parseInt(e.value) - 1 + f;
                if (!n.itemTrs[o]) {
                    break;
                }
                n.itemTrs[o].style.display = e.checked ? "" : "none";
                if (e.checked) {
                    a = true;
                    if (e.itemText) {
                        var u = e.itemText.value;
                        var h = n.itemTrs[o].getElementsByTagName("th")[0];
                        if (h) {
                            if (!h.span) {
                                h.span = document.createElement("span");
                                h.appendChild(h.span);
                            }
                            if (u && u != defaultOtherText) {
                                h.span.innerHTML = "[<span style='color:red;'>" + u + "</span>]";
                            } else {
                                h.span.innerHTML = "";
                            }
                        }
                    }
                }
                if (hasJoin && n.itemTrs[o].divSlider) {
                    var b = n.itemTrs[o].divSlider.getAttribute("defvalue");
                    if (b && isInt(b)) {
                        n.itemTrs[o].sliderImage.setValue(parseInt(b));
                    }
                }
            }
            if (f == 1) {
                n.itemTrs[0].style.display = a ? "" : "none";
            }
            c.style.display = a ? "none" : "";
            n.displayContent = a;
            break;
        case "radio":
        case "check":
            var g = n.itemInputs;
            for (var p = 0; p < k.itemInputs.length; p++) {
                var e = k.itemInputs[p];
                if (!e.value || e.type != "checkbox") {
                    f++;
                    continue;
                }
                var o = parseInt(e.value) - 1 + f;
                if (g[o] && g[o].parentNode) {
                    g[o].parentNode.style.display = e.checked ? "" : "none";
                }
                if (e.checked) {
                    a = true;
                }
            }
            c.style.display = a ? "none" : "";
            n.displayContent = a;
            break;
        }
    }
}
var curMatrixItem = null;

function divMatrixItemClick() {
    if (curMatrixItem == this) {
        return;
    }
    if (curMatrixItem != null) {
        curMatrixItem.style.background = curMatrixItem.prevBackColor || "";
        if (curMatrixItem.daoZhi) {
            itemInputs = curMatrixItem.itemInputs;
            for (var a = 0; a < itemInputs.length; a++) {
                itemInputs[a].parentNode.style.background = "";
            }
        }
    }
    curMatrixItem = this;
    this.prevBackColor = this.style.backgroundColor ? "#EFF6FB" : "";
    if (this.itemInputs) {
        this.style.background = "#fbd5b5";
    }
}

function divQuestionClick() {
    if (curdiv == this) {
        return;
    }
    if (curdiv != null) {
        curdiv.style.background = "";
    }
    if (curdiv && curdiv.uploadFrame) {
        curdiv.uploadFrame.style.backgroundColor = "";
    }
    hasSaveChanged = true;
    showLeftBar();
    curdiv = this;
    if (this.uploadFrame) {
        this.uploadFrame.style.backgroundColor = "#dff7ff";
    }
    if (curMatrixItem != null && curMatrixItem.parent != curdiv) {
        curMatrixItem.style.background = curMatrixItem.prevBackColor || "";
    }
    this.style.background = "#dff7ff";
    if (curMatrixItem != null && curMatrixItem.parent == curdiv) {
        this.style.background = "";
    }
    if (this.removeError) {
        this.removeError();
    }
    if (!completeLoaded) {
        this.style.background = "";
        curdiv = null;
    }
    if (this.itemTextarea && curdiv.parentNode && curdiv.parentNode.style.display != "none") {
        this.itemTextarea.focus();
    }
}

function showLeftBar() {
    if (window.divLeftBar && !hasDisplayed) {
        if (divProgressImg) {
            divProgressImg.style.visibility = "visible";
            $("loadprogress").style.visibility = "visible";
        }
        if (divSave) {
            divSave.parentNode.style.visibility = "visible";
            divSave.parentNode.style.marginTop = "5px";
        }
        hasDisplayed = true;
        divLeftBar.style.background = "#ffffff";
    }
}
var loadcss = null;
var loadprogress = null;

function updateProgressBar(b) {
    var a = b._topic;
    if (a > MaxTopic) {
        MaxTopic = a;
    }
    if (!progressArray[a]) {
        joinedTopic++;
        progressArray[a] = true;
        showProgressBar();
    }
}

function showProgressBar() {
    if (window.divProgressImg) {
        if (!loadcss) {
            loadcss = $("loadcss");
        }
        if (!loadprogress) {
            loadprogress = $("loadprogress");
        }
        var c = totalQ;
        var d = joinedTopic;
        if (progressBarType == 2) {
            c = totalPage;
            d = cur_page + 1;
        }
        var b = parseFloat(d) / c * 100;
        b = b || 0;
        var a = b + "%";
        loadcss.style.height = a;
        if (progressBarType == 1) {
            loadprogress.innerHTML = "&nbsp;&nbsp;" + b.toFixed(0) + "%";
        } else {
            loadprogress.innerHTML = "&nbsp;" + d + "/" + c + page_info;
        } if (hrefSave) {
            if (spanSave) {
                clearInterval(saveInterval);
            }
        }
    }
}

function itemSortClick() {
    var b = this.getAttribute("rel");
    if (b) {
        var e = $(b);
        var c = e.selectedIndex;
        var a = e.options.length;
        if (this.name == "up" && c > 0) {
            var d = e.options[c];
            var f = e.options[c - 1];
            e.insertBefore(d, f);
        } else {
            if (this.name == "first" && c > 0) {
                var d = e.options[c];
                var f = e.options[0];
                e.insertBefore(d, f);
            } else {
                if (this.name == "down" && c >= 0 && c < a - 1) {
                    var d = e.options[c];
                    var g = e.options[c + 1];
                    e.insertBefore(g, d);
                } else {
                    if (this.name == "last" && c >= 0 && c < a - 1) {
                        var d = e.options[c];
                        var g = e.options[a - 1];
                        e.insertBefore(d, g);
                        e.insertBefore(g, d);
                    }
                }
            }
        }
    }
}

function itemClick() {
    var h = this.parent.parent || this.parent;
    if (h.isTrap) {
        return;
    }
    var o = h.dataNode;
    updateProgressBar(o);
    if (this.itemText && this.itemText.onclick) {
        if (this.checked) {
            this.itemText.onclick();
        } else {
            if (this.itemText.onblur) {
                this.itemText.onblur();
            }
        }
    }
    if (this.type == "checkbox") {
        checkHuChi(h, this);
        var p = this.getAttribute("rel");
        if (p) {
            var d = $(p);
            var r = d.options;
            if (this.checked) {
                var c = this.nextSibling.nodeValue || this.nextSibling.innerHTML;
                if (!c && this.nextSibling.nextSibling) {
                    c = this.nextSibling.nextSibling.innerHTML;
                }
                var f = false;
                for (var e = 0; e < r.length; e++) {
                    if (r[e].value == this.value) {
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    d.options[r.length] = new Option(c, this.value);
                }
            } else {
                for (var e = 0; e < r.length; e++) {
                    if (r[e].value == this.value) {
                        r[e] = null;
                    }
                }
            }
        }
        if (o._referedTopics) {
            createItem(h);
        }
        displayRelationRaidoCheck(h, o);
        if (langVer == 0 && (o._maxValue > 0 || o._minValue > 0)) {
            var r = h.itemInputs;
            var g = 0;
            for (var b = 0; b < r.length; b++) {
                if (r[b].checked) {
                    g++;
                }
            }
            if (!h.divChecktip) {
                h.divChecktip = document.createElement("div");
                h.appendChild(h.divChecktip);
                h.divChecktip.style.color = "666";
            }
            var a = "&nbsp;&nbsp;&nbsp;您已经选择了" + g + "项";
            if (o._maxValue > 0 && g > o._maxValue) {
                if (!o._referedTopics && !o._hasjump && !relationQs[o._topic]) {
                    alert("此题最多只能选择" + o._maxValue + "项");
                    this.checked = false;
                    g--;
                    a = "&nbsp;&nbsp;&nbsp;您已经选择了" + g + "项";
                    if (o.isSort && p && $(p)) {
                        for (var e = 0; e < $(p).options.length; e++) {
                            if ($(p).options[e].value == this.value) {
                                $(p).options[e] = null;
                            }
                        }
                    }
                } else {
                    a += ",<span style='color:red;'>多选择了" + (g - o._maxValue) + "项</span>";
                }
            } else {
                if (o._minValue > 0 && g < o._minValue) {
                    a += ",<span style='color:red;'>少选择了" + (o._minValue - g) + "项</span>";
                }
            }
            h.divChecktip.innerHTML = a;
        }
        jump(h, this);
    } else {
        if (this.type == "radio" || o._type == "slider" || (o._type == "matrix" && o._mode != "201")) {
            if (!o._requir && !h.hasClearHref) {
                addClearHref(h);
            }
            displayRelationRaidoCheck(h, o);
            jump(h, this);
            if (o._type == "matrix" && (o._mode == "102" || o._mode == "103") && this.type == "text") {
                processTextR(this, h, o);
            }
        } else {
            if (o._type == "matrix" && o._mode == "201") {
                var m = h.itemTrs;
                var k = 0;
                var q;
                for (var e = 0; e < m.length; e++) {
                    k = validateMatrix(o, m[e], m[e].itemInputs[0]);
                    if (k && !q) {
                        q = m[e].itemInputs[0];
                        break;
                    }
                }
                if (h.removeError) {
                    h.removeError();
                }
                if (q) {
                    h.errorControl = q;
                    validate_ok = writeError(h, verifyMsg, 3000);
                }
                var n = false;
                for (var e = 0; e < m.length; e++) {
                    var l = m[e].itemInputs[0];
                    if (trim(l.value) != "") {
                        n = true;
                        break;
                    }
                }
                jumpAny(n, h);
            } else {
                if (o._type == "sum") {
                    if (this.parent.sliderImage) {
                        sumClick(h, this.parent.sliderImage.sliderValue);
                    } else {
                        sumClick(h, this);
                    }
                } else {
                    if (this.type == "text") {
                        processTextR(this, h, o);
                    } else {
                        if (this.nodeName == "SELECT") {
                            h.focus();
                            jump(h, this);
                            displayRelationDropDown(h, o);
                        }
                    }
                }
            }
        }
    }
}

function processTextR(c, a, b) {
    if (c.choiceRel) {
        if (c.value == defaultOtherText) {
            c.value = "";
        }
        c.choiceRel.checked = true;
        c.style.color = "#000000";
        c.style.background = "";
        if (b._referedTopics) {
            createItem(a);
        }
        displayRelationRaidoCheck(a, b);
        jump(a, c.choiceRel);
    }
}

function checkHuChi(c, f) {
    if (!f.checked) {
        return;
    }
    var e = c.dataNode;
    if (!e.hasHuChi) {
        return;
    }
    var a = c.itemInputs;
    var d = e._select[f.value - 1]._item_huchi;
    for (var b = 0; b < a.length; b++) {
        if (a[b].type != "checkbox") {
            continue;
        }
        if (a[b] == f) {
            continue;
        }
        if (!a[b].checked) {
            continue;
        }
        if (d) {
            a[b].click();
            a[b].checked = false;
        } else {
            var g = e._select[a[b].value - 1]._item_huchi;
            if (g) {
                a[b].click();
                a[b].checked = false;
            }
        }
    }
}

function relationJoin(b) {
    if (b.style.display != "none") {
        var c = b.dataNode;
        var a = c._type;
        if (a == "radio" || a == "check") {
            displayRelationRaidoCheck(b, c);
        } else {
            if (a == "radio_down") {
                displayRelationDropDown(b, c);
            }
        }
    }
}

function displayRelationRaidoCheck(h, k) {
    var d = k._topic;
    if (!relationQs[d]) {
        return;
    }
    h.hasDisplayByRelation = new Object();
    var i = -1;
    if (h.itemLis) {
        var e = h.itemLis;
        for (var f = 0; f < e.length; f++) {
            if (e[f].className.indexOf("on") > -1) {
                i = f + 1;
            }
        }
        for (var f = 0; f < e.length; f++) {
            var c = false;
            var b = e[f].value;
            var g = d + "," + b;
            if (i > -1 && b == i) {
                c = true;
            }
            displayByRelation(h, g, c);
        }
    } else {
        var e = h.itemInputs;
        for (var f = 0; f < e.length; f++) {
            var c = false;
            var b = e[f].value;
            var g = d + "," + b;
            if (e[f].checked) {
                c = true;
            }
            displayByRelation(h, g, c);
            var a = d + ",-" + b;
            if (relationHT[a]) {
                displayByRelationNotSelect(h, a, c);
            }
        }
    }
}

function displayRelationDropDown(f, h) {
    var c = h._topic;
    if (!relationQs[c]) {
        return;
    }
    var i = f.itemSel;
    var g = f.itemSel.value;
    f.hasDisplayByRelation = new Object();
    for (var d = 0; d < i.length; d++) {
        var b = false;
        var a = i[d].value;
        var e = c + "," + a;
        if (a == g) {
            b = true;
        }
        displayByRelation(f, e, b);
    }
}

function displayByRelation(c, f, b) {
    var d = relationHT[f];
    if (!d) {
        return;
    }
    for (var a = 0; a < d.length; a++) {
        if (c.hasDisplayByRelation[d[a].dataNode._topic]) {
            continue;
        }
        if (!b && d[a].style.display != "none") {
            loopHideRelation(d[a]);
        } else {
            if (b) {
                d[a].style.display = "";
                var e = d[a].dataNode._topic;
                c.hasDisplayByRelation[e] = "1";
                if (relationNotDisplayQ[e]) {
                    relationNotDisplayQ[e] = "";
                }
            }
        }
    }
}

function displayByRelationNotSelect(c, f, b) {
    var d = relationHT[f];
    if (!d) {
        return;
    }
    for (var a = 0; a < d.length; a++) {
        if (c.hasDisplayByRelation[d[a].dataNode._topic]) {
            continue;
        }
        if (b && d[a].style.display != "none") {
            loopHideRelation(d[a]);
        } else {
            if (!b) {
                d[a].style.display = "";
                var e = d[a].dataNode._topic;
                c.hasDisplayByRelation[e] = "1";
                if (relationNotDisplayQ[e]) {
                    relationNotDisplayQ[e] = "";
                }
            }
        }
    }
}

function loopHideRelation(a) {
    var c = a.dataNode._topic;
    var b = relationQs[c];
    if (b) {
        for (var d = 0; d < b.length; d++) {
            loopHideRelation(b[d], false);
        }
    }
    clearAllOption(a);
    a.style.display = "none";
    if (relationNotDisplayQ[c] == "") {
        relationNotDisplayQ[c] = "1";
    }
}

function sumClick(k, e, f) {
    var r = k.getElementsByTagName("input");
    var s = k.dataNode;
    updateProgressBar(s);
    if (r) {
        var h = r.length;
        var n = s._total;
        var c = n;
        var q = 0;
        var m;
        var o;
        var b = e.value;
        if (parseInt(b) < 0) {
            e.value = "";
        }
        for (var d = 0; d < h; d++) {
            var p = r[d].value;
            var l = k.itemTrs[d];
            var g = l.sliderImage;
            if (p && trim(p)) {
                if (isInt(p)) {
                    c = c - parseInt(p);
                    if (g._value == undefined || g._value == 0) {
                        g.setValue(parseInt(b), true);
                    } else {
                        if (f && r[d] == e) {
                            g.setValue(parseInt(b), true);
                        }
                    }
                } else {
                    r[d].value = "";
                    m = r[d];
                    o = g;
                    q++;
                }
            } else {
                if (l.style.display == "none") {} else {
                    m = r[d];
                    o = g;
                    q++;
                }
            }
        }
        if (q == 1) {
            if (c > 0) {
                o.setValue(c, true);
                m.value = c;
                c = 0;
            }
        }
        k.sumLeft = c;
        var a = "";
        if (q == 0 && c != 0) {
            a = "<br/><span style='color:red;'>" + sum_warn + "</span>";
        }
        if (k.relSum) {
            k.relSum.innerHTML = sum_total + "<b>" + n + "</b>" + sum_left + "<span style='color:red;font-bold:true;'>" + c + "</span>" + a;
        }
        jump(k, this);
    }
}

function jump(b, e) {
    var c = b.dataNode;
    var a = c._anytimejumpto;
    var d = c._hasjump;
    if (d) {
        if (a > 0) {
            jumpAnyChoice(b);
        } else {
            if (a == 0 && c._type != "radio" && c._type != "radio_down") {
                jumpAnyChoice(b);
            } else {
                jumpByChoice(b, e);
            }
        }
    }
}

function jumpAnyChoice(d, e) {
    var a = d.itemInputs || d.itemLis || d.itemTrs || d.gapFills;
    var c = false;
    if (a) {
        for (var b = 0; b < a.length; b++) {
            if (a[b].checked) {
                c = true;
            } else {
                if (a[b].className.indexOf("on") > -1) {
                    c = true;
                } else {
                    if (a[b].divSlider && a[b].divSlider.value) {
                        c = true;
                    } else {
                        if (a[b].tagName == "TEXTAREA" && trim(a[b].value) != "") {
                            c = true;
                        } else {
                            if (a[b].type == "text" && trim(a[b].value) != "") {
                                c = true;
                            } else {
                                if (a[b].itemSels) {
                                    for (var f = 0; f < a[b].itemSels.length; f++) {
                                        if (a[b].itemSels[f]) {
                                            c = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } if (c) {
                break;
            }
        }
    } else {
        if (d.itemSel) {
            if (d.itemSel.selectedIndex > 0) {
                c = true;
            } else {
                c = false;
            }
        } else {
            if (d.divSlider) {
                c = (d.divSlider.value != undefined && d.divSlider.value != null) ? true : false;
            } else {
                if (d.itemTextarea) {
                    c = trim(d.itemTextarea.value) != "";
                } else {
                    if (d.uploadFrame) {
                        c = d.fileName ? true : false;
                    }
                }
            }
        }
    }
    jumpAny(c, d, e);
}

function jumpByChoice(b, d) {
    var c = b.dataNode;
    if (d.value == "-2") {
        processJ(b.indexInPage - 0, 0, b.jumpInfo);
    } else {
        if (d.value == "-1" || d.value == "") {
            processJ(b.indexInPage - 0, 0, b.jumpInfo);
        } else {
            if ((c._type == "radio" || c._type == "radio_down") && parseInt(d.value) == d.value) {
                var a = c._select[d.value - 1]._item_jump;
                processJ(b.indexInPage - 0, a - 0, b.jumpInfo);
            }
        }
    }
}

function txtChange(c, l) {
    var e = c.parent.parent || c.parent;
    updateProgressBar(e.dataNode);
    hasSaveChanged = true;
    if (e.removeError) {
        e.removeError();
    }
    if ((e.dataNode._needOnly || (e.dataNode._hasList && e.dataNode._listId != -1)) && trim(c.value) != "" && l) {
        var a = getXmlHttp();
        a.onreadystatechange = function () {
            if (a.readyState == 4) {
                if (a.status == 200) {
                    if (unescape(a.responseText) == "false1") {
                        c.isOnly = false;
                        writeError(e, validate_only, 3000);
                    } else {
                        if (unescape(a.responseText) == "false2") {
                            c.isInList = false;
                            c.isOnly = true;
                            writeError(e, validate_list, 3000);
                        } else {
                            c.isInList = true;
                            c.isOnly = true;
                        }
                    }
                } else {
                    c.isOnly = true;
                    c.isInList = true;
                }
            }
        };
        var n = e.dataNode._needOnly;
        var i = e.dataNode._hasList && e.dataNode._listId != -1;
        var d = e.dataNode._listId;
        a.open("get", "/Handler/AnswerOnlyHandler.ashx?q=" + activityId + "&at=" + escape(c.value) + "&qI=" + e.dataNode._topic + "&o=" + n + "&l=" + i + "&lid=" + d + "&t=" + (new Date()).valueOf());
        a.send(null);
    }
    var h = e.dataNode._verify;
    if (e.dataNode._type == "matrix" && e.dataNode._mode == "303") {
        h = "数字";
    }
    if (c.value != "" && h && h != "0") {
        if (e.removeError) {
            e.removeError();
        }
        var k = e.dataNode;
        var m = e.getAttribute("issample");
        var g = true;
        if (jiFen > 0 && m && promoteSource != "t") {
            g = false;
        }
        if (g) {
            var b = verifyMinMax(c, h, k._minword, k._maxword);
            if (b != "") {
                validate_ok = writeError(e, b, 3000);
            }
            b = verifydata(c, h, e.dataNode);
            if (b != "") {
                validate_ok = writeError(e, b, 3000);
            }
        }
    }
    if (e.dataNode._type == "gapfill") {
        var f = 0;
        f = validateMatrix(e.dataNode, c, c);
        if (f) {
            e.errorControl = c;
            writeError(e, verifyMsg, 3000);
        }
    }
    if (e.dataNode._type == "sum") {
        sumClick(e, c, 1);
    } else {
        jumpAny(trim(c.value) != "", e);
    }
}

function jumpAny(a, b, d) {
    var c = b.dataNode;
    if (c._hasjump) {
        if (a) {
            processJ(b.indexInPage - 0, c._anytimejumpto - 0, b.jumpInfo, d);
        } else {
            processJ(b.indexInPage - 0, 0, b.jumpInfo, d);
            //b.jumpInfo.innerHTML = "<font color='#666666'>*" + jump_info + "</font>";
        }
    }
}

function processJ(o, c, l, d) {
    var a = o + 1;
    var b = cur_page;
    for (var g = cur_page; g < pageHolder.length; g++) {
        var m = pageHolder[g].questions;
        if (c == 1) {
            b = g;
        }
        for (var f = a; f < m.length; f++) {
            var h = m[f].dataNode._topic;
            if (h == c || c == 1) {
                b = g;
            }
            if (h < c || c == 1) {
                m[f].style.display = "none";
                if (!progressArray[h]) {
                    joinedTopic++;
                    progressArray[h] = "jump";
                }
            } else {
                if (relationNotDisplayQ[h]) {
                    var e = 1;
                } else {
                    m[f].style.display = "";
                } if (progressArray[h] == "jump") {
                    joinedTopic--;
                    progressArray[h] = false;
                }
                if (m[f].dataNode._hasjump && !d) {
                    clearAllOption(m[f]);
                    if (m[f].jumpInfo) {
                        m[f].jumpInfo.innerHTML = "<font color='#0066ff'>*" + jump_info + "</font>";
                    }
                }
            }
        }
        a = 0;
    }
    //l.innerHTML = "";
    var n = b - cur_page - 1;
    jumpPages = new Array();
    for (var k = 0; k < n; k++) {
        jumpPages[k] = cur_page + k + 1;
    }
    showProgressBar();
}
// 确认这个函数没有用
function addClearHref(b) {
    var c = b.dataNode;
    var a = document.createElement("a");
    a.title = validate_info_submit_title2;
    a.className = "link-999";
    a.style.marginLeft = "25px";
    a.innerHTML = "[" + type_radio_clear + "]";
    a.href = "javascript:void(0);";
    b.hasClearHref = true;
    b.divTitle.appendChild(a);
	
    b.clearHref = a;
    a.onclick = function () {
        clearAllOption(b);
        jumpAny(false, b);
    };
}

function clearAllOption(d) {
    var e = d.itemSel;
    if (e) {
        e.selectedIndex = 0;
    }
    if (d.divSlider && d.divSlider.value != undefined) {
        d.sliderImage.setValue(d.dataNode._minvalue, true);
        d.divSlider.value = undefined;
    }
    var a = d.itemInputs || d.itemLis || d.itemTrs;
    if (!a) {
        return;
    }
    d.hasClearHref = false;
    if (d.clearHref) {
        d.clearHref.parentNode.removeChild(d.clearHref);
        d.clearHref = null;
    }
    for (var c = 0; c < a.length; c++) {
        if (a[c].checked) {
            a[c].checked = false;
        } else {
            if (a[c].className.toLowerCase().indexOf("on") > -1) {
                a[c].className = "off" + d.dataNode._mode;
            } else {
                if (a[c].parent && a[c].parent.holder) {
                    a[c].parent.holder = 0;
                } else {
                    if (a[c].divSlider && a[c].divSlider.value) {
                        a[c].sliderImage.setValue(d.dataNode._minvalue, true);
                        a[c].divSlider.value = undefined;
                    } else {
                        var f = a[c].itemInputs || a[c].itemLis;
                        if (f) {
                            for (var b = 0; b < f.length; b++) {
                                if (f[b].checked) {
                                    f[b].checked = false;
                                } else {
                                    if (f[b].className.toLowerCase().indexOf("on") > -1) {
                                        f[b].className = "off" + d.dataNode._mode;
                                    } else {
                                        if (f[b].parent && f[b].parent.holder) {
                                            f[b].parent.holder = 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (d.holder) {
        d.holder = 0;
    }
}

function itemMouseOver() {
    var c = this.parent.parent || this.parent;
    if (c.dataNode.isRate) {
        var a = this.parent.itemLis.length;
        var d = "on";
        for (var b = 0; b < a; b++) {
            d = b < this.value ? "on" : "off";
            this.parent.itemLis[b].className = d + c.dataNode._mode;
        }
    }
}

function itemMouseOut() {
    var d = this.parent.parent || this.parent;
    if (d.dataNode.isRate) {
        var a = this.parent.itemLis.length;
        var e = "on";
        var c = this.parent.holder || 0;
        for (var b = 0; b < a; b++) {
            e = b < c ? "on" : "off";
            this.parent.itemLis[b].className = e + d.dataNode._mode;
        }
    }
}

function itemLiClick() {
    var b = this.parent.parent || this.parent;
    var c = b.dataNode;
    updateProgressBar(c);
    if (c.isRate) {
        this.parent.holder = this.value;
        for (var a = 0; a < this.value; a++) {
            this.parent.itemLis[a].className = "on" + c._mode;
        }
        if (!c._requir && !b.hasClearHref) {
            addClearHref(b);
        }
        displayRelationRaidoCheck(b, c);
        jump(b, this);
    }
}

function set_data_fromServer(d) {
   // console.log(d);
	var m = new Array();
    m = d.split("¤");
	// console.log(m);
    var h = m[0];
    var a = h.split("§");
    hasTouPiao = a[0] == "true";
    useSelfTopic = a[1] == "true";
	
    var q = 0;
    var n = 0;
    var l = true;
    var p = 0;
    for (var k = 1; k < m.length; k++) {
        var f = new Object();
        var e = m[k].split("§");
        switch (e[0]) {
        case "page":
            if (l) {
                l = false;
            } else {
                n++;
            }
            p = 0;
            if (e[2] == "true") {
                pageHolder[n]._iszhenbie = true;
            }
            pageHolder[n]._mintime = e[3] ? parseInt(e[3]) : "";
            pageHolder[n]._maxtime = e[4] ? parseInt(e[4]) : "";
			
            break;
        case "question":
            f._type = trim(e[0]);
            f._topic = trim(e[1]);
            f._height = trim(e[2]);
            f._maxword = trim(e[3]);
            if (e[4] == "true") {
                f._requir = true;
            } else {
                f._requir = false;
            } if (e[5] == "true") {
                f._norepeat = true;
            } else {
                f._norepeat = false;
            } if (trim(e[6]) == "true") {
                f._hasjump = true;
            } else {
                f._hasjump = false;
            }
            f._anytimejumpto = trim(e[7]);
            f._verify = trim(e[8]);
            f._needOnly = e[9] == "true" ? true : false;
            f._hasList = e[10] == "true" ? true : false;
            f._listId = e[11] ? parseInt(e[11]) : -1;
            f._minword = e[12];
            pageHolder[n].questions[p].dataNode = f;
            p++;
            break;
        case "slider":
            f._type = trim(e[0]);
            f._topic = trim(e[1]);
            if (e[2] == "true") {
                f._requir = true;
            } else {
                f._requir = false;
            }
            f._minvalue = trim(e[3]);
            f._maxvalue = trim(e[4]);
            if (trim(e[5]) == "true") {
                f._hasjump = true;
            } else {
                f._hasjump = false;
            }
            f._anytimejumpto = trim(e[6]);
            pageHolder[n].questions[p].dataNode = f;
            p++;
            break;
        case "fileupload":
            f._type = trim(e[0]);
            f._topic = trim(e[1]);
            if (e[2] == "true") {
                f._requir = true;
            } else {
                f._requir = false;
            }
            f._maxsize = trim(e[3]);
            f._ext = trim(e[4]);
            if (trim(e[5]) == "true") {
                f._hasjump = true;
            } else {
                f._hasjump = false;
            }
            f._anytimejumpto = trim(e[6]);
            pageHolder[n].questions[p].dataNode = f;
            p++;
            break;
        case "gapfill":
            f._type = trim(e[0]);
            f._topic = trim(e[1]);
            if (e[2] == "true") {
                f._requir = true;
            } else {
                f._requir = false;
            }
            f._gapcount = trim(e[3]);
            if (trim(e[4]) == "true") {
                f._hasjump = true;
            } else {
                f._hasjump = false;
            }
            f._anytimejumpto = trim(e[5]);
            pageHolder[n].questions[p].dataNode = f;
            p++;
            break;
        case "sum":
            f._type = trim(e[0]);
            f._topic = trim(e[1]);
            if (e[2] == "true") {
                f._requir = true;
            } else {
                f._requir = false;
            }
            f._total = parseInt(e[3]);
            if (trim(e[4]) == "true") {
                f._hasjump = true;
            } else {
                f._hasjump = false;
            }
            f._anytimejumpto = trim(e[5]);
            f._referTopic = e[6];
            pageHolder[n].questions[p].dataNode = f;
            p++;
            break;
        case "radio":
        case "check":
        case "radio_down":
        case "matrix":
			
            f._type = trim(e[0]);// 类型
            f._topic = trim(e[1]);// 编号
            f._numperrow = trim(e[2]);// 每行个数
			
            if (e[3] == "true") {
                f._hasvalue = true;
            } else {
                f._hasvalue = false;
            } if (e[4] == "true") {
                f._hasjump = true;
            } else {
                f._hasjump = false;//
            }
            f._anytimejumpto = e[5];// 0
            f._mode = trim(e[9]);//
			// console.log(e[9]);
            if (e[0] != "check") {
                if (e[6] == "true") {
                    f._requir = true;
                } else {
                    f._requir = false;
                }
                f.isSort = false;
                f.isRate = isRadioRate(f._mode);
            } else {
                var o = e[6].split(",");
                f._minValue = 0;
                f._maxValue = 0;
                if (o[0] == "true") {
                    f._requir = true;
                } else {
                    f._requir = false;
                } if (o[1] != "") {
                    f._minValue = Number(o[1]);
                }
                if (o[2] != "") {
                    f._maxValue = Number(o[2]);
                }
                f.isSort = f._mode != "" && f._mode != "0";
                f.isRate = false;
            } if (e[7] == "true") {
                f._isTouPiao = true;
            } else {
                f._isTouPiao = false;
            }
		
            f._verify = trim(e[8]);
            f._referTopic = e[10];
            f._referedTopics = e[11];
            var c = 12;
            f._select = new Array();
            for (var g = c; g < e.length; g++) {
                f._select[g - c] = new Object();
                var b = e[g].split("〒");
				// console.log(e[g],'dddd');
                if (b[0] == "true") {
                    f._select[g - c]._item_radio = true;
                } else {
                    f._select[g - c]._item_radio = false;
                }
                f._select[g - c]._item_value = trim(b[1]);
                f._select[g - c]._item_jump = trim(b[2]);
                f._select[g - c]._item_huchi = b[3] == "true";
                if (f._select[g - c]._item_huchi) {
                    f.hasHuChi = true;
                }
            }
			
            pageHolder[n].questions[p].dataNode = f;
		// console.log(f);
            p++;
            break;
        default:
            break;
        }
    }
}

function show_pre_page() {
    if (cur_page > 0 && pageHolder[cur_page - 1].hasExceedTime) {
        alert("上一页填写超时，不能返回上一页");
        return;
    }
    submit_table.style.display = "none";
    next_page.style.display = "";
    pageHolder[cur_page].style.display = "none";
    cur_page--;
    if (jumpPages) {
        var c = jumpPages.length;
        if (c > 0) {
            if (cur_page == jumpPages[c - 1]) {
                for (var b = c - 1; b >= 0; b--) {
                    if (cur_page == jumpPages[b]) {
                        cur_page--;
                    }
                }
            }
        }
    }
    for (var d = cur_page; d >= 0; d--) {
        if (pageHolder[d].skipPage) {
            cur_page--;
        } else {
            break;
        }
    }
    if (cur_page == 0 && pre_page) {
        pre_page.style.display = displayPrevPage;
        pre_page.disabled = true;
    }
    showDesc();
    pageHolder[cur_page].style.display = "";
    var a = pageHolder[cur_page].questions;
    pageHolder[cur_page].scrollIntoView();
    adjustHeight();
}

function adjustHeight() {
    try {
        if (window.parent && window.parent.adjustMyFrameHeight) {
            window.parent.adjustMyFrameHeight();
        }
    } catch (a) {}
}
var pubNoCheck = null;
var saveNeedAlert = true;

function checkDisalbed() {
    if (!submit_button.disabled) {
        return false;
    }
    if (divMinTime.innerHTML) {
        var a = divMinTime.innerHTML.replace(/<.+?>/gim, "");
        alert(a);
    }
    return true;
}

function show_next_page(a) {
    if (next_page) {
        next_page.disabled = true;
    }
    if (pubNoCheck != true) {
        if (isPreview) {
            submit_button.onclick = function () {
                if (checkDisalbed()) {
                    return;
                }
                alert("此为预览状态，不能提交答卷！");
            };
            to_next_page();
            return;
        } else {
            if (a != true && !validate()) {
                if (isPub && pubNoCheck == null) {
                    if (window.confirm("您填写的数据不符合要求，由于您是发布者，可以选择直接跳到下一页（此次填写的答卷将不能提交），是否确定？")) {
                        pubNoCheck = true;
                        $("submittest_button").onclick = submit_button.onclick = function () {
                            if (checkDisalbed()) {
                                return;
                            }
                            alert("由于您选择了跳过了数据检查，所以此次填写的答卷无法提交！如果您需要提交答卷，请刷新此页面并再次填写问卷。");
                        };
                        to_next_page();
                        return;
                    } else {
                        pubNoCheck = false;
                        next_page.disabled = false;
                        return;
                    }
                } else {
                    next_page.disabled = false;
                    return;
                }
            }
        }
    } else {
        if (pubNoCheck == true) {
            to_next_page();
            return;
        }
    } if (pageHolder[cur_page]._iszhenbie && isRunning == "true") {
        submit(3);
    } else {
        to_next_page();
        if (a != true && allowSaveJoin && isRunning == "true" && guid) {
            saveNeedAlert = false;
            submit(2);
        }
    }
}

function to_next_page() {
    if (cur_page == 0 && nextPageAlertText) {
        alert(nextPageAlertText);
    }
    pre_page.style.display = displayPrevPage;
    pre_page.disabled = false;
    pageHolder[cur_page].style.display = "none";
    cur_page++;
    next_page.disabled = false;
    if (jumpPages) {
        var b = jumpPages.length;
        if (b > 0) {
            if (cur_page == jumpPages[0]) {
                for (var a = 0; a < b; a++) {
                    if (cur_page == jumpPages[a]) {
                        cur_page++;
                    }
                }
            }
        }
    }
    for (var c = cur_page; c < pageHolder.length; c++) {
        if (pageHolder[c].skipPage) {
            cur_page++;
        } else {
            break;
        }
    }
    var d = true;
    for (var c = cur_page + 1; c < pageHolder.length; c++) {
        if (!pageHolder[c].skipPage) {
            d = false;
        }
    }
    if (cur_page >= pageHolder.length - 1 || d) {
        next_page.style.display = "none";
        if (hasJoin != "1") {
            submit_table.style.display = "";
        }
    }
    if (divMaxTime) {
        divMaxTime.style.display = "none";
    }
    showDesc();
    pageHolder[cur_page].style.display = "";
    pageHolder[cur_page].scrollIntoView();
    showProgressBar();
    processMinMax();
    adjustHeight();
}

function showDesc() {
    if (!window.divDec) {
        return;
    }
    var a = document.getElementById(window.divDec);
    if (a) {
        a.style.display = cur_page > 0 ? "none" : "";
    }
}
if (hrefPreview) {
    hrefPreview.onclick = function () {
        submit(0);
        return false;
    };
}
var spanSave = null;
var saveInterval = null;
var hasSaveChanged = false;
var manualSave = false;
var changeInterval = null;
var totalSaveSec = 1;
if (hrefSave) {
    hrefSave.onclick = function () {
        if (isRunning != "true") {
            alert("此问卷处于停止状态，不能保存！");
            return;
        }
        manualSave = true;
        submit(2);
        manualSave = false;
        return false;
    };
    if (isRunning == "true") {
        saveInterval = setInterval(function () {
            submit(2);
        }, 60000);
    }
}
var havereturn = false;
var timeoutTimer = null;
var errorTimes = 0;
var hasSendErrorMail = false;

function processError(e, c, b, a) {
    if (!havereturn) {
        havereturn = true;
        var d = "";
        var f = encodeURIComponent(e);
        if (f.length > 1800) {
            d = a + "&submitdata=exceed&partdata=" + f.substring(0, 1700);
        } else {
            d = a;
            if (a.indexOf("submitdata=") == -1) {
                d += "&submitdata=" + f;
            }
            if (a.indexOf("useget=") == -1) {
                d += "&useget=1";
            }
            if (a.indexOf("iframe=") == -1) {
                d += "&iframe=1";
            }
        }
        errorTimes++;
        if (errorTimes == 1 && !hasSendErrorMail) {
            d += "&nsd=1";
            hasSendErrorMail = true;
        }
        PDF_launch("/wjx/join/jqerror.aspx?" + d + "&status=" + encodeURIComponent(c) + "&et=" + errorTimes, 400, 120);
        refresh_validate();
        submit_tip.style.display = "none";
        submit_div.style.display = "block";
    }
    if (window.localStorage) {
        window.localStorage.setItem("lastSubmitData" + activityId, e);
    }
    if (!window.submitWithGet) {
        window.submitWithGet = 1;
    }
    if (timeoutTimer) {
        clearTimeout(timeoutTimer);
    }
}

function submit(l) {

    if (l != 2 && !validate()) {
        return;
    }
    if (tCode && tCode.style.display != "none" && l == 1) {
        var q = true;
        if (submit_text.value == "" || submit_text.value == validate_info_submit_title3) {
            alert(validate_info_submit1);
            q = false;
        } else {
            if (needAvoidCrack == 1 && !yucVerify) {
                alert("验证码输入错误");
                q = false;
            }
        } if (!q) {
            try {
                submit_text.focus();
                submit_text.click();
            } catch (r) {}
            return false;
        }
    }
    if (l == 1 && window.TouPiaoPay && !window.payPhone) {
        alert("很抱歉，您必须提供短信验证码才能投票，请先获取短信验证码！");
        return false;
    }
    submit_tip.innerHTML = validate_info_submit2;
    if (l == 0) {
        PromoteUser("正在处理，请稍候...", 3000, true);
    } else {
        if (l == 2) {
            if (!hasSaveChanged) {
                if (manualSave) {
                    if (spanSave) {
                        spanSave.innerHTML = "&nbsp;已保存";
                    } else {
                        PromoteUser("已保存", 3000, true);
                    }
                    submit_tip.scrollIntoView();
                }
                return;
            }
            if (spanSave) {
                spanSave.innerHTML = "&nbsp;正在保存，请稍候...";
            }
            var s = cur_page;
        } else {
            if (l == 3) {
                PromoteUser("正在验证，请稍候...", 3000, true);
            } else {
                submit_tip.style.display = "block";
                submit_div.style.display = "none";
            }
        }
    }
    needCheckLeave = false;
    var g = sent_to_answer();
    var t = getXmlHttp();
	
    t.onreadystatechange = function () {
        if (t.readyState == 4) {
            clearTimeout(timeoutTimer);
            var u = t.status;
            if (u == 200) {
                afterSubmit(t.responseText, l);
            } else {
                // processError(g, u, l, k);
            }
        }
    };
	    var k = "ttrid="+ttrid+"&pid="+tmpojectId+"&sid="+subjectId+"&tc="+trainingCollege+"&type="+paperType+"&valuator="+valuator+"&submittype=" + l + "&curID=" + activityId + "&t=" + (new Date()).valueOf();
    if (source) {
        k += "&source=" + encodeURIComponent(source);
    }
    if (window.udsid) {
        k += "&udsid=" + window.udsid;
    }
    if (refu) {
        k += "&refu=" + encodeURIComponent(refu);
    }
    if (hasTouPiao) {
        k += "&toupiao=t";
    }
    if (jiFen > 0) {
        k += "&jf=" + jiFen;
    }
    if (randomparm) {
        k += "&ranparm=" + randomparm;
    }
    if (qinvited) {
        k += "&qinvited=" + qinvited;
    }
    if (inviteid) {
        k += "&inviteid=" + inviteid;
    }
    if (eproguid) {
        k += "&eproguid=" + eproguid;
    }
    if (parterid) {
        k += "&partnerid=" + parterid;
    }
    if (l == 2) {
        k += "&lastpage=" + s + "&lastq=" + MaxTopic;
    }
    if (l == 3) {
        k += "&zbp=" + (cur_page + 1);
    }
    if (hasJoin) {
        k += "&nfjoinid=" + nfjoinid;
    }
    if (window.sojumpParm) {
        k += "&sojumpparm=" + encodeURIComponent(window.sojumpParm);
    }
    if (tCode && tCode.style.display != "none" && submit_text.value != "") {
        k += "&validate_text=" + encodeURIComponent(submit_text.value);
    }
    k += "&starttime=" + encodeURIComponent(starttime);
    if (guid) {
        k += "&emailguid=" + guid;
    }
    if (window.sjUser) {
        k += "&sjUser=" + encodeURIComponent(sjUser);
    }
    if (window.mobileRnum) {
        k += "&m=" + window.mobileRnum;
    }
    if (Password) {
        k += "&psd=" + encodeURIComponent(Password);
    }
    if (hasMaxtime) {
        k += "&hmt=1";
    }
    if (sourceDetail) {
        k += "&sd=" + sourceDetail;
    }
    if (window.parterparm && window.parterparmname) {
        k += "&" + parterparmname + "=" + parterparm;
    }
    if (wbid) {
        k += "&wbid=" + wbid;
    }
    if ($("BMserialnum")) {
        k += "&BMserialnum=" + encodeURIComponent($("BMserialnum").value);
    }
    if (window.alipayAccount) {
        k += "&alac=" + encodeURIComponent(window.alipayAccount);
    }
    if (window.TouPiaoPay) {
        k += "&payphone=" + encodeURIComponent(window.payPhone);
    }
    g = g.replace(/\+/g, "%2B");
	// console.log(g);
    var m = encodeURIComponent(g);
    var h = false;
    var e = "";
    var p = "";
    for (var i = 0; i < trapHolder.length; i++) {
        e = "";
        var c = trapHolder[i].itemInputs;
        for (var o = 0; o < c.length; o++) {
            if (c[o].checked) {
                e += c[o].value + ",";
            }
        }
        var n = trapHolder[i].getAttribute("trapanswer");
        if (e && n && e.indexOf(n) == -1) {
            h = true;
            p = trapHolder[i].getAttribute("tikuindex");
            break;
        }
    }
    if (h) {
        k += "&ite=1&ics=" + encodeURIComponent(p + ";" + e);
    }
    var f = false;
    var b = "post";
    var d = window.getMaxWidth || 1800;
    if (window.submitWithGet && m.length <= d) {
        f = true;
    }
    if (f) {
        k += "&submitdata=" + m;
        k += "&useget=1";
        b = "get";
    } else {
        if (window.submitWithGet) {
            window.postIframe = 1;
        }
    }
	
    var a = "../paper/paper_process?" + k;
	
    t.open(b, a, false);
    t.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    havereturn = false;
	
    if (window.postIframe) {
        postWithIframe(a, g, l);
    } else {
        if (f) {
            if (errorTimes == 2 || window.getWithIframe) {
                GetWithIframe(a, g, l, k);
            } else {
                /*
				 * if (l == 1) { timeoutTimer = setTimeout(function () {
				 * processError(g, "ajaxget", l, k); }, 20000); }
				 */
                t.send(null);
            }
        } else {
            /*
			 * if (l == 1) { timeoutTimer = setTimeout(function () {
			 * processError(g, "ajaxpost", l, k); }, 20000); }
			 */
			// console.log(m)
            t.send("submitdata=" + m);
        }
    }
}

function postWithIframe(d, e, b, a) {
    if (b == 1) {
        timeoutTimer = setTimeout(function () {
            processError(e, "postiframe", b, a);
        }, 20000);
    }
    var c = document.createElement("div");
    c.style.display = "none";
    c.innerHTML = "<iframe id='mainframe' name='mainframe' style='display:none;' > </iframe><form target='mainframe' id='frameform' action='' method='post' enctype='application/x-www-form-urlencoded'><input  value='' id='submitdata' name='submitdata' type='hidden'><input type='submit' value='提交' ></form>";
    document.body.appendChild(c);
    $("submitdata").value = e;
    var f = $("frameform");
    f.action = d + "&iframe=1";
    f.submit();
}

function GetWithIframe(d, e, b, a) {
    if (b == 1) {
        timeoutTimer = setTimeout(function () {
            processError(e, "getiframe", b, a);
        }, 20000);
    }
    var c = document.createElement("div");
    c.style.display = "none";
    var g = d + "&iframe=1";
    c.innerHTML = "<iframe id='mainframe' name='mainframe'> </iframe>";
    document.body.appendChild(c);
    var f = $("mainframe");
    f.src = g;
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

function afterSubmit(z, n) {
    havereturn = true;
    errorTimes = 0;
    if ($("PDF_bg_chezchenz")) {
        PDF_close();
    }
    clearTimeout(timeoutTimer);
	
    var q = z.split("〒");
    var m = q[0];
    if (n == 0) {
        if (m == 14) {
            var d = q[1];
            var t = "/wjx/previewanswer.aspx?activityid=" + activityId + "&sg=" + d + "&t=" + (new Date()).valueOf();
            var b = $("hrefPQ");
            b.href = t;
            PDF_launch("divPreviewQ", 300, 100);
        } else {
            alert("请点击预览答卷按钮");
        }
    } else {
        if (n == 2) {
            if (m == 14) {
                var d = q[1];
                var l = window.location.href.toLowerCase();
                if (l.indexOf("?") > -1) {
                    if (l.indexOf("sg=") > -1) {
                        l = l.replace(/sg=([\w|\-]+)/g, "sg=" + d);
                    } else {
                        l = l + "&sg=" + d;
                    }
                } else {
                    l = l + "?sg=" + d;
                } if (hrefSave) {
                    var u = getTop(hrefSave);
                    if (!spanSave) {
                        spanSave = document.createElement("div");
                        divSaveText.appendChild(spanSave);
                        spanSave.style.color = "#666666";
                        spanSave.style.lineHeight = "14px";
                        spanSave.style.width = "14px";
                        if (divProgressImg) {
                            divProgressImg.style.paddingLeft = "7px";
                        } else {
                            spanSave.style.paddingLeft = "15px";
                        }
                    }
                    var x = new Date();
                    var r = x.getMinutes();
                    if (r < 10) {
                        r = "0" + r;
                    }
                    var f = x.getHours();
                    if (f < 10) {
                        f = "0" + f;
                    }
                    var g = f + ":" + r;
                    spanSave.innerHTML = "答卷保存于<div id='saveData'>1</div><div id='divUnit'>秒</div>钟前";
                    if (divLeftBar) {
                        divLeftBar.title = "下次可以接着继续填写";
                    }
                    totalSaveSec = 1;
                    spanSave.style.display = "";
                    submit_tip.style.display = "none";
                    clearInterval(changeInterval);
                    changeInterval = setInterval(function () {
                        var e = $("saveData");
                        if (e) {
                            totalSaveSec++;
                            e.innerHTML = totalSaveSec;
                            if (totalSaveSec > 60) {
                                e.innerHTML = parseInt(totalSaveSec / 60);
                                $("divUnit").innerHTML = "分";
                            }
                        }
                    }, 1000);
                }
                hasSaveChanged = false;
                clearInterval(saveInterval);
                saveInterval = setInterval(function () {
                    submit(2);
                }, 60000);
                if (!guid) {
                    var o = window.location.host || "sojump.com";
                    try {
                        setCookie(activityId + "_save", d, getExpDate(30, 0, 0), "/", o, null);
                    } catch (w) {}
                }
                if (q[2]) {
                    nfjoinid = q[2];
                    hasJoin = "2";
                }
                if (q[3]) {
                    starttime = q[3];
                }
                return;
            }
        } else {
            if (n == 3) {
                if (m == 12) {
                    randomparm = q[1];
                    PromoteUser("", 1, true);
                    to_next_page();
                    return;
                } else {
                    if (m == 13) {
                        var k = q[1];
                        var A = q[2] || "0";
                        var l = "/wjx/join/complete.aspx?q=" + activityId + "&s=" + simple + "&joinid=" + k;
                        if (guid) {
                            l += "&guid=" + guid;
                        }
                        if (promoteSource == "t") {
                            l += "&ps=" + promoteSource;
                        }
                        if (eproguid) {
                            l += "&eproguid=" + eproguid;
                        }
                        l += "&v=" + A;
                        if (window.sjUser) {
                            l += "&sjUser=" + encodeURIComponent(sjUser);
                        }
                        location.replace(l);
                        return;
                    } else {
                        if (m == 5) {
                            alert(q[1]);
                            return;
                        }
                    }
                }
            } else {
				
                if (m == 10) {
                    var l = q[1];
                    l += "&s=" + simple;
                    if (promoteSource == "t") {
                        l += "&ps=" + promoteSource;
                    }
                    if (eproguid) {
                        l += "&eproguid=" + eproguid;
                    }
                    if (qwidth) {
                        l += "&width=" + qwidth;
                    }
                    if (qinvited) {
                        l += "&qinvited=" + qinvited;
                    }
                    if (inviteid) {
                        l += "&inviteid=" + inviteid;
                    }
                    if (parterid) {
                        l += "&partnerid=" + parterid;
                    }
                    if (source) {
                        l += "&source=" + encodeURIComponent(source);
                    }
                    if (guid) {
                        l += "&guid=" + guid;
                    }
                    if (window.sjUser) {
                        l += "&sjUser=" + encodeURIComponent(sjUser);
                    }
                    if (window.parterparm && window.parterparmname) {
                        l += "&" + parterparmname + "=" + parterparm;
                    }
                    if (window.needJQJiang) {
                        l += "&njqj=1";
                    }
                    if (window.HasJiFenBao) {
                        l += "&hjfb=1";
                    }
                    var o = window.location.host || "sojump.com";
                    try {
                        setCookie(activityId + "_save", "", getExpDate(-1, 0, 0), "/", o, null);
                    } catch (w) {}
                    location.replace(l);
                    return;
                } else {
                    if (m == 11) {
                        var p = q[1];
                        if (!p || p[0] == "?") {
                            p = window.location.href;
                        } else {
                            if (p.toLowerCase().indexOf("http://") == -1 && p.toLowerCase().indexOf("https://") == -1) {
                                p = "http://" + p;
                            }
                        }
                        var B = q[3] || "";
                        if (window.sojumpParm) {
                            p = p.replace("{output}", sojumpParm);
                        }
                        if (window.sojumpParm) {
                            var a = "sojumpindex=" + B;
                            if (p.indexOf("?") > -1) {
                                a = "&" + a;
                            } else {
                                a = "?" + a;
                            } if (p.toLowerCase().indexOf("sojumpparm=") == -1) {
                                a += "&sojumpparm=" + sojumpParm;
                            }
                            p += a;
                        }
                        var y = q[2];
                        if (y && y != "不提示" && window.jiFenBao == 0 && !window.sojumpParm) {
                            alert(y);
                        }
                        var o = window.location.host || "sojump.com";
                        try {
                            setCookie(activityId + "_save", "", getExpDate(-1, 0, 0), "/", o, null);
                        } catch (w) {}
                        location.replace(p);
                        return;
                    } else {
                        if (m == 9 || m == 16) {
                            var s = parseInt(q[1]);
                            var h = (s + 1) + "";
                            if (pageHolder.length == 1 && pageHolder[0].questions[s]) {
                                writeError(pageHolder[0].questions[s], q[2], 3000);
                                pageHolder[0].questions[s].scrollIntoView();
                            } else {
                                if (questionsObject[h]) {
                                    writeError(questionsObject[h], q[2], 3000);
                                    alert(q[2]);
                                    questionsObject[h].scrollIntoView();
                                } else {
                                    alert("您提交的数据有误，请检查！");
                                }
                            }
                        } else {
                            if (m == 7) {
                                alert(q[1]);
                                if (needAvoidCrack != 2) {
                                    tCode.style.display = "";
                                    submit_tip.style.display = "none";
                                    submit_div.style.display = "block";
                                    try {
                                        submit_text.focus();
                                        submit_text.click();
                                    } catch (v) {}
                                }
                            } else {
                                if (m == 2) {
                                    alert(q[1]);
                                } else {
                                    if (m == 17) {
                                        alert("密码冲突！在您提交答卷之前，此密码已经被另外一个用户使用了，请重新更换密码！\r\n系统会自动保存您当前填写的答卷，请复制新的链接重新提交此份答卷！");
                                        submit(2);
                                        return;
                                    } else {
                                        if (m == 4) {
                                            alert(q[1]);
                                            submit(2);
                                            return;
                                        } else {
                                            if (m == 5) {
                                                alert(q[1]);
                                                return;
                                            } else {
                                                if (m == 20) {
                                                    var c = q[1];
                                                    if (c == "noparm") {
                                                        c = "很抱歉，您必须提供短信验证码才能投票，请刷新页面重新投票！";
                                                        if (window.divTouPiouId) {
                                                            $(divTouPiouId).style.display = "";
                                                            TouPiaoPay = 1;
                                                            if (tCode) {
                                                                tCode.style.display = "none";
                                                            }
                                                            c = "很抱歉，您必须提供短信验证码才能投票，请先获取短信验证码！";
                                                        }
                                                    }
                                                    alert(c);
                                                    submit_tip.style.display = "none";
                                                    submit_div.style.display = "block";
                                                    return;
                                                } else {
                                                    if (m == 19) {
                                                        alert(q[1]);
                                                        window.location = "/";
                                                        return;
                                                    } else {// 进入这一分支 跳转
                                                        alert(q[1]);
														window.location = './que-suc.html';
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    refresh_validate();
    submit_tip.style.display = "none";
    submit_div.style.display = "block";
    return;
}
var firstError = null;
var firstMatrixError = null;

function sent_to_answer() {
    var p = new Array();
    var u = 0;
    for (var q = 0; q < pageHolder.length; q++) {
        var T = pageHolder[q].questions;
        var o = pageHolder[q]._maxtime > 0;
        for (var S = 0; S < T.length; S++) {
            var B = T[S].dataNode;
            var m = T[S].style.display.toLowerCase() == "none" || (T[S].dataNode._referTopic && !T[S].displayContent) || pageHolder[q].skipPage;
            var n = new Object();
            n._topic = B._topic;
            n._value = "";
            p[u++] = n;
            switch (B._type) {
            case "question":
                if (m) {
                    n._value = "(跳过)";
                    continue;
                }
                var K = T[S].itemTextarea || T[S].itemInputs[0];
                var w = K.value || "";
                n._value = replace_specialChar(w);
                break;
            case "gapfill":
                var d = T[S].gapFills;
                for (var P = 0; P < d.length; P++) {
                    if (P > 0) {
                        n._value += spChars[2];
                    }
                    if (m) {
                        n._value += "(跳过)";
                    } else {
                        n._value += replace_specialChar(trim(d[P].value.substring(0, 3000)));
                    }
                }
                break;
            case "slider":
                var L = T[S].divSlider.value;
                if (m) {
                    n._value = "(跳过)";
                    continue;
                }
                n._value = L == undefined ? "" : L;
                break;
            case "fileupload":
                var z = T[S].fileName;
                if (m) {
                    n._value = "(跳过)";
                    continue;
                }
                n._value = z || "";
                break;
            case "sum":
                var r = T[S].itemInputs;
                len = r.length;
                for (var P = 0; P < len; P++) {
                    var G = r[P];
                    var H = T[S].relSum == 0 ? trim(G.value) || "0" : trim(G.value);
                    if (P > 0) {
                        n._value += spChars[2] + H;
                    } else {
                        n._value = H;
                    }
                }
                if (m) {
                    var M = 0;
                    while (M < len) {
                        if (M == 0) {
                            n._value = "(跳过)";
                        } else {
                            n._value += spChars[2] + "(跳过)";
                        }
                        M++;
                    }
                }
                break;
            case "radio":
            case "check":
                if (T[S].itemSel) {
                    var U = T[S].itemSel;
                    var y = U.options;
                    if (m) {
                        var M = 0;
                        while (M < B._select.length) {
                            if (M == 0) {
                                n._value = "-3";
                            } else {
                                n._value += ",-3";
                            }
                            M++;
                        }
                        continue;
                    }
                    for (var P = 0; P < y.length; P++) {
                        if (P == 0) {
                            n._value = y[P].value + "";
                        } else {
                            n._value += "," + y[P].value;
                        }
                    }
                    if (y.length == 0 || y.length < B._select.length) {
                        var F = B._select.length;
                        for (var P = y.length; P < F; P++) {
                            if (n._value) {
                                n._value += ",-2";
                            } else {
                                n._value = "-2";
                            }
                        }
                    }
                    continue;
                }
                if (m) {
                    n._value = "-3";
                    continue;
                }
                var y = T[S].itemInputs || T[S].itemLis;
                var s = -1;
                var I = 0;
                for (var P = 0; P < y.length; P++) {
                    if (y[P].className.toLowerCase().indexOf("on") > -1) {
                        s = P;
                    }
                    var x = y[P].parentNode && y[P].parentNode.style.display == "none";
                    if (!x && y[P].checked) {
                        I++;
                        if (n._value) {
                            n._value += spChars[3] + y[P].value;
                        } else {
                            n._value = y[P].value + "";
                        } if (y[P].itemText) {
                            var Q = y[P].itemText.value;
                            if (Q == defaultOtherText) {
                                Q = "";
                            }
                            n._value += spChars[2] + replace_specialChar(trim(Q.substring(0, 3000)));
                        }
                    }
                }
                if (s > -1) {
                    n._value = y[s].value + "";
                } else {
                    if (I > 0) {} else {
                        n._value = "-2";
                    }
                }
                break;
            case "radio_down":
                if (m) {
                    n._value = "-3";
                    continue;
                }
                n._value = T[S].itemSel.value;
                break;
            case "matrix":
                var f = T[S].itemTrs;
                var h = B._mode;
                len = f.length;
                var V = 0;
                var l = 0;
                var E = 0;
                var N = new Array();
                var g = false;
                for (var P = 0; P < f.length; P++) {
                    var b = f[P].getAttribute("rowIndex");
                    if (b == 0 && f[P].getAttribute("RandomRow") == "true") {
                        g = true;
                    }
                    var W = new Object();
                    W.rowIndex = b;
                    if (f[P].style.display == "none") {
                        if (h != "201" && h != "202" && h != "301" && h != "302" && h != "303") {
                            if (n._value) {
                                n._value += ",-2";
                            } else {
                                n._value = "-2";
                            }
                            W.val = "-2";
                            continue;
                        }
                    }
                    var y = f[P].itemInputs || f[P].itemLis || f[P].divSlider || f[P].itemSels;
                    if (!y) {
                        len = len - 1;
                        continue;
                    } else {
                        V = y.length;
                    }
                    var s = -1;
                    var D = "";
                    if (h != "201" && h != "202") {
                        for (var M = 0; M < y.length; M++) {
                            if (y[M].className.toLowerCase().indexOf("on") > -1) {
                                s = M;
                                D = y[M].value;
                            }
                            if (y[M].checked) {
                                s = M;
                                if (D) {
                                    D += ";" + y[M].value;
                                } else {
                                    D = y[M].value;
                                } if ((h == "103" || h == "102") && y[M].itemText) {
                                    var R = trim(y[M].itemText.value);
                                    if (R == defaultOtherText) {
                                        R = "";
                                    }
                                    R = replace_specialChar(R).replace(/;/g, "；").replace(/,/g, "，");
                                    D += spChars[2] + R;
                                }
                            } else {
                                if (y[M].tagName == "TEXTAREA" || y[M].tagName == "SELECT") {
                                    s = M;
                                    var R = trim(y[M].value);
                                    if (M > 0) {
                                        D += spChars[3];
                                    }
                                    if (R) {
                                        if (h == "302") {
                                            R = replace_specialChar(R);
                                        }
                                        D += R;
                                    } else {
                                        if (h == "303") {
                                            D += "-2";
                                        } else {
                                            D += "(空)";
                                        }
                                    }
                                }
                            }
                        }
                        if (s > -1) {
                            if (n._value) {
                                if (h == "301" || h == "302" || h == "303") {
                                    n._value += spChars[2] + D;
                                } else {
                                    n._value += "," + D;
                                }
                            } else {
                                n._value = D;
                            }
                            W.val = D;
                        } else {
                            if (n._value) {
                                n._value += ",-2";
                            } else {
                                n._value = "-2";
                            }
                            W.val = "-2";
                        }
                    } else {
                        if (h == "201") {
                            var A = trim(y[0].value.substring(0, 3000));
                            if (f[P].style.display == "none") {
                                A = "";
                            }
                            if (E > 0) {
                                n._value += spChars[2] + replace_specialChar(A);
                            } else {
                                n._value = replace_specialChar(A);
                            }
                            W.val = replace_specialChar(A);
                        } else {
                            if (h == "202") {
                                var O = f[P].divSlider.value == undefined ? "" : f[P].divSlider.value;
                                if (f[P].style.display == "none") {
                                    O = "";
                                }
                                if (E > 0) {
                                    n._value += spChars[2] + O;
                                } else {
                                    n._value = O;
                                }
                                W.val = O;
                            }
                        }
                    }
                    N.push(W);
                    E++;
                }
                if (m) {
                    var M = 0;
                    n._value = "";
                    while (M < len) {
                        if (h == "201" || h == "202") {
                            if (M == 0) {
                                n._value = "(跳过)";
                            } else {
                                n._value += spChars[2] + "(跳过)";
                            }
                        } else {
                            if (h == "301" || h == "302" || h == "303") {
                                if (M > 0) {
                                    n._value += spChars[2];
                                }
                                for (var C = 0; C < V; C++) {
                                    if (C > 0) {
                                        n._value += spChars[3];
                                    }
                                    if (h == "303") {
                                        n._value += "-3";
                                    } else {
                                        n._value += "(跳过)";
                                    }
                                }
                            } else {
                                if (M == 0) {
                                    n._value = "-3";
                                } else {
                                    n._value += ",-3";
                                }
                            }
                        }
                        M++;
                    }
                    continue;
                } else {
                    if (g) {
                        N.sort(function (k, i) {
                            return k.rowIndex - i.rowIndex;
                        });
                        var c = spChars[2];
                        if (h != "201" && h != "202" && h != "301" && h != "302" && h != "303") {
                            c = ",";
                        }
                        var J = "";
                        for (var a = 0; a < N.length; a++) {
                            if (a > 0) {
                                J += c;
                            }
                            J += N[a].val;
                        }
                        n._value = J;
                    }
                }
                break;
            }
        }
    }
    p.sort(function (k, i) {
        return k._topic - i._topic;
    });
    var e = "";
    for (S = 0; S < p.length; S++) {
        if (S > 0) {
            e += spChars[1];
        }
        e += p[S]._topic;
        e += spChars[0];
        e += p[S]._value;
    }
    return e;
}
var verifyMsg = "";

function validate() {
    var v = true;
    var L = pageHolder[cur_page].questions;
    var x = pageHolder[cur_page].hasExceedTime;
    firstError = null;
    firstMatrixError = null;
    for (var K = 0; K < L.length; K++) {
        var u = L[K].dataNode;
        var N = u._hasjump;
        verifyMsg = "";
        var q = L[K].style.display.toLowerCase() == "none" || pageHolder[cur_page].skipPage;
        if (L[K].removeError) {
            L[K].removeError();
        }
        if (q || (L[K].dataNode._referTopic && !L[K].displayContent) || x) {
            continue;
        }
        switch (u._type) {
        case "question":
            var C = L[K].itemTextarea || L[K].itemInputs[0];
            var o = C.value || "";
            if (u._requir) {
                if (trim(o) == "") {
                    v = writeError(L[K], validate_info + validate_info_wd1, 3000);
                }
            }
            if (o.length - 3000 > 0) {
                v = writeError(L[K], "您输入的字数超过了3000，请修改！", 3000);
            }
            var m = u._verify;
            var D = L[K].getAttribute("issample");
            var z = true;
            if (jiFen > 0 && D && promoteSource != "t") {
                z = false;
            }
            if (z) {
                if (o) {
                    var B = verifyMinMax(C, m, u._minword, u._maxword);
                    if (B != "") {
                        v = writeError(L[K], B, 3000);
                    }
                }
                if (o != "" && m && m != "0") {
                    var B = verifydata(C, m, u);
                    if (B != "") {
                        v = writeError(L[K], B, 3000);
                    }
                }
            }
            if (v && trim(o) != "" && isRunning == "true") {
                if (u._needOnly) {
                    if (C.isOnly == false) {
                        v = writeError(L[K], validate_only, 3000);
                    } else {
                        if (C.isOnly != true) {
                            v = writeError(L[K], validate_error, 3000);
                            C.focus();
                            return v;
                        }
                    }
                }
                if (v && u._hasList && u._listId != -1) {
                    if (C.isInList == false) {
                        v = writeError(L[K], validate_list, 3000);
                    } else {
                        if (C.isInList != true) {
                            v = writeError(L[K], validate_error, 3000);
                            C.focus();
                            return v;
                        }
                    }
                }
            }
            if (N) {
                jumpAnyChoice(L[K], true);
            }
            break;
        case "gapfill":
            var c = L[K].gapFills;
            for (var I = 0; I < c.length; I++) {
                var o = c[I].value || "";
                if (trim(o) == "") {
                    if (u._requir) {
                        L[K].errorControl = c[I];
                        v = writeError(L[K], validate_info + validate_info_wd1, 3000);
                        break;
                    }
                } else {
                    var H = 0;
                    H = validateMatrix(u, c[I], c[I]);
                    if (H) {
                        L[K].errorControl = c[I];
                        v = writeError(L[K], verifyMsg, 3000);
                        break;
                    }
                }
            }
            break;
        case "slider":
            var E = L[K].divSlider.value;
            if (u._requir && E == undefined) {
                v = writeError(L[K], validate_info + validate_info_wd1, 3000);
            }
            if (N) {
                jumpAnyChoice(L[K], true);
            }
            break;
        case "fileupload":
            if (u._requir && !L[K].fileName) {
                v = writeError(L[K], validate_info + validate_info_wd1, 3000);
            }
            if (N) {
                jumpAnyChoice(L[K], true);
            }
            break;
        case "sum":
            var F = L[K].sumLeft;
            if (u._requir) {
                if (F != 0) {
                    v = false;
                    if (!firstError) {
                        firstError = L[K];
                    }
                    var B = "<br/><span style='color:red;'>" + sum_warn + "</span>";
                    if (L[K].relSum) {
                        L[K].relSum.innerHTML = sum_total + "<b>" + u._total + "</b>" + sum_left + "<span style='color:red;font-bold:true;'>" + (F || u._total) + "</span>" + B;
                    }
                }
            } else {
                if (F != undefined && F != 0) {
                    v = false;
                    if (!firstError) {
                        firstError = L[K];
                    }
                    var B = "<br/><span style='color:red;'>" + sum_warn + "</span>";
                    if (L[K].relSum) {
                        L[K].relSum.innerHTML = sum_total + "<b>" + u._total + "</b>" + sum_left + "<span style='color:red;font-bold:true;'>" + (F || u._total) + "</span>" + B;
                    }
                }
            } if (N) {
                jumpAnyChoice(L[K], true);
            }
            break;
        case "radio":
        case "check":
            if (L[K].itemSel) {
                var M = L[K].itemSel;
                var r = M.options;
                if (r.length == 0) {
				// if (r.length == 0 && u._requir) {
                    v = writeError(L[K], validate_info + validate_info_wd1, 3000);
                } else {
                    if (r.length > 0) {
                        if ((u._minValue == 0 || u._minValue == u._select.length) && r.length != u._select.length) {
                            v = writeError(L[K], validate_info + validate_info_check3, 3000);
                        } else {
                            if (u._maxValue > 0 && r.length > u._maxValue) {
                                var y = validate_info + validate_info_check1 + u._maxValue + validate_info_check2;
                                if (langVer == 0) {
                                    y += ",您多选择了" + (r.length - u._maxValue) + "项";
                                }
                                v = writeError(L[K], y, 3000);
                            } else {
                                if (u._minValue > 0 && r.length < u._minValue) {
                                    var y = validate_info + validate_info_check1 + u._minValue + validate_info_check2;
                                    if (langVer == 0) {
                                        y += ",您少选择了" + (u._minValue - r.length) + "项";
                                    }
                                    v = writeError(L[K], y, 3000);
                                }
                            }
                        }
                    }
                } if (N) {
                    jumpAnyChoice(L[K], true);
                }
                continue;
            }
            var r = L[K].itemInputs || L[K].itemLis;
            var n = -1;
            var A = 0;
            var f = -1;
            for (var I = 0; I < r.length; I++) {
                if (r[I].className.toLowerCase().indexOf("on") > -1) {
                    n = I;
                    f = I;
                }
                if (r[I].checked) {
                    A++;
                    f = I;
                    if (r[I].req && isTextBoxEmpty(r[I].itemText.value)) {
                        v = writeError(L[K], validate_textbox, 3000);
                    }
                }
            }
            if (n > -1) {
                hasChoice = true;
            } else {
                if (A > 0) {
                    hasChoice = true;
                    if (u._maxValue > 0 && A > u._maxValue) {
                        var y = validate_info + validate_info_check4 + u._maxValue + type_check_limit5;
                        if (langVer == 0) {
                            y += ",您多选择了" + (A - u._maxValue) + "项";
                        }
                        v = writeError(L[K], y, 3000);
                    } else {
                        if (u._minValue > 0 && A < u._minValue) {
                            var y = validate_info + validate_info_check5 + u._minValue + type_check_limit5;
                            if (langVer == 0) {
                                y += ",您少选择了" + (u._minValue - A) + "项";
                            }
                            v = writeError(L[K], y, 3000);
                        }
                    }
                } else {
                    // if (u._requir) {
                        v = writeError(L[K], validate_info + validate_info_wd1, 3000);
						// }
                }
            } if (N) {
                if (u._type == "radio" && u._anytimejumpto < 1) {
                    if (f > -1) {
                        processJ(L[K].indexInPage - 0, u._select[r[f].value - 1]._item_jump - 0, L[K].jumpInfo, true);
                    } else {
                        processJ(L[K].indexInPage - 0, 0, L[K].jumpInfo, true);
                    }
                } else {
                    jumpAnyChoice(L[K], true);
                }
            }
            break;
        case "radio_down":
            if (u._requir && L[K].itemSel.selectedIndex == 0) {
                v = writeError(L[K], validate_info + validate_info_wd1, 3000);
            }
            if (N) {
                if (u._anytimejumpto < 1) {
                    if (L[K].itemSel.selectedIndex == 0) {
                        processJ(L[K].indexInPage - 0, 0, L[K].jumpInfo, true);
                    } else {
                        processJ(L[K].indexInPage - 0, u._select[L[K].itemSel.value - 1]._item_jump - 0, L[K].jumpInfo, true);
                    }
                } else {
                    jumpAnyChoice(L[K], true);
                }
            }
            break;
        case "matrix":
            var e = L[K].itemTrs;
            var g = u._mode;
            len = e.length;
            var h = 0;
            var d = 0;
            var H = 0;
            var s;
            for (var I = 0; I < e.length; I++) {
                if (e[I].style.display == "none") {
                    len = len - 1;
                    continue;
                }
                var r = e[I].itemInputs || e[I].itemLis || e[I].divSlider || e[I].itemSels;
                if (!r) {
                    len = len - 1;
                    continue;
                }
                var n = -1;
                var A = 0;
                if (g != "201" && g != "202") {
                    for (var G = 0; G < r.length; G++) {
                        if (r[G].className.toLowerCase().indexOf("on") > -1) {
                            n = G;
                        } else {
                            if (r[G].checked) {
                                n = G;
                                A++;
                                if ((g == "103" || g == "102") && r[G].itemText && r[G].req) {
                                    var b = r[G].itemText.value;
                                    if (isTextBoxEmpty(b)) {
                                        if (!s) {
                                            s = r[G].itemText;
                                        }
                                        verifyMsg = validate_textbox;
                                        H = 1;
                                        if (!firstMatrixError) {
                                            firstMatrixError = L[K].itemTrs[I];
                                        }
                                    }
                                }
                            } else {
                                if (r[G].tagName == "TEXTAREA" || r[G].tagName == "SELECT") {
                                    var J = trim(r[G].value);
                                    n = G;
                                    if (!J) {
                                        n = -1;
                                        if (g == "301" && u._requir) {
                                            d = 1;
                                            if (!s) {
                                                s = r[G];
                                            }
                                            if (!firstMatrixError) {
                                                firstMatrixError = L[K].itemTrs[I];
                                            }
                                        }
                                        break;
                                    } else {
                                        if (g == "301") {
                                            J = DBC2SBC(r[G]);
                                            if (!/^(\-)?\d+(\.\d+)?$/.exec(J)) {
                                                d = 1;
                                            } else {
                                                if ((u._minvalue && parseInt(J) - parseInt(u._minvalue) < 0) || (u._maxvalue && parseInt(J) - parseInt(u._maxvalue) > 0)) {
                                                    d = 2;
                                                }
                                            } if (d) {
                                                if (!s) {
                                                    s = r[G];
                                                }
                                                if (!firstMatrixError) {
                                                    firstMatrixError = L[K].itemTrs[I];
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (g == "102") {
                        if (n > -1) {
                            var a = false;
                            if (u._maxvalue > 0 && A > u._maxvalue) {
                                var y = validate_info + validate_info_check4 + u._maxvalue + type_check_limit5;
                                if (langVer == 0) {
                                    y += ",您多选择了" + (A - u._maxvalue) + "项";
                                }
                                verifyMsg = y;
                                H = 1;
                                if (!firstMatrixError) {
                                    firstMatrixError = L[K].itemTrs[I];
                                }
                            } else {
                                if (u._minvalue > 0 && A < u._minvalue) {
                                    var y = validate_info + validate_info_check5 + u._minvalue + type_check_limit5;
                                    if (langVer == 0) {
                                        y += ",您少选择了" + (u._minvalue - A) + "项";
                                    }
                                    verifyMsg = y;
                                    H = 1;
                                    if (!firstMatrixError) {
                                        firstMatrixError = L[K].itemTrs[I];
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (g == "201") {
                        if (!H) {
                            H = validateMatrix(u, e[I], r[0]);
                        }
                        if (H) {
                            if (!s) {
                                s = r[0];
                            }
                            if (!firstMatrixError) {
                                firstMatrixError = L[K].itemTrs[I];
                            }
                        }
                        if (trim(r[0].value) != "") {
                            n = 0;
                        }
                    } else {
                        if (g == "202") {
                            if (e[I].divSlider.value != undefined) {
                                n = 0;
                            }
                        }
                    }
                } if (n > -1) {
                    h++;
                } else {
                    if (u._requir) {
                        break;
                    }
                }
            }
            if (u._requir && h < len) {
                v = writeError(L[K], validate_info + validate_info_matrix2 + validate_info_matrix1 + (h + 1) + validate_info_matrix3, 3000);
                if (L[K].itemTrs[I] && !firstMatrixError) {
                    firstMatrixError = L[K].itemTrs[I];
                    L[K].itemTrs[I].onclick();
                }
            }
            if (g == "201" && H) {
                if (s) {
                    L[K].errorControl = s;
                }
                v = writeError(L[K], verifyMsg, 3000);
                if (firstMatrixError) {
                    firstMatrixError.onclick();
                }
            }
            if ((g == "102" || g == "103") && H) {
                if (s) {
                    L[K].errorControl = s;
                }
                v = writeError(L[K], verifyMsg, 3000);
                if (firstMatrixError) {
                    firstMatrixError.onclick();
                }
            }
            if (g == "301" && d) {
                var p = "";
                if (d == 2) {
                    if (u._minvalue) {
                        p += "," + type_wd_minlimitDigit + ":" + u._minvalue;
                    }
                    if (u._maxvalue) {
                        p += "," + type_wd_maxlimitDigit + ":" + u._maxvalue;
                    }
                }
                if (s) {
                    L[K].errorControl = s;
                }
                v = writeError(L[K], validate_info + validate_info_matrix4 + p, 3000);
                if (firstMatrixError) {
                    firstMatrixError.onclick();
                }
            }
            if (N) {
                jumpAnyChoice(L[K], true);
            }
            break;
        }
    }
    for (var w = 0; w < trapHolder.length; w++) {
        if (trapHolder[w].pageIndex != cur_page + 1) {
            continue;
        }
        var r = trapHolder[w].itemInputs;
        var l = "";
        for (var I = 0; I < r.length; I++) {
            if (r[I].checked) {
                l += r[I].value + ",";
            }
        }
        if (!l) {
            v = writeError(trapHolder[w], validate_info + validate_info_wd1, 3000);
            break;
        }
    }
    if (firstError) {
        PromoteUser(validate_submit, 3000, false);
        if (firstMatrixError && firstMatrixError.parent == firstError) {
            firstMatrixError.scrollIntoView();
        } else {
            firstError.scrollIntoView();
        }
    }
    return v;
}

function validateMatrix(i, b, f) {
    var d = 0;
    if (!f.value) {
        return d;
    }
    var h = f.value;
    var a = b.getAttribute("itemverify") || "";
    var g = b.getAttribute("minword") || "";
    var c = b.getAttribute("maxword") || "";
    var k = b.getAttribute("issample");
    var e = true;
    verifyMsg = "";
    if (jiFen > 0 && k && promoteSource != "t") {
        e = false;
    }
    if (e) {
        verifyMsg = verifyMinMax(f, a, g, c);
    }
    if (verifyMsg != "") {
        d = 1;
    }
    if (e && d == 0 && a && a != "0") {
        verifyMsg = verifydata(f, a, i);
        if (verifyMsg != "") {
            d = 2;
        }
    }
    return d;
}

function removeError() {
    if (this.errorMessage) {
        try {
            this.removeChild(this.errorMessage);
            this.errorMessage = null;
        } catch (a) {
            this.errorMessage.innerHTML = "";
        }
        this.removeError = null;
        this.style.border = "solid 2px white";
        this.style.borderBottom = "solid 1px #efefef";
        this.style.paddingBottom = "5px";
        if (this.errorControl) {
            this.errorControl.style.background = "white";
            this.errorControl = null;
        }
    }
}

function PromoteUser(c, b, a) {
    show_status_tip(c, b);
    if (!a) {
        alert(c);
    }
}

function writeError(b, d, c) {
    if (b.errorMessage && b.errorMessage.innerHTML != "") {
        return;
    }
    if (b.dataNode && ((b.dataNode._type == "matrix" && b.dataNode._mode == "202") || b.dataNode._type == "slider")) {} else {
        b.style.padding = "4px";
        b.style.border = "solid 2px #ff9900";
        b.style.borderBottom = "solid 2px #ff9900";
    } if (b.errorMessage && b.errorMessage.innerHTML == "") {
        b.errorMessage.innerHTML = d;
    } else {
        var a = document.createElement("div");
        a.className = "errorMessage";
        a.setAttribute("for", b.id);
        a.setAttribute("htmlFor", b.id);
        a.appendChild(document.createTextNode(d));
        b.appendChild(a);
        b.errorMessage = a;
    }
    b.removeError = removeError;
    if (b.errorControl) {
        b.errorControl.style.background = "#ff3300";
    }
    if (!firstError) {
        firstError = b;
    }
    return false;
}

function show_status_tip(a, b) {
    submit_tip.style.display = "block";
    submit_tip.innerHTML = a;
    if (b > 0) {
        setTimeout("submit_tip.style.display='none'", b);
    }
}

function isDate(c) {
    var a = new Array();
    if (c.indexOf("-") != -1) {
        a = c.toString().split("-");
    } else {
        if (c.indexOf("/") != -1) {
            a = c.toString().split("/");
        } else {
            return false;
        }
    } if (a[0].length == 4) {
        var b = new Date(a[0], a[1] - 1, a[2]);
        if (b.getFullYear() == a[0] && b.getMonth() == a[1] - 1 && b.getDate() == a[2]) {
            return true;
        }
    }
    return false;
}

function DBC2SBC(b) {
    var e = b.value;
    var a;
    if (e.length <= 0) {
        return false;
    }
    qstr1 = "１２３４５６７８９０－";
    bstr1 = "1234567890-";
    var d = false;
    for (a = 0; a < e.length; a++) {
        var f = e.charAt(a);
        if (qstr1.indexOf(f) != -1) {
            e = e.replace(f, bstr1.charAt(qstr1.indexOf(f)));
            d = true;
        }
    }
    if (d) {
        b.value = e;
    }
    return b.value;
}

function verifydata(f, d, e) {
    var c = f.value;
    var a = null;
    if (d.toLowerCase() == "email" || d.toLowerCase() == "msn") {
        a = /^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/;
        if (!a.exec(c)) {
            return validate_email;
        } else {
            return "";
        }
    } else {
        if (d == "日期" || d == "生日" || d == "入学时间") {
            if (!isDate(c)) {
                return validate_date;
            } else {
                return "";
            }
        } else {
            if (d == "固话") {
                c = DBC2SBC(f);
                a = /^((\d{4}-\d{7})|(\d{3,4}-\d{8}))(-\d{1,4})?$/;
                if (!a.exec(c)) {
                    return validate_phone;
                } else {
                    return "";
                }
            } else {
                if (d == "手机") {
                    c = DBC2SBC(f);
                    a = /^\d{11}$/;
                    if (!a.exec(c)) {
                        return validate_mobile;
                    } else {
                        return "";
                    }
                } else {
                    if (d == "电话") {
                        a = /(^\d{11}$)|(^((\d{4}-\d{7})|(\d{3,4}-\d{8}))(-\d{1,4})?$)/;
                        if (!a.exec(c)) {
                            return validate_mo_phone;
                        } else {
                            return "";
                        }
                    } else {
                        if (d == "汉字") {
                            a = /^[\u4e00-\u9fa5]+$/;
                            if (!a.exec(c)) {
                                return validate_chinese;
                            } else {
                                return "";
                            }
                        } else {
                            if (d == "英文") {
                                a = /^[A-Za-z]+$/;
                                if (!a.exec(c)) {
                                    return validate_english;
                                } else {
                                    return "";
                                }
                            } else {
                                if (d == "网址" || d == "公司网址") {
                                    a = /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
                                    if (!a.exec(c)) {
                                        return validate_reticulation;
                                    } else {
                                        return "";
                                    }
                                } else {
                                    if (d == "身份证号") {
                                        c = DBC2SBC(f);
                                        a = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
                                        if (!a.exec(c)) {
                                            return validate_idcardNum;
                                        } else {
                                            return "";
                                        }
                                    } else {
                                        if (d == "数字") {
                                            c = DBC2SBC(f);
                                            a = /^(\-)?\d+$/;
                                            if (!a.exec(c)) {
                                                return validate_num;
                                            }
                                        } else {
                                            if (d == "小数") {
                                                c = DBC2SBC(f);
                                                a = /^(\-)?\d+(\.\d+)?$/;
                                                if (!a.exec(c)) {
                                                    return validate_decnum;
                                                }
                                            } else {
                                                if (d.toLowerCase() == "qq") {
                                                    c = DBC2SBC(f);
                                                    a = /^\d+$/;
                                                    var b = /^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/;
                                                    if (!a.exec(c) && !b.exec(c)) {
                                                        return validate_qq;
                                                    } else {
                                                        return "";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return "";
}

function verifyMinMax(f, e, c, a) {
    var d = f.value;
    if (e == "数字" || e == "小数") {
        if (!afterDigitPublish) {
            return "";
        }
        d = DBC2SBC(f);
        var b = /^(\-)?\d+$/;
        if (e == "小数") {
            b = /^(\-)?\d+(\.\d+)?$/;
        }
        if (!b.exec(d)) {
            if (e == "小数") {
                return validate_decnum;
            } else {
                return validate_num;
            }
        }
        if (c != "" && parseInt(d) - parseInt(c) < 0) {
            return validate_num2 + c;
        }
        if (a != "" && parseInt(d) - parseInt(a) > 0) {
            return validate_num1 + a;
        }
    } else {
        if (a != "" && d.length - a > 0) {
            return validate_info_wd3.format(a, d.length);
        }
        if (c != "" && d.length - c < 0) {
            return validate_info_wd4.format(c, d.length);
        }
    }
    return "";
}

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
if (hasQJump && window.jqLoaded) {
    jqLoaded();
}
if (nv == 1) {
    var ii = cur_page;
    for (; ii < totalPage; ii++) {
        if (validate()) {
            to_next_page();
        } else {
            break;
        }
    }
}
if (self.location != top.location) {
    var divpoweredby = document.getElementById("divpoweredby");
    if (divpoweredby) {
        divpoweredby.style.display = "block";
    }
}