function ShortCut() {
    this.all_shortcuts = new Object();
    this.add = function (b, h, d) {
        var g = {
            type: "keydown",
            propagate: false,
            disable_in_input: false,
            target: document,
            keycode: false
        };
        if (!d) {
            d = g;
        } else {
            for (var a in g) {
                if (typeof d[a] == "undefined") {
                    d[a] = g[a];
                }
            }
        }
        var f = d.target;
        if (typeof d.target == "string") {
            f = document.getElementById(d.target);
        }
        var c = this;
        b = b.toLowerCase();
        var e = function (o) {
                o = o || window.event;
                if (d.disable_in_input) {
                    var l;
                    if (o.target) {
                        l = o.target;
                    } else {
                        if (o.srcElement) {
                            l = o.srcElement;
                        }
                    }
                    if (l.nodeType == 3) {
                        l = l.parentNode;
                    }
                    if (l.tagName == "INPUT" || l.tagName == "TEXTAREA" || l.tagName == "SELECT") {
                        return;
                    }
                }
                if (o.keyCode) {
                    code = o.keyCode;
                } else {
                    if (o.which) {
                        code = o.which;
                    }
                }
                var n = String.fromCharCode(code).toLowerCase();
                if (code == 188) {
                    n = ",";
                }
                if (code == 190) {
                    n = ".";
                }
                var s = b.split("+");
                var r = 0;
                var p = {
                    "`": "~",
                    "1": "!",
                    "2": "@",
                    "3": "#",
                    "4": "$",
                    "5": "%",
                    "6": "^",
                    "7": "&",
                    "8": "*",
                    "9": "(",
                    "0": ")",
                    "-": "_",
                    "=": "+",
                    ";": ":",
                    "'": '"',
                    ",": "<",
                    ".": ">",
                    "/": "?",
                    "\\": "|"
                };
                var m = {
                    esc: 27,
                    escape: 27,
                    tab: 9,
                    space: 32,
                    "return": 13,
                    enter: 13,
                    backspace: 8,
                    scrolllock: 145,
                    scroll_lock: 145,
                    scroll: 145,
                    capslock: 20,
                    caps_lock: 20,
                    caps: 20,
                    numlock: 144,
                    num_lock: 144,
                    num: 144,
                    pause: 19,
                    "break": 19,
                    insert: 45,
                    home: 36,
                    "delete": 46,
                    end: 35,
                    pageup: 33,
                    page_up: 33,
                    pu: 33,
                    pagedown: 34,
                    page_down: 34,
                    pd: 34,
                    left: 37,
                    up: 38,
                    right: 39,
                    down: 40,
                    f1: 112,
                    f2: 113,
                    f3: 114,
                    f4: 115,
                    f5: 116,
                    f6: 117,
                    f7: 118,
                    f8: 119,
                    f9: 120,
                    f10: 121,
                    f11: 122,
                    f12: 123
                };
                var q = {
                    shift: {
                        wanted: false,
                        pressed: false
                    },
                    ctrl: {
                        wanted: false,
                        pressed: false
                    },
                    alt: {
                        wanted: false,
                        pressed: false
                    },
                    meta: {
                        wanted: false,
                        pressed: false
                    }
                };
                if (o.ctrlKey) {
                    q.ctrl.pressed = true;
                }
                if (o.shiftKey) {
                    q.shift.pressed = true;
                }
                if (o.altKey) {
                    q.alt.pressed = true;
                }
                if (o.metaKey) {
                    q.meta.pressed = true;
                }
                for (var j = 0; k = s[j], j < s.length; j++) {
                    if (k == "ctrl" || k == "control") {
                        r++;
                        q.ctrl.wanted = true;
                    } else {
                        if (k == "shift") {
                            r++;
                            q.shift.wanted = true;
                        } else {
                            if (k == "alt") {
                                r++;
                                q.alt.wanted = true;
                            } else {
                                if (k == "meta") {
                                    r++;
                                    q.meta.wanted = true;
                                } else {
                                    if (k.length > 1) {
                                        if (m[k] == code) {
                                            r++;
                                        }
                                    } else {
                                        if (d.keycode) {
                                            if (d.keycode == code) {
                                                r++;
                                            }
                                        } else {
                                            if (n == k) {
                                                r++;
                                            } else {
                                                if (p[n] && o.shiftKey) {
                                                    n = p[n];
                                                    if (n == k) {
                                                        r++;
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
                if (r == s.length && q.ctrl.pressed == q.ctrl.wanted && q.shift.pressed == q.shift.wanted && q.alt.pressed == q.alt.wanted && q.meta.pressed == q.meta.wanted) {
                    h(o);
                    if (!d.propagate) {
                        o.cancelBubble = true;
                        o.returnValue = false;
                        if (o.stopPropagation) {
                            o.stopPropagation();
                            o.preventDefault();
                        }
                        return false;
                    }
                }
            };
        this.all_shortcuts[b] = {
            callback: e,
            target: f,
            event: d.type
        };
        if (f.addEventListener) {
            f.addEventListener(d.type, e, false);
        } else {
            if (f.attachEvent) {
                f.attachEvent("on" + d.type, e);
            } else {
                f["on" + d.type] = e;
            }
        }
    };
    this.remove = function (a) {
        a = a.toLowerCase();
        var d = this.all_shortcuts[a];
        delete(this.all_shortcuts[a]);
        if (!d) {
            return;
        }
        var b = d.event;
        var c = d.target;
        var e = d.callback;
        if (c.detachEvent) {
            c.detachEvent("on" + b, e);
        } else {
            if (c.removeEventListener) {
                c.removeEventListener(b, e, false);
            } else {
                c["on" + b] = false;
            }
        }
    };
}
function initShortCut() {
    var a = new ShortCut();
    var b = {
        type: "keydown",
        propagate: false,
        disable_in_input: true
    };
    a.add("Alt+1", function () {
        useShortCutAddNewQ = true;
        createFreQ("radio");
    });
    a.add("Alt+2", function () {
        useShortCutAddNewQ = true;
        createFreQ("check");
    });
    a.add("Alt+3", function () {
        useShortCutAddNewQ = true;
        createFreQ("likert");
    });
    a.add("Ctrl+S", function () {
        save_paper("edit", true);
    });
    a.add("Ctrl+Z", function () {
        undo();
    }, b);
    a.add("Ctrl+Y", function () {
        redo();
    }, b);
    a.add("End", function () {
        if (questionHolder.length > 0) {
            questionHolder[questionHolder.length - 1].scrollIntoView();
        }
    }, b);
    a.add("Home", function () {
        if (questionHolder.length > 0) {
            questionHolder[0].scrollIntoView();
        }
    }, b);
}
initShortCut();
var getStyle = function (b, a) {
        if (!+"\v1") {
            a = a.replace(/\-(\w)/g, function (d, e) {
                return e.toUpperCase();
            });
            var c = b.currentStyle[a];
            (c == "auto") && (c = "0px");
            return c;
        } else {
            return document.defaultView.getComputedStyle(b, null).getPropertyValue(a);
        }
    };
var Drag = function (s) {
        var d = s,
            I = document.documentMode ? document.documentMode == 5 : document.compatMode && document.compatMode != "CSS1Compat",
            g = arguments[1] || {},
            u = g.container || document.documentElement,
            F = g.limit,
            r = g.lockX,
            q = g.lockY,
            G = g.ghosting,
            H = g.handle,
            e = g.revert,
            a = g.scroll,
            B = g.coords,
            D = g.onStart ||
        function () {}, t = g.onDrag ||
        function () {}, o = g.onEnd ||
        function () {}, z = parseFloat(getStyle(d, "margin-left")), h = parseFloat(getStyle(d, "margin-right")), f = parseFloat(getStyle(d, "margin-top")), m = parseFloat(getStyle(d, "margin-bottom")), c, j, x, y, E, b;
        d.lockX = getCoords(d).left;
        d.lockY = getCoords(d).top;
        if (H) {
            c = new RegExp("(^|\\s)" + H + "(\\s|$)");
            for (var A = 0, w = d.childNodes.length; A < w; A++) {
                var n = d.childNodes[A];
                if (n.nodeType == 1 && c.test(n.className)) {
                    j = n;
                    break;
                }
            }
        }
        b = (j || d).innerHTML;
        var v = function (J) {
                J = J || window.event;
                var l = mouseCoords(J);
                d.clickOriginY = l.y;
                var K = getCoords(d);
                d.offset_x = l.x - K.left;
                d.offset_y = l.y - K.top;
                document.onmouseup = p;
                document.onmousemove = C;
                if (G) {
                    x = d.cloneNode(false);
                    d.parentNode.insertBefore(x, d.nextSibling);
                    if (j) {
                        j = j.cloneNode(false);
                        x.appendChild(j);
                    }
                    x.style.position = "absolute";
                    x.style.background = "#ffff66";
                    x.style.width = d.offsetWidth + "px";
                    x.style.height = d.offsetHeight + "px";
                    x.style.display = "none";
                    x.innerHTML = d.dataNode._title;
                    !+"\v1" ? x.style.filter = "alpha(opacity=50)" : x.style.opacity = 0.5;
                }(x || d).style.zIndex = ++Drag.z;
                D();
                return false;
            };
        var C = function (N) {
                N = N || window.event;
                var Q = mouseCoords(N);
                if (Math.abs(d.clickOriginY - Q.y) <= 3) {
                    return;
                }
                window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
                var Q = mouseCoords(N);
                E = Q.x - d.offset_x;
                y = Q.y - d.offset_y;
                x.style.display = "";
                x.style.cursor = "pointer";
                d.style.visibility = "hidden";
                if (a) {
                    var R = I ? document.body : document.documentElement;
                    R = g.container.parentNode || R;
                }
                if (F) {
                    var l = E + d.offsetWidth,
                        M = y + d.offsetHeight,
                        K = getCoords(u),
                        J = K.left,
                        P = K.top,
                        L = J + u.clientWidth,
                        O = P + u.clientHeight;
                    E = Math.max(E, J);
                    y = Math.max(y, P);
                    if (l > L) {
                        E = L - d.offsetWidth - z - h;
                    }
                    if (M > O) {
                        y = O - d.offsetHeight - f - m;
                    }
                }
                r && (E = d.lockX);
                q && (y = d.lockY);
                (x || d).style.left = E + "px";
                (x || d).style.top = y + "px";
                B && ((j || x || d).innerHTML = E + " x " + y);
                t(N, d);
            };
        var p = function (l) {
                document.onmouseup = null;
                document.onmousemove = null;
                x && d.parentNode.removeChild(x);
                d.style.visibility = "visible";
                if (e) {
                    d.style.left = d.lockX + "px";
                    d.style.top = d.lockY + "px";
                }
                o();
            };
        Drag.z = 999;
        (j || d).onmousedown = v;
    };

function makeDrag(a) {
    new Drag(a, {
        container: questions,
        limit: true,
        ghosting: true,
        lockX: true,
        scroll: true,
        onDrag: moveQ,
        handle: "div_preview"
    });
}
if (!isMergeAnswer) {
    for (var i = 0; i < questionHolder.length; i++) {
        makeDrag(questionHolder[i]);
    }
}
function moveQ(h, b) {
    var o = mouseCoords(h);
    var p = o.y - b.offset_y;
    var n = p + b.offsetHeight / 2;
    for (var f = questionHolder.length - 1; f >= 0; f--) {
        var a = questionHolder[f];
        if (a == b) {
            continue;
        }
        var m = getCoords(a);
        var j = m.top;
        if (n > j && n < j + questionHolder[f].offsetHeight) {
            if (p > j) {
                if (b._referDivQ) {
                    var g = parseInt(a.dataNode._topic) - 1;
                    var c = b._referDivQ.dataNode._topic;
                    if (g < c) {
                        var l = "选项";
                        if (b.dataNode._type == "matrix" || b.dataNode._type == "sum") {
                            l = "行标题";
                        }
                        show_status_tip("此题" + l + "来源于第" + c + "题的选中项，不能再将此题移到第" + c + "题上方！", 4000);
                        a.onmouseout();
                        return;
                    }
                }
                b.parentNode.insertBefore(b, a);
                recreateEditor(b);
                recreateEditor(a);
                questionHolder.moveTo(b, questionHolder.indexOf(a));
            } else {
                if (b._referedArray) {
                    var d = "";
                    var g = parseInt(a.dataNode._topic) + 1;
                    for (var f = 0; f < b._referedArray.length; f++) {
                        var c = b._referedArray[f].dataNode._topic;
                        if (g - c > 0) {
                            var l = "选项";
                            if (b._referedArray[f].dataNode._type == "matrix" || b._referedArray[f].dataNode._type == "sum") {
                                l = "行标题";
                            }
                            show_status_tip("第" + c + "题的" + l + "来源于此题的选中项，不能将此题移到第" + c + "题下方！", 4000);
                            return;
                        }
                    }
                }
                a.parentNode.insertBefore(b, a);
                a.parentNode.insertBefore(a, b);
                recreateEditor(b);
                recreateEditor(a);
                questionHolder.moveTo(b, questionHolder.indexOf(a));
            }
            updateTopic();
        }
    }
}