/**
 * Copyright (C), 2004-2006, 
 * File Name: Utils.js
 * Encoding UTF-8
 * Version: 1.0
 * Date: Sep 12, 2006
 * History:	
 */
 
/**
 * 常用工具类，提供了大量静态常用方法。
 *
 */
Utils = Class.create();

/**
 * 判断对象是否为对象
 * 
 * @param Object obj 欲判断的对象
 * @return boolean true - 是对象，false - 不是对象
 */
Utils.isObject = function(obj){
	return ( obj == null || typeof(obj) == "undefined" ) ? false : true;
}

/**
 * 判断输入是否为货币数值
 * 
 * @param float money
 * @return boolean
 */
Utils.isMoney = function(money){

//	return /^[1-9][0-9]*\.[0-9]{2}/.test(money) ||
//		   /^0\.[0-9]{2}/.test(money);
	return Utils.isFloatValue(money);
}

/**
 * 判断一个对象是不是函数对象
 * 
 * @param fun 对象
 * @return boolean
 */
Utils.isFunction = function(fun){
	return (fun != null && typeof(fun) == "function");
}

/**
 * 判断一个数值（或字符串）是否为非负数。
 * 
 */
Utils.isNonNegative = function(num){
	
	return /^[1-9][0-9]*/.test(num) || (num == 0);
}

/**
 * 验证商品类别编号格式是否合法
 * 
 * @param code
 * @return boolean
 */
Utils.isWareCategoryCode = function(code){
    
    var reg = /^\w+$/;
    
    if (!reg.test(code)){
       return false;
    }
    
    return true;
}

Utils.isKHtml = /Konqueror|Safari|KHTML/i.test(navigator.userAgent);
Utils.isIE = (/msie/i.test(navigator.userAgent) &&
		   	  !/opera/i.test(navigator.userAgent));

Utils.isIE5 = (Utils.isIE && /msie 5\.0/i.test(navigator.userAgent));

/**
 * 获取某HTML元素的符合指定样式名称的第一个父元素。
 * 
 * @param HTMLElement 需要匹配的HTML元素
 * @param String className 匹配的样式名称
 * @return HTMLElement 返回符合条件的父元素，或返回空
 */
Utils.getParentByClassName = function(el, className){
	if (!el)
		return null;
		
	var e = el.parentNode;
	if (e.className == className)
		return e;
		
	Utils.getParentByClassName(e, className);
}

/**
 * 获取某HTMLElement下指定样式的子元素集合
 * 
 * @param HTMLElment el
 * @param String className
 * @return Array
 */
Utils.getChildrenByClassName = function(el, className){
	if (!el)
	var children = new Array();
	for (var i=0; i<el.childNodes.length; i++){
		if (el.childNodes[i].className == className)
			children.push(el.childNodes[i]);
	}
	return children;
}

/**
 * 
 */
Utils.ScriptFragmentRegExp = new RegExp(Prototype.ScriptFragment, 'img');

/**
 * 在指定的字符串中执行其中包含的脚本。
 * 
 * @param String str
 */
Utils.evalScripts = function(str){
	var scripts = str.match(Utils.ScriptFragmentRegExp);
	
	if (scripts) {
      	match = new RegExp(Prototype.ScriptFragment, 'im');
      	setTimeout((function() {
       		for (var i = 0; i < scripts.length; i++)
          		eval(scripts[i].match(match)[1]);
      	}).bind(this), 10);
   }
}

/**
 * 删除指定字符串中的所有脚本块。
 * 
 * @param String str
 * @return String
 */
Utils.omitScriptFragment = function(str){
    return str.replace(Utils.ScriptFragmentRegExp, '');
}

Utils.getComputedStyle = function(str){
	var reg = /[0-9]+/i;
	var value = str.match(reg);
	
	return (value == null) ? 0 : parseInt(value);
}

/**
 * 获取某个元素的绝对坐标。
 * 
 * @param HMTLElement el
 * @return Object
 */
Utils.getAbsolutePos = function(el) {
	var SL = 0, ST = 0;
	var is_div = /^div$/i.test(el.tagName);
	if (is_div && el.scrollLeft)
		SL = el.scrollLeft;
	if (is_div && el.scrollTop)
		ST = el.scrollTop;
	var r = { x: el.offsetLeft - SL, y: el.offsetTop - ST };
	if (el.offsetParent) {
		var tmp = this.getAbsolutePos(el.offsetParent);
		r.x += tmp.x;
		r.y += tmp.y;
	}
	
	return r;
}

/**
 * 验证是否为浮点数
 * 
 * @param String str 
 * @return boolean
 */
Utils.isFloatValue = function(floatValue){
    
    var reg = /(^((-|\+)?0\.)(\d*)$)|(^((-|\+)?[1-9])+\d*(\.\d*)?$)/;  
    
    return reg.test(floatValue);    
}

/**
 * HTML页面中比较龌龊的元素
 * 
 */
Utils.TERRIABLE_ELEMENTS = new Array("applet", "iframe", "select");

/**
 * 屏蔽HTML页面中比较龌龊的元素，如applet、iframe、select。
 * 由于Div在显示在这些元素之上，会出现破坏Div元素的情形，因此将在Div元素下的这些龌龊的元素隐藏。
 * 
 * @param HTMLEelement el 需要屏蔽龌龊元素的HTMLElement对象
 */
