function $get(a) {
    return document.getElementById(a);
}
function getElementTop(b) {
    if (!b) {
        return;
    }
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
var zheZhaoCallBack = null;
var zheZhaodivId = "";

function PDF_launch(ob, zzw, zzh, callback, titlestr) {
    var d = document;
    zzw = zzw ? parseInt(zzw) : 600;
    zzh = zzh ? parseInt(zzh) : 600;
    var _docW = (d.width != undefined) ? d.width : d.body.offsetWidth;
    var _docH = Math.max(d.documentElement.clientHeight, d.documentElement.scrollHeight);
    zheZhaoCallBack = callback;
    if (window.innerHeight > _docH) {
        _docH = window.innerHeight;
    }
    _docH = Math.max(_docH, d.body.scrollHeight);
    if (d.documentElement.clientHeight > 0) {
        _docW = d.documentElement.scrollWidth;
    } else {
        _docW = d.body.scrollWidth;
    }
    var _docS = (d.all ? Math.max(d.body.scrollTop, d.documentElement.scrollTop) : window.pageYOffset);
    var _docPH = (d.all ? Math.max(d.body.clientHeight, d.documentElement.clientHeight) : window.innerHeight);
    if (d.all) {
        if (d.documentElement.clientHeight > 0) {
            _docPH = d.documentElement.clientHeight;
        }
    }
    if (window.opera) {
        _docPH = window.innerHeight;
    }
    var PDF_bg = $get("PDF_bg_chezchenz");
    if (!PDF_bg) {
        var PDF_bg = d.createElement("div");
        PDF_bg.style.display = "none";
        PDF_bg.id = "PDF_bg_chezchenz";
        d.body.appendChild(PDF_bg);
    }
    with(PDF_bg.style) {
        position = "absolute";
        backgroundColor = "#000";
        left = "0px";
        top = "0px";
        zIndex = 10001;
        filter = "alpha(opacity=60)";
        opacity = 0.6;
        width = _docW + "px";
        height = _docH + "px";
        display = "block";
    }
    var PDF_c = $get("PDF_c_chezchenz");
    if (!PDF_c) {
        PDF_c = d.createElement("div");
        PDF_c.style.display = "none";
        PDF_c.id = "PDF_c_chezchenz";
        d.body.appendChild(PDF_c);
    }
    var siteStr = "新疆国培";
    if (window.location.host.toLowerCase().indexOf("sojiang.com") > -1) {
        siteStr = "";
    }
    if (titlestr) {
        titlestr = titlestr + "-" + siteStr;
    } else {
        titlestr = siteStr;
    }
    PDF_c.innerHTML = '<div style="margin-top:7px;float:left; padding-left:8px;"><a href="http://www.sojump.com/" target="_blank" style="font-size:12px; color:#555;font-weight:bold;text-decoration:none;" id="hrefTitle">' + titlestr + '</a></div><div style="float:right; padding-right:8px;cursor:pointer; color:#666666;vertical-align:middle; margin-top:5px;" onclick="PDF_close();" ><table cellpadding="0" cellspacing="0"><tr><td style="font-size:16px;color:#ff5457"><b>×</b></td><td>关闭</td></tr></table></div>';
    var bodyHeight = document.documentElement.clientHeight || document.body.clientHeight;
    var bodyWidth = document.documentElement.clientWidth || document.body.clientWidth;
    with(PDF_c.style) {
        width = zzw + 8 + "px";
        if (bodyHeight < zzh) {
            zzh = bodyHeight - 20;
        }
        position = "absolute";
        var ttt = _docPH > zzh ? bodyHeight - zzh : 20;
        top = ttt / 2 + _docS + 10 + "px";
        height = zzh + 28 + "px";
        left = (bodyWidth - zzw) / 2 + "px";
        backgroundColor = "#ccddff";
        zIndex = 10003;
    }
    var newTop = 0;
    if (window.ZheZhaoControl) {
        var zzcon = window.ZheZhaoControl;
        newTop = getElementTop(zzcon).y;
        var sheight = d.documentElement.scrollHeight;
        if (sheight > 0 && newTop + zzh > sheight) {
            newTop = sheight - zzh - 30;
        }
        PDF_c.style.top = newTop + "px";
    }
    PDF_c.style.display = "";
    var PDF_i = $get(ob);
    zheZhaodivId = "";
    var obChanged = false;
    if (PDF_i) {
        PDF_i.style.display = "none";
        zheZhaodivId = ob;
    } else {
        PDF_i = $get("PDF_i_chezchenz");
        if (!PDF_i) {
            PDF_i = d.createElement("iframe");
            PDF_i.id = "PDF_i_chezchenz";
            PDF_i.setAttribute("frameBorder", "0");
            d.body.appendChild(PDF_i);
        }
        if (document.all) {
            PDF_i.scrolling = "yes";
        }
        if (PDF_i.src && PDF_i.src.indexOf(ob) == -1) {
            obChanged = true;
        }
        PDF_i.src = ob;
    }
    with(PDF_i.style) {
        position = "absolute";
        width = zzw + "px";
        margin = "0px";
        padding = "0px";
        border = "0px";
        if (bodyHeight < zzh) {
            zzh = bodyHeight - 20;
        }
        height = zzh + "px";
        top = ((_docPH - zzh) / 2 + _docS + 34) + "px";
        left = ((bodyWidth - zzw) / 2 + 4) + "px";
        zIndex = 10005;
        background = "white";
    }
    if (obChanged) {
        setTimeout(function () {
            if (PDF_c && PDF_c.style.display != "none") {
                PDF_i.style.display = "block";
            }
        }, 1000);
    } else {
        PDF_i.style.display = "block";
    }
    if (newTop) {
        PDF_i.style.top = newTop + 24 + "px";
    }
    if (!$get("PDF_IframeObj")) {
        var iframeObj = d.createElement("iframe");
        iframeObj.id = "PDF_IframeObj";
        iframeObj.style.position = "absolute";
        d.body.appendChild(iframeObj);
    } else {
        iframeObj = $get("PDF_IframeObj");
    }
    if (iframeObj != null) {
        iframeObj.style.left = PDF_c.style.left;
        iframeObj.style.top = PDF_c.style.top;
        iframeObj.style.display = "";
        iframeObj.style.zIndex = 10000;
        iframeObj.style.width = PDF_c.offsetWidth + "px";
        iframeObj.style.height = PDF_c.offsetHeight + "px";
    }
}
function PDF_close(b) {
    var a = $get("divFlash");
    if (a) {
        a.style.display = "";
    }
    var c = document.getElementById(zheZhaodivId);
    if (c) {
        c.style.display = "none";
    }
    if ($get("PDF_i_chezchenz")) {
        $get("PDF_i_chezchenz").style.display = "none";
    }
    if ($get("PDF_bg_chezchenz")) {
        $get("PDF_bg_chezchenz").style.display = "none";
    }
    if ($get("PDF_c_chezchenz")) {
        $get("PDF_c_chezchenz").style.display = "none";
    }
    if ($get("PDF_IframeObj")) {
        $get("PDF_IframeObj").style.display = "none";
    }
    window.ZheZhaoControl = null;
    if (zheZhaoCallBack) {
        zheZhaoCallBack(b);
    }
}