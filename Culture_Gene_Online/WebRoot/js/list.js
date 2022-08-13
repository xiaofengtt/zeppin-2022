var parent=decodeURI(getParamValue("parent"));
var id=decodeURI(getParamValue("id"));
$(function(){
	hotSearch();
	$("#content .content_inner .screen li.classification a.all").attr("name",id);
	screen();
	var str="";
	$.get('../admin/categoryList?level=1&Status=2',function(r){
		var nationalHtml ="";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			str+="<li><a href='list.html?parent="+encodeURI(r.Records[i].name)+"&&id="+r.Records[i].id+"'>"+r.Records[i].name+"</a></li>";
			//$(".classify").html().attr({"name":r.Records[i].id,"href":encodeURI("list.html?parent="+r.Records[i].name+"&&id="+r.Records[i].id)});
		}
		$(".classify").html(str);
	});
	$("#banner .banner_inner ul.navList li:eq(0)").css("margin-right","20px")
	$("#banner .banner_inner ul.navList li:eq(1)").css("padding-left","16px").hover(function(){
		$("#banner .banner_inner ul.navList li .classify").css("display","block");
	},function(){
		$("#banner .banner_inner ul.navList li .classify").css("display","none");
	})
	//获取分类
	$.get('../admin/categoryList?level=2&parent='+id,function(r){
		var nationalHtml ="";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			nationalHtml+='<a name="'+r.Records[i].id+'">'+r.Records[i].name+'</a>';
		}
		$('.screen li.classification').append(nationalHtml);
		
	});

	$("span.parent").html(parent);
	setTimeout(function(){
		$('#brand-waterfall').waterfall();
	},500)
	$("#banner .banner_inner .nav li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	$("#banner .banner_inner .navList li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	setTimeout(function(){
		$("#content .content_inner .screen li a").click(function(){
			$(this).addClass("light").siblings().removeClass("light");
			screen();
		});
	},1000)
	
	$.get('../admin/nationalList',function(r){
		var nationalHtml ="<option value=''>全部</option>";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			nationalHtml+='<option value="'+r.Records[i].id+'">'+r.Records[i].name+'</option>';
		}
		$('.national').html(nationalHtml).change(function(){
			screen();
		});
	});

	
	if($.cookie('islogin')=="true"){
		$("#banner .banner_inner ul.nav").css("display","none");
		$("#banner .banner_inner ul.loggedLn").css("display","block");
		$("#banner .banner_inner ul.loggedLn .userName").text($.cookie('loginusername'));
		$("#banner .banner_inner ul.loggedLn .userName").click(function(){
			window.location.href="PersonalCenter.html";
		});
	}
	$("#banner .banner_inner .Search a.search").click(function(){
		if($("#banner .banner_inner .Search input.search_input").val()==""){
			return false;
		}else{
			window.location.href="Search.html?search="+encodeURI($("#banner .banner_inner .Search input.search_input").val());
		}
	});
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
	                $(this).css({"left":+pos[_temp][0]+"px", "top":+pos[_temp][1]+"px"});
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

//获取地址栏参数
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
    // 写入数据字典
    var tmparray = search.substr(1, search.length).split("&");
    var paramsArray = new Array;
    if (tmparray != null) {
        for (var i = 0; i < tmparray.length; i++) {
            var reg = /[=|^==]/;    // 用=进行拆分，但不包括==
            var set1 = tmparray[i].replace(reg, '&');
            var tmpStr2 = set1.split('&');
            var array = new Array;
            array[tmpStr2[0]] = tmpStr2[1];
            paramsArray.push(array);
        }
    }
    // 将参数数组进行返回
    return paramsArray;
}