Utils.hideShowCovered = function(el){
	function getVisib(obj){
		var value = obj.style.visibility;
		if (!value) {
			if (document.defaultView && typeof (document.defaultView.getComputedStyle) == "function") { // Gecko, W3C
				if (!Utils.isKHtml)
					value = document.defaultView.
						getComputedStyle(obj, "").getPropertyValue("visibility");
				else
					value = '';
			} else if (obj.currentStyle) { // IE
				value = obj.currentStyle.visibility;
			} else
				value = '';
		}
		return value;
	};

	var p = Utils.getAbsolutePos(el);
	var EX1 = p.x;
	var EX2 = el.offsetWidth + EX1;
	var EY1 = p.y;
	var EY2 = el.offsetHeight + EY1;

	for (var k = Utils.TERRIABLE_ELEMENTS.length; k > 0; ) {
		var ar = document.getElementsByTagName(Utils.TERRIABLE_ELEMENTS[--k]);
		var cc = null;

		for (var i = ar.length; i > 0;) {
			cc = ar[--i];

			p = Utils.getAbsolutePos(cc);
			var CX1 = p.x;
			var CX2 = cc.offsetWidth + CX1;
			var CY1 = p.y;
			var CY2 = cc.offsetHeight + CY1;

			if (self.hidden || (CX1 > EX2) || (CX2 < EX1) || (CY1 > EY2) || (CY2 < EY1)) {
				if (!cc.__msh_save_visibility) {
					cc.__msh_save_visibility = getVisib(cc);
				}
				cc.style.visibility = cc.__msh_save_visibility;
			} else {
				if (!cc.__msh_save_visibility) {
					cc.__msh_save_visibility = getVisib(cc);
				}
				cc.style.visibility = "hidden";
			}
		}
	}
}

/**
 * 
 * 
 */
Utils.calculateStringWidth = function(str){
	if (StringUtils.isEmpty(str))
		return null;
		
	var span = document.createElement("span");
	span.innerHTML = str;
	document.body.appendChild(span);
	
	var width = span.offsetWidth;
	document.body.removeChild(span);
	
	return width;
}

Utils.autoFitImage = function(img, width, height){
	
	if (!img || img.height == 0 || img.width == 0)
		return "&nbsp";
	
	var str = "<img src='" + img.src + "' ";
	
	if (img.width > width || img.height > height){
		var widthRate = img.width / width;
		var heightRate = img.height / height;
		if (widthRate > heightRate){
			str += "width='" + width + "' ";
			img.width = width;
		}else{
			str += "height='" + height + "' ";
			img.height = height;
		}
	}
		
	str += "/>";
	
	return str;
}

/*------------------- String ------------------------------*/


/**
 * 去除字符串中的头尾空白字符
 * 
 */
if (!String.prototype.trim){
	String.prototype.trim  =  function(){
    	return  this.replace(/(^\s*)|(\s*$)/g,  "");
	}
}

/**
 * 根据显示字符串的容器的宽度，自动截取字符串。
 * 
 * @param int containerWidth 显示字符串的容器的宽度
 * @return String
 */
String.prototype.substrByContainerWidth = function(containerWidth){
	var titleWidth = Utils.calculateStringWidth(this);
	if (titleWidth > containerWidth){
		var length = parseInt(this.length * (containerWidth/titleWidth));
		return this.substr(0, length-1)
	}else 
		return this;
}

/**
 * 判断字符串是否以某个字符串开始
 * 
 * @param String prefix
 * @return boolean
 */
if (!String.startsWith){
	String.prototype.startsWith = function(prefix){
		if (StringUtils.isEmpty(prefix))
			return false;
			
		return (this.indexOf(prefix) > -1);
	}
}

/*----------------- StringUtils ----------------------------*/


var StringUtils = Class.create();

StringUtils.isEmpty = function(str){
	return (!Utils.isObject(str) || str.length == 0) ? true : false;
}

/*---------------------- Date ------------------------------*/

/**
 * 将已格式化的字符串，解析成日期对象
 * 
 * @param String str 已格式化的字符串
 * @param String fmt 格式化规则
 * @return Date
 */
if (!Date.parseDate){
	Date.parseDate = function (str, fmt) {
		var y = 0;
		var m = -1;
		var d = 0;
		var a = str.split(/\W+/);
		if (!fmt) {
			fmt = "%Y-%m-%d";
		}
		var b = fmt.match(/%./g);
		var i = 0, j = 0;
		var hr = 0;
		var min = 0;
		for (i = 0; i < a.length; ++i) {
			if (!a[i])
				continue;
			switch (b[i]) {
			    case "%d":
			    case "%e":
				d = parseInt(a[i], 10);
				break;
	
			    case "%m":
				m = parseInt(a[i], 10) - 1;
				break;
	
			    case "%Y":
			    case "%y":
				y = parseInt(a[i], 10);
				(y < 100) && (y += (y > 29) ? 1900 : 2000);
				break;
	
			    case "%H":
			    case "%I":
			    case "%k":
			    case "%l":
				hr = parseInt(a[i], 10);
				break;
	
			    case "%P":
			    case "%p":
				if (/pm/i.test(a[i]) && hr < 12)
					hr += 12;
				break;
	
			    case "%M":
				min = parseInt(a[i], 10);
				break;
			}
		}
		if (y != 0 && m != -1 && d != 0) {
			return new Date(y, m, d, hr, min, 0);
		}

	}	
}