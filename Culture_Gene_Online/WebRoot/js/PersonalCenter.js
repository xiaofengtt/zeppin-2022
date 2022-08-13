var i=getParamValue("i");
$(function(){
	collect();
	download();
	PersonalResource();
	audit();	
	if(i=="1"){
		$("#content .content_inner .centerList li:eq(1)").addClass("light").siblings().removeClass("light");
		$("#content .content_inner .contentList div.bigDiv:eq(1)").css("display","block").siblings().css("display","none");
	}else if(i=="4"){
	$("#content .content_inner .centerList li:eq(4)").addClass("light").siblings().removeClass("light");
	$("#content .content_inner .contentList div.bigDiv:eq(4)").css("display","block").siblings().css("display","none");
}
	Getinfomation();
	if($.cookie('islogin')=="true"){
		$("ul.nav").css("display","none");
		$("ul.loggedLn").css("display","block");
		$("ul.loggedLn .userName").text($.cookie('loginusername'));
		$("ul.loggedLn .userName").click(function(){
			window.location.href="PersonalCenter.html";
		});
	}
	//分类列表
	$.get('../admin/categoryList?level=1',function(r){
		var nationalHtml ="";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			nationalHtml+='<option value="'+r.Records[i].id+'">'+r.Records[i].name+'</option>';
		}
		$('.category').html(nationalHtml);
	});
	//民族列表
	$.get('../admin/nationalList',function(r){
		var nationalHtml ="";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			nationalHtml+='<option value="'+r.Records[i].id+'">'+r.Records[i].name+'</option>';
		}
		$('.national').html(nationalHtml);
	});
	
	setTimeout(function(){
		$('#brand-waterfall').waterfall();
	},100)
	$("#banner .banner_inner .nav li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	$("#banner .banner_inner .navList li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	$("#content .content_inner .screen li a").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	$("body").css("min-height",$(window).height());
	$("#content .content_inner .centerList li").click(function(){
		var n=$(this).index();
//		if(n=="1"){
//			title();
//		}
		$(this).addClass("light").siblings().removeClass("light");
		$("#content .content_inner .contentList div.bigDiv:eq("+n+")").css("display","block").siblings().css("display","none");
		setTimeout(function(){
		$('#brand-waterfall1').waterfall();
	},100)
		setTimeout(function(){
		$('#brand-waterfall2').waterfall();
	},100)
	setTimeout(function(){
		$('#brand-waterfall3').waterfall();
	},100)
	});
	$(".labelss .labels li").click(function(){
		var n=$(this).index();
		$(this).addClass("light").siblings().removeClass("light");
		//$(".label .labelList div:eq("+n+")").css("display","block").siblings().css("display","none");
		$(".sourecss").val($(".labelss .labels li.light").attr("name"));
		if(n=="0"){
			$("label.path").html("链接地址：").css("display","inline-block");
			$("input.linkAddress").css("display","inline-block").attr("placeholder","链接地址");
			$("label.time").css("display","none");
			$("input.millTime").css("display","none");
		}else if(n=="1"){
			$("label.path").html("采集地点：").css("display","inline-block");
			$("input.linkAddress").css("display","inline-block").attr("placeholder","采集地点");
			$("label.time").css("display","inline-block");
			$("input.millTime").css("display","inline-block");
		}else if(n=="2"){
			$("label.path").html("出版物名称：").css("display","inline-block");
			$("input.linkAddress").css("display","inline-block").attr("placeholder","出版物名称");
			$("label.time").css("display","none");
			$("input.millTime").css("display","none");
		}else if(n=="3"){
			$("label.path").html("采集地点：").css("display","none");
			$("input.linkAddress").css("display","none").attr("placeholder","采集地点");
			$("label.time").css("display","none");
			$("input.millTime").css("display","none");
		}		
	});
	$("#formsubmit").submit(function () {
		$("#formsubmit").ajaxSubmit(function(data) {   
			if(data.Message=="请重新登录"){
        		$.cookie('islogin','',{ expires: 0, path: '/' });
				$.cookie('loginusername',"",{ expires: 0, path: '/' });
				$.cookie('loginid',"",{ expires: 0, path: '/' });
        		window.location.href="login.html";
			}else{			
				if(data.Status=="success"){
					layer.msg(data.Message);
					setTimeout(function(){
						window.location.href="PersonalCenter.html?i=1";
					},3000)
				}else{
					layer.msg(data.Message);
				}
			}
			
		  });
        return false;
    })
//   $(".controls").mouseleave(function(){
//	   $("#calListBox").hide();
//   });

});
$(window).resize(function(){
	$("body").css("min-height",$(window).height());
});


