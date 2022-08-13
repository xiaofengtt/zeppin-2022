var toolbar_start = "<ul class='stuff'>";
var toolbar_add = "<li><a href='javascript:void(0);' onclick='editQ(this);' title='您也可以双击题目来进行编辑'><span class='design-icon design-edit'></span><span>编辑</span></a></li>";
var toolbar_copy = "<li><a href='javascript:void(0);' onclick='curover.copyQ();' title='复制此题'><span class='design-icon design-copy'></span><span>复制</span></a></li>";
var toolbar_moveup = "<li><a href='javascript:void(0);' onclick='curover.moveUp();'  title='将此题上移'><span class='design-icon design-up'></span><span>上移</span></a></li>";
var toolbar_movedown = "<li><a href='javascript:void(0);' onclick='curover.moveDown();'  title='将此题下移'><span class='design-icon design-down'></span><span>下移</span></a></li>";
var toolbar_movefirst = "<li><a href='javascript:void(0);' onclick='curover.moveFirst();'  title='将此题移动到第一题'><span class='design-icon design-first'></span><span>最前</span></a></li>";
var toolbar_movelast = "<li><a href='javascript:void(0);' onclick='curover.moveLast();'  title='将此题移动到最后一题'><span class='design-icon design-last'></span><span>最后</span></a></li>";
var toolbar_del = "<li><a href='javascript:void(0);' onclick='curover.deleteQ();'  title='删除此题'><span class='design-icon design-delete'></span><span>删除</span></a></li>";
var toolbar_del_move = toolbar_del + toolbar_moveup + toolbar_movedown + toolbar_movefirst + toolbar_movelast;
var toolbar_end = "<div style='clear:both;'></div></ul>";
Array.prototype.indexOf = function (c) {
    for (var b = 0, a = this.length; b < a; b++) {
        if (this[b] == c) {
            return b;
        }
    }
    return -1;
};
Array.prototype.moveUp = function (b) {
    var a = this.indexOf(b);
    return this._moveElement(a, -1);
};
Array.prototype.moveFirst = function (b) {
    var a = this.indexOf(b);
    while (this._moveElement(a--, -1)) {}
};
Array.prototype.moveDown = function (b) {
    var a = this.indexOf(b);
    return this._moveElement(a, 1);
};
Array.prototype.moveLast = function (b) {
    var a = this.indexOf(b);
    while (this._moveElement(a++, 1)) {}
};
Array.prototype.moveTo = function (d, e) {
    var a = this.indexOf(d);
    var c = Math.abs(e - a);
    if (a < e) {
        for (var b = 0; b < c; b++) {
            this._moveElement(a++, 1);
        }
    } else {
        for (var b = 0; b < c; b++) {
            this._moveElement(a--, -1);
        }
    }
};
Array.prototype._moveElement = function (a, d) {
    var c, b;
    if (a < 0 || a >= this.length) {
        return false;
    }
    c = a + d;
    if (c < 0 || c >= this.length || c == a) {
        return false;
    }
    b = this[c];
    this[c] = this[a];
    this[a] = b;
    return true;
};
Array.prototype.insertAt = function (b, a) {
    this.splice(a, 0, b);
};
Array.prototype.insertBefore = function (b, a) {
    this.insertAt(b, this.indexOf(a));
};
Array.prototype.remove = function (a) {
    this.removeAt(this.indexOf(a));
    return a;
};
Array.prototype.removeAt = function (a) {
    var b = this[a];
    if (b) {
        this.splice(a, 1);
    }
    return b;
};

