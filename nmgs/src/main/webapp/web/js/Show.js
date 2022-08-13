$(function(){
	var id = (url('?id') != null) ? url('?id') : '';
	urls = (url('?url') != null) ? url('?url') : '';
	parentid = (url('?parentid') != null) ? url('?parentid') : '';
	pointTime = (url('?pointTime') != null) ? url('?pointTime') : '';
	
	getCommodity(id);
})
function gobtn(){
	$("a.closebtn").attr("href","Video.html?id="+parentid+"&pointTime="+pointTime+"&category="+category+"&pagetype="+pagetype);
}
var parentid;
var urls;
var pointTime;
var category;
category = (url('category') != null) ? url('?category') : '';
var pagetype= (url('?pagetype') != null) ? url('?pagetype') : '';
//获取商品信息
function getCommodity(id){
	$.get('../front/web/webInterface!execute?uid=i0004&id='+id,function(r){
		if(r.status=="success"){
			$(".shoppers a.goBuy").attr("href",r.data.commodityURL);
			$(".shoppers .shoppersLeft h1").html(r.data.name);
			$(".shoppers p.money span").html(r.data.price);
			var str ="";
			for ( var i = 0, l = r.data.webCommodityDisplays.length; i < l; i++ ) {
				str="<img data-src=.."+r.data.webCommodityDisplays[i].displayImageURL+">"+str;
			}	
			$("#circlr").prepend(str);
			var crl = circlr('circlr', {
				  scroll : true,
				  loader : 'loader'
				});
		}else{
			alert(r.message);
		}
	});
}