;(function ($) {
    $.fn.waterfall = function(options) {
        var df = {
            item: '.item',
            margin: 30,
            addfooter: true
        };
        options = $.extend(df, options);
        return this.each(function() {
            var $box = $(this), pos = [],
            _box_width = $box.width(),
            $items = $box.find(options.item),
            _owidth = $items.eq(0).outerWidth() + options.margin,
            _oheight = $items.eq(0).outerHeight() + options.margin,
            _num = Math.floor(_box_width/_owidth);
            (function() {
                var i = 0;
                for (; i < _num; i++) {
                    pos.push([i*_owidth,0]);
                } 
            })();
            $items.each(function() {
                var _this = $(this),
                _temp = 0,
                _height = _this.outerHeight() + options.margin;

                _this.hover(function() {
                    _this.addClass('hover');
                },function() {
                    _this.removeClass('hover');
                });

                for (var j = 0; j < _num; j++) {
                    if(pos[j][1] < pos[_temp][1]){
                        //暂存top值最小那列的index
                        _temp = j;
                    }
                }
                if(pos[_temp] != undefined){
	                this.style.cssText = 'left:'+pos[_temp][0]+'px; top:'+pos[_temp][1]+'px;';
	                //插入后，更新下该列的top值
	                pos[_temp][1] = pos[_temp][1] + _height;
                }
            });

            // 计算top值最大的赋给外围div
            (function() {
                var i = 0, tops = [];
                for (; i < _num; i++) {
                    tops.push(pos[i][1]);
                }
                tops.sort(function(a,b) {
                    return a-b;
                });
                $box.height(tops[_num-1]);
                //增加尾部填充div
//              if(options.addfooter){
//                  addfooter(tops[_num-1]);
//              }
            })();
            function addfooter(max) {
                var addfooter = document.createElement('div');
                addfooter.className = 'item additem';
                for (var i = 0; i < _num; i++) {
                    if(max != pos[i][1]){
                        var clone = addfooter.cloneNode(),
                        _height = max - pos[i][1] - options.margin;
                        clone.style.cssText = 'left:'+pos[i][0]+'px; top:'+pos[i][1]+'px; height:'+_height+'px;';
                        $box[0].appendChild(clone);
                    }
                }
            }
        });
    }
})(jQuery);

//获取个人信息
function Getinfomation(){
	$.ajax({
        type: "POST",
        url: "../admin/userLoad",
        data: { },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Status=="success"){
				$(".ModifyUsername").val(json.Records.name);
				$(".ModifyMail").val(json.Records.email);
				$(".ModifyCompany").val(json.Records.company);
				$(".ModifyPosition").val(json.Records.job);
        	}else if(json.Status=="fail"){
        		$.cookie('islogin','',{ expires: 0, path: '/' });
				$.cookie('loginusername',"",{ expires: 0, path: '/' });
				$.cookie('loginid',"",{ expires: 0, path: '/' });
				window.location.href="login.html";
        	}
        }
    });
}

//验证用户名是否为空
function UserName(){
	var val=$.trim($(".ModifyUsername").val());
	if(val==""){
		$(".ModifyUsername").addClass("light");
		return false;
	}else{
		$(".ModifyUsername").removeClass("light");
		return true;
	}
	
}