function updateTopic(k) {
    var a = 1;
    var b = 1;
    firstPage.divTopic.innerHTML = "<span style='font-size:14px; font-weight:bold;'>第" + (b) + "页/共" + total_page + "页</span>";
    b++;
    for (var e = 0; e < questionHolder.length; e++) {
        var d = questionHolder[e].dataNode;
        var f = d._type;
        if (f != "page" && f != "cut") {
            if (d._topic - a != 0) {
                if (d._hasjump) {
                    if (d._anytimejumpto - 1 > 0) {
                        d._anytimejumpto = parseInt(d._anytimejumpto) + (a - d._topic);
                        if (questionHolder[e].setAnyTimeJumpTo) {
                            questionHolder[e].setAnyTimeJumpTo();
                        }
                    } else {
                        if (d._select) {
                            for (var m = 1; m < d._select.length; m++) {
                                if (d._select[m]._item_jump - 1 > 0) {
                                    d._select[m]._item_jump = parseInt(d._select[m]._item_jump) + (a - d._topic);
                                }
                            }
                            if (questionHolder[e].setItemJump) {
                                questionHolder[e].setItemJump();
                            }
                        }
                    }
                } else {
                    if (d._relation) {
                        var j = d._relation.split(",");
                        var h = parseInt(j[0]);
                        var n = curinsert && curinsert.dataNode._topic < h;
                        if (!n) {
                            n = curover && curover.dataNode._topic < h;
                        }
                        if (n) {
                            h = h + (a - d._topic);
                            var c = h + "," + j[1];
                            d._relation = c;
                            var g = questionHolder[e].getRelation();
                            var l = questionHolder[e].RelationIns;
                            if (g) {
                                l.innerHTML = g[0];
                                l.title = g[1];
                            }
                            curinsert = null;
                        }
                    }
                }
            }
            d._topic = a;
            if (questionHolder[e].divTopic) {
                questionHolder[e].divTopic.innerHTML = a + ".";
            }
            if (questionHolder[e]._referDivQ) {
                if (questionHolder[e].createTableRadio) {
                    questionHolder[e].createTableRadio();
                } else {
                    if (questionHolder[e].createSum) {
                        questionHolder[e].createSum();
                    }
                }
                if (questionHolder[e].updateReferQ) {
                    questionHolder[e].updateReferQ();
                    questionHolder[e].updateSelCheck();
                }
            }
            a++;
        } else {
            if (f == "page") {
                questionHolder[e].dataNode._topic = b;
                questionHolder[e].divTopic.innerHTML = "<span style='font-size:14px; font-weight:bold;'>第" + b + "页/共" + total_page + "页</span>";
                b++;
            }
        }
    }
}
function recreateEditor(a) {
    if (a.hasCreatedAttr) {
        if (!KE.browser.IE) {
            var b = a.titleId;
            KE.remove(b);
            KE.create(b);
            KE.util.focus(b);
        }
    }
}
function setMoveStyle(b) {
    var a = b;
    setTimeout(function () {
        a.className = "div_question div_question_move";
        if (prevcurmove && prevcurmove != a && prevcurmove.className.toLowerCase() == "div_question div_question_move") {
            prevcurmove.className = "div_question div_question_mouseout";
        }
        prevcurmove = a;
        prevcurmove.divTableOperation.parentNode.style.visibility = "hidden";
    }, 2);
}
function afterMove(b, a) {
    recreateEditor(b);
    recreateEditor(a);
    updateTopic(b.dataNode._type);
    b.onmouseout();
    b.focus();
    setMoveStyle(b);
}
function moveUp() {
    var c = this.dataNode._type;
    var b = c == "page" || c == "cut";
    if (isMergeAnswer && !b) {
        alert("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许上移题目！");
        return;
    }
    if (this._referDivQ) {
        var e = parseInt(this.dataNode._topic) - 1;
        var g = this._referDivQ.dataNode._topic;
        if (e <= g) {
            var d = "选项";
            if (this.dataNode._type == "matrix" || this.dataNode._type == "sum") {
                d = "行标题";
            }
            show_status_tip("此题" + d + "来源于第" + g + "题的选中项，不能再将此题移到第" + g + "题上方！", 4000);
            return;
        }
    }
    var a = questionHolder.indexOf(this);
    if (a > 0) {
        var f = questionHolder[a - 1];
        this.parentNode.insertBefore(this, f);
        questionHolder.moveUp(this);
        afterMove(this, f);
    } else {
        show_status_tip("第1题不能再上移", 3000);
    }
}
function moveDown() {
    var e = this.dataNode._type;
    var g = e == "page" || e == "cut";
    if (isMergeAnswer && !this.isMergeNewAdded && !g) {
        alert("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许下移题目！");
        return;
    }
    if (this._referedArray) {
        var c = "";
        var f = parseInt(this.dataNode._topic) + 1;
        for (var d = 0; d < this._referedArray.length; d++) {
            var a = this._referedArray[d].dataNode._topic;
            if (f - a >= 0) {
                var j = "选项";
                if (this._referedArray[d].dataNode._type == "matrix" || this._referedArray[d].dataNode._type == "sum") {
                    j = "行标题";
                }
                show_status_tip("第" + a + "题的" + j + "来源于此题的选中项，不能将此题移到第" + a + "题下方！", 4000);
                return;
            }
        }
    }
    var h = questionHolder.indexOf(this);
    if (h < questionHolder.length - 1) {
        var b = questionHolder[h + 1];
        this.parentNode.insertBefore(b, this);
        questionHolder.moveDown(this);
        afterMove(this, b);
    } else {
        show_status_tip("最后1题不能再下移", 3000);
    }
}
function moveFirst() {
    var c = this.dataNode._type;
    var b = c == "page" || c == "cut";
    if (isMergeAnswer && !b) {
        alert("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许上移题目！");
        return;
    }
    if (this._referDivQ) {
        var d = "选项";
        if (this.dataNode._type == "matrix" || this.dataNode._type == "sum") {
            d = "行标题";
        }
        show_status_tip("此题" + d + "来源于第" + this._referDivQ.dataNode._topic + "题的选中项，不能将此题移动最前！", 4000);
        return;
    }
    var a = questionHolder.indexOf(this);
    if (a > 0) {
        var e = questionHolder[0];
        this.parentNode.insertBefore(this, e);
        questionHolder.moveFirst(this);
        afterMove(this, e);
    } else {
        show_status_tip("第1题不能再上移", 3000);
    }
}
function moveLast() {
    if (this._referedArray) {
        var a = "";
        for (var c = 0; c < this._referedArray.length; c++) {
            if (c > 0) {
                a += ",";
            }
            a += this._referedArray[c].dataNode._topic;
        }
        show_status_tip("第" + a + "题的选项或行标题来源于此题的选中项，不能将此题移动到最后！", 4000);
        return;
    }
    var b = questionHolder.indexOf(this);
    if (b < questionHolder.length - 1) {
        var d = questionHolder[questionHolder.length - 1];
        this.parentNode.insertBefore(this, d);
        this.parentNode.insertBefore(d, this);
        questionHolder.moveLast(this);
        afterMove(this, d);
    } else {
        show_status_tip("最后1题不能再下移", 3000);
    }
}
function insertQ(c) {
    var a = curinsert == c;
    if (a) {
        resetInsertQ();
    } else {
        curinsert = c;
        var b = $$("a", curinsert.divInsertOp)[0];
        if (b) {
            b.innerHTML = "取消插入点";
        }
        setMoveStyle(curinsert);
        show_status_tip("请在页面上方选择相应的题型插入到此题的后面", 5000);
    }
}
function resetInsertQ() {
    if (curinsert != null) {
        if (curinsert.className.toLowerCase() == "div_question div_question_move") {
            curinsert.className = "div_question div_question_mouseout";
        }
        var a = $$("a", curinsert.divInsertOp)[0];
        if (a) {
            a.innerHTML = "在此题后插入新题";
        }
        curinsert = null;
    }
}
function change_dataNode(g, d) {
    var b = new Object();
    b._isTouPiao = false;
    b._isCeShi = false;
    b._isCePing = false;
    var f = false;
    var e = false;
    if (d == "likert") {
        b._tag = 2;
        b._type = "radio";
    } else {
        if (d == "order") {
            b._tag = 1;
            b._type = "check";
            b._upLimit = b._lowLimit = "-1";
        } else {
            if (d.indexOf("matrix") > -1) {
                var h = d.split(",")[1];
                b._type = "matrix";
                b._tag = h;
                b._rowtitle = g._rowtitle;
                b._rowtitle2 = g._rowtitle2;
                if (g._columntitle) {
                    b._columntitle = g._columntitle;
                }
                b._rowwidth = g._rowwidth;
                b._rowwidth2 = g._rowwidth2;
                if (h == "202" || h == "301") {
                    b._minvalue = 0;
                    b._maxvalue = 10;
                }
                if (h - 101 <= 0 || h == "303") {
                    e = true;
                }
            } else {
                if (d == "toupiaoradio" || d == "toupiaocheck") {
                    b._type = d == "toupiaoradio" ? "radio" : "check";
                    b._isTouPiao = true;
                    b._touPiaoWidth = g._touPiaoWidth || 50;
                    b._displayTiao = g._displayTiao || true;
                    b._displayNum = g._displayNum || true;
                    b._displayPercent = g._displayPercent || true;
                } else {
                    if (d == "ceshiradio" || d == "ceshicheck") {
                        b._type = d == "ceshiradio" ? "radio" : "check";
                        b._isCeShi = true;
                        b._ceshiValue = 5;
                        b._ceshiDesc = "";
                        f = true;
                    } else {
                        if (d == "cepingradio" || d == "cepingcheck") {
                            b._type = d == "cepingradio" ? "radio" : "check";
                            b._isCePing = true;
                        } else {
                            b._type = d;
                            b._tag = 0;
                        }
                    }
                }
            }
        }
    }
    b._verify = "0";
    b._topic = g._topic;
    b._title = g._title;
    b._requir = g._requir;
    b._ins = g._ins;
    b._hasjump = g._hasjump;
    b._anytimejumpto = g._anytimejumpto;
    b._keyword = g._keyword;
    if (g._type == "question") {
        b._hasvalue = false;
        b._randomChoice = false;
        b._isTouPiao = false;
        b._isCeShi = false;
        b._numperrow = "1";
        b._select = new Array();
        for (var a = 1; a < 3; a++) {
            b._select[a] = new Object();
            b._select[a]._item_title = "选项" + a;
            b._select[a]._item_radio = false;
            b._select[a]._item_value = 0;
            b._select[a]._item_jump = 0;
            b._select[a]._item_tb = false;
            b._select[a]._item_tbr = false;
            b._select[a]._item_img = "";
            b._select[a]._item_imgtext = false;
            b._select[a]._item_desc = "";
            b._select[a]._item_label = "";
        }
        if (d == "likert") {
            b._hasvalue = true;
        }
        return b;
    } else {
        if (d == "question") {
            b._height = "1";
            b._maxword = "";
            b._minword = "";
            b._width = "";
            b._hasList = false;
            b._listId = "";
            b._underline = false;
            b._norepeat = false;
            b._default = "";
            return b;
        } else {
            b._hasvalue = false;
            if (b._isCePing) {
                b._hasvalue = true;
            }
            if (d.indexOf("matrix") > -1) {
                b._hasvalue = e;
            }
            b._randomChoice = g._randomChoice || false;
            b._numperrow = g._numperrow || 0;
            b._select = g._select;
            if (g._type == "check" || d == "likert" || d == "order" || b._isCePing) {
                for (var c = 1; c < b._select.length; c++) {
                    b._select[c]._item_radio = false;
                }
            }
            if (d == "order") {
                for (var c = 1; c < b._select.length; c++) {
                    b._select[c]._item_tb = false;
                    b._select[c]._item_tbr = false;
                }
            }
            if (d == "check" && b._hasjump && b._anytimejumpto == "0") {
                b._hasjump = false;
            }
            if (d == "likert" || e) {
                for (var c = 1; c < b._select.length; c++) {
                    b._select[c]._item_value = c;
                }
                b._hasvalue = true;
            }
            if (f) {
                b._hasvalue = false;
                b._hasjump = false;
                for (var c = 1; c < b._select.length; c++) {
                    if (c == 1) {
                        b._select[c]._item_radio = true;
                    } else {
                        b._select[c]._item_radio = false;
                    }
                    b._select[c]._item_tb = false;
                }
            }
            return b;
        }
    }
}
function changeQ(d) {
    cur.validate();
    if (cur._referDivQ) {
        var c = "选项";
        if (cur.dataNode._type == "matrix" || cur.dataNode._type == "sum") {
            c = "行标题";
        }
        show_status_tip("此题" + c + "来源于第" + cur._referDivQ.dataNode._topic + "题的选中项，不能转换题型！", 4000);
        return;
    }
    if (cur._referedArray) {
        var a = "";
        for (var b = 0; b < cur._referedArray.length; b++) {
            if (b > 0) {
                a += ",";
            }
            a += cur._referedArray[b].dataNode._topic;
        }
        show_status_tip("第" + a + "题的选项或行标题来源于此题的选中项，不能转换题型！", 4000);
        return;
    }
    if (cur.checkValid()) {
        var e = copyNode(cur.dataNode);
        var f = change_dataNode(e, d);
        f._topic = cur.dataNode._topic;
        createQ(f, true);
    }
}
function createFreQ(f, h, c) {
    var b = createFreQdataNode(f, h, c);
    var e = createQ(b);
    var d = !b._tag && (b._type == "radio" || b._type == "radio_down");
    var a = b._type == "check";
    var g = /^[a-zA-Z_]+$/.test(f);
    if ((d || a) && g) {
        e.newAddQ = true;
    }
}
function createFromText() {
    if (isMergeAnswer) {
        alert("很抱歉，您正在以合并答卷模式编辑问卷，不能使用此功能！");
    } else {
        if (confirm("您确定要放弃对此问卷的更改并重新生成此问卷吗？")) {
            windowGotoUrl("/MySojump/DesignQbytxt.aspx?activity=" + activityID);
        }
    }
}
function createFreQdataNode(C, w, s) {
    var A;
    var d;
    var a = "请在此输入问题标题";
    var n = "question§1§";
    var g = "§0§1§§false§false§§§false§§" + C;
    var o = "";
    if (C == "check" && s) {
        o = "-1";
    }
    var z = "请选择您认为正确的答案？";
    var l = "2009最受关注的中文网站/应用/服务";
    var F = "淘宝网〒false〒0〒0〒false〒false〒〒false〒〒§开心网〒false〒1〒0〒false〒false〒〒false〒〒§百度〒false〒1〒0〒false〒false〒〒false〒〒§腾讯〒false〒1〒0〒false〒false〒〒false〒〒§人人网〒false〒1〒0〒false〒false〒〒false〒〒";
    var k = "选项" + (select_item_num + 1) + "〒false〒1〒0〒false〒false〒〒false〒〒§选项" + (select_item_num + 2) + "〒false〒2〒0〒false〒false〒〒false〒〒";
    var h = "选项1〒true〒0〒0〒false〒false〒〒false〒〒§选项2〒false〒1〒0〒false〒false〒〒false〒〒";
    var i = "很不满意〒false〒1〒0§不满意〒false〒2〒0§一般〒false〒3〒0§满意〒false〒4〒0§很满意〒false〒5〒0";
    var E = "";
    var p = false;
    var r = "§§§§200§true§";
    var j = "";
    switch (C) {
    case "姓名":
        E = "您的姓名：";
        p = true;
        j = r;
        break;
    case "电话":
        E = "您常用的电话号码：";
        p = true;
        j = r;
        break;
    case "Email":
        E = "您常用的Email地址：";
        p = true;
        j = r;
        break;
    case "入学时间":
        E = "您入学的时间：";
        p = true;
        break;
    case "城市单选":
    case "城市多选":
        E = "请选择城市:";
        p = true;
        break;
    case "省市区":
        E = "请选择省份城市与地区:";
        p = true;
        break;
    case "生日":
        E = "请输入您的出生日期：";
        p = true;
        break;
    case "手机":
        E = "请输入您的手机号码：";
        p = true;
        j = r;
        break;
    case "网址":
        E = "请输入网址：";
        p = true;
        break;
    case "学号":
        E = "请输入您的学号：";
        p = true;
        j = r;
        break;
    case "高校":
        E = "您所在或者毕业的高校名称：";
        p = true;
        break;
    case "QQ":
        E = "请输入您的QQ号：";
        p = true;
        j = r;
        break;
    case "MSN":
        E = "请输入您的MSN：";
        p = true;
        j = r;
        break;
    case "身份证号":
        E = "请输入您的身份证号：";
        p = true;
        j = r;
        break;
    case "兴趣爱好":
        E = "请输入您的兴趣爱好：";
        p = true;
        break;
    case "地址":
        E = "请输入您的地址：";
        p = true;
        j = "§§§§400§true§";
        break;
    case "院系":
        E = "请输入您的院系：";
        p = true;
        j = r;
        break;
    case "班级":
        E = "请输入您的班级：";
        p = true;
        j = r;
        break;
    case "公司名称":
        E = "请输入您的公司名称：";
        p = true;
        j = "§§§§400§true§";
        break;
    case "公司网址":
        E = "请输入您的公司网址：";
        p = true;
        j = "§§§§400§true§";
        break;
    case "部门":
        E = "请输入您的部门：";
        p = true;
        j = r;
        break;
    case "员工编号":
        E = "请输入您的员工编号：";
        p = true;
        j = r;
        break;
    case "数字":
        E = "请输入您的卡号：";
        p = true;
        j = r;
        break;
    case "日期":
        E = "请选择日期：";
        p = true;
        break;
    case "滑动":
        E = "请选择您的评分：";
        p = true;
        break;
    case "性别":
        A = "radio§3§您的性别：§0§8§false§false§§true§0000§§性别§§§男〒false〒0〒0§女〒false〒0〒0";
        break;
    case "年龄段":
        A = "radio§3§您的年龄段：§0§8§false§false§§true§0000§§年龄段§§§15岁以下〒false〒0〒0§15~20〒false〒0〒0§21~25〒false〒0〒0§26~30〒false〒0〒0§31~40〒false〒0〒0§41~50〒false〒0〒0§51~60〒false〒0〒0§60以上〒false〒0〒0";
        break;
    case "城市":
        A = "radio§6§您目前常住的城市：§0§7§false§false§§false§0000§§城市§§§北京〒false〒0〒0§上海〒false〒0〒0§香港〒false〒0〒0§重庆〒false〒0〒0§杭州〒false〒0〒0§武汉〒false〒0〒0§长沙〒false〒0〒0§广州〒false〒0〒0§深圳〒false〒0〒0§南宁〒false〒0〒0§贵阳〒false〒0〒0§海口〒false〒0〒0§石家庄〒false〒0〒0§哈尔滨〒false〒0〒0§郑州〒false〒0〒0§福州〒false〒0〒0§兰州〒false〒0〒0§南京〒false〒0〒0§南昌〒false〒0〒0§长春〒false〒0〒0§沈阳〒false〒0〒0§呼和浩特〒false〒0〒0§银川〒false〒0〒0§西宁〒false〒0〒0§济南〒false〒0〒0§太原〒false〒0〒0§合肥〒false〒0〒0§西安〒false〒0〒0§成都〒false〒0〒0§天津〒false〒0〒0§乌鲁木齐〒false〒0〒0§拉萨〒false〒0〒0§昆明〒false〒0〒0§澳门〒false〒0〒0§台湾〒false〒0〒0§海外〒false〒0〒0§其他〒false〒0〒0〒true〒false〒〒false〒〒";
        break;
    case "专业":
        A = "radio_down§7§您攻读的专业类别：§0§1§false§false§§false§0000§§专业§§§电信〒false〒0〒0§计算机〒false〒0〒0§电子〒false〒0〒0§机械〒false〒0〒0§自动控制〒false〒0〒0§文学〒false〒0〒0§金融管理〒false〒0〒0§营销〒false〒0〒0§法律〒false〒0〒0§其他类〒false〒0〒0";
        break;
    case "学位":
        A = "radio§8§您正在攻读或已获得的最高学位：§0§6§false§false§§true§0000§§学位§§§小学以下〒false〒0〒0§初中〒false〒0〒0§高中〒false〒0〒0§中专〒false〒0〒0§大专〒false〒0〒0§大学本科〒false〒0〒0§硕士研究生〒false〒0〒0§博士研究生〒false〒0〒0§博士以上〒false〒0〒0";
        break;
    case "行业":
        A = "radio_down§10§您目前从事的行业：§0§1§false§false§§true§0000§§行业§§§计算机软件〒false〒0〒0§计算机硬件§计算机服务(系统/数据/维修)〒false〒0〒0§家电〒false〒0〒0§通信/电信运营/网络设备/增值服务〒false〒0〒0§互联网/电子商务〒false〒0〒0§网络游戏〒false〒0〒0§教育/培训/科研/院校〒false〒0〒0§非盈利机构/政府〒false〒0〒0§电子技术/半导体/集成电路〒false〒0〒0§仪器仪表/工业自动化〒false〒0〒0§会计/审计〒false〒0〒0§证券/投资银行/风险基金〒false〒0〒0§银行〒false〒0〒0§保险〒false〒0〒0§贸易/进出口〒false〒0〒0§批发/零售〒false〒0〒0§快速消费品(食品,饮料,化妆品)〒false〒0〒0§服装/纺织/皮革〒false〒0〒0§家具/工艺品/玩具〒false〒0〒0§办公用品及设备〒false〒0〒0§机械/设备/重工〒false〒0〒0§汽车及零配件〒false〒0〒0§制药/生物工程〒false〒0〒0§医疗/护理/保健/卫生〒false〒0〒0§医疗设备/器械〒false〒0〒0§广告/公关/媒体/艺术〒false〒0〒0§出版/印刷/包装〒false〒0〒0§房地产开发〒false〒0〒0§建筑工程/装潢/设计〒false〒0〒0§物业管理/商业中心〒false〒0〒0§中介/咨询/猎头/认证〒false〒0〒0§法律〒false〒0〒0§餐饮/娱乐/旅游/酒店/生活服务〒false〒0〒0§交通/运输/物流〒false〒0〒0§航天/航空〒false〒0〒0§能源/化工〒false〒0〒0§农业/渔业/林业〒false〒0〒0§其他行业〒false〒0〒0";
        break;
    case "职业":
        A = "radio_down§11§您目前从事的职业：§0§1§false§false§§true§0000§§职业§§§全日制学生〒false〒0〒0§生产人员〒false〒0〒0§销售人员〒false〒0〒0§市场/公关人员〒false〒0〒0§客服人员〒false〒0〒0§行政/后勤人员〒false〒0〒0§人力资源〒false〒0〒0§财务/审计人员〒false〒0〒0§文职/办事人员〒false〒0〒0§技术/研发人员〒false〒0〒0§管理人员〒false〒0〒0§教师〒false〒0〒0§顾问/咨询〒false〒0〒0§专业人士(如会计师、律师、建筑师、医护人员、记者等)〒false〒0〒0§其他〒false〒0〒0";
        break;
    case "月收入":
        A = "radio§12§您目前的月收入：§0§4§false§false§§true§0000§§月收入§§§还没有收入〒false〒31〒0§2000以下〒false〒0〒0§2000~3000〒false〒0〒0§3001~5000〒false〒0〒0§5001~8000〒false〒0〒0§8001~15000〒false〒0〒0§15001~50000〒false〒0〒0§50000以上〒false〒0〒0";
        break;
    case "婚姻状况":
        A = "radio§19§您的婚姻状况：§0§8§false§false§§true§0000§§婚姻状况§§§未婚〒false〒0〒0§已婚〒false〒1〒0";
        break;
    case "省份":
        A = "radio§19§您所在的省份：§0§8§false§false§§true§0000§§省份§§§安徽〒false〒0〒0§北京〒false〒1〒0§重庆〒false〒1〒0§福建〒false〒1〒0§甘肃〒false〒1〒0§广东〒false〒1〒0§广西〒false〒1〒0§贵州〒false〒1〒0§海南〒false〒1〒0§河北〒false〒1〒0§黑龙江〒false〒1〒0§河南〒false〒1〒0§香港〒false〒1〒0§湖北〒false〒1〒0§湖南〒false〒1〒0§江苏〒false〒1〒0§江西〒false〒1〒0§吉林〒false〒1〒0§辽宁〒false〒1〒0§澳门〒false〒1〒0§内蒙古〒false〒1〒0§宁夏〒false〒1〒0§青海〒false〒1〒0§山东〒false〒1〒0§上海〒false〒1〒0§山西〒false〒1〒0§陕西〒false〒1〒0§四川〒false〒1〒0§台湾〒false〒1〒0§天津〒false〒1〒0§新疆〒false〒1〒0§西藏〒false〒1〒0§云南〒false〒1〒0§浙江〒false〒1〒0§海外〒false〒1〒0";
        break;
    case "工作年限":
        A = "radio§12§您的工作年限：§0§7§false§false§§true§0000§§工作年限§§§1年以下〒false〒0〒0§1~2年〒false〒0〒0§2~3年〒false〒0〒0§3~5年〒false〒0〒0§5~10年〒false〒0〒0§10~20年〒false〒0〒0§20年以上〒false〒0〒0";
        break;
    case "年收入":
        A = "radio§12§您目前的年收入：§0§5§false§false§§true§0000§§年收入§§§暂时还没有收入〒false〒31〒0§1万以下〒false〒0〒0§1—3万〒false〒0〒0§3—5万〒false〒0〒0§5—8万〒false〒0〒0§8—15万〒false〒0〒0§15—30万〒false〒0〒0§30—100万〒false〒0〒0§100万以上〒false〒0〒0";
        break;
    case "年级":
        A = "radio§2§您所在年级：§0§8§false§false§§false§0000§§年级§§§大一〒false〒0〒0§大二〒false〒1〒0§大三〒false〒1〒0§大四〒false〒1〒0";
        break;
    case "radio":
    case "radio_down":
    case "check":
        var b = C == "check" ? "true," + o + "," + o : "true";
        if (w) {
            k += "§其它〒false〒1〒0〒true〒false〒〒false〒〒";
        }
        var D = s || 0;
        A = C + "§1§" + a + "§" + s + "§1§false§false§§" + b + "§§§§§§" + k;
        break;
    case "toupiao":
        C = w == 1 ? "radio" : "check";
        A = C + "§1§" + l + "§" + s + "§1§false§false§§true§true〒50〒true〒true〒true§§§§§" + F;
        break;
    case "ceshi":
        if (w == 3) {
            A = "question§1§" + a + "§0§1§§true§false§§§false§§§false§§§§§§5〒请输入标准答案";
        } else {
            C = w == 1 ? "radio" : "check";
            A = C + "§1§" + z + "§" + s + "§1§true§false§§true§ceshi〒5〒§§§§§" + h;
        }
        break;
    case "ceping":
        C = w == 1 ? "radio" : "check";
        A = C + "§1§" + a + "§0§1§true§false§§true§ceping§§§§§" + k;
        break;
    case "boolean":
        A = C + "§1§" + a + "§0§8§true§false§§true§0000§§§§§是〒false〒1〒0§否〒false〒0〒0";
        break;
    case "likert":
        A = "radio§1§" + a + "§1§1§true§false§§true§0000§§§§§" + i;
        break;
    case "matrix":
        var B = w || 2;
        var c = "";
        var v = "";
        var x = 10;
        if (B == "202") {
            x = 100;
        }
        var f = B < 102 || B == 303;
        var e = f ? "true" : "false";
        if (w - 300 > 0) {
            c = "百度\n谷歌\n腾讯搜搜\n网易有道\n搜狐搜狗";
        }
        if (B == 101) {
            A = "matrix§1§请根据您的实际情况选择最符合的项：1-->5表示非常不满意-->非常满意§" + B + "§内向\n悲观〒〒" + c + "§" + e + "§false§§true§" + v + "〒〒0〒" + x + "§§§§§1〒false〒1〒0§2〒false〒2〒0§3〒false〒3〒0§4〒false〒4〒0§5〒false〒5〒0";
        } else {
            if (B == 102) {
                var m = "速度快〒false〒1〒0§准确率高〒false〒2〒0§信息量多〒false〒3〒0§界面更美观〒false〒4〒0";
                A = "matrix§1§请评议下面的搜索引擎：§" + B + "§百度\nGoogle\n搜狗〒〒" + c + "§" + e + "§false§§true§" + v + "〒〒0〒" + x + "§§§§§" + m;
            } else {
                A = "matrix§1§" + a + "§" + B + "§外观\n性能〒〒" + c + "§" + e + "§false§§true§" + v + "〒〒0〒" + x + "§§§§§" + i;
            }
        }
        break;
    case "邮寄地址":
        A = "matrix§1§请输入您的联系地址：§201§姓名：\n所在地区：\n街道地址:\n邮政编码:\n手机或固话：〒〒§false§false§§true§15%〒〒0,,,;1,省市区,,,50;2,,,,50;3,数字,,;4,电话,,〒10§§§§§" + i;
        break;
    case "question":
        var q = w || 1;
        var u = s || false;
        if (u) {
            show_status_tip("如果您需要填写者提交的答案在一个列表中（如会员列表），请勾选“ 要求填写者输入的答案在指定的列表中”复选框，并设置答案列表！");
        }
        A = "question§1§" + a + "§0§" + q + "§§false§false§§§false§§§" + u + "§§§§§";
        break;
    case "gapfill":
        A = "gapfill§1§姓名：" + getFillStr(3) + "&nbsp;&nbsp;&nbsp;&nbsp;年龄：" + GapFillStr + "岁<br/>电话：" + getFillStr(4) + "§0§true§1§§false§0§";
        break;
    case "slider":
        A = "slider§1§" + a + "§0§true§0§100§不满意§满意§§false§0§";
        break;
    case "sum":
        A = "sum§1§" + a + "§0§true§100§外观\n性能§15%§0§§false§0§";
        break;
    case "fileupload":
        A = "fileupload§1§" + a + "§0§true§200§" + defaultFileExt + "§1024§§false§0§";
        break;
    case "page":
        var t = w ? "true" : "";
        A = C + "§1§§§" + t;
        break;
    case "cut":
        var E = w ? w : "请在此输入说明文字";
        A = C + "§" + E + "§";
        break;
    }
    if (p) {
        A = n + E + g + j;
    }
    d = set_string_to_dataNode(A);
    return d;
}
function addNewQ(d, f, a, g) {
    var e;
    var c = d._type;
    e = create_question(d);
    setQHandler(e);
    setAttrHander(e);
    if (curinsert != null) {
        g = curinsert;
        resetInsertQ();
    }
    if (a) {
        g = curover;
    }
    if (!f) {
        if ((!isMergeAnswer || c == "page" || c == "cut") && g) {
            if (questions.lastChild == g) {
                questions.appendChild(e);
            } else {
                questions.insertBefore(e, g.nextSibling);
            }
            if (g == firstPage) {
                questionHolder.unshift(e);
            } else {
                questionHolder.insertBefore(e, g);
                questionHolder.moveUp(g);
            }
            updateTopic();
        } else {
            questions.appendChild(e);
            questionHolder.push(e);
            updateTopic();
        }
    } else {
        cur.parentNode.insertBefore(e, cur);
        e.isMergeNewAdded = cur.isMergeNewAdded;
        questionHolder[questionHolder.indexOf(cur)] = e;
        cur.deleteQ("noNeedConfirm");
        cur = null;
    }
    if (a && curover) {
        if (curover._referDivQ) {
            var b = curover._referDivQ;
            e._referDivQ = b;
            if (!b._referedArray) {
                b._referedArray = new Array();
            }
            if (b._referedArray.indexOf(e) == -1) {
                b._referedArray.push(e);
            }
            b._referedArray.sort(function (i, h) {
                return i.dataNode._topic - h.dataNode._topic;
            });
        }
    }
    return e;
}
function createQ(c, e, a) {
    if (cur) {
        cur.divTableOperation.parentNode.style.visibility = "hidden";
    }
    var d = addNewQ(c, e, a);
    if (!isMergeAnswer && window.makeDrag) {
        makeDrag(d);
    }
    d.createOp();
    var b = new Date().getTime();
    if (!useShortCutAddNewQ) {
        if (lastAddNewQTime && !e) {
            var f = b - lastAddNewQTime;
            if (f > 8000) {
                d.ondblclick();
            } else {
                if (cur && cur.isEditing) {
                    qonclick.call(cur);
                }
                setMoveStyle(d);
            }
        } else {
            d.ondblclick();
        }
    } else {
        setMoveStyle(d);
    }
    d.focus();
    if (c._isCePing || isQuestionLikert(c)) {
        totalPingFen++;
        setLiCat(true);
    }
    lastAddNewQTime = b;
    return d;
}
function copyQ() {
    if (this.validate) {
        this.validate();
    }
    if (!this.checkValid || this.checkValid()) {
        var a = copyNode(this.dataNode);
        a._hasjump = false;
        a._needOnly = false;
        a._hasList = false;
        a._listId = -1;
        a._referedTopics = "";
        createQ(a, false, true);
        show_status_tip("复制成功！", 4000);
    }
}
function copyNode(f) {
    var a = new Object();
    for (var c in f) {
        a[c] = f[c];
    }
    if (f._select) {
        a._select = new Array();
        for (var b = 1; b < f._select.length; b++) {
            a._select[b] = new Object();
            for (var d in f._select[b]) {
                a._select[b][d] = f._select[b][d];
            }
        }
    }
    return a;
}
function deleteQ(c) {
    var f = this.dataNode._type;
    var e = f == "page" || f == "cut";
    if (this._referedArray) {
        var b = "";
        for (var g = 0; g < this._referedArray.length; g++) {
            if (g > 0) {
                b += ",";
            }
            b += this._referedArray[g].dataNode._topic;
        }
        show_status_tip("第" + b + "题的选项或行标题来源于此题的选中项，不能删除此题！", 4000);
        return;
    }
    if (c != "noNeedConfirm") {
        if (isMergeAnswer && !this.isMergeNewAdded && !e) {
            show_status_tip("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许删除原始问卷中的题目！", 4000);
            return;
        }
        show_status_tip("可以通过Ctrl+Z恢复删除的问题", 4000);
    }
    if (f == "page") {
        total_page--;
    } else {
        if (f != "cut") {
            total_question--;
        }
    }
    if (this.tabAttr) {
        this.tabAttr.parentNode.removeChild(this.tabAttr);
        this.tabAttr = null;
        this.hasCreatedAttr = false;
    }
    if (this.clearReferQ) {
        this.clearReferQ();
    }
    if (this.clearReferedQ) {
        this.clearReferedQ();
    }
    if (c != "noNeedConfirm") {
        var d = questionHolder.indexOf(this);
    }
    this.className = "div_question div_question_mouseout";
    this.parentNode.removeChild(this);
    questionHolder.remove(this);
    if (this.dataNode._isCePing) {
        totalPingFen--;
    }
    updateTopic(this.dataNode._type);
    if (c != "noNeedConfirm") {
        var a = firstPage;
        if (d > 0) {
            a = questionHolder[d - 1];
        }
        new DeleteAction(this, a).exec();
    }
    if (cur == this) {
        cur = null;
    }
}
function qSelect() {}
function qonclick() {
    try {
        window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
    } catch (a) {}
    this.className = "div_question div_question_onclick";
    this.title = "";
    resetInsertQ();
    this.divInsertOp.style.display = "none";
    if (cur != this || (cur == this && cur.isEditing)) {
        if (cur != null) {
            if (cur.updateItem) {
                cur.updateItem();
            }
            cur.className = "div_question";
            if (cur.hasCreatedAttr) {
                cur.tabAttr.style.display = "none";
            }
            cur.isEditing = false;
            cur.ondblclick = qonclick;
            cur.style.cursor = "move";
            changeEditText(cur.editQLink, false);
        }
        if (cur && cur.createAttr && (cur.checkValid && !cur.checkValid())) {
            cur.validate();
        }
        if (cur == this) {
            cur.focus();
            cancelInputClick(this);
            return false;
        }
    }
    cancelInputClick(this);
    cur = this;
    var b = this.dataNode;
    this.isEditing = true;
    this.ondblclick = null;
    hasDisplayEditTip = true;
    this.style.cursor = "default";
    changeEditText(this.editQLink, true);
    if (this.hasCreatedAttr) {
        this.tabAttr.style.display = "";
        cancelDblClick(this);
        if (this.hasDisplaySelCheck) {
            this.updateSelCheck();
        }
        this.focus();
        var e = 0;
        var d = document.documentElement.clientHeight || document.body.clientHeight;
        if (this.div_question_preview.offsetHeight > d) {
            e = this.offsetTop + this.div_question_preview.offsetHeight - 120;
        } else {
            e = this.offsetTop - this.div_question_preview.offsetHeight;
        }
        divSurvey.scrollTop = e;
    } else {
        if (this.createAttr) {
            var c = this;
            setTimeout(function () {
                c.createAttr();
                cancelDblClick(c);
                c.setDataNodeToDesign();
                c.focus();
                var g = 0;
                var f = document.documentElement.clientHeight || document.body.clientHeight;
                if (c.div_question_preview.offsetHeight > f) {
                    g = c.offsetTop + c.div_question_preview.offsetHeight - 120;
                } else {
                    g = c.offsetTop - c.div_question_preview.offsetHeight;
                }
                divSurvey.scrollTop = g;
            }, 0);
        } else {
            show_status_tip("正在加载问题属性，请耐心等待....", 4000);
        }
    }
    return false;
}
function editQ(a) {
    if (curover) {
        qonclick.call(curover);
    }
}
function changeEditText(c, a) {
    if (!c) {
        return;
    }
    if (a) {
        var b = c.nextSibling;
        if (b.nodeType == 3) {
            b = b.nextSibling;
        }
        b.innerHTML = "完成";
        c.className = "design-icon design-finish";
    } else {
        c.className = "design-icon design-edit";
        var b = c.nextSibling;
        if (b.nodeType == 3) {
            b = b.nextSibling;
        }
        b.innerHTML = "编辑";
    }
}
function createOp() {
    if (this.divTableOperation.OpCreated) {
        this.divTableOperation.parentNode.style.visibility = "visible";
    } else {
        this.deleteQ = deleteQ;
        this.copyQ = copyQ;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveFirst = moveFirst;
        this.moveLast = moveLast;
        var c = this.divTableOperation;
        var a = this.dataNode._type;
        if (a == "page") {
            if (this.dataNode._topic > 1) {
                c.innerHTML = toolbar_start + toolbar_add + toolbar_del_move + toolbar_end;
            } else {
                c.innerHTML = toolbar_start + toolbar_add + toolbar_end;
            }
        } else {
            if (a == "cut") {
                c.innerHTML = toolbar_start + toolbar_add + toolbar_del_move + toolbar_end;
            } else {
                if (isMergeAnswer && !this.isMergeNewAdded) {
                    c.innerHTML = toolbar_start + toolbar_add + toolbar_copy + toolbar_end;
                } else {
                    if (isMergeAnswer) {
                        c.innerHTML = toolbar_start + toolbar_add + toolbar_copy + toolbar_del + toolbar_movedown + toolbar_movelast + toolbar_end;
                    } else {
                        c.innerHTML = toolbar_start + toolbar_add + toolbar_copy + toolbar_del_move + toolbar_end;
                    }
                }
            }
        }
        c.OpCreated = true;
        this.divTableOperation.parentNode.style.visibility = "visible";
        var b = $$("span", c)[0];
        this.editQLink = b;
    }
}
var hasDisplayEditTipTimes = 0;

