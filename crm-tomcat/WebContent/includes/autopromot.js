// autopromot.js

function jsAuto(hint_div_id) {
	this._msg = []; // 提示信息库
	this._x = [];	// 提示框中的供选择的文本
	
	this._hint_div = document.getElementById(hint_div_id); // 提示框div
	if (! this._hint_div) return null;
	this._hint_div.style.visibility = "hidden";
	this._hint_div.style.position = "absolute";
	this._hint_div.style.zIndex = "9999";
	this._hint_div.style.overflow = "auto";
	this._hint_div.style.height = "100";
	var obj = this;
	this._hint_div.onblur = function() { obj.hide(); };
	
	this._r = null; // 需要提示的输入框(input)
	this._c = 0;
	this._s = false;
	this._to_be_matched = ""; // 需要提示的输入框(input)的value
	
	return this; // 
}

jsAuto.prototype.directionKey
  = function() { 
		with (this) {
			var e = _e.keyCode ? _e.keyCode : _e.which;
			var l = _hint_div.childNodes.length;
			if (_c>l-1 || _c<0) 
				_s = false;
		
			if ( e==40  &&  _s ) {
				_hint_div.childNodes[_c].className="mouseout";
				(_c >= l-1) ? _c=0 : _c ++;
				_hint_div.childNodes[_c].className="mouseover";
			} else if ( e==38  &&  _s ) {
				_hint_div.childNodes[_c].className="mouseout";
				_c--<=0 ? _c = _hint_div.childNodes.length-1 : "";
				_hint_div.childNodes[_c].className="mouseover";
			} else if( e==13 ) {
				if(_hint_div.childNodes[_c]  &&  _hint_div.style.visibility=="visible") {
					_r.value = _x[_c];
					_hint_div.style.visibility = "hidden";
				}
			}
			if( !_s ) {
				_c = 0;
				_hint_div.childNodes[_c].className="mouseover";
				_s = true;
			}
		}
	};

// mouseEvent.
jsAuto.prototype.domouseover
	= function(obj) { 
		with (this) {
			_hint_div.childNodes[_c].className = "mouseout";
			_c = 0;
			obj.tagName=="DIV" ? obj.className="mouseover" : obj.parentElement.className="mouseover";
		}
	};

jsAuto.prototype.domouseout
	= function(obj) {
		obj.tagName=="DIV" ? obj.className="mouseout" : obj.parentElement.className="mouseout";
	};

jsAuto.prototype.doclick
	= function(msg) { 
		with (this) {
			if (_r) {
				_r.value = msg;
				_hint_div.style.visibility = "hidden";
				
				document.getElementById("reload").href += msg.split("_")[1];
				document.getElementById("reload").click();
				//location.search = "?cust_id="+msg.split("_")[1];
				
			} else {
				alert("javascript autocomplete ERROR :\n\n can not get return object.");
			}
		}
	};

// object method;
jsAuto.prototype.item
	= function(msg) {
		if( msg.indexOf(",")>0 ) {
			var arrMsg=msg.split(",");
			for(var i=0; i<arrMsg.length; i++)
				if (arrMsg[i]) this._msg.push(arrMsg[i]);
		} else {
			this._msg.push(msg);
		}
		this._msg.sort();
	};

jsAuto.prototype.append 
	= function(msg) { 
		with (this) {
			_x.push(msg);
			var div = document.createElement("DIV");
			//bind event to object.
			div.onmouseover = function(){domouseover(this);};
			div.onmouseout = function(){domouseout(this);};
			div.onclick = function(){doclick(msg);};
			var obj = this;
			div.onblur = function() { obj.hide(); };
			var re  = new RegExp("(" + _to_be_matched + ")", "i");
			div.style.lineHeight="140%";
			div.className = "mouseout";
			if (_to_be_matched) div.innerHTML = msg.replace(re , "<strong>$1</strong>");
			div.style.fontFamily = "verdana";
		
			_hint_div.appendChild(div);
		}
	};

jsAuto.prototype.display
	= function() { 
		with(this) {
			_hint_div.style.left = _r.offsetLeft;
			_hint_div.style.width = _r.offsetWidth;
			_hint_div.style.top = _r.offsetTop + _r.offsetHeight;
			_hint_div.style.visibility = "visible";
		}
	};

jsAuto.prototype.hide
	= function() { 
		with(this) {
			if (document.activeElement==_hint_div)
				return;
				
			for (var i=0; i<_hint_div.childNodes.length; i++)
				if (document.activeElement==_hint_div.childNodes[i])
					return;
					
			_hint_div.style.visibility = "hidden";
		}
	};
	
jsAuto.prototype.handleEvent
    = function(elem, event) { 
        with (this) {
	        _e = event;
	        var e = _e.keyCode ? _e.keyCode : _e.which;
	        
	        _r = elem;
		    _to_be_matched = elem.value;
		    _hint_div.innerHTML="";
			_x = [];
		    
		    if (_to_be_matched=="") {  	
		    	_hint_div.style.visibility="hidden";
		    	return;
		    }
		    
	        var re = new RegExp("^" + _to_be_matched, "i"); // i: 不区分大小写       
			var matched = false;
	        for (var i=0; i<_msg.length; i++) {
		        if (re.test(_msg[i])) {
			        append(_msg[i]);
			        matched = true;
		        }
	        }

			if (matched) 
	      		display();
      		else 
				_hint_div.style.visibility="hidden";

	        if (matched) {
		        if (e==38 || e==40 || e==13) {
			        directionKey();
		        } else {
			        _c=0;
			        _hint_div.childNodes[_c].className = "mouseover";
			        _s=true;
		        }
	        }
        }
    };
