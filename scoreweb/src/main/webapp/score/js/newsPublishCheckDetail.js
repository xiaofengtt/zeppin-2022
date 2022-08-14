var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var treeCategory = '';//分类树
var categoryArr = [];//分类数组
var teamArr = [];//球队分类
var sourceHtml = '';//来源
var flagSubmit = true;
var categoryResult = '';
var categroy = '';
var categoryList = //球队分类
	[
        {
        	name : "中超",
            uuid : "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e"
        }, {
        	name : "英超",
            uuid : "9bd4e736-e57f-46d2-ab25-b91a4c36b061"
        }, {
        	name : "西甲",
            uuid : "dad45ea2-5c9d-4102-8445-b9720267f93d"
        }, {
        	name : "意甲",
            uuid : "adf1fb28-306d-4870-96e9-875402d044b7"
        },{
        	name : "德甲",
            uuid : "42fee8ba-677f-4152-b10c-69d3befc6467"
        }, {
        	name : "欧冠",
            uuid : "5f61cb0b-8d40-4449-9d25-cbcddde89a57"
        }, {
        	name : "亚冠",
            uuid : "5c3a7159-70e5-490e-b242-328c2f5c3cc1"
        },
    ]
$(function(){
	if(uuid!=""){
		$(".add-btn").hide();
		getDetail();
	}else{
		questionOpt.queMce('revenueFeature');
		$(".disable-btn").hide();
		$(".submit-btn").hide();
	}
	getCategroy();
	var firstChild = "";
	for(var i=0;i<categoryList.length;i++){
		firstChild+='<li class="firstNode" data-id="'+categoryList[i].uuid+'"><b class="takeUp" onclick="getTeamList(this)"></b><span>'
		+categoryList[i].name+'</span><ul></ul></li>'
	}
	$(".treeHead").append(firstChild);
	$(".treeHead li b").each(function(){
		$(this).click().click();

	})
	$(".chooseBtn").click(function(){
		if($(".teamList").css("display")=="none"){
			$(".teamList").css("display","block");
			$(this).addClass("selectUp");
			$(".treeHead").css("background-size","1px "+($(".treeHead li.firstNode:eq(6)").offset().top-$(".treeHead").offset().top+8)+"px");
		}else{
			$(".teamList").css("display","none");
			$(this).removeClass("selectUp");
		}
	})
	$(".chooseCategoryBtn").click(function(){
		if($("#category").css("display")=="none"){
			$("#category").css("display","block");
			$(this).addClass("selectUp");
			$(".firstUl").css("background-size","1px "+($(".firstUl li.firstNode:last").offset().top-$(".firstUl").offset().top+8)+"px");
		}else{
			$("#category").css("display","none");
			$(this).removeClass("selectUp");
		}
	})
});
//文章详情
function getDetail(){
	$.ajax({
        url: '../back/newsPublish/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			$("#articleTitle").val(r.data.title);//文章标题
    			$("#articleUrl").val(r.data.sourceurl);//文章链接
    			$("#newsTime").val(r.data.newstime);//发布时间
    			$("#source").val(baseSourceArr[r.data.source]);//来源
    			$("#author").val(r.data.author);//作者
    			$("#revenueFeature").html(r.data.content);
    			questionOpt.queMce('revenueFeature');
    			categoryArr = r.data.categoryList;
    			teamArr = r.data.teamList;
    			$("#resourceId").uploadFile({
    		        id: "1",
    		        url: "../back/resource/add",
    		        allowedTypes: "png,jpg,jpeg",
    		        maxFileSize: 1024 * 1024 * 1024 * 2,
    		        fileName: "file",
    		        maxFileCount: 100,
    		        dragDropStr: "",
    		        extErrorStr: "文件格式不正确，请上传指定类型的文件",
    		        multiple: false,
    		        showDone: false,
    		        showDelete: true,
    		        deletelStr: '删除',
    		        doneStr: "完成",
    		        showAbort: false,
    		        showStatusAfterSuccess: false,
    		        maxFileCountErrorStr: "文件数量过多，请先删除。",
    		        onSuccess: function(files, data, xhr) {
    		            $('input[name="document"]').val(data.data.uuid);
    		            $('#imageShow').attr('src', '..' + data.data.url).css("cursor","pointer").show().unbind().click(function(){
    		            	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
    		            });
    		            $(".ajax-upload-dragdrop").css("display", "block");
    		            $(".coverTips").html("修改封面").css("color","#fff");
    		        }
    		    });
    			if(r.data.cover){
    				$('input[name="document"]').val(r.data.cover);
                    $(".ajax-upload-dragdrop").show();
                    $('#imageShow').attr('src', '..' + r.data.coverUrl).css("cursor","pointer").show().unbind().click(function(){
                    	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
                    });
                    $(".coverTips").html("修改封面").css("color","#fff");
    			}
    			if(r.data.status=="publish"){//已发布状态
    				$(".submit-btn,.nopass-btn").hide();
    				$(".remove-btn").show();
    			}else if(r.data.status=="delete"){
    				$(".submit-btn,.nopass-btn,.remove-btn").hide();
    			}else if(r.data.status=="nopass"||r.data.status=="uncheck"){
    				$(".remove-btn").hide();
    			}
    		} else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	
        });
    });   	
}
//处理子级循环
function treeCategorys(r){
	if(r.length>0){
		for(var i=0;i<r.length;i++){
			var className = '';
			var classSign = '';
			if(r[i].children && r[i].children.length>0){
				treeCategoryss(r[i].children);
			}
			for(var j=0;j<categoryArr.length;j++){
				if(categoryArr[j]==r[i].uuid){
					className = "light";
				}
			}
			if(r[i].children.length>0){
				classSign = "<b class='takeUp' onclick='getCategoryList(this)'></b>";
			}else{
				classSign = '';
			}
			treeCategoryChild += '<li class="" data-id="'+r[i].uuid+'">'+classSign+
			'<span class="'+className+'" onclick="changeCategory(this)">'
			+r[i].name+'</span>'+treeCategoryChildss+'</li>';
		}
		categroy = '<ul class="secondUl">'+treeCategoryChild+'</ul>';
		treeCategoryChild = '';
	}	
}
var treeCategoryChild = '';
var treeCategoryChilds = '';
var treeCategoryChildss = '';
function treeCategoryss(r){
	treeCategoryChilds = '';
	if(r.length>0){	
		for(var i=0;i<r.length;i++){
			var className = '';		
			for(var j=0;j<categoryArr.length;j++){
				if(categoryArr[j]==r[i].uuid){
					className = "light";
					
				}
			}
			treeCategoryChilds += '<li data-id="'+r[i].uuid+'"><span class="'+className+'" onclick="changeCategory(this)">'
			+r[i].name+'</span></li>';
		}
		treeCategoryChildss = '<ul class="thirdUl">'+treeCategoryChilds+'</ul>';
	}	
}
//获取类别
function getCategroy(){
	$.ajax({
        url: '../back/category/list',
        type: 'get',
        async:false,
        data: {
           "istag":true
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
//    			treeCategorys(r.data);
    			for(var i = 0;i<r.data.length;i++){
    				var className = '';
    				var classSign = '';
    				for(var m=0;m<r.data[i].children.length;m++){
        				treeCategorys(r.data[i].children);
    				}
    				for(var j=0;j<categoryArr.length;j++){
    					if(categoryArr[j]==r.data[i].uuid){
    						className = "light";   						
    					}
    				}
    				if(r.data[i].children.length>0){
    					classSign = "<b class='takeUp' onclick='getCategoryList(this)'></b>";
    				}else{
    					classSign = '';
    				}	
    				categroy ='<li class="firstNode" data-id="'+r.data[i].uuid+'">'+classSign
    				+'<span class="'+className+'" onclick="changeCategory(this)">'+r.data[i].name+'</span>'+categroy+'</li>';
    			}
    			$("#category").html('<ul class="firstUl">'+categroy+'</ul>');
    			$(".firstUl span.light").each(function(){
    				categoryResult += '<div data-id="'+$(this).parent().attr("data-id")+'"><span>'+$(this).html()+'</span><a></a></div>';
    			});
    			$("#categoryResult").html(categoryResult);
    			$("#category").append('<ul id="thirdUl" style="display: none;"></ul>');
    			$(".firstUl").css("background-size","1px "+($(".firstUl li.firstNode:last").offset().top-$(".firstUl").offset().top+8)+"px");
//    			//点击分类结果的删除
    			delectCategoryResule();
    		} else {
    			
    		}
    }).fail(function(r) {
        
    });   	
}
//点击分类结果的删除
function delectCategoryResule(){
	$("#categoryResult a").unbind("click").click(function(){
		var deleteUuid = $(this).parent().attr("data-id");
		$(this).parent().remove();
		$(".firstUl span").each(function(){
			if($(this).parent().attr("data-id")==deleteUuid){
				$(this).removeClass("light");
			}
		});
	});
}