function qonmouseover(c) {
    if (!this.isEditing && !isMergeAnswer) {
        this.divInsertOp.style.display = "";
    }
    var b = this.className.toLowerCase();
    if (b.indexOf("div_question_onclick") < 0 && this != curinsert) {
        if (b.indexOf("div_question_error") < 0) {
            this.className = "div_question div_question_mouseover";
        } else {
            this.className = "div_question div_question_mouseover div_question_error";
        }
    }
    this.createOp();
    if (isMergeAnswer) {
        this.style.cursor = "default";
    }
    curover = this;
    var a = this;
    if (hasDisplayEditTipTimes < 3) {
        hasDisplayEditTipTimes++;
        toolTipLayer.innerHTML = "提示：您可以通过“双击”题目来进行编辑";
        sb_setmenunav(toolTipLayer, true, this, true);
        this.hasDisplayEditTip = true;
    }
}
function qonmouseout(b) {
    this.divInsertOp.style.display = "none";
    var a = this.className.toLowerCase();
    if (a.indexOf("div_question_onclick") < 0 && this != curinsert) {
        if (a.indexOf("div_question_error") < 0) {
            this.className = "div_question div_question_mouseout";
        } else {
            this.className = "div_question div_question_mouseout div_question_error";
        }
    }
    if (this.hasDisplayEditTip) {
        sb_setmenunav(toolTipLayer, false, this);
        this.hasDisplayEditTip = false;
    }
    if (this.divTableOperation.parentNode && !this.isEditing) {
        this.divTableOperation.parentNode.style.visibility = "hidden";
    }
}
function getObjPos(d) {
    var a = y = 0;
    if (d.getBoundingClientRect) {
        var b = d.getBoundingClientRect();
        var c = document.documentElement;
        a = b.left + Math.max(c.scrollLeft, document.body.scrollLeft) - c.clientLeft;
        y = b.top + Math.max(c.scrollTop, document.body.scrollTop) - c.clientTop;
    } else {
        while (d && d != document.body) {
            a += d.offsetLeft;
            y += d.offsetTop;
            d = d.offsetParent;
        }
    }
    return {
        x: a,
        y: y
    };
}
function getCurPos(b) {
    b = b || window.event;
    var a = document.documentElement || document.body;
    if (b.pageX) {
        return {
            x: b.pageX,
            y: b.pageY
        };
    }
    return {
        x: b.clientX + a.scrollLeft - a.clientLeft,
        y: b.clientY + a.scrollTop - a.clientTop
    };
}
function mouseOverout(a) {
    a.onmouseover = qonmouseover;
    a.onmouseout = qonmouseout;
}
function setQHandler(a) {
    mouseOverout(a);
    a.createOp = createOp;
    a.ondblclick = qonclick;
    a.deleteQ = deleteQ;
}
initEventHandler();

