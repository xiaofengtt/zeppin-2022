/**
 * 
 */

var scorelotteryactivityInfo = '';//签到活动name值
var scorelotteryselectDate;
var scorelotteryindex;//item序号
var scorelotterygoodsList = [];//存储最开始设置的数组

layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,$ = layui.jquery
	  ,upload = layui.upload
	  ,element = layui.element;	
	  element.on('tab(activity)', function(data){
		    if(data.index==2){
		    	getScorelottery()
		    	getScorelotteryGoodsList()
		    }
	  });
	// 获取积分转盘活动基础信息
		function getScorelottery(){
			$.ajax({
		        url: '../back/activity/get',
		        type: 'get',
		        async:false,
		        data: {
		        	'uuid':'scorelottery'
		        }
		    }).done(function(r) {
				if (r.status == "SUCCESS") {
					  //日期
					  laydate.render({
						    elem: '.scorelottery .activityTime'
						    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
						    ,type: 'datetime'
						    ,range:'-'
							,theme: '#3D99FF'
					    	,done: function(value, date){
					    		scorelotteryselectDate = value;
					        }
					  });
					  $(".scorelottery input[name='bannerUrl']").val(r.data.bannerUrl)
					  scorelotteryactivityInfo = r.data.name
					  scorelotteryselectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					  if(r.data.configuration){
						  $(".scorelottery input[name='timesline']").val(JSON.parse(r.data.configuration).timesLine)
						  $(".scorelottery input[name='scoreline']").val(JSON.parse(r.data.configuration).scoreLine)
					  }
					  if(r.data.status=="disable"){
						  $(".scorelottery .lay-startActivity").show()
						  $(".scorelottery .lay-closeActivity").hide()
					  }else{
						  $(".scorelottery .lay-startActivity").hide()
						  $(".scorelottery .lay-closeActivity").show()
					  }
					  $(".scorelottery .lay-startActivity").click(function(){
						  changeStatus('scorelottery','normal')
					  })
					  $(".scorelottery .lay-closeActivity").click(function(){
						  changeStatus('scorelottery','disable')
					  })
				}
		    })
		}
		$(".scorelottery .lay-submit").click(function(){
//			for(var m=0;m<$(".buyfree .goods-item").length;m++){
//				if($(".buyfree .goods-item"+m).find(".buyfree input[name='goodsId']").val()==""){
//					layer.msg("有未选择商品的选项");
//					return false;
//				}else if($(".buyfree .goods-item"+m).find(".buyfree input[name='image']").val()==""){
//					layer.msg("有未上传图片的选项");
//					return false;
//				}else if($(".buyfree .goods-item"+m).find(".buyfree input[name='goodsShares']").val()==""){
//					layer.msg("有未设置总需人次的选项");
//					return false;
//				}
//			}
			var data = {},details = [],detailItem = {};
			data['uuid'] = 'scorelottery';
			data['starttime'] = scorelotteryselectDate.substring(0,19)
			data['endtime'] = scorelotteryselectDate.substring(22)
			data['configuration'] ='{"timesLine":'+$(".scorelottery .timesline").val().replace(/(^\s*)|(\s*$)/g, "")+
									',"scoreLine":"'+$(".scorelottery .scoreline").val().replace(/(^\s*)|(\s*$)/g, "")+'"}'
			data['bannerUrl'] = $(".scorelottery .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
//			for(var i=0;i<scorelotteryindex;i++){
//				detailItem = {}
//				if($(".buyfree .goods-item"+i).find("input[name='uuid']").val()==""){
//					detailItem['activityInfo']=activityInfo
//					detailItem['uuid']=''
//					detailItem['activityInfo']=activityInfo
//					detailItem['type']='add'
//					detailItem['goodsId']=$(".checkIn .goods-item"+i).find("input[name='goodsId']").val()
//					detailItem['goodsCover']=$(".checkIn .goods-item"+i).find("input[name='image']").val()
//					detailItem['goodsPrice']=Number($(".checkIn .goods-item"+i).find("input[name='goodsPrice']").val())
//					detailItem['goodsTitle']=$(".checkIn .goods-item"+i).find("input[name='goodsTitle']").val()
//					detailItem['goodsShortTitle']=$(".checkIn .goods-item"+i).find("input[name='goodsShortTitle']").val()
//					detailItem['shares']=Number($(".checkIn .goods-item"+i).find("input[name='goodsShares']").val())
//					detailItem['sort']= i
//					details.push(detailItem)
//				}
//			}
//			for(var j=0;j<goodsList.length;j++){
//				detailItem = {}
//				var ishas = false;
//				for(var i=0;i<index;i++){
//					if(goodsList[j].uuid==$(".buyfree .goods-item"+i).find("input[name='uuid']").val()){
//						ishas = true;
//						detailItem['goodsCover']=$(".buyfree .goods-item"+i).find("input[name='image']").val()
//						detailItem['shares']=Number($(".buyfree .goods-item"+i).find("input[name='goodsShares']").val())
//					}
//				}
//				if(ishas){
//					detailItem['type']="edit"
//				}else{
//					detailItem['type']="delete"
//					detailItem['goodsCover']=goodsList[j].goodsCover
//					detailItem['shares']=goodsList[j].shares
//				}
//				detailItem['uuid']=goodsList[j].uuid
//				detailItem['activityInfo']=activityInfo
//				detailItem['goodsId']=goodsList[j].goodsId
//				detailItem['goodsPrice']=goodsList[j].goodsPrice
//				detailItem['goodsTitle']=goodsList[j].goodsTitle
//				detailItem['goodsShortTitle']=goodsList[j].goodsShortTitle
//				detailItem['sort'] = j
//				details.push(detailItem)
//			}
			for(var i=0;i<3;i++){
				detailItem = {}
				if(i==0){
					detailItem['uuid']=''
					detailItem['activityInfo']=scorelotteryactivityInfo
					detailItem['type']='add'
					detailItem['prizeTitle']='签到送金币'
					detailItem['prizeType']='gold'
					detailItem['prizeCover']='ae7028ea-5ae2-49bf-a71e-57852c3ea8a0'
					detailItem['prize']='5'
					detailItem['winningRate']= 30.19
					detailItem['sort']= 0
				}else if(i==1){
					detailItem['uuid']=''
					detailItem['activityInfo']=scorelotteryactivityInfo
					detailItem['type']='add'
					detailItem['prizeTitle']='签到送金券'
					detailItem['prizeType']='voucher'
					detailItem['prizeCover']='ae7028ea-5ae2-49bf-a71e-57852c3ea8a0'
					detailItem['prize']='9ea6d6cd-e395-4f02-af83-a91d0c7fb3f9'
					detailItem['winningRate']= 45
					detailItem['sort']= 1
				}else if(i==2){
					detailItem['uuid']=''
					detailItem['activityInfo']=scorelotteryactivityInfo
					detailItem['type']='add'
					detailItem['prizeTitle']='签到送实物'
					detailItem['prizeType']='entity'
					detailItem['prizeCover']='ae7028ea-5ae2-49bf-a71e-57852c3ea8a0'
					detailItem['prize']='9169ffef-8c58-4051-8fa6-ebc7c702d20d'
					detailItem['winningRate']= 24.81
					detailItem['sort']= 2
				}
				details.push(detailItem)
			}
			data['details']= JSON.stringify(details)
			$.ajax({
		        url: '../back/activity/edit',
		        type: 'post',
		        async:false,
		//        traditional: true,
		        data: data
		    }).done(function(r) {
				if (r.status == "SUCCESS") {
					layer.msg('设置成功')
					getScorelottery();
					getScorelotteryGoodsList();
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
			return false;
		})
		//获取签到活动详细设置
		function getScorelotteryGoodsList(){
			$.ajax({
		        url: '../back/activityScorelottery/prizelist',
		        type: 'get',
		        async:false,
		        data: {
		        	'sort':'sort asc'
		        }
		    }).done(function(r) {
				if (r.status == "SUCCESS") {
					var html = '';
					scorelotterygoodsList = r.data
					scorelotteryindex = r.data.length
					for(var i=0;i<r.data.length;i++){
						html += '<div class="goods-item goods-item'+i+'">'+
									'<div class="layui-col-md11">'+
										'<div class="layui-col-md4">'+
											'<div class="layui-col-md12 mb-15">'+
											    '<label class="layui-form-label">奖品名称</label>'+
											    '<div class="layui-input-block">'+
											        '<input type="text" name="goodsTitle" readonly class="layui-input" value="'+r.data[i].prizeTitle+'">'+
											        '<input type="hidden" name="goodsId" class="layui-input" value="'+r.data[i].goodsId+'">'+
											        '<input type="hidden" name="uuid" class="layui-input" value="'+r.data[i].uuid+'">'+
											    '</div>'+
											'</div>'+
											'<div class="layui-col-md12 mb-15">'+
											    '<label class="layui-form-label">奖品封面</label>'+
											    '<div class="layui-input-block">'+
											        '<div class="layui-upload">'+
														  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
														  '<input type="hidden" name="image" value="'+r.data[i].goodsCover+'" >'+
													'</div>'+
											    '</div>'+
											'</div>'+
										'</div>'+
										'<div class="layui-col-md4">'+
											'<div class="layui-col-md12 mb-15">'+
											    '<label class="layui-form-label">奖品设置</label>'+
											    '<div class="layui-input-block">'+
											    '<form class="layui-form" action="" lay-filter="first">'+
										        '<input type="radio" name="final" value="gold" title="金券" checked>'+
										        '<input type="radio" name="final" value="entity" title="金币">'+
										        '<input type="radio" name="final" value="voucher" title="实物">'+
										        '</form>'+
											    '</div>'+
											'</div>'+
										'</div>'+
										'<div class="layui-col-md4">'+
											'<img class="goodsCover" src="..'+r.data[i].goodsCoverUrl+'" />'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md1">'+
//										'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
										'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
									'</div>'+
									'<div class="clear"></div>'+
								'</div>';
					}
					$(".scorelottery .goodsList").html(html)
					for(var i=0;i<r.data.length;i++){
						upload.render({
						    elem: '.scorelottery .goods-item'+i+' #goodsList'
						    ,url: '../back/resource/add' //改成您自己的上传接口
						    ,multiple: true
						    ,before: function(obj){
						      //预读本地文件示例，不支持ie8
						    }
						    ,done: function(res,index){
						    	$('.scorelottery .goods-item'+i+' input[name="image"]').val(res.data.uuid);
						    	$('.scorelottery .goods-item'+i+' .goodsCover').attr('src','..' + res.data.url); 
						    }
						  });
					}
					$(".scorelottery .lay-delete").click(function(){
						$(this).parent().parent().remove()
					})
				}
		    })
		}
		//新增
		$(".scorelottery .lay-addNew").click(function(){
			var html = '<div class="goods-item goods-item'+checkInindex+'">'+
				'<div class="layui-col-md11">'+
					'<div class="layui-col-md4">'+
						'<div class="layui-col-md12 mb-15">'+
						    '<label class="layui-form-label">奖品名称</label>'+
						    '<div class="layui-input-block">'+
						        '<input type="text" name="goodsTitle" readonly class="layui-input">'+
						        '<input type="hidden" name="goodsId" class="layui-input">'+
						        '<input type="hidden" name="uuid" class="layui-input" value="">'+
						    '</div>'+
						'</div>'+
						'<div class="layui-col-md12 mb-15">'+
						    '<label class="layui-form-label">奖品封面</label>'+
						    '<div class="layui-input-block">'+
						        '<div class="layui-upload">'+
									  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
									  '<input type="hidden" name="image" >'+
								'</div>'+
						    '</div>'+
						'</div>'+
					'</div>'+
					'<div class="layui-col-md4">'+
						'<div class="layui-col-md12 mb-15">'+
						    '<label class="layui-form-label">奖品设置</label>'+
						    '<div class="layui-input-block">'+
						    	'<form class="layui-form" action="" lay-filter="first">'+
						        '<input type="radio" name="final" value="gold" title="金券" checked>'+
						        '<input type="radio" name="final" value="entity" title="金币">'+
						        '<input type="radio" name="final" value="voucher" title="实物">'+
						        '</form>'+
						    '</div>'+
						'</div>'+
					'</div>'+
					'<div class="layui-col-md4">'+
						'<img class="goodsCover"/>'+
					'</div>'+
				'</div>'+
				'<div class="layui-col-md1">'+
			//		'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
					'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
				'</div>'+
				'<div class="clear"></div>'+
			'</div>';
			  $(".scorelottery .goodsList").append(html)
			  scorelotteryAddUpload(scorelotteryindex)
//				$(".checkIn .goods-item"+index+" input[name='goodsTitle']").click(function(){
//					var className = $(this).parent().parent().parent().parent().parent().attr('class')
//					layer.open({
//						type: 2, 
//						title:false, 
//						area: ['80%', '80%'],
//						content: ['luckyGoodsChoose.html?classname='+className.substring(className.indexOf(' ')+1)] 
//					});
//				});
				$(".scorelottery .lay-delete").click(function(){
					$(this).parent().parent().remove()
				})
				scorelotteryindex = scorelotteryindex + 1;
		});
		function scorelotteryAddUpload(index){
			upload.render({
			    elem: '.scorelottery .goods-item'+index+' #goodsList'
			    ,url: '../back/resource/add' //改成您自己的上传接口
			    ,multiple: true
			    ,before: function(obj){
			      //预读本地文件示例，不支持ie8
			    }
			    ,done: function(res){
			    	$('.scorelottery .goods-item'+index+' input[name="image"]').val(res.data.uuid);
			    	$('.scorelottery .goods-item'+index+' .goodsCover').attr('src','..' + res.data.url); 
			    }
			});
		}
})