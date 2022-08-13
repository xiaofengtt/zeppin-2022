/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){
    var win;
    var button = Ext.get('show-btn');

    button.on('click', function(){
        // create the window on the first click and reuse on subsequent clicks
        if(!win){
            win = new Ext.Window({
                applyTo     : 'hello-win',
                layout      : 'fit',
                width       : 500,
                height      : 300,
                closeAction :'hide',
                plain       : true,
								items     : tabs
            });
        }
        win.show(button);
    });
    
			var tabs = new Ext.TabPanel({
				region    : 'center',
				margins   : '3 3 3 0', 
				activeTab : 0,
				defaults  : {
					autoScroll : true
				},
				items     : [{
					title    : 'Bogus Tab',
					html     : '<iframe src="www.baidu.com" width="100%" height="100%" scroll="auto"></iframe>'
				 },{
					title    : 'Another Tab',
					html     : Ext.example.bogusMarkup
				 },{ 
					title    : 'Closable Tab',
					html     : Ext.example.bogusMarkup,
					closable : true
				}]
			});
});