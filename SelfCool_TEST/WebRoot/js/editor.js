/*cetv
 version 0.1
*/
//选项编辑
function TextEdit() {
  this.obj = ''; //被编辑对象
  this.parentCon = {}; //编辑框
  this.addEdit = {}; //编辑框内容
  this.button = {}; //菜单按钮 
  this.fast_machine = {}; //快速操作按钮
  this.html = '';
  this.editor = ''; //FCK编辑器状态
  this.Tinfo = {};
  this.type = '';
  this.id = '';
  this.option_id = '';
  this.Cw = '';
  this.updown = '.js-up';
  this.OptionCnt = '.qustion-content';
}
TextEdit.prototype = {
  //标题类型编辑
  T_edit: function() {
    var _this = this;
   $(_this.OptionCnt).on('click','.T_edit', function(e) {
		 e.stopPropagation();
		 //bug
		 var target = $(e.currentTarget);
		 if(_this.obj && (target.data('id') || target.attr('id'))) {
		 	//this.html = _this.addEdit.html()
			_this.html = _this.addEdit.html();
			_this.Save_title();
		 	
		 }
	     _this.obj = $(this); 
		 _this.ConEdit(_this.obj);
	      //防点击生效
		  _this.addEdit.bind('click', function() {
		     return false;
		  });
	      
		  //生成高级编辑器
	  	_this.button.bind('click', function() {
	       _this.html = _this.addEdit.html();
	 	  _this.Save_title();
	 	  _this.mceModal();
	 	  /*if (location.href.indexOf("?dev") > 0) {
		    _this.mceModal();
		  }
		  else {
		     _this.createMce();
		  }*/
	      return false;
	    });
		
		$("body").one('click', function(e) {
            _this.html = _this.addEdit.html();
            _this.Save_title();
	     });
		return false;
    });
  },
  
  //保存编辑
  Save_title: function() {
    var text = this.html;
    this.obj.html(text);
    this.addEdit.unbind();
    this.parentCon.remove();
  },
  //生成标准编辑框
  ConEdit: function(obj) {
    var _this = this;
    _this.html = obj.html();
    var mbWidth = obj.width();
    mbWidth < 500 ? mbWidth = 500 : mbWidth;
    var mbHeight = obj.height();
    mbHeight < 29 ? mbHeight = 29 : mbHeight;
    var style = obj.attr('class').split(" ");
    var addstyle = '';

    for(var i = 0; i < style.length; i++) {
      if(style[i] !== "T_edit") {
        addstyle += style[i] + " "
      }
    }

   _this.fast_machine = $();
    _this.button = $('<div class="min_an"></div>');
    _this.parentCon = $('<div class="zon_edit"></div>');
    _this.addEdit = $('<div class="add_edit" contentEditable="true">' + _this.html + '</div>');

   
    _this.parentCon.append(_this.addEdit);
	_this.parentCon.append(_this.button);
    _this.addEdit.attr('style', obj.attr('style'));
    _this.addEdit.addClass(addstyle);
	
    _this.parentCon.css({
      'width': mbWidth + 'px',
      'minHeight': mbHeight + 'px',
      'position': 'absolute',
      'top': obj.offset().top + $('.main').scrollTop() - 82 + 'px',
      'left': obj.offset().left -226 + 'px'
    });
    _this.addEdit.css({
      'width': mbWidth + 'px',
      'minHeight': 22 + 'px',
      'padding': '4px' //,
    });
	
    //输出编辑框
    $('.main').append(_this.parentCon);
    _this.addEdit.focus(); //焦点
	_this.setSelectText(_this.addEdit);
	_this.GetRidFormat(_this.addEdit);
  },
  T_plus : function() {
	var _this = this;
	$(_this.OptionCnt).on('click','.js-plus',function(){
		var th = $(this).closest('tr');
		var mtype = url('?mtype');
	    var template = $('#queOptionsTpl-'+mtype).html();
	    $('.js-tableoptions tbody:last-child').append(template);
		_this.T_sortOptions();
	});
  },
  T_up_down : function() {
      //向上移动
	  var _this = this;
	   $(_this.OptionCnt).on('click','.js-up,.js-down',function(e){
		  	var th = $(this).closest('tr');
		    if ($(e.target).is('.js-down')) { 
		        th.next('tr').after(th); 
		    } else {
		        th.prev('tr').before(th); 
		    }
			_this.T_sortOptions();
	   })
      
	  
  },
 
  T_delete : function() {
	var _this = this;
	$(_this.OptionCnt).on('click','.js-remove',function(){
		if(questionOpt.Mtype != 2 && $('.js-tableoptions tbody').find('tr').length == 2 ) {
			var d = dialog({
			    title: '系统提示',
				width : 320,
			    content: '选项不能少于2个'
			});
			d.show();
			setTimeout(function () {
			    d.close().remove();
			}, 2000);
		}else {
			$(this).closest('tr').remove();
		}
		_this.T_sortOptions();
		
	});
  },
  T_act : function() {
	  this.T_plus();
	  this.T_up_down();
	  this.T_delete();
  },
  T_sortOptions : function() {
	var _this = this;
	$('.options-edit').each(function(i,v){
		$(this).attr('id','options-edit-'+(parseInt(i)+1));
		$(this).attr('data-id',(parseInt(i)+1));
	})
  },
  
  //光标控制（待定）
  setSelectText: function(el) {
	  try {
	  		var Check = check_title_select(el.text());

            window.getSelection().selectAllChildren(el[0]); //全选
            if (!Check) {
                window.getSelection().collapseToEnd(el[0]); //光标置后
            }
              
	      //window.getSelection().selectAllChildren(el[0]); //全选
	       //if(!Check) {
	         //window.getSelection().collapseToEnd(el[0]); //光标置后
	     }catch(e) {
	     	
	     }

  },
  
  //粘贴内容格式去除
  GetRidFormat: function(obj) {

    EventUtil.addHandler(obj[0], "paste", function(event) {

      setTimeout(function() {
        var html = obj.html();
        html = html.replace(/<\/?[^>(IMG)(img)][^>]*>/g, '');
        obj.html(html);
      }, 50);

    });

    function DgContents(htt) {

      htt = htt.replace(/<\/?SPAN[^>]*>/gi, "");
      htt = htt.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3");
      htt = htt.replace(/<(\w[^>]*) style="([^"]*)"([^>]*)/gi, "<$1$3");
      htt = htt.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3");
      htt = htt.replace(/<\\?\?xml[^>]*>/gi, "");
      htt = htt.replace(/<\/?\w+:[^>]*>/gi, "");
      htt = htt.replace(/&nbsp;/, " ");
      var re = new RegExp("(<P)([^>]*>.*?)(<\/P>)", "gi"); // Different because of a IE 5.0 error
      htt = htt.replace(re, "<div$2</div>");
      return htt;
    }

  },
 
  mceModal : function() {//## 弹出层编辑器
  	$("body").unbind('click');
  	var _this = this,
  		id = _this.obj.attr('id'),
  		html = $('#'+id).html();
  	var d = dialog({
	    title: '高级编辑',
		width : 900,
	    content: '<div id="D-'+ id +'">'+ html +'</div>',
	    okValue: '确定',
	    ok: function () {
	    	//_this.addEdit.html(tinymce.get('D-'+id).getContent());
	    	_this.html = tinymce.get('D-'+id).getContent();
	    	_this.addEdit.html(tinymce.get('D-'+id).getContent());
	 	 	_this.Save_title();
	 	 	
	        tinymce.remove('#D-'+id);
	        this.close().remove();
	        return false;
	    },
	    cancelValue: '取消',
	    	cancel: function () {
	    		tinymce.remove('#D-'+id);
	        	this.close().remove();
	    	}
		});
	d.showModal();
  	
  	tinymce.init({
	    selector: '#D-'+id,
	    plugins: [
	        "autoresize autolink link  lists paste advlist table charmap nonbreaking gkimage zpformula imageupload help"
	    ],
	    toolbar: "bold italic underline strikethrough subscript superscript nonbreaking | link gkimage imageupload zpformula charmap | table  bullist numlist | removeformat help",
		menubar : !1,
		statusbar : !1,
		toolbar_items_size: "normal",
		strikethrough: {inline: 'del'},

		//object_resizing: !1,
		autoscroll: !0,
		width: 695,
		height: 48,
		autoresize_min_height: 48,
		paste_preprocess: function(a, b) {
	      b.content = b.content.replace(/(<|\/)(h1|h2|h3|h4|h5|h6)/g, "$1p")
	    },
	    imageupload_url : "../resourceAdd?type=3",
		image_upload_url: "../resourceAdd?type=1",
		image_panel_width: 200,

		target_list: !1,
		atFunc: null,
		setup: function(editor) {
	        editor.on('init', function(e) {
	            tinymce.get('D-'+id).focus();
	        });
	    }
	});
	
  	
  },	 
  createMce :  function(){//##选项编辑器
	var _this = this;
	
	tinymce.init({
	    selector: '#'+_this.obj.attr('id'),
	    plugins: [
	        "autoresize autolink link  lists paste advlist table charmap nonbreaking gkimage zpformula help"
	    ],
	    toolbar: "bold italic underline strikethrough subscript superscript  nonbreaking | link gkimage charmap | zpformula table  bullist numlist | removeformat help",
		menubar : !1,
		statusbar : !1,
		toolbar_items_size: "normal",
		strikethrough: {inline: 'del'},

		//object_resizing: !1,
		autoscroll: !0,
		width: 555,
		height: 48,
		autoresize_min_height: 48,
		paste_preprocess: function(a, b) {
	      b.content = b.content.replace(/(<|\/)(h1|h2|h3|h4|h5|h6)/g, "$1p")
	    },
		
		image_upload_url: "../resourceAdd?type=1",
		image_panel_width: 200,

		target_list: !1,
		atFunc: null,
		setup : function(ed) {//光标移开
			ed.on('blur', function(e) {
			     setTimeout(function() {
			     	_this.addEdit.html(tinymce.get(_this.obj.attr('id')).getContent())
			     	tinymce.remove('#'+_this.obj.attr('id'));
			     	//_this.editor = null;
			     }, 100);
			});
			
		}
	});
	tinymce.get(_this.obj.attr('id')).focus();
	
	
	
  },
  //创建弹出框
  EditTcc: function(conw, conh) {

    $("body").unbind('click');

    if(!conw) {
      conw = 800
    };
    if(!conh) {
      conh = 360
    };
    var wb = new jsbox({
      onlyid: "EditTcc",
      title: false,
      conw: conw,
      conh: conh,
      title: "高级编辑",
      FixedTop: 170,
      range: true,
      content: '',
      mack: true
    }).show();

    var style = this.addEdit.html();
    var styleArr = [];
    if(!this.addEdit.css('fonztFamily')) {
      styleArr.push("font-family:" + this.addEdit.css('fontFamily') + ";");
    }
    styleArr.push("font-size:" + this.addEdit.css('fontSize') + ";");
    styleArr.push("color:" + this.addEdit.css('color') + ";");
    styleArr.push("font-style:" + this.addEdit.css('fontStyle') + ";");
    styleArr.push("font-weight:" + this.addEdit.css('fontWeight') + ";");
    styleArr.push("text-decoration:" + this.addEdit.css('textDecoration') + ";");
    // style += this.fontArr[i].color;
    // style += this.fontArr[i].bold;
    // style += this.fontArr[i].italic;
    // style += this.fontArr[i].underline;
    var ii = 0;
    var m = styleArr.length;
    var _this = this;
    dgStyle(ii, m);

    function dgStyle(ii, m) {
      if(ii !== m) {
        style = '<span style="' + styleArr[ii] + '">' + style + '</span>';
        ii++;
        return dgStyle(ii, m);
      } else {
        return style;
      }
    }
    this.createEditor(style, this.obj);

    $('#EditTcc .loaddiv').append("<div class='editTcc'><div class='WJButton wj_blue editTcc_an'>保存</div></div>");

  },
  
  //创建FCK编辑器
  createEditor: function(con, obj) {
    var _this = this;
    //console.log(con);
    if(this.editor) return;
    //初始化FCK
    this.editor = CKEDITOR.appendTo($('#EditTcc .loaddiv')[0], {
      toolbar: [
      //{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source'] },
      {
        name: 'basicstyles',
        groups: ['basicstyles', 'cleanup'],
        items: ['Bold', 'Italic', 'Underline', 'Strike']
      }, {
        name: 'paragraph',
        groups: ['list', 'indent', 'blocks', 'align', 'bidi'],
        items: ['Outdent', 'Indent', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']
      }, {
        name: 'links',
        items: ['Link', 'Unlink']
      }, {
        name: 'insert',
        items: ['Image', 'Flash']
      }, {
        name: 'colors',
        items: ['TextColor', 'BGColor']
      }, {
        name: 'styles',
        items: ['Format', 'Font', 'FontSize']
      }],
      height: 251 //设置高度
    }, con //输入内容

    );
   
    if(!this.editor) return;
    //点击删除按钮关闭
    $('#EditTcc .jsbox_close').one('mousedown', function() {
      _this.editor.destroy();
      _this.editor = null;
      _this.parentCon.remove();
    });

   
  }

}