//修改个人信息
function Modifyinfomation(){
	$(".warning").html("");
	if(UserName()){
		$.ajax({
	        type: "POST",
	        url: "../admin/userEdit",
	        data: {name:$.trim($(".ModifyUsername").val()),email:$(".ModifyMail").val(),company:$(".ModifyCompany").val(),job:$(".ModifyPosition").val() },
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	//console.log(data);
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Status=="success"){
	        		layer.msg(json.Message);
					$(".ModifyUsername").val(json.Records.name);
					$(".ModifyMail").val(json.Records.email);
					$(".ModifyCompany").val(json.Records.company);
					$(".ModifyPosition").val(json.Records.job);
					$.cookie('loginusername',json.Records.name,{ expires: 1, path: '/' });
					setTimeout(function(){
						window.location.href="PersonalCenter.html?i=4";
					},3000)
	        	}else if(json.Status=="fail"){
	        		if(json.Message=="请重新登录"){
	        			$.cookie('islogin','',{ expires: 0, path: '/' });
	    				$.cookie('loginusername',"",{ expires: 0, path: '/' });
	    				$.cookie('loginid',"",{ expires: 0, path: '/' });
	        			window.location.href="login.html";
	        		}else{
	        			layer.msg(json.Message);
	        			$(".warning").html(json.Message);
	        		}
					
	        	}
	        }
	    });
	}
	
}

//验证标题
function title(){
	var n=$(".labelss .labels li.light").index();
	var val=$.trim($(".labelss .labelList div:eq("+n+") input.title").val());
	if(val==""){
		$(".labelss .labelList div:eq("+n+") input.title").addClass("lights");
		return false;
	}else{
		$(".labelss .labelList div:eq("+n+") input.title").removeClass("lights");
		return true;
	}	
}

//验证描述信息
function comment(){
	var val=$.trim($(".textarea textarea").val());
	if(val==""){
		$(".textarea textarea").addClass("lights");
		return false;
	}else{
		$(".textarea textarea").removeClass("lights");
		return true;
	}	
}

//上传资源
function uploadResource(){
	var m=$(".labelss .labels li.light").index();
	$.ajax({
        type: "POST",
        url: "../admin/webResourceAdd",
        data: {title:$.trim($(".labelss .labelList div:eq("+m+") input.title").val()),national:$(".labelss .labelList div:eq("+m+") select.national option:selected").val(),category:$(".labelss .labelList div:eq("+m+") select.category option:selected").val(),comment:$.trim($(".textarea textarea").val()),source:$(".labelss .labels li.light").attr("name"),sourcePath: $(".labelss .labelList div:eq("+m+") .linkAddress").val(),sourceTime:$(".millTime").val(),isObject:$(".labelss .labelList div:eq("+m+") select.isObject option:selected").val()},
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	
        }
    });
}



function getParamValue(name) {
    var paramsArray = getUrlParams();
    if (paramsArray != null) {
        for (var i = 0; i < paramsArray.length; i++) {
            for (var j in paramsArray[i]) {
                if (j == name) {
                    return paramsArray[i][j];
                }
            }
        }
    }
    return null;
}
function getUrlParams() {
    var search = window.location.search;
    var tmparray = search.substr(1, search.length).split("&");
    var paramsArray = new Array;
    if (tmparray != null) {
        for (var i = 0; i < tmparray.length; i++) {
            var reg = /[=|^==]/;    
            var set1 = tmparray[i].replace(reg, '&');
            var tmpStr2 = set1.split('&');
            var array = new Array;
            array[tmpStr2[0]] = tmpStr2[1];
            paramsArray.push(array);
        }
    }
    return paramsArray;
}

//退出
function userLogout(){
	$.ajax({
        type: "POST",
        url: "../admin/userLogout",
        data: { },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Status=="success"){
        		$.cookie('islogin','',{ expires: 0, path: '/' });
				$.cookie('loginusername',"",{ expires: 0, path: '/' });
				$.cookie('loginid',"",{ expires: 0, path: '/' });
				window.location.href=window.location.href;
        	}
        }
    });
}



