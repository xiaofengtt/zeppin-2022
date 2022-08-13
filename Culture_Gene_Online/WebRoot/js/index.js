
$(function(){
	hotSearch();
	indexList();
	$("#banner .banner_inner .nav li").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	$("#banner .banner_inner .loggedLn li .userName").click(function(){
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
	
	$.get('../admin/categoryList?level=1&Status=2',function(r){
		var nationalHtml ="";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			$("#content .content_inner a.classification:eq("+i+")").html(r.Records[i].name).attr({"name":r.Records[i].id,"href":encodeURI("list.html?parent="+r.Records[i].name+"&&id="+r.Records[i].id)});
		}
	});

	$("#banner .banner_inner .Search a.search").click(function(){
		if($("#banner .banner_inner .Search input.search_input").val()==""){
			return false;
		}else{
			window.location.href="Search.html?search="+encodeURI($("#banner .banner_inner .Search input.search_input").val());
		}
	});
	
})
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
var n=1;
var count;
//取列表
function indexList(){
	var src="";
	var clear="<div class='clear'></div>";
	$.ajax({
		type: "POST",
        url: "../admin/webResourceList",
        data: {level:"1",status:"2",pagesize:"16",pagenum:n},
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
        		var width=json.Records[i].width;
        		var height=json.Records[i].height;
        		var style="";
        		var top="";
        		var left="";
        		var ratio;
        		if(width>=height){
        			ratio=254/height;
        			width=width*ratio;
        			left=(254-width)/2;
        			style="height:100%;margin-left:"+left+"px";
        		}else if(width<height){
        			ratio=254/width;
        			height=height*ratio;
        			top=(254-height)/2;
        			style="width:100%;margin-top:"+top+"px";
        		}
        		if(i=="1"){
        			src+="<div class='IMG img2"+n+"'><p class='list_top'><img src='../images/left_row.png' alt='左箭头' /><br><span class='leftspan1'>"+json.Records[0].title+"</span></p><p class='list_bottom'><img src='../images/right_row.png' alt='左箭头' /><br><span class='rightspan1'>"+json.Records[1].title+"</span></p></div>";
        		}else if(i=="4"){
        			if(json.Records.length>="5"){
        				src+="<div class='IMG img6 img6"+n+"'><p class='list_middle'><img src='../images/right_row.png' alt='左箭头' /><br><span class='rightspan6'>"+json.Records[4].title+"</span></p></div>"
        			}else{
        				src+="";
        			}
        			
        		}else if(i=="10"){
        			src+="<div class='IMG img10"+n+"'><p class='list_top'><img src='../images/left_row.png' alt='左箭头' /><br><span class='leftspan10'>"+json.Records[9].title+"</span></p><p class='list_bottom'><img src='../images/right_row.png' alt='左箭头' /><br><span class='rightspan10'>"+json.Records[10].title+"</span></p></div>";
        		}else if(i=="15"){
        			src+="<div class='IMG img15"+n+"'><p class='list_top'><img src='../images/left_row.png' alt='左箭头' /><br><span class='leftspan15'>"+json.Records[14].title+"</span></p><p class='list_bottom'><img src='../images/right_row.png' alt='左箭头' /><br><span class='rightspan15'>"+json.Records[15].title+"</span></p></div>";
        		}
        		src+="<div class='IMG list "+i+"'><a href='details.html?id="+id+"'><img src='"+url+"' alt='图片列表'  class='img' style='"+style+"'/></a></div>"; 	  
        	}

        	$(".ImgList").append(src);
//        	$(".img2"+n+" .leftspan1").html(json.Records[0].title);
//        	$(".img2"+n+" .rightspan1").html(json.Records[1].title);
//        	$(".img6"+n+" .rightspan6").html(json.Records[5].title);
//        	$(".img10"+n+" .leftspan10").html(json.Records[9].title);
//        	$(".img10"+n+" .rightspan10").html(json.Records[10].title);
//        	$(".img15"+n+" .leftspan15").html(json.Records[14].title);
//        	$(".img15"+n+" .rightspan15").html(json.Records[15].title);
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