//组合题目
var QuestionGOpt = {
	Mtype : url('?mtype'),//模板类型
	Qtype : url('?qtype'),//题目类型
	Pid : url('?paperid'),//试卷id
	Psid :url('?sectionid'),//试卷
	G_cntid : '#queGroupCnt',
	G_add : function(data){//添加题目
	    var template = $('#queGroupTpl-'+data.mtype).html();
	    var html = Mustache.render(template,data);
	    $('#queGroupCnt').append(html);
	},
	G_sortnum : function() {
		var _this = this;
		$('.module').each(function(i){
			var th = $(this),num = parseInt(i)+1;
			th.attr('id','module_'+num);
			th.find('.q_title').attr('id','q_title_'+num);
			th.find('.num').html(num);
			th.find('.q_options').find('label[name="option"]').each(function(j){
				$(this).attr('id','m'+ num +'_q_label_'+(parseInt(j)+1));
				$(this).prev().data('id',(parseInt(j)+1));
				$(this).prev().attr('name','radio-'+num);
			})
			th.find('textarea[name="analysis"]').attr('id','analysis_'+num);//为题目解析添加识别id
			th.find('textarea[name="qustionTitle"]').attr('id','qustionTitle_'+num);//为题目题干添加识别id
		})
	},
	G_sortoptions : function(th) {
		var _this = this;
		var m = $(th).closest('.module'),mid = m.attr('id').split('_')[1],mtype = m.data('mtype');
		m.find('label[name="option"]').each(function(j){
			if(mtype == 1) {
				$(this).prev().attr('name','radio-'+mid);
			}
			$(this).attr('id','m'+ mid +'_q_label_'+(parseInt(j)+1));
			$(this).prev().data('id',(parseInt(j)+1));
		})
	},
	G_editchecked : function () {
		$('.q_score').each(function(i){
			var module = $(this).parents('.module');
			var mtype = module.data('mtype');
			
			if(mtype == 5) {
				var inx = $(this).find('input[name="score"]').data('inx');
				var inxa = inx.split(',');
				console.log(module)
				for(j=0;j<inxa.length;j++) {
					
					module.find('input[name^="radio"]').eq(inxa[j]-1).prop('checked','true');				
				}
			}else {
				var inx = $(this).find('input[name="score"]').data('inx');
				if(inx > 0 || inx == 0)
					$(this).prev().find('input[name^="radio"]').eq(inx).attr('checked',true);
			}
		})
	},
	G_plus : function() {
		var _this = this;
		$('.qustion-content').on('click','.js-plus',function(){
			var mtype = $(this).data('mtype');
		    var template = $('#queGroupOptionTpl-'+mtype).html();
		    var html = Mustache.render(template);
		    $(this).closest('.module').find('.unstyled').append(html);
			_this.G_sortoptions(this);
		});
	},
	G_remove : function() {
		var _this = this;
		$('.qustion-content').on('click','.js-remove',function(){
			$(this).closest('.module').remove();
			_this.G_sortnum();
		});
	},
	G_up_down : function() {
  	  var _this = this;
  	   $('.qustion-content').on('click','.js-up,.js-down',function(e){
  		  	var th = $(this).closest('.module');
  		    if ($(e.target).is('.js-down')) { 
  		        th.next('.module').after(th); 
  		    } else {
  		        th.prev('.module').before(th); 
  		    }
  			_this.G_sortnum();
  	   })
	},
    G_act : function() {
  	 this.G_plus();
  	  this.G_up_down();
  	  this.G_remove();
    },
	G_createjson : function(type) {
		var formdata = $('#questionForm').serializeJSON();
		if(tinyMCE.get('qustionMaterial') == null){
			return false;
		}
		var material = string2json2(tinyMCE.get('qustionMaterial').getContent());//材料 组合题父题只有材料
		
		var stem = '';
		var datas = '[';
		
		$('.module').each(function(g){//题型循环
			var th = $(this),mtype = th.data('mtype'),num = parseInt(g)+1;
			
//			alert(string2json2(tinyMCE.get('analysis'+num).getContent()));
			var analysis = string2json2(tinyMCE.get('analysis_'+num).getContent());//解析 绑定在问题上
			
//			var mstem = string2json2(th.find('.q_title').html());
			var mstem = "";
			var gcom = (num == $('.module').length) ? '' : ','
			var optionsjson = '"options" : ['; 	  
			if (mtype == 1 || mtype == 5){//单选题
				var len = th.find('.q_options').find('label[name="option"]').length;
				for (i=1; i < len+1; i++){
					var com = (i== len) ? '' : ','
					optionsjson += '{"inx":'+ i +',"content":"'+ string2json2($('#m'+num+'_q_label_'+parseInt(i)).html()) +'"}'+ com ;
				}
				
			}
			optionsjson += '],';
			
			var rck = th.find('input[name^="radio"]:checked');
			
			if (mtype == 1) {//单选题
				mstem = string2json2(th.find('.q_title').html());
				var optionid = (rck.length > 0) ? rck.data('id') : '';
				var rckscore = (rck.length > 0) ? th.find('input[name="score"]').val() : '';
				var content = '';
				var rckjson = '"results":[{"mce":"1","inx":"'+ optionid +'","score":"'+ rckscore +'","content":"'+ content +'"}]';
			} else if (mtype == 5) {//多选题
				mstem = string2json2(th.find('.q_title').html());
				var optionid = [];
				if(rck.length > 0) {
					rck.each(function(){
						optionid.push($(this).data('id'))
					})
				}
				var rckscore = (rck.length > 0) ? th.find('input[name="score"]').val() : '';
				
				var content = '';
				var rckjson = '"results":[{"mce":"1","inx":"'+ optionid.join(',') +'","score":"'+ rckscore +'","content":"'+ content +'"}]';
				
			} else if(mtype == 3) {//判断题
				mstem = string2json2(th.find('.q_title').html());
				var optionid = (rck.length > 0) ? rck.data('id') : '';
				var rckscore = (rck.length > 0) ? th.find('input[name="score"]').val() : '';
				var content = rck.next().find('span').html();
				var rckjson = '"results":[{"mce":"1","inx":"'+ optionid +'","score":"'+ rckscore +'","content":"'+ content +'"}]';
			}else if(mtype == 2) {//填空题
				mstem = string2json2(th.find('.q_title').html());
				var rckjson = '"results":[';
				var ope = th.find('.q_options').find('label[name="option"]');
				var len = ope.length;
				for (i=0; i < len; i++){
					var rckscore = ope.eq(i).closest('li').find('input[name="score"]').val();
					var content = ope.eq(i).html();
					var i2 = parseInt(i)+1;
					var com = (i2== len) ? '' : ','
					rckjson += '{"mce":'+ i2 +',"inx":"'+ i2 +'","score":"'+ rckscore +'","content":"'+ string2json2(content) +'"}' + com;
				}
				rckjson +=']'
			
			}else if(mtype == 6){//问答题
				var rckscore = th.find('input[name="score"]').val();
//				alert(rckscore);
				var rckjson = '"score":"'+rckscore+'"';
				
				mstem = string2json2(tinyMCE.get('qustionTitle_'+num).getContent());//问答题的题干
			}
			
			datas += '{"itemType.id":"'+ QuestionGOpt.Qtype +'",'
				  +	 '"stem":"'+ mstem +'",'
				  +  '"analysis":"' + analysis + '",'
				  +  '"modelType":"' + mtype + '",'
				  +  optionsjson 
				  +  rckjson + '}'
				  +  gcom
			
		})
		
		datas += ']'
			
		
		var id = ( type == 'edit' ) ? '"id":"'+url('?id')+'",' : '';
		
		//试卷属性
		var ppoperty =(this.Pid != null)  ? '"paper.id":"'+ this.Pid +'","paperSection.id" :"'+ this.Psid +'",' : '';
		
		var questionItem = '{'
						+  ppoperty
						+  id
						+  '"itemType.id":"' + QuestionGOpt.Qtype + '",'
						+  '"itemModelType":"' + QuestionGOpt.Mtype + '",'
						+  '"sourceType":"'+ formdata.sourceType + '",'
						+  '"source":"' + formdata.source + '",'
						+  '"subject.id":"' + formdata['subject.id'] + '",'
						+  '"grade.id":"' + formdata['grade.id'] + '",'
						+  '"textbookCapter.id":"' + formdata['textbookCapter.id'] + '",'
						+  '"diffcultyLevel":"' + formdata.diffcultyLevel + '",'
						+  '"knowledge.id":"' + formdata['knowledge.id'] + '",'
						+  '"material":"' + material + '",'
//						+  '"analysis":"' + analysis + '",'
						+ '"data":' +datas
						+ '}';
//		alert(questionItem);
		return questionItem;
	},
	
	G_addQueJson : function() {
		var _this = this;
		$('.savebutton,.savenextbutton').on('click',function(e){
			$.post('../itemAddJson',{'questionItem':QuestionGOpt.G_createjson('add')},function(r){	
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: r.Message 
				});
				d.showModal();
				setTimeout(function () {
				    d.close().remove();
				}, 2000);
				if(r.Status == 'success') {
					if(e.target.id == 'savenextbutton') {
						location.reload();
					}else if(_this.Pid != null){
						location.href = url('host')+'/SelfCool/admin/paperLoadPaper?id='+_this.Pid;
					}else {
						location.href = url('host')+'/SelfCool/admin/itemList.html';
					}
				}else if(r.Status == 'error'){
					d.showModal();
					$(".savebutton").removeAttr("disabled");
					$(".savenextbutton").removeAttr("disabled");
				}
			}).fail(function(){
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: '试题信息录入不完整，请仔细检查！' ,
				    okValue: '确定',
   					ok: function () {}
				});
				$(".savebutton").removeAttr("disabled");
				$(".savenextbutton").removeAttr("disabled");
				d.showModal();
			})
		})
		
	},
	G_TieditQueJson : function() {
		var _this = this;
		$('.savebutton').on('click',function(){
			$.post('../itemUpdateJson',{'questionItem':QuestionGOpt.G_createjson('edit')},function(r){
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: r.Message
				});
				d.showModal();
				setTimeout(function () {
				    d.close().remove();
				}, 2000);
				
				if(r.Status == 'success') {
					if(_this.Pid != null){
						location.href = url('host')+'/SelfCool/admin/paperLoadPaper?id='+_this.Pid;
					}else {
						location.href = url('host')+'/SelfCool/admin/itemList.html';
					}
					return false;	
				}else if(r.Status == 'error'){
					d.showModal();
					$(".savebutton").removeAttr("disabled");
					$(".savenextbutton").removeAttr("disabled");
				}
			})
		})
	},
	
	G_Tiedit: function(data) {
		if(data != undefined){
			var html = $("#queGroupTpl").render(data);
			$("#queGroupCnt").html(html);
			var re = data;
			if(questionOpt.Mtype == 1) {//单选题
				var rinx = re.data.results[0].inx;
				var rscore = re.data.results[0].score;
				//$('.score')
				$.each(re.data.options,function(i,v) {
					var tt = $('.options-edit').eq(i).closest('tr');
					if(rinx == (parseInt(i)+1)) {
						tt.find('input[name^="radio"]').prop('checked','true');
					}
				});
			}else if(questionOpt.Mtype == 5){//多选题
				
			}
		}
	},
	G_addinit : function() {
		var _this = this;
		$('#quetypeCnt').on('click','a',function(){
			var data = $(this).data('object');
			_this.G_add(data);
			_this.G_sortnum();
//			var textEdit = new TextEdit();
//			textEdit.T_edit();
//			questionOpt.initQueMce('analysis');
			$('.module').each(function(i){
				var num = parseInt(i)+1;
				questionOpt.initQueMce('analysis_'+num);
				questionOpt.initQueMce('qustionTitle_'+num);
			});
		});
		_this.G_act()
	},
	G_editinit: function () {
		var _this = this;
		
		$.getJSON('../itemLoadItem?id='+url('?id')+'&split=_', function(r) {
			if(r.Status == 'success') {	
				var $json = r;
			    var template = $('#questionEditTpl').html();
			    var html = Mustache.render(template, $json);
			    $('#questionEditCnt').html(html);
				var data = $json.Records;
				
				//难易程度
				$('select[name="diffcultyLevel"] option').each(function(i,v){
					if(data.diffcultyLevel == $(this).val()) {
						$(this).prop('selected','selected');
					}
				})
				//来源类型
				$('select[name="sourceType"] option').each(function(i,v){
					if(data.sourceType == $(this).val()) {
						$(this).prop('selected','selected');
					}
				})
				questionOpt.qqTitle();
				questionOpt.getSubject();
				questionOpt.getgklist('knowledge','knowledgeSearchAllKnowledge');			
				
				questionOpt.queMce('qustionMaterial');

				QuestionGOpt.G_TieditQueJson();
				QuestionGOpt.G_Tiedit(data.data);
				
				//QuestionGOpt.G_act() 调用两次
				//QuestionGOpt.G_plus();
				QuestionGOpt.G_sortnum();
				$('.module').each(function(i){
					var num = parseInt(i)+1;
//					alert("analysis");
					questionOpt.queMce('analysis_'+num);
					questionOpt.queMce('qustionTitle_'+num);
				});
				QuestionGOpt.G_editchecked()
				var textEdit = new TextEdit();
				textEdit.T_edit();
			}else {
				alert('服务端出错！请稍后重试！');
			}
		})
		
	}
	
}