//收藏
function collect(){
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
        type: "POST",
        url: "../admin/webLoveList",
        data: { },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	$("#content .content_inner .contentList div.CollectionDiv h1 span").html(json.TotalRecordCount);
        	for(i=0;i<json.Records.length;i++){
        		var id=json.Records[i].id;
        		var url=json.Records[i].url;
        		var title=json.Records[i].title;        		
        		if(json.Records[i].level=="1"){
        			src+="<a href='details.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p></div></div></a>"; 	                 			
        		}else{
        			src+="<a href='debris.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p><a class='generate' onclick='generate("+id+")'>图案生成</a></div></div></a>"; 	                 		
        		}
        	}
        	$("#brand-waterfall2").append(src+clear);
        	setTimeout(function(){
        		$('#brand-waterfall2').waterfall();
        	},100)
        }
        
    });
	
}


//下载
function download(){
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
      type: "POST",
      url: "../admin/webDownloadList",
      data: { },
      dataType: "text",
      async: true,
      success: function (data) {
      	//console.log(data);
      	var json = (new Function("", "return " + data.split("&&&")[0]))();
      	$("#content .content_inner .contentList div.DownloadDiv h1 span").html(json.TotalRecordCount);
      	for(i=0;i<json.Records.length;i++){
    		var id=json.Records[i].id;
    		var url=json.Records[i].url;
    		var title=json.Records[i].title;        		
    		if(json.Records[i].level=="1"){
    			src+="<a href='details.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p></div></div></a>"; 	                 			
    		}else{
    			src+="<a href='debris.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p><a class='generate' onclick='generate("+id+")'>图案生成</a></div></div></a>"; 	                 		
    		}
    	}
    	$("#brand-waterfall3").append(src+clear);
    	setTimeout(function(){
    		$('#brand-waterfall3').waterfall();
    	},200)
      }
  });
	
}

//个人中心上传资源列表
function PersonalResource(){
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
	      type: "POST",
	      url: "../admin/webResourceList",
	      data: {user:$.cookie('loginid'),getCount:"1",level:"1",status:"2"},
	      dataType: "text",
	      async: true,
	      success: function (data) {
//	      	console.log(data);
	      	var json = (new Function("", "return " + data.split("&&&")[0]))();
	      	for(i=0;i<json.Records.length;i++){
        		var id=json.Records[i].id;
        		var url=json.Records[i].url;
        		var title=json.Records[i].title;  
        		if(json.Records[i].level=="1"){
        			src+="<a href='details.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p></div></div></a>"; 	                 			
        		}else{
        			src+="<a href='debris.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p><a class='generate' onclick='generate("+id+")'>图案生成</a></div></div></a>"; 	                 		
        		}
  	        	
        	}
        	$("#brand-waterfall1").append(src+clear);
        	setTimeout(function(){
        		$('#brand-waterfall1').waterfall();
        	},100)
	      }
	  });
}


//审核中、审核通过、审核未通过
function audit(){
	$.ajax({
	      type: "POST",
	      url: "../admin/webUploadCount",
	      data: {},
	      dataType: "text",
	      async: true,
	      success: function (data) {
	      	//console.log(data);
	      	var json = (new Function("", "return " + data.split("&&&")[0]))();
	      	$("#content .content_inner .contentList div.UploadDiv a.auditing span").html(json.Records.uncheck);
	      	$("#content .content_inner .contentList div.UploadDiv a.approved span").html(json.Records.pass);
	      	$("#content .content_inner .contentList div.UploadDiv a.auditFail span").html(json.Records.fail);
	      }
	  });
}


