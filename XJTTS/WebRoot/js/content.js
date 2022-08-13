var type=(url('?type') != null) ? url('?type') : '';
var id=decodeURI((url('?id') != null) ? url('?id') : '');
$(function(){
	$(".navbar-right li:eq("+Number(type)+") a").addClass("light");
//	$(".navbar-right li:eq("+2*Number(type)+") a").addClass("light");
	//$("iframe").attr("src","http://192.168.1.69/XJTTS/generic/web/viewer.html?file="+encodeURIComponent('http://192.168.1.69/XJTTS/upload/2016-07-08/71de7fa9-f391-4235-8dad-3611ee7432eb/新疆教师培训管理平台对接培训平台接口文档.pdf'))
	$(".ifrbox").colorbox({
		iframe : true,
		width : "370px",
		height: "394px",
		opacity : '0.5',
		overlayClose : false,
		escKey : true,
	});
	$(".idInput").val(id);
	getInfo();	
	
	$(".customContent").css("min-height",height-287-$(".footer").outerHeight(true)-$(".navbar-static-top").outerHeight(true));
	$(window).resize(function(){
		var height=$(window).height();
		$(".customContent").css("min-height",height-287-$(".footer").outerHeight(true)-$(".navbar-static-top").outerHeight(true));	
	})
})
var height=$(window).height();

function getInfo(){
	$(".spinner").css("display","block");
    $(".postmeta").css("display","none");
	$.get('admin/document_load.action?id='+id+'&documentType='+type,function(r){
//		console.log(r)
		if(r.status=="success"){
			$(".spinner").css("display","none");
			if(type=="4"){
				$(".customContent").css("display","block");
				$(".postmeta").css("display","block");
				$("p.title").html("培训动态");
				$(".location").html("培训动态").attr("href","list.html?type=4");
				$("h1").html(r.records.title);
				$(".Releasetime").html(r.records.creattime);
				$(".contentP").html(r.records.content);
				$(".inscribe").html(r.records.disciption);
			}else{
				$(".customContent").css("display","none");
				var docURL = r.records.docURL;
				docURL = docURL.replace(/:80/g,'').replace(/:443/g,'');
				$("iframe").attr("src","generic/web/viewer.html?file="+encodeURIComponent(docURL)).css("height","800px");	
				$("#originalfile").css("display","inline-block").attr("href",r.records.downloadURL);							
				if(type=="3"){
//					$("p.title").html("培训简报");
//					$(".location").html("培训简报").attr("href","list.html?type=3");					
				}else if(type=="2"){
					$("p.title").html("开班通知");
					$(".location").html("开班通知").attr("href","list.html?type=2");
					$("#downloadFile").css("display","inline-block");
					if(r.records.islogin=="0"){
						$("#downloadFile").click(function(){
							$(".ifrbox").click();
						})						
					}else if(r.records.islogin=="1"){
						$("#downloadFile").attr({"href":"admin/document_download.action?id="+id});
					}
				}else if(type=="1"){
//					$("p.title").html("培训文件");
//					$(".location").html("培训文件").attr("href","list.html?type=1");
				}
			}
		}else{
			
		}
		
	}).done(function(){		
		$(window).resize(function(){
			var height=$(window).height();
			$(".customContent").css("min-height",height-287-$(".footer").outerHeight(true)-$(".navbar-static-top").outerHeight(true));	
		})
	})
}

//搜索
function searchBtn(){
	var search=$("#search").val();
	if($.trim(search)!=""){
		window.location.href="list.html?search="+encodeURI($.trim(search));
	}
}


