/*  
 * 此方法為可多選下拉框，繼承自 Ext.form.ComboBox
 *  實現複選功能
 *  date ：2010-11-01  czc
 */
//Ext.form.MultiSelect begin

 // add RegExp.escape if it has not been already added
 if('function' !== typeof RegExp.escape) {
 RegExp.escape = function(s) {
    if('string' !== typeof s) {
     return s;
    }
    // Note: if pasting from forum, precede ]/\ with backslash manually
    return s.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
 }; // eo function escape
 }

 // create namespace
 Ext.ns('Ext.form');

/**
 * 此方法实现了combobox下拉框自适应宽度
 * date:2010-11-08 czc
 */
 //WhatyComboBox begin
Ext.form.WhatyComboBox = Ext.extend(Ext.form.ComboBox, {
	initList:function(){
		 Ext.form.WhatyComboBox.superclass.initList.apply(this, arguments);
		 this.list.setSize('auto'); 
		 this.innerList.setSize('auto');
		 if(Ext.isIE){
			 this.innerList.setStyle('display','inline');
			 this.innerList.setStyle('overflow-x','hidden'); 
		 }
	 }
	,doQuery : function(C, B) {
		if (C === undefined || C === null) {
			C = ""
		}
		var A = {
			query : C,
			forceAll : B,
			combo : this,
			cancel : false
		};
		if (this.fireEvent("beforequery", A) === false
				|| A.cancel) {
			return false
		}
		C = A.query;
		B = A.forceAll;
		if (B === true || (C.length >= this.minChars)) {
			if (this.lastQuery !== C) {
				this.lastQuery = C;
				if (this.mode == "local") {
					this.selectedIndex = -1;
					if (B) {
						this.store.clearFilter()
					} else {
						this.store.filter(this.displayField, C,true)
					}
					this.onLoad()
				} else {
					this.store.baseParams[this.queryParam] = C;
					this.store.load( {
						params : this.getParams(C)
					});
					this.expand()
				}
			} else {
				this.selectedIndex = -1;
				this.onLoad()
			}
		}
	}
});
//WhatyComboBox end
 
Ext.form.MultiSelect = Ext.extend(Ext.form.WhatyComboBox, {

     // configuration options
     checkField:'checked'
         ,separator:' or '
         ,initComponent:function() {
        // template with checkbox
        if(!this.tpl) {
         this.tpl =
          '<tpl for=".">'
          +'<div class="x-combo-list-item">'
          +'<img src="' + Ext.BLANK_IMAGE_URL + '" '
          +'class="ux-MultiSelect-icon ux-MultiSelect-icon-'
          +'{[values.' + this.checkField + '?"checked":"unchecked"' + ']}">'
          +'{[values.'+this.displayField+']}'
          +'&nbsp;&nbsp;&nbsp;&nbsp;</div>'
          +'</tpl>'
         ;
        }

        // call parent
        Ext.form.MultiSelect.superclass.initComponent.apply(this, arguments);

        // install internal event handlers
        this.on({
         scope:this
         ,beforequery:this.onBeforeQuery
        // ,blur:this.onRealBlur
         //,expand:this.onExpand
        });

        // remove selection from input field
        this.onLoad = this.onLoad.createSequence(function() {
         if(this.el) {
          var v = this.el.dom.value;
          this.el.dom.value = '';
          this.el.dom.value = v;
         }
        });

     } // e/o function initComponent
	 
     ,initEvents:function() {
        Ext.form.MultiSelect.superclass.initEvents.apply(this, arguments);

        // disable default tab handling - does no good
        this.keyNav.tab = false;

     } // eo function initEvents
    ,clearValue:function() {
        this.value = '';
        this.setRawValue(this.value);
        this.store.clearFilter();
        this.store.each(function(r) {
         r.set(this.checkField, false);
        }, this);
        if(this.hiddenField) {
         this.hiddenField.value = '';
        }
        this.applyEmptyText();
     } // eo function clearValue

     ,getCheckedDisplay:function() {
        var re = new RegExp(this.separator, "g");
        return this.getCheckedValue(this.displayField).replace(re, this.separator + ' ');
     } // eo function getCheckedDisplay

     ,getCheckedValue:function(field) {
        field = field || this.valueField;
        var c = [];

        // store may be filtered so get all records
        var snapshot = this.store.snapshot || this.store.data;

        snapshot.each(function(r) {
         if(r.get(this.checkField)) {
          c.push(r.get(field));
         }
        }, this);

        return c.join(this.separator);
     } // eo function getCheckedValue
     ,reSelected:function(displayValue){
     	var c = displayValue.split(this.separator);
     	for(var i=0;i<c.length;i++){
     		this.store.each(function(r) {
         		if(r.get(this.displayField)== c[i] ){
         			r.set(this.checkField, true);
                 }else{
                 	r.set(this.checkField, false);
                 }
    	       }, this);
     	}
 		
     } // eo function reSelected
     ,onBeforeQuery:function(qe) {
    	 this.reSelected(qe.query);
        qe.query = qe.query.replace(new RegExp(RegExp.escape(this.getCheckedDisplay()) + '[ ' + this.separator + ']*'), '');
     } // eo function onBeforeQuery

     ,onSelect:function(record, index) {
         if(this.fireEvent('beforeselect', this, record, index) !== false){
	         // toggle checked field
	         record.set(this.checkField, !record.get(this.checkField));
	         // display full list
	         if(this.store.isFiltered()) {
	          this.doQuery(this.allQuery);
	         }
	         // set (update) value and fire event
	         this.setSelectValue(this.getCheckedValue());
	         this.fireEvent('select', this, record, index);
         }
     } // eo function onSelect

     ,setSelectValue:function(v) {
        if(v) {
         v = '' + v;
         if(this.valueField) {
          this.store.clearFilter();
          this.store.each(function(r) {
           var checked = !(!v.match(
            '(^|' + this.separator + ')' + RegExp.escape(r.get(this.valueField))
            +'(' + this.separator + '|$)'))
           ;

           r.set(this.checkField, checked);
          }, this);
          this.value = this.getCheckedValue();
          this.setRawValue(this.getCheckedDisplay());
          if(this.hiddenField) {
           this.hiddenField.value = this.value;
          }
         }
         else {
          this.value = v;
          this.setRawValue(v);
          if(this.hiddenField) {
           this.hiddenField.value = v;
          }
         }
         if(this.el) {
          this.el.removeClass(this.emptyClass);
         }
        }
        else {
          this.clearValue();
        }
     } // eo function setValue

     ,selectAll:function() {
             this.store.each(function(record){
                 // toggle checked field
                 record.set(this.checkField, true);
             }, this);

             // display full list
             this.doQuery(this.allQuery);
             this.setSelectValue(this.getCheckedValue());
         } // eo full selectAll



         ,deselectAll:function() {
        this.clearValue();
         } // eo full deselectAll


}); // eo extend

     // register xtype
