/**
 * Copyright (C), 1995-2006, 
 * File Name: Dialog.js
 * Encoding UTF-8 
 * Version: 4.0 
 * Date: 2007-01-10
 * History: // 修改历史记录列表，每条修改记录应包括修改日期、修改者及修改内容简述 
 * 1. Date: 2007-01-10
 *    Author: 
 *    Modification:创建 Div提示框
 * 2.
 * ...
 */
 
/**
 * 提示对话框。
 * 类似于HTML元素上的title提示。创建提示对话框可以通过实例化对话框对象，也可以通过提示对话框的辅助
 * 工具进行注册。
 * 
 * example:
 * 	  new HintDialog($('text'));将产生一个默认提示对话框，与HTML ID为text的HTML元素绑定，
 * 无内容、默认宽度、默认触发事件(即创建实例的时候，显示对话框)。
 * 	  new HintDialog($('text'), 'This a hint dialog', {width: '300px', showEvent: 'click'});
 * 将产生一个提示对话框，与HTML ID为text的HTML元素绑定，宽度为300px，并且是在点击该HTML元素后显示对话框。
 *    
 *    HintDialog.register($('text'));注册一个默认提示对话框。
 * 	  HintDialog.register($('text'), 'This a hint dialog', {width: '300px', showEvent: 'click'});
 * 注册一个有内容、设置选项的提示对话框。
 * 
 * 提示对话框引用到的样式，默认定义在dialog.css中：
 * 	  hint-dialog-top-line：对话框顶部线条样式
 * 	  hint-dialog-arrow：对话框箭头样式
 *    hint-dialog-content：对话框内容部分样式
 *    hint-dialog-content-text：对话框内容文字样式
 */
HintDialog = Class.create();
var url = document.URL.toLowerCase();

/**
 * 产生的提示对话框的HTML ID。
 * 
 */
HintDialog.id = "__widget_hint_dialog";

/**
 * 提示对话框的注册工具。
 * 将表单控件及相应的实践绑定。
 * 
 * @param Object obj 需要绑定的表单控件
 * @param String msg 提示信息
 * @param Array options 提示对话框的选项
 */
HintDialog.register = function(obj, msg, options){
	if(!obj)
		return;
	
	new HintDialog(obj, msg, options);
}