function getCategory(bid,bname) {
	event.stopPropagation();
	var cUrl = '../categoryList'
	bid = (typeof (bid) == 'undefined') ? '' : bid;
	bname = (typeof (bname) == 'undefined') ? '' : bname+'>';
	var e = (bid) ? $('#cabido' + bid) : $('#cabido');
	cUrl += (bid) ? '?pagesize=10000&parent=' + bid : '?pagesize=10000&category=&level=1';
	if (bid)
		e.css('display') == 'none' ? e.show() : e.hide();
	$.getJSON(
		cUrl,
		function(data) {
			var cLis = '';
			if (data.Records.length > 0) {
				$.each(
					data.Records,
					function(i, c) {
						emClass = (c.haschild) ? ' class="c"' : '';
						emClick = (c.haschild) ? ' onclick="getCategory(\'' + c.id+'\',\''+bname+c.name + '\');changeIcon($(this))"' : '';
						aClick = ' onclick="setCategory(\'' + c.id + '\', \'' +bname + c.name + '\')"';
						cLis += '<div class="listnode" id="' + c.id + '"><em' + emClass + emClick + '></em><a href="javascript:void(0)" ' + aClick + '>'
								+ c.name + '</a><div id="cabido' + c.id	+ '" class="cSub" style="display:none">加载中...</div></div>';
					});
			}
			e.html(cLis);
		});
	
}
function setCategory(id, name) {
	$('#cabido').html('');
	$('#calId').html(name);
	$('#category').val(id);
	$('.listCate').hide();
}

function changeCategorybtn(e) {
	e.next('.listCate').toggle();
	
}

function clickHide(){
	$("#calListBox").hide();
}
var Show;
var index
//图像生成
function generate(id){
	index = layer.load(0,{shade:0.6});
	$.ajax({
	      type: "POST",
	      url: "../admin/webBuildImg",
	      data: {id:id},
	      dataType: "text",
	      async: true,
	      success: function (data) {
//	      	console.log(data);
	      	var json = (new Function("", "return " + data.split("&&&")[0]))();
	      	var li="";
	      	if(json.Status=="success"){
	      		layer.close(index);
	      		for(i=0;i<json.Records.length;i++){	        		
	        		li+="<li><img src='../"+json.Records[i]+"'><a href='../admin/webDownloadBuildImg?url="+json.Records[i]+"'><span>下载</span></a></li>"; 	                 			  	        	
	        	}
	      		Show=layer.open({
					type: 1,
					area: ['500px', '535px'],
					title: false,
					closeBtn: 1,
					offset: 'auto',
					scrollbar: false,
					shadeClose: false,
					shade:0.6,
					content: "<div class='aaaaaaa'><p class='title'><span class='current'>1</span>/<span class='totle'></span></p><span class='drileft'></span><span class='driright'></span><div class='aaaaaaa_inner'><ul class='IMGShow'>"+li+"</ul></div></div>"
					}); 
//						$(".layui-layer-setwin .layui-layer-close2").css("display", "none");
				   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
//	      		var str="<div class='aaaaaaa'><div class='aaaaaaa_inner'><p class='title'><span class='current'>1</span>/<span class='totle'></span></p><span class='drileft'></span><ul class='IMGShow'>"+li+"</ul><span class='driright'></span></div></div>"
	      		
	      	}else{
	      		layer.close(index);
	      		layer.msg(json.Message);
	      	}
	      }
	  }).done(function(){
		  var counts=$("ul.IMGShow li").length;
			$("span.totle").text(counts);
			$("ul.IMGShow").width(counts*301);
			var n = 0;
			$(".driright").click(function() {
				var marleft = parseInt($("ul.IMGShow").css("margin-left"));
				if (marleft > (-counts + 1) * 301) {
					$("ul.IMGShow").animate({
						"margin-left": -(n + 1) * 301
							}, 100);
							n++;
							$("span.current").text(n+1);
				} else {
					$("ul.IMGShow").animate({
						"margin-left": 0
					}, 200);
					n = 0;
					$("span.current").text(n+1);
				}
			});
					$(".drileft").click(function() {
						var marleft = parseInt($("ul.IMGShow").css("margin-left"));
						if (marleft < 0) {
							n--;
							$("span.current").text(n+1);
							$("ul.IMGShow").animate({
								"margin-left": -n * 301
							}, 100);
						} else {
							$("ul.IMGShow").animate({
								"margin-left": (-counts + 1) * 301
							}, 200);
							n = counts - 1;
							$("span.current").text(n+1);
						}
					});
	  });
}






