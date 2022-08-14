var pageNum = 1,
    pageSize = 20;
var flagSubmit = true;

$(function(){
	getCategroyList();
});


//获取列表
function getList(){
	$.ajax({
        url: '../back/match/list',
        type: 'get',
        async:false,
        data: {
        	"category": $("#categorySelect").val(),
        	"hometeam": $("#hometeamSelect").val(),
        	"awayteam": $("#awayteamSelect").val(),
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="10%">比赛状态</th>'+
				'<th width="15%">赛事</th>'+
				'<th width="20%">主队</th>'+
				'<th width="20%">客队</th>'+
				'<th width="15%">比赛时间</th>'+
				'<th width="20%" class="text-right">操作</th>'+
			'</tr>';
			for(var i=0;i<r.data.length;i++){
				var status = "";//状态
				var categoryHtml = '';
				if(r.data[i].status=="unstart"){
					status = "未开赛";
				}else if(r.data[i].status=="postponed"){
					status = "已推迟";
				}else if(r.data[i].status=="finished"){
					status = "已结束";
				}else{
					status = "进行中";
				}
				
				tableHtml += '<tr>'
    				+'<td><b>'+status+'</b></td>'
    				+'<td>'+r.data[i].categoryName+'</td>'
    				+'<td>'+r.data[i].hometeamName+'</td>'
    				+'<td>'+r.data[i].awayteamName+'</td>'
					+'<td>'+formatDate(r.data[i].time)+'</td>'
    				+'<td data-id="'+r.data[i].uuid+'" class="text-right"><a class="openBtn">开设</a></td>'
    				+'</tr>';
			}
            $('#pageTool').Paging({
                prevTpl: "<",
                nextTpl: ">",
                pagesize: pageSize,
                count: r.totalResultCount,
                current: pageNum,
                toolbar: true,
                pageSizeList: [10, 20, 50],
                callback: function(page, size, count) {
                    pageNum = page;
                    getList();
                    flag = false;
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                }
            });
            $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
            
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getList();
            });
            if(r.data.length==0){
				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
			}
			$(".table-list").html(tableHtml);
			allCheck();
			$(".openBtn").click(function(){
				open($(this).parent().attr("data-id"));
				return false;
			});
			$(".table-list tr").click(function(){
				var dataUrl = $(this).attr("data-url");
				if(dataUrl){
					window.location.href = dataUrl;
				}
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
        	
        });
    });   	
}

//获取类别
function getCategroyList(){
	$.ajax({
      url: '../back/category/list',
      type: 'get',
      async:false,
      traditional:true,
      data: {
    	  "status":"normal",
    	  "level":"3"
      }
  }).done(function(r) {
  		if (r.status == "SUCCESS") {
  			var categoryHtml = '<option value="">请选择</option>';
  			for(var i = 0;i<r.data.length;i++){
  				categoryHtml = categoryHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
  			}
  			$("#categorySelect").html(categoryHtml).selectpicker("refresh");
  		} else {
  			
  		}
  }).fail(function(r) {
      
  });   	
}

//获取球队
function getTeamList(){
	$.ajax({
	  url: '../back/team/list',
	  type: 'get',
	  async:false,
	  traditional:true,
	  data: {
		  "category": $("#categorySelect").val(),
		  "status":"normal"
	  }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
			for(var i = 0;i<r.data.length;i++){
				var teamHtml = '<option value="">请选择</option>';
	  			for(var i = 0;i<r.data.length;i++){
	  			teamHtml = teamHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
	  			}
	  			$("#hometeamSelect").html(teamHtml).selectpicker("refresh");
	  			$("#awayteamSelect").html(teamHtml).selectpicker("refresh");
			}
			
		} else {
			
		}
	}).fail(function(r) {
	  
	});   	
}

$('.search-btn').click(function(){
	getList();
})

function open(uuid){
	layer.confirm('确定开设这场比赛吗？', {
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
		      url: '../back/guessingMatch/add',
		      type: 'post',
		      async:false,
		      data: {
		    	  "infoMatch": uuid
		      }
		}).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			window.location.href="guessingMatchTypeDetail.html?uuid="+r.data.uuid;
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
}