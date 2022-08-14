var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pageNum = 1,
	pageSize = 10;
var flagSubmit = true;
var type = {"Goalkeepers":"门将","Defenders":"后卫","Midfielders":"中场","Forwards":"前锋"}
	
$(function(){
	$("#resourceId").uploadFile({
        id: "1",
        url: "../back/resource/add",
        allowedTypes: "png,jpg,jpeg",
        maxFileSize: 1024 * 1024 * 10,
        fileName: "file",
        maxFileCount: 100,
        extErrorStr: "文件格式不正确，请上传指定类型的文件",
        multiple: false,
        showDone: false,
        showDelete: false,
        showFileCounter: false,
        showAbort: false,
        showProgress: false,
        showStatusAfterSuccess: false,
        onLoad : function(e) {
        	 $(".ajax-upload-dragdrop").css("display", "none");
        	getDetail();
        	getPlayers();
		},
        onSuccess: function(files, data, xhr) {
            $('#icon').val(data.data.uuid);
            $('#iconImg').attr('src', '..' + data.data.url).show().unbind().click(function(){
            	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
            });
        }
    });
});
//球队
function getDetail(){
	$.ajax({
        url: '../back/team/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var imageUrl = '';
    			$("#name").val(r.data.name);//名称
    			$("#shortname").val(r.data.shortname);//英文名称
    			var coaches = "";
    			if(r.data.coachesList != null && r.data.coachesList.length > 0){
	    			for(var coache in r.data.coachesList){
	    				coaches = coaches + r.data.coachesList[coache].coach_name + ",";
	    			}
	    			coaches = coaches.substring(0, coaches.length-1);
    			}else{
    				coaches = "无";
    			}
    			$("#coaches").val(coaches);//教练列表
    			$("#icon").val(r.data.icon);
    			var imageUrl = r.data.iconUrl ? ".." +r.data.iconUrl:"img/default-logo.png";
    			$('#iconImg').attr('src', imageUrl).show().unbind().click(function(){
                	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
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


function getPlayers(){
	var str = 'team='+uuid+'&pageSize='+pageSize+'&pageNum='+pageNum+'&sort=ip.number*1';
	$.get('../back/team/playerList?'+str,function(r){
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="7%">号码</th>'+
				'<th width="15%">球员名称</th>'+
				'<th width="15%">英文名称</th>'+
				'<th width="7%">年龄</th>'+
				'<th width="10%">位置</th>'+
				'<th width="10%">国籍</th>'+
				'<th width="7%">场次</th>'+
				'<th width="7%">进球</th>'+
				'<th width="7%">黄牌</th>'+
				'<th width="7%">红牌</th>'+
				'<th width="8%">操作</th>'+
			'</tr>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<tr>'
					+'<td>'+r.data[i].number+'</td>'
					+'<td><b>'+r.data[i].cnname+'</b></td>'
					+'<td>'+r.data[i].name+'</td>'
					+'<td>'+r.data[i].age+'</td>'
					+'<td>'+type[r.data[i].type]+'</td>'
					+'<td>'+r.data[i].country+'</td>'
					+'<td>'+r.data[i].matchplayed+'</td>'
					+'<td>'+r.data[i].goals+'</td>'
					+'<td>'+r.data[i].yellowcards+'</td>'
					+'<td>'+r.data[i].redcards+'</td>'
					+'<td><a  onclick="playerEdit(this)" data-uuid="'+r.data[i].uuid+'" data-name="'+r.data[i].name+'" data-cnname="'+r.data[i].cnname+'">修改</a></td>'
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
                    getPlayers();
                    flag = false;
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                }
            });
            $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
            
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getPlayers();
            });
            if(r.data.length==0){
				tableHtml += '<tr><td colspan="11" align="center">暂无球员数据</td></tr>';
			}
			$(".table-list").html(tableHtml);
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

//编辑保存
$(".submit-btn").click(function(){
	layer.confirm('确定要保存吗？', {
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
	        url: '../back/team/edit',
	        type: 'post',
	        async:false,
	        data: {
	           "uuid":uuid,
	           "name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
	           "icon":$("#icon").val()
	        }
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("保存成功！", {
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
});


$(".back-btn").click(function(){
	layer.confirm('确定要返回吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){	
		window.location.href = "teamList.html";
	}, function(){
		layer.closeAll();
	});
});

function playerEdit(t){
	var player_uuid = $(t).attr("data-uuid");
	var player_name = $(t).attr("data-name");
	var player_cnname = $(t).attr("data-cnname");
	var html = '<div class="form-group col-md-12" style="padding-top:30px"><label>英文名称：</label><div class="content-items"><input class="form-control" id="player-name" value="'
		+ player_name + '" disabled></div><div class="clear"></div></div><div class="form-group col-md-12"><label>名称：</label><div class="content-items"><input class="form-control" id="player-cnname" value="'
		+ player_cnname + '"></div><div class="clear"></div></div><div class="btn-group"><a class="sure-btn" onclick="plyarSubmit(\''
		+ player_uuid + '\')">保存</a><a class="cancel-btn" onclick="plyarClose()">取消</a></div>';
	layer.open({
		type: 1,
		title: '编辑球员',
		closeBtn: 0,
		shadeClose: true,
		content: html
	});
};

function plyarSubmit(uuid){
	$.ajax({
        url: '../back/team/playerEdit',
        type: 'post',
        async:false,
        data: {
           "uuid":uuid,
           "cnname":$("#player-cnname").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg("保存成功！", {
	            time: 2000
	        },function(){
	        	layer.closeAll('page');
	        	getPlayers();
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
};

function plyarClose(){
	layer.closeAll();
};