var n=1;
var count;
//筛选
function screen(){
	n=1;
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
		type: "POST",
        url: "../admin/webResourceList",
        data: { search: $(".search_input").val(),category:$("#content .content_inner .screen li.classification a.light").attr("name"),national:$("#content .content_inner .national option:selected").val(),level:"1",status:"2",type:$("#content .content_inner .screen li.format a.light").attr("name"),getCount:"1",pagesize:"20",pagenum:n},
        dataType: "text",
        async: false,
        success: function (data) {
//        	console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	count=json.PageCount;
        	for(i=0;i<json.Records.length;i++){
        		var id=json.Records[i].id;
        		var url=json.Records[i].url;
        		var title=json.Records[i].title;    
        		var height=Number(json.Records[i].height)*252/Number(json.Records[i].width);
  	        	src+="<a href='details.html?id="+id+"' style='height:"+Number(height+105)+"px;width:290px;'><div class='item' style='height:"+Number(height+69)+"px'><img style='height:"+height+"px' src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title' title='"+title+"'>"+title+"</p><img src='../images/collection .png' alt='收藏'>&nbsp;收藏&nbsp;<span class='collect'>"+json.Records[i].userLoveCount+"</span>&nbsp;<img src='../images/download .png' alt='下载'>&nbsp;下载&nbsp;<span>"+json.Records[i].userDownloadCount+"</span></div></div></a>"; 	      
        		
        	}
        	$("#brand-waterfall").html(src+clear);
//        	setTimeout(function(){
//        		$('#brand-waterfall').waterfall();
//        	},500)
        }
	});
	if(n==count){
		$(".morehr").css("display","none");
		$("span.loadmore").css("display","none");
	}else if(count=="0"){
		$(".morehr").css("display","none");
		$("span.loadmore").css("display","none");
		$("#brand-waterfall").css("height","100px").html("<p style='text-align:center; font-size:20px;line-height:100px;height:100px'>抱歉！没有找到符合条件的相关素材</p>");
	}else{
		$(".morehr").css("display","block");
		$("span.loadmore").css("display","block");
	}
	n++;
	
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






//获取热门搜索
function hotSearch(){
	var src="";
	$.ajax({
        type: "POST",
        url: "../admin/webGetKeyword?number=4",
        data: { },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	for(i=0;i<json.Records.length;i++){
        		if(i==json.Records.length-1){
        			src+=json.Records[i];
        		}else{
        			src+=json.Records[i]+"、";
        		}
        		
        	}
        	$(".hotSearch").append(src);
        }
    });
}

//加载更多
function screens(){
	
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
		type: "POST",
        url: "../admin/webResourceList",
        data: { search: $(".search_input").val(),category:$("#content .content_inner .screen li.classification a.light").attr("name"),national:$("#content .content_inner .national option:selected").val(),level:"1",status:"2",type:$("#content .content_inner .screen li.format a.light").attr("name"),getCount:"1",pagesize:"20",pagenum:n},
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	for(i=0;i<json.Records.length;i++){
        		var id=json.Records[i].id;
        		var url=json.Records[i].url;
        		var title=json.Records[i].title;    
        		count=json.PageCount;
        		var height=Number(json.Records[i].height)*252/Number(json.Records[i].width);
  	        	src+="<a href='details.html?id="+id+"' style='height:"+Number(height+105)+"px;width:290px;'><div class='item' style='height:"+Number(height+69)+"px'><img style='height:"+height+"' src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title' title='"+title+"'>"+title+"</p><img src='../images/collection .png' alt='收藏'>&nbsp;收藏&nbsp;<span class='collect'>"+json.Records[i].userLoveCount+"</span>&nbsp;<img src='../images/download .png' alt='下载'>&nbsp;下载&nbsp;<span>"+json.Records[i].userDownloadCount+"</span></div></div></a>"; 	      
        		
        	}
        	$("#brand-waterfall").append(src+clear);
        	setTimeout(function(){
        		$('#brand-waterfall').waterfall();
        	},500)
        }
	});
	if(n==count){
		$(".morehr").css("display","none");
		$("span.loadmore").css("display","none");
	}else{
		$(".morehr").css("display","block");
		$("span.loadmore").css("display","block");
	}
	n++;
	
}