//添加/编辑问题
var questionOpt = {
	Mtype : url('?mtype'),//模板类型
	Qtype : url('?qtype'),//题目类型
	Pid : url('?paperid'),//试卷id
	Psid :url('?sectionid'),
	//获取学段 知识点 教材列表
	getgklist : function(type,url,sid,gid) {
		
		var cUrl = '../'+url;
		var e = $('#'+type+'ido');

		cUrl += '?split=_&level=1';
		cUrl += (typeof sid == 'undefined') ? '' : '&subject.id='+sid;
		cUrl += (typeof gid == 'undefined') ? '' : '&grade.id='+gid;
		
		var cHtml = '';
		//if (bid)
		var recursiveIteration = function (object,type) {
		    for (var property in object) {
		        if (object.hasOwnProperty(property)) {
					//var pname = (typeof pname != 'undefined') ? pname : object['name'];
		            if (typeof object[property] == "object"){
		                recursiveIteration(object[property],type);
		            }else{
						emClass = (object['haschild']) ? ' class="o"' : '';
						emClick = (object['haschild']) ? ' onclick="TreeList.changeIcon($(this))"' : '';
						divClass = (typeof object['level'] == 'undefined') ? ' data-level="11" class="tSub1"' : ' data-level="'+ parseInt(object['level']) + 1 +'" class="tSub'+ (parseInt(object['level']) + 1) +'"';
						aClick = ' onclick="questionOpt.setinputid(\'' + type + '\',\'' +object['name'] + '\',\'' + object['id'] + '\',this)"';
						
						if(property == 'name') {
							cHtml += '<div'+ divClass +'><em '+ emClass + emClick +' ></em><a data-level="'+ object['level'] +'" href="javascript:void(0)" '+ aClick +'>'+ object['name'] +'</a></div>';
							
						}
						
		               
		            }
		        }
		    }
		}
		
			
		$.getJSON(cUrl,function(data) {
			if(data.Status == 'success' && data.Records.length > 0) {
				recursiveIteration(data,type);
				$("#"+ type +"ido").html(cHtml);
			}else if (data.Status == 'success' && data.Records.length == 0) {
				$("#"+ type +"ido").html('<div class="no_data">暂无相关条件的数据</div>');
			}
			          
		});
	},
	
	//获得学科列表
	getSubject : function () {
		$.getJSON('../admin/subjectSearchAllSubject?split=_', function(r) {
			if(r.Status == 'success' && r.Records.length > 0) {
			    var template = $('#changeSubjectTpl').html();
			    var html = Mustache.render(template, r);
			    $('#changeSubjectCnt').html(html);
			}else {
				alert('登录超时，请重新登录！');
				location.href = '../admin/login.html';
			}
		})
	},
	//设置
	setinputid : function(type,name,id,obj) {
		$(obj).closest('.treelist').find('a').removeClass('cur');
		$(obj).addClass('cur');
		if(type !== 'subject'){
			$('#'+type+'Name').html(questionOpt.setNameAdd(obj));
		}else {
			$('#'+type+'Name').html(name);
		}
		type = (type == 'textbook') ? 'textbookCapter' : type;
		$('input[name="'+ type + '.id"]').val(id);
		
	},
	depeandbysubject : function(sid,gid) {
		//改变学科 重置学段知识点教材
		$('#gradeName').html('请选择学段');
		$('input[name="grade.id"]').val('');
		$('#knowledgeName').html('请选择知识点');
		$('input[name="knowledge.id"]').val('');
		$('#textbookName').html('请选择教材章节');
		$('input[name="textbookCapter.id"]').val('');
		questionOpt.getgklist('knowledge','knowledgeSearchAllKnowledge',sid,gid);
	},
	
	setNameAdd :function(obj){
		var arr = $(obj).parent().prevAll(),
			pl = $(obj).parent().data('level'),
			tl = '',
			strarr = [],
			tmplevel = '';
		strarr.push($(obj).parent().text());
		arr.each(function(){
			var l = $(this).data('level');
			if(pl > l && tl != l && tmplevel != 11){
				tl = l;
				tmplevel = $(this).data('level');
				strarr.push($(this).text());
			}
		})
		return strarr.reverse().join(' > ');
	},
	
	//初始化
	queMce : function(id) {//##题目编辑器gkimage imageupload zpformula charmap
		tinymce.init({
		    selector: "#"+id,
			//theme: "modern",
		    plugins: [
		        "autoresize autolink link gkpatch gkimage lists fullscreen paste advlist table charmap nonbreaking imageupload zpformula zpimages help"
		    ],
		    toolbar: "bold italic underline strikethrough subscript superscript  nonbreaking | link  gkimage imageupload zpformula charmap | table  bullist numlist | removeformat help |  fullscreen",
			menubar : !1,
			statusbar : !1,
			toolbar_items_size: "normal",
			strikethrough: {inline: 'del'},
			//object_resizing: !1,
			autoscroll: !0,
			width: 815,
			height: 58,
			paste_preprocess: function(a, b) {
		      b.content = b.content.replace(/(<|\/)(h1|h2|h3|h4|h5|h6)/g, "$1p")
		    },
			//submit_form_selector: "DefaultForm",
			image_upload_url: "../resourceAdd?type=1",//图片文件
			imageupload_url : "../resourceAdd?type=3",//音频文件
			image_panel_width: 200,
			target_list: !1,
			atFunc: null,
			setup : function(ed) {
				ed.on('init', function(e) {
					tinymce.activeEditor.focus();
				});
				ed.on('focus', function(e) {
					if($('.zon_edit').length) {
						$('.zon_edit').remove();
					}
				});
			}
		});
	},
	
	createjson : function(type) {//简单题型
		var formdata = $('#questionForm').serializeJSON();
		if(tinyMCE.get('qustionTitle') == null || tinyMCE.get('analysis') == null){
			return false;
		}
		var stem = string2json2(tinyMCE.get('qustionTitle').getContent());//题干
		var analysis = string2json2(tinyMCE.get('analysis').getContent());//解析
		var material = '';
		var optionsjson = '"options" : ['; 
		if (questionOpt.Mtype == 1||questionOpt.Mtype == 5) {//单选题
			var len = $('.options-edit').length;	
			for (i=1; i < len+1; i++){
				var com = (i== len) ? '' : ','
				optionsjson += '{"inx":'+ i +',"content":"'+ string2json2($('#options-edit-'+i).html()) +'"}'+ com ;
			}
		}
		optionsjson += '],';
		//console.log(optionsjson);
		var rck = $('input[name="radio"]:checked');
		if (questionOpt.Mtype == 1) {//单选题
			if(rck.length == 0){
				return false;
			}
			var optionid = (rck.length > 0) ? rck.closest('tr').find('.options-edit').data('id') : '';
			var rckscore = (rck.length > 0) ? $('input[name="score"]').val() : '';
			var content = '';
			var rckjson = '"results":[{"mce":"1","inx":"'+ optionid +'","score":"'+ rckscore +'","content":"'+ content +'"}]';
		}else if (questionOpt.Mtype == 5) {//多选题
			var optionid = [];
			if(rck.length == 0){
				return false;
			}else if(rck.length > 0) {
				rck.each(function(){
					optionid.push($(this).closest('tr').find('.options-edit').data('id'))
				})
			}
			var rckscore = (rck.length > 0) ? $('input[name="score"]').val() : '';
			var content = '';
			var rckjson = '"results":[{"mce":"1","inx":"'+ optionid.join(',') +'","score":"'+ rckscore +'","content":"'+ content +'"}]';
		}else if(questionOpt.Mtype == 3) {//判断题
			if(rck.length == 0){
				return false;
			}
			var optionid = (rck.length > 0) ? rck.data('id') : '';
			var rckscore = $('input[name="score"]').val();
			var content = rck.next().html();
			var rckjson = '"results":[{"mce":"1","inx":"'+ optionid +'","score":"'+ rckscore +'","content":"'+ content +'"}]';
		}else if(questionOpt.Mtype == 2) {//填空题
			
			var rckjson = '"results":[';
			var ope = $('.options-edit');
			var len = ope.length;
		
			if($(tinyMCE.get('qustionTitle').getBody()).find('.mce-blankunderline').length != len){
				var d = dialog({
				    title: '系统提示',
				    content: '题干空格和答案选项个数不一致，请重新确认！' 
				});
				d.show();
				setTimeout(function () {
				    d.close().remove();
				}, 3000);
				return false;
			}
			for (i=0; i < len; i++){
				var rckscore = ope.eq(i).closest('tr').find('input[name="score"]').val();
				var content = ope.eq(i).html();
				var i2 = parseInt(i)+1;
				var com = (i2== len) ? '' : ','
				rckjson += '{"mce":'+ i2 +',"inx":"'+ i2 +'","score":"'+ rckscore +'","content":"'+ string2json2(content) +'"}' + com;
			}
			rckjson +=']'
			
		}else if(questionOpt.Mtype == 6){
			var rckscore = $('input[name="score"]').val();
//			alert(rckscore);
			var rckjson = '"score":"'+rckscore+'"';
		}
		
		var id = ( type == 'edit' ) ? '"id":"'+url('?id')+'",' : '';
		
		//试卷属性
		var ppoperty =(this.Pid != null)  ? '"paper.id":"'+ this.Pid +'","paperSection.id" :"'+ this.Psid +'",' : '';
		
		
		var questionItem = '{'
						+  ppoperty
						+  id
						+  '"itemType.id":"' + questionOpt.Qtype + '",'
						+  '"itemModelType":"' + questionOpt.Mtype + '",'
						+  '"sourceType":"'+ formdata.sourceType + '",'
						+  '"source":"' + formdata.source + '",'
						+  '"subject.id":"' + formdata['subject.id'] + '",'
						+  '"grade.id":"' + formdata['grade.id'] + '",'
						+  '"textbookCapter.id":"' + formdata['textbookCapter.id'] + '",'
						+  '"diffcultyLevel":"' + formdata.diffcultyLevel + '",'
						+  '"knowledge.id":"' + formdata['knowledge.id'] + '",'
						+  '"material":"' + material + '",'
						+  '"analysis":"' + analysis + '",'
						+  '"data":{'
						+  '"itemType.id":"' + questionOpt.Qtype + '",'
						+  '"stem":"' + stem + '",'
						+  '"material":"' + material + '",'
						+  optionsjson
						+  rckjson
						+ '}}';
		return questionItem;
	},
	addQueJson : function() {
		var _this = this;
		$('.savebutton,.savenextbutton').on('click',function(e){
			if($('.zon_edit').length) return;
			//questionOpt.createjson('add');
			if (!questionOpt.validator()) return;
			
			$.post('../itemAddJson',{'questionItem':questionOpt.createjson('add')},function(r){
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: r.Message 
				});
				d.showModal();
				setTimeout(function () {
				    d.close().remove();
				}, 3000);
				if(r.Status == 'success') {
					if(e.target.id == 'savenextbutton') {
						location.reload();
					}else if(_this.Pid != null){
						location.href = url('host')+'/SelfCool/admin/paperLoadPaper?id='+_this.Pid;
					}else{
						location.href = url('host')+'/SelfCool/admin/itemList.html';
					}
				}else if(r.Status == 'error'){
					d.showModal();
					$(".savebutton").removeAttr("disabled");
					$(".savenextbutton").removeAttr("disabled");
				}
			}).fail(function(){
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: '试题信息录入不完整，请仔细检查！' ,
				    okValue: '确定',
   					ok: function () {}
				});
				$(".savebutton").removeAttr("disabled");
				$(".savenextbutton").removeAttr("disabled");
				d.showModal();
			})
		})
		
	},
	validator : function() {
		var gid = $('input[name="grade.id"]').val(),
			dlid = $('select[name="diffcultyLevel"]').val(),
			st = $('select[name="sourceType"]').val(),
			con = [];
		if(dlid.length < 1) {
			con.push('请选择难易程度');
		}
		if(st.length < 1) {
			con.push('请选择来源类型');
		}
		
		if(con.length > 0) {
			var d = dialog({
			    title: '系统提示',
				width : 320,
			    content:  con.join(' <br><br> '),
			    okValue: '关闭',
   				ok: function () {}
			});
			d.showModal();
			return false;
		}
		return true;
			
	},
	EditQueJson : function() {
		var _this = this;
		$('.savebutton').on('click',function(){
			$.post('../itemUpdateJson',{'questionItem':questionOpt.createjson('edit')},function(r){
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: r.Message
				});
				d.showModal();
				setTimeout(function () {
				    d.close().remove();
				}, 2000);
				if(r.Status == 'success') {
					if(_this.Pid != null){
						location.href = url('host') + '/SelfCool/admin/paperLoadPaper?id='+_this.Pid;
					}else {
						location.href = url('host') + '/SelfCool/admin/itemList.html';
					}
					
					return false;	
				}else if(r.Status == 'error'){
					d.showModal();
					$(".savebutton").removeAttr("disabled");
					$(".savenextbutton").removeAttr("disabled");
				}
			})
		})
		
	},
	optionshtml: function() {
	    var template = $('#queboxTpl-'+questionOpt.Mtype).html();
	    var html = Mustache.render(template);
	    $('#optionsCnt').html(html);
	},
	optionshtmledit: function(data) {
	    var template = $('#queboxTpl-'+questionOpt.Mtype).html();
	    var html = Mustache.render(template,data);
	    $('#optionsCnt').html(html);
		var re = data.Records;
		if(questionOpt.Mtype == 1) {//单选题
			var rinx = re.data.results[0].inx;
			var rscore = re.data.results[0].score;
			$.each(re.data.options,function(i,v) {
				$('input[name="score"]').val(rscore);
				var tt = $('.options-edit').eq(i).closest('tr');
				if(rinx == (parseInt(i)+1)) {
					tt.find('input[name="radio"]').prop('checked','true');
				}
			});
		}else if(questionOpt.Mtype == 5) {//多选题
			var rinx = re.data.results[0].inx;
			var rscore = re.data.results[0].score;
			
			$.each(re.data.options,function(i,v) {
				$('input[name="score"]').val(rscore);
				var tt = $('.options-edit').eq(i).closest('tr');
				var rinxa = rinx.split(',');
				for(j=0;j<rinxa.length;j++) {
					if(rinxa[j] == (parseInt(i)+1)) {
						tt.find('input[name="radio"]').prop('checked','true');
					}
				}
				
			});
		}else if(questionOpt.Mtype == 3){//判断题
			var rinx = re.data.results[0].inx;
			var rscore = re.data.results[0].score;
			$('.judgeScore').find('input[name="score"]').val(rscore);
			$('.judgeOptions').find('input[name="radio"]').each(function(){
				if($(this).data('id') == rinx) {
					$(this).prop('checked','true');
				}
			})
		}else if(questionOpt.Mtype == 2){//填空题
			
			
		}else if(questionOpt.Mtype == 6){//问答题
//			alert(re.data.score);
			var rscore = re.data.score;
			$('.judgeScore').find('input[name="score"]').val(rscore);			
		}
	},
	qqTitle: function() {//页面标题
		$.get('../itemTypeSearch?id='+questionOpt.Qtype,function(r){
			if(r.Status == 'success')
				$('#qqTitle').html(r.Records[0].name);
			else 
				alert('没有相关题型，请返回列表重新选择');
		})
		
	},
	qEditinit : function() {
		$.getJSON('../itemLoadItem?id='+url('?id')+'&split=_', function(r) {
			if(r.Status == 'success') {	
				var $json = r;
			    var template = $('#questionEditTpl').html();
			    var html = Mustache.render(template, $json);
			    $('#questionEditCnt').html(html);
				var data = $json.Records;
				
				//难易程度
				$('select[name="diffcultyLevel"] option').each(function(i,v){
					if(data.diffcultyLevel == $(this).val()) {
						$(this).prop('selected','selected');
					}
				})
				//来源类型
				$('select[name="sourceType"] option').each(function(i,v){
					if(data.sourceType == $(this).val()) {
						$(this).prop('selected','selected');
					}
				})
				questionOpt.qqTitle();
				questionOpt.getSubject();
				questionOpt.getgklist('knowledge','knowledgeSearchAllKnowledge',r.Records.subject_id);
				
				if(questionOpt.Mtype == 4) {//材料编辑器初始化
					questionOpt.queMce('qustionMaterial');
					questionOpt.queMce('analysis');
					QuestionGOpt.G_TieditQueJson();
					QuestionGOpt.G_Tiedit(data.data);
					
					var textEdit = new TextEdit();
					textEdit.T_edit();
				}else if(questionOpt.Mtype == 6){//问答题
//					$('#optionsCnt').parent().empty();//去除选项
					questionOpt.queMce('qustionTitle');
					questionOpt.queMce('analysis');
					questionOpt.optionshtmledit(r)
					questionOpt.EditQueJson();
				}else {
					questionOpt.queMce('qustionTitle');
					questionOpt.queMce('analysis');
					questionOpt.optionshtmledit(r)
					questionOpt.EditQueJson();
					//选项区域
					var textEdit = new TextEdit();
					textEdit.T_edit();
					textEdit.T_act();
				}
				
			}else {
				alert('服务端出错！请稍后重试！');
				location.href = '../admin/login.html';
			}
		})
	},
	
	Addinit : function() {
		
		if(this.Pid == null) {//单独录入
			if($.cookie('questionSubjectObj')) {
				var obj = $.parseJSON($.cookie('questionSubjectObj'));
				$('#subjectName').html(obj.sname);
				$('input[name="subject.id"]').val(obj.sid);
			}
			
			/*if($.cookie('gradeindexing')) {
				var gobj = $.parseJSON($.cookie('gradeindexing'));
				$('#gradeName').html(gobj.name);
				$('input[name="grade.id"]').val(gobj.id);
			}
			if($.cookie('knowledgeindexing')) {
				var kobj = $.parseJSON($.cookie('knowledgeindexing'));
				$('#knowledgeName').html(kobj.name);
				$('input[name="knowledge.id"]').val(kobj.id);
			}
			if($.cookie('textbookindexing')) {
				var tobj = $.parseJSON($.cookie('textbookindexing'));
				$('#textbookName').html(tobj.name);
				$('input[name="textbookCapter.id"]').val(tobj.id);
			}
			if($.cookie('difLevelindexing')) {
				var diobj = $.parseJSON($.cookie('difLevelindexing'));
				$('select[name="diffcultyLevel"]').val(diobj.id);
			}*/
			
			questionOpt.getSubject();
			questionOpt.getgklist('knowledge','knowledgeSearchAllKnowledge',obj.sid);
		}else {//试卷形式录入试题
			this.paperAddinit();
			$('#textbookName').parents('.col-md-12').hide();
		}
		
		if(questionOpt.Mtype == 4) {//材料编辑器初始化
			questionOpt.initQueMce('qustionMaterial');
			questionOpt.initQueMce('analysis');
			//questionOpt.queMce('qustionMaterial');
			questionOpt.qqTitle();
			QuestionGOpt.G_addQueJson();
			var textEdit = new TextEdit();
			textEdit.T_edit();
			//textEdit.T_act();	
		}else {//非组合题
			
			 /*if (location.href.indexOf("?dev") > 0) {
			    questionOpt.initQueMce('qustionTitle');
			  }
			  else {
			  	questionOpt.queMce('qustionTitle');//题干编辑器初始化
			    
			  }*/
			
			questionOpt.initQueMce('qustionTitle');
			questionOpt.initQueMce('analysis');
			questionOpt.optionshtml();//选项初始化
			questionOpt.addQueJson();//生成json
			questionOpt.qqTitle();
			//选项区域
			var textEdit = new TextEdit();
			textEdit.T_edit();
			textEdit.T_act();
		}

	},
	
	initQueMce : function (id) {
		$('#'+id).click(function(){
			$(this).html('');
			questionOpt.queMce(id);
		})
		
	},
	paperAddinit : function() {
		//添加试卷时候 初始化页面
		$.get('../paperLoadPaperSetting?id='+this.Pid,function(r) {
			if(r.Status == 'success') {
				$('#subjectName').html(r.Records['subject.name']);
				$('input[name="subject.id"]').val(r.Records['subject.id']);
				
				$('#gradeName').html(r.Records['grade.name']);
				$('input[name="grade.id"]').val(r.Records['grade.id']);
				$('input[name="source"]').val(r.Records['source']);
				$('select[name="sourceType"] option').each(function(i,v){
					if(r.Records['sourceType'] == $(this).text()) {
						$(this).prop('selected','selected');
					}
				})
				questionOpt.getgklist('knowledge','knowledgeSearchAllKnowledge',r.Records['subject.id']);
				
				
			}
		})
	}
	
	
	
};


