/* 
 Equation Editor Plugin for TinyMCE v4
 Version 2

 This plugin allows equations to be created and edited from within TinyMCE.
 For more information goto: http://www.codecogs.com/latex/integration/tinymce_v4/install.php
 
 Copyright CodeCogs 2013
 Written by Will Bateman.
*/
CCinstance=0;
tinymce.PluginManager.add("eqneditor",function(editor, url) {

	// Load necessary javscript for editor from CodeCogs
	var sl = new tinymce.dom.ScriptLoader();
	var host='latex.codecogs.com';
	//var host='192.168.1.106/Self_Cool/latex';	
	var http=('https:' == document.location.protocol ? 'https://' : 'http://');
	sl.add(http+host+'/js/eq_config.js');
	//sl.add(http+host+'/js/eq_editor-lite-17.js');
	sl.loadQueue(function(){});

	// Load Additional CSS 
	var fileref=document.createElement("link");
	fileref.setAttribute("rel", "stylesheet");
	fileref.setAttribute("type", "text/css");
	fileref.setAttribute("href", http+host+'/css/equation-embed.css');
	document.getElementsByTagName("head")[0].appendChild(fileref);

	function showDialog() {
		var http = ('https:' == document.location.protocol ? 'https://' : 'http://');

		CCinstance++;
		win = editor.windowManager.open({
			title: 'Equation Editor',
			width: 615,
			height: 380,
			items: [
				{
						name:'toolbar',
						type:'container',
						html:'<div style="padding:10px;"><div id="CCtoolbar'+CCinstance+'"></div>'+
								 '<p style="margin-top:5px"><label for="CClatex'+CCinstance+'">Equation (LaTeX):</p>'+
								 '<textarea id="CClatex'+CCinstance+'" rows="5" style="border:1px solid #8fb6bd; width:570px; font-size:16px; padding:5px; background-color:#ffc"></textarea>'+
								 '<p style="margin-top:5px"><label for="CClatex'+CCinstance+'">Preview:</p>'+
								 '<img id="CCequation'+CCinstance+'" src="'+http+'www.codecogs.com/images/spacer.gif" /></div>'
				}
			],
			buttons : [
					{
						type:'container',
						html:''
					},
					{type: "spacer", flex: 1},
					{
						text: 'Ok',
						subtype: 'primary',
						minWidth: 50,
						onclick: function() {	
							editor.execCommand('mceInsertContent', false, EqEditor.getTextArea().exportEquation('html'));
							Example.add_history(EqEditor.getTextArea().getLaTeX());
							win.close();
						}
					},
					{
						text: 'Cancel', 
						onclick: function() {	win.close();}
					}
			]
		});
		
		EqEditor.embed('CCtoolbar'+CCinstance,'','efull');
 		EqEditor.add(new EqTextArea('CCequation'+CCinstance, 'CClatex'+CCinstance),false);

		var imgElm=editor.selection.getNode();
		if(imgElm.nodeName=='IMG')
		{
			var sName = editor.dom.getAttrib(imgElm, 'src').match( /(gif|svg)\.latex\?(.*)/ );
			if(sName!=null) EqEditor.getTextArea().setText(sName[2]);
		}
	}
	
	
  editor.addButton('eqneditor', {
			title: 'Equation',
			//image: url+'/img/eqneditor.png',
			tooltip: '????????????',
			onclick: showDialog,
			stateSelector: 'img[src*="latex"]'
	});

	// Adds a menu item to the tools menu
	editor.addMenuItem('eqneditor', {
			image: url+'/img/eqneditor.png',
			text: '????????????',
			context: 'insert',
			prependToContext:true,
			onclick: showDialog
	}); 
	
	editor.on('DblClick', function(ed, e) {
		if (ed.target.nodeName.toLowerCase() == "img") {
			var sName = ed.target.src.match( /(gif|svg)\.latex\?(.*)/ );
			if(sName!=null) showDialog();
		}
	});
	
});
