var search=decodeURI(getParamValue("search"));
$(function(){
	hotSearch();
	Search();
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
})

var n=1;
function Search(){
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
		type: "POST",
        url: "../admin/webResourceList",
        data: { search:search,level:"1",status:"2",getCount:"1",pagenum:n},
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	count=json.PageCount;
        	for(i=0;i<json.Records.length;i++){
        		var id=json.Records[i].id;
        		var url=json.Records[i].url;
        		var title=json.Records[i].title;        		
  	        	src+="<a href='details.html?id="+id+"'><div class='item'><img src='"+url+"' alt=‘图片列表' class='img'><div class='instructions'><p class='title'>"+title+"</p><img src='../images/collection .png' alt='收藏'>&nbsp;收藏&nbsp;<span class='collect'>"+json.Records[i].userLoveCount+"</span>&nbsp;<img src='../images/download .png' alt='下载'>&nbsp;下载&nbsp;<span>"+json.Records[i].userDownloadCount+"</span></div></div></a>"; 	      
        		
        	}
        	$("#brand-waterfall").append(src+clear);
        	setTimeout(function(){
        		$('#brand-waterfall').waterfall();
        	},100)
        	if(json.Records.length=="0"){
        		$("#brand-waterfall").html("<p style='text-align:center; font-size:20px;height:100px;line-height:100px;'>抱歉！没有找到符合条件的\""+search+"\"相关素材</p>");
        		$(".morehr").css("display","none");
        		$("span.loadmore").css("display","none");
        	}else if(n==count||count=="0"){
        		$(".morehr").css("display","none");
        		$("span.loadmore").css("display","none");
        	}
        	n++;
        }
	});
}


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