//编辑保存
$(".submit-btn").click(function(){
	layer.confirm('确定要发布吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			var category = '',
				team = '';				
			$("#category li span").each(function(){
				if($(this).hasClass("light")){
					category += $(this).parent().attr("data-id") + ",";
				}
			});
			$(".lastNode span").each(function(){
				if($(this).hasClass("light")){
					team += $(this).parent().attr("data-id") + ",";
				}
			});
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			$.ajax({
		        url: '../back/newsPublish/edit',
		        type: 'post',
		        async:false,
		        data: {
		           "uuid":uuid,
		           "category":category.substring(0,category.length-1),
		           "team":team.substring(0,team.length-1),
		           "title":$("#articleTitle").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "content":tinymce.get('revenueFeature').getContent(),
		           "author":$("#author").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "newstime":$("#newsTime").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "cover":$("#document").val()
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			publishList("publish");	      
        		} else {
        			if(r.errorCode=="302"){
        				layer.msg(r.message, {
        		            time: 2000
        		        },function(){
        		        	window.location.href="login.html";
        		        });
        			}else{
        				layer.msg(r.message);
        			}
        		}
	    }).fail(function(r) {
	        layer.msg("error", {
	            time: 2000
	        },function(){
	        	window.location.href=window.location.href;
	        });
	    });   	
	}, function(){
	  layer.closeAll();
	});
});
//实际调用发布方法
function publishList(status){
	$.ajax({
        url: '../back/newsPublish/check',
        type: 'post',
        async:false,
        data: {
           "uuid":uuid,
           "status":status
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg("操作成功！", {
	            time: 2000
	        },function(){
	        	window.location.href=document.referrer;
	        });
				            
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
	}).fail(function(r) {
	    layer.msg("error", {
	        time: 2000
	    },function(){
	    	window.location.href=window.location.href;
	    });
	});   	
}

//点击禁用按钮
$(".nopass-btn").click(function(){
	layer.confirm('确定要审核不通过吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){			
		publishList("nopass");	
	}, function(){
	  layer.closeAll();
	});
});
$(".preview-btn").click(function(){
	$(".layerInner").scrollTop(0);
	$(".layerContent").html("");
	$(".layerContent").append('<h1>'+$("#articleTitle").val().replace(/(^\s*)|(\s*$)/g, "")+'</h1>');//标题
	$(".layerContent").append('<div><span>'+$("#author").val().replace(/(^\s*)|(\s*$)/g, "")
			+'</span><span>'+$("#newsTime").val().replace(/(^\s*)|(\s*$)/g, "")+'</span></div>');//时间和来源
	$(".layerContent").append(tinymce.get('revenueFeature').getContent());//内容
	$(".layerDiv").show();
});

