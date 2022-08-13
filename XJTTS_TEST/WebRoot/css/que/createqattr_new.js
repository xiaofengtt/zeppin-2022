function setListId(a, b) {
    PDF_close(a);
}
var pre_satisfaction = "很不满意\n不满意\n一般\n满意\n很满意";
var pre_agree = "很不同意\n不同意\n一般\n同意\n很同意";
var pre_import = "很不重要\n不重要\n一般\n重要\n很重要";
var pre_accord = "很不符合\n不符合\n一般\n符合\n很符合";
var pre_wanting = "很不愿意\n不愿意\n一般\n愿意\n很愿意";
var pre_bool_1 = "是\n否";
var pre_bool_2 = "对\n错";
var pre_bool_3 = "满意\n不满意";
var pre_bool_4 = "同意\n不同意";
var pre_bool_5 = "正确\n错误";
var pre_bool_6 = "支持\n反对";
var pre_bool_7 = "Ture\nFalse";
var pre_bool_8 = "Yes\nNo";
var currentRelation = "";

function setFloat(a) {
    a.className = "spanLeft";
}
function getEditorIndex() {
    return EditorIndex++;
}
function getVerifyHtml() {
    var b = "验证：<select onchange='cur.setVerify(this);'>";
    var e = ["0,无", "数字,整数", "小数", "汉字", "英文", "Email,邮件", "日期", "网址", "手机", "固话", "电话,手机或固话", "身份证号", "QQ", "城市单选", "城市多选", "省市区", "高校"];
    for (var d = 0; d < e.length; d++) {
        var f = e[d];
        var g = f.split(",");
        var a = g[0];
        var c = g.length == 2 ? g[1] : g[0];
        b += "<option value='" + a + "'>" + c + "</option>";
    }
    b += "</select>";
    return b;
}
function checkVerifyMinMax(d, a, e) {
    var b = d.value;
    var c = a.value;
    if (b && !isInt(b)) {
        return "您输入的数据不正确";
    }
    if (c && !isInt(b)) {
        return "您输入的数据不正确";
    }
    if (b && c && b - c > 0) {
        return "您输入的数据不正确";
    }
    if (e == "数字" || e == "小数") {
        return "";
    }
    if (b && b - 3000 > 0) {
        return "字数不能超过3000";
    }
    if (c && c - 3000 > 0) {
        return "字数不能超过3000";
    }
    return "";
}
function createAttr() {
    var aV = new Date().getTime();
    var bQ;
    var ax = document.createElement("div");
    this.appendChild(ax);
    var au = document.createDocumentFragment();
    this.tabAttr = ax;
    var cX = this;
    var aK = this.dataNode;
    var a6 = aK._type;
    var cb = this.dataNode._tag || 0;
    var ag = a6 == "question";
    var bz = a6 == "slider";
    var c = a6 == "sum";
    var c7 = a6 == "page";
    var N = a6 == "cut";
    var ae = a6 == "check";
    var bn = a6 == "radio";
    var ba = bn && cb;
    var bR = a6 == "radio_down";
    var dB = a6 == "matrix";
    var ck = a6 == "matrix" && cb > 300;
    var bf = a6 == "fileupload";
    var cq = a6 == "gapfill";
    var bu = ae && cb;
    var bl = bn || bR || ae || dB;
    var D = !N && !c7;
    var b1 = aK._verify || "0";
    var cY = b1 != "0";
    var ai = aK._isTouPiao;
    var aL = aK._isCeShi;
    var bw = aK._isCePing;
    var dm = dB && cb < 102;
    var bH = this.get_div_title();
    var a3 = "";
    var h = new Array();
    this.option_radio = h;
    var bI = document.createElement("div");
    bI.className = "div_title_attr_question";
    if (aL) {
        this.addCeShiSetting = function (dM) {
            var dH = $ce("div", "<span style='color: #05398c;'>测试设置：</span>", dM);
            dH.style.marginTop = "5px";
            var dK = $ce("span", "分值：", dH);
            dK.style.marginLeft = "15px";
            var dJ = "<select onchange='cur.setTValue(this);'>";
            for (var dG = 1; dG <= 10; dG++) {
                dJ += "<option value='" + dG + "'>" + dG + "</option>";
            }
            dJ += "</select>&nbsp;";
            dK.innerHTML += dJ;
            this.setTValue = function (i) {
                cX.dataNode._ceshiValue = parseInt(i.value);
            };
            this.initValue = function () {
                if (this.dataNode._ceshiValue) {
                    var i = $$("select", dK)[0];
                    i.value = this.dataNode._ceshiValue;
                }
            };
            dH.appendChild(dK);
            if (ag) {
                var dL = $ce("span", "&nbsp;标准答案：", dH);
                var dI = control_text("14");
                dL.appendChild(dI);
                dI.value = aK._answer;
                dI.onchange = dI.onblur = function () {
                    cX.dataNode._answer = this.value;
                    cX.spanCeShi.innerHTML = "（标准答案：" + this.value + "）";
                };
                dI.onchange();
            }
            var dF = $ce("span", "&nbsp;答案解析<a title='您可以填写针对此题答案的一些解析说明，在用户参与完测试后会看到此解析' href='javascript:void(0);' style='color:green;'><b>(?)</b></a>：", dH);
            var j = control_text("18");
            dF.appendChild(j);
            j.value = aK._ceshiDesc;
            j.onchange = j.onblur = function () {
                cX.dataNode._ceshiDesc = this.value;
                if (this.value.indexOf("<") > -1) {
                    this.style.display = "none";
                } else {
                    this.style.display = "";
                }
            };
            j.onchange();
            $ce("span", "&nbsp;", dH);
            var dN = $ce("a", "HTML编辑", dH);
            dN.href = "javascript:void(0)";
            dN.onclick = function () {
                openTitleEditor(j, function (i) {
                    if (i == "-1nc" || i == undefined) {
                        return;
                    }
                    j.value = trim(i);
                    j.onchange();
                });
            };
            this.initValue();
        };
    }
    var b = document.createElement("div");
    b.style.marginLeft = "15px";
    var cU = aK._title == "请在此输入问题标题";
    var dg = $ce("div", "", b);
    if (this.newAddQ || cU) {
        var bC = $ce("div", "<span style='float:left;'>&nbsp;<b>题目标题</b></span>", dg);
        bC.style.background = "#F0F0EE";
        bC.style.height = "27px";
        bC.style.lineHeight = "27px";
        bC.style.width = "402px";
        var bL = $ce("span", "", bC);
        bL.className = "spanRight";
        var db = $ce("a", "设置标题字体、插入图片视频 ", bL);
        db.href = "javascript:void(0);";
        db.className = "link-U666";
        db.onclick = function () {
            cE.style.height = "102px";
            cE.style.width = "400px";
            cX.createEditBox();
            bC.style.display = "none";
        };
        var cS = $ce("a", "", bL);
        cS.style.color = "green";
        cS.href = "javascript:void(0);";
       /*  cS.onmouseover = function () {
            toolTipLayer.style.width = "400px";
            toolTipLayer.innerHTML = '<img width="400" height="152" src="/Images/Mysojump/QuestionnaireMng/Design/edit_pic.jpg">';
            sb_setmenunav(toolTipLayer, true, this);
        };
        cS.onmouseout = function () {
            sb_setmenunav(toolTipLayer, false, this);
        }; */
        $ce("div", "", bC).className = "divclear";
    }
    var aF = $ce("div", "", dg);
    var cE = control_textarea("4", "29");
    if (D) {
        cE.title = "例如：您最喜欢的车型？";
        cE.value = aK.title;
    }
    if (c7) {
        cE.title = "您可以在此输入本页的页面标题信息（选填）";
    }
    if (N) {
        cE.title = "请在此输入内容";
        cE.value = aK.title;
    }
    var b8 = "tc" + aK._type + getEditorIndex();
    cE.value = aK._title;
    cE.id = b8;
    cE.style.overflow = "auto";
    cE.style.padding = "5px 0 0 5px";
    cE.style.border = "1px solid #ccc";
    aF.appendChild(cE);
    if (D) {
        cE.style.width = "395px";
        if (this.newAddQ || cU) {
            cE.style.height = "70px";
        } else {
            cE.style.height = "102px";
        }
    } else {
        cE.style.width = "495px";
        cE.style.height = "130px";
    }
    cE.onkeyup = cE.onchange = function () {
        cX.checkTitle();
    };
    cE.onfocus = function () {
        if (this.value == "请在此输入问题标题") {
            this.value = "";
        }
    };
    cE.onblur = function () {
        if (!this.value && cX.dataNode._type != "page") {
            this.value = "请在此输入问题标题";
            cX.checkTitle();
        }
    };
    var aJ = "";
    if (ai) {
        if (bn) {
            aJ = "<option value='radio'>列表单选题</option><option value='toupiaocheck'>投票多选题</option>";
        } else {
            if (ae) {
                if (!isMergeAnswer || this.isMergeNewAdded) {
                    aJ = "<option value='toupiaoradio'>投票单选题</option><option value='check'>多选题</option>";
                } else {
                    aJ = "<option value='check'>多选题</option>";
                }
            }
        }
    } else {
        if (aL) {
            if (bn) {
                aJ = "<option value='radio'>列表单选题</option><option value='ceshicheck'>测试多选题</option>";
            } else {
                if (ae) {
                    if (!isMergeAnswer || this.isMergeNewAdded) {
                        aJ = "<option value='ceshiradio'>测试单选题</option><option value='check'>多选题</option>";
                    } else {
                        aJ = "<option value='check'>多选题</option>";
                    }
                }
            }
        } else {
            if (bw) {
                if (bn) {
                    aJ = "<option value='radio'>列表单选题</option><option value='cepingcheck'>评分多选题</option>";
                } else {
                    if (ae) {
                        if (!isMergeAnswer || this.isMergeNewAdded) {
                            aJ = "<option value='cepingradio'>评分单选题</option><option value='check'>列表多选题</option>";
                        } else {
                            aJ = "<option value='check'>列表多选题</option>";
                        }
                    }
                }
            } else {
                if (!isMergeAnswer || this.isMergeNewAdded) {
                    if (ag) {
                        aJ = "<option value='radio'>列表单选题</option><option value='radio_down'>下拉框单选题</option><option value='check'>多选题</option><option value='likert'>量表题</option><option value='order'>排序题</option>";
                    } else {
                        if (dB) {
                            if (ck) {
                                if (cb != 303) {
                                    aJ += "<option value='matrix,303'>表格下拉框</option>";
                                }
                                if (cb != 301) {
                                    aJ += "<option value='matrix,301'>表格数值题</option>";
                                }
                                if (cb != 302) {
                                    aJ += "<option value='matrix,302'>表格文本框</option>";
                                }
                            } else {
                                if (cb <= 101) {
                                    aJ += "<option value='matrix,103'>矩阵单选题</option>";
                                    aJ += "<option value='matrix,102'>矩阵多选题</option>";
                                    aJ += "<option value='matrix,201'>矩阵文本题</option>";
                                    aJ += "<option value='matrix,202'>矩阵滑动条</option>";
                                } else {
                                    aJ += "<option value='matrix,2'>矩阵量表题</option>";
                                    if (cb != 103) {
                                        aJ += "<option value='matrix,103'>矩阵单选题</option>";
                                    }
                                    if (cb != 102) {
                                        aJ += "<option value='matrix,102'>矩阵多选题</option>";
                                    }
                                    if (cb != 201) {
                                        aJ += "<option value='matrix,201'>矩阵文本题</option>";
                                    }
                                    if (cb != 202) {
                                        aJ += "<option value='matrix,202'>矩阵滑动条</option>";
                                    }
                                }
                            }
                        } else {
                            if (bl) {
                                if (!bn || ba) {
                                    aJ += "<option value='radio'>列表单选题</option>";
                                }
                                if (!bR) {
                                    aJ += "<option value='radio_down'>下拉框单选题</option>";
                                }
                                if (!ae || bu) {
                                    aJ += "<option value='check'>多选题</option>";
                                }
                                if (!ba) {
                                    aJ += "<option value='likert'>量表题</option>";
                                }
                                if (!bu) {
                                    aJ += "<option value='order'>排序题</option>";
                                }
                                if (bn) {
                                    aJ += "<option value='toupiaoradio'>投票单选题</option>";
                                    aJ += "<option value='ceshiradio'>测试单选题</option>";
                                    aJ += "<option value='cepingradio'>评分单选题</option>";
                                } else {
                                    if (ae && !bu) {
                                        aJ += "<option value='toupiaocheck'>投票多选题</option>";
                                        aJ += "<option value='ceshicheck'>测试多选题</option>";
                                    }
                                }
                                aJ += "<option value='question'>问答题</option>";
                            }
                        }
                    }
                } else {
                    if ((bn || bR) && cb == 0) {
                        aJ = "<option value='likert'>量表题</option><option value='check'>多选题</option><option value='toupiaoradio'>投票单选题</option><option value='cepingradio'>评分单选题</option><option value='ceshiradio'>测试单选题</option>";
                    } else {
                        if (ae && !bu) {
                            aJ = "<option value='toupiaocheck'>投票多选题</option>";
                        }
                    }
                }
            }
        }
    }
    if (aJ) {
        aJ = "<select style='width: 100px' onchange='javascript:cur.selChangeType(this.value);'><option value='0'>转换题型</option>" + aJ + "</select>";
    }
    if (D) {
        setFloat(dg);
        var aN = $ce("div", "", b);
        this.getQType = function () {
            var i = cX.dataNode._type;
            if (i == "question") {
                return "文本题";
            } else {
                if (i == "gapfill") {
                    return "填空题";
                } else {
                    if (i == "radio") {
                        if (cX.dataNode._isTouPiao) {
                            return "投票单选题";
                        } else {
                            if (cX.dataNode._isCeShi) {
                                return "测试单选题";
                            } else {
                                if (cX.dataNode._isCePing) {
                                    return "评分单选题";
                                } else {
                                    if (cb) {
                                        return "量表题";
                                    }
                                }
                            }
                        }
                        return "单选题";
                    } else {
                        if (i == "radio_down") {
                            return "下拉框单选";
                        } else {
                            if (i == "check") {
                                if (cX.dataNode._isTouPiao) {
                                    return "投票多选题";
                                } else {
                                    if (cX.dataNode._isCeShi) {
                                        return "测试多选题";
                                    } else {
                                        if (cX.dataNode._isCePing) {
                                            return "评分多选题";
                                        } else {
                                            if (cb) {
                                                return "排序题";
                                            }
                                        }
                                    }
                                }
                                return "多选题";
                            } else {
                                if (i == "fileupload") {
                                    return "上传文件题";
                                } else {
                                    if (i == "matrix") {
                                        if (cb == 201) {
                                            return "矩阵文本题";
                                        } else {
                                            if (cb == 202) {
                                                return "矩阵滑动条";
                                            } else {
                                                if (cb == 301) {
                                                    return "表格数值题";
                                                } else {
                                                    if (cb == 302) {
                                                        return "表格文本题";
                                                    } else {
                                                        if (cb == 303) {
                                                            return "表格下拉框";
                                                        } else {
                                                            if (dm) {
                                                                return "矩阵量表题";
                                                            } else {
                                                                if (cb == 103) {
                                                                    return "矩阵单选题";
                                                                } else {
                                                                    if (cb == 102) {
                                                                        return "矩阵多选题";
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (i == "sum") {
                                            return "比重题";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return "";
        };
        setFloat(aN);
        if (aJ) {
            var c5 = $ce("div", "", aN);
            var a1 = this.getQType();
            if (a1) {
                a1 = "&nbsp;&nbsp;当前题型：<b>" + a1 + "</b>";
            }
            c5.innerHTML = a1 + "&nbsp;&nbsp;";
			
           /*  this.selChangeType = function (j, i) {
                if (j == "0") {
                    return;
                }
                if (j == "question") {
                    if (confirm("转换成问答题将丢失所有选项信息，是否继续？") == false) {
                        return;
                    }
                }
                if (j == "ceshiradio" || j == "ceshicheck") {
                    if (!checkPermission(1)) {
                        return;
                    }
                }
                changeQ(j);
                window.focus();
            }; */
        }
        var K = $ce("div", "", aN);
        var ak = this.get_span_required();
        K.innerHTML = "&nbsp;&nbsp;";
        var cw = control_check();
        cw.title = "是否参与统计";
        var R = document.createTextNode("是否参与统计");
        K.appendChild(cw);
        K.appendChild(R);
        //var dz = document.createElement("span");
       
       // dz.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;将所有题目设为：<b><a href='javascript:setAllRequired(true);' class='link-U00a6e6'>必答</b></a>&nbsp;<a href='javascript:setAllRequired(false);' class='link-U00a6e6'><b>非必答</b></a>";
        //K.appendChild(dz);
		//K.style.display = "none";
        cw.onclick = function () {
            cX.dataNode._requir = this.checked;
			ak.style.display = "";
            /*if (this.checked) {
                ak.style.display = "";
            } else {
                ak.style.display = "none";
            }*/
           // dz.style.display = "";
        };
        this.get_requir = function () {
            return cw;
        };
        var n = document.createElement("div");
        n.innerHTML = "&nbsp;&nbsp;";
        var at = control_check();
        n.appendChild(at);
        var ac = document.createTextNode("填写提示");
        n.appendChild(ac);
        var aX = $ce("span", "&nbsp;", n);
        var b9 = control_text("17");
        aX.appendChild(b9);
        $ce("span", "&nbsp;", aX);
        var aq = $ce("a", "HTML编辑", aX);
        aq.href = "javascript:void(0)";
        aX.style.display = "none";
        var aC = false;
        at.onclick = function () {
            aX.style.display = this.checked ? "" : "none";
            if (!this.checked) {
                cX.dataNode._oldins = b9.value;
                b9.value = "";
            } else {
                b9.value = cX.dataNode._oldins || "";
            }
            b9.onchange();
        };
        aq.onclick = function () {
            openTitleEditor(b9, function (i) {
                if (i == "-1nc" || i == undefined) {
                    return;
                }
                b9.value = trim(i);
                b9.onchange();
            });
        };
        b9.onchange = b9.onblur = function () {
            this.value = replace_specialChar(this.value);
            if (b9.value.length > 0) {
                cX.get_div_ins().innerHTML = subjectInfo + this.value;
                cX.get_div_ins().style.visibility = "visible";
            } else {
                cX.get_div_ins().style.visibility = "hidden";
            }
            cX.dataNode._ins = this.value;
            if (this.value.indexOf("<") > -1) {
                this.style.display = "none";
            } else {
                this.style.display = "";
            }
            if (cX.checkTextJump) {
                cX.checkTextJump(this.value);
            }
        };
		n.style.display = "none";
        aN.appendChild(n);
		
		//aN.style.display = "none";
        var dh = $ce("div", "", aN);
        dh.innerHTML = "&nbsp;&nbsp;";
        var dD = control_check();
        var P = document.createTextNode("无条件跳题");
        var aI = document.createElement("span");
        aI.style.display = "none";
        var O = document.createElement("span");
        O.innerHTML = "，填写此题后跳转到第";
        var aE = control_text("3");
        aE.onclick = function () {
            openJumpWindow(cX, this, true);
            aE.openJumped = true;
        };
        aE.onmouseout = function () {
            sb_setmenunav(toolTipLayer, false);
        };
        aE.onmouseover = function () {
            if (!this.error) {
                if (!this.title || this.hasChanged) {
                    this.title = getJumpTitle(this.value);
                }
            }
        };
        var L = document.createElement("span");
        L.innerHTML = "题";
        aI.appendChild(O);
        aI.appendChild(aE);
        aI.appendChild(L);
        dh.appendChild(dD);
        dh.appendChild(P);
        dh.appendChild(aI);
		dh.style.display = "none";
        var cT = $ce("a", "<b>(?)</b>", dh);
        cT.style.color = "green";
        cT.href = "javascript:void(0);";
        cT.onmouseover = function () {
            toolTipLayer.style.width = "250px";
            toolTipLayer.innerHTML = "通过配合设置其他题目的“按选项跳题”实现更复杂的跳题逻辑 <a target='_blank' class='link-U00a6e6' href='/help/help.aspx?catid=8'>查看示例</a>";
            sb_setmenunav(toolTipLayer, true, this);
        };
        cT.onmouseout = function () {
            sb_setmenunav(toolTipLayer, false, this);
        };
        dD.onclick = function () {
            if (this.checked) {
                if (ao && ao.value != "") {
                    if (confirm("设置跳转逻辑将清空本题的默认值，是否继续？")) {
                        ao.value = "";
                        cX.dataNode._default = "";
                        aM.value = "";
                    } else {
                        this.checked = false;
                    }
                }
                if (bB && bB.checked) {
                    bB.click();
                }
                if (!cw.checked) {
                    show_status_tip("由于设置了无条件跳题，建议将此题设为必答", 4000);
                }
            }
            if (cX.defaultCheckSet) {
                cX.defaultCheckSet();
            }
            if (ao) {
                ao.disabled = this.checked;
            }
            aI.style.display = this.checked ? "" : "none";
            cX.dataNode._anytimejumpto = cX.dataNode._anytimejumpto == "0" ? "" : cX.dataNode._anytimejumpto;
            if (this.checked && cX.dataNode._anytimejumpto) {
                cX.dataNode._hasjump = true;
            } else {
                if (!this.checked) {
                    cX.dataNode._hasjump = false;
                }
            }
            cX.set_jump_ins();
            aE.value = cX.dataNode._anytimejumpto;
        };
        aE.onblur = function () {
            if (this.value == this.prevValue) {
                this.hasChanged = false;
                return;
            }
            this.hasChanged = true;
            this.prevValue = this.value;
            cX.checkAnyJump(true);
            cX.set_jump_ins();
        };
        $ce("div", "", b).style.clear = "both";
        var af = $ce("div", "", aN);
        af.innerHTML = "&nbsp;&nbsp;";
        var J = control_check();
        var aW = document.createElement("span");
        aW.innerHTML = "关联逻辑</span>";
        var ct = document.createElement("span");
        var b5 = document.createElement("a");
        b5.innerHTML = "设置关联逻辑";
        b5.href = "javascript:void(0);";

        function dE() {
            cur.dataNode._relation = currentRelation || "";
            cur.displayRelation();
        }
        ct.appendChild(b5);
        cX.initRelation = function () {
            this.dataNode._relation = "";
            b5.innerHTML = "设置关联逻辑";
            J.checked = false;
            if (cX.RelationIns) {
                cX.RelationIns.style.display = "none";
            }
            ct.style.display = "none";
            aW.style.display = "";
        };
        cX.displayRelation = function () {
            var j = this.dataNode._relation;
            if (!j) {
                this.initRelation();
                return;
            } else {
                if (j == "0") {
                    J.checked = true;
                    return;
                }
            }
            J.checked = true;
            var i = this.getRelation();
            if (i) {
                b5.innerHTML = i[0];
                b5.title = i[1];
                cX.RelationIns.innerHTML = i[0];
                cX.RelationIns.title = i[1];
                cX.RelationIns.style.display = "";
                aW.style.display = "none";
                ct.style.display = "";
            }
        };
        b5.onclick = function () {
            var j = "/wjx/design/setrelation.aspx";
            var dF = cX.dataNode._topic;
            if (dF == "1") {
                show_status_tip("第1题不能设置关联逻辑", 5000);
                J.checked = false;
                return;
            }
            j += "?ct=" + dF;
            var i = cX.dataNode._relation;
            if (i) {
                j += "&rt=" + i;
            }
            currentRelation = i || "";
            PDF_launch(j, 500, 320, dE);
        };
        J.onclick = function () {
            if (!this.checked) {
                cX.initRelation();
                return;
            } else {
                b5.onclick();
            }
        };
        af.appendChild(J);
        af.appendChild(aW);
        af.appendChild(ct);
		af.style.display = "none";
        this.displayRelation();
        var cy = $ce("span", "<a href='/Help/Help.aspx?helpid=83' target='_blank' style='color:green;'><b>(?)</b>", af);
    }
    if (cq) {
        var ah = $ce("div", "", aN);
        ah.style.marginLeft = "13px";
        var ar = control_check();
        ah.appendChild(ar);
        ar.onclick = function () {
            cX.dataNode._useTextBox = this.checked;
            cX.checkTitle();
        };
        $ce("span", "文本框样式&nbsp;&nbsp;", ah);
        var aP = document.createElement("a");
        aP.innerHTML = "<b>插入填空</b>";
        aP.className = "link-U00a6e6";
        aP.href = "javascript:void(0);";
        aP.title = "填空用连续超过三个的下划线'_ _ _'表示";
        aP.onclick = function () {
            if (KE.g[b8].wyswygMode) {
                KE.util.focus(b8);
                KE.util.selection(b8);
                KE.util.insertHtml(b8, GapFillStr);
                KE.util.focus(b8);
            }
        };
        ah.appendChild(aP);
    }
    bI.appendChild(b);
    au.appendChild(bI);
    if (c7) {
        var c2 = document.createElement("span");
        c2.innerHTML = "此页是否是甄别页：";
        c2.title = "可以在此页设置筛选规则，如果用户提交的答卷不符合要求，则会终止后面的答题";
		c2.style.display = "none";//add by zheng
        var aU = control_check();
        aU.title = c2.title;
        aU.onclick = function () {
            cX.dataNode._iszhenbie = this.checked;
            cX.divZhenBie.style.display = this.checked ? "" : "none";
        };
		aU.style.display = "none";//add by zheng
        b.appendChild(c2);
        b.appendChild(aU);
        var U = $ce("a", "(?)", b);
        U.href = "javascript:void(0);";
        U.style.color = "green";
        U.style.fontWeight = "bold";
        U.onmouseover = function () {
            toolTipLayer.style.width = "250px";
            toolTipLayer.innerHTML = "可以对此页的题目设置无效答卷筛选规则，用户点击下一页时，如果此页的答题不符合要求，系统会终止该用户继续答题。<a href='/help/help.aspx?helpid=76' target='_blank'>如何设置筛选规则？</a>";
            sb_setmenunav(toolTipLayer, true, this);
        };
        U.onmouseout = function (i) {
            sb_setmenunav(toolTipLayer, false, this);
        };
		U.style.display = "none";//add by zheng
        var bV = document.createElement("div");
        bV.style.margin = "10px 0 0 15px";
		bV.style.display = "none";//add by zheng
        bI.appendChild(bV);
        $ce("span", "<b>批量插入分页符</b>&nbsp;", bV);
        var Z = $ce("span", "每隔", bV);
        var cv = control_text(3);
        cv.value = "1";
        Z.appendChild(cv);
        $ce("span", "题插入一个分页符", Z);
        cv.maxlength = 2;
        cv.onblur = cv.onchange = function () {
            if (!isPositive(this.value)) {
                this.value = 1;
            }
        };
        var b3 = 1;
        var cf = 1;
        var cj = total_question;
        $ce("span", "&nbsp;&nbsp;&nbsp;应用范围：<input type='radio' name='rblPageScope' onclick='cur.setInsertScopeType(1);' checked='checked'/>全部题<input type='radio' name='rblPageScope' onclick='cur.setInsertScopeType(2);'/>指定题", bV);
        var b2 = $ce("span", "&nbsp;第<input type='text' style='width:20px;' value='1' onblur='cur.setInsertMinPage(this);'>题至第<input type='text' style='width:20px;'  onblur='cur.setInsertMaxPage(this);' value='" + cj + "'>题", bV);
        b2.style.display = "none";
        cX.setInsertScopeType = function (i) {
            b3 = i;
            b2.style.display = i == 1 ? "none" : "";
        };
        cX.setInsertMinPage = function (i) {
            var j = i.value;
            if (!isPositive(j) || j - cj >= 0) {
                i.value = 1;
                show_status_tip("必须为正数，并且小于最大题数", 4000);
            }
            cf = i.value;
        };
        cX.setInsertMaxPage = function (i) {
            var j = i.value;
            if (!isPositive(j) || j - total_question > 0) {
                i.value = total_question;
                show_status_tip("必须为正数，并且小于总题数", 4000);
            } else {
                if (j - cf <= 0) {
                    i.value = total_question;
                    show_status_tip("必须大于最小题数", 4000);
                }
            }
            cj = i.value;
        };
        var dy = $ce("span", "", bV);
        var x = control_btn("批量插入");
        x.className = "operation";
        dy.appendChild(x);
        $ce("span", "&nbsp;", dy);
        var dn = control_btn("删除");
        dn.className = "operation";
        dn.title = "删除除第一页之外的所有分页";
        dy.appendChild(dn);
        dn.onclick = function () {
            if (confirm("确定删除除第一页之外的所有分页吗？")) {
                var dG = new Array();
                for (var j = 0; j < questionHolder.length; j++) {
                    var dF = questionHolder[j].dataNode;
                    if (dF._type == "page") {
                        dG.push(questionHolder[j]);
                    }
                }
                for (var j = 0; j < dG.length; j++) {
                    dG[j].deleteQ("noNeedConfirm");
                }
            }
        };
        x.onclick = function () {
            var dI = new Array();
            for (var dH = 0; dH < questionHolder.length; dH++) {
                var dK = questionHolder[dH].dataNode;
                questionHolder[dH].pageNext = false;
                if (dK._type != "page" && dK._type != "cut") {
                    dI.push(questionHolder[dH]);
                } else {
                    if (dK._type == "page") {
                        if (dH > 0) {
                            questionHolder[dH - 1].pageNext = true;
                        }
                    }
                }
            }
            var j = dI.length;
            if (j > 1) {
                var dF = parseInt(cv.value);
                var dL = (cf - 1) || dF - 1;
                if (b3 == 1) {
                    cj = total_question;
                }
                for (var dH = dL; dH < cj - 1; dH += dF) {
                    var dJ = questionHolder[dH + 1];
                    if (dI[dH].pageNext) {
                        continue;
                    }
                    var dG = createFreQdataNode("page");
                    addNewQ(dG, false, false, dI[dH]);
                }
            } else {
                show_status_tip("最后一题不需要添加分页", 4000);
            }
        };
        var cQ = document.createElement("div");
        cQ.style.margin = "10px 0 0 15px";
		cQ.style.display = 'none';//add by zheng
        $ce("span", "<b>填写时间控制</b>&nbsp;", cQ);
        bI.appendChild(cQ);
        var bg = control_text(3);
        $ce("span", "此页允许填写时间为：<b>最短</b>", cQ).appendChild(bg);
        var bO = control_text(3);
        var b4 = $ce("span", "秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>最长</b>", cQ);
        b4.appendChild(bO);
        $ce("span", "秒（空表示不限制）", cQ);
        bg.onblur = bg.onchange = function () {
            var j = this;
            var dF = j.value;
            var dG = cur.dataNode._maxtime;
            if (dF) {
                if (isPositive(dF) && (!dG || (dF - dG < 0))) {
                    cur.dataNode._mintime = parseInt(dF);
                } else {
                    show_status_tip("最短时间必须为正整数并且少于最长时间", 4000);
                    j.value = "";
                    cur.dataNode._mintime = "";
                }
            } else {
                cur.dataNode._mintime = "";
            }
            var i = cur.dataNode._mintime || cur.dataNode._maxtime;
            cp.style.display = i ? "" : "none";
            cX.showTimeLimit();
        };
        bg.onclick = function () {
            if (!vipUser) {
                alert("只有企业版用户才能设置每页最短填写时间，请升级！");
                this.value = "";
                this.blur();
                return;
            }
        };
        bO.onclick = function () {
            if (!vipUser) {
                alert("只有企业版用户才能设置每页最长填写时间，请升级！");
                this.value = "";
                this.blur();
                return;
            }
        };
        bO.onblur = bO.onchange = function () {
            var dF = this;
            var dG = dF.value;
            var j = cur.dataNode._mintime;
            if (dG) {
                if (isPositive(dG) && (!j || (dG - j > 0))) {
                    cur.dataNode._maxtime = parseInt(dG);
                } else {
                    show_status_tip("最长时间必须为正整数并且大于最短时间", 4000);
                    dF.value = "";
                }
            } else {
                cur.dataNode._maxtime = "";
            }
            var i = cur.dataNode._mintime || cur.dataNode._maxtime;
            cp.style.display = i ? "" : "none";
            cX.showTimeLimit();
        };
        cX.setMinMaxTime = function () {
            if (bg) {
                bg.value = this.dataNode._mintime;
            }
            if (bO) {
                bO.value = this.dataNode._maxtime;
            }
        };
        var cR = $ce("a", "(?)", cQ);
        cR.href = "javascript:void(0);";
        cR.style.color = "green";
        cR.style.fontWeight = "bold";
        cR.onmouseover = function () {
            toolTipLayer.style.width = "250px";
            toolTipLayer.innerHTML = "说明：从进入此页开始计时，在还未达到最短填写时间时不能进入下一页或提交答卷，当到达最长填写时间后还未做答完成将自动跳转到下一页或提交答卷。";
            sb_setmenunav(toolTipLayer, true, this);
        };
        cR.onmouseout = function (i) {
            sb_setmenunav(toolTipLayer, false, this);
        };
        var cp = $ce("div", "将上面的填写时间复制到&nbsp;", cQ);
        cp.style.marginLeft = "83px";
        var dq = 1;
        var f = 1;
        var ci = total_page;
        $ce("span", "<input type='radio'  name='rbltimesp' onclick='cur.setTimePageTime(1);'  checked='checked'  />所有页<input type='radio' name='rbltimesp'  onclick='cur.setTimePageTime(2);'/>指定页", cp);
        var ap = $ce("span", "：第<input type='text' value='1' onblur='cur.setTimePageStart(this);' style='width:20px;'/>页到<input type='text' value='" + ci + "' onblur='cur.setTimePageEnd(this);' style='width:20px;'/>页", cp);
        ap.style.display = "none";
        cX.setTimePageTime = function (i) {
            dq = i;
            ap.style.display = i == 1 ? "none" : "";
        };
        cX.setTimePageStart = function (i) {
            var j = i.value;
            if (!isPositive(j) || j - ci >= 0) {
                i.value = 1;
                show_status_tip("必须为正数，并且小于最大页数", 4000);
            }
            f = i.value;
        };
        cX.setTimePageEnd = function (i) {
            var j = i.value;
            if (!isPositive(j) || j - total_page > 0) {
                i.value = total_page;
                show_status_tip("必须为正数，并且小于总页数", 4000);
            } else {
                if (j - f <= 0) {
                    i.value = total_page;
                    show_status_tip("必须大于最小页数", 4000);
                }
            }
            ci = i.value;
        };
        var a5 = $ce("span", "&nbsp;", cp);
        var ab = control_btn("复制");
        ab.className = "operation";
        a5.appendChild(ab);
        ab.onclick = function () {
            if (dq == 1) {
                ci = total_page;
            }
            c9(firstPage);
            for (var j = 0; j < questionHolder.length; j++) {
                c9(questionHolder[j]);
            }
            show_status_tip("成功复制", 4000);
        };

        function c9(j) {
            var dF = j.dataNode;
            if (dF._type == "page") {
                var i = parseInt(dF._topic);
                if (i >= f && i <= ci) {
                    dF._mintime = cur.dataNode._mintime;
                    dF._maxtime = cur.dataNode._maxtime;
                    if (j.setMinMaxTime) {
                        j.setMinMaxTime();
                    }
                }
            }
        }
    }
    if (bl || c) {
        //var d = "<a href='javascript:cur.show_divAddFromCheck();' class='link-U00a6e6' title='引用其它题目的选中项'><b>引用选项</b></a>&nbsp;";
        var d = "";
        var cD = document.createElement("div");
        cD.style.margin = "10px 0px";
        var m = $ce("select", "", cD);
        cD.style.display = "none";
        var du = document.createElement("div");
        du.style.display = "none";
        du.style.margin = "10px 0";
        this.show_divAddFromCheck = function () {
            if (!cD.inited) {
                m.onchange = function () {
                    if (!vipUser) {
                        alert("只有企业版用户才能使用引用逻辑，请升级！");
                        this.value = "0";
                        return;
                    }
                    cur.addFromCheck(this);
                };
                cD.inited = true;
                $ce("label", "&nbsp;<a target='_blank' href='/help/help.aspx?catid=9' style='color:green;text-decoration:underline;'><b>帮助</b></a>", cD);
            }
            cD.style.display = cD.style.display == "" ? "none" : "";
            if (isMergeAnswer && !this.isMergeNewAdded) {
                cD.style.display = "none";
            }
            if (bn || ae) {
                dv.style.display = "none";
            }
            if (dB) {
                if (bW) {
                    bW.style.display = cD.style.display == "" ? "none" : "";
                }
                if (cW && !cW.checked) {
                    bW.style.display = "none";
                }
                if (m.value > 0 && bW) {
                    bW.style.display = "none";
                }
            }
            this.updateSelCheck();
            this.hasDisplaySelCheck = cD.style.display == "";
        };
        this.updateSelCheck = function () {
            for (var dH = 0; dH < m.options.length; dH++) {
                m.options[dH] = null;
            }
            m.options[0] = new Option("请选择来源题目（多选题或排序题）", 0);
            var dL = 1;
            for (var dH = 0; dH < questionHolder.length; dH++) {
                var dK = questionHolder[dH].dataNode;
                var dN = this.dataNode._topic;
                if (dK._type == "check" && dK._topic - dN < 0 && !questionHolder[dH]._referDivQ) {
                    var dF = "[多选题]";
                    if (dK._tag) {
                        dF = "[排序题]";
                    }
                    var dG = dK._title;
                    dG = dG.replace(/<(?!img|embed).*?>/ig, "");
                    dG = dG.replace(/&nbsp;/ig, " ").substring(0, 16);
                    var dJ = dG + "  " + dF;
                    if (!WjxActivity._use_self_topic) {
                        dJ = dK._topic + "." + dJ;
                    }
                    var dI = new Option(dJ, dK._topic);
                    dI.referDivQ = questionHolder[dH];
                    m.options[dL++] = dI;
                }
            }
            if (this._referDivQ) {
                var dM = m.options;
                for (var dH = 0; dH < dM.length; dH++) {
                    var j = dM[dH];
                    if (j.referDivQ == this._referDivQ) {
                        m.value = j.value;
                        break;
                    }
                }
            }
        };
        this.addFromCheck = function (dI) {
            var dG = m.selectedIndex;
            if (bn || ae) {
                aB.disabled = dG > 0 ? true : false;
                dC.style.display = dG > 0 ? "none" : "";
            } else {
                A.style.display = dG > 0 ? "none" : "";
                c8.style.display = A.style.display;
            }
            du.style.display = dG > 0 ? "" : "none";
            this.clearReferQ();
            if (m.value > 0) {
                if (this._referedArray) {
                    var dF = "";
                    for (var dH = 0; dH < this._referedArray.length; dH++) {
                        if (dH > 0) {
                            dF += ",";
                        }
                        dF += this._referedArray[dH].dataNode._topic;
                    }
                    show_status_tip("第" + dF + "题的选项或行标题来源于此题的选中项，此题不能再引用其他题");
                    m.value = "0";
                    this.show_divAddFromCheck();
                    return;
                }
                var j = m.options[m.selectedIndex].referDivQ;
                this._referDivQ = j;
                if (!j._referedArray) {
                    j._referedArray = new Array();
                }
                if (j._referedArray.indexOf(this) == -1) {
                    j._referedArray.push(this);
                }
                j._referedArray.sort(function (dJ, i) {
                    return dJ.dataNode._topic - i.dataNode._topic;
                });
                this.updateReferQ();
            } else {
                this.show_divAddFromCheck();
            }
            if (this.dataNode._daoZhi) {
                if (al) {
                    al.checked = false;
                    this.dataNode._daoZhi = false;
                    cX.updateSpanMatrix();
                    show_status_tip("使用引用逻辑后，不能再使用竖向选择", 5000);
                }
            }
            if (this.updateItem) {
                this.updateItem();
            } else {
                if (this.createSum) {
                    this.createSum();
                }
            }
        };
        this.removeRefer = function () {
            m.value = "0";
            this.addFromCheck();
        };
        this.clearReferQ = function () {
            if (this._referDivQ) {
                this._referDivQ._referedArray.remove(this);
                if (!this._referDivQ._referedArray.length) {
                    this._referDivQ._referedArray = null;
                }
                this._referDivQ = null;
            }
        };
        this.clearReferedQ = function () {
            if (this._referedArray) {
                for (var j = 0; j < this._referedArray.Length; j++) {
                    var dF = this._referedArray[j];
                    dF._referDivQ = null;
                    if (dF.updateItem) {
                        dF.updateItem();
                    } else {
                        if (dF.createSum) {
                            dF.createSum();
                        }
                    }
                }
            }
        };
        this.updateReferQ = function () {
            if (this._referDivQ) {
                var i = this._referDivQ;
                var dF = "选项";
                if (this.dataNode._type == "matrix" || this.dataNode._type == "sum") {
                    dF = "行标题";
                }
                var j = "，<a href='javascript:cur.removeRefer();' class='link-U00a6e6'>取消引用</a>";
                if (isMergeAnswer && !this.isMergeNewAdded) {
                    j = "";
                }
                du.innerHTML = "此题" + dF + "来源于第" + i.dataNode._topic + "题的选中项" + j;
            }
        };
    }
    if (D) {
        $ce("div", "", bI).style.clear = "both";
        var cu = document.createElement("div");
        cu.style.marginLeft = "15px";
        if (!bl) {
            var bp = document.createElement("div");
            bp.style.margin = "10px 0";
            cu.appendChild(bp);
        }
        bI.appendChild(cu);
    }
    if (bz) {
        $ce("span", "最小值：", bp);
        var de = control_text("3");
        de.title = "用户可以选择的最小值";
        de.maxLength = 4;
        bp.appendChild(de);
        $ce("span", "&nbsp;&nbsp;&nbsp;&nbsp;最小值显示文本：", bp);
        var a = control_text("10");
        bp.appendChild(a);
        var bG = document.createElement("br");
        bp.appendChild(bG);
        $ce("span", "最大值：", bp);
        var o = control_text("3");
        o.title = "用户可以选择的最大值";
        o.maxLength = 4;
        bp.appendChild(o);
        $ce("span", "&nbsp;&nbsp;&nbsp;&nbsp;最大值显示文本：", bp);
        var aY = control_text("10");
        bp.appendChild(aY);
        de.onchange = de.onblur = function () {
            var i = 100;
            if (!isInt(this.value) || this.value - o.value > 0) {
                show_status_tip("最小值不合法", 4000);
                i = (0 - o.value < 0) ? 0 : toInt(o.value) - 1;
            } else {
                i = toInt(this.value);
            }
            cX.dataNode._minvalue = i;
            this.value = i;
            cX.get_span_min_value().innerHTML = "(" + i + ")";
        };
        o.onchange = o.onblur = function () {
            if (!isInt(this.value) || this.value - de.value < 0) {
                show_status_tip("最大值不合法", 4000);
                val = (100 - de.value > 0) ? 100 : toInt(de.value) + 1;
            } else {
                val = toInt(this.value);
            }
            cX.dataNode._maxvalue = val;
            this.value = val;
            cX.get_span_max_value().innerHTML = "(" + val + ")";
        };
        a.onchange = a.onblur = function () {
            this.value = replace_specialChar(this.value);
            cX.get_span_min_value_text().innerHTML = cX.dataNode._minvaluetext = this.value;
        };
        aY.onchange = aY.onblur = function () {
            this.value = replace_specialChar(this.value);
            cX.get_span_max_value_text().innerHTML = cX.dataNode._maxvaluetext = this.value;
        };
    }
    if (bf) {
        bp.innerHTML = "<span style='font-size:10px'>●&nbsp;</span>";
        $ce("span", "上传文件允许的最大文件大小(KB)：", bp);
        var ch = control_text("8");
        ch.title = "最大文件大小不能超过1024KB（即1M）";
        ch.maxLength = 4;
        bp.appendChild(ch);
        ch.onblur = ch.onchange = function () {
            var i = ch.value;
            if (i) {
                if (isPositive(i) && i - 1024 <= 0) {
                    cX.dataNode._maxsize = i;
                } else {
                    cX.dataNode._maxsize = 1024;
                    show_status_tip("最大文件大小必须为正数，并且不能超过1024KB（即1M）", 3000);
                    this.value = "";
                }
            } else {
                cX.dataNode._maxsize = 1024;
            }
        };
        $ce("br", "", bp);
        $ce("span", "<span style='font-size:10px'>●&nbsp;</span>上传文件允许的扩展名：", bp);
        var z = "<div><b>&nbsp;&nbsp;图片文件：</b><input type='checkbox' value=''/>全选&nbsp;&nbsp;<input type='checkbox' value='.gif'/>.gif&nbsp;<input type='checkbox' value='.png'/>.png&nbsp;<input type='checkbox' value='.jpg'/>.jpg&nbsp;<input type='checkbox' value='.jpeg'/>.jpeg&nbsp;<input type='checkbox' value='.bmp'/>.bmp&nbsp;</div>";
        z += "<div><b>&nbsp;&nbsp;文档文件：</b><input type='checkbox' value=''/>全选&nbsp;&nbsp;<input type='checkbox' value='.doc'/>.doc&nbsp;<input type='checkbox' value='.docx'/>.docx&nbsp;<input type='checkbox' value='.pdf'/>.pdf&nbsp;<input type='checkbox' value='.xls'/>.xls&nbsp;<input type='checkbox' value='.xlsx'/>.xlsx&nbsp;<input type='checkbox' value='.ppt'/>.ppt&nbsp;<input type='checkbox' value='.txt'/>.txt&nbsp;</div>";
        z += "<div><b>&nbsp;&nbsp;压缩文件：</b><input type='checkbox' value=''/>全选&nbsp;&nbsp;<input type='checkbox' value='.rar'/>.rar&nbsp;<input type='checkbox' value='.zip'/>.zip&nbsp;<input type='checkbox' value='.gzip'/>.gzip</div>";
        var bo = $ce("div", z, bp);
        cX.updateExt = function (dG) {
            var i = $$("input", bo);
            var dF = "";
            for (var j = 0; j < i.length; j++) {
                if (i[j].checked && i[j].value) {
                    if (dF) {
                        dF += "|";
                    }
                    dF += i[j].value;
                }
            }
            this.dataNode._ext = dF;
            this.updateFileUpload();
        };
    }
    if (ag) {
        if (aL) {
            var cV = document.createElement("div");
            cu.appendChild(cV);
            this.addCeShiSetting(cV);
        }
        var cZ = this.get_span_maxword();
        var aM = this.get_textarea();
        var F = $ce("span", "高度：", bp);
        var bx = "<select onchange='cur.setTHeight(this);'><option value='1'>1行</option><option value='2'>2行</option><option value='3'>3行</option><option value='4'>4行</option><option value='5'>5行</option><option value='10'>10行</option><option value='自定义'>自定义</option></select>&nbsp;";
        F.innerHTML += bx;
        var bt = $ce("span", "", bp);
        var y = control_text("3");
        y.maxLength = 3;
        y.onchange = y.onblur = function () {
            var i = this.value;
            if (!isEmpty(i) && !isInt(i)) {
                show_status_tip("您输入的高度不合法！");
                this.value = "";
                cX.dataNode._height = "1";
            } else {
                cX.dataNode._height = i ? parseInt(i) : "";
            }
            cX.showTextAreaHeight();
        };
        this.setTHeight = function (i) {
            if (i.value != "自定义") {
                y.value = i.value;
                bt.style.display = "none";
                y.onchange();
            } else {
                bt.style.display = "";
            }
        };
        this.initHeight = function () {
            if (this.dataNode._height) {
                var i = $$("select", F)[0];
                i.value = this.dataNode._height;
                if (i.selectedIndex == -1) {
                    i.value = "自定义";
                    this.setTHeight(selWidth);
                }
            }
        };
        bt.style.display = "none";
        bt.appendChild(y);
        bt.appendChild(document.createTextNode("行"));
        var b6 = $ce("span", "&nbsp;&nbsp;宽度：", bp);
        var bq = "<select onchange='cur.setTWidth(this);'><option value=''>默认</option><option value='50'>50</option><option value='100'>100</option><option value='200'>200</option><option value='300'>300</option><option value='400'>400</option><option value='500'>500</option><option value='600'>600</option><option value='自定义'>自定义</option></select>&nbsp;";
        b6.innerHTML += bq;
        var X = control_text("5");
        X.maxLength = 3;
        X.onchange = X.onblur = function () {
            var i = this.value;
            if (!isEmpty(i) && !isInt(i)) {
                show_status_tip("您输入的宽度不合法！");
                this.value = "";
                cX.dataNode._width = "";
            } else {
                cX.dataNode._width = i ? parseInt(i) : "";
                if (i == "1" && cX.dataNode._requir) {
                    cw.click();
                }
            }
            cX.showTextAreaWidth();
        };
        this.setTWidth = function (i) {
            if (i.value != "自定义") {
                X.value = i.value;
                X.style.display = "none";
                X.onchange();
            } else {
                X.style.display = "";
            }
        };
        this.initWidth = function () {
            if (this.dataNode._width) {
                var i = $$("select", b6)[0];
                i.value = this.dataNode._width;
                if (i.selectedIndex == -1 || this.dataNode._width == "1") {
                    i.value = "自定义";
                    this.setTWidth(i);
                }
            }
        };
        bp.appendChild(b6);
        X.style.display = "none";
        bp.appendChild(X);
        var s = $ce("span", "&nbsp;&nbsp;", bp);
        var cr = control_check();
        s.appendChild(cr);
        s.appendChild(document.createTextNode("下划线样式"));
        cr.onclick = function () {
            cX.dataNode._underline = this.checked;
            cX.showTextAreaUnder();
        };
        var aD = $ce("span", "&nbsp;&nbsp;", bp);
        var bm = control_check();
        aD.appendChild(bm);
        var cJ = "默认值";
        var a9 = $ce("span", cJ, aD);
        var ao = control_textarea("1", "18");
        ao.style.overflow = "auto";
        ao.style.height = "15px";
        ao.style.display = "none";
        bm.onclick = function () {
            ao.style.display = ao.style.display == "none" ? "" : "none";
            if (!this.checked) {
                cX.dataNode._olddefault = ao.value;
                ao.value = "";
            } else {
                ao.value = cX.dataNode._olddefault || "";
            }
            ao.onchange();
        };
        aD.appendChild(ao);
        ao.onchange = ao.onblur = function () {
            if (cX.checkDefault) {
                cX.checkDefault();
            }
        };
        this.changeDefaultAttr = function (i) {
            if (this.dataNode._verify == "省市区" || this.dataNode._verify == "高校") {
                a9.innerHTML = "指定省份";
                ao.onmouseover = function () {
                    openProvinceWindow(cX, this);
                };
                ao.onmouseout = function () {
                    sb_setmenunav(toolTipLayer, false);
                };
            } else {
                a9.innerHTML = cJ;
                ao.onmouseover = ao.onmouseout = null;
            }
            if (i) {
                ao.value = "";
                ao.onchange();
            }
        };
        var cP = document.createElement("div");
        var cO = control_check();
        var r = document.createElement("span");
        var E = getVerifyHtml();
        r.innerHTML = E;
        cP.appendChild(r);
        this.setVerify = function (j) {
            var i = false;
            if (this.dataNode._verify == "省市区" || j.value == "省市区" || this.dataNode._verify == "高校" || j.value == "高校") {
                i = true;
            }
            this.dataNode._verify = j.value;
            if (this.dataNode._verify == "数字" || this.dataNode._verify == "小数") {
                q.innerHTML = q.innerHTML.replace("字数", "值");
                cs.innerHTML = cs.innerHTML.replace("字数", "值");
                cc = "值";
            } else {
                q.innerHTML = q.innerHTML.replace("值", "字数");
                cs.innerHTML = cs.innerHTML.replace("值", "字数");
                cc = "字数";
            }
            this.changeDefaultAttr(i);
            this.showTextAreaDate();
        };
        this.initVerify = function () {
            var j = this.dataNode._verify || "0";
            var i = $$("select", r)[0];
            i.value = j;
        };
        var C = $ce("span", "&nbsp;", cP);
        var cc = "字数";
        if (aK._verify == "数字" || aK._verify == "小数") {
            cc = "值";
        }
        var q = $ce("span", "最小" + cc + "：", C);
        var aH = control_text("4");
        aH.title = "不填表示无限制";
        C.appendChild(aH);
        aH.onchange = aH.onblur = function () {
            var j = this.value;
            var i = bF.value;
            if (!isEmpty(j) && (!isInt(j) || (!isEmpty(i) && this.value - i > 0))) {
                show_status_tip("最小" + cc + "不合法", 4000);
                this.value = "";
            } else {
                if (!isEmpty(j) && cX.dataNode._verify != "数字" && cX.dataNode._verify != "小数") {
                    if (j - 3000 > 0) {
                        show_status_tip("最小字数不能超过3000", 4000);
                        this.value = "";
                    }
                }
            }
            cX.dataNode._minword = this.value;
            cX.showMinMaxWord(i, this.value);
            cX.checkDefault();
        };
        var cs = $ce("span", "最大" + cc + "：", C);
        cs.style.marginLeft = "10px";
        var bF = control_text("4");
        bF.title = "不填表示无限制";
        bF.onchange = bF.onblur = function () {
            var j = this.value;
            var i = aH.value;
            if (!isEmpty(j) && (!isInt(j) || (!isEmpty(i) && this.value - i < 0))) {
                show_status_tip("最大字数不合法", 4000);
                this.value = "";
            } else {
                if (!isEmpty(j) && cX.dataNode._verify != "数字" && cX.dataNode._verify != "小数") {
                    if (j - 3000 > 0) {
                        show_status_tip("最大字数不能超过3000", 4000);
                        this.value = "";
                    }
                }
            }
            cX.dataNode._maxword = this.value;
            cX.showMinMaxWord(this.value, i);
            cX.checkDefault();
        };
        C.appendChild(bF);
        var aA = document.createElement("span");
        aA.innerHTML = "&nbsp;&nbsp;";
        var be = control_check();
        aA.appendChild(be);
        $ce("span", "不允许重复&nbsp;", aA);
        be.onclick = function () {
            cX.dataNode._needOnly = this.checked;
            if (this.checked && !cX.dataNode._requir) {
                show_status_tip("由于设置了唯一性，推荐将该题设为必答题", 4000);
                cw.click();
            }
        };
        be.title = "填写的答案是唯一的，不能重复";
        cP.appendChild(aA);
        cu.appendChild(cP);
        this.changeDefaultAttr();
    }
    if (cq) {
        var M = document.createElement("div");
        var a4 = document.createElement("span");
        M.appendChild(a4);
        this.gapSelClick = function (dH) {
            var dG = getGapFillCount(aK._title);
            if (dG == dH.options.length - 1) {
                return;
            }
            dH.options.length = 0;
            dH.options.add(new Option("请选择空", "-1"));
            for (var dF = 0; dF < dG; dF++) {
                var j = new Option("填空" + (dF + 1), dF);
                dH.options.add(j);
            }
        };
        this.refreshSelRow = function () {
            var dG = this.dataNode;
            var dF = getGapFillCount(dG._title);
            var j = "";
            a4.innerHTML = "<b>填空属性</b>：<select style='width:200px;' onclick='cur.gapSelClick(this);'></select>";
            var i = $$("select", a4)[0];
            this.gapSelClick(i);
            i.onchange = function () {
                var dJ = this.value;
                var dI = parseInt(dJ);
                aT.style.display = dI < 0 ? "none" : "";
                var dH = $$("select", aT)[0];
                if (!cX.dataNode._rowVerify || !cX.dataNode._rowVerify[dI]) {
                    dH.value = "0";
                    aH.value = "";
                    bF.value = "";
                    return;
                }
                dH.value = cX.dataNode._rowVerify[dI]._verify || "0";
                aH.value = cX.dataNode._rowVerify[dI]._minword || "";
                bF.value = cX.dataNode._rowVerify[dI]._maxword || "";
            };
        };
        this.refreshSelRow();
        M.style.margin = "10px 0 0 15px";
        bI.appendChild(M);
        var aT = document.createElement("span");
        aT.style.display = "none";
        var E = getVerifyHtml();
        aT.innerHTML = E;
        M.appendChild(aT);
        this.setVerify = function (dF) {
            if (!this.dataNode._rowVerify) {
                this.dataNode._rowVerify = new Array();
            }
            var i = $$("select", a4)[0];
            var dH = i.value;
            var dG = parseInt(dH);
            if (!cX.dataNode._rowVerify[dG]) {
                cX.dataNode._rowVerify[dG] = new Object();
            }
            var j = this.dataNode._rowVerify[dG]._verify;
            this.dataNode._rowVerify[dG]._verify = dF.value;
            if (dF.value == "数字" || dF.value == "小数") {
                q.innerHTML = q.innerHTML.replace("字数", "值");
                cs.innerHTML = cs.innerHTML.replace("字数", "值");
                cc = "值";
            } else {
                q.innerHTML = q.innerHTML.replace("值", "字数");
                cs.innerHTML = cs.innerHTML.replace("值", "字数");
                cc = "字数";
            }
            this.checkTitle();
        };
        this.initVerify = function () {
            var j = this.dataNode._verify || "0";
            var i = $$("select", aT)[0];
            i.value = j;
        };
        var C = $ce("span", "&nbsp;", aT);
        var cc = "字数";
        if (aK._verify == "数字" || aK._verify == "小数") {
            cc = "值";
        }
        var q = $ce("span", "最小" + cc + "：", C);
        var aH = control_text("4");
        aH.title = "不填表示无限制";
        C.appendChild(aH);
        aH.onchange = aH.onblur = function () {
            var dH = this.value;
            var j = $$("select", a4)[0];
            var dF = j.value;
            var dG = parseInt(dF);
            if (!cX.dataNode._rowVerify[dG]) {
                cX.dataNode._rowVerify[dG] = new Object();
            }
            var i = checkVerifyMinMax(aH, bF, cX.dataNode._rowVerify[dG]._verify);
            if (i) {
                show_status_tip(i, 4000);
                this.value = "";
            }
            cX.dataNode._rowVerify[dG]._minword = this.value;
        };
        var cs = $ce("span", "最大" + cc + "：", C);
        cs.style.marginLeft = "10px";
        var bF = control_text("4");
        bF.title = "不填表示无限制";
        bF.onchange = bF.onblur = function () {
            var dH = this.value;
            var j = $$("select", a4)[0];
            var dF = j.value;
            var dG = parseInt(dF);
            if (!cX.dataNode._rowVerify[dG]) {
                cX.dataNode._rowVerify[dG] = new Object();
            }
            var i = checkVerifyMinMax(aH, bF, cX.dataNode._rowVerify[dG]._verify);
            if (i) {
                show_status_tip(i, 4000);
                this.value = "";
            }
            cX.dataNode._rowVerify[dG]._maxword = this.value;
        };
        C.appendChild(bF);
    }
    if (bl) {
        var dC = document.createElement("div");
        dC.style.clear = "both";
    }
    if (dB || c) {
        var I = document.createElement("div");
        I.style.padding = "10px 0 0 10px";
        if (dB) {
            dC.appendChild(I);
        } else {
            cu.appendChild(I);
        }
        var c4 = document.createElement("div");
        c4.style.marginRight = "5px";
        I.appendChild(c4);
        c4.className = "matrixtitle";
        if (c) {
            I.style.background = "#D5E2F2";
            I.style.width = "400px";
            var aQ = $ce("div", "可分配的总比重值：", c4);
            var l = control_text("3");
            l.style.height = "16px";
            l.maxLength = 3;
            l.style.overflow = "auto";
            l.value = this.dataNode._total || "100";
            aQ.appendChild(l);
            l.onchange = l.onblur = function () {
                if (isInt(this.value) && parseInt(this.value) > 0) {
                    cX.dataNode._total = parseInt(this.value);
                } else {
                    cX.dataNode._total = 100;
                    show_status_tip("可分配总比重值要大于0", 4000);
                }
                this.value = cX.dataNode._total;
            };
        }
        var bs = dB ? "左行标题" : "比重评估项目";
        if (ck) {
            bs = "行标题";
        }
        var cM = ck ? "16" : "30";
        var k = c ? "4" : "7";
        if (isMergeAnswer && !this.isMergeNewAdded) {
            d = "";
        }
        var co = $ce("div", "<b>" + bs + "</b>：" + d, c4);
        if (dB) {
            var a8 = control_check();
            a8.onclick = function () {
                if (!vipUser) {
                    alert("只有企业版用户才能设置行标题随机，请升级！");
                    this.checked = false;
                    return;
                }
                cX.dataNode._randomRow = this.checked;
            };
            a8.checked = cX.dataNode._randomRow;
            var df = document.createElement("span");
            df.innerHTML = "随机&nbsp;&nbsp;";
            df.title = "标题随机显示";
            co.appendChild(a8);
            co.appendChild(df);
        }
        if (dB && !ck) {
            var cW = control_check();
            co.appendChild(cW);
            var bL = $ce("span", "右行标题", co);
            cW.checked = aK._rowtitle2 ? true : false;
        }
        c4.appendChild(cD);
        c4.appendChild(du);
        var A = control_textarea(k, cM);
        A.style.overflow = "auto";
        A.value = this.dataNode._rowtitle;
        A.style.margin = "0px";
        if (!isMergeAnswer || this.isMergeNewAdded) {
            A.title = "相当于每个小题的标题";
        } else {
            A.oldLen = A.value.split("\n").length;
            A.oldValue = A.value;
            A.title = "特别提示：有答卷的情况下只能修改文字，不能增加或删除行标题，也不能移动行标题顺序";
            A.onclick = function () {
                if (!A.alert) {
                    alert(A.title);
                    A.alert = true;
                }
            };
            A.onkeypress = function (i) {
                var i = i || window.event;
                if (i.keyCode == 13) {
                    alert("有答卷的情况下不能添加新行，只能修改文字内容！");
                    if (i.preventDefault) {
                        i.preventDefault();
                    }
                    if (i.returnValue !== undefined) {
                        i.returnValue = false;
                    }
                    event.keyCode = 0;
                    return false;
                }
            };
        }
        c4.appendChild(A);
        this.checkRowTitle = function () {
            var dF = "";
            if (trim(A.value) == "") {
                popHint(A, bs + "不能为空！", null);
                A.focus();
                this.isRowTitleValid = false;
                return false;
            } else {
                var dJ = A.value.split("\n");
                var dI = 0;
                for (var dH = 0; dH < dJ.length; dH++) {
                    if (trim(dJ[dH]) != "") {
                        if (dI > 0) {
                            dF += "\n";
                        }
                        dF += dJ[dH];
                        dI++;
                    }
                    for (var dG = dH + 1; dG < dJ.length; dG++) {
                        if (trim(dJ[dH]) != "" && trim(dJ[dH]) == trim(dJ[dG])) {
                            popHint(A, "第" + (dH + 1) + "行与第" + (dG + 1) + "行重复，请修改！", {
                                _event: "keyup"
                            });
                            this.isRowTitleValid = false;
                            return false;
                        }
                    }
                }
            }
            var dK = dF.split("\n").length;
            if (A.oldLen && dK != A.oldLen) {
                if (!confirm("有答卷的情况下不能增加或删除行标题，只能修改文字内容！\r\n是否还原为初始状态的值？")) {
                    this.isRowTitleValid = false;
                    return false;
                }
                dF = A.oldValue;
            }
            this.isRowTitleValid = true;
            A.value = dF;
            this.dataNode._rowtitle = dF;
            return true;
        };
        A.onblur = function () {
            cX.checkRowTitle();
            if (c) {
                cX.createSum();
            } else {
                if (dB) {
                    cX.updateItem();
                    if (cX.refreshSelRow) {
                        cX.refreshSelRow();
                    }
                }
            }
        };
        this.addLabel = function () {
            var i = "\n【标签】标签名";
            var j = A.value.length;
            A.focus();
            if (typeof document.selection != "undefined") {
                document.selection.createRange().text = i;
            } else {
                A.value = A.value.substr(0, A.selectionStart) + i + A.value.substring(A.selectionStart, j);
            }
            A.onblur();
        };
        var c8 = $ce("div", "<a href='javascript:void(0);' onclick='cur.addLabel();'>插入标签</a>&nbsp;&nbsp;宽度：", c4);
        c8.style.margin = "5px 0";
        var bq = "<select onchange='cur.setTWidth(this);'><option value=''>默认</option><option value='10%'>10%</option><option value='15%'>15%</option><option value='20%'>20%</option><option value='30%'>30%</option><option value='40%'>40%</option><option value='50%'>50%</option><option value='自定义'>自定义</option></select>&nbsp;";
        c8.innerHTML += bq;
        var X = control_text("3");
        X.maxLength = 3;
        X.onchange = X.onblur = function () {
            var i = this.value;
            if (!isEmpty(i) && (!isInt(i) || i - 100 > 0)) {
                show_status_tip("您输入的宽度不合法！");
                this.value = "";
                cX.dataNode._rowwidth = "";
            } else {
                cX.dataNode._rowwidth = i + "%";
                if (cX.dataNode._rowwidth == "%") {
                    cX.dataNode._rowwidth = "";
                }
            }
            if (c) {
                cX.createSum();
            } else {
                if (dB) {
                    cX.updateItem();
                }
            }
            window.focus();
        };
        this.setTWidth = function (i) {
            if (i.value != "自定义") {
                X.value = i.value.replace("%", "");
                X.style.display = "none";
                X.onchange();
            } else {
                X.style.display = "";
            }
        };
        this.initWidth = function () {
            if (this.dataNode._rowwidth && this.dataNode._rowwidth.indexOf("%") > -1) {
                var i = $$("select", c8)[0];
                i.value = this.dataNode._rowwidth;
                X.value = this.dataNode._rowwidth.replace("%", "");
                if (i.selectedIndex == -1) {
                    i.value = "自定义";
                    this.setTWidth(i);
                }
            }
        };
        X.style.display = "none";
        c8.appendChild(X);
        if (dB && !ck) {
            var bW = document.createElement("div");
            bW.style.display = cW.checked ? "" : "none";
            setFloat(bW);
            I.appendChild(bW);
            var aO = control_check();
            var a0 = $ce("div", "", bW);
            a0.appendChild(aO);
            $ce("span", "右行标题(可选)", a0);
            aO.checked = cW.checked;
            cW.onclick = aO.onclick = function () {
                bW.style.display = this.checked ? "" : "none";
                A.style.width = this.checked ? "160px" : "300px";
                cW.style.visibility = this.checked ? "hidden" : "visible";
                bL.style.display = this.checked ? "none" : "";
                cW.checked = aO.checked = this.checked;
                if (!this.checked) {
                    br.prevValue = br.value;
                    br.value = "";
                } else {
                    if (!br.value) {
                        br.value = br.prevValue || "";
                    }
                }
                br.onblur();
            };
            var br = control_textarea("7", "16");
            bW.appendChild(br);
            br.style.overflow = "auto";
            br.value = this.dataNode._rowtitle2 || "";
            br.title = "适用于“语义差异法”等场景";
            this.checkRowTitle2 = function () {
                if (dB) {
                    br.value = replace_specialChar(br.value);
                    this.dataNode._rowtitle2 = br.value;
                }
                return true;
            };
            br.onblur = function () {
                cX.checkRowTitle2();
                cX.updateItem();
            };
            var dl = $ce("div", "宽度：", bW);
            dl.style.marginTop = "5px";
            var cB = "<select onchange='cur.setTWidth2(this);'><option value=''>默认</option><option value='10%'>10%</option><option value='15%'>15%</option><option value='20%'>20%</option><option value='30%'>30%</option><option value='40%'>40%</option><option value='50%'>50%</option><option value='自定义'>自定义</option></select>&nbsp;";
            dl.innerHTML += cB;
            var ay = control_text("3");
            ay.maxLength = 3;
            ay.onchange = ay.onblur = function () {
                var i = this.value;
                if (!isEmpty(i) && (!isInt(i) || i - 100 > 0)) {
                    show_status_tip("您输入的宽度不合法！");
                    this.value = "";
                    cX.dataNode._rowwidth2 = "";
                } else {
                    cX.dataNode._rowwidth2 = i + "%";
                    if (cX.dataNode._rowwidth2 == "%") {
                        cX.dataNode._rowwidth2 = "";
                    }
                }
                cX.updateItem();
                window.focus();
            };
            this.setTWidth2 = function (i) {
                if (i.value != "自定义") {
                    ay.value = i.value.replace("%", "");
                    ay.style.display = "none";
                    ay.onchange();
                } else {
                    ay.style.display = "";
                }
            };
            this.initWidth2 = function () {
                if (this.dataNode._rowwidth2 && this.dataNode._rowwidth2.indexOf("%") > -1) {
                    var i = $$("select", dl)[0];
                    i.value = this.dataNode._rowwidth2;
                    ay.value = this.dataNode._rowwidth2.replace("%", "");
                    if (i.selectedIndex == -1) {
                        i.value = "自定义";
                        this.setTWidth2(i);
                    }
                }
            };
            ay.style.display = "none";
            dl.appendChild(ay);
        }
        if (ck) {
            var g = document.createElement("div");
            setFloat(g);
            I.appendChild(g);
            $ce("div", "<b>列标题：</b>&nbsp;", g);
            var ca = control_textarea("7", "16");
            ca.style.overflow = "auto";
            ca.value = this.dataNode._columntitle;
            ca.style.margin = "0px";
            if (!isMergeAnswer || this.isMergeNewAdded) {
                ca.title = "列标题";
            } else {
                ca.disabled = true;
                ca.title = "合并答卷模式下不能更改！";
            }
            g.appendChild(ca);
            ca.onblur = function () {
                cX.checkColumnTitle();
                cX.updateItem();
            };
            this.checkColumnTitle = function () {
                if (trim(ca.value) == "") {
                    popHint(ca, "列标题不能为空！", null);
                    ca.focus();
                    this.isColumnTitleValid = false;
                    return false;
                } else {
                    var dH = ca.value.split("\n");
                    for (var dG = 0; dG < dH.length; dG++) {
                        for (var dF = dG + 1; dF < dH.length; dF++) {
                            if (trim(dH[dG]) != "" && trim(dH[dG]) == trim(dH[dF])) {
                                popHint(ca, "第" + (dG + 1) + "行与第" + (dF + 1) + "行重复，请修改！", {
                                    _event: "keyup"
                                });
                                this.isColumnTitleValid = false;
                                return false;
                            }
                        }
                    }
                }
                this.isColumnTitleValid = true;
                ca.value = replace_specialChar(ca.value);
                this.dataNode._columntitle = ca.value;
                return true;
            };
            var bc = document.createElement("div");
            g.appendChild(bc);
            bc.style.marginTop = "5px";
            var bN = "<a href='javascript:void(0);' onclick='cur.changeRowColumnTitle();'>倒置行标题与列标题</a>";
            $ce("div", bN, bc);
            this.changeRowColumnTitle = function () {
                var i = this.dataNode._rowtitle;
                this.dataNode._rowtitle = this.dataNode._columntitle;
                this.dataNode._columntitle = i;
                A.value = this.dataNode._rowtitle;
                ca.value = cur.dataNode._columntitle;
                this.updateItem();
            };
        }
        var cN = $ce("div", "", I);
        cN.style.clear = "both";
        if (dB) {
            var bT = document.createElement("div");
            bT.style.margin = "8px 0 5px";
            $ce("span", "最小值：", bT);
            var dA = control_text("3");
            dA.title = "用户可以选择的最小值";
            dA.maxLength = 3;
            dA.value = this.dataNode._minvalue;
            bT.appendChild(dA);
            dA.onchange = dA.onblur = function () {
                var i = this.value;
                if (!isEmpty(this.value) || cX.dataNode._tag == "202") {
                    if (!isInt(this.value) || this.value - cI.value > 0) {
                        show_status_tip("最小值不合法", 4000);
                        i = (0 - cI.value < 0) ? 0 : toInt(cI.value) - 1;
                        if (parseInt(i) != i) {
                            i = 0;
                        }
                    } else {
                        i = toInt(this.value);
                    }
                }
                this.value = cX.dataNode._minvalue = i;
                cX.updateItem();
                if (cX.updateSpanCheck) {
                    cX.updateSpanCheck();
                }
            };
            $ce("span", "最大值：", bT).style.marginLeft = "60px";
            var cI = control_text("3");
            cI.title = "用户可以选择的最大值";
            cI.maxLength = 3;
            if (this.dataNode._tag == "301") {
                dA.maxLength = cI.maxLength = 12;
                dA.style.width = cI.style.width = "80px";
            }
            cI.value = this.dataNode._maxvalue;
            bT.appendChild(cI);
            bT.style.display = (this.dataNode._tag == "202" || this.dataNode._tag == "301") ? "" : "none";
            cI.onchange = cI.onblur = function () {
                var i = this.value;
                if (!isEmpty(this.value) || cX.dataNode._tag == "202") {
                    if (!isInt(this.value) || this.value - dA.value < 0) {
                        show_status_tip("最大值不合法", 4000);
                        i = (10 - dA.value > 0) ? 10 : toInt(dA.value) + 1;
                        if (parseInt(i) != i) {
                            i = 10;
                        }
                    } else {
                        i = toInt(this.value);
                    }
                }
                this.value = cX.dataNode._maxvalue = i;
                cX.updateItem();
                if (cX.updateSpanCheck) {
                    cX.updateSpanCheck();
                }
            };
            I.appendChild(bT);
        }
    }
    if (bl) {
        $ce("div", "", cu).style.marginTop = "10px";
        var am = $ce("div", "", cu);
        am.style.margin = "5px 0";
        var cH = $ce("div", "", am);
        if (dm || ba) {
            var aj = document.createElement("span");
            cH.appendChild(aj);
            var dt = "<span><b>量表样式：</b></span><ul class='likertImageTypeList' style='display:inline;margin:0;' >";
            if (dB) {
                dt += "<li class='design-icon design-offr' onclick='cur.set_likertMode(101);'></li>";
            } else {
                dt += "<li style='padding-top:6px;font-size:16px;'><a href='javascript:cur.set_likertMode(1);'><b>123</b></a></li> <li class='design-icon design-offr' onclick='cur.set_likertMode(101);'></li>";
            }
            var bJ = "";
            dt += "<li class='design-icon design-on2' onclick='cur.set_likertMode(2);' style='" + bJ + "'></li>";
            dt += "<li  class='design-icon design-on3' onclick='cur.set_likertMode(3);'  style='" + bJ + "'></li>";
            dt += "<li  class='design-icon design-on4' onclick='cur.set_likertMode(4);' style='" + bJ + "'></li>";
            dt += "<li  class='design-icon design-on5' onclick='cur.set_likertMode(5);' style='" + bJ + "'></li>";
            dt += "<li  class='design-icon design-on6' onclick='cur.set_likertMode(6);' style='" + bJ + "'></li><li style='clear:both;'></li>";
            dt += "</ul>";
            aj.innerHTML = dt;
            this.set_likertMode = function (i) {
                var dF = this.dataNode._tag < 102;
                this.dataNode._tag = i;
                this.createTableRadio(true);
                if (this.dataNode._type == "matrix") {
                    am.style.display = i > 200 ? "none" : "";
                    dw.style.display = am.style.display;
                    if (dF && i > 101) {
                        bd.disabled = false;
                        bd.checked = false;
                        bd.onclick();
                        this.dataNode._hasvalue = false;
                    } else {
                        if (!dF && i < 102) {
                            bd.checked = true;
                            bd.onclick();
                            bd.disabled = true;
                            this.dataNode._hasvalue = true;
                        }
                    }
                    bd.disabled = i > 101 ? false : true;
                    var dG = "<a href='javascript:cur.show_divAddItemBat();' class='link-U00a6e6' title='一次性添加多个选项，每行输入一个选项'><b>批量增加选项</b></a>";
                    //var dG = "";
                    //var j = "<a href='javascript:PDF_launch(\"oftenoptions.aspx\",540,400);' class='link-U00a6e6' title='例如：满意度/认同度/重要度等'><b>使用常用选项</b></a>";
                    var j = "";
                    if (!isMergeAnswer || this.isMergeNewAdded) {
                        w.style.display = i > 101 ? "none" : "";
                    }
                    if (i < 102) {
                        dj.innerHTML = "&nbsp;&nbsp;" + dG;
                    } else {
                        if (isMergeAnswer && !this.isMergeNewAdded) {
                            dj.innerHTML = dG;
                        } else {
                            dj.innerHTML = dG + "&nbsp;&nbsp;";
                        }
                    }
                    if (i == 202) {
                        bT.style.display = "";
                        this.dataNode._minvalue = this.dataNode._minvalue || 0;
                        this.dataNode._maxvalue = this.dataNode._maxvalue || 10;
                        this.dataNode._rowwidth = this.dataNode._rowwidth || 100;
                        rowwidth_text.value = rowwidth_text.value || 100;
                    } else {
                        bT.style.display = "none";
                    }
                }
            };
            if (isMergeAnswer && !this.isMergeNewAdded && this.dataNode._tag > 101) {
                aj.style.display = "none";
            }
        }
        if (((ba) || dB) && (!isMergeAnswer || this.isMergeNewAdded)) {
            var w = $ce("span", "", cH);
            var c3 = document.createElement("span");
            c3.innerHTML = "<b>量表级数：</b>";
            var ad = "<select onchange='cur.set_likert_num(this);'>";
            var cg = aK._select.length - 1;
            for (var cn = 2; cn <= 10; cn++) {
                var u = "";
                if (cn == cg) {
                    u = " selected='selected' ";
                }
                ad += "<option" + u + " value='" + cn + "'>" + cn + "</option>";
            }
            ad += "</select>";
            c3.innerHTML += ad;
            this.set_likert_num = function (dF) {
                var dG = dF.value;
                var j = h.length - 1;
                for (var i = 0; i < dG; i++) {
                    h[j + i].get_item_add().onclick();
                    h[j + i + 1].get_item_title().value = i + 1;
                    h[j + i + 1].get_item_value().value = i + 1;
                }
                for (var i = 1; i < j + 1; i++) {
                    h[1].get_item_del().onclick();
                }
                if (!dm) {
                    h[1].get_item_title().value = "非常不满意";
                    h[h.length - 1].get_item_title().value = "非常满意";
                }
                h[1].get_item_title().onchange();
                window.focus();
            };
            w.appendChild(c3);
            if (dB && this.dataNode._tag > 101) {
                w.style.display = "none";
            }
        }
        var cA = $ce("span", "", cH);
        var dj = document.createElement("span");
        if (bR || bu || (isMergeAnswer && !this.isMergeNewAdded) || dB) {
            d = "";
        }
        var az = "<a href='javascript:cur.show_divAddItemBat();' class='link-UF30' title='一次性添加多个选项，每行输入一个选项'><b>批量增加选项</b></a>&nbsp;";
        //var az = "";
        //var aS = "<a href='javascript:PDF_launch(\"oftenoptions.aspx\",540,400);' class='link-U00a6e6' title='例如：满意度/认同度/重要度等'><b>常用选项</b></a>";
        var aS = "";
        if (ba || (dB && this.dataNode._tag < 102)) {
            dj.innerHTML =  az;
        } else {
            if (isMergeAnswer && !this.isMergeNewAdded) {
                dj.innerHTML = az;
            } else {
                dj.innerHTML = az + "&nbsp;" + aS + "&nbsp;" + d;
            }
        }
        if (dB && (this.dataNode._tag == 102 || this.dataNode._tag == 103)) {
            var al = control_check();
            cH.appendChild(al);
            var di = $ce("span", "竖向选择<a title='当选项太多时，通过竖向选择可以得到更好的显示效果' href='javascript:void(0);' style='color:green;'><b>(?)</b></a>", cH);
            al.onclick = function () {
                if (cX._referDivQ) {
                    show_status_tip("使用引用逻辑后，不能再使用竖向选择", 5000);
                    return;
                }
                cX.dataNode._daoZhi = this.checked;
                cX.updateSpanMatrix();
                cX.updateItem();
            };
            $ce("span", "&nbsp;&nbsp;", cH);
            var cz = $ce("a", "交换选项与行", cH);
            var bk = $ce("span", "<a title='如果您将行与选项正好相反，可以“交换选项与行”。' href='javascript:void(0);' style='color:green;'><b>(?)</b></a>", cH);
            cz.href = "javascript:void(0);";
            cz.onclick = function () {
                if (isMergeAnswer && !cX.isMergeNewAdded) {
                    show_status_tip("在合并答卷模式下，不能“交换选项与行”", 5000);
                    return;
                }
                if (window.confirm("确定交换行与选项吗？")) {
                    var j = "";
                    var dG = cX.dataNode._select;
                    for (var i = 1; i < dG.length; i++) {
                        if (i > 1) {
                            j += "\n";
                        }
                        j += dG[i]._item_title;
                    }
                    var dF = A.value;
                    if (dF) {
                        A.value = j;
                        cX.checkRowTitle();
                        cX.setFreOptions(dF);
                    }
                }
            };
        }
        if (dB) {
            var p = $ce("span", "&nbsp;&nbsp;题目主体宽度：", cH);
            var S = "<select onchange='cur.setMainWidth(this);'><option value=''>默认</option><option value='50'>50%</option><option value='60'>60%</option><option value='70'>70%</option><option value='80'>80%</option><option value='90'>90%</option><option value='100'>100%</option><option value='自定义'>自定义</option></select>&nbsp;";
            p.innerHTML += S;
            var Q = $ce("span", "", p);
            var c1 = control_text("3");
            c1.maxLength = 2;
            c1.onchange = c1.onblur = function () {
                var i = this.value;
                if (!isEmpty(i) && !isInt(i)) {
                    show_status_tip("您输入的宽度不合法！");
                    this.value = "";
                    cX.dataNode._mainWidth = 50;
                } else {
                    cX.dataNode._mainWidth = i ? parseInt(i) : "";
                }
                cX.createTableRadio(true);
            };
            this.setMainWidth = function (i) {
                if (i.value != "自定义") {
                    c1.value = i.value;
                    Q.style.display = "none";
                    c1.onchange();
                } else {
                    Q.style.display = "";
                }
            };
            this.initMainWidth = function () {
                if (this.dataNode._mainWidth) {
                    c1.value = this.dataNode._mainWidth;
                    var dG = $$("select", p)[0];
                    dG.value = this.dataNode._mainWidth;
                    var j = true;
                    for (var dF = 0; dF < dG.options.length; dF++) {
                        if (dG.options[dF].value == this.dataNode._mainWidth) {
                            j = false;
                            break;
                        }
                    }
                    if (j) {
                        dG.value = "自定义";
                        this.setMainWidth(dG);
                    }
                }
            };
            Q.appendChild(c1);
            Q.appendChild(document.createTextNode("%"));
            Q.style.display = "none";
            if (cb == "102") {
                p.style.display = "none";
            }
        }
        if ((ba || (dB && this.dataNode._tag < 102)) && (!isMergeAnswer || this.isMergeNewAdded)) {
            var v = document.createElement("div");
            v.innerHTML = "<b>常用量表：</b>";
            v.style.marginTop = "5px";
            var bZ = document.createElement("span");
            bZ.style.background = "#ffffff";
            bZ.style.padding = "5px";
            bZ.innerHTML = "&nbsp;&nbsp;<a href='javascript:cur.setOftenLikert(pre_satisfaction);'>满意度</a>&nbsp;&nbsp;<a href='javascript:cur.setOftenLikert(pre_agree);'>认同度</a>&nbsp;&nbsp;<a href='javascript:cur.setOftenLikert(pre_import);'>重要度</a>&nbsp;&nbsp;<a href='javascript:cur.setOftenLikert(pre_wanting);'>愿意度</a>&nbsp;&nbsp;<a href='javascript:cur.setOftenLikert(pre_accord);'>符合度</a>";
            this.openOftenLikert = function () {
                bZ.style.display = bZ.style.display == "none" ? "" : "none";
            };
            this.setOftenLikert = function (i) {
                cur.setFreOptions(i);
                var j = $$("select", c3)[0];
                if (j) {
                    j.value = "5";
                }
            };
            v.appendChild(bZ);
            cH.appendChild(v);
        }
        var aR = $ce("span", "", cH);
        if ((bn && cb == 0) || ae) {
            var cF = null;
            var cL = document.createElement("span");
            cL.innerHTML = "<b>排列方向</b><select onchange='cur.checkNumPer(this);' style='width:90px;'><option value='1'>竖向排列</option><optgroup label='横向排列'><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option><option value='6'>6</option><option value='7'>7</option><option value='8'>8</option><option value='9'>9</option><option value='10'>10</option><option value='15'>15</option><option value='20'>20</option><option value='30'>30</option></optgroup></select>&nbsp;";
            cF = $$("select", cL)[0];
            for (var cn = 1; cn < cF.options.length; cn++) {
                cF.options[cn].text = "每行" + cF.options[cn].value + "列";
            }
            aR.appendChild(cL);
            if (bu && cF) {
                cL.style.display = "none";
            }
            this.checkNumPer = function (j) {
                var i = trim(j.value);
                this.dataNode._numperrow = parseInt(i);
                this.createTableRadio(true);
                this.focus();
            };
        }
        var dx = $ce("span", "", cH);
        var aB = control_check();
        dx.appendChild(aB);
        $ce("span", "选项随机&nbsp;", dx);
		dx.style.display = 'none';
        aB.onclick = function () {
            cX.dataNode._randomChoice = this.checked;
        };
        if (ba || dB) {
            dx.style.display = "none";
        }
        if (ae || (dB && cb == "102")) {
            var T = null;
            T = $ce("span", "", cH);
            var bj = "<b>限选项数</b>：最少";
            if (dB) {
                bj = "&nbsp;&nbsp;" + bj;
            }
            $ce("span", bj, T);
            var dp = document.createElement("span");
            var bS = "<select onchange='cur.limitChange(this);' onclick='cur.lowLimitClick(this);' style='width:44px;'>";
            bS += "<option value=''> </option>";
            for (var cn = 1; cn < aK._select.length; cn++) {
                bS += "<option value='" + cn + "'>" + cn + "</option>";
            }
            bS += "</select>";
            dp.innerHTML = bS;
            var W = document.createElement("span");
            W.innerHTML = "项&nbsp;最多";
            var da = document.createElement("span");
            var bv = "<select onchange='cur.limitChange(this);' onclick='cur.upLimitClick(this);' style='width:44px;'>";
            bv += "<option value=''> </option>";
            for (var cn = 2; cn < aK._select.length; cn++) {
                bv += "<option value='" + cn + "'>" + cn + "</option>";
            }
            bv += "</select>";
            da.innerHTML = bv;
            var bE = document.createTextNode("项");
            T.appendChild(dp);
            T.appendChild(W);
            T.appendChild(da);
            T.appendChild(bE);
            this.limitChange = function () {
                cX.checkCheckLimit();
                window.focus();
            };
            this.lowLimitClick = function (dG) {
                if (aK._select.length - 1 == dG.options.length - 1) {
                    return;
                }
                dG.options.length = 0;
                dG.options.add(new Option(" ", ""));
                for (var dF = 1; dF < aK._select.length; dF++) {
                    var j = new Option(dF, dF);
                    dG.options.add(j);
                }
            };
            this.upLimitClick = function (dG) {
                if (aK._select.length - 1 == dG.options.length) {
                    return;
                }
                dG.options.length = 0;
                dG.options.add(new Option(" ", ""));
                for (var dF = 2; dF < aK._select.length; dF++) {
                    var j = new Option(dF, dF);
                    dG.options.add(j);
                }
            };
            var b7 = aK._lowLimit;
            var av = aK._upLimit;
            if (bu) {
                if (!aK._lowLimit) {
                    b7 = aK._lowLimit = aK._select.length - 1;
                }
                if (!aK._upLimit) {
                    av = aK._upLimit = aK._select.length - 1;
                }
                if (aK._lowLimit == -1) {
                    b7 = "";
                }
                if (aK._upLimit == -1) {
                    av = "";
                }
            }
            $$("select", dp)[0].value = b7 || "";
            $$("select", da)[0].value = av || "";
            $ce("span", "&nbsp;&nbsp;", T);
            this.checkCheckLimit = function () {
                if (ae || (dB && cb == "102")) {
                    var j = $$("select", dp)[0].value;
                    var dF = $$("select", da)[0].value;
                    var i = h.length - 1;
                    if (j != "") {
                        if (dF != "" && dF - j < 0) {
                            j = dF;
                            $$("select", dp)[0].value = dF;
                            show_status_tip("要求用户最多选中选项数量不合法！", 4000);
                        }
                        if (!cw.checked) {
                            show_status_tip("由于设置了选项数量限制，建议将该题设为必答", 4000);
                        }
                    } else {
                        if (bu) {
                            j = -1;
                        }
                    }
                    if (dF != "") {
                        if (j != "" && dF - j < 0) {
                            dF = j;
                            $$("select", da)[0].value = j;
                            show_status_tip("要求用户最多选中选项数量不合法！", 4000);
                        }
                    } else {
                        if (bu) {
                            dF = -1;
                        }
                    }
                    this.dataNode._lowLimit = j;
                    this.dataNode._upLimit = dF;
                    this.updateSpanCheck();
                }
                return true;
            };
        }
        if (this.newAddQ) {
            aR.style.display = "none";
            dx.style.display = "none";
            if (T) {
                T.style.display = "none";
            }
        }
        cA.appendChild(dj);
        if (dB) {
            dC.style.background = "#D5E2F2";
        }
        if (a6 == "radio" || a6 == "check") {
            if (cb) {
                dC.style.width = "75%";
            } else {
                if (aL) {
                    dC.style.width = "66%";
                } else {
                    if (bw) {
                        dC.style.width = "75%";
                    }
                }
            }
        } else {
            if (a6 == "matrix") {
                if (cb == "201" || cb == "202" || cb == "301" || cb == "302") {
                    dC.style.width = "400px";
                }
            } else {
                dC.style.width = "75%";
            }
        }
        var dv = document.createElement("div");
        am.appendChild(dv);
        if (!dB) {
            am.appendChild(cD);
            am.appendChild(du);
        }
        this.setNotNewAdd = function () {
            if (!cX.newAddQ) {
                return;
            }
            dv.style.display = "none";
            bA.style.display = "";
            cX.newAddQ = false;
            dw.style.display = "";
            aR.style.display = "";
            dx.style.display = "";
            if (T) {
                T.style.display = "";
            }
            dv.textAddItemBat.value = "";
        };
        this.show_divAddItemBat = function () {
            if (!dv.inited) {
                dv.style.display = "none";
                dv.style.width = "400px";
                var dL = $ce("div", "", dv);
                dL.className = "bordCss bordBottomCss";
                dL.style.marginLeft = "30px";
                dL.style.borderWidth = "8px 8px 0";
                var dK = control_textarea("6", "40");
                dK.style.padding = "5px 0 0 5px";
                dK.style.width = "395px";
                dK.style.overflow = "auto";
                dK.title = "每行输入一个选项";
                $ce("div", "", dv).appendChild(dK);
                dv.textAddItemBat = dK;
                var dF = $ce("div", "", dv);
                dF.style.margin = "5px 0 10px";
                var dH = document.createElement("a");
                dH.innerHTML = "<span>确认增加</span>";
                dH.href = "javascript:void(0);";
                dH.className = "btnbg";
                var dM = document.createElement("a");
                dM.style.marginLeft = "20px";
                dM.style.display = "inline-block";
                dM.className = "link-U333";
                dM.innerHTML = "取消";
                dM.href = "javascript:void(0);";
                dF.appendChild(dH);
                dF.appendChild(dM);
                dK.value = "每行输入一个选项";
                if (this.newAddQ) {
                    var dG = "";
                    var j = 0;
                    for (var dJ = 1; dJ < this.dataNode._select.length; dJ++) {
                        if (j > 0) {
                            dG += "\n";
                        }
                        var dI = this.dataNode._select[dJ]._item_title;
                        if (/^选项\d+$/.exec(dI)) {
                            dG += dI;
                            j++;
                        }
                    }
                    dK.initText = dK.value = dG;
                }
                dv.inited = true;
                dK.onfocus = function () {
                    if (this.value == "每行输入一个选项" || this.value == this.initText) {
                        this.value = "";
                    }
                };
                dK.onblur = function () {
                    if (this.value == "") {
                        this.value = "每行输入一个选项";
                    }
                };
                dH.onclick = function () {
                    var i = cX.newAddQ;
                    var dN = dK.value;
                    cX.setNotNewAdd();
                    var dV = h.length - 1;
                    var dY = dN.split("\n");
                    var dZ = 0;
                    var dQ = h.length;
                    if (h[dV].get_item_tb() && h[dV].get_item_tb().checked) {
                        dV--;
                    }
                    var dT = new Object();
                    var dP = new Object();
                    var dU = /^选项\d+$/;
                    for (var dR = 1; dR < h.length; dR++) {
                        var dO = trim(h[dR].get_item_title().value);
                        dP[dO] = 1;
                    }
                    for (var dR = 0; dR < dY.length; dR++) {
                        var d0 = trim(dY[dR]);
                        if (d0 && !dT[d0] && d0 != "每行输入一个选项") {
                            if (dU.test(d0) && dP[d0]) {
                                continue;
                            }
                            h[dV + dZ].get_item_add().onclick();
                            if (!h[dV + dZ + 1]) {
                                return;
                            }
                            h[dV + dZ + 1].get_item_title().value = d0;
                            h[dV + dZ + 1].get_item_value().value = dZ + 1;
                            dT[d0] = 1;
                            dZ++;
                        }
                    }
                    var dX = h.length - 1;
                    for (var dR = 1; dR < h.length; dR++) {
                        var dO = h[dR].get_item_title().value;
                        if (dU.exec(dO)) {
                            dX--;
                        } else {
                            break;
                        }
                    }
                    var dS = false;
                    var dW = h.length - dX;
                    if (dZ >= 2 && i && cX.dataNode._isTouPiao) {
                        dX = dZ;
                        dW = dQ;
                    }
                    if (dX >= 2) {
                        for (var dR = 1; dR < dW; dR++) {
                            h[1].get_item_del().onclick();
                            dS = true;
                        }
                    }
                    if (cX.dataNode._isCeShi && dS && h[1].get_item_check()) {
                        h[1].get_item_check().click();
                    }
                    dv.style.display = "none";
                    dK.value = "";
                    cX.updateItem();
                };
                dM.onclick = function () {
                    cX.setNotNewAdd();
                    dv.style.display = "none";
                };
            } else {}
            dv.style.display = dv.style.display == "" ? "none" : "";
            cD.style.display = "none";
            if (dv.style.display == "none") {
                cX.setNotNewAdd();
            }
        };
        if (ai) {
            var B = $ce("div", "<span style='color: #05398c;'>投票设置：</span>", am);
            B.style.marginTop = "5px";
            var ds = $ce("span", "选项宽度：", B);
            ds.style.marginLeft = "15px";
            var S = "<select onchange='cur.setTWidth(this);'><option value='20'>20%</option><option value='30'>30%</option><option value='40'>40%</option><option value='50'>50%</option><option value='60'>60%</option><option value='70'>70%</option><option value='自定义'>自定义</option></select>&nbsp;";
            ds.innerHTML += S;
            var dr = $ce("span", "", ds);
            var cl = control_text("3");
            cl.maxLength = 2;
            cl.onchange = cl.onblur = function () {
                var i = this.value;
                if (!isEmpty(i) && !isInt(i)) {
                    show_status_tip("您输入的宽度不合法！");
                    this.value = "";
                    cX.dataNode._touPiaoWidth = 50;
                } else {
                    cX.dataNode._touPiaoWidth = i ? parseInt(i) : "";
                }
                cX.createTableRadio(true);
            };
            this.setTWidth = function (i) {
                if (i.value != "自定义") {
                    cl.value = i.value;
                    dr.style.display = "none";
                    cl.onchange();
                } else {
                    dr.style.display = "";
                }
            };
            this.initWidth = function () {
                if (this.dataNode._touPiaoWidth) {
                    cl.value = this.dataNode._touPiaoWidth;
                    var dG = $$("select", ds)[0];
                    dG.value = this.dataNode._touPiaoWidth;
                    var j = true;
                    for (var dF = 0; dF < dG.options.length; dF++) {
                        if (dG.options[dF].value == this.dataNode._touPiaoWidth) {
                            j = false;
                            break;
                        }
                    }
                    if (j) {
                        dG.value = "自定义";
                        this.setTWidth(dG);
                    }
                }
            };
            dr.appendChild(cl);
            dr.appendChild(document.createTextNode("%"));
            dr.style.display = "none";
            B.appendChild(ds);
            this.initWidth();
            var dc = $ce("span", "&nbsp;&nbsp;", B);
            var b0 = control_check();
            dc.appendChild(b0);
            dc.appendChild(document.createTextNode("显示条形图百分比"));
            var t = control_check();
            dc.appendChild(t);
            dc.appendChild(document.createTextNode("显示投票数"));
            var cx = control_check();
            dc.appendChild(cx);
            dc.appendChild(document.createTextNode("显示百分比"));
            b0.checked = b0.defaultChecked = aK._displayTiao;
            t.checked = t.defaultChecked = aK._displayNum;
            cx.checked = cx.defaultChecked = aK._displayPercent;
            b0.onclick = function () {
                cX.dataNode._displayTiao = this.checked;
                cX.createTableRadio(true);
            };
            cx.onclick = function () {
                cX.dataNode._displayPercent = this.checked;
                cX.createTableRadio(true);
            };
            t.onclick = function () {
                cX.dataNode._displayNum = this.checked;
                cX.createTableRadio(true);
            };
        } else {
            if (aL) {
                this.addCeShiSetting(am);
            }
        }
        var dw = $ce("div", "", dC);
        var dd = document.createElement("table");
        dd.className = "tableoption";
        dd.style.backgroundColor = "#D5E2F2";
        dd.cellSpacing = "3";
        dd.cellPadding = "3";
        dd.width = "94%";
        var bb = dd.insertRow(-1);
        h[0] = bb;
        var bX = !aK._tag && !bw && (aK._type == "check" || aK._type == "radio" || aK._type == "radio_down");
        var aZ = bb.insertCell(-1);
        aZ.style.width = "150px";
        var cK = $ce("span", "&nbsp;&nbsp;", aZ);
        var V = $ce("a", "选项文字↑↓", cK);
        V.title = "交换选项文字";
        V.className = "link-U00a6e6";
        V.href = "javascript:void(0);";
        V.onclick = function () {
            if (isMergeAnswer && !cur.isMergeNewAdded) {
                show_status_tip("合并答卷模式不能交换选项文字", 4000);
                return false;
            }
            var dH = false;
            var j = false;
            if (ba || dm || bw || cb == 303) {
                j = true;
            }
            if (j && confirm("是否同时交换选项分数？")) {
                dH = true;
            }
            var dG = 1;
            var i = h.length - 1;
            while (dG < i) {
                var dF = h[i].get_item_title().value;
                h[i].get_item_title().value = h[dG].get_item_title().value;
                h[dG].get_item_title().value = dF;
                if (dH) {
                    dF = h[i].get_item_value().value;
                    h[i].get_item_value().value = h[dG].get_item_value().value;
                    h[dG].get_item_value().value = dF;
                }
                dG++;
                i--;
            }
            cur.updateItem();
            return false;
        };
        var e = bb.insertCell(-1);
        e.align = "center";
        e.style.width = "90px";
        $ce("span", "操作", e);
        if (bw || (bX && aK._type != "radio_down")) {
            var c6 = bb.insertCell(-1);
            $ce("span", "", c6);
			
        }
        if (bw || (bX && aK._type != "radio_down")) {
            var H = bb.insertCell(-1);
            $ce("span", "说明", H);
            H.style.width = "37px";
        }
        if ((bX && aK._type != "radio_down") || (dB && cb == "102") || (dB && cb == "103")) {
            var aw = bb.insertCell(-1);
            var bY = $ce("span", "<b>允许填空</b>", aw);
            if (aL) {
                aw.style.display = "none";
            }
        }
        var bi = bb.insertCell(-1);
        if (!aL) {
            bi.style.width = "50px";
        }
        var dk = $ce("span", "", bi);
        var bd = control_check();
        bd.title = "给选项设置分数，可用于Likert量表或者测试类型的问卷";
        dk.appendChild(bd);
        var by = $ce("span", "", dk);
        by.innerHTML = "&nbsp;<b>分数</b><span class='bordCss bordBottomCss' style='border-color:#2E6AB1 transparent transparent;'></span>";
        by.style.cursor = "pointer";
        if (ba || dm || bw || cb == 303) {
            bd.style.display = "none";
            dk.onmouseover = function () {
                openValWindow(cX, this);
            };
            dk.onmouseout = function () {
                sb_setmenunav(toolTipLayer, false);
            };
        } else {
            bi.style.display = "none";
        }
        bd.onclick = function () {
            if (cX.dataNode._isCeShi) {
                return;
            }
            if (this.checked) {
                for (var i = 1; i < h.length; i++) {
                    h[i].get_item_value().parentNode.style.display = "";
                }
            } else {
                for (var i = 1; i < h.length; i++) {
                    h[i].get_item_value().parentNode.style.display = "none";
                }
            }
            cX.dataNode._hasvalue = this.checked;
        };
        var aG = bb.insertCell(-1);
        if (a6 == "radio" || a6 == "radio_down") {
            if (!aL) {
                aG.style.width = "80px";
            }
            var ce = $ce("span", "&nbsp;", aG);
            if (aL) {
                ce.style.display = "none";
            }
            var bB = control_check();
            var cC = $ce("span", "<b>跳题</b>", ce);
            var c0 = $ce("a", "<b>(?)</b>", cC);
            c0.href = "";
            c0.style.color = "green";
			
           /*  c0.onmouseover = function () {
                toolTipLayer.style.width = "250px";
                toolTipLayer.innerHTML = "按选项跳题，允许用户填写此题时根据选中的选项跳到后面指定的题号。通过配合设置其他题目的“无条件跳题”实现更复杂的跳题逻辑 <a target='_blank' class='link-U00a6e6' href='/help/help.aspx?catid=8'>查看示例</a>";
                sb_setmenunav(toolTipLayer, true, this);
            };
            c0.onmouseout = function () {
                sb_setmenunav(toolTipLayer, false, this);
            }; */
            ce.appendChild(bB);
            ce.appendChild(cC);
			ce.style.display = 'none';
            bB.onclick = function () {
                if (this.checked) {
                    cX.dataNode._anytimejumpto = "";
                    aE.value = "";
                    if (dD.checked) {
                        dD.click();
                    }
                    for (var i = 1; i < h.length; i++) {
                        h[i].get_item_jump().parentNode.style.display = "";
                    }
                } else {
                    for (var i = 1; i < h.length; i++) {
                        h[i].get_item_jump().parentNode.style.display = "none";
                    }
                }
                if (cX.defaultCheckSet) {
                    cX.defaultCheckSet();
                }
                cX.dataNode._hasjump = this.checked;
                cX.set_jump_ins();
                cX.updateItem();
            };
        }
        if (a6 == "check" && !aL && !bu && !bw) {
            var Y = bb.insertCell(-1);
            var cd = $ce("span", "&nbsp;", Y);
            var an = $ce("span", "", cd);
            an.title = "与其它选项互斥";
            cd.appendChild(an);
			
        }
        if (a6 == "matrix") {
            aG.style.display = "none";
        }
        var G = bb.insertCell(-1);
        if (bX) {
            var bP = $ce("span", "&nbsp;", G);
            var a2 = $ce("span", "", bP);
            a2.innerHTML = "默认";
            if (aL) {
                a2.innerHTML = "正确答案";
            }
            this.defaultCheckSet = function () {
                if (this.dataNode._isCeShi) {
                    return;
                }
                var j = dD.checked || (bB ? bB.checked : false);
                for (var i = 1; i < h.length; i++) {
                    var dF = h[i].get_item_check();
                    dF.parentNode.style.display = j ? "none" : "";
                    if (j) {
                        dF.checked = false;
                    }
                }
            };
        } else {
            G.style.display = "none";
        }
        if (bX && aK._type != "radio_down") {
            var bK = bb.insertCell(-1);
            $ce("span", "分组<a href='/help/help.aspx?helpid=149' target='_blank' style='color:green;'><b>(?)</b></a>", bK);
            if (aL) {
                bK.style.display = "none";
            }
        }
        if (ae) {
            for (var cm = 1; cm < aK._select.length; cm++) {
                h[cm] = new creat_item(cX, cm, dd, "check", false, aK._select[cm]);
            }
        } else {
            for (var cm = 1; cm < aK._select.length; cm++) {
                h[cm] = new creat_item(cX, cm, dd, "radio", false, aK._select[cm]);
            }
        }
        this.checkItemTitle = function () {
            var i = true;
            if (!this.checkEmptyItem()) {
                return false;
            }
            if (!this.checkRepeatItem()) {
                return false;
            }
            this.isItemTitleValid = true;
            return true;
        };
        this.checkEmptyItem = function () {
            var dF = true;
            for (var j = 1; j < h.length; j++) {
                if (trim(h[j].get_item_title().value) == "") {
                    popHint(h[j].get_item_title(), "选项不能为空！", {
                        _event: "keyup"
                    });
                    dF = false;
                    this.isItemTitleValid = false;
                }
            }
            return dF;
        };
        this.checkRepeatItem = function () {
            var dJ = true;
            for (var dH = 1; dH < h.length; dH++) {
                var dG = h[dH].get_item_title();
                if (dG._oldBorder || dG._oldBorder == "") {
                    dG.style.border = dG._oldBorder;
                    dG.title = dG._oldTitle;
                }
                for (var dF = dH + 1; dF < h.length; dF++) {
                    if (trim(h[dH].get_item_title().value) == trim(h[dF].get_item_title().value)) {
                        var dI = h[dF].get_item_title();
                        dG.rel = dI;
                        dI.rel = dG;
                        popHint(dG, "与第" + dF + "个选项重复，请修改！", {
                            _event: "keyup"
                        });
                        popHint(dI, "与第" + dH + "个选项重复，请修改！", {
                            _event: "keyup"
                        });
                        dJ = false;
                        this.isItemTitleValid = false;
                        return false;
                    }
                }
            }
            return dJ;
        };
        this.checkItemValue = function () {
            var dG = true;
            if (bd.checked) {
                for (var dF = 1; dF < h.length; dF++) {
                    var j = trim(h[dF].get_item_value().value);
                    if (j == "") {
                        h[dF].get_item_value().value = 0;
                    } else {
                        if (!isInt(j)) {
                            popHint(h[dF].get_item_value(), "选项的分数不合法，请修改！", null);
                            dG = false;
                        }
                    }
                }
            }
            this.isItemValueValid = dG;
            return dG;
        };
        this.checkItemJump = function (dF) {
            var dI = true;
            if (bB && bB.checked) {
                var dH;
                for (var j = 1; j < h.length; j++) {
                    dH = trim(h[j].get_item_jump().value);
                    if (dH != "" && dH != 0 && dH != 1) {
                        var dG = "";
                        if (!isPositive(dH)) {
                            dG = "选项的跳转题号必须是正整数";
                        } else {
                            if (toInt(dH) <= this.dataNode._topic) {
                                dG = "跳转题号必须大于本题题号(" + this.dataNode._topic + ")";
                            } else {
                                if (toInt(dH) > total_question) {
                                    dG = "跳转题号必须小于或等于最大题号(" + total_question + ")";
                                }
                            }
                        }
                        if (dG) {
                            popHint(h[j].get_item_jump(), dG, null);
                            dI = false;
                        }
                    }
                }
            }
            if (!dF) {
                this.isItemJumpValid = dI;
            } else {
                this.isItemJumpValid = false;
            }
            return dI;
        };
        this.checkCeShiSet = function () {
            if (!this.dataNode._isCeShi) {
                return true;
            }
            var j = false;
            for (var i = 1; i < aK._select.length; i++) {
                if (aK._select[i]._item_radio) {
                    j = true;
                }
            }
            if (!j) {
                popHint(a2, "请设置此题的正确答案", {
                    _event: "click"
                });
            }
            this.isCeShiValid = j;
            return j;
        };
        this.setItemJump = function () {
            for (var j = 1; j < h.length; j++) {
                h[j].get_item_jump().value = this.dataNode._select[j]._item_jump;
            }
        };
        dw.appendChild(dd);
        cu.appendChild(dC);
        this.setFreOptions = function (dG) {
            var dH = dG.split("\n");
            for (var j = 1; j < h.length; j++) {
                dd.deleteRow(1);
            }
            for (var dF = dH.length; dF < 2; dF++) {
                dH[dF] = "选项" + (dF + 1);
            }
            h = new Array();
            this.option_radio = h;
            for (var j = 0; j < dH.length; j++) {
                if (ae) {
                    h[j + 1] = new creat_item(this, j + 1, dd, "check", false, null);
                } else {
                    h[j + 1] = new creat_item(this, j + 1, dd, "radio", false, null);
                }
                h[j + 1].get_item_title().value = dH[j];
                h[j + 1].get_item_value().value = j + 1;
            }
            if (this.dataNode._isbool) {
                if (h.length == 3) {
                    h[1].get_item_value().value = 1;
                    h[2].get_item_value().value = 0;
                }
            }
            this.updateItem();
            this.setNotNewAdd();
        };
        this.checkTextJump = function (dF) {
            if ((bn || bR) && !bB.checked && !hasInsPromoteJump) {
                var j = /[一二三四五六七八九\d]+题/;
                var i = j.exec(dF);
                if (i) {
                    hasInsPromoteJump = true;
                    if (window.confirm("您是否需要按选项设置此题的跳题逻辑？")) {
                        bB.click();
                    }
                }
            }
        };
        this.updateItem = function (dK) {
            var dH = true;
            if (!this.checkItemTitle()) {
                dH = false;
            }
            if (!this.checkItemValue()) {
                dH = false;
            }
            if (!this.checkItemJump(true)) {
                dH = false;
            }
            if (!dH) {
                return;
            }
            var dJ = this.dataNode;
            dJ._select = new Array();
            var dG = !dJ._tag && (dJ._type == "check" || dJ._type == "radio" || dJ._type == "radio_down");
            for (var dF = 1; dF < h.length; dF++) {
                dJ._select[dF] = new Object();
                var dI = h[dF].get_item_title();
                var i = replace_specialChar(trim(dI.value));
                if (i != dI.value) {
                    dI.value = i;
                }
                dJ._select[dF]._item_title = dI.value;
                dJ._select[dF]._item_radio = false;
                if (dG || dJ._isCeShi) {
                    dJ._select[dF]._item_radio = h[dF].get_item_check().checked;
                }
                dJ._select[dF]._item_value = trim(h[dF].get_item_value().value);
                dJ._select[dF]._item_jump = 0;
                if (bn || bR) {
                    dJ._select[dF]._item_jump = trim(h[dF].get_item_jump().value);
                }
                if (h[dF].get_item_huchi()) {
                    dJ._select[dF]._item_huchi = h[dF].get_item_huchi().checked;
                }
                dJ._select[dF]._item_tb = false;
                dJ._select[dF]._item_tbr = false;
                dJ._select[dF]._item_img = "";
                dJ._select[dF]._item_imgtext = false;
                dJ._select[dF]._item_desc = "";
                dJ._select[dF]._item_label = "";
                if (h[dF].get_item_tb()) {
                    dJ._select[dF]._item_tb = h[dF].get_item_tb().checked;
                }
                if (h[dF].get_item_tbr()) {
                    dJ._select[dF]._item_tbr = h[dF].get_item_tbr().checked;
                }
                if (h[dF].get_item_img()) {
                    dJ._select[dF]._item_img = h[dF].get_item_img().value = replace_specialChar(trim(h[dF].get_item_img().value));
                }
                if (h[dF].get_item_imgtext()) {
                    dJ._select[dF]._item_imgtext = h[dF].get_item_imgtext().checked;
                }
                if (h[dF].get_item_desc()) {
                    dJ._select[dF]._item_desc = h[dF].get_item_desc().value = replace_specialChar(trim(h[dF].get_item_desc().value));
                }
                if (h[dF].get_item_label()) {
                    dJ._select[dF]._item_label = h[dF].get_item_label().value = replace_specialChar(trim(h[dF].get_item_label().value));
                }
            }
            if (!this.checkCeShiSet()) {
                return;
            }
            if (!dK) {
                this.createTableRadio(true);
            }
        };
    }
    if (dB) {
        dw.style.width = "310px";
        if (cb == "102" || cb == "103") {
            dw.style.width = "350px";
        }
        dw.style.paddingTop = "5px";
        dd.cellSpacing = "0";
        dd.cellPadding = "2";
        dd.width = "98%";
        setFloat(dw);
        setFloat(I);
        $ce("div", "", dC).style.clear = "both";
        if (cb == "201") {
            var M = document.createElement("div");
            var a4 = document.createElement("span");
            M.appendChild(a4);
            this.refreshSelRow = function () {
                var dK = this.dataNode;
                var dH = trim(dK._rowtitle).split("\n");
                var dJ = "";
                var dI = 0;
                for (var dG = 0; dG < dH.length; dG++) {
                    if (dH[dG].substring(0, 4) == "【标签】") {
                        continue;
                    }
                    var dF = "<option value='" + dI + "'>第" + (dI + 1) + "行:" + dH[dG] + "</option>";
                    dI++;
                    dJ += dF;
                }
                a4.innerHTML = "<b>行属性</b>：<select style='width:200px;'><option value='-1'>请选择行</option>" + dJ + "</select>&nbsp;";
                var j = $$("select", a4)[0];
                j.onchange = function () {
                    var dM = this.value;
                    var dL = parseInt(dM);
                    aT.style.display = dL < 0 ? "none" : "";
                    var i = $$("select", aT)[0];
                    if (!cX.dataNode._rowVerify || !cX.dataNode._rowVerify[dL]) {
                        i.value = "0";
                        aH.value = "";
                        bF.value = "";
                        bh.value = "";
                        return;
                    }
                    i.value = cX.dataNode._rowVerify[dL]._verify || "0";
                    aH.value = cX.dataNode._rowVerify[dL]._minword || "";
                    bF.value = cX.dataNode._rowVerify[dL]._maxword || "";
                    bh.value = cX.dataNode._rowVerify[dL]._width || "";
                };
            };
            this.refreshSelRow();
            M.style.margin = "10px 0 0 15px";
            bI.appendChild(M);
            var aT = document.createElement("span");
            aT.style.display = "none";
            var E = getVerifyHtml();
            aT.innerHTML = E;
            M.appendChild(aT);
            this.setVerify = function (dF) {
                if (!this.dataNode._rowVerify) {
                    this.dataNode._rowVerify = new Array();
                }
                var i = $$("select", a4)[0];
                var dH = i.value;
                var dG = parseInt(dH);
                if (!cX.dataNode._rowVerify[dG]) {
                    cX.dataNode._rowVerify[dG] = new Object();
                }
                var j = this.dataNode._rowVerify[dG]._verify;
                this.dataNode._rowVerify[dG]._verify = dF.value;
                if (dF.value == "数字" || dF.value == "小数") {
                    q.innerHTML = q.innerHTML.replace("字数", "值");
                    cs.innerHTML = cs.innerHTML.replace("字数", "值");
                    cc = "值";
                } else {
                    q.innerHTML = q.innerHTML.replace("值", "字数");
                    cs.innerHTML = cs.innerHTML.replace("值", "字数");
                    cc = "字数";
                }
                this.updateItem();
            };
            this.initVerify = function () {
                var j = this.dataNode._verify || "0";
                var i = $$("select", aT)[0];
                i.value = j;
            };
            var bU = $ce("span", "&nbsp;宽度：", aT);
            var bM = "<select onchange='cur.setMatrixItemWidth(this);'><option value=''>默认</option>";
            for (var cn = 1; cn < 9; cn++) {
                var aa = cn * 10;
                bM += "<option value='" + aa + "'>" + aa + "%</option>";
            }
            bM += "</select>";
            var bD = $ce("span", bM, aT);
            var bh = $$("select", bD)[0];
            this.setMatrixItemWidth = function (j) {
                if (!cX.dataNode._rowVerify) {
                    cX.dataNode._rowVerify = new Array();
                }
                var i = $$("select", a4)[0];
                var dG = i.value;
                var dF = parseInt(dG);
                if (dF == -1) {
                    alert("请选择行");
                    return;
                }
                if (!cX.dataNode._rowVerify[dF]) {
                    cX.dataNode._rowVerify[dF] = new Object();
                }
                cX.dataNode._rowVerify[dF]._width = j.value;
                this.updateItem();
            };
            var C = $ce("span", "&nbsp;", aT);
            var cc = "字数";
            if (aK._verify == "数字" || aK._verify == "小数") {
                cc = "值";
            }
            var q = $ce("span", "最小" + cc + "：", C);
            var aH = control_text("4");
            aH.title = "不填表示无限制";
            C.appendChild(aH);
            aH.onchange = aH.onblur = function () {
                var j = $$("select", a4)[0];
                var dF = j.value;
                var dG = parseInt(dF);
                if (!cX.dataNode._rowVerify[dG]) {
                    cX.dataNode._rowVerify[dG] = new Object();
                }
                var i = checkVerifyMinMax(aH, bF, cX.dataNode._rowVerify[dG]._verify);
                if (i) {
                    show_status_tip(i, 4000);
                    this.value = "";
                }
                cX.dataNode._rowVerify[dG]._minword = this.value;
            };
            var cs = $ce("span", "最大" + cc + "：", C);
            cs.style.marginLeft = "10px";
            var bF = control_text("4");
            bF.title = "不填表示无限制";
            bF.onchange = bF.onblur = function () {
                var j = $$("select", a4)[0];
                var dF = j.value;
                var dG = parseInt(dF);
                if (!cX.dataNode._rowVerify[dG]) {
                    cX.dataNode._rowVerify[dG] = new Object();
                }
                var i = checkVerifyMinMax(aH, bF, cX.dataNode._rowVerify[dG]._verify);
                if (i) {
                    show_status_tip(i, 4000);
                    this.value = "";
                }
                cX.dataNode._rowVerify[dG]._maxword = this.value;
            };
            C.appendChild(bF);
        }
    }
    var cG = $ce("div", "&nbsp;", bI);
    cG.style.clear = "both";
    cG.style.lineHeight = "1px";
    var bA = document.createElement("div");
    bA.style.margin = "10px 20px";
    var a7 = control_btn("完成");
    a7.className = "finish";
    a7.onclick = function () {
        qonclick.call(cX);
    };
    if (this.newAddQ) {
        bA.style.display = "none";
    }
    bA.appendChild(a7);
    bI.appendChild(bA);
    ax.appendChild(au);
    this.hasCreatedAttr = true;
    this.createEditBox = function () {
        var i = EditToolBarItemsPageCut;
        if (D) {
            i = EditToolBarItems;
        }
        KE.init({
            id: b8,
            items: i,
            afterChange: function (j) {
                KE.util.setData(j);
            }
        });
        this.titleId = b8;
        KE.create(b8);
        KE.util.focus(b8);
        this.hasEditBox = true;
    };
    if (!this.newAddQ && !cU) {
        this.createEditBox();
    }
    this.checkTitle = function () {
        if (cq) {
            var i = getGapFillCount(cE.value);
            if (i == 0) {
                show_status_tip("填空题的标题必须包含空“" + GapFillStr + "”!", 4000);
                this.isTitleValid = false;
                return false;
            } else {
                if (isMergeAnswer && !cX.isMergeNewAdded) {
                    if (i < this.dataNode._gapcount) {
                        show_status_tip("合并答卷时，不能删除填空题标题中的空!", 4000);
                        this.isTitleValid = false;
                        return false;
                    }
                }
            }
        }
        var j = cE.value;
        if (!this.hasEditBox && /\r\n|\n|\r/.test(j)) {
            j = cE.value = j.replace(/\r\n|\n|\r/g, "<br />");
            db.onclick();
        }
        if (cq) {
            j = replaceGapFill(j, this.dataNode);
        }
        bH.innerHTML = j;
        this.dataNode._title = cE.value;
        this.isTitleValid = true;
        return true;
    };
    this.checkDefault = function () {
        var i = replace_specialChar(trim(ao.value));
        ao.value = i;
        if (aH.value != "" && i != "" && i.length < aH.value) {
            show_status_tip("您输入的默认值少于您设置的最小字数，请修改！", 4000);
            popHint(ao, "您输入的默认值少于您设置的最小字数，请修改！", null);
            this.isDefaultValid = false;
            return false;
        } else {
            if (bF.value != "" && i.length > bF.value) {
                show_status_tip("您输入的默认值超过了您设置的最大字数，请修改！", 4000);
                popHint(ao, "您输入的默认值超过了您设置的最大字数，请修改！", null);
                this.isDefaultValid = false;
                return false;
            } else {
                if (this.dataNode._verify != "省市区" && this.dataNode._verify != "高校") {
                    aM.value = i;
                } else {
                    if (i) {
                        aM.value = "指定省份为：" + i;
                    } else {
                        aM.value = "";
                    }
                }
                this.dataNode._default = i;
                this.isDefaultValid = true;
            }
        }
        return true;
    };
    this.checkAnyJump = function (j) {
        if (dD.checked) {
            var dG;
            var i = toInt(this.dataNode._topic);
            dG = trim(aE.value);
            if (dG != 1 && dG != "" && dG != "0") {
                var dF = "";
                if (!isPositive(dG)) {
                    dF = "跳转题号必须为正整数";
                } else {
                    if (toInt(dG) <= i) {
                        dF = "跳转题号必须大于本题题号(" + i + ")";
                    } else {
                        if (toInt(dG) > total_question) {
                            dF = "跳转题号必须小于或等于最大题号(" + total_question + ")";
                        }
                    }
                }
                if (dF) {
                    popHint(aE, dF, {
                        _event: "click"
                    });
                    this.isAnyJumpValid = false;
                    return false;
                } else {
                    this.dataNode._hasjump = true;
                }
            } else {
                if (dG == 1) {
                    this.dataNode._hasjump = true;
                } else {
                    this.dataNode._hasjump = false;
                }
            }
            cX.set_jump_ins();
        }
        if (this.isAnyJumpValid == true || this.isAnyJumpValid == undefined) {
            if (aE._oldBorder || aE._oldBorder == "") {
                aE.style.border = aE._oldBorder;
                aE.title = aE._oldTitle;
            }
        }
        if (!j) {
            this.isAnyJumpValid = true;
        } else {
            this.isAnyJumpValid = false;
        }
        this.dataNode._anytimejumpto = trim(aE.value);
        return true;
    };
    this.setAnyTimeJumpTo = function () {
        aE.value = this.dataNode._anytimejumpto;
    };
    this.checkValid = function () {
        var dH = this.isAnyJumpValid == undefined || this.isAnyJumpValid;
        var dI = this.isTitleValid == undefined || this.isTitleValid;
        if (ag) {
            var j = this.isDefaultValid == undefined || this.isDefaultValid;
            return dI && j && dH;
        } else {
            var dL = this.isItemTitleValid == undefined || this.isItemTitleValid;
            var i = this.isItemJumpValid == undefined || this.isItemJumpValid;
            var dF = this.isItemValueValid == undefined || this.isItemValueValid;
            var dG = this.isRowTitleValid == undefined || this.isRowTitleValid;
            var dJ = this.isColumnTitleValid == undefined || this.isColumnTitleValid;
            var dK = this.isCeShiValid == undefined || this.isCeShiValid;
            return dL && i && dF && dH && dI && dG && dJ && dK;
        }
    };
    this.validate = function () {
        this.checkTitle();
        if (ag) {
            this.checkDefault();
        } else {
            if (c) {
                this.checkRowTitle();
            } else {
                if (bl) {
                    if (dB) {
                        this.checkRowTitle();
                    }
                    if (ck) {
                        this.checkColumnTitle();
                    }
                    this.checkItemTitle();
                    this.checkItemValue();
                    this.checkItemJump();
                    if (this.checkCheckLimit) {
                        this.checkCheckLimit();
                    }
                    if (this.checkCeShiSet) {
                        this.checkCeShiSet();
                    }
                }
            }
        }
        if (D) {
            this.checkAnyJump();
        }
        this.setErrorStyle();
    };
    this.setErrorStyle = function () {
        if (!this.checkValid()) {
            this.className += " div_question_error";
        } else {
            this.className = this.className.replace(/div_question_error/, "");
        }
    };
    this.setDataNodeToDesign = function () {
        var dI = this.dataNode;
        switch (dI._type) {
        case "question":
            cE.value = dI._title;
            cE.onblur();
            bF.value = dI._maxword;
            cw.checked = dI._requir;
            ao.value = dI._default;
            if (dI._default) {
                dI._olddefault = dI._default;
                bm.click();
            }
            b9.value = dI._ins;
            if (dI._hasjump) {
                dD.click();
            }
            aE.value = dI._anytimejumpto;
            be.checked = dI._needOnly;
            X.value = dI._width;
            if (dI._underline) {
                cr.checked = true;
            }
            aH.value = dI._minword;
            this.initVerify();
            this.initWidth();
            this.initHeight();
            break;
        case "sum":
            cE.value = dI._title;
            cE.onblur();
            cw.checked = dI._requir;
            A.value = dI._rowtitle;
            X.value = dI._rowwidth;
            this.initWidth();
            l.value = dI._total;
            b9.value = dI._ins;
            if (dI._hasjump) {
                dD.click();
            }
            aE.value = dI._anytimejumpto;
            if (this._referDivQ) {
                this.show_divAddFromCheck();
                m.value = this._referDivQ.dataNode._topic;
                this.addFromCheck(m);
            }
            break;
        case "cut":
        case "page":
            cE.value = dI._title;
            cE.onblur();
            if (dI._type == "page") {
                aU.checked = dI._iszhenbie;
                bg.value = dI._mintime;
                bO.value = dI._maxtime;
                bO.onblur();
            }
            break;
        case "fileupload":
            cE.value = dI._title;
            cE.onblur();
            var dM = $$("input", bo);
            var dN = dI._ext || defaultFileExt;
            ch.value = dI._maxsize;
            var dH = dN.split("|");
            for (var dO = 0; dO < dM.length; dO++) {
                dM[dO].onclick = function () {
                    if (!this.value) {
                        var dS = this.parentNode;
                        var dR = $$("input", dS);
                        for (var dQ = 0; dQ < dR.length; dQ++) {
                            if (dR[dQ] != this) {
                                dR[dQ].checked = this.checked;
                            }
                        }
                    }
                    if (cur.updateExt) {
                        cur.updateExt(this);
                    }
                };
                if (dH.indexOf(dM[dO].value) > -1) {
                    dM[dO].checked = true;
                }
            }
            for (var dO = 0; dO < dM.length; dO++) {
                if (dM[dO].value) {
                    continue;
                }
                var dP = true;
                var dL = dM[dO].parentNode;
                var dJ = $$("input", dL);
                for (var j = 0; j < dJ.length; j++) {
                    if (dJ[j] != dM[dO] && !dJ[j].checked) {
                        dP = false;
                        break;
                    }
                }
                if (dP) {
                    dM[dO].checked = true;
                }
            }
            cw.checked = dI._requir;
            b9.value = dI._ins;
            if (dI._hasjump) {
                dD.click();
            }
            aE.value = dI._anytimejumpto;
            break;
        case "gapfill":
            cE.value = dI._title;
            cE.onblur();
            cw.checked = dI._requir;
            b9.value = dI._ins;
            if (dI._hasjump) {
                dD.click();
            }
            aE.value = dI._anytimejumpto;
            ar.checked = dI._useTextBox;
            break;
        case "slider":
            cE.value = dI._title;
            cE.onblur();
            cw.checked = dI._requir;
            de.value = dI._minvalue || 0;
            o.value = dI._maxvalue || 100;
            a.value = dI._minvaluetext || "";
            aY.value = dI._maxvaluetext || "";
            b9.value = dI._ins;
            if (dI._hasjump) {
                dD.click();
            }
            aE.value = dI._anytimejumpto;
            break;
        case "radio":
        case "radio_down":
        case "check":
        case "matrix":
            cE.value = dI._title;
            cE.onblur();
            b9.value = dI._ins;
            if (cF) {
                cF.value = dI._numperrow;
            }
            aB.checked = dI._randomChoice;
            if (dI._hasvalue) {
                bd.checked = true;
                bd.onclick();
            }
            if (dI._tag) {
                bd.disabled = true;
            }
            if (dI._type == "matrix") {
                this.initMainWidth();
                A.value = dI._rowtitle;
                this.initWidth();
                if (dI._rowtitle2 && aO) {
                    aO.checked = true;
                    aO.onclick();
                    aO.checked = true;
                }
                if (dI._rowwidth2) {
                    this.initWidth2();
                }
                if (dI._daoZhi) {
                    if (al) {
                        al.checked = true;
                    }
                }
                am.style.display = (dI._tag > 200 && dI._tag != "303") ? "none" : "";
                dw.style.display = am.style.display;
                bd.disabled = (dI._tag <= 101 || dI._tag == 303) ? true : false;
            }
            var dK = false;
            var dG = !dI._tag && (dI._type == "check" || dI._type == "radio" || dI._type == "radio_down");
            if (this.newAddQ) {
                this.show_divAddItemBat();
                dw.style.display = "none";
            }
            for (var dF = 1; dF < dI._select.length; dF++) {
                h[dF].get_item_title().value = dI._select[dF]._item_title;
                if (dG || dI._isCeShi) {
                    var i = dI._select[dF]._item_radio;
                    h[dF].get_item_check().checked = i;
                    if (i) {
                        dK = true;
                    }
                    if (dI._select[dF]._item_huchi) {
                        h[dF].get_item_huchi().checked = true;
                    }
                }
                if (dK && this.defaultCheckSet) {
                    this.defaultCheckSet();
                }
                h[dF].get_item_value().value = dI._select[dF]._item_value;
                h[dF].get_item_jump().value = dI._select[dF]._item_jump;
                if (h[dF].get_item_tb()) {
                    h[dF].get_item_tb().checked = dI._select[dF]._item_tb;
                }
                if (h[dF].get_item_tbr()) {
                    h[dF].get_item_tbr().checked = dI._select[dF]._item_tbr;
                }
                if (h[dF].get_item_img()) {
                    h[dF].get_item_img().value = dI._select[dF]._item_img;
                }
                if (h[dF].get_item_imgtext()) {
                    h[dF].get_item_imgtext().checked = dI._select[dF]._item_imgtext;
                }
                if (h[dF].get_item_desc()) {
                    h[dF].get_item_desc().value = dI._select[dF]._item_desc;
                }
                if (h[dF].get_item_label()) {
                    h[dF].get_item_label().value = dI._select[dF]._item_label;
                }
            }
            cw.checked = dI._requir;
            if (dI._hasjump) {
                if (dI._type == "radio" || dI._type == "radio_down") {
                    if (dI._anytimejumpto < 1) {
                        bB.click();
                    } else {
                        dD.click();
                    }
                } else {
                    dD.click();
                }
            }
            aE.value = dI._anytimejumpto;
            if (this._referDivQ) {
                this.show_divAddFromCheck();
                m.value = this._referDivQ.dataNode._topic;
                this.addFromCheck(m);
            }
            break;
        }
        if (dI._ins) {
            dI._oldins = dI._ins;
            at.click();
            at.checked = true;
        }
    };
}
var itemImage = "";

function setImage(b, a) {
    PDF_close(b);
}
function creat_item(D, H, x, Z, B, E) {
    var M = H;
    var R;
    var j = x.insertRow(M);
    var q = j.insertCell(-1);
    var a = control_text("15");
    a.title = "回车添加新选项，上下键编辑前后选项";
    if (B) {
        select_item_num++;
    }
    a.value = E ? E._item_title : "选项" + select_item_num;
    a.className += " choicetxt";
    a.onchange = a.onblur = function () {
        if (!this.value) {
            this.value = this.initText;
        }
        if (cur && cur.updateItem) {
            cur.updateItem();
            cur.checkTextJump(this.value);
        }
    };
    a.onfocus = function () {
        if (!this.initText) {
            this.initText = this.value;
        }
        var aa = /^选项\d+$/;
        if (aa.test(this.value)) {
            this.value = "";
        }
    };
    a.onkeydown = function (ab) {
        var aa = ab || window.event;
        if (aa.keyCode == 13) {
            T.onclick();
        } else {
            if (aa.keyCode == 40) {
                if (M < cur.option_radio.length - 1) {
                    cur.option_radio[M + 1].get_item_title().focus();
                    cur.option_radio[M + 1].get_item_title().select();
                }
            } else {
                if (aa.keyCode == 38) {
                    if (M > 1) {
                        cur.option_radio[M - 1].get_item_title().focus();
                        cur.option_radio[M - 1].get_item_title().select();
                    }
                }
            }
        }
        return true;
    };
    q.appendChild(a);
    q.style.width = "150px";
    var F = j.insertCell(-1);
    var T = document.createElement("span");
    T.title = "在此选项下面插入一个新的选项";
    T.className = "choiceimg design-icon design-add";
    var g = document.createElement("span");
    g.title = "删除当前选项（最少保留2个选项）";
    g.className = "choiceimg design-icon design-minus";
    var Q = document.createElement("span");
    Q.title = "将当前选项上移一个位置";
    Q.className = "choiceimg design-icon design-up";
    var A = document.createElement("span");
    A.title = "将当前选项下移一个位置";
    A.className = "choiceimg design-icon design-down";
    F.appendChild(T);
    F.appendChild(g);
    F.appendChild(Q);
    F.appendChild(A);
    var s = cur || D;
    var i = s.dataNode._isCePing;
    var L = !s.dataNode._tag && (s.dataNode._type == "check" || s.dataNode._type == "radio") && !i;
    var b = s.dataNode._isCeShi;
    if (L || i) {
        var v = j.insertCell(-1);
        v.style.width = "47px";
        var O = j.insertCell(-1);
        var Y = document.createElement("input");
        Y.type = "hidden";
        Y.value = E ? E._item_img : "";
        v.appendChild(Y);
        var K = document.createElement("span");
        K.title = "添加图片";
        K.className = "choiceimg design-icon design-img";
        v.appendChild(K);
        K.style.border = Y.value ? "2px #ffff66 solid" : "";
		K.style.display = 'none';
        function n(ab) {
            if (ab == undefined) {
                return;
            }
            var aa = cur.option_radio[M].get_item_img();
            aa.value = ab;
            cur.option_radio[M].get_item_imgtext().parentNode.style.display = (ab) ? "" : "none";
            K.style.border = ab ? "2px #ffff66 solid" : "";
            cur.updateItem();
        }
        K.onclick = function () {
            itemImage = cur.option_radio[M].get_item_img().value;
            PDF_launch("uploadimg.aspx", 650, 460, n);
        };
        var N = $ce("span", "", v);
        var X = control_check();
        N.style.verticalAlign = "bottom";
        X.title = "是否显示选项文字";
        N.appendChild(X);
        N.style.display = Y.value ? "" : "none";
        X.onclick = function () {
            cur.updateItem();
        };
        var z = document.createElement("span");
        z.title = "选项说明，支持HTML，图片，视频等";
        z.className = "choiceimg design-icon design-desc";
        O.appendChild(z);
        var f = document.createElement("input");
        f.type = "hidden";
        f.value = E ? (E._item_desc || "") : "";
        O.appendChild(f);
        z.style.border = f.value ? "2px #ffff66 solid" : "";
        z.onclick = function () {
            openTitleEditor(f, function (ab) {
                if (ab == "-1nc" || ab == undefined) {
                    return;
                }
                f.value = trim(ab);
                var aa = cur.option_radio[M].get_item_img().value;
                z.style.border = f.value ? "2px #ffff66 solid" : "";
                cur.option_radio[M].get_item_imgtext().parentNode.style.display = (aa) ? "" : "none";
                cur.updateItem();
                if (aa) {
                    setTimeout(function () {
                        cur.updateItem();
                    }, 20);
                }
            });
        };
    }
    if (L || (s.dataNode._type == "matrix" && s.dataNode._tag == "102") || (s.dataNode._type == "matrix" && s.dataNode._tag == "103")) {
        var u = j.insertCell(-1);
        u.style.width = "70px";
        var P = $ce("span", "", u);
        P.style.verticalAlign = "bottom";
        var r = control_check();
        r.style.verticalAlign = "bottom";
        r.title = "允许填空";
        P.appendChild(r);
        r.checked = E ? E._item_tb : false;
        var W = $ce("span", "&nbsp;", P);
        var l = control_check();
        l.title = "文本框必填";
        W.appendChild(document.createTextNode("必填"));
        W.appendChild(l);
        l.checked = E ? E._item_tbr : false;
        W.style.visibility = r.checked ? "visible" : "hidden";
        r.onclick = function () {
            if (!this.checked) {
                cur.option_radio[M].get_item_tbr().checked = false;
            }
            cur.option_radio[M].get_item_tbr().parentNode.style.visibility = this.checked ? "visible" : "hidden";
            cur.updateItem();
        };
        l.onclick = function () {
            cur.updateItem();
        };
        if (b || i) {
            u.style.display = "none";
        }
    }
    var w = j.insertCell(-1);
    w.align = "left";
    var d = $ce("span", "&nbsp;", w);
    var m = control_text("4");
    m.maxLength = 5;
    m.title = "在此可以设置每个选项的分数（请输入整数）";
    m.value = E ? E._item_value : M;
    if (s.dataNode._hasvalue && (i || s.dataNode._tag)) {
        w.style.display = "";
    } else {
        w.style.display = "none";
    }
    d.appendChild(m);
    m.onchange = m.onblur = function () {
        if (i || (cur.dataNode._type == "radio" && cur.dataNode._tag)) {
            cur.updateItem();
        } else {
            cur.updateItem(true);
        }
    };
    m.onkeydown = function (ab) {
        var aa = ab || window.event;
        if (aa.keyCode == 13) {
            T.onclick();
        } else {
            if (aa.keyCode == 40) {
                if (M < cur.option_radio.length - 1) {
                    cur.option_radio[M + 1].get_item_value().select();
                }
            } else {
                if (aa.keyCode == 38) {
                    if (M > 1) {
                        cur.option_radio[M - 1].get_item_value().select();
                    }
                }
            }
        }
    };
    var k = j.insertCell(-1);
    if (D.dataNode._type == "matrix") {
        k.style.display = "none";
    }
    var G = $ce("span", "&nbsp;&nbsp;", k);
    k.align = "left";
    var C = control_text(3);
    C.maxLength = 4;
    C.value = E ? E._item_jump : "";
    if (s.dataNode._hasjump && s.dataNode._anytimejumpto == "0") {
        G.style.display = "";
    } else {
        G.style.display = "none";
    }
    G.appendChild(C);
    C.onclick = function () {
        openJumpWindow(s, this);
    };
    C.onmouseout = function () {
        sb_setmenunav(toolTipLayer, false);
    };
    C.onmouseover = function () {
        if (!this.error) {
            if (!this.title || this.hasChanged) {
                this.title = getJumpTitle(this.value);
            }
        }
    };
    C.onblur = function () {
        if (this.value == this.prevValue) {
            this.hasChanged = false;
            return;
        }
        this.hasChanged = true;
        this.prevValue = this.value;
        cur.updateItem(true);
    };
    C.onkeydown = function (ab) {
        var aa = ab || window.event;
        if (aa.keyCode == 13) {
            T.onclick();
        } else {
            if (aa.keyCode == 40) {
                if (M < cur.option_radio.length - 1) {
                    cur.option_radio[M + 1].get_item_jump().select();
                }
            } else {
                if (aa.keyCode == 38) {
                    if (M > 1) {
                        cur.option_radio[M - 1].get_item_jump().select();
                    }
                }
            }
        }
    };
    var y = null;
    if (L && Z == "check" && !b) {
        var J = j.insertCell(-1);
        var t = $ce("span", "&nbsp;", J);
        J.align = "left";
        y = control_check();
        y.title = "与其它选项互斥";
        t.appendChild(y);
        y.onclick = function () {
            cur.updateItem();
        };
		y.style.display = 'none';
    }
    var e = j.insertCell(-1);
    var I = $ce("span", "&nbsp;", e);
    e.align = "left";
    var U = null;
    if (b && s.dataNode._type == "radio") {
        U = control_radio(QIndentity);
    } else {
        U = control_check();
    }
    U.title = "若选中，用户在填问卷时此选项会默认被选中";
    if (L || s.dataNode._type == "radio_down") {
        e.style.display = "";
    } else {
        e.style.display = "none";
    }
    I.appendChild(U);
    if ((L || s.dataNode._type == "radio_down") && Z == "radio") {
        U.onclick = function () {
            if (U.checked) {
                for (var aa = 1; aa < cur.option_radio.length; aa++) {
                    if (cur.option_radio[aa].get_item_check() == this) {
                        this.checked = true;
                    } else {
                        cur.option_radio[aa].get_item_check().checked = false;
                    }
                }
            }
            cur.updateItem();
        };
    }
    if (L && Z == "check") {
        U.onclick = function () {
            cur.updateItem();
        };
    }
    if (L) {
        var h = j.insertCell(-1);
        h.align = "left";
        h.style.width = "80px";
        var S = $ce("a", "插入分组", h);
        S.tabIndex = "-1";
        if (cur.dataNode._select[M] && cur.dataNode._select[M]._item_label) {
            S.innerHTML = "编辑分组";
        }
        S.href = "javascript:void(0);";
        var p = document.createElement("input");
        p.type = "hidden";
        p.value = E ? (E._item_label || "") : "";
        h.appendChild(p);
        S.onclick = function () {
            if (!vipUser) {
                alert("只有企业版用户才能插入分组，请升级！");
                return;
            }
            openTitleEditor(p, function (aa) {
                if (aa == "-1nc" || aa == undefined) {
                    return;
                }
                p.value = trim(aa);
                S.innerHTML = p.value ? "编辑分组" : "插入分组";
                cur.updateItem();
            });
        };
        if (b) {
            h.style.display = "none";
        }
    }
    T.style.cursor = "pointer";
    g.style.cursor = "pointer";
    Q.style.cursor = "pointer";
    A.style.cursor = "pointer";
    T.onclick = function () {
        if (isMergeAnswer && !cur.isMergeNewAdded && M < cur.option_radio.length - 1) {
            alert("合并答卷模式下编辑问卷原始题目不能在中间插入选项！");
            return;
        }
        if (isMergeAnswer && !cur.isMergeNewAdded && !confirm("此题不能删除选项，是否确认增加选项？")) {
            return;
        }
        for (var aa = cur.option_radio.length - 1; aa > M; aa--) {
            cur.option_radio[aa].set_item_num(aa + 1);
            cur.option_radio[aa + 1] = cur.option_radio[aa];
        }
        if (Z == "radio") {
            cur.option_radio[M + 1] = new creat_item(cur, M + 1, x, "radio", true);
        }
        if (Z == "check") {
            cur.option_radio[M + 1] = new creat_item(cur, M + 1, x, "check", true);
        }
        cur.updateItem();
        cur.option_radio[M + 1].get_item_title().select();
    };
    g.onclick = function () {
        if (isMergeAnswer && !cur.isMergeNewAdded) {
            alert("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许删除原始题目的选项！");
            return;
        }
        if (cur.option_radio.length > 3) {
            x.deleteRow(M);
            for (var aa = M + 1; aa < cur.option_radio.length; aa++) {
                cur.option_radio[aa].set_item_num(aa - 1);
                cur.option_radio[aa - 1] = cur.option_radio[aa];
            }
            cur.option_radio.length--;
            cur.updateItem();
        } else {
            show_status_tip("选择题至少要有两个选项", 4000);
        }
    };
    Q.onclick = function () {
        if (isMergeAnswer && !cur.isMergeNewAdded) {
            alert("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许移动原始题目的选项！");
            return;
        }
        if ((M - 1) > 0) {
            c(cur.option_radio[M], cur.option_radio[M - 1]);
            if (Z == "check" || Z == "radio") {
                V(cur.option_radio[M], cur.option_radio[M - 1]);
            }
            o(cur.option_radio[M], cur.option_radio[M - 1]);
            cur.updateItem();
        }
    };
    A.onclick = function () {
        if (isMergeAnswer && !cur.isMergeNewAdded) {
            alert("很抱歉，在以合并答卷模式下修改问卷为了保持数据一致性不允许移动原始题目的选项！");
            return;
        }
        if ((M + 1) < cur.option_radio.length) {
            c(cur.option_radio[M], cur.option_radio[M + 1]);
            if (Z == "check" || Z == "radio") {
                V(cur.option_radio[M], cur.option_radio[M + 1]);
            }
            o(cur.option_radio[M], cur.option_radio[M + 1]);
            cur.updateItem();
        }
    };

    function c(ac, aa) {
        var ab = ac.get_item_title().value;
        ac.get_item_title().value = aa.get_item_title().value;
        aa.get_item_title().value = ab;
    }
    function V(ac, aa) {
        var ab = ac.get_item_check().checked;
        ac.get_item_check().checked = aa.get_item_check().checked;
        aa.get_item_check().checked = ab;
    }
    function o(ac, aa) {
        var ab = ac.get_item_value().value;
        ac.get_item_value().value = aa.get_item_value().value;
        aa.get_item_value().value = ab;
        ab = ac.get_item_jump().value;
        ac.get_item_jump().value = aa.get_item_jump().value;
        aa.get_item_jump().value = ab;
        if (ac.get_item_tb()) {
            ab = ac.get_item_tb().checked;
            ac.get_item_tb().checked = aa.get_item_tb().checked;
            aa.get_item_tb().checked = ab;
        }
        if (ac.get_item_tbr()) {
            ab = ac.get_item_tbr().checked;
            ac.get_item_tbr().checked = aa.get_item_tbr().checked;
            aa.get_item_tbr().checked = ab;
        }
        if (ac.get_item_img()) {
            ab = ac.get_item_img().value;
            ac.get_item_img().value = aa.get_item_img().value;
            aa.get_item_img().value = ab;
        }
        if (ac.get_item_imgtext()) {
            ab = ac.get_item_imgtext().checked;
            ac.get_item_imgtext().checked = aa.get_item_imgtext().checked;
            aa.get_item_imgtext().checked = ab;
        }
        if (ac.get_item_desc()) {
            ab = ac.get_item_desc().value;
            ac.get_item_desc().value = aa.get_item_desc().value;
            aa.get_item_desc().value = ab;
        }
        if (ac.get_item_label()) {
            ab = ac.get_item_label().value;
            ac.get_item_label().value = aa.get_item_label().value;
            aa.get_item_label().value = ab;
        }
    }
    this.get_item_add = function () {
        return T;
    };
    this.get_item_del = function () {
        return g;
    };
    this.get_item_table = function () {
        return R;
    };
    this.get_item_tr = function () {
        return j;
    };
    this.set_item_num = function (aa) {
        M = aa;
    };
    this.get_item_num = function () {
        return M;
    };
    this.get_item_title = function () {
        return a;
    };
    this.get_item_check = function () {
        return U;
    };
    this.get_item_huchi = function () {
        return y;
    };
    this.get_item_tb = function () {
        return r;
    };
    this.get_item_tbr = function () {
        return l;
    };
    this.get_item_img = function () {
        return Y;
    };
    this.get_item_desc = function () {
        return f;
    };
    this.get_item_label = function () {
        return p;
    };
    this.get_item_imgtext = function () {
        return X;
    };
    this.get_item_value = function () {
        return m;
    };
    this.get_item_jump = function () {
        return C;
    };
    return true;
}
function setAllRequired(c) {
    for (var a = 0; a < questionHolder.length; a++) {
        var b = questionHolder[a].dataNode._type;
        if (b != "cut" && b != "page" && questionHolder[a].dataNode._requir != c) {
            if (questionHolder[a].get_requir) {
                questionHolder[a].get_requir().checked = c;
            }
            questionHolder[a].get_span_required().style.display = c ? "" : "none";
            questionHolder[a].dataNode._requir = c;
        }
    }
    show_status_tip("设置成功！", 4000);
}
initAttrHandler();

function initAttrHandler() {
    firstPage.createAttr = createAttr;
    for (var b = 0; b < questionHolder.length; b++) {
        var a = questionHolder[b];
        setAttrHander(a);
    }
}
function setAttrHander(a) {
    a.createAttr = createAttr;
}