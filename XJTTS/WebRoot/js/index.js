/**
 * Created by thanosYao on 2015/7/2.
 */
// Can also be used with $(document).ready()
$(document).ready(function(){
	getList();
    $('.xs-navlist-toggle').click(function(){
        $('.xs-navlist').toggle();
    });
    $('.flexslider').flexslider({
        animation: "slide",
        slideshowSpeed: 10000
        //controlsContainer: ".flex-container"
    });
    $('.navlist li a.firstmenu').hover(function(){
        $(this).css("border-right","1px solid #fff");
        $(this).parent().prev().children('a').css("border-right","1px solid #fff");
        $('.subnavlist').hide();
        $(this).next('.subnavlist').fadeIn();
    },function(){
        $(this).css("border-right","1px solid #d4d3ce");
        $(this).parent().prev().children('a').css("border-right","1px solid #d4d3ce");
    });
    $('.navbar-nav').hover(function(){
        $('.subnavlist').hide();
    });
    $('.subnavlist').hover(function(){
        $(this).parent().addClass('active');
    }, function(){
        $(this).parent().removeClass('active');
        $(this).fadeOut();
    });
    $(function(){
        // bind change event to select
        $('.selecttogo').bind('change', function () {
            var url = $(this).val(); // get selected value
            if (url) { // require a URL
                window.location = url; // redirect
            }
            return false;
        });
    });
    $('.searchBtn').click(function(){
        //Search code
        console.log("start search");
    });
});

function getList(){
	$.get('admin/document_list.action?documentType=0&pagenum=1&pagesize=9',function(r){
		if(r.status=="success"){
			var str1 ="";
			for ( var i = 0, l = r.records.redHeadDocument.length; i < l; i++ ) {	
				if(i>3){
					break;
				}
				str1+="<li><a href='content.html?id="+encodeURI(r.records.redHeadDocument[i].id)+"&type=1' title='"+r.records.redHeadDocument[i].title+"'>"+r.records.redHeadDocument[i].title+"</a></li>";
			}	
			var str2 ="";
			for ( var i = 0, l = r.records.workReport.length; i < l; i++ ) {
				if(i>3){
					break;
				}
				str2+="<li><a href='content.html?id="+encodeURI(r.records.workReport[i].id)+"&type=3' title='"+r.records.workReport[i].title+"'>"+r.records.workReport[i].title+"</a></li>";
			}
			var str3 ="";
			for ( var i = 0, l = r.records.startMessage.length; i < l; i++ ) {		
				if(i>8){
					break;
				}
				str3+="<li><a href='content.html?id="+encodeURI(r.records.startMessage[i].id)+"&type=2' title='"+r.records.startMessage[i].title+"'>"+r.records.startMessage[i].title+"</a></li>";
			}
			var str4 ="";
			for ( var i = 0, l = r.records.dynamic.length; i < l; i++ ) {		
				if(i>8){
					break;
				}
				str4+="<li><a href='content.html?id="+encodeURI(r.records.dynamic[i].id)+"&type=4' title='"+r.records.dynamic[i].title+"'>"+r.records.dynamic[i].title+"</a></li>";
			}
			$("ol.list1").html(str1);
			$("ol.list2").html(str3);
			$("ol.list3").html(str2);
			$("ol.list4").html(str4);
		}else{
			$(".boxlist").html("<p style='color:#666;font-size:16px;line-height:50px;text-align:center;'>暂无内容</p>");
		}
	});
}
//搜索
function searchBtn(){
	var search=$("#search").val();
	if($.trim(search)!=""){
		window.location.href="list.html?search="+encodeURI($.trim(search));
	}
}