//下架
$(".remove-btn").click(function(){
	
	layer.confirm('确定要下架吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){	
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/newsPublish/remove',
	        type: 'post',
	        async:false,
	        data: {
	           "uuid":uuid
	        }
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("下架成功！", {
		            time: 2000
		        },function(){
		        	window.location.href=document.referrer;
		        });
    		} else {
    			if(r.errorCode=="302"){
      				layer.msg(r.message, {
      		            time: 2000
      		        },function(){
      		        	window.location.href="login.html";
      		        });
      			}else{
      				layer.msg(r.message);
      			}
    		}
	    }).fail(function(r) {
	    	layer.msg("error", {
		            time: 2000
		        },function(){
		        	window.location.href=window.location.href;
		        });
	    });   
	}, function(){
		layer.closeAll();
	});
})
//获取球队列表
function getTeamList(obj){
	if($(obj).hasClass("takeUp")){
		if($(obj).parent().find("ul").html()==""){			
			$.ajax({
		        url: '../back/team/list',
		        type: 'get',
		        async:false,
		        data: {
		        	category:$(obj).parent().attr("data-id"),
		        }
		    }).done(function(r) {
		    	if (r.status == "SUCCESS") {
		    		var html= "";
		    		for(var i= 0;i<r.data.length;i++){
		    			var className="";
		    			for(var j=0;j<teamArr.length;j++){
		    				if(r.data[i].uuid==teamArr[j]){
		    					className ="light";
		    				}
		    			}
		    			html += '<li class="lastNode" data-id="'+r.data[i].uuid+'"><span class="'+className
		    			+'" onclick="changeTeam(this)">'+r.data[i].name+'</span></li>';
		    		}
		    		$(obj).parent().find("ul").html(html);
		    		if($(obj).parent().find("span").hasClass("light")){
		    			$(obj).parent().children("span").addClass("light");
		    		}
		    		var teamList = "";
		    		$(".lastNode span.light").each(function(){
		    			teamList += '<div data-id="'+$(this).parent().attr("data-id")+'"><span>'+$(this).html()+'</span><a></a></div>';
		    		});
	    			$("#team").html(teamList);
	    			$("#team a").unbind("click").click(function(){
	    				var deleteUuid = $(this).parent().attr("data-id");
	    				$(this).parent().remove();
	    				$(".treeHead li").each(function(){
	    					if($(this).attr("data-id")==deleteUuid){
	    						$(this).children("span").removeClass("light");
	    						if($(this).parent().find("span").hasClass("light")){
	    	    					$(this).parent().prev("span").addClass("light");
	    	    					return false;
	    	    				}else{
	    	    					$(this).parent().prev("span").removeClass("light");
	    	    				} 
	    						return false;
	    					}
	    				});
	    			});
		    	}else {
					if(r.errorCode=="302"){
		  				layer.msg(r.message, {
		  		            time: 2000
		  		        },function(){
		  		        	window.location.href="login.html";
		  		        });
		  			}else{
		  				layer.msg(r.message);
		  			}
				}
		    
		    })		
	    }
	    $(obj).removeClass("takeUp").addClass("takeDown").parent().find("ul").show();
	}else{
		$(obj).removeClass("takeDown").addClass("takeUp").parent().find("ul").hide();
	}
	$(".treeHead").css("background-size","1px "+($(".treeHead li.firstNode:eq(6)").offset().top-$(".treeHead").offset().top+8)+"px");
}
function changeTeam(obj){
	var deleteUuid = $(obj).parent().attr("data-id");
	var deleteName = $(obj).html();
	if($(obj).hasClass("light")){
		$(obj).removeClass("light");
		$("#team div").each(function(){
			if($(this).attr("data-id")==deleteUuid){
				$(this).remove();
				return false;
			}
		});
	}else{
		$(obj).addClass("light");
		$("#team").append('<div data-id="'+deleteUuid+'"><span>'+deleteName+'</span><a></a></div>');
		$("#team a").unbind("click").click(function(){
			var deleteUuid = $(this).parent().attr("data-id");
			$(this).parent().remove();
			$(".treeHead li").each(function(){
				if($(this).attr("data-id")==deleteUuid){
					$(this).children("span").removeClass("light");
					if($(this).parent().find("span").hasClass("light")){
    					$(this).parent().prev("span").addClass("light");
    					return false;
    				}else{
    					$(this).parent().prev("span").removeClass("light");
    				} 
					return false;
				}
			});
		});
	}
	if($(obj).parent().parent().find("span").hasClass("light")){
		$(obj).parent().parent().prev("span").addClass("light");
		return false;
	}else{
		$(obj).parent().parent().prev("span").removeClass("light");
	}
	return false;
}