//试卷
/*
	1. 属性设置 
	2. 五种题型 添加 编辑
	3. 
*/
var paperQuestion = {
	settingform : '#papersettingForm',
	mainContainer: $('.paperContainer'),
	unitBox: '.unit-box',
	questionItemBox: ".question-item-box",
	paperSaveBtn : $('#paperSaveBtn'),
	paperSettingBtn : $('#paperSettingBtn'),
	
	//试卷属性 弹出层
	settingBtnClick : function(this_a) {
	    var thisId = $(this_a).parents('.paperContainer').data('paperid');
	    this.randerConfigBox(thisId)//, this.renderAlertBox(), this.goPosWin(thisId), this.radio_render(".openwin-box")
	},
	
	randerConfigBox: function(thisId) {
		var thisId = thisId;
	    var d = dialog({
	      icon: null,
	      width: 800,
	      title: "试卷设置",
	      cancel: !0,
		  cancelValue: '取消',
		  okValue : '确定',
	      content: '<div class="openwin-box">' + $("#setting-content").html() + "</div>",
	      ok: function(thisId) {
			$.get('../paperUpdate',{paper:paperQuestion.openwin_settingjson(url('?id'))},function(r){
				if(r.Status == 'success') {
					location.reload(true);
				}else {
					alert('服务端出错，稍后请重试');
				}
			})
	      }
	        
	      }).showModal()
	  },
	 	
	paperInit : function() {
		this.mainContainer.on({
	      mouseenter: function() {
	        $(this).addClass("hover")
	      },
	      mouseleave: function() {
	        $(this).removeClass("hover")
	      }
	    },this.unitBox);
		
	    this.mainContainer.on("click", ".unit-box", function() {
	         var this_a = this;
	         paperQuestion.settingBtnClick(this_a)
	    }),
		this.paperSettingBtn.on('click',function(){
	         var this_a = this;
	         paperQuestion.settingBtnClick(this_a)
		}),
	    this.mainContainer.on({
         mouseenter: function() {
           $(this).addClass("question-item-hover")
         },
         mouseleave: function() {
           $(this).removeClass("question-item-hover")
         }
       }, this.questionItemBox),
	   
	   this.mainContainer.on("click", ".control-box a", function(e) {
         var this_a1 = this;
       	 paperQuestion.nBtnClick(e, this_a1)
       })
	   
		
		paperQuestion.number_change('.itemtype-num');
		paperQuestion.number_change('.title-td-num');
		paperQuestion.orderSort();
		paperQuestion.paperSave();
	},
	Settinginit : function() {
		questionOpt.getSubject();
		paperQuestion.addsetting();
	},
	setItemType : function(sid) {
		$.get('../subjectItemTypeSearch?split=_&subject.id='+sid,function(r) {
			 $('#paper-set-win').html('<div class="twloading"></div>');
			if(r.Status =='success' && r.Records.length) {
			    var template = $('#paper-set-winTpl').html();
			    var html = Mustache.render(template, r);
			    $('#paper-set-win').html(html);
				paperQuestion.number_change('.title-td-num');
				
			}else {
				$('#paper-set-win').html('<div class="no_data">该学科下暂无题型</div>');
			}
		})
	},
	settingjson : function() {
		var f = $(paperQuestion.settingform).serializeJSON();
		var jdata = '';
		var pb = $('.paper-set-box');
		if(pb.length > 0) {
			$('.paper-set-box').each(function(i){
				var tb = $(this).find('table'),tbd = tb.data(),na = tb.find('textarea');
				var com = (i== (pb.length-1)) ? '' : ','
				jdata += '{"name":"'+ string2json2(na.val()) +'","inx":"'+ tbd.inx +'","itemType.id":"'+ tbd.itemtypeid +'","itemType.name":"'+ tbd.itemtypename +'"}' + com
			})
		}
		//var id = ( typeof pid == 'undefined' ) ? '"id":"'+pid+'",' : '';
		var jsoni = '{'
						//+ id
						+	'"type":"'+ f.type + '",'
						+	'"name":"'+ f.name + '",'
						+	'"source":"'+ f.source +'",'
						+	'"answerTime":"'+ f.answerTime  + '",'
						+	'"totalScore":"'+ f.totalScore + '",'
						+	'"year":"'+ f.year + '",'
						+	'"area.id":"'+ f['area.id'] + '",'
						+	'"cover":"'+ f.cover.replace(/"/g, "'").replace(/\n/g, "<br />") +'",'
						+	'"grade.id":"'+ f['grade.id'] + '",'
						+	'"subject.id":"'+ f['subject.id'] + '",'
						+	'"data":['
						+		jdata
						+	']'
						+'}';
		return jsoni;
	},
	openwin_settingjson : function(pid) {
		var f = $('.openwin-box').find('form').serializeJSON();
		var jdata = '';
		var pb = $('.openwin-box').find('.paper-set-box');
		if(pb.length > 0) {
			pb.each(function(i){
				var tb = $(this).find('table'),tbd = tb.data(),na = tb.find('textarea');
				var com = (i== (pb.length-1)) ? '' : ','
				jdata += '{"id":"'+ tbd.pid +'","name":"'+ string2json2( na.val() ) +'","inx":"'+ tbd.inx +'","itemType.id":"'+ tbd.itemtypeid +'","itemType.name":"'+ tbd.itemtypename +'"}' + com
			})
		}
		var id = (typeof pid) ? '"id":"'+pid+'",' : '';
		var jsoni = '{'
						+ id
						+	'"type":"'+ f.type + '",'
						+	'"name":"'+ f.name + '",'
						+	'"source":"'+ f.source +'",'
						+	'"answerTime":"'+ f.answerTime  + '",'
						+	'"totalScore":"'+ f.totalScore + '",'
						+	'"year":"'+ f.year + '",'
						+	'"area.id":"'+ f['area.id'] + '",'
						+	'"cover":"'+string2json2( f['cover'] )+'",'
						+	'"grade.id":"'+ f['grade.id'] + '",'
						+	'"subject.id":"'+ f['subject.id'] + '",'
						+	'"data":['
						+		jdata
						+	']'
						+'}';
		return jsoni;
	},
	paperSave : function() {//保存试题 题型列表
		var _this = this;
		this.paperSaveBtn.click(function(){
			var arr = [];
			$('.paper-type-box').each(function(i,e){
				var arr1 = [];
				arr1.push($(e).data('setionid'));
				$(e).find('.quesiton').each(function(k,qe){
					var d = $(qe).data('itemid');
					arr1.push(d);
				})
				arr.push(arr1.join(','));
			})
			var pid = _this.mainContainer.data('paperid');
			$.get('../paperUpdatePaper?papers='+pid+'$$'+arr.join('$$'),function(r) {
				if(r.Status == 'success') {
					var d = dialog({
					    title: '系统提示',
						width : 320,
					    content: r.Message
					});
					d.show();
					setTimeout(function () {
					    d.close().remove();
					}, 2000);
				}
			})
			
		})
	
	},
	nBtnClick : function(e, this_a1) {
		if(this_a = $(this_a1).find("span"), "del-icon" == this_a.attr("class")) {
			var d = dialog({
			    title: '系统提示',
			    content:  "确定删除当前题目？",
				cancel: !0,
				ok: function() {
					var url = '../paperDeletePaperItem',id = this_a.data('id');
					$.post(url,{'id':id},function(r){
						if(r.Status == 'success') {
							 d.close().remove();
							 $('#q'+id).remove(),paperQuestion.orderSort();
						}
					})
				}
			});
			d.showModal();
	    }
	    if("up-icon" == this_a.attr("class")) if("inner" == this_a.attr("opt")) {
	         var outerId = this_a.parents('.paper-type-box').attr("id"),
	           inde = this.isFirstEle("#q" + this_a.data("id"), "#" + outerId, ".question-item-box");
	         if(1 == inde || 0 == inde) return e.stopPropagation(), void 0;
	        this.moveQuestion("#" + outerId, "#" + this_a.parents('.question-item-box').attr("id"), "up", ".question-item-box"),
			this.orderSort();
	     }
		 if("down-icon" == this_a.attr("class")) if("inner" == this_a.attr("opt")) {
	       	var outerId = this_a.parents('.paper-type-box').attr("id"),
	        	 inde = this.isFirstEle("#q" + this_a.data("id"), "#" + outerId, ".question-item-box");
	       if(1 == inde || 2 == inde) return e.stopPropagation(), void 0;
	       	this.moveQuestion("#" + outerId, "#" + this_a.parents('.question-item-box').attr("id"), "down", ".question-item-box"), 			this.orderSort();
	     }  
	},
	addsetting : function() {//保存
		$('#saveSetting').click(function(){
			$.post('../paperAdd',{paper:paperQuestion.settingjson()},function(r){
				if(r.Status == 'success') {
					location.href = url('host')+'/SelfCool/admin/paperLoadPaper?id='+r.Records.id;
				}else {
					alert('服务端出错，稍后请重试');
				}
			}).fail(function(){
				var d = dialog({
				    title: '系统提示',
					width : 320,
				    content: '试卷信息录入不完整，请仔细检查！' ,
				    okValue: '确定',
   					ok: function () {}
				});
				d.showModal();
				$(".savebutton").removeAttr("disabled");
			})
			return false;
		})
	},
	
	goPosWin: function(thisId) {
	    $(thisId).addClass("active"), $(".paper-set-win").scrollTop(0), $(".paper-set-win").scrollTop($(thisId).position().top - 60)
	},
	moveQuestion: function(container, moveEle, toward, sameEle) {
	    var now_no = $(container).find(sameEle).index($(moveEle)),
	      nu = "up" == toward ? now_no - 1 : now_no;
	    if(!("up" == toward && 0 == now_no || "down" == toward && now_no == $(container).find(sameEle).length - 1)) {
	      var clo = $(moveEle).clone();
	      $(moveEle).remove(), "up" == toward && $(container).find(sameEle).eq(nu).before(clo), "down" == toward && $(container).find(sameEle).eq(nu).after(clo)
	    }
	},
	isFirstEle: function(ele, con, same) {
	    var isfir = $(con).find(same).index($(ele)),
	      len = $(con).find(same).length;
	    return 1 == len ? 1 : 0 == isfir ? 0 : isfir == $(con).find(same).length - 1 ? 2 : void 0
	},
    orderSort: function() {//题目排序
       var con = this.mainContainer.find(".question-index");
 	 	con.each(function(index, element) {
         $(element).text(index + 1 + "、")
       })
    },
    number_change: function(id) {
       $(id).each(function() {
         switch($(this).text()) {
         case "1":
           $(this).text("一");
           break;
         case "2":
           $(this).text("二");
           break;
         case "3":
           $(this).text("三");
           break;
         case "4":
           $(this).text("四");
           break;
         case "5":
           $(this).text("五");
           break;
         case "6":
           $(this).text("六");
           break;
         case "7":
           $(this).text("七");
           break;
         case "8":
           $(this).text("八");
           break;
         case "9":
           $(this).text("九");
           break;
         case "10":
           $(this).text("十");
           break;
         case "11":
           $(this).text("十一");
           break;
         case "12":
           $(this).text("十二");
           break;
         case "13":
           $(this).text("十三");
           break;
         default:
           return
         }
       })
   }
}
$(function(){
	
	//var textEdit = new TextEdit();
	//textEdit.T_edit();
	
})

function string2json2 (str) {
	return str.replace(/\\/g, "\\\\")
      .replace(/'/g, "\\'")
      .replace(/"/g, '\\"')
      .replace(/\\&/g, "\\&")
      .replace(/\\r/g, "\\r")
	  .replace(/\\n/g, "\\n")
      .replace(/\\t/g, "\\t")
      .replace(/\\b/g, "\\b")
      .replace(/\\f/g, "\\f");
}

function check_title_select(title){
    if (/^选项\d*$/.test(title)){
        return true;
    }
    return false;
}



//js原生事件注册
var EventUtil = {

  //增加事件处理函数
  addHandler: function(element, type, handler) {
    if(element.addEventListener) {
      element.addEventListener(type, handler, false);
    } else if(element.attachEvent) {
      element.attachEvent("on" + type, handler);
    } else {
      element["on" + type] = handler;
    }
  },
  //移除事件处理函数    
  removeHandler: function(element, type, handler) {
    if(element.removeEventListener) {
      element.removeEventListener(type, handler, false);
    } else if(element.detachEvent) {
      element.detachEvent("on" + type, handler);
    } else {
      element["on" + type] = null;
    }
  }

}


