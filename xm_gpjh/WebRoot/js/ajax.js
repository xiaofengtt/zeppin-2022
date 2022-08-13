//////////////////////////
function submitRequest(formObj, method, type, myProcess, conname) {
	if (formObj) {// 判断form不为null
		var sinfo = null;
		var url = formObj.action;// 获得url
		if (url.indexOf("?") == -1) {
			url += "?time=";
		} else {
			url += "&time=";
		}
		url += new Date().getTime();// url最后跟时间防止ie缓存
		if ((method.toLowerCase()) == "post") {
			sinfo = formToString(formObj);// 做换字符编码
		}
		request(url, method, sinfo, type, myProcess, conname);// 提交请求
		return true;
	}
	return false;
}

// 把表单中的参数转为字符串
function formToString(formObj) {
	var allStr = "";
	if (formObj) {
		var elementsObj = formObj.elements;
		var obj;
		if (elementsObj) {
			for ( var i = 0; i < elementsObj.length; i += 1) {
				obj = elementsObj[i];
				if (obj.name != undefined
						&& obj.name != ""
						&& ((obj.type != undefined && obj.type == 'checkbox' && obj.checked)
								|| (obj.type == undefined && !obj.disabled) || (obj.type != undefined
								&& obj.type != 'checkbox' && !obj.disabled))) {
					allStr += "&" + obj.name + "="
							+ encodeURIComponent(obj.value);
				}
			}
		} else {
			alert("没有elements对象!");
			return;
		}
	} else {
		alert("form不存在!");
		return;
	}
	return allStr;
}

function request(url, method, sinfo, type, myProcess, conname) {
	var http_request = init_request();
	http_request.onreadystatechange = function() {
		if (http_request.readyState == 4 && http_request.status == 200) {
			var result = "";
			if (type == null || type == "" || type == "text") {// 判断返回数据类型
				result = http_request.responseText;
			} else if (type == "xml") {
				result = http_request.responseXml;
			}
			if (myProcess) {// 判断自定义处理数据方法是否存在
				myProcess(result, conname);
			} else {
				pageChange(result, conname);
			}
		}
	}
	http_request.open(method, url, true);
	if (method == null || method == "" || (method.toLowerCase()) == "post") {
		http_request.setRequestHeader("Content-Length", sinfo.length);
		http_request.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
	}
	http_request.send(sinfo);
}

function pageChange(count, conname) {
	if (conname) {
		conname.innerHTML = count;
	}
}

function init_request() {
	var http_request = false;
	if (window.XMLHttpRequest) { // Mozilla
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {// MiME
			http_request.overrideMimeType("text/xml");
		}
	} else {
		if (window.ActiveXObject) { // IE
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
				}
			}
		}
	}
	if (!http_request) { //
		window.alert("XMLHttpRequest=null");
		return false;
	}
	return http_request;
}
var _tempurl;
var _doc;
function showinfobyurl(e,url){
	try{
		var oThis = arguments.callee;
		if (!url) {
			oThis.sug.style.visibility = 'hidden';
			document.onmousemove = null;
			return;
		}
			
		if (!oThis.sug) {
			var div = document.createElement('div'), css = 'top:0; left:0; position:absolute; z-index:100; visibility:hidden';
			div.style.cssText = css;
			div.id='_showinfodiv';
			div.setAttribute('style', css);
			var sug = document.createElement('div'), css = 'font:normal 12px/16px "宋体"; white-space:nowrap; color:#666; padding:3px; position:absolute; left:0; top:0; z-index:10; background:#f9fdfd; border:1px solid #0aa';
			sug.style.cssText = css;
			sug.setAttribute('style', css);
			var ifr = document.createElement('iframe'), css = 'position:absolute; left:0; top:0; z-index:8; filter:alpha(opacity=0); opacity:0';
			ifr.style.cssText = css;
			ifr.setAttribute('style', css);
			div.appendChild(ifr);
			div.appendChild(sug);
			div.sug = sug;
			if(document.getElementById('_showinfodiv')==null){document.body.appendChild(div);}
			oThis.sug = div;
			oThis.ifr = ifr;
			div = ifr = sug = null;
		}
		var e = e || window.event, obj = oThis.sug, ifr = oThis.ifr;
		obj.sug.innerHTML = "<img src='/js/extjs/images/default/grid/wait.gif'/>";
		var _temp = obj.sug.innerHTML;
		var w = obj.sug.offsetWidth, 
			h = obj.sug.offsetHeight, 
			dw = document.documentElement.clientWidth || document.body.clientWidth
			dh = document.documentElement.clientHeight || document.body.clientHeight;
		var st = document.documentElement.scrollTop || document.body.scrollTop, 
			sl = document.documentElement.scrollLeft || document.body.scrollLeft;
		var left = e.clientX + sl + 17 + w < dw + sl && e.clientX + sl + 15
				|| e.clientX + sl - 8 - w, 
			top = e.clientY + st + 17 + h < dh + st
				&& e.clientY + st + 17 || e.clientY + st - 5 - h;
		obj.style.left = left + 10 + 'px';
		obj.style.top = top + 10 + 'px';
		ifr.style.width = w + 3 + 'px';
		ifr.style.height = h + 3 + 'px';
		obj.style.visibility = 'visible';
		document.onmousemove = function(e) {
			var e = e || window.event, 
				st = document.documentElement.scrollTop	|| document.body.scrollTop,
				sl = window.top.document.documentElement.scrollLeft || window.top.document.body.scrollLeft;
			var left = e.clientX + sl + 17 + w < dw + sl && e.clientX + sl + 15	|| e.clientX + sl - 8 - w, 
				top = e.clientY + st + 17 + h < dh + st	&& e.clientY + st + 17 || e.clientY + st - 5 - h;
			obj.style.left = left + 'px';
			obj.style.top = top + 'px';
		}
		if(_tempurl!=url){
			_tempurl = url;
			if(url.indexOf('?')>=0){
				url=url+"&"
			}else{
				url=url+"?"
			}
			url=url+"time="+(new Date()).getTime();
		    var XMLHttpReq = init_request();
		    XMLHttpReq.onreadystatechange = function(){
		    	if (XMLHttpReq.readyState == 4) { // 判断对象状态
			        if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息	 
			            _doc =  XMLHttpReq.responseText;
			        	obj.sug.innerHTML = _doc;
			        	w = obj.sug.offsetWidth;
			    		h = obj.sug.offsetHeight;
			        }
		    	}
		    };
		    XMLHttpReq.open("GET", url, true);
		    XMLHttpReq.send(null);  // 发送请求
		}else{
			obj.sug.innerHTML = _doc;
	    	w = obj.sug.offsetWidth;
			h = obj.sug.offsetHeight;
		}
	}catch(e){		
	}
}