function getCategoryList(obj){
	if($(obj).hasClass("takeUp")){
	    $(obj).removeClass("takeUp").addClass("takeDown").parent().children("ul").show();
	}else{
		$(obj).removeClass("takeDown").addClass("takeUp").parent().children("ul").hide();
	}
	$(".firstUl").css("background-size","1px "+($(".firstUl li.firstNode:last").offset().top-$(".firstUl").offset().top+8)+"px");
}
function changeCategory(obj){
	var deleteUuid = $(obj).parent().attr("data-id");
	var deleteName = $(obj).html();
	if($(obj).hasClass("light")){
		$(obj).removeClass("light");
		$("#categoryResult div").each(function(){
			if($(this).attr("data-id")==deleteUuid){
				$(this).remove();
				return false;
			}
		});
	}else{
		$(obj).addClass("light");
		$("#categoryResult").append('<div data-id="'+deleteUuid+'"><span>'+deleteName+'</span><a></a></div>');
		$("#categoryResult a").unbind("click").click(function(){
			var deleteUuid = $(this).parent().attr("data-id");
			$(this).parent().remove();
			$(".firstUl li").each(function(){
				if($(this).attr("data-id")==deleteUuid){
					$(this).children("span").removeClass("light");
					return false;
				}
			});
		});
	}
	return false;
}