function initEventHandler() {
    var a = $("divId");
    a.onmouseover = function () {
        this.style.border = "2px solid #FDB553";
    };
    a.onmouseout = function () {
        this.style.border = "2px solid #ffffff";
    };
    firstPage.ondblclick = qonclick;
    mouseOverout(firstPage);
    firstPage.createOp = createOp;
    for (var c = 0; c < questionHolder.length; c++) {
        var b = questionHolder[c];
        setQHandler(b);
    }
}
function cancelDblClick(d) {
    var a = d.tabAttr;
    if (a) {
        var b = $$("input", a);
        for (var c = 0; c < b.length; c++) {
            b[c].ondblclick = function (f) {
                f = window.event || f;
                if (f) {
                    f.cancelBubble = true;
                    if (f.stopPropagation) {
                        f.stopPropagation();
                    }
                }
            };
        }
    }
}
function popHint(e, g, a) {
    var f = $(e),
        h = g,
        d = a;
    if (f._oldBorder == undefined || f._oldBorder == null) {
        f._oldBorder = f.style.border || "solid 1px #7F9DB9";
    }
    if (f._oldTitle == undefined) {
        f._oldTitle = f.title;
    }
    f.style.border = "solid 1px red";
    f.title = g;
    f.error = true;
    var c = a ? a._event : "change";
    addEventSimple(f, c, b);

    function b() {
        f.style.border = f._oldBorder;
        f.title = f._oldTitle;
        f.error = false;
        removeEventSimple(f, c, b);
        if (f.rel) {
            removeEventSimple(f.rel, c, b);
            f.rel.style.border = f._oldBorder;
            f.rel.title = f._oldTitle;
            if (f.rel.rel) {
                f.rel.rel = null;
            }
            f.rel = null;
        }
    }
}
var actionStack = new Array();
var actionIndex = 0;

