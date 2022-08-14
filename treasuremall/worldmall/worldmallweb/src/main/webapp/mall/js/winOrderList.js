/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;
var datas = {};
var type = {
		'normal':'未领奖',
		'gold':'金币',
		'entity':'实物'
}
var gameType = {
	'tradition':'传统玩法',
	'onevone':'1v1PK',
	'group2':'两群PK',
	'group3':'三群PK'
}
var teamGroup = {
		'lucky':'幸运队',
		'raider':'夺宝队'
}

$(function(){	
	getList();
	goodsList();
	$(".lay-export").click(function(){
		exportList();
	});
});

$(".lay-submit").click(function(){	
	pageNum=1;
	getList();
	return false;
});
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,$ = layui.jquery
	  ,upload = layui.upload
	  ,element = layui.element;	  
	  //日期
	  laydate.render({
	    elem: '.worktimeEnd,.worktimeBegin'
	    ,type: 'datetime'
	    ,range: '_'
		,theme: '#3D99FF'
	  });
});
//奖品列表
function goodsList(){
	$.ajax({
      url: '../back/goods/list',
      type: 'get',
      async:false,
      data: {
      	'pageSize':'-1',
      	'pageNum':pageNum
      }
  }).done(function(r) {
  		if (r.status == "SUCCESS") {
  			var tableHtml = '<option value="">请选择</option>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].shortname+'</option>';
				}
				$("select[name='goodsId']").html(tableHtml);
  		} 
  })
}
//list
function getList(){
	datas = $("form").serializeObject();
	datas['pageSize'] = pageSize;
	datas['pageNum'] = pageNum;
	datas['sort'] = 'winning_time desc';
	$.ajax({
        url: '../back/winningInfo/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="5%" class="text-center">期号</th>'+
								'<th width="16%" class="text-center">奖品信息</th>'+
								'<th width="8%" class="text-center">开始时间</th>'+
								'<th width="8%" class="text-center">开奖时间</th>'+
								'<th width="6%" class="text-center">幸运号</th>'+
								'<th width="17%" class="text-center">中奖人</th>'+
								'<th width="6%" class="text-center">玩法类型</th>'+
								'<th width="6%" class="text-center">投注量</th>'+
								'<th width="6%" class="text-center">是否马甲</th>'+
								'<th width="7%" class="text-center">领奖IP</th>'+
								'<th width="7%" class="text-center">领奖类型</th>'+
								'<th width="8%" class="text-center">领奖时间</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var imgUrl = r.data[i].imageUrl?(".."+r.data[i].imageUrl):'../image/img-defaultAvatar.png';
					var luckyGroup = '';
					if(r.data[i].gameType=="group2"){
						luckyGroup = '<p>获奖战队：'+teamGroup[r.data[i].luckyGroup]+'</p><p>获得金币：'+r.data[i].dealPrice+'</p>'
					}
					tableHtml += '<tr data-showId="'+r.data[i].frontUser+'"><td class="text-center">'+r.data[i].issueNum+
					'</td><td class="text-center"><div style="width:35%;float:left;"><img src="..'+
					r.data[i].cover+'"/></div><div style="width:65%;float:left; text-align:left;"><p>'+r.data[i].shortTitle+'</p><p>价值：$'
					+r.data[i].price+'</p></div>'+
					'</td><td class="text-center">'+formatDate(r.data[i].starttime)+
					'</td><td class="text-center">'+formatDate(r.data[i].winningTime)+'</td><td class="text-center">'
					+r.data[i].luckynum+'</td><td class="text-center showDetail"><div style="width:35%;float:left;text-align:right;padding-right:5px;"><img src="'+
					imgUrl+'" class="touxiang" /></div><div style="width:65%;float:left; text-align:left;"><p>昵称：'
					+r.data[i].nickname+'</p><p>ID：'+r.data[i].showId+'</p>'+luckyGroup+'</div>'+
					'</td><td class="text-center">'+gameType[r.data[i].gameType]+
					'</td><td class="text-center">'+r.data[i].paymentDAmount+
					'</td><td class="text-center">'+(r.data[i].isRobot?'是':'否')+'</td><td class="text-center">'+(r.data[i].ip?r.data[i].ip:'-')+
					'</td><td class="text-center">'+type[r.data[i].type]+
					'</td><td class="text-center">'+(r.data[i].operattime?formatDate(r.data[i].operattime):'-')+'</td></tr>'; 
				}
				if (r.totalPageCount!=0) {
				    $('#pageTool').Paging({
				        prevTpl: "<",
				        nextTpl: ">",
				        pagesize: pageSize,
				        count: r.totalResultCount,
				        current: pageNum,
				        toolbar: true,
				        pageSizeList: [50, 200, 1000],
				        callback: function(page, size, count) {
				            pageNum = page;
				            getList();
				            flag = false;
				            document.body.scrollTop = document.documentElement.scrollTop = 0;
				        }
				    });
				    $(".ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
				    $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
				}else{
					$("#pageTool").hide();
				}
				$(".ui-select-pagesize").unbind("change").change(function() {
				    pageSize = $(this).val();
				    pageNum = 1;
				    getList();
				});
				if(r.data.length==0){
					tableHtml += '<tr><td colspan="12" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);
				$(".showDetail").click(function(){
					layer.open({
						type: 2, 
						title:false, 
						area: ['90%', '90%'],
						content: ['userDetail.html?uuid='+$(this).parent().attr("data-showId")] 
					});
					return false;
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

//导出
function exportList(){
	var layerIndex = layer.open({
		type: 3
	});
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	var data = $("form").serialize();
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href = "../back/winningInfo/exportlist?"+data;
	link.click();
	return false;
}