Ext.reg('multiSelect', Ext.form.MultiSelect);

// Ext.form.MultiSelect end




	/*此方法用于实现gridpanel在点击重置按钮时自适应内容的宽度
	 * date：2010-11-05 czc
	 * */
	// resizeGrid begin  
    function resizeGrid(grid){
    	var cellPadding= 8;
    	var gridView = grid.getView();
    	for(var i = 1 ; i< grid.getColumnModel().getColumnCount();i++ ){
    		var el = gridView.getHeaderCell(i), width, rowIndex, count;
    		
    		if (gridView.cm.isFixed(i) || gridView.cm.isHidden(i)) {
                return;
            }
            el = el.firstChild;
            el.style.width = '0px';
            width = el.scrollWidth;
            el.style.width = 'auto';
            for (rowIndex = 0, count = gridView.ds.getCount(); rowIndex < count; rowIndex++) {
                el = gridView.getCell(rowIndex, i).firstChild;
                el.style.width = '0px';
                width = Math.max(width, el.scrollWidth);
                el.style.width = 'auto';
            }
            gridView.onColumnSplitterMoved(i, width + cellPadding);
    	}
    }
    // resizeGrid begin
    
    
    /*  
     * 实现保存列表页面功能，修改原有cookieProvider中保存数量超过4k时丢失数据问题
     *  date ：2010-11-03  czc
     */
    //Ext.form.WhatyCookieProvider begin
    Ext.state.WhatyCookieProvider = function(config){   
        Ext.state.WhatyCookieProvider.superclass.constructor.call(this);   
        try{   
            if(Ext.isIE)   
            {   
                this.storage = new IESessionStorage("whatyExtState");   
            }else if(window.globalStorage)   
            {   
                this.storage =  window.globalStorage[window.location.hostname];   
            }   
        }catch(e){   
        }   
    };   
      
    Ext.extend(Ext.state.WhatyCookieProvider, Ext.state.Provider, {   
        get : function(name, defaultValue){   
            if(!this.storage)   
            {   
                return defaultValue;   
            }   
            try{   
                var value = this.storage.getItem("ys-"+name)   
                return value == "undefined" ? defaultValue : this.decodeValue(value);   
            }catch(e){   
                return defaultValue;   
            }   
        },   
        // private   
        set : function(name, value){  
        	
            if(!this.storage)   
            {   
                return;   
            }   
            if(typeof value == "undefined" || value === null){   
                this.clear(name);   
                return;   
            }   
            try{   
                this.storage.setItem("ys-"+name, this.encodeValue(value));   
                Ext.state.WhatyCookieProvider.superclass.set.call(this, name, value);   
            }catch(e){   
            }   
        },   
      
        // private   
        clear : function(name){   
            if(!this.storage)   
            {   
                return;   
            }   
            try{   
                this.storage.removeItem(name)   
                Ext.state.WhatyCookieProvider.superclass.clear.call(this, name);   
            }catch(e){   
            }   
        }   
    });   
      
    IESessionStorage = function(fileName){   
        this.fileName = fileName;   
        this.ele = document.documentElement;   
        this.ele.addBehavior("#default#userdata");   
        this.ele.load(fileName);   
    }   
    IESessionStorage.prototype = {
        setItem:function(key, value){
    		this.ele.setAttribute(key, value);   
            this.ele.save(this.fileName);
            
        },   
        getItem:function(key){   
            return this.ele.getAttribute(key);   
        },   
        removeItem:function(key){   
            this.ele.removeAttribute(key);   
            this.ele.save(this.fileName);   
        },   
        deleteSelf:function(){   
            this.ele.expires = new Date(315532799000).toUTCString();   
            this.ele.save(this.fileName);   
        }   
    }  
    //Ext.form.WhatyCookieProvider end
      
      

