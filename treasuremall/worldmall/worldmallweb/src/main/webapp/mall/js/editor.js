/*cetv
 version 0.1
*/

//添加/编辑问题
var questionOpt = {
//初始化
	queMce : function(id) {//##题目编辑器gkimage imageupload zpformula charmap
		tinymce.init({
		    selector: "#"+id,
			//theme: "modern",
		    plugins: [
		        "autoresize autolink link gkpatch gkimage lists fullscreen paste advlist table charmap nonbreaking imageupload zpformula zpimages"
		    ],
		    toolbar: "bold italic underline strikethrough subscript superscript  nonbreaking | link  gkimage zpformula charmap | table  bullist numlist | removeformat |  fullscreen",
			menubar : !1,
			statusbar : !1,
			toolbar_items_size: "normal",
			strikethrough: {inline: 'del'},
			//object_resizing: !1,
			autoscroll: !0,
			width: '100%',
			height: 58,
			paste_preprocess: function(a, b) {
		      b.content = b.content.replace(/(<|\/)(h1|h2|h3|h4|h5|h6)/g, "$1p")
		    },
			//submit_form_selector: "DefaultForm",
			image_upload_url: "../back/resource/add",//图片文件
			imageupload_url : "../back/resource/add",//音频文件
			image_panel_width: 200,
			target_list: !1,
			atFunc: null,
			setup : function(ed) {
				ed.on('init', function(e) {
//					tinymce.activeEditor.focus();
				});
				ed.on('focus', function(e) {
					if($('.zon_edit').length) {
						$('.zon_edit').remove();
					}
				});
			}
		});
	}
};









