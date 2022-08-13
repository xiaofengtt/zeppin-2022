var type=(url('?type') != null) ? url('?type') : '';
var search=decodeURI((url('?search') != null) ? url('?search') : '');
var pageNum=1;
var pagesize=15;
var pageTotal;
$(function(){
	$(".navbar-right li:eq("+Number(type) +") a").addClass("light");
	$("#search").val(search);
	if(type!=""){
		getList(1,search);
	}else{
		type=5;
	}
	if(type==2||type==3){
		$(".quickSearch").css("display","block");
		quickSearchList();
	}else{
		$(".quickSearch").css("display","none");
	}
	if(search!=""){
		$(".navbar-right li a").removeClass("light");
		getList(1,search);
		$("p.title").html("搜索结果");
		$(".location").html("搜索结果");
	}else{
		search="";
	}
	var height=$(window).height();
	$("ul.articleList").css("min-height",height-279-$(".footer").outerHeight(true)-$(".navbar-static-top").outerHeight(true));
	$(window).resize(function(){
		var height=$(window).height();
		$("ul.articleList").css("min-height",height-279-$(".footer").outerHeight(true)-$(".navbar-static-top").outerHeight(true));	
	})
})

function getList(pageNum,search){
	$(".currentPage").html(pageNum);
	$.get('admin/document_list.action?documentType='+type+'&pagenum='+pageNum+'&pagesize='+pagesize+'&title='
			+search,function(r){
//			console.log(r)
		$(".totalArticle").html("共"+r.totalCount+"篇");
		if(r.totalCount=="0"){
			pageTotal=1;
		}else{
			pageTotal=Math.ceil(r.totalCount/pagesize);
		}		
		$(".totalPage").html(pageTotal);		
		if(r.status=="success"){				
			var str1 ="";
			var clear="<div class='clear'></div>"
			if(type=="1"){
//				$("p.title").html("培训文件");
//				$(".location").html("培训文件");
//				if(r.totalCount!="0"){
//					for ( var i = 0, l = r.records.redHeadDocument.length; i < l; i++ ) {				
//						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.redHeadDocument[i].id)+"&type=1' title='"+r.records.redHeadDocument[i].title+"'>"+r.records.redHeadDocument[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.redHeadDocument[i].creattime+"</span><span class='clear'></span></li>";
//					}
//				}else{
//					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
//				}
//				
			}else if(type=="2"){
				$("p.title").html("开班通知");
				$(".location").html("开班通知");
				if(r.totalCount!="0"){
					for ( var i = 0, l = r.records.startMessage.length; i < l; i++ ) {				
						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.startMessage[i].id)+"&type=2' title='"+r.records.startMessage[i].title+"'>"+r.records.startMessage[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.startMessage[i].creattime+"</span><span class='clear'></span></li>";
					}
				}else{
					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
				}
			}else if(type=="3"){
//				$("p.title").html("培训简报");
//				$(".location").html("培训简报");
//				if(r.totalCount!="0"){
//					for ( var i = 0, l = r.records.workReport.length; i < l; i++ ) {				
//						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.workReport[i].id)+"&type=3' title='"+r.records.workReport[i].title+"'>"+r.records.workReport[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.workReport[i].creattime+"</span><span class='clear'></span></li>";
//					}
//				}else{
//					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
//				}
			}else if(type=="4"){
				$("p.title").html("培训动态");
				$(".location").html("培训动态");
				if(r.totalCount!="0"){
					for ( var i = 0, l = r.records.dynamic.length; i < l; i++ ) {				
						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.dynamic[i].id)+"&type=4' title='"+r.records.dynamic[i].title+"'>"+r.records.dynamic[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.dynamic[i].creattime+"</span><span class='clear'></span></li>";
					}
				}else{
					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
				}
			}else if(search!=""){
				var j=0;
				if(r.totalCount!="0"){
					for ( var i = 0, l = r.records.search.length; i < l; i++ ) {				
						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.search[i].id)+"&type="+r.records.search[i].type+"' title='"+r.records.search[i].title+"'>"+r.records.search[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.search[i].creattime+"</span><span class='clear'></span></li>";
						j++;
					}
				}else{
					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
				}
			}	
			$("ul.articleList").html(str1+clear);
		}else{
			$("ul.articleList").html("<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>");
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

//上一页
function previousPage(){
	if(pageNum>1){
		pageNum--;
		if(type==2&&$("#scode").val()!=""){
			getLists(pageNum,$("#scode").val());
		}else if(type==3&&$("#scode").val()!=""){
			getLists(pageNum,$("#scode").val());
		}else{
			getList(pageNum,search);
		}
	}
	
}
//下一页
function nextPage(){
	if(pageNum<pageTotal){
		pageNum++;
		if(type==2&&$("#scode").val()!=""){
			getLists(pageNum,$("#scode").val());
		}else if(type==3&&$("#scode").val()!=""){
			getLists(pageNum,$("#scode").val());
		}else{
			getList(pageNum,search);
		}
		
	}	
}
//首页
function indexPage(){
	pageNum=1;
	if(type==2&&$("#scode").val()!=""){
		getLists(1,$("#scode").val());
	}else if(type==3&&$("#scode").val()!=""){
		getLists(1,$("#scode").val());
	}else{
		getList(1,search);
	}
	
}
//末页
function lastPage(){
	pageNum=pageTotal;
	if(type==2&&$("#scode").val()!=""){
		getLists(pageTotal,$("#scode").val());
	}else if(type==3&&$("#scode").val()!=""){
		getLists(pageTotal,$("#scode").val());
	}else{
		getList(pageTotal,search);
	}
	
}
//获取快速搜索列表
function quickSearchList(){
	$.ajax({
        type: "GET",
        url: "admin/document_getProjectTypeList.action",
        data: {},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Result=="OK"){
        		var str="";
        		var width=($(".loginList").width()-$(".loginList a").width())/2-10+"px";
        		$(".searchList ul").html("");
        		for(i=0;i<json.Records.length;i++){
        			str+="<li style='margin-left:"+width+"'><span onclick='getLists(1,\""+json.Records[i].scode+"\")'><a>"+json.Records[i].name+"</a></span></li>"
        		}
        		$(".searchList ul").html(str);
        	}
        }
	});
}

function getLists(pageNum,scode){
	$(".currentPage").html(pageNum);
	$("#scode").val(scode)
	$.get('admin/document_list.action?documentType='+type+'&pagenum='+pageNum+'&pagesize='+pagesize+'&projectType='+scode+'&title='
			+search,function(r){
//			console.log(r)
		$(".totalArticle").html("共"+r.totalCount+"篇");
		if(r.totalCount=="0"){
			pageTotal=1;
		}else{
			pageTotal=Math.ceil(r.totalCount/pagesize);
		}		
		$(".totalPage").html(pageTotal);		
		if(r.status=="success"){				
			var str1 ="";
			var clear="<div class='clear'></div>"
			if(type=="1"){
//				$("p.title").html("培训文件");
//				$(".location").html("培训文件");
//				if(r.totalCount!="0"){
//					for ( var i = 0, l = r.records.redHeadDocument.length; i < l; i++ ) {				
//						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.redHeadDocument[i].id)+"&type=1' title='"+r.records.redHeadDocument[i].title+"'>"+r.records.redHeadDocument[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.redHeadDocument[i].creattime+"</span><span class='clear'></span></li>";
//					}
//				}else{
//					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
//				}
//				
			}else if(type=="2"){
				$("p.title").html("开班通知");
				$(".location").html("开班通知");
				if(r.totalCount!="0"){
					for ( var i = 0, l = r.records.startMessage.length; i < l; i++ ) {				
						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.startMessage[i].id)+"&type=2' title='"+r.records.startMessage[i].title+"'>"+r.records.startMessage[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.startMessage[i].creattime+"</span><span class='clear'></span></li>";
					}
				}else{
					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
				}
			}else if(type=="3"){
//				$("p.title").html("培训简报");
//				$(".location").html("培训简报");
//				if(r.totalCount!="0"){
//					for ( var i = 0, l = r.records.workReport.length; i < l; i++ ) {				
//						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.workReport[i].id)+"&type=3' title='"+r.records.workReport[i].title+"'>"+r.records.workReport[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.workReport[i].creattime+"</span><span class='clear'></span></li>";
//					}
//				}else{
//					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
//				}
			}else if(type=="4"){
				$("p.title").html("培训动态");
				$(".location").html("培训动态");
				if(r.totalCount!="0"){
					for ( var i = 0, l = r.records.dynamic.length; i < l; i++ ) {				
						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.dynamic[i].id)+"&type=4' title='"+r.records.dynamic[i].title+"'>"+r.records.dynamic[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.dynamic[i].creattime+"</span><span class='clear'></span></li>";
					}
				}else{
					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
				}
			}else if(search!=""){
				var j=0;
				if(r.totalCount!="0"){
					for ( var i = 0, l = r.records.search.length; i < l; i++ ) {				
						str1+="<li><span class='col-md-9'><a href='content.html?id="+encodeURI(r.records.search[i].id)+"&type="+r.records.search[i].type+"' title='"+r.records.search[i].title+"'>"+r.records.search[i].title+"</a></span><span class='col-md-1'></span><span class='col-md-2'>"+r.records.search[i].creattime+"</span><span class='clear'></span></li>";
						j++;
					}
				}else{
					str1="<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>";
				}
			}	
			$("ul.articleList").html(str1+clear);
		}else{
			$("ul.articleList").html("<p style='color:#666;font-size:16px;line-height:100px;text-align:center;'>暂无内容</p>");
		}
	});
}