function showinfo(e, str) {
	try{
		var oThis = arguments.callee;
		if (!str) {
			oThis.sug.style.visibility = 'hidden';
			document.onmousemove = null;
			return;
		}
		if (!oThis.sug) {
			var div = document.createElement('div'), css = 'top:0; left:0; position:absolute; z-index:100; visibility:hidden';
			div.style.cssText = css;
			div.setAttribute('style', css);
			var sug = document.createElement('div'), css = 'font:normal 12px/16px "宋体"; white-space:nowrap; color:#666; padding:3px; position:absolute; left:0; top:0; z-index:10; background:#f9fdfd; border:1px solid #0aa';
			sug.style.cssText = css;
			sug.setAttribute('style', css);
			var ifr = document.createElement('iframe'), css = 'position:absolute; left:0; top:0; z-index:8; filter:alpha(opacity=0); opacity:0';
			ifr.style.cssText = css;
			ifr.setAttribute('style', css);
			div.appendChild(ifr);
			div.appendChild(sug);
			div.sug = sug;
			document.body.appendChild(div);
			oThis.sug = div;
			oThis.ifr = ifr;
			div = ifr = sug = null;
		}
		var e = e || window.event, obj = oThis.sug, ifr = oThis.ifr;
		obj.sug.innerHTML = str;
	
		var w = obj.sug.offsetWidth, h = obj.sug.offsetHeight, dw = document.documentElement.clientWidth
				|| document.body.clientWidth;
		dh = document.documentElement.clientHeight || document.body.clientHeight;
		var st = document.documentElement.scrollTop || document.body.scrollTop, sl = document.documentElement.scrollLeft
				|| document.body.scrollLeft;
		var left = e.clientX + sl + 17 + w < dw + sl && e.clientX + sl + 15
				|| e.clientX + sl - 8 - w, top = e.clientY + st + 17 + h < dh + st
				&& e.clientY + st + 17 || e.clientY + st - 5 - h;
		obj.style.left = left + 10 + 'px';
		obj.style.top = top + 10 + 'px';
		ifr.style.width = w + 3 + 'px';
		ifr.style.height = h + 3 + 'px';
		obj.style.visibility = 'visible';
		document.onmousemove = function(e) {
			var e = e || window.event, st = document.documentElement.scrollTop
					|| document.body.scrollTop, sl = document.documentElement.scrollLeft
					|| document.body.scrollLeft;
			var left = e.clientX + sl + 17 + w < dw + sl && e.clientX + sl + 15
					|| e.clientX + sl - 8 - w, top = e.clientY + st + 17 + h < dh
					+ st
					&& e.clientY + st + 17 || e.clientY + st - 5 - h;
			obj.style.left = left + 'px';
			obj.style.top = top + 'px';
		}
	}catch(e){
	}
}
//计算字节数（用于校验字符串长度是否可以用VARCHAR2）
function mbStringLength(s) {
    var totalLength = 0;
    var i;
    var charCode;
    for (i = 0; i < s.length; i++) {
      charCode = s.charCodeAt(i);
      if (charCode < 0x007f) {
        totalLength = totalLength + 1;
      } else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
        totalLength += 1;
      } else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
        totalLength += 2;
      }
    }
    return totalLength;
  } 