HintDialog.prototype = {
	
	/**
	 * 提示信息
	 * 
	 */
	_msg : "",
	
	_obj : null,
	
	/*------------------ initializer --------------*/
	
	/**
	 * 构造函数。
	 * 
	 * @param Object obj 需要绑定的表单控件
 	 * @param String msg 提示信息
 	 * @param Array options 提示对话框的选项 
	 */
	initialize : function(obj, msg, options){
		if(!obj)
			return;
	    var tempList = document.getElementsByTagName("DIV");
	    for(var i=0;i<tempList.length;i++){
	        if(tempList[i].className=="accurate" || tempList[i].className=="error"){
	           tempList[i].style.display = "none";
	        }
	    }
	    try{
	        SetTabPageHW(top.window.loadingIframeId);
	    }
	    catch(e){}
		this._msg = (msg) ? msg : "";
		this._options = {
			width : "",							//对话框宽度
			topOffset : 10,								//对话框相对表单控件的顶部偏远量
			leftOffset : -10,							//对话框相对表单控件的左侧偏远量
			showEvent : null,							//显示对话框的触发事件，默认为无，即创建了提示对话框后立即显示
			hideEvent : "blur"							//隐藏对话框的触发事件，默认为控件失去焦点
		}
		
		this._obj = obj;
		
		if (this._options.showEvent != null){
			Event.observe(this._obj, this._options.showEvent, 
				this._showHint.bindAsEventListener(this), false);
		}else {
			this._showHint();
		}
		
		Event.observe(this._obj, this._options.hideEvent, 
			this._hideHint.bindAsEventListener(this), false);
		
	},
	
	/*----------- private methods ---------------------*/
	
	/**
	 * 显示对话框。
	 * 
	 */
	_showHint : function(){
	
		if ($(HintDialog.id))
			return;
		
		var objPosition = Utils.getAbsolutePos(this._obj);
		//if(this._obj.id == "txtPassword" && url.indexOf("logon.aspx")>-1){
		    objPosition.y -=  54;
		//}
		var box = document.createElement("DIV");
			box.id = HintDialog.id;
		with(box.style){
			position = "absolute";
			top = (objPosition.y+this._options.topOffset) + "px";
			left = (objPosition.x+this._options.leftOffset) + "px";
			width = this._options.width;
		}
		/*
		 * 装配对话框头部
		 * 
		 */
		//box.appendChild(this._generateHeader());
		
		/*
		 * 装配对话框内容
		 */
		box.appendChild(this._generateContent());
		
		document.body.appendChild(box);
		
		if (Utils.isIE)
			Utils.hideShowCovered(box);
	},
	
	/**
	 * 隐藏对话框。
	 * 
	 */
	_hideHint : function(){
		var obj = $(HintDialog.id);
		if (!obj)
			return;
		
		document.body.removeChild(obj);
		
		if (Utils.isIE)	
			Utils.hideShowCovered(obj);
		
	},
	
	/**
	 * 生成对话框头部，即箭头部分，并返回HTML元素。
	 * 
	 * @return Div
	 */
	_generateHeader : function(){
		var header = document.createElement("DIV");
			header.className = "hint-dialog-top-line";
			header.style.width = "100%";
			
		var arrow = document.createElement("DIV");
			arrow.className = "hint-dialog-arrow";
			
		header.appendChild(arrow);
		return header;
	},
	
	/**
	 * 生成对话框内容部分，并返回HTML元素。
	 * 
	 * @return Div
	 */
	_generateContent : function(){
		var width = (Utils.getComputedStyle(this._options.width)-2) + "px";
		
		var content = document.createElement("DIV");
			//content.style.width = width;
		
		var contentPadder = document.createElement("DIV");
			//contentPadder.style.width = width;
			
		var msg = document.createElement("DIV");
			//msg.className = "hint-dialog-content-text";	     
//	    if(this._obj.id != "txtPassword"){
//	        this._msg = "<div style=\"margin-top:15px; height:30px;\">"
//	                  + "<div style=\"float:left;\"><img src=\"images/text_content-left.gif\" /></div>"
//	                  + "<div style=\"float:left; height:24px; background:url(images/text_content-bg.gif); line-height:24px; color:#265972;\">"
//	                  + this._msg
//	                  + "</div>"
//	                  + "<div style=\"float:left;\"><img src=\"images/text_content-right.gif\" /></div>"
//	                  + "</div>"
//	                  + "<div style=\"clear:both;\"></div>"
//	                  + "<div style=\"margin-left:40px; width:80px; margin-top:-42px;\"><img src=\"images/top_angle.gif\" /></div>";
//	    }
//	    else if(url.indexOf("logon.aspx")>-1){
			this._msg = "<div style=\"margin-top:15px; height:30px;\">"
			          + "<div style=\"float:left;\"><img src=\"images/text_content-left.gif\" /></div>"
			          + "<div style=\"float:left; height:24px; background:url(images/text_content-bg.gif); line-height:24px; color:#265972;\">"
			          + this._msg
			          + "</div>"
			          + "<div style=\"float:left;\"><img src=\"images/text_content-right.gif\" /></div>"
			          + "</div>"
			          + "<div style=\"clear:both;\"></div>"
			          + "<div style=\"margin-left:40px; width:80px; margin-top:-9px; position:absolute;\"><img src=\"images/buttom_angle.gif\" /></div>";
//		}
	    msg.innerHTML = this._msg;
			
		with(msg.style){
			//width = (Utils.getComputedStyle(this._options.width)-12) + "px";
			overflow = "hidden";
		}
		
		contentPadder.appendChild(msg);
		content.appendChild(contentPadder);
		
		return content;
	}
}