function BaseAction() {}
BaseAction.prototype.exec = function () {
    actionStack[actionIndex++] = this;
};
BaseAction.prototype.undo = function () {
    return false;
};
BaseAction.prototype.redo = function () {
    return false;
};

function DeleteAction(b, a) {
    this.deleteDiv = b;
    this.prevDiv = a;
    this.status = "done";
}
DeleteAction.prototype = new BaseAction();
DeleteAction.prototype.undo = function () {
    if (this.status != "done") {
        return;
    }
    this.prevDiv.parentNode.insertBefore(this.deleteDiv, this.prevDiv);
    this.prevDiv.parentNode.insertBefore(this.prevDiv, this.deleteDiv);
    if (this.prevDiv == firstPage) {
        questionHolder.unshift(this.deleteDiv);
    } else {
        questionHolder.insertBefore(this.deleteDiv, this.prevDiv);
        questionHolder.moveUp(this.prevDiv);
    }
    updateTopic();
    this.deleteDiv.focus();
    this.status = "undone";
};
DeleteAction.prototype.redo = function () {
    if (this.status != "undone") {
        return;
    }
    this.deleteDiv.deleteQ();
    this.status = "done";
};

function undo() {
    if (actionIndex > 0) {
        actionStack[--actionIndex].undo();
    }
}
function redo() {
    if (actionIndex < actionStack.length) {
        actionStack[actionIndex++].redo();
    }
}