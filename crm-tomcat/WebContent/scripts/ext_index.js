	function alertreport()
	{
		Ext.MessageBox.show({
			title: '系统日志便签',
			msg: '欢迎使用系统日志便签',
			width:500,
        	height:300,
        	minWidth:300,
        	minHeight:300,
			buttons: Ext.MessageBox.OKCANCEL,
			multiline: true,
			modal: true,
			animEl: 'reportMenu'
		});
	}
	
var HelloWorld = function(){
    var dialog, showBtn;
    return {
        init : function(){
             showBtn = Ext.get('systemMessage');
             showBtn.on('click', this.showDialog, this);
        },
       
        showDialog : function(){
            if(!dialog){
                dialog = new Ext.BasicDialog("hello-dlg", { 
                        width:280,
                        height:130,
                        shadow:true,
						modal: true,
                        minWidth:280,
                        minHeight:130,
                        proxyDrag: true,
                        resizable:false,
                        buttonAlign:'center'
                });
                dialog.addKeyListener(27, dialog.hide, dialog);
                dialog.addButton('确 定', matchCust, dialog);
                dialog.addButton('关 闭', dialog.hide, dialog);
            }
            dialog.show(showBtn.dom);
        }
    };
}();

Ext.onReady(HelloWorld.init, HelloWorld, true);