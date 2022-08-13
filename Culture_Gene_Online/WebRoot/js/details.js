var id=getParamValue("id");
$(function(){
	details();
	userWorks();
	hotSearch();
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
	setTimeout(function(){
		$('#brand-waterfall').waterfall();
	},100);
	$("#banner .banner_inner .nav li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	$("#banner .banner_inner .navList li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
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
			window.location.href="Search.html?search="+$("#banner .banner_inner .Search input.search_input").val();
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
var ownerId;
var categoryId;
var IMGurl;
//获取详情
function details(){
	var src="";
	$.ajax({
        type: "POST",
        url: "../admin/webResourceLoad",
        data: {id:id },
        dataType: "text",
        async: false,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	ownerId=json.Records.ownerId;
        	categoryId=json.Records.categoryId;
        	IMGurl=json.Records.url
        	$("#hrefa").attr("href",IMGurl);
        	$("#content .content_inner span.title .categoryName").html(json.Records.categoryName);
        	$("#content .content_inner span.title .categoryNameParent").attr({"href":"list.html?parent="+json.Records.categoryName+"&&id="+categoryId});
        	$("#content .content_inner span.title .categorytitle").html(json.Records.title);
        	$("#content .content_inner .bigImg p").html(json.Records.title);
        	$("#content .content_inner .bigImg img.img").attr("src",json.Records.url);
        	$("#content .content_inner .infomation ul li span.id").html(json.Records.id);
        	$("#content .content_inner .infomation ul li span.size").html(json.Records.sizeKB+"KB");
        	$("#content .content_inner .infomation ul li span.category").html(json.Records.nationalName);
        	$("#content .content_inner .infomation ul li span.type").html(json.Records.type);
        	$("#content .content_inner .infomation ul li span.ownerName").html(json.Records.ownerName);
        	$("#content .content_inner .infomation ul li span.meaning").html(json.Records.meaning);
        	$("#content .content_inner .infomation ul li span.ratio").html(json.Records.ratio+"像素");
        	$("#content .content_inner .infomation ul li span.createtime").html(json.Records.createtime);
        	$("#content .content_inner .infomation ul li span.sourceCN").html(json.Records.sourceCN);
        	$("#content .content_inner .bigImg a.collect span.collectspan").html(json.Records.userLoveCount);
        	$("#content .content_inner .infomation ul li span.describe").html(json.Records.comment);
        	$("#content .content_inner .bigImg a.download span.downloadspan").html(json.Records.userDownloadCount);
//        	for(i=0;i<json.Records.children.length;i++){
//        		src+="<a href='debris.html?id="+json.Records.children[i].id+"'><img src='../"+json.Records.children[i].url+"' alt='"+json.Records.children[i].title+"' class='img'></a>"; 	   	        	            		
//        	}
//        	$("#content .content_inner .debris").append(src);
        	getGene(json.Records.scode);
        	if(json.Records.isLogin=="0"){
        		$("#content .content_inner .bigImg a.collect, .download").css({"background-color":"#aaa","cursor":"not-allowed"});
        	}else{ 
        		$('.download').attr('href','../admin/webDownloadImg?id='+id);
        		if(json.Records.isLove>"0"){
	        		$("#content .content_inner .bigImg a.collect").css({"background-color":"#aaa","cursor":"not-allowed"});
	        		$("#content .content_inner .bigImg a.collect").removeAttr("onclick");
	        	}
        	}
        }
    });
	likeList();
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

//获取同一人上传作品
function userWorks(){
	var src="";
	$.ajax({
        type: "POST",
        url: "../admin/webResourceList",
        data: {user:ownerId,status:"2",level:"1",except:id },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();        	
        	for(i=0;i<json.Records.length;i++){
        		var width=json.Records[i].width;
        		var height=json.Records[i].height;
        		var style="";
        		var top="";
        		var left="";
        		var ratio;
        		if(width>=height){
        			ratio=120/height;
        			width=width*ratio;
        			left=(120-width)/2;
        			style="height:100%;margin-left:"+left+"px";
        		}else if(width<height){
        			ratio=120/width;
        			height=height*ratio;
        			top=(120-height)/2;
        			style="width:100%;margin-top:"+top+"px";
        		}
        		src+="<a href='details.html?id="+json.Records[i].id+"'><img src='"+json.Records[i].url+"' alt='"+json.Records[i].title+"' class='img' style='"+style+"'></a>"; 	   	        	            		
        	}
        	$("#content .content_inner .gene").append(src);
        }
    });
}

//下载
function collect(){
	$.ajax({
        type: "POST",
        url: "../admin/webLoveAdd",
        data: {id:id },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))(); 
        	if(json.Message=="请重新登录"){
        		$.cookie('islogin','',{ expires: 0, path: '/' });
				$.cookie('loginusername',"",{ expires: 0, path: '/' });
				$.cookie('loginid',"",{ expires: 0, path: '/' });
        		window.location.href="login.html"
        	}else if(json.Status=="success"){
        		var number=parseInt($("#content .content_inner .bigImg a.collect span.collectspan").html())+parseInt(1);
        		$("#content .content_inner .bigImg a.collect span.collectspan").html(number);
        		$("#content .content_inner .bigImg a.collect").css({"background-color":"#aaa","cursor":"not-allowed"});
        		$("#content .content_inner .bigImg a.collect").removeAttr("onclick");
        	}
        }
    });
}

//收藏
function download1(){
	$.ajax({
        type: "POST",
        url: "../admin/webDownloadAdd",
        data: {id:id },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))(); 
        	if(json.Message=="请重新登录"){
        		$.cookie('islogin','',{ expires: 0, path: '/' });
				$.cookie('loginusername',"",{ expires: 0, path: '/' });
				$.cookie('loginid',"",{ expires: 0, path: '/' });
        		window.location.href="login.html"
        	}else if(json.Status=="success"){
        		var number=parseInt($("#content .content_inner .bigImg a.download span.downloadspan").html())+parseInt(1);
        		$("#content .content_inner .bigImg a.download span.downloadspan").html(number);       		 
        	}
        }
    });
}


var n=1;
var count;
//取列表
function likeList(){
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
		type: "POST",
        url: "../admin/webResourceList",
        data: {level:"1",status:"2",pagesize:"4",pagenum:n,category:categoryId,getCount:"1",except:id},
        dataType: "text",
        async: false,
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
        }
	});
	if(n==count||count=="0"){
		$(".morehr").css("display","none");
		$("span.loadmore").css("display","none");
	}
	n++;
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


//获取子集基因
function getGene(code){
	$.ajax({
        type: "POST",
        url: "../admin/webResourceList",
        data: {status:"2",except:id,resourceScode:code},
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var src="";
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	for(i=0;i<json.Records.length;i++){
        		src+="<a href='debris.html?id="+json.Records[i].id+"'><img src='"+json.Records[i].url+"' alt='"+json.Records[i].title+"' class='img'></a>"; 	   	        	            		
        	}
        	$("#content .content_inner .debris").append(src);
        }
    });
}



