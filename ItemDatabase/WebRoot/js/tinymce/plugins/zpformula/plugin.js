/**
 * plugin.js
 *
 * Copyright, Moxiecode Systems AB
 * Released under LGPL License.
 *
 * License: http://www.tinymce.com/license
 * Contributing: http://www.tinymce.com/contributing
 */

/*global tinymce:true */

tinymce.PluginManager.add('zpformula', function(editor) {
	var charmap = [
		{name:'分数', replaceWith: '\\frac{b}{a} '},
		{name:'平方根', replaceWith: '\\sqrt{ab} '},
		{name:'根号', replaceWith: '\\sqrt[n]{ab} '},
		{name:'上标', replaceWith: 'x^{a} '},
		{name:'下标', replaceWith: 'x_{a} '},
		{name:'上下标', replaceWith: 'x_{a}^{b} '},
		{name:'倒数', replaceWith: '\\dot{x} '},
		{name:'下划线', replaceWith: '\\underline{ab} '},
		{name:'上划线', replaceWith: '\\overline{ab} '},
		{name:'矢量(右)', replaceWith: '\\overrightarrow{ab} '},
		{name:'矢量(左)', replaceWith: '\\overleftarrow{ab} '},
		{name:'矢量', replaceWith: '\\widehat{ab} '},
		{name:'矩阵', replaceWith: '\\begin{bmatrix}a & b \\\\c & d \\end{bmatrix} '},
		{name:'\'', replaceWith: '\\prime '},
		{name:'求和', replaceWith: '\\sum_{x}^{y}{z} '},
		{name:'+', replaceWith: ' + '},
		{name:'-', replaceWith: ' - '},
		{name:'×', replaceWith: '\\times '},
		{name:'÷', replaceWith: '\\div '},
		{name:'不等于', replaceWith: '\\neq '},
		{name:'因为', replaceWith: '\\because '},
		{name:'所以', replaceWith: '\\therefore '},
		{name:'正负', replaceWith: '\\pm '},
		{name:'并集', replaceWith: '\\cup '},
		{name:'交集', replaceWith: '\\cap '},
		{name:'垂直', replaceWith: '\\perp '},
		{name:'无穷', replaceWith: '\\infty '},
		{name:'方程组', replaceWith: '\\begin{cases} {a} \\\\ {b} \\end{cases} '},
		{name:'化学反应方程式', replaceWith: '\\mathop{=\\!=\\!=}^{a}_{b} '},
		{name:'极限', replaceWith: '\\lim_{n \\to \\infty} '},
		{name:'小于等于', replaceWith: '\\leq '},
		{name:'大于等于', replaceWith: '\\geq '},
		{name:'子集', replaceWith: '\\subset '},
		{name:'父集', replaceWith: '\\supset '},
		{name:'子集或等于', replaceWith: '\\subseteq '},
		{name:'父集或等于', replaceWith: '\\supseteq '},
		{name:'包含于', replaceWith: '\\in '},
		{name:'不包含于', replaceWith: '\\ni '},
		{name:'大于', replaceWith: ' > '},
		{name:'小于', replaceWith: ' < '},
		{name:'约等于', replaceWith: '\\approx '},
		{name:'相似', replaceWith: '\\sim '},
		{name:'全等', replaceWith: '\\cong '},
		{name:'上箭头', replaceWith: '\\uparrow '},
		{name:'下箭头', replaceWith: '\\downarrow '},
		{name:'α', replaceWith: '\\alpha '},
		{name:'β', replaceWith: '\\beta '},
		{name:'γ', replaceWith: '\\gamma '},
		{name:'θ', replaceWith: '\\theta '},
		{name:'λ', replaceWith: '\\lambda '},
		{name:'π', replaceWith: '\\pi '},
		{name:'μ', replaceWith: '\\mu '},
		{name:'ρ', replaceWith: '\\rho '},
		{name:'∑', replaceWith: '\\Sigma '},
		{name:'Ω', replaceWith: '\\Omega '},
		{name:'Δ', replaceWith: '\\Delta '},
		{name:'空集', replaceWith: '\\phi '},
		{name:'三角形', replaceWith: '\\triangle '},
		{name:'角', replaceWith: '\\angle '},
		{name:'圆', replaceWith: '\\odot '}
	];
	
	
	
	function showDialog() {
		
		
		var gridHtml, x, win;
		
		gridHtml = '<div class="formula-content"><div class="formula-header"><ul>';
		
		var len = charmap.length;;
		
		for (x = 0; x < len; x++) {
			var chr = charmap[x];
		
			gridHtml += '<li title="' + chr.replaceWith + '"><a href="javascript:void(0)" title="'+ chr.name +'" data-latex="'+ chr.replaceWith +'">' + chr.name + '</a></li>';
		}

		gridHtml += '</ul></div><span>输入TEX</span><textarea id="editorBlockValue" class="mce-textbox editor-textbox" cols="40" rows="3" hidefocus="true"></textarea><span>预览</span><div id="previewFormula" class="preview-formula"><p id="previewTxt" style="color: red;"></p><img src="" id="previewImg" style="display: none;"></div></div>';

		var charMapPanel = {
			type: 'container',
			html: gridHtml,
			onclick: function(e) {
				
				var target = e.target;
				
				if(target.tagName == 'A') {
					//var target = e.target;
					
					$('#editorBlockValue').val($('#editorBlockValue').val() + $(target).attr('data-latex'));
					$('#previewImg').attr('src','../ItemDatabase/latex.action?'+encodeURIComponent($('#editorBlockValue').val())).show();
					
				}
				
				
			}
			
			
		};
		
		
		
		win = editor.windowManager.open({
			title: "插入公式",
			spacing: 10,
			padding: 10,
			items: [
				charMapPanel,
				{
					type: 'label',
					name: 'preview',
					text: ' ',
					style: 'font-size: 40px; text-align: center',
					border: 1,
					width: 640,
					minHeight: 80
				}
			],
			buttons: [
				{text: "Close", 
				onclick: function() {
					win.close();
				}},
				{
					type: "button",
					text: "Ok",
					subtype: "primary",
					onclick: function() {
						editor.insertContent(
							'<img class="zpmath" src="' + $('#previewImg').attr('src') + '" data-math="'+ $('#editorBlockValue').val() +'" >'
						);
						win.close();
				                       
				   }}
			]
		});
		
       
		var d, e = editor.dom, f = tinymce.DOM,i = !1;
		f.bind(f.get("editorBlockValue"), "focus", function() {
            f.bind(f.get("editorBlockValue"), "keyup", function() {
                clearTimeout(d), d = setTimeout(function() {
					
                    var vl = $.trim($("#editorBlockValue").val());
					if(vl.length < 1) {$('#previewImg').hide();return;}
					$('#previewImg').attr('src','/ItemDatabase/latex.action?'+encodeURIComponent(vl)).show();
					
					
                }, 700)
            })
        }), 
		f.bind(f.get("editorBlockValue"), "blur", function() {
            clearTimeout(d)
        }), 
		setTimeout(function() {
            f.get("editorBlockValue").select()
        }, 500)
	
	}
	

	editor.addButton('zpformula', {
		icon: 'zpformula',
		
		tooltip: '插入公式',
		onclick: showDialog,
		stateSelector: "img[data-math]"
	});

	editor.addMenuItem('charmap', {
		icon: 'charmap',
		text: '插入公式',
		onclick: showDialog,
		context: 'insert'
	});
});